package com.uuzuche.lib_zxing.decoding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.uuzuche.lib_zxing.camera.PlanarYUVLuminanceSource;
import java.util.Hashtable;

/* loaded from: classes6.dex */
final class DecodeHandler extends Handler {
    private static final String TAG = "DecodeHandler";
    private final CaptureFragment fragment;
    private final MultiFormatReader multiFormatReader;

    public DecodeHandler(CaptureFragment captureFragment, Hashtable<DecodeHintType, Object> hashtable) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        this.multiFormatReader = multiFormatReader;
        multiFormatReader.setHints(hashtable);
        this.fragment = captureFragment;
    }

    private void decode(byte[] bArr, int i2, int i3) {
        Result resultDecodeWithState;
        long jCurrentTimeMillis = System.currentTimeMillis();
        byte[] bArr2 = new byte[bArr.length];
        for (int i4 = 0; i4 < i3; i4++) {
            for (int i5 = 0; i5 < i2; i5++) {
                bArr2[(((i5 * i3) + i3) - i4) - 1] = bArr[(i4 * i2) + i5];
            }
        }
        PlanarYUVLuminanceSource planarYUVLuminanceSourceBuildLuminanceSource = CameraManager.get().buildLuminanceSource(bArr2, i3, i2);
        try {
            resultDecodeWithState = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(planarYUVLuminanceSourceBuildLuminanceSource)));
            this.multiFormatReader.reset();
        } catch (ReaderException unused) {
            this.multiFormatReader.reset();
            resultDecodeWithState = null;
        } catch (Throwable th) {
            this.multiFormatReader.reset();
            throw th;
        }
        if (resultDecodeWithState == null) {
            Message.obtain(this.fragment.getHandler(), R.id.decode_failed).sendToTarget();
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        Log.d(TAG, "Found barcode (" + (jCurrentTimeMillis2 - jCurrentTimeMillis) + " ms):\n" + resultDecodeWithState.toString());
        Message messageObtain = Message.obtain(this.fragment.getHandler(), R.id.decode_succeeded, resultDecodeWithState);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DecodeThread.BARCODE_BITMAP, planarYUVLuminanceSourceBuildLuminanceSource.renderCroppedGreyscaleBitmap());
        messageObtain.setData(bundle);
        messageObtain.sendToTarget();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == R.id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (i2 == R.id.quit) {
            Looper.myLooper().quit();
        }
    }
}
