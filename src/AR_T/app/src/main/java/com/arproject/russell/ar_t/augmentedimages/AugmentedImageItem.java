package com.arproject.russell.ar_t.augmentedimages;

public class AugmentedImageItem {

    private String title;
    private String desc;
    private int resId;

    private AugmentedImagesFragment.CONTENT_TYPE type;

    public AugmentedImageItem(String title, String desc, AugmentedImagesFragment.CONTENT_TYPE type, int resId) {
        this.title = title;
        this.desc = desc;
        this.resId = resId;
        this.type = type;
    }

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

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public AugmentedImagesFragment.CONTENT_TYPE getType() {
        return type;
    }

    public void setType(AugmentedImagesFragment.CONTENT_TYPE type) {
        this.type = type;
    }
}
