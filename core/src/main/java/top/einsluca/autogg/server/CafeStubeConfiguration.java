package top.einsluca.autogg.server;

import java.util.List;

public class CafeStubeConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("cafestu.be", "cafestibe.eu");
    }

    @Override
    public List<String> getFormat() {
        return List.of("has won the game!", "hat das Spiel gewonnen!");
    }

    @Override
    public List<String> getFilter() {
        return List.of("|", "●", "◗");
    }
}
