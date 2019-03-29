package main.java.core.loader;

public class ResourceLoader {

    private static class ResourceLoaderHolder {
        private static final ResourceLoader INSTANCE = new ResourceLoader();
    }

    private ResourceLoader() { }

    public ResourceLoader getInstance() {
        return ResourceLoaderHolder.INSTANCE;
    }



}
