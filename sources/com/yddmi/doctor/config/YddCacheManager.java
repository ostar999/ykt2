package com.yddmi.doctor.config;

import cn.hutool.core.text.StrPool;
import com.catchpig.mvvm.ext.GsonExtKt;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.entity.result.HomeMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J#\u0010\f\u001a\u00020\b2\u0010\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\rR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/config/YddCacheManager;", "", "()V", "mNoticeMsgList", "", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "getNoticeCacheMsg", "saveNoticeMsg2Cache", "", "data", "(Lcom/yddmi/doctor/entity/result/HomeMsg;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveNoticeMsg2CacheN0", "saveNoticeMsgList2Cache", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nYddCacheManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 YddCacheManager.kt\ncom/yddmi/doctor/config/YddCacheManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,97:1\n1855#2,2:98\n*S KotlinDebug\n*F\n+ 1 YddCacheManager.kt\ncom/yddmi/doctor/config/YddCacheManager\n*L\n86#1:98,2\n*E\n"})
/* loaded from: classes6.dex */
public final class YddCacheManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<YddCacheManager> instance$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<YddCacheManager>() { // from class: com.yddmi.doctor.config.YddCacheManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final YddCacheManager invoke() {
            return new YddCacheManager();
        }
    });

    @NotNull
    private List<HomeMsg> mNoticeMsgList = new ArrayList();

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/config/YddCacheManager$Companion;", "", "()V", "instance", "Lcom/yddmi/doctor/config/YddCacheManager;", "getInstance", "()Lcom/yddmi/doctor/config/YddCacheManager;", "instance$delegate", "Lkotlin/Lazy;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final YddCacheManager getInstance() {
            return (YddCacheManager) YddCacheManager.instance$delegate.getValue();
        }
    }

    @NotNull
    public final List<HomeMsg> getNoticeCacheMsg() {
        if (this.mNoticeMsgList.isEmpty()) {
            try {
                String str = (String) DataStoreUtils.INSTANCE.getSyncData("sp_notice_" + YddHostConfig.INSTANCE.getInstance().getCurrentHost().name() + StrPool.UNDERLINE + YddUserManager.INSTANCE.getInstance().userId(), "");
                if (str.length() > 0) {
                    Iterator it = GsonUtil.INSTANCE.jsonToList(str, HomeMsg.class).iterator();
                    while (it.hasNext()) {
                        this.mNoticeMsgList.add((HomeMsg) it.next());
                    }
                }
            } catch (Throwable th) {
                LogExtKt.loge("通知缓存信息json解析异常 " + th.getMessage(), YddConfig.TAG);
            }
        }
        return this.mNoticeMsgList;
    }

    @Nullable
    public final Object saveNoticeMsg2Cache(@NotNull HomeMsg homeMsg, @NotNull Continuation<? super Unit> continuation) {
        if (homeMsg.isRead() == 0) {
            return Unit.INSTANCE;
        }
        this.mNoticeMsgList.add(homeMsg);
        CollectionsKt___CollectionsKt.distinct(this.mNoticeMsgList);
        Object objPutData = DataStoreUtils.INSTANCE.putData("sp_notice_" + YddHostConfig.INSTANCE.getInstance().getCurrentHost().name() + StrPool.UNDERLINE + YddUserManager.INSTANCE.getInstance().userId(), GsonExtKt.jsonToString$default(this.mNoticeMsgList, null, 1, null), continuation);
        return objPutData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objPutData : Unit.INSTANCE;
    }

    public final void saveNoticeMsg2CacheN0(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.isRead() == 0) {
            return;
        }
        this.mNoticeMsgList.add(data);
        CollectionsKt___CollectionsKt.distinct(this.mNoticeMsgList);
        DataStoreUtils.INSTANCE.putSyncData("sp_notice_" + YddHostConfig.INSTANCE.getInstance().getCurrentHost().name() + StrPool.UNDERLINE + YddUserManager.INSTANCE.getInstance().userId(), GsonExtKt.jsonToString$default(this.mNoticeMsgList, null, 1, null));
    }

    @Nullable
    public final Object saveNoticeMsgList2Cache(@Nullable List<HomeMsg> list, @NotNull Continuation<? super Unit> continuation) {
        List<HomeMsg> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return Unit.INSTANCE;
        }
        this.mNoticeMsgList.addAll(list2);
        CollectionsKt___CollectionsKt.distinct(this.mNoticeMsgList);
        LogExtKt.logd("通知全部已读数量： " + this.mNoticeMsgList.size(), YddConfig.TAG);
        Object objPutData = DataStoreUtils.INSTANCE.putData("sp_notice_" + YddHostConfig.INSTANCE.getInstance().getCurrentHost().name() + StrPool.UNDERLINE + YddUserManager.INSTANCE.getInstance().userId(), GsonExtKt.jsonToString$default(this.mNoticeMsgList, null, 1, null), continuation);
        return objPutData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objPutData : Unit.INSTANCE;
    }
}
