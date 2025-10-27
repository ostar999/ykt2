package com.pizidea.imagepicker.bean;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class ImageItem implements Serializable {
    public String name;
    public String path;
    public long time;

    public ImageItem(String str, String str2, long j2) {
        this.path = str;
        this.name = str2;
        this.time = j2;
    }

    public boolean equals(Object obj) {
        try {
            ImageItem imageItem = (ImageItem) obj;
            if (this.path.equalsIgnoreCase(imageItem.path)) {
                if (this.time == imageItem.time) {
                    return true;
                }
            }
            return false;
        } catch (ClassCastException e2) {
            e2.printStackTrace();
            return super.equals(obj);
        }
    }
}
