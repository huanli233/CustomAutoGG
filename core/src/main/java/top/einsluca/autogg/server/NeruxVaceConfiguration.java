package top.einsluca.autogg.server;

import java.util.List;

public class NeruxVaceConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("neruxvace.de", "neruxvace.net", "nerux.net", "thevace.net", "neruxvase.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Das Spiel ist beendet!");
    }

    @Override
    public List<String> getFilter() {
        return List.of("MVP", "VIP", "PLAYER", "MVP+");
    }
}
