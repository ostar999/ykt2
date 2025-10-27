package com.catchpig.mvvm.ksp.interfaces;

import com.catchpig.mvvm.entity.ServiceParam;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.vivo.push.PushClientConstants;
import com.yddmi.doctor.network.api.OtherApi;
import com.yddmi.doctor.network.interceptor.DataEncryptInterceptor;
import com.yddmi.doctor.network.interceptor.RequestInterceptor;
import com.yddmi.doctor.network.interceptor.ResponseInterceptor;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005H\u0016R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/ksp/interfaces/ServiceApi_Compiler;", "Lcom/catchpig/mvvm/ksp/interfaces/ServiceApiCompiler;", "()V", "serviceMap", "Ljava/util/HashMap;", "", "Lcom/catchpig/mvvm/entity/ServiceParam;", "Lkotlin/collections/HashMap;", "getResponseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", "", PushClientConstants.TAG_CLASS_NAME, "type", "Ljava/lang/reflect/Type;", "getServiceParam", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ServiceApi_Compiler implements ServiceApiCompiler {

    @NotNull
    private final HashMap<String, ServiceParam> serviceMap;

    public ServiceApi_Compiler() {
        HashMap<String, ServiceParam> map = new HashMap<>();
        this.serviceMap = map;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add(new RequestInterceptor());
        arrayList.add(new DataEncryptInterceptor());
        arrayList2.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.OtherService", new ServiceParam("", 60000L, 180000L, arrayList, arrayList2, false));
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        arrayList3.add(new RequestInterceptor());
        arrayList3.add(new ResponseInterceptor());
        arrayList4.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.WxService", new ServiceParam(OtherApi.wxBaseUrl, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, 6000L, arrayList3, arrayList4, false));
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        arrayList5.add(new RequestInterceptor());
        arrayList5.add(new DataEncryptInterceptor());
        arrayList6.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceDev", new ServiceParam("http://192.168.3.113/api/", 1000L, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, arrayList5, arrayList6, false));
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        arrayList7.add(new RequestInterceptor());
        arrayList7.add(new DataEncryptInterceptor());
        arrayList8.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceFormal", new ServiceParam("https://www.medmeta.com/api/", 1000L, 5000L, arrayList7, arrayList8, false));
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        arrayList9.add(new RequestInterceptor());
        arrayList9.add(new DataEncryptInterceptor());
        arrayList10.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceTest", new ServiceParam("http://59.173.18.239:2336/api/", 1000L, 30000L, arrayList9, arrayList10, false));
        ArrayList arrayList11 = new ArrayList();
        ArrayList arrayList12 = new ArrayList();
        arrayList11.add(new RequestInterceptor());
        arrayList11.add(new DataEncryptInterceptor());
        arrayList12.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceTest126", new ServiceParam("https://192.168.3.126/api/", 1000L, 30000L, arrayList11, arrayList12, false));
        ArrayList arrayList13 = new ArrayList();
        ArrayList arrayList14 = new ArrayList();
        arrayList13.add(new RequestInterceptor());
        arrayList13.add(new DataEncryptInterceptor());
        arrayList14.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceTest192", new ServiceParam("https://192.168.3.123/api/", 1000L, 30000L, arrayList13, arrayList14, false));
        ArrayList arrayList15 = new ArrayList();
        ArrayList arrayList16 = new ArrayList();
        arrayList15.add(new RequestInterceptor());
        arrayList15.add(new DataEncryptInterceptor());
        arrayList16.add(new OkHttpProfilerInterceptor());
        map.put("com.yddmi.doctor.network.api.YddServiceUat", new ServiceParam("https://192.168.3.192/api/", 1000L, 30000L, arrayList15, arrayList16, false));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008e  */
    @Override // com.catchpig.mvvm.ksp.interfaces.ServiceApiCompiler
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public retrofit2.Converter<okhttp3.ResponseBody, java.lang.Object> getResponseBodyConverter(@org.jetbrains.annotations.NotNull java.lang.String r2, @org.jetbrains.annotations.NotNull java.lang.reflect.Type r3) {
        /*
            r1 = this;
            java.lang.String r0 = "className"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            java.lang.String r0 = "type"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            int r0 = r2.hashCode()
            switch(r0) {
                case -1863109865: goto L7f;
                case -403643160: goto L70;
                case -373093750: goto L61;
                case -373093537: goto L52;
                case 886827042: goto L43;
                case 920656843: goto L34;
                case 999514556: goto L24;
                case 999530767: goto L13;
                default: goto L11;
            }
        L11:
            goto L8e
        L13:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceUat"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L1d
            goto L8e
        L1d:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L24:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceDev"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L2e
            goto L8e
        L2e:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L34:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceTest"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L3d
            goto L8e
        L3d:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L43:
            java.lang.String r0 = "com.yddmi.doctor.network.api.OtherService"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L4c
            goto L8e
        L4c:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L52:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceTest192"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L5b
            goto L8e
        L5b:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L61:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceTest126"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L6a
            goto L8e
        L6a:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L70:
            java.lang.String r0 = "com.yddmi.doctor.network.api.YddServiceFormal"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L79
            goto L8e
        L79:
            com.yddmi.doctor.network.ResponseBodyConverter r2 = new com.yddmi.doctor.network.ResponseBodyConverter
            r2.<init>()
            goto L8f
        L7f:
            java.lang.String r0 = "com.yddmi.doctor.network.api.WxService"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L88
            goto L8e
        L88:
            com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter r2 = new com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter
            r2.<init>()
            goto L8f
        L8e:
            r2 = 0
        L8f:
            boolean r0 = r2 instanceof com.catchpig.mvvm.network.converter.BaseResponseBodyConverter
            if (r0 == 0) goto L9a
            r0 = r2
            com.catchpig.mvvm.network.converter.BaseResponseBodyConverter r0 = (com.catchpig.mvvm.network.converter.BaseResponseBodyConverter) r0
            r0.setType(r3)
            goto La4
        L9a:
            boolean r0 = r2 instanceof com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter
            if (r0 == 0) goto La4
            r0 = r2
            com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter r0 = (com.catchpig.mvvm.network.converter.SerializationResponseBodyConverter) r0
            r0.setType(r3)
        La4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.ksp.interfaces.ServiceApi_Compiler.getResponseBodyConverter(java.lang.String, java.lang.reflect.Type):retrofit2.Converter");
    }

    @Override // com.catchpig.mvvm.ksp.interfaces.ServiceApiCompiler
    @NotNull
    public ServiceParam getServiceParam(@NotNull String className) {
        Intrinsics.checkNotNullParameter(className, "className");
        ServiceParam serviceParam = this.serviceMap.get(className);
        Intrinsics.checkNotNull(serviceParam);
        return serviceParam;
    }
}
