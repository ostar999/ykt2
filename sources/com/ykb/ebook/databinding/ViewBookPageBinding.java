package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.BatteryView;
import com.ykb.ebook.weight.BatteryWidget;
import com.ykb.ebook.weight.ContentTextView;

/* loaded from: classes6.dex */
public final class ViewBookPageBinding implements ViewBinding {

    @NonNull
    public final BatteryWidget batteryInfo;

    @NonNull
    public final ContentTextView contentTextView;

    @NonNull
    public final ActivityReadOverBinding endPage;

    @NonNull
    public final ActivityBookInfoBinding firstPage;

    @NonNull
    public final FrameLayout flContent;

    @NonNull
    public final LinearLayout llBottom;

    @NonNull
    public final LinearLayout llPermission;

    @NonNull
    public final LinearLayout llTop;

    @NonNull
    public final FrameLayout pageRoot;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RecyclerView rvBottom;

    @NonNull
    public final View topEmpty;

    @NonNull
    public final BatteryView tvBattery;

    @NonNull
    public final BatteryView tvProgress;

    @NonNull
    public final BatteryView tvTime;

    @NonNull
    public final TextView tvTitle;

    @NonNull
    public final View vBg;

    @NonNull
    public final View vwBg;

    @NonNull
    public final ConstraintLayout vwRoot;

    @NonNull
    public final FrameLayout vwStatusBar;

    private ViewBookPageBinding(@NonNull FrameLayout frameLayout, @NonNull BatteryWidget batteryWidget, @NonNull ContentTextView contentTextView, @NonNull ActivityReadOverBinding activityReadOverBinding, @NonNull ActivityBookInfoBinding activityBookInfoBinding, @NonNull FrameLayout frameLayout2, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull FrameLayout frameLayout3, @NonNull RecyclerView recyclerView, @NonNull View view, @NonNull BatteryView batteryView, @NonNull BatteryView batteryView2, @NonNull BatteryView batteryView3, @NonNull TextView textView, @NonNull View view2, @NonNull View view3, @NonNull ConstraintLayout constraintLayout, @NonNull FrameLayout frameLayout4) {
        this.rootView = frameLayout;
        this.batteryInfo = batteryWidget;
        this.contentTextView = contentTextView;
        this.endPage = activityReadOverBinding;
        this.firstPage = activityBookInfoBinding;
        this.flContent = frameLayout2;
        this.llBottom = linearLayout;
        this.llPermission = linearLayout2;
        this.llTop = linearLayout3;
        this.pageRoot = frameLayout3;
        this.rvBottom = recyclerView;
        this.topEmpty = view;
        this.tvBattery = batteryView;
        this.tvProgress = batteryView2;
        this.tvTime = batteryView3;
        this.tvTitle = textView;
        this.vBg = view2;
        this.vwBg = view3;
        this.vwRoot = constraintLayout;
        this.vwStatusBar = frameLayout4;
    }

    @NonNull
    public static ViewBookPageBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        View viewFindChildViewById4;
        int i2 = R.id.battery_info;
        BatteryWidget batteryWidget = (BatteryWidget) ViewBindings.findChildViewById(view, i2);
        if (batteryWidget != null) {
            i2 = R.id.content_text_view;
            ContentTextView contentTextView = (ContentTextView) ViewBindings.findChildViewById(view, i2);
            if (contentTextView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.end_page))) != null) {
                ActivityReadOverBinding activityReadOverBindingBind = ActivityReadOverBinding.bind(viewFindChildViewById);
                i2 = R.id.first_page;
                View viewFindChildViewById5 = ViewBindings.findChildViewById(view, i2);
                if (viewFindChildViewById5 != null) {
                    ActivityBookInfoBinding activityBookInfoBindingBind = ActivityBookInfoBinding.bind(viewFindChildViewById5);
                    i2 = R.id.fl_content;
                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                    if (frameLayout != null) {
                        i2 = R.id.ll_bottom;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            i2 = R.id.ll_permission;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout2 != null) {
                                i2 = R.id.ll_top;
                                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout3 != null) {
                                    FrameLayout frameLayout2 = (FrameLayout) view;
                                    i2 = R.id.rv_bottom;
                                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                    if (recyclerView != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.top_empty))) != null) {
                                        i2 = R.id.tv_battery;
                                        BatteryView batteryView = (BatteryView) ViewBindings.findChildViewById(view, i2);
                                        if (batteryView != null) {
                                            i2 = R.id.tv_progress;
                                            BatteryView batteryView2 = (BatteryView) ViewBindings.findChildViewById(view, i2);
                                            if (batteryView2 != null) {
                                                i2 = R.id.tv_time;
                                                BatteryView batteryView3 = (BatteryView) ViewBindings.findChildViewById(view, i2);
                                                if (batteryView3 != null) {
                                                    i2 = R.id.tv_title;
                                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.v_bg))) != null && (viewFindChildViewById4 = ViewBindings.findChildViewById(view, (i2 = R.id.vw_bg))) != null) {
                                                        i2 = R.id.vw_root;
                                                        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (constraintLayout != null) {
                                                            i2 = R.id.vw_status_bar;
                                                            FrameLayout frameLayout3 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (frameLayout3 != null) {
                                                                return new ViewBookPageBinding(frameLayout2, batteryWidget, contentTextView, activityReadOverBindingBind, activityBookInfoBindingBind, frameLayout, linearLayout, linearLayout2, linearLayout3, frameLayout2, recyclerView, viewFindChildViewById2, batteryView, batteryView2, batteryView3, textView, viewFindChildViewById3, viewFindChildViewById4, constraintLayout, frameLayout3);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ViewBookPageBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ViewBookPageBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.view_book_page, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
