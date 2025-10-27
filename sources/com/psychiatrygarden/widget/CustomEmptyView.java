package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CustomEmptyView extends LinearLayout {
    private AnimationDrawable animationDrawable;
    private TextView btnReload;
    private ImageView imgView;
    private Context mContext;
    private ImageView mImgLoading;
    private LinearLayout mLyEmptyView;
    private LinearLayout mLyLoading;
    private int mResImgId;
    private TextView mTvLoading;
    private RelativeLayout root;
    private TextView tvEmpty;

    public CustomEmptyView(Context context, int resImgId, String emptyStr, int layoutResId) {
        super(context);
        initCustomerLayout(context, resImgId, emptyStr, "", layoutResId);
    }

    private void init(Context context, int resImgId, String emptyStr, String type) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_empty_common_view, this);
        this.root = (RelativeLayout) findViewById(R.id.root);
        this.imgView = (ImageView) findViewById(R.id.img_empty);
        this.tvEmpty = (TextView) findViewById(R.id.tv_empty);
        this.btnReload = (TextView) findViewById(R.id.btn_reload);
        this.mTvLoading = (TextView) findViewById(R.id.tv_loading);
        this.mImgLoading = (ImageView) findViewById(R.id.iv_loading);
        this.mLyLoading = (LinearLayout) findViewById(R.id.rl_loading);
        this.mLyEmptyView = (LinearLayout) findViewById(R.id.ly_empty_view);
        this.mResImgId = resImgId;
        if (resImgId == 0) {
            this.mResImgId = SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.ic_empty_data_normal_night : R.drawable.ic_empty_data_normal_day;
        }
        this.imgView.setImageResource(this.mResImgId);
        this.mImgLoading.setBackgroundResource(SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.loading_night : R.drawable.loading);
        TextView textView = this.tvEmpty;
        if (TextUtils.isEmpty(emptyStr)) {
            emptyStr = "暂无数据";
        }
        textView.setText(emptyStr);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mImgLoading.getBackground();
        this.animationDrawable = animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        if (type.equals("sign_continuous") || type.equals("sign_total")) {
            this.imgView.setImageResource(R.drawable.ic_empty_data_normal_day);
            this.mImgLoading.setBackgroundResource(R.drawable.loading);
            this.mTvLoading.setTextColor(getResources().getColor(R.color.white, null));
            this.root.setBackgroundColor(getResources().getColor(R.color.white_background, null));
        }
    }

    private void initCustomerLayout(Context context, int resImgId, String emptyStr, String type, int layoutId) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(layoutId, this);
        this.root = (RelativeLayout) findViewById(R.id.root);
        this.imgView = (ImageView) findViewById(R.id.img_empty);
        this.tvEmpty = (TextView) findViewById(R.id.tv_empty);
        this.btnReload = (TextView) findViewById(R.id.btn_reload);
        this.mTvLoading = (TextView) findViewById(R.id.tv_loading);
        this.mImgLoading = (ImageView) findViewById(R.id.iv_loading);
        this.mLyLoading = (LinearLayout) findViewById(R.id.rl_loading);
        this.mLyEmptyView = (LinearLayout) findViewById(R.id.ly_empty_view);
        this.mResImgId = resImgId;
        if (resImgId == 0) {
            this.mResImgId = SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.ic_empty_data_normal_night : R.drawable.ic_empty_data_normal_day;
        }
        this.imgView.setImageResource(this.mResImgId);
        this.mImgLoading.setBackgroundResource(SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.loading_night : R.drawable.loading);
        TextView textView = this.tvEmpty;
        if (TextUtils.isEmpty(emptyStr)) {
            emptyStr = "暂无数据";
        }
        textView.setText(emptyStr);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mImgLoading.getBackground();
        this.animationDrawable = animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        if (type.equals("sign_continuous") || type.equals("sign_total")) {
            this.imgView.setImageResource(R.drawable.ic_empty_data_normal_day);
            this.mImgLoading.setBackgroundResource(R.drawable.loading);
            this.mTvLoading.setTextColor(getResources().getColor(R.color.white, null));
            this.root.setBackgroundColor(getResources().getColor(R.color.white_background, null));
        }
    }

    @SuppressLint({"ResourceType"})
    public void changeEmptyViewNewBgTwoColor() {
        this.root.setBackgroundColor(this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_two_color}).getColor(0, Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT)));
    }

    @SuppressLint({"ResourceType"})
    public void changeEmptyViewNewBgTwoNewColor() {
        this.root.setBackgroundColor(this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_two_color_new}).getColor(0, Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT)));
    }

    @SuppressLint({"ResourceType"})
    public void changeEmptyViewNightBg() throws Resources.NotFoundException {
        this.root.setBackgroundColor(this.mContext.getResources().getColor(R.color.color_14, null));
    }

    @SuppressLint({"ResourceType"})
    public void changeEmptyViewWriteBg() {
        this.root.setBackgroundColor(this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.app_bg}).getColor(0, Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT)));
    }

    public void restartAnim() {
        LinearLayout linearLayout = this.mLyLoading;
        if (linearLayout != null && linearLayout.getVisibility() == 8) {
            this.mLyLoading.setVisibility(0);
            this.mLyEmptyView.setVisibility(8);
        }
        this.mImgLoading.setBackgroundResource(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.loading_night : R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mImgLoading.getBackground();
        this.animationDrawable = animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public void setEmptyTextStr(String str) {
        TextView textView = this.tvEmpty;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setImgEmptyRes(int resId) {
        ImageView imageView = this.imgView;
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
    }

    public void setIsShowReloadBtn(boolean isShow, String txtStr) {
        stopAnim();
        TextView textView = this.btnReload;
        if (textView != null) {
            textView.setVisibility(isShow ? 0 : 8);
            this.btnReload.setText(txtStr);
        }
    }

    public void setLoadFileResUi(Context context) {
        stopAnim();
        TextView textView = this.btnReload;
        if (textView != null) {
            textView.setVisibility(0);
            this.btnReload.setText("点击刷新页面");
        }
        TextView textView2 = this.tvEmpty;
        if (textView2 != null) {
            textView2.setText("页面加载失败");
        }
        ImageView imageView = this.imgView;
        if (imageView != null) {
            imageView.setImageResource(SkinManager.getCurrentSkinType(context) == 1 ? R.drawable.ic_load_data_failed_night : R.drawable.ic_load_data_failed_day);
        }
    }

    public void setOnReloadData(View.OnClickListener clickListener) {
        TextView textView = this.btnReload;
        if (textView != null) {
            textView.setOnClickListener(clickListener);
        }
    }

    public void showEmptyView() {
        if (this.mLyLoading != null) {
            stopAnim();
        }
    }

    public void stopAnim() {
        LinearLayout linearLayout = this.mLyLoading;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            this.mLyLoading.setVisibility(8);
            this.mLyEmptyView.setVisibility(0);
        }
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
            this.animationDrawable = null;
        }
    }

    public void uploadEmptyViewResUi() {
        stopAnim();
        TextView textView = this.btnReload;
        if (textView != null && textView.getVisibility() == 0) {
            this.btnReload.setVisibility(8);
        }
        TextView textView2 = this.tvEmpty;
        if (textView2 != null) {
            textView2.setText("暂无数据");
        }
        ImageView imageView = this.imgView;
        if (imageView != null) {
            imageView.setImageResource(this.mResImgId);
        }
    }

    public CustomEmptyView(Context context, int resImgId, String emptyStr) {
        super(context);
        init(context, resImgId, emptyStr, "");
    }

    public CustomEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, 0, "", "");
    }

    public CustomEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, 0, "", "");
    }

    public CustomEmptyView(Context context, int resImgId, String emptyStr, String type) {
        super(context);
        init(context, resImgId, "", type);
    }
}
