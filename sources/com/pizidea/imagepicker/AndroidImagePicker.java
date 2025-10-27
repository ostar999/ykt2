package com.pizidea.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.bean.ImageSet;
import com.pizidea.imagepicker.fileProvider.FileProviders;
import com.pizidea.imagepicker.ui.activity.ImagesGridActivity;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes4.dex */
public class AndroidImagePicker {
    public static final String KEY_PIC_PATH = "key_pic_path";
    public static final String KEY_PIC_SELECTED_POSITION = "key_pic_selected";
    public static final int REQ_CAMERA = 1431;
    public static final int REQ_PREVIEW = 2347;
    public static final String TAG = "AndroidImagePicker";
    private static AndroidImagePicker mInstance;
    private String mCurrentPhotoPath;
    private List<OnImageCropCompleteListener> mImageCropCompleteListeners;
    private List<OnImageSelectedChangeListener> mImageSelectedChangeListeners;
    private List<ImageSet> mImageSets;
    private OnImagePickCompleteListener mOnImagePickCompleteListener;
    public boolean cropMode = false;
    public int cropSize = 120;
    private int selectLimit = 9;
    private int selectMode = 1;
    private boolean shouldShowCamera = true;
    private int mCurrentSelectedImageSetPosition = 0;
    Set<ImageItem> mSelectedImages = new LinkedHashSet();

    public interface OnImageCropCompleteListener {
        void onImageCropComplete(Bitmap bitmap, float f2);
    }

    public interface OnImagePickCompleteListener {
        void onImagePickComplete(List<ImageItem> list);
    }

    public interface OnImageSelectedChangeListener {
        void onImageSelectChange(int i2, ImageItem imageItem, int i3, int i4);
    }

    public interface Select_Mode {
        public static final int MODE_MULTI = 1;
        public static final int MODE_SINGLE = 0;
    }

