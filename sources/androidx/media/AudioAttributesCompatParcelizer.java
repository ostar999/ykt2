package androidx.media;

import androidx.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;
import java.lang.reflect.InvocationTargetException;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AudioAttributesCompatParcelizer {
    public static AudioAttributesCompat read(VersionedParcel parcel) {
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.mImpl = (AudioAttributesImpl) parcel.readVersionedParcelable(audioAttributesCompat.mImpl, 1);
        return audioAttributesCompat;
    }

    public static void write(AudioAttributesCompat obj, VersionedParcel parcel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        parcel.setSerializationFlags(false, false);
        parcel.writeVersionedParcelable(obj.mImpl, 1);
    }
}
