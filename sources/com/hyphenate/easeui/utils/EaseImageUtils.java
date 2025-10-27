package com.hyphenate.easeui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.hyphenate.util.PathUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseImageUtils extends ImageUtils {
    public static int[] getImageMaxSize(Context context) {
        float[] screenInfo = EaseCommonUtils.getScreenInfo(context);
        int[] iArr = new int[2];
        if (screenInfo != null) {
            float f2 = screenInfo[0];
            iArr[0] = (int) (f2 / 3.0f);
            iArr[1] = (int) (f2 / 2.0f);
        }
        return iArr;
    }

    public static String getImagePath(String str) {
        String str2 = PathUtil.getInstance().getImagePath() + "/" + str.substring(str.lastIndexOf("/") + 1, str.length());
        EMLog.d("msg", "image path:" + str2);
        return str2;
    }

    public static String getImagePathByFileName(String str) {
        String str2 = PathUtil.getInstance().getImagePath() + "/" + str;
        EMLog.d("msg", "image path:" + str2);
        return str2;
    }

    public static ViewGroup.LayoutParams getImageShowSize(Context context, EMMessage eMMessage) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        EMMessageBody body = eMMessage.getBody();
        if (!(body instanceof EMImageMessageBody)) {
            return layoutParams;
        }
        EMImageMessageBody eMImageMessageBody = (EMImageMessageBody) body;
        int width = eMImageMessageBody.getWidth();
        int height = eMImageMessageBody.getHeight();
        Uri localUri = eMImageMessageBody.getLocalUri();
        BitmapFactory.Options bitmapOptions = null;
        if (!EaseFileUtils.isFileExistByUri(context, localUri)) {
            localUri = eMImageMessageBody.thumbnailLocalUri();
            if (!EaseFileUtils.isFileExistByUri(context, localUri)) {
                localUri = null;
            }
        }
        if (width == 0 || height == 0) {
            try {
                bitmapOptions = ImageUtils.getBitmapOptions(context, localUri);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (bitmapOptions != null) {
                width = bitmapOptions.outWidth;
                height = bitmapOptions.outHeight;
            }
        }
        int[] imageMaxSize = getImageMaxSize(context);
        int i2 = imageMaxSize[0];
        int i3 = imageMaxSize[1];
        float f2 = i2;
        float f3 = i3;
        float f4 = (f2 * 1.0f) / f3;
        float f5 = width * 1.0f;
        if (height == 0) {
            height = 1;
        }
        float f6 = f5 / height;
        float f7 = f6 != 0.0f ? f6 : 1.0f;
        if (i3 == 0 && i2 == 0) {
            return layoutParams;
        }
        float f8 = f4 / f7;
        if (f8 < 0.1f) {
            layoutParams.width = i2;
            layoutParams.height = i2 / 2;
        } else if (f8 > 4.0f) {
            layoutParams.width = i3 / 2;
            layoutParams.height = i3;
        } else if (f7 < f4) {
            layoutParams.height = i3;
            layoutParams.width = (int) (f3 * f7);
        } else {
            layoutParams.width = i2;
            layoutParams.height = (int) (f2 / f7);
        }
        return layoutParams;
    }

    public static String getThumbnailImagePath(String str) {
        String str2 = PathUtil.getInstance().getImagePath() + "/th" + str.substring(str.lastIndexOf("/") + 1, str.length());
        EMLog.d("msg", "thum image path:" + str2);
        return str2;
    }

    public static String getThumbnailImagePathByName(String str) {
        String str2 = PathUtil.getInstance().getImagePath() + "/th" + str;
        EMLog.d("msg", "thum image dgdfg path:" + str2);
        return str2;
    }

    public static Uri imageToJpeg(Context context, Uri uri, File file) throws IOException {
        String filePath = EaseFileUtils.getFilePath(context, uri);
        Bitmap bitmapByUri = (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) ? ImageUtils.getBitmapByUri(context, uri, null) : BitmapFactory.decodeFile(filePath, null);
        if (bitmapByUri == null || file == null) {
            return uri;
        }
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmapByUri.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return Uri.fromFile(file);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0074 A[PHI: r2
      0x0074: PHI (r2v1 android.net.Uri) = (r2v0 android.net.Uri), (r2v6 android.net.Uri) binds: [B:7:0x0045, B:9:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.ViewGroup.LayoutParams showImage(android.content.Context r13, android.widget.ImageView r14, com.hyphenate.chat.EMMessage r15) {
        /*
            com.hyphenate.chat.EMMessageBody r15 = r15.getBody()
            boolean r0 = r15 instanceof com.hyphenate.chat.EMImageMessageBody
            if (r0 != 0) goto Ld
            android.view.ViewGroup$LayoutParams r13 = r14.getLayoutParams()
            return r13
        Ld:
            com.hyphenate.chat.EMImageMessageBody r15 = (com.hyphenate.chat.EMImageMessageBody) r15
            int r0 = r15.getWidth()
            int r1 = r15.getHeight()
            android.net.Uri r2 = r15.getLocalUri()
            com.hyphenate.easeui.utils.EaseFileUtils.takePersistableUriPermission(r13, r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "current show small view big file: uri:"
            r3.append(r4)
            r3.append(r2)
            java.lang.String r4 = " exist: "
            r3.append(r4)
            boolean r5 = com.hyphenate.easeui.utils.EaseFileUtils.isFileExistByUri(r13, r2)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            java.lang.String r5 = "tag"
            com.hyphenate.util.EMLog.e(r5, r3)
            boolean r3 = com.hyphenate.easeui.utils.EaseFileUtils.isFileExistByUri(r13, r2)
            r6 = 0
            if (r3 != 0) goto L74
            android.net.Uri r2 = r15.thumbnailLocalUri()
            com.hyphenate.easeui.utils.EaseFileUtils.takePersistableUriPermission(r13, r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r7 = "current show small view thumbnail file: uri:"
            r3.append(r7)
            r3.append(r2)
            r3.append(r4)
            boolean r4 = com.hyphenate.easeui.utils.EaseFileUtils.isFileExistByUri(r13, r2)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.hyphenate.util.EMLog.e(r5, r3)
            boolean r3 = com.hyphenate.easeui.utils.EaseFileUtils.isFileExistByUri(r13, r2)
            if (r3 != 0) goto L74
            r9 = r6
            goto L75
        L74:
            r9 = r2
        L75:
            if (r0 == 0) goto L79
            if (r1 != 0) goto L89
        L79:
            android.graphics.BitmapFactory$Options r2 = com.hyphenate.util.ImageUtils.getBitmapOptions(r13, r9)     // Catch: java.lang.Exception -> L7e
            goto L83
        L7e:
            r2 = move-exception
            r2.printStackTrace()
            r2 = r6
        L83:
            if (r2 == 0) goto L89
            int r0 = r2.outWidth
            int r1 = r2.outHeight
        L89:
            r11 = r0
            r12 = r1
            com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.getInstance()
            com.hyphenate.chat.EMOptions r0 = r0.getOptions()
            boolean r0 = r0.getAutodownloadThumbnail()
            if (r0 == 0) goto La7
            java.lang.String r6 = r15.getThumbnailUrl()
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 == 0) goto La7
            java.lang.String r6 = r15.getRemoteUrl()
        La7:
            r10 = r6
            r7 = r13
            r8 = r14
            android.view.ViewGroup$LayoutParams r13 = showImage(r7, r8, r9, r10, r11, r12)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.easeui.utils.EaseImageUtils.showImage(android.content.Context, android.widget.ImageView, com.hyphenate.chat.EMMessage):android.view.ViewGroup$LayoutParams");
    }

    public static ViewGroup.LayoutParams showVideoThumb(Context context, ImageView imageView, EMMessage eMMessage) {
        EMMessageBody body = eMMessage.getBody();
        if (!(body instanceof EMVideoMessageBody)) {
            return imageView.getLayoutParams();
        }
        EMVideoMessageBody eMVideoMessageBody = (EMVideoMessageBody) body;
        int thumbnailWidth = eMVideoMessageBody.getThumbnailWidth();
        int thumbnailHeight = eMVideoMessageBody.getThumbnailHeight();
        Uri localThumbUri = eMVideoMessageBody.getLocalThumbUri();
        EaseFileUtils.takePersistableUriPermission(context, localThumbUri);
        return showImage(context, imageView, !EaseFileUtils.isFileExistByUri(context, localThumbUri) ? null : localThumbUri, eMVideoMessageBody.getThumbnailUrl(), thumbnailWidth, thumbnailHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ViewGroup.LayoutParams showImage(Context context, ImageView imageView, Uri uri, String str, int i2, int i3) {
        int[] imageMaxSize = getImageMaxSize(context);
        int i4 = imageMaxSize[0];
        int i5 = imageMaxSize[1];
        float f2 = i4;
        float f3 = i5;
        float f4 = (f2 * 1.0f) / f3;
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        float f5 = i2 * 1.0f;
        if (i3 == 0) {
            i3 = 1;
        }
        float f6 = f5 / i3;
        float f7 = f6 != 0.0f ? f6 : 1.0f;
        if (i5 == 0 && i4 == 0) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing() || activity.isDestroyed()) {
                    return imageView.getLayoutParams();
                }
            }
            RequestManager requestManagerWith = Glide.with(context);
            if (uri == null) {
                uri = str;
            }
            requestManagerWith.load((Object) uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            return imageView.getLayoutParams();
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        float f8 = f4 / f7;
        if (f8 < 0.1f) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layoutParams.width = i4;
            layoutParams.height = i4 / 2;
        } else if (f8 > 4.0f) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layoutParams.width = i5 / 2;
            layoutParams.height = i5;
        } else if (f7 < f4) {
            layoutParams.height = i5;
            layoutParams.width = (int) (f3 * f7);
        } else {
            layoutParams.width = i4;
            layoutParams.height = (int) (f2 / f7);
        }
        if (context instanceof Activity) {
            Activity activity2 = (Activity) context;
            if (activity2.isFinishing() || activity2.isDestroyed()) {
                return layoutParams;
            }
        }
        RequestManager requestManagerWith2 = Glide.with(context);
        if (uri == null) {
            uri = str;
        }
        requestManagerWith2.load((Object) uri).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.ease_default_image)).diskCacheStrategy(DiskCacheStrategy.ALL).override(layoutParams.width, layoutParams.height).into(imageView);
        return layoutParams;
    }
}