    private File createImageSaveFile(Context context) {
        if (!Util.isStorageEnable()) {
            File file = new File(Environment.getDataDirectory(), ("IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date())) + ".jpg");
            this.mCurrentPhotoPath = file.getAbsolutePath();
            Log.i(TAG, "=====camera path:" + this.mCurrentPhotoPath);
            return file;
        }
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!externalStoragePublicDirectory.exists()) {
            externalStoragePublicDirectory.mkdirs();
        }
        File file2 = new File(externalStoragePublicDirectory, ("IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date())) + ".jpg");
        this.mCurrentPhotoPath = file2.getAbsolutePath();
        Log.i(TAG, "=====camera path:" + this.mCurrentPhotoPath);
        return file2;
    }

    public static void galleryAddPic(Context context, String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        context.sendBroadcast(intent);
    }

    public static AndroidImagePicker getInstance() {
        if (mInstance == null) {
            synchronized (AndroidImagePicker.class) {
                if (mInstance == null) {
                    mInstance = new AndroidImagePicker();
                }
            }
        }
        return mInstance;
    }

    public static Bitmap makeCropBitmap(Bitmap bitmap, Rect rect, RectF rectF, int i2) {
        float fWidth = rectF.width() / bitmap.getWidth();
        int i3 = (int) ((rect.left - rectF.left) / fWidth);
        int i4 = (int) ((rect.top - rectF.top) / fWidth);
        int iWidth = (int) (rect.width() / fWidth);
        int iHeight = (int) (rect.height() / fWidth);
        if (i3 < 0) {
            i3 = 0;
        }
        if (i4 < 0) {
            i4 = 0;
        }
        if (i3 + iWidth > bitmap.getWidth()) {
            iWidth = bitmap.getWidth() - i3;
        }
        if (i4 + iHeight > bitmap.getHeight()) {
            iHeight = bitmap.getHeight() - i4;
        }
        int i5 = iWidth < i2 ? i2 : iWidth;
        if (iWidth <= i2) {
            i2 = i5;
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, i3, i4, iWidth, iHeight);
            return (i2 == iWidth || i2 == iHeight) ? bitmapCreateBitmap : Bitmap.createScaledBitmap(bitmapCreateBitmap, i2, i2, true);
        } catch (OutOfMemoryError unused) {
            Log.v(TAG, "OOM when create bitmap");
            return bitmap;
        }
    }

    private void notifyImageSelectedChanged(int i2, ImageItem imageItem, boolean z2) {
        if ((z2 && getSelectImageCount() > this.selectLimit) || (!z2 && getSelectImageCount() == this.selectLimit)) {
            Log.i(TAG, "=====ignore notifyImageSelectedChanged:isAdd?" + z2);
            return;
        }
        if (this.mImageSelectedChangeListeners == null) {
            return;
        }
        Log.i(TAG, "=====notify mImageSelectedChangeListeners:item=" + imageItem.path);
        Iterator<OnImageSelectedChangeListener> it = this.mImageSelectedChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onImageSelectChange(i2, imageItem, this.mSelectedImages.size(), this.selectLimit);
        }
    }

    private void setSelectMode(int i2) {
        this.selectMode = i2;
    }

    private void setShouldShowCamera(boolean z2) {
        this.shouldShowCamera = z2;
    }

    public void addOnImageCropCompleteListener(OnImageCropCompleteListener onImageCropCompleteListener) {
        if (this.mImageCropCompleteListeners == null) {
            this.mImageCropCompleteListeners = new ArrayList();
            Log.i(TAG, "=====create new ImageCropCompleteListener List");
        }
        this.mImageCropCompleteListeners.add(onImageCropCompleteListener);
        Log.i(TAG, "=====addOnImageCropCompleteListener:" + onImageCropCompleteListener.getClass().toString());
    }

    public void addOnImageSelectedChangeListener(OnImageSelectedChangeListener onImageSelectedChangeListener) {
        if (this.mImageSelectedChangeListeners == null) {
            this.mImageSelectedChangeListeners = new ArrayList();
            Log.i(TAG, "=====create new ImageSelectedListener List");
        }
        this.mImageSelectedChangeListeners.add(onImageSelectedChangeListener);
        Log.i(TAG, "=====addOnImageSelectedChangeListener:" + onImageSelectedChangeListener.getClass().toString());
    }

    public void addSelectedImageItem(int i2, ImageItem imageItem) {
        this.mSelectedImages.add(imageItem);
        Log.i(TAG, "=====addSelectedImageItem:" + imageItem.path);
        notifyImageSelectedChanged(i2, imageItem, true);
    }

    public void clearImageSets() {
        List<ImageSet> list = this.mImageSets;
        if (list != null) {
            list.clear();
            this.mImageSets = null;
        }
    }

    public void clearSelectedImages() {
        Set<ImageItem> set = this.mSelectedImages;
        if (set != null) {
            set.clear();
            Log.i(TAG, "=====clear all selected images");
        }
    }

    public void deleteOnImagePickCompleteListener(OnImagePickCompleteListener onImagePickCompleteListener) {
        if (onImagePickCompleteListener.getClass().getName().equals(this.mOnImagePickCompleteListener.getClass().getName())) {
            this.mOnImagePickCompleteListener = null;
            Log.i(TAG, "=====remove mOnImagePickCompleteListener:" + onImagePickCompleteListener.getClass().toString());
            System.gc();
        }
    }

    public void deleteSelectedImageItem(int i2, ImageItem imageItem) {
        this.mSelectedImages.remove(imageItem);
        Log.i(TAG, "=====deleteSelectedImageItem:" + imageItem.path);
        notifyImageSelectedChanged(i2, imageItem, false);
    }

    public String getCurrentPhotoPath() {
        return this.mCurrentPhotoPath;
    }

    public int getCurrentSelectedImageSetPosition() {
        return this.mCurrentSelectedImageSetPosition;
    }

    public List<ImageItem> getImageItemsOfCurrentImageSet() {
        List<ImageSet> list = this.mImageSets;
        if (list != null) {
            return list.get(this.mCurrentSelectedImageSetPosition).imageItems;
        }
        return null;
    }

    public List<ImageSet> getImageSets() {
        return this.mImageSets;
    }

    public int getSelectImageCount() {
        Set<ImageItem> set = this.mSelectedImages;
        if (set == null) {
            return 0;
        }
        return set.size();
    }

    public int getSelectLimit() {
        return this.selectLimit;
    }

    public int getSelectMode() {
        return this.selectMode;
    }

    public List<ImageItem> getSelectedImages() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mSelectedImages);
        return arrayList;
    }

    public boolean isSelect(int i2, ImageItem imageItem) {
        return this.mSelectedImages.contains(imageItem);
    }

    public boolean isShouldShowCamera() {
        return this.shouldShowCamera;
    }

    public void notifyImageCropComplete(Bitmap bitmap, int i2) {
        if (this.mImageCropCompleteListeners != null) {
            Log.i(TAG, "=====notify onImageCropCompleteListener  bitmap=" + bitmap.toString() + "  ratio=" + i2);
            Iterator<OnImageCropCompleteListener> it = this.mImageCropCompleteListeners.iterator();
            while (it.hasNext()) {
                it.next().onImageCropComplete(bitmap, i2);
            }
        }
    }

    public void notifyOnImagePickComplete() {
        if (this.mOnImagePickCompleteListener != null) {
            List<ImageItem> selectedImages = getSelectedImages();
            Log.i(TAG, "=====notify mOnImagePickCompleteListener:selected size=" + selectedImages.size());
            this.mOnImagePickCompleteListener.onImagePickComplete(selectedImages);
        }
    }

    public void onDestroy() {
        List<OnImageSelectedChangeListener> list = this.mImageSelectedChangeListeners;
        if (list != null) {
            list.clear();
            this.mImageSelectedChangeListeners = null;
        }
        List<OnImageCropCompleteListener> list2 = this.mImageCropCompleteListeners;
        if (list2 != null) {
            list2.clear();
            this.mImageCropCompleteListeners = null;
        }
        clearImageSets();
        this.mCurrentSelectedImageSetPosition = 0;
        Log.i(TAG, "=====destroy:clear all data and listeners");
    }

    public void pickAndCrop(Activity activity, boolean z2, int i2, OnImageCropCompleteListener onImageCropCompleteListener) {
        setSelectMode(0);
        setShouldShowCamera(z2);
        List<OnImageCropCompleteListener> list = this.mImageCropCompleteListeners;
        if (list != null) {
            list.clear();
        }
        addOnImageCropCompleteListener(onImageCropCompleteListener);
        this.cropMode = true;
        this.cropSize = i2;
        activity.startActivity(new Intent(activity, (Class<?>) ImagesGridActivity.class));
    }

    public void pickMulti(Activity activity, boolean z2, OnImagePickCompleteListener onImagePickCompleteListener) {
        setSelectMode(1);
        setShouldShowCamera(z2);
        setOnImagePickCompleteListener(onImagePickCompleteListener);
        this.cropMode = false;
        activity.startActivity(new Intent(activity, (Class<?>) ImagesGridActivity.class));
    }

    public void pickSingle(Activity activity, boolean z2, OnImagePickCompleteListener onImagePickCompleteListener) {
        setSelectMode(0);
        setShouldShowCamera(z2);
        setOnImagePickCompleteListener(onImagePickCompleteListener);
        this.cropMode = false;
        activity.startActivity(new Intent(activity, (Class<?>) ImagesGridActivity.class));
    }

    public void removeOnImageCropCompleteListener(OnImageCropCompleteListener onImageCropCompleteListener) {
        List<OnImageCropCompleteListener> list = this.mImageCropCompleteListeners;
        if (list == null) {
            return;
        }
        list.remove(onImageCropCompleteListener);
        Log.i(TAG, "=====remove mImageCropCompleteListeners:" + onImageCropCompleteListener.getClass().toString());
    }

    public void removeOnImageItemSelectedChangeListener(OnImageSelectedChangeListener onImageSelectedChangeListener) {
        if (this.mImageSelectedChangeListeners == null) {
            return;
        }
        Log.i(TAG, "=====remove from mImageSelectedChangeListeners:" + onImageSelectedChangeListener.getClass().toString());
        this.mImageSelectedChangeListeners.remove(onImageSelectedChangeListener);
    }

    public void setCurrentSelectedImageSetPosition(int i2) {
        this.mCurrentSelectedImageSetPosition = i2;
    }

    public void setImageSets(List<ImageSet> list) {
        this.mImageSets = list;
    }

    public void setOnImagePickCompleteListener(OnImagePickCompleteListener onImagePickCompleteListener) {
        this.mOnImagePickCompleteListener = onImagePickCompleteListener;
        Log.i(TAG, "=====setOnImagePickCompleteListener:" + onImagePickCompleteListener.getClass().toString());
    }

    public void setSelectLimit(int i2) {
        this.selectLimit = i2;
    }

    public void takePicture(Activity activity, int i2) throws IOException {
        File fileCreateImageSaveFile;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(activity.getPackageManager()) != null && (fileCreateImageSaveFile = createImageSaveFile(activity)) != null) {
            intent.putExtra("output", Uri.fromFile(fileCreateImageSaveFile));
        }
        activity.startActivityForResult(intent, i2);
    }

    public void takePicture(Fragment fragment, int i2) throws IOException {
        File fileCreateImageSaveFile;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.setFlags(67108864);
        if (intent.resolveActivity(fragment.getContext().getPackageManager()) != null && (fileCreateImageSaveFile = createImageSaveFile(fragment.getContext())) != null) {
            intent.putExtra("output", FileProviders.getUriForFile(fragment.getContext(), fileCreateImageSaveFile, intent));
        }
        fragment.startActivityForResult(intent, i2);
    }
}
