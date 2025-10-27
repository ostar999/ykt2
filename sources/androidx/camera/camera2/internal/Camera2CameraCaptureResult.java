package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.TagBundle;
import androidx.camera.core.impl.utils.ExifData;
import java.nio.BufferUnderflowException;

@RequiresApi(21)
/* loaded from: classes.dex */
public class Camera2CameraCaptureResult implements CameraCaptureResult {
    private static final String TAG = "C2CameraCaptureResult";
    private final CaptureResult mCaptureResult;
    private final TagBundle mTagBundle;

    public Camera2CameraCaptureResult(@NonNull TagBundle tagBundle, @NonNull CaptureResult captureResult) {
        this.mTagBundle = tagBundle;
        this.mCaptureResult = captureResult;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CameraCaptureMetaData.AeState getAeState() {
        Integer num = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_AE_STATE);
        if (num == null) {
            return CameraCaptureMetaData.AeState.UNKNOWN;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return CameraCaptureMetaData.AeState.INACTIVE;
        }
        if (iIntValue != 1) {
            if (iIntValue == 2) {
                return CameraCaptureMetaData.AeState.CONVERGED;
            }
            if (iIntValue == 3) {
                return CameraCaptureMetaData.AeState.LOCKED;
            }
            if (iIntValue == 4) {
                return CameraCaptureMetaData.AeState.FLASH_REQUIRED;
            }
            if (iIntValue != 5) {
                Logger.e(TAG, "Undefined ae state: " + num);
                return CameraCaptureMetaData.AeState.UNKNOWN;
            }
        }
        return CameraCaptureMetaData.AeState.SEARCHING;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CameraCaptureMetaData.AfMode getAfMode() {
        Integer num = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_AF_MODE);
        if (num == null) {
            return CameraCaptureMetaData.AfMode.UNKNOWN;
        }
        int iIntValue = num.intValue();
        if (iIntValue != 0) {
            if (iIntValue == 1 || iIntValue == 2) {
                return CameraCaptureMetaData.AfMode.ON_MANUAL_AUTO;
            }
            if (iIntValue == 3 || iIntValue == 4) {
                return CameraCaptureMetaData.AfMode.ON_CONTINUOUS_AUTO;
            }
            if (iIntValue != 5) {
                Logger.e(TAG, "Undefined af mode: " + num);
                return CameraCaptureMetaData.AfMode.UNKNOWN;
            }
        }
        return CameraCaptureMetaData.AfMode.OFF;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CameraCaptureMetaData.AfState getAfState() {
        Integer num = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
        if (num == null) {
            return CameraCaptureMetaData.AfState.UNKNOWN;
        }
        switch (num.intValue()) {
            case 0:
                break;
            case 1:
            case 3:
                break;
            case 2:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                Logger.e(TAG, "Undefined af state: " + num);
                break;
        }
        return CameraCaptureMetaData.AfState.UNKNOWN;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CameraCaptureMetaData.AwbState getAwbState() {
        Integer num = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_AWB_STATE);
        if (num == null) {
            return CameraCaptureMetaData.AwbState.UNKNOWN;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return CameraCaptureMetaData.AwbState.INACTIVE;
        }
        if (iIntValue == 1) {
            return CameraCaptureMetaData.AwbState.METERING;
        }
        if (iIntValue == 2) {
            return CameraCaptureMetaData.AwbState.CONVERGED;
        }
        if (iIntValue == 3) {
            return CameraCaptureMetaData.AwbState.LOCKED;
        }
        Logger.e(TAG, "Undefined awb state: " + num);
        return CameraCaptureMetaData.AwbState.UNKNOWN;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CaptureResult getCaptureResult() {
        return this.mCaptureResult;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public CameraCaptureMetaData.FlashState getFlashState() {
        Integer num = (Integer) this.mCaptureResult.get(CaptureResult.FLASH_STATE);
        if (num == null) {
            return CameraCaptureMetaData.FlashState.UNKNOWN;
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0 || iIntValue == 1) {
            return CameraCaptureMetaData.FlashState.NONE;
        }
        if (iIntValue == 2) {
            return CameraCaptureMetaData.FlashState.READY;
        }
        if (iIntValue == 3 || iIntValue == 4) {
            return CameraCaptureMetaData.FlashState.FIRED;
        }
        Logger.e(TAG, "Undefined flash state: " + num);
        return CameraCaptureMetaData.FlashState.UNKNOWN;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    @NonNull
    public TagBundle getTagBundle() {
        return this.mTagBundle;
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    public long getTimestamp() {
        Long l2 = (Long) this.mCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
        if (l2 == null) {
            return -1L;
        }
        return l2.longValue();
    }

    @Override // androidx.camera.core.impl.CameraCaptureResult
    public void populateExifData(@NonNull ExifData.Builder builder) {
        Integer num;
        androidx.camera.core.impl.a.b(this, builder);
        Rect rect = (Rect) this.mCaptureResult.get(CaptureResult.SCALER_CROP_REGION);
        if (rect != null) {
            builder.setImageWidth(rect.width()).setImageHeight(rect.height());
        }
        try {
            Integer num2 = (Integer) this.mCaptureResult.get(CaptureResult.JPEG_ORIENTATION);
            if (num2 != null) {
                builder.setOrientationDegrees(num2.intValue());
            }
        } catch (BufferUnderflowException unused) {
            Logger.w(TAG, "Failed to get JPEG orientation.");
        }
        Long l2 = (Long) this.mCaptureResult.get(CaptureResult.SENSOR_EXPOSURE_TIME);
        if (l2 != null) {
            builder.setExposureTimeNanos(l2.longValue());
        }
        Float f2 = (Float) this.mCaptureResult.get(CaptureResult.LENS_APERTURE);
        if (f2 != null) {
            builder.setLensFNumber(f2.floatValue());
        }
        Integer numValueOf = (Integer) this.mCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
        if (numValueOf != null) {
            if (Build.VERSION.SDK_INT >= 24 && (num = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_POST_RAW_SENSITIVITY_BOOST)) != null) {
                numValueOf = Integer.valueOf(numValueOf.intValue() * ((int) (num.intValue() / 100.0f)));
            }
            builder.setIso(numValueOf.intValue());
        }
        Float f3 = (Float) this.mCaptureResult.get(CaptureResult.LENS_FOCAL_LENGTH);
        if (f3 != null) {
            builder.setFocalLength(f3.floatValue());
        }
        Integer num3 = (Integer) this.mCaptureResult.get(CaptureResult.CONTROL_AWB_MODE);
        if (num3 != null) {
            ExifData.WhiteBalanceMode whiteBalanceMode = ExifData.WhiteBalanceMode.AUTO;
            if (num3.intValue() == 0) {
                whiteBalanceMode = ExifData.WhiteBalanceMode.MANUAL;
            }
            builder.setWhiteBalanceMode(whiteBalanceMode);
        }
    }

    public Camera2CameraCaptureResult(@NonNull CaptureResult captureResult) {
        this(TagBundle.emptyBundle(), captureResult);
    }
}
