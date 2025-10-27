package com.psychiatrygarden.activity.comment.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.utils.ToastUtils;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteCourseChapterActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussHeaderBean;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.QuestioBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshCourseStateEvent;
import com.psychiatrygarden.event.RefreshPraiseEvent;
import com.psychiatrygarden.event.RefreshVideoCommentEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogCourseNoteInput;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DownloadTipPop;
import com.psychiatrygarden.widget.english.PopNoteCourseList;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class BottomInputView extends FrameLayout implements PopNoteCourseList.NoNoteListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public ActivityResultLauncher activityResultLauncher;
    public Bundle bundle;
    private String captureScreenFilePath;
    public String collectionStr;
    public DiscussStatus commentEnum;
    public String comment_type;
    public Context context;
    private String courseId;
    private boolean fromVideo;
    private boolean isInit;
    private String isProhibit;
    private String isSeeStr;
    private String is_note;
    public ImageView iv_my_daka;
    public ImageView iv_topic_detail_msg;
    public ImageView iv_topic_detail_zan;
    public LinearLayout ly_iv_my_daka;
    private LinearLayout ly_iv_topic_detail_msg;
    private LinearLayout ly_iv_topic_detail_zan;
    public LinearLayout ly_questiondetails_btn_collect;
    public LinearLayout ly_questiondetails_btn_edit;
    public BottomInputOnclickIml mBottomInputOnclickIml;
    private DialogInterface.OnClickListener mClickListener;
    private VideoPageActionListener mVideoPageActionListener;
    public int module_type;
    public String noteStr;
    public String obj_id;
    private PopupWindow popupWindow_note;
    public ImageView questiondetails_btn_collect;
    public ImageView questiondetails_btn_edit;
    public RelativeLayout relksd;
    public TextView tv_topic_detail_add_comment;
    public String videoType;
    private String vidteaching_id;

    public interface BottomInputOnclickIml {
        void mPostSuccessFul();
    }

    public interface VideoPageActionListener {
        void updateCollect(boolean isCollect);

        void updateHasNote(boolean hasNote);
    }

    public BottomInputView(@NonNull @NotNull Context context) {
        super(context);
        this.module_type = 1;
        this.videoType = "1";
        this.obj_id = "";
        this.comment_type = "2";
        this.isSeeStr = "0";
        this.vidteaching_id = "";
        this.is_note = "0";
        this.isInit = false;
    }

    private void checkPraise() {
        AjaxParams ajaxParams = new AjaxParams();
        if (TextUtils.isEmpty(this.obj_id)) {
            return;
        }
        if (this.module_type == 13) {
            ajaxParams.put("unit_id", this.obj_id);
        } else {
            ajaxParams.put("vidteaching_id", this.obj_id);
        }
        YJYHttpUtils.get(getContext(), this.module_type == 14 ? NetworkRequestsURL.infoApi : NetworkRequestsURL.mInfourl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    QuestioBean questioBean = (QuestioBean) new Gson().fromJson(s2, QuestioBean.class);
                    if (!questioBean.getCode().equals("200") || questioBean.getData() == null) {
                        return;
                    }
                    int is_comment = questioBean.getData().getIs_comment();
                    BottomInputView.this.setZantongTrue(questioBean.getData().getIs_praise() == 1);
                    BottomInputView.this.setCommentTrue(is_comment == 1);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$10() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$7(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$8(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$9(String str, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) NoteCourseChapterActivity.class);
        intent.putExtra("course_id", this.obj_id);
        intent.putExtra("notestr", str);
        intent.putExtra("noteStatus", this.noteStr);
        intent.putExtra("vidteaching_id", "" + this.vidteaching_id);
        this.activityResultLauncher.launch(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.commentEnum.getCode() == 8) {
            if (!this.is_note.equals("1") && !"1".equals(this.noteStr)) {
                new DialogCourseNoteInput(getContext(), this.vidteaching_id, this.obj_id, new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.3
                    @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                    public void onclickStringBack(QuestionNoteBean questionNoteBean) {
                        BottomInputView.this.setNoteBtn(true);
                        if (!BottomInputView.this.fromVideo || BottomInputView.this.mVideoPageActionListener == null) {
                            return;
                        }
                        BottomInputView.this.mVideoPageActionListener.updateHasNote(true);
                    }
                }).show();
                return;
            } else {
                new XPopup.Builder(getContext()).hasShadowBg(Boolean.TRUE).moveUpToKeyboard(Boolean.FALSE).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.2
                    @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        if (BottomInputView.this.getContext() instanceof Activity) {
                            ((Activity) BottomInputView.this.getContext()).getWindow().clearFlags(2);
                        }
                    }
                }).enableDrag(true).asCustom(new PopNoteCourseList(getContext(), this.obj_id, this.vidteaching_id, this, this.fromVideo, this.captureScreenFilePath)).show();
                return;
            }
        }
        if (this.commentEnum.getCode() == 13) {
            if (!TextUtils.equals(this.noteStr, "0")) {
                getKedanNoteData();
                return;
            }
            Intent intent = new Intent(this.context, (Class<?>) NoteCourseChapterActivity.class);
            intent.putExtra("course_id", this.obj_id);
            intent.putExtra("notestr", "");
            intent.putExtra("noteStatus", this.noteStr);
            intent.putExtra("vidteaching_id", "" + this.vidteaching_id);
            this.activityResultLauncher.launch(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        getCollectNewData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        coursenewVideoDaka();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SYStem_Red_Dot, false, this.context);
        EventBus.getDefault().post(CommonParameter.SYStem_Red_Dot);
        Intent intent = new Intent(this.context, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", this.obj_id);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("comment_type", "2");
        intent.putExtra("title", "我的评论");
        intent.putExtra("commentEnum", DiscussStatus.MyComments);
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        Intent intent = new Intent(this.context, (Class<?>) DiscussPublicActivity.class);
        intent.putExtra("obj_id", this.obj_id);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("comment_type", "2");
        intent.putExtra("title", "我的点赞");
        intent.putExtra("commentEnum", DiscussStatus.MyPraise);
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(bundle);
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        this.activityResultLauncher.launch(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(View view) {
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(getContext(), "本帖已被锁定，不支持回帖");
            return;
        }
        Context context = this.context;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.h
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f11784a.lambda$initView$5(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        DialogInput dialogInput = new DialogInput(context, ondialogclicklistener, z2);
        dialogInput.setFromVideo(this.fromVideo);
        dialogInput.show();
    }

    public void addComment() {
        this.tv_topic_detail_add_comment.performClick();
    }

    public void coursenewVideoDaka() {
        if (this.context == null) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.obj_id);
        ajaxParams.put("vidteaching_id", this.vidteaching_id);
        YJYHttpUtils.post(this.context, NetworkRequestsURL.handleWatchedApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    if (!new JSONObject(t2).optString("code").equals("200")) {
                        ToastUtils.show(BottomInputView.this.context, "打卡失败");
                        return;
                    }
                    if (BottomInputView.this.isSeeStr.equals("1")) {
                        BottomInputView.this.isSeeStr = "0";
                        BottomInputView.this.setPunchTheClock(false);
                        ToastUtils.show(BottomInputView.this.context, "取消打卡成功");
                    } else {
                        BottomInputView.this.isSeeStr = "1";
                        ToastUtils.show(BottomInputView.this.context, "打卡成功");
                        BottomInputView.this.setPunchTheClock(true);
                    }
                    DiscussStatus discussStatus = BottomInputView.this.commentEnum;
                    if (discussStatus == null || discussStatus.getCode() != DiscussStatus.LessonList.getCode()) {
                        return;
                    }
                    RefreshCourseStateEvent refreshCourseStateEvent = new RefreshCourseStateEvent();
                    refreshCourseStateEvent.setVid(BottomInputView.this.obj_id);
                    refreshCourseStateEvent.setRefreshDaKa(true);
                    refreshCourseStateEvent.setDaKa("1".equals(BottomInputView.this.isSeeStr));
                    EventBus.getDefault().post(refreshCourseStateEvent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void dialogNote(final String content) {
        Context context = this.context;
        if (context == null) {
            return;
        }
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11785c.lambda$dialogNote$7(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11786c.lambda$dialogNote$8(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11787c.lambda$dialogNote$9(content, view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.comment.widget.b
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                BottomInputView.lambda$dialogNote$10();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_note.showAtLocation(this, 17, 0, 0);
    }

    public void getCollectNewData() {
        String str;
        if (this.context == null) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        if (this.commentEnum.getCode() == 8) {
            str = NetworkRequestsURL.curriculumcollectUrl;
            ajaxParams.put("course_id", this.vidteaching_id);
            ajaxParams.put("video_id", this.obj_id);
        } else if (this.commentEnum.getCode() == 13) {
            str = NetworkRequestsURL.collectVideoApi;
            ajaxParams.put("vidteaching_id", this.vidteaching_id);
            ajaxParams.put("id", this.obj_id);
        } else {
            str = "";
        }
        YJYHttpUtils.post(this.context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (SkinManager.getCurrentSkinType(BottomInputView.this.getContext()) == 0) {
                    ProjectApp.instance().showDialogWindow();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                ProjectApp.instance().hideDialogWindow();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        if ("1".equals(BottomInputView.this.collectionStr)) {
                            BottomInputView bottomInputView = BottomInputView.this;
                            bottomInputView.collectionStr = "0";
                            bottomInputView.setCollectBtn(false);
                        } else {
                            BottomInputView bottomInputView2 = BottomInputView.this;
                            bottomInputView2.collectionStr = "1";
                            bottomInputView2.setCollectBtn(true);
                        }
                        if (BottomInputView.this.fromVideo && BottomInputView.this.mVideoPageActionListener != null) {
                            BottomInputView.this.mVideoPageActionListener.updateCollect("1".equals(BottomInputView.this.collectionStr));
                            new XPopup.Builder(BottomInputView.this.getContext()).hasShadowBg(Boolean.FALSE).asCustom(new DownloadTipPop(BottomInputView.this.getContext(), "1".equals(BottomInputView.this.collectionStr) ? "已收藏" : "取消收藏", false)).show();
                        }
                        DiscussStatus discussStatus = BottomInputView.this.commentEnum;
                        if (discussStatus != null && discussStatus.getCode() == DiscussStatus.LessonList.getCode()) {
                            RefreshCourseStateEvent refreshCourseStateEvent = new RefreshCourseStateEvent();
                            refreshCourseStateEvent.setVid(BottomInputView.this.obj_id);
                            refreshCourseStateEvent.setRefreshCollection(true);
                            refreshCourseStateEvent.setCollect("1".equals(BottomInputView.this.collectionStr));
                            EventBus.getDefault().post(refreshCourseStateEvent);
                        }
                        if (BottomInputView.this.fromVideo) {
                            return;
                        }
                        ToastUtils.show(BottomInputView.this.context, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getKedanNoteData() {
        if (this.context == null) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", this.obj_id);
        ajaxParams.put("vidteaching_id", this.vidteaching_id);
        YJYHttpUtils.get(this.context, NetworkRequestsURL.getNoteApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strOptString = new JSONObject(jSONObject.optString("data")).optString("content");
                        if (!TextUtils.isEmpty(strOptString)) {
                            BottomInputView.this.dialogNote(strOptString);
                        }
                    } else {
                        ToastUtils.show(BottomInputView.this.context, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getVideoState() {
        if (this.context == null) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", "" + this.vidteaching_id);
        ajaxParams.put("video_id", "" + this.obj_id);
        YJYHttpUtils.get(this.context, NetworkRequestsURL.curriculumstateUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optJSONObject("data").optString("is_collection");
                    String strOptString2 = jSONObject.optJSONObject("data").optString("is_note");
                    BottomInputView.this.setCollectionStr(strOptString);
                    BottomInputView.this.setNoteStr(strOptString2);
                    BottomInputView.this.setCollectBtn("1".equals(strOptString));
                    BottomInputView.this.setNoteBtn("1".equals(strOptString2));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void initView() {
        int code;
        if (this.isInit) {
            return;
        }
        this.activityResultLauncher = ((BaseActivity) this.context).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.1
            @Override // androidx.activity.result.ActivityResultCallback
            public void onActivityResult(ActivityResult result) {
                if (result != null) {
                    int resultCode = result.getResultCode();
                    if (resultCode == 1) {
                        if (result.getData() != null) {
                            BottomInputView.this.putComment(result.getData().getBundleExtra("bundleIntent"));
                        }
                    } else if (resultCode == 2) {
                        ToastUtil.shortToast(BottomInputView.this.context, "删除笔记成功！");
                        BottomInputView.this.setNoteBtn(false);
                    } else if (resultCode == 3) {
                        ToastUtil.shortToast(BottomInputView.this.context, "保存笔记成功！");
                        BottomInputView.this.setNoteBtn(true);
                    }
                }
            }
        });
        View.inflate(this.context, R.layout.layout_bottom_input_view, this);
        this.relksd = (RelativeLayout) findViewById(R.id.relksd);
        EventBus.getDefault().register(this);
        this.questiondetails_btn_edit = (ImageView) findViewById(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_collect = (ImageView) findViewById(R.id.questiondetails_btn_collect);
        this.ly_questiondetails_btn_edit = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_edit);
        this.ly_questiondetails_btn_collect = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_collect);
        this.iv_my_daka = (ImageView) findViewById(R.id.iv_my_daka);
        this.ly_iv_my_daka = (LinearLayout) findViewById(R.id.ly_iv_my_daka);
        this.ly_questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11778c.lambda$initView$0(view);
            }
        });
        this.ly_questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11779c.lambda$initView$1(view);
            }
        });
        this.ly_iv_my_daka.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11780c.lambda$initView$2(view);
            }
        });
        this.ly_iv_topic_detail_msg = (LinearLayout) findViewById(R.id.ly_iv_topic_detail_msg);
        this.ly_iv_topic_detail_zan = (LinearLayout) findViewById(R.id.ly_iv_topic_detail_zan);
        this.iv_topic_detail_msg = (ImageView) findViewById(R.id.iv_topic_detail_msg);
        this.iv_topic_detail_zan = (ImageView) findViewById(R.id.iv_topic_detail_zan);
        this.tv_topic_detail_add_comment = (TextView) findViewById(R.id.tv_topic_detail_add_comment);
        if (this.isProhibit.equals("0")) {
            this.tv_topic_detail_add_comment.setText("写评论");
            this.tv_topic_detail_add_comment.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#575F79" : "#909499"));
        } else {
            this.tv_topic_detail_add_comment.setText("本帖已被锁定");
            this.tv_topic_detail_add_comment.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#B2575C" : "#F95843"));
        }
        this.ly_iv_topic_detail_msg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11781c.lambda$initView$3(view);
            }
        });
        this.ly_iv_topic_detail_zan.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11782c.lambda$initView$4(view);
            }
        });
        this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.widget.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11783c.lambda$initView$6(view);
            }
        });
        showRelksd(8);
        DiscussStatus discussStatus = this.commentEnum;
        if (discussStatus != null && ((code = discussStatus.getCode()) == 2 || code == 8 || code == 11 || code == 13)) {
            showRelksd(0);
        }
        this.isInit = true;
    }

    @Override // com.psychiatrygarden.widget.english.PopNoteCourseList.NoNoteListener
    public void noNote() {
        VideoPageActionListener videoPageActionListener;
        setNoteBtn(false);
        if (!this.fromVideo || (videoPageActionListener = this.mVideoPageActionListener) == null) {
            return;
        }
        videoPageActionListener.updateHasNote(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDetachedFromWindow();
    }

    @Subscribe
    public void onEventMainThread(RefreshPraiseEvent event) {
        int i2 = this.module_type;
        if (i2 == 13 || i2 == 14) {
            if (event.isPraise()) {
                setZantongTrue(true);
            } else {
                checkPraise();
            }
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Context context;
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus || (context = this.context) == null || !((Activity) context).isFinishing()) {
            return;
        }
        this.activityResultLauncher = null;
        this.context = null;
        this.commentEnum = null;
        this.bundle = null;
        this.mBottomInputOnclickIml = null;
    }

    public void putComment(Bundle b3) {
        if (this.context == null) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        if (this.commentEnum.getCode() == 10) {
            DiscussHeaderBean discussHeaderBean = (DiscussHeaderBean) this.bundle.getBinder("IPCBinder");
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            if (discussHeaderBean.getDiscussbean() instanceof CommentBean.DataBean.HotBean) {
                hotBean = (CommentBean.DataBean.HotBean) discussHeaderBean.getDiscussbean();
            } else if (discussHeaderBean.getDiscussbean() instanceof CommentBean.DataBean.HotBean.ReplyBean) {
                hotBean = (CommentBean.DataBean.HotBean.ReplyBean) discussHeaderBean.getDiscussbean();
            }
            ajaxParams.put("parent_id", hotBean.getId());
            ajaxParams.put("reply_primary_id", hotBean.getReply_primary_id());
            ajaxParams.put("to_user_id", hotBean.getUser_id());
            ajaxParams.put("obj_id", hotBean.getObj_id());
        } else {
            ajaxParams.put("obj_id", this.obj_id + "");
            ajaxParams.put("video_type", this.videoType);
        }
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(this.context, NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.widget.BottomInputView.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ToastUtil.shortToast(BottomInputView.this.context, "请求服务器超时！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals("401")) {
                            new CusomNewDialog(BottomInputView.this.context).setMessage(jSONObject.optString("message")).show();
                            return;
                        } else {
                            NewToast.showShort(BottomInputView.this.context, jSONObject.optString("message"), 0).show();
                            return;
                        }
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", BottomInputView.this.context);
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    if (BottomInputView.this.commentEnum.getCode() == 2) {
                        EventBus.getDefault().post("autorefault");
                        BottomInputOnclickIml bottomInputOnclickIml = BottomInputView.this.mBottomInputOnclickIml;
                        if (bottomInputOnclickIml != null) {
                            bottomInputOnclickIml.mPostSuccessFul();
                        }
                    } else {
                        EventBus.getDefault().post("autoData");
                    }
                    CommonUtil.showFristDialog(jSONObject);
                    BottomInputView.this.setCommentTrue(true);
                    if (BottomInputView.this.fromVideo) {
                        EventBus.getDefault().post(new RefreshVideoCommentEvent());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setCollectBtn(boolean collection) {
        if (collection) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
                return;
            } else {
                this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void setCollectionStr(String collectionStr) {
        VideoPageActionListener videoPageActionListener;
        this.collectionStr = collectionStr;
        if ((getContext() instanceof AliPlayerVideoPlayActivity) && this.fromVideo && (videoPageActionListener = this.mVideoPageActionListener) != null && this.isInit) {
            videoPageActionListener.updateCollect("1".equals(collectionStr));
        }
    }

    public void setCommentEnum(DiscussStatus commentEnum) {
        this.commentEnum = commentEnum;
    }

    public void setCommentTrue(boolean commentTrue) {
        if (commentTrue) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun);
                return;
            } else {
                this.iv_topic_detail_msg.setImageResource(R.drawable.youpinglun_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg);
        } else {
            this.iv_topic_detail_msg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCourseId(String id) {
        this.courseId = id;
    }

    public void setFromVideo(boolean fromVideo) {
        this.fromVideo = fromVideo;
    }

    public void setIsProhibit(String isProhibit) {
        if (TextUtils.isEmpty(isProhibit)) {
            isProhibit = "0";
        }
        this.isProhibit = isProhibit;
    }

    public void setIsSeeStr(String isSeeStr) {
        this.isSeeStr = isSeeStr;
    }

    public void setModule_type(int module_type) {
        this.module_type = module_type;
    }

    public void setNoteBtn(boolean note) {
        if (this.isInit) {
            if (note) {
                this.is_note = "1";
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
                    return;
                } else {
                    this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
                    return;
                }
            }
            this.is_note = "0";
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
            } else {
                this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
            }
        }
    }

    public void setNoteStr(String noteStr) {
        this.noteStr = noteStr;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public void setPunchTheClock(boolean punchTheClock) {
        if (punchTheClock) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.iv_my_daka.setImageResource(R.drawable.video_daka_ok);
                return;
            } else {
                this.iv_my_daka.setImageResource(R.drawable.video_daka_ok_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.iv_my_daka.setImageResource(R.drawable.video_daka_no);
        } else {
            this.iv_my_daka.setImageResource(R.drawable.video_daka_no_night);
        }
    }

    public void setVideoPageActionListener(VideoPageActionListener l2) {
        this.mVideoPageActionListener = l2;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public void setVidteaching_id(String vidteaching_id) {
        this.vidteaching_id = vidteaching_id;
    }

    public void setZantongTrue(boolean zantongTrue) {
        if (zantongTrue) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                this.iv_topic_detail_zan.setImageResource(R.drawable.youdianzan);
                return;
            } else {
                this.iv_topic_detail_zan.setImageResource(R.drawable.youdianzan_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.iv_topic_detail_zan.setImageResource(R.drawable.dianzancourse);
        } else {
            this.iv_topic_detail_zan.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    public void setmBottomInputOnclickIml(BottomInputOnclickIml mBottomInputOnclickIml) {
        this.mBottomInputOnclickIml = mBottomInputOnclickIml;
    }

    public void showCollect() {
        this.ly_questiondetails_btn_collect.performClick();
    }

    public void showNoteDialog(String captureScreenFilePath) {
        if (this.fromVideo) {
            this.captureScreenFilePath = captureScreenFilePath;
            this.ly_questiondetails_btn_edit.performClick();
        }
    }

    public void showRelksd(int ishow) {
        this.relksd.setVisibility(ishow);
        if (this.commentEnum.getCode() == 2 || this.commentEnum.getCode() == 11) {
            this.ly_iv_topic_detail_msg.setVisibility(0);
            this.ly_iv_topic_detail_zan.setVisibility(0);
            this.ly_questiondetails_btn_edit.setVisibility(8);
            this.ly_questiondetails_btn_collect.setVisibility(8);
            this.ly_iv_my_daka.setVisibility(8);
            return;
        }
        if (this.commentEnum.getCode() != 13) {
            if (this.commentEnum.getCode() == 8) {
                this.ly_iv_topic_detail_msg.setVisibility(8);
                this.ly_iv_topic_detail_zan.setVisibility(8);
                this.ly_questiondetails_btn_edit.setVisibility(0);
                this.ly_questiondetails_btn_collect.setVisibility(0);
                this.ly_iv_my_daka.setVisibility(8);
                return;
            }
            return;
        }
        this.ly_iv_topic_detail_msg.setVisibility(8);
        this.ly_iv_topic_detail_zan.setVisibility(8);
        this.ly_questiondetails_btn_edit.setVisibility(0);
        this.ly_questiondetails_btn_collect.setVisibility(0);
        this.ly_iv_my_daka.setVisibility(0);
        setCollectBtn("1".equals(this.collectionStr));
        setNoteBtn("1".equals(this.noteStr));
        setPunchTheClock("1".equals(this.isSeeStr));
    }

    public BottomInputView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.module_type = 1;
        this.videoType = "1";
        this.obj_id = "";
        this.comment_type = "2";
        this.isSeeStr = "0";
        this.vidteaching_id = "";
        this.is_note = "0";
        this.isInit = false;
    }

    public BottomInputView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.module_type = 1;
        this.videoType = "1";
        this.obj_id = "";
        this.comment_type = "2";
        this.isSeeStr = "0";
        this.vidteaching_id = "";
        this.is_note = "0";
        this.isInit = false;
    }
}
