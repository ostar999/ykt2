package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.lxj.xpopup.core.PopupInfo;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.purchase.activity.VideoRePlayActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.HandoutsInfoAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.bean.HandCollectBean;
import com.psychiatrygarden.bean.HandShareBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConstantUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.AlphaImageView;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogAttrShare;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class HandoutsInfoActivity extends BaseActivity implements DialogAttrShare.callBack, View.OnClickListener {
    private View addFooterView;
    AlertDialog alertDialog;
    public String article;
    private String break_point;
    TextView btn_comment;
    private int contentHeight;
    public String h5_path;
    String index;
    private boolean isLoadingData;
    public String is_rich_text;
    private LinearLayout lineselect;
    public HandoutsInfoAdapter mCommListAdapter;
    private View mHeaderView;
    private boolean mIsSdkAd;
    private PinnedSectionListView1 mListView;
    private RelativeLayout mLyLoading;
    private SmartRefreshLayout mRefreshLayout;
    private String mTotalHtml;
    private TextView mTvNoData;
    ProgressBar progressBar2;
    private ImageView questiondetails_btn_centerMsg;
    AlphaImageView questiondetails_btn_collect;
    TextView questiondetails_btn_commentNum;
    AlphaImageView questiondetails_btn_share;
    private ImageView questiondetails_btn_zantong;
    private CheckedTextView remen;
    public String strValue;
    public String title_str;
    WebView webView1;
    private CheckedTextView zuixin;
    String shareUrl = "";
    String shareTitle = "";
    String shareContent = "";
    String shareImageUrl = "";
    String shareCommentUrl = "";
    String shareCommentTitle = "";
    String shareCommentContent = "";
    String shareCommentImageUrl = "";
    String is_collect = "0";
    String is_share = "0";
    String is_read = "0";
    String child_id = "";
    String is_comment_share = "0";
    Boolean is_comment = Boolean.FALSE;
    public String commnum = "0";
    public boolean is_onclick = false;
    public ArrayList<String> imgstr = new ArrayList<>();
    public String pub_url = "";
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private int positionTab = 0;
    private boolean mHasConfigAd = false;
    private boolean isCollection = false;
    private String mAppId = "";
    private boolean isNoMoreData = false;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 != 2) {
                if (i2 != 3) {
                    return;
                }
                HandoutsInfoActivity.this.getPushArticle();
                return;
            }
            Bundle bundle = (Bundle) msg.obj;
            bundle.putInt("result", 1);
            if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                HandoutsInfoActivity.this.pushComment(bundle);
                return;
            }
            Intent intent = new Intent(HandoutsInfoActivity.this, (Class<?>) CorpCupActivity.class);
            intent.putExtra("bundleIntent", bundle);
            HandoutsInfoActivity.this.startActivityForResult(intent, 1);
        }
    };
    private int pageNum = 1;
    private UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.13
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
            HandoutsInfoActivity.this.AlertToast("用户取消分享");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            HandoutsInfoActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            AlertDialog alertDialog;
            HandoutsInfoActivity.this.AlertToast("分享成功");
            if (arg0 == SHARE_MEDIA.QQ) {
                AlertDialog alertDialog2 = HandoutsInfoActivity.this.alertDialog;
                if (alertDialog2 == null || !alertDialog2.isShowing()) {
                    return;
                }
                HandoutsInfoActivity.this.shareData();
                return;
            }
            if (arg0 == SHARE_MEDIA.WEIXIN) {
                AlertDialog alertDialog3 = HandoutsInfoActivity.this.alertDialog;
                if (alertDialog3 == null || !alertDialog3.isShowing()) {
                    return;
                }
                HandoutsInfoActivity.this.shareData();
                return;
            }
            if (arg0 == SHARE_MEDIA.SINA) {
                HandoutsInfoActivity.this.shareData();
            } else if (arg0 == SHARE_MEDIA.WEIXIN_CIRCLE && (alertDialog = HandoutsInfoActivity.this.alertDialog) != null && alertDialog.isShowing()) {
                HandoutsInfoActivity.this.shareData();
            }
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.activity.HandoutsInfoActivity$11, reason: invalid class name */
    public class AnonymousClass11 extends AjaxCallBack<String> {
        public AnonymousClass11() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFailure$1() {
            HandoutsInfoActivity.this.getAdConfig();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            HandoutsInfoActivity.this.getAdConfig();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            HandoutsInfoActivity.this.mRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.dc
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12241c.lambda$onFailure$1();
                }
            }, 500L);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass11) t2);
            try {
                JSONObject jSONObject = new JSONObject(t2);
                String str = "<html manifest='demo.appcache'><head><meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no'><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><meta name='format-detection' content='telephone=no'></head><script type='text/javascript'> function aOnClick( obj ){var aon = document.getElementsByTagName('a'); var alen = aon.length; for(var i=0;i<alen;i++){if(obj==aon[i]){javaScxript.getCoument(obj.getAttribute('url'),obj.getAttribute('aid'));}}};  function prictaction( obj ){ var image = document.getElementsByTagName('img'); var imglen = image.length; for(var i=0;i<imglen;i++){if(obj==image[i]){javaScxript.getPrictData(i);}}}; function wave( obj ){var divvideo = document.getElementsByTagName('div');var len = divvideo.length;for( var i = 0; i < len; i++ ){if( obj == divvideo[i] ){javaScxript.getVideoData(obj.getElementsByTagName('video')[0].getAttribute('video-url'));}}}   </script><body  class='android w_" + CommonUtil.getScreenWidth(HandoutsInfoActivity.this) + "'>" + (SkinManager.getCurrentSkinType(HandoutsInfoActivity.this.mContext) == 0 ? "<script type='text/javascript'> document.body.style.backgroundColor=\"#ffffff\"; </script>" : "<script type='text/javascript'> document.body.style.backgroundColor=\"#121622\";</script>");
                String strOptString = jSONObject.optString(TtmlNode.TAG_BODY);
                try {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.optString("title"));
                    HandoutsInfoActivity.this.title_str = jSONObject2.optString("title");
                    HandoutsInfoActivity.this.child_id = jSONObject2.optString("child_id");
                    if (HandoutsInfoActivity.this.title_str.equals("null")) {
                        HandoutsInfoActivity.this.title_str = "";
                    }
                    if (!HandoutsInfoActivity.this.title_str.equals("")) {
                        Matcher matcher = Pattern.compile(jSONObject2.optString("ref")).matcher(strOptString);
                        while (matcher.find()) {
                            strOptString = strOptString.replace(matcher.group(0), HandoutsInfoActivity.this.title_str + "&nbsp;&nbsp;");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    JSONArray jSONArray = new JSONArray(jSONObject.optString("img"));
                    if (jSONArray.length() > 0) {
                        HandoutsInfoActivity.this.imgstr.clear();
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                            HandoutsInfoActivity.this.imgstr.add(jSONObjectOptJSONObject.optString("src"));
                            Matcher matcher2 = Pattern.compile(jSONObjectOptJSONObject.optString("ref")).matcher(strOptString);
                            while (matcher2.find()) {
                                strOptString = strOptString.replace(matcher2.group(0), "<img onclick='prictaction(this)' src='" + jSONObjectOptJSONObject.optString("src") + "' alt='" + jSONObjectOptJSONObject.optString("alt") + "'/>");
                            }
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                try {
                    JSONArray jSONArray2 = new JSONArray(jSONObject.optString("ainfo"));
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        JSONObject jSONObjectOptJSONObject2 = jSONArray2.optJSONObject(i3);
                        Matcher matcher3 = Pattern.compile(jSONObjectOptJSONObject2.optString("ref")).matcher(strOptString);
                        while (matcher3.find()) {
                            strOptString = strOptString.replace(matcher3.group(0), "<a href='" + jSONObjectOptJSONObject2.optString("aurl") + "'>" + jSONObjectOptJSONObject2.optString("atitle") + "</a>");
                        }
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                HandoutsInfoActivity.this.mTotalHtml = str + strOptString + "</body></html>";
                HandoutsInfoActivity.this.webView1.getSettings().setDefaultTextEncodingName("utf-8");
                HandoutsInfoActivity.this.webView1.getSettings().setCacheMode(2);
                HandoutsInfoActivity.this.webView1.resumeTimers();
                HandoutsInfoActivity.this.webView1.setDrawingCacheEnabled(true);
                HandoutsInfoActivity.this.webView1.buildDrawingCache();
                HandoutsInfoActivity.this.webView1.buildLayer();
                HandoutsInfoActivity.this.webView1.getSettings().setJavaScriptEnabled(true);
                HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                handoutsInfoActivity.webView1.addJavascriptInterface(handoutsInfoActivity.new PrictData(handoutsInfoActivity), "javaScxript");
                ((WebView) HandoutsInfoActivity.this.findViewById(R.id.temp_webview)).loadDataWithBaseURL(null, HandoutsInfoActivity.this.mTotalHtml, "text/html", "utf-8", null);
                JSONObject jSONObjectOptJSONObject3 = jSONObject.optJSONObject("share");
                if (jSONObjectOptJSONObject3 != null) {
                    HandoutsInfoActivity.this.shareUrl = jSONObjectOptJSONObject3.optString(CommonParameter.SHARE_URL);
                    HandoutsInfoActivity.this.shareTitle = jSONObjectOptJSONObject3.optString(CommonParameter.SHARE_TITLE);
                    HandoutsInfoActivity.this.shareContent = jSONObjectOptJSONObject3.optString("share_desc");
                    HandoutsInfoActivity.this.shareImageUrl = jSONObjectOptJSONObject3.optString("share_img");
                }
                HandoutsInfoActivity.this.mRefreshLayout.autoRefreshAnimationOnly();
                HandoutsInfoActivity.this.mRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.cc
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11142c.lambda$onSuccess$0();
                    }
                }, 500L);
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.HandoutsInfoActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        final /* synthetic */ boolean val$isAddComment;

        public AnonymousClass4(final boolean val$isAddComment) {
            this.val$isAddComment = val$isAddComment;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            HandoutsInfoActivity.this.setRemen(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            HandoutsInfoActivity.this.getPushArticle();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            HandoutsInfoActivity.this.isLoadingData = false;
            if (HandoutsInfoActivity.this.pageNum == 1) {
                HandoutsInfoActivity.this.commlist.clear();
                HandoutsInfoActivity.this.time_lines.clear();
                if (HandoutsInfoActivity.this.commlist.size() > 0) {
                    HandoutsInfoActivity.this.AlertToast("请求服务器失败");
                    return;
                }
                CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                hotBean.setType(1);
                hotBean.setName("最新评论(0)");
                HandoutsInfoActivity.this.commlist.add(hotBean);
                CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                hotBean2.setId("");
                hotBean2.setContent("暂无评论");
                HandoutsInfoActivity.this.commlist.add(hotBean2);
                HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                HandoutsInfoActivity handoutsInfoActivity2 = HandoutsInfoActivity.this;
                Context context = handoutsInfoActivity2.mContext;
                List list = handoutsInfoActivity2.commlist;
                HandoutsInfoActivity handoutsInfoActivity3 = HandoutsInfoActivity.this;
                handoutsInfoActivity.mCommListAdapter = new HandoutsInfoAdapter(context, list, handoutsInfoActivity3, "0", handoutsInfoActivity3.mAppId);
                HandoutsInfoActivity.this.mListView.setAdapter((ListAdapter) HandoutsInfoActivity.this.mCommListAdapter);
                if (HandoutsInfoActivity.this.mListView.getHeaderViewsCount() == 0) {
                    HandoutsInfoActivity.this.mListView.addHeaderView(HandoutsInfoActivity.this.mHeaderView);
                }
            } else {
                HandoutsInfoActivity.this.addFooterView.setVisibility(8);
            }
            HandoutsInfoActivity.this.mRefreshLayout.finishRefresh(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass4) s2);
            HandoutsInfoActivity.this.isLoadingData = false;
            try {
                CommentBean commentBean = (CommentBean) new Gson().fromJson(s2, CommentBean.class);
                if (commentBean.getCode().equals("200")) {
                    HandoutsInfoActivity.this.hot = commentBean.getData().getHot();
                    HandoutsInfoActivity.this.time_line = commentBean.getData().getTime_line();
                    if (HandoutsInfoActivity.this.pageNum == 1) {
                        HandoutsInfoActivity.this.initCommentCount(commentBean.getData().getTime_line_total());
                        HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                        handoutsInfoActivity.positionTab = handoutsInfoActivity.hot.size();
                        HandoutsInfoActivity.this.commlist.clear();
                        HandoutsInfoActivity.this.time_lines.clear();
                        if (HandoutsInfoActivity.this.hot.size() > 0) {
                            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                            Iterator it = HandoutsInfoActivity.this.hot.iterator();
                            while (it.hasNext()) {
                                ((CommentBean.DataBean.HotBean) it.next()).setHot(true);
                            }
                            hotBean.setType(1);
                            hotBean.setHot(true);
                            hotBean.setName("最热评论(" + HandoutsInfoActivity.this.hot.size() + ")");
                            HandoutsInfoActivity.this.commlist.add(hotBean);
                            HandoutsInfoActivity.this.commlist.addAll(HandoutsInfoActivity.this.hot);
                            HandoutsInfoActivity.this.isSelect(false, false);
                        }
                        CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                        hotBean2.setType(1);
                        hotBean2.setName("最新评论(" + commentBean.getData().getTime_line_total() + ")");
                        HandoutsInfoActivity.this.commlist.add(hotBean2);
                        if (HandoutsInfoActivity.this.time_line.size() > 0) {
                            HandoutsInfoActivity.this.commlist.addAll(HandoutsInfoActivity.this.time_line);
                        } else {
                            CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                            hotBean3.setId("");
                            hotBean3.setContent("暂无评论");
                            HandoutsInfoActivity.this.commlist.add(hotBean3);
                        }
                        HandoutsInfoActivity.this.time_lines.addAll(HandoutsInfoActivity.this.time_line);
                        HandoutsInfoActivity handoutsInfoActivity2 = HandoutsInfoActivity.this;
                        HandoutsInfoActivity handoutsInfoActivity3 = HandoutsInfoActivity.this;
                        Context context = handoutsInfoActivity3.mContext;
                        List list = handoutsInfoActivity3.commlist;
                        List list2 = HandoutsInfoActivity.this.time_line;
                        String str = HandoutsInfoActivity.this.getIntent().getExtras().getString("article") + "";
                        HandoutsInfoActivity handoutsInfoActivity4 = HandoutsInfoActivity.this;
                        handoutsInfoActivity2.mCommListAdapter = new HandoutsInfoAdapter(context, list, list2, 3, "2", str, handoutsInfoActivity4, "0", handoutsInfoActivity4.mAppId, new HandoutsInfoAdapter.CircleInfoClickIml() { // from class: com.psychiatrygarden.activity.ec
                            @Override // com.psychiatrygarden.adapter.HandoutsInfoAdapter.CircleInfoClickIml
                            public final void doSectionClick(int i2) {
                                this.f12311a.lambda$onSuccess$0(i2);
                            }
                        });
                        HandoutsInfoActivity handoutsInfoActivity5 = HandoutsInfoActivity.this;
                        handoutsInfoActivity5.mCommListAdapter.setShowAd(handoutsInfoActivity5.mHasConfigAd && HandoutsInfoActivity.this.hot.size() >= 8, !HandoutsInfoActivity.this.mIsSdkAd);
                        HandoutsInfoActivity.this.mCommListAdapter.setItemClickActionListener(new HandoutsInfoAdapter.ItemActionListener() { // from class: com.psychiatrygarden.activity.fc
                            @Override // com.psychiatrygarden.adapter.HandoutsInfoAdapter.ItemActionListener
                            public final void updateBottom() {
                                this.f12349a.lambda$onSuccess$1();
                            }
                        });
                        HandoutsInfoActivity.this.mListView.setAdapter((ListAdapter) HandoutsInfoActivity.this.mCommListAdapter);
                        if (HandoutsInfoActivity.this.hot.size() >= 8 && HandoutsInfoActivity.this.mHasConfigAd && !HandoutsInfoActivity.this.mIsSdkAd) {
                            HandoutsInfoActivity.this.getCommentAd();
                        }
                        if (HandoutsInfoActivity.this.mListView.getHeaderViewsCount() == 0) {
                            HandoutsInfoActivity.this.mListView.addHeaderView(HandoutsInfoActivity.this.mHeaderView);
                        }
                    } else {
                        HandoutsInfoActivity.this.mListView.invalidateViews();
                        if (HandoutsInfoActivity.this.time_line.size() == 0) {
                            HandoutsInfoActivity.this.mTvNoData.setVisibility(0);
                            HandoutsInfoActivity.this.mLyLoading.setVisibility(8);
                            HandoutsInfoActivity.this.isNoMoreData = true;
                        } else {
                            HandoutsInfoActivity.this.commlist.addAll(HandoutsInfoActivity.this.time_line);
                            HandoutsInfoActivity.this.time_lines.addAll(HandoutsInfoActivity.this.time_line);
                            HandoutsInfoActivity handoutsInfoActivity6 = HandoutsInfoActivity.this;
                            handoutsInfoActivity6.mCommListAdapter.setRefeault(handoutsInfoActivity6.time_line);
                            HandoutsInfoActivity.this.mCommListAdapter.notifyDataSetChanged();
                        }
                    }
                    if (this.val$isAddComment) {
                        HandoutsInfoActivity.this.zuixin.performClick();
                    }
                } else if (HandoutsInfoActivity.this.pageNum == 1) {
                    HandoutsInfoActivity.this.commlist.clear();
                    HandoutsInfoActivity.this.time_lines.clear();
                    if (HandoutsInfoActivity.this.commlist.size() == 0) {
                        CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                        hotBean4.setType(1);
                        hotBean4.setName("最新评论(0)");
                        HandoutsInfoActivity.this.commlist.add(hotBean4);
                        CommentBean.DataBean.HotBean hotBean5 = new CommentBean.DataBean.HotBean();
                        hotBean5.setId("");
                        hotBean5.setContent("暂无评论");
                        HandoutsInfoActivity.this.commlist.add(hotBean5);
                        HandoutsInfoActivity handoutsInfoActivity7 = HandoutsInfoActivity.this;
                        HandoutsInfoActivity handoutsInfoActivity8 = HandoutsInfoActivity.this;
                        Context context2 = handoutsInfoActivity8.mContext;
                        List list3 = handoutsInfoActivity8.commlist;
                        HandoutsInfoActivity handoutsInfoActivity9 = HandoutsInfoActivity.this;
                        handoutsInfoActivity7.mCommListAdapter = new HandoutsInfoAdapter(context2, list3, handoutsInfoActivity9, "0", handoutsInfoActivity9.mAppId);
                        HandoutsInfoActivity.this.mListView.setAdapter((ListAdapter) HandoutsInfoActivity.this.mCommListAdapter);
                        if (HandoutsInfoActivity.this.mListView.getHeaderViewsCount() == 0) {
                            HandoutsInfoActivity.this.mListView.addHeaderView(HandoutsInfoActivity.this.mHeaderView);
                        }
                    } else {
                        HandoutsInfoActivity.this.AlertToast(commentBean.getMessage());
                    }
                } else {
                    HandoutsInfoActivity.this.AlertToast(commentBean.getMessage());
                    HandoutsInfoActivity.this.addFooterView.setVisibility(8);
                }
                HandoutsInfoActivity.this.mRefreshLayout.finishRefresh(true);
            } catch (Exception e2) {
                HandoutsInfoActivity.this.mRefreshLayout.finishRefresh(false);
                e2.printStackTrace();
                if (HandoutsInfoActivity.this.pageNum == 1) {
                    HandoutsInfoActivity.this.commlist.clear();
                    HandoutsInfoActivity.this.time_lines.clear();
                    CommentBean.DataBean.HotBean hotBean6 = new CommentBean.DataBean.HotBean();
                    hotBean6.setType(1);
                    hotBean6.setName("最新评论(0)");
                    HandoutsInfoActivity.this.commlist.add(hotBean6);
                    CommentBean.DataBean.HotBean hotBean7 = new CommentBean.DataBean.HotBean();
                    hotBean7.setId("");
                    hotBean7.setContent("暂无评论");
                    HandoutsInfoActivity.this.commlist.add(hotBean7);
                    HandoutsInfoActivity handoutsInfoActivity10 = HandoutsInfoActivity.this;
                    HandoutsInfoActivity handoutsInfoActivity11 = HandoutsInfoActivity.this;
                    Context context3 = handoutsInfoActivity11.mContext;
                    List list4 = handoutsInfoActivity11.commlist;
                    HandoutsInfoActivity handoutsInfoActivity12 = HandoutsInfoActivity.this;
                    handoutsInfoActivity10.mCommListAdapter = new HandoutsInfoAdapter(context3, list4, handoutsInfoActivity12, "0", handoutsInfoActivity12.mAppId);
                    HandoutsInfoActivity.this.mListView.setAdapter((ListAdapter) HandoutsInfoActivity.this.mCommListAdapter);
                    if (HandoutsInfoActivity.this.mListView.getHeaderViewsCount() == 0) {
                        HandoutsInfoActivity.this.mListView.addHeaderView(HandoutsInfoActivity.this.mHeaderView);
                    }
                } else {
                    HandoutsInfoActivity.this.addFooterView.setVisibility(8);
                }
            }
            HandoutsInfoActivity handoutsInfoActivity13 = HandoutsInfoActivity.this;
            if (handoutsInfoActivity13.mCommListAdapter == null || handoutsInfoActivity13.commlist.isEmpty()) {
                return;
            }
            Iterator it2 = HandoutsInfoActivity.this.commlist.iterator();
            while (it2.hasNext()) {
                if ("1".equals(((CommentBean.DataBean.HotBean) it2.next()).getIs_praise())) {
                    HandoutsInfoActivity.this.updateLikeIconStatus("1");
                    return;
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.HandoutsInfoActivity$7, reason: invalid class name */
    public class AnonymousClass7 extends WebViewClient {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageFinished$0(String str) {
            LogUtils.e("dark_web", "value===>" + str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPageFinished$1(String str) {
            LogUtils.e("dark_web", "value_foo===>" + str);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(0);
            HandoutsInfoActivity.this.webView1.evaluateJavascript("javascript:toggleDark(" + (SkinManager.getCurrentSkinType(HandoutsInfoActivity.this.mContext) == 1) + ")", new ValueCallback() { // from class: com.psychiatrygarden.activity.gc
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    HandoutsInfoActivity.AnonymousClass7.lambda$onPageFinished$0((String) obj);
                }
            });
            HandoutsInfoActivity.this.webView1.evaluateJavascript("javascript:foo()", new ValueCallback() { // from class: com.psychiatrygarden.activity.hc
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    HandoutsInfoActivity.AnonymousClass7.lambda$onPageFinished$1((String) obj);
                }
            });
            super.onPageFinished(view, url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            HandoutsInfoActivity.this.webView1.loadUrl(url);
            return true;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.HandoutsInfoActivity$9, reason: invalid class name */
    public class AnonymousClass9 extends AjaxCallBack<String> {
        public AnonymousClass9() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            HandoutsInfoActivity.this.readData();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (CommonUtil.isNetworkConnected(HandoutsInfoActivity.this.mContext)) {
                HandoutsInfoActivity.this.AlertToast("请求失败");
            } else {
                HandoutsInfoActivity.this.AlertToast("网络连接失败，请检查网络");
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) throws NumberFormatException {
            JSONObject jSONObjectOptJSONObject;
            super.onSuccess((AnonymousClass9) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                    return;
                }
                jSONObjectOptJSONObject.optString("comment_count");
                HandoutsInfoActivity.this.is_comment_share = jSONObjectOptJSONObject.optString("is_comment_share");
                try {
                    String strOptString = jSONObjectOptJSONObject.optString("comment_count");
                    if (strOptString.length() == 0 || "null".equals(strOptString) || PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL.equals(strOptString)) {
                        HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText("0");
                    } else {
                        int i2 = Integer.parseInt(jSONObjectOptJSONObject.optString("comment_count"));
                        if (i2 > 10000) {
                            HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                        } else {
                            HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.valueOf(i2));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(jSONObjectOptJSONObject.optString("comment_count") + "");
                }
                HandoutsInfoActivity.this.is_share = jSONObjectOptJSONObject.optString("is_share");
                HandoutsInfoActivity.this.is_read = jSONObjectOptJSONObject.optString("is_read");
                jSONObjectOptJSONObject.optString("is_comment");
                jSONObjectOptJSONObject.optString("is_praise");
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("item_id", "" + HandoutsInfoActivity.this.article);
                ajaxParams.put(UriUtil.QUERY_CATEGORY, "2");
                MemInterface.getInstance().setCategory("2");
                MemInterface.getInstance().setItem_id(HandoutsInfoActivity.this.article);
                MemInterface.getInstance().setDisType(0);
                MemInterface.getInstance().getMemData(HandoutsInfoActivity.this, ajaxParams, false, 0);
                MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.ic
                    @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                    public final void mUShareListener() {
                        this.f12506a.lambda$onSuccess$0();
                    }
                });
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public class PrictData {
        Context context;

        public PrictData(Context context) {
            this.context = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getPrictData$0(int i2) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(HandoutsInfoActivity.this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.setImageUrls(new ArrayList(HandoutsInfoActivity.this.imgstr)).setSrcView(null, i2).show();
        }

        @JavascriptInterface
        public void getCoument(String url, String aid) {
            if (!url.equals(HandoutsInfoActivity.this.pub_url)) {
                HandoutsInfoActivity.this.getcommnum(url, aid);
                return;
            }
            HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
            handoutsInfoActivity.is_onclick = true;
            handoutsInfoActivity.getDataValue(url);
        }

        @JavascriptInterface
        public void getPrictData(final int postion) {
            HandoutsInfoActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.jc
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12554c.lambda$getPrictData$0(postion);
                }
            });
        }

        @JavascriptInterface
        public void getVideoData(String videourl) {
            Intent intent = new Intent(HandoutsInfoActivity.this.mContext, (Class<?>) VideoRePlayActivity.class);
            intent.putExtra("video_url", videourl);
            HandoutsInfoActivity.this.mContext.startActivity(intent);
        }

        @JavascriptInterface
        public void getVideoDataTxt(String videourl) {
        }
    }

    public static /* synthetic */ int access$508(HandoutsInfoActivity handoutsInfoActivity) {
        int i2 = handoutsInfoActivity.pageNum;
        handoutsInfoActivity.pageNum = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectionMsgUI(boolean isCollection) {
        if (isCollection) {
            this.questiondetails_btn_collect.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_yes_night : R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_no_night : R.drawable.icon_collect_no);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commentMsgUI(boolean isComment) {
        if (isComment) {
            this.questiondetails_btn_centerMsg.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youpinglun_night : R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.question_msg_night : R.drawable.question_msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAdConfig() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.QUESTION_AD_CONFIG, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.17
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                HandoutsInfoActivity.this.getCommentListData(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass17) s2);
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
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), HandoutsInfoActivity.this.getApplicationContext());
                                    HandoutsInfoActivity.this.mIsSdkAd = false;
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                    HandoutsInfoActivity.this.getCommentListData(false);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    HandoutsInfoActivity.this.getCommentListData(false);
                }
            }
        });
    }

    private void getBottomInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", this.article);
        ajaxParams.put("module_type", "3");
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getForumBottomStatus, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.19
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass19) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("is_collection");
                        String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("is_praise");
                        String strOptString3 = new JSONObject(s2).optJSONObject("data").optString("is_comment");
                        String strOptString4 = new JSONObject(s2).optJSONObject("data").optString("comment_count");
                        HandoutsInfoActivity.this.isCollection = (TextUtils.isEmpty(strOptString) || strOptString.equals("0")) ? false : true;
                        boolean z2 = (TextUtils.isEmpty(strOptString2) || strOptString2.equals("0")) ? false : true;
                        boolean z3 = (TextUtils.isEmpty(strOptString3) || strOptString3.equals("0")) ? false : true;
                        HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                        handoutsInfoActivity.is_collect = strOptString;
                        handoutsInfoActivity.praiseMsgUI(z2);
                        HandoutsInfoActivity handoutsInfoActivity2 = HandoutsInfoActivity.this;
                        handoutsInfoActivity2.collectionMsgUI(handoutsInfoActivity2.isCollection);
                        HandoutsInfoActivity.this.commentMsgUI(z3);
                        if (TextUtils.isEmpty(strOptString4)) {
                            HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText("0");
                            return;
                        }
                        int i2 = Integer.parseInt(strOptString4);
                        if (i2 > 10000) {
                            HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                        } else {
                            HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.valueOf(i2));
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
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.18
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
                        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, HandoutsInfoActivity.this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, HandoutsInfoActivity.this.mContext, 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0 || HandoutsInfoActivity.this.commlist.size() <= 8 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                            return;
                        }
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setAds(jSONObject.optString("data"));
                        if (HandoutsInfoActivity.this.hot == null || HandoutsInfoActivity.this.hot.size() < 8) {
                            return;
                        }
                        boolean z2 = true;
                        hotBean.setHot(true);
                        HandoutsInfoActivity.this.commlist.add(9, hotBean);
                        HandoutsInfoActivity.this.mHasConfigAd = true;
                        HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                        HandoutsInfoAdapter handoutsInfoAdapter = handoutsInfoActivity.mCommListAdapter;
                        boolean z3 = handoutsInfoActivity.mHasConfigAd && HandoutsInfoActivity.this.hot.size() >= 8;
                        if (HandoutsInfoActivity.this.mIsSdkAd) {
                            z2 = false;
                        }
                        handoutsInfoAdapter.setShowAd(z3, z2);
                        HandoutsInfoActivity handoutsInfoActivity2 = HandoutsInfoActivity.this;
                        handoutsInfoActivity2.mCommListAdapter.setList(handoutsInfoActivity2.commlist);
                        HandoutsInfoActivity.this.mCommListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData(boolean isAddComment) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.pageNum));
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("module_type", "3");
        ajaxParams.put("only_author", "0");
        ajaxParams.put("obj_id", this.article);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        if (this.pageNum == 1) {
            this.break_point = (System.currentTimeMillis() / 1000) + "";
        }
        if (this.pageNum > 1) {
            this.addFooterView.setVisibility(0);
        } else {
            this.addFooterView.setVisibility(8);
            this.mTvNoData.setVisibility(8);
            this.mLyLoading.setVisibility(0);
        }
        ajaxParams.put("break_point", this.break_point);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mCommentList, ajaxParams, new AnonymousClass4(isAddComment));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPushArticle() {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mGetJINGYANDataUrl;
        ajaxParams.put("article_id", this.article);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AnonymousClass9());
    }

    private void getShareData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mCommentShareInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.15
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass15) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        HandoutsInfoActivity.this.shareCommentUrl = new JSONObject(s2).optJSONObject("data").optString(CommonParameter.SHARE_URL);
                        HandoutsInfoActivity.this.shareCommentTitle = new JSONObject(s2).optJSONObject("data").optString(CommonParameter.SHARE_TITLE);
                        HandoutsInfoActivity.this.shareCommentContent = new JSONObject(s2).optJSONObject("data").optString("share_desc");
                        HandoutsInfoActivity.this.shareCommentImageUrl = new JSONObject(s2).optJSONObject("data").optString("share_img");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static void i(String tag, String msg) {
        int length = 2001 - tag.length();
        while (msg.length() > length) {
            Log.i(tag, msg.substring(0, length));
            msg = msg.substring(length);
        }
        Log.i(tag, msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCommentCount(String comment) throws NumberFormatException {
        this.commnum = comment;
        try {
            int i2 = Integer.parseInt(comment);
            if (i2 > 10000) {
                this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
            } else {
                this.questiondetails_btn_commentNum.setText(String.valueOf(i2));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.questiondetails_btn_commentNum.setText(getIntent().getExtras().getString("comm_count") + "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        if (this.shareUrl.equals("")) {
            AlertToast("数据加载失败，请重新加载");
        } else {
            shareDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        EventBus.getDefault().post(new HandCollectBean(this.index, this.is_collect));
        getCollection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", "" + str);
        bundle.putString("b_img", "" + str2);
        bundle.putString("s_img", "" + str3);
        Message message = new Message();
        message.what = 2;
        this.strValue = str;
        message.obj = bundle;
        this.mHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.tb
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f13949a.lambda$init$7(str, str2, str3);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$9() {
        EventBus.getDefault().post("NewsFragment");
        this.is_comment_share = "1";
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_commentNum.setTextColor(ContextCompat.getColorStateList(this.mContext, R.color.white));
        } else {
            this.questiondetails_btn_commentNum.setTextColor(ContextCompat.getColorStateList(this.mContext, R.color.comment_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        setRemen(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(View view) {
        setRemen(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$2(RefreshLayout refreshLayout) {
        this.addFooterView.setVisibility(8);
        this.pageNum = 1;
        this.isNoMoreData = false;
        getCommentListData(false);
        getBottomInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setContentView$3(View view) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRemen$4(int i2) {
        this.mListView.setSelection(i2 + 1);
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
    public void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("parent_id", b3.getString("parent_id"));
        ajaxParams.put("reply_primary_id", b3.getString("reply_primary_id"));
        ajaxParams.put("to_user_id", b3.getString("to_user_id"));
        ajaxParams.put("obj_id", this.article);
        ajaxParams.put("module_type", "3");
        ajaxParams.put("comment_type", "2");
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
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0084 -> B:22:0x00c0). Please report as a decompilation issue!!! */
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.PINGLUNTXT, "", HandoutsInfoActivity.this.mContext);
                        HandoutsInfoActivity.this.AlertToast(jSONObject.optString("message"));
                        HandoutsInfoActivity.this.pageNum = 1;
                        HandoutsInfoActivity.this.getCommentListData(true);
                        try {
                            int i2 = Integer.parseInt(HandoutsInfoActivity.this.commnum) + 1;
                            HandoutsInfoActivity.this.commentMsgUI(true);
                            if (i2 > 10000) {
                                HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                            } else {
                                HandoutsInfoActivity.this.questiondetails_btn_commentNum.setText(String.valueOf(i2));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                            handoutsInfoActivity.questiondetails_btn_commentNum.setText(handoutsInfoActivity.commnum);
                        }
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(HandoutsInfoActivity.this.mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        HandoutsInfoActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRemen(int type) {
        final int i2 = 0;
        if (type == 0) {
            isSelect(true, false);
            this.mListView.setSelection(1);
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
        this.mListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.zb
            @Override // java.lang.Runnable
            public final void run() {
                this.f14243c.lambda$setRemen$4(i2);
            }
        }, 310L);
    }

    @SuppressLint({"NewApi"})
    private void shareDialog() {
        new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.qb
            @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
            public final void onclickIntBack(int i2) {
                this.f13732a.shareAppControl(i2);
            }
        }, true).show();
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

    public void getCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", this.article);
        ajaxParams.put("is_collection", this.isCollection ? "0" : "1");
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mAddCollectionUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass5) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                        handoutsInfoActivity.isCollection = !handoutsInfoActivity.isCollection;
                        HandoutsInfoActivity handoutsInfoActivity2 = HandoutsInfoActivity.this;
                        handoutsInfoActivity2.collectionMsgUI(handoutsInfoActivity2.isCollection);
                        EventBus.getDefault().post(EventBusConstant.EVENT_JINGYAN_COLLECT);
                        HandoutsInfoActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                    EventBus eventBus = EventBus.getDefault();
                    HandoutsInfoActivity handoutsInfoActivity3 = HandoutsInfoActivity.this;
                    eventBus.post(new HandCollectBean(handoutsInfoActivity3.index, handoutsInfoActivity3.is_collect));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getDataValue(String urlStr) {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.is_onclick) {
            this.is_onclick = false;
        } else {
            urlStr = getIntent().getBooleanExtra("imagCateValue", false) ? getIntent().getExtras().getString("url") : getIntent().getExtras().getString("json_path");
        }
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        this.pub_url = urlStr;
        YJYHttpUtils.getmd5(this.mContext, urlStr, ajaxParams, new AnonymousClass11());
    }

    public void getcommnum(final String url, String aid) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", aid);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, "", ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                Intent intent = new Intent(HandoutsInfoActivity.this.mContext, (Class<?>) HandoutsInfoActivity.class);
                intent.putExtra("imagCateValue", true);
                intent.putExtra("url", url);
                intent.putExtra("comm_count", "0");
                HandoutsInfoActivity.this.startActivity(intent);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass12) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        HandoutsInfoActivity.this.commnum = jSONObject.optJSONObject("data").optString("comment_count");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                Intent intent = new Intent(HandoutsInfoActivity.this.mContext, (Class<?>) HandoutsInfoActivity.class);
                intent.putExtra("imagCateValue", true);
                intent.putExtra("url", url);
                intent.putExtra("comm_count", HandoutsInfoActivity.this.commnum);
                HandoutsInfoActivity.this.startActivity(intent);
            }
        });
    }

    public void geth5ShareData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", this.article);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getH5ShareUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                    if (jSONObjectOptJSONObject != null) {
                        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("share");
                        if (jSONObjectOptJSONObject2 != null) {
                            HandoutsInfoActivity.this.shareUrl = jSONObjectOptJSONObject2.optString(CommonParameter.SHARE_URL);
                            HandoutsInfoActivity.this.shareTitle = jSONObjectOptJSONObject2.optString(CommonParameter.SHARE_TITLE);
                            HandoutsInfoActivity.this.shareContent = jSONObjectOptJSONObject2.optString("share_desc");
                            HandoutsInfoActivity.this.shareImageUrl = jSONObjectOptJSONObject2.optString("share_img");
                        }
                        JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject.optJSONObject("comment_share");
                        if (jSONObjectOptJSONObject3 != null) {
                            HandoutsInfoActivity.this.shareCommentUrl = jSONObjectOptJSONObject3.optString(CommonParameter.SHARE_URL);
                            HandoutsInfoActivity.this.shareCommentTitle = jSONObjectOptJSONObject3.optString(CommonParameter.SHARE_TITLE);
                            HandoutsInfoActivity.this.shareCommentContent = jSONObjectOptJSONObject3.optString("share_desc");
                            HandoutsInfoActivity.this.shareCommentImageUrl = jSONObjectOptJSONObject3.optString("share_img");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("经验");
        this.btn_comment = (TextView) findViewById(R.id.btn_comment);
        this.questiondetails_btn_commentNum = (TextView) findViewById(R.id.questiondetails_btn_commentNum);
        this.questiondetails_btn_collect = (AlphaImageView) findViewById(R.id.questiondetails_btn_collect);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_collect);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ll_question_comment);
        this.questiondetails_btn_zantong = (ImageView) findViewById(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) findViewById(R.id.questiondetails_btn_centerMsg);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_zantong);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_centerMsg);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        this.questiondetails_btn_share = (AlphaImageView) findViewById(R.id.questiondetails_btn_share);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.ly_questiondetails_btn_share);
        try {
            final RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.root_view);
            ((WebView) findViewById(R.id.temp_webview)).setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.6
                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    HandoutsInfoActivity.this.contentHeight = (int) (view.getContentHeight() * view.getScale());
                    view.destroy();
                    relativeLayout2.removeView(view);
                    HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                    WebView webView = handoutsInfoActivity.webView1;
                    if (webView != null) {
                        webView.loadDataWithBaseURL(null, handoutsInfoActivity.mTotalHtml, "text/html", "utf-8", null);
                    }
                }
            });
            this.webView1.setWebViewClient(new AnonymousClass7());
            if (TextUtils.equals(this.is_rich_text, "1")) {
                this.webView1.getSettings().setDefaultTextEncodingName("utf-8");
                this.webView1.resumeTimers();
                this.webView1.setDrawingCacheEnabled(true);
                this.webView1.buildDrawingCache();
                this.webView1.buildLayer();
                this.webView1.getSettings().setJavaScriptEnabled(true);
                this.webView1.loadUrl(this.h5_path);
                geth5ShareData();
                this.mRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.vb
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f14022c.getAdConfig();
                    }
                }, 500L);
            } else {
                getDataValue("");
                getShareData();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            getDataValue("");
        }
        linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14144c.lambda$init$5(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14178c.lambda$init$6(view);
            }
        });
        relativeLayout.setOnClickListener(this);
        this.questiondetails_btn_commentNum.setOnClickListener(this);
        this.btn_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14212c.lambda$init$8(view);
            }
        });
        this.mHandler.sendEmptyMessage(3);
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

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int arg1, Intent data) {
        super.onActivityResult(requestCode, arg1, data);
        UMShareAPI.get(this.mContext).onActivityResult(requestCode, arg1, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0 || requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.ll_question_comment /* 2131364860 */:
            case R.id.questiondetails_btn_commentNum /* 2131366219 */:
                EventBus.getDefault().post(new HandShareBean(this.index, this.is_share));
                if (!this.is_comment_share.equals("1")) {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("item_id", "" + this.article);
                    ajaxParams.put(UriUtil.QUERY_CATEGORY, "3");
                    MemInterface.getInstance().setCategory("3");
                    MemInterface.getInstance().setItem_id(this.article);
                    MemInterface.getInstance().setDisType(1);
                    MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
                    MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.ub
                        @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                        public final void mUShareListener() {
                            this.f13982a.lambda$onClick$9();
                        }
                    });
                    break;
                } else if (this.commlist.size() <= 0) {
                    this.pageNum = 1;
                    getCommentListData(false);
                    break;
                } else {
                    this.remen.performClick();
                    break;
                }
            case R.id.ly_questiondetails_btn_centerMsg /* 2131365233 */:
                Intent intent = new Intent(this, (Class<?>) CommentNewActivity.class);
                intent.putExtra("article", this.article);
                intent.putExtra("module_type", 3);
                intent.putExtra("comment_type", "2");
                intent.putExtra("isNewCom", true);
                intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
                intent.putExtra("iscomValu", true);
                intent.putExtra("app_id", this.mAppId);
                startActivity(intent);
                break;
            case R.id.ly_questiondetails_btn_zantong /* 2131365237 */:
                Intent intent2 = new Intent(this, (Class<?>) CommentNewActivity.class);
                intent2.putExtra("article", this.article);
                intent2.putExtra("module_type", 3);
                intent2.putExtra("comment_type", "2");
                intent2.putExtra("isNewComzantong", true);
                intent2.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
                intent2.putExtra("iscomValu", true);
                intent2.putExtra("app_id", this.mAppId);
                startActivity(intent2);
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantUtil.mFontIndex = 2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        WebView webView = this.webView1;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.webView1);
            }
            this.webView1.stopLoading();
            this.webView1.getSettings().setJavaScriptEnabled(false);
            this.webView1.clearHistory();
            this.webView1.clearView();
            this.webView1.removeAllViews();
            this.webView1.destroy();
            this.webView1 = null;
        }
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("delReplyAndLoadData".equals(str)) {
            this.pageNum = 1;
            getCommentListData(false);
            getBottomInfo();
        } else if ("mCommentResult".equals(str) || "refresh_praise".equals(str) || TextUtils.equals(EventBusConstant.EVENT_REFRESH_COMMENT_NUM, str)) {
            getBottomInfo();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onPause();
        try {
            this.webView1.getClass().getMethod("onPause", new Class[0]).invoke(this.webView1, null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onResume();
        try {
            this.webView1.getClass().getMethod("onResume", new Class[0]).invoke(this.webView1, null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void readData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", this.article);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mIsReadStatusUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.16
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
                super.onSuccess((AnonymousClass16) t2);
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        HandoutsInfoActivity.this.is_share = "1";
                        EventBus eventBus = EventBus.getDefault();
                        HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                        eventBus.post(new HandShareBean(handoutsInfoActivity.index, handoutsInfoActivity.is_share));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setNewStyleStatusBarColor();
        setNavBarColor();
        setContentView(R.layout.activity_handouts_info);
        try {
            this.index = getIntent().getStringExtra("index");
        } catch (Exception unused) {
            this.index = String.valueOf(getIntent().getIntExtra("index", 0));
        }
        this.article = getIntent().getExtras().getString("article");
        this.h5_path = getIntent().getExtras().getString("h5_path");
        this.is_rich_text = getIntent().getExtras().getString("is_rich_text");
        this.mAppId = getIntent().getStringExtra("app_id");
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh);
        this.mListView = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.lineselect = (LinearLayout) findViewById(R.id.lineselect2);
        this.remen = (CheckedTextView) findViewById(R.id.remen2);
        this.zuixin = (CheckedTextView) findViewById(R.id.zuixin2);
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ac
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10989c.lambda$setContentView$0(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.bc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11107c.lambda$setContentView$1(view);
            }
        });
        isSelect(true, false);
        this.mHeaderView = View.inflate(this, R.layout.header_handouts_info, null);
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.mListView.addFooterView(this.addFooterView);
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (HandoutsInfoActivity.this.positionTab <= 0) {
                    if (firstVisibleItem >= 1) {
                        HandoutsInfoActivity.this.isSelect(false, true);
                        return;
                    } else {
                        HandoutsInfoActivity.this.isSelect(true, false);
                        return;
                    }
                }
                if (firstVisibleItem > HandoutsInfoActivity.this.positionTab + 1) {
                    HandoutsInfoActivity.this.isSelect(false, true);
                    HandoutsInfoActivity.this.lineselect.setVisibility(0);
                } else if (firstVisibleItem < 1 || firstVisibleItem > HandoutsInfoActivity.this.positionTab + 1) {
                    HandoutsInfoActivity.this.lineselect.setVisibility(8);
                    HandoutsInfoActivity.this.isSelect(false, false);
                } else {
                    HandoutsInfoActivity.this.lineselect.setVisibility(0);
                    HandoutsInfoActivity.this.isSelect(true, false);
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    int count = (view.getCount() - 1) - view.getFirstVisiblePosition();
                    LogUtils.e("dis_item_count", "差值：" + count);
                    if (count > 6 || HandoutsInfoActivity.this.isNoMoreData || HandoutsInfoActivity.this.isLoadingData) {
                        return;
                    }
                    HandoutsInfoActivity.this.addFooterView.setVisibility(0);
                    HandoutsInfoActivity.access$508(HandoutsInfoActivity.this);
                    HandoutsInfoActivity.this.isLoadingData = true;
                    HandoutsInfoActivity.this.getCommentListData(false);
                }
            }
        });
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.rb
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f13794c.lambda$setContentView$2(refreshLayout);
            }
        });
        this.webView1 = (WebView) this.mHeaderView.findViewById(R.id.webView1);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.webView1.setVisibility(0);
        } else {
            this.webView1.setVisibility(8);
        }
        this.webView1.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.activity.sb
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return HandoutsInfoActivity.lambda$setContentView$3(view);
            }
        });
        WebView.setWebContentsDebuggingEnabled(true);
        this.progressBar2 = (ProgressBar) this.mHeaderView.findViewById(R.id.progressBar2);
        this.webView1.setWebChromeClient(new WebChromeClient() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.3
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 80) {
                    HandoutsInfoActivity.this.progressBar2.setVisibility(8);
                } else {
                    HandoutsInfoActivity.this.progressBar2.setVisibility(0);
                }
            }
        });
        getBottomInfo();
    }

    @Override // com.psychiatrygarden.widget.DialogAttrShare.callBack
    public void setFontSize(float font, int index) {
        this.webView1.getSettings().setSupportZoom(true);
        if (index == 0) {
            this.webView1.getSettings().setTextZoom(50);
            ConstantUtil.mFontIndex = 0;
            return;
        }
        if (index == 1) {
            this.webView1.getSettings().setTextZoom(75);
            ConstantUtil.mFontIndex = 1;
            return;
        }
        if (index == 2) {
            this.webView1.getSettings().setTextZoom(100);
            ConstantUtil.mFontIndex = 2;
        } else if (index == 3) {
            this.webView1.getSettings().setTextZoom(150);
            ConstantUtil.mFontIndex = 3;
        } else {
            if (index != 4) {
                return;
            }
            this.webView1.getSettings().setTextZoom(200);
            ConstantUtil.mFontIndex = 4;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void shareAppControl(int i2) {
        if (i2 == 4 || i2 == 5) {
            if (i2 != 4) {
                if (i2 != 5) {
                    return;
                }
                DialogAttrShare dialogAttrShare = new DialogAttrShare(this.mContext);
                dialogAttrShare.setCallback(this);
                dialogAttrShare.show();
                return;
            }
            SkinManager.changeSkin(this);
            ProjectApp.isjingyan = true;
            Intent intent = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
            intent.putExtra("article", getIntent().getExtras().getString("article"));
            intent.putExtra("json_path", getIntent().getExtras().getString("json_path"));
            intent.putExtra("html_path", getIntent().getExtras().getString("html_path"));
            intent.putExtra("h5_path", getIntent().getExtras().getString("h5_path"));
            intent.putExtra("is_rich_text", getIntent().getExtras().getString("is_rich_text"));
            intent.putExtra("index", getIntent().getExtras().getString("index"));
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
            return;
        }
        if (this.is_comment.booleanValue()) {
            if (this.shareCommentUrl.equals("")) {
                AlertToast("数据加载失败，请重新加载" + i2);
                return;
            }
            UMImage uMImage = new UMImage(this.mContext, this.shareCommentImageUrl);
            if (this.shareCommentImageUrl.equals("")) {
                uMImage = new UMImage(this.mContext, R.drawable.app_icon);
            }
            UMWeb uMWeb = new UMWeb(this.shareCommentUrl);
            uMWeb.setTitle(this.shareCommentTitle);
            uMWeb.setDescription(this.shareCommentContent);
            uMWeb.setThumb(uMImage);
            if (i2 == 0 || i2 == 1) {
                shareData();
            }
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
            return;
        }
        if (this.shareUrl.equals("")) {
            AlertToast("数据加载失败，请重新加载" + i2);
            return;
        }
        UMImage uMImage2 = new UMImage(this.mContext, this.shareImageUrl);
        if (this.shareImageUrl.equals("")) {
            uMImage2 = new UMImage(this.mContext, R.drawable.app_icon);
        }
        UMWeb uMWeb2 = new UMWeb(this.shareUrl);
        if (i2 == 3) {
            uMWeb2.setDescription(this.shareTitle);
        } else {
            uMWeb2.setTitle(this.shareTitle);
            uMWeb2.setDescription(this.shareContent);
        }
        uMWeb2.setThumb(uMImage2);
        if (i2 == 0 || i2 == 1) {
            shareData();
        }
        new ShareAction(this).withMedia(uMWeb2).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }

    public void shareData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("article_id", this.article);
        if (!TextUtils.isEmpty(this.mAppId)) {
            ajaxParams.put("app_id", this.mAppId);
        }
        YJYHttpUtils.post(this.mContext, this.is_comment.booleanValue() ? NetworkRequestsURL.mAddCommentShareUrl : NetworkRequestsURL.mAddShareUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.HandoutsInfoActivity.14
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
                super.onSuccess((AnonymousClass14) t2);
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        HandoutsInfoActivity.this.alertDialog.dismiss();
                        EventBus.getDefault().post("NewsFragment");
                        if (HandoutsInfoActivity.this.is_comment.booleanValue()) {
                            HandoutsInfoActivity handoutsInfoActivity = HandoutsInfoActivity.this;
                            handoutsInfoActivity.is_comment_share = "1";
                            if (SkinManager.getCurrentSkinType(handoutsInfoActivity.mContext) == 0) {
                                HandoutsInfoActivity.this.questiondetails_btn_commentNum.setTextColor(ContextCompat.getColorStateList(HandoutsInfoActivity.this.mContext, R.color.white));
                            } else {
                                HandoutsInfoActivity.this.questiondetails_btn_commentNum.setTextColor(ContextCompat.getColorStateList(HandoutsInfoActivity.this.mContext, R.color.comment_color_night));
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
