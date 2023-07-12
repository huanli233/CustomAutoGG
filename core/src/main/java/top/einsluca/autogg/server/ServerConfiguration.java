package top.einsluca.autogg.server;

import java.util.List;

public interface ServerConfiguration {

    // List of all available server addresses for this server
    List<String> getServerAddress();

    // List of all messages on which the gg message should be sent
    List<String> getFormat();

    // List of all characters for which, if present, the GG message should not be sent. (Serves for protection)
    List<String> getFilter();

}
