package top.einsluca.autogg.server;

import java.util.List;

public class StayMCConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("staymc.net", "play.staymc.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("hat die Runde gewonnen!");
    }

    @Override
    public List<String> getFilter() {
        return List.of(":");
    }
}
