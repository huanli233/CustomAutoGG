package top.einsluca.autogg;

import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import top.einsluca.autogg.listener.ChatListener;
import top.einsluca.autogg.server.ServerRegistry;

import java.util.Timer;
import java.util.TimerTask;

@AddonMain
public class AutoGGAddon extends LabyAddon<AutoGGConfiguration> {

    private static AutoGGAddon instance;

    private final ServerRegistry serverRegistry = new ServerRegistry();

    private boolean alreadySent = false;

    public AutoGGAddon() {
        instance = this;
    }

    public static AutoGGAddon get() {
        return instance;
    }

    @Override
    protected void enable() {
        this.registerSettingCategory();

        this.registerListener(new ChatListener(this));
    }

    public void sendGG() {
        new Timer("sendGG").schedule(new TimerTask() {
            @Override
            public void run() {
                if (!alreadySent) {
                    Laby.references().chatExecutor().chat(configuration().message.getOrDefault("GG"), false);
                    alreadySent = true;
                }
            }
        }, 1000L * configuration().delay.getOrDefault(1));


        new Timer("resetSend").schedule(new TimerTask() {
            @Override
            public void run() {
                alreadySent = false;
            }
        }, 15000L);
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    @Override
    protected Class<? extends AutoGGConfiguration> configurationClass() {
        return AutoGGConfiguration.class;
    }
}
