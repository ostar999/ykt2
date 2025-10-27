package com.psychiatrygarden.activity.comment.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentReplyActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussHeaderBean;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.comment.fragment.DiscussFragment;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.aliPlayer.widget.CustomAliPlayerView;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.bean.SubComments;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshPraiseEvent;
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
import com.psychiatrygarden.utils.CloneUtil;
import com.psychiatrygarden.utils.CommentListenter;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogAdminInput;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.FloorView;
import com.psychiatrygarden.widget.MyExpendView;
import com.psychiatrygarden.widget.PopInputWindow;
import com.psychiatrygarden.widget.PopupBianjiComWindow;
import com.psychiatrygarden.widget.PopupComWindow;
import com.psychiatrygarden.widget.PopupWin7Comment;
import com.psychiatrygarden.widget.SubFloorFactory;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class DiscussFragment extends BaseFragment {
    public ActivityResultLauncher activityResultLauncher;
    public BaseQuickAdapter<CommentBean.DataBean.HotBean, BaseViewHolder> adapter;
    private String break_point;
    public DiscussStatus commentEnum;
    private String commentId;
    public int count;
    private DiscussHeaderBean discussHeaderBean;
    public RecyclerView discussRecyview;
    public View footerview;
    public TextView group_name;
    public String id;
    private boolean isAnimate;
    public boolean isDestroyed;
    public boolean isLastposition;
    private String isProhibit;
    public int moduleType;
    public String obj_id;
    public SmartRefreshLayout refreshLayout;
    public String rule;
    public String target_user_id;
    public List<CommentBean.DataBean.HotBean> list = new ArrayList();
    public String comment_type = "2";
    public String isShowVideo = "1";
    public int durtaion = 300;
    public int page = 1;
    private boolean isReplyCollection = false;
    private boolean noAccess = false;

    /* renamed from: com.psychiatrygarden.activity.comment.fragment.DiscussFragment$12, reason: invalid class name */
    public class AnonymousClass12 implements onDialogShareClickListener {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;
        final /* synthetic */ View val$v;

        public AnonymousClass12(final CommentBean.DataBean.HotBean val$mCommentBean, final View val$v) {
            this.val$mCommentBean = val$mCommentBean;
            this.val$v = val$v;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mBianjiData$0(CommentBean.DataBean.HotBean hotBean, String str) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), str, hotBean.getContent(), hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mBianjiData$1(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mBianjiData$2(CommentBean.DataBean.HotBean hotBean, String str) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), str, hotBean.getOppose_num(), null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$mBianjiData$3(CommentBean.DataBean.HotBean hotBean, String str) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), hotBean.getCtime_timestamp(), hotBean.getContent(), hotBean.getPraise_num(), str, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$4(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", hotBean.getUser_id());
                bundle.putString("module_type", hotBean.getModule_type() + "");
                bundle.putString("content", str);
                bundle.putString("parent_id", hotBean.getId());
                bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                DiscussFragment.this.putComment(bundle, 2, hotBean, DiscussFragment.this.moduleType == 101 ? hotBean.getFile_id() : hotBean.getObj_id(), hotBean.getModule_type());
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", hotBean.getUser_id());
            bundle2.putString("module_type", hotBean.getModule_type() + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", hotBean.getId());
            bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
            if (DiscussFragment.this.moduleType == 101) {
                hotBean.setObj_id(hotBean.getFile_id());
            }
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 2);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(DiscussFragment.this.getActivity(), (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            DiscussFragment.this.activityResultLauncher.launch(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$5(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), hotBean.getCtime_timestamp(), str, hotBean.getPraise_num(), hotBean.getOppose_num(), null, 2, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$6(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
            DiscussFragment.this.mEdit_A_Single_Comment(hotBean.getId(), str, str2, str3, str4, null, 2, hotBean, hotBean.getC_imgs().getB_img(), hotBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onclickIntBack$7(final CommentBean.DataBean.HotBean hotBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(DiscussFragment.this.getActivity(), "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.12.1
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
        public /* synthetic */ void lambda$onclickIntBack$8(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            DiscussFragment discussFragment = DiscussFragment.this;
            if (discussFragment.moduleType == 101) {
                HttpRequstData.getIntance(discussFragment.getActivity()).postReportSourceData(hotBean.getId(), str);
                return;
            }
            HttpRequstData.getIntance(discussFragment.getActivity()).postReportData(hotBean.getId(), str, hotBean.getModule_type() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onclickIntBack$9(CommentBean.DataBean.HotBean hotBean, QuestionNoteBean questionNoteBean) {
            EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, hotBean.getObj_id()));
            ProjectApp.noteContent = "";
            ProjectApp.noteSmellImage = "";
            ProjectApp.noteBigImage = "";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mBianjiData(int i12) {
            boolean z2 = true;
            if (i12 == 0) {
                FragmentActivity activity = DiscussFragment.this.getActivity();
                String ctime_timestamp = this.val$mCommentBean.getCtime_timestamp();
                final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
                new PopInputWindow(activity, ctime_timestamp, 1, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.z
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f11776a.lambda$mBianjiData$0(hotBean, str);
                    }
                }).showPopUp(this.val$v);
                return;
            }
            if (i12 != 1) {
                if (i12 == 2) {
                    FragmentActivity activity2 = DiscussFragment.this.getActivity();
                    String praise_num = this.val$mCommentBean.getPraise_num();
                    final CommentBean.DataBean.HotBean hotBean2 = this.val$mCommentBean;
                    new PopInputWindow(activity2, praise_num, 2, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.b0
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public final void mInputDataListter(String str) {
                            this.f11700a.lambda$mBianjiData$2(hotBean2, str);
                        }
                    }).showPopUp(this.val$v);
                    return;
                }
                if (i12 != 3) {
                    return;
                }
                FragmentActivity activity3 = DiscussFragment.this.getActivity();
                String oppose_num = this.val$mCommentBean.getOppose_num();
                final CommentBean.DataBean.HotBean hotBean3 = this.val$mCommentBean;
                new PopInputWindow(activity3, oppose_num, 3, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.s
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public final void mInputDataListter(String str) {
                        this.f11763a.lambda$mBianjiData$3(hotBean3, str);
                    }
                }).showPopUp(this.val$v);
                return;
            }
            FragmentActivity activity4 = DiscussFragment.this.getActivity();
            final CommentBean.DataBean.HotBean hotBean4 = this.val$mCommentBean;
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.a0
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f11696a.lambda$mBianjiData$1(hotBean4, str, str2, str3);
                }
            };
            String content = this.val$mCommentBean.getContent();
            String str = "正在编辑" + this.val$mCommentBean.getNickname() + "的评论...";
            if (!this.val$mCommentBean.getModule_type().equals("1") && !this.val$mCommentBean.getModule_type().equals("4")) {
                z2 = false;
            }
            new DialogInput(activity4, ondialogclicklistener, content, str, z2, this.val$mCommentBean.getC_imgs().getB_img(), this.val$mCommentBean.getC_imgs().getS_img(), 1, this.val$mCommentBean.getC_imgs().getVideoId()).show();
        }

        @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
        public void onclickIntBack(int i2) {
            switch (i2) {
                case 0:
                    if (!DiscussFragment.this.isProhibit.equals("1")) {
                        FragmentActivity activity = DiscussFragment.this.getActivity();
                        final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
                        new DialogInput(activity, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.r
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f11761a.lambda$onclickIntBack$4(hotBean, str, str2, str3);
                            }
                        }, "", "回复" + this.val$mCommentBean.getNickname(), this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4")).show();
                        break;
                    } else {
                        ToastUtil.shortToast(((BaseFragment) DiscussFragment.this).mContext, "本帖已被锁定，不支持回帖");
                        break;
                    }
                    break;
                case 1:
                    if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        FragmentActivity activity2 = DiscussFragment.this.getActivity();
                        final CommentBean.DataBean.HotBean hotBean2 = this.val$mCommentBean;
                        new DialogInput(activity2, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.u
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str, String str2, String str3) {
                                this.f11766a.lambda$onclickIntBack$5(hotBean2, str, str2, str3);
                            }
                        }, this.val$mCommentBean.getContent(), "正在编辑" + this.val$mCommentBean.getNickname() + "的评论...", this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4"), this.val$mCommentBean.getC_imgs().getB_img(), this.val$mCommentBean.getC_imgs().getS_img(), 1, this.val$mCommentBean.getC_imgs().getVideoId()).show();
                        break;
                    } else {
                        new XPopup.Builder(DiscussFragment.this.getContext()).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(DiscussFragment.this.getActivity(), new OnClickbianjiListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.t
                            @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                            public final void mBianjiData(int i3) {
                                this.f11765a.mBianjiData(i3);
                            }
                        })).show();
                        break;
                    }
                    break;
                case 2:
                    DiscussFragment discussFragment = DiscussFragment.this;
                    String id = this.val$mCommentBean.getId();
                    String obj_id = this.val$mCommentBean.getObj_id();
                    String reply_primary_id = this.val$mCommentBean.getReply_primary_id();
                    CommentBean.DataBean.HotBean hotBean3 = this.val$mCommentBean;
                    discussFragment.deleteComment(id, obj_id, reply_primary_id, 2, hotBean3, hotBean3.getModule_type());
                    break;
                case 3:
                    ((ClipboardManager) DiscussFragment.this.getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", this.val$mCommentBean.getContent()));
                    NewToast.showShort(DiscussFragment.this.getActivity(), "复制成功", 0).show();
                    break;
                case 4:
                    DiscussFragment discussFragment2 = DiscussFragment.this;
                    discussFragment2.getUserInfo(discussFragment2.getActivity(), this.val$mCommentBean.getUser_id());
                    break;
                case 6:
                    FragmentActivity activity3 = DiscussFragment.this.getActivity();
                    final CommentBean.DataBean.HotBean hotBean4 = this.val$mCommentBean;
                    new DialogAdminInput(activity3, new BianjiInfaceInput() { // from class: com.psychiatrygarden.activity.comment.fragment.v
                        @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                        public final void mBianjiInfaceInput(String str, String str2, String str3, String str4) {
                            this.f11768a.lambda$onclickIntBack$6(hotBean4, str, str2, str3, str4);
                        }
                    }, this.val$mCommentBean.getCtime_timestamp(), this.val$mCommentBean.getContent(), this.val$mCommentBean.getPraise_num(), this.val$mCommentBean.getOppose_num()).show();
                    break;
                case 7:
                    FragmentActivity activity4 = DiscussFragment.this.getActivity();
                    final CommentBean.DataBean.HotBean hotBean5 = this.val$mCommentBean;
                    final View view = this.val$v;
                    new PopupComWindow(activity4, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.w
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                            this.f11770a.lambda$onclickIntBack$7(hotBean5, view, str, popupComWindow);
                        }
                    }).showPopUp(this.val$v);
                    break;
                case 8:
                    FragmentActivity activity5 = DiscussFragment.this.getActivity();
                    final CommentBean.DataBean.HotBean hotBean6 = this.val$mCommentBean;
                    new PopupComWindow(activity5, "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.x
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                            this.f11773a.lambda$onclickIntBack$8(hotBean6, str, popupComWindow);
                        }
                    }).showPopUp(this.val$v);
                    break;
                case 9:
                    ProjectApp.noteContent = this.val$mCommentBean.getContent();
                    if (TextUtils.isEmpty(this.val$mCommentBean.getVideo_id()) && !TextUtils.isEmpty(this.val$mCommentBean.getC_imgs().getS_img())) {
                        ProjectApp.noteSmellImage = this.val$mCommentBean.getC_imgs().getS_img();
                        ProjectApp.noteBigImage = this.val$mCommentBean.getC_imgs().getB_img();
                    }
                    Context context = DiscussFragment.this.getContext();
                    String str = DiscussFragment.this.moduleType + "";
                    String str2 = this.val$mCommentBean.getObj_id() + "";
                    final CommentBean.DataBean.HotBean hotBean7 = this.val$mCommentBean;
                    new DialogNoteInput(context, str, str2, "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.comment.fragment.y
                        @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                        public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                            DiscussFragment.AnonymousClass12.lambda$onclickIntBack$9(hotBean7, questionNoteBean);
                        }
                    }).show();
                    break;
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.comment.fragment.DiscussFragment$3, reason: invalid class name */
    public class AnonymousClass3 implements CommentListenter {
        final /* synthetic */ CommentBean.DataBean.HotBean val$mCommentBean;

        public AnonymousClass3(final CommentBean.DataBean.HotBean val$mCommentBean) {
            this.val$mCommentBean = val$mCommentBean;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commListReply$9(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", replyBean.getModule_type() + "");
                bundle.putString("content", str);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", replyBean.getReply_primary_id());
                bundle.putString("b_img", str2);
                bundle.putString("s_img", str3);
                DiscussFragment.this.putComment(bundle, 1, hotBean, DiscussFragment.this.moduleType == 101 ? hotBean.getFile_id() : hotBean.getObj_id(), hotBean.getModule_type());
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", replyBean.getUser_id());
            bundle2.putString("module_type", replyBean.getModule_type() + "");
            bundle2.putString("content", str);
            bundle2.putString("parent_id", replyBean.getId());
            bundle2.putString("reply_primary_id", replyBean.getReply_primary_id());
            if (DiscussFragment.this.moduleType == 101) {
                hotBean.setObj_id(hotBean.getFile_id());
            }
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putInt("type", 1);
            bundle2.putInt("result", 0);
            bundle2.putString("b_img", str2);
            bundle2.putString("s_img", str3);
            Intent intent = new Intent(DiscussFragment.this.getActivity(), (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            DiscussFragment.this.activityResultLauncher.launch(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$0(CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, CommentBean.DataBean.HotBean hotBean, String str2, String str3, String str4) {
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("to_user_id", replyBean.getUser_id());
                bundle.putString("module_type", replyBean.getModule_type() + "");
                bundle.putString("content", str2);
                bundle.putString("parent_id", replyBean.getId());
                bundle.putString("reply_primary_id", str);
                bundle.putString("b_img", str3);
                bundle.putString("s_img", str4);
                DiscussFragment.this.putComment(bundle, 1, hotBean, DiscussFragment.this.moduleType == 101 ? hotBean.getFile_id() : hotBean.getObj_id(), hotBean.getModule_type());
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("to_user_id", replyBean.getUser_id());
            bundle2.putString("module_type", replyBean.getModule_type() + "");
            bundle2.putString("content", str2);
            bundle2.putString("parent_id", replyBean.getId());
            bundle2.putString("reply_primary_id", str);
            if (DiscussFragment.this.moduleType == 101) {
                hotBean.setObj_id(hotBean.getFile_id());
            }
            bundle2.putSerializable("mHotbean", hotBean);
            bundle2.putString("b_img", str3);
            bundle2.putString("s_img", str4);
            bundle2.putInt("type", 1);
            bundle2.putInt("result", 0);
            Intent intent = new Intent(DiscussFragment.this.getActivity(), (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle2);
            DiscussFragment.this.activityResultLauncher.launch(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$1(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final CommentBean.DataBean.HotBean hotBean, View view, int i2) {
            boolean z2 = true;
            if (i2 == 0) {
                new PopInputWindow(DiscussFragment.this.getActivity(), replyBean.getCtime_timestamp(), 1, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.3.1
                    @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                    public void mInputDataListter(String txtSter) {
                        DiscussFragment discussFragment = DiscussFragment.this;
                        String id = replyBean.getId();
                        String content = replyBean.getContent();
                        String praise_num = replyBean.getPraise_num();
                        String oppose_num = replyBean.getOppose_num();
                        CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                        discussFragment.mEdit_A_Single_Comment(id, txtSter, content, praise_num, oppose_num, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                    }
                }).showPopUp(view);
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    new PopInputWindow(DiscussFragment.this.getActivity(), replyBean.getPraise_num(), 2, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.3.3
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            DiscussFragment discussFragment = DiscussFragment.this;
                            String id = replyBean.getId();
                            String ctime_timestamp = replyBean.getCtime_timestamp();
                            String content = replyBean.getContent();
                            String oppose_num = replyBean.getOppose_num();
                            CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                            discussFragment.mEdit_A_Single_Comment(id, ctime_timestamp, content, txtSter, oppose_num, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    new PopInputWindow(DiscussFragment.this.getActivity(), replyBean.getOppose_num(), 3, new OnMInputListenster() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.3.4
                        @Override // com.psychiatrygarden.interfaceclass.OnMInputListenster
                        public void mInputDataListter(String txtSter) {
                            DiscussFragment discussFragment = DiscussFragment.this;
                            String id = replyBean.getId();
                            String ctime_timestamp = replyBean.getCtime_timestamp();
                            String content = replyBean.getContent();
                            String praise_num = replyBean.getPraise_num();
                            CommentBean.DataBean.HotBean.ReplyBean replyBean2 = replyBean;
                            discussFragment.mEdit_A_Single_Comment(id, ctime_timestamp, content, praise_num, txtSter, replyBean2, 1, hotBean, replyBean2.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
                        }
                    }).showPopUp(view);
                    return;
                }
            }
            FragmentActivity activity = DiscussFragment.this.getActivity();
            onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.3.2
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public void onclickStringBack(String str, String b_img, String s_img) {
                    DiscussFragment.this.mEdit_A_Single_Comment(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, b_img, s_img);
                }
            };
            String content = replyBean.getContent();
            String str = "正在编辑" + replyBean.getNickname() + "的评论...";
            if (!replyBean.getModule_type().equals("1") && !replyBean.getModule_type().equals("4")) {
                z2 = false;
            }
            new DialogInput(activity, ondialogclicklistener, content, str, z2, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img(), 1, replyBean.getC_imgs().getVideoId()).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$2(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
            DiscussFragment.this.mEdit_A_Single_Comment(replyBean.getId(), replyBean.getCtime_timestamp(), str, replyBean.getPraise_num(), replyBean.getOppose_num(), replyBean, 1, hotBean, str2, str3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$3(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3, String str4) {
            DiscussFragment.this.mEdit_A_Single_Comment(replyBean.getId(), str, str2, str3, str4, replyBean, 1, hotBean, replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$4(PopupComWindow popupComWindow, CommentBean.DataBean.HotBean.ReplyBean replyBean, String str, String str2, PopupComWindow popupComWindow2) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            if (popupComWindow2 != null && popupComWindow2.isShowing()) {
                popupComWindow2.dismiss();
            }
            HttpRequstData.getIntance(DiscussFragment.this.getActivity()).postBannedData(replyBean.getUser_id(), str2, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$5(final CommentBean.DataBean.HotBean.ReplyBean replyBean, View view, final String str, final PopupComWindow popupComWindow) {
            new PopupComWindow(DiscussFragment.this.getActivity(), "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.c0
                @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                    this.f11704a.lambda$commentListenerData$4(popupComWindow, replyBean, str, str2, popupComWindow2);
                }
            }).showPopUp(view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$6(CommentBean.DataBean.HotBean hotBean, String str, PopupComWindow popupComWindow) {
            if (popupComWindow != null && popupComWindow.isShowing()) {
                popupComWindow.dismiss();
            }
            DiscussFragment discussFragment = DiscussFragment.this;
            if (discussFragment.moduleType == 101) {
                HttpRequstData.getIntance(discussFragment.getActivity()).postReportSourceData(hotBean.getId(), str);
                return;
            }
            HttpRequstData.getIntance(discussFragment.getActivity()).postReportData(hotBean.getId(), str, hotBean.getModule_type() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$commentListenerData$7(CommentBean.DataBean.HotBean.ReplyBean replyBean, QuestionNoteBean questionNoteBean) {
            EventBus.getDefault().post(new NoteEventBean(questionNoteBean.getContent(), questionNoteBean.getImg() != null && questionNoteBean.getImg().size() > 0, replyBean.getObj_id()));
            ProjectApp.noteContent = "";
            ProjectApp.noteSmellImage = "";
            ProjectApp.noteBigImage = "";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commentListenerData$8(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final String str, final CommentBean.DataBean.HotBean hotBean, final View view, final CommentBean.DataBean.HotBean hotBean2, int i2) {
            switch (i2) {
                case 0:
                    if (!DiscussFragment.this.isProhibit.equals("1")) {
                        new DialogInput(DiscussFragment.this.getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.d0
                            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                            public final void onclickStringBack(String str2, String str3, String str4) {
                                this.f11709a.lambda$commentListenerData$0(replyBean, str, hotBean, str2, str3, str4);
                            }
                        }, "", "回复" + replyBean.getNickname(), replyBean.getModule_type().equals("1") || replyBean.getModule_type().equals("4")).show();
                        break;
                    } else {
                        ToastUtil.shortToast(((BaseFragment) DiscussFragment.this).mContext, "本帖已被锁定，不支持回帖");
                        break;
                    }
                    break;
                case 1:
                    if (!"1".equals(replyBean.getStatus())) {
                        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                            new DialogInput(DiscussFragment.this.getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.f0
                                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                                public final void onclickStringBack(String str2, String str3, String str4) {
                                    this.f11719a.lambda$commentListenerData$2(replyBean, hotBean, str2, str3, str4);
                                }
                            }, replyBean.getContent(), "正在编辑" + replyBean.getNickname() + "的评论...", replyBean.getModule_type().equals("1") || replyBean.getModule_type().equals("4"), replyBean.getC_imgs().getB_img(), replyBean.getC_imgs().getS_img(), 1, replyBean.getC_imgs().getVideoId()).show();
                            break;
                        } else {
                            new XPopup.Builder(DiscussFragment.this.getContext()).autoDismiss(Boolean.FALSE).popupAnimation(null).asCustom(new PopupBianjiComWindow(DiscussFragment.this.getActivity(), new OnClickbianjiListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.e0
                                @Override // com.psychiatrygarden.interfaceclass.OnClickbianjiListenter
                                public final void mBianjiData(int i3) {
                                    this.f11714a.lambda$commentListenerData$1(replyBean, hotBean, view, i3);
                                }
                            })).show();
                            break;
                        }
                    } else {
                        CommonUtil.mToastShow(DiscussFragment.this.getActivity());
                        break;
                    }
                    break;
                case 2:
                    DiscussFragment.this.deleteComment(replyBean.getId(), replyBean.getObj_id(), str, 1, hotBean, replyBean.getModule_type());
                    break;
                case 3:
                    ((ClipboardManager) DiscussFragment.this.getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", replyBean.getContent()));
                    NewToast.showShort(DiscussFragment.this.getActivity(), "复制成功", 0).show();
                    break;
                case 4:
                    DiscussFragment discussFragment = DiscussFragment.this;
                    discussFragment.getUserInfo(discussFragment.getActivity(), replyBean.getUser_id());
                    break;
                case 6:
                    new DialogAdminInput(DiscussFragment.this.getActivity(), new BianjiInfaceInput() { // from class: com.psychiatrygarden.activity.comment.fragment.g0
                        @Override // com.psychiatrygarden.interfaceclass.BianjiInfaceInput
                        public final void mBianjiInfaceInput(String str2, String str3, String str4, String str5) {
                            this.f11723a.lambda$commentListenerData$3(replyBean, hotBean, str2, str3, str4, str5);
                        }
                    }, replyBean.getCtime_timestamp(), replyBean.getContent(), replyBean.getPraise_num(), replyBean.getOppose_num()).show();
                    break;
                case 7:
                    new PopupComWindow(DiscussFragment.this.getActivity(), "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.h0
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str2, PopupComWindow popupComWindow) {
                            this.f11728a.lambda$commentListenerData$5(replyBean, view, str2, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 8:
                    new PopupComWindow(DiscussFragment.this.getActivity(), "欢迎举报,请选择举报理由", "（被举报的评论将及时清理）", 2, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.comment.fragment.i0
                        @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
                        public final void onDiogClick(String str2, PopupComWindow popupComWindow) {
                            this.f11733a.lambda$commentListenerData$6(hotBean2, str2, popupComWindow);
                        }
                    }).showPopUp(view);
                    break;
                case 9:
                    ProjectApp.noteContent = replyBean.getContent();
                    if (TextUtils.isEmpty(replyBean.getVideo_id()) && !TextUtils.isEmpty(replyBean.getC_imgs().getS_img())) {
                        ProjectApp.noteSmellImage = replyBean.getC_imgs().getS_img();
                        ProjectApp.noteBigImage = replyBean.getC_imgs().getB_img();
                    }
                    new DialogNoteInput(DiscussFragment.this.getContext(), DiscussFragment.this.moduleType + "", replyBean.getObj_id() + "", "添加至笔记", new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.comment.fragment.j0
                        @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                        public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                            DiscussFragment.AnonymousClass3.lambda$commentListenerData$7(replyBean, questionNoteBean);
                        }
                    }).show();
                    break;
            }
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListOppose(String moudle_type, String is_Oppose, String id, String obj_id) {
            DiscussFragment.this.putOppose(id, is_Oppose.equals("0") ? "1" : "0", obj_id, moudle_type);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraise(String moudle_type, String is_praise, View v2, String id, String obj_id) {
            DiscussFragment.this.putPraise(id, is_praise.equals("0") ? "1" : "0", obj_id, moudle_type);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListPraiseFaile() {
            NewToast.showShort(DiscussFragment.this.getActivity(), "您已经点赞了", 0).show();
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListReply(final CommentBean.DataBean.HotBean.ReplyBean cmt, View v2) {
            if (Integer.parseInt(cmt.getReply_num()) < 1) {
                if (cmt.getIs_del().equals("1")) {
                    NewToast.showShort(DiscussFragment.this.getActivity(), "评论已删除", 0).show();
                    return;
                }
                FragmentActivity activity = DiscussFragment.this.getActivity();
                final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
                new DialogInput(activity, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.l0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f11747a.lambda$commListReply$9(cmt, hotBean, str, str2, str3);
                    }
                }, "", "回复" + cmt.getNickname(), this.val$mCommentBean.getModule_type().equals("1") || this.val$mCommentBean.getModule_type().equals("4")).show();
                return;
            }
            if (DiscussFragment.this.moduleType == 101) {
                Intent intent = new Intent(DiscussFragment.this.getActivity(), (Class<?>) CommentReplyActivity.class);
                intent.putExtra("is_replybean", false);
                intent.putExtra("bean", cmt);
                intent.putExtra("isVisiable", true);
                intent.putExtra("isShowVideo", "1");
                intent.putExtra("isSource", true);
                DiscussFragment.this.getActivity().startActivity(intent);
                return;
            }
            Intent intent2 = new Intent(DiscussFragment.this.getActivity(), (Class<?>) DiscussPublicActivity.class);
            intent2.putExtra("module_type", Integer.parseInt(cmt.getModule_type()));
            intent2.putExtra("comment_type", DiscussFragment.this.comment_type);
            intent2.putExtra("isReplyCollection", true);
            intent2.putExtra("title", "回复合集");
            Bundle bundle = new Bundle();
            bundle.putBinder("IPCBinder", new DiscussHeaderBean(cmt));
            intent2.putExtra("bundle", bundle);
            if (v2 == null) {
                intent2.putExtra("isShowVideo", "0");
            } else {
                intent2.putExtra("isShowVideo", "1");
            }
            intent2.putExtra("commentEnum", DiscussStatus.CollectionOfReplies);
            intent2.putExtra("isProhibit", DiscussFragment.this.isProhibit);
            DiscussFragment.this.startActivity(intent2);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commListSupport(String moudle_type, String is_Support, String id, String obj_id) {
            DiscussFragment.this.putPraise(id, is_Support.equals("0") ? "1" : "0", obj_id, moudle_type);
        }

        @Override // com.psychiatrygarden.utils.CommentListenter
        public void commentListenerData(final CommentBean.DataBean.HotBean.ReplyBean replyBean, final View view) {
            final CommentBean.DataBean.HotBean hotBean = this.val$mCommentBean;
            final String reply_primary_id = hotBean.getReply_primary_id();
            boolean zEquals = UserConfig.getUserId().equals(replyBean.getUser_id());
            if ("1".equals(replyBean.getStatus())) {
                CommonUtil.mToastShow(DiscussFragment.this.getActivity());
                return;
            }
            int i2 = TextUtils.isEmpty(replyBean.getReply_num()) ? 0 : Integer.parseInt(replyBean.getReply_num());
            FragmentActivity activity = DiscussFragment.this.getActivity();
            String content = replyBean.getContent();
            String nickname = replyBean.getNickname();
            String str = replyBean.getIs_anonymous() + "";
            String str2 = DiscussFragment.this.moduleType + "";
            String delete_skill = replyBean.getDelete_skill();
            String id = replyBean.getId();
            final CommentBean.DataBean.HotBean hotBean2 = this.val$mCommentBean;
            new XPopup.Builder(DiscussFragment.this.getActivity()).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(activity, content, nickname, zEquals ? 1 : 0, str, str2, delete_skill, id, i2, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.k0
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i3) {
                    this.f11739a.lambda$commentListenerData$8(replyBean, reply_primary_id, hotBean, view, hotBean2, i3);
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDiscussListData(final boolean isAddComment, final int newsPos) {
        String url;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.moduleType == 101) {
            url = NetworkRequestsURL.soutceCommentList;
            ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.obj_id);
            ajaxParams.put("comment_id", this.commentId);
            ajaxParams.put("search_type", "3");
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        } else {
            ajaxParams.put("comment_type", "" + this.comment_type);
            ajaxParams.put("module_type", "" + this.moduleType);
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
            if (this.commentEnum.getCode() == 2) {
                ajaxParams.put(com.heytap.mcssdk.constant.b.f7191p, "" + this.rule);
                ajaxParams.put("obj_id", "" + this.obj_id);
            } else if (this.commentEnum.getCode() == 0) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mycomment");
                ajaxParams.put("obj_id", "" + this.obj_id);
            } else if (this.commentEnum.getCode() == 1) {
                ajaxParams.put(com.alipay.sdk.authjs.a.f3174g, "mypraise");
                ajaxParams.put("obj_id", "" + this.obj_id);
            } else if (this.commentEnum.getCode() == 3) {
                ajaxParams.put("only_author", ((DiscussPublicActivity) getActivity()).only_author);
                ajaxParams.put("obj_id", "" + this.obj_id);
            } else if (this.commentEnum.getCode() == 5 || this.commentEnum.getCode() == 6 || this.commentEnum.getCode() == 7) {
                if (!UserConfig.getUserId().equals(this.target_user_id)) {
                    ajaxParams.put("target_user_id", "" + this.target_user_id);
                }
            } else if (this.commentEnum.getCode() == 9) {
                ajaxParams.put("first_line_comment_id", "" + this.obj_id);
            } else if (this.commentEnum.getCode() == 10) {
                ajaxParams.put("id", this.id);
                ajaxParams.put("obj_id", this.obj_id);
            } else if (this.commentEnum.getCode() == 15) {
                ajaxParams.put("obj_id", "" + this.obj_id);
                ajaxParams.put("type", getArguments().getString("recommendType"));
            } else {
                ajaxParams.put("obj_id", "" + this.obj_id);
            }
            ajaxParams.put("break_point", this.break_point);
            url = this.commentEnum.getUrl();
        }
        YJYHttpUtils.post(getActivity(), url, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                DiscussFragment discussFragment = DiscussFragment.this;
                int i2 = discussFragment.page;
                if (i2 > 1) {
                    discussFragment.page = i2 - 1;
                }
                discussFragment.refreshLayout.finishRefresh(false);
                DiscussFragment.this.refreshLayout.finishLoadMore(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                List<CommentBean.DataBean.HotBean> list;
                super.onSuccess((AnonymousClass11) s2);
                try {
                    CommentBean commentBean = (CommentBean) new Gson().fromJson(s2, CommentBean.class);
                    DiscussFragment.this.refreshLayout.finishRefresh();
                    DiscussFragment.this.refreshLayout.finishLoadMore();
                    if (!commentBean.getCode().equals("200")) {
                        onFailure(null, 0, "");
                        return;
                    }
                    DiscussFragment discussFragment = DiscussFragment.this;
                    if (discussFragment.page == 1) {
                        discussFragment.list.clear();
                        if (commentBean.getData().getHot().size() > 0) {
                            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                            hotBean.setType(1);
                            hotBean.setName("最热评论");
                            hotBean.setTypeName("最热评论");
                            DiscussFragment.this.list.add(hotBean);
                            for (int i2 = 0; i2 < commentBean.getData().getHot().size(); i2++) {
                                commentBean.getData().getHot().get(i2).setTypeName("最热评论");
                            }
                            DiscussFragment.this.list.addAll(commentBean.getData().getHot());
                            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                            hotBean2.setType(1);
                            hotBean2.setName("最新评论");
                            DiscussFragment.this.list.add(hotBean2);
                            DiscussFragment.this.isAnimate = true;
                        } else {
                            DiscussFragment.this.isAnimate = false;
                        }
                        DiscussFragment.this.list.addAll(commentBean.getData().getTime_line());
                        DiscussFragment discussFragment2 = DiscussFragment.this;
                        discussFragment2.adapter.setList(discussFragment2.list);
                        DiscussFragment discussFragment3 = DiscussFragment.this;
                        if (!discussFragment3.isLastposition) {
                            discussFragment3.adapter.removeFooterView(discussFragment3.footerview);
                            DiscussFragment.this.refreshLayout.setEnableLoadMore(true);
                        }
                        if (DiscussFragment.this.commentEnum.getCode() == 2 && ((DiscussFragment.this.rule.equals("recommend") || DiscussFragment.this.count < 1) && (list = DiscussFragment.this.list) != null && list.size() > 1)) {
                            DiscussFragment.this.getCommentAd();
                        }
                    } else if (commentBean.getData().getTime_line() == null || commentBean.getData().getTime_line().size() <= 0) {
                        DiscussFragment.this.refreshLayout.finishLoadMoreWithNoMoreData();
                        DiscussFragment discussFragment4 = DiscussFragment.this;
                        if (!discussFragment4.isLastposition) {
                            discussFragment4.adapter.setFooterView(discussFragment4.footerview, 0);
                            DiscussFragment.this.refreshLayout.setEnableLoadMore(false);
                        }
                    } else {
                        DiscussFragment.this.list.addAll(commentBean.getData().getTime_line());
                        DiscussFragment.this.adapter.addData(commentBean.getData().getTime_line());
                        DiscussFragment.this.refreshLayout.finishLoadMore(true);
                    }
                    if (isAddComment) {
                        DiscussFragment.this.discussRecyview.smoothScrollToPosition(newsPos);
                    }
                } catch (Exception e2) {
                    onFailure(e2.fillInStackTrace(), 0, "数据解析错误");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNewPositions() {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            CommentBean.DataBean.HotBean hotBean = this.list.get(i2);
            int type = hotBean.getType();
            boolean zIsHot = hotBean.isHot();
            if (type == 1 && !zIsHot && hotBean.getName() != null && hotBean.getName().contains("最新")) {
                return i2 + 1;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserInfo(Context mContext, String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", user_id);
        YJYHttpUtils.get(mContext, NetworkRequestsURL.mGetUserInfoUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        CommonUtil.mPutChatData(DiscussFragment.this.getActivity(), jSONObject.optJSONObject("data").optString("user_uuid"), jSONObject.optJSONObject("data").optString("nickname"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$10(CommentBean.DataBean.HotBean hotBean, TextView textView, View view) {
        if (ProjectApp.isSerachClick || hotBean.getIs_oppose().equals("1")) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(getActivity(), "评论已删除", 0).show();
            return;
        }
        String str = "0";
        if (hotBean.getIs_praise().equals("1")) {
            putPraise(hotBean.getId(), "0", hotBean.getObj_id(), hotBean.getModule_type());
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
                textView.setText(String.format("赞同(%s)", hotBean.getPraise_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            CommonUtil.Toast_pop(textView, 0);
            putPraise(hotBean.getId(), "1", hotBean.getObj_id(), hotBean.getModule_type());
            hotBean.setIs_praise("1");
            try {
                String praise_num2 = hotBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                hotBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已赞同(%s)", hotBean.getPraise_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$11(CommentBean.DataBean.HotBean hotBean, TextView textView, View view) {
        if (ProjectApp.isSerachClick || "1".equals(hotBean.getIs_praise())) {
            return;
        }
        if (hotBean.getIs_del().equals("1")) {
            NewToast.showShort(getActivity(), "评论已删除", 0).show();
            return;
        }
        if (hotBean.getIs_praise().equals("1")) {
            return;
        }
        String str = "0";
        if (hotBean.getIs_oppose().equals("1")) {
            putOppose(hotBean.getId(), "0", hotBean.getObj_id(), hotBean.getModule_type());
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
                textView.setText(String.format("反对(%s)", hotBean.getOppose_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            CommonUtil.Toast_pop(textView, 1);
            putOppose(hotBean.getId(), "1", hotBean.getObj_id(), hotBean.getModule_type());
            hotBean.setIs_oppose("1");
            try {
                String oppose_num2 = hotBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num2)) {
                    str = oppose_num2;
                }
                hotBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已反对(%s)", hotBean.getOppose_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$12(CommentBean.DataBean.HotBean hotBean, String str, String str2, String str3) {
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("to_user_id", hotBean.getUser_id());
            bundle.putString("module_type", hotBean.getModule_type() + "");
            bundle.putString("content", str);
            bundle.putString("parent_id", hotBean.getId());
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            bundle.putString("reply_primary_id", hotBean.getReply_primary_id());
            putComment(bundle, 2, hotBean, this.moduleType == 101 ? hotBean.getFile_id() : hotBean.getObj_id(), hotBean.getModule_type());
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("to_user_id", hotBean.getUser_id());
        bundle2.putString("module_type", hotBean.getModule_type() + "");
        bundle2.putString("content", str);
        bundle2.putString("parent_id", hotBean.getId());
        bundle2.putString("reply_primary_id", hotBean.getReply_primary_id());
        if (this.moduleType == 101) {
            hotBean.setObj_id(hotBean.getFile_id());
        }
        bundle2.putSerializable("mHotbean", hotBean);
        bundle2.putInt("type", 2);
        bundle2.putInt("result", 0);
        bundle2.putString("b_img", str2);
        bundle2.putString("s_img", str3);
        Intent intent = new Intent(getActivity(), (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle2);
        this.activityResultLauncher.launch(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$13(final CommentBean.DataBean.HotBean hotBean, View view) {
        if (hotBean.getStatus().equals("1")) {
            CommonUtil.mToastShow(getActivity());
            return;
        }
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(this.mContext, "本帖已被锁定，不支持回帖");
            return;
        }
        if (Integer.parseInt(hotBean.getReply_num()) <= 1 || hotBean.getIs_header() != 0) {
            if (ProjectApp.isSerachClick) {
                return;
            }
            new DialogInput(getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.h
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f11726a.lambda$convertViewHoder$12(hotBean, str, str2, str3);
                }
            }, "", "回复" + hotBean.getNickname(), hotBean.getModule_type().equals("1") || hotBean.getModule_type().equals("4")).show();
            return;
        }
        if (this.moduleType == 101) {
            Intent intent = new Intent(getActivity(), (Class<?>) CommentReplyActivity.class);
            intent.putExtra("is_replybean", false);
            intent.putExtra("bean", hotBean);
            intent.putExtra("isVisiable", true);
            intent.putExtra("isShowVideo", "1");
            intent.putExtra("isSource", true);
            getActivity().startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(getActivity(), (Class<?>) DiscussPublicActivity.class);
        intent2.putExtra("comment_type", this.comment_type);
        intent2.putExtra("module_type", Integer.parseInt(hotBean.getModule_type()));
        intent2.putExtra("isReplyCollection", true);
        intent2.putExtra("title", "回复合集");
        Bundle bundle = new Bundle();
        bundle.putBinder("IPCBinder", new DiscussHeaderBean(hotBean));
        intent2.putExtra("bundle", bundle);
        intent2.putExtra("isShowVideo", "1");
        intent2.putExtra("commentEnum", DiscussStatus.CollectionOfReplies);
        intent2.putExtra("isProhibit", this.isProhibit);
        getActivity().startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$14(CommentBean.DataBean.HotBean hotBean, View view) {
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
        Intent intent = new Intent(getActivity(), (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.putExtra("jiav", hotBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$15(CommentBean.DataBean.HotBean hotBean, View view) {
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
        Intent intent = new Intent(getActivity(), (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", hotBean.getUser_id());
        intent.putExtra("jiav", hotBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$4(BaseViewHolder baseViewHolder, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, Long.valueOf(System.currentTimeMillis()), this.mContext);
        this.list.remove(baseViewHolder.getBindingAdapterPosition());
        this.adapter.setList(this.list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convertViewHoder$5(JSONObject jSONObject, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        PublicMethodActivity.getInstance().mToActivity(jSONObject.optString(PushConstants.EXTRA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$6(CommentBean.DataBean.HotBean hotBean, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.noAccess) {
            startActivity(new Intent(getActivity(), (Class<?>) MemberCenterActivity.class));
            return;
        }
        if (this.moduleType == 101) {
            hotBean.setModule_type("101");
        }
        PublicMethodActivity.getInstance().mCommentMethod(hotBean.getModule_type() + "", hotBean.getTarget_params());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convertViewHoder$7(CommentBean.DataBean.HotBean hotBean, NineGridTestLayout nineGridTestLayout) {
        float width;
        float width2;
        float s_width = hotBean.getC_imgs().getS_width();
        float s_height = hotBean.getC_imgs().getS_height();
        if (s_height >= s_width * 2.0f) {
            width2 = (nineGridTestLayout.getWidth() / 3) * 2.0f;
        } else {
            if (s_height >= s_width) {
                width = (nineGridTestLayout.getWidth() / 2) - 50;
            } else if (s_width >= s_height * 2.0f) {
                width2 = ((nineGridTestLayout.getWidth() * 2) / 3) / 2.0f;
            } else {
                double d3 = s_width / s_height;
                s_width = 5.0f;
                if (d3 <= 1.1d) {
                    s_height = (nineGridTestLayout.getWidth() / 5) * 3;
                    width = 4.0f;
                } else {
                    s_height = (nineGridTestLayout.getWidth() / 5) * 3;
                    width = 3.0f;
                }
            }
            width2 = (s_height * width) / s_width;
        }
        ViewGroup.LayoutParams layoutParams = nineGridTestLayout.getLayoutParams();
        layoutParams.height = (int) width2;
        nineGridTestLayout.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$8(CommentBean.DataBean.HotBean hotBean) {
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(getActivity());
            return;
        }
        if (hotBean.getIs_header() == 1) {
            addAliplayer(hotBean, 1);
            return;
        }
        if (this.moduleType == 101) {
            Intent intent = new Intent(getActivity(), (Class<?>) CommentReplyActivity.class);
            intent.putExtra("is_replybean", false);
            intent.putExtra("bean", hotBean);
            intent.putExtra("isVisiable", true);
            intent.putExtra("isShowVideo", "1");
            intent.putExtra("isSource", true);
            getActivity().startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(getActivity(), (Class<?>) DiscussPublicActivity.class);
        intent2.putExtra("comment_type", this.comment_type);
        intent2.putExtra("module_type", Integer.parseInt(hotBean.getModule_type()));
        intent2.putExtra("isShowVideo", "0");
        intent2.putExtra("isReplyCollection", true);
        intent2.putExtra("title", "回复合集");
        Bundle bundle = new Bundle();
        bundle.putBinder("IPCBinder", new DiscussHeaderBean(hotBean));
        intent2.putExtra("bundle", bundle);
        intent2.putExtra("commentEnum", DiscussStatus.CollectionOfReplies);
        intent2.putExtra("isProhibit", this.isProhibit);
        startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertViewHoder$9(View view) {
        ToastUtil.shortToast(this.mContext, "已认证");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page = 1;
        this.break_point = (System.currentTimeMillis() / 1000) + "";
        getDiscussListData(false, 0);
        TextView textView = this.group_name;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        this.page++;
        getDiscussListData(false, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) throws Resources.NotFoundException {
        if (this.isLastposition) {
            return;
        }
        ((DiscussPublicActivity) getActivity()).viewpager.setCurrentItem(this.count);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(ActivityResult activityResult) {
        if (activityResult == null || activityResult.getData() == null) {
            return;
        }
        int resultCode = activityResult.getResultCode();
        Bundle bundleExtra = activityResult.getData().getBundleExtra("bundleIntent");
        if (resultCode == 0) {
            putComment(bundleExtra, Integer.valueOf(bundleExtra.getInt("type")), (CommentBean.DataBean.HotBean) bundleExtra.getSerializable("mHotbean"), ((CommentBean.DataBean.HotBean) bundleExtra.getSerializable("mHotbean")).getObj_id(), ((CommentBean.DataBean.HotBean) bundleExtra.getSerializable("mHotbean")).getModule_type());
        }
    }

    public void addAliplayer(CommentBean.DataBean.HotBean mCommentBean, int type) {
        if (mCommentBean == null) {
            return;
        }
        if (!mCommentBean.getUser_id().equals(UserConfig.getUserId()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            startActivity(new Intent(getActivity(), (Class<?>) MemberCenterActivity.class));
            getActivity().finish();
        }
        ((BaseActivity) getActivity()).mActionBar.hide();
        CustomAliPlayerView customAliPlayerView = new CustomAliPlayerView(getActivity());
        customAliPlayerView.initView(getActivity(), mCommentBean.getVideo_id(), UserConfig.isCanPlayBy4g(this.mContext));
        CommonUtil.mPlayerData(mCommentBean.getVideo_id(), customAliPlayerView, true);
        BaseQuickAdapter<CommentBean.DataBean.HotBean, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter == null) {
            return;
        }
        baseQuickAdapter.setHeaderView(customAliPlayerView, 0);
        changeThemeStutas();
    }

    public void catAliPlayer(int type) {
        BaseQuickAdapter<CommentBean.DataBean.HotBean, BaseViewHolder> baseQuickAdapter;
        LinearLayout headerLayout;
        if (this.commentEnum.getCode() != 10 || (baseQuickAdapter = this.adapter) == null || baseQuickAdapter.getHeaderLayout() == null || (headerLayout = this.adapter.getHeaderLayout()) == null) {
            return;
        }
        for (int i2 = 0; i2 < headerLayout.getChildCount(); i2++) {
            View childAt = headerLayout.getChildAt(i2);
            if (childAt instanceof CustomAliPlayerView) {
                CustomAliPlayerView customAliPlayerView = (CustomAliPlayerView) childAt;
                if (type == 1) {
                    customAliPlayerView.onResume();
                    return;
                }
                if (type == 2) {
                    customAliPlayerView.onPause();
                    return;
                } else {
                    if (type != 3) {
                        return;
                    }
                    customAliPlayerView.onDestory();
                    this.isDestroyed = true;
                    return;
                }
            }
        }
    }

    public void changeDesTroy() {
        if (this.isDestroyed) {
            return;
        }
        catAliPlayer(3);
    }

    @SuppressLint({"RestrictedApi"})
    public void changeThemeStutas() {
        this.refreshLayout.getRefreshHeader().setPrimaryColors(this.mContext.getResources().getColor(R.color.black));
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.black), 0);
    }

    public void convertViewHoder(final BaseViewHolder baseViewHolder, final CommentBean.DataBean.HotBean mCommentBean) {
        final NineGridTestLayout nineGridTestLayout;
        final TextView textView;
        final TextView textView2;
        TextView textView3;
        FloorView floorView;
        NineGridTestLayout nineGridTestLayout2 = (NineGridTestLayout) baseViewHolder.getView(R.id.ningrids);
        TextView textView4 = (TextView) baseViewHolder.getView(R.id.group_name);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.linciew);
        TextView textView5 = (TextView) baseViewHolder.getView(R.id.commentList_item_tv_userName);
        TextView textView6 = (TextView) baseViewHolder.getView(R.id.commentList_item_tv_school);
        FloorView floorView2 = (FloorView) baseViewHolder.getView(R.id.foor);
        CircleImageView circleImageView = (CircleImageView) baseViewHolder.getView(R.id.commentList_item_userIcon);
        TextView textView7 = (TextView) baseViewHolder.getView(R.id.commentList_item_tv_praise);
        MyExpendView myExpendView = (MyExpendView) baseViewHolder.getView(R.id.myexptervew);
        TextView textView8 = (TextView) baseViewHolder.getView(R.id.tv_support);
        TextView textView9 = (TextView) baseViewHolder.getView(R.id.tv_oppose);
        TextView textView10 = (TextView) baseViewHolder.getView(R.id.tv_reply);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.jiav);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.vipimgid);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.lineremen);
        TextView textView11 = (TextView) baseViewHolder.getView(R.id.tv_question);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.isverauth);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_main_content);
        LinearLayout linearLayout3 = (LinearLayout) baseViewHolder.getView(R.id.lineadview);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_delete_ad);
        final ImageView imageView5 = (ImageView) baseViewHolder.getView(R.id.adimgs);
        TextView textView12 = (TextView) baseViewHolder.getView(R.id.is_one_top_Tv);
        if (!TextUtils.isEmpty(mCommentBean.getAds())) {
            try {
                final JSONObject jSONObject = new JSONObject(mCommentBean.getAds());
                if (jSONObject.optString("force").equals("1")) {
                    imageView4.setVisibility(8);
                } else {
                    imageView4.setVisibility(0);
                    imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.a
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11694c.lambda$convertViewHoder$4(baseViewHolder, view);
                        }
                    });
                }
                linearLayout3.setVisibility(0);
                linearLayout2.setVisibility(8);
                Glide.with(this.mContext).asBitmap().load((Object) GlideUtils.generateUrl(jSONObject.optString("img"))).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.2
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull @NotNull Object resource, @Nullable Transition transition) {
                        onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        double screenWidth = ScreenUtil.getScreenWidth(DiscussFragment.this.getActivity()) - CommonUtil.dip2px(DiscussFragment.this.getActivity(), 73.0f);
                        if (AndroidBaseUtils.isPad(((BaseFragment) DiscussFragment.this).mContext) && AndroidBaseUtils.isCurOriLand(DiscussFragment.this.getContext())) {
                            screenWidth /= 3.0d;
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView5.getLayoutParams();
                        layoutParams.width = (int) screenWidth;
                        layoutParams.height = (int) (resource.getHeight() * (screenWidth / resource.getWidth()));
                        layoutParams.rightMargin = CommonUtil.dip2px(DiscussFragment.this.getActivity(), 20.0f);
                        layoutParams.leftMargin = CommonUtil.dip2px(DiscussFragment.this.getActivity(), 33.0f);
                        imageView5.setLayoutParams(layoutParams);
                        imageView5.setImageBitmap(resource);
                    }
                });
                imageView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.k
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DiscussFragment.lambda$convertViewHoder$5(jSONObject, view);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            baseViewHolder.itemView.setOnClickListener(null);
            return;
        }
        linearLayout3.setVisibility(8);
        linearLayout2.setVisibility(0);
        if (mCommentBean.getType() == 1) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            textView4.setText(mCommentBean.getName());
            relativeLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(8);
            textView4.setVisibility(8);
            relativeLayout.setVisibility(0);
        }
        int code = this.commentEnum.getCode();
        if (code == 5 || code == 6 || code == 7 || code == 9) {
            textView11.setVisibility(0);
        } else {
            textView11.setVisibility(8);
        }
        textView11.setText(mCommentBean.getTo_from());
        textView11.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11745c.lambda$convertViewHoder$6(mCommentBean, view);
            }
        });
        if (TextUtils.isEmpty(mCommentBean.getAvatar() + "")) {
            circleImageView.setImageResource(R.drawable.empty_photo);
        } else {
            GlideApp.with(getActivity()).load((Object) GlideUtils.generateUrl(mCommentBean.getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE).placeholder(R.drawable.empty_photo).error(R.drawable.empty_photo)).into(circleImageView);
        }
        textView6.setText(String.format("%s %s", mCommentBean.getSchool(), mCommentBean.getCtime()));
        textView5.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? Color.parseColor("#244D8D") : Color.parseColor(mCommentBean.getUser_identity_color()));
        textView5.setText(mCommentBean.getNickname());
        textView7.setText(mCommentBean.getPraise_num());
        if (this.commentEnum.getCode() == 2 && "recommend".equals(this.rule)) {
            if ("1".equals(mCommentBean.getOn_the_top())) {
                textView12.setVisibility(0);
            } else {
                textView12.setVisibility(8);
            }
        }
        if (mCommentBean.getC_imgs() == null || TextUtils.isEmpty(mCommentBean.getC_imgs().getS_img())) {
            nineGridTestLayout = nineGridTestLayout2;
            nineGridTestLayout.setVisibility(8);
        } else {
            nineGridTestLayout = nineGridTestLayout2;
            nineGridTestLayout.setVisibility(0);
            nineGridTestLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.comment.fragment.m
                @Override // java.lang.Runnable
                public final void run() {
                    DiscussFragment.lambda$convertViewHoder$7(mCommentBean, nineGridTestLayout);
                }
            });
        }
        if (TextUtils.isEmpty(mCommentBean.getVideo_id())) {
            mCommentBean.getC_imgs().setVideoId("");
        } else {
            mCommentBean.getC_imgs().setVideoId(mCommentBean.getVideo_id() + "");
        }
        nineGridTestLayout.setmImagesBean(mCommentBean.getC_imgs(), 1);
        nineGridTestLayout.setIsShowAll(false);
        nineGridTestLayout.setDownImgUrl(mCommentBean.getImg_watermark() + "");
        nineGridTestLayout.setmVideoClickIml(new NineGridLayout.VideoClickIml() { // from class: com.psychiatrygarden.activity.comment.fragment.n
            @Override // com.psychiatrygarden.gradview.NineGridLayout.VideoClickIml
            public final void mVideoClickData() {
                this.f11752a.lambda$convertViewHoder$8(mCommentBean);
            }
        });
        if (Integer.parseInt(mCommentBean.getPraise_num()) <= Integer.parseInt(mCommentBean.getOppose_num())) {
            textView = textView9;
            textView2 = textView8;
            if (Integer.parseInt(mCommentBean.getPraise_num()) < Integer.parseInt(mCommentBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                } else {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                }
            } else if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } else if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            textView2 = textView8;
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
            textView = textView9;
            textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
        } else {
            textView = textView9;
            textView2 = textView8;
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
            textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
        }
        if (mCommentBean.getIs_svip().equals("1")) {
            imageView2.setVisibility(0);
            imageView2.setImageResource(R.drawable.svip333img);
        } else if (mCommentBean.getIs_vip().equals("1")) {
            imageView2.setVisibility(0);
            imageView2.setImageResource(R.drawable.vipimg);
        } else {
            imageView2.setVisibility(8);
        }
        if (mCommentBean.getIs_praise().equals("1")) {
            textView2.setText(String.format("已赞同(%s)", mCommentBean.getPraise_num()));
        } else {
            textView2.setText(String.format("赞同(%s)", mCommentBean.getPraise_num()));
        }
        if ((mCommentBean.getIs_oppose() + "").equals("1")) {
            textView.setText(String.format("已反对(%s)", mCommentBean.getOppose_num()));
        } else {
            textView.setText(String.format("反对(%s)", mCommentBean.getOppose_num()));
        }
        if (mCommentBean.getUser_identity().equals("1") || mCommentBean.getIs_authentication().equals("1")) {
            imageView.setVisibility(8);
            imageView3.setVisibility(0);
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11754c.lambda$convertViewHoder$9(view);
                }
            });
        } else {
            imageView.setVisibility(8);
            imageView3.setVisibility(8);
        }
        if (mCommentBean.getReply_num().trim().equals("0")) {
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView3 = textView10;
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font_new2));
            } else {
                textView3 = textView10;
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
            textView3.setText("回复");
            textView3.setBackgroundResource(R.color.transparent);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            textView3.setPadding(0, 10, 0, 10);
            textView3.setLayoutParams(layoutParams);
        } else {
            textView3 = textView10;
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_gray));
                textView3.setBackgroundResource(R.drawable.gray_round_bg);
            } else {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                textView3.setBackgroundResource(R.drawable.gray_round_bg_night);
            }
            textView3.setText(String.format("%s 回复", mCommentBean.getReply_num()));
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11755c.lambda$convertViewHoder$10(mCommentBean, textView2, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11758c.lambda$convertViewHoder$11(mCommentBean, textView, view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11698c.lambda$convertViewHoder$13(mCommentBean, view);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11702c.lambda$convertViewHoder$14(mCommentBean, view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11731c.lambda$convertViewHoder$15(mCommentBean, view);
            }
        });
        SubComments subComments = new SubComments(mCommentBean.getReply());
        if (subComments.size() > 0) {
            floorView = floorView2;
            floorView.setVisibility(0);
        } else {
            floorView = floorView2;
            floorView.setVisibility(8);
        }
        floorView.setComments(subComments, false, this.adapter);
        floorView.setFactory(new SubFloorFactory());
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            floorView.setBoundDrawer(ContextCompat.getDrawable(this.mContext, R.drawable.bondcomm));
        } else {
            floorView.setBoundDrawer(ContextCompat.getDrawable(this.mContext, R.drawable.bound_night));
        }
        floorView.setmCommentListenter(new AnonymousClass3(mCommentBean));
        floorView.init();
        myExpendView.setIs_del(mCommentBean.getIs_del());
        myExpendView.setText(mCommentBean.getContent(), mCommentBean.is_showValue());
        myExpendView.setListener(new MyExpendView.OnExpandStateChangeListener() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.4
            @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
            public void onClickStateChange(final View v2) {
                DiscussFragment.this.lambda$convertViewHoder$16(mCommentBean, v2);
            }

            @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
            public void onExpandStateChanged(boolean isExpanded) {
                mCommentBean.setIs_showValue(isExpanded);
            }
        });
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11735c.lambda$convertViewHoder$16(mCommentBean, view);
            }
        });
    }

    public void deleteComment(final String id, String obj_id, String reply_primary_id, final Integer valueInt, final CommentBean.DataBean.HotBean mHotBean, String module_type) {
        String str = NetworkRequestsURL.mDeleteUrl;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.moduleType == 101) {
            str = NetworkRequestsURL.soutceCommentDel;
        } else {
            ajaxParams.put("obj_id", obj_id);
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("reply_primary_id", reply_primary_id);
            ajaxParams.put("confirm", "1");
        }
        ajaxParams.put("id", id);
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DiscussFragment.this.getActivity(), "请求服务器失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        if (valueInt.intValue() == 1) {
                            for (int i2 = 0; i2 < DiscussFragment.this.list.size(); i2++) {
                                List<CommentBean.DataBean.HotBean.ReplyBean> reply = DiscussFragment.this.list.get(i2).getReply();
                                if (reply != null && reply.size() > 0) {
                                    for (int i3 = 0; i3 < DiscussFragment.this.list.get(i2).getReply().size(); i3++) {
                                        if (id.equals(DiscussFragment.this.list.get(i2).getReply().get(i3).getId())) {
                                            DiscussFragment.this.list.get(i2).getReply().get(i3).setContent("已删除");
                                            DiscussFragment.this.list.get(i2).getReply().get(i3).setC_imgs(new ImagesBean());
                                            DiscussFragment.this.list.get(i2).getReply().get(i3).setIs_del("1");
                                        }
                                    }
                                }
                            }
                            DiscussFragment.this.refreshLayout.autoRefresh();
                        } else {
                            int i4 = -1;
                            for (int i5 = 0; i5 < DiscussFragment.this.adapter.getData().size(); i5++) {
                                if (mHotBean.getId().equals(DiscussFragment.this.adapter.getData().get(i5).getId())) {
                                    i4 = i5;
                                }
                            }
                            if (i4 > -1) {
                                if (DiscussFragment.this.adapter.getItem(i4).getReply() == null || DiscussFragment.this.adapter.getItem(i4).getReply().size() <= 0) {
                                    DiscussFragment.this.adapter.removeAt(i4);
                                } else {
                                    int size = DiscussFragment.this.adapter.getItem(i4).getReply().size() - 1;
                                    CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(new Gson().toJson(DiscussFragment.this.adapter.getItem(i4).getReply().get(size)), CommentBean.DataBean.HotBean.class);
                                    if (Integer.parseInt(hotBean.getReply_num()) > 0) {
                                        hotBean.setReply_num(String.valueOf(Integer.parseInt(hotBean.getReply_num()) - 1));
                                    }
                                    if (size > 0) {
                                        hotBean.setReply(DiscussFragment.this.adapter.getItem(i4).getReply().subList(0, size));
                                    } else {
                                        hotBean.setReply(null);
                                    }
                                    DiscussFragment.this.adapter.getData().set(i4, hotBean);
                                    DiscussFragment.this.adapter.notifyItemChanged(i4);
                                }
                            }
                        }
                    }
                    NewToast.showShort(DiscussFragment.this.getActivity(), jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getCommentAd() {
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.adsInCommentApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                List<CommentBean.DataBean.HotBean> list;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.optString("code").equals("200")) {
                        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) DiscussFragment.this).mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, ((BaseFragment) DiscussFragment.this).mContext, 0L).longValue()) / 1000) - jSONObject.optJSONObject("data").optLong("time_interval", 0L) : 0L) < 0 || (list = DiscussFragment.this.list) == null || list.size() <= 1 || TextUtils.isEmpty(jSONObject.optJSONObject("data").optString("img"))) {
                            return;
                        }
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setAds(jSONObject.optString("data"));
                        DiscussFragment.this.list.add(1, hotBean);
                        DiscussFragment discussFragment = DiscussFragment.this;
                        discussFragment.adapter.setList(discussFragment.list);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_discuss;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_FULL_SCREEN_BTN, false, getContext());
        this.commentId = getArguments().getString("comment_id", "");
        this.isReplyCollection = getArguments().getBoolean("isReplyCollection", false);
        this.commentEnum = (DiscussStatus) getArguments().getSerializable("commentEnum");
        this.obj_id = getArguments().getString("obj_id");
        this.target_user_id = getArguments().getString("target_user_id");
        this.count = getArguments().getInt("count");
        this.moduleType = getArguments().getInt("module_type");
        this.comment_type = getArguments().getString("comment_type");
        this.isLastposition = getArguments().getBoolean("isLastposition", false);
        this.rule = getArguments().getString(com.heytap.mcssdk.constant.b.f7191p, "");
        this.noAccess = getArguments().getBoolean("noAccess", false);
        String string = getArguments().getString("isProhibit");
        this.isProhibit = string;
        if (TextUtils.isEmpty(string)) {
            this.isProhibit = "0";
        }
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.refreshLayout = smartRefreshLayout;
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.comment.fragment.d
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11708c.lambda$initViews$1(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.comment.fragment.e
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11713c.lambda$initViews$2(refreshLayout);
            }
        });
        CommentBean.DataBean.HotBean hotBean = null;
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_discuss_buttom_item, (ViewGroup) null);
        this.footerview = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.fragment.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                this.f11718c.lambda$initViews$3(view);
            }
        });
        this.group_name = (TextView) holder.get(R.id.group_name);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.discussRecyview);
        this.discussRecyview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<CommentBean.DataBean.HotBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CommentBean.DataBean.HotBean, BaseViewHolder>(R.layout.activity_comment_list2_new) { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, CommentBean.DataBean.HotBean mCommentBean) {
                DiscussFragment.this.convertViewHoder(baseViewHolder, mCommentBean);
            }
        };
        this.adapter = baseQuickAdapter;
        this.discussRecyview.setAdapter(baseQuickAdapter);
        this.adapter.setEmptyView(R.layout.layout_empty_view);
        if (this.commentEnum.getCode() == 0 || this.commentEnum.getCode() == 1 || this.commentEnum.getCode() == 3 || this.commentEnum.getCode() == 8 || this.commentEnum.getCode() == 11 || this.commentEnum.getCode() == 12 || this.commentEnum.getCode() == 13 || this.commentEnum.getCode() == 14) {
            this.adapter.setEmptyView(R.layout.layout_discuss_empty_view);
        }
        if (this.commentEnum.getCode() != 10 || getArguments() == null || getArguments().getBundle("bundle") == null) {
            return;
        }
        this.discussHeaderBean = (DiscussHeaderBean) getArguments().getBundle("bundle").getBinder("IPCBinder");
        this.isShowVideo = getArguments().getString("isShowVideo");
        if (this.discussHeaderBean == null) {
            return;
        }
        View viewInflate2 = getLayoutInflater().inflate(R.layout.activity_comment_list2_new, (ViewGroup) null);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            viewInflate2.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.white));
        }
        BaseViewHolder baseViewHolder = new BaseViewHolder(viewInflate2);
        try {
            if (this.discussHeaderBean.getDiscussbean() instanceof CommentBean.DataBean.HotBean) {
                hotBean = (CommentBean.DataBean.HotBean) CloneUtil.clone((CommentBean.DataBean.HotBean) this.discussHeaderBean.getDiscussbean());
            } else if (this.discussHeaderBean.getDiscussbean() instanceof CommentBean.DataBean.HotBean.ReplyBean) {
                hotBean = (CommentBean.DataBean.HotBean) CloneUtil.clone((CommentBean.DataBean.HotBean.ReplyBean) this.discussHeaderBean.getDiscussbean());
            }
            if (hotBean == null) {
                return;
            }
            hotBean.setReply(new ArrayList());
            hotBean.setIs_header(1);
            convertViewHoder(baseViewHolder, hotBean);
            this.id = hotBean.getId();
            this.obj_id = hotBean.getObj_id();
            if ("1".equals(this.isShowVideo)) {
                this.adapter.setHeaderView(viewInflate2, 0);
                ((BaseActivity) getActivity()).mActionBar.show();
            } else {
                addAliplayer(hotBean, 0);
            }
            this.adapter.setHeaderWithEmptyEnable(true);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.discussRecyview.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.hor_view_color));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void mEdit_A_Single_Comment(String id, final String pushTime, final String pushcontent, final String pushoppn, final String pushno, final CommentBean.DataBean.HotBean.ReplyBean commListBean, final int type, final CommentBean.DataBean.HotBean mHotbean, final String b_img, final String s_img) {
        String str = NetworkRequestsURL.mPutBianAdminDataUrl;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        if (this.moduleType == 101) {
            str = NetworkRequestsURL.updateComment;
        } else {
            ajaxParams.put("ctime_timestamp", pushTime);
            ajaxParams.put("praise_num", pushoppn);
            ajaxParams.put("oppose_num", pushno);
            if (type == 1) {
                ajaxParams.put("module_type", commListBean.getModule_type() + "");
            } else if (type == 2) {
                ajaxParams.put("module_type", mHotbean.getModule_type() + "");
            }
            ajaxParams.put("comment_type", this.comment_type);
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
        YJYHttpUtils.post(getActivity().getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.8
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
                super.onSuccess((AnonymousClass8) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(DiscussFragment.this.getActivity(), new JSONObject(s2).optString("message"), 0).show();
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
                    DiscussFragment.this.adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.psychiatrygarden.activity.comment.fragment.g
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f11722a.lambda$onCreate$0((ActivityResult) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        changeDesTroy();
        super.onDestroyView();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if ("autorefault".equals(str) && "newest".equals(this.rule)) {
            this.refreshLayout.autoRefresh(this.durtaion);
        }
        if ("autoData".equals(str)) {
            int newPositions = getNewPositions();
            if (newPositions == -1) {
                newPositions = 0;
            }
            this.page = 1;
            this.break_point = (System.currentTimeMillis() / 1000) + "";
            getDiscussListData(true, newPositions);
        }
        if (str.equals("BuySuccess")) {
            this.noAccess = false;
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        this.refreshLayout.autoRefresh(this.durtaion);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (getActivity() == null || !getActivity().isFinishing()) {
            return;
        }
        changeDesTroy();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        catAliPlayer(1);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        catAliPlayer(2);
    }

    public void putComment(Bundle b3, final Integer changeCode, final CommentBean.DataBean.HotBean mHotBean, String question_id, String module_type) {
        final String string = b3.getString("parent_id");
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutComment;
        if (this.moduleType == 101) {
            str = NetworkRequestsURL.submitComment;
            ajaxParams.put(FontsContractCompat.Columns.FILE_ID, question_id);
        } else {
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("obj_id", question_id);
            ajaxParams.put("to_user_id", b3.getString("to_user_id"));
        }
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("parent_id", b3.getString("parent_id"));
        ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
        String string2 = b3.getString("b_img");
        String string3 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string2)) {
            if (string2.contains("http")) {
                ajaxParams.put("b_img", string2);
                ajaxParams.put("s_img", string3);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(DiscussFragment.this.getActivity(), "请求服务器超时！", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    int i2 = 0;
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(DiscussFragment.this.getActivity(), jSONObject.optString("message"), 0).show();
                        return;
                    }
                    CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.getJSONObject("data").toString(), CommentBean.DataBean.HotBean.class);
                    if (DiscussFragment.this.isReplyCollection) {
                        if (TextUtils.isEmpty(DiscussFragment.this.id) || !DiscussFragment.this.id.equals(string)) {
                            while (true) {
                                if (i2 >= DiscussFragment.this.adapter.getData().size()) {
                                    i2 = -1;
                                    break;
                                }
                                CommentBean.DataBean.HotBean hotBean2 = DiscussFragment.this.adapter.getData().get(i2);
                                if (hotBean2.getType() != 1 && !hotBean2.isHot() && hotBean2.getId().equals(string)) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                            if (i2 > -1) {
                                DiscussFragment.this.adapter.getData().get(i2).setReply_num(String.valueOf(Integer.parseInt(DiscussFragment.this.adapter.getData().get(i2).getReply_num()) + 1));
                                DiscussFragment.this.adapter.notifyDataSetChanged();
                            }
                        } else {
                            hotBean.setReply(null);
                            DiscussFragment.this.adapter.addData(0, (int) hotBean);
                            DiscussFragment.this.adapter.notifyDataSetChanged();
                        }
                    } else if (DiscussFragment.this.adapter.getData().size() <= 0) {
                        DiscussFragment discussFragment = DiscussFragment.this;
                        discussFragment.page = 1;
                        discussFragment.getDiscussListData(false, 0);
                    } else if (TextUtils.isEmpty(string)) {
                        int newPositions = DiscussFragment.this.getNewPositions();
                        DiscussFragment.this.adapter.getData().add(newPositions, hotBean);
                        DiscussFragment.this.adapter.notifyDataSetChanged();
                        DiscussFragment.this.discussRecyview.smoothScrollToPosition(newPositions);
                    } else {
                        while (true) {
                            if (i2 >= DiscussFragment.this.adapter.getData().size()) {
                                i2 = -1;
                                break;
                            }
                            CommentBean.DataBean.HotBean hotBean3 = DiscussFragment.this.adapter.getData().get(i2);
                            if (hotBean3.getType() != 1 && !hotBean3.isHot() && hotBean3.getId().equals(string)) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (i2 > -1) {
                            DiscussFragment.this.adapter.getData().set(i2, hotBean);
                        }
                        DiscussFragment.this.adapter.notifyDataSetChanged();
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", DiscussFragment.this.getActivity());
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    CommonUtil.showFristDialog(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putOppose(String id, String type, String question_id, String module_type) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutOpposeUrl;
        if (this.moduleType == 101) {
            str = NetworkRequestsURL.soutceCommentOppose;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("obj_id", question_id);
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.6
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
                super.onSuccess((AnonymousClass6) s2);
            }
        });
    }

    public void putPraise(String id, final String type, String question_id, final String module_type) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutPraseUrl;
        if (this.moduleType == 101) {
            str = NetworkRequestsURL.soutceCommentSupport;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("obj_id", question_id);
            ajaxParams.put("module_type", module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.fragment.DiscussFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                if (com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP.equals(module_type) || com.tencent.connect.common.Constants.VIA_REPORT_TYPE_MAKE_FRIEND.equals(module_type) || "31".equals(module_type)) {
                    EventBus.getDefault().post(new RefreshPraiseEvent("1".equals(type)));
                }
            }
        });
    }

    /* renamed from: showClikPop, reason: merged with bridge method [inline-methods] */
    public void lambda$convertViewHoder$16(CommentBean.DataBean.HotBean hotBean, View view) {
        boolean zEquals = UserConfig.getUserId().equals(hotBean.getUser_id());
        if ("1".equals(hotBean.getStatus())) {
            CommonUtil.mToastShow(getActivity());
            return;
        }
        int i2 = TextUtils.isEmpty(hotBean.getReply_num()) ? 0 : Integer.parseInt(hotBean.getReply_num());
        new XPopup.Builder(getActivity()).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(getActivity(), hotBean.getContent(), hotBean.getNickname(), zEquals ? 1 : 0, hotBean.getIs_anonymous() + "", this.moduleType + "", hotBean.getDelete_skill(), hotBean.getId(), i2, new AnonymousClass12(hotBean, view))).show();
    }
}
