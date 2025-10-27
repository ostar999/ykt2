package com.psychiatrygarden.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.CourseStructureAdapter;
import com.psychiatrygarden.bean.BottomBtn;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.ShareEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u001bH\u0016J\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u000eH\u0016J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0007J\u0012\u0010 \u001a\u00020\u001b2\b\u0010#\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010$\u001a\u00020\u001bH\u0016J\b\u0010%\u001a\u00020\u001bH\u0016J\b\u0010&\u001a\u00020\u001bH\u0002J\u0012\u0010'\u001a\u00020\u001b2\b\u0010(\u001a\u0004\u0018\u00010\tH\u0002J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020+H\u0002R\"\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00020\u0006\u0018\u0001`\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/psychiatrygarden/activity/ActCourseStructure;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "bottomBtn", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/BottomBtn;", "Lkotlin/collections/ArrayList;", "courseId", "", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "giveWayType", "llBottom", "Landroid/view/View;", "llBuyVip", "llJointActivity", "llTakeNow", "mAdapter", "Lcom/psychiatrygarden/adapter/CourseStructureAdapter;", "rvCourseStructure", "Landroidx/recyclerview/widget/RecyclerView;", "service", "Lcom/psychiatrygarden/bean/OnlineServiceBean;", "showBuy", "", "getCourse", "", "getData", "init", "onClick", "v", "onEventMainThread", AliyunLogKey.KEY_EVENT, "Lcom/psychiatrygarden/event/ShareEvent;", "str", "setContentView", "setListenerForWidget", "setMargin", "shareGetCourse", AdUnitActivity.EXTRA_ACTIVITY_ID, "unlock", "type", "", "vipTakeCourse", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nActCourseStructure.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActCourseStructure.kt\ncom/psychiatrygarden/activity/ActCourseStructure\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,379:1\n223#2,2:380\n1#3:382\n*S KotlinDebug\n*F\n+ 1 ActCourseStructure.kt\ncom/psychiatrygarden/activity/ActCourseStructure\n*L\n76#1:380,2\n*E\n"})
/* loaded from: classes5.dex */
public final class ActCourseStructure extends BaseActivity implements View.OnClickListener {

    @Nullable
    private ArrayList<BottomBtn> bottomBtn;
    private CustomEmptyView emptyView;
    private View llBottom;
    private View llBuyVip;
    private View llJointActivity;
    private View llTakeNow;
    private CourseStructureAdapter mAdapter;
    private RecyclerView rvCourseStructure;

    @Nullable
    private OnlineServiceBean service;
    private boolean showBuy;

    @NotNull
    private String courseId = "";

    @NotNull
    private String giveWayType = "";

