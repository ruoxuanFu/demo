package com.ikcrm.testapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Select label list view.
 */
public class SelectLabelListView extends LinearLayout {

    private Context mContext;
    private TextView mTvLabelName;
    private TagFlowLayout mTflLabelList;

    //数据源
    private ArrayList<LabelBean> mLabelBeans;
    //点选的颜色
    private String mOnSelectedColor;

    //点击监听
    private OnLabelSelectedListener mOnLabelSelectedListener;
    private OnLabelUnSelectedListener mOnLabelUnSelectedListener;

    /**
     * The interface On label selected listener.
     */
    public interface OnLabelSelectedListener {
        /**
         * On label selected.
         *
         * @param tag       the tag
         * @param position  the position
         * @param labelBean the label bean
         */
        void onLabelSelected(Object tag, int position, LabelBean labelBean);
    }

    /**
     * The interface On label un selected listener.
     */
    public interface OnLabelUnSelectedListener {
        /**
         * On label un selected.
         *
         * @param tag       the tag
         * @param position  the position
         * @param labelBean the label bean
         */
        void onLabelUnSelected(Object tag, int position, LabelBean labelBean);
    }

    /**
     * Sets on label selected listener.
     *
     * @param onLabelSelectedListener the on label selected listener
     */
    public void setOnLabelSelectedListener(OnLabelSelectedListener onLabelSelectedListener) {
        mOnLabelSelectedListener = onLabelSelectedListener;
    }

    /**
     * Sets on label un selected listener.
     *
     * @param onLabelUnSelectedListener the on label un selected listener
     */
    public void setOnLabelUnSelectedListener(OnLabelUnSelectedListener onLabelUnSelectedListener) {
        mOnLabelUnSelectedListener = onLabelUnSelectedListener;
    }

    /**
     * Instantiates a new Select label list view.
     *
     * @param context the context
     */
    public SelectLabelListView(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Select label list view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SelectLabelListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Select label list view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public SelectLabelListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_select_label_list, this, true);
        mTvLabelName = rootView.findViewById(R.id.tvLabelName);
        mTflLabelList = rootView.findViewById(R.id.tflLabelList);
    }

    /**
     * Sets tv label name.
     *
     * @param tvLabelName the tv label name
     */
    public void setTvLabelName(String tvLabelName) {
        mTvLabelName.setText(tvLabelName);
    }

    /**
     * Sets select type.
     *
     * @param selectType the select type
     */
    public void setSelectType(String selectType) {
        if (TextUtils.equals("multi", selectType)) {
            mTflLabelList.setMaxSelectCount(-1);
        } else {
            mTflLabelList.setMaxSelectCount(1);
        }
    }

    /**
     * Sets label beans.
     *
     * @param labelBeans      the label beans
     * @param onSelectedColor the on selected color
     */
    public void setLabelBeans(List<LabelBean> labelBeans, String onSelectedColor) {
        mLabelBeans = (ArrayList<LabelBean>) labelBeans;
        mOnSelectedColor = onSelectedColor;
        if (mLabelBeans != null && !mLabelBeans.isEmpty()) {
            initLabelList();
        } else {
            Toast.makeText(mContext, "创建标签列表失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLabelList() {
        mTflLabelList.setAdapter(new TagAdapter<LabelBean>(mLabelBeans) {
            @Override
            public View getView(FlowLayout parent, int position, LabelBean labelBean) {
                RadioTextView textView = (RadioTextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.item_rv_label, mTflLabelList, false);
                textView.setText(mLabelBeans.get(position).getContent());
                return textView;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                RadioTextView textView = (RadioTextView) view;
                textView.setBgColor(mOnSelectedColor);
                if (mOnLabelSelectedListener != null) {
                    mOnLabelSelectedListener.onLabelSelected(getTag(), position, mLabelBeans.get(position));
                }
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                RadioTextView textView = (RadioTextView) view;
                textView.setBgColor("", true);
                if (mOnLabelUnSelectedListener != null) {
                    mOnLabelUnSelectedListener.onLabelUnSelected(getTag(), position, mLabelBeans.get(position));
                }
            }
        });
    }

    /**
     * Gets select labels.
     *
     * @return the select labels
     */
    public ArrayList<LabelBean> getSelectLabels() {
        ArrayList<LabelBean> selectLabels = new ArrayList<>();
        if (mLabelBeans != null && !mLabelBeans.isEmpty()
                && mTflLabelList.getSelectedList() != null && !mTflLabelList.getSelectedList().isEmpty()) {
            for (int index : mTflLabelList.getSelectedList()) {
                mLabelBeans.get(index).setColor(mOnSelectedColor);
                selectLabels.add(mLabelBeans.get(index));
            }
        }
        return selectLabels;
    }
}
