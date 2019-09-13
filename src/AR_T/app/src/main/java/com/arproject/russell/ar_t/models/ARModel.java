package com.arproject.russell.ar_t.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ARModel implements Parcelable {

    private int arModelId;
    private int userId;
    private String modelName;
    private String modelDesc;
    private String modelLocation;

    protected ARModel(Parcel in) {
        arModelId = in.readInt();
        userId = in.readInt();
        modelName = in.readString();
        modelDesc = in.readString();
        modelLocation = in.readString();
    }

    public static final Creator<ARModel> CREATOR = new Creator<ARModel>() {
        @Override
        public ARModel createFromParcel(Parcel in) {
            return new ARModel(in);
        }

        @Override
        public ARModel[] newArray(int size) {
            return new ARModel[size];
        }
    };

    public int getArModelId() {
        return arModelId;
    }

    public void setArModelId(int arModelId) {
        this.arModelId = arModelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getModelLocation() {
        return modelLocation;
    }

    public void setModelLocation(String modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(arModelId);
        parcel.writeInt(userId);
        parcel.writeString(modelName);
        parcel.writeString(modelDesc);
        parcel.writeString(modelLocation);
    }
}
