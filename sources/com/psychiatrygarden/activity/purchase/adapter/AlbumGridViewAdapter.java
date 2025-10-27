package com.psychiatrygarden.activity.purchase.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.psychiatrygarden.activity.purchase.util.BitmapCache;
import com.psychiatrygarden.activity.purchase.util.ImageItem;
import com.psychiatrygarden.activity.purchase.util.Res;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class AlbumGridViewAdapter extends BaseAdapter {
    private ArrayList<ImageItem> dataList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ImageItem> selectedDataList;
    final String TAG = getClass().getSimpleName();
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() { // from class: com.psychiatrygarden.activity.purchase.adapter.a
        @Override // com.psychiatrygarden.activity.purchase.util.BitmapCache.ImageCallback
        public final void imageLoad(ImageView imageView, Bitmap bitmap, Object[] objArr) {
            this.f13674a.lambda$new$0(imageView, bitmap, objArr);
        }
    };
    BitmapCache cache = new BitmapCache();
    private DisplayMetrics dm = new DisplayMetrics();

    public interface OnItemClickListener {
        void onItemClick(ToggleButton view, int position, boolean isChecked, Button chooseBt);
    }

    public class ToggleClickListener implements View.OnClickListener {
        Button chooseBt;

        public ToggleClickListener(Button choosebt) {
            this.chooseBt = choosebt;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                int iIntValue = ((Integer) toggleButton.getTag()).intValue();
                if (AlbumGridViewAdapter.this.dataList == null || AlbumGridViewAdapter.this.mOnItemClickListener == null || iIntValue >= AlbumGridViewAdapter.this.dataList.size()) {
                    return;
                }
                AlbumGridViewAdapter.this.mOnItemClickListener.onItemClick(toggleButton, iIntValue, toggleButton.isChecked(), this.chooseBt);
            }
        }
    }

    public static class ViewHolder {
        public Button choosetoggle;
        public ImageView imageView;
        public TextView textView;
        public ToggleButton toggleButton;

        private ViewHolder() {
        }
    }

    public AlbumGridViewAdapter(Context c3, ArrayList<ImageItem> dataList, ArrayList<ImageItem> selectedDataList) {
        this.mContext = c3;
        this.dataList = dataList;
        this.selectedDataList = selectedDataList;
        ((Activity) this.mContext).getWindowManager().getDefaultDisplay().getMetrics(this.dm);
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
        return this.dataList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.dataList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewInflate;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.mContext).inflate(Res.getLayoutID("plugin_camera_select_imageview"), parent, false);
            viewHolder.imageView = (ImageView) viewInflate.findViewById(Res.getWidgetID("image_view"));
            viewHolder.toggleButton = (ToggleButton) viewInflate.findViewById(Res.getWidgetID("toggle_button"));
            viewHolder.choosetoggle = (Button) viewInflate.findViewById(Res.getWidgetID("choosedbt"));
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArrayList<ImageItem> arrayList = this.dataList;
        if (((arrayList == null || arrayList.size() <= position) ? "camera_default" : this.dataList.get(position).imagePath).contains("camera_default")) {
            viewHolder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
        } else {
            ImageItem imageItem = this.dataList.get(position);
            viewHolder.imageView.setTag(imageItem.imagePath);
            this.cache.displayBmp(viewHolder.imageView, imageItem.thumbnailPath, imageItem.imagePath, this.callback);
        }
        viewHolder.toggleButton.setTag(Integer.valueOf(position));
        viewHolder.choosetoggle.setTag(Integer.valueOf(position));
        viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.choosetoggle));
        if (this.selectedDataList.contains(this.dataList.get(position))) {
            viewHolder.toggleButton.setChecked(true);
            viewHolder.choosetoggle.setVisibility(0);
        } else {
            viewHolder.toggleButton.setChecked(false);
            viewHolder.choosetoggle.setVisibility(8);
        }
        return viewInflate;
    }

    public void setOnItemClickListener(OnItemClickListener l2) {
        this.mOnItemClickListener = l2;
    }
}
