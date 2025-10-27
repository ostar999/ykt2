package com.google.android.exoplayer2.drm;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.jetty.http.MimeTypes;

/* loaded from: classes3.dex */
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final HttpDataSource.Factory dataSourceFactory;

    @Nullable
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(@Nullable String str, HttpDataSource.Factory factory) {
        this(str, false, factory);
    }

    private static byte[] executePost(HttpDataSource.Factory factory, String str, @Nullable byte[] bArr, Map<String, String> map) throws MediaDrmCallbackException {
        StatsDataSource statsDataSource = new StatsDataSource(factory.createDataSource());
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(str).setHttpRequestHeaders(map).setHttpMethod(2).setHttpBody(bArr).setFlags(1).build();
        int i2 = 0;
        DataSpec dataSpecBuild2 = dataSpecBuild;
        while (true) {
            try {
                DataSourceInputStream dataSourceInputStream = new DataSourceInputStream(statsDataSource, dataSpecBuild2);
                try {
                    return Util.toByteArray(dataSourceInputStream);
                } catch (HttpDataSource.InvalidResponseCodeException e2) {
                    String redirectUrl = getRedirectUrl(e2, i2);
                    if (redirectUrl == null) {
                        throw e2;
                    }
                    i2++;
                    dataSpecBuild2 = dataSpecBuild2.buildUpon().setUri(redirectUrl).build();
                } finally {
                    Util.closeQuietly(dataSourceInputStream);
                }
            } catch (Exception e3) {
                throw new MediaDrmCallbackException(dataSpecBuild, (Uri) Assertions.checkNotNull(statsDataSource.getLastOpenedUri()), statsDataSource.getResponseHeaders(), statsDataSource.getBytesRead(), e3);
            }
        }
    }

    @Nullable
    private static String getRedirectUrl(HttpDataSource.InvalidResponseCodeException invalidResponseCodeException, int i2) {
        Map<String, List<String>> map;
        List<String> list;
        int i3 = invalidResponseCodeException.responseCode;
        if (!((i3 == 307 || i3 == 308) && i2 < 5) || (map = invalidResponseCodeException.headerFields) == null || (list = map.get("Location")) == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(str);
        }
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest keyRequest) throws MediaDrmCallbackException {
        String licenseServerUrl = keyRequest.getLicenseServerUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.defaultLicenseUrl;
        }
        if (TextUtils.isEmpty(licenseServerUrl)) {
            throw new MediaDrmCallbackException(new DataSpec.Builder().setUri(Uri.EMPTY).build(), Uri.EMPTY, ImmutableMap.of(), 0L, new IllegalStateException("No license URL"));
        }
        HashMap map = new HashMap();
        UUID uuid2 = C.PLAYREADY_UUID;
        map.put("Content-Type", uuid2.equals(uuid) ? MimeTypes.TEXT_XML : C.CLEARKEY_UUID.equals(uuid) ? com.psychiatrygarden.utils.MimeTypes.APP_JSON : "application/octet-stream");
        if (uuid2.equals(uuid)) {
            map.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            map.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, licenseServerUrl, keyRequest.getData(), map);
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest provisionRequest) throws MediaDrmCallbackException {
        String defaultUrl = provisionRequest.getDefaultUrl();
        String strFromUtf8Bytes = Util.fromUtf8Bytes(provisionRequest.getData());
        StringBuilder sb = new StringBuilder(String.valueOf(defaultUrl).length() + 15 + String.valueOf(strFromUtf8Bytes).length());
        sb.append(defaultUrl);
        sb.append("&signedRequest=");
        sb.append(strFromUtf8Bytes);
        return executePost(this.dataSourceFactory, sb.toString(), null, Collections.emptyMap());
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(str, str2);
        }
    }

    public HttpMediaDrmCallback(@Nullable String str, boolean z2, HttpDataSource.Factory factory) {
        Assertions.checkArgument((z2 && TextUtils.isEmpty(str)) ? false : true);
        this.dataSourceFactory = factory;
        this.defaultLicenseUrl = str;
        this.forceDefaultLicenseUrl = z2;
        this.keyRequestProperties = new HashMap();
    }
}
