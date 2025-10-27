package com.ykb.ebook.util;

import android.content.AppCtxKt;
import com.google.gson.JsonParseException;
import com.ykb.ebook.R;
import com.ykb.ebook.network.ApiException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import retrofit2.HttpException;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"getErrorMsg", "", "error", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NetUtilKt {
    @NotNull
    public static final String getErrorMsg(@NotNull Throwable error) {
        Intrinsics.checkNotNullParameter(error, "error");
        if (error instanceof ApiException) {
            ApiException apiException = (ApiException) error;
            String string = apiException.getCode() == 500 ? AppCtxKt.getAppCtx().getString(R.string.net_error_service) : apiException.getMsg();
            Intrinsics.checkNotNullExpressionValue(string, "{\n            when (erro…e\n            }\n        }");
            return string;
        }
        if (error instanceof UnknownHostException ? true : error instanceof ConnectException) {
            String string2 = AppCtxKt.getAppCtx().getString(R.string.net_error_unavailable);
            Intrinsics.checkNotNullExpressionValue(string2, "appCtx.getString(R.string.net_error_unavailable)");
            return string2;
        }
        if (error instanceof SocketTimeoutException) {
            String string3 = AppCtxKt.getAppCtx().getString(R.string.net_error_time_out);
            Intrinsics.checkNotNullExpressionValue(string3, "appCtx.getString(R.string.net_error_time_out)");
            return string3;
        }
        if (error instanceof HttpException) {
            int iCode = ((HttpException) error).code();
            String string4 = iCode != 307 ? iCode != 401 ? iCode != 500 ? iCode != 403 ? iCode != 404 ? AppCtxKt.getAppCtx().getString(R.string.net_error_un_know) : AppCtxKt.getAppCtx().getString(R.string.net_error_host_not_exist) : AppCtxKt.getAppCtx().getString(R.string.net_error_request_forbidden) : AppCtxKt.getAppCtx().getString(R.string.net_error_service) : AppCtxKt.getAppCtx().getString(R.string.net_error_auth_expire) : AppCtxKt.getAppCtx().getString(R.string.net_error_request_redirected);
            Intrinsics.checkNotNullExpressionValue(string4, "{\n            when (erro…)\n            }\n        }");
            return string4;
        }
        if (error instanceof JsonParseException ? true : error instanceof ParseException ? true : error instanceof JSONException) {
            String string5 = AppCtxKt.getAppCtx().getString(R.string.net_error_data_parse);
            Intrinsics.checkNotNullExpressionValue(string5, "appCtx.getString(R.string.net_error_data_parse)");
            return string5;
        }
        if (error instanceof NullPointerException) {
            String string6 = AppCtxKt.getAppCtx().getString(R.string.net_error_empty_response);
            Intrinsics.checkNotNullExpressionValue(string6, "appCtx.getString(R.strin…net_error_empty_response)");
            return string6;
        }
        String string7 = AppCtxKt.getAppCtx().getString(R.string.net_error_un_know);
        Intrinsics.checkNotNullExpressionValue(string7, "appCtx.getString(R.string.net_error_un_know)");
        return string7;
    }
}
