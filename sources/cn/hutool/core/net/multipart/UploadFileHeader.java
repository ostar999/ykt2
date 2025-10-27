package cn.hutool.core.net.multipart;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.tencent.smtt.sdk.TbsVideoCacheTask;

/* loaded from: classes.dex */
public class UploadFileHeader {
    String contentDisposition;
    String contentType;
    String fileName;
    String formFieldName;
    String formFileName;
    boolean isFile;
    String mimeSubtype;
    String mimeType;
    String path;

    public UploadFileHeader(String str) {
        processHeaderString(str);
    }

    private String getDataFieldValue(String str, String str2) {
        String str3 = CharSequenceUtil.format("{}=\"", str2);
        int iIndexOf = str.indexOf(str3);
        if (iIndexOf > 0) {
            int length = iIndexOf + str3.length();
            int iIndexOf2 = str.indexOf(34, length);
            if (length > 0 && iIndexOf2 > 0) {
                return str.substring(length, iIndexOf2);
            }
        }
        return null;
    }

    private void processHeaderString(String str) {
        this.isFile = str.indexOf(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME) > 0;
        this.formFieldName = getDataFieldValue(str, "name");
        if (this.isFile) {
            String dataFieldValue = getDataFieldValue(str, TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME);
            this.formFileName = dataFieldValue;
            if (dataFieldValue == null) {
                return;
            }
            if (dataFieldValue.length() == 0) {
                this.path = "";
                this.fileName = "";
            }
            int iLastIndexOfSeparator = FileUtil.lastIndexOfSeparator(this.formFileName);
            if (iLastIndexOfSeparator == -1) {
                this.path = "";
                this.fileName = this.formFileName;
            } else {
                this.path = this.formFileName.substring(0, iLastIndexOfSeparator);
                this.fileName = this.formFileName.substring(iLastIndexOfSeparator);
            }
            if (this.fileName.length() > 0) {
                String contentType = getContentType(str);
                this.contentType = contentType;
                this.mimeType = getMimeType(contentType);
                this.mimeSubtype = getMimeSubtype(this.contentType);
                this.contentDisposition = getContentDisposition(str);
            }
        }
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFormFieldName() {
        return this.formFieldName;
    }

    public String getFormFileName() {
        return this.formFileName;
    }

    public String getMimeSubtype() {
        return this.mimeSubtype;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public boolean isFile() {
        return this.isFile;
    }

    private String getContentDisposition(String str) {
        return str.substring(str.indexOf(58) + 1, str.indexOf(59));
    }

    private String getContentType(String str) {
        int iIndexOf = str.indexOf("Content-Type:");
        return iIndexOf == -1 ? "" : str.substring(iIndexOf + 13);
    }

    private String getMimeSubtype(String str) {
        int iIndexOf = str.indexOf(47);
        return iIndexOf == -1 ? str : str.substring(iIndexOf + 1);
    }

    private String getMimeType(String str) {
        int iIndexOf = str.indexOf(47);
        return iIndexOf == -1 ? str : str.substring(1, iIndexOf);
    }
}
