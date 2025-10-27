package com.pizidea.imagepicker;

import android.text.TextUtils;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;

/* loaded from: classes4.dex */
public class PicassoImgLoader implements ImgLoader {
    @Override // com.pizidea.imagepicker.ImgLoader
    public void onPresentImage(ImageView imageView, String str, int i2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            File file = new File(str);
            if (file.exists()) {
                Picasso.with(imageView.getContext()).load(file).centerCrop().resize((i2 / 4) * 3, (i2 / 4) * 3).placeholder(R.drawable.default_img).into(imageView);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
