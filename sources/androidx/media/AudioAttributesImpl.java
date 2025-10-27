package androidx.media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcelable;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public interface AudioAttributesImpl extends VersionedParcelable {

    public interface Builder {
        @NonNull
        AudioAttributesImpl build();

        @NonNull
        Builder setContentType(int contentType);

        @NonNull
        Builder setFlags(int flags);

        @NonNull
        Builder setLegacyStreamType(int streamType);

        @NonNull
        Builder setUsage(int usage);
    }

    @Nullable
    Object getAudioAttributes();

    int getContentType();

    int getFlags();

    int getLegacyStreamType();

    int getRawLegacyStreamType();

    int getUsage();

    int getVolumeControlStream();
}
