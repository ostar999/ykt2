package com.hyphenate.easeui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.hyphenate.util.ImageUtils;

/* loaded from: classes4.dex */
public class EaseLoadLocalBigImgTask extends AsyncTask<Void, Void, Bitmap> {
    private Context context;
    private int height;
    private String path;
    private ProgressBar pb;
    private EasePhotoView photoView;
    private int width;

    public EaseLoadLocalBigImgTask(Context context, String str, EasePhotoView easePhotoView, ProgressBar progressBar, int i2, int i3) {
        this.context = context;
        this.path = str;
        this.photoView = easePhotoView;
        this.pb = progressBar;
        this.width = i2;
        this.height = i3;
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        super.onPreExecute();
        if (ImageUtils.readPictureDegree(this.path) != 0) {
            this.pb.setVisibility(0);
            this.photoView.setVisibility(4);
        } else {
            this.pb.setVisibility(4);
            this.photoView.setVisibility(0);
        }
    }

    @Override // android.os.AsyncTask
    public Bitmap doInBackground(Void... voidArr) {
        return ImageUtils.decodeScaleImage(this.path, this.width, this.height);
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(Bitmap bitmap) {
        super.onPostExecute((EaseLoadLocalBigImgTask) bitmap);
        this.pb.setVisibility(4);
        this.photoView.setVisibility(0);
        if (bitmap != null) {
            EaseImageCache.getInstance().put(this.path, bitmap);
        } else {
            bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.ease_default_image);
        }
        this.photoView.setImageBitmap(bitmap);
    }
}
