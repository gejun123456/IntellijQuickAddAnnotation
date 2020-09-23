package com.bruce.quickaddannotation;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author bruce ge 2020/9/22
 */
public class AddAnnotationAction extends BaseGenerateAction {
    public AddAnnotationAction() {
        super(new AddAnnotationHandler());
    }


}
