package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.ActCourseStructure;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.adapter.CourseStructureAdapter;
import com.psychiatrygarden.bean.BottomBtn;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseGiftItem;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.DetailCourseStructureWidget;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000k\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0012\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B#\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\fJ\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\fH\u0002J\u0018\u0010#\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010$\u001a\u00020\fH\u0002J(\u0010%\u001a\u00020\u001b2\u000e\u0010&\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\tH\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseStructureWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", AdUnitActivity.EXTRA_ACTIVITY_ID, "", "courseDetailBean", "Lcom/psychiatrygarden/bean/CourseDetailBean;", "directoryView", "Lcom/psychiatrygarden/widget/DetailCourseDirectoryWidget;", "mAdapter", "com/psychiatrygarden/widget/DetailCourseStructureWidget$mAdapter$1", "Lcom/psychiatrygarden/widget/DetailCourseStructureWidget$mAdapter$1;", "rvCourseStructure", "Landroidx/recyclerview/widget/RecyclerView;", "rvSku", "showMax", "tvDescription", "Landroid/widget/TextView;", "init", "", "goodsId", "extraData", com.umeng.socialize.tracker.a.f23806c, "data", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "loadCourseData", "id", "loadData", "type", "onItemClick", "adapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailCourseStructureWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseStructureWidget.kt\ncom/psychiatrygarden/widget/DetailCourseStructureWidget\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,243:1\n1#2:244\n1#2:253\n350#3,7:245\n2645#3:252\n1864#3,3:254\n*S KotlinDebug\n*F\n+ 1 DetailCourseStructureWidget.kt\ncom/psychiatrygarden/widget/DetailCourseStructureWidget\n*L\n238#1:253\n236#1:245,7\n238#1:252\n238#1:254,3\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailCourseStructureWidget extends LinearLayout implements BaseContentWidget, OnItemClickListener {

    @Nullable
    private String activityId;

    @Nullable
    private CourseDetailBean courseDetailBean;

    @NotNull
    private DetailCourseDirectoryWidget directoryView;

    @NotNull
    private final DetailCourseStructureWidget$mAdapter$1 mAdapter;
    private RecyclerView rvCourseStructure;

    @NotNull
    private RecyclerView rvSku;
    private final int showMax;

    @NotNull
    private TextView tvDescription;

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/widget/DetailCourseStructureWidget$loadData$2", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nDetailCourseStructureWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseStructureWidget.kt\ncom/psychiatrygarden/widget/DetailCourseStructureWidget$loadData$2\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,243:1\n1#2:244\n*E\n"})
    /* renamed from: com.psychiatrygarden.widget.DetailCourseStructureWidget$loadData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C06222 extends AjaxCallBack<String> {
        final /* synthetic */ String $goodsId;

        public C06222(String str) {
            this.$goodsId = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$5(DetailCourseStructureWidget this$0, String goodsId, View view) {
            OnlineServiceBean beforeCs;
            ArrayList<BottomBtn> btnList;
            Object next;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(goodsId, "$goodsId");
            CourseDetailBean courseDetailBean = this$0.courseDetailBean;
            boolean z2 = false;
            String str = "";
            if (courseDetailBean != null) {
                ArrayList<CourseGiftItem> gift = courseDetailBean.getGift();
                if (!(gift == null || gift.isEmpty())) {
                    Iterator<T> it = courseDetailBean.getGift().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (Intrinsics.areEqual(((CourseGiftItem) next).getGoods_type(), "1")) {
                                break;
                            }
                        }
                    }
                    if (((CourseGiftItem) next) != null) {
                        str = "1";
                    }
                }
            }
            Intent intent = new Intent();
            intent.setClass(this$0.getContext(), ActCourseStructure.class);
            CourseDetailBean courseDetailBean2 = this$0.courseDetailBean;
            if (courseDetailBean2 != null && (btnList = courseDetailBean2.getBtnList()) != null) {
                intent.putExtra("btn", btnList);
            }
            intent.putExtra("courseId", goodsId);
            intent.putExtra("giveWayType", str);
            CourseDetailBean courseDetailBean3 = this$0.courseDetailBean;
            intent.putExtra("showBuy", (courseDetailBean3 == null || courseDetailBean3.hasPermission()) ? false : true);
            CourseDetailBean courseDetailBean4 = this$0.courseDetailBean;
            if (courseDetailBean4 != null && (beforeCs = courseDetailBean4.getBeforeCs()) != null) {
                intent.putExtra("service", beforeCs);
            }
            CourseDetailBean courseDetailBean5 = this$0.courseDetailBean;
            if (courseDetailBean5 != null && !courseDetailBean5.hasPermission()) {
                z2 = true;
            }
            if (!z2) {
                this$0.getContext().startActivity(intent);
                return;
            }
            Context context = this$0.getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
            ((Activity) context).startActivityForResult(intent, 1);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            View viewFindViewById = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(R.id.rl_all)");
            ViewExtensionsKt.gone(viewFindViewById);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String t2) {
            Intrinsics.checkNotNullParameter(t2, "t");
            super.onSuccess((C06222) t2);
            try {
                JSONObject jSONObject = new JSONObject(t2);
                if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                    NewToast.showShort(DetailCourseStructureWidget.this.getContext(), jSONObject.optString("message", ""), 0).show();
                    View viewFindViewById = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(R.id.rl_all)");
                    ViewExtensionsKt.gone(viewFindViewById);
                    return;
                }
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                RecyclerView recyclerView = null;
                String strOptString = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("package_text") : null;
                DetailCourseStructureWidget.this.tvDescription.setText('(' + strOptString + ')');
                Context context = DetailCourseStructureWidget.this.getContext();
                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
                CourseStructureAdapter courseStructureAdapter = new CourseStructureAdapter((Activity) context);
                courseStructureAdapter.setHaveLine(false);
                CustomEmptyView customEmptyView = new CustomEmptyView(DetailCourseStructureWidget.this.getContext(), SkinManager.getCurrentSkinType(DetailCourseStructureWidget.this.getContext()) == 1 ? R.drawable.ic_empty_data_wait_update_night_svg : R.drawable.ic_empty_data_wait_update_day_svg, "内容更新中");
                customEmptyView.showEmptyView();
                customEmptyView.setPadding(customEmptyView.getPaddingLeft(), customEmptyView.getPaddingTop(), customEmptyView.getPaddingRight(), SizeUtil.dp2px(DetailCourseStructureWidget.this.getContext(), 10));
                courseStructureAdapter.setEmptyView(customEmptyView);
                JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONArray("package") : null;
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() != 0) {
                    Object objFromJson = new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<? extends CurriculumItemBean.DataDTO>>() { // from class: com.psychiatrygarden.widget.DetailCourseStructureWidget$loadData$2$onSuccess$setMeal$1
                    }.getType());
                    Intrinsics.checkNotNull(objFromJson, "null cannot be cast to non-null type kotlin.collections.List<com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean.DataDTO>");
                    List list = (List) objFromJson;
                    CourseDetailBean courseDetailBean = DetailCourseStructureWidget.this.courseDetailBean;
                    Intrinsics.checkNotNull(courseDetailBean);
                    if (!Intrinsics.areEqual(courseDetailBean.getShowMode(), "0")) {
                        View viewFindViewById2 = DetailCourseStructureWidget.this.findViewById(R.id.ll_type_normal);
                        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<View>(R.id.ll_type_normal)");
                        ViewExtensionsKt.gone(viewFindViewById2);
                        View viewFindViewById3 = DetailCourseStructureWidget.this.findViewById(R.id.ll_type_directory);
                        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById<View>(R.id.ll_type_directory)");
                        ViewExtensionsKt.visible(viewFindViewById3);
                        DetailCourseStructureWidget.this.rvSku.setAdapter(DetailCourseStructureWidget.this.mAdapter);
                        ((CurriculumItemBean.DataDTO) list.get(0)).setSelect(true);
                        setList(list);
                        DetailCourseStructureWidget detailCourseStructureWidget = DetailCourseStructureWidget.this;
                        String id = ((CurriculumItemBean.DataDTO) list.get(0)).getId();
                        Intrinsics.checkNotNullExpressionValue(id, "setMeal[0].id");
                        detailCourseStructureWidget.loadCourseData(id);
                        setOnItemClickListener(DetailCourseStructureWidget.this);
                        return;
                    }
                    View viewFindViewById4 = DetailCourseStructureWidget.this.findViewById(R.id.ll_type_normal);
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById<View>(R.id.ll_type_normal)");
                    ViewExtensionsKt.visible(viewFindViewById4);
                    View viewFindViewById5 = DetailCourseStructureWidget.this.findViewById(R.id.ll_type_directory);
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById<View>(R.id.ll_type_directory)");
                    ViewExtensionsKt.gone(viewFindViewById5);
                    if (true ^ list.isEmpty()) {
                        RecyclerView recyclerView2 = DetailCourseStructureWidget.this.rvCourseStructure;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvCourseStructure");
                        } else {
                            recyclerView = recyclerView2;
                        }
                        recyclerView.setAdapter(courseStructureAdapter);
                        courseStructureAdapter.setList(list.size() <= DetailCourseStructureWidget.this.showMax ? list : list.subList(0, DetailCourseStructureWidget.this.showMax));
                        if (list.size() <= DetailCourseStructureWidget.this.showMax) {
                            View viewFindViewById6 = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById<View>(R.id.rl_all)");
                            ViewExtensionsKt.gone(viewFindViewById6);
                        } else {
                            DetailCourseStructureWidget.this.findViewById(R.id.rl_all).setVisibility(0);
                            View viewFindViewById7 = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                            final DetailCourseStructureWidget detailCourseStructureWidget2 = DetailCourseStructureWidget.this;
                            final String str = this.$goodsId;
                            viewFindViewById7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.g6
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    DetailCourseStructureWidget.C06222.onSuccess$lambda$5(detailCourseStructureWidget2, str, view);
                                }
                            });
                        }
                    } else {
                        View viewFindViewById8 = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<View>(R.id.rl_all)");
                        ViewExtensionsKt.gone(viewFindViewById8);
                    }
                    EventBus.getDefault().post("CALCULATE");
                    return;
                }
                RecyclerView recyclerView3 = DetailCourseStructureWidget.this.rvCourseStructure;
                if (recyclerView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvCourseStructure");
                } else {
                    recyclerView = recyclerView3;
                }
                recyclerView.setAdapter(courseStructureAdapter);
                courseStructureAdapter.setList(new ArrayList());
                View viewFindViewById9 = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<View>(R.id.rl_all)");
                ViewExtensionsKt.gone(viewFindViewById9);
            } catch (Throwable th) {
                th.printStackTrace();
                View viewFindViewById10 = DetailCourseStructureWidget.this.findViewById(R.id.rl_all);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById<View>(R.id.rl_all)");
                ViewExtensionsKt.gone(viewFindViewById10);
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseStructureWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r3v16, types: [com.psychiatrygarden.widget.DetailCourseStructureWidget$mAdapter$1] */
    @JvmOverloads
    public DetailCourseStructureWidget(@NotNull final Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.showMax = 3;
        View.inflate(context, R.layout.layout_course_detail_structure, this);
        View viewFindViewById = findViewById(R.id.rvCourseStructure);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvCourseStructure)");
        this.rvCourseStructure = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_description);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_description)");
        this.tvDescription = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.directory);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.directory)");
        this.directoryView = (DetailCourseDirectoryWidget) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.rvSku);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.rvSku)");
        this.rvSku = (RecyclerView) viewFindViewById4;
        if (context instanceof ActCourseOrGoodsDetail) {
            ((ActCourseOrGoodsDetail) context).clickDirectoryItem(this.directoryView);
        }
        this.mAdapter = new BaseQuickAdapter<CurriculumItemBean.DataDTO, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.DetailCourseStructureWidget$mAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_directory_mode, null, 2, null);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CurriculumItemBean.DataDTO item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.itemView.setSelected(item.isSelect());
                TextView textView = (TextView) holder.getView(R.id.tv_course_count);
                TypedArray typedArrayObtainStyledAttributes = this.this$0.getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color, R.attr.second_txt_color});
                Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "getContext().theme.obtai…R.attr.second_txt_color))");
                textView.setTextColor(item.isSelect() ? typedArrayObtainStyledAttributes.getColor(0, context.getColor(R.color.main_theme_color)) : typedArrayObtainStyledAttributes.getColor(1, context.getColor(R.color.second_txt_color)));
                holder.getView(R.id.tv_course_name).setSelected(item.isSelect());
                BaseViewHolder text = holder.setText(R.id.tv_course_name, (holder.getLayoutPosition() + 1) + (char) 12289 + item.getTitle());
                StringBuilder sb = new StringBuilder();
                sb.append(item.getCourseCount());
                sb.append("节课");
                text.setText(R.id.tv_course_count, sb.toString());
                typedArrayObtainStyledAttributes.recycle();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadCourseData(final String id) {
        Context context = getContext();
        String str = NetworkRequestsURL.courseDetail;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DetailCourseStructureWidget.loadCourseData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                NewToast.showShort(DetailCourseStructureWidget.this.getContext(), strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        DetailCourseStructureWidget.this.courseDetailBean = (CourseDetailBean) new Gson().fromJson(jSONObject.optString("data"), CourseDetailBean.class);
                        if (DetailCourseStructureWidget.this.courseDetailBean != null) {
                            DetailCourseDirectoryWidget detailCourseDirectoryWidget = DetailCourseStructureWidget.this.directoryView;
                            String str2 = id;
                            CourseDetailBean courseDetailBean = DetailCourseStructureWidget.this.courseDetailBean;
                            Intrinsics.checkNotNull(courseDetailBean);
                            String type = courseDetailBean.getType();
                            CourseDetailBean courseDetailBean2 = DetailCourseStructureWidget.this.courseDetailBean;
                            Intrinsics.checkNotNull(courseDetailBean2);
                            detailCourseDirectoryWidget.initParams2(str2, type, (16 & 4) != 0 ? false : false, (16 & 8) != 0 ? true : courseDetailBean2.hasPermission(), (16 & 16) != 0 ? null : null, (16 & 32) != 0, (16 & 64) != 0 ? true : true);
                        }
                        if (DetailCourseStructureWidget.this.getContext() instanceof ActCourseOrGoodsDetail) {
                            Context context2 = DetailCourseStructureWidget.this.getContext();
                            Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type com.psychiatrygarden.activity.ActCourseOrGoodsDetail");
                            CourseDetailBean courseDetailBean3 = DetailCourseStructureWidget.this.courseDetailBean;
                            Intrinsics.checkNotNull(courseDetailBean3);
                            ((ActCourseOrGoodsDetail) context2).setChildCourseData(courseDetailBean3);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadData(String goodsId, String type) {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext());
        Context context = getContext();
        String str = NetworkRequestsURL.courseStructure;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", type);
        ajaxParams.put("id", goodsId);
        ajaxParams.put("identity_id", strConfig);
        CourseDetailBean courseDetailBean = this.courseDetailBean;
        Intrinsics.checkNotNull(courseDetailBean);
        ajaxParams.put("display_type", courseDetailBean.getShowMode());
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new C06222(goodsId));
    }

    public final void init(@NotNull String goodsId, @NotNull String extraData) {
        Intrinsics.checkNotNullParameter(goodsId, "goodsId");
        Intrinsics.checkNotNullParameter(extraData, "extraData");
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        TextUtils.isEmpty(extraData);
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.courseDetailBean = data.getCourseData();
        TextView textView = (TextView) findViewById(R.id.tv_title);
        CourseDetailBean courseDetailBean = this.courseDetailBean;
        Intrinsics.checkNotNull(courseDetailBean);
        if (Intrinsics.areEqual(courseDetailBean.getShowMode(), "1")) {
            textView.setText("课程目录");
        }
        String goodsId = data.getGoodsId();
        Intrinsics.checkNotNullExpressionValue(goodsId, "data.goodsId");
        loadData(goodsId, data.getCourseData().getType());
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view, int position) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
        List<CurriculumItemBean.DataDTO> data = getData();
        Iterator<CurriculumItemBean.DataDTO> it = data.iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (it.next().isSelect()) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == position) {
            return;
        }
        int i3 = 0;
        for (Object obj : data) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            ((CurriculumItemBean.DataDTO) obj).setSelect(position == i3);
            i3 = i4;
        }
        adapter.notifyDataSetChanged();
        adapter.getRecyclerView().scrollToPosition(position);
        String id = getItem(position).getId();
        Intrinsics.checkNotNullExpressionValue(id, "mAdapter.getItem(position).id");
        loadCourseData(id);
    }

    public /* synthetic */ DetailCourseStructureWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
