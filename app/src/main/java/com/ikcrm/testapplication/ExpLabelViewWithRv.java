package com.ikcrm.testapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

/**
 * Created by furuoxuan on 2018-09-12.
 */
public class ExpLabelViewWithRv extends AbsExpView {

    private RelativeLayout rl_base, mRlContent;
    private ImageView iv_arrow;
    private TextView txt_label;
    private TextView txt_content;
    private TagFlowLayout mTfLabels;

    private boolean isEnable = true;

    private ArrayList<LabelBean> mData;

    private OnLabelTitleClickListener mOnLabelTitleClickListener;

    public void setOnLabelTitleClickListener(OnLabelTitleClickListener onLabelTitleClickListener) {
        mOnLabelTitleClickListener = onLabelTitleClickListener;
    }

    interface OnLabelTitleClickListener {
        void onLabelTitleClick();
    }

    @Override
    public void setOnExChangeListener(OnExChangeListener listener) {
        super.setOnExChangeListener(listener);
    }

    public ExpLabelViewWithRv(Context context) {
        this(context, null);
    }

    public ExpLabelViewWithRv(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpLabelViewWithRv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = LayoutInflater.from(context).inflate(R.layout.exp_label_layout_with_rc, this, true);

        rl_base = rootView.findViewById(R.id.rvLayout);
        mRlContent = rootView.findViewById(R.id.rvContent);
        iv_arrow = rootView.findViewById(R.id.iv_arrow);
        txt_label = rootView.findViewById(R.id.txt_lable);
        txt_content = rootView.findViewById(R.id.txt_content);
        mTfLabels = rootView.findViewById(R.id.tf_labels);

        mData = new ArrayList<>();

        mTfLabels.setVisibility(GONE);
        iv_arrow.setVisibility(VISIBLE);
        mRlContent.setOnClickListener(this);
    }

    @Override
    public void initDate(FieldModel fieldModel) {
        super.fieldModel = fieldModel;
        super.isRequried = fieldModel.required;

        setCanEdit(!fieldModel.cannot_edit);
        setHintText(fieldModel.placeholder);
        setLable(fieldModel.label);
        mTfLabels.setMaxSelectCount(0);
    }

    @Override
    public void onClick(View v) {
        if (isEnable) {
            if (v.getId() == R.id.rvContent) {
                if (mOnLabelTitleClickListener != null) {
                    mOnLabelTitleClickListener.onLabelTitleClick();
                }
            }
        }
    }

    @Override
    public void setCanEdit(boolean canEdit) {
        this.isCanEdit = canEdit;
    }

    @Override
    public void setHintText(String hintText) {
        if (!TextUtils.isEmpty(hintText)) txt_content.setHint(hintText);
    }

    @Override
    public void setContent(String content, String hint) {
    }

    public void setContent(ArrayList<LabelBean> arrayList) {
        int l_color = isCanEdit ? R.color.color_blue : R.color.color_gray;
        if (mData != null) {
            mData.clear();
            mData.addAll(arrayList);
            setLableColor();
            StringBuilder stringBuffer = new StringBuilder();
            if (!mData.isEmpty()) {
                for (LabelBean labelBean : mData) {
                    stringBuffer.append(labelBean.getContent()).append(",");
                }
                stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            setParamValue(stringBuffer.toString());
            if (mTfLabels.getAdapter() != null) {
                mTfLabels.onChanged();
            } else {
                mTfLabels.setAdapter(new TagAdapter<LabelBean>(mData) {
                    @Override
                    public View getView(FlowLayout parent, int position, LabelBean labelBean) {
                        RadioTextView textView = (RadioTextView) LayoutInflater.from(getContext())
                                .inflate(R.layout.item_rv_label, mTfLabels, false);
                        textView.setText(labelBean.getContent());
                        textView.setBgColor(labelBean.getColor());
                        return textView;
                    }
                });
            }
        } else {
            Log.e("ExpLabelViewWithRv", "setContent is null");
        }
    }

    @Override
    public String getContent() {
        if (mData != null && !mData.isEmpty() && mTfLabels.getVisibility() == VISIBLE
                && mTfLabels.getSelectedList() != null && !mTfLabels.getSelectedList().isEmpty()) {
            StringBuilder stringBuffer = new StringBuilder();
            for (int index : mTfLabels.getSelectedList()) {
                stringBuffer.append(mData.get(index)).append(",");
            }
            return stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
        } else {
            return "";
        }
    }

    @Override
    public void setLable(String lable) {
        txt_label.setText("标签");
        txt_content.setText("请选择");
    }

    public void setData(ArrayList<LabelBean> data) {
        mData = data;
    }

    @Override
    public void setLableColor(int lableColor) {
    }

    public void setLableColor() {
        txt_label.setTextColor(getResources().getColor(R.color.color_blue));
        txt_content.setTextColor(getResources().getColor(R.color.color_gray));
        if (mData != null && !mData.isEmpty()) {
            txt_content.setVisibility(GONE);
            mTfLabels.setVisibility(VISIBLE);
        } else {
            txt_content.setVisibility(VISIBLE);
            mTfLabels.setVisibility(GONE);
        }
    }

    @Override
    public void setRequired(boolean isRequired) {
    }

    @Override
    public void setMEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public void setShowArrow(boolean isShow) {
        iv_arrow.setVisibility(isShow ? VISIBLE : INVISIBLE);
    }
}
