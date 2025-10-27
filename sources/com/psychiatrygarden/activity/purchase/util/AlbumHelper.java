package com.psychiatrygarden.activity.purchase.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import com.umeng.analytics.pro.aq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class AlbumHelper {
    private static AlbumHelper instance;
    Context context;
    ContentResolver cr;
    final String TAG = getClass().getSimpleName();
    HashMap<String, String> thumbnailList = new HashMap<>();
    List<HashMap<String, String>> albumList = new ArrayList();
    HashMap<String, ImageBucket> bucketList = new HashMap<>();
    boolean hasBuildImagesBucketList = false;

    private AlbumHelper() {
    }

    private void getAlbumColumnData(Cursor cur) {
        Cursor cursor = cur;
        if (!cur.moveToFirst()) {
            return;
        }
        int columnIndex = cursor.getColumnIndex(aq.f22519d);
        int columnIndex2 = cursor.getColumnIndex("album");
        int columnIndex3 = cursor.getColumnIndex("album_art");
        int columnIndex4 = cursor.getColumnIndex("album_key");
        int columnIndex5 = cursor.getColumnIndex("artist");
        int columnIndex6 = cursor.getColumnIndex("numsongs");
        while (true) {
            int i2 = cursor.getInt(columnIndex);
            String string = cursor.getString(columnIndex2);
            String string2 = cursor.getString(columnIndex3);
            String string3 = cursor.getString(columnIndex4);
            String string4 = cursor.getString(columnIndex5);
            int i3 = columnIndex;
            int i4 = cursor.getInt(columnIndex6);
            String str = this.TAG;
            int i5 = columnIndex2;
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            int i6 = columnIndex3;
            sb.append(" album:");
            sb.append(string);
            sb.append(" albumArt:");
            sb.append(string2);
            sb.append("albumKey: ");
            sb.append(string3);
            sb.append(" artist: ");
            sb.append(string4);
            sb.append(" numOfSongs: ");
            sb.append(i4);
            sb.append("---");
            Log.i(str, sb.toString());
            HashMap<String, String> map = new HashMap<>();
            map.put(aq.f22519d, i2 + "");
            map.put("album", string);
            map.put("albumArt", string2);
            map.put("albumKey", string3);
            map.put("artist", string4);
            map.put("numOfSongs", i4 + "");
            this.albumList.add(map);
            if (!cur.moveToNext()) {
                cur.close();
                return;
            }
            cursor = cur;
            columnIndex = i3;
            columnIndex2 = i5;
            columnIndex3 = i6;
        }
    }

    public static AlbumHelper getHelper() {
        if (instance == null) {
            instance = new AlbumHelper();
        }
        return instance;
    }

    private void getThumbnail() {
        getThumbnailColumnData(this.cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{aq.f22519d, "image_id", "_data"}, null, null, null));
    }

    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int columnIndex = cur.getColumnIndex(aq.f22519d);
            int columnIndex2 = cur.getColumnIndex("image_id");
            int columnIndex3 = cur.getColumnIndex("_data");
            do {
                cur.getInt(columnIndex);
                int i2 = cur.getInt(columnIndex2);
                String string = cur.getString(columnIndex3);
                this.thumbnailList.put("" + i2, string);
            } while (cur.moveToNext());
            cur.close();
        }
    }

    public void buildImagesBucketList() throws IllegalArgumentException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        getThumbnail();
        Cursor cursorQuery = this.cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{aq.f22519d, "bucket_id", "picasa_id", "_data", "_display_name", "title", "_size", "bucket_display_name"}, null, null, null);
        if (cursorQuery.moveToFirst()) {
            int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow(aq.f22519d);
            int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("_data");
            int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("_display_name");
            int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("title");
            int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("_size");
            int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("bucket_display_name");
            int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("bucket_id");
            int columnIndexOrThrow8 = cursorQuery.getColumnIndexOrThrow("picasa_id");
            cursorQuery.getCount();
            while (true) {
                String string = cursorQuery.getString(columnIndexOrThrow);
                String string2 = cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.getString(columnIndexOrThrow2);
                String string4 = cursorQuery.getString(columnIndexOrThrow4);
                int i2 = columnIndexOrThrow;
                String string5 = cursorQuery.getString(columnIndexOrThrow5);
                int i3 = columnIndexOrThrow2;
                String string6 = cursorQuery.getString(columnIndexOrThrow6);
                int i4 = columnIndexOrThrow3;
                String string7 = cursorQuery.getString(columnIndexOrThrow7);
                int i5 = columnIndexOrThrow4;
                String string8 = cursorQuery.getString(columnIndexOrThrow8);
                int i6 = columnIndexOrThrow5;
                String str = this.TAG;
                int i7 = columnIndexOrThrow6;
                StringBuilder sb = new StringBuilder();
                sb.append(string);
                int i8 = columnIndexOrThrow7;
                sb.append(", bucketId: ");
                sb.append(string7);
                sb.append(", picasaId: ");
                sb.append(string8);
                sb.append(" name:");
                sb.append(string2);
                sb.append(" path:");
                sb.append(string3);
                sb.append(" title: ");
                sb.append(string4);
                sb.append(" size: ");
                sb.append(string5);
                sb.append(" bucket: ");
                sb.append(string6);
                sb.append("---");
                Log.i(str, sb.toString());
                ImageBucket imageBucket = this.bucketList.get(string7);
                if (imageBucket == null) {
                    imageBucket = new ImageBucket();
                    this.bucketList.put(string7, imageBucket);
                    imageBucket.imageList = new ArrayList();
                    imageBucket.bucketName = string6;
                }
                imageBucket.count++;
                ImageItem imageItem = new ImageItem();
                imageItem.imageId = string;
                imageItem.imagePath = string3;
                imageItem.thumbnailPath = this.thumbnailList.get(string);
                imageItem.filename = string2;
                imageItem.size = string5;
                imageBucket.imageList.add(imageItem);
                if (!cursorQuery.moveToNext()) {
                    break;
                }
                columnIndexOrThrow = i2;
                columnIndexOrThrow2 = i3;
                columnIndexOrThrow3 = i4;
                columnIndexOrThrow4 = i5;
                columnIndexOrThrow5 = i6;
                columnIndexOrThrow6 = i7;
                columnIndexOrThrow7 = i8;
            }
        }
        cursorQuery.close();
        for (Map.Entry<String, ImageBucket> entry : this.bucketList.entrySet()) {
            ImageBucket value = entry.getValue();
            Log.d(this.TAG, entry.getKey() + ", " + value.bucketName + ", " + value.count + " ---------- ");
            for (int i9 = 0; i9 < value.imageList.size(); i9++) {
                ImageItem imageItem2 = value.imageList.get(i9);
                Log.d(this.TAG, "----- " + imageItem2.imageId + ", " + imageItem2.imagePath + ", " + imageItem2.thumbnailPath);
            }
        }
        this.hasBuildImagesBucketList = true;
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        Log.d(this.TAG, "use time: " + (jCurrentTimeMillis2 - jCurrentTimeMillis) + " ms");
    }

    public void getAlbum() {
        getAlbumColumnData(this.cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{aq.f22519d, "album", "album_art", "album_key", "artist", "numsongs"}, null, null, null));
    }

    public List<ImageBucket> getImagesBucketList(boolean refresh) throws IllegalArgumentException {
        if (refresh || (!refresh && !this.hasBuildImagesBucketList)) {
            buildImagesBucketList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, ImageBucket>> it = this.bucketList.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        return arrayList;
    }

    public String getOriginalImagePath(String image_id) {
        String string;
        Log.i(this.TAG, "---(^o^)----" + image_id);
        String[] strArr = {aq.f22519d, "_data"};
        Cursor cursorQuery = this.cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, "_id=" + image_id, null, null);
        if (cursorQuery != null) {
            cursorQuery.moveToFirst();
            string = cursorQuery.getString(cursorQuery.getColumnIndex("_data"));
        } else {
            string = null;
        }
        cursorQuery.close();
        return string;
    }

    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
            this.cr = context.getContentResolver();
        }
    }
}
