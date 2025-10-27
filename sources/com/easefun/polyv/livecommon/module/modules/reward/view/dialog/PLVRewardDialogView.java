package com.easefun.polyv.livecommon.module.modules.reward.view.dialog;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.reward.view.adapter.PLVRewardListAdapter;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.PLVBeadWidget;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVRewardDialogView {
    private PLVRewardPageAdapter adapter;
    private AppCompatActivity context;
    private boolean isLandscape;
    private boolean isShown = false;
    private PLVRewardListAdapter landscapeAdapter;
    private OnMakeRewardListener makeRewardListener;
    private int makeRewardNum;
    private PLVBeadWidget plvBeadPointReward;
    private Button plvBtnPointRewardMakeReward;
    private ImageView plvIvPointRewardClose;
    private LinearLayout plvLlRewardBottomLayout;
    private LinearLayout plvLlRewardDialogView;
    private RadioGroup plvRgPointRewardSendCount;
    private RecyclerView plvRvRewardLandscape;
    private TextView plvTvPointRewardRemainingPoint;
    private TextView plvTvPointRewardTitle;
    private RelativeLayout plvTvPointRewardTopLayout;
    private View plvViewTopTransparent;
    private ViewPager plvVpPointReward;
    private View rootView;
    private OnShowListener showListener;

    public interface OnMakeRewardListener {
        void onMakeReward(PLVBaseViewData data, int rewardNum);
    }

    public interface OnShowListener {
        void onShow();
    }

    public PLVRewardDialogView(AppCompatActivity activity, ViewGroup parent) {
        this.context = activity;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.plv_point_reward_window, parent, false);
        this.rootView = viewInflate;
        viewInflate.setFocusable(true);
        this.rootView.setFocusableInTouchMode(true);
        this.rootView.setClickable(true);
        parent.addView(this.rootView);
        this.plvTvPointRewardTopLayout = (RelativeLayout) this.rootView.findViewById(R.id.plv_tv_point_reward_top_layout);
        this.plvTvPointRewardTitle = (TextView) this.rootView.findViewById(R.id.plv_tv_point_reward_title);
        this.plvTvPointRewardRemainingPoint = (TextView) this.rootView.findViewById(R.id.plv_tv_point_reward_remaining_point);
        this.plvIvPointRewardClose = (ImageView) this.rootView.findViewById(R.id.plv_iv_point_reward_close);
        this.plvVpPointReward = (ViewPager) this.rootView.findViewById(R.id.plv_vp_point_reward);
        this.plvBeadPointReward = (PLVBeadWidget) this.rootView.findViewById(R.id.plv_bead_point_reward);
        this.plvRgPointRewardSendCount = (RadioGroup) this.rootView.findViewById(R.id.plv_rg_point_reward_send_count);
        this.plvBtnPointRewardMakeReward = (Button) this.rootView.findViewById(R.id.plv_btn_point_reward_make_reward);
        this.plvLlRewardBottomLayout = (LinearLayout) this.rootView.findViewById(R.id.plv_ll_reward_bottom);
        this.plvViewTopTransparent = this.rootView.findViewById(R.id.plv_v_top_transparent);
        this.plvLlRewardDialogView = (LinearLayout) this.rootView.findViewById(R.id.plv_ll_reward_dialog_view);
        this.plvRvRewardLandscape = (RecyclerView) this.rootView.findViewById(R.id.plv_rv_reward_landscape);
        this.plvViewTopTransparent.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) throws Resources.NotFoundException {
                PLVRewardDialogView.this.hide();
            }
        });
        this.plvIvPointRewardClose.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) throws Resources.NotFoundException {
                PLVRewardDialogView.this.hide();
            }
        });
        this.plvBtnPointRewardMakeReward.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) throws Resources.NotFoundException {
                PLVRewardDialogView.this.makeReward();
            }
        });
        this.plvRgPointRewardSendCount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.4
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.plv_rb_point_reward_reward_1) {
                    PLVRewardDialogView.this.makeRewardNum = 1;
                    return;
                }
                if (checkedId == R.id.plv_rb_point_reward_reward_5) {
                    PLVRewardDialogView.this.makeRewardNum = 5;
                    return;
                }
                if (checkedId == R.id.plv_rb_point_reward_reward_10) {
                    PLVRewardDialogView.this.makeRewardNum = 10;
                    return;
                }
                if (checkedId == R.id.plv_rb_point_reward_reward_66) {
                    PLVRewardDialogView.this.makeRewardNum = 66;
                } else if (checkedId == R.id.plv_rb_point_reward_reward_88) {
                    PLVRewardDialogView.this.makeRewardNum = 88;
                } else if (checkedId == R.id.plv_rb_point_reward_reward_666) {
                    PLVRewardDialogView.this.makeRewardNum = R2.attr.bl_selected_gradient_centerY;
                }
            }
        });
        this.rootView.setVisibility(8);
    }

    private void changeRewardNumButtonSpace(int dpSpace) {
        for (int i2 = 1; i2 < this.plvRgPointRewardSendCount.getChildCount(); i2++) {
            ((RadioGroup.LayoutParams) ((RadioButton) this.plvRgPointRewardSendCount.getChildAt(i2)).getLayoutParams()).leftMargin = ConvertUtils.dp2px(dpSpace);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeReward() throws Resources.NotFoundException {
        PLVBaseViewData selectData;
        if (this.makeRewardNum <= 0) {
            ToastUtils.showShort("请选择打赏数量");
            return;
        }
        if (this.isLandscape) {
            PLVRewardListAdapter pLVRewardListAdapter = this.landscapeAdapter;
            if (pLVRewardListAdapter == null || pLVRewardListAdapter.getSelectData() == null) {
                ToastUtils.showShort("请选择打赏道具");
                return;
            }
            selectData = this.landscapeAdapter.getSelectData();
        } else {
            PLVRewardPageAdapter pLVRewardPageAdapter = this.adapter;
            if (pLVRewardPageAdapter == null || pLVRewardPageAdapter.getSelectData() == null) {
                ToastUtils.showShort("请选择打赏道具");
                return;
            }
            selectData = this.adapter.getSelectData();
        }
        OnMakeRewardListener onMakeRewardListener = this.makeRewardListener;
        if (onMakeRewardListener != null) {
            onMakeRewardListener.onMakeReward(selectData, this.makeRewardNum);
        }
        hide();
    }

    public void changeDialogTop(boolean isRightAngle) {
        this.plvLlRewardDialogView.setBackgroundResource(isRightAngle ? R.drawable.plv_shape_reward_right_angle : R.drawable.plv_shape_reward_fillet);
    }

    public void changeToLandscape() {
        this.isLandscape = true;
        this.plvRvRewardLandscape.setVisibility(0);
        this.plvVpPointReward.setVisibility(8);
        this.plvBeadPointReward.setVisibility(8);
        changeRewardNumButtonSpace(8);
        ((RelativeLayout.LayoutParams) this.plvTvPointRewardTitle.getLayoutParams()).leftMargin = ConvertUtils.dp2px(30.0f);
    }

    public void changeToPortrait() {
        this.isLandscape = false;
        this.plvVpPointReward.setVisibility(0);
        this.plvRvRewardLandscape.setVisibility(8);
        PLVRewardPageAdapter pLVRewardPageAdapter = this.adapter;
        if (pLVRewardPageAdapter != null && pLVRewardPageAdapter.getPageCount() > 0) {
            this.plvBeadPointReward.setVisibility(0);
        }
        changeRewardNumButtonSpace(4);
        ((RelativeLayout.LayoutParams) this.plvTvPointRewardTitle.getLayoutParams()).leftMargin = ConvertUtils.dp2px(16.0f);
    }

    public ImageView getCloseButton() {
        return this.plvIvPointRewardClose;
    }

    public Button getMakeRewardButton() {
        return this.plvBtnPointRewardMakeReward;
    }

    public TextView getRemainingPointTextView() {
        return this.plvTvPointRewardRemainingPoint;
    }

    public TextView getRewardTitleTextView() {
        return this.plvTvPointRewardTitle;
    }

    public void hide() throws Resources.NotFoundException {
        if (isShown()) {
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.context, R.anim.plv_point_reward_exit);
            animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.6
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    PLVRewardDialogView.this.rootView.setVisibility(8);
                    PLVRewardDialogView.this.rootView.clearFocus();
                    PLVOrientationManager.getInstance().unlockOrientation();
                    PLVRewardDialogView.this.isShown = false;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
            this.rootView.startAnimation(animationLoadAnimation);
        }
    }

    public void init(List<PLVBaseViewData> dataList) throws Resources.NotFoundException {
        if (this.adapter == null) {
            this.adapter = new PLVRewardPageAdapter(this.context.getSupportFragmentManager(), dataList, true, 10);
            this.plvVpPointReward.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.5
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    PLVRewardDialogView.this.plvBeadPointReward.setCurrentSelectedIndex(position);
                }
            });
            this.plvVpPointReward.setAdapter(this.adapter);
            this.plvBeadPointReward.setBeadCount(this.adapter.getPageCount());
            this.adapter.notifyDataSetChanged();
            this.plvVpPointReward.setOffscreenPageLimit(this.adapter.getPageCount());
        }
        this.plvRvRewardLandscape.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        PLVRewardListAdapter pLVRewardListAdapter = new PLVRewardListAdapter(true);
        this.landscapeAdapter = pLVRewardListAdapter;
        pLVRewardListAdapter.setDataList(new ArrayList(dataList));
        this.plvRvRewardLandscape.setAdapter(this.landscapeAdapter);
        this.plvRvRewardLandscape.setVisibility(8);
    }

    public boolean isShown() {
        return this.isShown;
    }

    public void setMakeRewardListener(OnMakeRewardListener makeRewardListener) {
        this.makeRewardListener = makeRewardListener;
    }

    public void setShowListener(OnShowListener showListener) {
        this.showListener = showListener;
    }

    public void show() throws Resources.NotFoundException {
        if (isShown()) {
            return;
        }
        this.rootView.setVisibility(0);
        this.rootView.requestFocus();
        this.rootView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.plv_point_reward_enter));
        this.isShown = true;
        OnShowListener onShowListener = this.showListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
    }

    public void updateRemainingPoint(String remainingPoint) {
        this.plvTvPointRewardRemainingPoint.setText("我的积分：" + remainingPoint);
    }
}
