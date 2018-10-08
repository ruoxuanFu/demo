package com.ikcrm.testapplication;

import java.util.List;

/**
 * Created by furuoxuan on 2018-09-28.
 */
public class LabelGroupsBean {

    private int group_id;
    private String group_name;
    private String select_type;
    private String color;
    private List<LabelBean> labels;

    public int getGroupId() {
        return group_id;
    }

    public void setGroupId(int groupId) {
        this.group_id = groupId;
    }

    public String getGroupName() {
        return group_name;
    }

    public void setGroupName(String groupName) {
        this.group_name = groupName;
    }

    public String getSelectType() {
        return select_type;
    }

    public void setSelectType(String selectType) {
        this.select_type = selectType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<LabelBean> getLabel() {
        return labels;
    }

    public void setLabel(List<LabelBean> label) {
        this.labels = label;
    }

}
