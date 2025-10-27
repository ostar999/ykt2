package com.psychiatrygarden.activity.purchase.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.purchase.activity.AlbumActivity;
import com.psychiatrygarden.activity.purchase.activity.ShowAllPhoto;
import com.psychiatrygarden.activity.purchase.util.BitmapCache;
import com.psychiatrygarden.activity.purchase.util.ImageItem;
import com.psychiatrygarden.activity.purchase.util.Res;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class FolderAdapter extends BaseAdapter {
    private DisplayMetrics dm;
    private Context mContext;
    private Intent mIntent;
    final String TAG = getClass().getSimpleName();
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() { // from class: com.psychiatrygarden.activity.purchase.adapter.b
        @Override // com.psychiatrygarden.activity.purchase.util.BitmapCache.ImageCallback
        public final void imageLoad(ImageView imageView, Bitmap bitmap, Object[] objArr) {
            this.f13675a.lambda$new$0(imageView, bitmap, objArr);
        }
    };
    ViewHolder holder = null;
    BitmapCache cache = new BitmapCache();

    public class ImageViewClickListener implements View.OnClickListener {
        private ImageView choose_back;
        private Intent intent;
        private int position;

        public ImageViewClickListener(int position, Intent intent, ImageView choose_back) {
            this.position = position;
            this.intent = intent;
            this.choose_back = choose_back;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            ShowAllPhoto.dataList = (ArrayList) AlbumActivity.contentList.get(this.position).imageList;
            Intent intent = new Intent();
            intent.putExtra("folderName", AlbumActivity.contentList.get(this.position).bucketName);
            intent.setClass(FolderAdapter.this.mContext, ShowAllPhoto.class);
            FolderAdapter.this.mContext.startActivity(intent);
            this.choose_back.setVisibility(0);
            ((Activity) FolderAdapter.this.mContext).finish();
        }
    }

    public static class ViewHolder {
        public ImageView backImage;
        public ImageView choose_back;
        public TextView fileNum;
        public TextView folderName;
        public ImageView imageView;

        private ViewHolder() {
        }
    }

    public FolderAdapter(Context c3) {
        init(c3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageView imageView, Bitmap bitmap, Object[] objArr) {
        if (imageView == null || bitmap == null) {
            Log.e(this.TAG, "callback, bmp null");
            return;
        }
        String str = (String) objArr[0];
        if (str == null || !str.equals(imageView.getTag())) {
            Log.e(this.TAG, "callback, bmp not match");
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public int dipToPx(int dip) {
        return (int) ((dip * this.dm.density) + 0.5f);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return AlbumActivity.contentList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        String str;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(Res.getLayoutID("plugin_camera_select_folder"), (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            this.holder = viewHolder;
            viewHolder.backImage = (ImageView) convertView.findViewById(Res.getWidgetID("file_back"));
            this.holder.imageView = (ImageView) convertView.findViewById(Res.getWidgetID("file_image"));
            this.holder.choose_back = (ImageView) convertView.findViewById(Res.getWidgetID("choose_back"));
            this.holder.folderName = (TextView) convertView.findViewById(Res.getWidgetID("name"));
            this.holder.fileNum = (TextView) convertView.findViewById(Res.getWidgetID("filenum"));
            this.holder.imageView.setAdjustViewBounds(true);
            this.holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView.setTag(this.holder);
        } else {
            this.holder = (ViewHolder) convertView.getTag();
        }
        if (AlbumActivity.contentList.get(position).imageList != null) {
            str = AlbumActivity.contentList.get(position).imageList.get(0).imagePath;
            this.holder.folderName.setText(AlbumActivity.contentList.get(position).bucketName);
            this.holder.fileNum.setText("" + AlbumActivity.contentList.get(position).count);
        } else {
            str = "android_hybrid_camera_default";
        }
        if (str.contains("android_hybrid_camera_default")) {
            this.holder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
        } else {
            ImageItem imageItem = AlbumActivity.contentList.get(position).imageList.get(0);
            this.holder.imageView.setTag(imageItem.imagePath);
            this.cache.displayBmp(this.holder.imageView, imageItem.thumbnailPath, imageItem.imagePath, this.callback);
        }
        ViewHolder viewHolder2 = this.holder;
        viewHolder2.imageView.setOnClickListener(new ImageViewClickListener(position, this.mIntent, viewHolder2.choose_back));
        return convertView;
    }

    public void init(Context c3) {
        this.mContext = c3;
        this.mIntent = ((Activity) c3).getIntent();
        this.dm = new DisplayMetrics();
        ((Activity) this.mContext).getWindowManager().getDefaultDisplay().getMetrics(this.dm);
    }
}
