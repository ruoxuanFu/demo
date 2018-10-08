package com.ikcrm.testapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by furuoxuan on 2018-09-28.
 */
public class LabelBean implements Parcelable {
    private int id;
    private String content;
    private String color;

    protected LabelBean(Parcel in) {
        id = in.readInt();
        content = in.readString();
        color = in.readString();
    }

    public static final Creator<LabelBean> CREATOR = new Creator<LabelBean>() {
        @Override
        public LabelBean createFromParcel(Parcel in) {
            return new LabelBean(in);
        }

        @Override
        public LabelBean[] newArray(int size) {
            return new LabelBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(content);
        parcel.writeString(color);
    }
}
