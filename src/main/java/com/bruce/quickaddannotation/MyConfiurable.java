package com.bruce.quickaddannotation;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author bruce ge 2020/9/23
 */
public class MyConfiurable implements Configurable {

    private SettingForm form;
    @Override
    public String getDisplayName() {
        return "QuickAddAnnotation";
    }

    @Override
    public void reset() {
        AnnotationApplicationComponent instance = AnnotationApplicationComponent.getInstance();
        form.reset(instance.getState());
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        AnnotationApplicationComponent instance = AnnotationApplicationComponent.getInstance();
        form = new SettingForm(instance.getState());
        return form.$$$getRootComponent$$$();
    }

    @Override
    public boolean isModified() {
        AnnotationApplicationComponent instance = AnnotationApplicationComponent.getInstance();
        PluginState state = instance.getState();
        return !state.getAnnotationNames().equals(form.getState());
    }

    @Override
    public void apply() throws ConfigurationException {
        AnnotationApplicationComponent instance = AnnotationApplicationComponent.getInstance();
        PluginState state = instance.getState();
        form.applySetting(state);
    }
}
