package top.einsluca.autogg.server;

import java.util.List;

public class ServerConfigurationImpl implements ServerConfiguration {

    private final List<String> serverAddress;
    private final List<String> format;
    private final List<String> filter;

    public ServerConfigurationImpl(List<String> addresses, List<String> formats, List<String> filters) {
        this.serverAddress = addresses;
        this.format = formats;
        this.filter = filters;
    }

    @Override
    public List<String> getServerAddress() {
        return serverAddress;
    }

    @Override
    public List<String> getFormat() {
        return format;
    }

    @Override
    public List<String> getFilter() {
        return filter;
    }
}
