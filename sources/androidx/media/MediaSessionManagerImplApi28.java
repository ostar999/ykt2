package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import androidx.annotation.RequiresApi;
import androidx.media.MediaSessionManager;
import androidx.media.MediaSessionManagerImplBase;

@RequiresApi(28)
/* loaded from: classes.dex */
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {
    android.media.session.MediaSessionManager mObject;

    public MediaSessionManagerImplApi28(Context context) {
        super(context);
        this.mObject = (android.media.session.MediaSessionManager) context.getSystemService("media_session");
    }

    @Override // androidx.media.MediaSessionManagerImplApi21, androidx.media.MediaSessionManagerImplBase, androidx.media.MediaSessionManager.MediaSessionManagerImpl
    public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl userInfo) {
        return super.isTrustedForMediaControl(userInfo);
    }

    @RequiresApi(28)
    public static final class RemoteUserInfoImplApi28 extends MediaSessionManagerImplBase.RemoteUserInfoImplBase {
        final MediaSessionManager.RemoteUserInfo mObject;

        public RemoteUserInfoImplApi28(String packageName, int pid, int uid) {
            super(packageName, pid, uid);
            this.mObject = new MediaSessionManager.RemoteUserInfo(packageName, pid, uid);
        }

        public static String getPackageName(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            return remoteUserInfo.getPackageName();
        }

        public RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            super(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
            this.mObject = remoteUserInfo;
        }
    }
}
