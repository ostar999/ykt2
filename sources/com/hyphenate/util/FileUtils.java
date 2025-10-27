package com.hyphenate.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;
import androidx.core.content.FileProvider;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes4.dex */
public class FileUtils {
    private static final String TAG = "FileUtils";
    public static String[] fileTypes = {"apk", "avi", ImgUtil.IMAGE_TYPE_BMP, "chm", "dll", "doc", "docx", "dos", ImgUtil.IMAGE_TYPE_GIF, "html", "jpeg", "jpg", "movie", "mp3", "dat", "mp4", "mpe", "mpeg", "mpg", "pdf", "png", "ppt", "pptx", "rar", "txt", "wav", "wma", "wmv", "xls", "xlsx", AliyunVodHttpCommon.Format.FORMAT_XML, "zip"};

    public static class MyComparator implements Comparator<File> {
        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    }

    public static String getMIMEType(File file) {
        String name = file.getName();
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(name.lastIndexOf(StrPool.DOT) + 1, name.length()).toLowerCase());
    }

    public static String getMIMEType(String str) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(str.lastIndexOf(StrPool.DOT) + 1, str.length()).toLowerCase());
    }

    public static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
    }

    public static File[] loadFiles(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            fileArrListFiles = new File[0];
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                arrayList.add(file2);
            } else if (file2.isFile()) {
                arrayList2.add(file2);
            }
        }
        MyComparator myComparator = new MyComparator();
        Collections.sort(arrayList, myComparator);
        Collections.sort(arrayList2, myComparator);
        File[] fileArr = new File[arrayList.size() + arrayList2.size()];
        System.arraycopy(arrayList.toArray(new File[arrayList.size()]), 0, fileArr, 0, arrayList.size());
        System.arraycopy(arrayList2.toArray(new File[arrayList2.size()]), 0, fileArr, arrayList.size(), arrayList2.size());
        return fileArr;
    }
}
