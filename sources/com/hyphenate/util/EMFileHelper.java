package com.hyphenate.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import java.io.File;

/* loaded from: classes4.dex */
public class EMFileHelper {
    private Context mContext;
    private IFilePresenter mHelper;

    public static class EMFileHelperInstance {
        private static final EMFileHelper instance = new EMFileHelper();

        private EMFileHelperInstance() {
        }
    }

    public static class FilePresenterImpl implements IFilePresenter {
        @Override // com.hyphenate.util.EMFileHelper.IFilePresenter
        public long getFileLength(Context context, Uri uri) {
            return UriUtils.getFileLength(context, uri);
        }

        @Override // com.hyphenate.util.EMFileHelper.IFilePresenter
        public String getFileMimeType(Context context, Uri uri) {
            return UriUtils.getMimeType(context, uri);
        }

        @Override // com.hyphenate.util.EMFileHelper.IFilePresenter
        public String getFilePath(Context context, Uri uri) {
            return UriUtils.getFilePath(context, uri);
        }

        @Override // com.hyphenate.util.EMFileHelper.IFilePresenter
        public String getFilename(Context context, Uri uri) {
            return UriUtils.getFileNameByUri(context, uri);
        }

        @Override // com.hyphenate.util.EMFileHelper.IFilePresenter
        public boolean isFileExist(Context context, Uri uri) {
            return UriUtils.isFileExistByUri(context, uri);
        }
    }

    public interface IFilePresenter {
        long getFileLength(Context context, Uri uri);

        String getFileMimeType(Context context, Uri uri);

        String getFilePath(Context context, Uri uri);

        String getFilename(Context context, Uri uri);

        boolean isFileExist(Context context, Uri uri);
    }

    private EMFileHelper() {
        this.mContext = EMClient.getInstance().getContext();
        this.mHelper = new FilePresenterImpl();
    }

    public static EMFileHelper getInstance() {
        return EMFileHelperInstance.instance;
    }

    public boolean deletePrivateFile(String str) {
        if (TextUtils.isEmpty(str) || !isFileExist(str)) {
            return false;
        }
        String filePath = getFilePath(Uri.parse(str));
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    public Uri formatInUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        if (VersionUtils.isTargetQ(this.mContext) && UriUtils.uriStartWithContent(uri)) {
            return uri;
        }
        String filePath = getFilePath(uri);
        return !TextUtils.isEmpty(filePath) ? Uri.parse(filePath) : uri;
    }

    public Uri formatInUri(File file) {
        if (file == null) {
            return null;
        }
        return Uri.parse(file.getAbsolutePath());
    }

    public Uri formatInUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return formatInUri(Uri.parse(str));
    }

    public String formatInUriToString(Uri uri) {
        Uri inUri = formatInUri(uri);
        return inUri == null ? "" : inUri.toString();
    }

    public String formatInUriToString(File file) {
        Uri inUri = formatInUri(file);
        return inUri == null ? "" : inUri.toString();
    }

    public String formatInUriToString(String str) {
        return TextUtils.isEmpty(str) ? "" : formatInUriToString(Uri.parse(str));
    }

    public String formatOutLocalUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String filePath = getFilePath(str);
        return !TextUtils.isEmpty(filePath) ? filePath : str;
    }

    public Uri formatOutUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Uri uri = Uri.parse(str);
        if (VersionUtils.isTargetQ(this.mContext) && UriUtils.uriStartWithContent(uri)) {
            return uri;
        }
        String filePath = getFilePath(uri);
        return !TextUtils.isEmpty(filePath) ? Uri.fromFile(new File(filePath)) : uri;
    }

    public long getFileLength(Uri uri) {
        return this.mHelper.getFileLength(this.mContext, uri);
    }

    public long getFileLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        return getFileLength(Uri.parse(str));
    }

    public String getFileMimeType(Uri uri) {
        return this.mHelper.getFileMimeType(this.mContext, uri);
    }

    public String getFilePath(Context context, Uri uri) {
        return this.mHelper.getFilePath(context, uri);
    }

    public String getFilePath(Context context, String str) {
        return TextUtils.isEmpty(str) ? str : getFilePath(context, Uri.parse(str));
    }

    public String getFilePath(Uri uri) {
        return this.mHelper.getFilePath(this.mContext, uri);
    }

    public String getFilePath(String str) {
        return TextUtils.isEmpty(str) ? str : getFilePath(Uri.parse(str));
    }

    public String getFilename(Uri uri) {
        return this.mHelper.getFilename(this.mContext, uri);
    }

    public String getFilename(String str) {
        return TextUtils.isEmpty(str) ? "" : getFilename(Uri.parse(str));
    }

    public boolean isFileExist(Context context, Uri uri) {
        return this.mHelper.isFileExist(context, uri);
    }

    public boolean isFileExist(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return isFileExist(context, Uri.parse(str));
    }

    public boolean isFileExist(Uri uri) {
        return this.mHelper.isFileExist(this.mContext, uri);
    }

    public boolean isFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return isFileExist(Uri.parse(str));
    }

    public void setFileHelper(IFilePresenter iFilePresenter) {
        this.mHelper = iFilePresenter;
    }

    public String uriToString(Uri uri) {
        return uri == null ? "" : uri.toString();
    }
}
