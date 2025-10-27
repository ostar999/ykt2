package com.google.firebase.appindexing;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.icing.zzhj;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.Thing;
import com.tencent.connect.common.Constants;

/* loaded from: classes4.dex */
public interface Indexable {
    public static final int MAX_BYTE_SIZE = 30000;
    public static final int MAX_INDEXABLES_TO_BE_UPDATED_IN_ONE_CALL = 1000;
    public static final int MAX_NESTING_DEPTH = 5;
    public static final int MAX_NUMBER_OF_FIELDS = 20;
    public static final int MAX_REPEATED_SIZE = 100;
    public static final int MAX_STRING_LENGTH = 20000;
    public static final int MAX_URL_LENGTH = 256;

    public static class Builder extends IndexableBuilder<Builder> {
        public Builder() {
            this("Thing");
        }

        public Builder(@NonNull String str) {
            super(str);
        }
    }

    public interface Metadata {

        public static final class Builder {
            private boolean zzeo = zzhj.zza.zzdx().zzdv();
            private int score = zzhj.zza.zzdx().getScore();
            private String zzep = zzhj.zza.zzdx().zzdw();
            private final Bundle zzay = new Bundle();

            public final Builder setScope(int i2) {
                boolean z2 = i2 > 0 && i2 <= 3;
                StringBuilder sb = new StringBuilder(69);
                sb.append("The scope of this indexable is not valid, scope value is ");
                sb.append(i2);
                sb.append(StrPool.DOT);
                Preconditions.checkArgument(z2, sb.toString());
                IndexableBuilder.zza(this.zzay, Constants.PARAM_SCOPE, i2);
                return this;
            }

            public final Builder setScore(int i2) {
                boolean z2 = i2 >= 0;
                StringBuilder sb = new StringBuilder(53);
                sb.append("Negative score values are invalid. Value: ");
                sb.append(i2);
                Preconditions.checkArgument(z2, sb.toString());
                this.score = i2;
                return this;
            }

            public final Builder setSliceUri(@NonNull Uri uri) {
                Preconditions.checkNotNull(uri);
                IndexableBuilder.zza(this.zzay, "grantSlicePermission", true);
                IndexableBuilder.zza(this.zzay, "sliceUri", uri.toString());
                return this;
            }

            public final Builder setWorksOffline(boolean z2) {
                this.zzeo = z2;
                return this;
            }

            public final Thing.zza zzaa() {
                return new Thing.zza(this.zzeo, this.score, this.zzep, this.zzay);
            }
        }
    }
}
