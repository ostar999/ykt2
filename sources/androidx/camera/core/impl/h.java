package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final /* synthetic */ class h {
    @NonNull
    public static CameraSelector a(final CameraInfoInternal cameraInfoInternal) {
        return new CameraSelector.Builder().addCameraFilter(new CameraFilter() { // from class: androidx.camera.core.impl.g
            @Override // androidx.camera.core.CameraFilter
            public final List filter(List list) {
                return h.b(cameraInfoInternal, list);
            }

            @Override // androidx.camera.core.CameraFilter
            public /* synthetic */ Identifier getIdentifier() {
                return androidx.camera.core.e.a(this);
            }
        }).build();
    }

    public static /* synthetic */ List b(CameraInfoInternal cameraInfoInternal, List list) {
        String cameraId = cameraInfoInternal.getCameraId();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CameraInfo cameraInfo = (CameraInfo) it.next();
            Preconditions.checkArgument(cameraInfo instanceof CameraInfoInternal);
            if (((CameraInfoInternal) cameraInfo).getCameraId().equals(cameraId)) {
                return Collections.singletonList(cameraInfo);
            }
        }
        throw new IllegalStateException("Unable to find camera with id " + cameraId + " from list of available cameras.");
    }
}
