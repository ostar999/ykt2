package com.plv.beauty.api;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import com.plv.beauty.api.vo.PLVBeautyInitParam;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/* loaded from: classes4.dex */
public class PLVBeautyManager implements IPLVBeautyManager {
    private static final String TAG = "PLVBeautyManager";

    @Nullable
    private final IPLVBeautyManager beautyManagerImpl;

    public static class PLVBeautyManagerHolder {
        private static PLVBeautyManager instance = new PLVBeautyManager();

        private PLVBeautyManagerHolder() {
        }
    }

    public static IPLVBeautyManager getInstance() {
        return PLVBeautyManagerHolder.instance;
    }

    private static IPLVBeautyManager loadBeautyManagerImpl() {
        Iterator it = ServiceLoader.load(IPLVBeautyManager.class).iterator();
        while (it.hasNext()) {
            try {
                IPLVBeautyManager iPLVBeautyManager = (IPLVBeautyManager) it.next();
                if (iPLVBeautyManager != null && !(iPLVBeautyManager instanceof PLVBeautyManager)) {
                    return iPLVBeautyManager;
                }
            } catch (Exception e2) {
                Log.i(TAG, "No beauty implementation found: " + e2.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void addInitCallback(@NonNull WeakReference<IPLVBeautyManager.InitCallback> weakReference) {
        IPLVBeautyManager.InitCallback initCallback;
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.addInitCallback(weakReference);
        } else {
            if (weakReference == null || (initCallback = weakReference.get()) == null) {
                return;
            }
            initCallback.onFinishInit(-1);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void clearBeautyOption() {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.clearBeautyOption();
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void destroy() {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.destroy();
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public List<? extends PLVFilterOption> getSupportFilterOption() {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        return iPLVBeautyManager != null ? iPLVBeautyManager.getSupportFilterOption() : Collections.emptyList();
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void init(@NonNull Context context) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.init(context);
        } else {
            Log.i(TAG, "No beauty implementation found.");
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public boolean isBeautyOptionSupport(@NonNull PLVBeautyOption pLVBeautyOption) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            return iPLVBeautyManager.isBeautyOptionSupport(pLVBeautyOption);
        }
        return false;
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public boolean isBeautySupport() {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            return iPLVBeautyManager.isBeautySupport();
        }
        return false;
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public int processTexture2dTo2d(int i2, int i3, int i4, int i5, long j2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        return iPLVBeautyManager != null ? iPLVBeautyManager.processTexture2dTo2d(i2, i3, i4, i5, j2) : i2;
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public int processTextureOesTo2d(int i2, int i3, int i4, int i5, long j2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        return iPLVBeautyManager != null ? iPLVBeautyManager.processTextureOesTo2d(i2, i3, i4, i5, j2) : i2;
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void processTextureOesToRgba(int i2, ByteBuffer byteBuffer, int i3, int i4, int i5, long j2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.processTextureOesToRgba(i2, byteBuffer, i3, i4, i5, j2);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void release() {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.release();
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void removeBeautyOption(@NonNull PLVBeautyOption pLVBeautyOption) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.removeBeautyOption(pLVBeautyOption);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void setCameraFacing(boolean z2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.setCameraFacing(z2);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void setFilterOption(@Nullable PLVFilterOption pLVFilterOption) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.setFilterOption(pLVFilterOption);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void setInitParam(PLVBeautyInitParam pLVBeautyInitParam) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.setInitParam(pLVBeautyInitParam);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void setup(@Nullable IPLVBeautyManager.SetupCallback setupCallback) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.setup(setupCallback);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void updateBeautyOption(@NonNull PLVBeautyOption pLVBeautyOption) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.updateBeautyOption(pLVBeautyOption);
        }
    }

    private PLVBeautyManager() {
        this.beautyManagerImpl = loadBeautyManagerImpl();
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void processTexture2dTo2d(int i2, int i3, int i4, int i5, int i6, long j2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.processTexture2dTo2d(i2, i3, i4, i5, i6, j2);
        }
    }

    @Override // com.plv.beauty.api.IPLVBeautyManager
    public void processTextureOesTo2d(int i2, int i3, int i4, int i5, int i6, long j2) {
        IPLVBeautyManager iPLVBeautyManager = this.beautyManagerImpl;
        if (iPLVBeautyManager != null) {
            iPLVBeautyManager.processTextureOesTo2d(i2, i3, i4, i5, i6, j2);
        }
    }
}
