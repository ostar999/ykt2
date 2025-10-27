package com.psychiatrygarden.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import com.psychiatrygarden.fragmenthome.CourseOrGoodsCommentFragment;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/activity/ActCourseCommentList;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Lcom/scwang/smartrefresh/layout/listener/OnRefreshLoadMoreListener;", "()V", "goodsComment", "", "goodsId", "", "showCount", "", "showLongPicTag", "init", "", "onEventMainThread", "str", "onLoadMore", "refreshLayout", "Lcom/scwang/smartrefresh/layout/api/RefreshLayout;", "onRefresh", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ActCourseCommentList extends BaseActivity implements OnRefreshLoadMoreListener {
    private boolean goodsComment;

    @NotNull
    private String goodsId = "";
    private int showCount = -1;
    private boolean showLongPicTag;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(ActCourseCommentList this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("goods_id");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.goodsId = stringExtra;
        this.goodsComment = getIntent().getBooleanExtra("goodsComment", false);
        this.showLongPicTag = getIntent().getBooleanExtra("show_long_pic_tag", false);
        this.showCount = getIntent().getIntExtra("showCount", -1);
        CourseOrGoodsCommentFragment.Companion companion = CourseOrGoodsCommentFragment.INSTANCE;
        Bundle bundle = new Bundle();
        bundle.putBoolean("goodsComment", this.goodsComment);
        bundle.putInt("showCount", this.showCount);
        bundle.putBoolean("show_long_pic_tag", this.showLongPicTag);
        bundle.putString("goods_id", this.goodsId);
        CourseOrGoodsCommentFragment courseOrGoodsCommentFragmentNewInstance = companion.newInstance(bundle);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        if (this.goodsComment) {
            textView.setText("商品评价");
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, courseOrGoodsCommentFragmentNewInstance).commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_course_comment_list);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActCourseCommentList.setListenerForWidget$lambda$1(this.f12459c, view);
            }
        });
        if (SkinManager.getCurrentSkinType(this) == 1) {
            ((ImageView) findViewById(R.id.iv_back)).setImageTintList(ColorStateList.valueOf(getColor(R.color.color_7380a9)));
        }
    }
}
