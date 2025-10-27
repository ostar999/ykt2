package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/* loaded from: classes6.dex */
public class URLImageParser {
    private boolean isCircleTrue;
    private Context mContext;
    private int mImageSize;
    private TextView mTextView;

    public class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {
        private Context context;
        private String source;
        private TextView textView;
        private URLDrawable urlDrawable;

        public ImageGetterAsyncTask(Context context, String source, URLDrawable urlDrawable) {
            this.context = context;
            this.source = source;
            this.urlDrawable = urlDrawable;
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(TextView... params) {
            this.textView = params[0];
            try {
                return Picasso.with(this.context).load(this.source).get();
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(final Bitmap bitmap) {
            try {
                if (bitmap == null) {
                    this.textView.postInvalidate();
                    TextView textView = this.textView;
                    textView.setText(textView.getText());
                    return;
                }
                if (URLImageParser.this.isCircleTrue) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(this.context.getResources(), bitmap);
                    int screenWidth = (CommonUtil.getScreenWidth(this.context) - this.textView.getPaddingLeft()) - this.textView.getPaddingRight();
                    int width = (screenWidth / bitmap.getWidth()) * bitmap.getHeight();
                    bitmapDrawable.setBounds(0, 0, screenWidth, width);
                    this.urlDrawable.setBounds(0, 0, screenWidth, width);
                    this.urlDrawable.drawable = bitmapDrawable;
                } else {
                    Paint.FontMetrics fontMetrics = this.textView.getPaint().getFontMetrics();
                    int iCeil = (((int) Math.ceil(fontMetrics.descent - fontMetrics.ascent)) * 9) / 10;
                    Math.ceil(fontMetrics.descent - fontMetrics.top);
                    bitmap.getWidth();
                    bitmap.getHeight();
                    BitmapDrawable bitmapDrawable2 = new BitmapDrawable(this.context.getResources(), bitmap);
                    int intrinsicWidth = (bitmapDrawable2.getIntrinsicWidth() * iCeil) / bitmapDrawable2.getIntrinsicHeight();
                    bitmapDrawable2.setBounds(0, 0, intrinsicWidth, iCeil);
                    this.urlDrawable.setBounds(0, 0, intrinsicWidth, iCeil);
                    this.urlDrawable.drawable = bitmapDrawable2;
                }
                this.urlDrawable.invalidateSelf();
                this.textView.postInvalidate();
                TextView textView2 = this.textView;
                textView2.setText(textView2.getText());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public URLImageParser(TextView textView, Context context, int imageSize) {
        this.mTextView = textView;
        this.mContext = context;
        this.mImageSize = imageSize;
    }

    public Drawable getDrawable(String url) {
        URLDrawable uRLDrawable = new URLDrawable();
        new ImageGetterAsyncTask(this.mContext, url, uRLDrawable).execute(this.mTextView);
        return uRLDrawable;
    }

    public URLImageParser(TextView textView, Context context, boolean isCircleTrue) {
        this.mTextView = textView;
        this.mContext = context;
        this.isCircleTrue = isCircleTrue;
    }
}