    private final void getCourse() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.courseId);
        ajaxParams.put("identity_id", strConfig);
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.getFreeCourse, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseStructure.getCourse.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActCourseStructure.this.hideProgressDialog();
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                ActCourseStructure.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ActCourseStructure.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                ActCourseStructure.this.hideProgressDialog();
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            ActCourseStructure.this.AlertToast(strOptString);
                        }
                        EventBus.getDefault().post("BuySuccess");
                        ActCourseStructure.this.showBuy = false;
                        ActCourseStructure.this.setMargin();
                        ActCourseStructure.this.getData();
                        View view = ActCourseStructure.this.llBottom;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llBottom");
                            view = null;
                        }
                        ViewExtensionsKt.gone(view);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getData() {
        String str = NetworkRequestsURL.courseStructure;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.courseId);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseStructure.getData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActCourseStructure.this.AlertToast(strMsg);
                CustomEmptyView customEmptyView = ActCourseStructure.this.emptyView;
                CustomEmptyView customEmptyView2 = null;
                if (customEmptyView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView = null;
                }
                customEmptyView.stopAnim();
                CustomEmptyView customEmptyView3 = ActCourseStructure.this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView2 = customEmptyView3;
                }
                ViewExtensionsKt.gone(customEmptyView2);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        NewToast.showShort(ActCourseStructure.this.mContext, jSONObject.optString("message", ""), 0).show();
                        return;
                    }
                    CustomEmptyView customEmptyView = ActCourseStructure.this.emptyView;
                    CourseStructureAdapter courseStructureAdapter = null;
                    if (customEmptyView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        customEmptyView = null;
                    }
                    customEmptyView.stopAnim();
                    CustomEmptyView customEmptyView2 = ActCourseStructure.this.emptyView;
                    if (customEmptyView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        customEmptyView2 = null;
                    }
                    ViewExtensionsKt.gone(customEmptyView2);
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONArray("package") : null;
                    if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() != 0) {
                        Object objFromJson = new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<? extends CurriculumItemBean.DataDTO>>() { // from class: com.psychiatrygarden.activity.ActCourseStructure$getData$2$onSuccess$setMeal$1
                        }.getType());
                        Intrinsics.checkNotNull(objFromJson, "null cannot be cast to non-null type kotlin.collections.List<com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean.DataDTO>");
                        List list = (List) objFromJson;
                        if (!list.isEmpty()) {
                            CourseStructureAdapter courseStructureAdapter2 = ActCourseStructure.this.mAdapter;
                            if (courseStructureAdapter2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            } else {
                                courseStructureAdapter = courseStructureAdapter2;
                            }
                            courseStructureAdapter.setList(list);
                            return;
                        }
                        CourseStructureAdapter courseStructureAdapter3 = ActCourseStructure.this.mAdapter;
                        if (courseStructureAdapter3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                        } else {
                            courseStructureAdapter = courseStructureAdapter3;
                        }
                        courseStructureAdapter.setList(new ArrayList());
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$10$lambda$4(final ActCourseStructure this$0, BottomBtn btn, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(btn, "$btn");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", btn.getType());
        ajaxParams.put("id", this$0.courseId);
        ajaxParams.put("giveWayType", this$0.giveWayType);
        this$0.showProgressDialog("");
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.c0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                ActCourseStructure.init$lambda$10$lambda$4$lambda$2(this.f11132a);
            }
        });
        MemInterface.getInstance().courseGetMemData(this$0, ajaxParams, NetworkRequestsURL.courseUnlock, 0, false);
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.d0
            @Override // java.lang.Runnable
            public final void run() {
                ActCourseStructure.init$lambda$10$lambda$4$lambda$3(this.f12223c);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$10$lambda$4$lambda$2(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setResult(-1);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$10$lambda$4$lambda$3(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$12$lambda$11(ActCourseStructure this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommonUtil.onlineService(this$0, this$0.service);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$17(final ActCourseStructure this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", Constants.VIA_SHARE_TYPE_INFO);
        ajaxParams.put("id", this$0.courseId);
        if (!TextUtils.isEmpty(this$0.giveWayType)) {
            ajaxParams.put("giveWayType", this$0.giveWayType);
        }
        this$0.showProgressDialog("");
        MemInterface.getInstance().setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.activity.y
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
            public final void shareSuccess(String str) {
                ActCourseStructure.init$lambda$17$lambda$14(this.f14199a, str);
            }
        });
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.a0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                ActCourseStructure.init$lambda$17$lambda$15(this.f10979a);
            }
        });
        MemInterface.getInstance().courseGetMemData(this$0, ajaxParams, NetworkRequestsURL.courseUnlock, 0, false);
        view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.b0
            @Override // java.lang.Runnable
            public final void run() {
                ActCourseStructure.init$lambda$17$lambda$16(this.f11092c);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$17$lambda$14(ActCourseStructure this$0, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.shareGetCourse(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$17$lambda$15(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EventBus.getDefault().post("BuySuccess");
        this$0.showBuy = false;
        this$0.setMargin();
        this$0.getData();
        View view = this$0.llBottom;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBottom");
            view = null;
        }
        ViewExtensionsKt.gone(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$17$lambda$16(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgressDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setMargin() {
        View view = this.llBottom;
        RecyclerView recyclerView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBottom");
            view = null;
        }
        view.setVisibility(this.showBuy ? 0 : 8);
        RecyclerView recyclerView2 = this.rvCourseStructure;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCourseStructure");
            recyclerView2 = null;
        }
        ViewGroup.LayoutParams layoutParams = recyclerView2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.bottomMargin = SizeUtil.dp2px(this, this.showBuy ? 59 : 0);
        RecyclerView recyclerView3 = this.rvCourseStructure;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCourseStructure");
        } else {
            recyclerView = recyclerView3;
        }
        recyclerView.setLayoutParams(layoutParams2);
    }

    private final void shareGetCourse(String activityId) {
        String str = NetworkRequestsURL.shareGetCourse;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.courseId);
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activityId);
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseStructure.shareGetCourse.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ActCourseStructure.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ActCourseStructure.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((C05632) t2);
                ActCourseStructure.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        ActCourseStructure.this.AlertToast("领取成功");
                        EventBus.getDefault().post("BuySuccess");
                        ActCourseStructure.this.showBuy = false;
                        ActCourseStructure.this.setMargin();
                        ActCourseStructure.this.getData();
                    } else {
                        ActCourseStructure.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void unlock(int type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", String.valueOf(type));
        ajaxParams.put("id", this.courseId);
        ajaxParams.put("giveWayType", this.giveWayType);
        showProgressDialog("");
        MemInterface.getInstance().setShareSuccessListener(new MemInterface.ShareSuccessListener() { // from class: com.psychiatrygarden.activity.e0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.ShareSuccessListener
            public final void shareSuccess(String str) {
                ActCourseStructure.unlock$lambda$21(this.f12264a, str);
            }
        });
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.f0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                ActCourseStructure.unlock$lambda$22(this.f12333a);
            }
        });
        MemInterface.getInstance().courseGetMemData(this, ajaxParams, NetworkRequestsURL.courseUnlock, 0, false);
        View view = this.llBuyVip;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBuyVip");
            view = null;
        }
        view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.g0
            @Override // java.lang.Runnable
            public final void run() {
                ActCourseStructure.unlock$lambda$23(this.f12421c);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$21(ActCourseStructure this$0, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.shareGetCourse(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$22(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EventBus.getDefault().post("BuySuccess");
        this$0.showBuy = false;
        this$0.setMargin();
        this$0.getData();
        View view = this$0.llBottom;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("llBottom");
            view = null;
        }
        ViewExtensionsKt.gone(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unlock$lambda$23(ActCourseStructure this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideProgressDialog();
    }

    private final void vipTakeCourse(int type) {
        String str = NetworkRequestsURL.vipGetCourse;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.courseId);
        ajaxParams.put("type", String.valueOf(type));
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(this, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ActCourseStructure.vipTakeCourse.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String t2) {
                super.onSuccess((C05642) t2);
                try {
                    if (t2 == null) {
                        t2 = "";
                    }
                    if (Intrinsics.areEqual(new JSONObject(t2).optString("code"), "200")) {
                        ActCourseStructure.this.AlertToast("领取成功");
                        EventBus.getDefault().post("BuySuccess");
                        ActCourseStructure.this.showBuy = false;
                        ActCourseStructure.this.setMargin();
                        ActCourseStructure.this.getData();
                        View view = ActCourseStructure.this.llBottom;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("llBottom");
                            view = null;
                        }
                        ViewExtensionsKt.gone(view);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Object next;
        Object next2;
        Object next3;
        Object next4;
        Object next5;
        View viewFindViewById = findViewById(R.id.rvCourseStructure);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvCourseStructure)");
        this.rvCourseStructure = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.empty_view)");
        this.emptyView = (CustomEmptyView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.ll_bottom);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.ll_bottom)");
        this.llBottom = viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.ll_take_now);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.ll_take_now)");
        this.llTakeNow = viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.ll_buy_vip_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.ll_buy_vip_study)");
        this.llBuyVip = viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.ll_join_activity_study);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.ll_join_activity_study)");
        this.llJointActivity = viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.ll_buy_now);
        TextView textView = (TextView) findViewById(R.id.tv_buy);
        ArrayList<BottomBtn> arrayList = this.bottomBtn;
        CourseStructureAdapter courseStructureAdapter = null;
        if (arrayList != null) {
            for (final BottomBtn bottomBtn : arrayList) {
                if (!Intrinsics.areEqual(bottomBtn.getDisable(), "1")) {
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ActCourseStructure.init$lambda$10$lambda$4(this.f12460c, bottomBtn, view);
                        }
                    });
                    View view = this.llTakeNow;
                    if (view == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
                        view = null;
                    }
                    Iterator<T> it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        BottomBtn bottomBtn2 = (BottomBtn) next;
                        if (Intrinsics.areEqual("2", bottomBtn2.getType()) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, bottomBtn2.getType()) || Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, bottomBtn2.getType())) {
                            break;
                        }
                    }
                    view.setVisibility(next != null ? 0 : 8);
                    Iterator<T> it2 = arrayList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            next2 = null;
                            break;
                        }
                        next2 = it2.next();
                        BottomBtn bottomBtn3 = (BottomBtn) next2;
                        if (Intrinsics.areEqual("2", bottomBtn3.getType()) || Intrinsics.areEqual(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, bottomBtn3.getType()) || Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, bottomBtn3.getType())) {
                            break;
                        }
                    }
                    BottomBtn bottomBtn4 = (BottomBtn) next2;
                    String type = bottomBtn4 != null ? bottomBtn4.getType() : null;
                    View view2 = this.llTakeNow;
                    if (view2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
                        view2 = null;
                    }
                    view2.setTag(type != null ? Integer.valueOf(Integer.parseInt(type)) : 0);
                    View view3 = this.llBuyVip;
                    if (view3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llBuyVip");
                        view3 = null;
                    }
                    Iterator<T> it3 = arrayList.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            next3 = it3.next();
                            if (Intrinsics.areEqual("7", ((BottomBtn) next3).getType())) {
                                break;
                            }
                        } else {
                            next3 = null;
                            break;
                        }
                    }
                    view3.setVisibility(next3 != null ? 0 : 8);
                    Iterator<T> it4 = arrayList.iterator();
                    while (true) {
                        if (it4.hasNext()) {
                            next4 = it4.next();
                            if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_INFO, ((BottomBtn) next4).getType())) {
                                break;
                            }
                        } else {
                            next4 = null;
                            break;
                        }
                    }
                    viewFindViewById7.setVisibility(next4 != null ? 0 : 8);
                    View view4 = this.llBuyVip;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llBuyVip");
                        view4 = null;
                    }
                    view4.setTag(7);
                    View view5 = this.llJointActivity;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llJointActivity");
                        view5 = null;
                    }
                    Iterator<T> it5 = arrayList.iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            next5 = it5.next();
                            if (Intrinsics.areEqual(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, ((BottomBtn) next5).getType())) {
                                break;
                            }
                        } else {
                            next5 = null;
                            break;
                        }
                    }
                    view5.setVisibility(next5 == null ? 8 : 0);
                    View view6 = this.llJointActivity;
                    if (view6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llJointActivity");
                        view6 = null;
                    }
                    view6.setTag(8);
                    View view7 = this.llTakeNow;
                    if (view7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llTakeNow");
                        view7 = null;
                    }
                    view7.setOnClickListener(this);
                    View view8 = this.llJointActivity;
                    if (view8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llJointActivity");
                        view8 = null;
                    }
                    view8.setOnClickListener(this);
                    View view9 = this.llBuyVip;
                    if (view9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("llBuyVip");
                        view9 = null;
                    }
                    view9.setOnClickListener(this);
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        if (this.service != null) {
            findViewById(R.id.iv_customer).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.i0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view10) {
                    ActCourseStructure.init$lambda$12$lambda$11(this.f12490c, view10);
                }
            });
        }
        RecyclerView recyclerView = this.rvCourseStructure;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCourseStructure");
            recyclerView = null;
        }
        CourseStructureAdapter courseStructureAdapter2 = this.mAdapter;
        if (courseStructureAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            courseStructureAdapter = courseStructureAdapter2;
        }
        recyclerView.setAdapter(courseStructureAdapter);
        if (!TextUtils.isEmpty(this.courseId)) {
            getData();
        }
        setMargin();
        findViewById(R.id.ll_buy_now).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view10) {
                ActCourseStructure.init$lambda$17(this.f14228c, view10);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        Object tag = v2.getTag();
        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) tag).intValue();
        int id = v2.getId();
        if (id == R.id.ll_buy_vip_study || id == R.id.ll_join_activity_study) {
            unlock(iIntValue);
            return;
        }
        if (id != R.id.ll_take_now) {
            return;
        }
        if (iIntValue == 9 || iIntValue == 10) {
            vipTakeCourse(iIntValue);
        } else {
            getCourse();
        }
    }

    @Subscribe
    public final void onEventMainThread(@NotNull ShareEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        shareGetCourse(e2.getActivityId());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_course_structure);
        String stringExtra = getIntent().getStringExtra("courseId");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.courseId = stringExtra;
        String stringExtra2 = getIntent().getStringExtra("giveWayType");
        this.giveWayType = stringExtra2 != null ? stringExtra2 : "";
        Serializable serializableExtra = getIntent().getSerializableExtra("service");
        CourseStructureAdapter courseStructureAdapter = null;
        this.service = serializableExtra instanceof OnlineServiceBean ? (OnlineServiceBean) serializableExtra : null;
        this.showBuy = getIntent().getBooleanExtra("showBuy", false);
        Serializable serializableExtra2 = getIntent().getSerializableExtra("btn");
        this.bottomBtn = serializableExtra2 instanceof ArrayList ? (ArrayList) serializableExtra2 : null;
        CourseStructureAdapter courseStructureAdapter2 = new CourseStructureAdapter(this);
        this.mAdapter = courseStructureAdapter2;
        courseStructureAdapter2.setLastItemAddMarginBottom(true);
        CourseStructureAdapter courseStructureAdapter3 = this.mAdapter;
        if (courseStructureAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            courseStructureAdapter = courseStructureAdapter3;
        }
        courseStructureAdapter.setHaveLine(false);
        setNewStyleStatusBarColor();
        setTitle("课程体系");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
