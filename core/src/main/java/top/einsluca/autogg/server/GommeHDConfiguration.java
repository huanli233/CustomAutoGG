package top.einsluca.autogg.server;

import java.util.ArrayList;
import java.util.List;

public class GommeHDConfiguration implements ServerConfiguration {
    @Override
    public List<String> getServerAddress() {
        return List.of("gommehd.net", "play.gommehd.net", "gommehd.de");
    }

    @Override
    public List<String> getFormat() {
        return List.of("-= Statistiken dieser Runde =-", "-= Statistics of this game =-", "hat die PartyGames gewonnen!");
    }

    @Override
    public List<String> getFilter() {
        return List.of(":");
    }
}
