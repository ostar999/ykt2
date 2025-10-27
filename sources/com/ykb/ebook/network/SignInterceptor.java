package com.ykb.ebook.network;

import android.content.AppCtxKt;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.tencent.open.SocialOperation;
import com.yikaobang.yixue.BuildConfig;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.util.MD5Utils;
import com.ykb.ebook.util.Sha1Utils;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/network/SignInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSignInterceptor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SignInterceptor.kt\ncom/ykb/ebook/network/SignInterceptor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,233:1\n1855#2,2:234\n1045#2:236\n1045#2:239\n1855#2,2:242\n1045#2:244\n1045#2:247\n215#3,2:237\n215#3,2:240\n215#3,2:245\n215#3,2:248\n*S KotlinDebug\n*F\n+ 1 SignInterceptor.kt\ncom/ykb/ebook/network/SignInterceptor\n*L\n37#1:234,2\n42#1:236\n91#1:239\n120#1:242,2\n146#1:244\n185#1:247\n45#1:237,2\n94#1:240,2\n149#1:245,2\n188#1:248,2\n*E\n"})
/* loaded from: classes7.dex */
public final class SignInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request request = chain.request();
        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "user_id", "");
        Intrinsics.checkNotNull(prefString);
        String prefString2 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "app_id", "");
        Intrinsics.checkNotNull(prefString2);
        String strMethod = request.method();
        String str = "app-type";
        if (Intrinsics.areEqual(strMethod, "GET")) {
            HttpUrl.Builder builderNewBuilder = request.url().newBuilder();
            builderNewBuilder.addEncodedQueryParameter("user_id", prefString);
            builderNewBuilder.addEncodedQueryParameter("app_id", prefString2);
            builderNewBuilder.addEncodedQueryParameter("token", ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "token", null, 2, null));
            builderNewBuilder.addEncodedQueryParameter("secret", ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "secret", null, 2, null));
            HttpUrl httpUrlBuild = builderNewBuilder.build();
            String strEncodedQuery = httpUrlBuild.encodedQuery();
            if (!(strEncodedQuery == null || strEncodedQuery.length() == 0)) {
                List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) strEncodedQuery, new String[]{"&"}, false, 0, 6, (Object) null);
                HashMap map = new HashMap();
                Iterator it = listSplit$default.iterator();
                while (it.hasNext()) {
                    List listSplit$default2 = StringsKt__StringsKt.split$default((CharSequence) it.next(), new String[]{"="}, false, 0, 6, (Object) null);
                    map.put(listSplit$default2.get(0), listSplit$default2.get(1));
                }
                String str2 = "";
                for (Map.Entry entry : MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(map), new Comparator() { // from class: com.ykb.ebook.network.SignInterceptor$intercept$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t2, T t3) {
                        return ComparisonsKt__ComparisonsKt.compareValues((String) ((Pair) t2).component1(), (String) ((Pair) t3).component1());
                    }
                })).entrySet()) {
                    str2 = str2 + ((String) entry.getKey()) + '=' + ((String) entry.getValue());
                }
                String str3 = (System.currentTimeMillis() / 1000) + "";
                String str4 = prefString2 + str3;
                Request.Builder builderAddHeader = request.newBuilder().addHeader("client-type", "android").addHeader("channel", "10000");
                CommonConfig commonConfig = CommonConfig.INSTANCE;
                String app_version = commonConfig.getApp_version();
                Intrinsics.checkNotNull(app_version);
                Request.Builder builderAddHeader2 = builderAddHeader.addHeader("app-version", app_version).addHeader("app-type", commonConfig.getYI_KAO_BANG() ? BuildConfig.FLAVOR : "hkb").addHeader("Content-type", "application/x-www-form-urlencoded").addHeader("user_id", prefString).addHeader("app_id", prefString2);
                String uuid = commonConfig.getUuid();
                Intrinsics.checkNotNull(uuid);
                Request.Builder builderAddHeader3 = builderAddHeader2.addHeader(AliyunLogKey.KEY_UUID, uuid);
                String user_Agent = commonConfig.getUser_Agent();
                Intrinsics.checkNotNull(user_Agent);
                Request.Builder builderAddHeader4 = builderAddHeader3.addHeader("User-Agent", user_Agent).addHeader("timestamp", String.valueOf(str3));
                StringBuilder sb = new StringBuilder();
                sb.append(MD5Utils.INSTANCE.MD5Encode(str2 + str4));
                sb.append("bfde83c3208f4bfe97a57765ee824e92");
                String strEncode = Sha1Utils.encode(sb.toString());
                Intrinsics.checkNotNullExpressionValue(strEncode, "encode(MD5Utils.MD5Encod…08f4bfe97a57765ee824e92\")");
                request = builderAddHeader4.addHeader(SocialOperation.GAME_SIGNATURE, strEncode).url(httpUrlBuild).build();
            }
        } else if (Intrinsics.areEqual(strMethod, "POST")) {
            RequestBody requestBodyBody = request.body();
            if (requestBodyBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder(null, 1, 0 == true ? 1 : 0);
                RequestBody requestBodyBody2 = request.body();
                Intrinsics.checkNotNull(requestBodyBody2, "null cannot be cast to non-null type okhttp3.FormBody");
                FormBody formBody = (FormBody) requestBodyBody2;
                int size = formBody.size();
                int i2 = 0;
                while (i2 < size) {
                    builder.addEncoded(formBody.encodedName(i2), formBody.encodedValue(i2));
                    i2++;
                    size = size;
                    str = str;
                }
                String str5 = str;
                FormBody.Builder builderAddEncoded = builder.addEncoded("user_id", prefString);
                String prefString3 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "token", "");
                Intrinsics.checkNotNull(prefString3);
                FormBody.Builder builderAddEncoded2 = builderAddEncoded.addEncoded("token", prefString3);
                String prefString4 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "secret", "");
                Intrinsics.checkNotNull(prefString4);
                FormBody formBodyBuild = builderAddEncoded2.addEncoded("secret", prefString4).addEncoded("app_id", prefString2).build();
                HashMap map2 = new HashMap();
                int i3 = 0;
                for (int size2 = formBodyBuild.size(); i3 < size2; size2 = size2) {
                    map2.put(formBodyBuild.name(i3), formBodyBuild.value(i3));
                    i3++;
                }
                String str6 = "";
                for (Iterator it2 = MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(map2), new Comparator() { // from class: com.ykb.ebook.network.SignInterceptor$intercept$$inlined$sortedBy$2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t2, T t3) {
                        return ComparisonsKt__ComparisonsKt.compareValues((String) ((Pair) t2).component1(), (String) ((Pair) t3).component1());
                    }
                })).entrySet().iterator(); it2.hasNext(); it2 = it2) {
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    str6 = str6 + ((String) entry2.getKey()) + '=' + ((String) entry2.getValue());
                }
                StringBuilder sb2 = new StringBuilder();
                String str7 = str6;
                sb2.append(System.currentTimeMillis() / 1000);
                sb2.append("");
                String string = sb2.toString();
                String str8 = prefString2 + string;
                Request.Builder builderAddHeader5 = request.newBuilder().addHeader("user_id", prefString).addHeader("app_id", prefString2).addHeader("client-type", "android").addHeader("channel", "10000");
                CommonConfig commonConfig2 = CommonConfig.INSTANCE;
                String app_version2 = commonConfig2.getApp_version();
                Intrinsics.checkNotNull(app_version2);
                Request.Builder builderAddHeader6 = builderAddHeader5.addHeader("app-version", app_version2).addHeader(str5, commonConfig2.getYI_KAO_BANG() ? BuildConfig.FLAVOR : "hkb").addHeader("Content-type", "application/x-www-form-urlencoded");
                String uuid2 = commonConfig2.getUuid();
                Intrinsics.checkNotNull(uuid2);
                Request.Builder builderAddHeader7 = builderAddHeader6.addHeader(AliyunLogKey.KEY_UUID, uuid2);
                String user_Agent2 = commonConfig2.getUser_Agent();
                Intrinsics.checkNotNull(user_Agent2);
                Request.Builder builderAddHeader8 = builderAddHeader7.addHeader("User-Agent", user_Agent2).addHeader("timestamp", string);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(MD5Utils.INSTANCE.MD5Encode(str7 + str8));
                sb3.append("bfde83c3208f4bfe97a57765ee824e92");
                String strEncode2 = Sha1Utils.encode(sb3.toString());
                Intrinsics.checkNotNullExpressionValue(strEncode2, "encode(MD5Utils.MD5Encod…08f4bfe97a57765ee824e92\")");
                request = builderAddHeader8.addHeader(SocialOperation.GAME_SIGNATURE, strEncode2).post(formBodyBuild).build();
            } else if (requestBodyBody instanceof MultipartBody) {
                RequestBody requestBodyBody3 = request.body();
                Intrinsics.checkNotNull(requestBodyBody3, "null cannot be cast to non-null type okhttp3.MultipartBody");
                MultipartBody multipartBody = (MultipartBody) requestBodyBody3;
                MultipartBody.Builder type = new MultipartBody.Builder(null, 1, null).setType(multipartBody.type());
                Iterator<T> it3 = multipartBody.parts().iterator();
                while (it3.hasNext()) {
                    type.addPart((MultipartBody.Part) it3.next());
                }
                MultipartBody.Builder builderAddFormDataPart = type.addFormDataPart("user_id", prefString);
                String prefString5 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "token", "");
                Intrinsics.checkNotNull(prefString5);
                MultipartBody.Builder builderAddFormDataPart2 = builderAddFormDataPart.addFormDataPart("token", prefString5);
                String prefString6 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "secret", "");
                Intrinsics.checkNotNull(prefString6);
                MultipartBody multipartBodyBuild = builderAddFormDataPart2.addFormDataPart("secret", prefString6).addFormDataPart("app_id", prefString2).build();
                FormBody.Builder builderAddEncoded3 = new FormBody.Builder(null, 1, 0 == true ? 1 : 0).addEncoded("user_id", prefString);
                String prefString7 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "token", "");
                Intrinsics.checkNotNull(prefString7);
                FormBody.Builder builderAddEncoded4 = builderAddEncoded3.addEncoded("token", prefString7);
                String prefString8 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "secret", "");
                Intrinsics.checkNotNull(prefString8);
                FormBody formBodyBuild2 = builderAddEncoded4.addEncoded("secret", prefString8).addEncoded("app_id", prefString2).build();
                HashMap map3 = new HashMap();
                int size3 = formBodyBuild2.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    map3.put(formBodyBuild2.name(i4), formBodyBuild2.value(i4));
                }
                String str9 = "";
                for (Map.Entry entry3 : MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(map3), new Comparator() { // from class: com.ykb.ebook.network.SignInterceptor$intercept$$inlined$sortedBy$3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t2, T t3) {
                        return ComparisonsKt__ComparisonsKt.compareValues((String) ((Pair) t2).component1(), (String) ((Pair) t3).component1());
                    }
                })).entrySet()) {
                    str9 = str9 + ((String) entry3.getKey()) + '=' + ((String) entry3.getValue());
                }
                String str10 = (System.currentTimeMillis() / 1000) + "";
                String str11 = prefString2 + str10;
                Request.Builder builderAddHeader9 = request.newBuilder().addHeader("client-type", "android").addHeader("channel", "10000");
                CommonConfig commonConfig3 = CommonConfig.INSTANCE;
                String app_version3 = commonConfig3.getApp_version();
                Intrinsics.checkNotNull(app_version3);
                Request.Builder builderAddHeader10 = builderAddHeader9.addHeader("app-version", app_version3).addHeader("app-type", commonConfig3.getYI_KAO_BANG() ? BuildConfig.FLAVOR : "hkb").addHeader("Content-type", "application/x-www-form-urlencoded");
                String uuid3 = commonConfig3.getUuid();
                Intrinsics.checkNotNull(uuid3);
                Request.Builder builderAddHeader11 = builderAddHeader10.addHeader(AliyunLogKey.KEY_UUID, uuid3);
                String user_Agent3 = commonConfig3.getUser_Agent();
                Intrinsics.checkNotNull(user_Agent3);
                Request.Builder builderAddHeader12 = builderAddHeader11.addHeader("User-Agent", user_Agent3).addHeader("timestamp", str10);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(MD5Utils.INSTANCE.MD5Encode(str9 + str11));
                sb4.append("bfde83c3208f4bfe97a57765ee824e92");
                String strEncode3 = Sha1Utils.encode(sb4.toString());
                Intrinsics.checkNotNullExpressionValue(strEncode3, "encode(MD5Utils.MD5Encod…08f4bfe97a57765ee824e92\")");
                request = builderAddHeader12.addHeader(SocialOperation.GAME_SIGNATURE, strEncode3).post(multipartBodyBuild).build();
            } else if (requestBodyBody != null) {
                FormBody.Builder builderAddEncoded5 = new FormBody.Builder(null, 1, 0 == true ? 1 : 0).addEncoded("user_id", prefString);
                String prefString9 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "token", "");
                Intrinsics.checkNotNull(prefString9);
                FormBody.Builder builderAddEncoded6 = builderAddEncoded5.addEncoded("token", prefString9);
                String prefString10 = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "secret", "");
                Intrinsics.checkNotNull(prefString10);
                FormBody formBodyBuild3 = builderAddEncoded6.addEncoded("secret", prefString10).addEncoded("app_id", prefString2).build();
                HashMap map4 = new HashMap();
                int size4 = formBodyBuild3.size();
                for (int i5 = 0; i5 < size4; i5++) {
                    map4.put(formBodyBuild3.name(i5), formBodyBuild3.value(i5));
                }
                String str12 = "";
                for (Map.Entry entry4 : MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(map4), new Comparator() { // from class: com.ykb.ebook.network.SignInterceptor$intercept$$inlined$sortedBy$4
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t2, T t3) {
                        return ComparisonsKt__ComparisonsKt.compareValues((String) ((Pair) t2).component1(), (String) ((Pair) t3).component1());
                    }
                })).entrySet()) {
                    str12 = str12 + ((String) entry4.getKey()) + '=' + ((String) entry4.getValue());
                }
                String str13 = (System.currentTimeMillis() / 1000) + "";
                String str14 = prefString2 + str13;
                Request.Builder builderAddHeader13 = request.newBuilder().addHeader("client-type", "android").addHeader("channel", "10000");
                CommonConfig commonConfig4 = CommonConfig.INSTANCE;
                String app_version4 = commonConfig4.getApp_version();
                Intrinsics.checkNotNull(app_version4);
                Request.Builder builderAddHeader14 = builderAddHeader13.addHeader("app-version", app_version4).addHeader("app-type", commonConfig4.getYI_KAO_BANG() ? BuildConfig.FLAVOR : "hkb").addHeader("Content-type", "application/x-www-form-urlencoded");
                String uuid4 = commonConfig4.getUuid();
                Intrinsics.checkNotNull(uuid4);
                Request.Builder builderAddHeader15 = builderAddHeader14.addHeader(AliyunLogKey.KEY_UUID, uuid4);
                String user_Agent4 = commonConfig4.getUser_Agent();
                Intrinsics.checkNotNull(user_Agent4);
                Request.Builder builderAddHeader16 = builderAddHeader15.addHeader("User-Agent", user_Agent4).addHeader("timestamp", str13);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(MD5Utils.INSTANCE.MD5Encode(str12 + str14));
                sb5.append("bfde83c3208f4bfe97a57765ee824e92");
                String strEncode4 = Sha1Utils.encode(sb5.toString());
                Intrinsics.checkNotNullExpressionValue(strEncode4, "encode(MD5Utils.MD5Encod…08f4bfe97a57765ee824e92\")");
                request = builderAddHeader16.addHeader(SocialOperation.GAME_SIGNATURE, strEncode4).build();
            }
        }
        return chain.proceed(request);
    }
}
