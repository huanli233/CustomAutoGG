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

    private boolean watingForSend = false;
    private boolean worldChanged = false;
    private long lastSendTime;

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
        if (watingForSend || System.currentTimeMillis() - lastSendTime <= configuration().interval.getOrDefault() ) return;
        watingForSend = true;
        worldChanged = false;
        new Timer("sendGG").schedule(new TimerTask() {
            @Override
            public void run() {
                if (!worldChanged) {
                    Laby.references().chatExecutor().chat(configuration().message.getOrDefault("GG"), false);
                    lastSendTime = System.currentTimeMillis();
                    watingForSend = false;
                }
            }
        }, getDelay());
    }

    private int getDelay() {
        try {
            return Integer.parseInt(configuration().delay.getOrDefault());
        } catch (NumberFormatException e) {
            configuration().delay.set(String.valueOf(1000));
            return 1000;
        }
    }

    public void onWorldChange() {
        worldChanged = true;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    @Override
    protected Class<? extends AutoGGConfiguration> configurationClass() {
        return AutoGGConfiguration.class;
    }
}
