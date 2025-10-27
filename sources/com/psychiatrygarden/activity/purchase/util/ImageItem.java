package com.psychiatrygarden.activity.purchase.util;

import android.graphics.Bitmap;
import java.io.IOException;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class ImageItem implements Serializable {
    private Bitmap bitmap;
    public String filename;
    public String imageId;
    public String imagePath;
    public boolean isSelected = false;
    public String size;
    public String thumbnailPath;

    public Bitmap getBitmap() {
        if (this.bitmap == null) {
            try {
                this.bitmap = Bimp.revitionImageSize(this.imagePath);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return this.bitmap;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getImageId() {
        return this.imageId;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getSize() {
        return this.size;
    }

    public String getThumbnailPath() {
        return this.thumbnailPath;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
