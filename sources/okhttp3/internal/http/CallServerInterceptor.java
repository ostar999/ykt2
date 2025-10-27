package okhttp3.internal.http;

import kotlin.Metadata;
import okhttp3.Interceptor;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "shouldIgnoreAndWaitForRealResponse", "code", "", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean z2) {
        this.forWebSocket = z2;
    }

    private final boolean shouldIgnoreAndWaitForRealResponse(int code) {
        if (code == 100) {
            return true;
        }
        return 102 <= code && code < 200;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e0 A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012a A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0153 A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x015e A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0167 A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0171 A[Catch: IOException -> 0x01a0, TryCatch #3 {IOException -> 0x01a0, blocks: (B:39:0x00a9, B:41:0x00b2, B:42:0x00b6, B:44:0x00e0, B:46:0x00e9, B:47:0x00ec, B:48:0x0110, B:52:0x011b, B:54:0x013a, B:56:0x0148, B:63:0x015e, B:69:0x0171, B:73:0x0194, B:74:0x019e, B:72:0x018c, B:66:0x0167, B:58:0x0153, B:53:0x012a), top: B:88:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00a9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v13, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v21, types: [okhttp3.Response$Builder] */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26 */
    @Override // okhttp3.Interceptor
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull okhttp3.Interceptor.Chain r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.CallServerInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }
}
