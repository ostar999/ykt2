package com.psychiatrygarden.activity.chooseSchool.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u000eB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\r¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/widget/ChooseAreaFilterPopUpWindow;", "Landroid/widget/PopupWindow;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "dropView", "Landroid/view/View;", "allViewHeight", "", "mList", "", "Lcom/psychiatrygarden/bean/ChooseSchoolFilterBean;", "itemChooseListener", "Lcom/psychiatrygarden/activity/chooseSchool/widget/ChooseAreaFilterPopUpWindow$ProjectChooseInterface;", "(Landroid/app/Activity;Landroid/view/View;ILjava/util/List;Lcom/psychiatrygarden/activity/chooseSchool/widget/ChooseAreaFilterPopUpWindow$ProjectChooseInterface;)V", "ProjectChooseInterface", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseAreaFilterPopUpWindow extends PopupWindow {

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u001a\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0016\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000bH&¨\u0006\f"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/widget/ChooseAreaFilterPopUpWindow$ProjectChooseInterface;", "", "mItemDismissListener", "", "mItemListener", "choosePos", "", "type", "Lcom/psychiatrygarden/bean/ChooseSchoolFilterBean;", "mSubmitListener", "mList", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface ProjectChooseInterface {
        void mItemDismissListener();

        void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type);

        void mSubmitListener(@NotNull List<ChooseSchoolFilterBean> mList);
    }

    public ChooseAreaFilterPopUpWindow(@NotNull final Activity activity, @Nullable View view, int i2, @NotNull final List<ChooseSchoolFilterBean> mList, @NotNull final ProjectChooseInterface itemChooseListener) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(mList, "mList");
        Intrinsics.checkNotNullParameter(itemChooseListener, "itemChooseListener");
        Object systemService = activity.getSystemService("layout_inflater");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
        View viewInflate = ((LayoutInflater) systemService).inflate(R.layout.layout_choose_area_pop_window, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflater.inflate(R.layou…se_area_pop_window, null)");
        GridView gridView = (GridView) viewInflate.findViewById(R.id.gridview);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tvSubmit);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tvCancel);
        activity.getWindow().setNavigationBarColor(Color.parseColor("#50000000"));
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, i2);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.a
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ChooseAreaFilterPopUpWindow._init_$lambda$0(activity, itemChooseListener);
            }
        });
        popupWindow.setAnimationStyle(0);
        popupWindow.showAsDropDown(view);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChooseAreaFilterPopUpWindow._init_$lambda$1(popupWindow, view2);
            }
        });
        final CommAdapter<ChooseSchoolFilterBean> commAdapter = new CommAdapter<ChooseSchoolFilterBean>(mList, activity) { // from class: com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow$adapter$1
            final /* synthetic */ Activity $activity;

            {
                this.$activity = activity;
            }

            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(@NotNull ViewHolder vHolder, @NotNull ChooseSchoolFilterBean dataBean, int position) {
                Intrinsics.checkNotNullParameter(vHolder, "vHolder");
                Intrinsics.checkNotNullParameter(dataBean, "dataBean");
                TextView textView3 = (TextView) vHolder.getView(R.id.tv_title);
                textView3.setText(dataBean.getTitle());
                TextPaint paint = textView3.getPaint();
                if (dataBean.getSelected()) {
                    textView3.setTextColor(SkinManager.getCurrentSkinType(this.$activity) == 0 ? this.$activity.getResources().getColor(R.color.main_theme_color) : this.$activity.getResources().getColor(R.color.main_theme_color_night));
                    textView3.setBackgroundResource(R.drawable.shape_main_color_round8);
                    paint.setFakeBoldText(true);
                } else {
                    paint.setFakeBoldText(false);
                    textView3.setTextColor(SkinManager.getCurrentSkinType(this.$activity) == 0 ? this.$activity.getResources().getColor(R.color.first_txt_color) : this.$activity.getResources().getColor(R.color.first_txt_color_night));
                    textView3.setBackgroundResource(R.drawable.shape_bg_one_round8);
                }
                textView3.invalidate();
                notifyDataSetChanged();
            }
        };
        gridView.setNumColumns(4);
        gridView.setAdapter((ListAdapter) commAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.c
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i3, long j2) {
                ChooseAreaFilterPopUpWindow._init_$lambda$2(commAdapter, adapterView, view2, i3, j2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChooseAreaFilterPopUpWindow._init_$lambda$3(mList, itemChooseListener, popupWindow, view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.widget.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChooseAreaFilterPopUpWindow._init_$lambda$4(popupWindow, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(Activity activity, ProjectChooseInterface itemChooseListener) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(itemChooseListener, "$itemChooseListener");
        if (SkinManager.getCurrentSkinType(activity) == 0) {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        itemChooseListener.mItemDismissListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(PopupWindow popupWindow, View view) {
        Intrinsics.checkNotNullParameter(popupWindow, "$popupWindow");
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$2(CommAdapter adapter, AdapterView adapterView, View view, int i2, long j2) {
        Intrinsics.checkNotNullParameter(adapter, "$adapter");
        ((ChooseSchoolFilterBean) adapter.getItem(i2)).setSelected(!r1.getSelected());
        adapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$3(List mList, ProjectChooseInterface itemChooseListener, PopupWindow popupWindow, View view) {
        Intrinsics.checkNotNullParameter(mList, "$mList");
        Intrinsics.checkNotNullParameter(itemChooseListener, "$itemChooseListener");
        Intrinsics.checkNotNullParameter(popupWindow, "$popupWindow");
        ArrayList arrayList = new ArrayList();
        int size = mList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((ChooseSchoolFilterBean) mList.get(i2)).getSelected()) {
                arrayList.add(mList.get(i2));
            }
        }
        itemChooseListener.mSubmitListener(arrayList);
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$4(PopupWindow popupWindow, View view) {
        Intrinsics.checkNotNullParameter(popupWindow, "$popupWindow");
        popupWindow.dismiss();
    }
}
