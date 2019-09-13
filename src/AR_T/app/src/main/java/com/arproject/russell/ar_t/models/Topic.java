package com.arproject.russell.ar_t.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {

    private String title;
    private String desc;
    private int imageId;
    private int backgroundId;
    private String contents;

    public Topic(String title, String desc, int imageId, int backgroundId, String contents) {
        this.title = title;
        this.desc = desc;
        this.imageId = imageId;
        this.backgroundId = backgroundId;
        this.contents = contents;
    }

    protected Topic(Parcel in) {
        title = in.readString();
        desc = in.readString();
        imageId = in.readInt();
        backgroundId = in.readInt();
        contents = in.readString();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeInt(imageId);
        parcel.writeInt(backgroundId);
        parcel.writeString(contents);
    }
}
