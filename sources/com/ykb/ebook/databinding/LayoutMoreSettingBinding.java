package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class LayoutMoreSettingBinding implements ViewBinding {

    @NonNull
    public final ImageView closeMoreIvTime;

    @NonNull
    public final TextView closeTimeTv;

    @NonNull
    public final LinearLayout contentBotLl;

    @NonNull
    public final LinearLayout contentTopLl;

    @NonNull
    public final AppCompatImageView imgBack;

    @NonNull
    public final ImageView ivArrow;

    @NonNull
    public final RelativeLayout lyCloseTime;

    @NonNull
    public final RelativeLayout lyResetTime;

    @NonNull
    public final ImageView restMoreIvTime;

    @NonNull
    public final TextView restTimeTv;

    @NonNull
    public final RelativeLayout rlFlipPage;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView settingBatteryTv;

    @NonNull
    public final View settingLine1;

    @NonNull
    public final View settingLine2;

    @NonNull
    public final View settingLine3;

    @NonNull
    public final View settingLine4;

    @NonNull
    public final View settingLine5;

    @NonNull
    public final TextView showStatusBarTv;

    @NonNull
    public final Switch swShowBattery;

    @NonNull
    public final Switch swShowBookNote;

    @NonNull
    public final Switch swShowParagraphComment;

    @NonNull
    public final Switch swStatusBar;

    @NonNull
    public final Switch swVoicePage;

    @NonNull
    public final FrameLayout titleBar;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final TextView tvPageSet;

    @NonNull
    public final TextView tvRest;

    @NonNull
    public final TextView tvTime;

    @NonNull
    public final TextView voiceClickTv;

    private LayoutMoreSettingBinding(@NonNull LinearLayout linearLayout, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull AppCompatImageView appCompatImageView, @NonNull ImageView imageView2, @NonNull RelativeLayout relativeLayout, @NonNull RelativeLayout relativeLayout2, @NonNull ImageView imageView3, @NonNull TextView textView2, @NonNull RelativeLayout relativeLayout3, @NonNull TextView textView3, @NonNull View view, @NonNull View view2, @NonNull View view3, @NonNull View view4, @NonNull View view5, @NonNull TextView textView4, @NonNull Switch r22, @NonNull Switch r23, @NonNull Switch r24, @NonNull Switch r25, @NonNull Switch r26, @NonNull FrameLayout frameLayout, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9) {
        this.rootView = linearLayout;
        this.closeMoreIvTime = imageView;
        this.closeTimeTv = textView;
        this.contentBotLl = linearLayout2;
        this.contentTopLl = linearLayout3;
        this.imgBack = appCompatImageView;
        this.ivArrow = imageView2;
        this.lyCloseTime = relativeLayout;
        this.lyResetTime = relativeLayout2;
        this.restMoreIvTime = imageView3;
        this.restTimeTv = textView2;
        this.rlFlipPage = relativeLayout3;
        this.settingBatteryTv = textView3;
        this.settingLine1 = view;
        this.settingLine2 = view2;
        this.settingLine3 = view3;
        this.settingLine4 = view4;
        this.settingLine5 = view5;
        this.showStatusBarTv = textView4;
        this.swShowBattery = r22;
        this.swShowBookNote = r23;
        this.swShowParagraphComment = r24;
        this.swStatusBar = r25;
        this.swVoicePage = r26;
        this.titleBar = frameLayout;
        this.titleTv = textView5;
        this.tvPageSet = textView6;
        this.tvRest = textView7;
        this.tvTime = textView8;
        this.voiceClickTv = textView9;
    }

    @NonNull
    public static LayoutMoreSettingBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        View viewFindChildViewById4;
        View viewFindChildViewById5;
        int i2 = R.id.closeMoreIvTime;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.closeTimeTv;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.contentBotLl;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                if (linearLayout != null) {
                    i2 = R.id.contentTopLl;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout2 != null) {
                        i2 = R.id.img_back;
                        AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                        if (appCompatImageView != null) {
                            i2 = R.id.iv_arrow;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView2 != null) {
                                i2 = R.id.ly_close_time;
                                RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                if (relativeLayout != null) {
                                    i2 = R.id.ly_reset_time;
                                    RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                    if (relativeLayout2 != null) {
                                        i2 = R.id.restMoreIvTime;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView3 != null) {
                                            i2 = R.id.restTimeTv;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView2 != null) {
                                                i2 = R.id.rl_flip_page;
                                                RelativeLayout relativeLayout3 = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                                if (relativeLayout3 != null) {
                                                    i2 = R.id.settingBatteryTv;
                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView3 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.settingLine1))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.settingLine2))) != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.settingLine3))) != null && (viewFindChildViewById4 = ViewBindings.findChildViewById(view, (i2 = R.id.settingLine4))) != null && (viewFindChildViewById5 = ViewBindings.findChildViewById(view, (i2 = R.id.settingLine5))) != null) {
                                                        i2 = R.id.showStatusBarTv;
                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView4 != null) {
                                                            i2 = R.id.sw_show_battery;
                                                            Switch r23 = (Switch) ViewBindings.findChildViewById(view, i2);
                                                            if (r23 != null) {
                                                                i2 = R.id.sw_show_book_note;
                                                                Switch r24 = (Switch) ViewBindings.findChildViewById(view, i2);
                                                                if (r24 != null) {
                                                                    i2 = R.id.sw_show_paragraph_comment;
                                                                    Switch r25 = (Switch) ViewBindings.findChildViewById(view, i2);
                                                                    if (r25 != null) {
                                                                        i2 = R.id.sw_status_bar;
                                                                        Switch r26 = (Switch) ViewBindings.findChildViewById(view, i2);
                                                                        if (r26 != null) {
                                                                            i2 = R.id.sw_voice_page;
                                                                            Switch r27 = (Switch) ViewBindings.findChildViewById(view, i2);
                                                                            if (r27 != null) {
                                                                                i2 = R.id.titleBar;
                                                                                FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                                if (frameLayout != null) {
                                                                                    i2 = R.id.titleTv;
                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView5 != null) {
                                                                                        i2 = R.id.tv_page_set;
                                                                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView6 != null) {
                                                                                            i2 = R.id.tv_rest;
                                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (textView7 != null) {
                                                                                                i2 = R.id.tv_time;
                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (textView8 != null) {
                                                                                                    i2 = R.id.voiceClickTv;
                                                                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (textView9 != null) {
                                                                                                        return new LayoutMoreSettingBinding((LinearLayout) view, imageView, textView, linearLayout, linearLayout2, appCompatImageView, imageView2, relativeLayout, relativeLayout2, imageView3, textView2, relativeLayout3, textView3, viewFindChildViewById, viewFindChildViewById2, viewFindChildViewById3, viewFindChildViewById4, viewFindChildViewById5, textView4, r23, r24, r25, r26, r27, frameLayout, textView5, textView6, textView7, textView8, textView9);
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
    public static LayoutMoreSettingBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutMoreSettingBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_more_setting, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
