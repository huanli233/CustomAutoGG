package top.einsluca.autogg.server;

import java.util.List;

public class AntiACConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("antiac.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Overview of the round", "Rundenübersicht");
    }

    @Override
    public List<String> getFilter() {
        return List.of("»");
    }
}
