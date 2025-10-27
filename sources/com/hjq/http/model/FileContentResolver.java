package com.hjq.http.model;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.MediaType;

/* loaded from: classes4.dex */
public class FileContentResolver extends FileWrapper {
    private final ContentResolver mContentResolver;
    private MediaType mContentType;
    private final Uri mContentUri;
    private String mFileName;

    public FileContentResolver(Context context, Uri uri) {
        this(context.getContentResolver(), uri);
    }

    @Override // java.io.File
    public boolean delete() {
        return this.mContentResolver.delete(this.mContentUri, null, null) > 0;
    }

    @Override // java.io.File
    public boolean exists() {
        return true;
    }

    public MediaType getContentType() {
        return this.mContentType;
    }

    public Uri getContentUri() {
        return this.mContentUri;
    }

    public String getFileName() {
        return this.mFileName;
    }

    @Override // java.io.File
    @Nullable
    public File getParentFile() {
        return null;
    }

    @Override // java.io.File
    public boolean isDirectory() {
        return false;
    }

    @Override // java.io.File
    public boolean isFile() {
        return true;
    }

    @Override // java.io.File
    public boolean isHidden() {
        return false;
    }

    @Override // java.io.File
    public long lastModified() {
        return 0L;
    }

    @Override // java.io.File
    public long length() {
        InputStream inputStreamOpenInputStream = null;
        try {
            try {
                try {
                    inputStreamOpenInputStream = openInputStream();
                    if (inputStreamOpenInputStream != null) {
                        return inputStreamOpenInputStream.available();
                    }
                } catch (FileNotFoundException e2) {
                    EasyLog.print(e2);
                }
            } catch (IOException e3) {
                EasyLog.print(e3);
            }
            return 0L;
        } finally {
            EasyUtils.closeStream(inputStreamOpenInputStream);
        }
    }

    @Override // java.io.File
    @Nullable
    public String[] list() {
        return null;
    }

    @Override // java.io.File
    @Nullable
    public String[] list(@Nullable FilenameFilter filenameFilter) {
        return null;
    }

    @Override // java.io.File
    @Nullable
    public File[] listFiles() {
        return null;
    }

    @Override // java.io.File
    @Nullable
    public File[] listFiles(@Nullable FileFilter fileFilter) {
        return null;
    }

    @Override // java.io.File
    @Nullable
    public File[] listFiles(@Nullable FilenameFilter filenameFilter) {
        return null;
    }

    @Override // java.io.File
    public boolean mkdir() {
        return true;
    }

    @Override // java.io.File
    public boolean mkdirs() {
        return true;
    }

    @Override // com.hjq.http.model.FileWrapper
    public InputStream openInputStream() throws FileNotFoundException {
        return this.mContentResolver.openInputStream(this.mContentUri);
    }

    @Override // com.hjq.http.model.FileWrapper
    public OutputStream openOutputStream() throws FileNotFoundException {
        return this.mContentResolver.openOutputStream(this.mContentUri);
    }

    @Override // java.io.File
    public boolean renameTo(@NonNull File file) {
        return false;
    }

    public void setContentType(MediaType mediaType) {
        this.mContentType = mediaType;
    }

    public void setFileName(String str) {
        this.mFileName = str;
    }

    @Override // java.io.File
    public boolean setLastModified(long j2) {
        return false;
    }

    public FileContentResolver(ContentResolver contentResolver, Uri uri) {
        this(contentResolver, uri, (String) null);
    }

    public FileContentResolver(Context context, Uri uri, String str) {
        this(context.getContentResolver(), uri, str);
    }

    public FileContentResolver(ContentResolver contentResolver, Uri uri, String str) {
        super(new File(uri.toString()));
        this.mContentResolver = contentResolver;
        this.mContentUri = uri;
        if (!TextUtils.isEmpty(str)) {
            this.mFileName = str;
            this.mContentType = ContentType.guessMimeType(str);
        } else {
            this.mFileName = getName();
            this.mContentType = ContentType.STREAM;
        }
    }
}
