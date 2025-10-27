package com.pizidea.imagepicker.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class ImageSet implements Serializable {
    public ImageItem cover;
    public List<ImageItem> imageItems;
    public String name;
    public String path;

    public boolean equals(Object obj) {
        try {
            ImageSet imageSet = (ImageSet) obj;
            if (this.path.equalsIgnoreCase(imageSet.path)) {
                if (this.name.equalsIgnoreCase(imageSet.name)) {
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
