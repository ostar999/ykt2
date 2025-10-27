package com.yddmi.doctor.config;

import com.blankj.utilcode.util.DeviceUtils;
import com.catchpig.mvvm.config.Config;
import com.catchpig.mvvm.ext.GsonExtKt;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.google.gson.Gson;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.Operation;
import com.yddmi.doctor.entity.request.PointSaveReq;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\tJ\u0006\u0010\f\u001a\u00020\u0007J\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\b\u0010\u000f\u001a\u00020\u0007H\u0002J\u0006\u0010\u0010\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/config/YddPointManager;", "", "()V", "operationList", "", "Lcom/yddmi/doctor/entity/request/Operation;", "addPoint", "", "operationCode", "", "businessRemark", "businessId", "clearOperationList", "getPointSave", "Lcom/yddmi/doctor/entity/request/PointSaveReq;", "restorePoint", "savePoint2Cache", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nYddPointManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 YddPointManager.kt\ncom/yddmi/doctor/config/YddPointManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 GsonExt.kt\ncom/catchpig/mvvm/ext/GsonExtKt\n*L\n1#1,89:1\n1#2:90\n10#3,2:91\n*S KotlinDebug\n*F\n+ 1 YddPointManager.kt\ncom/yddmi/doctor/config/YddPointManager\n*L\n79#1:91,2\n*E\n"})
/* loaded from: classes6.dex */
public final class YddPointManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<YddPointManager> instance$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<YddPointManager>() { // from class: com.yddmi.doctor.config.YddPointManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final YddPointManager invoke() {
            return new YddPointManager();
        }
    });

    @NotNull
    private final List<Operation> operationList = new ArrayList();

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/config/YddPointManager$Companion;", "", "()V", "instance", "Lcom/yddmi/doctor/config/YddPointManager;", "getInstance", "()Lcom/yddmi/doctor/config/YddPointManager;", "instance$delegate", "Lkotlin/Lazy;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final YddPointManager getInstance() {
            return (YddPointManager) YddPointManager.instance$delegate.getValue();
        }
    }

    public static /* synthetic */ void addPoint$default(YddPointManager yddPointManager, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        if ((i2 & 4) != 0) {
            str3 = "";
        }
        yddPointManager.addPoint(str, str2, str3);
    }

    private final void restorePoint() {
        try {
            DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
            String str = (String) dataStoreUtils.getSyncData(YddConfig.SP_POINT_SAVE, "");
            if (str == null || str.length() == 0) {
                return;
            }
            dataStoreUtils.putSyncData(YddConfig.SP_POINT_SAVE, "");
            Gson gson = Config.INSTANCE.getGson();
            Intrinsics.checkNotNullExpressionValue(gson, "Config.gson");
            List list = (List) gson.fromJson(str, List.class);
            if (list != null) {
                this.operationList.addAll(0, list);
            }
        } catch (Throwable th) {
            LogExtKt.loge("埋点restorePoint " + th.getMessage(), YddConfig.TAG);
        }
    }

    public final void addPoint(@NotNull String operationCode, @NotNull String businessRemark, @NotNull String businessId) {
        Intrinsics.checkNotNullParameter(operationCode, "operationCode");
        Intrinsics.checkNotNullParameter(businessRemark, "businessRemark");
        Intrinsics.checkNotNullParameter(businessId, "businessId");
        List<Operation> list = this.operationList;
        if (businessId.length() == 0) {
            businessId = "-1";
        }
        list.add(new Operation(operationCode, businessRemark, businessId, DateUtil.getTimeInSecondLong()));
    }

    public final void clearOperationList() {
        this.operationList.clear();
    }

    @Nullable
    public final PointSaveReq getPointSave() {
        restorePoint();
        List<Operation> list = this.operationList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        String manufacturer = DeviceUtils.getManufacturer();
        Intrinsics.checkNotNullExpressionValue(manufacturer, "getManufacturer()");
        String model = DeviceUtils.getModel();
        Intrinsics.checkNotNullExpressionValue(model, "getModel()");
        String versionName = AppInformationUtil.getVersionName();
        Intrinsics.checkNotNullExpressionValue(versionName, "getVersionName()");
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        return new PointSaveReq(manufacturer, model, versionName, companion.getInstance().userId(), companion.getInstance().userName(), this.operationList);
    }

    public final void savePoint2Cache() {
        List<Operation> list = this.operationList;
        if (list == null || list.isEmpty()) {
            return;
        }
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_POINT_SAVE, GsonExtKt.jsonToString$default(this.operationList, null, 1, null));
    }
}
