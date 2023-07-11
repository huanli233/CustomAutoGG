package top.einsluca.autogg;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class AutoGGConfiguration extends AddonConfig {
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> custom = new ConfigProperty<>(true);

    @TextFieldWidget.TextFieldSetting
    public final ConfigProperty<String> message = new ConfigProperty<>("gg");

    @SliderWidget.SliderSetting(min = 1, max = 10, steps = 1)
    public final ConfigProperty<Integer> delay = new ConfigProperty<>(1);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    public ConfigProperty<Boolean> getCustom() {
        return custom;
    }
}
