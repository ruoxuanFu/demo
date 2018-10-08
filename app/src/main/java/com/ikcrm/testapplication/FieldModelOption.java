package com.ikcrm.testapplication;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenhuai on 2017/9/18.
 */

public class FieldModelOption implements Serializable {

    private static final long serialVersionUID = 8455784530823620859L;

    public String select_dependence,select_was_dependence,format;
    private List<FieldOptionItem> collection_options;

    //private List<FieldOptionItem> optionsList;

    public List<FieldOptionItem> getCollection_options() {
        return collection_options;
    }

    public void setCollection_options(String options) {
        if(!TextUtils.isEmpty(options) && options.startsWith("[")){
            collection_options = JSON.parseArray(options,FieldOptionItem.class);
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
