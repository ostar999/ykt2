package com.google.android.gms.appindexing;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;

@VisibleForTesting
@Deprecated
/* loaded from: classes3.dex */
public class Thing {
    final Bundle zzay;

    public Thing(Bundle bundle) {
        this.zzay = bundle;
    }

    public final Bundle zze() {
        return this.zzay;
    }

    @VisibleForTesting
    @Deprecated
    public static class Builder {
        final Bundle zzax = new Bundle();

        public Thing build() {
            return new Thing(this.zzax);
        }

        public Builder put(String str, String str2) {
            Preconditions.checkNotNull(str);
            if (str2 != null) {
                this.zzax.putString(str, str2);
            }
            return this;
        }

        public Builder setDescription(String str) {
            put("description", str);
            return this;
        }

        public Builder setId(String str) {
            if (str != null) {
                put("id", str);
            }
            return this;
        }

        public Builder setName(String str) {
            Preconditions.checkNotNull(str);
            put("name", str);
            return this;
        }

        public Builder setType(String str) {
            put("type", str);
            return this;
        }

        public Builder setUrl(Uri uri) {
            Preconditions.checkNotNull(uri);
            put("url", uri.toString());
            return this;
        }

        public Builder put(String str, String[] strArr) {
            Preconditions.checkNotNull(str);
            if (strArr != null) {
                this.zzax.putStringArray(str, strArr);
            }
            return this;
        }

        public Builder put(String str, Thing thing) {
            Preconditions.checkNotNull(str);
            if (thing != null) {
                this.zzax.putParcelable(str, thing.zzay);
            }
            return this;
        }

        public Builder put(String str, Thing[] thingArr) {
            Preconditions.checkNotNull(str);
            if (thingArr != null) {
                ArrayList arrayList = new ArrayList();
                for (Thing thing : thingArr) {
                    if (thing != null) {
                        arrayList.add(thing.zzay);
                    }
                }
                this.zzax.putParcelableArray(str, (Parcelable[]) arrayList.toArray(new Bundle[arrayList.size()]));
            }
            return this;
        }

        public Builder put(String str, boolean z2) {
            Preconditions.checkNotNull(str);
            this.zzax.putBoolean(str, z2);
            return this;
        }
    }
}
