package okhttp3;

import cn.hutool.crypto.KeyUtil;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010)\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 C2\u00020\u00012\u00020\u0002:\u0004BCDEB\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0018\u00010\"R\u00020\fH\u0002J\b\u0010#\u001a\u00020 H\u0016J\u0006\u0010$\u001a\u00020 J\r\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\b%J\u0006\u0010&\u001a\u00020 J\b\u0010'\u001a\u00020 H\u0016J\u0017\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b,J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010-\u001a\u00020 J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0015\u001a\u00020\u0011J\u0017\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020)H\u0000¢\u0006\u0002\b1J\u0015\u00102\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b3J\u0006\u0010\u0016\u001a\u00020\u0011J\u0006\u00104\u001a\u00020\u0006J\r\u00105\u001a\u00020 H\u0000¢\u0006\u0002\b6J\u0015\u00107\u001a\u00020 2\u0006\u00108\u001a\u000209H\u0000¢\u0006\u0002\b:J\u001d\u0010;\u001a\u00020 2\u0006\u0010<\u001a\u00020)2\u0006\u0010=\u001a\u00020)H\u0000¢\u0006\u0002\b>J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@J\u0006\u0010\u0017\u001a\u00020\u0011J\u0006\u0010\u001c\u001a\u00020\u0011R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u00048G¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001b¨\u0006F"}, d2 = {"Lokhttp3/Cache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "directory", "Ljava/io/File;", "maxSize", "", "(Ljava/io/File;J)V", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "(Ljava/io/File;JLokhttp3/internal/io/FileSystem;)V", "cache", "Lokhttp3/internal/cache/DiskLruCache;", "getCache$okhttp", "()Lokhttp3/internal/cache/DiskLruCache;", "()Ljava/io/File;", "hitCount", "", "isClosed", "", "()Z", "networkCount", "requestCount", "writeAbortCount", "getWriteAbortCount$okhttp", "()I", "setWriteAbortCount$okhttp", "(I)V", "writeSuccessCount", "getWriteSuccessCount$okhttp", "setWriteSuccessCount$okhttp", "abortQuietly", "", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "close", RequestParameters.SUBRESOURCE_DELETE, "-deprecated_directory", "evictAll", Socket.EVENT_FLUSH, "get", "Lokhttp3/Response;", SocialConstants.TYPE_REQUEST, "Lokhttp3/Request;", "get$okhttp", "initialize", "put", "Lokhttp3/internal/cache/CacheRequest;", "response", "put$okhttp", PLVRemoveMicSiteEvent.EVENT_NAME, "remove$okhttp", DatabaseManager.SIZE, "trackConditionalCacheHit", "trackConditionalCacheHit$okhttp", "trackResponse", "cacheStrategy", "Lokhttp3/internal/cache/CacheStrategy;", "trackResponse$okhttp", PLVUpdateMicSiteEvent.EVENT_NAME, "cached", "network", "update$okhttp", "urls", "", "", "CacheResponseBody", "Companion", "Entry", "RealCacheRequest", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class Cache implements Closeable, Flushable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;

    @NotNull
    private final DiskLruCache cache;
    private int hitCount;
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u0007\u001a\u00020\rH\u0016J\n\u0010\u0005\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0002\u001a\u00060\u0003R\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"Lokhttp3/Cache$CacheResponseBody;", "Lokhttp3/ResponseBody;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "contentType", "", "contentLength", "(Lokhttp3/internal/cache/DiskLruCache$Snapshot;Ljava/lang/String;Ljava/lang/String;)V", "bodySource", "Lokio/BufferedSource;", "getSnapshot", "()Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "", "Lokhttp3/MediaType;", SocialConstants.PARAM_SOURCE, "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class CacheResponseBody extends ResponseBody {

        @NotNull
        private final BufferedSource bodySource;

        @Nullable
        private final String contentLength;

        @Nullable
        private final String contentType;

        @NotNull
        private final DiskLruCache.Snapshot snapshot;

        public CacheResponseBody(@NotNull DiskLruCache.Snapshot snapshot, @Nullable String str, @Nullable String str2) {
            Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            this.snapshot = snapshot;
            this.contentType = str;
            this.contentLength = str2;
            this.bodySource = Okio.buffer(new ForwardingSource(this) { // from class: okhttp3.Cache.CacheResponseBody.1
                final /* synthetic */ CacheResponseBody this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(this.$source);
                    this.this$0 = this;
                }

                @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    this.this$0.getSnapshot().close();
                    super.close();
                }
            });
        }

        @Override // okhttp3.ResponseBody
        /* renamed from: contentLength */
        public long getContentLength() {
            String str = this.contentLength;
            if (str == null) {
                return -1L;
            }
            return Util.toLongOrDefault(str, -1L);
        }

        @Override // okhttp3.ResponseBody
        @Nullable
        /* renamed from: contentType */
        public MediaType get$contentType() {
            String str = this.contentType;
            if (str == null) {
                return null;
            }
            return MediaType.INSTANCE.parse(str);
        }

        @NotNull
        public final DiskLruCache.Snapshot getSnapshot() {
            return this.snapshot;
        }

        @Override // okhttp3.ResponseBody
        @NotNull
        /* renamed from: source, reason: from getter */
        public BufferedSource getSource() {
            return this.bodySource;
        }
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0012\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lokhttp3/Cache$RealCacheRequest;", "Lokhttp3/internal/cache/CacheRequest;", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/Cache;Lokhttp3/internal/cache/DiskLruCache$Editor;)V", TtmlNode.TAG_BODY, "Lokio/Sink;", "cacheOut", "done", "", "getDone", "()Z", "setDone", "(Z)V", "abort", "", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class RealCacheRequest implements CacheRequest {

        @NotNull
        private final Sink body;

        @NotNull
        private final Sink cacheOut;
        private boolean done;

        @NotNull
        private final DiskLruCache.Editor editor;
        final /* synthetic */ Cache this$0;

        public RealCacheRequest(@NotNull final Cache this$0, DiskLruCache.Editor editor) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(editor, "editor");
            this.this$0 = this$0;
            this.editor = editor;
            Sink sinkNewSink = editor.newSink(1);
            this.cacheOut = sinkNewSink;
            this.body = new ForwardingSink(sinkNewSink) { // from class: okhttp3.Cache.RealCacheRequest.1
                @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    Cache cache = this$0;
                    RealCacheRequest realCacheRequest = this;
                    synchronized (cache) {
                        if (realCacheRequest.getDone()) {
                            return;
                        }
                        realCacheRequest.setDone(true);
                        cache.setWriteSuccessCount$okhttp(cache.getWriteSuccessCount() + 1);
                        super.close();
                        this.editor.commit();
                    }
                }
            };
        }

        @Override // okhttp3.internal.cache.CacheRequest
        public void abort() throws IOException {
            Cache cache = this.this$0;
            synchronized (cache) {
                if (getDone()) {
                    return;
                }
                setDone(true);
                cache.setWriteAbortCount$okhttp(cache.getWriteAbortCount() + 1);
                Util.closeQuietly(this.cacheOut);
                try {
                    this.editor.abort();
                } catch (IOException unused) {
                }
            }
        }

        @Override // okhttp3.internal.cache.CacheRequest
        @NotNull
        /* renamed from: body, reason: from getter */
        public Sink getBody() {
            return this.body;
        }

        public final boolean getDone() {
            return this.done;
        }

        public final void setDone(boolean z2) {
            this.done = z2;
        }
    }

    @Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0010)\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\t\u001a\u00020\u0004H\u0096\u0002J\t\u0010\n\u001a\u00020\u0002H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"okhttp3/Cache$urls$1", "", "", "canRemove", "", "delegate", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "nextUrl", "hasNext", "next", PLVRemoveMicSiteEvent.EVENT_NAME, "", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: okhttp3.Cache$urls$1, reason: invalid class name */
    public static final class AnonymousClass1 implements Iterator<String>, KMutableIterator {
        private boolean canRemove;

        @NotNull
        private final Iterator<DiskLruCache.Snapshot> delegate;

        @Nullable
        private String nextUrl;

        public AnonymousClass1() {
            this.delegate = Cache.this.getCache().snapshots();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextUrl != null) {
                return true;
            }
            this.canRemove = false;
            while (this.delegate.hasNext()) {
                try {
                    DiskLruCache.Snapshot next = this.delegate.next();
                    try {
                        continue;
                        this.nextUrl = Okio.buffer(next.getSource(0)).readUtf8LineStrict();
                        CloseableKt.closeFinally(next, null);
                        return true;
                    } finally {
                        try {
                            continue;
                        } catch (Throwable th) {
                        }
                    }
                } catch (IOException unused) {
                }
            }
            return false;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.canRemove) {
                throw new IllegalStateException("remove() before next()".toString());
            }
            this.delegate.remove();
        }

        @Override // java.util.Iterator
        @NotNull
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = this.nextUrl;
            Intrinsics.checkNotNull(str);
            this.nextUrl = null;
            this.canRemove = true;
            return str;
        }
    }

    public Cache(@NotNull File directory, long j2, @NotNull FileSystem fileSystem) {
        Intrinsics.checkNotNullParameter(directory, "directory");
        Intrinsics.checkNotNullParameter(fileSystem, "fileSystem");
        this.cache = new DiskLruCache(fileSystem, directory, VERSION, 2, j2, TaskRunner.INSTANCE);
    }

    private final void abortQuietly(DiskLruCache.Editor editor) {
        if (editor == null) {
            return;
        }
        try {
            editor.abort();
        } catch (IOException unused) {
        }
    }

    @JvmStatic
    @NotNull
    public static final String key(@NotNull HttpUrl httpUrl) {
        return INSTANCE.key(httpUrl);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "directory", imports = {}))
    @JvmName(name = "-deprecated_directory")
    @NotNull
    /* renamed from: -deprecated_directory, reason: not valid java name */
    public final File m2426deprecated_directory() {
        return this.cache.getDirectory();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.cache.close();
    }

    public final void delete() throws IOException {
        this.cache.delete();
    }

    @JvmName(name = "directory")
    @NotNull
    public final File directory() {
        return this.cache.getDirectory();
    }

    public final void evictAll() throws IOException {
        this.cache.evictAll();
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        this.cache.flush();
    }

    @Nullable
    public final Response get$okhttp(@NotNull Request request) throws IOException {
        Intrinsics.checkNotNullParameter(request, "request");
        try {
            DiskLruCache.Snapshot snapshot = this.cache.get(INSTANCE.key(request.url()));
            if (snapshot == null) {
                return null;
            }
            try {
                Entry entry = new Entry(snapshot.getSource(0));
                Response response = entry.response(snapshot);
                if (entry.matches(request, response)) {
                    return response;
                }
                ResponseBody responseBodyBody = response.body();
                if (responseBodyBody != null) {
                    Util.closeQuietly(responseBodyBody);
                }
                return null;
            } catch (IOException unused) {
                Util.closeQuietly(snapshot);
                return null;
            }
        } catch (IOException unused2) {
        }
    }

    @NotNull
    /* renamed from: getCache$okhttp, reason: from getter */
    public final DiskLruCache getCache() {
        return this.cache;
    }

    /* renamed from: getWriteAbortCount$okhttp, reason: from getter */
    public final int getWriteAbortCount() {
        return this.writeAbortCount;
    }

    /* renamed from: getWriteSuccessCount$okhttp, reason: from getter */
    public final int getWriteSuccessCount() {
        return this.writeSuccessCount;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final void initialize() throws IOException {
        this.cache.initialize();
    }

    public final boolean isClosed() {
        return this.cache.isClosed();
    }

    public final long maxSize() {
        return this.cache.getMaxSize();
    }

    public final synchronized int networkCount() {
        return this.networkCount;
    }

    @Nullable
    public final CacheRequest put$okhttp(@NotNull Response response) {
        DiskLruCache.Editor editorEdit$default;
        Intrinsics.checkNotNullParameter(response, "response");
        String strMethod = response.request().method();
        if (HttpMethod.INSTANCE.invalidatesCache(response.request().method())) {
            try {
                remove$okhttp(response.request());
            } catch (IOException unused) {
            }
            return null;
        }
        if (!Intrinsics.areEqual(strMethod, "GET")) {
            return null;
        }
        Companion companion = INSTANCE;
        if (companion.hasVaryAll(response)) {
            return null;
        }
        Entry entry = new Entry(response);
        try {
            editorEdit$default = DiskLruCache.edit$default(this.cache, companion.key(response.request().url()), 0L, 2, null);
            if (editorEdit$default == null) {
                return null;
            }
            try {
                entry.writeTo(editorEdit$default);
                return new RealCacheRequest(this, editorEdit$default);
            } catch (IOException unused2) {
                abortQuietly(editorEdit$default);
                return null;
            }
        } catch (IOException unused3) {
            editorEdit$default = null;
        }
    }

    public final void remove$okhttp(@NotNull Request request) throws IOException {
        Intrinsics.checkNotNullParameter(request, "request");
        this.cache.remove(INSTANCE.key(request.url()));
    }

    public final synchronized int requestCount() {
        return this.requestCount;
    }

    public final void setWriteAbortCount$okhttp(int i2) {
        this.writeAbortCount = i2;
    }

    public final void setWriteSuccessCount$okhttp(int i2) {
        this.writeSuccessCount = i2;
    }

    public final long size() throws IOException {
        return this.cache.size();
    }

    public final synchronized void trackConditionalCacheHit$okhttp() {
        this.hitCount++;
    }

    public final synchronized void trackResponse$okhttp(@NotNull CacheStrategy cacheStrategy) {
        Intrinsics.checkNotNullParameter(cacheStrategy, "cacheStrategy");
        this.requestCount++;
        if (cacheStrategy.getNetworkRequest() != null) {
            this.networkCount++;
        } else if (cacheStrategy.getCacheResponse() != null) {
            this.hitCount++;
        }
    }

    public final void update$okhttp(@NotNull Response cached, @NotNull Response network) {
        DiskLruCache.Editor editorEdit;
        Intrinsics.checkNotNullParameter(cached, "cached");
        Intrinsics.checkNotNullParameter(network, "network");
        Entry entry = new Entry(network);
        ResponseBody responseBodyBody = cached.body();
        if (responseBodyBody == null) {
            throw new NullPointerException("null cannot be cast to non-null type okhttp3.Cache.CacheResponseBody");
        }
        try {
            editorEdit = ((CacheResponseBody) responseBodyBody).getSnapshot().edit();
            if (editorEdit == null) {
                return;
            }
            try {
                entry.writeTo(editorEdit);
                editorEdit.commit();
            } catch (IOException unused) {
                abortQuietly(editorEdit);
            }
        } catch (IOException unused2) {
            editorEdit = null;
        }
    }

    @NotNull
    public final Iterator<String> urls() throws IOException {
        return new AnonymousClass1();
    }

    public final synchronized int writeAbortCount() {
        return this.writeAbortCount;
    }

    public final synchronized int writeSuccessCount() {
        return this.writeSuccessCount;
    }

    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0015\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\n\u0010\u001b\u001a\u00020\u0015*\u00020\u0017J\u0012\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u001d*\u00020\u0011H\u0002J\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lokhttp3/Cache$Companion;", "", "()V", "ENTRY_BODY", "", "ENTRY_COUNT", "ENTRY_METADATA", "VERSION", "key", "", "url", "Lokhttp3/HttpUrl;", "readInt", SocialConstants.PARAM_SOURCE, "Lokio/BufferedSource;", "readInt$okhttp", "varyHeaders", "Lokhttp3/Headers;", "requestHeaders", "responseHeaders", "varyMatches", "", "cachedResponse", "Lokhttp3/Response;", "cachedRequest", "newRequest", "Lokhttp3/Request;", "hasVaryAll", "varyFields", "", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Set<String> varyFields(Headers headers) {
            int size = headers.size();
            TreeSet treeSet = null;
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                if (StringsKt__StringsJVMKt.equals("Vary", headers.name(i2), true)) {
                    String strValue = headers.value(i2);
                    if (treeSet == null) {
                        treeSet = new TreeSet(StringsKt__StringsJVMKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
                    }
                    Iterator it = StringsKt__StringsKt.split$default((CharSequence) strValue, new char[]{','}, false, 0, 6, (Object) null).iterator();
                    while (it.hasNext()) {
                        treeSet.add(StringsKt__StringsKt.trim((CharSequence) it.next()).toString());
                    }
                }
                i2 = i3;
            }
            return treeSet == null ? SetsKt__SetsKt.emptySet() : treeSet;
        }

        public final boolean hasVaryAll(@NotNull Response response) {
            Intrinsics.checkNotNullParameter(response, "<this>");
            return varyFields(response.headers()).contains("*");
        }

        @JvmStatic
        @NotNull
        public final String key(@NotNull HttpUrl url) {
            Intrinsics.checkNotNullParameter(url, "url");
            return ByteString.INSTANCE.encodeUtf8(url.getUrl()).md5().hex();
        }

        public final int readInt$okhttp(@NotNull BufferedSource source) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            try {
                long decimalLong = source.readDecimalLong();
                String utf8LineStrict = source.readUtf8LineStrict();
                if (decimalLong >= 0 && decimalLong <= 2147483647L) {
                    if (!(utf8LineStrict.length() > 0)) {
                        return (int) decimalLong;
                    }
                }
                throw new IOException("expected an int but was \"" + decimalLong + utf8LineStrict + '\"');
            } catch (NumberFormatException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        @NotNull
        public final Headers varyHeaders(@NotNull Response response) {
            Intrinsics.checkNotNullParameter(response, "<this>");
            Response responseNetworkResponse = response.networkResponse();
            Intrinsics.checkNotNull(responseNetworkResponse);
            return varyHeaders(responseNetworkResponse.request().headers(), response.headers());
        }

        public final boolean varyMatches(@NotNull Response cachedResponse, @NotNull Headers cachedRequest, @NotNull Request newRequest) {
            Intrinsics.checkNotNullParameter(cachedResponse, "cachedResponse");
            Intrinsics.checkNotNullParameter(cachedRequest, "cachedRequest");
            Intrinsics.checkNotNullParameter(newRequest, "newRequest");
            Set<String> setVaryFields = varyFields(cachedResponse.headers());
            if ((setVaryFields instanceof Collection) && setVaryFields.isEmpty()) {
                return true;
            }
            for (String str : setVaryFields) {
                if (!Intrinsics.areEqual(cachedRequest.values(str), newRequest.headers(str))) {
                    return false;
                }
            }
            return true;
        }

        private final Headers varyHeaders(Headers requestHeaders, Headers responseHeaders) {
            Set<String> setVaryFields = varyFields(responseHeaders);
            if (setVaryFields.isEmpty()) {
                return Util.EMPTY_HEADERS;
            }
            Headers.Builder builder = new Headers.Builder();
            int size = requestHeaders.size();
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                String strName = requestHeaders.name(i2);
                if (setVaryFields.contains(strName)) {
                    builder.add(strName, requestHeaders.value(i2));
                }
                i2 = i3;
            }
            return builder.build();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Cache(@NotNull File directory, long j2) {
        this(directory, j2, FileSystem.SYSTEM);
        Intrinsics.checkNotNullParameter(directory, "directory");
    }

    @Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 /2\u00020\u0001:\u0001/B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\"\u001a\u00020#H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\n\u0010$\u001a\u00060%R\u00020&J\u001e\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020!0 H\u0002J\u0012\u0010,\u001a\u00020(2\n\u0010-\u001a\u00060.R\u00020&R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lokhttp3/Cache$Entry;", "", "rawSource", "Lokio/Source;", "(Lokio/Source;)V", "response", "Lokhttp3/Response;", "(Lokhttp3/Response;)V", "code", "", Socket.EVENT_HANDSHAKE, "Lokhttp3/Handshake;", "isHttps", "", "()Z", "message", "", "protocol", "Lokhttp3/Protocol;", "receivedResponseMillis", "", "requestMethod", "responseHeaders", "Lokhttp3/Headers;", "sentRequestMillis", "url", "Lokhttp3/HttpUrl;", "varyHeaders", "matches", SocialConstants.TYPE_REQUEST, "Lokhttp3/Request;", "readCertificateList", "", "Ljava/security/cert/Certificate;", SocialConstants.PARAM_SOURCE, "Lokio/BufferedSource;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "writeCertList", "", "sink", "Lokio/BufferedSink;", "certificates", "writeTo", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Companion", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Entry {

        @NotNull
        private static final String RECEIVED_MILLIS;

        @NotNull
        private static final String SENT_MILLIS;
        private final int code;

        @Nullable
        private final Handshake handshake;

        @NotNull
        private final String message;

        @NotNull
        private final Protocol protocol;
        private final long receivedResponseMillis;

        @NotNull
        private final String requestMethod;

        @NotNull
        private final Headers responseHeaders;
        private final long sentRequestMillis;

        @NotNull
        private final HttpUrl url;

        @NotNull
        private final Headers varyHeaders;

        static {
            Platform.Companion companion = Platform.INSTANCE;
            SENT_MILLIS = Intrinsics.stringPlus(companion.get().getPrefix(), "-Sent-Millis");
            RECEIVED_MILLIS = Intrinsics.stringPlus(companion.get().getPrefix(), "-Received-Millis");
        }

        public Entry(@NotNull Source rawSource) throws IOException {
            Intrinsics.checkNotNullParameter(rawSource, "rawSource");
            try {
                BufferedSource bufferedSourceBuffer = Okio.buffer(rawSource);
                String utf8LineStrict = bufferedSourceBuffer.readUtf8LineStrict();
                HttpUrl httpUrl = HttpUrl.INSTANCE.parse(utf8LineStrict);
                if (httpUrl == null) {
                    IOException iOException = new IOException(Intrinsics.stringPlus("Cache corruption for ", utf8LineStrict));
                    Platform.INSTANCE.get().log("cache corruption", 5, iOException);
                    throw iOException;
                }
                this.url = httpUrl;
                this.requestMethod = bufferedSourceBuffer.readUtf8LineStrict();
                Headers.Builder builder = new Headers.Builder();
                int int$okhttp = Cache.INSTANCE.readInt$okhttp(bufferedSourceBuffer);
                int i2 = 0;
                while (i2 < int$okhttp) {
                    i2++;
                    builder.addLenient$okhttp(bufferedSourceBuffer.readUtf8LineStrict());
                }
                this.varyHeaders = builder.build();
                StatusLine statusLine = StatusLine.INSTANCE.parse(bufferedSourceBuffer.readUtf8LineStrict());
                this.protocol = statusLine.protocol;
                this.code = statusLine.code;
                this.message = statusLine.message;
                Headers.Builder builder2 = new Headers.Builder();
                int int$okhttp2 = Cache.INSTANCE.readInt$okhttp(bufferedSourceBuffer);
                int i3 = 0;
                while (i3 < int$okhttp2) {
                    i3++;
                    builder2.addLenient$okhttp(bufferedSourceBuffer.readUtf8LineStrict());
                }
                String str = SENT_MILLIS;
                String str2 = builder2.get(str);
                String str3 = RECEIVED_MILLIS;
                String str4 = builder2.get(str3);
                builder2.removeAll(str);
                builder2.removeAll(str3);
                long j2 = 0;
                this.sentRequestMillis = str2 == null ? 0L : Long.parseLong(str2);
                if (str4 != null) {
                    j2 = Long.parseLong(str4);
                }
                this.receivedResponseMillis = j2;
                this.responseHeaders = builder2.build();
                if (isHttps()) {
                    String utf8LineStrict2 = bufferedSourceBuffer.readUtf8LineStrict();
                    if (utf8LineStrict2.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + utf8LineStrict2 + '\"');
                    }
                    this.handshake = Handshake.INSTANCE.get(!bufferedSourceBuffer.exhausted() ? TlsVersion.INSTANCE.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()) : TlsVersion.SSL_3_0, CipherSuite.INSTANCE.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()), readCertificateList(bufferedSourceBuffer), readCertificateList(bufferedSourceBuffer));
                } else {
                    this.handshake = null;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(rawSource, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(rawSource, th);
                    throw th2;
                }
            }
        }

        private final boolean isHttps() {
            return Intrinsics.areEqual(this.url.scheme(), "https");
        }

        private final List<Certificate> readCertificateList(BufferedSource source) throws IOException, CertificateException {
            int int$okhttp = Cache.INSTANCE.readInt$okhttp(source);
            if (int$okhttp == -1) {
                return CollectionsKt__CollectionsKt.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509);
                ArrayList arrayList = new ArrayList(int$okhttp);
                int i2 = 0;
                while (i2 < int$okhttp) {
                    i2++;
                    String utf8LineStrict = source.readUtf8LineStrict();
                    Buffer buffer = new Buffer();
                    ByteString byteStringDecodeBase64 = ByteString.INSTANCE.decodeBase64(utf8LineStrict);
                    Intrinsics.checkNotNull(byteStringDecodeBase64);
                    buffer.write(byteStringDecodeBase64);
                    arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
                }
                return arrayList;
            } catch (CertificateException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        private final void writeCertList(BufferedSink sink, List<? extends Certificate> certificates) throws IOException, CertificateEncodingException {
            try {
                sink.writeDecimalLong(certificates.size()).writeByte(10);
                Iterator<? extends Certificate> it = certificates.iterator();
                while (it.hasNext()) {
                    byte[] bytes = it.next().getEncoded();
                    ByteString.Companion companion = ByteString.INSTANCE;
                    Intrinsics.checkNotNullExpressionValue(bytes, "bytes");
                    sink.writeUtf8(ByteString.Companion.of$default(companion, bytes, 0, 0, 3, null).base64()).writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public final boolean matches(@NotNull Request request, @NotNull Response response) {
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(response, "response");
            return Intrinsics.areEqual(this.url, request.url()) && Intrinsics.areEqual(this.requestMethod, request.method()) && Cache.INSTANCE.varyMatches(response, this.varyHeaders, request);
        }

        @NotNull
        public final Response response(@NotNull DiskLruCache.Snapshot snapshot) {
            Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            String str = this.responseHeaders.get("Content-Type");
            String str2 = this.responseHeaders.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build()).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
        }

        public final void writeTo(@NotNull DiskLruCache.Editor editor) throws IOException {
            Intrinsics.checkNotNullParameter(editor, "editor");
            BufferedSink bufferedSinkBuffer = Okio.buffer(editor.newSink(0));
            try {
                bufferedSinkBuffer.writeUtf8(this.url.getUrl()).writeByte(10);
                bufferedSinkBuffer.writeUtf8(this.requestMethod).writeByte(10);
                bufferedSinkBuffer.writeDecimalLong(this.varyHeaders.size()).writeByte(10);
                int size = this.varyHeaders.size();
                int i2 = 0;
                while (i2 < size) {
                    int i3 = i2 + 1;
                    bufferedSinkBuffer.writeUtf8(this.varyHeaders.name(i2)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(i2)).writeByte(10);
                    i2 = i3;
                }
                bufferedSinkBuffer.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString()).writeByte(10);
                bufferedSinkBuffer.writeDecimalLong(this.responseHeaders.size() + 2).writeByte(10);
                int size2 = this.responseHeaders.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    bufferedSinkBuffer.writeUtf8(this.responseHeaders.name(i4)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(i4)).writeByte(10);
                }
                bufferedSinkBuffer.writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
                bufferedSinkBuffer.writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
                if (isHttps()) {
                    bufferedSinkBuffer.writeByte(10);
                    Handshake handshake = this.handshake;
                    Intrinsics.checkNotNull(handshake);
                    bufferedSinkBuffer.writeUtf8(handshake.cipherSuite().javaName()).writeByte(10);
                    writeCertList(bufferedSinkBuffer, this.handshake.peerCertificates());
                    writeCertList(bufferedSinkBuffer, this.handshake.localCertificates());
                    bufferedSinkBuffer.writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedSinkBuffer, null);
            } finally {
            }
        }

        public Entry(@NotNull Response response) {
            Intrinsics.checkNotNullParameter(response, "response");
            this.url = response.request().url();
            this.varyHeaders = Cache.INSTANCE.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
            this.sentRequestMillis = response.sentRequestAtMillis();
            this.receivedResponseMillis = response.receivedResponseAtMillis();
        }
    }
}
