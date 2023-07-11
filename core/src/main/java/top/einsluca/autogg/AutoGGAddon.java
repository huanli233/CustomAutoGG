package top.einsluca.autogg;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.models.addon.annotation.AddonMain;
import top.einsluca.autogg.listener.ChatListener;
import top.einsluca.autogg.listener.NetworkPayloadListener;
import top.einsluca.autogg.server.ServerRegistry;

import java.util.Timer;
import java.util.TimerTask;

@AddonMain
public class AutoGGAddon extends LabyAddon<AutoGGConfiguration> {

    private final ServerRegistry serverRegistry = new ServerRegistry();

    @Override
    protected void enable() {
        this.registerSettingCategory();

        this.registerListener(new ChatListener(this));
        this.registerListener(new NetworkPayloadListener(this));


    }

    public void sendGG() {
        new Timer("sendGG").schedule(new TimerTask() {
            @Override
            public void run() {
                Laby.references().chatExecutor().chat(configuration().message.getOrDefault("GG"), false);
            }
        }, 1000L*configuration().delay.getOrDefault(1));
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    @Override
    protected Class<? extends AutoGGConfiguration> configurationClass() {
        return AutoGGConfiguration.class;
    }
}
