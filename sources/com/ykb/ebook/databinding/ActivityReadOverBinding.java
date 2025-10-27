package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.RoundCornerProgressBar;

/* loaded from: classes6.dex */
public final class ActivityReadOverBinding implements ViewBinding {

    @NonNull
    public final RoundCornerProgressBar highCommentProgress;

    @NonNull
    public final TextView highCommentProgressStr;

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final ImageView ivBg;

    @NonNull
    public final ImageView ivBook;

    @NonNull
    public final View lineLeft;

    @NonNull
    public final View lineRight;

    @NonNull
    public final FrameLayout llEndPage;

    @NonNull
    public final LinearLayout llPj;

    @NonNull
    public final RLinearLayout llReadDay;

    @NonNull
    public final RLinearLayout llReadTime;

    @NonNull
    public final RoundCornerProgressBar lowCommentProgress;

    @NonNull
    public final TextView lowCommentProgressStr;

    @NonNull
    public final RoundCornerProgressBar middleCommentProgress;

    @NonNull
    public final TextView middleCommentProgressStr;

    @NonNull
    public final RTextView rbHigh;

    @NonNull
    public final RTextView rbLow;

    @NonNull
    public final RTextView rbMiddle;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final View scoreLine;

    @NonNull
    public final TextView tvCommentNum;

    @NonNull
    public final TextView tvCommentStr;

    @NonNull
    public final TextView tvDayStr;

    @NonNull
    public final TextView tvDays;

    @NonNull
    public final RTextView tvExitRead;

    @NonNull
    public final TextView tvHour;

    @NonNull
    public final TextView tvHourStr;

    @NonNull
    public final TextView tvMinute;

    @NonNull
    public final TextView tvMinuteStr;

    @NonNull
    public final TextView tvNextTips;

    @NonNull
    public final TextView tvReadDate;

    @NonNull
    public final TextView tvReadTimeStr;

    @NonNull
    public final TextView tvReadTimeStrDay;

    @NonNull
    public final TextView tvRowDays;

    @NonNull
    public final TextView tvScore;

    @NonNull
    public final TextView tvScoreStr;

    @NonNull
    public final TextView tvTips;

    private ActivityReadOverBinding(@NonNull FrameLayout frameLayout, @NonNull RoundCornerProgressBar roundCornerProgressBar, @NonNull TextView textView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull View view, @NonNull View view2, @NonNull FrameLayout frameLayout2, @NonNull LinearLayout linearLayout, @NonNull RLinearLayout rLinearLayout, @NonNull RLinearLayout rLinearLayout2, @NonNull RoundCornerProgressBar roundCornerProgressBar2, @NonNull TextView textView2, @NonNull RoundCornerProgressBar roundCornerProgressBar3, @NonNull TextView textView3, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull View view3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull RTextView rTextView4, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull TextView textView11, @NonNull TextView textView12, @NonNull TextView textView13, @NonNull TextView textView14, @NonNull TextView textView15, @NonNull TextView textView16, @NonNull TextView textView17, @NonNull TextView textView18, @NonNull TextView textView19) {
        this.rootView = frameLayout;
        this.highCommentProgress = roundCornerProgressBar;
        this.highCommentProgressStr = textView;
        this.imgBack = imageView;
        this.ivBg = imageView2;
        this.ivBook = imageView3;
        this.lineLeft = view;
        this.lineRight = view2;
        this.llEndPage = frameLayout2;
        this.llPj = linearLayout;
        this.llReadDay = rLinearLayout;
        this.llReadTime = rLinearLayout2;
        this.lowCommentProgress = roundCornerProgressBar2;
        this.lowCommentProgressStr = textView2;
        this.middleCommentProgress = roundCornerProgressBar3;
        this.middleCommentProgressStr = textView3;
        this.rbHigh = rTextView;
        this.rbLow = rTextView2;
        this.rbMiddle = rTextView3;
        this.scoreLine = view3;
        this.tvCommentNum = textView4;
        this.tvCommentStr = textView5;
        this.tvDayStr = textView6;
        this.tvDays = textView7;
        this.tvExitRead = rTextView4;
        this.tvHour = textView8;
        this.tvHourStr = textView9;
        this.tvMinute = textView10;
        this.tvMinuteStr = textView11;
        this.tvNextTips = textView12;
        this.tvReadDate = textView13;
        this.tvReadTimeStr = textView14;
        this.tvReadTimeStrDay = textView15;
        this.tvRowDays = textView16;
        this.tvScore = textView17;
        this.tvScoreStr = textView18;
        this.tvTips = textView19;
    }

