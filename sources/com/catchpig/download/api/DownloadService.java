package com.catchpig.download.api;

import io.reactivex.rxjava3.core.Flowable;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H'\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, d2 = {"Lcom/catchpig/download/api/DownloadService;", "", "coroutinesDownload", "Lokhttp3/ResponseBody;", "url", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rxJavaDownload", "Lio/reactivex/rxjava3/core/Flowable;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface DownloadService {
    @Streaming
    @GET
    @Nullable
    Object coroutinesDownload(@Url @NotNull String str, @NotNull Continuation<? super ResponseBody> continuation);

    @Streaming
    @GET
    @NotNull
    Flowable<ResponseBody> rxJavaDownload(@Url @NotNull String url);
}
