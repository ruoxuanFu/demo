package com.ikcrm.testapplication;

import java.io.Serializable;

/**
 * Created by luzefeng on 2015/1/22.
 */
public class FieldModel implements Serializable {

    private static final long serialVersionUID = -2974521308712335087L;

    public int id;
    public String name,label,field_type,category,placeholder
            ,custom_column_name,status,field_type_option;
    public boolean required,cannot_edit,cannot_add;
    //用于区分页面有多个相关组件
    private String viewType;

    public FieldModelOption input_field_options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getCustom_column_name() {
        return custom_column_name;
    }

    public void setCustom_column_name(String custom_column_name) {
        this.custom_column_name = custom_column_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getField_type_option() {
        return field_type_option;
    }

    public void setField_type_option(String field_type_option) {
        this.field_type_option = field_type_option;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isCannot_edit() {
        return cannot_edit;
    }

    public void setCannot_edit(boolean cannot_edit) {
        this.cannot_edit = cannot_edit;
    }

    public boolean isCannot_add() {
        return cannot_add;
    }

    public void setCannot_add(boolean cannot_add) {
        this.cannot_add = cannot_add;
    }

    public FieldModelOption getInput_field_options() {
        return input_field_options;
    }

    public void setInput_field_options(FieldModelOption input_field_options) {
        this.input_field_options = input_field_options;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}

