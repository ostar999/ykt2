package com.psychiatrygarden.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ExoPlayer;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CombineQuestionGuideActivity extends BaseActivity {
    private AnimationDrawable mAnimationDrawable;
    private final CountDownTimer mCountDownTimer = new CountDownTimer(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, 1000) { // from class: com.psychiatrygarden.activity.CombineQuestionGuideActivity.1
        @Override // android.os.CountDownTimer
        public void onFinish() {
            CombineQuestionGuideActivity combineQuestionGuideActivity = CombineQuestionGuideActivity.this;
            CombineQuestionMainNewActivity.gotoCombineQuestionMain(combineQuestionGuideActivity, combineQuestionGuideActivity.mType);
            CombineQuestionGuideActivity.this.finish();
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
        }
    };
    private String mType;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        this.mCountDownTimer.cancel();
        startActivity(new Intent(view.getContext(), (Class<?>) CombineQuestionRecordListActivity.class));
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mType = getIntent().getStringExtra("type");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable == null || !animationDrawable.isRunning()) {
            return;
        }
        this.mAnimationDrawable.stop();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        AnimationDrawable animationDrawable = this.mAnimationDrawable;
        if (animationDrawable == null || animationDrawable.isRunning()) {
            return;
        }
        this.mAnimationDrawable.start();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.mCountDownTimer.cancel();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_combine_question_guide);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.iv_actionbar_right.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        this.iv_actionbar_right.setLayoutParams(layoutParams);
        this.iv_actionbar_right.setVisibility(0);
        this.iv_actionbar_right.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.drawable.ic_combine_record : R.drawable.ic_combine_record_night);
        ImageView imageView = (ImageView) findViewById(R.id.iv_robot);
        this.mBtnActionbarRight.setVisibility(8);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.anim_combine_question_guide_night));
        }
        this.mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.g2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12424c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12463c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mCountDownTimer.start();
    }
}
