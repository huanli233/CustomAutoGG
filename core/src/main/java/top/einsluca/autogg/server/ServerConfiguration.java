package top.einsluca.autogg.server;

import java.util.List;

public interface ServerConfiguration {

    List<String> getServerAddress();

    List<String> getFormat();

    List<String> getFilter();

}