    @NonNull
    public static ActivityReadOverBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        int i2 = R.id.high_comment_progress;
        RoundCornerProgressBar roundCornerProgressBar = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
        if (roundCornerProgressBar != null) {
            i2 = R.id.high_comment_progress_str;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView != null) {
                i2 = R.id.img_back;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.iv_bg;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.iv_book;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView3 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line_left))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.line_right))) != null) {
                            FrameLayout frameLayout = (FrameLayout) view;
                            i2 = R.id.ll_pj;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout != null) {
                                i2 = R.id.ll_read_day;
                                RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (rLinearLayout != null) {
                                    i2 = R.id.ll_read_time;
                                    RLinearLayout rLinearLayout2 = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                    if (rLinearLayout2 != null) {
                                        i2 = R.id.low_comment_progress;
                                        RoundCornerProgressBar roundCornerProgressBar2 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                        if (roundCornerProgressBar2 != null) {
                                            i2 = R.id.low_comment_progress_str;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView2 != null) {
                                                i2 = R.id.middle_comment_progress;
                                                RoundCornerProgressBar roundCornerProgressBar3 = (RoundCornerProgressBar) ViewBindings.findChildViewById(view, i2);
                                                if (roundCornerProgressBar3 != null) {
                                                    i2 = R.id.middle_comment_progress_str;
                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView3 != null) {
                                                        i2 = R.id.rb_high;
                                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView != null) {
                                                            i2 = R.id.rb_low;
                                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView2 != null) {
                                                                i2 = R.id.rb_middle;
                                                                RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                if (rTextView3 != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.score_line))) != null) {
                                                                    i2 = R.id.tv_comment_num;
                                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView4 != null) {
                                                                        i2 = R.id.tv_comment_str;
                                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView5 != null) {
                                                                            i2 = R.id.tv_day_str;
                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView6 != null) {
                                                                                i2 = R.id.tv_days;
                                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView7 != null) {
                                                                                    i2 = R.id.tv_exit_read;
                                                                                    RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (rTextView4 != null) {
                                                                                        i2 = R.id.tv_hour;
                                                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView8 != null) {
                                                                                            i2 = R.id.tv_hour_str;
                                                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (textView9 != null) {
                                                                                                i2 = R.id.tv_minute;
                                                                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (textView10 != null) {
                                                                                                    i2 = R.id.tv_minute_str;
                                                                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (textView11 != null) {
                                                                                                        i2 = R.id.tv_next_tips;
                                                                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (textView12 != null) {
                                                                                                            i2 = R.id.tv_read_date;
                                                                                                            TextView textView13 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (textView13 != null) {
                                                                                                                i2 = R.id.tv_read_time_str;
                                                                                                                TextView textView14 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (textView14 != null) {
                                                                                                                    i2 = R.id.tv_read_time_str_day;
                                                                                                                    TextView textView15 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (textView15 != null) {
                                                                                                                        i2 = R.id.tv_row_days;
                                                                                                                        TextView textView16 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                        if (textView16 != null) {
                                                                                                                            i2 = R.id.tv_score;
                                                                                                                            TextView textView17 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                            if (textView17 != null) {
                                                                                                                                i2 = R.id.tv_score_str;
                                                                                                                                TextView textView18 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                if (textView18 != null) {
                                                                                                                                    i2 = R.id.tv_tips;
                                                                                                                                    TextView textView19 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                    if (textView19 != null) {
                                                                                                                                        return new ActivityReadOverBinding(frameLayout, roundCornerProgressBar, textView, imageView, imageView2, imageView3, viewFindChildViewById, viewFindChildViewById2, frameLayout, linearLayout, rLinearLayout, rLinearLayout2, roundCornerProgressBar2, textView2, roundCornerProgressBar3, textView3, rTextView, rTextView2, rTextView3, viewFindChildViewById3, textView4, textView5, textView6, textView7, rTextView4, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19);
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
    public static ActivityReadOverBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ActivityReadOverBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_read_over, viewGroup, false);
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
