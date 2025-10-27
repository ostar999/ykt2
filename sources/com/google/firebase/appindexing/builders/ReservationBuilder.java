package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.heytap.mcssdk.constant.b;
import java.util.Date;

/* loaded from: classes4.dex */
public final class ReservationBuilder extends IndexableBuilder<ReservationBuilder> {
    public ReservationBuilder() {
        super("Reservation");
    }

    public final ReservationBuilder setPartySize(@NonNull long j2) {
        return put("partySize", j2);
    }

    public final ReservationBuilder setReservationFor(@NonNull LocalBusinessBuilder localBusinessBuilder) {
        return put("reservationFor", localBusinessBuilder);
    }

    public final ReservationBuilder setStartDate(@NonNull Date date) {
        Preconditions.checkNotNull(date);
        return put(b.f7194s, date.getTime());
    }
}
