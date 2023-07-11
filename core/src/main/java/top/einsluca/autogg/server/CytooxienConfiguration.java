package top.einsluca.autogg.server;

import java.util.List;

public class CytooxienConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("cytooxien.de", "cytooxien.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Statistiken dieser Runde:", "Statistics of this game:");
    }

    @Override
    public List<String> getFilter() {
        return List.of("-");
    }
}
