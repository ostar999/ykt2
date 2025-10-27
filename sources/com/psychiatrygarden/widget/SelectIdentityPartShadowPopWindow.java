package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SelectIdentityPartShadowPopWindow extends PartShadowPopupView {
    public SelectPartDow SelectPartDow;
    private BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> adapter;
    public List<SelectIdentityBean.DataBean> data;
    public String selectedText;

    /* renamed from: com.psychiatrygarden.widget.SelectIdentityPartShadowPopWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> {
        final /* synthetic */ int val$normalTextColor;
        final /* synthetic */ int val$selectTextColor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int layoutResId, List data, final int val$selectTextColor, final int val$normalTextColor) {
            super(layoutResId, data);
            this.val$selectTextColor = val$selectTextColor;
            this.val$normalTextColor = val$normalTextColor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(SelectIdentityBean.DataBean dataBean, View view) {
            SelectIdentityPartShadowPopWindow.this.SelectPartDow.mSelectPartDowe(dataBean.getTitle(), dataBean.getIdentity_id(), dataBean.getChildren());
            SelectIdentityPartShadowPopWindow.this.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final SelectIdentityBean.DataBean dataBean) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.text);
            RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relview);
            textView.setText(dataBean.getTitle());
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
            int pxByDp = ScreenUtil.getPxByDp(getContext(), 16);
            marginLayoutParams.topMargin = pxByDp;
            marginLayoutParams.leftMargin = pxByDp;
            marginLayoutParams.rightMargin = pxByDp;
            if (SelectIdentityPartShadowPopWindow.this.adapter.getItemPosition(dataBean) == SelectIdentityPartShadowPopWindow.this.adapter.getData().size() - 1) {
                marginLayoutParams.bottomMargin = pxByDp;
            } else {
                marginLayoutParams.bottomMargin = 0;
            }
            relativeLayout.setLayoutParams(marginLayoutParams);
            textView.setCompoundDrawables(null, null, null, null);
            textView.setBackgroundResource(R.drawable.shape_identity_gray);
            textView.setGravity(17);
            if (dataBean.getTitle().equals(SelectIdentityPartShadowPopWindow.this.selectedText)) {
                textView.setTextColor(this.val$selectTextColor);
                textView.setSelected(true);
            } else {
                textView.setTextColor(this.val$normalTextColor);
                textView.setSelected(false);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.vg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f17004c.lambda$convert$0(dataBean, view);
                }
            });
        }
    }

    public interface SelectPartDow {
        void dismissToListen();

        void mSelectPartDowe(String title, String ident_id, List<SelectIdentityBean.DataBean> data);
    }

    public SelectIdentityPartShadowPopWindow(@NonNull Context context, String selectedText, List<SelectIdentityBean.DataBean> data, SelectPartDow SelectPartDow2) {
        super(context);
        new ArrayList();
        this.selectedText = selectedText;
        this.data = data;
        this.SelectPartDow = SelectPartDow2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.SelectPartDow.dismissToListen();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_select_list;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.app_theme_red, R.attr.first_text_color});
        int color = typedArrayObtainStyledAttributes.getColor(0, getContext().getColor(R.color.app_theme_red));
        int color2 = typedArrayObtainStyledAttributes.getColor(1, getContext().getColor(R.color.first_text_color));
        typedArrayObtainStyledAttributes.recycle();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_select_identity_item, this.data, color, color2);
        this.adapter = anonymousClass1;
        recyclerView.setAdapter(anonymousClass1);
    }
}
