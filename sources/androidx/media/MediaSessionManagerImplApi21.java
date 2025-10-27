package androidx.media;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.media.MediaSessionManager;

@RequiresApi(21)
/* loaded from: classes.dex */
class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {
    public MediaSessionManagerImplApi21(Context context) {
        super(context);
        this.mContext = context;
    }

    private boolean hasMediaControlPermission(@NonNull MediaSessionManager.RemoteUserInfoImpl userInfo) {
        return getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", userInfo.getPid(), userInfo.getUid()) == 0;
    }

    @Override // androidx.media.MediaSessionManagerImplBase, androidx.media.MediaSessionManager.MediaSessionManagerImpl
    public boolean isTrustedForMediaControl(@NonNull MediaSessionManager.RemoteUserInfoImpl userInfo) {
        return hasMediaControlPermission(userInfo) || super.isTrustedForMediaControl(userInfo);
    }
}
