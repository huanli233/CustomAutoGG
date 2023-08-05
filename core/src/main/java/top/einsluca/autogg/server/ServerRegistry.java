package top.einsluca.autogg.server;

import java.util.ArrayList;
import java.util.List;

public class ServerRegistry {

    private final List<ServerConfiguration> servers = new ArrayList<>();

    public ServerRegistry() {
        this.servers.add(new GommeHDConfiguration());
        this.servers.add(new NeruxVaceConfiguration());
        this.servers.add(new CafeStubeConfiguration());
        this.servers.add(new CytooxienConfiguration());
        this.servers.add(new AntiACConfiguration());
        this.servers.add(new MCCIslandConfiguration());
        this.servers.add(new StayMCConfiguration());
    }

    public List<ServerConfiguration> getServers() {
        return this.servers;
    }


}
