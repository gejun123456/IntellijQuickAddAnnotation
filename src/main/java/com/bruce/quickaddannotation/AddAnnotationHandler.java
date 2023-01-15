package com.bruce.quickaddannotation;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.ClassMember;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.codeInsight.generation.PsiElementClassMember;
import com.intellij.codeInsight.generation.PsiFieldMember;
import com.intellij.ide.util.MemberChooser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.util.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * @author bruce ge 2020/9/22
 */
public class AddAnnotationHandler implements CodeInsightActionHandler {
    private static final Logger LOG = Logger.getInstance("#AddAnnotationHandler");


    @Override
    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (!EditorModificationUtil.checkModificationAllowed(editor)) return;
        if (!FileDocumentManager.getInstance().requestWriting(editor.getDocument(), project)) {
            return;
        }

        final PsiClass aClass = OverrideImplementUtil.getContextClass(project, editor, file, false);
        if (aClass == null || aClass.isInterface()) return; //?
        LOG.assertTrue(aClass.isValid());
        LOG.assertTrue(aClass.getContainingFile() != null);
        String s1 = Messages.showInputDialog(project, "input your package name", "PackageName", null);
        if(StringUtils.isBlank(s1)){
            Messages.showErrorDialog(project,"package name shall not be empty","error");
            return;
        }
        PsiPackage aPackage = JavaPsiFacade.getInstance(project).findPackage(s1);
        //find all class in aPackage.
        PsiClass[] classes = aPackage.getClasses();
        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                for (PsiClass psiClass : classes) {
                    PsiModifierList modifierList = psiClass.getModifierList();
                    //add annotation to psiClass
                    PsiMethod[] methods = psiClass.getMethods();
                    for (PsiMethod method : methods) {
                        PsiModifierList modifierList1 = method.getModifierList();
                        modifierList1.addAnnotation("com.baomidou.mybatisplus.annotation.InterceptorIgnore(tenantLine = \"true\")");
                    }
                    JavaCodeStyleManager.getInstance(project).shortenClassReferences(psiClass);
                }
            }
        });
//        try {
//            final ClassMember[] members = chooseOriginalMembers(aClass, project, editor);
//            if (members == null) return;
//
//            ChooseAnnotationForm chooseAnnotationForm = new ChooseAnnotationForm(project, false);
//            boolean b = chooseAnnotationForm.showAndGet();
//            if (b) {
//                Object selectedItem = chooseAnnotationForm.comboBox1.getSelectedItem();
//                String s = ObjectUtils.tryCast(selectedItem, String.class);
//                for (ClassMember member : members) {
//                    if (member instanceof PsiFieldMember) {
//                        PsiFieldMember psiFieldMember = ObjectUtils.tryCast(member, PsiFieldMember.class);
//                        PsiField element = psiFieldMember.getElement();
//                        PsiAnnotation annotationFromText = PsiElementFactory.SERVICE.getInstance(project).createAnnotationFromText(s, element);
//                        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
//                            @Override
//                            public void run() {
//                                element.getModifierList().addAfter(annotationFromText, null);
//                                JavaCodeStyleManager.getInstance(project).shortenClassReferences(element);
//                            }
//                        });
//                    }
//                }
//
//
//            }
//        } finally {
////            cleanup();
//        }

    }

    private ClassMember[] chooseOriginalMembers(PsiClass aClass, Project project, Editor editor) {
        PsiField[] fields = aClass.getFields();
        List<ClassMember> myMembers = Lists.newArrayList();
        for (PsiField field : fields) {
            PsiFieldMember psiFieldMember = new PsiFieldMember(field);
            myMembers.add(psiFieldMember);
        }
        ClassMember[] allMembers = myMembers.toArray(new ClassMember[0]);
        return chooseMembers(allMembers, false, false, project, null);
    }


    @Nullable
    protected ClassMember[] chooseMembers(ClassMember[] members,
                                          boolean allowEmptySelection,
                                          boolean copyJavadocCheckbox,
                                          Project project,
                                          @Nullable Editor editor) {
        MemberChooser<ClassMember> chooser = createMembersChooser(members, allowEmptySelection, copyJavadocCheckbox, project);
        if (editor != null) {
            final int offset = editor.getCaretModel().getOffset();

            ClassMember preselection = null;
            for (ClassMember member : members) {
                if (member instanceof PsiElementClassMember) {
                    final PsiDocCommentOwner owner = ((PsiElementClassMember) member).getElement();
                    if (owner != null) {
                        final TextRange textRange = owner.getTextRange();
                        if (textRange != null && textRange.contains(offset)) {
                            preselection = member;
                            break;
                        }
                    }
                }
            }
            if (preselection != null) {
                chooser.selectElements(new ClassMember[]{preselection});
            }
        }

        chooser.show();
//        myToCopyJavaDoc = chooser.isCopyJavadoc();
        final List<ClassMember> list = chooser.getSelectedElements();
        return list == null ? null : list.toArray(ClassMember.EMPTY_ARRAY);
    }


    protected MemberChooser<ClassMember> createMembersChooser(ClassMember[] members,
                                                              boolean allowEmptySelection,
                                                              boolean copyJavadocCheckbox,
                                                              Project project) {
        MemberChooser<ClassMember> chooser = new MemberChooser<ClassMember>(members, allowEmptySelection, true, project, getHeaderPanel(project), getOptionControls()) {
            @Nullable
            @Override
            protected String getHelpId() {
                return "Add Annotation to field";
            }
        };
        chooser.setTitle("Add Annotation to Field");
        chooser.setCopyJavadocVisible(copyJavadocCheckbox);
        return chooser;
    }

    private JComponent[] getOptionControls() {
        return null;
    }

    private JPanel getHeaderPanel(Project project) {
        return null;
    }


    @Override
    public boolean startInWriteAction() {
        return false;
    }
}



