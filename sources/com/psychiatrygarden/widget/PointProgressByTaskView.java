package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.ProjectApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PointProgressByTaskView extends LinearLayout {
    private LinearLayout mLyAll;
    private LinearLayout mLyFive;
    private LinearLayout mLyFour;
    private LinearLayout mLyMine;
    private LinearLayout mLyOne;
    private LinearLayout mLyThree;
    private LinearLayout mLyTwo;
    private LinearLayout mLyView;
    private TextView mTvPercent;
    private TextView mTvRightPercent;
    private TextView mTvValueFive;
    private TextView mTvValueFour;
    private TextView mTvValueOne;
    private TextView mTvValueThree;
    private TextView mTvValueTwo;
    private int progress;
    private int progressTask;

    public PointProgressByTaskView(Context context) {
        super(context);
        this.progress = 0;
        this.progressTask = 72;
        initView();
    }

    private void initView() {
        int iDip2px;
        int i2;
        int iDip2px2;
        int i3;
        int iDip2px3;
        int i4;
        int i5;
        int iDip2px4;
        int i6;
        int iDip2px5;
        int i7;
        int iDip2px6;
        int i8;
        int i9;
        LayoutInflater.from(getContext()).inflate(R.layout.view_point_progress_by_task, this);
        this.mLyView = (LinearLayout) findViewById(R.id.ly_view);
        this.mTvValueOne = (TextView) findViewById(R.id.tv_value_one);
        this.mTvValueTwo = (TextView) findViewById(R.id.tv_value_two);
        this.mTvValueThree = (TextView) findViewById(R.id.tv_value_three);
        this.mTvValueFour = (TextView) findViewById(R.id.tv_value_four);
        this.mTvValueFive = (TextView) findViewById(R.id.tv_value_five);
        this.mLyMine = (LinearLayout) findViewById(R.id.ly_mine);
        this.mLyAll = (LinearLayout) findViewById(R.id.ly_all_task);
        this.mLyOne = (LinearLayout) findViewById(R.id.ly_one);
        this.mLyTwo = (LinearLayout) findViewById(R.id.ly_two);
        this.mLyThree = (LinearLayout) findViewById(R.id.ly_three);
        this.mLyFour = (LinearLayout) findViewById(R.id.ly_four);
        this.mLyFive = (LinearLayout) findViewById(R.id.ly_five);
        this.mTvRightPercent = (TextView) findViewById(R.id.tv_right_percent);
        this.mTvPercent = (TextView) findViewById(R.id.tv_percent);
        View viewFindViewById = findViewById(R.id.mine_dot);
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 52.0d)) / 5;
        int iDip2px7 = UIUtil.dip2px(getContext(), 5.0d);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyOne.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.setMarginEnd(iDip2px7);
        this.mLyOne.setLayoutParams(layoutParams);
        this.mLyTwo.setLayoutParams(layoutParams);
        this.mLyThree.setLayoutParams(layoutParams);
        this.mLyFour.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLyFive.getLayoutParams();
        layoutParams2.width = screenWidth;
        layoutParams2.setMarginEnd(0);
        this.mLyFive.setLayoutParams(layoutParams2);
        this.mTvValueOne.setText("<50%");
        this.mTvValueTwo.setText("50%-60%");
        this.mTvValueThree.setText("60%-70%");
        this.mTvValueFour.setText("70%-80%");
        this.mTvValueFive.setText("≥80%");
        int iDip2px8 = UIUtil.dip2px(getContext(), 17.0d);
        int iDip2px9 = UIUtil.dip2px(getContext(), 16.0d);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mLyView.getLayoutParams();
        layoutParams3.topMargin = iDip2px8;
        layoutParams3.setMargins(iDip2px9, iDip2px8, iDip2px9, 0);
        this.mLyView.setLayoutParams(layoutParams3);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ProjectApp.instance().getTopActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float f2 = displayMetrics.densityDpi;
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
        if (f2 == 420.0d) {
            layoutParams4.topMargin = UIUtil.dip2px(getContext(), 2.0d);
        } else {
            layoutParams4.topMargin = UIUtil.dip2px(getContext(), 3.5d);
        }
        viewFindViewById.setLayoutParams(layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.mLyMine.getLayoutParams();
        int i10 = this.progress;
        if (i10 < 50) {
            int i11 = (int) ((screenWidth / 49.0d) * (49 - (49 - i10)));
            int i12 = layoutParams5.width;
            if (i11 - (i12 / 2) < 0) {
                iDip2px = i11 + iDip2px9;
            } else {
                i5 = (i11 - (i12 / 2)) + (iDip2px9 * 2);
                iDip2px4 = UIUtil.dip2px(getContext(), 4.0d);
                iDip2px = i5 - iDip2px4;
            }
        } else if (i10 < 60) {
            int i13 = ((int) (screenWidth / 9.0d)) * (9 - (59 - i10));
            if (i13 == 0) {
                iDip2px = (((screenWidth + iDip2px7) + i13) - (layoutParams5.width / 2)) + (iDip2px9 * 2) + UIUtil.dip2px(getContext(), 4.0d);
            } else {
                i5 = (((screenWidth + iDip2px7) + i13) - (layoutParams5.width / 2)) + (iDip2px9 * 2);
                iDip2px4 = UIUtil.dip2px(getContext(), 4.0d);
                iDip2px = i5 - iDip2px4;
            }
        } else {
            if (i10 < 70) {
                int i14 = (int) ((screenWidth / 9.0d) * (9 - (69 - i10)));
                if (i14 == 0) {
                    i3 = ((((screenWidth * 2) + (iDip2px7 * 2)) + i14) - (layoutParams5.width / 2)) + (iDip2px9 * 2);
                    iDip2px3 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i3 + iDip2px3;
                } else {
                    i2 = ((((screenWidth * 2) + (iDip2px7 * 2)) + i14) - (layoutParams5.width / 2)) + (iDip2px9 * 2);
                    iDip2px2 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i2 - iDip2px2;
                }
            } else if (i10 < 80) {
                int i15 = (int) ((screenWidth / 9.0d) * (9 - (79 - i10)));
                if (i15 == 0) {
                    i3 = ((((screenWidth * 3) + (iDip2px7 * 3)) + i15) - (layoutParams5.width / 2)) + (iDip2px9 * 2);
                    iDip2px3 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i3 + iDip2px3;
                } else {
                    i2 = ((((screenWidth * 3) + (iDip2px7 * 3)) + i15) - (layoutParams5.width / 2)) + (iDip2px9 * 2);
                    iDip2px2 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i2 - iDip2px2;
                }
            } else {
                iDip2px = (((((screenWidth * 4) + (iDip2px7 * 4)) + ((int) ((screenWidth / 20.0d) * (20 - (100 - i10))))) - (layoutParams5.width / 2)) + (iDip2px9 * 2)) - UIUtil.dip2px(getContext(), 4.0d);
            }
            iDip2px = i4;
        }
        layoutParams5.setMarginStart(iDip2px);
        this.mLyMine.setLayoutParams(layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.mLyAll.getLayoutParams();
        layoutParams6.topMargin = UIUtil.dip2px(getContext(), 16.0d);
        int i16 = this.progressTask;
        if (i16 < 50) {
            i9 = (int) ((screenWidth / 49.0d) * (49 - (49 - i16)));
            int i17 = layoutParams6.width;
            if (i9 - (i17 / 2) >= 0) {
                i6 = (i9 - (i17 / 2)) + (iDip2px9 * 2);
                iDip2px5 = UIUtil.dip2px(getContext(), 4.0d);
                i8 = i6 - iDip2px5;
            }
            i8 = i9 + iDip2px9;
        } else {
            if (i16 < 60) {
                int i18 = (int) ((screenWidth / 9.0d) * (9 - (59 - i16)));
                if (i18 == 0) {
                    i9 = ((screenWidth + iDip2px7) + i18) - (layoutParams6.width / 2);
                    iDip2px9 *= 2;
                    i8 = i9 + iDip2px9;
                } else {
                    i6 = (((screenWidth + iDip2px7) + i18) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px5 = UIUtil.dip2px(getContext(), 4.0d);
                }
            } else if (i16 < 70) {
                int i19 = (int) ((screenWidth / 9.0d) * (9 - (69 - i16)));
                if (i19 == 0) {
                    i7 = ((((screenWidth * 2) + (iDip2px7 * 2)) + i19) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i7 + iDip2px6;
                } else {
                    i6 = ((((screenWidth * 2) + (iDip2px7 * 2)) + i19) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px5 = UIUtil.dip2px(getContext(), 4.0d);
                }
            } else if (i16 < 80) {
                int i20 = (int) ((screenWidth / 9.0d) * (9 - (79 - i16)));
                if (i20 == 0) {
                    i7 = ((((screenWidth * 3) + (iDip2px7 * 3)) + i20) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i7 + iDip2px6;
                } else {
                    i6 = ((((screenWidth * 3) + (iDip2px7 * 3)) + i20) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px5 = UIUtil.dip2px(getContext(), 4.0d);
                }
            } else {
                int i21 = (int) ((screenWidth / 20.0d) * (20 - (100 - i16)));
                if (i21 == 0) {
                    i7 = ((((screenWidth * 4) + (iDip2px7 * 4)) + i21) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i7 + iDip2px6;
                } else {
                    i6 = ((((screenWidth * 4) + (iDip2px7 * 4)) + i21) - (layoutParams6.width / 2)) + (iDip2px9 * 2);
                    iDip2px5 = UIUtil.dip2px(getContext(), 4.0d);
                }
            }
            i8 = i6 - iDip2px5;
        }
        layoutParams6.setMarginStart(i8);
        this.mLyAll.setLayoutParams(layoutParams6);
    }

    public void setData(int progress, int progressTask, String personPercent, String peoplePercent) {
        int iDip2px;
        int i2;
        int iDip2px2;
        int i3;
        int iDip2px3;
        int i4;
        int i5;
        int iDip2px4;
        int iDip2px5;
        int i6;
        int iDip2px6;
        int i7;
        int iDip2px7;
        int i8;
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 64.0d)) / 5;
        int iDip2px8 = UIUtil.dip2px(getContext(), 8.0d);
        int iDip2px9 = UIUtil.dip2px(getContext(), 8.0d);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLyMine.getLayoutParams();
        if (progress < 50) {
            int i9 = (int) ((screenWidth / 49.0d) * (49 - (49 - progress)));
            int i10 = layoutParams.width;
            if (i9 - (i10 / 2) < 0) {
                iDip2px = i9 + iDip2px9;
            } else {
                i5 = (i9 - (i10 / 2)) + (iDip2px9 * 2);
                iDip2px4 = UIUtil.dip2px(getContext(), 4.0d);
                iDip2px = i5 - iDip2px4;
            }
        } else if (progress < 60) {
            int i11 = ((int) (screenWidth / 9.0d)) * (9 - (59 - progress));
            if (i11 == 0) {
                iDip2px = (((screenWidth + iDip2px8) + i11) - (layoutParams.width / 2)) + (iDip2px9 * 2) + UIUtil.dip2px(getContext(), 4.0d);
            } else {
                i5 = (((screenWidth + iDip2px8) + i11) - (layoutParams.width / 2)) + (iDip2px9 * 2);
                iDip2px4 = UIUtil.dip2px(getContext(), 4.0d);
                iDip2px = i5 - iDip2px4;
            }
        } else {
            if (progress < 70) {
                int i12 = (int) ((screenWidth / 9.0d) * (9 - (69 - progress)));
                if (i12 == 0) {
                    i3 = ((((screenWidth * 2) + (iDip2px8 * 2)) + i12) - (layoutParams.width / 2)) + (iDip2px9 * 2);
                    iDip2px3 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i3 + iDip2px3;
                } else {
                    i2 = ((((screenWidth * 2) + (iDip2px8 * 2)) + i12) - (layoutParams.width / 2)) + (iDip2px9 * 2);
                    iDip2px2 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i2 - iDip2px2;
                }
            } else if (progress < 80) {
                int i13 = (int) ((screenWidth / 9.0d) * (9 - (79 - progress)));
                if (i13 == 0) {
                    i3 = ((((screenWidth * 3) + (iDip2px8 * 3)) + i13) - (layoutParams.width / 2)) + (iDip2px9 * 2);
                    iDip2px3 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i3 + iDip2px3;
                } else {
                    i2 = ((((screenWidth * 3) + (iDip2px8 * 3)) + i13) - (layoutParams.width / 2)) + (iDip2px9 * 2);
                    iDip2px2 = UIUtil.dip2px(getContext(), 4.0d);
                    i4 = i2 - iDip2px2;
                }
            } else {
                iDip2px = (((((screenWidth * 4) + (iDip2px8 * 4)) + ((int) ((screenWidth / 20.0d) * (20 - (100 - progress))))) - (layoutParams.width / 2)) + (iDip2px9 * 2)) - UIUtil.dip2px(getContext(), 4.0d);
            }
            iDip2px = i4;
        }
        layoutParams.setMarginStart(iDip2px);
        this.mLyMine.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mLyAll.getLayoutParams();
        layoutParams2.topMargin = UIUtil.dip2px(getContext(), 16.0d);
        if (progressTask < 50) {
            int i14 = (int) ((screenWidth / 49.0d) * (49 - (49 - progressTask)));
            int i15 = layoutParams2.width;
            iDip2px5 = i14 - (i15 / 2) < 0 ? i14 + iDip2px9 : ((i14 - (i15 / 2)) + (iDip2px9 * 2)) - UIUtil.dip2px(getContext(), 4.0d);
        } else if (progressTask < 60) {
            int i16 = (int) ((screenWidth / 9.0d) * (9 - (59 - progressTask)));
            if (i16 == 0) {
                i8 = (((screenWidth + iDip2px8) + i16) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                iDip2px5 = i8;
            } else {
                i6 = (((screenWidth + iDip2px8) + i16) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                i8 = i6 - iDip2px6;
                iDip2px5 = i8;
            }
        } else {
            if (progressTask < 70) {
                int i17 = (int) ((screenWidth / 9.0d) * (9 - (69 - progressTask)));
                if (i17 == 0) {
                    i7 = ((((screenWidth * 2) + (iDip2px8 * 2)) + i17) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                    iDip2px7 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i7 + iDip2px7;
                } else {
                    i6 = ((((screenWidth * 2) + (iDip2px8 * 2)) + i17) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                    iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i6 - iDip2px6;
                }
            } else if (progressTask < 80) {
                int i18 = (int) ((screenWidth / 9.0d) * (9 - (79 - progressTask)));
                if (i18 == 0) {
                    i7 = ((((screenWidth * 3) + (iDip2px8 * 3)) + i18) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                    iDip2px7 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i7 + iDip2px7;
                } else {
                    i6 = ((((screenWidth * 3) + (iDip2px8 * 3)) + i18) - (layoutParams2.width / 2)) + (iDip2px9 * 2);
                    iDip2px6 = UIUtil.dip2px(getContext(), 4.0d);
                    i8 = i6 - iDip2px6;
                }
            } else {
                int i19 = (int) ((screenWidth / 20.0d) * (20 - (100 - progressTask)));
                iDip2px5 = i19 == 0 ? UIUtil.dip2px(getContext(), 4.0d) + ((((screenWidth * 4) + (iDip2px8 * 4)) + i19) - (layoutParams2.width / 2)) + (iDip2px9 * 2) : (((((screenWidth * 4) + (iDip2px8 * 4)) + i19) - (layoutParams2.width / 2)) + (iDip2px9 * 2)) - UIUtil.dip2px(getContext(), 4.0d);
            }
            iDip2px5 = i8;
        }
        layoutParams2.setMarginStart(iDip2px5);
        this.mLyAll.setLayoutParams(layoutParams2);
        this.mTvRightPercent.setText(personPercent + "%");
        this.mTvPercent.setText(peoplePercent + "%");
    }

    public PointProgressByTaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.progress = 0;
        this.progressTask = 72;
        initView();
    }

    public PointProgressByTaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.progress = 0;
        this.progressTask = 72;
        initView();
    }
}
