package com.bruce.quickaddannotation;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AddEditDeleteListPanel;
import com.intellij.ui.ListSpeedSearch;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author bruce ge
 */
public class MyAddDeleteListPanel extends AddEditDeleteListPanel<String> {

    private List<String> propretyList;

    public MyAddDeleteListPanel(String title, List<String> propertyList, List<String> methodNameList) {
        super(title, methodNameList);
        this.propretyList = propertyList;
        new ListSpeedSearch(myList);
    }

    public List<String> getPropretyList() {
        return propretyList;
    }

    public void setPropretyList(List<String> propretyList) {
        this.propretyList = propretyList;
    }

    @Override
    @Nullable
    protected String findItemToAdd() {
        return showEditDialog("", "Add Value");
    }

    @Nullable
    private String showEditDialog(final String initialValue, String title) {
//            return Messages.showMultilineInputDialog(this.project, "", title, initialValue, Messages.getQuestionIcon(), new InputValidatorEx() {
//                @Override
//                public boolean checkInput(String inputString) {
//                    return !StringUtil.isEmpty(inputString);
//                }
//
//                @Override
//                public boolean canClose(String inputString) {
//                    return !StringUtil.isEmpty(inputString);
//                }
//
//                @Nullable
//                @Override
//                public String getErrorText(String inputString) {
//                    if (!checkInput(inputString)) {
//                        return "value shall not be empty";
//                    }
//                    return null;
//                }
//            });
        String s = Messages.showInputDialog("Annotation", "Add Annotation", null, null, null);
        if (StringUtils.isNotBlank(s)) {
            return s;
        }
        return initialValue;
    }

    public void resetFrom(List<String> patterns) {
        myListModel.clear();
        patterns.stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(myListModel::addElement);
    }

    public List<String> getValues() {
        List<String> result = Lists.newArrayList();
        Object[] listItems = this.getListItems();
        for (Object listItem : listItems) {
            result.add((String) listItem);
        }
        return result;
    }

    void applyTo(List<String> patterns) {
        patterns.clear();
        for (Object o : getListItems()) {
            patterns.add((String) o);
        }
    }

    public void addRule(String rule) {
        addElement(rule);
    }

    @Override
    protected String editSelectedItem(String item) {
        return showEditDialog(item, "Edit Value");
    }
}
