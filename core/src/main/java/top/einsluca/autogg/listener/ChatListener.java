package top.einsluca.autogg.listener;

import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import top.einsluca.autogg.AutoGGAddon;

public class ChatListener {

    private final AutoGGAddon addon;

    public ChatListener(AutoGGAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onChat(ChatReceiveEvent event) {
        if (Laby.labyAPI().serverController().getCurrentServerData() == null) {
            return;
        }
        String message = event.chatMessage().getOriginalPlainText();

        this.addon.getServerRegistry().getServers().forEach(serverConfiguration -> {
            serverConfiguration.getServerAddress().forEach(serverAddress -> {
                if (Laby.labyAPI().serverController().getCurrentServerData().address().getHost().equals(serverAddress)) {
                    serverConfiguration.getFormat().forEach(format -> {
                        if (message.contains(format)) {
                            this.addon.sendGG();
                        }
                    });
                }

            });
        });

    }

}
