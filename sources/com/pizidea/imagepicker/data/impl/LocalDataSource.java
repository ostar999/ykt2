package com.pizidea.imagepicker.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.bean.ImageSet;
import com.pizidea.imagepicker.data.DataSource;
import com.pizidea.imagepicker.data.OnImagesLoadedListener;
import com.umeng.analytics.pro.aq;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class LocalDataSource implements DataSource, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ALL = 0;
    public static final int LOADER_CATEGORY = 1;
    OnImagesLoadedListener imagesLoadedListener;
    Context mContext;
    private final String[] IMAGE_PROJECTION = {"_data", "_display_name", "date_added", aq.f22519d};
    private ArrayList<ImageSet> mImageSetList = new ArrayList<>();

    public LocalDataSource(Context context) {
        this.mContext = context;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public Loader<Cursor> onCreateLoader(int i2, Bundle bundle) {
        if (i2 == 0) {
            return new CursorLoader(this.mContext, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.IMAGE_PROJECTION, null, null, this.IMAGE_PROJECTION[2] + " DESC");
        }
        if (i2 != 1) {
            return null;
        }
        return new CursorLoader(this.mContext, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.IMAGE_PROJECTION, this.IMAGE_PROJECTION[0] + " like '%" + bundle.getString("path") + "%'", null, this.IMAGE_PROJECTION[2] + " DESC");
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override // com.pizidea.imagepicker.data.DataSource
    public void provideMediaItems(OnImagesLoadedListener onImagesLoadedListener) {
        this.imagesLoadedListener = onImagesLoadedListener;
        Context context = this.mContext;
        if (!(context instanceof FragmentActivity)) {
            throw new RuntimeException("your activity must be instance of FragmentActivity");
        }
        ((FragmentActivity) context).getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        this.mImageSetList.clear();
        if (cursor != null) {
            ArrayList arrayList = new ArrayList();
            if (cursor.getCount() <= 0) {
                return;
            }
            cursor.moveToFirst();
            do {
                try {
                    String string = cursor.getString(cursor.getColumnIndexOrThrow(this.IMAGE_PROJECTION[0]));
                    String string2 = cursor.getString(cursor.getColumnIndexOrThrow(this.IMAGE_PROJECTION[1]));
                    long j2 = cursor.getLong(cursor.getColumnIndexOrThrow(this.IMAGE_PROJECTION[2]));
                    if (!TextUtils.isEmpty(string)) {
                        File file = new File(string);
                        ImageItem imageItem = new ImageItem(string, string2, j2);
                        arrayList.add(imageItem);
                        File parentFile = file.getParentFile();
                        ImageSet imageSet = new ImageSet();
                        imageSet.name = parentFile.getName();
                        imageSet.path = parentFile.getAbsolutePath();
                        imageSet.cover = imageItem;
                        if (this.mImageSetList.contains(imageSet)) {
                            ArrayList<ImageSet> arrayList2 = this.mImageSetList;
                            arrayList2.get(arrayList2.indexOf(imageSet)).imageItems.add(imageItem);
                        } else {
                            ArrayList arrayList3 = new ArrayList();
                            arrayList3.add(imageItem);
                            imageSet.imageItems = arrayList3;
                            this.mImageSetList.add(imageSet);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } while (cursor.moveToNext());
            ImageSet imageSet2 = new ImageSet();
            imageSet2.name = this.mContext.getResources().getString(R.string.all_images);
            imageSet2.cover = (ImageItem) arrayList.get(0);
            imageSet2.imageItems = arrayList;
            imageSet2.path = "/";
            if (this.mImageSetList.contains(imageSet2)) {
                this.mImageSetList.remove(imageSet2);
            }
            this.mImageSetList.add(0, imageSet2);
            this.imagesLoadedListener.onImagesLoaded(this.mImageSetList);
            AndroidImagePicker.getInstance().setImageSets(this.mImageSetList);
        }
    }
}
