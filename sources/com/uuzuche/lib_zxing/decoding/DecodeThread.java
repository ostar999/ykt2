package com.uuzuche.lib_zxing.decoding;

import android.os.Handler;
import android.os.Looper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes6.dex */
final class DecodeThread extends Thread {
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    private final CaptureFragment fragment;
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Hashtable<DecodeHintType, Object> hints;

    public DecodeThread(CaptureFragment captureFragment, Vector<BarcodeFormat> vector, String str, ResultPointCallback resultPointCallback) {
        this.fragment = captureFragment;
        Hashtable<DecodeHintType, Object> hashtable = new Hashtable<>(3);
        this.hints = hashtable;
        if (vector == null || vector.isEmpty()) {
            vector = new Vector<>();
            vector.addAll(DecodeFormatManager.ONE_D_FORMATS);
            vector.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            vector.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
        if (str != null) {
            hashtable.put(DecodeHintType.CHARACTER_SET, str);
        }
        hashtable.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
    }

    public Handler getHandler() throws InterruptedException {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException unused) {
        }
        return this.handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.fragment, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
