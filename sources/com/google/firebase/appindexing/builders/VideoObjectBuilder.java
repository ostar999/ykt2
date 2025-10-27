package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Date;

/* loaded from: classes4.dex */
public final class VideoObjectBuilder extends IndexableBuilder<VideoObjectBuilder> {
    public VideoObjectBuilder() {
        super("VideoObject");
    }

    public final VideoObjectBuilder setAuthor(@NonNull PersonBuilder personBuilder) {
        return put(SocializeProtocolConstants.AUTHOR, personBuilder);
    }

    public final VideoObjectBuilder setDuration(long j2) {
        return put("duration", j2);
    }

    public final VideoObjectBuilder setDurationWatched(long j2) {
        return put("durationWatched", j2);
    }

    public final VideoObjectBuilder setLocationCreated(@NonNull PlaceBuilder placeBuilder) {
        return put("locationCreated", placeBuilder);
    }

    public final VideoObjectBuilder setSeriesName(@NonNull String str) {
        return put("seriesName", str);
    }

    public final VideoObjectBuilder setUploadDate(@NonNull Date date) {
        return put("uploadDate", date.getTime());
    }
}
