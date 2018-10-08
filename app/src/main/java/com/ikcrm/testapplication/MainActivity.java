package com.ikcrm.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mAboveView;
    private LinearLayout mBaseView;
    private LinearLayout mBlowView;
    private ExpLabelViewWithRv expLabelViewWithRv;

    private Boolean change = false;

    private ArrayList<LabelBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAboveView = findViewById(R.id.aboveView);
        mBaseView = findViewById(R.id.baseView);
        mBlowView = findViewById(R.id.blowView);
        mAboveView.removeAllViews();
        mBaseView.removeAllViews();
        mBlowView.removeAllViews();

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            if (i % 2 == 0) {
                data.add("信息" + (i + 1));
            } else if (i % 3 == 0) {
                data.add("基本信" + (i + 1));
            } else if (i % 8 == 0) {
                data.add("大数据中心" + (i + 1));
            } else if (i % 13 == 0) {
                data.add("大数据中" + (i + 1));
            } else if (i % 17 == 0) {
                data.add("大数123" + (i + 1));
            } else if (i % 11 == 0) {
                data.add("大数12" + (i + 1));
            } else if (i % 19 == 0) {
                data.add("大数123568899" + (i + 1));
            } else {
                data.add("基本信息" + (i + 1));
            }
        }

        for (int i = 0; i < 10; i++) {
            RadioTextView textView2 = new RadioTextView(this);
            textView2.setText("基本信息");
            textView2.setGravity(Gravity.CENTER);
            textView2.setPadding(10, 10, 10, 10);
            textView2.setTextSize(18);
            textView2.setBgColor("#8dca35");
            mAboveView.addView(textView2);
        }
        for (int i = 0; i < 10; i++) {
            RadioTextView textView2 = new RadioTextView(this);
            textView2.setText("基本信息");
            textView2.setGravity(Gravity.CENTER);
            textView2.setPadding(10, 10, 10, 10);
            textView2.setTextSize(18);
            textView2.setBgColor("#ef7750");
            mBlowView.addView(textView2);
        }

        mData = new ArrayList<>();

        expLabelViewWithRv = new ExpLabelViewWithRv(this);
        expLabelViewWithRv.setCanEdit(true);
        expLabelViewWithRv.setLable("标签");
        expLabelViewWithRv.setHintText("请选择");
        expLabelViewWithRv.setShowArrow(true);
        //expLabelViewWithRv.setContent(mData);
        mBaseView.addView(expLabelViewWithRv);
        expLabelViewWithRv.setOnLabelTitleClickListener(new ExpLabelViewWithRv.OnLabelTitleClickListener() {
            @Override
            public void onLabelTitleClick() {
                startActivityForResult(new Intent(MainActivity.this, LabelSelectACOther.class), 18928);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 18928 && data != null) {
                ArrayList<LabelBean> selectLabel = data.getParcelableArrayListExtra("selectLabel");
                expLabelViewWithRv.setContent(selectLabel);
                Log.d("Fmsg", "size = " + selectLabel.size());
            }
        }
    }
}
