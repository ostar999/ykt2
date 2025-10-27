package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class CircleInfoMorePopWindow extends BottomPopupView implements OnItemClickListener {
    private onDialogShareClickListener clickListener;
    private List<String> contentList;
    private Context context;
    private VerticalRecyclerView recyclerView;

    /* renamed from: com.psychiatrygarden.widget.CircleInfoMorePopWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<String, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, View view) {
            CircleInfoMorePopWindow.this.clickListener.onclickIntBack(i2);
            CircleInfoMorePopWindow.this.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, String value) {
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_reply_layout);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_text);
            baseViewHolder.getView(R.id.line).setBackground(SkinManager.getCurrentSkinType(CircleInfoMorePopWindow.this.context) == 0 ? new ColorDrawable(Color.parseColor("#E7E7E7")) : new ColorDrawable(Color.parseColor("#2E3241")));
            textView.setText(value);
            final int layoutPosition = baseViewHolder.getLayoutPosition();
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.q2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16811c.lambda$convert$0(layoutPosition, view);
                }
            });
        }
    }

    public CircleInfoMorePopWindow(Context context, List<String> contentList, onDialogShareClickListener clickListener) {
        super(context);
        this.context = context;
        this.contentList = contentList;
        this.clickListener = clickListener;
    }

    private void initRv() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(new AnonymousClass1(R.layout.dialog_admin_tiezi, this.contentList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.circle_info_more_popuwindow;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_top_indicator);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && (drawable instanceof GradientDrawable)) {
            ((GradientDrawable) drawable).setColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#2E3241" : "#CBCBCB"));
            imageView.setImageDrawable(drawable);
        }
        VerticalRecyclerView verticalRecyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = verticalRecyclerView;
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initRv();
        findViewById(R.id.tv_comment_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.p2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16779c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }
}
