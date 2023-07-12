package top.einsluca.autogg.server;

import java.util.List;

public class HypixelConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("hypixel.net");
    }

    @Override
    public List<String> getFormat() {
        return List.of("Game over!", "won the game!", "They captured all wools!", "YOUR STATISTICS");
    }

    @Override
    public List<String> getFilter() {
        return List.of(":");
    }
}
