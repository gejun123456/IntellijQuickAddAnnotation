package com.bruce.quickaddannotation;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author bruce ge 2020/9/23
 */
@State(name = "QuickAddAnnotation", storages = {@Storage(file =
        "$APP_CONFIG$/QuickAddAnnotation.xml")})
public class AnnotationApplicationComponent implements
        PersistentStateComponent<PluginState> {


    private PluginState settings;


    public AnnotationApplicationComponent() {

    }


    public static AnnotationApplicationComponent getInstance() {
        return ServiceManager.getService(AnnotationApplicationComponent.class);
    }


    @Override
    public PluginState getState() {
        if (settings == null) {
            settings = new PluginState();
        }
        return settings;
    }

    @Override
    public void loadState(PluginState state) {
        this.settings = state;
    }
}
