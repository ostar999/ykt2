package com.psychiatrygarden.activity.circleactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.just.agentweb.DefaultWebClient;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.circleactivity.BookCirclesListAdp;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.z3;
import com.psychiatrygarden.adapter.CircleInfoAdapter;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.ImageDataBean;
import com.psychiatrygarden.bean.LinkDataBean;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.bean.ReportParams;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.CircleInfoPicBannerAdp;
import com.psychiatrygarden.forum.NinePicAdp;
import com.psychiatrygarden.forum.PushCircleAndArticleAct;
import com.psychiatrygarden.http.HttpRequstData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.OnClickDiogListenter;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleDoThinkPopWindow;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CircleInfoMorePopWindow;
import com.psychiatrygarden.widget.CircleReportPop;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.DotIndicatorScrollView;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.MyListView;
import com.psychiatrygarden.widget.OnSelectListener;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.psychiatrygarden.widget.PopupComWindow;
import com.psychiatrygarden.widget.PopupWin7Comment;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.SelectableTextHelper;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.glideUtil.progress.GlideRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.activity.ReadBookActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CircleInfoAdapter.CircleInfoClickIml {
    private static final int REFRESH_COMPLETE = 272;
    private View addFooterView;
    public String bodysTXT;
    private MyListView bookList;
    private String break_point;
    private TextView canyuren;
    private ListView fjlistview;
    private boolean hasHotComment;
    private CircleImageView imgheader;
    public RelativeLayout include_title_layout;
    private DotIndicatorScrollView indicator_view;
    private boolean initScroll;
    private boolean isCanLoadNextPage;
    private boolean isLastPage;
    private boolean isLoading;
    private ImageView isverauth;
    private ImageView isvipimg;
    private ImageView ivTieziMore;
    public ImageView iv_topic_detail_msg;
    private ImageView jiav;
    private LinearLayout lineselect;
    private MyListView listOption;
    private TextView longPicTag1;
    private TextView longPicTag2;
    private CardView mCardviewLongPic;
    private CircleInfoBean mCircleInfoBean;
    private CircleReportPop mCircleReportPop;
    private CommAdapter<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> mCommAdapter;
    public CircleInfoAdapter mCommListAdapter;
    private DialogShare mDialogShare;
    private BookCirclesListAdp mEbookAdapter;
    private RoundedImageView mImgBigPic;
    private SubsamplingScaleImageView mImgBigPicLong;
    private RoundedImageView mImgOne;
    private RoundedImageView mImgTwo;
    private boolean mIsSdkAd;
    private PushCirclesListAdapter mListAdapter;
    private CustomEmptyView mLoadingView;
    private RelativeLayout mLyBannerView;
    private RelativeLayout mLyLoading;
    private LinearLayout mLyPicTwo;
    private NinePicAdp mPicAdapter;
    private Banner mPicBanner;
    private GridLayoutManager mPicLayoutManager;
    private RecyclerView mPicRecycler;
    public PinnedSectionListView1 mPinnedSecListView;
    private MyListView mPushCircleListView;
    private TextView mQuestiondetailsBtnCommentNum;
    private ReportParams mReportParams;
    public SmartRefreshLayout mSwipeRefreshLayout;
    private TextView mTvNoData;
    private TextView mTvPicNumber;
    private TextView okTv;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_zantong;
    private CheckedTextView remen;
    private SpannableStringBuilder spanBuilder;
    private TextView timedata;
    private TextView title;
    private TextView titleContent;
    private int totalPage;
    private LinearLayout toupiaolayout;
    private TextView tvOptionType;
    private TextView tv_oppose;
    private TextView tv_reply;
    private TextView tv_support;
    public TextView tv_topic_detail_add_comment;
    private TextView tv_topic_detail_msg;
    private TextView username;
    public WebView webview;
    private TextView wrongtxt;
    private CheckedTextView zuixin;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private int pageNum = 1;
    private String mIsProhibit = "0";
    private boolean isHUAdong = false;
    private int positionTab = 0;
    private String WEBVIEW_CONTENT = "";
    private int posInvate = 1;
    private final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 1;
    private final List<CirclrListBean.DataBean> mDataBeans = new ArrayList();
    private List<PushBookData> mBookDatas = new ArrayList();
    private final int[] voteColors = {R.color.color_vote_item_0, R.color.color_vote_item_1, R.color.color_vote_item_2, R.color.color_vote_item_3, R.color.color_vote_item_4, R.color.color_vote_item_5, R.color.color_vote_item_6, R.color.color_vote_item_7, R.color.color_vote_item_8, R.color.color_vote_item_9};
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                CircleInfoActivity.this.pageNum = 1;
                CircleInfoActivity.this.isNoMoreData = false;
                CircleInfoActivity.this.mSwipeRefreshLayout.autoRefresh();
            }
        }
    };
    private boolean mHasConfigAd = false;
    private boolean isCollection = false;
    private String mAppId = "";
    private boolean isNoMoreData = false;
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.8
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
            CircleInfoActivity.this.AlertToast("用户取消分享");
            if (CircleInfoActivity.this.mDialogShare != null) {
                CircleInfoActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            CircleInfoActivity.this.AlertToast("未检测到QQ/微信应用或请安装正式版QQ/微信分享！");
            if (CircleInfoActivity.this.mDialogShare != null) {
                CircleInfoActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            CircleInfoActivity.this.AlertToast("分享成功");
            CircleInfoActivity.this.shareArticleSuccess();
            if (CircleInfoActivity.this.mDialogShare != null) {
                CircleInfoActivity.this.mDialogShare.dismiss();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity$13, reason: invalid class name */
    public class AnonymousClass13 extends AjaxCallBack<String> {

        /* renamed from: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity$13$6, reason: invalid class name */
        public class AnonymousClass6 extends CommAdapter<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> {
            public AnonymousClass6(List mData, Context mcontext, int layoutId) {
                super(mData, mcontext, layoutId);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$0(CheckBox checkBox, View view) {
                boolean zAnyMatch = CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().stream().anyMatch(new Predicate() { // from class: com.psychiatrygarden.activity.circleactivity.s0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) obj).isTrue();
                    }
                });
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getChoice().equals("1") && zAnyMatch) {
                    checkBox.setChecked(false);
                }
                int iIntValue = ((Integer) view.getTag()).intValue();
                for (int i2 = 0; i2 < CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().size(); i2++) {
                    CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean = CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i2);
                    if (i2 == iIntValue) {
                        optionsBean.setTrue(checkBox.isChecked());
                    }
                }
                CircleInfoActivity.this.okTv.setVisibility(CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().stream().anyMatch(new Predicate() { // from class: com.psychiatrygarden.activity.circleactivity.s0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) obj).isTrue();
                    }
                }) ? 0 : 8);
            }

            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            @RequiresApi(api = 24)
            public void convert(ViewHolder vHolder, CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean, int position) throws NumberFormatException {
                TextView textView = (TextView) vHolder.getView(R.id.tv_background);
                TextView textView2 = (TextView) vHolder.getView(R.id.toupiaocontent);
                final CheckBox checkBox = (CheckBox) vHolder.getView(R.id.check);
                LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.lineduihao);
                TextView textView3 = (TextView) vHolder.getView(R.id.baifenbi);
                TextView textView4 = (TextView) vHolder.getView(R.id.duigou);
                checkBox.setVisibility(CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getIs_vote().equals("0") ? 0 : 8);
                textView2.setText(optionsBean.getOption());
                checkBox.setChecked(optionsBean.isTrue());
                checkBox.setTag(Integer.valueOf(position));
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getChoice().equals("1")) {
                    TypedValue typedValue = new TypedValue();
                    CircleInfoActivity.this.getTheme().resolveAttribute(R.attr.checkbox_style_single, typedValue, true);
                    checkBox.setBackgroundResource(typedValue.resourceId);
                } else {
                    TypedValue typedValue2 = new TypedValue();
                    CircleInfoActivity.this.getTheme().resolveAttribute(R.attr.checkbox_style, typedValue2, true);
                    checkBox.setBackgroundResource(typedValue2.resourceId);
                }
                checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.t0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11593c.lambda$convert$0(checkBox, view);
                    }
                });
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getIs_vote().equals("0")) {
                    linearLayout.setVisibility(8);
                    return;
                }
                String[] strArrSplit = CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getVote_option().split(",");
                textView4.setVisibility(8);
                for (String str : strArrSplit) {
                    if (optionsBean.getId().equals(str)) {
                        textView4.setVisibility(0);
                    }
                }
                linearLayout.setVisibility(0);
                int i2 = Integer.parseInt(optionsBean.getVote_nums());
                int i3 = 0;
                for (int i4 = 0; i4 < CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().size(); i4++) {
                    i3 += Integer.parseInt(CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i4).getVote_nums());
                }
                float f2 = i3 == 0 ? i2 * 100.0f : (i2 * 100.0f) / i3;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.width = (int) ((CircleInfoActivity.this.getResources().getDisplayMetrics().widthPixels * f2) / 100.0f);
                textView.setLayoutParams(layoutParams);
                try {
                    if (position > CircleInfoActivity.this.voteColors.length - 1) {
                        textView.setBackgroundResource(CircleInfoActivity.this.voteColors[new Random().nextInt(CircleInfoActivity.this.voteColors.length)]);
                    } else {
                        textView.setBackgroundResource(CircleInfoActivity.this.voteColors[position]);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    textView.setBackgroundResource(CircleInfoActivity.this.voteColors[0]);
                }
                textView3.setText(CircleInfoActivity.this.replaceValue(new DecimalFormat("#0.0").format(f2)) + "%");
            }
        }

        public AnonymousClass13() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(View view) {
            ToastUtil.shortToast(CircleInfoActivity.this.mContext, "已认证");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(String str, String str2, String str3) {
            Bundle bundle = new Bundle();
            bundle.putString("content", str);
            bundle.putInt("result", 1);
            bundle.putString("b_img", str2);
            bundle.putString("s_img", str3);
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                CircleInfoActivity.this.putComment(bundle);
                return;
            }
            Intent intent = new Intent(CircleInfoActivity.this.mContext, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle);
            CircleInfoActivity.this.startActivityForResult(intent, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$2(View view) {
            if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_prohibit().equals("1")) {
                ToastUtil.shortToast(CircleInfoActivity.this.mContext, "本帖已被锁定，不支持回帖");
            } else {
                new DialogInput(CircleInfoActivity.this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.r0
                    @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                    public final void onclickStringBack(String str, String str2, String str3) {
                        this.f11585a.lambda$onSuccess$1(str, str2, str3);
                    }
                }, false).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$3(int i2) {
            Log.e("pay_success", "支付成功回调");
            CircleInfoActivity.this.mCircleInfoBean.getData().getEnclosure().get(i2).setIs_rights("1");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$4(AdapterView adapterView, View view, final int i2, long j2) {
            if (!CircleInfoActivity.this.mCircleInfoBean.getData().getEnclosure().get(i2).getIs_rights().equals("1")) {
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("enclosure_id", CircleInfoActivity.this.mCircleInfoBean.getData().getEnclosure().get(i2).getId());
                MemInterface.getInstance().getFilePermission(CircleInfoActivity.this, ajaxParams);
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.circleactivity.m0
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        this.f11563a.lambda$onSuccess$3(i2);
                    }
                });
                return;
            }
            if (!CommonUtil.hasRequiredPermissionsWriteStorage(CircleInfoActivity.this)) {
                new XPopup.Builder(CircleInfoActivity.this).asCustom(new RequestMediaPermissionPop(CircleInfoActivity.this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.13.2
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public void onConfirm() {
                        ActivityCompat.requestPermissions(CircleInfoActivity.this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                })).show();
            } else {
                CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                InformationPreviewAct.newIntent(circleInfoActivity, circleInfoActivity.mCircleInfoBean.getData().getEnclosure().get(i2).getId(), CircleInfoActivity.this.mCircleInfoBean.getData().getEnclosure().get(i2).getUrl(), false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$5(AdapterView adapterView, View view, int i2, long j2) {
            if (CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getIs_vote().equals("0")) {
                CircleInfoActivity.this.okTv.setVisibility(0);
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getChoice().equals("1")) {
                    for (int i3 = 0; i3 < CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().size(); i3++) {
                        CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i3).setTrue(false);
                    }
                    CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).setTrue(true);
                } else {
                    CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).setTrue(!CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).isTrue());
                }
                CircleInfoActivity.this.mCommAdapter.notifyDataSetChanged();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CircleInfoActivity.this.mSwipeRefreshLayout.finishRefresh(false);
            CircleInfoActivity.this.wrongtxt.setVisibility(0);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String s2) throws NumberFormatException {
            super.onSuccess((AnonymousClass13) s2);
            CircleInfoActivity.this.mLoadingView.setVisibility(8);
            CircleInfoActivity.this.mLoadingView.stopAnim();
            Log.d(CircleInfoActivity.this.TAG, "onSuccess: " + s2);
            try {
                CircleInfoActivity.this.wrongtxt.setVisibility(8);
                CircleInfoActivity.this.mCircleInfoBean = (CircleInfoBean) new Gson().fromJson(s2, CircleInfoBean.class);
                if (!CircleInfoActivity.this.mCircleInfoBean.getCode().equals("200")) {
                    if (CircleInfoActivity.this.mCircleInfoBean.getCode().equals("401")) {
                        CircleInfoActivity.this.startActivity(new Intent(CircleInfoActivity.this, (Class<?>) MemberCenterActivity.class));
                        CircleInfoActivity.this.finish();
                        return;
                    } else {
                        CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                        circleInfoActivity.AlertToast(circleInfoActivity.mCircleInfoBean.getMessage());
                        return;
                    }
                }
                CircleInfoActivity circleInfoActivity2 = CircleInfoActivity.this;
                circleInfoActivity2.getCollectData(circleInfoActivity2.mCircleInfoBean.getData().getIs_collection());
                CircleInfoActivity.this.title.setText(CircleInfoActivity.this.mCircleInfoBean.getData().getTitle());
                if (TextUtils.isEmpty(CircleInfoActivity.this.mCircleInfoBean.getData().getAvatar() + "")) {
                    CircleInfoActivity.this.imgheader.setImageResource(R.drawable.empty_photo);
                } else {
                    GlideUtils.loadImage(CircleInfoActivity.this.imgheader.getContext(), CircleInfoActivity.this.mCircleInfoBean.getData().getAvatar(), CircleInfoActivity.this.imgheader);
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getPush_article_list() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getPush_article_list().size() <= 0) {
                    CircleInfoActivity.this.mPushCircleListView.setVisibility(8);
                } else {
                    CircleInfoActivity.this.mPushCircleListView.setVisibility(0);
                    boolean z2 = CircleInfoActivity.this.mDataBeans.size() > 0;
                    CircleInfoActivity.this.mDataBeans.clear();
                    CircleInfoActivity.this.mDataBeans.addAll(CircleInfoActivity.this.mCircleInfoBean.getData().getPush_article_list());
                    if (z2) {
                        CircleInfoActivity.this.mListAdapter = new PushCirclesListAdapter(CircleInfoActivity.this.mDataBeans, false, true);
                        CircleInfoActivity.this.mPushCircleListView.setAdapter((ListAdapter) CircleInfoActivity.this.mListAdapter);
                    } else {
                        CircleInfoActivity.this.mListAdapter.notifyDataSetChanged();
                    }
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getEbook() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getEbook().size() <= 0) {
                    CircleInfoActivity.this.bookList.setVisibility(8);
                } else {
                    CircleInfoActivity.this.bookList.setVisibility(0);
                    CircleInfoActivity.this.mBookDatas.clear();
                    CircleInfoActivity.this.mBookDatas.addAll(CircleInfoActivity.this.mCircleInfoBean.getData().getEbook());
                    CircleInfoActivity.this.mEbookAdapter.notifyDataSetChanged();
                }
                CircleInfoActivity.this.username.setText(CircleInfoActivity.this.mCircleInfoBean.getData().getNickname());
                CircleInfoActivity.this.username.setTextColor(Color.parseColor(CircleInfoActivity.this.mCircleInfoBean.getData().getUser_identity_color()));
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getDisplayed().equals("1")) {
                    CircleInfoActivity.this.ivTieziMore.setVisibility(8);
                    ((BaseActivity) CircleInfoActivity.this).iv_actionbar_right.setVisibility(0);
                } else {
                    CircleInfoActivity.this.ivTieziMore.setVisibility(8);
                    ((BaseActivity) CircleInfoActivity.this).iv_actionbar_right.setVisibility(8);
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getUser_identity().equals("1") || CircleInfoActivity.this.mCircleInfoBean.getData().getIs_authentication().equals("1")) {
                    CircleInfoActivity.this.jiav.setVisibility(8);
                    CircleInfoActivity.this.isverauth.setVisibility(0);
                    CircleInfoActivity.this.isverauth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.n0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11568c.lambda$onSuccess$0(view);
                        }
                    });
                } else {
                    CircleInfoActivity.this.jiav.setVisibility(8);
                    CircleInfoActivity.this.isverauth.setVisibility(8);
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getAuthor_is_svip().equals("1")) {
                    CircleInfoActivity.this.isvipimg.setVisibility(0);
                    CircleInfoActivity.this.isvipimg.setImageResource(R.drawable.svip333img);
                } else if (CircleInfoActivity.this.mCircleInfoBean.getData().getAuthor_is_vip().equals("1")) {
                    CircleInfoActivity.this.isvipimg.setVisibility(0);
                    CircleInfoActivity.this.isvipimg.setImageResource(R.drawable.vipimg);
                } else {
                    CircleInfoActivity.this.isvipimg.setVisibility(8);
                }
                CircleInfoActivity circleInfoActivity3 = CircleInfoActivity.this;
                circleInfoActivity3.mIsProhibit = circleInfoActivity3.mCircleInfoBean.getData().getIs_prohibit();
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_prohibit().equals("0")) {
                    CircleInfoActivity.this.tv_topic_detail_add_comment.setText("发表评论");
                    CircleInfoActivity.this.tv_topic_detail_add_comment.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleInfoActivity.this) == 1 ? "#575F79" : "#909499"));
                    int i2 = SkinManager.getCurrentSkinType(CircleInfoActivity.this) == 1 ? R.mipmap.icon_write_comment_night : R.mipmap.icon_write_comment;
                    CircleInfoActivity circleInfoActivity4 = CircleInfoActivity.this;
                    CommonUtil.mDoDrawable(circleInfoActivity4, circleInfoActivity4.tv_topic_detail_add_comment, i2, 0);
                } else {
                    CircleInfoActivity.this.tv_topic_detail_add_comment.setText("本帖已被锁定");
                    CircleInfoActivity.this.tv_topic_detail_add_comment.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleInfoActivity.this) == 1 ? "#B2575C" : "#F95843"));
                    int i3 = SkinManager.getCurrentSkinType(CircleInfoActivity.this) == 1 ? R.drawable.suohong_night : R.drawable.suohong;
                    CircleInfoActivity circleInfoActivity5 = CircleInfoActivity.this;
                    CommonUtil.mDoDrawable(circleInfoActivity5, circleInfoActivity5.tv_topic_detail_add_comment, i3, 0);
                }
                CircleInfoActivity.this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.o0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11572c.lambda$onSuccess$2(view);
                    }
                });
                CircleInfoActivity.this.timedata.setText(CircleInfoActivity.this.mCircleInfoBean.getData().getSchool_name() + " " + CircleInfoActivity.this.mCircleInfoBean.getData().getCtime());
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_support().equals("1")) {
                    CircleInfoActivity.this.tv_support.setText("已赞同(" + CircleInfoActivity.this.mCircleInfoBean.getData().getSupport_count() + ")");
                } else {
                    CircleInfoActivity.this.tv_support.setText("赞同(" + CircleInfoActivity.this.mCircleInfoBean.getData().getSupport_count() + ")");
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_opposition().equals("1")) {
                    CircleInfoActivity.this.tv_oppose.setText("已反对(" + CircleInfoActivity.this.mCircleInfoBean.getData().getOpposition_count() + ")");
                } else {
                    CircleInfoActivity.this.tv_oppose.setText("反对(" + CircleInfoActivity.this.mCircleInfoBean.getData().getOpposition_count() + ")");
                }
                CircleInfoActivity.this.tv_reply.setText("举报");
                try {
                    int i4 = Integer.parseInt(CircleInfoActivity.this.mCircleInfoBean.getData().getComment_count());
                    if (i4 > 10000) {
                        CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText((i4 / 10000) + StrPool.DOT + ((i4 % 10000) / 1000) + "万");
                    } else {
                        CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText(i4 + "");
                    }
                    CircleInfoActivity.this.totalPage = i4 / 10;
                    if (i4 % 10 > 0) {
                        CircleInfoActivity.access$3712(CircleInfoActivity.this, 1);
                    }
                    LogUtils.d("total_page", String.valueOf(CircleInfoActivity.this.totalPage));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText(CircleInfoActivity.this.mCircleInfoBean.getData().getComment_count() + "");
                }
                CircleInfoActivity circleInfoActivity6 = CircleInfoActivity.this;
                circleInfoActivity6.getColorTv(Integer.parseInt(circleInfoActivity6.mCircleInfoBean.getData().getSupport_count()), Integer.parseInt(CircleInfoActivity.this.mCircleInfoBean.getData().getOpposition_count()));
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_enclosure().equals("1")) {
                    CircleInfoActivity.this.fjlistview.setVisibility(0);
                    CircleInfoActivity.this.fjlistview.setAdapter((ListAdapter) new CommAdapter<CircleInfoBean.DataBean.Enclosure>(CircleInfoActivity.this.mCircleInfoBean.getData().getEnclosure(), CircleInfoActivity.this.mContext, R.layout.layout_fujian_item) { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.13.1
                        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                        public void convert(ViewHolder vHolder, CircleInfoBean.DataBean.Enclosure enclosure, int position) {
                            ImageView imageView = (ImageView) vHolder.getView(R.id.img);
                            TextView textView = (TextView) vHolder.getView(R.id.name);
                            GlideApp.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(enclosure.getIcon())).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.app_icon)).into(imageView);
                            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                                textView.setText(enclosure.getTitle() + enclosure.getType_name() + "（" + enclosure.getSize() + "）");
                                return;
                            }
                            if (enclosure.getView_num() == null || "".equals(enclosure.getView_num())) {
                                textView.setText(enclosure.getTitle() + enclosure.getType_name() + "（" + enclosure.getSize() + "）");
                                return;
                            }
                            try {
                                String str = enclosure.getTitle() + enclosure.getType_name() + "（" + enclosure.getSize() + "）";
                                String str2 = enclosure.getTitle() + enclosure.getType_name() + "（" + enclosure.getSize() + "）" + enclosure.getView_num();
                                textView.setText(CharacterParser.getSpannableText(str2, str.length(), str2.length(), "#B2575C"));
                            } catch (Exception unused) {
                            }
                        }
                    });
                    CircleInfoActivity.this.fjlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.p0
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view, int i5, long j2) {
                            this.f11576c.lambda$onSuccess$4(adapterView, view, i5, j2);
                        }
                    });
                } else {
                    CircleInfoActivity.this.fjlistview.setVisibility(8);
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getContent() != null) {
                    String content = CircleInfoActivity.this.mCircleInfoBean.getData().getContent();
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().size() <= 0) {
                        Matcher matcher = Pattern.compile("\\[image\\]").matcher(content);
                        while (matcher.find()) {
                            content = content.replaceFirst("\\[image\\]", "");
                        }
                    } else {
                        Matcher matcher2 = Pattern.compile("\\[image\\]").matcher(content);
                        int i5 = 0;
                        while (matcher2.find()) {
                            content = content.replaceFirst("\\[image\\]", "<p><img id='showimg' onclick='prictaction(this)' style='width: 100%; height: auto;' src='" + CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().get(i5).getImage() + "' onerror=\"this.src='file:///android_res/drawable/imgplacehodel_image.png';this.onerror=null\" /></p>");
                            i5++;
                        }
                    }
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getLink() != null && CircleInfoActivity.this.mCircleInfoBean.getData().getLink().size() > 0) {
                        Matcher matcher3 = Pattern.compile("\\[link\\]").matcher(content);
                        int i6 = 0;
                        while (matcher3.find()) {
                            content = SkinManager.getCurrentSkinType(CircleInfoActivity.this) == 1 ? content.replaceFirst("\\[link\\]", "<a  style=\"color:#0062BA;text-decoration:none;background:url('file:///android_res/drawable/lianjietu_night.png');background-size:20px 20px;background-repeat:no-repeat;padding-left:22px;box-sizing:border-box;background-position:0% 50%;\" onclick='aOnClick(this)' href='javascript:void(0)'  url='" + CircleInfoActivity.this.mCircleInfoBean.getData().getLink().get(i6).getUrl() + "'>" + CircleInfoActivity.this.mCircleInfoBean.getData().getLink().get(i6).getTitle() + "</a>") : content.replaceFirst("\\[link\\]", "<a  style=\"color:#0062BA;text-decoration:none;background:url('file:///android_res/drawable/lianjietu.png');background-size:20px 20px;background-repeat:no-repeat;padding-left:22px;box-sizing:border-box;background-position:0% 50%;\" onclick='aOnClick(this)' href='javascript:void(0)'  url='" + CircleInfoActivity.this.mCircleInfoBean.getData().getLink().get(i6).getUrl() + "'>" + CircleInfoActivity.this.mCircleInfoBean.getData().getLink().get(i6).getTitle() + "</a>");
                            i6++;
                        }
                    }
                    String str = String.format(CircleInfoActivity.this.WEBVIEW_CONTENT, content);
                    SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleInfoActivity.this, "").matches("^[0-9]+$");
                    CircleInfoActivity circleInfoActivity7 = CircleInfoActivity.this;
                    circleInfoActivity7.bodysTXT = content;
                    WebView webView = circleInfoActivity7.webview;
                    if (webView != null) {
                        webView.loadDataWithBaseURL(null, str.replaceAll("\n", "<br/>"), "text/html", "utf-8", null);
                    }
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getImg().isEmpty()) {
                    CircleInfoActivity.this.mLyBannerView.setVisibility(8);
                    CircleInfoActivity.this.mPicBanner.setVisibility(8);
                } else if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg_position().equals("2")) {
                    CircleInfoActivity.this.mLyBannerView.setVisibility(0);
                    CircleInfoActivity.this.mTvPicNumber.setVisibility(CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size() > 1 ? 0 : 8);
                    CircleInfoActivity circleInfoActivity8 = CircleInfoActivity.this;
                    circleInfoActivity8.initPicBanner(circleInfoActivity8.mCircleInfoBean.getData().getImg());
                } else {
                    CircleInfoActivity.this.mLyBannerView.setVisibility(8);
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size() == 1) {
                        CircleInfoActivity.this.mPicRecycler.setVisibility(8);
                        CircleInfoActivity.this.mLyPicTwo.setVisibility(8);
                        CircleInfoActivity.this.mImgBigPicLong.setVisibility(0);
                        CircleInfoActivity.this.mCardviewLongPic.setVisibility(0);
                        CircleInfoActivity.this.mImgBigPic.setVisibility(8);
                        Glide.with(CircleInfoActivity.this.mImgBigPicLong).load(CircleInfoActivity.this.mCircleInfoBean.getData().getImg().get(0).getImage()).placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).downloadOnly(new SimpleTarget<File>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.13.3
                            @Override // com.bumptech.glide.request.target.Target
                            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                                onResourceReady((File) resource, (Transition<? super File>) transition);
                            }

                            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                CircleInfoActivity.this.mImgBigPicLong.setImage(ImageSource.uri(Uri.fromFile(resource)));
                            }
                        });
                    } else if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size() == 2) {
                        CircleInfoActivity.this.mImgBigPic.setVisibility(8);
                        CircleInfoActivity.this.mImgBigPicLong.setVisibility(8);
                        CircleInfoActivity.this.mCardviewLongPic.setVisibility(8);
                        CircleInfoActivity.this.mLyPicTwo.setVisibility(0);
                        CircleInfoActivity.this.mPicRecycler.setVisibility(8);
                        GlideApp.with(CircleInfoActivity.this.mContext).asBitmap().load((Object) GlideUtils.generateUrl(CircleInfoActivity.this.mCircleInfoBean.getData().getImg().get(0).getImage())).placeholder(R.drawable.imgplacehodel_image).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.13.4
                            @Override // com.bumptech.glide.request.target.Target
                            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull @NotNull Object resource, @Nullable Transition transition) {
                                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                            }

                            public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if ((resource.getHeight() * 1.0f) / resource.getWidth() >= 3.0f) {
                                    CircleInfoActivity.this.longPicTag1.setVisibility(0);
                                    CircleInfoActivity.this.mImgOne.setTag(Boolean.TRUE);
                                } else {
                                    CircleInfoActivity.this.longPicTag1.setVisibility(8);
                                }
                                CircleInfoActivity.this.mImgOne.setImageBitmap(resource);
                            }
                        });
                        GlideApp.with(CircleInfoActivity.this.mContext).asBitmap().load((Object) GlideUtils.generateUrl(CircleInfoActivity.this.mCircleInfoBean.getData().getImg().get(1).getImage())).placeholder(R.drawable.imgplacehodel_image).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.13.5
                            @Override // com.bumptech.glide.request.target.Target
                            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull @NotNull Object resource, @Nullable Transition transition) {
                                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                            }

                            public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if ((resource.getHeight() * 1.0f) / resource.getWidth() >= 3.0f) {
                                    CircleInfoActivity.this.longPicTag2.setVisibility(0);
                                    CircleInfoActivity.this.mImgTwo.setTag(Boolean.TRUE);
                                } else {
                                    CircleInfoActivity.this.longPicTag2.setVisibility(8);
                                }
                                CircleInfoActivity.this.mImgTwo.setImageBitmap(resource);
                            }
                        });
                    } else {
                        CircleInfoActivity.this.mImgBigPic.setVisibility(8);
                        CircleInfoActivity.this.mImgBigPicLong.setVisibility(8);
                        CircleInfoActivity.this.mCardviewLongPic.setVisibility(8);
                        CircleInfoActivity.this.mLyPicTwo.setVisibility(8);
                        CircleInfoActivity.this.mPicRecycler.setVisibility(0);
                        if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size() == 4) {
                            CircleInfoActivity circleInfoActivity9 = CircleInfoActivity.this;
                            circleInfoActivity9.mPicLayoutManager = new GridLayoutManager(circleInfoActivity9, 2);
                            CircleInfoActivity.this.mPicAdapter.setSpanCount(2);
                        } else {
                            CircleInfoActivity circleInfoActivity10 = CircleInfoActivity.this;
                            circleInfoActivity10.mPicLayoutManager = new GridLayoutManager(circleInfoActivity10, 3);
                            CircleInfoActivity.this.mPicAdapter.setSpanCount(3);
                        }
                        CircleInfoActivity.this.mPicAdapter.setShowLongPicTag(true);
                        CircleInfoActivity.this.mPicRecycler.setLayoutManager(CircleInfoActivity.this.mPicLayoutManager);
                        CircleInfoActivity.this.mPicRecycler.setVisibility(0);
                        ArrayList arrayList = new ArrayList();
                        List<CircleInfoBean.DataBean.ImgBean> img = CircleInfoActivity.this.mCircleInfoBean.getData().getImg();
                        for (int i7 = 0; i7 < img.size(); i7++) {
                            CircleInfoBean.DataBean.ImgBean imgBean = img.get(i7);
                            arrayList.add(imgBean.getImage());
                            String height = imgBean.getHeight();
                            String width = imgBean.getWidth();
                            if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(width) && height.matches(RegexPool.NUMBERS) && width.matches(RegexPool.NUMBERS) && (Integer.parseInt(height) * 1.0f) / Integer.parseInt(width) >= 3.0f) {
                                CircleInfoActivity.this.mPicAdapter.setIsLongPic(i7, true);
                            }
                        }
                        CircleInfoActivity.this.mPicAdapter.setList(arrayList);
                    }
                }
                if (CircleInfoActivity.this.mCircleInfoBean.getData().getOptions() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions().size() <= 0) {
                    CircleInfoActivity.this.toupiaolayout.setVisibility(8);
                } else {
                    CircleInfoActivity.this.toupiaolayout.setVisibility(0);
                    CircleInfoActivity.this.canyuren.setText(CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getUser_count() + "人参与了投票");
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getChoice().equals("1")) {
                        CircleInfoActivity.this.tvOptionType.setText("单选");
                    } else {
                        CircleInfoActivity.this.tvOptionType.setText("多选");
                    }
                    CircleInfoActivity.this.mCommAdapter = new AnonymousClass6(CircleInfoActivity.this.mCircleInfoBean.getData().getOptions().getOptions(), CircleInfoActivity.this.mContext, R.layout.activity_weitoupiaoitem);
                    CircleInfoActivity.this.listOption.setAdapter((ListAdapter) CircleInfoActivity.this.mCommAdapter);
                    CircleInfoActivity.this.listOption.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.q0
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view, int i8, long j2) {
                            this.f11580c.lambda$onSuccess$5(adapterView, view, i8, j2);
                        }
                    });
                }
                CircleInfoActivity.this.getCommentListData(false, 0);
            } catch (Exception e3) {
                e3.printStackTrace();
                CircleInfoActivity.this.wrongtxt.setVisibility(0);
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity$17, reason: invalid class name */
    public class AnonymousClass17 extends AjaxCallBack<String> {
        final /* synthetic */ boolean val$isAddComment;

        public AnonymousClass17(final boolean val$isAddComment) {
            this.val$isAddComment = val$isAddComment;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            CircleInfoActivity.this.updateLikeIconStatus("1");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (CircleInfoActivity.this.pageNum == 1) {
                CircleInfoActivity.this.commlist.clear();
                CircleInfoActivity.this.time_lines.clear();
                if (CircleInfoActivity.this.commlist.size() > 0) {
                    CircleInfoActivity.this.AlertToast("请求服务器失败");
                    CircleInfoActivity.this.addFooterView.setVisibility(8);
                    return;
                }
                CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                hotBean.setType(1);
                hotBean.setName("最新评论(0)");
                CircleInfoActivity.this.commlist.add(hotBean);
                CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                hotBean2.setId("");
                hotBean2.setContent("暂无评论");
                CircleInfoActivity.this.commlist.add(hotBean2);
                CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                CircleInfoActivity circleInfoActivity2 = CircleInfoActivity.this;
                Context context = circleInfoActivity2.mContext;
                List list = circleInfoActivity2.commlist;
                CircleInfoActivity circleInfoActivity3 = CircleInfoActivity.this;
                circleInfoActivity.mCommListAdapter = new CircleInfoAdapter(context, list, circleInfoActivity3, circleInfoActivity3.mIsProhibit, CircleInfoActivity.this.mAppId);
                CircleInfoActivity circleInfoActivity4 = CircleInfoActivity.this;
                circleInfoActivity4.mPinnedSecListView.setAdapter((ListAdapter) circleInfoActivity4.mCommListAdapter);
            } else {
                CircleInfoActivity.this.addFooterView.setVisibility(8);
            }
            CircleInfoActivity.this.mSwipeRefreshLayout.finishRefresh(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        /* JADX WARN: Not initialized variable reg: 21, insn: 0x0367: MOVE (r4 I:??[OBJECT, ARRAY]) = (r21 I:??[OBJECT, ARRAY]), block:B:64:0x0367 */
        /* JADX WARN: Removed duplicated region for block: B:77:0x040c  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0421 A[EDGE_INSN: B:85:0x0421->B:80:0x0421 BREAK  A[LOOP:1: B:75:0x0406->B:87:?], SYNTHETIC] */
        @Override // net.tsz.afinal.http.AjaxCallBack
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSuccess(java.lang.String r23) {
            /*
                Method dump skipped, instructions count: 1064
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.AnonymousClass17.onSuccess(java.lang.String):void");
        }
    }

    /* renamed from: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity$3, reason: invalid class name */
    public class AnonymousClass3 implements AbsListView.OnScrollListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
            circleInfoActivity.mPinnedSecListView.setSelection(circleInfoActivity.posInvate);
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (CircleInfoActivity.this.positionTab <= 0) {
                if (firstVisibleItem >= 1) {
                    CircleInfoActivity.this.isSelect(false, true);
                    return;
                } else {
                    CircleInfoActivity.this.isSelect(true, false);
                    return;
                }
            }
            if (firstVisibleItem > CircleInfoActivity.this.positionTab + 1) {
                CircleInfoActivity.this.isSelect(false, true);
                CircleInfoActivity.this.lineselect.setVisibility(0);
            } else if (firstVisibleItem < 1 || firstVisibleItem > CircleInfoActivity.this.positionTab + 1) {
                CircleInfoActivity.this.lineselect.setVisibility(8);
                CircleInfoActivity.this.isSelect(false, false);
            } else {
                CircleInfoActivity.this.lineselect.setVisibility(0);
                CircleInfoActivity.this.isSelect(true, false);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                if (CircleInfoActivity.this.isHUAdong) {
                    CircleInfoActivity.this.isHUAdong = false;
                    CircleInfoActivity.this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.v0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11605c.lambda$onScrollStateChanged$0();
                        }
                    }, 500L);
                    return;
                }
                int size = CircleInfoActivity.this.commlist.size() - view.getLastVisiblePosition();
                LogUtils.e("dis_item_count", "差值：" + size);
                if (size > 6 || CircleInfoActivity.this.isCanLoadNextPage || CircleInfoActivity.this.isNoMoreData) {
                    return;
                }
                CircleInfoActivity.this.isCanLoadNextPage = true;
                CircleInfoActivity.access$008(CircleInfoActivity.this);
                CircleInfoActivity.this.getCommentListData(false, 0);
            }
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            CircleInfoActivity.this.webview.getSettings().setBlockNetworkImage(false);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    public class PrictData {
        Context context;

        public PrictData(Context context) {
            this.context = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getPrictData$0(List list, int i2) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(CircleInfoActivity.this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.setImageUrls(list).setSrcView(null, i2).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBodyClick$1() {
            new XPopup.Builder(this.context).autoDismiss(Boolean.FALSE).asCustom(new PopupWin7Comment(this.context, CircleInfoActivity.this.mCircleInfoBean.getData().getContent(), CircleInfoActivity.this.mCircleInfoBean.getData().getNickname(), true, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.PrictData.1
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public void onclickIntBack(int i2) {
                    if (i2 != 3 || CircleInfoActivity.this.mCircleInfoBean == null || CircleInfoActivity.this.mCircleInfoBean.getData() == null) {
                        return;
                    }
                    String strReplaceAll = CircleInfoActivity.this.mCircleInfoBean.getData().getContent().replaceAll("\\[image\\]", "");
                    Log.d("213213213", "onSelect: " + strReplaceAll);
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getLink() != null && CircleInfoActivity.this.mCircleInfoBean.getData().getLink().size() > 0) {
                        Matcher matcher = Pattern.compile("\\[link\\]").matcher(strReplaceAll);
                        int i3 = 0;
                        while (matcher.find()) {
                            strReplaceAll = strReplaceAll.replaceFirst("\\[link\\]", CircleInfoActivity.this.mCircleInfoBean.getData().getLink().get(i3).getTitle());
                            i3++;
                        }
                    }
                    if (TextUtils.isEmpty(strReplaceAll)) {
                        ToastUtil.shortToast(CircleInfoActivity.this, "内容为空，复制不成功");
                        return;
                    }
                    try {
                        ((ClipboardManager) PrictData.this.context.getSystemService("clipboard")).setText(strReplaceAll.replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
                        ToastUtil.shortToast(CircleInfoActivity.this, "复制成功");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            })).show();
        }

        @JavascriptInterface
        public void getCoument(String url) {
            try {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                if (TextUtils.isEmpty(Uri.parse(url).getScheme())) {
                    url = DefaultWebClient.HTTP_SCHEME + url;
                }
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction("android.intent.action.VIEW");
                CircleInfoActivity.this.mContext.startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @JavascriptInterface
        public void getPrictData(final int postion) {
            int i2;
            if (CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().size() <= 0 || postion >= CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().size()) {
                return;
            }
            final ArrayList arrayList = new ArrayList();
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleInfoActivity.this, "");
            for (int i3 = 0; i3 < CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().size(); i3++) {
                String image = CircleInfoActivity.this.mCircleInfoBean.getData().getRich_img().get(i3).getImage();
                if (strConfig.matches("^[0-9]+$") && (i2 = Integer.parseInt(strConfig)) >= 30 && i2 <= 35) {
                    image = image.replace("?x-oss-process=style/imgSet", "");
                }
                arrayList.add(image);
            }
            CircleInfoActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.w0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11609c.lambda$getPrictData$0(arrayList, postion);
                }
            });
        }

        @JavascriptInterface
        public void onBodyClick() {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.x0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11615c.lambda$onBodyClick$1();
                }
            });
        }
    }

    public static /* synthetic */ int access$008(CircleInfoActivity circleInfoActivity) {
        int i2 = circleInfoActivity.pageNum;
        circleInfoActivity.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$3712(CircleInfoActivity circleInfoActivity, int i2) {
        int i3 = circleInfoActivity.totalPage + i2;
        circleInfoActivity.totalPage = i3;
        return i3;
    }

    public static /* synthetic */ int access$912(CircleInfoActivity circleInfoActivity, int i2) {
        int i3 = circleInfoActivity.posInvate + i2;
        circleInfoActivity.posInvate = i3;
        return i3;
    }

    private void bannedDialog(final View view) {
        new PopupComWindow(this, "请选择封禁类型", "", 1, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.circleactivity.k
            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
            public final void onDiogClick(String str, PopupComWindow popupComWindow) {
                this.f11552a.lambda$bannedDialog$15(view, str, popupComWindow);
            }
        }).showPopUp(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeNum(final String support_num, final String opposition_num) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", "" + getIntent().getExtras().getString("article_id"));
        ajaxParams.put("support_num", "" + support_num);
        ajaxParams.put("opposition_num", "" + opposition_num);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this, 16 == getIntent().getExtras().getInt("module_type", 12) ? NetworkRequestsURL.getforumeditPraiseApi : NetworkRequestsURL.editorLikesApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ToastUtil.shortToast(CircleInfoActivity.this, "修改失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        ToastUtil.shortToast(CircleInfoActivity.this, jSONObject.optString("message"));
                        return;
                    }
                    CircleInfoActivity.this.getColorTv(Integer.parseInt(support_num), Integer.parseInt(opposition_num));
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_support().equals("1")) {
                        CircleInfoActivity.this.tv_support.setText("已赞同(" + support_num + ")");
                    } else {
                        CircleInfoActivity.this.tv_support.setText("赞同(" + support_num + ")");
                    }
                    if (CircleInfoActivity.this.mCircleInfoBean.getData().getIs_opposition().equals("1")) {
                        CircleInfoActivity.this.tv_oppose.setText("已反对(" + opposition_num + ")");
                    } else {
                        CircleInfoActivity.this.tv_oppose.setText("反对(" + opposition_num + ")");
                    }
                    CircleInfoActivity.this.mCircleInfoBean.getData().setSupport_count(support_num);
                    CircleInfoActivity.this.mCircleInfoBean.getData().setOpposition_count(opposition_num);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void changeOpptionAndagree() {
        new XPopup.Builder(this).asCustom(new CircleDoThinkPopWindow(this, this.mCircleInfoBean.getData().getSupport_count() + "", this.mCircleInfoBean.getData().getOpposition_count() + "", new CircleDoThinkPopWindow.onClickIMl() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.5
            @Override // com.psychiatrygarden.widget.CircleDoThinkPopWindow.onClickIMl
            public void onClickMethod(String argeeNum, String opptionNum) {
                CircleInfoActivity.this.changeNum(argeeNum, opptionNum);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commentMsgUI(boolean isComment) {
        if (isComment) {
            this.questiondetails_btn_centerMsg.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youpinglun_night : R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.question_msg_night : R.drawable.question_msg);
        }
    }

    private void deleteDialog() {
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.4
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                CircleInfoActivity.this.deleTieziData();
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("您确定删除此帖？"), "取消", "确定")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAdConfig() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.QUESTION_AD_CONFIG, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleInfoActivity.this.getHeaderData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass19) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", "0"))) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        if (jSONArray.length() > 0) {
                            int length = jSONArray.length();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (CommonParameter.AD_CONFIG.equals(jSONObject2.optString("key", ""))) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), CircleInfoActivity.this.getApplicationContext());
                                    CircleInfoActivity.this.mIsSdkAd = false;
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                    CircleInfoActivity.this.getHeaderData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleInfoActivity.this.getHeaderData();
                }
            }
        });
    }

    private void getBottomInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", getIntent().getExtras().getString("article_id"));
        ajaxParams.put("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getForumBottomStatus, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass14) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("is_collection");
                        String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("is_praise");
                        String strOptString3 = new JSONObject(s2).optJSONObject("data").optString("is_comment");
                        String strOptString4 = new JSONObject(s2).optJSONObject("data").optString("comment_count");
                        CircleInfoActivity.this.isCollection = (TextUtils.isEmpty(strOptString) || strOptString.equals("0")) ? false : true;
                        boolean z2 = (TextUtils.isEmpty(strOptString2) || strOptString2.equals("0")) ? false : true;
                        boolean z3 = (TextUtils.isEmpty(strOptString3) || strOptString3.equals("0")) ? false : true;
                        CircleInfoActivity.this.praiseMsgUI(z2);
                        CircleInfoActivity.this.getCollectData(strOptString);
                        CircleInfoActivity.this.commentMsgUI(z3);
                        if (TextUtils.isEmpty(strOptString4)) {
                            CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText("0");
                            return;
                        }
                        int i2 = Integer.parseInt(strOptString4);
                        if (i2 > 10000) {
                            CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                        } else {
                            CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText(String.valueOf(i2));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.20
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (t2 != null) {
                    t2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                JSONObject jSONObjectOptJSONObject;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.optString("code").equals("200") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null && jSONObjectOptJSONObject.length() > 0) {
                        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, CircleInfoActivity.this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, CircleInfoActivity.this.mContext, 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0 || CircleInfoActivity.this.commlist.size() <= 8 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                            return;
                        }
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setAds(jSONObject.optString("data"));
                        if (CircleInfoActivity.this.hot == null || CircleInfoActivity.this.hot.size() < 8) {
                            return;
                        }
                        boolean z2 = true;
                        hotBean.setHot(true);
                        CircleInfoActivity.this.commlist.add(9, hotBean);
                        CircleInfoActivity.this.mHasConfigAd = true;
                        CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                        CircleInfoAdapter circleInfoAdapter = circleInfoActivity.mCommListAdapter;
                        boolean z3 = circleInfoActivity.mHasConfigAd && CircleInfoActivity.this.hot.size() >= 8;
                        if (CircleInfoActivity.this.mIsSdkAd) {
                            z2 = false;
                        }
                        circleInfoAdapter.setShowAd(z3, z2);
                        CircleInfoActivity circleInfoActivity2 = CircleInfoActivity.this;
                        circleInfoActivity2.mCommListAdapter.setList(circleInfoActivity2.commlist);
                        CircleInfoActivity.this.mCommListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData(boolean isAddComment, int position) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("only_author", "0");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        Object obj = extras.get("module_type");
        if (obj == null) {
            obj = 12;
        }
        ajaxParams.put("module_type", String.valueOf(obj));
        ajaxParams.put("obj_id", getIntent().getExtras().getString("article_id"));
        ajaxParams.put("comment_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.pageNum));
        if (this.pageNum == 1) {
            this.break_point = (System.currentTimeMillis() / 1000) + "";
        }
        ajaxParams.put("break_point", this.break_point);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        if (this.pageNum > 1) {
            this.addFooterView.setVisibility(0);
        } else {
            this.addFooterView.setVisibility(8);
            this.mTvNoData.setVisibility(8);
            this.mLyLoading.setVisibility(0);
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass17(isAddComment));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNewPositions() {
        for (int i2 = 0; i2 < this.commlist.size(); i2++) {
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i2);
            int type = hotBean.getType();
            boolean zIsHot = hotBean.isHot();
            if (type == 1 && !zIsHot && hotBean.getName() != null && hotBean.getName().contains("最新")) {
                return i2 + 1;
            }
        }
        return 0;
    }

    private void imgReset() {
        this.webview.loadUrl("javascript:(function(){var objs = document.getElementsByTagName('img'); for(var i=0;i<objs.length;i++)  {var img = objs[i];   if(img.id=='showimg'){EXIF.getData(img,function(){EXIF.getAllTags(this);EXIF.getTag(this, 'Orientation');});}}})()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPicBanner(final List<CircleInfoBean.DataBean.ImgBean> picList) {
        final ArrayList arrayList = new ArrayList();
        Iterator<CircleInfoBean.DataBean.ImgBean> it = picList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getImage());
        }
        this.mTvPicNumber.setText("1/" + picList.size());
        int color = Color.parseColor(SkinManager.getCurrentSkinType(this) == 0 ? "#D9D9D9" : "#3E465F");
        int color2 = Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#B2575C" : "#F95843");
        this.indicator_view.setVisibility(arrayList.size() > 1 ? 0 : 8);
        this.indicator_view.initIndicator(arrayList.size(), color2, color);
        this.mPicBanner.addBannerLifecycleObserver(this).isAutoLoop(false).setAdapter(new CircleInfoPicBannerAdp(picList)).setPageTransformer(new AlphaPageTransformer()).setIndicatorRadius(ScreenUtil.getPxByDp(this.mContext, 6)).setIndicatorNormalWidth(ScreenUtil.getPxByDp(this.mContext, 6)).setIndicatorSelectedWidth(ScreenUtil.getPxByDp(this.mContext, 6)).setIndicatorNormalColor(color).setIndicatorSelectedColor(color2);
        this.mPicBanner.addOnPageChangeListener(new OnPageChangeListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.21
            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // com.youth.banner.listener.OnPageChangeListener
            public void onPageSelected(int position) {
                CircleInfoActivity.this.mTvPicNumber.setText((position + 1) + "/" + picList.size());
                CircleInfoActivity.this.indicator_view.bannerPageChange(position);
            }
        });
        this.mPicBanner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.activity.circleactivity.s
            @Override // com.youth.banner.listener.OnBannerListener
            public final void OnBannerClick(Object obj, int i2) {
                this.f11588a.lambda$initPicBanner$25(arrayList, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initScrollListener() {
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass3());
    }

    private void jumpToCircleInfo(CirclrListBean.DataBean item) {
        try {
            if (isLogin()) {
                if ("1".equals(item.getNo_access())) {
                    startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (item.getType().equals("3")) {
                    Intent intent = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                    intent.putExtra("cat_id", item.getCid());
                    intent.putExtra("article", item.getOrigin_id());
                    intent.putExtra("json_path", item.getJson_path());
                    intent.putExtra("html_path", item.getHtml_path());
                    intent.putExtra("h5_path", item.getH5_path());
                    intent.putExtra("is_rich_text", item.getIs_rich_text());
                    intent.putExtra("index", 0);
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                intent2.putExtra("channel_id", getIntent().getExtras().getString("channel_id"));
                intent2.putExtra("article_id", "" + item.getOrigin_id());
                intent2.putExtra("module_type", getIntent().getExtras().getInt("module_type", 12));
                intent2.putExtra("commentCount", item.getComment_count());
                startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$16() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bannedDialog$14(PopupComWindow popupComWindow, String str, String str2, PopupComWindow popupComWindow2) {
        if (popupComWindow != null && popupComWindow.isShowing()) {
            popupComWindow.dismiss();
        }
        if (popupComWindow2 != null && popupComWindow2.isShowing()) {
            popupComWindow2.dismiss();
        }
        HttpRequstData.getIntance(this).postBannedData("bbs", this.mCircleInfoBean.getData().getAuthor_id(), str2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bannedDialog$15(View view, final String str, final PopupComWindow popupComWindow) {
        new PopupComWindow(this, "请选择封禁理由", "", 3, new OnClickDiogListenter() { // from class: com.psychiatrygarden.activity.circleactivity.v
            @Override // com.psychiatrygarden.interfaceclass.OnClickDiogListenter
            public final void onDiogClick(String str2, PopupComWindow popupComWindow2) {
                this.f11602a.lambda$bannedDialog$14(popupComWindow, str, str2, popupComWindow2);
            }
        }).showPopUp(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        shareDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        try {
            if (this.mCircleInfoBean.getData().getIs_collection().equals("0")) {
                getCollectData(1);
            } else {
                getCollectData(2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(AdapterView adapterView, View view, int i2, long j2) {
        jumpToCircleInfo(this.mDataBeans.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$11(View view) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < this.mCircleInfoBean.getData().getOptions().getOptions().size(); i2++) {
                if (this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).isTrue()) {
                    sb.append(",");
                    sb.append(this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).getId());
                    String vote_nums = this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).getVote_nums();
                    if (TextUtils.isEmpty(vote_nums)) {
                        vote_nums = "0";
                    }
                    this.mCircleInfoBean.getData().getOptions().getOptions().get(i2).setVote_nums((Integer.parseInt(vote_nums) + 1) + "");
                }
            }
            if (sb.length() <= 0) {
                AlertToast("请选择投票选项");
                return;
            }
            this.mCircleInfoBean.getData().getOptions().setVote_option(sb.substring(1));
            String user_count = this.mCircleInfoBean.getData().getOptions().getUser_count();
            this.mCircleInfoBean.getData().getOptions().setUser_count((Integer.parseInt(user_count) + 1) + "");
            this.mCircleInfoBean.getData().getOptions().setIs_vote("1");
            this.canyuren.setText(String.format(Locale.CHINA, "%s人参与了投票", this.mCircleInfoBean.getData().getOptions().getUser_count()));
            if (this.mCircleInfoBean.getData().getChoice().equals("1")) {
                this.tvOptionType.setText("单选");
            } else {
                this.tvOptionType.setText("多选");
            }
            this.okTv.setVisibility(8);
            this.mCommAdapter.notifyDataSetChanged();
            getTopicData();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$12(View view) {
        try {
            if (this.mCircleInfoBean.getData().getIs_opposition().equals("1")) {
                return;
            }
            String str = "0";
            if (this.mCircleInfoBean.getData().getIs_support().equals("1")) {
                this.mCircleInfoBean.getData().setIs_support("0");
                String support_count = this.mCircleInfoBean.getData().getSupport_count();
                if (!TextUtils.isEmpty(support_count)) {
                    str = support_count;
                }
                if (Integer.parseInt(str) > 0) {
                    this.mCircleInfoBean.getData().setSupport_count((Integer.parseInt(str) - 1) + "");
                } else {
                    this.mCircleInfoBean.getData().setSupport_count(str);
                }
                this.tv_support.setText(String.format(Locale.CHINA, "赞同(%s)", this.mCircleInfoBean.getData().getSupport_count()));
                mOpporSuport(1, 2);
            } else {
                this.mCircleInfoBean.getData().setIs_support("1");
                String support_count2 = this.mCircleInfoBean.getData().getSupport_count();
                if (!TextUtils.isEmpty(support_count2)) {
                    str = support_count2;
                }
                this.mCircleInfoBean.getData().setSupport_count((Integer.parseInt(str) + 1) + "");
                Toast_pop(view, 0);
                this.tv_support.setText(String.format(Locale.CHINA, "已赞同(%s)", this.mCircleInfoBean.getData().getSupport_count()));
                mOpporSuport(1, 1);
            }
            getColorTv(Integer.parseInt(this.mCircleInfoBean.getData().getSupport_count()), Integer.parseInt(this.mCircleInfoBean.getData().getOpposition_count()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$13(View view) {
        try {
            if (this.mCircleInfoBean.getData().getIs_support().equals("1")) {
                return;
            }
            String str = "0";
            if (this.mCircleInfoBean.getData().getIs_opposition().equals("1")) {
                this.mCircleInfoBean.getData().setIs_opposition("0");
                String opposition_count = this.mCircleInfoBean.getData().getOpposition_count();
                if (!TextUtils.isEmpty(opposition_count)) {
                    str = opposition_count;
                }
                if (Integer.parseInt(str) > 0) {
                    this.mCircleInfoBean.getData().setOpposition_count((Integer.parseInt(str) - 1) + "");
                } else {
                    this.mCircleInfoBean.getData().setOpposition_count(str);
                }
                this.tv_oppose.setText("反对(" + this.mCircleInfoBean.getData().getOpposition_count() + ")");
                mOpporSuport(2, 2);
            } else {
                this.mCircleInfoBean.getData().setIs_opposition("1");
                String opposition_count2 = this.mCircleInfoBean.getData().getOpposition_count();
                if (!TextUtils.isEmpty(opposition_count2)) {
                    str = opposition_count2;
                }
                this.mCircleInfoBean.getData().setOpposition_count((Integer.parseInt(str) + 1) + "");
                Toast_pop(view, 1);
                this.tv_oppose.setText("已反对(" + this.mCircleInfoBean.getData().getOpposition_count() + ")");
                mOpporSuport(2, 1);
            }
            getColorTv(Integer.parseInt(this.mCircleInfoBean.getData().getSupport_count()), Integer.parseInt(this.mCircleInfoBean.getData().getOpposition_count()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(RefreshLayout refreshLayout) {
        this.pageNum = 1;
        getHeaderData();
        getBottomInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        setRemen(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        setRemen(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view, int i2) {
        if (i2 == 0) {
            deleteDialog();
            return;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                bannedDialog(view);
                return;
            } else {
                if (i2 != 3) {
                    return;
                }
                changeOpptionAndagree();
                return;
            }
        }
        CircleInfoBean circleInfoBean = this.mCircleInfoBean;
        if (circleInfoBean == null || circleInfoBean.getData() == null || this.mCircleInfoBean.getData().getContent() == null) {
            AlertToast("数据异常！");
        } else {
            bianjiData(this.mCircleInfoBean.getData().getContent());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(final View view) {
        ArrayList arrayList = new ArrayList();
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            arrayList.add("删除此帖");
            arrayList.add("编辑此帖");
            arrayList.add("禁言此用户");
            arrayList.add("修改赞同、反对数");
        } else {
            arrayList.add("删除此帖");
            arrayList.add("编辑此帖");
        }
        new XPopup.Builder(this).autoDismiss(Boolean.FALSE).asCustom(new CircleInfoMorePopWindow(this, arrayList, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.u
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f11597a.lambda$init$5(view, i2);
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(View view) {
        viewUserInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(CharSequence charSequence) {
        try {
            ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence.toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9(View view) {
        viewUserInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPicBanner$25(List list, Object obj, int i2) {
        showPicView(list, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$23() {
        this.isHUAdong = true;
        Iterator<CommentBean.DataBean.HotBean> it = this.commlist.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().isHot()) {
                i2++;
            }
        }
        if (i2 > 1) {
            setRemen(0);
        } else {
            setRemen(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(View view) {
        showCircleReport();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$18(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ArrayList arrayList = new ArrayList(this.mPicAdapter.getData());
        this.mPicAdapter.getIsLongPicByPosition(i2);
        showPicView(arrayList, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$19(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(0).getImage());
        showPicView(arrayList, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$20(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(0).getImage());
        showPicView(arrayList, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$21(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(0).getImage());
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(1).getImage());
        showPicView(arrayList, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$22(View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(0).getImage());
        arrayList.add(this.mCircleInfoBean.getData().getImg().get(1).getImage());
        showPicView(arrayList, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRemen$24(int i2) {
        this.mPinnedSecListView.setSelection(i2 + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void praiseMsgUI(boolean isPraise) {
        if (isPraise) {
            this.questiondetails_btn_zantong.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareArticleSuccess() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", "" + getIntent().getExtras().getString("article_id"));
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.shareArticle, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.10
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

    @SuppressLint({"NewApi"})
    private void shareDialog() {
        if ("1".equals(this.mCircleInfoBean.getData().getIs_anonymous())) {
            ToastUtil.shortToast(this, "匿名帖子无法分享");
            return;
        }
        DialogShare dialogShare = new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.t
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f11592a.shareAppControl(i2);
            }
        });
        this.mDialogShare = dialogShare;
        dialogShare.show();
    }

    private void showCircleReport() {
        if (this.mReportParams == null) {
            this.mReportParams = new ReportParams();
        }
        this.mReportParams.setArticle_id(getIntent().getExtras().getString("article_id"));
        CircleReportPop circleReportPop = (CircleReportPop) new XPopup.Builder(this).asCustom(new CircleReportPop(this, this.mReportParams));
        this.mCircleReportPop = circleReportPop;
        circleReportPop.show();
    }

    private void showPicView(List<Object> list, int position) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(list).setSrcView(null, position).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLikeIconStatus(String like) {
        if ("1".equals(like)) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
                return;
            } else {
                this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    private void viewUserInfo() {
        if (CommonUtil.isFastClick() || UserConfig.getUserId().equals("") || "1".equals(this.mCircleInfoBean.getData().getIs_anonymous())) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", this.mCircleInfoBean.getData().getAuthor_id());
        intent.putExtra("jiav", "");
        intent.addFlags(67108864);
        this.mContext.startActivity(intent);
        ((Activity) this.mContext).overridePendingTransition(R.anim.umeng_fb_slide_in_from_right, R.anim.umeng_fb_slide_out_from_left);
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
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.circleactivity.d0
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                CircleInfoActivity.lambda$Toast_pop$16();
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

    public void bianjiData(String content) {
        ArrayList arrayList = new ArrayList();
        if (this.mCircleInfoBean.getData().getImg() != null && this.mCircleInfoBean.getData().getImg().size() > 0) {
            for (int i2 = 0; i2 < this.mCircleInfoBean.getData().getImg().size(); i2++) {
                ImageDataBean imageDataBean = new ImageDataBean();
                imageDataBean.setId(new Random().nextInt(100000));
                imageDataBean.setB_img(this.mCircleInfoBean.getData().getImg().get(i2).getImage());
                imageDataBean.setS_img(this.mCircleInfoBean.getData().getImg().get(i2).getImage());
                arrayList.add(imageDataBean);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        if (this.mCircleInfoBean.getData().getRich_img() != null && this.mCircleInfoBean.getData().getRich_img().size() > 0) {
            for (int i3 = 0; i3 < this.mCircleInfoBean.getData().getRich_img().size(); i3++) {
                ImageDataBean imageDataBean2 = new ImageDataBean();
                imageDataBean2.setId(new Random().nextInt(100000));
                imageDataBean2.setB_img(this.mCircleInfoBean.getData().getRich_img().get(i3).getImage());
                imageDataBean2.setS_img(this.mCircleInfoBean.getData().getRich_img().get(i3).getImage());
                arrayList2.add(imageDataBean2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        if (this.mCircleInfoBean.getData().getLink() != null && this.mCircleInfoBean.getData().getLink().size() > 0) {
            for (int i4 = 0; i4 < this.mCircleInfoBean.getData().getLink().size(); i4++) {
                LinkDataBean linkDataBean = new LinkDataBean();
                linkDataBean.setId(i4);
                linkDataBean.setTitle(this.mCircleInfoBean.getData().getLink().get(i4).getTitle());
                linkDataBean.setUrl(this.mCircleInfoBean.getData().getLink().get(i4).getUrl());
                arrayList3.add(linkDataBean);
            }
        }
        try {
            Intent intent = new Intent(this, (Class<?>) PushCircleAndArticleAct.class);
            intent.putExtra("isEdit", true);
            intent.putExtra("isRich", this.mCircleInfoBean.getData().getRich_text());
            intent.putExtra("isRichImg", arrayList2);
            intent.putExtra("is_anonymous", "1".equals(this.mCircleInfoBean.getData().getIs_anonymous()));
            intent.putExtra("htmlContent", new Gson().toJson(this.mCircleInfoBean.getData().getContent()));
            intent.putExtra("htmlTitle", "" + this.mCircleInfoBean.getData().getTitle());
            try {
                intent.putExtra("options", (Serializable) this.mCircleInfoBean.getData().getOptions().getOptions());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            intent.putExtra("mImageList", arrayList);
            intent.putExtra("mLinkList", arrayList3);
            intent.putExtra("cid", (Serializable) this.mCircleInfoBean.getData().getCid());
            intent.putExtra("type", this.mCircleInfoBean.getData().getType());
            intent.putExtra("article_id", this.mCircleInfoBean.getData().getId());
            intent.putExtra("choice", this.mCircleInfoBean.getData().getChoice());
            intent.putExtra("topicId", this.mCircleInfoBean.getData().getTopic_id());
            intent.putExtra("topicName", this.mCircleInfoBean.getData().getTopic_name());
            intent.putExtra("module_type", getIntent().getExtras().getInt("module_type", 12));
            intent.putExtra("group_id", "" + getIntent().getExtras().getString("group_id", "0"));
            intent.putExtra("pushCircleList", (Serializable) this.mCircleInfoBean.getData().getPush_article_list());
            intent.putExtra("bookList", (Serializable) this.mCircleInfoBean.getData().getEbook());
            startActivity(intent);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void deleTieziData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", "" + getIntent().getExtras().getString("article_id"));
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumDDeteleInfoApi;
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        } else {
            str = NetworkRequestsURL.deleTieziUrl;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post("isRefaulfCircle");
                        CircleInfoActivity.this.finish();
                    } else {
                        ToastUtil.shortToast(CircleInfoActivity.this, new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.adapter.CircleInfoAdapter.CircleInfoClickIml
    public void doSectionClick(int type) {
        setRemen(type);
    }

    public void getCollectData(final int flag) {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", "" + getIntent().getExtras().getString("article_id"));
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumDCollectionInfoApi;
        } else {
            str = NetworkRequestsURL.mcollection;
            ajaxParams.put("flag", "" + flag);
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        int i2 = flag;
                        if (i2 == 1) {
                            CircleInfoActivity.this.mCircleInfoBean.getData().setIs_collection("1");
                        } else if (i2 == 2) {
                            CircleInfoActivity.this.mCircleInfoBean.getData().setIs_collection("0");
                        }
                        CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                        circleInfoActivity.getCollectData(circleInfoActivity.mCircleInfoBean.getData().getIs_collection());
                        EventBus.getDefault().post("collectChange");
                    }
                    CircleInfoActivity.this.AlertToast(jSONObject.optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getColorTv(int suport, int oppo) {
        if (suport > oppo) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_success_color));
                this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color));
                return;
            } else {
                this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_success_color_night));
                this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color_night));
                return;
            }
        }
        if (suport < oppo) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color));
                this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_fail_color));
                return;
            } else {
                this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color_night));
                this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_fail_color_night));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color));
            this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color));
        } else {
            this.tv_support.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color_night));
            this.tv_oppose.setTextColor(ContextCompat.getColor(this.mContext, R.color.forth_txt_color_night));
        }
    }

    public void getHeaderData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", getIntent().getExtras().getString("article_id"));
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        String str = 16 == getIntent().getExtras().getInt("module_type", 12) ? NetworkRequestsURL.getforumDetailInfoApi : NetworkRequestsURL.getArticleInfo;
        hideProgressDialog();
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AnonymousClass13());
    }

    public void getImageData() {
        try {
            if (this.mCircleInfoBean.getData().getImg() == null || this.mCircleInfoBean.getData().getImg().size() <= 0) {
                return;
            }
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(this.spanBuilder.toString());
            int i2 = 0;
            while (matcher.find()) {
                if (matcher.group().contains("http")) {
                    lastPositionData(this.mCircleInfoBean.getData().getImg().get(i2).getImage(), matcher.start(), matcher.end(), i2);
                    i2++;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void getTopicData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", getIntent().getExtras().getString("article_id"));
        ajaxParams.put("option_id", "" + this.mCircleInfoBean.getData().getOptions().getVote_option());
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, 16 == getIntent().getExtras().getInt("module_type", 12) ? NetworkRequestsURL.getforumvoteApi : NetworkRequestsURL.vote, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass12) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        return;
                    }
                    ToastUtil.shortToast(CircleInfoActivity.this, jSONObject.optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("帖子详情");
        setNavBarColor();
        setNewStyleStatusBarColor();
        this.mTxtActionbarTitle.setTextSize(2, 16.0f);
        this.WEBVIEW_CONTENT = CommonUtil.getWebHtml(this);
        this.include_title_layout = (RelativeLayout) findViewById(R.id.include_title_relative);
        this.remen = (CheckedTextView) findViewById(R.id.remen2);
        this.zuixin = (CheckedTextView) findViewById(R.id.zuixin2);
        this.lineselect = (LinearLayout) findViewById(R.id.lineselect2);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ll_question_comment);
        this.mQuestiondetailsBtnCommentNum = (TextView) findViewById(R.id.questiondetails_btn_commentNum);
        this.questiondetails_btn_zantong = (ImageView) findViewById(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) findViewById(R.id.questiondetails_btn_centerMsg);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_centerMsg);
        ((LinearLayout) findViewById(R.id.ly_questiondetails_btn_zantong)).setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ly_iv_topic_detail_fenxiang);
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setIsPlanCanvas(0);
        this.tv_topic_detail_add_comment = (TextView) findViewById(R.id.tv_topic_detail_add_comment);
        this.iv_topic_detail_msg = (ImageView) findViewById(R.id.iv_topic_detail_msg);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ly_iv_topic_detail_msg);
        this.tv_topic_detail_msg = (TextView) findViewById(R.id.tv_topic_detail_msg);
        this.mLoadingView = (CustomEmptyView) findViewById(R.id.loading_view);
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11512c.lambda$init$0(view);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11549c.lambda$init$1(view);
            }
        });
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh);
        this.mSwipeRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(false);
        this.mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.circleactivity.k0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11554c.lambda$init$2(refreshLayout);
            }
        });
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.l0
            @Override // java.lang.Runnable
            public final void run() {
                this.f11558c.getAdConfig();
            }
        });
        getBottomInfo();
        isSelect(true, false);
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11557c.lambda$init$3(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11562c.lambda$init$4(view);
            }
        });
        View viewInflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.activity_headercircle, (ViewGroup) null);
        this.mPicBanner = (Banner) viewInflate2.findViewById(R.id.banner);
        this.indicator_view = (DotIndicatorScrollView) viewInflate2.findViewById(R.id.indicator_view);
        this.mLyBannerView = (RelativeLayout) viewInflate2.findViewById(R.id.ly_banner);
        this.mTvPicNumber = (TextView) viewInflate2.findViewById(R.id.tv_pic_num);
        this.isverauth = (ImageView) viewInflate2.findViewById(R.id.isverauth);
        this.fjlistview = (ListView) viewInflate2.findViewById(R.id.fjlistview);
        this.webview = (WebView) viewInflate2.findViewById(R.id.webview);
        this.mImgBigPic = (RoundedImageView) viewInflate2.findViewById(R.id.img_pic_big);
        this.mImgBigPicLong = (SubsamplingScaleImageView) viewInflate2.findViewById(R.id.img_pic_big_long);
        this.mCardviewLongPic = (CardView) viewInflate2.findViewById(R.id.cardview_long_pic);
        this.mLyPicTwo = (LinearLayout) viewInflate2.findViewById(R.id.ly_pic_two);
        this.mImgOne = (RoundedImageView) viewInflate2.findViewById(R.id.img_pic_one);
        this.mImgTwo = (RoundedImageView) viewInflate2.findViewById(R.id.img_pic_two);
        this.mPicRecycler = (RecyclerView) viewInflate2.findViewById(R.id.pic_recycler);
        this.longPicTag1 = (TextView) viewInflate2.findViewById(R.id.tv_long_pic_1);
        this.longPicTag2 = (TextView) viewInflate2.findViewById(R.id.tv_long_pic_2);
        NinePicAdp ninePicAdp = new NinePicAdp(this, true);
        this.mPicAdapter = ninePicAdp;
        this.mPicRecycler.setAdapter(ninePicAdp);
        WebSettings settings = this.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webview.setHorizontalScrollBarEnabled(false);
        this.webview.setVerticalScrollBarEnabled(false);
        this.webview.getSettings().setBlockNetworkImage(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDefaultTextEncodingName("utf-8");
        this.webview.setBackgroundColor(0);
        this.webview.getSettings().setDefaultFontSize(17);
        this.webview.setWebViewClient(new MyWebViewClient());
        this.webview.addJavascriptInterface(new PrictData(this.mContext), "javaScxript");
        this.wrongtxt = (TextView) viewInflate2.findViewById(R.id.wrongtxt);
        this.isvipimg = (ImageView) viewInflate2.findViewById(R.id.isvipimg);
        this.jiav = (CircleImageView) viewInflate2.findViewById(R.id.jiav);
        this.ivTieziMore = (ImageView) viewInflate2.findViewById(R.id.iv_tiezi_more);
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11567c.lambda$init$6(view);
            }
        });
        this.tv_support = (TextView) viewInflate2.findViewById(R.id.tv_support);
        this.tv_oppose = (TextView) viewInflate2.findViewById(R.id.tv_oppose);
        this.tv_reply = (TextView) viewInflate2.findViewById(R.id.tv_reply);
        this.toupiaolayout = (LinearLayout) viewInflate2.findViewById(R.id.toupiaolayout);
        this.listOption = (MyListView) viewInflate2.findViewById(R.id.listOption);
        this.canyuren = (TextView) viewInflate2.findViewById(R.id.canyuren);
        this.tvOptionType = (TextView) viewInflate2.findViewById(R.id.tv_option_type);
        this.okTv = (TextView) viewInflate2.findViewById(R.id.okTv);
        this.title = (TextView) viewInflate2.findViewById(R.id.title);
        TextView textView = (TextView) viewInflate2.findViewById(R.id.username);
        this.username = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11571c.lambda$init$7(view);
            }
        });
        this.timedata = (TextView) viewInflate2.findViewById(R.id.timedata);
        this.imgheader = (CircleImageView) viewInflate2.findViewById(R.id.imgheader);
        this.titleContent = (TextView) viewInflate2.findViewById(R.id.titleContent);
        int color = ContextCompat.getColor(this.mContext, R.color.app_theme_red);
        int color2 = ContextCompat.getColor(this.mContext, R.color.trans_app_theme_red);
        try {
            TextView textView2 = this.titleContent;
            if (textView2 != null) {
                new SelectableTextHelper.Builder(textView2).setSelectedColor(color2).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(color).build().setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.activity.circleactivity.p
                    @Override // com.psychiatrygarden.widget.OnSelectListener
                    public final void onTextSelected(CharSequence charSequence) {
                        this.f11575a.lambda$init$8(charSequence);
                    }
                });
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.imgheader.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11579c.lambda$init$9(view);
            }
        });
        this.mListAdapter = new PushCirclesListAdapter(this.mDataBeans, false, true);
        this.mPushCircleListView = (MyListView) viewInflate2.findViewById(R.id.push_ListView);
        this.bookList = (MyListView) viewInflate2.findViewById(R.id.book_ListView);
        this.mEbookAdapter = new BookCirclesListAdp(this.mBookDatas, false, true);
        this.mPushCircleListView.setAdapter((ListAdapter) this.mListAdapter);
        this.bookList.setAdapter((ListAdapter) this.mEbookAdapter);
        this.mEbookAdapter.setOnItemChoosedLisenter(new BookCirclesListAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.2
            @Override // com.psychiatrygarden.activity.circleactivity.BookCirclesListAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, PushBookData item) {
            }

            @Override // com.psychiatrygarden.activity.circleactivity.BookCirclesListAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, PushBookData item) {
                if (UserConfig.isLogin()) {
                    String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                    String admin = UserConfig.getInstance().getUser().getAdmin();
                    String avatar = UserConfig.getInstance().getUser().getAvatar();
                    CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                    circleInfoActivity.startActivity(ReadBookActivity.INSTANCE.newIntent(circleInfoActivity, item.getId(), UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
                }
            }
        });
        this.mPinnedSecListView.addHeaderView(viewInflate2);
        this.mPinnedSecListView.addFooterView(this.addFooterView);
        this.addFooterView.setVisibility(8);
        this.mPushCircleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.f0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f11522c.lambda$init$10(adapterView, view, i2, j2);
            }
        });
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11532c.lambda$init$11(view);
            }
        });
        this.tv_support.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11538c.lambda$init$12(view);
            }
        });
        this.tv_oppose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11545c.lambda$init$13(view);
            }
        });
        relativeLayout.setOnClickListener(this);
        this.mQuestiondetailsBtnCommentNum.setOnClickListener(this);
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

    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    public void lastPositionData(String imgUrl, final int start, final int end, final int postion) {
        Glide.with(this.mContext).asBitmap().load((Object) GlideUtils.generateUrl(imgUrl)).listener(new RequestListener<Bitmap>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.16
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.15
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object bitmap, @Nullable Transition transition) {
                onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                int screenWidth = (CommonUtil.getScreenWidth(CircleInfoActivity.this.mContext) - CircleInfoActivity.this.titleContent.getPaddingLeft()) - CircleInfoActivity.this.titleContent.getPaddingRight();
                int height = (bitmap.getHeight() * screenWidth) / bitmap.getWidth();
                if (height > 4096) {
                    height = R2.color.N_stretchTextColor;
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                bitmapDrawable.setBounds(0, 0, screenWidth, height);
                CircleInfoActivity.this.spanBuilder.setSpan(new ImageSpan(bitmapDrawable, 1), start, end, 33);
                CircleInfoActivity.this.spanBuilder.setSpan(new ClickableSpan() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.15.1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View widget) {
                        if (CircleInfoActivity.this.mCircleInfoBean.getData().getImg() == null || CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size() <= 0) {
                            return;
                        }
                        AnonymousClass15 anonymousClass15 = AnonymousClass15.this;
                        if (postion >= CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size()) {
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < CircleInfoActivity.this.mCircleInfoBean.getData().getImg().size(); i2++) {
                            arrayList.add(CircleInfoActivity.this.mCircleInfoBean.getData().getImg().get(i2).getImage());
                        }
                        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(CircleInfoActivity.this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                        xPopupImageLoader.popupInfo = new PopupInfo();
                        xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, postion).show();
                    }
                }, start, end, 33);
                CircleInfoActivity.this.titleContent.setMovementMethod(LinkMovementMethod.getInstance());
                CircleInfoActivity.this.titleContent.setText(CircleInfoActivity.this.spanBuilder);
                CircleInfoActivity.this.titleContent.requestLayout();
            }
        });
    }

    public void mOpporSuport(int type, int flag) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", "" + getIntent().getExtras().getString("article_id"));
        ajaxParams.put("type", "" + type);
        ajaxParams.put("flag", "" + flag);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, 16 == getIntent().getExtras().getInt("module_type", 12) ? NetworkRequestsURL.getforumDpraiseInfoApi : NetworkRequestsURL.interAction, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    CircleInfoActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            this.mCommListAdapter.getputData(data.getBundleExtra("bundleIntent"));
        } else {
            if (requestCode != 1) {
                return;
            }
            putComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        String id = this.mCircleInfoBean.getData().getId();
        switch (v2.getId()) {
            case R.id.ll_question_comment /* 2131364860 */:
            case R.id.questiondetails_btn_commentNum /* 2131366219 */:
                this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.c0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11499c.lambda$onClick$23();
                    }
                });
                break;
            case R.id.ly_questiondetails_btn_centerMsg /* 2131365233 */:
                Intent intent = new Intent(this, (Class<?>) CommentNewActivity.class);
                if (id.matches("-?[0-9]+")) {
                    intent.putExtra("circle_id", Long.parseLong(id));
                }
                intent.putExtra("module_type", getIntent().getExtras().getInt("module_type", 12));
                intent.putExtra("comment_type", "2");
                intent.putExtra("isNewCom", true);
                intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
                intent.putExtra("iscomValu", true);
                intent.putExtra("app_id", this.mAppId);
                intent.putExtra("isProhibit", this.mIsProhibit);
                startActivity(intent);
                break;
            case R.id.ly_questiondetails_btn_zantong /* 2131365237 */:
                Intent intent2 = new Intent(this, (Class<?>) CommentNewActivity.class);
                if (id.matches("-?[0-9]+")) {
                    intent2.putExtra("circle_id", Long.parseLong(id));
                }
                intent2.putExtra("module_type", getIntent().getExtras().getInt("module_type", 12));
                intent2.putExtra("comment_type", "2");
                intent2.putExtra("isNewComzantong", true);
                intent2.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
                intent2.putExtra("iscomValu", true);
                intent2.putExtra("app_id", this.mAppId);
                intent2.putExtra("isProhibit", this.mIsProhibit);
                startActivity(intent2);
                break;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        CircleReportPop circleReportPop;
        super.onConfigurationChanged(newConfig);
        if (ScreenUtil.isTablet(this) && (circleReportPop = this.mCircleReportPop) != null && circleReportPop.isShow()) {
            recreate();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.iv_actionbar_right.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.jmui_chat_detail);
        if (drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 0 ? R.color.first_txt_color : R.color.first_text_color_night), PorterDuff.Mode.SRC_IN);
            this.iv_actionbar_right.setImageDrawable(drawable);
        } else {
            this.iv_actionbar_right.setImageResource(R.drawable.jmui_chat_detail);
        }
        if (savedInstanceState == null || !savedInstanceState.getBoolean("show_circle_report", false)) {
            return;
        }
        Parcelable parcelable = savedInstanceState.getParcelable("data");
        if (parcelable != null) {
            this.mReportParams = (ReportParams) parcelable;
        }
        showCircleReport();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WebView webView = this.webview;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.webview);
            }
            this.webview.stopLoading();
            this.webview.getSettings().setJavaScriptEnabled(false);
            this.webview.clearHistory();
            this.webview.clearView();
            this.webview.removeAllViews();
            this.webview.destroy();
            this.webview = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("isRefaulfCircle") || str.equals("isRefaulfCircleInfo")) {
            this.mHandler.sendEmptyMessageDelayed(272, 1000L);
            return;
        }
        if (str.equals("BuySuccess")) {
            Log.e("pay_success", "支付成功回调");
            getHeaderData();
            return;
        }
        if ("delReplyAndLoadData".equals(str)) {
            this.pageNum = 1;
            getCommentListData(false, 0);
            getBottomInfo();
        } else if ("mCommentResult".equals(str) || "refresh_praise".equals(str) || TextUtils.equals(EventBusConstant.EVENT_REFRESH_COMMENT_NUM, str)) {
            getBottomInfo();
        } else if ("jump2KnowledgeChapter".equals(str)) {
            finish();
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
            ToastUtil.shortToast(this, "请检查app存储权限是否打开，否则会影响查看！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        DialogShare dialogShare = this.mDialogShare;
        if (dialogShare != null) {
            dialogShare.dismiss();
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        CircleReportPop circleReportPop = this.mCircleReportPop;
        if (circleReportPop == null || !circleReportPop.isShow()) {
            return;
        }
        outState.putBoolean("show_circle_report", true);
        ReportParams reportParams = this.mReportParams;
        if (reportParams != null) {
            outState.putParcelable("data", reportParams);
        }
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", getIntent().getExtras().getInt("module_type", 12) + "");
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("obj_id", getIntent().getExtras().getString("article_id") + "");
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleInfoActivity.18
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleInfoActivity.this.AlertToast("请求服务器超时！");
            }

            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x00b6 -> B:18:0x00f0). Please report as a decompilation issue!!! */
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String strOptString = "";
                super.onSuccess((AnonymousClass18) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        NewToast.showShort(ProjectApp.instance(), "评论成功");
                        CircleInfoActivity.this.pageNum = 1;
                        CircleInfoActivity circleInfoActivity = CircleInfoActivity.this;
                        circleInfoActivity.getCommentListData(true, circleInfoActivity.getNewPositions());
                        CommonUtil.showFristDialog(jSONObject);
                        CircleInfoActivity.this.commentMsgUI(true);
                        try {
                            int i2 = Integer.parseInt(CircleInfoActivity.this.mCircleInfoBean.getData().getComment_count()) + 1;
                            CircleInfoActivity.this.mCircleInfoBean.getData().setComment_count(i2 + "");
                            if (i2 > 10000) {
                                CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText((i2 / 10000) + StrPool.DOT + ((i2 % 10000) / 1000) + "万");
                            } else {
                                CircleInfoActivity.this.mQuestiondetailsBtnCommentNum.setText(i2 + "");
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            TextView textView = CircleInfoActivity.this.mQuestiondetailsBtnCommentNum;
                            strOptString = CircleInfoActivity.this.mCircleInfoBean.getData().getComment_count() + strOptString;
                            textView.setText(strOptString);
                        }
                    } else {
                        CircleInfoActivity circleInfoActivity2 = CircleInfoActivity.this;
                        strOptString = jSONObject.optString("message");
                        circleInfoActivity2.AlertToast(strOptString);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    public String replaceValue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_info);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_reply.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11608c.lambda$setListenerForWidget$17(view);
            }
        });
        this.mPicAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.x
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11614c.lambda$setListenerForWidget$18(baseQuickAdapter, view, i2);
            }
        });
        this.mImgBigPic.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11618c.lambda$setListenerForWidget$19(view);
            }
        });
        this.mImgBigPicLong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11622c.lambda$setListenerForWidget$20(view);
            }
        });
        this.mImgOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11489c.lambda$setListenerForWidget$21(view);
            }
        });
        this.mImgTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11495c.lambda$setListenerForWidget$22(view);
            }
        });
    }

    public void setRemen(int type) {
        final int i2 = 0;
        if (type == 0) {
            isSelect(true, false);
            this.mPinnedSecListView.setSelection(1);
            return;
        }
        isSelect(false, true);
        int i3 = 0;
        while (true) {
            if (i3 >= this.commlist.size()) {
                break;
            }
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i3);
            if (hotBean.getType() == 1 && !hotBean.isHot() && hotBean.getName().contains("最新")) {
                i2 = i3;
                break;
            }
            i3++;
        }
        this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.r
            @Override // java.lang.Runnable
            public final void run() {
                this.f11583c.lambda$setRemen$24(i2);
            }
        }, 310L);
    }

    public void shareAppControl(int i2) {
        String share_url = this.mCircleInfoBean.getData().getShare_url();
        String title = this.mCircleInfoBean.getData().getTitle();
        String content = this.mCircleInfoBean.getData().getContent();
        UMImage uMImage = new UMImage(this.mContext, R.drawable.app_icon);
        UMWeb uMWeb = new UMWeb(share_url);
        uMWeb.setTitle(title);
        uMWeb.setThumb(uMImage);
        if (i2 != 3) {
            share_url = content;
        }
        uMWeb.setDescription(share_url);
        if (i2 == 0 || i2 == 1) {
            shareArticleSuccess();
        }
        new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }

    public void getCollectData(String isCollect) {
        this.tv_topic_detail_msg.setText("收藏");
        if (isCollect.equals("0")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.iv_topic_detail_msg.setBackgroundResource(R.drawable.icon_collect_no);
                return;
            } else {
                this.iv_topic_detail_msg.setBackgroundResource(R.drawable.icon_collect_no_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.iv_topic_detail_msg.setBackgroundResource(R.drawable.icon_collect_yes);
        } else {
            this.iv_topic_detail_msg.setBackgroundResource(R.drawable.icon_collect_yes_night);
        }
    }
}
