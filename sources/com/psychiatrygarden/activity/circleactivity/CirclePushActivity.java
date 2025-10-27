package com.psychiatrygarden.activity.circleactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicBase;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.circleactivity.PushCircleAdp;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ImageDataBean;
import com.psychiatrygarden.bean.LinkDataBean;
import com.psychiatrygarden.bean.TopicListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleEditSavePop;
import com.psychiatrygarden.widget.CircleRichEditor;
import com.psychiatrygarden.widget.NoticePopWindow;
import com.psychiatrygarden.widget.PushCircleDialog;
import com.psychiatrygarden.widget.PushCircleVoteDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.SelectTopicPopWindow;
import com.psychiatrygarden.widget.UrlLinkActivity;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.wasabeef.richeditor.RichEditor;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CirclePushActivity extends BaseActivity {
    private static final String REGEX_HTML = "<[^>]+>";
    public LinearLayout format_bar;
    ImageView include_btn_left;
    private ImageView mImgCloseVote;
    private LinearLayout mLyAddViewVote;
    private LinearLayout mLyVote;
    private PushCircleAdp mPushAdapter;
    private RecyclerView mPushRecycler;
    private TextView mTvVoteType;
    private CircleRichEditor sourceview_content;
    TextView step;
    public EditText title_circle;
    private TextView topicLabel;
    TextView yulan;
    private List<ImageDataBean> mImageList = new ArrayList();
    private List<LinkDataBean> mLinkDataList = new ArrayList();
    private final List<LinkDataBean> mLinkDataListTemp = new ArrayList();
    public String mStrHtml = "";
    public List<String> imageDataTemp = new ArrayList();
    private String topicId = "";
    private String topicName = "";
    long strLeth = 0;
    private boolean isEdit = false;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new AnonymousClass10();

    /* renamed from: com.psychiatrygarden.activity.circleactivity.CirclePushActivity$10, reason: invalid class name */
    public class AnonymousClass10 extends Handler {
        public AnonymousClass10() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0() {
            CirclePushActivity.this.sourceview_content.setFontView("#303030");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1() {
            CirclePushActivity.this.sourceview_content.setFontView("#7380A9");
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (TextUtils.isEmpty(CirclePushActivity.this.mStrHtml)) {
                    if (SkinManager.getCurrentSkinType(CirclePushActivity.this.mContext) == 0) {
                        CirclePushActivity.this.sourceview_content.setFontView("#BFBFBF");
                        return;
                    } else {
                        CirclePushActivity.this.sourceview_content.setFontView("#575F79");
                        return;
                    }
                }
                if (SkinManager.getCurrentSkinType(CirclePushActivity.this.mContext) == 0) {
                    CirclePushActivity.this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.j2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11551c.lambda$handleMessage$0();
                        }
                    });
                } else {
                    CirclePushActivity.this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.k2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11556c.lambda$handleMessage$1();
                        }
                    });
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.circleactivity.CirclePushActivity$15, reason: invalid class name */
    public class AnonymousClass15 extends AjaxCallBack<String> {
        public AnonymousClass15() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            CirclePushActivity.this.finish();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(String str) {
            try {
                CirclePushActivity.this.hideInputMethod();
                CirclePushActivity.this.title_circle.setText("");
                CirclePushActivity.this.sourceview_content.setHtml("");
                CirclePushActivity circlePushActivity = CirclePushActivity.this;
                circlePushActivity.mStrHtml = "";
                circlePushActivity.clearLocalCircleInfo();
                CommonUtil.showFristDialog(new JSONObject(str), new NoticePopWindow.NoticeClickIml() { // from class: com.psychiatrygarden.activity.circleactivity.m2
                    @Override // com.psychiatrygarden.widget.NoticePopWindow.NoticeClickIml
                    public final void mNoticeClick() {
                        this.f11566a.lambda$onSuccess$0();
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CirclePushActivity.this.AlertToast("发表失败！");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String s2) {
            super.onSuccess((AnonymousClass15) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    EventBus.getDefault().post("isRefaulfCircle");
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.l2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11560c.lambda$onSuccess$1(s2);
                        }
                    }, 500L);
                }
                CirclePushActivity.this.AlertToast(new JSONObject(s2).optString("message"));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearLocalCircleInfo() {
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleTitle, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleContent, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circlePushPost, "", this.mContext);
    }

    private void editArticle(String cids, String content) {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cid", cids);
        ajaxParams.put("type", getIntent().getExtras().getString("type"));
        if ("2".equals(getIntent().getExtras().getString("type"))) {
            ajaxParams.put("choice", getIntent().getExtras().getString("choice", "1"));
        }
        handleOptionsData(ajaxParams);
        ajaxParams.put("title", this.title_circle.getText().toString().trim());
        ajaxParams.put("content", content);
        ajaxParams.put(PLVErrorCodeLinkMicBase.LINK_MODULE, new Gson().toJson(this.mLinkDataListTemp));
        ajaxParams.put("img_url", new Gson().toJson(this.imageDataTemp));
        if (!"none".equals(this.topicId) && !"".equals(this.topicId)) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_ID, this.topicId);
        }
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumpostingApi;
            ajaxParams.put("id", getIntent().getExtras().getString("article_id"));
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        } else {
            str = NetworkRequestsURL.editArticle;
            ajaxParams.put("article_id", getIntent().getExtras().getString("article_id"));
            ArrayList arrayList = new ArrayList();
            Iterator<CirclrListBean.DataBean> it = this.mPushAdapter.getData().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getId());
            }
            if (arrayList.size() > 0) {
                ajaxParams.put("push_ids", new Gson().toJson(arrayList));
            }
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.14
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CirclePushActivity.this.AlertToast("发表失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass14) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post("isRefaulfCircleInfo");
                        CirclePushActivity.this.finish();
                    }
                    CirclePushActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> getVoteStr() {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mLyAddViewVote.getChildCount(); i2++) {
            if (this.mLyAddViewVote.getChildAt(i2) instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) this.mLyAddViewVote.getChildAt(i2);
                Object tag = relativeLayout.getTag();
                for (int i3 = 0; i3 < relativeLayout.getChildCount(); i3++) {
                    if (relativeLayout.getChildAt(i3) instanceof EditText) {
                        EditText editText = (EditText) relativeLayout.getChildAt(i3);
                        if (!TextUtils.isEmpty(editText.getText())) {
                            if (tag == null || !(tag instanceof CircleInfoBean.DataBean.OptionsBeanX.OptionsBean)) {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean = new CircleInfoBean.DataBean.OptionsBeanX.OptionsBean();
                                optionsBean.setId("0");
                                optionsBean.setOption(editText.getText().toString());
                                arrayList.add(optionsBean);
                            } else {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean2 = (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) tag;
                                optionsBean2.setOption(editText.getText().toString());
                                arrayList.add(optionsBean2);
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private void handleOptionsData(AjaxParams params) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mLyAddViewVote.getChildCount(); i2++) {
            if (this.mLyAddViewVote.getChildAt(i2) instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) this.mLyAddViewVote.getChildAt(i2);
                Object tag = relativeLayout.getTag();
                for (int i3 = 0; i3 < relativeLayout.getChildCount(); i3++) {
                    if (relativeLayout.getChildAt(i3) instanceof EditText) {
                        EditText editText = (EditText) relativeLayout.getChildAt(i3);
                        if (TextUtils.isEmpty(editText.getText())) {
                            continue;
                        } else {
                            if (editText.getText().toString().length() < 2 || editText.getText().toString().length() > 15) {
                                AlertToast("选项" + i2 + "长度不符合！");
                                return;
                            }
                            if (tag == null || !(tag instanceof CircleInfoBean.DataBean.OptionsBeanX.OptionsBean)) {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean = new CircleInfoBean.DataBean.OptionsBeanX.OptionsBean();
                                optionsBean.setId("0");
                                optionsBean.setOption(editText.getText().toString());
                                arrayList.add(optionsBean);
                            } else {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean2 = (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) tag;
                                optionsBean2.setOption(editText.getText().toString());
                                arrayList.add(optionsBean2);
                            }
                        }
                    }
                }
            }
        }
        if (arrayList.size() == 1) {
            AlertToast("投票选项必须大于等于2！");
            return;
        }
        if (arrayList.size() <= 0) {
            params.put("type", "1");
            return;
        }
        params.put("type", "2");
        params.put("options", "" + new Gson().toJson(arrayList));
        params.put("choice", this.mTvVoteType.getText().toString().equals("单选") ? "1" : "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionViews$35(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionViews$36(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$27() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$28() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            TextView textView = this.yulan;
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
        } else {
            TextView textView2 = this.yulan;
            textView2.setTextColor(ContextCompat.getColor(textView2.getContext(), R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$29() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            TextView textView = this.step;
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.qianred));
        } else {
            TextView textView2 = this.step;
            textView2.setTextColor(ContextCompat.getColor(textView2.getContext(), R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$30() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.qianred));
        } else {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$31() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$32() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$33() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.qianred));
        } else {
            this.step.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeColor$34() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.qianred));
        } else {
            this.yulan.setTextColor(ContextCompat.getColor(this, R.color.question_color_night));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        this.format_bar.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(boolean z2) {
        if (!z2) {
            this.format_bar.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.j1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11550c.lambda$init$9();
                }
            });
        } else {
            this.mHandler.sendEmptyMessage(0);
            this.format_bar.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.6
                @Override // java.lang.Runnable
                public void run() {
                    CirclePushActivity.this.format_bar.setVisibility(0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$11(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$12(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$13(String str) {
        this.mStrHtml = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$14(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        this.sourceview_content.insertLink(jCurrentThreadTimeMillis, str3, str2);
        LinkDataBean linkDataBean = new LinkDataBean();
        linkDataBean.setId(jCurrentThreadTimeMillis);
        linkDataBean.setTitle(str2);
        linkDataBean.setUrl(str3);
        this.mLinkDataList.add(linkDataBean);
        SharePreferencesUtils.saveLinkList(this, CommonParameter.linkdata, this.mLinkDataList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$15(View view) {
        new UrlLinkActivity(this.mContext, new UrlLinkActivity.OnDialogClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.u1
            @Override // com.psychiatrygarden.widget.UrlLinkActivity.OnDialogClickListener
            public final void onConfirmButtonClick(String str, String str2, String str3) {
                this.f11600a.lambda$init$14(str, str2, str3);
            }
        }, "", "", "").show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$16(View view) {
        final PushCircleDialog pushCircleDialog = new PushCircleDialog(this.mContext, this.mPushAdapter.getData());
        pushCircleDialog.setOnChoosedLisenter(new PushCircleDialog.OnChoosedLisenter() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.7
            @Override // com.psychiatrygarden.widget.PushCircleDialog.OnChoosedLisenter
            public void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<CirclrListBean.DataBean> mChooseCircleItems) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < CirclePushActivity.this.mPushAdapter.getData().size(); i2++) {
                    CirclrListBean.DataBean item = CirclePushActivity.this.mPushAdapter.getItem(i2);
                    if (oldCircleIds.contains(item.getId()) && !newCircleIds.contains(item.getId())) {
                        arrayList.add(item);
                    }
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    CirclePushActivity.this.mPushAdapter.remove((PushCircleAdp) arrayList.get(i3));
                }
                for (CirclrListBean.DataBean dataBean : mChooseCircleItems) {
                    if (!oldCircleIds.contains(dataBean.getId())) {
                        CirclePushActivity.this.mPushAdapter.addData((PushCircleAdp) dataBean);
                    }
                }
                pushCircleDialog.dismiss();
                CirclePushActivity.this.mPushRecycler.setVisibility(CirclePushActivity.this.mPushAdapter.getData().size() <= 0 ? 8 : 0);
            }
        });
        pushCircleDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$17(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        this.sourceview_content.changeLink(str3, str2);
        for (int i2 = 0; i2 < this.mLinkDataList.size(); i2++) {
            if (this.mLinkDataList.get(i2).getId() == Long.parseLong(str)) {
                this.mLinkDataList.get(i2).setUrl(str3);
                this.mLinkDataList.get(i2).setTitle(str2);
            }
        }
        SharePreferencesUtils.saveLinkList(this, CommonParameter.linkdata, this.mLinkDataList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$18(String str, String str2, String str3) {
        new UrlLinkActivity(this.mContext, new UrlLinkActivity.OnDialogClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.y0
            @Override // com.psychiatrygarden.widget.UrlLinkActivity.OnDialogClickListener
            public final void onConfirmButtonClick(String str4, String str5, String str6) {
                this.f11619a.lambda$init$17(str4, str5, str6);
            }
        }, str, str3, str2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$19() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view, boolean z2) {
        if (z2) {
            this.format_bar.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.c2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11501c.lambda$init$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$20(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        LogUtils.d("BitmapFactoryType", "图片类型1：" + mimeTypeFromFile);
        if (mimeTypeFromFile.toUpperCase().equals("IMAGE/WEBP")) {
            AlertToast("不支持此文件格式，请选择其它图片上传！");
        } else if (ImageFactory.getImageSize(str) > 10.0f) {
            NewToast.showShort(this.mContext, "请选择小于10M的图片上传", 0).show();
        } else {
            getImageData(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$21(View view) {
        if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.circleactivity.e2
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f11514a.lambda$init$19();
                }
            })).show();
        } else {
            AndroidImagePicker.getInstance().setSelectLimit(1);
            AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.circleactivity.f2
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public final void onImagePickComplete(List list) {
                    this.f11524a.lambda$init$20(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$22() {
        this.sourceview_content.setFontView("#303030");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$23() {
        this.sourceview_content.setFontView("#7380A9");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$24() {
        this.sourceview_content.setFontView("#303030");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$25() {
        this.sourceview_content.setFontView("#7380A9");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$26(View view) {
        this.mLyVote.setVisibility(8);
        this.mLyAddViewVote.removeAllViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$3(View view, MotionEvent motionEvent) {
        this.title_circle.findFocus();
        this.title_circle.setFocusable(true);
        this.title_circle.setFocusableInTouchMode(true);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        viewArticle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        dialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        pushTieziData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(View view) {
        getHeaderData(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(long j2) {
        this.strLeth = j2;
        changeColor();
        if (this.strLeth > 0) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.4
                    @Override // java.lang.Runnable
                    public void run() {
                        CirclePushActivity.this.sourceview_content.setFontView("#303030");
                    }
                });
            } else {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.5
                    @Override // java.lang.Runnable
                    public void run() {
                        CirclePushActivity.this.sourceview_content.setFontView("#7380A9");
                    }
                });
            }
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.sourceview_content.setFontView("#BFBFBF");
        } else {
            this.sourceview_content.setFontView("#575F79");
        }
        if (j2 > com.heytap.mcssdk.constant.a.f7153q) {
            AlertToast("超出内容长度！");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9() {
        this.format_bar.setVisibility(8);
    }

    private void showVoteDialog() {
        final PushCircleVoteDialog pushCircleVoteDialog = new PushCircleVoteDialog(this.mContext, getVoteStr(), this.mTvVoteType.getText().toString());
        pushCircleVoteDialog.setOnChoosedLisenter(new PushCircleVoteDialog.OnChoosedLisenter() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.9
            @Override // com.psychiatrygarden.widget.PushCircleVoteDialog.OnChoosedLisenter
            public void onCircleChoosed(List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> votes, String isSingle) {
                CirclePushActivity.this.mLyVote.setVisibility(0);
                CirclePushActivity.this.mLyAddViewVote.removeAllViews();
                CirclePushActivity.this.mTvVoteType.setText(isSingle.equals("1") ? "单选" : "多选");
                for (int i2 = 0; i2 < votes.size(); i2++) {
                    CirclePushActivity.this.addOptionViews(votes.get(i2));
                }
                pushCircleVoteDialog.dismiss();
            }
        });
        pushCircleVoteDialog.show();
    }

    public void addOptionViews(String content, CircleInfoBean.DataBean.OptionsBeanX.OptionsBean bean) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.layout_circle_option_new, (ViewGroup) null);
        relativeLayout.setTag(bean);
        EditText editText = (EditText) relativeLayout.findViewById(R.id.editId);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.deleEdit);
        RelativeLayout relativeLayout2 = (RelativeLayout) relativeLayout.findViewById(R.id.optionRel);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_content);
        imageView.setVisibility(4);
        editText.setVisibility(4);
        textView.setVisibility(0);
        textView.setText(bean.getOption());
        editText.setText(bean.getOption());
        if (this.mLyAddViewVote.getChildCount() > 1) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.s1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11590c.lambda$addOptionViews$35(view);
            }
        });
        this.mLyAddViewVote.addView(relativeLayout);
    }

    public void changeColor() {
        boolean z2;
        String strReplace;
        String strTrim = this.mStrHtml.replace("<div>", "\n").replace("</div>", "").replace("<br>", StrPool.TAB).replace("&nbsp;", "").replace("\n\t\t\t\t", "").replace(StrPool.CR, "").trim();
        Matcher matcher = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        boolean z3 = true;
        if (matcher.find()) {
            strReplace = strTrim.replace(matcher.group(), "");
            z2 = true;
        } else {
            z2 = false;
            strReplace = strTrim;
        }
        Matcher matcher2 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        if (matcher2.find()) {
            strReplace = strReplace.replace(matcher2.group(), "");
        } else {
            z3 = z2;
        }
        if (z3) {
            if (this.title_circle.getText().toString().replaceAll("\n", "").replaceAll(" ", "").toString().length() >= 10) {
                this.step.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.t1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11595c.lambda$changeColor$27();
                    }
                });
                this.yulan.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.v1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11606c.lambda$changeColor$28();
                    }
                });
                return;
            } else {
                this.step.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.w1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11612c.lambda$changeColor$29();
                    }
                });
                this.yulan.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.x1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11616c.lambda$changeColor$30();
                    }
                });
                return;
            }
        }
        if (this.title_circle.getText().toString().replaceAll("\n", "").replaceAll(" ", "").toString().length() < 10 || strReplace.replaceAll(" ", "").toString().length() < 10 || strReplace.replaceAll(" ", "").toString().length() >= 10000) {
            this.step.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.a2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11491c.lambda$changeColor$33();
                }
            });
            this.yulan.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.b2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11497c.lambda$changeColor$34();
                }
            });
        } else {
            this.step.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.y1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11620c.lambda$changeColor$31();
                }
            });
            this.yulan.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.z1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11624c.lambda$changeColor$32();
                }
            });
        }
    }

    public void dialog() {
        if ((TextUtils.isEmpty(this.title_circle.getText().toString()) && TextUtils.isEmpty(this.mStrHtml)) || this.isEdit) {
            finish();
        } else {
            hideInputMethod();
            new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).asCustom(new CircleEditSavePop(this.mContext, new CircleEditSavePop.ClickListenerEditSave() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.11
                @Override // com.psychiatrygarden.widget.CircleEditSavePop.ClickListenerEditSave
                public void noSave() {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    CirclePushActivity.this.clearLocalCircleInfo();
                    CirclePushActivity.this.finish();
                }

                @Override // com.psychiatrygarden.widget.CircleEditSavePop.ClickListenerEditSave
                public void save() {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.circleTitle, "" + CirclePushActivity.this.title_circle.getText().toString(), CirclePushActivity.this.mContext);
                    CirclePushActivity circlePushActivity = CirclePushActivity.this;
                    SharePreferencesUtils.writeStrConfig(CommonParameter.circleContent, circlePushActivity.mStrHtml, circlePushActivity.mContext);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.circlePushPost, new Gson().toJson(CirclePushActivity.this.mPushAdapter.getData()), CirclePushActivity.this.mContext);
                    CirclePushActivity.this.finish();
                }
            })).show();
        }
    }

    public void getHeaderData(final int type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "posting");
        YJYHttpUtils.get(this, NetworkRequestsURL.mTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.16

            /* renamed from: com.psychiatrygarden.activity.circleactivity.CirclePushActivity$16$1, reason: invalid class name */
            public class AnonymousClass1 implements SelectTopicPopWindow.SelecTopicIml {
                final /* synthetic */ TopicListBean val$topicListBean;

                public AnonymousClass1(final TopicListBean val$topicListBean) {
                    this.val$topicListBean = val$topicListBean;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$selecTopicData$0() {
                    ProjectApp.instance().hideDialogWindow();
                    CirclePushActivity.this.pushTieziData();
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$selecTopicData$1() {
                    ((InputMethodManager) CirclePushActivity.this.getSystemService("input_method")).toggleSoftInput(0, 2);
                }

                @Override // com.psychiatrygarden.widget.SelectTopicPopWindow.SelecTopicIml
                public void dismissOnData(List<TopicListBean.DataDTO> dataList) {
                    try {
                        if (this.val$topicListBean.getData() == null || this.val$topicListBean.getData().size() <= 0) {
                            return;
                        }
                        for (int i2 = 0; i2 < dataList.size(); i2++) {
                            if (!this.val$topicListBean.getData().get(i2).getId().equals(dataList.get(i2).getId())) {
                                CirclePushActivity.this.getmCustomTopicListData(new Gson().toJson(dataList));
                                return;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.psychiatrygarden.widget.SelectTopicPopWindow.SelecTopicIml
                public void selecTopicData(List<TopicListBean.DataDTO> dataList, int p2) {
                    CirclePushActivity.this.topicId = dataList.get(p2).getId();
                    CirclePushActivity.this.topicName = dataList.get(p2).getName();
                    CirclePushActivity.this.topicLabel.setText(CirclePushActivity.this.topicName);
                    AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                    if (type == 3) {
                        ProjectApp.instance().showDialogWindow();
                        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.n2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f11570c.lambda$selecTopicData$0();
                            }
                        }, 1000L);
                        return;
                    }
                    CirclePushActivity.this.title_circle.clearFocus();
                    CirclePushActivity.this.title_circle.setFocusable(false);
                    CirclePushActivity.this.title_circle.setFocusableInTouchMode(true);
                    CirclePushActivity.this.sourceview_content.focusEditor();
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.o2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11574c.lambda$selecTopicData$1();
                        }
                    }, 200L);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass16) s2);
                try {
                    TopicListBean topicListBean = (TopicListBean) new Gson().fromJson(s2, TopicListBean.class);
                    if (!topicListBean.getCode().equals("200")) {
                        CirclePushActivity.this.topicId = "none";
                        CirclePushActivity.this.topicLabel.setVisibility(8);
                        return;
                    }
                    int i2 = 0;
                    if (type == 1) {
                        while (i2 < topicListBean.getData().size()) {
                            if (CirclePushActivity.this.topicId.equals(topicListBean.getData().get(i2).getId())) {
                                CirclePushActivity.this.topicName = topicListBean.getData().get(i2).getName();
                                CirclePushActivity.this.topicLabel.setText(CirclePushActivity.this.topicName);
                                return;
                            }
                            i2++;
                        }
                        return;
                    }
                    CirclePushActivity.this.hideInputMethod();
                    CirclePushActivity.this.topicLabel.setVisibility(0);
                    ArrayList arrayList = new ArrayList();
                    while (i2 < topicListBean.getData().size()) {
                        TopicListBean.DataDTO dataDTO = new TopicListBean.DataDTO();
                        dataDTO.setId(topicListBean.getData().get(i2).getId() + "");
                        dataDTO.setName(topicListBean.getData().get(i2).getName() + "");
                        dataDTO.setIs_default(topicListBean.getData().get(i2).getIs_default());
                        arrayList.add(dataDTO);
                        i2++;
                    }
                    XPopup.Builder builderMoveUpToKeyboard = new XPopup.Builder(CirclePushActivity.this).moveUpToKeyboard(Boolean.FALSE);
                    CirclePushActivity circlePushActivity = CirclePushActivity.this;
                    builderMoveUpToKeyboard.asCustom(new SelectTopicPopWindow(circlePushActivity, arrayList, circlePushActivity.topicId, new AnonymousClass1(topicListBean))).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getImageData(final String imgUrl) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("image", new File(imgUrl));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.getforumuploadImageApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(CirclePushActivity.this.mContext, "上传失败！", 0).show();
                CirclePushActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass12) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(CirclePushActivity.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                    } else {
                        if (CirclePushActivity.this.isFinishing() || Looper.myLooper() != Looper.getMainLooper()) {
                            return;
                        }
                        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("url");
                        CirclePushActivity.this.sourceview_content.insertImage(strOptString + "?x-oss-process=image/resize,h_4096,m_lfit", Long.valueOf(jCurrentThreadTimeMillis), 0L, 0L);
                        ImageDataBean imageDataBean = new ImageDataBean();
                        imageDataBean.setId(jCurrentThreadTimeMillis);
                        imageDataBean.setB_img(strOptString);
                        imageDataBean.setS_img(strOptString);
                        if (CirclePushActivity.this.mImageList == null) {
                            CirclePushActivity.this.mImageList = new ArrayList();
                        }
                        CirclePushActivity.this.mImageList.add(imageDataBean);
                        CirclePushActivity circlePushActivity = CirclePushActivity.this;
                        SharePreferencesUtils.saveImageList(circlePushActivity.mContext, CommonParameter.circleImage, circlePushActivity.mImageList);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                CirclePushActivity.this.hideProgressDialog();
            }
        });
    }

    public void getmCustomTopicListData(String json) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("custom_sort", json);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCustomTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.17
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass17) s2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.format_bar);
        this.format_bar = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.g2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CirclePushActivity.lambda$init$0(view);
            }
        });
        EditText editText = (EditText) findViewById(R.id.title_circle);
        this.title_circle = editText;
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.psychiatrygarden.activity.circleactivity.h1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                this.f11539c.lambda$init$2(view, z2);
            }
        });
        this.title_circle.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.circleactivity.k1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f11555c.lambda$init$3(view, motionEvent);
            }
        });
        this.title_circle.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                CirclePushActivity.this.changeColor();
                if (s2.length() > 50) {
                    NewToast.showShort(CirclePushActivity.this.mContext, "超出字数限制", 0).show();
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.sourceview_content = (CircleRichEditor) findViewById(R.id.sourceview_content);
        this.include_btn_left = (ImageView) findViewById(R.id.include_btn_left);
        this.step = (TextView) findViewById(R.id.step);
        TextView textView = (TextView) findViewById(R.id.yulan);
        this.yulan = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.l1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11559c.lambda$init$4(view);
            }
        });
        this.include_btn_left.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.m1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11565c.lambda$init$5(view);
            }
        });
        this.step.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.n1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11569c.lambda$init$6(view);
            }
        });
        this.topicLabel = (TextView) findViewById(R.id.topicLabel);
        ImageView imageView = (ImageView) findViewById(R.id.circle_paizhao);
        ImageView imageView2 = (ImageView) findViewById(R.id.circle_lianjie);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.circle_toupai);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.push_circle);
        this.mPushRecycler = (RecyclerView) findViewById(R.id.push_recyclerview);
        this.mLyAddViewVote = (LinearLayout) findViewById(R.id.ly_add_vote_view);
        this.mLyVote = (LinearLayout) findViewById(R.id.ly_vote);
        this.mImgCloseVote = (ImageView) findViewById(R.id.btn_cancel_vote);
        this.mTvVoteType = (TextView) findViewById(R.id.tv_vote_type);
        this.mPushAdapter = new PushCircleAdp(true, false);
        OnItemDragListener onItemDragListener = new OnItemDragListener() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.2
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                ViewCompat.animate(viewHolder.itemView).setDuration(200L).scaleX(1.0f).scaleY(1.0f).start();
                Log.e("sort_circle", new Gson().toJson(CirclePushActivity.this.mPushAdapter.getData()));
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                ViewCompat.animate(((BaseViewHolder) viewHolder).itemView).setDuration(200L).scaleX(1.1f).scaleY(1.1f).start();
            }
        };
        this.mPushAdapter.getDraggableModule().setDragEnabled(true);
        this.mPushAdapter.getDraggableModule().setOnItemDragListener(onItemDragListener);
        this.mPushAdapter.getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(48);
        this.mPushRecycler.setAdapter(this.mPushAdapter);
        this.mPushAdapter.setOnItemChoosedLisenter(new PushCircleAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.3
            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, CirclrListBean.DataBean item) {
                CirclePushActivity.this.mPushAdapter.removeAt(pos);
            }

            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, CirclrListBean.DataBean item) {
            }
        });
        this.topicLabel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.o1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11573c.lambda$init$7(view);
            }
        });
        this.sourceview_content.setOnTextLengthChangeListener(new RichEditor.OnTextLengthChangeListener() { // from class: com.psychiatrygarden.activity.circleactivity.p1
            @Override // jp.wasabeef.richeditor.RichEditor.OnTextLengthChangeListener
            public final void onTextLengthChange(long j2) {
                this.f11577a.lambda$init$8(j2);
            }
        });
        this.sourceview_content.setOnFocusChangeListener(new RichEditor.OnFocusChangeListener() { // from class: com.psychiatrygarden.activity.circleactivity.q1
            @Override // jp.wasabeef.richeditor.RichEditor.OnFocusChangeListener
            public final void onFocusChange(boolean z2) {
                this.f11581a.lambda$init$10(z2);
            }
        });
        this.mLyVote.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.r1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11586c.lambda$init$11(view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.h2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11540c.lambda$init$12(view);
            }
        });
        this.sourceview_content.setOnTextChangeListener(new RichEditor.OnTextChangeListener() { // from class: com.psychiatrygarden.activity.circleactivity.i2
            @Override // jp.wasabeef.richeditor.RichEditor.OnTextChangeListener
            public final void onTextChange(String str) {
                this.f11547a.lambda$init$13(str);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.z0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11623c.lambda$init$15(view);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.a1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11490c.lambda$init$16(view);
            }
        });
        this.sourceview_content.setOnLinkClickListener(new RichEditor.OnLinkClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.b1
            @Override // jp.wasabeef.richeditor.RichEditor.OnLinkClickListener
            public final void onLinkClick(String str, String str2, String str3) {
                this.f11496a.lambda$init$18(str, str2, str3);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.c1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11500c.lambda$init$21(view);
            }
        });
        this.step.setText("发表");
        this.topicId = getIntent().getExtras().getString("topicId", "");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.sourceview_content.setEditorBackgroundColor("#FFFFFF", ContextCompat.getColor(this, R.color.bg_backgroud));
        } else {
            this.sourceview_content.setEditorBackgroundColor("#121622", ContextCompat.getColor(this, R.color.app_bg_night));
        }
        if (getIntent().getExtras().getString("htmlContent").equals("")) {
            linearLayout2.setVisibility(0);
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.circleTitle, this.mContext).equals("") || !SharePreferencesUtils.readStrConfig(CommonParameter.circleContent, this.mContext).equals("") || !SharePreferencesUtils.readStrConfig(CommonParameter.circlePushPost, this.mContext).equals("")) {
                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.circleContent, this.mContext);
                this.mStrHtml = strConfig;
                this.sourceview_content.setHtml(strConfig.replaceAll("\n", "<br/>"));
                this.title_circle.setText(SharePreferencesUtils.readStrConfig(CommonParameter.circleTitle, this.mContext));
                this.mImageList.clear();
                this.mImageList = SharePreferencesUtils.getImageList(this.mContext, CommonParameter.circleImage);
                this.mLinkDataList.clear();
                this.mLinkDataList = SharePreferencesUtils.getLinkList(this.mContext, CommonParameter.linkdata);
                List list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.circlePushPost, this.mContext), new TypeToken<List<CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.8
                }.getType());
                if (list == null || list.size() <= 0) {
                    this.mPushRecycler.setVisibility(8);
                } else {
                    this.mPushRecycler.setVisibility(0);
                    this.mPushAdapter.setList(list);
                }
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.f1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11523c.lambda$init$24();
                        }
                    });
                } else {
                    this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.g1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11533c.lambda$init$25();
                        }
                    });
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.sourceview_content.setFontView("#BFBFBF");
            } else {
                this.sourceview_content.setFontView("#575F79");
            }
            changeColor();
        } else {
            this.isEdit = true;
            if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                linearLayout2.setVisibility(0);
                this.mImgCloseVote.setVisibility(0);
            } else {
                linearLayout2.setVisibility(8);
                this.mImgCloseVote.setVisibility(8);
            }
            this.mHandler.sendEmptyMessage(0);
            this.mImageList.clear();
            this.mImageList = (List) getIntent().getSerializableExtra("mImageList");
            this.mLinkDataList.clear();
            this.mLinkDataList = (List) getIntent().getSerializableExtra("mLinkList");
            this.mStrHtml = getIntent().getExtras().getString("htmlContent");
            List list2 = (List) getIntent().getSerializableExtra("pushCircleList");
            if (list2 == null || list2.size() <= 0) {
                this.mPushRecycler.setVisibility(8);
            } else {
                this.mPushRecycler.setVisibility(0);
                this.mPushAdapter.setList(list2);
            }
            String string = getIntent().getExtras().getString("topicName", "");
            this.topicName = string;
            if (!TextUtils.isEmpty(string)) {
                this.topicLabel.setText(this.topicName);
            }
            String str = this.mStrHtml;
            this.mStrHtml = str.substring(1, str.length() - 1);
            List<ImageDataBean> list3 = this.mImageList;
            if (list3 != null && list3.size() > 0) {
                Matcher matcher = Pattern.compile("\\[image\\]").matcher(this.mStrHtml);
                int i2 = 0;
                while (matcher.find()) {
                    this.mStrHtml = this.mStrHtml.replaceFirst("\\[image\\]", "<img class=\"images\" data-id=\"" + this.mImageList.get(i2).getId() + "\" src=\"" + this.mImageList.get(i2).getB_img() + "\" style=\"max-width:100%\">");
                    i2++;
                }
            }
            List<LinkDataBean> list4 = this.mLinkDataList;
            if (list4 != null && list4.size() > 0) {
                Matcher matcher2 = Pattern.compile("\\[link\\]").matcher(this.mStrHtml);
                int i3 = 0;
                while (matcher2.find()) {
                    this.mStrHtml = this.mStrHtml.replaceFirst("\\[link\\]", "<a data-id=\"" + this.mLinkDataList.get(i3).getId() + "\" href=\"" + this.mLinkDataList.get(i3).getUrl() + " \"class=\"editor-link\">" + this.mLinkDataList.get(i3).getTitle() + "</a>");
                    i3++;
                }
            }
            this.sourceview_content.setHtml(this.mStrHtml);
            this.title_circle.setText(getIntent().getExtras().getString("htmlTitle"));
            List list5 = (List) getIntent().getSerializableExtra("options");
            if (list5 != null && list5.size() > 0) {
                this.mLyVote.setVisibility(0);
                this.mLyAddViewVote.removeAllViews();
                for (int i4 = 0; i4 < list5.size(); i4++) {
                    addOptionViews(((CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list5.get(i4)).getOption(), (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list5.get(i4));
                }
            }
            if (getIntent().getExtras().getString("choice", "1").equals("2")) {
                this.mTvVoteType.setText("多选");
            } else {
                this.mTvVoteType.setText("单选");
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.d1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11506c.lambda$init$22();
                    }
                });
            } else {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.e1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11513c.lambda$init$23();
                    }
                });
            }
        }
        this.mImgCloseVote.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11546c.lambda$init$26(view);
            }
        });
        getHeaderData(1);
    }

    public void mRefreshOptionView() {
        this.mLyVote.setVisibility(0);
        if (this.mLyAddViewVote.getChildCount() <= 2) {
            for (int i2 = 0; i2 < this.mLyAddViewVote.getChildCount(); i2++) {
                ((ImageView) this.mLyAddViewVote.getChildAt(i2).findViewById(R.id.deleEdit)).setVisibility(4);
            }
            return;
        }
        for (int i3 = 0; i3 < this.mLyAddViewVote.getChildCount(); i3++) {
            ImageView imageView = (ImageView) this.mLyAddViewVote.getChildAt(i3).findViewById(R.id.deleEdit);
            if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode != 273) {
            if (requestCode != 546) {
                return;
            }
            pushData(data.getBundleExtra("bundleIntent").getString("cid"), data.getBundleExtra("bundleIntent").getString("bodys"), data.getBundleExtra("bundleIntent").getString("virtual_user_id"));
            return;
        }
        if (this.isEdit) {
            editArticle(data.getExtras().getString("cid"), data.getExtras().getString("bodys"));
            return;
        }
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushData(data.getExtras().getString("cid"), data.getExtras().getString("bodys"), "");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("cid", "" + data.getExtras().getString("cid"));
        bundle.putString("bodys", "" + data.getExtras().getString("bodys"));
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 546);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (TextUtils.isEmpty(this.title_circle.getText().toString()) && TextUtils.isEmpty(this.mStrHtml) && !this.isEdit) {
            super.onBackPressed();
        } else {
            dialog();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        CircleRichEditor circleRichEditor = this.sourceview_content;
        if (circleRichEditor != null) {
            ViewParent parent = circleRichEditor.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.sourceview_content);
            }
            this.sourceview_content.stopLoading();
            this.sourceview_content.getSettings().setJavaScriptEnabled(false);
            this.sourceview_content.clearHistory();
            this.sourceview_content.clearView();
            this.sourceview_content.removeAllViews();
            this.sourceview_content.destroy();
            this.sourceview_content = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        dialog();
        return true;
    }

    public void pushArticleData(String bodys) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mLyAddViewVote.getChildCount(); i2++) {
            if (this.mLyAddViewVote.getChildAt(i2) instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) this.mLyAddViewVote.getChildAt(i2);
                for (int i3 = 0; i3 < relativeLayout.getChildCount(); i3++) {
                    if (relativeLayout.getChildAt(i3) instanceof EditText) {
                        EditText editText = (EditText) relativeLayout.getChildAt(i3);
                        if (TextUtils.isEmpty(editText.getText())) {
                            continue;
                        } else {
                            if (editText.getText().toString().length() < 2 || editText.getText().toString().length() > 15) {
                                AlertToast("选项" + i2 + "长度不符合！");
                                return;
                            }
                            arrayList.add(editText.getText().toString());
                        }
                    }
                }
            }
        }
        if (arrayList.size() == 1) {
            AlertToast("投票选项必须大于等于2！");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cid", "");
        if (arrayList.size() > 0) {
            ajaxParams.put("type", "2");
            ajaxParams.put("options", "" + new Gson().toJson(arrayList));
            ajaxParams.put("choice", this.mTvVoteType.getText().toString().equals("单选") ? "1" : "2");
        } else {
            ajaxParams.put("type", "1");
        }
        ajaxParams.put("title", this.title_circle.getText().toString().trim());
        ajaxParams.put("content", bodys);
        ajaxParams.put(PLVErrorCodeLinkMicBase.LINK_MODULE, new Gson().toJson(this.mLinkDataListTemp));
        ajaxParams.put("img_url", new Gson().toJson(this.imageDataTemp));
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumviewApi;
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        } else {
            str = NetworkRequestsURL.viewArticle;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CirclePushActivity.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass13) s2);
                try {
                    Intent intent = new Intent(CirclePushActivity.this, (Class<?>) ArticeViewActivity.class);
                    intent.putExtra("data", (Serializable) new Gson().fromJson(new JSONObject(s2).optString("data"), CircleInfoBean.DataBean.class));
                    CirclePushActivity.this.startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void pushData(String cid, String content, String virtual_user_id) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mLyAddViewVote.getChildCount(); i2++) {
            if (this.mLyAddViewVote.getChildAt(i2) instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) this.mLyAddViewVote.getChildAt(i2);
                for (int i3 = 0; i3 < relativeLayout.getChildCount(); i3++) {
                    if (relativeLayout.getChildAt(i3) instanceof EditText) {
                        EditText editText = (EditText) relativeLayout.getChildAt(i3);
                        if (TextUtils.isEmpty(editText.getText())) {
                            continue;
                        } else {
                            if (editText.getText().toString().length() < 2 || editText.getText().toString().length() > 15) {
                                AlertToast("选项" + i2 + "长度不符合！");
                                return;
                            }
                            arrayList.add(editText.getText().toString());
                        }
                    }
                }
            }
        }
        if (arrayList.size() == 1) {
            AlertToast("投票选项必须大于等于2！");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cid", cid);
        if (arrayList.size() > 0) {
            ajaxParams.put("type", "2");
            ajaxParams.put("options", "" + new Gson().toJson(arrayList));
            ajaxParams.put("choice", this.mTvVoteType.getText().toString().equals("单选") ? "1" : "2");
        } else {
            ajaxParams.put("type", "1");
        }
        ajaxParams.put("title", this.title_circle.getText().toString().trim());
        ajaxParams.put("content", content);
        ajaxParams.put(PLVErrorCodeLinkMicBase.LINK_MODULE, new Gson().toJson(this.mLinkDataListTemp));
        ajaxParams.put("img_url", new Gson().toJson(this.imageDataTemp));
        if (!"none".equals(this.topicId) && !"".equals(this.topicId)) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_ID, this.topicId);
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", virtual_user_id);
        }
        if (this.mPushAdapter.getData().size() > 100) {
            ToastUtil.shortToast(this, "最多只能添加100个帖子");
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<CirclrListBean.DataBean> it = this.mPushAdapter.getData().iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().getId());
        }
        if (arrayList2.size() > 0) {
            ajaxParams.put("push_ids", new Gson().toJson(arrayList2));
        }
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumpostingApi;
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        } else {
            str = NetworkRequestsURL.addArticle;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AnonymousClass15());
    }

    public void pushTieziData() {
        String strReplace;
        boolean z2;
        String strTrim = this.mStrHtml.replaceAll("<br/>", "").replace("<div>", "\n").replace("</div>", "").replace("<br>", "").replace("&nbsp;", "").replace("\n\t\t\t\t", "").replace(StrPool.CR, "").trim();
        if (TextUtils.isEmpty(this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", ""))) {
            AlertToast("标题不能为空");
            return;
        }
        if (this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() < 10 || this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() > 50) {
            AlertToast("标题长度不符合！");
            return;
        }
        if (TextUtils.isEmpty(strTrim)) {
            AlertToast("内容不能为空");
            return;
        }
        Matcher matcher = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        if (matcher.find()) {
            strReplace = strTrim.replace(matcher.group(), "");
            z2 = true;
        } else {
            strReplace = strTrim;
            z2 = false;
        }
        Matcher matcher2 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        if (matcher2.find()) {
            strReplace = strReplace.replace(matcher2.group(), "");
            z2 = true;
        }
        if (!z2 && (strReplace.replaceAll(" ", "").toString().length() < 10 || strReplace.replaceAll(" ", "").toString().length() > 10000)) {
            AlertToast("内容长度不符合！");
            return;
        }
        if (TextUtils.isEmpty(this.topicId)) {
            getHeaderData(3);
            return;
        }
        this.mLinkDataListTemp.clear();
        Matcher matcher3 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        Pattern patternCompile = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher3.find()) {
            Matcher matcher4 = patternCompile.matcher(matcher3.group());
            while (matcher4.find()) {
                for (int i2 = 0; i2 < this.mLinkDataList.size(); i2++) {
                    if (this.mLinkDataList.get(i2).getId() == Long.parseLong(matcher4.group().substring(9, matcher4.group().length() - 1))) {
                        this.mLinkDataListTemp.add(this.mLinkDataList.get(i2));
                    }
                }
            }
            strTrim = strTrim.replace(matcher3.group(), "[link]");
        }
        this.imageDataTemp.clear();
        Matcher matcher5 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        Pattern patternCompile2 = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher5.find()) {
            Matcher matcher6 = patternCompile2.matcher(matcher5.group());
            while (matcher6.find()) {
                for (int i3 = 0; i3 < this.mImageList.size(); i3++) {
                    if (this.mImageList.get(i3).getId() == Long.parseLong(matcher6.group().substring(9, matcher6.group().length() - 1))) {
                        this.imageDataTemp.add(this.mImageList.get(i3).getB_img());
                    }
                }
            }
            strTrim = strTrim.replace(matcher5.group(), "[image]");
        }
        String strReplaceAll = Pattern.compile(REGEX_HTML, 2).matcher(strTrim).replaceAll("");
        if (this.isEdit) {
            editArticle("", strReplaceAll);
            return;
        }
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushData("", strReplaceAll, "");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("cid", "");
        bundle.putString("bodys", "" + strReplaceAll);
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 546);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_push);
        setSwipeBackEnable(false);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void viewArticle() {
        String strReplace;
        boolean z2;
        String strTrim = this.mStrHtml.replace("<div>", "\n").replace("</div>", "").replace("<br>", "").replace("&nbsp;", "").replace("\n\t\t\t\t", "").replace(StrPool.CR, "").trim();
        if (TextUtils.isEmpty(this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", ""))) {
            AlertToast("标题不能为空");
            return;
        }
        if (this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() < 10 || this.title_circle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() > 50) {
            AlertToast("标题长度不符合！");
            return;
        }
        if (TextUtils.isEmpty(strTrim)) {
            AlertToast("内容不能为空");
            return;
        }
        Matcher matcher = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        if (matcher.find()) {
            strReplace = strTrim.replace(matcher.group(), "");
            z2 = true;
        } else {
            strReplace = strTrim;
            z2 = false;
        }
        Matcher matcher2 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        if (matcher2.find()) {
            strReplace = strReplace.replace(matcher2.group(), "");
            z2 = true;
        }
        if (!z2 && (strReplace.replaceAll(" ", "").toString().length() < 20 || strReplace.replaceAll(" ", "").toString().length() > 10000)) {
            AlertToast("内容长度不符合！");
            return;
        }
        this.mLinkDataListTemp.clear();
        Matcher matcher3 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        Pattern patternCompile = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher3.find()) {
            Matcher matcher4 = patternCompile.matcher(matcher3.group());
            while (matcher4.find()) {
                for (int i2 = 0; i2 < this.mLinkDataList.size(); i2++) {
                    if (this.mLinkDataList.get(i2).getId() == Long.parseLong(matcher4.group().substring(9, matcher4.group().length() - 1))) {
                        this.mLinkDataListTemp.add(this.mLinkDataList.get(i2));
                    }
                }
            }
            strTrim = strTrim.replace(matcher3.group(), "[link]\t");
        }
        this.imageDataTemp.clear();
        Matcher matcher5 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        Pattern patternCompile2 = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher5.find()) {
            Matcher matcher6 = patternCompile2.matcher(matcher5.group());
            while (matcher6.find()) {
                for (int i3 = 0; i3 < this.mImageList.size(); i3++) {
                    if (this.mImageList.get(i3).getId() == Long.parseLong(matcher6.group().substring(9, matcher6.group().length() - 1))) {
                        this.imageDataTemp.add(this.mImageList.get(i3).getB_img());
                    }
                }
            }
            strTrim = strTrim.replace(matcher5.group(), "[image]");
        }
        pushArticleData(Pattern.compile(REGEX_HTML, 2).matcher(strTrim).replaceAll(""));
    }

    public void addOptionViews(CircleInfoBean.DataBean.OptionsBeanX.OptionsBean bean) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.layout_circle_option_new, (ViewGroup) null);
        relativeLayout.setTag(bean);
        EditText editText = (EditText) relativeLayout.findViewById(R.id.editId);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_content);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.deleEdit);
        RelativeLayout relativeLayout2 = (RelativeLayout) relativeLayout.findViewById(R.id.optionRel);
        editText.setVisibility(4);
        textView.setVisibility(0);
        textView.setText(bean.getOption());
        editText.setText(bean.getOption());
        imageView.setVisibility(8);
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.d2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11507c.lambda$addOptionViews$36(view);
            }
        });
        if (this.mLyAddViewVote.getChildCount() > 1) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        this.mLyAddViewVote.addView(relativeLayout);
    }
}
