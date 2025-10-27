package com.psychiatrygarden.activity.comment.alipler;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.comment.fragment.DiscussFragment;
import com.psychiatrygarden.activity.comment.widget.BottomInputView;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AliperCommentActivity extends BaseActivity implements CustomAliPlayerView.ShareDataBuyCourse {
    public CustomAliPlayerView aliplerView;
    public BottomInputView bottom_input_view;
    public DiscussStatus commentEnum;
    private View contentView;
    public boolean isDestroyed;
    public String vids = "";
    public String expire_str = "";
    public String watch_permission = "0";
    public long free_watch_time = 0;
    public String isFreeWatch = "0";
    public String activity_id = "";
    public int module_type = 1;
    public String obj_id = "";
    private boolean showDiscuss = true;
    public String seeDuration = "0";
    public String knowledgeId = "0";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) {
        if (childrenDTO != null) {
            if ("0".equals(this.watch_permission) && "0".equals(childrenDTO.getIsFreeWatch())) {
                finish();
                return;
            }
            this.obj_id = childrenDTO.getId();
            this.vids = childrenDTO.getVid();
            SharePreferencesUtils.writeStrConfig(CommonParameter.curriculum_vid, "" + this.vids, this);
            this.module_type = 8;
            this.expire_str = "";
            this.free_watch_time = 0L;
            this.seeDuration = childrenDTO.getSee();
            this.aliplerView.setExpire_str("");
            this.aliplerView.setFree_watch_time(this.free_watch_time);
            if ("0".equals(this.watch_permission) && "1".equals(this.isFreeWatch)) {
                this.aliplerView.setWatch_permission("1");
            } else {
                this.aliplerView.setWatch_permission(this.watch_permission);
            }
            this.aliplerView.setVids(this.vids);
            this.aliplerView.setSeeDuration(this.seeDuration);
            this.aliplerView.setmShareDataBuyCourse(this);
            CommonUtil.mPlayerData(this.vids, this.aliplerView, true);
            this.bottom_input_view.setObj_id(this.obj_id);
            this.bottom_input_view.getVideoState();
            Bundle bundle = new Bundle();
            bundle.putString("obj_id", "" + this.obj_id);
            bundle.putInt("module_type", this.module_type);
            bundle.putString("comment_type", "2");
            bundle.putInt("count", 0);
            bundle.putSerializable("commentEnum", this.commentEnum);
            bundle.putBoolean("isLastposition", true);
            DiscussFragment discussFragment = new DiscussFragment();
            discussFragment.setArguments(bundle);
            replaceFragment(discussFragment, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mShareDataBuyCourse$1() {
        this.watch_permission = "1";
        this.aliplerView.setWatch_permission("1");
        this.aliplerView.hideVideoBuyView();
    }

    public void changeDesTroy() {
        CustomAliPlayerView customAliPlayerView;
        if (this.isDestroyed || (customAliPlayerView = this.aliplerView) == null) {
            return;
        }
        customAliPlayerView.onDestory();
        this.isDestroyed = true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, true, this);
        this.commentEnum = (DiscussStatus) getIntent().getExtras().getSerializable("commentEnum");
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.COURSE_VIDEO, false, this);
        this.showDiscuss = getIntent().getBooleanExtra("showDiscuss", true);
        this.obj_id = getIntent().getExtras().getString("obj_id");
        this.knowledgeId = getIntent().getExtras().getString("knowledgeId");
        this.activity_id = getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "");
        this.vids = getIntent().getExtras().getString("vid");
        this.module_type = getIntent().getExtras().getInt("module_type");
        this.expire_str = getIntent().getExtras().getString("expire_str");
        this.watch_permission = getIntent().getExtras().getString("watch_permission");
        this.seeDuration = getIntent().getExtras().getString("seeDuration");
        this.free_watch_time = getIntent().getLongExtra("free_watch_time", 0L);
        this.aliplerView = (CustomAliPlayerView) findViewById(R.id.aliplerView);
        this.bottom_input_view = (BottomInputView) findViewById(R.id.bottom_input_view);
        View viewFindViewById = findViewById(R.id.ll_content);
        this.contentView = viewFindViewById;
        viewFindViewById.setVisibility(this.showDiscuss ? 0 : 8);
        if (this.showDiscuss) {
            this.bottom_input_view.setComment_type("2");
            this.bottom_input_view.setObj_id(this.obj_id);
            this.bottom_input_view.setContext(this);
            this.bottom_input_view.setModule_type(this.module_type);
            this.bottom_input_view.setObj_id(this.obj_id);
            this.bottom_input_view.setCommentEnum(this.commentEnum);
            String stringExtra = getIntent().getStringExtra("isProhibit");
            if (TextUtils.isEmpty(stringExtra)) {
                stringExtra = "0";
            }
            this.bottom_input_view.setIsProhibit(stringExtra);
            if (this.commentEnum.getCode() == 13) {
                this.bottom_input_view.setCollectionStr(getIntent().getExtras().getString("collection", "0"));
                this.bottom_input_view.setNoteStr(getIntent().getExtras().getString("note", "0"));
                this.bottom_input_view.setIsSeeStr(getIntent().getExtras().getString("is_see", "0"));
                this.bottom_input_view.setVidteaching_id(getIntent().getExtras().getString("chapter_id", "0"));
            } else if (this.commentEnum.getCode() == 8) {
                this.bottom_input_view.setVidteaching_id(getIntent().getExtras().getString("chapter_id", "0"));
                this.bottom_input_view.setNoteStr(getIntent().getExtras().getString("note", "0"));
                this.seeDuration = getIntent().getExtras().getString("seeDuration");
                this.isFreeWatch = getIntent().getExtras().getString("isFreeWatch");
            }
            this.bottom_input_view.initView();
            this.aliplerView.setOnCompleteToNext(new CustomAliPlayerView.OnCompleteToNext() { // from class: com.psychiatrygarden.activity.comment.alipler.s0
                @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.OnCompleteToNext
                public final void onCompleteToNext(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) {
                    this.f11684a.lambda$init$0(childrenDTO);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void mShareDataBuyCourse() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.commentEnum.getCode() == 14) {
            ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, "real_question_video");
        } else {
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID));
        }
        MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.comment.alipler.r0
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11682a.lambda$mShareDataBuyCourse$1();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black), 0);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        changeDesTroy();
        super.onDestroy();
        ProjectApp.noteContent = "";
        ProjectApp.noteBigImage = "";
        ProjectApp.noteSmellImage = "";
        EventBus.getDefault().post(new EventBusMessage("refreshDuration", ""));
        ProjectApp.mPlayerVideo.clear();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("REFRESH_COURSE_LIST".equals(str)) {
            this.watch_permission = "1";
            this.aliplerView.setWatch_permission("1");
            this.aliplerView.hideVideoBuyView();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            changeDesTroy();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.aliplerView.setExpire_str(this.expire_str);
        this.aliplerView.setFree_watch_time(this.free_watch_time);
        if ("0".equals(this.watch_permission) && "1".equals(this.isFreeWatch)) {
            this.aliplerView.setWatch_permission("1");
        } else {
            this.aliplerView.setWatch_permission(this.watch_permission);
        }
        this.aliplerView.initView(this, this.vids, UserConfig.isCanPlayBy4g(this));
        this.aliplerView.setSeeDuration(this.seeDuration);
        this.aliplerView.setmShareDataBuyCourse(this);
        CommonUtil.mPlayerData(this.vids, this.aliplerView, true);
        if (findFragment(DiscussFragment.class) == null && this.showDiscuss) {
            Bundle bundle = new Bundle();
            bundle.putString("obj_id", "" + this.obj_id);
            bundle.putInt("module_type", this.module_type);
            bundle.putString("comment_type", "2");
            bundle.putInt("count", 0);
            bundle.putSerializable("commentEnum", this.commentEnum);
            bundle.putBoolean("isLastposition", true);
            DiscussFragment discussFragment = new DiscussFragment();
            discussFragment.setArguments(bundle);
            loadRootFragment(R.id.fragment, discussFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CustomAliPlayerView customAliPlayerView = this.aliplerView;
        if (customAliPlayerView != null) {
            customAliPlayerView.onResume();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        CustomAliPlayerView customAliPlayerView = this.aliplerView;
        if (customAliPlayerView != null) {
            customAliPlayerView.onPause();
        }
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void peekVideo() {
    }

    public void postDuration(long time) {
        String str = CommonParameter.LAST_RECOMMEND_VIDEO + CommonParameter.App_Id + StrPool.UNDERLINE + this.knowledgeId + StrPool.UNDERLINE + this.obj_id;
        StringBuilder sb = new StringBuilder();
        sb.append(this.vids);
        sb.append(StrPool.UNDERLINE);
        sb.append("");
        double d3 = time / 1000.0d;
        sb.append(d3);
        SharePreferencesUtils.writeStrConfig(str, sb.toString(), this);
        if (!TextUtils.isEmpty(this.knowledgeId)) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("video_id", this.obj_id);
            ajaxParams.put("duration", String.valueOf(time / 1000));
            YJYHttpUtils.post(this, NetworkRequestsURL.recommendVideoSeeRecord, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity.1
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    LogUtils.d("onSuccess", " false === 保存推荐视频观看记录失败");
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass1) s2);
                    LogUtils.d("onSuccess", " true === 保存推荐视频观看记录成功");
                }
            });
            return;
        }
        if (this.commentEnum.getCode() != 8) {
            return;
        }
        AjaxParams ajaxParams2 = new AjaxParams();
        ajaxParams2.put("course_id", "" + getIntent().getExtras().getString("chapter_id", "0"));
        ajaxParams2.put("video_id", "" + this.obj_id);
        ajaxParams2.put("duration", "" + d3);
        YJYHttpUtils.post(this, NetworkRequestsURL.curriculumseeUrl, ajaxParams2, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        return;
                    }
                    AliperCommentActivity.this.AlertToast("播放记录保存失败！");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView.ShareDataBuyCourse
    public void postSeekDuration(long time) {
        postDuration(time);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_alipler_comment);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
