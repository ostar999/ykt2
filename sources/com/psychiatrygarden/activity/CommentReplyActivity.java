package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.source.Definition;
import com.aliyun.player.source.VidSts;
import com.aliyun.svideo.common.utils.ScreenUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.adapter.MyCommentAdapter;
import com.psychiatrygarden.bean.AliYunKeyInfoBean;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ReplayCommentUserInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateCommentInfoEvent;
import com.psychiatrygarden.gradview.NineGridLayout;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.FloorView;
import com.psychiatrygarden.widget.MyExpendView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CommentReplyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private View addFooterView;
    private View addHeadView;
    private AnalysisAdapter analysisAdapter;
    private String break_point;
    private TextView commentList_item_tv_school;
    private TextView commentList_item_tv_userName;
    private String comment_type;
    public TextView commentnum;
    private CommentBean.DataBean.HotBean hotBean;
    private String isProhibit;
    public ImageView iv_topic_detail_msg;
    public LinearLayout linevideoView;
    private ListView listView;
    public LinearLayout llay_reply_bg;
    private MyCommentAdapter mAdapter;
    private AnalysisBean.DataBean mAnalysisBean;
    private String mAppId;
    private boolean mIsSource;
    private int module_type;
    private RelativeLayout relReplyView;
    private CommentBean.DataBean.HotBean.ReplyBean replyBean;
    private SwipeRefreshLayout swipe;
    private TextView tv_reply;
    public TextView tv_topic_detail_add_comment;
    private AliyunVodPlayerView video_view;
    private String viewType;
    private int pageNum = 1;
    private List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private boolean is_replybean = true;
    private String isShowVideo = "";
    private Boolean hideButtomVisiavle = Boolean.FALSE;
    private boolean isNoData = false;
    private boolean isLoading = false;
    private boolean isDestroyed = false;

    /* renamed from: com.psychiatrygarden.activity.CommentReplyActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<String> {
        public AnonymousClass1(List mData, Context mcontext, int layoutId) {
            super(mData, mcontext, layoutId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(View view) {
            if (ProjectApp.isSerachClick) {
                return;
            }
            CommentReplyActivity.this.setHiddenData();
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, String s2, int position) {
            ((TextView) vHolder.getView(R.id.textchilcl)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.l4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12654c.lambda$convert$0(view);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.CommentReplyActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            CommentReplyActivity.access$508(CommentReplyActivity.this);
            CommentReplyActivity.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && CommentReplyActivity.this.listView.getFooterViewsCount() <= 0) {
                if (CommentReplyActivity.this.swipe.isRefreshing()) {
                    CommentReplyActivity.this.swipe.setRefreshing(false);
                    return;
                }
                CommentReplyActivity.this.listView.addFooterView(CommentReplyActivity.this.addFooterView);
                CommentReplyActivity.this.addFooterView.setVisibility(0);
                if (CommentReplyActivity.this.isNoData) {
                    CommentReplyActivity.this.listView.removeFooterView(CommentReplyActivity.this.addFooterView);
                    CommentReplyActivity.this.addFooterView.setVisibility(8);
                } else {
                    CommentReplyActivity.this.isLoading = true;
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.n4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13036c.lambda$onScrollStateChanged$0();
                        }
                    }, 1000L);
                }
            }
        }
    }

    public static /* synthetic */ int access$508(CommentReplyActivity commentReplyActivity) {
        int i2 = commentReplyActivity.pageNum;
        commentReplyActivity.pageNum = i2 + 1;
        return i2;
    }

    private void addAnaHeader() {
        View viewInflate = View.inflate(this, R.layout.layout_header_analy, null);
        this.addHeadView = viewInflate;
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mAnalysisBean);
        AnalysisAdapter analysisAdapter = new AnalysisAdapter(arrayList);
        this.analysisAdapter = analysisAdapter;
        recyclerView.setAdapter(analysisAdapter);
        this.listView.addHeaderView(this.addHeadView);
    }

    private void addHead() throws Resources.NotFoundException {
        View viewInflate = View.inflate(this, R.layout.comment_list_2_new, null);
        this.addHeadView = viewInflate;
        this.llay_reply_bg = (LinearLayout) viewInflate.findViewById(R.id.llay_reply_bg);
        this.commentnum = (TextView) this.addHeadView.findViewById(R.id.commentnum);
        this.linevideoView = (LinearLayout) this.addHeadView.findViewById(R.id.linevideoView);
        this.video_view = (AliyunVodPlayerView) this.addHeadView.findViewById(R.id.video_view);
        TextView textView = (TextView) this.addHeadView.findViewById(R.id.group_name);
        RelativeLayout relativeLayout = (RelativeLayout) this.addHeadView.findViewById(R.id.linciew);
        final NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) this.addHeadView.findViewById(R.id.ningrids);
        this.commentList_item_tv_userName = (TextView) this.addHeadView.findViewById(R.id.commentList_item_tv_userName);
        this.commentList_item_tv_school = (TextView) this.addHeadView.findViewById(R.id.commentList_item_tv_school);
        FloorView floorView = (FloorView) this.addHeadView.findViewById(R.id.foor);
        CircleImageView circleImageView = (CircleImageView) this.addHeadView.findViewById(R.id.commentList_item_userIcon);
        TextView textView2 = (TextView) this.addHeadView.findViewById(R.id.commentList_item_tv_praise);
        MyExpendView myExpendView = (MyExpendView) this.addHeadView.findViewById(R.id.myexptervew);
        ImageView imageView = (ImageView) this.addHeadView.findViewById(R.id.iv_elite);
        final TextView textView3 = (TextView) this.addHeadView.findViewById(R.id.tv_support);
        final TextView textView4 = (TextView) this.addHeadView.findViewById(R.id.tv_oppose);
        this.tv_reply = (TextView) this.addHeadView.findViewById(R.id.tv_reply);
        textView.setVisibility(8);
        relativeLayout.setVisibility(0);
        floorView.setVisibility(8);
        this.video_view.setKeepScreenOn(true);
        this.video_view.setTheme(Theme.Red);
        this.video_view.setCirclePlay(false);
        this.video_view.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.activity.e4
            @Override // com.aliyun.player.IPlayer.OnPreparedListener
            public final void onPrepared() {
                this.f12268a.lambda$addHead$4();
            }
        });
        this.video_view.setOnCompletionListener(new IPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.activity.o3
            @Override // com.aliyun.player.IPlayer.OnCompletionListener
            public final void onCompletion() {
                CommentReplyActivity.lambda$addHead$5();
            }
        });
        if (this.is_replybean) {
            String is_essence = TextUtils.isEmpty(this.replyBean.getIs_essence()) ? "0" : this.replyBean.getIs_essence();
            if (TextUtils.isEmpty(is_essence) || is_essence.equals("0")) {
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(8);
            }
            this.commentList_item_tv_userName.setText(this.replyBean.getNickname());
            this.commentList_item_tv_userName.setTextColor(Color.parseColor(this.replyBean.getUser_identity_color()));
            textView2.setText(this.replyBean.getPraise_num());
            if (this.replyBean.getIs_svip().equals("1")) {
                CommonUtil.mDoDrawable(this, this.commentList_item_tv_userName, R.drawable.svip333img, 2);
            } else if (this.replyBean.getIs_vip().equals("1")) {
                CommonUtil.mDoDrawable(this, this.commentList_item_tv_userName, R.drawable.vipimg, 2);
            } else {
                this.commentList_item_tv_userName.setCompoundDrawables(null, null, null, null);
            }
            circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13530c.lambda$addHead$6(view);
                }
            });
            this.commentList_item_tv_userName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.q3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13726c.lambda$addHead$7(view);
                }
            });
            if (this.replyBean.getC_imgs() != null && !TextUtils.isEmpty(this.replyBean.getC_imgs().getS_img())) {
                nineGridTestLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.r3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13756c.lambda$addHead$8(nineGridTestLayout);
                    }
                });
            }
            this.replyBean.getC_imgs().setVideoId(this.replyBean.getVideo_id());
            this.replyBean.getC_imgs().setWatch_permission(this.replyBean.getWatch_permission());
            nineGridTestLayout.setmImagesBean(this.replyBean.getC_imgs(), 1);
            nineGridTestLayout.setDownImgUrl(this.replyBean.getImg_watermark() + "");
            nineGridTestLayout.setIsShowAll(false);
            nineGridTestLayout.setmVideoClickIml(new NineGridLayout.VideoClickIml() { // from class: com.psychiatrygarden.activity.s3
                @Override // com.psychiatrygarden.gradview.NineGridLayout.VideoClickIml
                public final void mVideoClickData() {
                    this.f13817a.lambda$addHead$9();
                }
            });
            if (TextUtils.isEmpty(this.replyBean.getVideo_id()) || "1".equals(this.isShowVideo)) {
                mRefasultData();
            } else if (!UserConfig.getInstance().getUser().getIs_vip().equals("0") || this.replyBean.getUser_id().equals(UserConfig.getUserId())) {
                mRefasultData2(this.replyBean.getVideo_id());
            } else {
                mRefasultData();
            }
            myExpendView.setIs_del(this.replyBean.getIs_del());
            myExpendView.setText(this.replyBean.getContent(), this.replyBean.is_showValue());
            myExpendView.setListener(new MyExpendView.OnExpandStateChangeListener() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.4
                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onClickStateChange(View v2) {
                }

                @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                public void onExpandStateChanged(boolean isExpanded) {
                    CommentReplyActivity.this.replyBean.setIs_showValue(isExpanded);
                }
            });
            this.commentList_item_tv_school.setText(String.format("%s %s", this.replyBean.getSchool(), this.replyBean.getCtime()));
            if (TextUtils.isEmpty(this.replyBean.getAvatar() + "")) {
                circleImageView.setImageResource(R.drawable.empty_photo);
            } else {
                GlideUtils.loadImage(circleImageView.getContext(), this.replyBean.getAvatar(), circleImageView);
            }
            if (Integer.parseInt(this.replyBean.getPraise_num()) > Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                }
            } else if (Integer.parseInt(this.replyBean.getPraise_num()) < Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
            if (this.replyBean.getIs_praise().equals("1")) {
                textView3.setText(String.format("已赞同(%s)", this.replyBean.getPraise_num()));
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                }
            } else {
                textView3.setText(String.format("赞同(%s)", this.replyBean.getPraise_num()));
            }
            if (this.replyBean.getIs_oppose().equals("1")) {
                textView4.setText(String.format("已反对(%s)", this.replyBean.getOppose_num()));
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                } else {
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                }
            } else {
                textView4.setText(String.format("反对(%s)", this.replyBean.getOppose_num()));
            }
            if (this.replyBean.getReply_num().trim().equals("0")) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                    this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font));
                } else {
                    this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                    this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                }
                this.tv_reply.setText(" 回复");
            } else {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                    this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font));
                } else {
                    this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                    this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                }
                this.tv_reply.setText(String.format("%s 回复", this.replyBean.getReply_num()));
                this.commentnum.setText(String.format("全部评论（%s）", this.replyBean.getReply_num()));
            }
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13939c.lambda$addHead$10(textView3, textView4, view);
                }
            });
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.u3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13972c.lambda$addHead$11(textView4, textView3, view);
                }
            });
            this.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14010c.lambda$addHead$13(view);
                }
            });
        } else {
            String is_essence2 = TextUtils.isEmpty(this.hotBean.getIs_essence()) ? "0" : this.hotBean.getIs_essence();
            if (this.hotBean != null) {
                if (is_essence2.equals("0")) {
                    imageView.setVisibility(8);
                } else {
                    imageView.setVisibility(8);
                }
                this.commentnum.setVisibility(8);
                this.commentList_item_tv_userName.setText(this.hotBean.getNickname());
                this.commentList_item_tv_userName.setTextColor(Color.parseColor(this.hotBean.getUser_identity_color()));
                if (this.hotBean.getIs_svip().equals("1")) {
                    CommonUtil.mDoDrawable(this, this.commentList_item_tv_userName, R.drawable.svip333img, 2);
                } else if (this.hotBean.getIs_vip().equals("1")) {
                    CommonUtil.mDoDrawable(this, this.commentList_item_tv_userName, R.drawable.vipimg, 2);
                } else {
                    this.commentList_item_tv_userName.setCompoundDrawables(null, null, null, null);
                }
                textView2.setText(this.hotBean.getPraise_num());
                circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.w3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14137c.lambda$addHead$14(view);
                    }
                });
                if (this.hotBean.getC_imgs() != null && !TextUtils.isEmpty(this.hotBean.getC_imgs().getS_img())) {
                    nineGridTestLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.f4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12340c.lambda$addHead$15(nineGridTestLayout);
                        }
                    });
                }
                this.hotBean.getC_imgs().setVideoId(this.hotBean.getVideo_id());
                this.hotBean.getC_imgs().setWatch_permission(this.hotBean.getWatch_permission());
                nineGridTestLayout.setmImagesBean(this.hotBean.getC_imgs(), 1);
                nineGridTestLayout.setDownImgUrl(this.hotBean.getImg_watermark() + "");
                nineGridTestLayout.setIsShowAll(false);
                nineGridTestLayout.setmVideoClickIml(new NineGridLayout.VideoClickIml() { // from class: com.psychiatrygarden.activity.g4
                    @Override // com.psychiatrygarden.gradview.NineGridLayout.VideoClickIml
                    public final void mVideoClickData() {
                        this.f12426a.lambda$addHead$16();
                    }
                });
                if (TextUtils.isEmpty(this.hotBean.getVideo_id()) || "1".equals(this.isShowVideo)) {
                    mRefasultData();
                } else if (!UserConfig.getInstance().getUser().getIs_vip().equals("0") || this.hotBean.getUser_id().equals(UserConfig.getUserId())) {
                    mRefasultData2(this.hotBean.getVideo_id());
                } else {
                    mRefasultData();
                }
                this.commentList_item_tv_userName.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.h4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12466c.lambda$addHead$17(view);
                    }
                });
                myExpendView.setIs_del(this.hotBean.getIs_del());
                myExpendView.setText(this.hotBean.getContent(), this.hotBean.is_showValue());
                myExpendView.setListener(new MyExpendView.OnExpandStateChangeListener() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.5
                    @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                    public void onClickStateChange(View v2) {
                    }

                    @Override // com.psychiatrygarden.widget.MyExpendView.OnExpandStateChangeListener
                    public void onExpandStateChanged(boolean isExpanded) {
                        CommentReplyActivity.this.hotBean.setIs_showValue(isExpanded);
                    }
                });
                this.commentList_item_tv_school.setText(String.format("%s %s", this.hotBean.getSchool(), this.hotBean.getCtime()));
                if (TextUtils.isEmpty(this.hotBean.getAvatar() + "")) {
                    circleImageView.setImageResource(R.drawable.empty_photo);
                } else {
                    GlideUtils.loadImage(circleImageView.getContext(), this.hotBean.getAvatar(), circleImageView);
                }
                if (Integer.parseInt(this.hotBean.getPraise_num()) > Integer.parseInt(this.hotBean.getOppose_num())) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    } else {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    }
                } else if (Integer.parseInt(this.hotBean.getPraise_num()) < Integer.parseInt(this.hotBean.getOppose_num())) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    } else {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    }
                } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                }
                if (this.hotBean.getIs_praise().equals("1")) {
                    textView3.setText(String.format("已赞同(%s)", this.hotBean.getPraise_num()));
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    } else {
                        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    }
                } else {
                    textView3.setText(String.format("赞同(%s)", this.hotBean.getPraise_num()));
                }
                if (this.hotBean.getIs_oppose().equals("1")) {
                    textView4.setText(String.format("已反对(%s)", this.hotBean.getOppose_num()));
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    } else {
                        textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    }
                } else {
                    textView4.setText(String.format("反对(%s)", this.hotBean.getOppose_num()));
                }
                if (this.hotBean.getReply_num().trim().equals("0")) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                        this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font));
                    } else {
                        this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                        this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                    }
                    this.tv_reply.setText(" 回复");
                } else {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg);
                        this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font));
                    } else {
                        this.tv_reply.setBackgroundResource(R.drawable.gray_round_bg_night);
                        this.tv_reply.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                    }
                    this.tv_reply.setText(String.format("%s 回复", this.hotBean.getReply_num()));
                }
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.i4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12495c.lambda$addHead$18(textView3, textView4, view);
                    }
                });
                textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.j4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12546c.lambda$addHead$19(textView4, textView3, view);
                    }
                });
                this.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.k4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12578c.lambda$addHead$21(view);
                    }
                });
            }
        }
        this.listView.addHeaderView(this.addHeadView);
    }

    private boolean isStrangePhone() {
        String str = Build.DEVICE;
        return str.equalsIgnoreCase("mx5") || str.equalsIgnoreCase("Redmi Note2") || str.equalsIgnoreCase("Z00A_1") || str.equalsIgnoreCase("hwH60-L02") || str.equalsIgnoreCase("hermes") || (str.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$22() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$10(TextView textView, TextView textView2, View view) {
        if (ProjectApp.isSerachClick || this.replyBean.getIs_oppose().equals("1")) {
            return;
        }
        String str = "0";
        if (!this.replyBean.getIs_praise().equals("1")) {
            Toast_pop(textView, 0);
            putPraise(false, this.replyBean.getId(), this.replyBean.getObj_id(), "1", this.replyBean.getContent());
            this.replyBean.setIs_praise("1");
            try {
                String praise_num = this.replyBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num)) {
                    str = praise_num;
                }
                this.replyBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已赞同(%s)", this.replyBean.getPraise_num()));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        putPraise(false, this.replyBean.getId(), this.replyBean.getObj_id(), "0", this.replyBean.getContent());
        this.replyBean.setIs_praise("0");
        try {
            String praise_num2 = this.replyBean.getPraise_num();
            if (TextUtils.isEmpty(praise_num2)) {
                praise_num2 = "0";
            }
            if (praise_num2.trim().equals("0")) {
                this.replyBean.setPraise_num("0");
            } else {
                this.replyBean.setPraise_num((Integer.parseInt(praise_num2) - 1) + "");
            }
            textView.setText(String.format("赞同(%s)", this.replyBean.getPraise_num()));
            if (Integer.parseInt(this.replyBean.getPraise_num()) > Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    return;
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    return;
                }
            }
            if (Integer.parseInt(this.replyBean.getPraise_num()) < Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    return;
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$11(TextView textView, TextView textView2, View view) {
        if (ProjectApp.isSerachClick || this.replyBean.getIs_praise().equals("1")) {
            return;
        }
        String str = "0";
        if (!this.replyBean.getIs_oppose().equals("1")) {
            Toast_pop(textView, 1);
            putOppose(false, this.replyBean.getId(), this.replyBean.getObj_id(), "1", this.replyBean.getContent());
            this.replyBean.setIs_oppose("1");
            try {
                String oppose_num = this.replyBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num)) {
                    str = oppose_num;
                }
                this.replyBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已反对(%s)", this.replyBean.getOppose_num()));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        putOppose(false, this.replyBean.getId(), this.replyBean.getObj_id(), "0", this.replyBean.getContent());
        this.replyBean.setIs_oppose("0");
        try {
            String oppose_num2 = this.replyBean.getOppose_num();
            if (TextUtils.isEmpty(oppose_num2)) {
                oppose_num2 = "0";
            }
            if (oppose_num2.trim().equals("0")) {
                this.replyBean.setOppose_num("0");
            } else {
                this.replyBean.setOppose_num((Integer.parseInt(oppose_num2) - 1) + "");
            }
            textView.setText(String.format("反对(%s)", this.replyBean.getOppose_num()));
            if (Integer.parseInt(this.replyBean.getPraise_num()) > Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    return;
                } else {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    return;
                }
            }
            if (Integer.parseInt(this.replyBean.getPraise_num()) < Integer.parseInt(this.replyBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    return;
                } else {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$12(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(false, bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$13(View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(this.mContext, "本帖已被锁定，不支持回帖");
            return;
        }
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.n3
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f13035a.lambda$addHead$12(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$14(View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (this.hotBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (this.hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", this.hotBean.getUser_id());
        intent.putExtra("jiav", this.hotBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$15(NineGridTestLayout nineGridTestLayout) {
        float width;
        float s_width = this.hotBean.getC_imgs().getS_width();
        float s_height = this.hotBean.getC_imgs().getS_height();
        if (s_height >= s_width * 2.0f) {
            width = (nineGridTestLayout.getWidth() / 3.0f) * 2.0f;
        } else if (s_height >= s_width) {
            width = (s_height * ((nineGridTestLayout.getWidth() / 2.0f) - 50.0f)) / s_width;
        } else if (s_width >= s_height * 2.0f) {
            width = ((nineGridTestLayout.getWidth() * 2.0f) / 3.0f) / 2.0f;
        } else {
            width = (((double) (s_width / s_height)) <= 1.1d ? ((nineGridTestLayout.getWidth() / 5.0f) * 3.0f) * 4.0f : ((nineGridTestLayout.getWidth() / 5.0f) * 3.0f) * 3.0f) / 5.0f;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nineGridTestLayout.getLayoutParams();
        layoutParams.height = (int) width;
        nineGridTestLayout.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$16() {
        if ("1".equals(this.hotBean.getStatus())) {
            CommonUtil.mToastShow(this.mContext);
            return;
        }
        if (!UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            initVideoView(this.hotBean.getC_imgs().getVideoId() + "");
            return;
        }
        if (!this.hotBean.getUser_id().equals(UserConfig.getUserId())) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
            return;
        }
        initVideoView(this.hotBean.getC_imgs().getVideoId() + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$17(View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (this.hotBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (this.hotBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", this.hotBean.getUser_id());
        intent.putExtra("jiav", this.hotBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$18(TextView textView, TextView textView2, View view) {
        if (this.hotBean.getIs_oppose().equals("1") || ProjectApp.isSerachClick) {
            return;
        }
        String str = "0";
        if (!this.hotBean.getIs_praise().equals("1")) {
            Toast_pop(textView, 0);
            putPraise(true, this.hotBean.getId(), this.hotBean.getObj_id(), "1", this.hotBean.getContent());
            this.hotBean.setIs_praise("1");
            try {
                String praise_num = this.hotBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num)) {
                    str = praise_num;
                }
                this.hotBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已赞同(%s)", this.hotBean.getPraise_num()));
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                }
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        putPraise(true, this.hotBean.getId(), this.hotBean.getObj_id(), "0", this.hotBean.getContent());
        this.hotBean.setIs_praise("0");
        try {
            String praise_num2 = this.hotBean.getPraise_num();
            if (TextUtils.isEmpty(praise_num2)) {
                praise_num2 = "0";
            }
            if (praise_num2.trim().equals("0")) {
                this.hotBean.setPraise_num("0");
            } else {
                this.hotBean.setPraise_num((Integer.parseInt(praise_num2) - 1) + "");
            }
            textView.setText(String.format("赞同(%s)", this.hotBean.getPraise_num()));
            if (Integer.parseInt(this.hotBean.getPraise_num()) > Integer.parseInt(this.hotBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    return;
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    return;
                }
            }
            if (Integer.parseInt(this.hotBean.getPraise_num()) < Integer.parseInt(this.hotBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    return;
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$19(TextView textView, TextView textView2, View view) {
        if (ProjectApp.isSerachClick || this.hotBean.getIs_praise().equals("1")) {
            return;
        }
        String str = "0";
        if (!this.hotBean.getIs_oppose().equals("1")) {
            Toast_pop(textView, 1);
            putOppose(true, this.hotBean.getId(), this.hotBean.getObj_id(), "1", this.hotBean.getContent());
            this.hotBean.setIs_oppose("1");
            try {
                String oppose_num = this.hotBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num)) {
                    str = oppose_num;
                }
                this.hotBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已反对(%s)", this.hotBean.getOppose_num()));
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                } else {
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                }
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        putOppose(true, this.hotBean.getId(), this.hotBean.getObj_id(), "0", this.hotBean.getContent());
        this.hotBean.setIs_oppose("0");
        try {
            String oppose_num2 = this.hotBean.getOppose_num();
            if (TextUtils.isEmpty(oppose_num2)) {
                oppose_num2 = "0";
            }
            if (oppose_num2.trim().equals("0")) {
                this.hotBean.setOppose_num("0");
            } else {
                this.hotBean.setOppose_num((Integer.parseInt(oppose_num2) - 1) + "");
            }
            textView.setText(String.format("反对(%s)", this.hotBean.getOppose_num()));
            if (Integer.parseInt(this.hotBean.getPraise_num()) > Integer.parseInt(this.hotBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    return;
                } else {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.green_theme_night));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    return;
                }
            }
            if (Integer.parseInt(this.hotBean.getPraise_num()) < Integer.parseInt(this.hotBean.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_red));
                    return;
                } else {
                    textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.red_theme_night));
                    return;
                }
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_light));
            } else {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$20(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(true, bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$21(View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(this.mContext, "本帖已被锁定，不支持回帖");
            return;
        }
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.c4
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f11135a.lambda$addHead$20(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$4() {
        this.video_view.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addHead$5() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$6(View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (this.replyBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (this.replyBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", this.replyBean.getUser_id());
        intent.putExtra("jiav", this.replyBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$7(View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (this.replyBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (this.replyBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", this.replyBean.getUser_id());
        intent.putExtra("jiav", this.replyBean.getUser_identity());
        intent.addFlags(67108864);
        startActivity(intent);
        overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$8(NineGridTestLayout nineGridTestLayout) {
        float width;
        float s_width = this.replyBean.getC_imgs().getS_width();
        float s_height = this.replyBean.getC_imgs().getS_height();
        if (s_height >= s_width * 2.0f) {
            width = (nineGridTestLayout.getWidth() / 3.0f) * 2.0f;
        } else if (s_height >= s_width) {
            width = (s_height * ((nineGridTestLayout.getWidth() / 2) - 50.0f)) / s_width;
        } else if (s_width >= s_height * 2.0f) {
            width = ((nineGridTestLayout.getWidth() * 2) / 3.0f) / 2.0f;
        } else {
            width = (((double) (s_width / s_height)) <= 1.1d ? (nineGridTestLayout.getWidth() / 5) * 4.0f : ((nineGridTestLayout.getWidth() / 5) * 3.0f) * 3.0f) / 5.0f;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nineGridTestLayout.getLayoutParams();
        layoutParams.height = (int) width;
        nineGridTestLayout.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHead$9() {
        if ("1".equals(this.replyBean.getStatus())) {
            CommonUtil.mToastShow(this.mContext);
            return;
        }
        if (!UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            initVideoView(this.replyBean.getC_imgs().getVideoId() + "");
            return;
        }
        if (!this.replyBean.getUser_id().equals(UserConfig.getUserId())) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
            return;
        }
        initVideoView(this.replyBean.getC_imgs().getVideoId() + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        int i2 = this.module_type;
        if (i2 == 1) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext);
            EventBus.getDefault().post(CommonParameter.Comment_library_Red_Dot);
        } else if (i2 == 3) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext);
            EventBus.getDefault().post(CommonParameter.Experience_comment_Red_Dot);
        }
        Intent intent = new Intent(this.mContext, (Class<?>) MyCommentListActivity.class);
        intent.putExtra("title", "评论我");
        intent.putExtra("type", 1);
        intent.putExtra("comment_type", this.comment_type);
        intent.putExtra("module_type", this.module_type);
        intent.putExtra("isProhibit", this.isProhibit);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(false, bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (this.isProhibit.equals("1")) {
            ToastUtil.shortToast(this.mContext, "本帖已被锁定，不支持回帖");
            return;
        }
        Context context = this.mContext;
        onDialogClickListener ondialogclicklistener = new onDialogClickListener() { // from class: com.psychiatrygarden.activity.b4
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f11096a.lambda$init$1(str, str2, str3);
            }
        };
        int i2 = this.module_type;
        boolean z2 = true;
        if (i2 != 1 && i2 != 4) {
            z2 = false;
        }
        new DialogInput(context, ondialogclicklistener, z2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataList$3() {
        this.swipe.setRefreshing(true);
        getCommentListData();
    }

    private void updatePlayerViewMode() {
        if (this.video_view != null) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 != 1) {
                if (i2 == 2) {
                    if (!isStrangePhone()) {
                        getWindow().setFlags(1024, 1024);
                        this.video_view.setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.video_view.getLayoutParams();
                    layoutParams.width = -1;
                    layoutParams.height = ScreenUtils.getHeight(this);
                    this.video_view.setLayoutParams(layoutParams);
                    findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
                    return;
                }
                return;
            }
            getWindow().clearFlags(1024);
            this.video_view.setSystemUiVisibility(0);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.video_view.getLayoutParams();
            layoutParams2.width = -1;
            layoutParams2.height = ScreenUtil.getPxByDp((Context) this, R2.attr.actionModeFindDrawable);
            this.video_view.setLayoutParams(layoutParams2);
            if (this.hideButtomVisiavle.booleanValue()) {
                findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
            } else if (ProjectApp.isSerachClick) {
                findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
            } else {
                findViewById(R.id.rl_topic_detail_bottom).setVisibility(0);
            }
        }
    }

    public void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.x3
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                CommentReplyActivity.lambda$Toast_pop$22();
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
        new Handler().postDelayed(new z3(popupWindow), 1000L);
    }

    public void getCommentListData() {
        String str = NetworkRequestsURL.mGetCommentReplyUrl;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
        if (this.mIsSource) {
            str = NetworkRequestsURL.sourceReplyList;
            if (this.is_replybean) {
                ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.replyBean.getFile_id());
                ajaxParams.put("parent_id", this.replyBean.getId());
            } else {
                ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.hotBean.getFile_id());
                ajaxParams.put("parent_id", this.hotBean.getId());
            }
        } else {
            ajaxParams.put("module_type", this.module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            if (this.pageNum == 1) {
                this.break_point = (System.currentTimeMillis() / 1000) + "";
            }
            ajaxParams.put("break_point", this.break_point);
            if (getIntent().getSerializableExtra("bean") instanceof AnalysisBean.DataBean) {
                ajaxParams.put("id", this.mAnalysisBean.getId() + "");
                ajaxParams.put("obj_id", this.mAnalysisBean.getQuestion_id() + "");
            } else if (this.is_replybean) {
                ajaxParams.put("id", this.replyBean.getId());
                ajaxParams.put("obj_id", this.replyBean.getObj_id());
            } else {
                ajaxParams.put("id", this.hotBean.getId());
                ajaxParams.put("obj_id", this.hotBean.getObj_id());
            }
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CommentReplyActivity.this.swipe.isRefreshing()) {
                    CommentReplyActivity.this.swipe.setRefreshing(false);
                    CommentReplyActivity.this.AlertToast("加载失败");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        CommentReplyActivity.this.AlertToast(jSONObject.optString("message"));
                    } else if (CommentReplyActivity.this.pageNum == 1) {
                        ReplayCommentUserInfoBean replayCommentUserInfoBean = (ReplayCommentUserInfoBean) new Gson().fromJson(jSONObject.optJSONObject("data").optString("comment_user"), ReplayCommentUserInfoBean.class);
                        if (replayCommentUserInfoBean != null) {
                            replayCommentUserInfoBean.getSchool();
                            CommentReplyActivity.this.commentList_item_tv_school.setText(String.format("%s %s", replayCommentUserInfoBean.getSchool(), replayCommentUserInfoBean.getCtime()));
                            if (replayCommentUserInfoBean.getIs_svip().equals("1")) {
                                CommentReplyActivity commentReplyActivity = CommentReplyActivity.this;
                                CommonUtil.mDoDrawable(commentReplyActivity, commentReplyActivity.commentList_item_tv_userName, R.drawable.svip333img, 2);
                            } else if (replayCommentUserInfoBean.getIs_vip().equals("1")) {
                                CommentReplyActivity commentReplyActivity2 = CommentReplyActivity.this;
                                CommonUtil.mDoDrawable(commentReplyActivity2, commentReplyActivity2.commentList_item_tv_userName, R.drawable.vipimg, 2);
                            } else {
                                CommentReplyActivity.this.commentList_item_tv_userName.setCompoundDrawables(null, null, null, null);
                            }
                        }
                        if (CommentReplyActivity.this.commlist != null && CommentReplyActivity.this.commlist.size() > 0) {
                            CommentReplyActivity.this.commlist.clear();
                        }
                        CommentReplyActivity.this.commlist = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("time_line"), new TypeToken<List<CommentBean.DataBean.HotBean>>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.8.1
                        }.getType());
                        CommentReplyActivity commentReplyActivity3 = CommentReplyActivity.this;
                        CommentReplyActivity commentReplyActivity4 = CommentReplyActivity.this;
                        List list = commentReplyActivity4.commlist;
                        CommentReplyActivity commentReplyActivity5 = CommentReplyActivity.this;
                        commentReplyActivity3.mAdapter = new MyCommentAdapter(commentReplyActivity4, list, commentReplyActivity5.mContext, commentReplyActivity5.comment_type, false, CommentReplyActivity.this.mIsSource, CommentReplyActivity.this.isProhibit, CommentReplyActivity.this.viewType);
                        CommentReplyActivity.this.listView.setAdapter((ListAdapter) CommentReplyActivity.this.mAdapter);
                        try {
                            CommentReplyActivity.this.mAdapter.setisViable(CommentReplyActivity.this.getIntent().getBooleanExtra("isVisiable", false));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        CommentReplyActivity.this.listView.removeFooterView(CommentReplyActivity.this.addFooterView);
                        CommentReplyActivity.this.addFooterView.setVisibility(8);
                        new ArrayList();
                        List list2 = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("time_line"), new TypeToken<List<CommentBean.DataBean.HotBean>>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.8.2
                        }.getType());
                        if (list2.size() == 0) {
                            CommentReplyActivity.this.isNoData = true;
                            CommentReplyActivity.this.AlertToast("已经是最后一条");
                            CommentReplyActivity.this.isLoading = false;
                            return;
                        }
                        CommentReplyActivity.this.commlist.addAll(list2);
                        CommentReplyActivity.this.mAdapter.notifyDataSetChanged();
                    }
                    CommentReplyActivity.this.isLoading = false;
                    if (CommentReplyActivity.this.swipe.isRefreshing()) {
                        CommentReplyActivity.this.swipe.setRefreshing(false);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public void getCourseAk(final String vid, final AliyunVodPlayerView mAliyunVodPlayerView) {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CommentReplyActivity.this.hideProgressDialog();
                ToastUtil.shortToast(CommentReplyActivity.this, "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CommentReplyActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    AliYunKeyInfoBean aliYunKeyInfoBean = (AliYunKeyInfoBean) new Gson().fromJson(s2, AliYunKeyInfoBean.class);
                    if (!"200".equals(aliYunKeyInfoBean.getCode()) || aliYunKeyInfoBean.getData() == null) {
                        ToastUtil.shortToast(CommentReplyActivity.this, jSONObject.optString("message"));
                    } else {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getAkId());
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getAkSecret());
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, aliYunKeyInfoBean.getData().getSt());
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(vid);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setAccessKeySecret(strDecode2);
                        if (GlobalPlayerConfig.PlayConfig.mAutoSwitchOpen) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(Definition.DEFINITION_AUTO);
                            vidSts.setDefinition(arrayList);
                        }
                        mAliyunVodPlayerView.setVidSts(vidSts);
                        CommentReplyActivity.this.mActionBar.hide();
                        final AliyunVodPlayerView aliyunVodPlayerView = mAliyunVodPlayerView;
                        Objects.requireNonNull(aliyunVodPlayerView);
                        aliyunVodPlayerView.setOnPreparedListener(new IPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.activity.m4
                            @Override // com.aliyun.player.IPlayer.OnPreparedListener
                            public final void onPrepared() {
                                aliyunVodPlayerView.start();
                            }
                        });
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                CommentReplyActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.isShowVideo = getIntent().getExtras().getString("isShowVideo", "1");
        this.swipe = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.relReplyView = (RelativeLayout) findViewById(R.id.relReplyView);
        this.listView = (ListView) findViewById(R.id.listView);
        if (this.hideButtomVisiavle.booleanValue() || ProjectApp.isSerachClick) {
            findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
        } else {
            findViewById(R.id.rl_topic_detail_bottom).setVisibility(0);
        }
        this.tv_topic_detail_add_comment = (TextView) findViewById(R.id.tv_topic_detail_add_comment);
        ImageView imageView = (ImageView) findViewById(R.id.iv_topic_detail_msg);
        this.iv_topic_detail_msg = imageView;
        if (this.module_type == 8) {
            imageView.setVisibility(8);
        }
        if (this.isProhibit.equals("0")) {
            this.tv_topic_detail_add_comment.setText("写回复");
        } else {
            this.tv_topic_detail_add_comment.setText("本帖已被锁定");
            this.tv_topic_detail_add_comment.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#B2575C" : "#F95843"));
            CommonUtil.mDoDrawable(this, this.tv_topic_detail_add_comment, SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.suohong_night : R.drawable.suohong, 0);
        }
        this.iv_topic_detail_msg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.y3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14205c.lambda$init$0(view);
            }
        });
        this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.d4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12228c.lambda$init$2(view);
            }
        });
        this.addFooterView = View.inflate(this, R.layout.activity_hideview, null);
        if (getIntent().getSerializableExtra("bean") instanceof AnalysisBean.DataBean) {
            addAnaHeader();
        } else {
            addHead();
        }
        this.swipe.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        if ((getIntent().getSerializableExtra("bean") instanceof AnalysisBean.DataBean) && "1".equals(this.mAnalysisBean.getIs_hidden())) {
            this.swipe.setEnabled(false);
            findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
            ArrayList arrayList = new ArrayList();
            arrayList.add("");
            this.listView.setAdapter((ListAdapter) new AnonymousClass1(arrayList, this, R.layout.layout_analy_item));
        } else {
            initDataList();
        }
        this.listView.setOnScrollListener(new AnonymousClass2());
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext) || SharePreferencesUtils.readBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext)) {
            this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
        }
    }

    public void initDataList() {
        this.swipe.post(new Runnable() { // from class: com.psychiatrygarden.activity.a4
            @Override // java.lang.Runnable
            public final void run() {
                this.f10983c.lambda$initDataList$3();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    public void initVideoView(String videoId) {
        this.llay_reply_bg.setVisibility(8);
        this.linevideoView.setVisibility(0);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black), 0);
        this.mActionBar.hide();
        getCourseAk(videoId, this.video_view);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            this.relReplyView.setBackgroundResource(R.color.bg_backgroud);
        } else {
            this.relReplyView.setBackgroundResource(R.color.bg_backgroud_night);
        }
    }

    public void mOnDestroy() {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        AliyunVodPlayerView aliyunVodPlayerView = this.video_view;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onDestroy();
            this.video_view = null;
        }
    }

    public void mRefasultData() {
        this.linevideoView.setVisibility(8);
        this.llay_reply_bg.setVisibility(0);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            this.relReplyView.setBackgroundResource(R.color.hor_view_color);
        } else {
            this.relReplyView.setBackgroundResource(R.color.hor_view_color_night);
        }
    }

    public void mRefasultData2(String videoId) {
        this.linevideoView.setVisibility(0);
        this.llay_reply_bg.setVisibility(8);
        getCourseAk(videoId, this.video_view);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black), 0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            this.mAdapter.getputData(data.getBundleExtra("bundleIntent"));
        } else {
            if (requestCode != 1) {
                return;
            }
            putComment(false, data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        mOnDestroy();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("shuaxinhuifu")) {
            getCommentListData();
        }
        if (str.equals(CommonParameter.Experience_comment_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext)) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg));
            } else {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_night));
            }
        }
        if (str.equals(CommonParameter.Comment_library_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, this.mContext)) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg));
            } else {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_night));
            }
        }
        if ("mCommentResult".equals(str)) {
            getCommentListData();
        } else if ("delReplyAndLoadData".equals(str)) {
            onRefresh();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            mOnDestroy();
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        if (this.isLoading) {
            this.swipe.setRefreshing(false);
            return;
        }
        this.pageNum = 1;
        this.isNoData = false;
        this.listView.removeFooterView(this.addFooterView);
        this.addFooterView.setVisibility(8);
        getCommentListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        updatePlayerViewMode();
        AliyunVodPlayerView aliyunVodPlayerView = this.video_view;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onResume();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        AliyunVodPlayerView aliyunVodPlayerView = this.video_view;
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }

    public void putComment(final boolean isTopReply, Bundle b3) {
        String str = NetworkRequestsURL.mPutComment;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        if (this.mIsSource) {
            str = NetworkRequestsURL.submitComment;
            if (this.is_replybean) {
                CommentBean.DataBean.HotBean.ReplyBean replyBean = this.replyBean;
                if (replyBean != null) {
                    ajaxParams.put("parent_id", replyBean.getId());
                    ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.replyBean.getFile_id());
                    ajaxParams.put("reply_primary_id", this.replyBean.getReply_primary_id());
                }
            } else {
                CommentBean.DataBean.HotBean hotBean = this.hotBean;
                if (hotBean != null) {
                    ajaxParams.put("parent_id", hotBean.getId());
                    ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.hotBean.getFile_id());
                    ajaxParams.put("reply_primary_id", this.hotBean.getReply_primary_id());
                }
            }
        } else {
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("module_type", this.module_type + "");
            if (this.is_replybean) {
                CommentBean.DataBean.HotBean.ReplyBean replyBean2 = this.replyBean;
                if (replyBean2 != null) {
                    ajaxParams.put("parent_id", replyBean2.getId());
                    ajaxParams.put("reply_primary_id", this.replyBean.getReply_primary_id());
                    ajaxParams.put("to_user_id", this.replyBean.getUser_id());
                    ajaxParams.put("obj_id", this.replyBean.getObj_id());
                }
            } else {
                CommentBean.DataBean.HotBean hotBean2 = this.hotBean;
                if (hotBean2 != null) {
                    ajaxParams.put("parent_id", hotBean2.getId());
                    ajaxParams.put("reply_primary_id", this.hotBean.getReply_primary_id());
                    ajaxParams.put("to_user_id", this.hotBean.getUser_id());
                    ajaxParams.put("obj_id", this.hotBean.getObj_id());
                } else {
                    ajaxParams.put("obj_id", this.mAnalysisBean.getId());
                }
            }
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
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CommentReplyActivity.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals("401")) {
                            new CusomNewDialog(CommentReplyActivity.this.mContext).setMessage(jSONObject.optString("message")).show();
                            return;
                        } else {
                            NewToast.showShort(CommentReplyActivity.this.mContext, jSONObject.optString("message"), 0).show();
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
                    SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", CommentReplyActivity.this.mContext);
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    CommentReplyActivity.this.pageNum = 1;
                    CommentReplyActivity.this.isNoData = false;
                    CommentReplyActivity.this.getCommentListData();
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    if (isTopReply) {
                        EventBus.getDefault().post(new UpdateCommentInfoEvent(CommentReplyActivity.this.hotBean.getObj_id(), "reply", true));
                    }
                    CommonUtil.showFristDialog(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void putOppose(final boolean isTopOppose, String id, final String obj_id, final String type, String content) {
        String str;
        String str2 = NetworkRequestsURL.mPutOpposeUrl;
        AjaxParams ajaxParams = new AjaxParams();
        if (this.mIsSource) {
            str2 = NetworkRequestsURL.soutceCommentOppose;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("obj_id", obj_id);
            ajaxParams.put("module_type", this.module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                if (isTopOppose) {
                    EventBus.getDefault().post(new UpdateCommentInfoEvent(obj_id, "oppose", type.equals("1")));
                }
            }
        });
        if (this.mIsSource || (str = this.viewType) == null || !str.equals("1")) {
            return;
        }
        String str3 = "[\"" + id + "\"]";
        String str4 = "[\"" + content + "\"]";
        String key = (type.equals("1") ? AliyunEvent.AddOppose : AliyunEvent.CancelOppose).getKey();
        CommonUtil.addLog(key, (type.equals("1") ? AliyunEvent.AddOppose : AliyunEvent.CancelOppose).getValue(), System.currentTimeMillis() + "", "", str3, str4, "", "2");
    }

    public void putPraise(final boolean isTopPraise, String id, final String obj_id, final String type, String content) {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        String str2 = NetworkRequestsURL.mPutPraseUrl;
        if (this.mIsSource) {
            str2 = NetworkRequestsURL.soutceCommentSupport;
            ajaxParams.put("review_id", id);
        } else {
            ajaxParams.put("user_id", UserConfig.getUserId());
            ajaxParams.put("obj_id", obj_id);
            ajaxParams.put("module_type", this.module_type + "");
            ajaxParams.put("comment_type", this.comment_type);
            ajaxParams.put("id", id);
            ajaxParams.put("type", type);
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                if (isTopPraise) {
                    EventBus.getDefault().post(new UpdateCommentInfoEvent(obj_id, "praise", type.equals("1")));
                }
            }
        });
        if (this.mIsSource || (str = this.viewType) == null || !str.equals("1")) {
            return;
        }
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.module_type + "");
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setUnit_id(ProjectApp.unit_id);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(ProjectApp.identity_id);
        questionDetailBean.setChapter_title(ProjectApp.chapter_title);
        questionDetailBean.setChapter_id(ProjectApp.chapter_id);
        questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        String str3 = "[\"" + id + "\"]";
        String str4 = "[\"" + content + "\"]";
        String key = (type.equals("1") ? AliyunEvent.AddPraise : AliyunEvent.CancelPraise).getKey();
        CommonUtil.addLog(key, (type.equals("1") ? AliyunEvent.AddPraise : AliyunEvent.CancelPraise).getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.is_replybean = getIntent().getBooleanExtra("is_replybean", true);
        this.module_type = getIntent().getIntExtra("module_type", 0);
        this.hideButtomVisiavle = Boolean.valueOf(getIntent().getBooleanExtra("hideButtomVisiavle", false));
        this.comment_type = getIntent().getStringExtra("comment_type");
        this.mIsSource = getIntent().getBooleanExtra("isSource", false);
        this.mAppId = getIntent().getStringExtra("app_id");
        this.isProhibit = getIntent().getStringExtra("isProhibit");
        this.viewType = getIntent().getStringExtra("viewType");
        if (TextUtils.isEmpty(this.isProhibit)) {
            this.isProhibit = "0";
        }
        if (getIntent().getSerializableExtra("bean") instanceof AnalysisBean.DataBean) {
            AnalysisBean.DataBean dataBean = (AnalysisBean.DataBean) getIntent().getSerializableExtra("bean");
            this.mAnalysisBean = dataBean;
            if (dataBean != null) {
                setTitle("解析");
            }
        } else if (this.is_replybean) {
            this.replyBean = (CommentBean.DataBean.HotBean.ReplyBean) getIntent().getSerializableExtra("bean");
            setTitle("回复合集");
        } else {
            this.hotBean = (CommentBean.DataBean.HotBean) getIntent().getSerializableExtra("bean");
            setTitle("回复合集");
        }
        setContentView(R.layout.activity_replycomment);
    }

    public void setHiddenData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("analysis_id", "" + this.mAnalysisBean.getId());
        ajaxParams.put("is_hidden", "1");
        ajaxParams.put("question_id", "" + this.mAnalysisBean.getQuestion_id());
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.setHiddenApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CommentReplyActivity.3
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
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        CommentReplyActivity.this.initDataList();
                        CommentReplyActivity.this.swipe.setEnabled(true);
                        if (CommentReplyActivity.this.hideButtomVisiavle.booleanValue() || ProjectApp.isSerachClick) {
                            CommentReplyActivity.this.findViewById(R.id.rl_topic_detail_bottom).setVisibility(8);
                        } else {
                            CommentReplyActivity.this.findViewById(R.id.rl_topic_detail_bottom).setVisibility(0);
                        }
                        CommentReplyActivity.this.mAnalysisBean.setIs_hidden("0");
                        CommentReplyActivity.this.analysisAdapter.setData(0, CommentReplyActivity.this.mAnalysisBean);
                        EventBus.getDefault().post(CommentReplyActivity.this.mAnalysisBean);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
