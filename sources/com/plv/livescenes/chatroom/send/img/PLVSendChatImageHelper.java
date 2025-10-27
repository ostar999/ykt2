package com.plv.livescenes.chatroom.send.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVRfProgressListener;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import com.plv.livescenes.model.PLVUploadTokenVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import com.plv.thirdpart.blankj.utilcode.util.ImageUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import net.polyv.danmaku.danmaku.util.IOUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class PLVSendChatImageHelper {
    private static final int ALLOW_LENGTH = 2097152;
    private static final String FILE = "file";
    private static final String KEY = "key";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String OSS_ACCESS_KEY_ID = "OSSAccessKeyId";
    private static final String POLICY = "policy";
    private static final String SIGNATURE = "signature";
    private static final String SUCCESS_ACTION_STATUS = "success_action_status";
    private static final String TAG = "PolyvSendChatImageHelpe";
    private static final String WEBP_FILE_HEADER_RIFF = "RIFF";
    private static final int WEBP_FILE_HEADER_SIZE = 12;
    private static final String WEBP_FILE_HEADER_WEBP = "WEBP";

    public static int calculateInSampleSize(BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        if (i4 <= i3 && i5 <= i2) {
            return 1;
        }
        int iRound = Math.round(i4 / i3);
        int iRound2 = Math.round(i5 / i2);
        return iRound < iRound2 ? iRound : iRound2;
    }

    public static Bitmap compressImage(String str) throws Exception {
        File file = new File(str);
        if (!file.isFile() || file.length() < 1048576) {
            return null;
        }
        int startQuality = getStartQuality(file.length() / 1048576);
        String absolutePath = createTmpFile(file).getAbsolutePath();
        compressImage(str, absolutePath, startQuality, true);
        return BitmapFactory.decodeFile(absolutePath);
    }

    private static File createTmpFile(File file) {
        if (Build.VERSION.SDK_INT < 29) {
            File file2 = new File(PLVSDCardUtils.createPath(PLVAppUtils.getApp(), "PolyvImg/tmp"), file.getName());
            if (file2.getAbsolutePath().equals(file.getAbsolutePath())) {
                file2 = new File(file2.getParent(), "nc_" + file2.getName());
            }
            PLVSDCardUtils.createNoMediaFile(file2.getParent());
            return file2;
        }
        File file3 = new File(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/PolyvImg/tmp/" + file.getName());
        if (file3.getAbsolutePath().equals(file.getAbsolutePath())) {
            file3 = new File(file3.getParent(), "nc_" + file3.getName());
        }
        PLVSDCardUtils.createNoMediaFile(file3.getParent());
        return file3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public static File getImageFile(File file, String[] strArr, PLVSendLocalImgEvent pLVSendLocalImgEvent, File file2) throws Exception {
        String imageType = ImageUtils.getImageType(file);
        if (imageType != null) {
            strArr[0] = imageType.toLowerCase();
        } else if (isWebPFile(new FileInputStream(file))) {
            strArr[0] = WEBP_FILE_HEADER_WEBP.toLowerCase();
        }
        if (!"jpg".equals(strArr[0]) && !"jpeg".equals(strArr[0]) && !"png".equals(strArr[0]) && !ImgUtil.IMAGE_TYPE_GIF.equals(strArr[0]) && !"webp".equals(strArr[0])) {
            throw new Exception("图片格式不支持");
        }
        if (pLVSendLocalImgEvent.getHeight() <= 500 && (pLVSendLocalImgEvent.getWidth() <= 500 || ImgUtil.IMAGE_TYPE_GIF.equals(strArr[0]))) {
            return file;
        }
        compressImage(file.getAbsolutePath(), file2.getAbsolutePath(), 100, true);
        int[] pictureWh = getPictureWh(file2.getAbsolutePath());
        pLVSendLocalImgEvent.setWidth(pictureWh[0]);
        pLVSendLocalImgEvent.setHeight(pictureWh[1]);
        return file2;
    }

    public static int[] getPictureWh(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Bitmap getSmallBitmap(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, 500, 500);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getStartQuality(long j2) {
        if (j2 > 20) {
            return 20;
        }
        if (j2 > 15) {
            return 30;
        }
        if (j2 > 10) {
            return 40;
        }
        return j2 > 5 ? 55 : 70;
    }

    private static boolean isWebPFile(InputStream inputStream) throws IOException {
        boolean z2 = false;
        try {
            try {
                try {
                    byte[] bArr = new byte[12];
                    if (inputStream.read(bArr, 0, 12) == 12 && WEBP_FILE_HEADER_RIFF.equals(new String(bArr, 0, 4, "US-ASCII"))) {
                        if (WEBP_FILE_HEADER_WEBP.equals(new String(bArr, 8, 4, "US-ASCII"))) {
                            z2 = true;
                        }
                    }
                } catch (UnsupportedEncodingException e2) {
                    PLVCommonLog.e(TAG, "isWebPFile:" + e2.getMessage());
                }
            } catch (IOException e3) {
                PLVCommonLog.e(TAG, "isWebPFile:" + e3.getMessage());
            }
            return z2;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static int readPictureDegree(String str) {
        int i2;
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                i2 = 180;
            } else if (attributeInt == 6) {
                i2 = 90;
            } else {
                if (attributeInt != 8) {
                    return 0;
                }
                i2 = 270;
            }
            return i2;
        } catch (IOException e2) {
            PLVCommonLog.e(TAG, "readPictureDegree:" + e2.getMessage());
            return 0;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i2) {
        if (bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static void sendChatImage(final String str, final PLVSendLocalImgEvent pLVSendLocalImgEvent, final PLVSendChatImageListener pLVSendChatImageListener, final CompositeDisposable... compositeDisposableArr) {
        final String imageFilePath = pLVSendLocalImgEvent.getImageFilePath();
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final String lowerCase = EncryptUtils.encryptMD5ToString("upload_token_live" + str + jCurrentTimeMillis).toLowerCase();
        final File file = new File(imageFilePath);
        final String[] strArr = new String[1];
        final File fileCreateTmpFile = createTmpFile(file);
        Disposable disposableSubscribe = Observable.just(1).map(new Function<Integer, File>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.3
            @Override // io.reactivex.functions.Function
            public File apply(Integer num) throws Exception {
                return PLVSendChatImageHelper.getImageFile(file, strArr, pLVSendLocalImgEvent, fileCreateTmpFile);
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<File>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.1
            @Override // io.reactivex.functions.Consumer
            public void accept(File file2) throws Exception {
                if (file2.length() <= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
                    PLVSendChatImageHelper.uploadImg(file2, imageFilePath, strArr[0], jCurrentTimeMillis, lowerCase, str, pLVSendLocalImgEvent, pLVSendChatImageListener, compositeDisposableArr);
                    return;
                }
                int startQuality = PLVSendChatImageHelper.getStartQuality(file2.length() / 1048576);
                if (ImgUtil.IMAGE_TYPE_GIF.equals(strArr[0])) {
                    PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                    if (pLVSendChatImageListener2 != null) {
                        pLVSendChatImageListener2.onUploadFail(pLVSendLocalImgEvent, new Exception("图片资源过大"));
                        return;
                    }
                    return;
                }
                Disposable disposableUploadImgCompressDisposable = PLVSendChatImageHelper.uploadImgCompressDisposable(file2, startQuality, fileCreateTmpFile, imageFilePath, jCurrentTimeMillis, lowerCase, str, pLVSendLocalImgEvent, pLVSendChatImageListener, compositeDisposableArr);
                CompositeDisposable[] compositeDisposableArr2 = compositeDisposableArr;
                if (compositeDisposableArr2 == null || compositeDisposableArr2.length <= 0) {
                    return;
                }
                compositeDisposableArr2[0].add(disposableUploadImgCompressDisposable);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onUploadFail(pLVSendLocalImgEvent, th);
                }
            }
        });
        if (compositeDisposableArr == null || compositeDisposableArr.length <= 0) {
            return;
        }
        compositeDisposableArr[0].add(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void uploadImg(final File file, String str, String str2, final long j2, String str3, String str4, final PLVSendLocalImgEvent pLVSendLocalImgEvent, final PLVSendChatImageListener pLVSendChatImageListener, final CompositeDisposable... compositeDisposableArr) {
        String strSubstring;
        if (file.getName().contains(StrPool.DOT)) {
            strSubstring = str.substring(str.lastIndexOf(46));
        } else {
            strSubstring = StrPool.DOT + str2;
        }
        final String str5 = strSubstring;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        HashMap map = new HashMap();
        map.put("appId", appId);
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        Disposable disposableSubscribe = PLVApiManager.getPlvLiveStatusApi().getUploadToken2(appId, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVUploadTokenVO>(PLVUploadTokenVO.DataBean.class) { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.9
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVUploadTokenVO pLVUploadTokenVO) {
                return new Pair<>(pLVUploadTokenVO.getDataObj(), Boolean.valueOf(pLVUploadTokenVO.isEncryption()));
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVUploadTokenVO>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.7
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVUploadTokenVO pLVUploadTokenVO) throws Exception {
                String str6 = "chat_img_Android_" + j2;
                String str7 = pLVUploadTokenVO.getData().getDir() + str6 + str5;
                Disposable disposableUploadLiveImageDisposable = PLVSendChatImageHelper.uploadLiveImageDisposable(str6, str7, new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(PLVSendChatImageHelper.KEY, str7).addFormDataPart("policy", pLVUploadTokenVO.getData().getPolicy()).addFormDataPart("OSSAccessKeyId", pLVUploadTokenVO.getData().getAccessid()).addFormDataPart("signature", pLVUploadTokenVO.getData().getSignature()).addFormDataPart(PLVSendChatImageHelper.SUCCESS_ACTION_STATUS, "200").addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file)).build(), pLVSendChatImageListener, pLVSendLocalImgEvent);
                CompositeDisposable[] compositeDisposableArr2 = compositeDisposableArr;
                if (compositeDisposableArr2 == null || compositeDisposableArr2.length <= 0) {
                    return;
                }
                compositeDisposableArr2[0].add(disposableUploadLiveImageDisposable);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onUploadFail(pLVSendLocalImgEvent, th);
                }
            }
        });
        if (compositeDisposableArr == null || compositeDisposableArr.length <= 0) {
            return;
        }
        compositeDisposableArr[0].add(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public static Disposable uploadImgCompressDisposable(final File file, final int i2, final File file2, final String str, final long j2, final String str2, final String str3, final PLVSendLocalImgEvent pLVSendLocalImgEvent, final PLVSendChatImageListener pLVSendChatImageListener, final CompositeDisposable[] compositeDisposableArr) {
        return Observable.just(1).map(new Function<Integer, Integer>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.6
            @Override // io.reactivex.functions.Function
            public Integer apply(Integer num) throws Exception {
                for (int i3 = i2; i3 >= 0; i3 -= 8) {
                    PLVSendChatImageHelper.compressImage(file.getAbsolutePath(), file2.getAbsolutePath(), i3, false);
                    if (file2.length() < PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
                        break;
                    }
                }
                if (file2.length() <= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
                    return num;
                }
                throw new Exception("图片资源太大");
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<Integer>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) throws Exception {
                File file3 = file2;
                PLVSendChatImageHelper.uploadImg(file3, str, ImageUtils.getImageType(file3).toLowerCase(), j2, str2, str3, pLVSendLocalImgEvent, pLVSendChatImageListener, compositeDisposableArr);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onUploadFail(pLVSendLocalImgEvent, th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public static Disposable uploadLiveImageDisposable(final String str, final String str2, MultipartBody multipartBody, final PLVSendChatImageListener pLVSendChatImageListener, final PLVSendLocalImgEvent pLVSendLocalImgEvent) {
        return PLVApiManager.getPlvLiveImagesApi(multipartBody, new PLVRfProgressListener() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.12
            @Override // com.plv.foundationsdk.net.PLVRfProgressListener
            public void onProgress(long j2, long j3) {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onProgress(pLVSendLocalImgEvent, (j2 * 1.0f) / j3);
                }
            }
        }).uploadLiveImages(multipartBody).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.10
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onSuccess(pLVSendLocalImgEvent, "https://liveimages.videocc.net/" + str2, str);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper.11
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVSendChatImageListener pLVSendChatImageListener2 = pLVSendChatImageListener;
                if (pLVSendChatImageListener2 != null) {
                    pLVSendChatImageListener2.onUploadFail(pLVSendLocalImgEvent, th);
                }
            }
        });
    }

    public static String compressImage(String str, String str2, int i2, boolean z2) throws Exception {
        Bitmap smallBitmap = z2 ? getSmallBitmap(str) : BitmapFactory.decodeFile(str);
        File file = new File(str2);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                } else if (!file.delete()) {
                    PLVCommonLog.d(TAG, "delete outputFile fail");
                    throw new Exception("delete  outputFile fail");
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    if (smallBitmap.compress(Bitmap.CompressFormat.JPEG, i2, fileOutputStream2)) {
                        fileOutputStream2.close();
                        return file.getPath();
                    }
                    throw new Exception("compress fail");
                } catch (Exception e2) {
                    throw e2;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                throw e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
