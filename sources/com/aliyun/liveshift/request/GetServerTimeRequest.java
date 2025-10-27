package com.aliyun.liveshift.request;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.utils.b;
import com.aliyun.utils.d;
import com.aliyun.utils.e;
import com.just.agentweb.DefaultWebClient;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class GetServerTimeRequest extends b {
    private static final String TAG = "GetServerTimeRequest";
    private d httpClientHelper;
    private WeakReference<Context> mContextWeak;
    private String mHost;

    public GetServerTimeRequest(Context context, String str, b.d<Long> dVar) {
        super(context, dVar);
        this.httpClientHelper = null;
        this.mHost = str;
        this.mContextWeak = new WeakReference<>(context);
    }

    @Override // com.aliyun.utils.b
    public void runInBackground() throws Throwable {
        int value;
        String str;
        String str2 = DefaultWebClient.HTTPS_SCHEME + this.mHost + "/openapi/getutc?lhs_start=1";
        if (this.wantStop) {
            sendFailResult(-1, "", "");
            return;
        }
        try {
            d dVar = new d(str2);
            this.httpClientHelper = dVar;
            String strB = dVar.b();
            if (TextUtils.isEmpty(strB)) {
                sendFailResult(ErrorCode.ERROR_SERVER_LIVESHIFT_REQUEST_ERROR.getValue(), "request fail", "");
                return;
            }
            if (strB.split("=").length == 2) {
                long jB = e.b(new JSONObject(strB), "GT");
                if (jB != 0) {
                    sendSuccessResult(Long.valueOf(jB), "");
                    return;
                }
            }
            sendFailResult(ErrorCode.ERROR_SERVER_LIVESHIFT_REQUEST_ERROR.getValue(), "request fail", "");
        } catch (JSONException unused) {
            value = ErrorCode.ERROR_SERVER_LIVESHIFT_DATA_PARSER_ERROR.getValue();
            str = "response not json";
            sendFailResult(value, str, "");
        } catch (Exception unused2) {
            value = ErrorCode.ERROR_SERVER_LIVESHIFT_UNKNOWN.getValue();
            str = "unknow error";
            sendFailResult(value, str, "");
        }
    }

    @Override // com.aliyun.utils.b
    public void stopInner() {
        d dVar = this.httpClientHelper;
        if (dVar != null) {
            dVar.e();
        }
    }
}
