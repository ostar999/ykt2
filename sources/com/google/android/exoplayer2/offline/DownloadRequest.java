package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>() { // from class: com.google.android.exoplayer2.offline.DownloadRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest createFromParcel(Parcel parcel) {
            return new DownloadRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DownloadRequest[] newArray(int i2) {
            return new DownloadRequest[i2];
        }
    };

    @Nullable
    public final String customCacheKey;
    public final byte[] data;
    public final String id;

    @Nullable
    public final byte[] keySetId;

    @Nullable
    public final String mimeType;
    public final List<StreamKey> streamKeys;
    public final Uri uri;

    public static class Builder {

        @Nullable
        private String customCacheKey;

        @Nullable
        private byte[] data;
        private final String id;

        @Nullable
        private byte[] keySetId;

        @Nullable
        private String mimeType;

        @Nullable
        private List<StreamKey> streamKeys;
        private final Uri uri;

        public Builder(String str, Uri uri) {
            this.id = str;
            this.uri = uri;
        }

        public DownloadRequest build() {
            String str = this.id;
            Uri uri = this.uri;
            String str2 = this.mimeType;
            List listOf = this.streamKeys;
            if (listOf == null) {
                listOf = ImmutableList.of();
            }
            return new DownloadRequest(str, uri, str2, listOf, this.keySetId, this.customCacheKey, this.data);
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.customCacheKey = str;
            return this;
        }

        public Builder setData(@Nullable byte[] bArr) {
            this.data = bArr;
            return this;
        }

        public Builder setKeySetId(@Nullable byte[] bArr) {
            this.keySetId = bArr;
            return this;
        }

        public Builder setMimeType(@Nullable String str) {
            this.mimeType = str;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            this.streamKeys = list;
            return this;
        }
    }

    public static class UnsupportedRequestException extends IOException {
    }

    public DownloadRequest copyWithId(String str) {
        return new DownloadRequest(str, this.uri, this.mimeType, this.streamKeys, this.keySetId, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithKeySetId(@Nullable byte[] bArr) {
        return new DownloadRequest(this.id, this.uri, this.mimeType, this.streamKeys, bArr, this.customCacheKey, this.data);
    }

    public DownloadRequest copyWithMergedRequest(DownloadRequest downloadRequest) {
        List listEmptyList;
        Assertions.checkArgument(this.id.equals(downloadRequest.id));
        if (this.streamKeys.isEmpty() || downloadRequest.streamKeys.isEmpty()) {
            listEmptyList = Collections.emptyList();
        } else {
            listEmptyList = new ArrayList(this.streamKeys);
            for (int i2 = 0; i2 < downloadRequest.streamKeys.size(); i2++) {
                StreamKey streamKey = downloadRequest.streamKeys.get(i2);
                if (!listEmptyList.contains(streamKey)) {
                    listEmptyList.add(streamKey);
                }
            }
        }
        return new DownloadRequest(this.id, downloadRequest.uri, downloadRequest.mimeType, listEmptyList, downloadRequest.keySetId, downloadRequest.customCacheKey, downloadRequest.data);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest downloadRequest = (DownloadRequest) obj;
        return this.id.equals(downloadRequest.id) && this.uri.equals(downloadRequest.uri) && Util.areEqual(this.mimeType, downloadRequest.mimeType) && this.streamKeys.equals(downloadRequest.streamKeys) && Arrays.equals(this.keySetId, downloadRequest.keySetId) && Util.areEqual(this.customCacheKey, downloadRequest.customCacheKey) && Arrays.equals(this.data, downloadRequest.data);
    }

    public final int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31 * 31) + this.uri.hashCode()) * 31;
        String str = this.mimeType;
        int iHashCode2 = (((((iHashCode + (str != null ? str.hashCode() : 0)) * 31) + this.streamKeys.hashCode()) * 31) + Arrays.hashCode(this.keySetId)) * 31;
        String str2 = this.customCacheKey;
        return ((iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31) + Arrays.hashCode(this.data);
    }

    public MediaItem toMediaItem() {
        return new MediaItem.Builder().setMediaId(this.id).setUri(this.uri).setCustomCacheKey(this.customCacheKey).setMimeType(this.mimeType).setStreamKeys(this.streamKeys).build();
    }

    public String toString() {
        String str = this.mimeType;
        String str2 = this.id;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.id);
        parcel.writeString(this.uri.toString());
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.streamKeys.size());
        for (int i3 = 0; i3 < this.streamKeys.size(); i3++) {
            parcel.writeParcelable(this.streamKeys.get(i3), 0);
        }
        parcel.writeByteArray(this.keySetId);
        parcel.writeString(this.customCacheKey);
        parcel.writeByteArray(this.data);
    }

    private DownloadRequest(String str, Uri uri, @Nullable String str2, List<StreamKey> list, @Nullable byte[] bArr, @Nullable String str3, @Nullable byte[] bArr2) {
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(uri, str2);
        if (iInferContentTypeForUriAndMimeType == 0 || iInferContentTypeForUriAndMimeType == 2 || iInferContentTypeForUriAndMimeType == 1) {
            boolean z2 = str3 == null;
            StringBuilder sb = new StringBuilder(49);
            sb.append("customCacheKey must be null for type: ");
            sb.append(iInferContentTypeForUriAndMimeType);
            Assertions.checkArgument(z2, sb.toString());
        }
        this.id = str;
        this.uri = uri;
        this.mimeType = str2;
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList);
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        this.customCacheKey = str3;
        this.data = bArr2 != null ? Arrays.copyOf(bArr2, bArr2.length) : Util.EMPTY_BYTE_ARRAY;
    }

    public DownloadRequest(Parcel parcel) {
        this.id = (String) Util.castNonNull(parcel.readString());
        this.uri = Uri.parse((String) Util.castNonNull(parcel.readString()));
        this.mimeType = parcel.readString();
        int i2 = parcel.readInt();
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add((StreamKey) parcel.readParcelable(StreamKey.class.getClassLoader()));
        }
        this.streamKeys = Collections.unmodifiableList(arrayList);
        this.keySetId = parcel.createByteArray();
        this.customCacheKey = parcel.readString();
        this.data = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
