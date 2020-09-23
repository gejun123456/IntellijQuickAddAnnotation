package com.bruce.quickaddannotation;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author bruce ge 2020/9/23
 */
public class PluginState {
    private List<String> annotationNames = Lists.newArrayList("@com.fasterxml.jackson.annotation.JsonIgnore"
    ,"@javax.validation.constraints.NotNull","@javax.validation.constraints.NotBlank"
    ,"@javax.validation.constraints.NotEmpty",
            "@javax.validation.constraints.Email");


    public List<String> getAnnotationNames() {
        return annotationNames;
    }

    public void setAnnotationNames(List<String> annotationNames) {
        this.annotationNames = annotationNames;
    }
}
