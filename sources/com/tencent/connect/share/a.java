package com.tencent.connect.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.d;
import com.tencent.open.utils.f;
import com.tencent.open.utils.k;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class a {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean b(String str, int i2, int i3) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(str, options);
        } catch (OutOfMemoryError e2) {
            SLog.e("openSDK_LOG.AsynScaleCompressImage", "isBitMapNeedToCompress exception:", e2);
        }
        int i4 = options.outWidth;
        int i5 = options.outHeight;
        if (options.mCancel || i4 == -1 || i5 == -1) {
            return false;
        }
        int i6 = i4 > i5 ? i4 : i5;
        if (i4 >= i5) {
            i4 = i5;
        }
        SLog.d("openSDK_LOG.AsynScaleCompressImage", "longSide=" + i6 + "shortSide=" + i4);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return i6 > i3 || i4 > i2;
    }

    public static final void a(final Context context, final String str, final d dVar) {
        SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage()");
        if (TextUtils.isEmpty(str)) {
            dVar.a(1, (String) null);
        } else if (!k.a()) {
            dVar.a(2, (String) null);
        } else {
            final Handler handler = new Handler(context.getMainLooper()) { // from class: com.tencent.connect.share.a.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i2 = message.what;
                    if (i2 == 101) {
                        dVar.a(0, (ArrayList<String>) message.obj);
                    } else if (i2 != 102) {
                        super.handleMessage(message);
                    } else {
                        dVar.a(message.arg1, (String) null);
                    }
                }
            };
            new Thread(new Runnable() { // from class: com.tencent.connect.share.a.2
                @Override // java.lang.Runnable
                public void run() {
                    String str2;
                    String string;
                    try {
                        Bitmap bitmapA = a.a(str, R2.attr.buttomTextColorPress);
                        if (bitmapA != null) {
                            File fileA = f.a("Images");
                            String str3 = null;
                            if (fileA != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(fileA.getAbsolutePath());
                                String str4 = File.separator;
                                sb.append(str4);
                                sb.append(Constants.QQ_SHARE_TEMP_DIR);
                                sb.append(str4);
                                string = sb.toString();
                                str2 = null;
                            } else {
                                File fileD = f.d();
                                if (fileD == null) {
                                    SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() getCacheDir = null,return error");
                                    Message messageObtainMessage = handler.obtainMessage();
                                    messageObtainMessage.arg1 = 102;
                                    handler.sendMessage(messageObtainMessage);
                                    return;
                                }
                                String absolutePath = fileD.getAbsolutePath();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(absolutePath);
                                String str5 = File.separator;
                                sb2.append(str5);
                                sb2.append(Constants.QQ_SHARE_TEMP_DIR);
                                sb2.append(str5);
                                String string2 = sb2.toString();
                                SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() use cache dir=" + string2);
                                str2 = absolutePath;
                                string = string2;
                            }
                            String str6 = "share2qq_temp" + k.g(str) + ".jpg";
                            String str7 = str;
                            if (a.b(str7, R2.attr.buttomTextColorPress, R2.attr.buttomTextColorPress)) {
                                SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() out of bound,compress!");
                                String strA = a.a(bitmapA, string, str6);
                                if (!TextUtils.isEmpty(strA)) {
                                    str7 = strA;
                                }
                            } else {
                                SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() not out of bound,not compress!");
                            }
                            boolean zN = k.n(str7);
                            SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() check file isAppSpecificDir=" + zN);
                            ArrayList arrayList = new ArrayList(2);
                            if (zN) {
                                str3 = str7;
                            } else if (TextUtils.isEmpty(str2)) {
                                String str8 = string + str6;
                                boolean zA = k.a(context, str7, str8);
                                SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() sd permission not denied. copy to app sepcific:" + str8 + ",isSuccess=" + zA);
                                if (zA) {
                                    str3 = str8;
                                }
                            }
                            arrayList.add(str7);
                            arrayList.add(str3);
                            if (arrayList.size() >= 2 && (arrayList.get(0) != null || arrayList.get(1) != null)) {
                                SLog.i("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() return success ! destFilePath=[" + ((String) arrayList.get(0)) + "," + ((String) arrayList.get(1)) + StrPool.BRACKET_END);
                                Message messageObtainMessage2 = handler.obtainMessage(101);
                                messageObtainMessage2.obj = arrayList;
                                handler.sendMessage(messageObtainMessage2);
                                return;
                            }
                        }
                    } catch (Exception e2) {
                        SLog.e("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage runnable exception e:", e2);
                    }
                    SLog.d("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage() return failed!");
                    Message messageObtainMessage3 = handler.obtainMessage(102);
                    messageObtainMessage3.arg1 = 3;
                    handler.sendMessage(messageObtainMessage3);
                }
            }).start();
        }
    }

    private static Bitmap a(Bitmap bitmap, int i2) {
        Matrix matrix = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= height) {
            width = height;
        }
        float f2 = i2 / width;
        matrix.postScale(f2, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static int b(BitmapFactory.Options options, int i2, int i3) {
        int iMin;
        double d3 = options.outWidth;
        double d4 = options.outHeight;
        int iCeil = i3 == -1 ? 1 : (int) Math.ceil(Math.sqrt((d3 * d4) / i3));
        if (i2 == -1) {
            iMin = 128;
        } else {
            double d5 = i2;
            iMin = (int) Math.min(Math.floor(d3 / d5), Math.floor(d4 / d5));
        }
        if (iMin < iCeil) {
            return iCeil;
        }
        if (i3 == -1 && i2 == -1) {
            return 1;
        }
        return i2 == -1 ? iCeil : iMin;
    }

    public static final String a(Bitmap bitmap, String str, String str2) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append(str2);
        String string = stringBuffer.toString();
        File file2 = new File(string);
        if (file2.exists()) {
            file2.delete();
        }
        if (bitmap == null) {
            return null;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();
            return string;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.Bitmap a(java.lang.String r6, int r7) {
        /*
            java.lang.String r0 = "openSDK_LOG.AsynScaleCompressImage"
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            r2 = 0
            if (r1 == 0) goto La
            return r2
        La:
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options
            r1.<init>()
            r3 = 1
            r1.inJustDecodeBounds = r3
            android.graphics.BitmapFactory.decodeFile(r6, r1)     // Catch: java.lang.OutOfMemoryError -> L16
            goto L1d
        L16:
            r3 = move-exception
            java.lang.String r4 = "scaleBitmap exception1:"
            com.tencent.open.log.SLog.e(r0, r4, r3)
        L1d:
            int r3 = r1.outWidth
            int r4 = r1.outHeight
            boolean r5 = r1.mCancel
            if (r5 != 0) goto L6d
            r5 = -1
            if (r3 == r5) goto L6d
            if (r4 != r5) goto L2b
            goto L6d
        L2b:
            if (r3 <= r4) goto L2e
            goto L2f
        L2e:
            r3 = r4
        L2f:
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.RGB_565
            r1.inPreferredConfig = r4
            if (r3 <= r7) goto L3d
            int r3 = r7 * r7
            int r3 = a(r1, r5, r3)
            r1.inSampleSize = r3
        L3d:
            r3 = 0
            r1.inJustDecodeBounds = r3
            android.graphics.Bitmap r6 = android.graphics.BitmapFactory.decodeFile(r6, r1)     // Catch: java.lang.OutOfMemoryError -> L45 java.lang.Exception -> L4d
            goto L55
        L45:
            r6 = move-exception
            java.lang.String r3 = "scaleBitmap OutOfMemoryError:"
            com.tencent.open.log.SLog.e(r0, r3, r6)
            goto L54
        L4d:
            r6 = move-exception
            java.lang.String r3 = "scaleBitmap exception2:"
            com.tencent.open.log.SLog.e(r0, r3, r6)
        L54:
            r6 = r2
        L55:
            if (r6 != 0) goto L5e
            java.lang.String r6 = "scaleBitmap return null"
            com.tencent.open.log.SLog.e(r0, r6)
            return r2
        L5e:
            int r0 = r1.outWidth
            int r1 = r1.outHeight
            if (r0 <= r1) goto L65
            goto L66
        L65:
            r0 = r1
        L66:
            if (r0 <= r7) goto L6c
            android.graphics.Bitmap r6 = a(r6, r7)
        L6c:
            return r6
        L6d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.a.a(java.lang.String, int):android.graphics.Bitmap");
    }

    public static final int a(BitmapFactory.Options options, int i2, int i3) {
        int iB = b(options, i2, i3);
        if (iB > 8) {
            return 8 * ((iB + 7) / 8);
        }
        int i4 = 1;
        while (i4 < iB) {
            i4 <<= 1;
        }
        return i4;
    }
}
