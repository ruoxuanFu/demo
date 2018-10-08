package com.ikcrm.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class LabelSelectACOther extends AppCompatActivity implements View.OnClickListener, SelectLabelListView.OnLabelSelectedListener, SelectLabelListView.OnLabelUnSelectedListener {

    private ImageView mImgBack;
    private LinearLayout mLlSelectLabel;
    private LinearLayout mLlLabelList;

    //保存list的tag
    private ArrayList<String> mLabelListTags;
    //保存list的color
    private ArrayList<String> mLabelListColors;
    //保存选择过的标签
    private ArrayList<ArrayList<LabelBean>> mLabelSetList;
    //动态添加已选中的标签
    private ArrayList<TagFlowLayout> mFlowLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_select_ac);
        initView();
        initData();
    }

    private void initView() {
        mImgBack = findViewById(R.id.img_back);
        mLlSelectLabel = findViewById(R.id.llSelectLabel);
        mLlLabelList = findViewById(R.id.llLabelList);

        mLlSelectLabel.removeAllViews();
        mLlLabelList.removeAllViews();
        mImgBack.setOnClickListener(this);
    }

    private void initData() {

        mLabelListTags = new ArrayList<>();
        mLabelSetList = new ArrayList<>();
        mLabelListColors = new ArrayList<>();
        mFlowLayoutList = new ArrayList<>();

        String labelData = getString(R.string.label_data);
        LabelResponse fieldBean = new Gson().fromJson(labelData, LabelResponse.class);

        for (int i = 0; i < fieldBean.getData().getLabelGroups().size(); i++) {
            LabelGroupsBean bean = fieldBean.getData().getLabelGroups().get(i);
            SelectLabelListView labelListView = new SelectLabelListView(this);
            labelListView.setTvLabelName(bean.getGroupName());
            labelListView.setLabelBeans(bean.getLabel(), bean.getColor());
            labelListView.setSelectType(bean.getSelectType());
            labelListView.setOnLabelSelectedListener(this);
            labelListView.setOnLabelUnSelectedListener(this);

            labelListView.setTag("labelListView" + i);
            mLabelListTags.add("labelListView" + i);

            mLabelSetList.add(new ArrayList<>());
            mLabelListColors.add(bean.getColor());

            mLlLabelList.addView(labelListView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                ArrayList<LabelBean> selectLabel = new ArrayList<>();
                for (int i = 0; i < mLabelSetList.size(); i++) {
                    for (LabelBean labelBean : mLabelSetList.get(i)) {
                        labelBean.setColor(mLabelListColors.get(i));
                        selectLabel.add(labelBean);
                    }
                }
                if (!selectLabel.isEmpty()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putParcelableArrayListExtra("selectLabel", selectLabel);
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
        }
    }

    @Override
    public void onLabelSelected(Object tag, int position, LabelBean labelBean) {
        for (int i = 0; i < mLabelListTags.size(); i++) {
            if (TextUtils.equals(mLabelListTags.get(i), (CharSequence) tag)) {
                mLabelSetList.get(i).add(labelBean);
                break;
            }
        }
        setSelectLabels();
    }

    @Override
    public void onLabelUnSelected(Object tag, int position, LabelBean labelBean) {
        for (int i = 0; i < mLabelListTags.size(); i++) {
            if (TextUtils.equals(mLabelListTags.get(i), (CharSequence) tag)) {
                mLabelSetList.get(i).remove(labelBean);
                break;
            }
        }
        setSelectLabels();
    }

    /**
     * 设置选中的标签
     */
    private void setSelectLabels() {
        for (int i = 0; i < mLabelSetList.size(); i++) {
            if (mFlowLayoutList.size() < mLabelSetList.size()) {
                //创建选中的标签
                TagFlowLayout tagFlowLayout = createSelectedLabels(i, mLabelSetList.get(i));
                mFlowLayoutList.add(tagFlowLayout);
                mLlSelectLabel.addView(mFlowLayoutList.get(i));
            } else {
                //更新标签
                mFlowLayoutList.get(i).onChanged();
            }
        }
    }

    /**
     * 创建选中的标签
     */
    private TagFlowLayout createSelectedLabels(int index, ArrayList<LabelBean> list) {
        TagFlowLayout tagFlowLayout = new TagFlowLayout(this);
        tagFlowLayout.setMaxSelectCount(-1);
        tagFlowLayout.setAdapter(new TagAdapter<LabelBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, LabelBean labelBean) {
                RadioTextView textView = (RadioTextView) LayoutInflater.from(LabelSelectACOther.this)
                        .inflate(R.layout.item_rv_label, tagFlowLayout, false);
                textView.setText(labelBean.getContent());
                textView.setBgColor(mLabelListColors.get(index));
                return textView;
            }
        });
        return tagFlowLayout;
    }
}
