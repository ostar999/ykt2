package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.psychiatrygarden.activity.ActCourseCommentList;
import com.psychiatrygarden.adapter.CourseCommentAdapter;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0012J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000eH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseCommentWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "adapter", "Lcom/psychiatrygarden/adapter/CourseCommentAdapter;", "dataType", "titleText", "", com.umeng.socialize.tracker.a.f23806c, "", "data", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "initParams", "item", "loadCommentData", "goodsId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DetailCourseCommentWidget extends LinearLayout implements BaseContentWidget {

    @NotNull
    private final CourseCommentAdapter adapter;
    private int dataType;

    @NotNull
    private String titleText;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseCommentWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseCommentWidget(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        View.inflate(context, R.layout.layout_course_detail_comment, this);
        this.titleText = "评论列表";
        this.adapter = new CourseCommentAdapter();
        this.dataType = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initParams$lambda$0(DetailCourseCommentWidget this$0, GoodsDetailItem item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        this$0.getContext().startActivity(new Intent(this$0.getContext(), (Class<?>) ActCourseCommentList.class).putExtra("goods_id", item.getGoodsId()).putExtra("goodsComment", this$0.dataType == 2));
    }

    private final void loadCommentData(String goodsId) {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.dataType == 2) {
            ajaxParams.put("obj_id", goodsId);
            ajaxParams.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
            ajaxParams.put("comment_type", "1");
            ajaxParams.put(DatabaseManager.SIZE, "5");
        } else {
            ajaxParams.put("course_id", goodsId);
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
            ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        }
        final TextView textView = (TextView) findViewById(R.id.tv_title);
        textView.setText(this.titleText);
        YJYHttpUtils.post(getContext(), this.dataType == 2 ? NetworkRequestsURL.mCommentList : NetworkRequestsURL.courseCommentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DetailCourseCommentWidget.loadCommentData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            /* JADX WARN: Removed duplicated region for block: B:18:0x0070  */
            @Override // net.tsz.afinal.http.AjaxCallBack
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(@org.jetbrains.annotations.NotNull java.lang.String r9) throws java.lang.NumberFormatException {
                /*
                    Method dump skipped, instructions count: 323
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DetailCourseCommentWidget.AnonymousClass1.onSuccess(java.lang.String):void");
            }
        });
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getCourseData().getDataType() == 2) {
            this.titleText = "评价列表";
        }
        this.dataType = data.getCourseData().getDataType();
        initParams(data);
    }

    public final void initParams(@NotNull final GoodsDetailItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        findViewById(R.id.tv_all_comment).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.z5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailCourseCommentWidget.initParams$lambda$0(this.f17144c, item, view);
            }
        });
        String goodsId = item.getGoodsId();
        Intrinsics.checkNotNullExpressionValue(goodsId, "item.goodsId");
        loadCommentData(goodsId);
    }

    public /* synthetic */ DetailCourseCommentWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
