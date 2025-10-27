package com.easefun.polyv.livecommon.module.utils.document;

import android.app.Activity;
import android.content.Intent;
import android.webkit.MimeTypeMap;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.psychiatrygarden.utils.MimeTypes;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PLVFileChooseUtils {
    public static final int REQUEST_CODE_CHOOSE_UPLOAD_DOCUMENT = 37;
    public static final String[] SUPPORT_FILE_MIME_TYPES = {MimeType.PPT, MimeType.PPTX, MimeType.PDF, MimeType.DOC, MimeType.DOCX, MimeType.XLS, MimeType.XLSX, MimeType.WPS, MimeType.JPG, MimeType.JPEG, MimeType.PNG};

    public static class MimeType {
        public static final String WPS = "application/vnd.ms-works";
        public static final String PPT = getMimeTypeFromExtension("ppt");
        public static final String PPTX = getMimeTypeFromExtension("pptx");
        public static final String PDF = getMimeTypeFromExtension("pdf");
        public static final String DOC = getMimeTypeFromExtension("doc");
        public static final String DOCX = getMimeTypeFromExtension("docx");
        public static final String XLS = getMimeTypeFromExtension("xls");
        public static final String XLSX = getMimeTypeFromExtension("xlsx");
        public static final String JPG = getMimeTypeFromExtension("jpg");
        public static final String JPEG = getMimeTypeFromExtension("jpeg");
        public static final String PNG = getMimeTypeFromExtension("png");

        private static String getMimeTypeFromExtension(String extension) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
    }

    public static void chooseFile(final Activity activity, final int requestCode) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Permission.WRITE_EXTERNAL_STORAGE);
        arrayList.add(Permission.READ_EXTERNAL_STORAGE);
        PLVFastPermission.getInstance().start(activity, arrayList, new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecommon.module.utils.document.PLVFileChooseUtils.1
            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onAllGranted() {
                PLVFileChooseUtils.openDirChooseFile(activity, requestCode, PLVFileChooseUtils.SUPPORT_FILE_MIME_TYPES);
            }

            @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
            public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
            }
        });
    }

    public static boolean isSupportMimeType(String mimeType) {
        for (String str : SUPPORT_FILE_MIME_TYPES) {
            if (str.equals(mimeType)) {
                return true;
            }
        }
        return false;
    }

    public static void openDirChooseFile(Activity activity, int requestCode, String[] mimeTypes) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        if (mimeTypes != null) {
            intent.putExtra("android.intent.extra.MIME_TYPES", mimeTypes);
        }
        intent.setType(MimeTypes.ANY_TYPE);
        intent.addCategory("android.intent.category.OPENABLE");
        activity.startActivityForResult(intent, requestCode);
    }
}
