package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseTeacher;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0010H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseTeacherInfoWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "rvTeachers", "Landroidx/recyclerview/widget/RecyclerView;", com.umeng.socialize.tracker.a.f23806c, "", "data", "Lcom/psychiatrygarden/bean/CourseDetailBean;", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DetailCourseTeacherInfoWidget extends LinearLayout implements BaseContentWidget {

    @NotNull
    private RecyclerView rvTeachers;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseTeacherInfoWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseTeacherInfoWidget(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        View.inflate(context, R.layout.layout_detail_teacher_info, this);
        View viewFindViewById = findViewById(R.id.rvTeachers);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvTeachers)");
        this.rvTeachers = (RecyclerView) viewFindViewById;
    }

    public final void initData(@NotNull CourseDetailBean data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.rvTeachers.setAdapter(new BaseQuickAdapter<CourseTeacher, BaseViewHolder>(new ArrayList()) { // from class: com.psychiatrygarden.widget.DetailCourseTeacherInfoWidget.initData.1
            {
                super(R.layout.item_course_teacher, arrayList);
                setList(this.$data.getTeacher());
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull CourseTeacher item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                BaseViewHolder text = holder.setText(R.id.tv_name, item.getName());
                ArrayList<String> label = item.getLabel();
                text.setText(R.id.tv_description, label != null ? CollectionsKt___CollectionsKt.joinToString$default(label, "、", null, null, 0, null, null, 62, null) : null).setGone(R.id.tv_name, TextUtils.isEmpty(item.getName()));
                ArrayList<String> label2 = item.getLabel();
                if (label2 == null || label2.isEmpty()) {
                    ViewExtensionsKt.gone(holder.getView(R.id.tv_description));
                    ((TextView) holder.getView(R.id.tv_name)).setGravity(19);
                } else {
                    ViewExtensionsKt.visible(holder.getView(R.id.tv_description));
                    ((TextView) holder.getView(R.id.tv_name)).setGravity(51);
                }
                Context context = getContext();
                String avatar = item.getAvatar();
                if (avatar == null) {
                    avatar = "";
                }
                GlideUtils.loadImage(context, avatar, (ImageView) holder.getView(R.id.iv_avatar));
                int layoutPosition = holder.getLayoutPosition();
                ArrayList<CourseTeacher> teacher = this.$data.getTeacher();
                Intrinsics.checkNotNull(teacher);
                holder.setVisible(R.id.line, layoutPosition != teacher.size() - 1);
            }
        });
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        CourseDetailBean courseData = data.getCourseData();
        Intrinsics.checkNotNullExpressionValue(courseData, "data.courseData");
        initData(courseData);
    }

    public /* synthetic */ DetailCourseTeacherInfoWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
