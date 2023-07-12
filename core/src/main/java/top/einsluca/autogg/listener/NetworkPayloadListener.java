package top.einsluca.autogg.listener;

import com.google.gson.JsonParser;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;
import top.einsluca.autogg.AutoGGAddon;

public class NetworkPayloadListener {

    private final AutoGGAddon addon;

    public NetworkPayloadListener(AutoGGAddon addon) {
        this.addon = addon;
    }


    @Subscribe
    public void onReceive(NetworkPayloadEvent event) {
        if (!addon.configuration().getCustom().get()) {
            return;
        }
        if (event.identifier().getNamespace().equals("labymod3") & event.identifier().getPath().equals("main")) {
            PayloadReader reader = new PayloadReader(event.getPayload());
            String messageKey = reader.readString();
            String messageContent = reader.readString();

            if (messageKey.equals("autogg")) {
                addon.sendGG();
            }

        }
    }
}
