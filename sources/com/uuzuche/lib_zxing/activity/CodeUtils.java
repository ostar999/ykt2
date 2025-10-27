package com.uuzuche.lib_zxing.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.uuzuche.lib_zxing.camera.BitmapLuminanceSource;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.uuzuche.lib_zxing.decoding.DecodeFormatManager;
import java.util.Hashtable;
import java.util.Vector;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes6.dex */
public class CodeUtils {
    public static final String LAYOUT_ID = "layout_id";
    public static final int RESULT_FAILED = 2;
    public static final String RESULT_STRING = "result_string";
    public static final int RESULT_SUCCESS = 1;
    public static final String RESULT_TYPE = "result_type";

    public interface AnalyzeCallback {
        void onAnalyzeFailed();

        void onAnalyzeSuccess(Bitmap bitmap, String str);
    }

    public static void analyzeBitmap(String str, AnalyzeCallback analyzeCallback) {
        Result resultDecodeWithState;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i2 = (int) (options.outHeight / 400.0f);
        options.inSampleSize = i2 > 0 ? i2 : 1;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        Hashtable hashtable = new Hashtable(2);
        Vector vector = new Vector();
        if (vector.isEmpty()) {
            vector = new Vector();
            vector.addAll(DecodeFormatManager.ONE_D_FORMATS);
            vector.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            vector.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
        multiFormatReader.setHints(hashtable);
        try {
            resultDecodeWithState = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(bitmapDecodeFile))));
        } catch (Exception e2) {
            e2.printStackTrace();
            resultDecodeWithState = null;
        }
        if (resultDecodeWithState != null) {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeSuccess(bitmapDecodeFile, resultDecodeWithState.getText());
            }
        } else if (analyzeCallback != null) {
            analyzeCallback.onAnalyzeFailed();
        }
    }

    public static Bitmap createImage(String str, int i2, int i3, Bitmap bitmap) {
        int i4;
        int i5;
        int i6;
        int i7;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Bitmap scaleLogo = getScaleLogo(bitmap, i2, i3);
            int i8 = i2 / 2;
            int i9 = i3 / 2;
            int i10 = 0;
            if (scaleLogo != null) {
                int width = scaleLogo.getWidth();
                int height = scaleLogo.getHeight();
                i6 = width;
                i7 = height;
                i4 = (i2 - width) / 2;
                i5 = (i3 - height) / 2;
            } else {
                i4 = i8;
                i5 = i9;
                i6 = 0;
                i7 = 0;
            }
            Hashtable hashtable = new Hashtable();
            hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hashtable.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrixEncode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i2, i3, hashtable);
            int[] iArr = new int[i2 * i3];
            int i11 = 0;
            while (i11 < i3) {
                for (int i12 = i10; i12 < i2; i12++) {
                    int i13 = -16777216;
                    if (i12 >= i4 && i12 < i4 + i6 && i11 >= i5 && i11 < i5 + i7) {
                        int pixel = scaleLogo.getPixel(i12 - i4, i11 - i5);
                        if (pixel != 0) {
                            i13 = pixel;
                        } else if (!bitMatrixEncode.get(i12, i11)) {
                            i13 = -1;
                        }
                        iArr[(i11 * i2) + i12] = i13;
                    } else if (bitMatrixEncode.get(i12, i11)) {
                        iArr[(i11 * i2) + i12] = -16777216;
                    } else {
                        iArr[(i11 * i2) + i12] = -1;
                    }
                }
                i11++;
                i10 = 0;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.setPixels(iArr, 0, i2, 0, 0, i2, i3);
            return bitmapCreateBitmap;
        } catch (WriterException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static Bitmap getScaleLogo(Bitmap bitmap, int i2, int i3) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        float fMin = Math.min(((i2 * 1.0f) / 5.0f) / bitmap.getWidth(), ((i3 * 1.0f) / 5.0f) / bitmap.getHeight());
        matrix.postScale(fMin, fMin);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static void isLightEnable(boolean z2) {
        if (z2) {
            Camera camera = CameraManager.get().getCamera();
            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode("torch");
                camera.setParameters(parameters);
                return;
            }
            return;
        }
        Camera camera2 = CameraManager.get().getCamera();
        if (camera2 != null) {
            Camera.Parameters parameters2 = camera2.getParameters();
            parameters2.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
            camera2.setParameters(parameters2);
        }
    }

    public static void setFragmentArgs(CaptureFragment captureFragment, int i2) {
        if (captureFragment == null || i2 == -1) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID, i2);
        captureFragment.setArguments(bundle);
    }
}
