package top.einsluca.autogg;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AutoActivity
@Link("manage.lss")
@Link("overview.lss")
public class CustomActivity extends Activity {

    private final AutoGGAddon addon;
    private final VerticalListWidget<TextViewWidget> contentList;
    private final Map<String, TextViewWidget> contentWidgets;

    private TextViewWidget selectedContent;

    private ButtonWidget removeButton;
    private ButtonWidget editButton;

    private FlexibleContentWidget inputWidget;

    private Action action;

    private final AutoGGConfiguration.CustomConfigsType type;

    public CustomActivity(AutoGGConfiguration.CustomConfigsType type) {
        this.type = type;
        this.addon = AutoGGAddon.get();

        this.contentWidgets = new HashMap<>();
        if (type == AutoGGConfiguration.CustomConfigsType.FORMAT) {
            this.addon.configuration().getCustomFormats().forEach(content -> {
                this.contentWidgets.put(content, new TextViewWidget(content));
            });
        } else {
            this.addon.configuration().getCustomFilters().forEach(content -> {
                this.contentWidgets.put(content, new TextViewWidget(content));
            });
        }

        this.contentList = new VerticalListWidget<>();
        this.contentList.addId("content-list");
        this.contentList.setSelectCallback(widget -> {
            TextViewWidget selected = this.contentList.listSession().getSelectedEntry();
            if (selected == null
                    || !Objects.equals(selected.content, widget.content)) {
                this.editButton.setEnabled(true);
                this.removeButton.setEnabled(true);
            }
        });

        this.contentList.setDoubleClickCallback(nameTagWidget -> this.setAction(Action.EDIT));
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);

        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("content-container");
        for (TextViewWidget textViewWidget : this.contentWidgets.values()) {
            this.contentList.addChild(textViewWidget);
        }

        container.addFlexibleContent(new ScrollWidget(this.contentList));

        this.selectedContent = this.contentList.listSession().getSelectedEntry();
        HorizontalListWidget menu = new HorizontalListWidget();
        menu.addId("overview-button-menu");

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.add", () -> this.setAction(Action.ADD)));

        this.editButton = ButtonWidget.i18n("labymod.ui.button.edit",
                () -> this.setAction(Action.EDIT));
        this.editButton.setEnabled(this.selectedContent != null);
        menu.addEntry(this.editButton);

        this.removeButton = ButtonWidget.i18n("labymod.ui.button.remove",
                () -> this.setAction(Action.REMOVE));
        this.removeButton.setEnabled(this.selectedContent != null);
        menu.addEntry(this.removeButton);

        container.addContent(menu);
        this.document().addChild(container);
        if (this.action == null) {
            return;
        }

        DivWidget manageContainer = new DivWidget();
        manageContainer.addId("manage-container");

        Widget overlayWidget = switch (this.action) {
            case ADD -> {
                TextViewWidget newContent = new TextViewWidget("");
                yield this.initializeManageContainer(newContent);
            }
            case EDIT -> this.initializeManageContainer(this.selectedContent);
            case REMOVE -> this.initializeRemoveContainer(this.selectedContent);
        };

        manageContainer.addChild(overlayWidget);
        this.document().addChild(manageContainer);
    }

    private FlexibleContentWidget initializeRemoveContainer(TextViewWidget textViewWidget) {
        this.inputWidget = new FlexibleContentWidget();
        this.inputWidget.addId("remove-container");

        ComponentWidget confirmationWidget = ComponentWidget.i18n(
                "labymod.ui.button.remove");
        confirmationWidget.addId("remove-confirmation");
        this.inputWidget.addContent(confirmationWidget);

        TextViewWidget previewWidget = new TextViewWidget(textViewWidget.content);
        previewWidget.addId("remove-preview");
        this.inputWidget.addContent(previewWidget);

        HorizontalListWidget menu = new HorizontalListWidget();
        menu.addId("remove-button-menu");

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.remove", () -> {
            if (type == AutoGGConfiguration.CustomConfigsType.FORMAT) {
                this.addon.configuration().getCustomFormats().remove(textViewWidget.content);
            } else {
                this.addon.configuration().getCustomFilters().remove(textViewWidget.content);
            }
            this.contentWidgets.remove(textViewWidget.content);
            this.contentList.listSession().setSelectedEntry(null);
            this.setAction(null);
        }));

        menu.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));
        this.inputWidget.addContent(menu);

        return this.inputWidget;
    }

    private DivWidget initializeManageContainer(TextViewWidget textViewWidget) {
        ButtonWidget doneButton = ButtonWidget.i18n("labymod.ui.button.done");

        DivWidget inputContainer = new DivWidget();
        inputContainer.addId("input-container");

        this.inputWidget = new FlexibleContentWidget();
        this.inputWidget.addId("input-list");

        TextFieldWidget contentTextField = new TextFieldWidget();
        contentTextField.setText(textViewWidget.content);
        this.inputWidget.addContent(contentTextField);

        HorizontalListWidget buttonList = new HorizontalListWidget();
        buttonList.addId("edit-button-menu");

        contentTextField.updateListener(newValue -> {
            doneButton.setEnabled(!contentTextField.getText().trim().isEmpty());
        });
        doneButton.setPressable(() -> {
            if (textViewWidget.content.isEmpty()) {
                textViewWidget.content = contentTextField.getText();
                this.contentWidgets.put(contentTextField.getText(), textViewWidget);
                this.contentList.listSession().setSelectedEntry(textViewWidget);
            }
            textViewWidget.content = contentTextField.getText();

            if (type == AutoGGConfiguration.CustomConfigsType.FORMAT) {
                this.addon.configuration().getCustomFormats().remove(textViewWidget.content);
                this.addon.configuration().getCustomFormats().add(contentTextField.getText());
            } else {
                this.addon.configuration().getCustomFilters().remove(textViewWidget.content);
                this.addon.configuration().getCustomFilters().add(contentTextField.getText());
            }
            this.setAction(null);
        });

        buttonList.addEntry(doneButton);

        buttonList.addEntry(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.setAction(null)));
        inputContainer.addChild(this.inputWidget);
        this.inputWidget.addContent(buttonList);
        return inputContainer;
    }

    @Override
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        try {
            if (this.action != null) {
                return this.inputWidget.mouseClicked(mouse, mouseButton);
            }

            return super.mouseClicked(mouse, mouseButton);
        } finally {
            this.selectedContent = this.contentList.listSession().getSelectedEntry();
            this.removeButton.setEnabled(this.selectedContent != null);
            this.editButton.setEnabled(this.selectedContent != null);
        }
    }

    @Override
    public boolean keyPressed(Key key, InputType type) {
        if (key.getId() == 256 && this.action != null) {
            this.setAction(null);
            return true;
        }

        return super.keyPressed(key, type);
    }

    private void setAction(Action action) {
        this.action = action;
        this.reload();
    }

    private enum Action {
        ADD, EDIT, REMOVE
    }

    public static class TextViewWidget extends SimpleWidget {

        private String content;

        public TextViewWidget(String content) {
            this.content = content;
        }

        @Override
        public void initialize(Parent parent) {
            super.initialize(parent);
            ComponentWidget contentWidget = ComponentWidget.component(Component.text(this.content));
            contentWidget.addId("content");
            this.addChild(contentWidget);
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
