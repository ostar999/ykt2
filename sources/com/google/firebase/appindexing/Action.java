package com.google.firebase.appindexing;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.zzt;
import java.util.Arrays;

/* loaded from: classes4.dex */
public interface Action {

    public static class Builder {
        public static final String ACTIVATE_ACTION = "ActivateAction";
        public static final String ADD_ACTION = "AddAction";
        public static final String BOOKMARK_ACTION = "BookmarkAction";
        public static final String COMMENT_ACTION = "CommentAction";
        public static final String LIKE_ACTION = "LikeAction";
        public static final String LISTEN_ACTION = "ListenAction";
        public static final String SEND_ACTION = "SendAction";
        public static final String SHARE_ACTION = "ShareAction";
        public static final String STATUS_TYPE_ACTIVE = "http://schema.org/ActiveActionStatus";
        public static final String STATUS_TYPE_COMPLETED = "http://schema.org/CompletedActionStatus";
        public static final String STATUS_TYPE_FAILED = "http://schema.org/FailedActionStatus";
        public static final String VIEW_ACTION = "ViewAction";
        public static final String WATCH_ACTION = "WatchAction";
        private final String zzar;
        private final Bundle zzay = new Bundle();
        private String zzeg;
        private String zzeh;
        private String zzei;
        private com.google.firebase.appindexing.internal.zzc zzej;
        private String zzek;

        public Builder(@NonNull String str) {
            this.zzar = str;
        }

        public Action build() {
            Preconditions.checkNotNull(this.zzeg, "setObject is required before calling build().");
            Preconditions.checkNotNull(this.zzeh, "setObject is required before calling build().");
            String str = this.zzar;
            String str2 = this.zzeg;
            String str3 = this.zzeh;
            String str4 = this.zzei;
            com.google.firebase.appindexing.internal.zzc zzcVarZzz = this.zzej;
            if (zzcVarZzz == null) {
                zzcVarZzz = new Metadata.Builder().zzz();
            }
            return new com.google.firebase.appindexing.internal.zza(str, str2, str3, str4, zzcVarZzz, this.zzek, this.zzay);
        }

        public final String getName() {
            String str = this.zzeg;
            if (str == null) {
                return null;
            }
            return new String(str);
        }

        public final String getUrl() {
            String str = this.zzeh;
            if (str == null) {
                return null;
            }
            return new String(str);
        }

        public Builder put(@NonNull String str, @NonNull String... strArr) {
            IndexableBuilder.zza(this.zzay, str, strArr);
            return this;
        }

        public Builder setActionStatus(@NonNull String str) {
            Preconditions.checkNotNull(str);
            this.zzek = str;
            return this;
        }

        public Builder setMetadata(@NonNull Metadata.Builder builder) {
            Preconditions.checkNotNull(builder);
            this.zzej = builder.zzz();
            return this;
        }

        public final Builder setName(@NonNull String str) {
            Preconditions.checkNotNull(str);
            this.zzeg = str;
            return put("name", str);
        }

        public Builder setObject(@NonNull String str, @NonNull String str2) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(str2);
            this.zzeg = str;
            this.zzeh = str2;
            return this;
        }

        public Builder setResult(@NonNull Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
            return put("result", indexableArr);
        }

        public final Builder setUrl(@NonNull String str) {
            Preconditions.checkNotNull(str);
            this.zzeh = str;
            return put("url", str);
        }

        public final String zzy() {
            return new String(this.zzek);
        }

        public Builder put(@NonNull String str, @NonNull Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
            IndexableBuilder.zza(this.zzay, str, indexableArr);
            return this;
        }

        public Builder put(@NonNull String str, @NonNull boolean... zArr) {
            IndexableBuilder.zza(this.zzay, str, zArr);
            return this;
        }

        public Builder put(@NonNull String str, @NonNull long... jArr) {
            IndexableBuilder.zza(this.zzay, str, jArr);
            return this;
        }

        public Builder put(@NonNull String str, @NonNull double... dArr) {
            Bundle bundle = this.zzay;
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(dArr);
            if (dArr.length > 0) {
                if (dArr.length >= 100) {
                    zzt.zzn("Input Array of elements is too big, cutting off.");
                    dArr = Arrays.copyOf(dArr, 100);
                }
                bundle.putDoubleArray(str, dArr);
            } else {
                zzt.zzn("Double array is empty and is ignored by put method.");
            }
            return this;
        }

        public Builder setObject(@NonNull String str, @NonNull String str2, @NonNull String str3) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(str2);
            Preconditions.checkNotNull(str3);
            this.zzeg = str;
            this.zzeh = str2;
            this.zzei = str3;
            return this;
        }
    }

    public interface Metadata {

        public static class Builder {
            private boolean zzel = true;
            private boolean zzem = false;

            public Builder setUpload(boolean z2) {
                this.zzel = z2;
                return this;
            }

            public final com.google.firebase.appindexing.internal.zzc zzz() {
                return new com.google.firebase.appindexing.internal.zzc(this.zzel, null, null, null, false);
            }
        }
    }
}
