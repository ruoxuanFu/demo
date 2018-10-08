package com.ikcrm.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class LabelSelectAC extends AppCompatActivity implements View.OnClickListener, SelectLabelListView.OnLabelSelectedListener, SelectLabelListView.OnLabelUnSelectedListener {

    private ImageView mImgBack;
    private LinearLayout mLlSelectLabel;
    private LinearLayout mLlLabelList;

    //保存list的tag
    private ArrayList<String> mLabelListTags;
    //保存list的color
    private ArrayList<String> mLabelListColors;
    //保存选择过的标签
    private ArrayList<ArraySet<LabelBean>> mLabelSetList;
    //动态添加已选中的标签
    private ArrayList<TagFlowLayout> mFlowLayoutList;
    //动态添加已选中的标签
    private ArrayList<ArrayList<LabelBean>> mFlowLayoutDataList;

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
        mImgBack.setOnClickListener(this);
    }

    private void initData() {

        mLabelListTags = new ArrayList<>();
        mLabelSetList = new ArrayList<>();
        mLabelListColors = new ArrayList<>();
        mFlowLayoutList = new ArrayList<>();
        mFlowLayoutDataList = new ArrayList<>();

        String labelData = getString(R.string.label_data);
        mLlLabelList.removeAllViews();
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
            mLabelSetList.add(new ArraySet<>());
            mLabelListColors.add(bean.getColor());

            mLlLabelList.addView(labelListView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                ArrayList<LabelBean> selectLabel = new ArrayList<>();
                for (int i = 0; i < mLlLabelList.getChildCount(); i++) {
                    //获取子View
                    SelectLabelListView labelListView = (SelectLabelListView) mLlLabelList.getChildAt(i);
                    //添加标签
                    selectLabel.addAll(labelListView.getSelectLabels());
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
                ArraySet<LabelBean> labelBeanArraySet = mLabelSetList.get(i);
                labelBeanArraySet.add(labelBean);
                break;
            }
        }
        setSelectLabels();
    }

    @Override
    public void onLabelUnSelected(Object tag, int position, LabelBean labelBean) {
        for (int i = 0; i < mLabelListTags.size(); i++) {
            if (TextUtils.equals(mLabelListTags.get(i), (CharSequence) tag)) {
                ArraySet<LabelBean> labelBeanArraySet = mLabelSetList.get(i);
                labelBeanArraySet.remove(labelBean);
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
            ArraySet<LabelBean> labelBeanArraySet = mLabelSetList.get(i);
            if (mFlowLayoutList.size() < mLabelSetList.size()) {
                TagFlowLayout tagFlowLayout = createSelectedLabels(i, new ArrayList<>(labelBeanArraySet));
                mFlowLayoutList.add(tagFlowLayout);
                mLlSelectLabel.addView(mFlowLayoutList.get(i));
            } else {
                mFlowLayoutDataList.get(i).clear();
                mFlowLayoutDataList.get(i).addAll(labelBeanArraySet);
                mFlowLayoutList.get(i).onChanged();
            }
        }
    }

    /**
     * 创建选中的标签
     */
    private TagFlowLayout createSelectedLabels(int index, ArrayList<LabelBean> list) {
        mFlowLayoutDataList.add(list);
        TagFlowLayout tagFlowLayout = new TagFlowLayout(this);
        tagFlowLayout.setMaxSelectCount(-1);
        tagFlowLayout.setAdapter(new TagAdapter<LabelBean>(mFlowLayoutDataList.get(index)) {
            @Override
            public View getView(FlowLayout parent, int position, LabelBean labelBean) {
                RadioTextView textView = (RadioTextView) LayoutInflater.from(LabelSelectAC.this)
                        .inflate(R.layout.item_rv_label, tagFlowLayout, false);
                textView.setText(labelBean.getContent());
                textView.setBgColor(mLabelListColors.get(index));
                return textView;
            }
        });
        return tagFlowLayout;
    }

    /**
     * 设置选中的标签
     */
    /*private void setSelectLabels() {
        mLlSelectLabel.removeAllViews();
        for (int i = 0; i < mLabelSetList.size(); i++) {
            ArraySet<LabelBean> labelBeanArraySet = mLabelSetList.get(i);
            TagFlowLayout tagFlowLayout = new TagFlowLayout(this);
            tagFlowLayout.setMaxSelectCount(-1);
            int final_i = i;
            tagFlowLayout.setAdapter(new TagAdapter<LabelBean>(new ArrayList<>(labelBeanArraySet)) {
                @Override
                public View getView(FlowLayout parent, int position, LabelBean labelBean) {
                    RadioTextView textView = (RadioTextView) LayoutInflater.from(LabelSelectAC.this)
                            .inflate(R.layout.item_rv_label, tagFlowLayout, false);
                    textView.setText(labelBean.getContent());
                    textView.setBgColor(mLabelListColors.get(final_i));
                    return textView;
                }
            });
            mLlSelectLabel.addView(tagFlowLayout);
        }
    }*/
}
