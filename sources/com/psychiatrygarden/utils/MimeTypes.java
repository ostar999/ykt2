package com.psychiatrygarden.utils;

/* loaded from: classes6.dex */
public class MimeTypes {
    public static final String ANY_TYPE = "*/*";
    public static final String APP_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String APP_EXCEL = "application/vnd.ms-excel";
    public static final String APP_JSON = "application/json";
    public static final String APP_MSWORD = "application/msword";
    public static final String APP_OCTET_STREAM = "application/octet-stream";
    public static final String APP_PDF = "application/pdf";
    public static final String APP_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String APP_ZIP = "application/zip";
    public static final String AUDIO_AAC = "audio/aac";
    public static final String AUDIO_ALL = "audio/*";
    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_OGG = "audio/ogg";
    public static final String AUDIO_WAV = "audio/wav";
    public static final String IMAGE_ALL = "image/*";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_OR_VIDEO = "image/* video/*";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_WEBP = "image/webp";
    public static final String MULTIPART_FORM = "multipart/form-data";
    public static final String TEXT_CSS = "text/css";
    public static final String TEXT_CSV = "text/csv";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_VCARD = "text/vcard";
    public static final String VIDEO_3GPP = "video/3gpp";
    public static final String VIDEO_ALL = "video/*";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_OGG = "video/ogg";
    public static final String VIDEO_WEBM = "video/webm";

    public static String fromExtension(String extension) {
        String lowerCase = extension.toLowerCase();
        lowerCase.hashCode();
        switch (lowerCase) {
            case "doc":
                return APP_MSWORD;
            case "gif":
                return IMAGE_GIF;
            case "htm":
            case "html":
                return "text/html";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "mp3":
                return "audio/mpeg";
            case "mp4":
                return "video/mp4";
            case "pdf":
                return APP_PDF;
            case "png":
                return "image/png";
            case "txt":
                return "text/plain";
            case "xls":
                return APP_EXCEL;
            case "zip":
                return APP_ZIP;
            case "docx":
                return APP_DOCX;
            case "json":
                return APP_JSON;
            case "webp":
                return IMAGE_WEBP;
            case "xlsx":
                return APP_XLSX;
            default:
                return "application/octet-stream";
        }
    }
}
