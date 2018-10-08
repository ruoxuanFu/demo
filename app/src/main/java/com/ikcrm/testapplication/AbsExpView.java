package com.ikcrm.testapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by chenhuai on 2017/8/16.
 */
public abstract class AbsExpView extends RelativeLayout implements View.OnClickListener {

    //页面基本参数设置
    protected FieldModel fieldModel;
    protected boolean isCanEdit = true;
    protected boolean isEnable = true;
    protected boolean isRequried = false;

    protected String tipString = "";
    //保存上送接口的参数
    protected String paramValue = "";

    //钱的小数位
    protected int digit_money = 2;

    public AbsExpView(Context context) {
        this(context, null);
    }

    public AbsExpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsExpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        digit_money = PreferenceUtils.getPrefInt(context, "money_digit", 2);
    }

    protected OnExpClickListener onExpClickListener;
    public void setOnExpClickListener(OnExpClickListener l) {
        this.onExpClickListener = l;
    }

    /**
     * 用于页面内容变化时内容回调
     */
    public interface OnExChangeListener{
        /**
         * @param fieldModel 当前组件初始化信息
         * @param change 变化回调内容
         * @param param  备用参数
         */
        void onChange(FieldModel fieldModel, String change, String param);
    }
    protected OnExChangeListener onExChangeListener;
    public void setOnExChangeListener(OnExChangeListener listener) {
        this.onExChangeListener = listener;
    }

    public interface OnImageDeleteListener{
        void imageDelete(AbsExpView view, String localPath, int position);
    }

    protected OnImageDeleteListener onImageDeleteListener;
    public void setOnImageDeleteListener(OnImageDeleteListener listener) {
        this.onImageDeleteListener = listener;
    }

    /**
     * 初始化基本事件
     *
     * @param fieldModel
     */
    public abstract void initDate(FieldModel fieldModel);

    /**
     * 设置是否可编辑
     *
     * @param canEdit
     */
    public abstract void setCanEdit(boolean canEdit);

    /**
     * 设置提示信息
     *
     * @param hintText
     */
    public abstract void setHintText(String hintText);


    /**
     * 设置显示内容
     *
     * @param content 内容
     * @param tip     提示
     */
    public abstract void setContent(String content, String tip);

    /**
     * 获取输入内容
     *
     * @return
     */
    public abstract String getContent();

    /**
     * 设置标签
     *
     * @return
     */
    public abstract void setLable(String lable);

    /**
     * 设置标签颜色
     *
     * @return
     */
    public abstract void setLableColor(int colorId);

    /**
     * 是否必填
     *
     * @param isRequired
     */
    public abstract void setRequired(boolean isRequired);

    /**
     * 是否可用
     *
     * @param isEnable
     */
    public abstract void setMEnable(boolean isEnable);

    /**
     * 是否显示编辑图标
     *
     * @param isShow
     */
    public abstract void setShowArrow(boolean isShow);

    /**
     * 获取接口上送参数
     * @return
     */
    public String getParamValue(){
        return paramValue;
    }

    /**
     * 动态设置上送接口参数
     * @param paramValue
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 获取出事组件信息
     * @return
     */
    public FieldModel getFieldModel() {
        return fieldModel;
    }
}

