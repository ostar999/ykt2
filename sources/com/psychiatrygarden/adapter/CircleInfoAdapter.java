package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.CircleInfoAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.SubComments;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.HttpRequstData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BianjiInfaceInput;
import com.psychiatrygarden.interfaceclass.OnClickDiogListenter;
import com.psychiatrygarden.interfaceclass.OnClickbianjiListenter;
import com.psychiatrygarden.interfaceclass.OnMInputListenster;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommentListenter;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogAdminInput;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.FloorView;
import com.psychiatrygarden.widget.MyExpendView;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.psychiatrygarden.widget.PopInputWindow;
import com.psychiatrygarden.widget.PopupBianjiComWindow;
import com.psychiatrygarden.widget.PopupComWindow;
import com.psychiatrygarden.widget.PopupWin7Comment;
import com.psychiatrygarden.widget.QuestionAdWidegt;
import com.psychiatrygarden.widget.SubFloorFactory;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleInfoAdapter extends BaseAdapter implements PinnedSectionListView1.PinnedSectionListAdapter {
    private View anchorView;
    private CircleInfoClickIml circleInfoClickIml;
    private String comment_type;
    private Context context;
    private String courseId;
    private boolean fromVideo;
    private String is_is_prohibit;
    private boolean landScape;
    private List<CommentBean.DataBean.HotBean> list;
    private ItemActionListener mActionListener;
    public BaseActivity mActivity;
    private String mAppId;
    public CommentBean.DataBean.HotBean mH;
    private int module_type;
    private String question_id;
    private boolean showAd;
    public List<CommentBean.DataBean.HotBean> time_line;
    private boolean ykbAd;
    private boolean isNewComzantong = false;
    private boolean isOkTrue = false;
    private Handler mHandler = new AnonymousClass1();

    /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {

        /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$1$1, reason: invalid class name and collision with other inner class name */
        public class C02791 implements onDialogShareClickListener {
            final /* synthetic */ CommentBean.DataBean.HotBean.ReplyBean val$commListBean;
            final /* synthetic */ CommentBean.DataBean.HotBean val$mHotbean;
            final /* synthetic */ String val$reply_primary_id;
            final /* synthetic */ View val$v;

            public C02791(final CommentBean.DataBean.HotBean.ReplyBean val$commListBean, final String val$reply_primary_id, final CommentBean.DataBean.HotBean val$mHotbean, final View val$v) {
                this.val$commListBean = val$commListBean;
                this.val$reply_primary_id = val$reply_primary_id;
                this.val$mHotbean = val$mHotbean;
                this.val$v = val$v;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, CommentBean.DataBean.HotBean hotBean, String str2, String str3, String str4) {
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("to_user_id", replyBean.getUser_id());
                    bundle.putString("module_type", CircleInfoAdapter.this.module_type + "");
                    bundle.putString("content", str2);
                    bundle.putString("parent_id", replyBean.getId());
                    bundle.putString("reply_primary_id", str);
                    bundle.putString("b_img", str3);
                    bundle.putString("s_img", str4);
                    CircleInfoAdapter.this.putComment(bundle, 1, hotBean);
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("to_user_id", replyBean.getUser_id());
                bundle2.putString("module_type", CircleInfoAdapter.this.module_type + "");
                bundle2.putString("content", str2);
                bundle2.putString("parent_id", replyBean.getId());
                bundle2.putString("reply_primary_id", str);
                bundle2.putSerializable("mHotbean", hotBean);
                bundle2.putString("b_img", str3);
                bundle2.putString("s_img", str4);
                bundle2.putInt("type", 1);
                bundle2.putInt("result", 0);
                Intent intent = new Intent(CircleInfoAdapter.this.context, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle2);
                CircleInfoAdapter.this.mActivity.startActivityForResult(intent, 0);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$1(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                CircleInfoAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$2(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
                if (i2 == 0) {
                    new PopInputWindow(CircleInfoAdapter.this.context, replyBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.1
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                            String id = replyBean.getId();
                            String content = replyBean.getContent();
                            String praise_num = replyBean.getPraise_num();
                            String oppose_num = replyBean.getOppose_num();
                            CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                            circleInfoAdapter.mBianjiDataVisition(id, txtSter, content, praise_num, oppose_num, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                }
                if (i2 != 1) {
                    if (i2 == 2) {
                        new PopInputWindow(CircleInfoAdapter.this.context, replyBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.2
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public void mInputDataListter(String txtSter) {
                                CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                                String id = replyBean.getId();
                                String ctime_timestamp = replyBean.getCtime_timestamp();
                                String content = replyBean.getContent();
                                String oppose_num = replyBean.getOppose_num();
                                CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                                circleInfoAdapter.mBianjiDataVisition(id, ctime_timestamp, content, txtSter, oppose_num, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                            }
                        }).showPopUp(view);
                        return;
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        new PopInputWindow(CircleInfoAdapter.this.context, replyBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.3
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public void mInputDataListter(String txtSter) {
                                CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                                String id = replyBean.getId();
                                String ctime_timestamp = replyBean.getCtime_timestamp();
                                String content = replyBean.getContent();
                                String praise_num = replyBean.getPraise_num();
                                CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                                circleInfoAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                            }
                        }).showPopUp(view);
                        return;
                    }
                }
                Context context = CircleInfoAdapter.this.context;
                onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.b0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14304a.lambda$onclickIntBack$1(replyBean, hotBean, str, str2, str3);
                    }
                };
                String content = replyBean.getContent();
                StringBuilder sb = new StringBuilder();
                sb.append("正在编辑");
                sb.append(TextUtils.equals(replyBean.getUser_id(), UserConfig.getUserId()) ? "我" : replyBean.getNickname());
                sb.append("的评论...");
                new DialogInput(context, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img(), 1, replyBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$3(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                CircleInfoAdapter.this.mBianjiDataVisition(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$4() {
                CircleInfoAdapter.this.setAlpha(true);
            }

            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public void onclickIntBack(int i2) {
                switch (i2) {
                    case 0:
                        if (CircleInfoAdapter.this.is_is_prohibit.equals("1")) {
                            ToastUtil.shortToast(CircleInfoAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                            break;
                        } else {
                            boolean z2 = CircleInfoAdapter.this.fromVideo;
                            Context context = CircleInfoAdapter.this.context;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean = this.val$commListBean;
                            final String str = this.val$reply_primary_id;
                            final CommentBean.DataBean.HotBean hotBean = this.val$mHotbean;
                            new DialogInput(z2, context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.x
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str2, String str3, String str4) {
                                    this.f15151a.lambda$onclickIntBack$0(replyBean, str, hotBean, str2, str3, str4);
                                }
                            }, "", "回复" + this.val$commListBean.getNickname(), false).show();
                            break;
                        }
                    case 1:
                        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                            BaseActivity baseActivity = CircleInfoAdapter.this.mActivity;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean2 = this.val$commListBean;
                            final CommentBean.DataBean.HotBean hotBean2 = this.val$mHotbean;
                            final View view = this.val$v;
                            new XPopup.Builder(CircleInfoAdapter.this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(baseActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.y
                                @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                                public final void mBianjiData(int i3) {
                                    this.f15182a.lambda$onclickIntBack$2(replyBean2, hotBean2, view, i3);
                                }
                            })).show();
                            break;
                        } else {
                            Context context2 = CircleInfoAdapter.this.context;
                            final CommentBean.DataBean.HotBean.ReplyBean replyBean3 = this.val$commListBean;
                            final CommentBean.DataBean.HotBean hotBean3 = this.val$mHotbean;
                            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.z
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str2, String str3, String str4) {
                                    this.f15217a.lambda$onclickIntBack$3(replyBean3, hotBean3, str2, str3, str4);
                                }
                            };
                            String content = this.val$commListBean.getContent();
                            StringBuilder sb = new StringBuilder();
                            sb.append("正在编辑");
                            sb.append(TextUtils.equals(this.val$commListBean.getUser_id(), UserConfig.getUserId()) ? "我" : this.val$commListBean.getNickname());
                            sb.append("的评论...");
                            new DialogInput(context2, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, this.val$commListBean.getC_imgs().getB_img(), this.val$commListBean.getC_imgs().getS_img(), 1, this.val$commListBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
                            break;
                        }
                    case 2:
                        CircleInfoAdapter.this.deleteComm(this.val$commListBean.getId(), this.val$reply_primary_id, 1, this.val$mHotbean);
                        break;
                    case 3:
                        ((ClipboardManager) CircleInfoAdapter.this.context.getSystemService("clipboard")).setText(this.val$commListBean.getContent());
                        NewToast.showShort(CircleInfoAdapter.this.context, "复制成功", 0).show();
                        break;
                    case 4:
                        CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                        circleInfoAdapter.getUserInfo(circleInfoAdapter.context, this.val$commListBean.getUser_id());
                        break;
                    case 5:
                        if ((TextUtils.isEmpty(this.val$commListBean.getIs_essence()) ? "0" : this.val$commListBean.getIs_essence()).equals("1")) {
                            for (int i3 = 0; i3 < CircleInfoAdapter.this.list.size(); i3++) {
                                try {
                                    if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getId())) {
                                        ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).setIs_essence("0");
                                    }
                                    if (((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getReply() != null && ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getReply().size() > 0) {
                                        for (int i4 = 0; i4 < ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getReply().size(); i4++) {
                                            if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getReply().get(i4).getId())) {
                                                ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getReply().get(i4).setIs_essence("0");
                                            }
                                        }
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            NewToast.showShort(CircleInfoAdapter.this.context, "取消加精成功", 0).show();
                        } else {
                            for (int i5 = 0; i5 < CircleInfoAdapter.this.list.size(); i5++) {
                                try {
                                    if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getId())) {
                                        ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).setIs_essence("1");
                                    }
                                    if (((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getReply() != null && ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getReply().size() > 0) {
                                        for (int i6 = 0; i6 < ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getReply().size(); i6++) {
                                            if (this.val$commListBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getReply().get(i6).getId())) {
                                                ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getReply().get(i6).setIs_essence("1");
                                            }
                                        }
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            CircleInfoAdapter.this.putDigest(this.val$commListBean.getId());
                        }
                        CircleInfoAdapter.this.notifyDataSetChanged();
                        break;
                    case 6:
                        new DialogAdminInput(CircleInfoAdapter.this.context, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.4
                            @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                            public void mBianjiInfaceInput(String pushTime, String pushcontent, String pushoppn, String pushno) {
                                C02791 c02791 = C02791.this;
                                CircleInfoAdapter circleInfoAdapter2 = CircleInfoAdapter.this;
                                String id = c02791.val$commListBean.getId();
                                C02791 c027912 = C02791.this;
                                CommentBean.DataBean.HotBean.ReplyBean replyBean4 = c027912.val$commListBean;
                                circleInfoAdapter2.mBianjiDataVisition(id, pushTime, pushcontent, pushoppn, pushno, replyBean4, 1, c027912.val$mHotbean, replyBean4.getC_imgs().getB_img(), C02791.this.val$commListBean.getC_imgs().getS_img());
                            }
                        }, this.val$commListBean.getCtime_timestamp(), this.val$commListBean.getContent(), this.val$commListBean.getPraise_num(), this.val$commListBean.getOppose_num()).show();
                        break;
                    case 7:
                        PopupComWindow popupComWindow = new PopupComWindow(CircleInfoAdapter.this.mActivity, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.5
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public void onDiogClick(final String str2, final PopupComWindow popupComWindow1) {
                                new PopupComWindow(CircleInfoAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.5.1
                                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                                    public void onDiogClick(String str3, PopupComWindow popupComWindow2) {
                                        PopupComWindow popupComWindow3 = popupComWindow1;
                                        if (popupComWindow3 != null && popupComWindow3.isShowing()) {
                                            popupComWindow1.dismiss();
                                        }
                                        if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                                            popupComWindow2.dismiss();
                                        }
                                        HttpRequstData.getIntance(CircleInfoAdapter.this.context).postBannedData(C02791.this.val$commListBean.getUser_id(), str3, str2);
                                    }
                                }).showPopUp(C02791.this.val$v);
                            }
                        }, CircleInfoAdapter.this.landScape);
                        CircleInfoAdapter.this.setAlpha(false);
                        popupComWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.a0
                            @Override // android.widget.PopupWindow.OnDismissListener
                            public final void onDismiss() {
                                this.f14266c.lambda$onclickIntBack$4();
                            }
                        });
                        popupComWindow.showPopUp(this.val$v);
                        break;
                    case 8:
                        new PopupComWindow(CircleInfoAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.1.1.6
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                                if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                                    popupComWindow2.dismiss();
                                }
                                HttpRequstData.getIntance(CircleInfoAdapter.this.context).postReportData(C02791.this.val$commListBean.getId(), str2, CircleInfoAdapter.this.module_type + "");
                            }
                        }, CircleInfoAdapter.this.landScape).showPopUp(this.val$v);
                        break;
                }
            }
        }

        public AnonymousClass1() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    CircleInfoAdapter.this.putPraise(message.getData().getString("id"), message.getData().getString("is_praise").equals("0") ? "1" : "0");
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    CircleInfoAdapter.this.putOppose(message.getData().getString("id"), message.getData().getString("is_Oppose").equals("0") ? "1" : "0");
                    return;
                }
            }
            CommentBean.DataBean.HotBean.ReplyBean replyBean = (CommentBean.DataBean.HotBean.ReplyBean) message.getData().get("commListBean");
            CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) message.getData().get("commListBeans");
            String string = message.getData().getString("reply_primary_id");
            View view = (View) message.obj;
            boolean zEquals = UserConfig.getUserId().equals(replyBean.getUser_id());
            if ("1".equals(replyBean.getStatus())) {
                CommonUtil.mToastShow(CircleInfoAdapter.this.context);
                return;
            }
            new XPopup.Builder(CircleInfoAdapter.this.context).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(CircleInfoAdapter.this.context, replyBean.getContent(), replyBean.getNickname(), zEquals ? 1 : 0, replyBean.getIs_anonymous() + "", replyBean.getDelete_skill(), new C02791(replyBean, string, hotBean, view))).show();
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$3, reason: invalid class name */
    public class AnonymousClass3 implements MyExpendView.OnExpandStateChangeListener {
        final /* synthetic */ View val$finalConverView;
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$3$1, reason: invalid class name */
        public class AnonymousClass1 implements onDialogShareClickListener {
            final /* synthetic */ View val$v;

            public AnonymousClass1(final View val$v) {
                this.val$v = val$v;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$0(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("to_user_id", hotBean.getUser_id());
                    bundle.putString("course_id", CircleInfoAdapter.this.courseId);
                    bundle.putString("module_type", CircleInfoAdapter.this.module_type + "");
                    bundle.putString("content", str);
                    bundle.putString("parent_id", hotBean.getId());
                    bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
                    bundle.putString("b_img", str2);
                    bundle.putString("s_img", str3);
                    CircleInfoAdapter.this.putComment(bundle, 2, hotBean);
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("to_user_id", hotBean.getUser_id());
                bundle2.putString("module_type", CircleInfoAdapter.this.module_type + "");
                bundle2.putString("course_id", CircleInfoAdapter.this.courseId);
                bundle2.putString("content", str);
                bundle2.putString("parent_id", hotBean.getId());
                bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
                bundle2.putSerializable("mHotbean", hotBean);
                bundle2.putInt("type", 2);
                bundle2.putInt("result", 0);
                bundle2.putString("b_img", str2);
                bundle2.putString("s_img", str3);
                Intent intent = new Intent(CircleInfoAdapter.this.context, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle2);
                CircleInfoAdapter.this.mActivity.startActivityForResult(intent, 0);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$10(CommentBean.DataBean.HotBean hotBean, String str) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$11(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$12(CommentBean.DataBean.HotBean hotBean, String str) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$13(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
                if (i2 == 0) {
                    new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.m0
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f14740a.lambda$onclickIntBack$10(hotBean, str);
                        }
                    }).showPopUp(view);
                    return;
                }
                if (i2 != 1) {
                    if (i2 == 2) {
                        new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.o0
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public final void mInputDataListter(String str) {
                                this.f14818a.lambda$onclickIntBack$12(hotBean, str);
                            }
                        }).showPopUp(view);
                        return;
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.3.1.1
                            @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                            public void mInputDataListter(String txtSter) {
                                CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                                String id = hotBean.getId();
                                String ctime_timestamp = hotBean.getCtime_timestamp();
                                String content = hotBean.getContent();
                                String praise_num = hotBean.getPraise_num();
                                CommentBean.DataBean.HotBean hotBean2 = hotBean;
                                circleInfoAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                            }
                        }).showPopUp(view);
                        return;
                    }
                }
                Context context = CircleInfoAdapter.this.context;
                onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.n0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14781a.lambda$onclickIntBack$11(hotBean, str, str2, str3);
                    }
                };
                String content = hotBean.getContent();
                StringBuilder sb = new StringBuilder();
                sb.append("正在编辑");
                sb.append(TextUtils.equals(hotBean.getUser_id(), UserConfig.getUserId()) ? "我" : hotBean.getNickname());
                sb.append("的评论...");
                new DialogInput(context, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$14(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$15(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, str2, str3, str4, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ void lambda$onclickIntBack$16(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean hotBean, String str, String str2, PopupComWindow popupComWindow2) {
                if (popupComWindow != null && popupComWindow.isShowing()) {
                    popupComWindow.dismiss();
                }
                if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                    popupComWindow2.dismiss();
                }
                HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$17(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
                new PopupComWindow(CircleInfoAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.k0
                    @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                    public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                        CircleInfoAdapter.AnonymousClass3.AnonymousClass1.lambda$onclickIntBack$16(popupComWindow, hotBean, str, str2, popupComWindow2);
                    }
                }).showPopUp(view);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$18() {
                CircleInfoAdapter.this.setAlpha(true);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$2(CommentBean.DataBean.HotBean hotBean, String str) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$3(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
                new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.j0
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14625a.lambda$onclickIntBack$2(hotBean, str);
                    }
                }).showPopUp(view);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$4(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$5(final CommentBean.DataBean.HotBean hotBean, View view) {
                Context context = CircleInfoAdapter.this.context;
                onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.p0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f14858a.lambda$onclickIntBack$4(hotBean, str, str2, str3);
                    }
                };
                String content = hotBean.getContent();
                StringBuilder sb = new StringBuilder();
                sb.append("正在编辑");
                sb.append(TextUtils.equals(hotBean.getUser_id(), UserConfig.getUserId()) ? "我" : hotBean.getNickname());
                sb.append("的评论...");
                new DialogInput(context, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$6(CommentBean.DataBean.HotBean hotBean, String str) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$7(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
                new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.l0
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14694a.lambda$onclickIntBack$6(hotBean, str);
                    }
                }).showPopUp(view);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$8(CommentBean.DataBean.HotBean hotBean, String str) {
                CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), str, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onclickIntBack$9(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
                new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.c0
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14341a.lambda$onclickIntBack$8(hotBean, str);
                    }
                }).showPopUp(view);
            }

            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public void onclickIntBack(int i2) {
                switch (i2) {
                    case 0:
                        if (CircleInfoAdapter.this.is_is_prohibit.equals("1")) {
                            ToastUtil.shortToast(CircleInfoAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                            break;
                        } else {
                            boolean z2 = CircleInfoAdapter.this.fromVideo;
                            Context context = CircleInfoAdapter.this.context;
                            final CommentBean.DataBean.HotBean hotBean = AnonymousClass3.this.val$mCommentBean;
                            new DialogInput(z2, context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.q0
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str, String str2, String str3) {
                                    this.f14896a.lambda$onclickIntBack$0(hotBean, str, str2, str3);
                                }
                            }, "", "回复 " + AnonymousClass3.this.val$mCommentBean.getNickname(), CircleInfoAdapter.this.isNewComzantong, CircleInfoAdapter.this.landScape).show();
                            break;
                        }
                    case 1:
                        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                            if (CircleInfoAdapter.this.landScape) {
                                View viewInflate = View.inflate(CircleInfoAdapter.this.context, R.layout.pop_edit_window, null);
                                final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -2);
                                popupWindow.setBackgroundDrawable(new ColorDrawable(0));
                                popupWindow.setOutsideTouchable(true);
                                popupWindow.setFocusable(true);
                                popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
                                viewInflate.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.s0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });
                                View viewFindViewById = viewInflate.findViewById(R.id.bainjishijian);
                                final CommentBean.DataBean.HotBean hotBean2 = AnonymousClass3.this.val$mCommentBean;
                                final View view = this.val$v;
                                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.t0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view2) {
                                        this.f15018c.lambda$onclickIntBack$3(hotBean2, view, view2);
                                    }
                                });
                                View viewFindViewById2 = viewInflate.findViewById(R.id.bainjineirong);
                                final CommentBean.DataBean.HotBean hotBean3 = AnonymousClass3.this.val$mCommentBean;
                                viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.u0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view2) {
                                        this.f15053c.lambda$onclickIntBack$5(hotBean3, view2);
                                    }
                                });
                                View viewFindViewById3 = viewInflate.findViewById(R.id.bainjizantongshu);
                                final CommentBean.DataBean.HotBean hotBean4 = AnonymousClass3.this.val$mCommentBean;
                                final View view2 = this.val$v;
                                viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.d0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view3) {
                                        this.f14382c.lambda$onclickIntBack$7(hotBean4, view2, view3);
                                    }
                                });
                                View viewFindViewById4 = viewInflate.findViewById(R.id.bainjifanduishu);
                                final CommentBean.DataBean.HotBean hotBean5 = AnonymousClass3.this.val$mCommentBean;
                                final View view3 = this.val$v;
                                viewFindViewById4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.e0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view4) {
                                        this.f14418c.lambda$onclickIntBack$9(hotBean5, view3, view4);
                                    }
                                });
                                popupWindow.showAtLocation(AnonymousClass3.this.val$finalConverView, 80, 0, 0);
                                popupWindow.update();
                                break;
                            } else {
                                AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                BaseActivity baseActivity = CircleInfoAdapter.this.mActivity;
                                final CommentBean.DataBean.HotBean hotBean6 = anonymousClass3.val$mCommentBean;
                                final View view4 = this.val$v;
                                new XPopup.Builder(CircleInfoAdapter.this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(baseActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.f0
                                    @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                                    public final void mBianjiData(int i3) {
                                        this.f14458a.lambda$onclickIntBack$13(hotBean6, view4, i3);
                                    }
                                })).show();
                                break;
                            }
                        } else {
                            Context context2 = CircleInfoAdapter.this.context;
                            final CommentBean.DataBean.HotBean hotBean7 = AnonymousClass3.this.val$mCommentBean;
                            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.g0
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str, String str2, String str3) {
                                    this.f14497a.lambda$onclickIntBack$14(hotBean7, str, str2, str3);
                                }
                            };
                            String content = AnonymousClass3.this.val$mCommentBean.getContent();
                            StringBuilder sb = new StringBuilder();
                            sb.append("正在编辑");
                            sb.append(TextUtils.equals(AnonymousClass3.this.val$mCommentBean.getUser_id(), UserConfig.getUserId()) ? "我" : AnonymousClass3.this.val$mCommentBean.getNickname());
                            sb.append("的评论...");
                            new DialogInput(context2, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, AnonymousClass3.this.val$mCommentBean.getC_imgs().getB_img(), AnonymousClass3.this.val$mCommentBean.getC_imgs().getS_img(), 1, AnonymousClass3.this.val$mCommentBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
                            break;
                        }
                    case 2:
                        AnonymousClass3 anonymousClass32 = AnonymousClass3.this;
                        CircleInfoAdapter.this.deleteComm(anonymousClass32.val$mCommentBean.getId(), AnonymousClass3.this.val$mCommentBean.getReply_primary_id(), 2, AnonymousClass3.this.val$mCommentBean);
                        break;
                    case 3:
                        ((ClipboardManager) CircleInfoAdapter.this.context.getSystemService("clipboard")).setText(AnonymousClass3.this.val$mCommentBean.getContent());
                        NewToast.showShort(CircleInfoAdapter.this.context, "复制成功", 0).show();
                        break;
                    case 4:
                        CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                        circleInfoAdapter.getUserInfo(circleInfoAdapter.context, AnonymousClass3.this.val$mCommentBean.getUser_id());
                        break;
                    case 5:
                        if ((TextUtils.isEmpty(AnonymousClass3.this.val$mCommentBean.getIs_essence()) ? "0" : AnonymousClass3.this.val$mCommentBean.getIs_essence()).equals("1")) {
                            try {
                                AnonymousClass3.this.val$mCommentBean.setIs_essence("0");
                                for (int i3 = 0; i3 < CircleInfoAdapter.this.list.size(); i3++) {
                                    List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply();
                                    if (reply != null && reply.size() > 0) {
                                        for (int i4 = 0; i4 < reply.size(); i4++) {
                                            if (AnonymousClass3.this.val$mCommentBean.getId().equals(reply.get(i4).getId())) {
                                                reply.get(i4).setIs_essence("0");
                                            }
                                        }
                                    }
                                }
                                CircleInfoAdapter.this.notifyDataSetChanged();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            NewToast.showShort(CircleInfoAdapter.this.context, "取消加精成功", 0).show();
                            break;
                        } else {
                            try {
                                AnonymousClass3.this.val$mCommentBean.setIs_essence("1");
                                for (int i5 = 0; i5 < CircleInfoAdapter.this.list.size(); i5++) {
                                    List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply();
                                    if (reply2 != null && reply2.size() > 0) {
                                        for (int i6 = 0; i6 < reply2.size(); i6++) {
                                            if (AnonymousClass3.this.val$mCommentBean.getId().equals(reply2.get(i6).getId())) {
                                                reply2.get(i6).setIs_essence("1");
                                            }
                                        }
                                    }
                                }
                                CircleInfoAdapter.this.notifyDataSetChanged();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            AnonymousClass3 anonymousClass33 = AnonymousClass3.this;
                            CircleInfoAdapter.this.putDigest(anonymousClass33.val$mCommentBean.getId());
                            break;
                        }
                        break;
                    case 6:
                        Context context3 = CircleInfoAdapter.this.context;
                        final CommentBean.DataBean.HotBean hotBean8 = AnonymousClass3.this.val$mCommentBean;
                        new DialogAdminInput(context3, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.h0
                            @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                            public final void mBianjiInfaceInput(String str, String str2, String str3, String str4) {
                                this.f14541a.lambda$onclickIntBack$15(hotBean8, str, str2, str3, str4);
                            }
                        }, AnonymousClass3.this.val$mCommentBean.getCtime_timestamp(), AnonymousClass3.this.val$mCommentBean.getContent(), AnonymousClass3.this.val$mCommentBean.getPraise_num(), AnonymousClass3.this.val$mCommentBean.getOppose_num()).show();
                        break;
                    case 7:
                        AnonymousClass3 anonymousClass34 = AnonymousClass3.this;
                        BaseActivity baseActivity2 = CircleInfoAdapter.this.mActivity;
                        final CommentBean.DataBean.HotBean hotBean9 = anonymousClass34.val$mCommentBean;
                        final View view5 = this.val$v;
                        PopupComWindow popupComWindow = new PopupComWindow(baseActivity2, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.i0
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public final void onDiogClick(String str, PopupComWindow popupComWindow2) {
                                this.f14579a.lambda$onclickIntBack$17(hotBean9, view5, str, popupComWindow2);
                            }
                        }, CircleInfoAdapter.this.landScape);
                        CircleInfoAdapter.this.setAlpha(false);
                        popupComWindow.showPopUp(this.val$v);
                        popupComWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.r0
                            @Override // android.widget.PopupWindow.OnDismissListener
                            public final void onDismiss() {
                                this.f14938c.lambda$onclickIntBack$18();
                            }
                        });
                        break;
                    case 8:
                        new PopupComWindow(CircleInfoAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.3.1.2
                            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                            public void onDiogClick(String str, PopupComWindow popupComWindow2) {
                                if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                                    popupComWindow2.dismiss();
                                }
                                HttpRequstData.getIntance(CircleInfoAdapter.this.context).postReportData(AnonymousClass3.this.val$mCommentBean.getId(), str, CircleInfoAdapter.this.module_type + "");
                            }
                        }, CircleInfoAdapter.this.landScape).showPopUp(this.val$v);
                        break;
                }
            }
        }

        public AnonymousClass3(final CommentBean.DataBean.HotBean val$mCommentBean, final View val$finalConverView) {
            this.val$mCommentBean = val$mCommentBean;
            this.val$finalConverView = val$finalConverView;
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onClickStateChange(View view) {
            boolean zEquals = UserConfig.getUserId().equals(this.val$mCommentBean.getUser_id());
            if ("1".equals(this.val$mCommentBean.getStatus())) {
                CommonUtil.mToastShow(CircleInfoAdapter.this.context);
                return;
            }
            PopupWin7Comment popupWin7Comment = new PopupWin7Comment(CircleInfoAdapter.this.context, this.val$mCommentBean.getContent(), this.val$mCommentBean.getNickname(), zEquals ? 1 : 0, this.val$mCommentBean.getIs_anonymous() + "", this.val$mCommentBean.getDelete_skill(), new AnonymousClass1(view));
            XPopup.Builder builder = new XPopup.Builder(CircleInfoAdapter.this.context);
            popupWin7Comment.setLandScape(CircleInfoAdapter.this.landScape);
            builder.popupWidth(CircleInfoAdapter.this.context.getResources().getDisplayMetrics().widthPixels);
            if (CircleInfoAdapter.this.landScape) {
                if (CircleInfoAdapter.this.anchorView != null) {
                    popupWin7Comment.setPopAnchorView(CircleInfoAdapter.this.anchorView);
                }
                builder.popupHeight(SizeUtil.dp2px(CircleInfoAdapter.this.context, R2.attr.ad_height));
            }
            builder.autoDismiss(Boolean.FALSE).asCustom(popupWin7Comment).show();
        }

        @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
        public void onExpandStateChanged(boolean isExpanded) {
            this.val$mCommentBean.setIs_showValue(isExpanded);
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$4, reason: invalid class name */
    public class AnonymousClass4 implements onDialogShareClickListener {
        final /* synthetic */ View val$finalConverView;
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;
        final /* synthetic */ View val$v;

        public AnonymousClass4(final CommentBean.DataBean.HotBean val$mCommentBean, final View val$v, final View val$finalConverView) {
            this.val$mCommentBean = val$mCommentBean;
            this.val$v = val$v;
            this.val$finalConverView = val$finalConverView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$0(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", hotBean.getUser_id());
                bundle.putString("module_type", CircleInfoAdapter.this.module_type + "");
                bundle.putString("course_id", CircleInfoAdapter.this.courseId);
                bundle.putString("content", str);
                bundle.putString("parent_id", hotBean.getId());
                bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                CircleInfoAdapter.this.putComment(bundle, 2, hotBean);
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", hotBean.getUser_id());
            bundle2.putString("module_type", CircleInfoAdapter.this.module_type + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", hotBean.getId());
            bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 2);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(CircleInfoAdapter.this.context, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            CircleInfoAdapter.this.mActivity.startActivityForResult(intent, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$10(CommentBean.DataBean.HotBean hotBean, String str) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$11(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$12(final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            if (i2 == 0) {
                new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.b1
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f14307a.lambda$onclickIntBack$10(hotBean, str);
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.4.1
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                            String id = hotBean.getId();
                            String ctime_timestamp = hotBean.getCtime_timestamp();
                            String content = hotBean.getContent();
                            String oppose_num = hotBean.getOppose_num();
                            CommentBean.DataBean.HotBean hotBean2 = hotBean;
                            circleInfoAdapter.mBianjiDataVisition(id, ctime_timestamp, content, txtSter, oppose_num, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.4.2
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                            String id = hotBean.getId();
                            String ctime_timestamp = hotBean.getCtime_timestamp();
                            String content = hotBean.getContent();
                            String praise_num = hotBean.getPraise_num();
                            CommentBean.DataBean.HotBean hotBean2 = hotBean;
                            circleInfoAdapter.mBianjiDataVisition(id, ctime_timestamp, content, praise_num, txtSter, null, 2, hotBean2, hotBean2.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            String nickname = hotBean.getNickname();
            if (TextUtils.equals(hotBean.getUser_id(), UserConfig.getUserId())) {
                nickname = "我";
            }
            new DialogInput(CircleInfoAdapter.this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.c1
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14343a.lambda$onclickIntBack$11(hotBean, str, str2, str3);
                }
            }, hotBean.getContent(), "正在编辑" + nickname + "的评论...", CircleInfoAdapter.this.isNewComzantong, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$13(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$14(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, str2, str3, str4, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$15(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(CircleInfoAdapter.this.mActivity, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.4.3
                @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                public void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                    PopupComWindow popupComWindow3 = popupComWindow;
                    if (popupComWindow3 != null && popupComWindow3.isShowing()) {
                        popupComWindow.dismiss();
                    }
                    if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                        popupComWindow2.dismiss();
                    }
                    HttpRequstData.getIntance(ProjectApp.instance()).postBannedData(hotBean.getUser_id(), str2, str);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$16() {
            CircleInfoAdapter.this.setAlpha(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$2(CommentBean.DataBean.HotBean hotBean, String str) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$3(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
            new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.a1
                @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                public final void mInputDataListter(String str) {
                    this.f14267a.lambda$onclickIntBack$2(hotBean, str);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$4(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$5(final CommentBean.DataBean.HotBean hotBean, View view) {
            Context context = CircleInfoAdapter.this.context;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.x0
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f15155a.lambda$onclickIntBack$4(hotBean, str, str2, str3);
                }
            };
            String content = hotBean.getContent();
            StringBuilder sb = new StringBuilder();
            sb.append("正在编辑");
            sb.append(TextUtils.equals(hotBean.getUser_id(), UserConfig.getUserId()) ? "我" : hotBean.getNickname());
            sb.append("的评论...");
            new DialogInput(context, ondialogclicklistener, content, sb.toString(), CircleInfoAdapter.this.isNewComzantong, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img(), 1, hotBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$6(CommentBean.DataBean.HotBean hotBean, String str) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$7(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
            new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.y0
                @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                public final void mInputDataListter(String str) {
                    this.f15186a.lambda$onclickIntBack$6(hotBean, str);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$8(CommentBean.DataBean.HotBean hotBean, String str) {
            CircleInfoAdapter.this.mBianjiDataVisition(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), str, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$9(final CommentBean.DataBean.HotBean hotBean, View view, View view2) {
            new PopInputWindow(CircleInfoAdapter.this.context, hotBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.adapter.z0
                @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                public final void mInputDataListter(String str) {
                    this.f15220a.lambda$onclickIntBack$8(hotBean, str);
                }
            }).showPopUp(view);
        }

        @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
        public void onclickIntBack(int i2) {
            switch (i2) {
                case 0:
                    if (CircleInfoAdapter.this.is_is_prohibit.equals("1")) {
                        ToastUtil.shortToast(CircleInfoAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                        break;
                    } else {
                        boolean z2 = CircleInfoAdapter.this.fromVideo;
                        Context context = CircleInfoAdapter.this.context;
                        final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
                        new DialogInput(z2, context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.v0
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f15089a.lambda$onclickIntBack$0(hotBean, str, str2, str3);
                            }
                        }, "", "回复 " + this.val$mCommentBean.getNickname(), CircleInfoAdapter.this.isNewComzantong, CircleInfoAdapter.this.landScape).show();
                        break;
                    }
                case 1:
                    if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        if (CircleInfoAdapter.this.landScape) {
                            View viewInflate = View.inflate(CircleInfoAdapter.this.context, R.layout.pop_edit_window, null);
                            final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -2);
                            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.setFocusable(true);
                            popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
                            viewInflate.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.e1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            View viewFindViewById = viewInflate.findViewById(R.id.bainjishijian);
                            final CommentBean.DataBean.HotBean hotBean2 = this.val$mCommentBean;
                            final View view = this.val$v;
                            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.f1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    this.f14461c.lambda$onclickIntBack$3(hotBean2, view, view2);
                                }
                            });
                            View viewFindViewById2 = viewInflate.findViewById(R.id.bainjineirong);
                            final CommentBean.DataBean.HotBean hotBean3 = this.val$mCommentBean;
                            viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.g1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    this.f14499c.lambda$onclickIntBack$5(hotBean3, view2);
                                }
                            });
                            View viewFindViewById3 = viewInflate.findViewById(R.id.bainjizantongshu);
                            final CommentBean.DataBean.HotBean hotBean4 = this.val$mCommentBean;
                            final View view2 = this.val$v;
                            viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.h1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view3) {
                                    this.f14543c.lambda$onclickIntBack$7(hotBean4, view2, view3);
                                }
                            });
                            View viewFindViewById4 = viewInflate.findViewById(R.id.bainjifanduishu);
                            final CommentBean.DataBean.HotBean hotBean5 = this.val$mCommentBean;
                            final View view3 = this.val$v;
                            viewFindViewById4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.i1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view4) {
                                    this.f14582c.lambda$onclickIntBack$9(hotBean5, view3, view4);
                                }
                            });
                            popupWindow.showAtLocation(this.val$finalConverView, 80, 0, 0);
                            popupWindow.update();
                            break;
                        } else {
                            BaseActivity baseActivity = CircleInfoAdapter.this.mActivity;
                            final CommentBean.DataBean.HotBean hotBean6 = this.val$mCommentBean;
                            final View view4 = this.val$v;
                            new XPopup.Builder(CircleInfoAdapter.this.context).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(baseActivity, new OnClickbianjiListenter() { // from class: com.psychiatrygarden.adapter.j1
                                @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                                public final void mBianjiData(int i3) {
                                    this.f14627a.lambda$onclickIntBack$12(hotBean6, view4, i3);
                                }
                            })).show();
                            break;
                        }
                    } else {
                        String nickname = this.val$mCommentBean.getNickname();
                        if (TextUtils.equals(this.val$mCommentBean.getUser_id(), UserConfig.getUserId())) {
                            nickname = "我";
                        }
                        Context context2 = CircleInfoAdapter.this.context;
                        final CommentBean.DataBean.HotBean hotBean7 = this.val$mCommentBean;
                        new DialogInput(context2, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.k1
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f14665a.lambda$onclickIntBack$13(hotBean7, str, str2, str3);
                            }
                        }, this.val$mCommentBean.getContent(), "正在编辑" + nickname + "的评论...", CircleInfoAdapter.this.isNewComzantong, this.val$mCommentBean.getC_imgs().getB_img(), this.val$mCommentBean.getC_imgs().getS_img(), 1, this.val$mCommentBean.getC_imgs().getVideoId(), CircleInfoAdapter.this.landScape).show();
                        break;
                    }
                case 2:
                    CircleInfoAdapter.this.deleteComm(this.val$mCommentBean.getId(), this.val$mCommentBean.getReply_primary_id(), 2, this.val$mCommentBean);
                    break;
                case 3:
                    ((ClipboardManager) CircleInfoAdapter.this.context.getSystemService("clipboard")).setText(this.val$mCommentBean.getContent());
                    NewToast.showShort(CircleInfoAdapter.this.context, "复制成功", 0).show();
                    break;
                case 4:
                    CircleInfoAdapter circleInfoAdapter = CircleInfoAdapter.this;
                    circleInfoAdapter.getUserInfo(circleInfoAdapter.context, this.val$mCommentBean.getUser_id());
                    break;
                case 5:
                    if ((TextUtils.isEmpty(this.val$mCommentBean.getIs_essence()) ? "0" : this.val$mCommentBean.getIs_essence()).equals("1")) {
                        try {
                            this.val$mCommentBean.setIs_essence("0");
                            for (int i3 = 0; i3 < CircleInfoAdapter.this.list.size(); i3++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i4 = 0; i4 < reply.size(); i4++) {
                                        if (this.val$mCommentBean.getId().equals(reply.get(i4).getId())) {
                                            reply.get(i4).setIs_essence("0");
                                        }
                                    }
                                }
                            }
                            CircleInfoAdapter.this.notifyDataSetChanged();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        NewToast.showShort(CircleInfoAdapter.this.context, "取消加精成功", 0).show();
                        break;
                    } else {
                        try {
                            this.val$mCommentBean.setIs_essence("1");
                            for (int i5 = 0; i5 < CircleInfoAdapter.this.list.size(); i5++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply2 = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply();
                                if (reply2 != null && reply2.size() > 0) {
                                    for (int i6 = 0; i6 < reply2.size(); i6++) {
                                        if (this.val$mCommentBean.getId().equals(reply2.get(i6).getId())) {
                                            reply2.get(i6).setIs_essence("1");
                                        }
                                    }
                                }
                            }
                            CircleInfoAdapter.this.notifyDataSetChanged();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        CircleInfoAdapter.this.putDigest(this.val$mCommentBean.getId());
                        break;
                    }
                    break;
                case 6:
                    Context context3 = CircleInfoAdapter.this.context;
                    final CommentBean.DataBean.HotBean hotBean8 = this.val$mCommentBean;
                    new DialogAdminInput(context3, new BianjiInfaceInput() { // from class: com.psychiatrygarden.adapter.l1
                        @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                        public final void mBianjiInfaceInput(String str, String str2, String str3, String str4) {
                            this.f14696a.lambda$onclickIntBack$14(hotBean8, str, str2, str3, str4);
                        }
                    }, this.val$mCommentBean.getCtime_timestamp(), this.val$mCommentBean.getContent(), this.val$mCommentBean.getPraise_num(), this.val$mCommentBean.getOppose_num()).show();
                    break;
                case 7:
                    BaseActivity baseActivity2 = CircleInfoAdapter.this.mActivity;
                    final CommentBean.DataBean.HotBean hotBean9 = this.val$mCommentBean;
                    final View view5 = this.val$v;
                    PopupComWindow popupComWindow = new PopupComWindow(baseActivity2, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.w0
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str, PopupComWindow popupComWindow2) {
                            this.f15123a.lambda$onclickIntBack$15(hotBean9, view5, str, popupComWindow2);
                        }
                    });
                    popupComWindow.showPopUp(this.val$v);
                    CircleInfoAdapter.this.setAlpha(false);
                    popupComWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.d1
                        @Override // android.widget.PopupWindow.OnDismissListener
                        public final void onDismiss() {
                            this.f14385c.lambda$onclickIntBack$16();
                        }
                    });
                    break;
                case 8:
                    new PopupComWindow(CircleInfoAdapter.this.mActivity, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.4.4
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public void onDiogClick(String str, PopupComWindow popupComWindow2) {
                            if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                                popupComWindow2.dismiss();
                            }
                            HttpRequstData.getIntance(CircleInfoAdapter.this.context).postReportData(AnonymousClass4.this.val$mCommentBean.getId(), str, CircleInfoAdapter.this.module_type + "");
                        }
                    }).showPopUp(this.val$v);
                    break;
            }
        }
    }

    /* renamed from: com.psychiatrygarden.adapter.CircleInfoAdapter$5, reason: invalid class name */
    public class AnonymousClass5 implements CommentListenter {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass5(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commListReply$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", CircleInfoAdapter.this.module_type + "");
                bundle.putString("content", str);
                bundle.putString("course_id", CircleInfoAdapter.this.courseId);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", replyBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                bundle.putString("course_id", CircleInfoAdapter.this.courseId);
                CircleInfoAdapter.this.putComment(bundle, 1, hotBean);
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", replyBean.getUser_id());
            bundle2.putString("module_type", CircleInfoAdapter.this.module_type + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", replyBean.getId());
            bundle2.putString("reply_primary_id", replyBean.getReply_primary_id());
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 1);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(CircleInfoAdapter.this.context, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            CircleInfoAdapter.this.mActivity.startActivityForResult(intent, 0);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListOppose(String moudle_type, String is_Oppose, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 3;
            Bundle bundle = new Bundle();
            bundle.putString("is_Oppose", is_Oppose);
            bundle.putString("id", id);
            messageObtain.setData(bundle);
            CircleInfoAdapter.this.mHandler.sendMessage(messageObtain);
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
            CircleInfoAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraiseFaile() {
            NewToast.showShort(CircleInfoAdapter.this.context, "您已经点赞了", 0).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListReply(final CommentBean.DataBean.HotBean.ReplyBean cmt, View v2) {
            if (Integer.parseInt(cmt.getReply_num()) >= 1) {
                Intent intent = new Intent(CircleInfoAdapter.this.context, (Class<?>) CommentReplyActivity.class);
                intent.putExtra("is_replybean", true);
                intent.putExtra("module_type", CircleInfoAdapter.this.module_type);
                intent.putExtra("comment_type", CircleInfoAdapter.this.comment_type);
                intent.putExtra("bean", cmt);
                intent.putExtra("isVisiable", true);
                intent.putExtra("app_id", CircleInfoAdapter.this.mAppId);
                intent.putExtra("isProhibit", CircleInfoAdapter.this.is_is_prohibit);
                CircleInfoAdapter.this.context.startActivity(intent);
                return;
            }
            if (cmt.getIs_del().equals("1")) {
                NewToast.showShort(CircleInfoAdapter.this.context, "评论已删除", 0).show();
                return;
            }
            boolean z2 = CircleInfoAdapter.this.fromVideo;
            Context context = CircleInfoAdapter.this.context;
            final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
            new DialogInput(z2, context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.m1
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f14742a.lambda$commListReply$0(cmt, hotBean, str, str2, str3);
                }
            }, "", "回复 " + cmt.getNickname(), CircleInfoAdapter.this.isNewComzantong, CircleInfoAdapter.this.landScape).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListSupport(String moudle_type, String is_Support, String id, String obj_id) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            Bundle bundle = new Bundle();
            bundle.putString("is_praise", is_Support);
            bundle.putString("id", id);
            messageObtain.setData(bundle);
            CircleInfoAdapter.this.mHandler.sendMessage(messageObtain);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commentListenerData(CommentBean.DataBean.HotBean.ReplyBean commListBean, View v2) {
            if (CircleInfoAdapter.this.is_is_prohibit.equals("1")) {
                ToastUtil.shortToast(CircleInfoAdapter.this.mActivity, "本帖已被锁定，不支持回帖");
                return;
            }
            Message messageObtain = Message.obtain();
            messageObtain.obj = v2;
            messageObtain.what = 1;
            Bundle bundle = new Bundle();
            bundle.putSerializable("commListBean", commListBean);
            bundle.putSerializable("commListBeans", this.val$mCommentBean);
            bundle.putString("reply_primary_id", this.val$mCommentBean.getReply_primary_id().toString());
            messageObtain.setData(bundle);
            CircleInfoAdapter.this.mHandler.sendMessage(messageObtain);
        }
    }

    public interface CircleInfoClickIml {
        void doSectionClick(int type);
    }

    public interface ItemActionListener {
        void clickLike();
    }

    public static class ViewHolder {
        TextView commentList_item_tv_praise;
        TextView commentList_item_tv_school;
        TextView commentList_item_tv_userName;
        CircleImageView commentList_item_userIcon;
        TextView group_name;
        ImageView imageView2_praiuse;
        TextView isreadtext;
        ImageView isverauth;
        ImageView iv_elite;
        CircleImageView jiav;
        LinearLayout lin_praise;
        RelativeLayout linciew;
        private View lineBottom;
        LinearLayout line_null;
        LinearLayout lineremen;
        LinearLayout lineselect2;
        private QuestionAdWidegt llAd;
        private LinearLayout llAdLayout;
        private LinearLayout llComment;
        private LinearLayout llay_reply_bg;
        MyExpendView myexptervew;
        NineGridTestLayout ningrids;
        ImageView noDataImageView;
        CheckedTextView remen2;
        FloorView subFloors;
        TextView tv_null;
        TextView tv_oppose;
        TextView tv_reply;
        TextView tv_support;
        ImageView vipimgid;
        CheckedTextView zuixin2;
    }

    public CircleInfoAdapter(Context context, List<CommentBean.DataBean.HotBean> list, List<CommentBean.DataBean.HotBean> time_line, int module_type, String comment_type, String question_id, BaseActivity mActivity, String is_is_prohibit, String appId, CircleInfoClickIml circleInfoClickIml) {
        this.is_is_prohibit = "0";
        this.mAppId = "";
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
        this.time_line = time_line;
        this.module_type = module_type;
        this.comment_type = comment_type;
        this.question_id = question_id;
        this.is_is_prohibit = is_is_prohibit;
        this.circleInfoClickIml = circleInfoClickIml;
        this.mAppId = appId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserInfo(Context mContext, String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", user_id);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.get(mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.11
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
                        CommonUtil.mPutChatData(CircleInfoAdapter.this.mActivity, jSONObject.optJSONObject("data").optString("user_uuid"), jSONObject.optJSONObject("data").optString("nickname"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$11() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(ViewHolder viewHolder, int i2) {
        viewHolder.llAdLayout.setVisibility(8);
        this.list.remove(i2);
        setList(this.list);
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$1(View view) {
        this.circleInfoClickIml.doSectionClick(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$10(CommentBean.DataBean.HotBean hotBean, View view, View view2) {
        if (this.is_is_prohibit.equals("1")) {
            ToastUtil.shortToast(this.mActivity, "本帖已被锁定，不支持回帖");
            return;
        }
        boolean zEquals = UserConfig.getUserId().equals(hotBean.getUser_id());
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(this.context);
            return;
        }
        PopupWin7Comment popupWin7Comment = new PopupWin7Comment(this.context, hotBean.getContent(), hotBean.getNickname(), zEquals ? 1 : 0, hotBean.getIs_anonymous() + "", hotBean.getDelete_skill(), new AnonymousClass4(hotBean, view2, view));
        popupWin7Comment.setLandScape(this.landScape);
        XPopup.Builder builder = new XPopup.Builder(this.context);
        builder.popupWidth(this.context.getResources().getDisplayMetrics().widthPixels);
        if (this.landScape) {
            View view3 = this.anchorView;
            if (view3 != null) {
                popupWin7Comment.setPopAnchorView(view3);
            }
            builder.popupHeight(SizeUtil.dp2px(this.context, R2.attr.ad_height));
        }
        builder.autoDismiss(Boolean.FALSE).asCustom(popupWin7Comment).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$2(View view) {
        this.circleInfoClickIml.doSectionClick(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$3(View view) {
        ToastUtil.shortToast(this.context, "已认证");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$4(CommentBean.DataBean.HotBean hotBean, ViewHolder viewHolder, View view) {
        if (hotBean.getIs_oppose().equals("1")) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(this.context, "评论已删除", 0).show();
            return;
        }
        String str = "0";
        try {
            if (hotBean.getIs_praise().equals("1")) {
                putPraise(hotBean.getId(), "0");
                hotBean.setIs_praise("0");
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
            } else {
                Toast_pop(viewHolder.tv_support, 0);
                putPraise(hotBean.getId(), "1");
                hotBean.setIs_praise("1");
                String praise_num2 = hotBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                hotBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                viewHolder.tv_support.setText("已赞同(" + hotBean.getPraise_num() + ")");
            }
        } catch (Exception unused) {
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$5(CommentBean.DataBean.HotBean hotBean, ViewHolder viewHolder, View view) {
        if (hotBean.getIs_praise().equals("1")) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(this.context, "评论已删除", 0).show();
            return;
        }
        if (hotBean.getIs_praise().equals("1")) {
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
            } catch (Exception unused) {
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
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$6(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("to_user_id", hotBean.getUser_id());
            bundle.putString("module_type", this.module_type + "");
            bundle.putString("content", str);
            bundle.putString("course_id", this.courseId);
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
        bundle2.putString("course_id", this.courseId);
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
    public /* synthetic */ void lambda$getView$7(final CommentBean.DataBean.HotBean hotBean, View view) {
        if (Integer.parseInt(hotBean.getReply_num()) > 0) {
            Intent intent = new Intent(this.context, (Class<?>) CommentReplyActivity.class);
            intent.putExtra("is_replybean", false);
            intent.putExtra("comment_type", this.comment_type);
            intent.putExtra("module_type", this.module_type);
            intent.putExtra("bean", hotBean);
            intent.putExtra("isVisiable", true);
            intent.putExtra("app_id", this.mAppId);
            intent.putExtra("isProhibit", this.is_is_prohibit);
            this.context.startActivity(intent);
            return;
        }
        if (this.is_is_prohibit.equals("1")) {
            ToastUtil.shortToast(this.mActivity, "本帖已被锁定，不支持回帖");
            return;
        }
        new DialogInput(this.fromVideo, this.context, new onDialogClickListener() { // from class: com.psychiatrygarden.adapter.n
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f14779a.lambda$getView$6(hotBean, str, str2, str3);
            }
        }, "", "回复 " + hotBean.getNickname(), this.isNewComzantong, this.landScape).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$8(CommentBean.DataBean.HotBean hotBean, View view) {
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
    public /* synthetic */ void lambda$getView$9(CommentBean.DataBean.HotBean hotBean, View view) {
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
    public void setAlpha(boolean normal) {
        WindowManager.LayoutParams attributes = this.mActivity.getWindow().getAttributes();
        if (normal) {
            attributes.alpha = 1.0f;
        } else {
            attributes.alpha = 0.8f;
        }
        this.mActivity.getWindow().setAttributes(attributes);
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.adapter.k
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                CircleInfoAdapter.lambda$Toast_pop$11();
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
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.adapter.o
            @Override // java.lang.Runnable
            public final void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    public void deleteComm(final String id, String reply_primary_id, final Integer valueInt, final CommentBean.DataBean.HotBean mHotBean) {
        this.mActivity.showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("reply_primary_id", reply_primary_id);
        ajaxParams.put("id", id);
        ajaxParams.put("confirm", "1");
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mDeleteUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleInfoAdapter.this.mActivity.hideProgressDialog();
                NewToast.showShort(CircleInfoAdapter.this.context, "请求服务器失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post("delReplyAndLoadData");
                        if (valueInt.intValue() == 1) {
                            for (int i2 = 0; i2 < CircleInfoAdapter.this.list.size(); i2++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i3 = 0; i3 < ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply().size(); i3++) {
                                        if (id.equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply().get(i3).getId())) {
                                            ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply().get(i3).setContent("已删除");
                                            ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply().get(i3).setC_imgs(new ImagesBean());
                                            ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i2)).getReply().get(i3).setIs_del("1");
                                        }
                                    }
                                }
                            }
                            EventBus.getDefault().post("delReplyAndLoadData");
                        } else {
                            int i4 = -1;
                            for (int i5 = 0; i5 < CircleInfoAdapter.this.list.size(); i5++) {
                                if (mHotBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i5)).getId())) {
                                    i4 = i5;
                                }
                            }
                            if (i4 > -1) {
                                if (((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).getReply() == null || ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).getReply().size() <= 0) {
                                    CircleInfoAdapter.this.list.remove(i4);
                                } else {
                                    int size = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).getReply().size() - 1;
                                    CommentBean.DataBean.HotBean.ReplyBean replyBean = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).getReply().get(size);
                                    if (size > 0) {
                                        List<CommentBean.DataBean.HotBean.ReplyBean> listSubList = ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).getReply().subList(0, size);
                                        CircleInfoAdapter.this.list.set(i4, replyBean);
                                        ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).setReply(listSubList);
                                    } else {
                                        ((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i4)).setReply(null);
                                        CircleInfoAdapter.this.list.set(i4, replyBean);
                                    }
                                }
                            }
                        }
                        CircleInfoAdapter.this.notifyDataSetChanged();
                    }
                    NewToast.showShort(CircleInfoAdapter.this.context, jSONObject.optString("message"), 0).show();
                    CircleInfoAdapter.this.mActivity.hideProgressDialog();
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
    public View getView(int position, View converView, ViewGroup viewGrop) throws InterruptedException {
        final View viewInflate;
        final ViewHolder viewHolder;
        float width;
        float width2;
        float f2;
        if (converView == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.context).inflate((this.fromVideo && this.landScape) ? R.layout.item_course_video_comment : R.layout.activity_circleinfo, (ViewGroup) null);
            viewHolder.llComment = (LinearLayout) viewInflate.findViewById(R.id.ll_comment);
            viewHolder.llay_reply_bg = (LinearLayout) viewInflate.findViewById(R.id.llay_reply_bg);
            if (this.fromVideo && this.landScape) {
                viewHolder.llay_reply_bg.setBackground(new ColorDrawable(Color.parseColor("#141414")));
            }
            viewHolder.lineremen = (LinearLayout) viewInflate.findViewById(R.id.lineremen);
            viewHolder.lineBottom = viewInflate.findViewById(R.id.view_line_bottom);
            viewHolder.llAd = (QuestionAdWidegt) viewInflate.findViewById(R.id.ll_ad);
            viewHolder.llAdLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_ad_layout);
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
            viewHolder.noDataImageView = (ImageView) viewInflate.findViewById(R.id.noDataImageView);
            viewHolder.isreadtext = (TextView) viewInflate.findViewById(R.id.isreadtext);
            viewHolder.jiav = (CircleImageView) viewInflate.findViewById(R.id.jiav);
            viewHolder.vipimgid = (ImageView) viewInflate.findViewById(R.id.vipimgid);
            viewHolder.isverauth = (ImageView) viewInflate.findViewById(R.id.isverauth);
            viewHolder.lineselect2 = (LinearLayout) viewInflate.findViewById(R.id.lineselect2);
            viewHolder.remen2 = (CheckedTextView) viewInflate.findViewById(R.id.remen2);
            viewHolder.zuixin2 = (CheckedTextView) viewInflate.findViewById(R.id.zuixin2);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = converView;
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.noDataImageView.setVisibility(0);
        final CommentBean.DataBean.HotBean item = getItem(position);
        if (this.showAd && position == 9) {
            viewHolder.llComment.setVisibility(8);
            viewHolder.llAdLayout.setVisibility(0);
            if (this.ykbAd) {
                viewHolder.llAd.loadYkbAd(item.getAds(), position, new QuestionAdWidegt.OnRemoveListener() { // from class: com.psychiatrygarden.adapter.p
                    @Override // com.psychiatrygarden.widget.QuestionAdWidegt.OnRemoveListener
                    public final void onCloseAd(int i2) {
                        this.f14856a.lambda$getView$0(viewHolder, i2);
                    }
                });
            } else {
                viewHolder.llAd.loadSdkAd(true);
            }
        } else {
            viewHolder.llComment.setVisibility(0);
            viewHolder.llAdLayout.setVisibility(8);
        }
        if (item.getType() == 1) {
            viewHolder.lineremen.setVisibility(0);
            viewHolder.group_name.setVisibility(0);
            viewHolder.linciew.setVisibility(8);
            viewHolder.line_null.setVisibility(8);
            viewHolder.group_name.setText(item.getName());
            if (item.getName().contains("热门评论")) {
                viewHolder.lineselect2.setVisibility(8);
            } else {
                viewHolder.lineselect2.setVisibility(8);
            }
            viewHolder.remen2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14895c.lambda$getView$1(view);
                }
            });
            viewHolder.zuixin2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14937c.lambda$getView$2(view);
                }
            });
        } else {
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
            viewHolder.ningrids.setmImagesBean(item.getC_imgs(), 1);
            viewHolder.ningrids.setDownImgUrl(item.getImg_watermark() + "");
            viewHolder.ningrids.setIsShowAll(false);
            viewHolder.group_name.setVisibility(8);
            viewHolder.lineremen.setVisibility(8);
            if (item.getId() == null || item.getId().equals("")) {
                viewHolder.linciew.setVisibility(8);
                viewHolder.line_null.setVisibility(0);
                viewHolder.tv_null.setText(item.getContent());
                return viewInflate;
            }
            viewHolder.linciew.setVisibility(0);
            viewHolder.line_null.setVisibility(8);
            if (Integer.parseInt(item.getPraise_num()) > Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.green));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                } else {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.green_theme_night));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                }
                if (this.fromVideo && this.landScape) {
                    viewHolder.tv_support.setTextColor(Color.parseColor("#81CB30"));
                    viewHolder.tv_oppose.setTextColor(Color.parseColor("#7B7E83"));
                }
            } else if (Integer.parseInt(item.getPraise_num()) < Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.context) != 0 || this.landScape) {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.red_theme_night));
                } else {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.app_theme_red));
                }
                if (this.fromVideo && this.landScape) {
                    viewHolder.tv_support.setTextColor(Color.parseColor("#7B7E83"));
                    viewHolder.tv_oppose.setTextColor(Color.parseColor("#F95843"));
                }
            } else {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.gray_light));
                } else {
                    viewHolder.tv_support.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    viewHolder.tv_oppose.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                }
                if (this.fromVideo && this.landScape) {
                    viewHolder.tv_support.setTextColor(Color.parseColor("#7B7E83"));
                    viewHolder.tv_oppose.setTextColor(Color.parseColor("#7B7E83"));
                }
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
            if (item.getIs_praise().equals("1")) {
                viewHolder.tv_support.setText("已赞同(" + item.getPraise_num() + ")");
            } else {
                viewHolder.tv_support.setText("赞同(" + item.getPraise_num() + ")");
            }
            if (item.getIs_oppose().equals("1")) {
                viewHolder.tv_oppose.setText("已反对(" + item.getOppose_num() + ")");
            } else {
                viewHolder.tv_oppose.setText("反对(" + item.getOppose_num() + ")");
            }
            if (item.getUser_identity().equals("1") || item.getIs_authentication().equals("1")) {
                viewHolder.jiav.setVisibility(8);
                viewHolder.isverauth.setVisibility(0);
                viewHolder.isverauth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.s
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14979c.lambda$getView$3(view);
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
                if (this.fromVideo && this.landScape) {
                    viewHolder.tv_reply.setTextColor(Color.parseColor("#7B7E83"));
                }
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                if (!this.fromVideo) {
                    viewHolder.tv_reply.setPadding(0, 10, 0, 10);
                }
                viewHolder.tv_reply.setLayoutParams(layoutParams2);
            } else {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.gray_font));
                    viewHolder.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                } else {
                    viewHolder.tv_reply.setTextColor(this.context.getResources().getColor(R.color.jiucuo_night));
                    viewHolder.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                }
                viewHolder.tv_reply.setText(item.getReply_num() + " 回复");
                if (this.fromVideo && this.landScape) {
                    viewHolder.tv_reply.setBackground(this.context.getDrawable(R.drawable.bg_video_comment_land_reply));
                    viewHolder.tv_reply.setTextColor(Color.parseColor("#F7F7F7"));
                    viewHolder.tv_reply.setPadding(SizeUtil.dp2px(this.context, 6), 2, SizeUtil.dp2px(this.context, 6), 2);
                }
            }
            viewHolder.tv_support.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15015c.lambda$getView$4(item, viewHolder, view);
                }
            });
            viewHolder.tv_oppose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15050c.lambda$getView$5(item, viewHolder, view);
                }
            });
            viewHolder.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15087c.lambda$getView$7(item, view);
                }
            });
            viewHolder.commentList_item_userIcon.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15121c.lambda$getView$8(item, view);
                }
            });
            viewHolder.commentList_item_tv_userName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14692c.lambda$getView$9(item, view);
                }
            });
            if ((TextUtils.isEmpty(item.getIs_essence()) ? "0" : item.getIs_essence()).equals("0")) {
                viewHolder.iv_elite.setVisibility(8);
            } else {
                viewHolder.iv_elite.setVisibility(8);
            }
            if (!this.fromVideo) {
                viewHolder.commentList_item_tv_userName.setTextColor(Color.parseColor(item.getUser_identity_color()));
            }
            viewHolder.commentList_item_tv_userName.setText(item.getNickname());
            viewHolder.commentList_item_tv_praise.setText(item.getPraise_num());
            viewHolder.myexptervew.setIs_del(item.getIs_del());
            viewHolder.myexptervew.setText(item.getContent(), item.is_showValue());
            viewHolder.myexptervew.setListener(new AnonymousClass3(item, viewInflate));
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14737c.lambda$getView$10(item, viewInflate, view);
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
            SubFloorFactory subFloorFactory = new SubFloorFactory();
            subFloorFactory.setFromVideo(this.landScape);
            viewHolder.subFloors.setFactory(subFloorFactory);
            viewHolder.subFloors.setIslouzhu("1");
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                viewHolder.subFloors.setBoundDrawer(this.context.getDrawable(R.drawable.bondcomm));
            } else {
                viewHolder.subFloors.setBoundDrawer(this.context.getDrawable(R.drawable.bound_night));
            }
            if (this.fromVideo && this.landScape) {
                viewHolder.subFloors.setBoundDrawer(this.context.getDrawable(R.drawable.bg_reply_floor_border));
            }
            viewHolder.subFloors.setmCommentListenter(new AnonymousClass5(item));
            viewHolder.subFloors.init();
        }
        if (this.fromVideo || this.landScape) {
            viewHolder.lineselect2.setVisibility(8);
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutBianAdminDataUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.2
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
                        NewToast.showShort(CircleInfoAdapter.this.context, new JSONObject(s2).optString("message"), 0).show();
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
                    CircleInfoAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putComment(Bundle b3, final Integer changeCode, final CommentBean.DataBean.HotBean mHotBean) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", b3.getString("course_id", ""));
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("video_type", b3.getString("video_type", "1"));
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(CircleInfoAdapter.this.context, "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(CircleInfoAdapter.this.context, jSONObject.optString("message"), 0).show();
                        return;
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", CircleInfoAdapter.this.context);
                    if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                        CircleInfoAdapter.this.mH = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        int i2 = -1;
                        for (int i3 = 0; i3 < CircleInfoAdapter.this.list.size(); i3++) {
                            if (mHotBean.getId().equals(((CommentBean.DataBean.HotBean) CircleInfoAdapter.this.list.get(i3)).getId())) {
                                i2 = i3;
                            }
                        }
                        CircleInfoAdapter.this.list.set(i2, CircleInfoAdapter.this.mH);
                        CircleInfoAdapter.this.notifyDataSetChanged();
                    }
                    if (CircleInfoAdapter.this.module_type == 8) {
                        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, CircleInfoAdapter.this.context, 0);
                        int intConfig2 = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, CircleInfoAdapter.this.context, 0);
                        int i4 = intConfig + 1;
                        SharePreferencesUtils.writeIntConfig(CommonParameter.COMMENT_COUNT, i4, CircleInfoAdapter.this.context);
                        EventBus.getDefault().post(new UpdateVideoCommentNote(intConfig2, i4));
                    }
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    EventBus.getDefault().post("refresh_praise");
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mDigest, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(CircleInfoAdapter.this.context, "加精成功", 0).show();
                    }
                } catch (Exception unused) {
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutOpposeUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.7
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
                super.onSuccess((AnonymousClass7) s2);
                try {
                    new JSONObject(s2).optString("code").equals("200");
                } catch (Exception unused) {
                }
            }
        });
    }

    public void putPraise(String id, final String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("obj_id", this.question_id);
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put("id", id);
        ajaxParams.put("type", type);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.context.getApplicationContext(), NetworkRequestsURL.mPutPraseUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.CircleInfoAdapter.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post("refresh_praise");
                        if (CircleInfoAdapter.this.mActionListener == null || !"1".equals(type)) {
                            return;
                        }
                        CircleInfoAdapter.this.mActionListener.clickLike();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void refresh(ArrayList<CommentBean.DataBean.HotBean> arr) {
        setList(arr);
        notifyDataSetChanged();
    }

    public void setAnchorView(View v2) {
        this.anchorView = v2;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setFromVideo(boolean fromVideo) {
        this.fromVideo = fromVideo;
    }

    public void setItemClickActionListener(ItemActionListener l2) {
        this.mActionListener = l2;
    }

    public void setLandScape(boolean landScape) {
        this.landScape = landScape;
    }

    public void setList(List<CommentBean.DataBean.HotBean> list) {
        if (list != null) {
            this.list = list;
        }
    }

    public void setRefeault(List<CommentBean.DataBean.HotBean> time_line) {
        this.time_line.addAll(time_line);
    }

    public void setShowAd(boolean showAd, boolean ykbAd) {
        this.showAd = showAd;
        this.ykbAd = ykbAd;
    }

    @Override // android.widget.Adapter
    public CommentBean.DataBean.HotBean getItem(int position) {
        return this.list.get(position);
    }

    public CircleInfoAdapter(Context context, List<CommentBean.DataBean.HotBean> list, BaseActivity mActivity, String is_is_prohibit, String appId) {
        this.is_is_prohibit = "0";
        this.mAppId = "";
        setList(list);
        this.context = context;
        this.mActivity = mActivity;
        this.is_is_prohibit = is_is_prohibit;
        this.mAppId = appId;
    }
}
