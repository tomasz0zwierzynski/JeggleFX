package main.java.core.fsm;

import javafx.util.Pair;
import main.java.core.fsm.annotation.*;
import main.java.core.fsm.exception.AnnotationException;
import main.java.core.fsm.exception.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FiniteStateMachine implements StateMachine {
    private static final Logger LOG = LogManager.getLogger(FiniteStateMachine.class);


    private Map<String, Class<?>> states = new HashMap<>();

    private Map<String, Pair<String, String>> eventTransition = new HashMap<>();

    private Object currentState;
    private String currentStateName;

    private boolean cached = true;

    private String statesPackageName;

    private final Queue<String> transitionsQueue = new LinkedBlockingQueue<>();

    private ExecutorService executorService;

    public FiniteStateMachine(String statesPackageName) {

        this.statesPackageName = statesPackageName;

        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void init() {
        executorService.execute(() -> {


            try {
                List<Class> allClasses = Arrays.asList(getClasses(statesPackageName));
                allClasses.forEach(clazz -> {
                    if (clazz.isAnnotationPresent(State.class)) {
                        addState(clazz);
                    }
                });
            } catch (ClassNotFoundException | IOException e) {
                throw new ConfigurationException("Error while loading all classes from package: " + statesPackageName);
            }

            String initialStateName = states.values().stream()
                    .filter(clazz -> clazz.getAnnotation(State.class).initial())
                    .findFirst()
                    .orElseThrow(() -> new AnnotationException("At least one class should be marked as initial in State annotation"))
                    .getAnnotation(State.class).value();

            initialize(initialStateName);
        });
    }

    private synchronized void initialize(String state) {
        Class clazz = states.get(state);

        try {
            currentState = clazz.getConstructor(StateMachine.class, StateContext.class).newInstance(this, null);

            currentStateName = state;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            // TODO: clean up
            e.printStackTrace();
        }

        Method[] currentStateMethods2 = currentState.getClass().getMethods();
        for (Method method : currentStateMethods2) {
            OnEntry exitMethod = method.getAnnotation(OnEntry.class);
            if (exitMethod != null) {
                try {
                    method.invoke(currentState);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new AnnotationException("OnExit method invoking exception");
                }
            }
        }
    }

    private synchronized void addState(Class<?> stateClass) {
        LOG.debug("Adding stateClass to stateMachine: " + stateClass.getSimpleName());

        State state = stateClass.getAnnotation(State.class);

        String stateName;
        if (state != null) {
            stateName = state.value();
        } else {
            throw new AnnotationException("State does not have State annotation");
        }

        if (!states.containsKey(stateName)) {
            states.put(stateName, stateClass);
        } else {
            LOG.warn("Class already added to possible stateClass collection");
            return;
        }

        // Pobieranie przejść
        TransitionSource[] sourceAnnotations = stateClass.getAnnotationsByType(TransitionSource.class);
        if (sourceAnnotations != null) {
            if (sourceAnnotations.length == 0) {
                // TODO: raczej to jest błąd konfiguracjiTarget
                // brak źródeł -> nie możliwe wyjście z tego stanu
            } else if (sourceAnnotations.length == 1) {
                // jedno wyjście
                LOG.debug("Adding new transition: from " + stateName + " to " + sourceAnnotations[0].target() + " on " + sourceAnnotations[0].event());
                eventTransition.put(sourceAnnotations[0].event(), new Pair<>(stateName, sourceAnnotations[0].target()));
            } else {
                TransitionSources sources = stateClass.getAnnotation(TransitionSources.class);
                if (sources != null) {
                    for (TransitionSource source : sources.value()) {
                        LOG.debug("Adding new transition: from " + stateName + " to " + source.target() + " on " + source.event());
                        eventTransition.put(source.event(), new Pair<>(stateName, source.target()));
                    }
                }
            }
        }

    }

    public void triggerEvent(String event) {
        LOG.debug("Triggering event: " + event);
        executorService.execute(()->{
            transitionsQueue.offer(event);
            checkTransitionQueue();
        });
    }

    private void checkTransitionQueue() {
        if (!transitionsQueue.isEmpty()) {
            performTransition(transitionsQueue.poll());
            checkTransitionQueue();
        }
    }

    private void performTransition(String event) {
        if (eventTransition.containsKey(event)) {
            if (eventTransition.get(event).getKey().equals(currentStateName)) {

                Method[] currentStateMethods = currentState.getClass().getMethods();
                for (Method method : currentStateMethods) {
                    OnExit exitMethod = method.getAnnotation(OnExit.class);
                    if (exitMethod != null) {
                        try {
                            LOG.debug("State: " + currentStateName + "Invoke OnExit " + method.getName() + " method");
                            method.invoke(currentState);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LOG.error("Throwable thrown while executing @OnExit method", e);
                            // throw new AnnotationException("Exception while invoking method!");
                        }
                    }
                }

                Method m = null;
                try {
                    m = currentState.getClass().getMethod("getContext");
                } catch (NoSuchMethodException e) {
                    // TODO: clean up
                    e.printStackTrace();
                }

                StateContext context = null;
                if (m != null) {
                    try {
                        context = (StateContext) m.invoke(currentState);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                currentStateName = eventTransition.get(event).getValue();

                Class clazz = states.get(currentStateName);
                try {
                    if (context != null) {
                        currentState = clazz.getConstructor(StateMachine.class, StateContext.class).newInstance(this, context);
                    } else {
                        currentState = clazz.newInstance();
                    }
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    // TODO: clean up
                    e.printStackTrace();
                }

                Method[] currentStateMethods2 = currentState.getClass().getMethods();
                for (Method method : currentStateMethods2) {
                    OnEntry exitMethod = method.getAnnotation(OnEntry.class);
                    if (exitMethod != null) {
                        try {
                            LOG.debug("State: " + currentStateName + "Invoke OnEntry " + method.getName() + " method");
                            method.invoke(currentState);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LOG.error("Throwable thrown while executing @OnEntry method", e);
                        }
                    }
                }


            }
        }
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
