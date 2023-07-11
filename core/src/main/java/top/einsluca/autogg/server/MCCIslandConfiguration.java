package top.einsluca.autogg.server;

import java.util.List;

public class MCCIslandConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("mccisland.net", "play.mccisland.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Game Over!");
    }

    @Override
    public List<String> getFilter() {
        return List.of(":");
    }
}
