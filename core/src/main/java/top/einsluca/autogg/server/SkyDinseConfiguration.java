package top.einsluca.autogg.server;

import java.util.List;

public class SkyDinseConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("skydinse.de", "play.skindinse.de");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Statistics of the game:", "Statistiken der Runde:");
    }

    @Override
    public List<String> getFilter() {
        return List.of("|");
    }
}
