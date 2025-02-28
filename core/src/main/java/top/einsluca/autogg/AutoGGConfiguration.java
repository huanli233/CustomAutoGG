package top.einsluca.autogg;

import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.ActivitySettingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.MethodOrder;

import java.util.ArrayList;
import java.util.List;

@ConfigName("settings")
public class AutoGGConfiguration extends AddonConfig {
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> defaults = new ConfigProperty<>(true);

    @TextFieldWidget.TextFieldSetting
    public final ConfigProperty<String> message = new ConfigProperty<>("gg");

    @SliderWidget.SliderSetting(min = 1, max = 10, steps = 1)
    public final ConfigProperty<Integer> delay = new ConfigProperty<>(1);

    @Exclude
    private final List<String> customFormats = new ArrayList<>();

    @Exclude
    private final List<String> customFilters = new ArrayList<>();

    @SettingSection("help")
    @ButtonWidget.ButtonSetting
    @MethodOrder(after = "delay")
    public void openServerList() {
        Laby.labyAPI().minecraft().chatExecutor().openUrl("https://github.com/EinsLucaaa/AutoGG/wiki/Supported-server");
    }

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ConfigProperty<Boolean> getDefaults() {
        return defaults;
    }

    public List<String> getCustomFormats() {
        return customFormats;
    }

    public List<String> getCustomFilters() {
        return customFilters;
    }

    @MethodOrder(after = "defaults")
    @ActivitySettingWidget.ActivitySetting
    public Activity openCustomFormats() {
        return new CustomActivity(CustomConfigsType.FORMAT);
    }

    @MethodOrder(after = "openCustomFormats")
    @ActivitySettingWidget.ActivitySetting
    public Activity openCustomFilters() {
        return new CustomActivity(CustomConfigsType.FILTER);
    }

    public enum CustomConfigsType {
        FORMAT, FILTER
    }
}
