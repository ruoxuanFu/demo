package com.ikcrm.testapplication;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by furuoxuan on 2018-09-12.
 */
public class ExpLabelView extends AbsExpView {

    protected TextView txt_lable, txt_content, txt_needed;
    protected ImageView iv_arrow;
    protected FlexboxLayout flb_label;
    protected RelativeLayout rvLayout;
    protected LinearLayout lay_right;

    public ExpLabelView(Context context) {
        this(context, null);
    }

    public ExpLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = LayoutInflater.from(context).inflate(R.layout.exp_label_layout, this, true);
        lay_right = rootView.findViewById(R.id.lay_right);
        txt_lable = rootView.findViewById(R.id.txt_lable);
        txt_content = rootView.findViewById(R.id.txt_content);
        txt_needed = rootView.findViewById(R.id.txt_needed);
        iv_arrow = rootView.findViewById(R.id.iv_arrow);
        flb_label = rootView.findViewById(R.id.flb_label);
        rvLayout = rootView.findViewById(R.id.rvLayout);
    }

    @Override
    public void initDate(FieldModel fieldModel) {
        super.fieldModel = fieldModel;
        super.isRequried = fieldModel.required;

        setCanEdit(!fieldModel.cannot_edit);
        setHintText(fieldModel.placeholder);
        setLable(fieldModel.label);
    }

    @Override
    public void setCanEdit(boolean canEdit) {
        this.isCanEdit = canEdit;
        iv_arrow.setVisibility(canEdit ? VISIBLE : INVISIBLE);
        txt_needed.setVisibility(isCanEdit && isRequried && TextUtils.isEmpty(getContent())
                ? VISIBLE : GONE);

        setLableColor(isCanEdit ? R.color.color_blue : R.color.color_gray);
    }

    public void setRvBg(int color) {
        rvLayout.setBackgroundColor(getResources().getColor(color));
    }

    @Override
    public void setHintText(String hintText) {
        if (!TextUtils.isEmpty(hintText)) {
            txt_content.setHint(hintText);
        }
    }

    @Override
    public void setContent(String content, String tip) {
        txt_lable.setVisibility(GONE);
        txt_content.setText(tip);
        flb_label.removeAllViews();
        for (int i = 0; i < 20; i++) {
            TextView textView = newTextView(i);
            flb_label.addView(textView);

            FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) textView.getLayoutParams();
            lp.setFlexShrink(0);
            textView.setLayoutParams(lp);
        }
    }

    @Override
    public String getContent() {
        String temp = txt_content.getText().toString();
        if (TextUtils.isEmpty(temp) || temp.equals("请选择") || temp.equals("不选择")) {
            return "";
        } else {
            return temp;
        }
    }

    @Override
    public void setLable(String lable) {
        if (txt_lable.getVisibility() == GONE) {
            txt_lable.setVisibility(VISIBLE);
        }
        txt_lable.setText(lable);
        setHintText(lable);
    }

    @Override
    public void setLableColor(int colorId) {
        if (colorId > 0) {
            txt_lable.setTextColor(getResources().getColor(colorId));
            txt_content.setHintTextColor(getResources().getColor(colorId));
        }
    }

    @Override
    public void setRequired(boolean isRequired) {
        txt_needed.setTextColor(getResources().getColor(
                isRequired ? R.color.color_line_orange : R.color.color_gray));

        boolean isEmpty = TextUtils.isEmpty(getContent());
        txt_needed.setText((isRequired && isEmpty) ? "必填" : "");
        txt_needed.setVisibility((isRequired && isCanEdit && isEmpty) ? VISIBLE : GONE);

        setParamValue(paramValue);
    }

    @Override
    public void setMEnable(boolean isEnable) {
        this.isEnable = isEnable;
        txt_content.setEnabled(isEnable);
    }

    @Override
    public void setShowArrow(boolean isShow) {
        iv_arrow.setVisibility(isShow ? VISIBLE : INVISIBLE);
        if (!isShow) {
            setHintText("");
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "跳转到标签界面", Toast.LENGTH_SHORT).show();
    }

    private TextView newTextView(int mCount) {
        final RadioTextView textView = new RadioTextView(getContext());
        textView.setBgColor("#037BFF");
        if (mCount % 4 == 0) {
            textView.setText("第" + mCount + "个TextView");
        } else if (mCount % 4 == 1) {
            textView.setText("第" + mCount + "个");
        } else if (mCount % 4 == 2) {
            textView.setText("TextView --- " + mCount);
        } else if (mCount % 5 == 0) {
            textView.setText("This TextView text is " + mCount);
        } else if (mCount % 7 == 0) {
            textView.setText("My Count is " + mCount + " and My Count % 7 is 0");
        } else {
            textView.setText("I'm + " + mCount + "~~");
        }
        textView.setPadding(15, 8, 10, 15);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setTextColor(Color.WHITE);
        textView.setTag(mCount);

        return textView;
    }
}
