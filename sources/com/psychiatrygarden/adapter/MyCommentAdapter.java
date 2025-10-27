package com.psychiatrygarden.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.adapter.MyCommentAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.bean.SubComments;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.gradview.NineGridLayout;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.HttpRequstData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.OnClickDiogListenter;
import com.psychiatrygarden.interfaceclass.OnClickbianjiListenter;
import com.psychiatrygarden.interfaceclass.OnMInputListenster;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommentListenter;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.FloorView;
import com.psychiatrygarden.widget.MyExpendView;
import com.psychiatrygarden.widget.PopInputWindow;
import com.psychiatrygarden.widget.PopupBianjiComWindow;
import com.psychiatrygarden.widget.PopupComWindow;
import com.psychiatrygarden.widget.PopupWin7Comment;
import com.psychiatrygarden.widget.SubFloorFactory;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyCommentAdapter extends BaseAdapter {
    private String comment_type;
    private boolean isVisiable;
    private boolean is_callMe;
    private String is_prohibit;
    BaseActivity mActivity;
    private Context mContext;
    private List<CommentBean.DataBean.HotBean> mHotBean;
    private boolean mIsSource;
    private String viewType;
    QuestionInfoBean mQuestionInfoBean = null;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.1

        /* renamed from: com.psychiatrygarden.adapter.MyCommentAdapter$1$1, reason: invalid class name and collision with other inner class name */
        public class C02931 implements onDialogShareClickListener {
            final /* synthetic */ CommentBean.DataBean.HotBean.ReplyBean val$commListBean;
            final /* synthetic */ CommentBean.DataBean.HotBean val$mHotbean;
            final /* synthetic */ String val$reply_primary_id;
            final /* synthetic */ View val$v;

            public C02931(final CommentBean.DataBean.HotBean.ReplyBean val$commListBean, final String val$reply_primary_id, final CommentBean.DataBean.HotBean val$mHotbean, final View val$v) {
                this.val$commListBean = val$commListBean;
                this.val$reply_primary_id = val$reply_primary_id;
                this.val$mHotbean = val$mHotbean;
                this.val$v = val$v;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, String str2, String str3, String str4) {
                Bundle bundle = new Bundle();
                bundle.putString("obj_id", replyBean.getObj_id());
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", replyBean.getModule_type());
                bundle.putString("content", str2);
                bundle.putString("b_img", str3);
                bundle.putString("s_img", str4);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", str);
                bundle.putString("fileId", replyBean.getFile_id());
                MyCommentAdapter.this.putComment(bundle, 1);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$1(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str) {
                MyCommentAdapter.this.mBianjiDataVisition(replyBean.getId(), str, replyBean.getContent(), replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$2(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                MyCommentAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$3(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str) {
                MyCommentAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), replyBean.getContent(), str, replyBean.getOppose_num(), replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$4(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str) {
                MyCommentAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), replyBean.getContent(), replyBean.getPraise_num(), str, replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$5(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
                boolean z2 = true;
                if (i2 == 0) {
                    new PopInputWindow(MyCommentAdapter.this.mContext, replyBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.lb
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f14726a.lambda$onclickIntBack$1(replyBean, hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                }
                if (i2 != 1) {
                    if (i2 == 2) {
                        new PopInputWindow(MyCommentAdapter.this.mContext, replyBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.nb
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public final void mInputDataListter(String str) {
                                this.f14805a.lambda$onclickIntBack$3(replyBean, hotBean, str);
                            }
                        }).showPopUp(view);
                        return;
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        new PopInputWindow(MyCommentAdapter.this.mContext, replyBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ob
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public final void mInputDataListter(String str) {
                                this.f14843a.lambda$onclickIntBack$4(replyBean, hotBean, str);
                            }
                        }).showPopUp(view);
                        return;
                    }
                }
                Context context = MyCommentAdapter.this.mContext;
                onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.mb
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14765a.lambda$onclickIntBack$2(replyBean, hotBean, str, str2, str3);
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
            public /* synthetic */ void lambda$onclickIntBack$6(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                MyCommentAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$7(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, String str2, PopupComWindow popupComWindow2) {
                if (popupComWindow != null && popupComWindow.isShowing()) {
                    popupComWindow.dismiss();
                }
                if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                    popupComWindow2.dismiss();
                }
                HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postBannedData(replyBean.getUser_id(), str2, str);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$8(final CommentBean.DataBean.HotBean.ReplyBean replyBean, View view, final String str, final PopupComWindow popupComWindow) {
                new PopupComWindow(MyCommentAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.kb
                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                    public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                        this.f14680a.lambda$onclickIntBack$7(popupComWindow, replyBean, str, str2, popupComWindow2);
                    }
                }).showPopUp(view);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$9(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, PopupComWindow popupComWindow) {
                if (popupComWindow != null && popupComWindow.isShowing()) {
                    popupComWindow.dismiss();
                }
                if (MyCommentAdapter.this.mIsSource) {
                    HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportSourceData(replyBean.getId(), str);
                    return;
                }
                if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                    String str2 = "[\"" + replyBean.getId() + "\"]";
                    String str3 = "[\"" + replyBean.getContent() + "\"]";
                    String targetData = MyCommentAdapter.this.getTargetData(replyBean.getModule_type());
                    AliyunEvent aliyunEvent = AliyunEvent.BehaviorReport;
                    CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str2, str3, targetData, "2");
                }
                HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportData(replyBean.getId(), str, replyBean.getModule_type() + "");
            }

            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public void onclickIntBack(int i2) {
                switch (i2) {
                    case 0:
                        if (MyCommentAdapter.this.is_prohibit.equals("1")) {
                            ToastUtil.shortToast(MyCommentAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                            break;
                        } else {
                            Context context = MyCommentAdapter.this.mContext;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean = this.val$commListBean;
                            final String str = this.val$reply_primary_id;
                            new DialogInput(context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.pb
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str2, String str3, String str4) {
                                    this.f14881a.lambda$onclickIntBack$0(replyBean, str, str2, str3, str4);
                                }
                            }, "", "回复" + this.val$commListBean.getNickname(), this.val$commListBean.getModule_type().equals("1") || this.val$commListBean.getModule_type().equals("4")).show();
                            break;
                        }
                    case 1:
                        if (UserConfig.getInstance().getUser().getAvatar().equals("1")) {
                            BaseActivity baseActivity = MyCommentAdapter.this.mActivity;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean2 = this.val$commListBean;
                            final CommentBean.DataBean.HotBean hotBean = this.val$mHotbean;
                            final View view = this.val$v;
                            new XPopup.Builder(MyCommentAdapter.this.mContext).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(baseActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.qb
                                @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                                public final void mBianjiData(int i3) {
                                    this.f14923a.lambda$onclickIntBack$5(replyBean2, hotBean, view, i3);
                                }
                            })).show();
                            break;
                        } else {
                            Context context2 = MyCommentAdapter.this.mContext;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean3 = this.val$commListBean;
                            final CommentBean.DataBean.HotBean hotBean2 = this.val$mHotbean;
                            new DialogInput(context2, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.rb
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str2, String str3, String str4) {
                                    this.f14966a.lambda$onclickIntBack$6(replyBean3, hotBean2, str2, str3, str4);
                                }
                            }, this.val$commListBean.getContent(), "正在编辑" + this.val$commListBean.getNickname() + "的评论...", this.val$commListBean.getModule_type().equals("1") || this.val$commListBean.getModule_type().equals("4"), this.val$commListBean.getC_imgs().getB_img(), this.val$commListBean.getC_imgs().getS_img(), 1, this.val$commListBean.getC_imgs().getVideoId()).show();
                            break;
                        }
                        break;
                    case 2:
                        if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                            String targetData = MyCommentAdapter.this.getTargetData(this.val$commListBean.getModule_type());
                            String str2 = "[\"" + this.val$commListBean.getId() + "\"]";
                            String str3 = "[\"" + this.val$commListBean.getContent() + "\"]";
                            AliyunEvent aliyunEvent = AliyunEvent.DeleComment;
                            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str2, str3, targetData, "2");
                        }
                        MyCommentAdapter.this.deleteComm(this.val$commListBean.getModule_type(), this.val$commListBean.getId(), this.val$reply_primary_id, 1, this.val$mHotbean, this.val$commListBean.getObj_id());
                        break;
                    case 3:
                        ((ClipboardManager) MyCommentAdapter.this.mContext.getSystemService("clipboard")).setText(this.val$commListBean.getContent());
                        NewToast.showShort(MyCommentAdapter.this.mContext, "复制成功", 0).show();
                        if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                            String str4 = "[\"" + this.val$commListBean.getId() + "\"]";
                            String str5 = "[\"" + this.val$commListBean.getContent() + "\"]";
                            String targetData2 = MyCommentAdapter.this.getTargetData(this.val$commListBean.getModule_type());
                            AliyunEvent aliyunEvent2 = AliyunEvent.CopyComment;
                            CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str4, str5, targetData2, "2");
                            break;
                        }
                        break;
                    case 4:
                        MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                        myCommentAdapter.getUserInfo(myCommentAdapter.mContext, this.val$commListBean.getUser_id());
                        break;
                    case 5:
                        if ((TextUtils.isEmpty(this.val$commListBean.getIs_essence()) ? "0" : this.val$commListBean.getIs_essence()).equals("1")) {
                            for (int i3 = 0; i3 < MyCommentAdapter.this.mHotBean.size(); i3++) {
                                if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getId())) {
                                    ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).setIs_essence("0");
                                }
                                if (((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getReply() != null && ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getReply().size() > 0) {
                                    for (int i4 = 0; i4 < ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getReply().size(); i4++) {
                                        if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getReply().get(i4).getId())) {
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i3)).getReply().get(i4).setIs_essence("0");
                                        }
                                    }
                                }
                            }
                            NewToast.showShort(MyCommentAdapter.this.mContext, "取消加精成功", 0).show();
                        } else {
                            for (int i5 = 0; i5 < MyCommentAdapter.this.mHotBean.size(); i5++) {
                                if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getId())) {
                                    ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).setIs_essence("1");
                                }
                                if (((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getReply() != null && ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getReply().size() > 0) {
                                    for (int i6 = 0; i6 < ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getReply().size(); i6++) {
                                        if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getReply().get(i6).getId())) {
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i5)).getReply().get(i6).setIs_essence("1");
                                        }
                                    }
                                }
                            }
                            MyCommentAdapter.this.putDigest(this.val$commListBean.getId(), this.val$commListBean.getObj_id());
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                        break;
                    case 7:
                        BaseActivity baseActivity2 = MyCommentAdapter.this.mActivity;
                        final CommentBean.DataBean.HotBean.ReplyBean replyBean4 = this.val$commListBean;
                        final View view2 = this.val$v;
                        new PopupComWindow(baseActivity2, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.sb
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public final void onDiogClick(String str6, PopupComWindow popupComWindow) {
                                this.f15005a.lambda$onclickIntBack$8(replyBean4, view2, str6, popupComWindow);
                            }
                        }).showPopUp(this.val$v);
                        break;
                    case 8:
                        BaseActivity baseActivity3 = MyCommentAdapter.this.mActivity;
                        final CommentBean.DataBean.HotBean.ReplyBean replyBean5 = this.val$commListBean;
                        new PopupComWindow(baseActivity3, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.tb
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public final void onDiogClick(String str6, PopupComWindow popupComWindow) {
                                this.f15041a.lambda$onclickIntBack$9(replyBean5, str6, popupComWindow);
                            }
                        }).showPopUp(this.val$v);
                        break;
                }
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 == 1) {
                CommentBean.DataBean.HotBean.ReplyBean replyBean = (CommentBean.DataBean.HotBean.ReplyBean) message.getData().get("commListBean");
                CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) message.getData().get("commListBeans");
                String string = message.getData().getString("reply_primary_id");
                View view = (View) message.obj;
                boolean zEquals = UserConfig.getUserId().equals(replyBean.getUser_id());
                if ("1".equals(replyBean.getStatus())) {
                    CommonUtil.mToastShow(MyCommentAdapter.this.mContext);
                    return;
                }
                new XPopup.Builder(MyCommentAdapter.this.mContext).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(MyCommentAdapter.this.mContext, replyBean.getContent(), replyBean.getNickname(), zEquals ? 1 : 0, replyBean.getIs_anonymous() + "", replyBean.getDelete_skill(), new C02931(replyBean, string, hotBean, view))).show();
                return;
            }
            if (i2 == 2) {
                String string2 = message.getData().getString("is_praise");
                MyCommentAdapter.this.putPraise(message.getData().getString("module_type"), message.getData().getString("id"), message.getData().getString("obj_id"), string2.equals("0") ? "1" : "0", message.getData().getString("comment_content"));
                return;
            }
            if (i2 == 3) {
                CommentBean.DataBean.HotBean hotBean2 = (CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(message.getData().getInt("position"));
                MyCommentAdapter.this.putPraise(hotBean2.getModule_type(), hotBean2.getId(), hotBean2.getObj_id(), hotBean2.getIs_praise().equals("0") ? "1" : "0", hotBean2.getContent());
                return;
            }
            if (i2 != 4) {
                if (i2 != 8) {
                    return;
                }
                NewToast.showShort(MyCommentAdapter.this.mContext, "原题已删除", 0).show();
            } else {
                String string3 = message.getData().getString("is_Oppose");
                MyCommentAdapter.this.putOppose(message.getData().getString("module_type"), message.getData().getString("id"), message.getData().getString("obj_id"), string3.equals("0") ? "1" : "0", message.getData().getString("comment_content"));
            }
        }
    };

    /* renamed from: com.psychiatrygarden.adapter.MyCommentAdapter$4, reason: invalid class name */
    public class AnonymousClass4 implements MyExpendView.OnExpandStateChangeListener {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass4(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$0(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            Bundle bundle = new Bundle();
            bundle.putString("obj_id", hotBean.getObj_id());
            bundle.putString("to_user_id", hotBean.getUser_id());
            bundle.putString("module_type", hotBean.getModule_type());
            bundle.putString("content", str);
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            bundle.putString("parent_id", hotBean.getId());
            bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
            bundle.putInt("type", 2);
            bundle.putInt("result", 0);
            bundle.putString("fileId", hotBean.getFile_id());
            MyCommentAdapter.this.putComment(bundle, 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$1(CommentBean.DataBean.HotBean hotBean, String str) {
            MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClickStateChange$10(CommentBean.DataBean.HotBean hotBean, QuestionNoteBean questionNoteBean) {
            EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, hotBean.getObj_id()));
            ProjectApp.noteContent = "";
            ProjectApp.noteSmellImage = "";
            ProjectApp.noteBigImage = "";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$11(final CommentBean.DataBean.HotBean hotBean, final View view, int i2) {
            boolean z2 = true;
            switch (i2) {
                case 0:
                    if (MyCommentAdapter.this.is_prohibit.equals("1")) {
                        ToastUtil.shortToast(MyCommentAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                        break;
                    } else {
                        Context context = MyCommentAdapter.this.mContext;
                        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.bc
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f14329a.lambda$onClickStateChange$0(hotBean, str, str2, str3);
                            }
                        };
                        String str = "回复" + hotBean.getNickname();
                        if (!hotBean.getModule_type().equals("1") && !hotBean.getModule_type().equals("4")) {
                            z2 = false;
                        }
                        new DialogInput(context, ondialogclicklistener, "", str, z2).show();
                        break;
                    }
                case 1:
                    if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        new XPopup.Builder(MyCommentAdapter.this.mContext).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(MyCommentAdapter.this.mActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.cc
                            @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                            public final void mBianjiData(int i3) {
                                this.f14371a.lambda$onClickStateChange$5(hotBean, view, i3);
                            }
                        })).show();
                        break;
                    } else {
                        Context context2 = MyCommentAdapter.this.mContext;
                        onDialogClickListener ondialogclicklistener2 = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.dc
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str2, String str3, String str4) {
                                this.f14408a.lambda$onClickStateChange$6(hotBean, str2, str3, str4);
                            }
                        };
                        String content = hotBean.getContent();
                        String str2 = "正在编辑" + hotBean.getNickname() + "的评论...";
                        if (!hotBean.getModule_type().equals("1") && !hotBean.getModule_type().equals("4")) {
                            z2 = false;
                        }
                        new DialogInput(context2, ondialogclicklistener2, content, str2, z2, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId()).show();
                        break;
                    }
                    break;
                case 2:
                    if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                        String targetData = MyCommentAdapter.this.getTargetData(hotBean.getModule_type());
                        String str3 = "[\"" + hotBean.getId() + "\"]";
                        String str4 = "[\"" + hotBean.getContent() + "\"]";
                        AliyunEvent aliyunEvent = AliyunEvent.DeleComment;
                        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str3, str4, targetData, "2");
                    }
                    MyCommentAdapter.this.deleteComm(hotBean.getModule_type(), hotBean.getId(), hotBean.getReply_primary_id(), 2, hotBean, hotBean.getObj_id());
                    break;
                case 3:
                    ((ClipboardManager) MyCommentAdapter.this.mContext.getSystemService("clipboard")).setText(hotBean.getContent());
                    NewToast.showShort(MyCommentAdapter.this.mContext, "复制成功", 0).show();
                    if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                        String str5 = "[\"" + hotBean.getId() + "\"]";
                        String str6 = "[\"" + hotBean.getContent() + "\"]";
                        String targetData2 = MyCommentAdapter.this.getTargetData(hotBean.getModule_type());
                        AliyunEvent aliyunEvent2 = AliyunEvent.CopyComment;
                        CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str5, str6, targetData2, "2");
                        break;
                    }
                    break;
                case 4:
                    MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                    myCommentAdapter.getUserInfo(myCommentAdapter.mContext, hotBean.getUser_id());
                    break;
                case 5:
                    if ((TextUtils.isEmpty(hotBean.getIs_essence()) ? "0" : hotBean.getIs_essence()).equals("1")) {
                        hotBean.setIs_essence("0");
                        for (int i3 = 0; i3 < MyCommentAdapter.this.mHotBean.size(); i3++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply();
                            if (reply != null && reply.size() > 0) {
                                for (int i4 = 0; i4 < reply.size(); i4++) {
                                    if (hotBean.getId().equals(reply.get(i4).getId())) {
                                        reply.get(i4).setIs_essence("0");
                                    }
                                }
                            }
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                        NewToast.showShort(MyCommentAdapter.this.mContext, "取消加精成功", 0).show();
                        break;
                    } else {
                        hotBean.setIs_essence("1");
                        for (int i5 = 0; i5 < MyCommentAdapter.this.mHotBean.size(); i5++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply();
                            if (reply2 != null && reply2.size() > 0) {
                                for (int i6 = 0; i6 < reply2.size(); i6++) {
                                    if (hotBean.getId().equals(reply2.get(i6).getId())) {
                                        reply2.get(i6).setIs_essence("1");
                                    }
                                }
                            }
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                        MyCommentAdapter.this.putDigest(hotBean.getId(), hotBean.getObj_id());
                        break;
                    }
                    break;
                case 7:
                    new PopupComWindow(MyCommentAdapter.this.mActivity, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.ec
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str7, PopupComWindow popupComWindow) {
                            this.f14446a.lambda$onClickStateChange$8(hotBean, view, str7, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 8:
                    new PopupComWindow(MyCommentAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.fc
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str7, PopupComWindow popupComWindow) {
                            this.f14485a.lambda$onClickStateChange$9(hotBean, str7, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 9:
                    ProjectApp.noteContent = hotBean.getContent();
                    if (TextUtils.isEmpty(hotBean.getVideo_id()) && !TextUtils.isEmpty(hotBean.getC_imgs().getS_img())) {
                        ProjectApp.noteSmellImage = hotBean.getC_imgs().getS_img();
                        ProjectApp.noteBigImage = hotBean.getC_imgs().getB_img();
                    }
                    new DialogNoteInput(MyCommentAdapter.this.mContext, hotBean.getModule_type(), hotBean.getObj_id() + "", "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.adapter.vb
                        @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                        public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                            MyCommentAdapter.AnonymousClass4.lambda$onClickStateChange$10(hotBean, questionNoteBean);
                        }
                    }).show();
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$2(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$3(CommentBean.DataBean.HotBean hotBean, String str) {
            MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$4(CommentBean.DataBean.HotBean hotBean, String str) {
            MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), str, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$5(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            boolean z2 = true;
            if (i2 == 0) {
                new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.ub
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f15079a.lambda$onClickStateChange$1(hotBean, str);
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.yb
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f15209a.lambda$onClickStateChange$3(hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.zb
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f15243a.lambda$onClickStateChange$4(hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            Context context = MyCommentAdapter.this.mContext;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.xb
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f15174a.lambda$onClickStateChange$2(hotBean, str, str2, str3);
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
            MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClickStateChange$7(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean hotBean, String str, String str2, PopupComWindow popupComWindow2) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                popupComWindow2.dismiss();
            }
            HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$8(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(MyCommentAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.ac
                @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                    MyCommentAdapter.AnonymousClass4.lambda$onClickStateChange$7(popupComWindow, hotBean, str, str2, popupComWindow2);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClickStateChange$9(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (MyCommentAdapter.this.mIsSource) {
                HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportSourceData(hotBean.getId(), str);
                return;
            }
            if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                String str2 = "[\"" + hotBean.getId() + "\"]";
                String str3 = "[\"" + hotBean.getContent() + "\"]";
                String targetData = MyCommentAdapter.this.getTargetData(hotBean.getModule_type());
                AliyunEvent aliyunEvent = AliyunEvent.BehaviorReport;
                CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str2, str3, targetData, "2");
            }
            HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportData(hotBean.getId(), str, hotBean.getModule_type() + "");
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onClickStateChange(final View view) {
            boolean zEquals = UserConfig.getUserId().equals(this.val$mCommentBean.getUser_id());
            if ("1".equals(this.val$mCommentBean.getStatus())) {
                CommonUtil.mToastShow(MyCommentAdapter.this.mContext);
                return;
            }
            int i2 = TextUtils.isEmpty(this.val$mCommentBean.getReply_num()) ? 0 : Integer.parseInt(this.val$mCommentBean.getReply_num());
            Context context = MyCommentAdapter.this.mContext;
            String content = this.val$mCommentBean.getContent();
            String nickname = this.val$mCommentBean.getNickname();
            String str = this.val$mCommentBean.getIs_anonymous() + "";
            String module_type = this.val$mCommentBean.getModule_type();
            String delete_skill = this.val$mCommentBean.getDelete_skill();
            String id = this.val$mCommentBean.getId();
            final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
            new XPopup.Builder(MyCommentAdapter.this.mContext).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(context, content, nickname, zEquals ? 1 : 0, str, module_type, delete_skill, id, i2, new onDialogShareClickListener() { // from class: com.psychiatrygarden.adapter.wb
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i3) {
                    this.f15142a.lambda$onClickStateChange$11(hotBean, view, i3);
                }
            })).show();
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onExpandStateChanged(boolean isExpanded) {
            this.val$mCommentBean.setIs_showValue(isExpanded);
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.MyCommentAdapter$5, reason: invalid class name */
    public class AnonymousClass5 implements onDialogShareClickListener {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;
        final /* synthetic */ View val$v;

        /* renamed from: com.psychiatrygarden.adapter.MyCommentAdapter$5$7, reason: invalid class name */
        public class AnonymousClass7 implements OnClickDiogListenter {
            public AnonymousClass7() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ void lambda$onDiogClick$0(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean hotBean, String str, String str2, PopupComWindow popupComWindow2) {
                if (popupComWindow != null && popupComWindow.isShowing()) {
                    popupComWindow.dismiss();
                }
                if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                    popupComWindow2.dismiss();
                }
                HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
            }

            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
            public void onDiogClick(final String str, final PopupComWindow popupComWindow1) {
                AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                BaseActivity baseActivity = MyCommentAdapter.this.mActivity;
                final CommentBean.DataBean.HotBean hotBean = anonymousClass5.val$mCommentBean;
                new PopupComWindow(baseActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.ic
                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                    public final void onDiogClick(String str2, PopupComWindow popupComWindow) {
                        MyCommentAdapter.AnonymousClass5.AnonymousClass7.lambda$onDiogClick$0(popupComWindow1, hotBean, str, str2, popupComWindow);
                    }
                }).showPopUp(AnonymousClass5.this.val$v);
            }
        }

        public AnonymousClass5(final CommentBean.DataBean.HotBean val$mCommentBean, final View val$v) {
            this.val$mCommentBean = val$mCommentBean;
            this.val$v = val$v;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$0(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            boolean z2 = true;
            if (i2 == 0) {
                new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.2
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public void mInputDataListter(String txtSter) {
                        MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                        String id = hotBean.getId();
                        String content = hotBean.getContent();
                        String praise_num = hotBean.getPraise_num();
                        String oppose_num = hotBean.getOppose_num();
                        CommentBean.DataBean.HotBean hotBean2 = hotBean;
                        myCommentAdapter.mBianjiDataVisition(id, txtSter, content, praise_num, oppose_num, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.4
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                            String id = hotBean.getId();
                            String ctime_timestamp = hotBean.getCtime_timestamp();
                            String content = hotBean.getContent();
                            String oppose_num = hotBean.getOppose_num();
                            CommentBean.DataBean.HotBean hotBean2 = hotBean;
                            myCommentAdapter.mBianjiDataVisition(id, ctime_timestamp, content, txtSter, oppose_num, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(MyCommentAdapter.this.mContext, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.5
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                            String id = hotBean.getId();
                            String ctime_timestamp = hotBean.getCtime_timestamp();
                            String content = hotBean.getContent();
                            String praise_num = hotBean.getPraise_num();
                            CommentBean.DataBean.HotBean hotBean2 = hotBean;
                            myCommentAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            Context context = MyCommentAdapter.this.mContext;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.3
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public void onclickStringBack(String str, String b_img, String s_img) {
                    MyCommentAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, b_img, s_img);
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
        public /* synthetic */ void lambda$onclickIntBack$1(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (MyCommentAdapter.this.mIsSource) {
                HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportSourceData(hotBean.getId(), str);
                return;
            }
            if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                String str2 = "[\"" + hotBean.getId() + "\"]";
                String str3 = "[\"" + hotBean.getContent() + "\"]";
                String targetData = MyCommentAdapter.this.getTargetData(hotBean.getModule_type());
                AliyunEvent aliyunEvent = AliyunEvent.BehaviorReport;
                CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str2, str3, targetData, "2");
            }
            HttpRequstData.getIntance(MyCommentAdapter.this.mContext).postReportData(hotBean.getId(), str, hotBean.getModule_type() + "");
        }

        @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
        public void onclickIntBack(int i2) {
            switch (i2) {
                case 0:
                    if (MyCommentAdapter.this.is_prohibit.equals("1")) {
                        ToastUtil.shortToast(MyCommentAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                        break;
                    } else {
                        new DialogInput(MyCommentAdapter.this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.1
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public void onclickStringBack(String str, String b_img, String s_img) {
                                Bundle bundle = new Bundle();
                                bundle.putString("obj_id", AnonymousClass5.this.val$mCommentBean.getObj_id());
                                bundle.putString("to_user_id", AnonymousClass5.this.val$mCommentBean.getUser_id());
                                bundle.putString("module_type", AnonymousClass5.this.val$mCommentBean.getModule_type());
                                bundle.putString("content", str);
                                bundle.putString("b_img", b_img);
                                bundle.putString("s_img", s_img);
                                bundle.putString("parent_id", AnonymousClass5.this.val$mCommentBean.getId());
                                bundle.putString("reply_primary_id", AnonymousClass5.this.val$mCommentBean.getReply_primary_id());
                                bundle.putInt("type", 2);
                                bundle.putInt("result", 0);
                                bundle.putString("fileId", AnonymousClass5.this.val$mCommentBean.getFile_id());
                                MyCommentAdapter.this.putComment(bundle, 2);
                            }
                        }, "", "回复" + this.val$mCommentBean.getNickname(), this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4")).show();
                        break;
                    }
                    break;
                case 1:
                    if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        BaseActivity baseActivity = MyCommentAdapter.this.mActivity;
                        final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
                        final View view = this.val$v;
                        new XPopup.Builder(MyCommentAdapter.this.mContext).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(baseActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.gc
                            @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                            public final void mBianjiData(int i3) {
                                this.f14526a.lambda$onclickIntBack$0(hotBean, view, i3);
                            }
                        })).show();
                        break;
                    } else {
                        new DialogInput(MyCommentAdapter.this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.5.6
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public void onclickStringBack(String str, String b_img, String s_img) {
                                AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                                MyCommentAdapter.this.mBianjiDataVisition(anonymousClass5.val$mCommentBean.getId(), AnonymousClass5.this.val$mCommentBean.getCtime_timestamp(), str, AnonymousClass5.this.val$mCommentBean.getPraise_num(), AnonymousClass5.this.val$mCommentBean.getOppose_num(), null, 2, AnonymousClass5.this.val$mCommentBean, b_img, s_img);
                            }
                        }, this.val$mCommentBean.getContent(), "正在编辑" + this.val$mCommentBean.getNickname() + "的评论...", this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4"), this.val$mCommentBean.getC_imgs().getB_img(), this.val$mCommentBean.getC_imgs().getS_img(), 1, this.val$mCommentBean.getC_imgs().getVideoId()).show();
                        break;
                    }
                case 2:
                    if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                        String targetData = MyCommentAdapter.this.getTargetData(this.val$mCommentBean.getModule_type());
                        String str = "[\"" + this.val$mCommentBean.getId() + "\"]";
                        String str2 = "[\"" + this.val$mCommentBean.getContent() + "\"]";
                        AliyunEvent aliyunEvent = AliyunEvent.DeleComment;
                        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, targetData, "2");
                    }
                    MyCommentAdapter myCommentAdapter = MyCommentAdapter.this;
                    String module_type = this.val$mCommentBean.getModule_type();
                    String id = this.val$mCommentBean.getId();
                    String reply_primary_id = this.val$mCommentBean.getReply_primary_id();
                    CommentBean.DataBean.HotBean hotBean2 = this.val$mCommentBean;
                    myCommentAdapter.deleteComm(module_type, id, reply_primary_id, 2, hotBean2, hotBean2.getObj_id());
                    break;
                case 3:
                    ((ClipboardManager) MyCommentAdapter.this.mContext.getSystemService("clipboard")).setText(this.val$mCommentBean.getContent());
                    NewToast.showShort(MyCommentAdapter.this.mContext, "复制成功", 0).show();
                    if (MyCommentAdapter.this.viewType != null && MyCommentAdapter.this.viewType.equals("1")) {
                        String str3 = "[\"" + this.val$mCommentBean.getId() + "\"]";
                        String str4 = "[\"" + this.val$mCommentBean.getContent() + "\"]";
                        String targetData2 = MyCommentAdapter.this.getTargetData(this.val$mCommentBean.getModule_type());
                        AliyunEvent aliyunEvent2 = AliyunEvent.CopyComment;
                        CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str3, str4, targetData2, "2");
                        break;
                    }
                    break;
                case 4:
                    MyCommentAdapter myCommentAdapter2 = MyCommentAdapter.this;
                    myCommentAdapter2.getUserInfo(myCommentAdapter2.mContext, this.val$mCommentBean.getUser_id());
                    break;
                case 5:
                    if ((TextUtils.isEmpty(this.val$mCommentBean.getIs_essence()) ? "0" : this.val$mCommentBean.getIs_essence()).equals("1")) {
                        this.val$mCommentBean.setIs_essence("0");
                        for (int i3 = 0; i3 < MyCommentAdapter.this.mHotBean.size(); i3++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply();
                            if (reply != null && reply.size() > 0) {
                                for (int i4 = 0; i4 < reply.size(); i4++) {
                                    if (this.val$mCommentBean.getId().equals(reply.get(i4).getId())) {
                                        reply.get(i4).setIs_essence("0");
                                    }
                                }
                            }
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                        NewToast.showShort(MyCommentAdapter.this.mContext, "取消加精成功", 0).show();
                        break;
                    } else {
                        this.val$mCommentBean.setIs_essence("1");
                        for (int i5 = 0; i5 < MyCommentAdapter.this.mHotBean.size(); i5++) {
                            List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply();
                            if (reply2 != null && reply2.size() > 0) {
                                for (int i6 = 0; i6 < reply2.size(); i6++) {
                                    if (this.val$mCommentBean.getId().equals(reply2.get(i6).getId())) {
                                        reply2.get(i6).setIs_essence("1");
                                    }
                                }
                            }
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                        MyCommentAdapter.this.putDigest(this.val$mCommentBean.getId(), this.val$mCommentBean.getObj_id());
                        break;
                    }
                    break;
                case 7:
                    new PopupComWindow(MyCommentAdapter.this.mActivity, "请选择封禁类型", "", 1, new AnonymousClass7()).showPopUp(this.val$v);
                    break;
                case 8:
                    BaseActivity baseActivity2 = MyCommentAdapter.this.mActivity;
                    final CommentBean.DataBean.HotBean hotBean3 = this.val$mCommentBean;
                    new PopupComWindow(baseActivity2, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.hc
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str5, PopupComWindow popupComWindow) {
                            this.f14567a.lambda$onclickIntBack$1(hotBean3, str5, popupComWindow);
                        }
                    }).showPopUp(this.val$v);
                    break;
            }
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.MyCommentAdapter$6, reason: invalid class name */
    public class AnonymousClass6 implements CommentListenter {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass6(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commListReply$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, String str2, String str3) {
            Bundle bundle = new Bundle();
            bundle.putString("obj_id", replyBean.getObj_id());
            bundle.putString("to_user_id", replyBean.getUser_id());
            bundle.putString("module_type", replyBean.getModule_type());
            bundle.putString("content", str);
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            bundle.putString("parent_id", replyBean.getId());
            bundle.putString("reply_primary_id", replyBean.getReply_primary_id());
            bundle.putInt("type", 2);
            bundle.putInt("result", 0);
            bundle.putString("fileId", replyBean.getFile_id());
            MyCommentAdapter.this.putComment(bundle, 2);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListOppose(String module_type, String is_Oppose, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 4;
            Bundle bundle = new Bundle();
            bundle.putString("is_Oppose", is_Oppose);
            bundle.putString("id", id);
            bundle.putString("obj_id", obj_id);
            bundle.putString("module_type", module_type);
            if (this.val$mCommentBean.getReply().size() > 0) {
                Iterator<CommentBean.DataBean.HotBean.ReplyBean> it = this.val$mCommentBean.getReply().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    CommentBean.DataBean.HotBean.ReplyBean next = it.next();
                    if (next.getId().equals(id)) {
                        bundle.putString("comment_content", next.getContent());
                        break;
                    }
                }
            }
            messageObtain.setData(bundle);
            MyCommentAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraise(String module_type, String is_praise, View v2, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = v2;
            messageObtain.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("is_praise", is_praise);
            bundle.putString("id", id);
            bundle.putString("obj_id", obj_id);
            bundle.putString("module_type", module_type);
            if (this.val$mCommentBean.getReply().size() > 0) {
                Iterator<CommentBean.DataBean.HotBean.ReplyBean> it = this.val$mCommentBean.getReply().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    CommentBean.DataBean.HotBean.ReplyBean next = it.next();
                    if (next.getId().equals(id)) {
                        bundle.putString("comment_content", next.getContent());
                        break;
                    }
                }
            }
            messageObtain.setData(bundle);
            MyCommentAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraiseFaile() {
            NewToast.showShort(MyCommentAdapter.this.mContext, "您已经点赞了", 0).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListReply(final CommentBean.DataBean.HotBean.ReplyBean cmt, View v2) {
            boolean z2 = true;
            if (Integer.parseInt(cmt.getReply_num()) <= 1) {
                Context context = MyCommentAdapter.this.mContext;
                onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.jc
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14653a.lambda$commListReply$0(cmt, str, str2, str3);
                    }
                };
                String str = "回复" + cmt.getNickname();
                if (!this.val$mCommentBean.getModule_type().equals("1") && !this.val$mCommentBean.getModule_type().equals("4")) {
                    z2 = false;
                }
                new DialogInput(context, ondialogclicklistener, "", str, z2).show();
                return;
            }
            Intent intent = new Intent(MyCommentAdapter.this.mContext, (Class<?>) CommentReplyActivity.class);
            intent.putExtra("is_replybean", true);
            intent.putExtra("bean", cmt);
            intent.putExtra("comment_type", MyCommentAdapter.this.comment_type);
            if (v2 == null) {
                intent.putExtra("isShowVideo", "0");
            } else {
                intent.putExtra("isShowVideo", "1");
            }
            intent.putExtra("module_type", Integer.parseInt(cmt.getModule_type()));
            intent.putExtra("isVisiable", MyCommentAdapter.this.isVisiable);
            intent.putExtra("isProhibit", MyCommentAdapter.this.is_prohibit);
            intent.putExtra("isSource", MyCommentAdapter.this.mIsSource);
            MyCommentAdapter.this.mContext.startActivity(intent);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListSupport(String module_type, String is_Support, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("is_praise", is_Support);
            bundle.putString("id", id);
            bundle.putString("obj_id", obj_id);
            bundle.putString("module_type", module_type);
            if (this.val$mCommentBean.getReply().size() > 0) {
                Iterator<CommentBean.DataBean.HotBean.ReplyBean> it = this.val$mCommentBean.getReply().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    CommentBean.DataBean.HotBean.ReplyBean next = it.next();
                    if (next.getId().equals(id)) {
                        bundle.putString("comment_content", next.getContent());
                        break;
                    }
                }
            }
            messageObtain.setData(bundle);
            MyCommentAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commentListenerData(CommentBean.DataBean.HotBean.ReplyBean commListBean, View v2) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = v2;
            messageObtain.what = 1;
            Bundle bundle = new Bundle();
            bundle.putSerializable("commListBean", commListBean);
            bundle.putSerializable("commListBeans", this.val$mCommentBean);
            bundle.putString("reply_primary_id", this.val$mCommentBean.getReply_primary_id());
            messageObtain.setData(bundle);
            MyCommentAdapter.this.mHandler.sendMessage(messageObtain);
        }
    }

    public class ViewHoder {
        TextView commentList_item_tv_praise;
        TextView commentList_item_tv_school;
        TextView commentList_item_tv_userName;
        CircleImageView commentList_item_userIcon;
        TextView group_name;
        ImageView imageView2_praiuse;
        ImageView isverauth;
        ImageView iv_comment_me_new;
        ImageView iv_elite;
        CircleImageView jiav;
        LinearLayout lin_praise;
        RelativeLayout linciew;
        LinearLayout llay_reply_bg;
        MyExpendView myexptervew;
        NineGridTestLayout ningrids;
        FloorView subFloors;
        TextView tv_oppose;
        TextView tv_question;
        TextView tv_reply;
        TextView tv_support;
        ImageView vipimgid;

        private ViewHoder() {
        }
    }

    public MyCommentAdapter(BaseActivity mActivity, List<CommentBean.DataBean.HotBean> mHotBean, Context mContext, String comment_type, boolean is_callMe, boolean isSource, String is_prohibit) {
        this.mIsSource = false;
        this.mHotBean = mHotBean;
        this.mContext = mContext;
        this.comment_type = comment_type;
        this.is_callMe = is_callMe;
        this.mActivity = mActivity;
        this.mIsSource = isSource;
        this.is_prohibit = is_prohibit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTargetData(String module_type) {
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(module_type);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setUnit_id(ProjectApp.unit_id);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(ProjectApp.identity_id);
        questionDetailBean.setChapter_title(ProjectApp.chapter_title);
        questionDetailBean.setChapter_id(ProjectApp.chapter_id);
        questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        return ProjectApp.gson.toJson(questionDetailBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserInfo(Context mContext, String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", user_id);
        YJYHttpUtils.get(mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.12
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
                        String strOptString = jSONObject.optJSONObject("data").optString("nickname");
                        CommonUtil.mPutChatData(MyCommentAdapter.this.mActivity, jSONObject.optJSONObject("data").optString("user_uuid"), strOptString);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$10() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(View view) {
        ToastUtil.shortToast(this.mContext, "已认证");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$1(CommentBean.DataBean.HotBean hotBean, ViewHoder viewHoder, View view) {
        if (ProjectApp.isSerachClick || hotBean.getIs_oppose().equals("1")) {
            return;
        }
        String str = "0";
        if (hotBean.getIs_praise().equals("1")) {
            putPraise(hotBean.getModule_type(), hotBean.getId(), hotBean.getObj_id(), "0", hotBean.getContent());
            hotBean.setIs_praise("0");
            try {
                String praise_num = hotBean.getPraise_num();
                if (TextUtils.isEmpty(praise_num)) {
                    praise_num = "0";
                }
                if (praise_num.trim().equals("0")) {
                    hotBean.setPraise_num("0");
                } else {
                    hotBean.setPraise_num((Integer.parseInt(praise_num) - 1) + "");
                }
                viewHoder.tv_support.setText(String.format("赞同(%s)", hotBean.getPraise_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(viewHoder.tv_support, 0);
            putPraise(hotBean.getModule_type(), hotBean.getId(), hotBean.getObj_id(), "1", hotBean.getContent());
            hotBean.setIs_praise("1");
            try {
                String praise_num2 = hotBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                hotBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                viewHoder.tv_support.setText(String.format("已赞同(%s)", hotBean.getPraise_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$2(CommentBean.DataBean.HotBean hotBean, ViewHoder viewHoder, View view) {
        if (ProjectApp.isSerachClick || hotBean.getIs_praise().equals("1")) {
            return;
        }
        String str = "0";
        if (hotBean.getIs_oppose().equals("1")) {
            putOppose(hotBean.getModule_type(), hotBean.getId(), hotBean.getObj_id(), "0", hotBean.getContent());
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
                viewHoder.tv_oppose.setText(String.format("反对(%s)", hotBean.getOppose_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(viewHoder.tv_oppose, 1);
            putOppose(hotBean.getModule_type(), hotBean.getId(), hotBean.getObj_id(), "1", hotBean.getContent());
            hotBean.setIs_oppose("1");
            try {
                String oppose_num2 = hotBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num2)) {
                    str = oppose_num2;
                }
                hotBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                viewHoder.tv_oppose.setText(String.format("已反对(%s)", hotBean.getOppose_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$3(final CommentBean.DataBean.HotBean hotBean, View view) {
        if (hotBean.getStatus().equals("1")) {
            CommonUtil.mToastShow(this.mContext);
            return;
        }
        if (Integer.parseInt(hotBean.getReply_num()) >= 1) {
            Intent intent = new Intent(this.mContext, (Class<?>) CommentReplyActivity.class);
            intent.putExtra("is_replybean", false);
            intent.putExtra("bean", hotBean);
            intent.putExtra("module_type", Integer.parseInt(hotBean.getModule_type()));
            intent.putExtra("comment_type", this.comment_type);
            intent.putExtra("isVisiable", this.isVisiable);
            intent.putExtra("isShowVideo", "1");
            intent.putExtra("isProhibit", this.is_prohibit);
            intent.putExtra("isSource", this.mIsSource);
            intent.putExtra("viewType", this.viewType);
            this.mContext.startActivity(intent);
            return;
        }
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (this.is_prohibit.equals("1")) {
            ToastUtil.shortToast(this.mActivity, "本帖已被锁定，不支持回帖");
            return;
        }
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.3
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public void onclickStringBack(String str, String b_img, String s_img) {
                Bundle bundle = new Bundle();
                bundle.putString("obj_id", hotBean.getObj_id());
                bundle.putString("to_user_id", hotBean.getUser_id());
                bundle.putString("module_type", hotBean.getModule_type());
                bundle.putString("content", str);
                bundle.putString("b_img", b_img);
                bundle.putString("s_img", s_img);
                bundle.putString("parent_id", hotBean.getId());
                bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
                bundle.putInt("type", 2);
                bundle.putInt("result", 0);
                bundle.putString("fileId", hotBean.getFile_id());
                MyCommentAdapter.this.putComment(bundle, 2);
            }
        }, "", "回复" + hotBean.getNickname(), hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$4(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (hotBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.addFlags(67108864);
        intent.putExtra("jiav", hotBean.getUser_identity());
        this.mContext.startActivity(intent);
        ((Activity) this.mContext).overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$5(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (hotBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.putExtra("jiav", hotBean.getUser_identity());
        intent.addFlags(67108864);
        this.mContext.startActivity(intent);
        ((Activity) this.mContext).overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$6(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        PublicMethodActivity.getInstance().mCommentMethod(hotBean.getModule_type() + "", hotBean.getTarget_params());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$7(CommentBean.DataBean.HotBean hotBean, ViewHoder viewHoder, int i2, View view) throws Resources.NotFoundException {
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (hotBean.getIs_praise().equals("1")) {
            NewToast.showShort(this.mContext, "您已点过赞了", 0).show();
            return;
        }
        hotBean.setIs_praise("1");
        viewHoder.imageView2_praiuse.setBackgroundResource(R.drawable.icon_praise_red_left);
        viewHoder.lin_praise.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.welcome_loading));
        try {
            String praise_num = hotBean.getPraise_num();
            if (TextUtils.isEmpty(praise_num)) {
                praise_num = "0";
            }
            hotBean.setPraise_num((Integer.parseInt(praise_num) + 1) + "");
            viewHoder.commentList_item_tv_praise.setText(hotBean.getPraise_num());
        } catch (Exception unused) {
        }
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 3;
        Bundle bundle = new Bundle();
        bundle.putInt("position", i2);
        messageObtainMessage.setData(bundle);
        this.mHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$8(CommentBean.DataBean.HotBean hotBean) {
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(this.mContext);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CommentReplyActivity.class);
        intent.putExtra("is_replybean", false);
        intent.putExtra("comment_type", this.comment_type);
        intent.putExtra("hideButtomVisiavle", true);
        intent.putExtra("module_type", Integer.parseInt("" + hotBean.getModule_type()));
        intent.putExtra("bean", hotBean);
        intent.putExtra("isVisiable", true);
        intent.putExtra("isShowVideo", "0");
        intent.putExtra("isProhibit", this.is_prohibit);
        intent.putExtra("isSource", this.mIsSource);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$9(CommentBean.DataBean.HotBean hotBean, View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        boolean zEquals = UserConfig.getUserId().equals(hotBean.getUser_id());
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(this.mContext);
            return;
        }
        new XPopup.Builder(this.mContext).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(this.mContext, hotBean.getContent(), hotBean.getNickname(), zEquals ? 1 : 0, hotBean.getIs_anonymous() + "", hotBean.getDelete_skill(), new AnonymousClass5(hotBean, view))).show();
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.za
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                MyCommentAdapter.lambda$Toast_pop$10();
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
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.11
            @Override // java.lang.Runnable
            public void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    public void deleteComm(String module_type, final String id, String reply_primary_id, final Integer valueInt, final CommentBean.DataBean.HotBean mHotBeans, String obj_id) {
        this.mActivity.showProgressDialog();
        String str = NetworkRequestsURL.mDeleteUrl;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.mIsSource) {
            str = NetworkRequestsURL.soutceCommentDel;
        }
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", obj_id);
        int iIntValue = valueInt.intValue();
        if (iIntValue == 1) {
            ajaxParams.put("module_type", module_type + "");
        } else if (iIntValue == 2) {
            ajaxParams.put("module_type", module_type + "");
        }
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("reply_primary_id", reply_primary_id);
        ajaxParams.put("id", id);
        ajaxParams.put("confirm", "1");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(MyCommentAdapter.this.mContext, "请求服务器失败", 0).show();
                MyCommentAdapter.this.mActivity.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        if (valueInt.intValue() == 1) {
                            for (int i2 = 0; i2 < MyCommentAdapter.this.mHotBean.size(); i2++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i3 = 0; i3 < ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().size(); i3++) {
                                        if (id.equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().get(i3).getId())) {
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().get(i3).setContent("已删除");
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().get(i3).setC_imgs(new ImagesBean());
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().get(i3).setContent("已删除");
                                            ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i2)).getReply().get(i3).setIs_del("1");
                                        }
                                    }
                                }
                            }
                            EventBus.getDefault().post("delReplyAndLoadData");
                        } else {
                            ArrayList arrayList = new ArrayList();
                            for (int i4 = 0; i4 < MyCommentAdapter.this.mHotBean.size(); i4++) {
                                if (((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i4)).getReply() != null && ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i4)).getReply().size() > 0) {
                                    for (int i5 = 0; i5 < ((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i4)).getReply().size(); i5++) {
                                        if (mHotBeans.getReply().equals(((CommentBean.DataBean.HotBean) MyCommentAdapter.this.mHotBean.get(i4)).getReply())) {
                                            arrayList.add(id);
                                        }
                                    }
                                }
                            }
                            if (arrayList.size() > 1) {
                                MyCommentAdapter.this.mHotBean.remove(MyCommentAdapter.this.mHotBean);
                            } else {
                                mHotBeans.setContent("已删除");
                                mHotBeans.setC_imgs(new ImagesBean());
                                mHotBeans.setIs_del("1");
                                ListIterator listIterator = MyCommentAdapter.this.mHotBean.listIterator();
                                while (listIterator.hasNext()) {
                                    if (TextUtils.equals(((CommentBean.DataBean.HotBean) listIterator.next()).getId(), id)) {
                                        listIterator.remove();
                                    }
                                }
                            }
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                    }
                    EventBus.getDefault().post("mCommentResult");
                    EventBus.getDefault().post("shuaxinhuifu");
                    NewToast.showShort(MyCommentAdapter.this.mContext, jSONObject.optString("message"), 0).show();
                    MyCommentAdapter.this.mActivity.hideProgressDialog();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mHotBean.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        View viewInflate;
        float width;
        float width2;
        float f2;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.comitem, (ViewGroup) null);
            viewHoder.group_name = (TextView) viewInflate.findViewById(R.id.group_name);
            viewHoder.linciew = (RelativeLayout) viewInflate.findViewById(R.id.linciew);
            viewHoder.commentList_item_tv_userName = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_userName);
            viewHoder.commentList_item_tv_school = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_school);
            viewHoder.subFloors = (FloorView) viewInflate.findViewById(R.id.foor);
            viewHoder.commentList_item_userIcon = (CircleImageView) viewInflate.findViewById(R.id.commentList_item_userIcon);
            viewHoder.commentList_item_tv_praise = (TextView) viewInflate.findViewById(R.id.commentList_item_tv_praise);
            viewHoder.imageView2_praiuse = (ImageView) viewInflate.findViewById(R.id.imageView2_praiuse);
            viewHoder.lin_praise = (LinearLayout) viewInflate.findViewById(R.id.lin_praise);
            viewHoder.myexptervew = (MyExpendView) viewInflate.findViewById(R.id.myexptervew);
            viewHoder.tv_question = (TextView) viewInflate.findViewById(R.id.tv_question);
            viewHoder.iv_elite = (ImageView) viewInflate.findViewById(R.id.iv_elite);
            viewHoder.tv_support = (TextView) viewInflate.findViewById(R.id.tv_support);
            viewHoder.tv_oppose = (TextView) viewInflate.findViewById(R.id.tv_oppose);
            viewHoder.tv_reply = (TextView) viewInflate.findViewById(R.id.tv_reply);
            viewHoder.llay_reply_bg = (LinearLayout) viewInflate.findViewById(R.id.llay_reply_bg);
            viewHoder.iv_comment_me_new = (ImageView) viewInflate.findViewById(R.id.iv_comment_me_new);
            viewHoder.jiav = (CircleImageView) viewInflate.findViewById(R.id.jiav);
            viewHoder.ningrids = (NineGridTestLayout) viewInflate.findViewById(R.id.ningrids);
            viewHoder.vipimgid = (ImageView) viewInflate.findViewById(R.id.vipimgid);
            viewHoder.isverauth = (ImageView) viewInflate.findViewById(R.id.isverauth);
            viewInflate.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
            viewInflate = convertView;
        }
        final ViewHoder viewHoder2 = viewHoder;
        final CommentBean.DataBean.HotBean item = getItem(position);
        viewHoder2.group_name.setVisibility(8);
        viewHoder2.linciew.setVisibility(0);
        viewHoder2.tv_question.setVisibility(0);
        if (item.getIs_svip().equals("1")) {
            viewHoder2.vipimgid.setVisibility(0);
            viewHoder2.vipimgid.setImageResource(R.drawable.svip333img);
        } else if (item.getIs_vip().equals("1")) {
            viewHoder2.vipimgid.setVisibility(0);
            viewHoder2.vipimgid.setImageResource(R.drawable.vipimg);
        } else {
            viewHoder2.vipimgid.setVisibility(8);
        }
        try {
            if (Integer.parseInt(item.getPraise_num()) > Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                } else {
                    viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                }
            } else if (Integer.parseInt(item.getPraise_num()) < Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                } else {
                    viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String is_praise = item.getIs_praise();
        if (is_praise.equals("1")) {
            viewHoder2.tv_support.setText(String.format("已赞同(%s)", item.getPraise_num()));
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
            } else {
                viewHoder2.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
            }
        } else {
            viewHoder2.tv_support.setText(String.format("赞同(%s)", item.getPraise_num()));
        }
        if (item.getIs_oppose().equals("1")) {
            viewHoder2.tv_oppose.setText(String.format("已反对(%s)", item.getOppose_num()));
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
            } else {
                viewHoder2.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
            }
        } else {
            viewHoder2.tv_oppose.setText(String.format("反对(%s)", item.getOppose_num()));
        }
        if (item.getUser_identity().equals("1") || item.getIs_authentication().equals("1")) {
            viewHoder2.jiav.setVisibility(8);
            viewHoder2.isverauth.setVisibility(0);
            viewHoder2.isverauth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.bb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14328c.lambda$getView$0(view);
                }
            });
        } else {
            viewHoder2.jiav.setVisibility(8);
            viewHoder2.isverauth.setVisibility(8);
        }
        if (this.is_callMe) {
            if (item.getIs_read().equals("0")) {
                viewHoder2.iv_comment_me_new.setVisibility(0);
            } else {
                viewHoder2.iv_comment_me_new.setVisibility(8);
            }
        }
        if (item.getReply_num().trim().equals("0")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                viewHoder2.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font_new2));
            } else {
                viewHoder2.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
            viewHoder2.tv_reply.setText("回复");
            viewHoder2.tv_reply.setBackgroundResource(R.color.transparent);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            viewHoder2.tv_reply.setPadding(0, 10, 0, 10);
            viewHoder2.tv_reply.setLayoutParams(layoutParams);
        } else {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                viewHoder2.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font));
                viewHoder2.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
            } else {
                viewHoder2.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                viewHoder2.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
            }
            viewHoder2.tv_reply.setText(String.format("%s 回复", item.getReply_num()));
        }
        viewHoder2.tv_support.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.cb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14368c.lambda$getView$1(item, viewHoder2, view);
            }
        });
        viewHoder2.tv_oppose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.db
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14405c.lambda$getView$2(item, viewHoder2, view);
            }
        });
        viewHoder2.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.eb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14444c.lambda$getView$3(item, view);
            }
        });
        try {
            viewHoder2.tv_question.setVisibility(0);
            if (TextUtils.isEmpty(item.getTo_from())) {
                viewHoder2.tv_question.setVisibility(8);
            } else {
                viewHoder2.tv_question.setVisibility(0);
                viewHoder2.tv_question.setText(item.getTo_from());
            }
            if (item.getModule_type().equals("2") || item.getModule_type().equals(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO)) {
                viewHoder2.tv_question.setVisibility(8);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        viewHoder2.commentList_item_tv_userName.setTextColor(Color.parseColor(item.getUser_identity_color()));
        viewHoder2.commentList_item_userIcon.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.fb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14483c.lambda$getView$4(item, view);
            }
        });
        viewHoder2.commentList_item_tv_userName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.gb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14524c.lambda$getView$5(item, view);
            }
        });
        viewHoder2.tv_question.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.hb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyCommentAdapter.lambda$getView$6(item, view);
            }
        });
        if (is_praise.equals("1")) {
            viewHoder2.imageView2_praiuse.setBackgroundResource(R.drawable.icon_praise_red_left);
        } else {
            viewHoder2.imageView2_praiuse.setBackgroundResource(R.drawable.icon_praise_gray_left);
        }
        if ((TextUtils.isEmpty(item.getIs_essence()) ? "0" : item.getIs_essence()).equals("0")) {
            viewHoder2.iv_elite.setVisibility(8);
        } else {
            viewHoder2.iv_elite.setVisibility(8);
        }
        viewHoder2.lin_praise.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ib
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f14605c.lambda$getView$7(item, viewHoder2, position, view);
            }
        });
        viewHoder2.commentList_item_tv_userName.setText(item.getNickname());
        viewHoder2.commentList_item_tv_praise.setText(item.getPraise_num());
        if (item.getC_imgs() != null && !TextUtils.isEmpty(item.getC_imgs().getS_img())) {
            float s_width = item.getC_imgs().getS_width();
            float s_height = item.getC_imgs().getS_height();
            if (s_height >= s_width * 2.0f) {
                width = (viewHoder2.ningrids.getWidth() / 3) * 2.0f;
            } else if (s_height >= s_width) {
                width = (s_height * ((viewHoder2.ningrids.getWidth() / 2) - 50)) / s_width;
            } else if (s_width >= s_height * 2.0f) {
                width = ((viewHoder2.ningrids.getWidth() * 2) / 3) / 2.0f;
            } else {
                if (s_width / s_height <= 1.1d) {
                    width2 = (viewHoder2.ningrids.getWidth() / 5) * 3;
                    f2 = 4.0f;
                } else {
                    width2 = (viewHoder2.ningrids.getWidth() / 5) * 3;
                    f2 = 3.0f;
                }
                width = (width2 * f2) / 5.0f;
            }
            ViewGroup.LayoutParams layoutParams2 = viewHoder2.ningrids.getLayoutParams();
            layoutParams2.height = (int) width;
            viewHoder2.ningrids.setLayoutParams(layoutParams2);
        }
        if (TextUtils.isEmpty(item.getVideo_id())) {
            item.getC_imgs().setVideoId("");
            item.getC_imgs().setStatus("");
        } else {
            item.getC_imgs().setVideoId(item.getVideo_id() + "");
            item.getC_imgs().setStatus(item.getStatus());
        }
        viewHoder2.ningrids.setmImagesBean(item.getC_imgs(), 1);
        viewHoder2.ningrids.setDownImgUrl(item.getImg_watermark() + "");
        viewHoder2.ningrids.setIsShowAll(true);
        viewHoder2.ningrids.setmVideoClickIml(new NineGridLayout.VideoClickIml() { // from class: com.psychiatrygarden.adapter.jb
            @Override // com.psychiatrygarden.gradview.NineGridLayout.VideoClickIml
            public final void mVideoClickData() {
                this.f14651a.lambda$getView$8(item);
            }
        });
        viewHoder2.myexptervew.setIs_del(item.getIs_del());
        viewHoder2.myexptervew.setText(item.getContent(), item.is_showValue());
        viewHoder2.myexptervew.setListener(new AnonymousClass4(item));
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ab
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14291c.lambda$getView$9(item, view);
            }
        });
        viewHoder2.commentList_item_tv_school.setText(item.getSchool() + " " + item.getCtime());
        if (TextUtils.isEmpty(item.getAvatar() + "")) {
            viewHoder2.commentList_item_userIcon.setImageResource(R.drawable.empty_photo);
        } else {
            GlideUtils.loadImage(viewHoder2.commentList_item_userIcon.getContext(), item.getAvatar(), viewHoder2.commentList_item_userIcon);
        }
        viewHoder2.subFloors.setComments(new SubComments(item.getReply()), false, this);
        viewHoder2.subFloors.setFactory(new SubFloorFactory());
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            viewHoder2.subFloors.setBoundDrawer(ContextCompat.getDrawable(this.mContext, R.drawable.bondcomm));
        } else {
            viewHoder2.subFloors.setBoundDrawer(ContextCompat.getDrawable(this.mContext, R.drawable.bound_night));
        }
        viewHoder2.subFloors.setmCommentListenter(new AnonymousClass6(item));
        viewHoder2.subFloors.init();
        try {
            if (this.isVisiable || TextUtils.isEmpty(item.getTo_from())) {
                viewHoder2.tv_question.setVisibility(8);
            } else {
                viewHoder2.tv_question.setVisibility(0);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return viewInflate;
    }

    public void getputData(Bundle b3) {
        putComment(b3, Integer.valueOf(b3.getInt("type")));
    }

    public void mBianjiDataVisition(String id, final String pushTime, final String pushcontent, final String pushoppn, final String pushno, final CommentBean.DataBean.HotBean.ReplyBean commListBean, final int type, final CommentBean.DataBean.HotBean mHotbean, final String b_img, final String s_img) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutBianAdminDataUrl;
        ajaxParams.put("id", id);
        if (this.mIsSource) {
            str = NetworkRequestsURL.updateComment;
        } else {
            ajaxParams.put("ctime_timestamp", pushTime);
            ajaxParams.put("praise_num", pushoppn);
            ajaxParams.put("oppose_num", pushno);
            ajaxParams.put("comment_type", this.comment_type);
            if (type == 1) {
                ajaxParams.put("module_type", commListBean.getModule_type() + "");
            } else if (type == 2) {
                ajaxParams.put("module_type", mHotbean.getModule_type() + "");
            }
        }
        String str2 = str;
        ajaxParams.put("content", pushcontent);
        if (!TextUtils.isEmpty(b_img)) {
            if (b_img.contains("http")) {
                ajaxParams.put("b_img", b_img);
                ajaxParams.put("s_img", s_img);
            } else {
                ajaxParams.put("video_id", b_img);
            }
        }
        YJYHttpUtils.post(this.mContext, str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.2
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
                        NewToast.showShort(MyCommentAdapter.this.mContext, new JSONObject(s2).optString("message"), 0).show();
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
                    MyCommentAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putComment(Bundle b3, final Integer changeCode) {
        String str = NetworkRequestsURL.mPutComment;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("parent_id", b3.getString("parent_id"));
        if (this.mIsSource) {
            str = NetworkRequestsURL.submitComment;
            ajaxParams.put(FontsContractCompat.Columns.FILE_ID, b3.getString("fileId"));
            ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
        } else {
            ajaxParams.put("to_user_id", b3.getString("to_user_id"));
            ajaxParams.put("obj_id", b3.getString("obj_id"));
            ajaxParams.put("module_type", b3.getString("module_type") + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(MyCommentAdapter.this.mContext, "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(MyCommentAdapter.this.mContext, jSONObject.optString("message"), 0).show();
                        return;
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", MyCommentAdapter.this.mContext);
                    BaseActivity baseActivity = MyCommentAdapter.this.mActivity;
                    if (baseActivity != null && (baseActivity instanceof CommentReplyActivity)) {
                        EventBus.getDefault().post("shuaxinhuifu");
                        NewToast.showShort(ProjectApp.instance(), "评论成功");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                        return;
                    }
                    if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        int iIntValue = changeCode.intValue();
                        if (iIntValue == 1) {
                            MyCommentAdapter.this.mHotBean.add(0, hotBean);
                        } else if (iIntValue == 2) {
                            MyCommentAdapter.this.mHotBean.remove(MyCommentAdapter.this.mHotBean);
                            MyCommentAdapter.this.mHotBean.add(0, hotBean);
                        }
                        MyCommentAdapter.this.notifyDataSetChanged();
                    }
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    CommonUtil.showFristDialog(jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putDigest(String id, String obj_id) {
    }

    public void putOppose(String module_type, String id, String obj_id, String type, String content) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutOpposeUrl;
        if (this.mIsSource) {
            str = NetworkRequestsURL.soutceCommentOppose;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("obj_id", obj_id);
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    new JSONObject(s2).optString("code").equals("200");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        String str2 = this.viewType;
        if (str2 == null || !str2.equals("1") || this.mIsSource) {
            return;
        }
        String str3 = "[\"" + id + "\"]";
        String str4 = "[\"" + content + "\"]";
        String targetData = getTargetData(module_type);
        String key = (type.equals("1") ? AliyunEvent.AddOppose : AliyunEvent.CancelOppose).getKey();
        CommonUtil.addLog(key, (type.equals("1") ? AliyunEvent.AddOppose : AliyunEvent.CancelOppose).getValue(), System.currentTimeMillis() + "", "", str3, str4, targetData, "2");
    }

    public void putPraise(String module_type, String id, String obj_id, String type, String content) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutPraseUrl;
        if (this.mIsSource) {
            str = NetworkRequestsURL.soutceCommentSupport;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("obj_id", obj_id);
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.MyCommentAdapter.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
            }
        });
        String str2 = this.viewType;
        if (str2 == null || !str2.equals("1") || this.mIsSource) {
            return;
        }
        String targetData = getTargetData(module_type + "");
        String str3 = "[\"" + id + "\"]";
        String str4 = "[\"" + content + "\"]";
        String key = (type.equals("1") ? AliyunEvent.AddPraise : AliyunEvent.CancelPraise).getKey();
        CommonUtil.addLog(key, (type.equals("1") ? AliyunEvent.AddPraise : AliyunEvent.CancelPraise).getValue(), System.currentTimeMillis() + "", "", str3, str4, targetData, "2");
    }

    public void setisViable(boolean is_viable) {
        this.isVisiable = is_viable;
    }

    @Override // android.widget.Adapter
    public CommentBean.DataBean.HotBean getItem(int position) {
        return this.mHotBean.get(position);
    }

    public MyCommentAdapter(BaseActivity mActivity, List<CommentBean.DataBean.HotBean> mHotBean, Context mContext, String comment_type, boolean is_callMe, boolean isSource, String is_prohibit, String viewType) {
        this.mIsSource = false;
        this.mHotBean = mHotBean;
        this.mContext = mContext;
        this.comment_type = comment_type;
        this.is_callMe = is_callMe;
        this.mActivity = mActivity;
        this.mIsSource = isSource;
        this.is_prohibit = is_prohibit;
        this.viewType = viewType;
    }
}
