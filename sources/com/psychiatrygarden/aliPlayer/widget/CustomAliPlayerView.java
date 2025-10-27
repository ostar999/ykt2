package com.psychiatrygarden.aliPlayer.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.view.choice.AlivcShowMoreDialog;
import com.aliyun.player.alivcplayerexpand.view.control.ControlView;
import com.aliyun.player.alivcplayerexpand.view.more.SpeedValue;
import com.aliyun.player.alivcplayerexpand.view.more.TrackInfoView;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener;
import com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.aliyun.svideo.common.utils.ScreenUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.purchase.VideoFullActivity;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.FullScreenVideoPopwindow;
import com.psychiatrygarden.widget.PopNoteCourseListLandScape;
import com.psychiatrygarden.widget.SelectDefinitionFullScreenPopWindow;
import com.psychiatrygarden.widget.SelectDefinitionPopWindow;
import com.psychiatrygarden.widget.SelectSpeedFullScreenPopWindow;
import com.psychiatrygarden.widget.SelectSpeedPopWindow;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CustomAliPlayerView extends FrameLayout {
    private static OnAudioPlayStatusListenter mAudioPlayStatusLisenter;
    private String Mp4Url;
    private String VideoTitle;
    private Activity activity;
    private CountDownTimer countDownTimer;
    public AliyunScreenMode currentScreenMode;
    private String defaultVideoDefinition;
    public String expire_str;
    public FrameLayout fraviewGroup;
    public long free_watch_time;
    private boolean isCanPlayBy4g;
    private boolean isFreeWatch;
    private boolean isFullAttrs;
    public boolean isFullForbbtie;
    private boolean isUnlock;
    private boolean isVideo;
    public AliyunVodPlayerView mAliyunVodPlayerView;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
    private AudioManager mAudioManager;
    private OnBackClickListener mBackClickListener;
    private String mCurrentSpeed;
    private ShareDataBuyCourse mShareDataBuyCourse;
    private TryWatchUnlockView mTryWatchUnlockView;
    public WindowManager mWindowManager;
    private OnCompleteToNext onCompleteToNext;
    private int pipViewId;
    private RelativeLayout relPlayerview;
    public String seeDuration;
    private AlivcShowMoreDialog showMoreDialog;
    private int speekPosition;
    private String tag;
    private String vids;
    public String watch_permission;

    /* renamed from: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView$5, reason: invalid class name */
    public class AnonymousClass5 implements ControlView.OnQualityBtnClickListener {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQualityBtnClick$0(List list, String str) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                TrackInfo trackInfo = (TrackInfo) it.next();
                if (TextUtils.equals(str, trackInfo.vodDefinition)) {
                    CustomAliPlayerView.this.mAliyunVodPlayerView.selectTrack(trackInfo);
                }
            }
            CustomAliPlayerView.this.defaultVideoDefinition = str;
            CustomAliPlayerView.this.mAliyunVodPlayerView.getControlView().updateVideoDefinitionShow(str);
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnQualityBtnClickListener
        public void onHideQualityView() {
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnQualityBtnClickListener
        public void onQualityBtnClick(View v2, final List<TrackInfo> qualities, String currentQuality) {
            if (qualities == null || qualities.isEmpty()) {
                return;
            }
            XPopup.Builder builder = new XPopup.Builder(CustomAliPlayerView.this.activity);
            builder.popupPosition(PopupPosition.Right).asCustom(new SelectDefinitionFullScreenPopWindow(CustomAliPlayerView.this.activity, qualities, CustomAliPlayerView.this.mAliyunVodPlayerView.getControlView().getCurrentQuality(), new SelectDefinitionFullScreenPopWindow.OnDefinitionSelectListener() { // from class: com.psychiatrygarden.aliPlayer.widget.m
                @Override // com.psychiatrygarden.widget.SelectDefinitionFullScreenPopWindow.OnDefinitionSelectListener
                public final void onDefinitionSelect(String str) {
                    this.f15282a.lambda$onQualityBtnClick$0(qualities, str);
                }
            })).show();
        }
    }

    /* renamed from: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView$6, reason: invalid class name */
    public class AnonymousClass6 implements ControlView.OnTrackInfoClickListener {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDefinitionClick$0(List list, String str) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                TrackInfo trackInfo = (TrackInfo) it.next();
                if (TextUtils.equals(str, trackInfo.vodDefinition)) {
                    CustomAliPlayerView.this.mAliyunVodPlayerView.selectTrack(trackInfo);
                }
            }
            CustomAliPlayerView.this.defaultVideoDefinition = str;
            CustomAliPlayerView.this.mAliyunVodPlayerView.getControlView().updateVideoDefinitionShow(str);
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onAudioClick(List<TrackInfo> trackInfoList) {
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onBitrateClick(List<TrackInfo> trackInfoList) {
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onDefinitionClick(List<TrackInfo> trackInfoList) {
            final List<TrackInfo> definitionTrackInfoList = CustomAliPlayerView.this.mAliyunVodPlayerView.getControlView().getDefinitionTrackInfoList();
            TrackInfo trackInfoCurrentTrack = CustomAliPlayerView.this.mAliyunVodPlayerView.currentTrack(TrackInfo.Type.TYPE_VIDEO);
            if (trackInfoCurrentTrack != null && CustomAliPlayerView.this.defaultVideoDefinition == null) {
                CustomAliPlayerView.this.defaultVideoDefinition = trackInfoCurrentTrack.getVodDefinition();
            }
            if (CustomAliPlayerView.this.mAliyunVodPlayerView.getControlView().getCurrentQuality() != null) {
                CustomAliPlayerView customAliPlayerView = CustomAliPlayerView.this;
                customAliPlayerView.defaultVideoDefinition = customAliPlayerView.mAliyunVodPlayerView.getControlView().getCurrentQuality();
            }
            if (CustomAliPlayerView.this.defaultVideoDefinition == null) {
                CustomAliPlayerView.this.defaultVideoDefinition = QualityValue.QUALITY_LOW;
            }
            if (definitionTrackInfoList == null || definitionTrackInfoList.size() <= 0) {
                return;
            }
            new XPopup.Builder(CustomAliPlayerView.this.activity).asCustom(new SelectDefinitionPopWindow(CustomAliPlayerView.this.activity, definitionTrackInfoList, CustomAliPlayerView.this.defaultVideoDefinition, new SelectDefinitionPopWindow.OnDefinitionSelectListener() { // from class: com.psychiatrygarden.aliPlayer.widget.n
                @Override // com.psychiatrygarden.widget.SelectDefinitionPopWindow.OnDefinitionSelectListener
                public final void onDefinitionSelect(String str) {
                    this.f15284a.lambda$onDefinitionClick$0(definitionTrackInfoList, str);
                }
            })).show();
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onSubtitleClick(List<TrackInfo> trackInfoList) {
        }
    }

    public static class MyCompletionListener implements IPlayer.OnCompletionListener {
        private WeakReference<CustomAliPlayerView> customAliPlayerViewWeakReference;

        public MyCompletionListener(CustomAliPlayerView customAliPlayerView) {
            this.customAliPlayerViewWeakReference = new WeakReference<>(customAliPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            CustomAliPlayerView customAliPlayerView = this.customAliPlayerViewWeakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onCompletion();
                if (CustomAliPlayerView.mAudioPlayStatusLisenter != null) {
                    CustomAliPlayerView.mAudioPlayStatusLisenter.onPlayEnd();
                }
            }
        }
    }

    public static class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
        private WeakReference<CustomAliPlayerView> activityWeakReference;

        public MyNetConnectedListener(CustomAliPlayerView customAliPlayerView) {
            this.activityWeakReference = new WeakReference<>(customAliPlayerView);
        }

        @Override // com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.NetConnectedListener
        public void onNetUnConnected() {
            CustomAliPlayerView customAliPlayerView = this.activityWeakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onNetUnConnected();
            }
        }

        @Override // com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.NetConnectedListener
        public void onReNetConnected(boolean isReconnect) {
            CustomAliPlayerView customAliPlayerView = this.activityWeakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onReNetConnected(isReconnect);
            }
        }
    }

    public static class MyOnErrorListener implements IPlayer.OnErrorListener {
        private WeakReference<CustomAliPlayerView> weakReference;

        public MyOnErrorListener(CustomAliPlayerView activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // com.aliyun.player.IPlayer.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onError(errorInfo);
                if (CustomAliPlayerView.mAudioPlayStatusLisenter != null) {
                    CustomAliPlayerView.mAudioPlayStatusLisenter.onPlayEnd();
                }
            }
        }
    }

    public static class MyOnScreenBrightnessListener implements AliyunVodPlayerView.OnScreenBrightnessListener {
        private WeakReference<CustomAliPlayerView> weakReference;

        public MyOnScreenBrightnessListener(CustomAliPlayerView activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView.OnScreenBrightnessListener
        public void onScreenBrightness(int brightness) {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.setWindowBrightness(brightness);
                AliyunVodPlayerView aliyunVodPlayerView = customAliPlayerView.mAliyunVodPlayerView;
                if (aliyunVodPlayerView != null) {
                    aliyunVodPlayerView.setScreenBrightness(brightness);
                }
            }
        }
    }

    public static class MyOnTipClickListener implements TipsView.OnTipClickListener {
        private WeakReference<CustomAliPlayerView> weakReference;

        public MyOnTipClickListener(CustomAliPlayerView aliyunPlayerSkinActivity) {
            this.weakReference = new WeakReference<>(aliyunPlayerSkinActivity);
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onContinuePlay() {
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onExit() {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.activity.finish();
            }
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onRefreshSts() {
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onReplay() {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.setSeeDuration("0");
            }
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onRetryPlay(int errorCode) {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                if (errorCode == ErrorCode.ERROR_LOADING_TIMEOUT.getValue()) {
                    customAliPlayerView.mAliyunVodPlayerView.reTry();
                } else {
                    customAliPlayerView.refresh();
                }
            }
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onStopPlay() {
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.TipsView.OnTipClickListener
        public void onWait() {
        }
    }

    public static class MyOnTipsViewBackClickListener implements OnTipsViewBackClickListener {
        private WeakReference<CustomAliPlayerView> weakReference;

        public MyOnTipsViewBackClickListener(CustomAliPlayerView aliyunPlayerSkinActivity) {
            this.weakReference = new WeakReference<>(aliyunPlayerSkinActivity);
        }

        @Override // com.aliyun.player.aliyunplayerbase.view.tipsview.OnTipsViewBackClickListener
        public void onBackClick() {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onTipsViewClick();
            }
        }
    }

    public static class MyOnTrackInfoClickListener implements ControlView.OnTrackInfoClickListener {
        private WeakReference<CustomAliPlayerView> weakReference;

        public MyOnTrackInfoClickListener(CustomAliPlayerView activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onAudioClick(List<TrackInfo> audioTrackInfoList) {
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onBitrateClick(List<TrackInfo> bitrateTrackInfoList) {
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onDefinitionClick(List<TrackInfo> definitionTrackInfoList) {
            this.weakReference.get();
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnTrackInfoClickListener
        public void onSubtitleClick(List<TrackInfo> subtitleTrackInfoList) {
        }
    }

    public static class MyPrepareListener implements IPlayer.OnPreparedListener {
        private WeakReference<CustomAliPlayerView> activityWeakReference;

        public MyPrepareListener(CustomAliPlayerView customAliPlayerView) {
            this.activityWeakReference = new WeakReference<>(customAliPlayerView);
        }

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            CustomAliPlayerView customAliPlayerView = this.activityWeakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onPrepared();
            }
        }
    }

    public class MySeekOnCompleteListener implements IPlayer.OnSeekCompleteListener {
        private WeakReference<CustomAliPlayerView> activityWeakReference;

        public MySeekOnCompleteListener(CustomAliPlayerView activityWeakReference) {
            this.activityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override // com.aliyun.player.IPlayer.OnSeekCompleteListener
        public void onSeekComplete() {
            this.activityWeakReference.get();
        }
    }

    public static class MyShowMoreClickLisener implements ControlView.OnShowMoreClickListener {
        WeakReference<CustomAliPlayerView> weakReference;

        public MyShowMoreClickLisener(CustomAliPlayerView activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.OnShowMoreClickListener
        public void showMore() {
            CustomAliPlayerView customAliPlayerView = this.weakReference.get();
            if (customAliPlayerView == null || FastClickUtil.isFastClick()) {
                return;
            }
            customAliPlayerView.showMore();
        }
    }

    public static class MyStoppedListener implements OnStoppedListener {
        private WeakReference<CustomAliPlayerView> activityWeakReference;

        public MyStoppedListener(CustomAliPlayerView skinActivity) {
            this.activityWeakReference = new WeakReference<>(skinActivity);
        }

        @Override // com.aliyun.player.alivcplayerexpand.listener.OnStoppedListener
        public void onStop() {
            CustomAliPlayerView customAliPlayerView = this.activityWeakReference.get();
            if (customAliPlayerView != null) {
                customAliPlayerView.onStopped();
            }
        }
    }

    public interface OnAudioPlayStatusListenter {
        void onPlayEnd();

        void onPlaying();
    }

    public interface OnBackClickListener {
        void onBackClick();
    }

    public interface OnCompleteToNext {
        void onCompleteToNext(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO);
    }

    public interface PlayProgressListener {
        void onProgress(float progress);
    }

    public interface ShareDataBuyCourse {
        void mShareDataBuyCourse();

        void peekVideo();

        void postSeekDuration(long time);
    }

    public CustomAliPlayerView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    private int getCurrentBrightValue() {
        try {
            return Settings.System.getInt(this.activity.getContentResolver(), "screen_brightness", 255);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAllDialog() {
        AlivcShowMoreDialog alivcShowMoreDialog = this.showMoreDialog;
        if (alivcShowMoreDialog == null || !alivcShowMoreDialog.isShowing()) {
            return;
        }
        this.showMoreDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addPlayImageView$10(View view) {
        hideAllDialog();
        this.relPlayerview.setVisibility(8);
        this.mAliyunVodPlayerView.showProcessControl();
        this.mAliyunVodPlayerView.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        this.mShareDataBuyCourse.mShareDataBuyCourse();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initView$1() {
        EventBus.getDefault().post("DOWNLOAD_COURSE_VIDEO");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        if (TextUtils.isEmpty(this.Mp4Url)) {
            return;
        }
        this.relPlayerview.setVisibility(0);
        this.mAliyunVodPlayerView.hideProcessControl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i2) {
        Activity activity = this.activity;
        if (activity == null) {
            return;
        }
        if (i2 != 0) {
            activity.finish();
            return;
        }
        Intent intent = new Intent(this.activity, (Class<?>) VideoFullActivity.class);
        intent.putExtra("vid", "" + this.vids);
        intent.putExtra("Mp4Url", "" + this.Mp4Url);
        intent.putExtra("videotitle", "" + getVideoTitle());
        intent.putExtra("tag", "" + this.tag);
        intent.putExtra("mCurrentPosition", this.mAliyunVodPlayerView.getmCurrentPosition());
        this.activity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDefinitionClick$9(TrackInfo trackInfo) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.selectTrack(trackInfo);
        }
        hideAllDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPrepared$5() {
        if (TextUtils.isEmpty(this.Mp4Url)) {
            this.mAliyunVodPlayerView.start();
            if (!this.isVideo) {
                mAudioPlayStatusLisenter.onPlaying();
            }
        }
        initTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDrawerNoteList$4() {
        if (getContext() instanceof AliPlayerVideoPlayActivity) {
            ((AliPlayerVideoPlayActivity) getContext()).noteNote();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showMore$8(int i2) {
        if (i2 == 0) {
            this.speekPosition = 0;
            this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
        } else if (i2 == 1) {
            this.speekPosition = 1;
            this.mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
        } else if (i2 == 2) {
            this.speekPosition = 2;
            this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OnePointTwoFive);
        } else if (i2 == 3) {
            this.speekPosition = 3;
            this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
        } else if (i2 == 4) {
            this.speekPosition = 4;
            this.mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
        }
        int i3 = this.speekPosition;
        if (i3 == 0) {
            this.mCurrentSpeed = "0.5X";
        } else if (i3 == 1) {
            this.mCurrentSpeed = "1X";
        } else if (i3 == 2) {
            this.mCurrentSpeed = "1.25X";
        } else if (i3 == 3) {
            this.mCurrentSpeed = "1.5X";
        } else if (i3 == 4) {
            this.mCurrentSpeed = "2X";
        }
        this.mAliyunVodPlayerView.updateSpeedPlayShow(i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPortView$7(int i2) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null) {
            return;
        }
        if (i2 == 0) {
            this.speekPosition = 0;
            aliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
        } else if (i2 == 1) {
            this.speekPosition = 1;
            aliyunVodPlayerView.changeSpeed(SpeedValue.One);
        } else if (i2 == 2) {
            this.speekPosition = 2;
            aliyunVodPlayerView.changeSpeed(SpeedValue.OnePointTwoFive);
        } else if (i2 == 3) {
            this.speekPosition = 3;
            aliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
        } else if (i2 == 4) {
            this.speekPosition = 4;
            aliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
        }
        this.mAliyunVodPlayerView.updateSpeedPlayShow(this.speekPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCompletion() {
        hideAllDialog();
        if (!TextUtils.isEmpty(this.Mp4Url)) {
            this.seeDuration = "";
            this.mAliyunVodPlayerView.rePlay();
            this.mAliyunVodPlayerView.hideProcessControl();
            this.relPlayerview.setVisibility(0);
            return;
        }
        if (this.mAliyunVodPlayerView != null) {
            OnCompleteToNext onCompleteToNext = this.onCompleteToNext;
            if (onCompleteToNext != null) {
                onCompleteToNext.onCompleteToNext(null);
            }
            List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> list = ProjectApp.mPlayerVideo;
            if (list == null || list.size() <= 0) {
                if (this.onCompleteToNext != null) {
                    if (this.isFreeWatch) {
                        this.mTryWatchUnlockView.setVisibility(0);
                    } else {
                        this.mAliyunVodPlayerView.showReplay();
                    }
                    this.onCompleteToNext.onCompleteToNext(null);
                    return;
                }
                return;
            }
            for (int i2 = 0; i2 < ProjectApp.mPlayerVideo.size(); i2++) {
                CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = ProjectApp.mPlayerVideo.get(i2);
                if (this.vids.equals(childrenDTO.getVid())) {
                    ProjectApp.mPlayerVideo.remove(childrenDTO);
                }
            }
            if (this.onCompleteToNext == null) {
                TryWatchUnlockView tryWatchUnlockView = this.mTryWatchUnlockView;
                if (tryWatchUnlockView != null) {
                    tryWatchUnlockView.setVisibility(0);
                    return;
                } else {
                    this.mAliyunVodPlayerView.showReplay();
                    return;
                }
            }
            ShareDataBuyCourse shareDataBuyCourse = this.mShareDataBuyCourse;
            if (shareDataBuyCourse != null) {
                shareDataBuyCourse.postSeekDuration(this.mAliyunVodPlayerView.getDuration());
            }
            if (ProjectApp.mPlayerVideo.size() > 0) {
                CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO2 = ProjectApp.mPlayerVideo.get(0);
                this.vids = childrenDTO2.getVid();
                this.onCompleteToNext.onCompleteToNext(childrenDTO2);
            } else {
                if (!this.isFreeWatch) {
                    this.mAliyunVodPlayerView.showReplay();
                    return;
                }
                TryWatchUnlockView tryWatchUnlockView2 = this.mTryWatchUnlockView;
                if (tryWatchUnlockView2 != null) {
                    tryWatchUnlockView2.setVisibility(0);
                }
            }
        }
    }

    private void onDefinitionClick(List<TrackInfo> definitionTrackInfoList) {
        this.showMoreDialog = new AlivcShowMoreDialog(this.activity);
        TrackInfoView trackInfoView = new TrackInfoView(this.activity);
        trackInfoView.setTrackInfoLists(definitionTrackInfoList);
        trackInfoView.setCurrentTrackInfo(this.mAliyunVodPlayerView.currentTrack(TrackInfo.Type.TYPE_VOD));
        this.showMoreDialog.setContentView(trackInfoView);
        this.showMoreDialog.show();
        trackInfoView.setOnDefinitionChangedListener(new TrackInfoView.OnDefinitionChangedListrener() { // from class: com.psychiatrygarden.aliPlayer.widget.a
            @Override // com.aliyun.player.alivcplayerexpand.view.more.TrackInfoView.OnDefinitionChangedListrener
            public final void onDefinitionChanged(TrackInfo trackInfo) {
                this.f15271a.lambda$onDefinitionClick$9(trackInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(ErrorInfo errorInfo) {
        if (errorInfo.getCode().getValue() == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
            refresh();
        }
        if (errorInfo.getCode().getValue() == ErrorCode.ERROR_GENERAL_EIO.getValue()) {
            refresh();
        }
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNetUnConnected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReNetConnected(boolean isReconnect) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTipsViewClick() {
        OnBackClickListener onBackClickListener = this.mBackClickListener;
        if (onBackClickListener != null) {
            onBackClickListener.onBackClick();
        } else {
            this.activity.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        if (!TextUtils.isEmpty(this.vids)) {
            CommonUtil.getCourseDownAk(this.vids, this, true);
        } else {
            if (TextUtils.isEmpty(this.Mp4Url)) {
                return;
            }
            setMp4UrlSource();
        }
    }

    private void releaseTheAudioFocus(AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener) {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager == null || mAudioFocusChangeListener == null) {
            return;
        }
        audioManager.abandonAudioFocus(mAudioFocusChangeListener);
    }

    private int requestTheAudioFocus() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.activity.getSystemService("audio");
        }
        if (this.mAudioFocusChangeListener == null) {
            this.mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.9
                @Override // android.media.AudioManager.OnAudioFocusChangeListener
                public void onAudioFocusChange(int focusChange) {
                }
            };
        }
        return this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWindowBrightness(int brightness) {
        Window window = this.activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = brightness / 100.0f;
        window.setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMore() {
        if (this.mAliyunVodPlayerView.getScreenMode() == AliyunScreenMode.Small) {
            showPortView();
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this.activity);
        builder.popupPosition(PopupPosition.Right);
        builder.asCustom(new SelectSpeedFullScreenPopWindow(this.activity, this.mCurrentSpeed, new SelectSpeedFullScreenPopWindow.OnSpeedSelectListener() { // from class: com.psychiatrygarden.aliPlayer.widget.h
            @Override // com.psychiatrygarden.widget.SelectSpeedFullScreenPopWindow.OnSpeedSelectListener
            public final void onSpeedSelect(int i2) {
                this.f15277a.lambda$showMore$8(i2);
            }
        })).show();
    }

    private void showSelectDefinition() {
    }

    private void updatePlayerViewMode() {
        if (this.mAliyunVodPlayerView != null) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 == 1) {
                this.mAliyunVodPlayerView.setSystemUiVisibility(0);
                int width = (int) ((ScreenUtils.getWidth(this.activity) * 9.0f) / 16.0f);
                if (!TextUtils.isEmpty(getTag())) {
                    width = (int) (((ScreenUtils.getWidth(this.activity) - (CommonUtil.dip2px(this.activity, 16.0f) * 2)) * 9.0f) / 16.0f);
                }
                this.mAliyunVodPlayerView.setLayoutParams(new FrameLayout.LayoutParams(-1, width));
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = -1;
                    layoutParams.height = -2;
                    setLayoutParams(layoutParams);
                    ViewGroup.LayoutParams layoutParams2 = ((ViewGroup) getParent()).getLayoutParams();
                    if (layoutParams2 != null) {
                        layoutParams2.width = -1;
                        layoutParams2.height = -2;
                        ((ViewGroup) getParent()).setLayoutParams(layoutParams2);
                        return;
                    }
                    return;
                }
                return;
            }
            if (i2 != 2 || this.isFullForbbtie) {
                return;
            }
            this.mAliyunVodPlayerView.setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
            this.mAliyunVodPlayerView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            ViewGroup.LayoutParams layoutParams3 = getLayoutParams();
            if (layoutParams3 != null) {
                layoutParams3.width = -1;
                layoutParams3.height = -1;
                setLayoutParams(layoutParams3);
                ViewGroup.LayoutParams layoutParams4 = ((ViewGroup) getParent()).getLayoutParams();
                if (layoutParams4 != null) {
                    layoutParams4.width = -1;
                    layoutParams4.height = -1;
                    ((ViewGroup) getParent()).setLayoutParams(layoutParams4);
                }
            }
        }
    }

    public void addPlayImageView() {
        this.relPlayerview = new RelativeLayout(this.activity);
        ImageView imageView = new ImageView(this.activity);
        imageView.setImageDrawable(ContextCompat.getDrawable(this.activity, R.drawable.vipplayer));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(CommonUtil.dip2px(this.activity, 50.0f), CommonUtil.dip2px(this.activity, 50.0f));
        layoutParams.addRule(13);
        this.relPlayerview.addView(imageView, layoutParams);
        TextView textView = new TextView(this.activity);
        textView.setText(getVideoTitle());
        textView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
        textView.setTextSize(2, 16.0f);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(12);
        layoutParams2.setMargins(CommonUtil.dip2px(this.activity, 12.0f), 0, 0, CommonUtil.dip2px(this.activity, 12.0f));
        this.relPlayerview.addView(textView, layoutParams2);
        this.mAliyunVodPlayerView.addSubView(this.relPlayerview);
        if (isFullAttrs()) {
            hideAllDialog();
            this.relPlayerview.setVisibility(8);
            this.mAliyunVodPlayerView.showProcessControl();
            this.mAliyunVodPlayerView.start();
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15278c.lambda$addPlayImageView$10(view);
            }
        });
    }

    public void changePlayLocalSource(String url, String title) {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        urlSource.setTitle(title);
        this.mAliyunVodPlayerView.setLocalSource(urlSource);
    }

    public void fullView() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.type = 99;
        layoutParams.flags = 1280;
        layoutParams.systemUiVisibility = 4102;
        FrameLayout frameLayout = this.fraviewGroup;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        this.mWindowManager.addView(this.mAliyunVodPlayerView, layoutParams);
    }

    public void fullorori(boolean isfull) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Full, isfull);
        }
    }

    public String getExpire_str() {
        return this.expire_str;
    }

    public long getFree_watch_time() {
        return this.free_watch_time;
    }

    public String getVideoTitle() {
        return this.VideoTitle;
    }

    public String getVids() {
        return this.vids;
    }

    public String getWatch_permission() {
        return this.watch_permission;
    }

    public void hidePipBtn() {
    }

    public void hideRelPlayer() {
        RelativeLayout relativeLayout = this.relPlayerview;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
            this.mAliyunVodPlayerView.showProcessControl();
        }
    }

    public void hideVideoBuyView() {
        this.mTryWatchUnlockView.setVisibility(8);
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null || aliyunVodPlayerView.isPlaying()) {
            return;
        }
        this.mAliyunVodPlayerView.onResume();
    }

    public void initTimer() {
        if (("0".equals(this.watch_permission) && this.free_watch_time > 0) || this.isFreeWatch) {
            if (this.countDownTimer == null) {
                this.countDownTimer = new CountDownTimer(this.free_watch_time * 1000, 1000L) { // from class: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.8
                    @Override // android.os.CountDownTimer
                    public void onFinish() {
                        CustomAliPlayerView.this.mTryWatchUnlockView.setVisibility(0);
                        CustomAliPlayerView.this.mAliyunVodPlayerView.setTryWatchComplete(true);
                        CustomAliPlayerView.this.hidePipBtn();
                        AliyunVodPlayerView aliyunVodPlayerView = CustomAliPlayerView.this.mAliyunVodPlayerView;
                        if (aliyunVodPlayerView != null) {
                            aliyunVodPlayerView.pause();
                            CustomAliPlayerView.this.mAliyunVodPlayerView.setCurrentPosition(0L);
                        }
                        if (CustomAliPlayerView.this.countDownTimer != null) {
                            CustomAliPlayerView.this.countDownTimer.cancel();
                            CustomAliPlayerView.this.countDownTimer = null;
                        }
                    }

                    @Override // android.os.CountDownTimer
                    public void onTick(long l2) {
                        CustomAliPlayerView.this.mTryWatchUnlockView.setVisibility(8);
                        if (CustomAliPlayerView.this.mAliyunVodPlayerView.getmCurrentPosition() > CustomAliPlayerView.this.free_watch_time * 1000) {
                            onFinish();
                        }
                        CustomAliPlayerView.this.mAliyunVodPlayerView.setTryWatchComplete(true);
                    }
                };
            }
            this.countDownTimer.start();
            return;
        }
        this.mAliyunVodPlayerView.setTryWatchComplete(false);
        if (TextUtils.isEmpty(this.expire_str)) {
            this.mTryWatchUnlockView.setVisibility(8);
            showPipBtn();
        }
        if (this.mAliyunVodPlayerView == null || TextUtils.isEmpty(this.seeDuration)) {
            return;
        }
        int i2 = (int) (Double.parseDouble(this.seeDuration) * 1000.0d);
        if (this.mAliyunVodPlayerView.getDuration() - i2 <= 1000) {
            return;
        }
        this.mAliyunVodPlayerView.seekTo(i2);
        showPipBtn();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x012e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initView(android.content.Context r7, java.lang.String r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.initView(android.content.Context, java.lang.String, boolean):void");
    }

    public void initVipView() {
    }

    public boolean isFullAttrs() {
        return this.isFullAttrs;
    }

    public void mDestoryView() {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onDestroy();
            this.mAliyunVodPlayerView = null;
        }
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
        this.activity = null;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        TryWatchUnlockView tryWatchUnlockView = this.mTryWatchUnlockView;
        if (tryWatchUnlockView != null) {
            View viewFindViewById = tryWatchUnlockView.findViewById(R.id.iv_back);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewFindViewById.getLayoutParams();
            if (newConfig.orientation == 1) {
                this.currentScreenMode = AliyunScreenMode.Small;
                layoutParams.topMargin = 0;
            } else {
                this.currentScreenMode = AliyunScreenMode.Full;
                layoutParams.topMargin = SizeUtil.dp2px(getContext(), 46);
            }
            viewFindViewById.setLayoutParams(layoutParams);
            updatePlayerViewMode();
        }
    }

    public void onDestory() {
        ShareDataBuyCourse shareDataBuyCourse = this.mShareDataBuyCourse;
        if (shareDataBuyCourse != null) {
            shareDataBuyCourse.postSeekDuration(this.mAliyunVodPlayerView.getmCurrentPosition());
        }
        mDestoryView();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0019  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r4.getAction()
            r1 = 1
            if (r0 == 0) goto L19
            if (r0 == r1) goto Ld
            r2 = 2
            if (r0 == r2) goto L19
            goto L20
        Ld:
            android.view.ViewParent r0 = r3.getParent()
            r1 = 0
            r0.requestDisallowInterceptTouchEvent(r1)
            r3.resetSpeed()
            goto L20
        L19:
            android.view.ViewParent r0 = r3.getParent()
            r0.requestDisallowInterceptTouchEvent(r1)
        L20:
            boolean r4 = super.onInterceptTouchEvent(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public void onPause() {
        releaseTheAudioFocus(this.mAudioFocusChangeListener);
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }

    public void onPrepared() {
        Activity activity = this.activity;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.aliPlayer.widget.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15273c.lambda$onPrepared$5();
                }
            });
        }
    }

    public void onResume() {
        if (this.activity == null) {
            return;
        }
        requestTheAudioFocus();
        if (this.mAliyunVodPlayerView == null || this.mTryWatchUnlockView.getVisibility() != 8) {
            return;
        }
        if ("1".equals(this.watch_permission) || this.free_watch_time > 0) {
            this.mAliyunVodPlayerView.onResume();
        }
    }

    public void onStopped() {
        OnAudioPlayStatusListenter onAudioPlayStatusListenter = mAudioPlayStatusLisenter;
        if (onAudioPlayStatusListenter != null) {
            onAudioPlayStatusListenter.onPlayEnd();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    public void pauseVideo() {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.pause();
        }
    }

    public void removeWindowView() {
        try {
            AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
            if (aliyunVodPlayerView == null || (aliyunVodPlayerView.getParent() instanceof FrameLayout)) {
                return;
            }
            this.mWindowManager.removeView(this.mAliyunVodPlayerView);
            if (this.fraviewGroup.getChildCount() <= 0) {
                this.fraviewGroup.addView(this.mAliyunVodPlayerView);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void resetSpeed() {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.hideFastForwardView();
            AliyunVodPlayerView aliyunVodPlayerView2 = this.mAliyunVodPlayerView;
            if (aliyunVodPlayerView2.isLongPress) {
                float currentSpeed = aliyunVodPlayerView2.getCurrentSpeed();
                if (currentSpeed == 0.5f) {
                    this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (currentSpeed == 1.0f) {
                    this.mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (currentSpeed == 1.25f) {
                    this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OnePointTwoFive);
                } else if (currentSpeed == 1.5f) {
                    this.mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (currentSpeed == 2.0f) {
                    this.mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }
                this.mAliyunVodPlayerView.isLongPress = false;
            }
        }
    }

    public void setBackClickListener(OnBackClickListener listener) {
        this.mBackClickListener = listener;
    }

    public void setExpire_str(String expire_str) {
        this.expire_str = expire_str;
    }

    public void setFreeWatch(boolean isFreeWatch) {
        this.isFreeWatch = isFreeWatch;
    }

    public void setFree_watch_time(long free_watch_time) {
        this.free_watch_time = free_watch_time;
    }

    public void setFullAttrs(boolean fullAttrs) {
        this.isFullAttrs = fullAttrs;
    }

    public void setFullForbbtie(boolean fullForbbtie) {
        this.isFullForbbtie = fullForbbtie;
    }

    public void setIsVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public void setMp4Url(String mp4Url) {
        this.Mp4Url = mp4Url;
    }

    public void setMp4UrlSource() {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(this.Mp4Url);
        this.mAliyunVodPlayerView.setMP4UrlSource(urlSource);
    }

    public void setOnCompleteToNext(OnCompleteToNext onCompleteToNext) {
        this.onCompleteToNext = onCompleteToNext;
    }

    public void setOnPlayStatusLisenter(OnAudioPlayStatusListenter onStatusListener) {
        mAudioPlayStatusLisenter = onStatusListener;
    }

    public void setProgressListener(final PlayProgressListener l2) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.getControlView().setPlayProgressListener(new ControlView.PlayProgressListener() { // from class: com.psychiatrygarden.aliPlayer.widget.j
                @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.PlayProgressListener
                public final void onProgress(float f2) {
                    l2.onProgress(f2);
                }
            });
        }
    }

    public void setSeeDuration(String seeDuration) {
        this.seeDuration = seeDuration;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setVidSts(VidSts vidSts) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.setVidSts(vidSts);
        }
    }

    public void setVideoTitle(String videoTitle) {
        this.VideoTitle = videoTitle;
    }

    public void setVids(String vids) {
        this.vids = vids;
    }

    public void setWatch_permission(String watch_permission) {
        this.watch_permission = watch_permission;
        this.isUnlock = "1".equals(watch_permission);
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null || aliyunVodPlayerView.getControlView() == null) {
            return;
        }
        if (!"0".equals(watch_permission) || this.free_watch_time <= 0) {
            this.mAliyunVodPlayerView.getControlView().setTryWatchTime(0L);
        } else {
            this.mAliyunVodPlayerView.getControlView().setTryWatchTime(this.free_watch_time);
        }
    }

    public void setmShareDataBuyCourse(final ShareDataBuyCourse mShareDataBuyCourse) {
        ControlView controlView;
        this.mShareDataBuyCourse = mShareDataBuyCourse;
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView == null || (controlView = aliyunVodPlayerView.getControlView()) == null) {
            return;
        }
        Objects.requireNonNull(mShareDataBuyCourse);
        controlView.setUnlockListener(new ControlView.UnlockListener() { // from class: com.psychiatrygarden.aliPlayer.widget.k
            @Override // com.aliyun.player.alivcplayerexpand.view.control.ControlView.UnlockListener
            public final void onLock() {
                mShareDataBuyCourse.mShareDataBuyCourse();
            }
        });
    }

    public void showDrawerNoteList(String obj_id, String courseId, String captureScreenFilePath, String module_type) {
        XPopup.Builder builder = new XPopup.Builder(this.activity);
        builder.popupType(PopupType.Position).popupPosition(PopupPosition.Right);
        PopNoteCourseListLandScape popNoteCourseListLandScape = new PopNoteCourseListLandScape(this.activity, obj_id, courseId, new PopNoteCourseListLandScape.NoNoteListener() { // from class: com.psychiatrygarden.aliPlayer.widget.b
            @Override // com.psychiatrygarden.widget.PopNoteCourseListLandScape.NoNoteListener
            public final void noNote() {
                this.f15272a.lambda$showDrawerNoteList$4();
            }
        }, true, captureScreenFilePath);
        popNoteCourseListLandScape.setModuleType(module_type);
        builder.setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.7
            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onDismiss(BasePopupView popupView) {
                super.onDismiss(popupView);
            }

            @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
            public void onShow(BasePopupView popupView) {
                super.onShow(popupView);
            }
        });
        builder.asCustom(popNoteCourseListLandScape).show();
    }

    public void showFullPop() {
        Activity activity = this.activity;
        if (activity != null) {
            new XPopup.Builder(activity).asCustom(new FullScreenVideoPopwindow(this.activity, this.vids)).show();
        }
    }

    public void showHideConTrol(boolean isshow) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.setControlBarCanShow(isshow);
        }
    }

    public void showHideTitle(boolean isshow) {
        AliyunVodPlayerView aliyunVodPlayerView = this.mAliyunVodPlayerView;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.setTitleBarCanShow(isshow);
        }
    }

    public void showHideoView() {
        this.mAliyunVodPlayerView.hideProcessControl();
    }

    public void showPipBtn() {
    }

    public void showPortView() {
        int i2 = this.speekPosition;
        if (i2 == 0) {
            this.mCurrentSpeed = "0.5X";
        } else if (i2 == 1) {
            this.mCurrentSpeed = "1X";
        } else if (i2 == 2) {
            this.mCurrentSpeed = "1.25X";
        } else if (i2 == 3) {
            this.mCurrentSpeed = "1.5X";
        } else if (i2 == 4) {
            this.mCurrentSpeed = "2X";
        }
        new XPopup.Builder(this.activity).asCustom(new SelectSpeedPopWindow(this.activity, this.mCurrentSpeed, new SelectSpeedPopWindow.OnSpeedSelectListener() { // from class: com.psychiatrygarden.aliPlayer.widget.l
            @Override // com.psychiatrygarden.widget.SelectSpeedPopWindow.OnSpeedSelectListener
            public final void onSpeedSelect(int i3) {
                this.f15281a.lambda$showPortView$7(i3);
            }
        })).show();
    }

    public void updateCommentAndNoteCount(String noteCount, String commentCount) {
        this.mAliyunVodPlayerView.getControlView().updateCommentAndNoteCount(commentCount, noteCount);
    }

    public CustomAliPlayerView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override // android.view.View
    public String getTag() {
        return this.tag;
    }

    @SuppressLint({"RestrictedApi"})
    public CustomAliPlayerView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentScreenMode = AliyunScreenMode.Small;
        this.vids = "";
        this.expire_str = "";
        this.watch_permission = "1";
        this.free_watch_time = 0L;
        this.speekPosition = 1;
        this.Mp4Url = "";
        this.isFullAttrs = false;
        this.VideoTitle = "";
        this.tag = "";
        this.isCanPlayBy4g = false;
        this.mCurrentSpeed = "1X";
        this.isVideo = true;
    }
}
