package com.psychiatrygarden.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.AllErrorCorrectionActivity;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.adapter.ErrorCorrectionAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.bean.SubComments;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.gradview.NineGridLayout;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.HttpRequstData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BianjiInfaceInput;
import com.psychiatrygarden.interfaceclass.OnClickDiogListenter;
import com.psychiatrygarden.interfaceclass.OnClickbianjiListenter;
import com.psychiatrygarden.interfaceclass.OnMInputListenster;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommentListenter;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogAdminInput;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.FloorView;
import com.psychiatrygarden.widget.MyExpendView;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.psychiatrygarden.widget.PopInputWindow;
import com.psychiatrygarden.widget.PopupBianjiComWindow;
import com.psychiatrygarden.widget.PopupComWindow;
import com.psychiatrygarden.widget.PopupWin7Comment;
import com.psychiatrygarden.widget.SubFloorFactory;
import com.umeng.analytics.pro.aq;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ErrorCorrectionAdapter extends BaseAdapter implements PinnedSectionListView1.PinnedSectionListAdapter {
    private String comment_type;
    private Context context;
    private List<CommentBean.DataBean.HotBean> list;
    public Activity mActivity;
    public CommentBean.DataBean.HotBean mH;
    private int module_type;
    private String question_id;
    public List<CommentBean.DataBean.HotBean> time_line;
    private boolean isOkTrue = false;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new AnonymousClass1();

    /* renamed from: com.psychiatrygarden.adapter.ErrorCorrectionAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, CommentBean.DataBean.HotBean hotBean, String str2, String str3, String str4) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
                bundle.putString("content", str2);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", str);
                bundle.putString("b_img", str3);
                bundle.putString("s_img", str4);
                ErrorCorrectionAdapter.this.putComment(bundle, 1, hotBean);
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", replyBean.getUser_id());
            bundle2.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
            bundle2.putString("content", str2);
            bundle2.putString("parent_id", replyBean.getId());
            bundle2.putString("reply_primary_id", str);
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putString("b_img", str3);
            bundle2.putString("s_img", str4);
            bundle2.putInt("type", 1);
            bundle2.putInt("result", 0);
            Intent intent = new Intent(ErrorCorrectionAdapter.this.context, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            ErrorCorrectionAdapter.this.mActivity.startActivityForResult(intent, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(replyBean.getId(), str, replyBean.getContent(), replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$10(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final String str, final CommentBean.DataBean.HotBean hotBean, final View view, int i2) {
            switch (i2) {
                case 0:
                    new DialogInput(ErrorCorrectionAdapter.this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.q5
                        @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                        public final void onclickStringBack(String str2, String str3, String str4) {
                            this.f14906a.lambda$handleMessage$0(replyBean, str, hotBean, str2, str3, str4);
                        }
                    }, "", "回复" + replyBean.getNickname(), replyBean.getModule_type().equals("1") || replyBean.getModule_type().equals("4")).show();
                    break;
                case 1:
                    if ("1".equals(replyBean.getStatus())) {
                        CommonUtil.mToastShow(ErrorCorrectionAdapter.this.context);
                        break;
                    } else if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        new XPopup.Builder(ErrorCorrectionAdapter.this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(ErrorCorrectionAdapter.this.mActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.r5
                            @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                            public final void mBianjiData(int i3) {
                                this.f14950a.lambda$handleMessage$3(replyBean, hotBean, view, i3);
                            }
                        })).show();
                        break;
                    } else {
                        new DialogInput(ErrorCorrectionAdapter.this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.s5
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str2, String str3, String str4) {
                                this.f14992a.lambda$handleMessage$4(replyBean, hotBean, str2, str3, str4);
                            }
                        }, replyBean.getContent(), "正在编辑" + replyBean.getNickname() + "的评论...", replyBean.getModule_type().equals("1") || replyBean.getModule_type().equals("4"), replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img(), 1, replyBean.getC_imgs().getVideoId()).show();
                        break;
                    }
                    break;
                case 2:
                    ErrorCorrectionAdapter.this.deleteComm(replyBean.getId(), replyBean.getObj_id(), str, 1, hotBean);
                    break;
                case 3:
                    ((ClipboardManager) ErrorCorrectionAdapter.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", replyBean.getContent()));
                    NewToast.showShort(ErrorCorrectionAdapter.this.context, "复制成功", 0).show();
                    break;
                case 4:
                    ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                    errorCorrectionAdapter.getUserInfo(errorCorrectionAdapter.context, replyBean.getUser_id());
                    break;
                case 5:
                    if ((TextUtils.isEmpty(replyBean.getIs_essence()) ? "0" : replyBean.getIs_essence()).equals("1")) {
                        for (int i3 = 0; i3 < ErrorCorrectionAdapter.this.list.size(); i3++) {
                            try {
                                if (replyBean.getId().equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getId())) {
                                    ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).setIs_essence("0");
                                }
                                if (((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getReply() != null && ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getReply().size() > 0) {
                                    for (int i4 = 0; i4 < ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getReply().size(); i4++) {
                                        if (replyBean.getId().equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getReply().get(i4).getId())) {
                                            ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i3)).getReply().get(i4).setIs_essence("0");
                                        }
                                    }
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        NewToast.showShort(ErrorCorrectionAdapter.this.context, "取消加精成功", 0).show();
                    } else {
                        for (int i5 = 0; i5 < ErrorCorrectionAdapter.this.list.size(); i5++) {
                            try {
                                if (replyBean.getId().equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getId())) {
                                    ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).setIs_essence("1");
                                }
                                if (((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getReply() != null && ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getReply().size() > 0) {
                                    for (int i6 = 0; i6 < ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getReply().size(); i6++) {
                                        if (replyBean.getId().equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getReply().get(i6).getId())) {
                                            ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i5)).getReply().get(i6).setIs_essence("1");
                                        }
                                    }
                                }
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        ErrorCorrectionAdapter.this.putDigest(replyBean.getId());
                    }
                    ErrorCorrectionAdapter.this.notifyDataSetChanged();
                    break;
                case 6:
                    new DialogAdminInput(ErrorCorrectionAdapter.this.context, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.t5
                        @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                        public final void mBianjiInfaceInput(String str2, String str3, String str4, String str5) {
                            this.f15029a.lambda$handleMessage$5(replyBean, hotBean, str2, str3, str4, str5);
                        }
                    }, replyBean.getCtime_timestamp(), replyBean.getContent(), replyBean.getPraise_num(), replyBean.getOppose_num()).show();
                    break;
                case 7:
                    new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.u5
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str2, PopupComWindow popupComWindow) {
                            this.f15066a.lambda$handleMessage$7(replyBean, view, str2, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 8:
                    new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.v5
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str2, PopupComWindow popupComWindow) {
                            this.f15101a.lambda$handleMessage$8(replyBean, str2, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 9:
                    ProjectApp.noteContent = replyBean.getContent();
                    if (TextUtils.isEmpty(replyBean.getVideo_id()) && !TextUtils.isEmpty(replyBean.getC_imgs().getS_img())) {
                        ProjectApp.noteSmellImage = replyBean.getC_imgs().getS_img();
                        ProjectApp.noteBigImage = replyBean.getC_imgs().getB_img();
                    }
                    new DialogNoteInput(ErrorCorrectionAdapter.this.context, ErrorCorrectionAdapter.this.module_type + "", replyBean.getObj_id() + "", "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.adapter.m5
                        @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                        public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                            ErrorCorrectionAdapter.AnonymousClass1.lambda$handleMessage$9(replyBean, questionNoteBean);
                        }
                    }).show();
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$2(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$3(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            boolean z2 = true;
            if (i2 == 0) {
                new PopInputWindow(ErrorCorrectionAdapter.this.context, replyBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.n5
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14791a.lambda$handleMessage$1(replyBean, hotBean, str);
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(ErrorCorrectionAdapter.this.context, replyBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.1.1
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                            String id = replyBean.getId();
                            String ctime_timestamp = replyBean.getCtime_timestamp();
                            String content = replyBean.getContent();
                            String oppose_num = replyBean.getOppose_num();
                            CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                            errorCorrectionAdapter.mBianjiDataVisition(id, ctime_timestamp, content, txtSter, oppose_num, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(ErrorCorrectionAdapter.this.context, replyBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.1.2
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                            String id = replyBean.getId();
                            String ctime_timestamp = replyBean.getCtime_timestamp();
                            String content = replyBean.getContent();
                            String praise_num = replyBean.getPraise_num();
                            CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                            errorCorrectionAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            Context context = ErrorCorrectionAdapter.this.context;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.o5
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14830a.lambda$handleMessage$2(replyBean, hotBean, str, str2, str3);
                }
            };
            String content = replyBean.getContent();
            String str = "正在编辑" + replyBean.getNickname() + "的评论...";
            if (!replyBean.getModule_type().equals("1") && !replyBean.getModule_type().equals("4")) {
                z2 = false;
            }
            new DialogInput(context, ondialogclicklistener, content, str, z2, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img(), 1, replyBean.getC_imgs().getVideoId()).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$4(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$5(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(replyBean.getId(), str, str2, str3, str4, replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$6(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, String str2, PopupComWindow popupComWindow2) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                popupComWindow2.dismiss();
            }
            HttpRequstData.getIntance(ErrorCorrectionAdapter.this.context).postBannedData(replyBean.getUser_id(), str2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$7(final CommentBean.DataBean.HotBean.ReplyBean replyBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.p5
                @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                    this.f14869a.lambda$handleMessage$6(popupComWindow, replyBean, str, str2, popupComWindow2);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$8(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            HttpRequstData.getIntance(ErrorCorrectionAdapter.this.context).postReportData(replyBean.getId(), str, ErrorCorrectionAdapter.this.module_type + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$handleMessage$9(CommentBean.DataBean.HotBean.ReplyBean replyBean, QuestionNoteBean questionNoteBean) {
            EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, replyBean.getObj_id()));
            ProjectApp.noteContent = "";
            ProjectApp.noteSmellImage = "";
            ProjectApp.noteBigImage = "";
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    ErrorCorrectionAdapter.this.putPraise(message.getData().getString("id"), message.getData().getString("is_praise").equals("0") ? "1" : "0");
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    ErrorCorrectionAdapter.this.putOppose(message.getData().getString("id"), message.getData().getString("is_Oppose").equals("0") ? "1" : "0");
                    return;
                }
            }
            final CommentBean.DataBean.HotBean.ReplyBean replyBean = (CommentBean.DataBean.HotBean.ReplyBean) message.getData().get("commListBean");
            final CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) message.getData().get("commListBeans");
            final String string = message.getData().getString("reply_primary_id");
            final View view = (View) message.obj;
            boolean zEquals = UserConfig.getUserId().equals(replyBean.getUser_id());
            if ("1".equals(replyBean.getStatus())) {
                CommonUtil.mToastShow(ErrorCorrectionAdapter.this.context);
                return;
            }
            int i3 = TextUtils.isEmpty(replyBean.getReply_num()) ? 0 : Integer.parseInt(replyBean.getReply_num());
            new XPopup.Builder(ErrorCorrectionAdapter.this.context).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(ErrorCorrectionAdapter.this.context, replyBean.getContent(), replyBean.getNickname(), zEquals ? 1 : 0, replyBean.getIs_anonymous() + "", ErrorCorrectionAdapter.this.module_type + "", replyBean.getDelete_skill(), replyBean.getId(), i3, new onDialogShareClickListener() { // from class: com.psychiatrygarden.adapter.l5
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i4) {
                    this.f14705a.lambda$handleMessage$10(replyBean, string, hotBean, view, i4);
                }
            })).show();
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.ErrorCorrectionAdapter$4, reason: invalid class name */
    public class AnonymousClass4 implements MyExpendView.OnExpandStateChangeListener {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass4(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$0(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", hotBean.getUser_id());
                bundle.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
                bundle.putString("content", str);
                bundle.putString("parent_id", hotBean.getId());
                bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                ErrorCorrectionAdapter.this.putComment(bundle, 2, hotBean);
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", hotBean.getUser_id());
            bundle2.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", hotBean.getId());
            bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 2);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(ErrorCorrectionAdapter.this.context, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            ErrorCorrectionAdapter.this.mActivity.startActivityForResult(intent, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$1(CommentBean.DataBean.HotBean hotBean, String str) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$10(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            HttpRequstData.getIntance(ErrorCorrectionAdapter.this.context).postReportData(hotBean.getId(), str, ErrorCorrectionAdapter.this.module_type + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClickStateChange$11(CommentBean.DataBean.HotBean hotBean, QuestionNoteBean questionNoteBean) {
            EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, hotBean.getObj_id()));
            ProjectApp.noteContent = "";
            ProjectApp.noteSmellImage = "";
            ProjectApp.noteBigImage = "";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$12(final CommentBean.DataBean.HotBean hotBean, final View view, int i2) {
            switch (i2) {
                case 0:
                    new DialogInput(ErrorCorrectionAdapter.this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.w5
                        @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                        public final void onclickStringBack(String str, String str2, String str3) {
                            this.f15132a.lambda$onClickStateChange$0(hotBean, str, str2, str3);
                        }
                    }, "", "回复" + hotBean.getNickname(), hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4")).show();
                    break;
                case 1:
                    if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        new XPopup.Builder(ErrorCorrectionAdapter.this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(ErrorCorrectionAdapter.this.mActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.a6
                            @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                            public final void mBianjiData(int i3) {
                                this.f14281a.lambda$onClickStateChange$5(hotBean, view, i3);
                            }
                        })).show();
                        break;
                    } else {
                        new DialogInput(ErrorCorrectionAdapter.this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.b6
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f14320a.lambda$onClickStateChange$6(hotBean, str, str2, str3);
                            }
                        }, hotBean.getContent(), "正在编辑" + hotBean.getNickname() + "的评论...", hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4"), hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId()).show();
                        break;
                    }
                    break;
                case 2:
                    ErrorCorrectionAdapter.this.deleteComm(hotBean.getId(), hotBean.getObj_id(), hotBean.getReply_primary_id(), 2, hotBean);
                    break;
                case 3:
                    ((ClipboardManager) ErrorCorrectionAdapter.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", hotBean.getContent()));
                    NewToast.showShort(ErrorCorrectionAdapter.this.context, "复制成功", 0).show();
                    break;
                case 4:
                    ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                    errorCorrectionAdapter.getUserInfo(errorCorrectionAdapter.context, hotBean.getUser_id());
                    break;
                case 5:
                    if ((TextUtils.isEmpty(hotBean.getIs_essence()) ? "0" : hotBean.getIs_essence()).equals("1")) {
                        try {
                            hotBean.setIs_essence("0");
                            for (int i3 = 0; i3 < ErrorCorrectionAdapter.this.list.size(); i3++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i4 = 0; i4 < reply.size(); i4++) {
                                        if (hotBean.getId().equals(reply.get(i4).getId())) {
                                            reply.get(i4).setIs_essence("0");
                                        }
                                    }
                                }
                            }
                            ErrorCorrectionAdapter.this.notifyDataSetChanged();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        NewToast.showShort(ErrorCorrectionAdapter.this.context, "取消加精成功", 0).show();
                        break;
                    } else {
                        try {
                            hotBean.setIs_essence("1");
                            for (int i5 = 0; i5 < ErrorCorrectionAdapter.this.list.size(); i5++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply();
                                if (reply2 != null && reply2.size() > 0) {
                                    for (int i6 = 0; i6 < reply2.size(); i6++) {
                                        if (hotBean.getId().equals(reply2.get(i6).getId())) {
                                            reply2.get(i6).setIs_essence("1");
                                        }
                                    }
                                }
                            }
                            ErrorCorrectionAdapter.this.notifyDataSetChanged();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        ErrorCorrectionAdapter.this.putDigest(hotBean.getId());
                        break;
                    }
                    break;
                case 6:
                    new DialogAdminInput(ErrorCorrectionAdapter.this.context, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.c6
                        @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                        public final void mBianjiInfaceInput(String str, String str2, String str3, String str4) {
                            this.f14354a.lambda$onClickStateChange$7(hotBean, str, str2, str3, str4);
                        }
                    }, hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num()).show();
                    break;
                case 7:
                    new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.d6
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                            this.f14397a.lambda$onClickStateChange$9(hotBean, view, str, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 8:
                    new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.e6
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                            this.f14433a.lambda$onClickStateChange$10(hotBean, str, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 9:
                    ProjectApp.noteContent = hotBean.getContent();
                    if (TextUtils.isEmpty(hotBean.getVideo_id()) && !TextUtils.isEmpty(hotBean.getC_imgs().getS_img())) {
                        ProjectApp.noteSmellImage = hotBean.getC_imgs().getS_img();
                        ProjectApp.noteBigImage = hotBean.getC_imgs().getB_img();
                    }
                    new DialogNoteInput(ErrorCorrectionAdapter.this.context, ErrorCorrectionAdapter.this.module_type + "", hotBean.getObj_id() + "", "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.adapter.f6
                        @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                        public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                            ErrorCorrectionAdapter.AnonymousClass4.lambda$onClickStateChange$11(hotBean, questionNoteBean);
                        }
                    }).show();
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$2(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$3(CommentBean.DataBean.HotBean hotBean, String str) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$4(CommentBean.DataBean.HotBean hotBean, String str) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), str, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$5(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            boolean z2 = true;
            if (i2 == 0) {
                new PopInputWindow(ErrorCorrectionAdapter.this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.h6
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14554a.lambda$onClickStateChange$1(hotBean, str);
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(ErrorCorrectionAdapter.this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.x5
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f15162a.lambda$onClickStateChange$3(hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(ErrorCorrectionAdapter.this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.y5
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f15196a.lambda$onClickStateChange$4(hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            Context context = ErrorCorrectionAdapter.this.context;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.i6
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14593a.lambda$onClickStateChange$2(hotBean, str, str2, str3);
                }
            };
            String content = hotBean.getContent();
            String str = "正在编辑" + hotBean.getNickname() + "的评论...";
            if (!hotBean.getModule_type().equals("1") && !hotBean.getModule_type().equals("4")) {
                z2 = false;
            }
            new DialogInput(context, ondialogclicklistener, content, str, z2, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId()).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$6(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$7(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
            ErrorCorrectionAdapter.this.mBianjiDataVisition(hotBean.getId(), str, str2, str3, str4, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClickStateChange$8(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean hotBean, String str, String str2, PopupComWindow popupComWindow2) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                popupComWindow2.dismiss();
            }
            HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$9(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(ErrorCorrectionAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.z5
                @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                    ErrorCorrectionAdapter.AnonymousClass4.lambda$onClickStateChange$8(popupComWindow, hotBean, str, str2, popupComWindow2);
                }
            }).showPopUp(view);
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onClickStateChange(final View view) {
            boolean zEquals = UserConfig.getUserId().equals(this.val$mCommentBean.getUser_id());
            if ("1".equals(this.val$mCommentBean.getStatus())) {
                CommonUtil.mToastShow(ErrorCorrectionAdapter.this.context);
                return;
            }
            int i2 = TextUtils.isEmpty(this.val$mCommentBean.getReply_num()) ? 0 : Integer.parseInt(this.val$mCommentBean.getReply_num());
            Context context = ErrorCorrectionAdapter.this.context;
            String content = this.val$mCommentBean.getContent();
            String nickname = this.val$mCommentBean.getNickname();
            String str = this.val$mCommentBean.getIs_anonymous() + "";
            String str2 = ErrorCorrectionAdapter.this.module_type + "";
            String delete_skill = this.val$mCommentBean.getDelete_skill();
            String id = this.val$mCommentBean.getId();
            final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
            new XPopup.Builder(ErrorCorrectionAdapter.this.context).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(context, content, nickname, zEquals ? 1 : 0, str, str2, delete_skill, id, i2, new onDialogShareClickListener() { // from class: com.psychiatrygarden.adapter.g6
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i3) {
                    this.f14509a.lambda$onClickStateChange$12(hotBean, view, i3);
                }
            })).show();
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onExpandStateChanged(boolean isExpanded) {
            this.val$mCommentBean.setIs_showValue(isExpanded);
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.ErrorCorrectionAdapter$7, reason: invalid class name */
    public class AnonymousClass7 implements CommentListenter {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass7(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commListReply$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
                bundle.putString("content", str);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", replyBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                ErrorCorrectionAdapter.this.putComment(bundle, 1, hotBean);
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", replyBean.getUser_id());
            bundle2.putString("module_type", ErrorCorrectionAdapter.this.module_type + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", replyBean.getId());
            bundle2.putString("reply_primary_id", replyBean.getReply_primary_id());
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 1);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(ErrorCorrectionAdapter.this.context, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            ErrorCorrectionAdapter.this.mActivity.startActivityForResult(intent, 0);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListOppose(String moudle_type, String is_Oppose, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 3;
            Bundle bundle = new Bundle();
            bundle.putString("is_Oppose", is_Oppose);
            bundle.putString("id", id);
            messageObtain.setData(bundle);
            ErrorCorrectionAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraise(String moudle_type, String is_praise, View v2, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = v2;
            messageObtain.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("is_praise", is_praise);
            bundle.putString("id", id);
            messageObtain.setData(bundle);
            ErrorCorrectionAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraiseFaile() {
            NewToast.showShort(ErrorCorrectionAdapter.this.context, "您已经点赞了", 0).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListReply(final CommentBean.DataBean.HotBean.ReplyBean cmt, View v2) {
            if (Integer.parseInt(cmt.getReply_num()) >= 1) {
                Intent intent = new Intent(ErrorCorrectionAdapter.this.context, (Class<?>) CommentReplyActivity.class);
                intent.putExtra("is_replybean", true);
                intent.putExtra("module_type", ErrorCorrectionAdapter.this.module_type);
                intent.putExtra("comment_type", ErrorCorrectionAdapter.this.comment_type);
                intent.putExtra("bean", cmt);
                if (v2 == null) {
                    intent.putExtra("isShowVideo", "0");
                } else {
                    intent.putExtra("isShowVideo", "1");
                }
                intent.putExtra("isVisiable", true);
                ErrorCorrectionAdapter.this.context.startActivity(intent);
                return;
            }
            if (cmt.getIs_del().equals("1")) {
                NewToast.showShort(ErrorCorrectionAdapter.this.context, "评论已删除", 0).show();
                return;
            }
            Context context = ErrorCorrectionAdapter.this.context;
            final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
            new DialogInput(context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.j6
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14637a.lambda$commListReply$0(cmt, hotBean, str, str2, str3);
                }
            }, "", "回复" + cmt.getNickname(), this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4")).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListSupport(String moudle_type, String is_Support, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("is_praise", is_Support);
            bundle.putString("id", id);
            messageObtain.setData(bundle);
            ErrorCorrectionAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commentListenerData(CommentBean.DataBean.HotBean.ReplyBean commListBean, View v2) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = v2;
            messageObtain.what = 1;
            Bundle bundle = new Bundle();
            bundle.putSerializable("commListBean", commListBean);
            bundle.putSerializable("commListBeans", this.val$mCommentBean);
            bundle.putString("reply_primary_id", this.val$mCommentBean.getReply_primary_id().toString());
            messageObtain.setData(bundle);
            ErrorCorrectionAdapter.this.mHandler.sendMessage(messageObtain);
        }
    }

    public static class ViewHolder {
        ImageView adimgs;
        TextView commentList_item_tv_praise;
        TextView commentList_item_tv_school;
        TextView commentList_item_tv_userName;
        CircleImageView commentList_item_userIcon;
        TextView group_name;
        TextView group_nametv;
        ImageView imageView2_praiuse;
        TextView isreadtext;
        ImageView isverauth;
        ImageView iv_delete_ad;
        ImageView iv_elite;
        CircleImageView jiav;
        LinearLayout lin_praise;
        RelativeLayout linciew;
        LinearLayout line_null;
        LinearLayout lineadview;
        LinearLayout lineremen;
        LinearLayout ll_main_content;
        TextView mTvLookMore;
        MyExpendView myexptervew;
        NineGridTestLayout ningrids;
        ImageView reviceimg;
        FloorView subFloors;
        LinearLayout threeview;
        TextView tv_null;
        TextView tv_oppose;
        TextView tv_question;
        TextView tv_reply;
        TextView tv_support;
        ImageView vipimgid;
    }

    public ErrorCorrectionAdapter(Context context, List<CommentBean.DataBean.HotBean> list, List<CommentBean.DataBean.HotBean> time_line, int module_type, String comment_type, String question_id, BaseActivity mActivity, boolean isNewComzantong) {
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
        this.time_line = time_line;
        this.module_type = module_type;
        this.comment_type = comment_type;
        this.question_id = question_id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserInfo(Context mContext, String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", user_id);
        YJYHttpUtils.get(mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.14
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
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        CommonUtil.mPutChatData(ErrorCorrectionAdapter.this.mActivity, jSONObject.optJSONObject("data").optString("user_uuid"), jSONObject.optJSONObject("data").optString("nickname"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$25() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(int i2, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, Long.valueOf(System.currentTimeMillis()), this.mActivity);
        this.list.remove(i2);
        setList(this.list);
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$1(JSONObject jSONObject, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        PublicMethodActivity.getInstance().mToActivity(jSONObject.optString(PushConstants.EXTRA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$10(final CommentBean.DataBean.HotBean hotBean, View view) {
        if (hotBean.getStatus().equals("1")) {
            CommonUtil.mToastShow(this.context);
            return;
        }
        if (Integer.parseInt(hotBean.getReply_num()) <= 0) {
            new DialogInput(this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.o4
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14828a.lambda$getView$9(hotBean, str, str2, str3);
                }
            }, "", "回复" + hotBean.getNickname(), hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4")).show();
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) CommentReplyActivity.class);
        intent.putExtra("is_replybean", false);
        intent.putExtra("comment_type", this.comment_type);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("bean", hotBean);
        intent.putExtra("isVisiable", true);
        intent.putExtra("isShowVideo", "1");
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$11(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (hotBean.getIs_logout().equals("1")) {
            ToastUtil.shortToast(this.context, "该用户已注销");
            return;
        }
        if (hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.putExtra("jiav", hotBean.getUser_identity());
        intent.addFlags(67108864);
        this.context.startActivity(intent);
        ((Activity) this.context).overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$12(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (hotBean.getIs_logout().equals("1")) {
            ToastUtil.shortToast(this.context, "该用户已注销");
            return;
        }
        if (hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.putExtra("jiav", hotBean.getUser_identity());
        intent.addFlags(67108864);
        this.context.startActivity(intent);
        ((Activity) this.context).overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$13(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("to_user_id", hotBean.getUser_id());
            bundle.putString("module_type", this.module_type + "");
            bundle.putString("content", str);
            bundle.putString("parent_id", hotBean.getId());
            bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            putComment(bundle, 2, hotBean);
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("to_user_id", hotBean.getUser_id());
        bundle2.putString("module_type", this.module_type + "");
        bundle2.putString("content", str);
        bundle2.putString("parent_id", hotBean.getId());
        bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
        bundle2.putSerializable("mHotbean", hotBean);
        bundle2.putInt("type", 2);
        bundle2.putInt("result", 0);
        bundle2.putString("b_img", str2);
        bundle2.putString("s_img", str3);
        Intent intent = new Intent(this.context, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle2);
        this.mActivity.startActivityForResult(intent, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$14(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$15(CommentBean.DataBean.HotBean hotBean, String str) {
        mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$16(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
        boolean z2 = true;
        if (i2 == 0) {
            new PopInputWindow(this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.5
                @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                public void mInputDataListter(String txtSter) {
                    ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                    String id = hotBean.getId();
                    String content = hotBean.getContent();
                    String praise_num = hotBean.getPraise_num();
                    String oppose_num = hotBean.getOppose_num();
                    CommentBean.DataBean.HotBean hotBean2 = hotBean;
                    errorCorrectionAdapter.mBianjiDataVisition(id, txtSter, content, praise_num, oppose_num, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                }
            }).showPopUp(view);
            return;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                new PopInputWindow(this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.w4
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f15130a.lambda$getView$15(hotBean, str);
                    }
                }).showPopUp(view);
                return;
            } else {
                if (i2 != 3) {
                    return;
                }
                new PopInputWindow(this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.6
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public void mInputDataListter(String txtSter) {
                        ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                        String id = hotBean.getId();
                        String ctime_timestamp = hotBean.getCtime_timestamp();
                        String content = hotBean.getContent();
                        String praise_num = hotBean.getPraise_num();
                        CommentBean.DataBean.HotBean hotBean2 = hotBean;
                        errorCorrectionAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                    }
                }).showPopUp(view);
                return;
            }
        }
        Context context = this.context;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.l4
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f14703a.lambda$getView$14(hotBean, str, str2, str3);
            }
        };
        String content = hotBean.getContent();
        String str = "正在编辑" + hotBean.getNickname() + "的评论...";
        if (!hotBean.getModule_type().equals("1") && !hotBean.getModule_type().equals("4")) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, content, str, z2, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId()).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$17(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$18(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
        mBianjiDataVisition(hotBean.getId(), str, str2, str3, str4, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$19(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean hotBean, String str, String str2, PopupComWindow popupComWindow2) {
        if (popupComWindow != null && popupComWindow.isShowing()) {
            popupComWindow.dismiss();
        }
        if (popupComWindow2 != null && popupComWindow2.isShowing()) {
            popupComWindow2.dismiss();
        }
        HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$2(CommentBean.DataBean.HotBean hotBean, View view) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AllErrorCorrectionActivity.class);
        intent.putExtra(aq.f22519d, this.question_id + "");
        intent.putExtra("title", hotBean.getName() + "");
        intent.putExtra("module_type", this.module_type);
        this.mActivity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$20(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
        new PopupComWindow(this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.n4
            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
            public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                ErrorCorrectionAdapter.lambda$getView$19(popupComWindow, hotBean, str, str2, popupComWindow2);
            }
        }).showPopUp(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$21(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
        if (popupComWindow != null && popupComWindow.isShowing()) {
            popupComWindow.dismiss();
        }
        HttpRequstData.getIntance(this.context).postReportData(hotBean.getId(), str, this.module_type + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$22(CommentBean.DataBean.HotBean hotBean, QuestionNoteBean questionNoteBean) {
        EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, hotBean.getObj_id()));
        ProjectApp.noteContent = "";
        ProjectApp.noteSmellImage = "";
        ProjectApp.noteBigImage = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$23(final CommentBean.DataBean.HotBean hotBean, final View view, int i2) {
        switch (i2) {
            case 0:
                new DialogInput(this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.d5
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14395a.lambda$getView$13(hotBean, str, str2, str3);
                    }
                }, "", "回复" + hotBean.getNickname(), hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4")).show();
                break;
            case 1:
                if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    new XPopup.Builder(this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(this.mActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.e5
                        @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                        public final void mBianjiData(int i3) {
                            this.f14430a.lambda$getView$16(hotBean, view, i3);
                        }
                    })).show();
                    break;
                } else {
                    new DialogInput(this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.f5
                        @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                        public final void onclickStringBack(String str, String str2, String str3) {
                            this.f14468a.lambda$getView$17(hotBean, str, str2, str3);
                        }
                    }, hotBean.getContent(), "正在编辑" + hotBean.getNickname() + "的评论...", hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4"), hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId()).show();
                    break;
                }
                break;
            case 2:
                deleteComm(hotBean.getId(), hotBean.getObj_id(), hotBean.getReply_primary_id(), 2, hotBean);
                break;
            case 3:
                ((ClipboardManager) this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", hotBean.getContent()));
                NewToast.showShort(this.context, "复制成功", 0).show();
                break;
            case 4:
                getUserInfo(this.context, hotBean.getUser_id());
                break;
            case 5:
                if ((TextUtils.isEmpty(hotBean.getIs_essence()) ? "0" : hotBean.getIs_essence()).equals("1")) {
                    try {
                        hotBean.setIs_essence("0");
                        for (int i3 = 0; i3 < this.list.size(); i3++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply = this.list.get(i2).getReply();
                            if (reply != null && reply.size() > 0) {
                                for (int i4 = 0; i4 < reply.size(); i4++) {
                                    if (hotBean.getId().equals(reply.get(i4).getId())) {
                                        reply.get(i4).setIs_essence("0");
                                    }
                                }
                            }
                        }
                        notifyDataSetChanged();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    NewToast.showShort(this.context, "取消加精成功", 0).show();
                    break;
                } else {
                    try {
                        hotBean.setIs_essence("1");
                        for (int i5 = 0; i5 < this.list.size(); i5++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = this.list.get(i2).getReply();
                            if (reply2 != null && reply2.size() > 0) {
                                for (int i6 = 0; i6 < reply2.size(); i6++) {
                                    if (hotBean.getId().equals(reply2.get(i6).getId())) {
                                        reply2.get(i6).setIs_essence("1");
                                    }
                                }
                            }
                        }
                        notifyDataSetChanged();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    putDigest(hotBean.getId());
                    break;
                }
                break;
            case 6:
                new DialogAdminInput(this.context, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.g5
                    @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                    public final void mBianjiInfaceInput(String str, String str2, String str3, String str4) {
                        this.f14507a.lambda$getView$18(hotBean, str, str2, str3, str4);
                    }
                }, hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num()).show();
                break;
            case 7:
                new PopupComWindow(this.mActivity, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.h5
                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                    public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                        this.f14551a.lambda$getView$20(hotBean, view, str, popupComWindow);
                    }
                }).showPopUp(view);
                break;
            case 8:
                new PopupComWindow(this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.i5
                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                    public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                        this.f14591a.lambda$getView$21(hotBean, str, popupComWindow);
                    }
                }).showPopUp(view);
                break;
            case 9:
                ProjectApp.noteContent = hotBean.getContent();
                if (TextUtils.isEmpty(hotBean.getVideo_id()) && !TextUtils.isEmpty(hotBean.getC_imgs().getS_img())) {
                    ProjectApp.noteSmellImage = hotBean.getC_imgs().getS_img();
                    ProjectApp.noteBigImage = hotBean.getC_imgs().getB_img();
                }
                new DialogNoteInput(this.context, this.module_type + "", hotBean.getObj_id() + "", "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.adapter.j5
                    @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                    public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                        ErrorCorrectionAdapter.lambda$getView$22(hotBean, questionNoteBean);
                    }
                }).show();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$24(final CommentBean.DataBean.HotBean hotBean, final View view) {
        boolean zEquals = UserConfig.getUserId().equals(hotBean.getUser_id());
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(this.context);
            return;
        }
        int i2 = TextUtils.isEmpty(hotBean.getReply_num()) ? 0 : Integer.parseInt(hotBean.getReply_num());
        new XPopup.Builder(this.context).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(this.context, hotBean.getContent(), hotBean.getNickname(), zEquals ? 1 : 0, hotBean.getIs_anonymous() + "", this.module_type + "", hotBean.getDelete_skill(), hotBean.getId(), i2, new onDialogShareClickListener() { // from class: com.psychiatrygarden.adapter.m4
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i3) {
                this.f14748a.lambda$getView$23(hotBean, view, i3);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$3(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        PublicMethodActivity.getInstance().mCommentMethod(hotBean.getModule_type() + "", hotBean.getTarget_params());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$4(CommentBean.DataBean.HotBean hotBean) {
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(this.context);
            return;
        }
        Intent intent = new Intent(this.context, (Class<?>) CommentReplyActivity.class);
        intent.putExtra("is_replybean", false);
        intent.putExtra("comment_type", this.comment_type);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("bean", hotBean);
        intent.putExtra("isVisiable", true);
        intent.putExtra("isShowVideo", "0");
        this.context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$5(CommentBean.DataBean.HotBean hotBean, View view) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AllErrorCorrectionActivity.class);
        intent.putExtra(aq.f22519d, this.question_id + "");
        intent.putExtra("title", hotBean.getName() + "");
        intent.putExtra("module_type", this.module_type);
        this.mActivity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$6(View view) {
        ToastUtil.shortToast(this.context, "已认证");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$7(CommentBean.DataBean.HotBean hotBean, ViewHolder viewHolder, View view) {
        if (hotBean.getIs_oppose().equals("1")) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(this.context, "评论已删除", 0).show();
            return;
        }
        String str = "0";
        if (hotBean.getIs_praise().equals("1")) {
            putPraise(hotBean.getId(), "0");
            hotBean.setIs_praise("0");
            try {
                String praise_num = hotBean.getPraise_num();
                if (TextUtils.isEmpty(praise_num)) {
                    praise_num = "0";
                }
                if (praise_num.trim().equals("0")) {
                    hotBean.setPraise_num("0");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Integer.parseInt(praise_num) - 1);
                    sb.append("");
                    hotBean.setPraise_num(sb.toString());
                }
                viewHolder.tv_support.setText("赞同(" + hotBean.getPraise_num() + ")");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(viewHolder.tv_support, 0);
            putPraise(hotBean.getId(), "1");
            hotBean.setIs_praise("1");
            try {
                String praise_num2 = hotBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                hotBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                viewHolder.tv_support.setText("已赞同(" + hotBean.getPraise_num() + ")");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$8(CommentBean.DataBean.HotBean hotBean, ViewHolder viewHolder, View view) {
        if (hotBean.getIs_praise().equals("1")) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(this.context, "评论已删除", 0).show();
            return;
        }
        String str = "0";
        if (hotBean.getIs_oppose().equals("1")) {
            putOppose(hotBean.getId(), "0");
            hotBean.setIs_oppose("0");
            try {
                String oppose_num = hotBean.getOppose_num();
                if (TextUtils.isEmpty(oppose_num)) {
                    oppose_num = "0";
                }
                if (oppose_num.trim().equals("0")) {
                    hotBean.setOppose_num("0");
                } else {
                    hotBean.setOppose_num((Integer.parseInt(oppose_num) - 1) + "");
                }
                viewHolder.tv_oppose.setText("反对(" + hotBean.getOppose_num() + ")");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(viewHolder.tv_oppose, 1);
            putOppose(hotBean.getId(), "1");
            hotBean.setIs_oppose("1");
            try {
                String oppose_num2 = hotBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num2)) {
                    str = oppose_num2;
                }
                hotBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                viewHolder.tv_oppose.setText("已反对(" + hotBean.getOppose_num() + ")");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$9(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("to_user_id", hotBean.getUser_id());
            bundle.putString("module_type", this.module_type + "");
            bundle.putString("content", str);
            bundle.putString("parent_id", hotBean.getId());
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
            putComment(bundle, 2, hotBean);
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("to_user_id", hotBean.getUser_id());
        bundle2.putString("module_type", this.module_type + "");
        bundle2.putString("content", str);
        bundle2.putString("parent_id", hotBean.getId());
        bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
        bundle2.putSerializable("mHotbean", hotBean);
        bundle2.putInt("type", 2);
        bundle2.putInt("result", 0);
        bundle2.putString("b_img", str2);
        bundle2.putString("s_img", str3);
        Intent intent = new Intent(this.context, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle2);
        this.mActivity.startActivityForResult(intent, 0);
    }

    public void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        final PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.k5
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ErrorCorrectionAdapter.lambda$Toast_pop$25();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        v2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        v2.getLocalVisibleRect(rect);
        if (flag == 0) {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.12
            @Override // java.lang.Runnable
            public void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    public void deleteComm(final String id, String obj_id, String reply_primary_id, final Integer valueInt, final CommentBean.DataBean.HotBean mHotBean) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", obj_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("reply_primary_id", reply_primary_id);
        ajaxParams.put("id", id);
        ajaxParams.put("confirm", "1");
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mDeleteUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(ErrorCorrectionAdapter.this.context, "请求服务器失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass13) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        if (valueInt.intValue() == 1) {
                            for (int i2 = 0; i2 < ErrorCorrectionAdapter.this.list.size(); i2++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i3 = 0; i3 < ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply().size(); i3++) {
                                        if (id.equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply().get(i3).getId())) {
                                            ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply().get(i3).setContent("已删除");
                                            ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply().get(i3).setC_imgs(new ImagesBean());
                                            ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i2)).getReply().get(i3).setIs_del("1");
                                        }
                                    }
                                }
                            }
                            EventBus.getDefault().post("delReplyAndLoadData");
                        } else {
                            ArrayList arrayList = new ArrayList();
                            for (int i4 = 0; i4 < ErrorCorrectionAdapter.this.list.size(); i4++) {
                                if (((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i4)).getReply() != null && ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i4)).getReply().size() > 0) {
                                    for (int i5 = 0; i5 < ((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i4)).getReply().size(); i5++) {
                                        if (mHotBean.getReply().equals(((CommentBean.DataBean.HotBean) ErrorCorrectionAdapter.this.list.get(i4)).getReply())) {
                                            arrayList.add(id);
                                        }
                                    }
                                }
                            }
                            if (arrayList.size() > 1) {
                                ErrorCorrectionAdapter.this.list.remove(mHotBean);
                            } else {
                                Iterator it = ErrorCorrectionAdapter.this.list.iterator();
                                while (it.hasNext()) {
                                    if (TextUtils.equals(((CommentBean.DataBean.HotBean) it.next()).getId(), id)) {
                                        it.remove();
                                    }
                                }
                            }
                        }
                    }
                    EventBus.getDefault().post("mCommentResult");
                    NewToast.showShort(ErrorCorrectionAdapter.this.context, jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    public List<CommentBean.DataBean.HotBean> getList() {
        return this.list;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View converView, ViewGroup viewGrop) {
        View viewInflate;
        final ViewHolder viewHolder;
        float width;
        float width2;
        float f2;
        if (converView == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.context).inflate(R.layout.activity_comment_list2_new, (ViewGroup) null);
            viewHolder.ningrids = (NineGridTestLayout) viewInflate.findViewById(R.id.ningrids);
            viewHolder.group_name = (TextView) viewInflate.findViewById(R.id.group_name);
            viewHolder.linciew = (RelativeLayout) viewInflate.findViewById(R.id.linciew);
            viewHolder.commentList_item_tv_userName = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_userName);
            viewHolder.commentList_item_tv_school = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_school);
            viewHolder.subFloors = (FloorView) viewInflate.findViewById(R.id.foor);
            viewHolder.commentList_item_userIcon = (CircleImageView) viewInflate.findViewById(R.id.commentList_item_userIcon);
            viewHolder.commentList_item_tv_praise = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_praise);
            viewHolder.imageView2_praiuse = (ImageView) viewInflate.findViewById(R.id.imageView2_praiuse);
            viewHolder.lin_praise = (LinearLayout) viewInflate.findViewById(R.id.lin_praise);
            viewHolder.myexptervew = (MyExpendView) viewInflate.findViewById(R.id.myexptervew);
            viewHolder.iv_elite = (ImageView) viewInflate.findViewById(R.id.iv_elite);
            viewHolder.tv_support = (TextView) viewInflate.findViewById(R.id.tv_support);
            viewHolder.tv_oppose = (TextView) viewInflate.findViewById(R.id.tv_oppose);
            viewHolder.tv_reply = (TextView) viewInflate.findViewById(R.id.tv_reply);
            viewHolder.line_null = (LinearLayout) viewInflate.findViewById(R.id.line_null);
            viewHolder.tv_null = (TextView) viewInflate.findViewById(R.id.tv_null);
            viewHolder.isreadtext = (TextView) viewInflate.findViewById(R.id.isreadtext);
            viewHolder.jiav = (CircleImageView) viewInflate.findViewById(R.id.jiav);
            viewHolder.vipimgid = (ImageView) viewInflate.findViewById(R.id.vipimgid);
            viewHolder.isverauth = (ImageView) viewInflate.findViewById(R.id.isverauth);
            viewHolder.threeview = (LinearLayout) viewInflate.findViewById(R.id.threeview);
            viewHolder.mTvLookMore = (TextView) viewInflate.findViewById(R.id.tv_look_more);
            viewHolder.lineremen = (LinearLayout) viewInflate.findViewById(R.id.lineremen);
            viewHolder.group_nametv = (TextView) viewInflate.findViewById(R.id.group_nametv);
            viewHolder.reviceimg = (ImageView) viewInflate.findViewById(R.id.reviceimg);
            viewHolder.tv_question = (TextView) viewInflate.findViewById(R.id.tv_question);
            viewHolder.iv_delete_ad = (ImageView) viewInflate.findViewById(R.id.iv_delete_ad);
            viewHolder.adimgs = (ImageView) viewInflate.findViewById(R.id.adimgs);
            viewHolder.ll_main_content = (LinearLayout) viewInflate.findViewById(R.id.ll_main_content);
            viewHolder.lineadview = (LinearLayout) viewInflate.findViewById(R.id.lineadview);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = converView;
            viewHolder = (ViewHolder) converView.getTag();
        }
        final CommentBean.DataBean.HotBean item = getItem(position);
        if (TextUtils.isEmpty(item.getAds())) {
            viewHolder.lineadview.setVisibility(8);
            viewHolder.ll_main_content.setVisibility(0);
            if (item.getType() == 1) {
                viewHolder.group_name.setVisibility(8);
                viewHolder.group_nametv.setText(item.getName());
                viewHolder.lineremen.setVisibility(0);
                viewHolder.linciew.setVisibility(8);
                viewHolder.threeview.setVisibility(8);
                viewHolder.line_null.setVisibility(8);
                viewHolder.lineremen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.u4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15064c.lambda$getView$2(item, view);
                    }
                });
                viewHolder.reviceimg.setVisibility(8);
            } else {
                viewHolder.group_name.setVisibility(8);
                viewHolder.lineremen.setVisibility(8);
                if (TextUtils.isEmpty(item.getTo_from())) {
                    viewHolder.tv_question.setVisibility(8);
                } else {
                    viewHolder.tv_question.setVisibility(0);
                }
                viewHolder.tv_question.setText(item.getTo_from());
                viewHolder.tv_question.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.v4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ErrorCorrectionAdapter.lambda$getView$3(item, view);
                    }
                });
                if ("0".equals(item.getIs_adopt())) {
                    viewHolder.reviceimg.setVisibility(8);
                    viewHolder.reviceimg.setImageResource(0);
                } else if ("1".equals(item.getIs_adopt())) {
                    viewHolder.reviceimg.setVisibility(0);
                    viewHolder.reviceimg.setImageResource(R.drawable.yesreviceimg);
                } else if ("2".equals(item.getIs_adopt())) {
                    viewHolder.reviceimg.setVisibility(0);
                    viewHolder.reviceimg.setImageResource(R.drawable.noreviceimg);
                }
                if (item.getC_imgs() != null && !TextUtils.isEmpty(item.getC_imgs().getS_img())) {
                    float s_width = item.getC_imgs().getS_width();
                    float s_height = item.getC_imgs().getS_height();
                    if (s_height >= s_width * 2.0f) {
                        width = (viewHolder.ningrids.getWidth() / 3) * 2.0f;
                    } else if (s_height >= s_width) {
                        width = (s_height * ((viewHolder.ningrids.getWidth() / 2) - 50)) / s_width;
                    } else if (s_width >= s_height * 2.0f) {
                        width = ((viewHolder.ningrids.getWidth() * 2) / 3) / 2.0f;
                    } else {
                        if (s_width / s_height <= 1.1d) {
                            width2 = (viewHolder.ningrids.getWidth() / 5) * 3;
                            f2 = 4.0f;
                        } else {
                            width2 = (viewHolder.ningrids.getWidth() / 5) * 3;
                            f2 = 3.0f;
                        }
                        width = (width2 * f2) / 5.0f;
                    }
                    ViewGroup.LayoutParams layoutParams = viewHolder.ningrids.getLayoutParams();
                    layoutParams.height = (int) width;
                    viewHolder.ningrids.setLayoutParams(layoutParams);
                }
                if (TextUtils.isEmpty(item.getVideo_id())) {
                    item.getC_imgs().setVideoId("");
                } else {
                    item.getC_imgs().setVideoId(item.getVideo_id() + "");
                }
                viewHolder.ningrids.setmImagesBean(item.getC_imgs(), 1);
                viewHolder.ningrids.setDownImgUrl(item.getImg_watermark() + "");
                viewHolder.ningrids.setIsShowAll(false);
                viewHolder.ningrids.setmVideoClickIml(new NineGridLayout.VideoClickIml() { // from class: com.psychiatrygarden.adapter.x4
                    @Override // com.psychiatrygarden.gradview.NineGridLayout.VideoClickIml
                    public final void mVideoClickData() {
                        this.f15160a.lambda$getView$4(item);
                    }
                });
                viewHolder.threeview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.y4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15194c.lambda$getView$5(item, view);
                    }
                });
                if (item.getId() == null || item.getId().equals("")) {
                    if (item.getOtherView() == 3) {
                        viewHolder.linciew.setVisibility(8);
                        viewHolder.line_null.setVisibility(8);
                        viewHolder.threeview.setVisibility(0);
                        viewHolder.reviceimg.setVisibility(8);
                        viewHolder.mTvLookMore.setText("查看全部采纳纠错");
                    } else {
                        viewHolder.linciew.setVisibility(8);
                        viewHolder.line_null.setVisibility(0);
                        viewHolder.threeview.setVisibility(8);
                        viewHolder.tv_null.setText(item.getContent());
                    }
                    return viewInflate;
                }
                viewHolder.linciew.setVisibility(0);
                viewHolder.line_null.setVisibility(8);
                viewHolder.threeview.setVisibility(8);
                if (Integer.parseInt(item.getPraise_num()) > Integer.parseInt(item.getOppose_num())) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.green));
                        viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                    } else {
                        viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.green_theme_night));
                        viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    }
                } else if (Integer.parseInt(item.getPraise_num()) < Integer.parseInt(item.getOppose_num())) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                        viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.app_theme_red));
                    } else {
                        viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                        viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.red_theme_night));
                    }
                } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                } else {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                }
                if (item.getIs_svip().equals("1")) {
                    viewHolder.vipimgid.setVisibility(0);
                    viewHolder.vipimgid.setImageResource(R.drawable.svip333img);
                } else if (item.getIs_vip().equals("1")) {
                    viewHolder.vipimgid.setVisibility(0);
                    viewHolder.vipimgid.setImageResource(R.drawable.vipimg);
                } else {
                    viewHolder.vipimgid.setVisibility(8);
                }
                if (item.getIs_vip().equals("1")) {
                    viewHolder.vipimgid.setVisibility(0);
                } else {
                    viewHolder.vipimgid.setVisibility(8);
                }
                if (item.getIs_praise().equals("1")) {
                    viewHolder.tv_support.setText("已赞同(" + item.getPraise_num() + ")");
                } else {
                    viewHolder.tv_support.setText("赞同(" + item.getPraise_num() + ")");
                }
                if ((item.getIs_oppose() + "").equals("1")) {
                    viewHolder.tv_oppose.setText("已反对(" + item.getOppose_num() + ")");
                } else {
                    viewHolder.tv_oppose.setText("反对(" + item.getOppose_num() + ")");
                }
                if (item.getUser_identity().equals("1") || item.getIs_authentication().equals("1")) {
                    viewHolder.jiav.setVisibility(8);
                    viewHolder.isverauth.setVisibility(0);
                    viewHolder.isverauth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.z4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f15229c.lambda$getView$6(view);
                        }
                    });
                } else {
                    viewHolder.jiav.setVisibility(8);
                    viewHolder.isverauth.setVisibility(8);
                }
                if (item.getReply_num().trim().equals("0")) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.gray_font_new2));
                    } else {
                        viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    }
                    viewHolder.tv_reply.setText("回复");
                    viewHolder.tv_reply.setBackgroundResource(R.color.transparent);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    viewHolder.tv_reply.setPadding(0, 10, 0, 10);
                    viewHolder.tv_reply.setLayoutParams(layoutParams2);
                } else {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.app_theme_gray));
                        viewHolder.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                    } else {
                        viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.question_color_night));
                        viewHolder.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                    }
                    viewHolder.tv_reply.setText(String.format(Locale.CHINA, "%s 回复", item.getReply_num()));
                }
                viewHolder.tv_support.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.a5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14278c.lambda$getView$7(item, viewHolder, view);
                    }
                });
                viewHolder.tv_oppose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.b5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14317c.lambda$getView$8(item, viewHolder, view);
                    }
                });
                viewHolder.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.c5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14352c.lambda$getView$10(item, view);
                    }
                });
                viewHolder.commentList_item_userIcon.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.q4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14904c.lambda$getView$11(item, view);
                    }
                });
                viewHolder.commentList_item_tv_userName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.r4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14948c.lambda$getView$12(item, view);
                    }
                });
                if ((TextUtils.isEmpty(item.getIs_essence()) ? "0" : item.getIs_essence()).equals("0")) {
                    viewHolder.iv_elite.setVisibility(8);
                } else {
                    viewHolder.iv_elite.setVisibility(8);
                }
                viewHolder.commentList_item_tv_userName.setTextColor(Color.parseColor(item.getUser_identity_color()));
                viewHolder.commentList_item_tv_userName.setText(item.getNickname());
                viewHolder.commentList_item_tv_praise.setText(item.getPraise_num());
                viewHolder.myexptervew.setIs_del(item.getIs_del());
                viewHolder.myexptervew.setText(item.getContent(), item.is_showValue());
                viewHolder.myexptervew.setListener(new AnonymousClass4(item));
                viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.s4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14990c.lambda$getView$24(item, view);
                    }
                });
                viewHolder.commentList_item_tv_school.setText(item.getSchool() + " " + item.getCtime());
                if (TextUtils.isEmpty(item.getAvatar() + "")) {
                    viewHolder.commentList_item_userIcon.setImageResource(R.drawable.empty_photo);
                } else {
                    GlideUtils.loadImage(viewHolder.commentList_item_userIcon.getContext(), item.getAvatar(), viewHolder.commentList_item_userIcon);
                }
                SubComments subComments = new SubComments(item.getReply());
                if (subComments.size() > 0) {
                    viewHolder.subFloors.setVisibility(0);
                } else {
                    viewHolder.subFloors.setVisibility(8);
                }
                viewHolder.subFloors.setComments(subComments, this.isOkTrue, this);
                viewHolder.subFloors.setFactory(new SubFloorFactory());
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    viewHolder.subFloors.setBoundDrawer(this.context.getResources().getDrawable(R.drawable.bondcomm));
                } else {
                    viewHolder.subFloors.setBoundDrawer(this.context.getResources().getDrawable(R.drawable.bound_night));
                }
                viewHolder.subFloors.setmCommentListenter(new AnonymousClass7(item));
                viewHolder.subFloors.init();
            }
        } else {
            try {
                final JSONObject jSONObject = new JSONObject(item.getAds());
                if (jSONObject.optString("force").equals("1")) {
                    viewHolder.iv_delete_ad.setVisibility(8);
                } else {
                    viewHolder.iv_delete_ad.setVisibility(0);
                    viewHolder.iv_delete_ad.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.p4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f14867c.lambda$getView$0(position, view);
                        }
                    });
                }
                viewHolder.lineadview.setVisibility(0);
                viewHolder.ll_main_content.setVisibility(8);
                Glide.with(this.mActivity).asBitmap().load((Object) GlideUtils.generateUrl(jSONObject.optString("img"))).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.3
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull @NotNull Object resource, @Nullable Transition transition) {
                        onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        double screenWidth = ScreenUtil.getScreenWidth(ErrorCorrectionAdapter.this.mActivity) - CommonUtil.dip2px(ErrorCorrectionAdapter.this.mActivity, 73.0f);
                        if (AndroidBaseUtils.isPad(ErrorCorrectionAdapter.this.mActivity) && AndroidBaseUtils.isCurOriLand(ErrorCorrectionAdapter.this.mActivity)) {
                            screenWidth /= 3.0d;
                        }
                        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) viewHolder.adimgs.getLayoutParams();
                        layoutParams3.width = (int) screenWidth;
                        layoutParams3.height = (int) (resource.getHeight() * (screenWidth / resource.getWidth()));
                        layoutParams3.rightMargin = CommonUtil.dip2px(ErrorCorrectionAdapter.this.mActivity, 20.0f);
                        layoutParams3.leftMargin = CommonUtil.dip2px(ErrorCorrectionAdapter.this.mActivity, 33.0f);
                        viewHolder.adimgs.setLayoutParams(layoutParams3);
                        viewHolder.adimgs.setImageBitmap(resource);
                    }
                });
                viewHolder.adimgs.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.t4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ErrorCorrectionAdapter.lambda$getView$1(jSONObject, view);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return viewInflate;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    public void getputData(Bundle b3) {
        putComment(b3, Integer.valueOf(b3.getInt("type")), (CommentBean.DataBean.HotBean) b3.getSerializable("mHotbean"));
    }

    @Override // com.psychiatrygarden.widget.PinnedSectionListView1.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public void mBianjiDataVisition(String id, final String pushTime, final String pushcontent, final String pushoppn, final String pushno, final CommentBean.DataBean.HotBean.ReplyBean commListBean, final int type, final CommentBean.DataBean.HotBean mHotbean, final String b_img, final String s_img) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("ctime_timestamp", pushTime);
        ajaxParams.put("content", pushcontent);
        ajaxParams.put("praise_num", pushoppn);
        ajaxParams.put("oppose_num", pushno);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        if (!TextUtils.isEmpty(b_img)) {
            if (b_img.contains("http")) {
                ajaxParams.put("b_img", b_img);
                ajaxParams.put("s_img", s_img);
            } else {
                ajaxParams.put("video_id", b_img);
            }
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutBianAdminDataUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.2
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
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(ErrorCorrectionAdapter.this.context, new JSONObject(s2).optString("message"), 0).show();
                        return;
                    }
                    int i2 = type;
                    if (i2 == 1) {
                        commListBean.setCtime(CommonUtil.getDate(pushTime));
                        commListBean.setContent(pushcontent);
                        commListBean.setPraise_num(pushoppn);
                        commListBean.setOppose_num(pushno);
                        commListBean.getC_imgs().setB_img(b_img);
                        commListBean.getC_imgs().setS_img(s_img);
                    } else if (i2 == 2) {
                        mHotbean.setCtime(CommonUtil.getDate(pushTime));
                        mHotbean.setContent(pushcontent);
                        mHotbean.setPraise_num(pushoppn);
                        mHotbean.setOppose_num(pushno);
                        mHotbean.getC_imgs().setB_img(b_img);
                        mHotbean.getC_imgs().setS_img(s_img);
                    }
                    ErrorCorrectionAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putComment(Bundle b3, final Integer changeCode, final CommentBean.DataBean.HotBean mHotBean) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("parent_id", b3.getString("parent_id"));
        ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", b3.getString("to_user_id"));
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
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
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(ErrorCorrectionAdapter.this.context, "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(ErrorCorrectionAdapter.this.context, jSONObject.optString("message"), 0).show();
                        return;
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", ErrorCorrectionAdapter.this.context);
                    if (!jSONObject.optString("data").equals(StrPool.EMPTY_JSON)) {
                        ErrorCorrectionAdapter.this.mH = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        ErrorCorrectionAdapter.this.list.removeAll(ErrorCorrectionAdapter.this.time_line);
                        int iIntValue = changeCode.intValue();
                        if (iIntValue == 1) {
                            ErrorCorrectionAdapter errorCorrectionAdapter = ErrorCorrectionAdapter.this;
                            errorCorrectionAdapter.time_line.add(0, errorCorrectionAdapter.mH);
                        } else if (iIntValue == 2) {
                            ErrorCorrectionAdapter.this.time_line.remove(mHotBean);
                            ErrorCorrectionAdapter errorCorrectionAdapter2 = ErrorCorrectionAdapter.this;
                            errorCorrectionAdapter2.time_line.add(0, errorCorrectionAdapter2.mH);
                        }
                        ErrorCorrectionAdapter.this.list.addAll(ErrorCorrectionAdapter.this.time_line);
                        ErrorCorrectionAdapter.this.notifyDataSetChanged();
                    }
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    EventBus.getDefault().post("mCommentResult");
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    CommonUtil.showFristDialog(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putDigest(String id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("id", id);
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mDigest, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(ErrorCorrectionAdapter.this.context, "加精成功", 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putOppose(String id, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("id", id);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutOpposeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.9
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
                super.onSuccess((AnonymousClass9) s2);
            }
        });
    }

    public void putPraise(String id, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("id", id);
        ajaxParams.put("type", type);
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutPraseUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.ErrorCorrectionAdapter.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
            }
        });
    }

    public void refresh(ArrayList<CommentBean.DataBean.HotBean> arr) {
        setList(arr);
        notifyDataSetChanged();
    }

    public void setList(List<CommentBean.DataBean.HotBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    public void setRefeault(List<CommentBean.DataBean.HotBean> time_line) {
        this.time_line.addAll(time_line);
    }

    @Override // android.widget.Adapter
    public CommentBean.DataBean.HotBean getItem(int position) {
        return this.list.get(position);
    }

    public ErrorCorrectionAdapter(Context context, List<CommentBean.DataBean.HotBean> list, Activity mActivity) {
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
    }
}
