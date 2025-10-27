package androidx.camera.core.impl;

import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraFilter;
import java.util.Collections;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public class CameraFilters {
    public static final CameraFilter ANY = new CameraFilter() { // from class: androidx.camera.core.impl.e
        @Override // androidx.camera.core.CameraFilter
        public final List filter(List list) {
            return CameraFilters.lambda$static$0(list);
        }

        @Override // androidx.camera.core.CameraFilter
        public /* synthetic */ Identifier getIdentifier() {
            return androidx.camera.core.e.a(this);
        }
    };
    public static final CameraFilter NONE = new CameraFilter() { // from class: androidx.camera.core.impl.f
        @Override // androidx.camera.core.CameraFilter
        public final List filter(List list) {
            return CameraFilters.lambda$static$1(list);
        }

        @Override // androidx.camera.core.CameraFilter
        public /* synthetic */ Identifier getIdentifier() {
            return androidx.camera.core.e.a(this);
        }
    };

    private CameraFilters() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$static$0(List list) {
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List lambda$static$1(List list) {
        return Collections.emptyList();
    }
}
