package com.psychiatrygarden.widget.english;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.adapter.PopWordInfoAdapter;
import com.psychiatrygarden.bean.WordTranslateBean;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.english.CustomPopWindow;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopWord implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, QuestionDataCallBack<String> {
    private AnimationDrawable animationDrawable;
    private ImageView iv_play;
    private ImageView iv_word_collect;
    private ProgressBar lav_loading;
    PopupWindow.OnDismissListener listener;
    private int mBottom;
    private Context mContext;
    private CustomPopWindow mPopWordCard;
    private float[] mRawY;
    private NestedScrollView mTevRoot;
    private int mTop;
    private RelativeLayout mView;
    private String mWord;
    private MediaPlayer mediaPlayer;
    private int popHeight;
    private int popWeight;
    private RecyclerView rv_word_info;
    private String sid;
    private TextView tv_word_name;
    private TextView tv_word_symbol;
    private List<String> list = new ArrayList();
    private WordTranslateBean mWordTranslateBean = new WordTranslateBean();

    @SuppressLint({"InflateParams"})
    public PopWord(Context activity, float[] rawY, int top2, int bottom, PopupWindow.OnDismissListener listener, String word, String sid) {
        EventBus.getDefault().register(this);
        this.listener = listener;
        this.mContext = activity;
        this.mRawY = rawY;
        this.mTop = top2;
        this.mBottom = bottom;
        this.mWord = word;
        this.sid = sid;
        this.popWeight = (ScreenUtil.getDpByPx(activity, ScreenUtil.getScreenWidth((Activity) activity)) * 4) / 5;
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.pop_word, (ViewGroup) null);
        this.mView = relativeLayout;
        relativeLayout.setOnClickListener(this);
        NestedScrollView nestedScrollView = (NestedScrollView) this.mView.findViewById(R.id.mTevRoot);
        this.mTevRoot = nestedScrollView;
        nestedScrollView.addView(getView(this.mWordTranslateBean));
        this.popHeight = ScreenUtil.getScreenHeight((Activity) this.mContext) / 3;
        this.mPopWordCard = new CustomPopWindow.PopupWindowBuilder(activity).setView(this.mView).setWidth(this.mContext, this.popWeight).setHeight(this.popHeight).setOutsideTouchable(true).setFocusable(true).setOnDissmissListener(listener).create();
        QuestionDataRequest.getIntance(this.mContext).getWordInfo(this.mWord, this);
    }

    private View getView(WordTranslateBean bean) {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.pop_word_children, (ViewGroup) null);
        this.lav_loading = (ProgressBar) viewInflate.findViewById(R.id.lav_loading);
        this.tv_word_name = (TextView) viewInflate.findViewById(R.id.tv_word_name);
        this.tv_word_symbol = (TextView) viewInflate.findViewById(R.id.tv_word_symbol);
        this.rv_word_info = (RecyclerView) viewInflate.findViewById(R.id.rv_word_info);
        this.iv_word_collect = (ImageView) viewInflate.findViewById(R.id.iv_word_collect);
        if (this.sid.equals(Constants.VIA_REPORT_TYPE_START_GROUP) || TextUtils.isEmpty(this.sid)) {
            this.iv_word_collect.setVisibility(4);
        } else {
            this.iv_word_collect.setVisibility(0);
        }
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_play);
        this.iv_play = imageView;
        this.animationDrawable = (AnimationDrawable) imageView.getDrawable();
        this.rv_word_info.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rv_word_info.setAdapter(new PopWordInfoAdapter(this.list));
        this.iv_play.setOnClickListener(this);
        this.iv_word_collect.setOnClickListener(this);
        return viewInflate;
    }

    private void initWordInfo() {
        this.tv_word_name.setText(this.mWordTranslateBean.getData().getWord());
        if (TextUtils.isEmpty(this.mWordTranslateBean.getData().getSymbols())) {
            this.tv_word_symbol.setVisibility(4);
        } else {
            this.tv_word_symbol.setVisibility(0);
            this.tv_word_symbol.setText("/" + this.mWordTranslateBean.getData().getSymbols() + "/");
        }
        this.rv_word_info.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.list = this.mWordTranslateBean.getData().getExplain();
        this.rv_word_info.setAdapter(new PopWordInfoAdapter(this.list));
    }

    private void playWord() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.mWordTranslateBean == null) {
            ToastUtil.shortToast(this.mContext, "暂无语音");
            return;
        }
        if (this.animationDrawable.isRunning()) {
            this.animationDrawable.stop();
            this.animationDrawable.setVisible(true, true);
            releasePlayer();
            return;
        }
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setDataSource(this.mWordTranslateBean.getData().getPronunciation());
            this.mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.widget.english.m
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer2) throws IllegalStateException {
                    this.f16463c.onPrepared(mediaPlayer2);
                }
            });
            this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.widget.english.n
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer2) throws IllegalStateException {
                    this.f16464c.onCompletion(mediaPlayer2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void dismiss() {
        CustomPopWindow customPopWindow = this.mPopWordCard;
        if (customPopWindow != null) {
            customPopWindow.dismiss();
        }
    }

    public boolean isShow() {
        CustomPopWindow customPopWindow = this.mPopWordCard;
        if (customPopWindow == null) {
            return false;
        }
        return customPopWindow.isShowing();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        int id = v2.getId();
        if (id == R.id.iv_play) {
            playWord();
        } else {
            if (id != R.id.rl_root) {
                return;
            }
            this.mPopWordCard.dismiss();
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) throws IllegalStateException {
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable == null || !animationDrawable.isRunning()) {
            return;
        }
        this.animationDrawable.stop();
        this.animationDrawable.setVisible(true, true);
        releasePlayer();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String str) throws IllegalStateException {
        if (str.equals(EventBusConstant.EVENT_HOME_DESTORY)) {
            releasePlayer();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        NewToast.showShort(this.mContext, "请求失败", 0).show();
        this.lav_loading.setVisibility(8);
        this.mPopWordCard.dismiss();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) throws IllegalStateException {
        this.animationDrawable.start();
        this.mediaPlayer.start();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        this.lav_loading.setVisibility(0);
        this.iv_word_collect.setVisibility(8);
        this.iv_play.setVisibility(8);
    }

    public void releasePlayer() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        EventBus.getDefault().unregister(this);
    }

    public void show(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[1];
        float[] fArr = this.mRawY;
        float f2 = fArr[0];
        float f3 = fArr[1];
        if ((f2 + f3) / 2.0f > (this.mTop + this.mBottom) / 2.0f) {
            this.mPopWordCard.showAsDropDownCenter(view, (int) ((((f2 - i2) - ScreenUtil.getPxByDp(this.mContext, 5)) - view.getMeasuredHeight()) - this.popHeight));
            this.mView.setGravity(80);
        } else {
            this.mPopWordCard.showAsDropDownCenter(view, (int) (((f3 - i2) + ScreenUtil.getPxByDp(this.mContext, 5)) - view.getMeasuredHeight()));
            this.mView.setGravity(48);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            this.iv_play.setVisibility(0);
            this.lav_loading.setVisibility(8);
            if (new JSONObject(s2).optString("code").equals("200")) {
                this.mWordTranslateBean = (WordTranslateBean) new Gson().fromJson(s2, WordTranslateBean.class);
                this.mTevRoot.removeAllViews();
                this.mTevRoot.addView(getView(this.mWordTranslateBean));
                initWordInfo();
                playWord();
            } else {
                NewToast.showShort(this.mContext, this.mWordTranslateBean.getMessage(), 0).show();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
