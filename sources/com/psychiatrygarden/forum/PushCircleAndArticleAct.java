package com.psychiatrygarden.forum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import cn.hutool.core.lang.RegexPool;
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
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.circleactivity.PushCircleAdp;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ImageDataBean;
import com.psychiatrygarden.bean.LinkDataBean;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.NinePicAdp;
import com.psychiatrygarden.forum.PushBookAdp;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleEditSavePop;
import com.psychiatrygarden.widget.CircleRichEditor;
import com.psychiatrygarden.widget.NoticePopWindow;
import com.psychiatrygarden.widget.PushBookCircleDialogFragment;
import com.psychiatrygarden.widget.PushCircleDialog;
import com.psychiatrygarden.widget.PushCircleVoteDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
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
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PushCircleAndArticleAct extends BaseActivity {
    private static final String REGEX_HTML = "<[^>]+>";
    private boolean enableAnonymousPush;
    private boolean isEitAnonymous;
    private PushBookAdp mBookAdp;
    private RecyclerView mBookRecycler;
    private ImageView mBtnAdminPic;
    private ImageView mBtnBack;
    private TextView mBtnFinish;
    private ImageView mBtnLink;
    private ImageView mBtnPic;
    private EditText mEtTitle;
    private ImageView mImgCloseVote;
    private LinearLayout mLyAddViewVote;
    private LinearLayout mLyCircleBook;
    private LinearLayout mLyCircleTouPiao;
    private LinearLayout mLyFormatBar;
    private LinearLayout mLyPushCircle;
    private LinearLayout mLyVote;
    private NinePicAdp mPicAdapter;
    private RecyclerView mPicRecycler;
    private PushCircleAdp mPushAdapter;
    private RecyclerView mPushRecycler;
    private TextView mTvVoteType;
    private int moduleType;
    private int pushTimes;
    private RelativeLayout rlAnonymous;
    private CircleRichEditor sourceview_content;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new AnonymousClass1();
    private boolean isEdit = false;
    private String htmlContent = "";
    private String htmlTitle = "";
    private List<LinkDataBean> mLinkDataList = new ArrayList();
    private List<ImageDataBean> mImageList = new ArrayList();
    private final List<LinkDataBean> mLinkDataListTemp = new ArrayList();
    public List<String> imageDataTemp = new ArrayList();
    public List<String> imageAdminDataTemp = new ArrayList();
    private List<ImageDataBean> mImageAdminList = new ArrayList();
    private long strLeth = 0;

    /* renamed from: com.psychiatrygarden.forum.PushCircleAndArticleAct$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0() {
            PushCircleAndArticleAct.this.sourceview_content.setFontView("#141516");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1() {
            PushCircleAndArticleAct.this.sourceview_content.setFontView("#7380A9");
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (TextUtils.isEmpty(PushCircleAndArticleAct.this.htmlContent)) {
                    if (SkinManager.getCurrentSkinType(PushCircleAndArticleAct.this.mContext) == 0) {
                        PushCircleAndArticleAct.this.sourceview_content.setFontView("#C2C6CB");
                        return;
                    } else {
                        PushCircleAndArticleAct.this.sourceview_content.setFontView("#454C64");
                        return;
                    }
                }
                if (SkinManager.getCurrentSkinType(PushCircleAndArticleAct.this.mContext) == 0) {
                    PushCircleAndArticleAct.this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.x1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15418c.lambda$handleMessage$0();
                        }
                    });
                } else {
                    PushCircleAndArticleAct.this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.y1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15421c.lambda$handleMessage$1();
                        }
                    });
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.forum.PushCircleAndArticleAct$17, reason: invalid class name */
    public class AnonymousClass17 extends AjaxCallBack<String> {
        public AnonymousClass17() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            PushCircleAndArticleAct.this.finish();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(String str) {
            try {
                PushCircleAndArticleAct.this.hideInputMethod();
                PushCircleAndArticleAct.this.mEtTitle.setText("");
                PushCircleAndArticleAct.this.sourceview_content.setHtml("");
                PushCircleAndArticleAct.this.htmlContent = "";
                PushCircleAndArticleAct.this.clearLocalCircleInfo();
                PushCircleAndArticleAct.this.clearLocalCircleInfo();
                CommonUtil.showFristDialog(new JSONObject(str), new NoticePopWindow.NoticeClickIml() { // from class: com.psychiatrygarden.forum.z1
                    @Override // com.psychiatrygarden.widget.NoticePopWindow.NoticeClickIml
                    public final void mNoticeClick() {
                        this.f15424a.lambda$onSuccess$0();
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            PushCircleAndArticleAct.this.AlertToast("发表失败！");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String s2) {
            super.onSuccess((AnonymousClass17) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    EventBus.getDefault().post("isRefaulfCircle");
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.forum.a2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15314c.lambda$onSuccess$1(s2);
                        }
                    }, 500L);
                    PushCircleAndArticleAct.this.AlertToast("发布成功!");
                } else {
                    PushCircleAndArticleAct.this.AlertToast(new JSONObject(s2).optString("message"));
                }
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
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleBookPost, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleOptionPost, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleOptionType, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circlePicPost, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.circleAdminPicPost, "", this.mContext);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.circleAnonymous, false, this.mContext);
    }

    private void dialog() {
        final ArrayList arrayList = new ArrayList();
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
        hideInputMethod();
        new XPopup.Builder(this.mContext).moveUpToKeyboard(Boolean.FALSE).asCustom(new CircleEditSavePop(this.mContext, new CircleEditSavePop.ClickListenerEditSave() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.19
            @Override // com.psychiatrygarden.widget.CircleEditSavePop.ClickListenerEditSave
            public void noSave() {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                PushCircleAndArticleAct.this.clearLocalCircleInfo();
                PushCircleAndArticleAct.this.finish();
            }

            @Override // com.psychiatrygarden.widget.CircleEditSavePop.ClickListenerEditSave
            public void save() {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                SharePreferencesUtils.writeStrConfig(CommonParameter.circleTitle, "" + PushCircleAndArticleAct.this.mEtTitle.getText().toString(), PushCircleAndArticleAct.this.mContext);
                SharePreferencesUtils.writeStrConfig(CommonParameter.circleContent, PushCircleAndArticleAct.this.htmlContent, PushCircleAndArticleAct.this.mContext);
                int length = PushCircleAndArticleAct.this.mEtTitle.getText().toString().length();
                SharePreferencesUtils.writeStrConfig(CommonParameter.circleCanPush, (PushCircleAndArticleAct.this.strLeth > 10L ? 1 : (PushCircleAndArticleAct.this.strLeth == 10L ? 0 : -1)) >= 0 && (PushCircleAndArticleAct.this.strLeth > com.heytap.mcssdk.constant.a.f7153q ? 1 : (PushCircleAndArticleAct.this.strLeth == com.heytap.mcssdk.constant.a.f7153q ? 0 : -1)) <= 0 && length >= 10 && length <= 50 ? "1" : "0", PushCircleAndArticleAct.this.mContext);
                SharePreferencesUtils.writeStrConfig(CommonParameter.circlePushPost, new Gson().toJson(PushCircleAndArticleAct.this.mPushAdapter.getData()), PushCircleAndArticleAct.this.mContext);
                SharePreferencesUtils.writeStrConfig(CommonParameter.circleBookPost, new Gson().toJson(PushCircleAndArticleAct.this.mBookAdp.getData()), PushCircleAndArticleAct.this.mContext);
                if (!arrayList.isEmpty()) {
                    SharePreferencesUtils.writeStrConfig(CommonParameter.circleOptionPost, new Gson().toJson(arrayList), PushCircleAndArticleAct.this.mContext);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.circleOptionType, PushCircleAndArticleAct.this.mTvVoteType.getText().toString().equals("单选") ? "1" : "2", PushCircleAndArticleAct.this.mContext);
                }
                Log.e("pic_length", "pic===>" + PushCircleAndArticleAct.this.mImageList.size());
                SharePreferencesUtils.writeStrConfig(CommonParameter.circlePicPost, new Gson().toJson(PushCircleAndArticleAct.this.mImageList), PushCircleAndArticleAct.this.mContext);
                SharePreferencesUtils.writeStrConfig(CommonParameter.circleAdminPicPost, new Gson().toJson(PushCircleAndArticleAct.this.mImageAdminList), PushCircleAndArticleAct.this.mContext);
                View viewFindViewById = PushCircleAndArticleAct.this.findViewById(R.id.iv_anonymous_push);
                SharePreferencesUtils.writeBooleanConfig(CommonParameter.circleAnonymous, (viewFindViewById.getTag() == null || viewFindViewById.getTag() == Boolean.FALSE) ? false : true, PushCircleAndArticleAct.this.mContext);
                PushCircleAndArticleAct.this.finish();
            }
        })).show();
    }

    private void editArticle(String content) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", getIntent().getExtras().getString("type"));
        if ("2".equals(getIntent().getExtras().getString("type"))) {
            ajaxParams.put("choice", getIntent().getExtras().getString("choice", "1"));
        }
        handleOptionsData(ajaxParams);
        ajaxParams.put("title", this.mEtTitle.getText().toString().trim());
        ajaxParams.put("content", content);
        ajaxParams.put(PLVErrorCodeLinkMicBase.LINK_MODULE, new Gson().toJson(this.mLinkDataListTemp));
        ajaxParams.put("img_url", new Gson().toJson(this.imageDataTemp));
        ajaxParams.put("rich_img_url", new Gson().toJson(this.imageAdminDataTemp));
        if (!this.enableAnonymousPush || this.pushTimes <= 0) {
            ajaxParams.put("is_anonymous", this.isEitAnonymous ? "1" : "0");
        } else if (findViewById(R.id.iv_anonymous_push).getTag() == Boolean.TRUE) {
            ajaxParams.put("is_anonymous", "1");
        } else {
            ajaxParams.put("is_anonymous", "0");
        }
        String str = NetworkRequestsURL.editArticleNew;
        ajaxParams.put("article_id", getIntent().getExtras().getString("article_id"));
        ArrayList arrayList = new ArrayList();
        Iterator<CirclrListBean.DataBean> it = this.mPushAdapter.getData().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getOrigin_id());
        }
        if (arrayList.size() > 0) {
            ajaxParams.put("push_ids", new Gson().toJson(arrayList));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<PushBookData> it2 = this.mBookAdp.getData().iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getId());
        }
        if (arrayList2.size() > 0) {
            ajaxParams.put("ebook_ids", new Gson().toJson(arrayList2));
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.18
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PushCircleAndArticleAct.this.AlertToast("发表失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass18) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        EventBus.getDefault().post("isRefaulfCircleInfo");
                        PushCircleAndArticleAct.this.finish();
                    }
                    PushCircleAndArticleAct.this.AlertToast(new JSONObject(s2).optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getAnonymousConfig() {
        YJYHttpUtils.get(this, NetworkRequestsURL.anonymousPushCircleConfig, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.15
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                JSONObject jSONObjectOptJSONObject;
                super.onSuccess((AnonymousClass15) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code", "").equals("200") || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                        return;
                    }
                    PushCircleAndArticleAct.this.enableAnonymousPush = "1".equals(jSONObjectOptJSONObject.optString("is_anonymous", "0"));
                    String strOptString = jSONObjectOptJSONObject.optString("last_num", "0");
                    if ("-1".equals(strOptString)) {
                        PushCircleAndArticleAct.this.pushTimes = 9999999;
                    } else if (strOptString.matches(RegexPool.NUMBERS)) {
                        PushCircleAndArticleAct.this.pushTimes = Integer.parseInt(strOptString);
                    }
                    int i2 = 0;
                    PushCircleAndArticleAct.this.rlAnonymous.setVisibility(PushCircleAndArticleAct.this.enableAnonymousPush ? 0 : 8);
                    if (PushCircleAndArticleAct.this.isEdit) {
                        PushCircleAndArticleAct.this.rlAnonymous.setVisibility(0);
                        ImageView imageView = (ImageView) PushCircleAndArticleAct.this.findViewById(R.id.iv_anonymous_push);
                        imageView.setTag(PushCircleAndArticleAct.this.isEitAnonymous ? Boolean.TRUE : Boolean.FALSE);
                        TypedArray typedArrayObtainStyledAttributes = PushCircleAndArticleAct.this.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_circle_anonymous_single_select, R.attr.ic_circle_anonymous_single_not_select});
                        if (!PushCircleAndArticleAct.this.isEitAnonymous) {
                            i2 = 1;
                        }
                        imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(i2));
                        typedArrayObtainStyledAttributes.recycle();
                    }
                } catch (JSONException e2) {
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

    private void initData() {
        CharSequence charSequence;
        Object obj;
        getAnonymousConfig();
        String str = "1";
        if (!this.isEdit) {
            if (UserConfig.isLogin()) {
                this.mBtnAdminPic.setVisibility(UserConfig.getInstance().getUser().getAdmin().equals("1") ? 0 : 8);
            }
            this.mLyCircleTouPiao.setVisibility(0);
            if (SharePreferencesUtils.readStrConfig(CommonParameter.circleTitle, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circleContent, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circlePushPost, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circleBookPost, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circlePicPost, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circleOptionPost, this.mContext).equals("") && SharePreferencesUtils.readStrConfig(CommonParameter.circleOptionType, this.mContext).equals("")) {
                ArrayList arrayList = (ArrayList) getIntent().getSerializableExtra("bookList");
                if (arrayList != null) {
                    this.mBookRecycler.setVisibility(0);
                    this.mBookAdp.setList(arrayList);
                }
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.sourceview_content.setFontView("#C2C6CB");
                    return;
                } else {
                    this.sourceview_content.setFontView("#454C64");
                    return;
                }
            }
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.circleCanPush, this.mContext);
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.circleAnonymous, false, this.mContext)) {
                ImageView imageView = (ImageView) findViewById(R.id.iv_anonymous_push);
                charSequence = "多选";
                obj = "2";
                TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.ic_circle_anonymous_single_select});
                imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                typedArrayObtainStyledAttributes.recycle();
                imageView.setTag(Boolean.TRUE);
            } else {
                charSequence = "多选";
                obj = "2";
            }
            String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.circleContent, this.mContext);
            this.htmlContent = strConfig2;
            this.sourceview_content.setHtml(strConfig2.replaceAll("\n", "<br/>"));
            this.mEtTitle.setText(SharePreferencesUtils.readStrConfig(CommonParameter.circleTitle, this.mContext));
            this.mLinkDataList.clear();
            this.mLinkDataList = SharePreferencesUtils.getLinkList(this.mContext, CommonParameter.linkdata);
            if ("1".equals(strConfig)) {
                this.mBtnFinish.setEnabled(true);
                this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_bg);
            }
            List list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.circlePushPost, this.mContext), new TypeToken<List<CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.6
            }.getType());
            if (list == null || list.size() <= 0) {
                this.mPushRecycler.setVisibility(8);
            } else {
                this.mPushRecycler.setVisibility(0);
                this.mPushAdapter.setList(list);
            }
            List list2 = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.circleBookPost, this.mContext), new TypeToken<List<PushBookData>>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.7
            }.getType());
            if (list2 == null || list2.size() <= 0) {
                this.mBookRecycler.setVisibility(8);
            } else {
                this.mBookRecycler.setVisibility(0);
                this.mBookAdp.setList(list2);
            }
            String strConfig3 = SharePreferencesUtils.readStrConfig(CommonParameter.circleAdminPicPost, this.mContext);
            this.mImageAdminList.clear();
            this.mImageAdminList = (List) new Gson().fromJson(strConfig3, new TypeToken<List<ImageDataBean>>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.8
            }.getType());
            String strConfig4 = SharePreferencesUtils.readStrConfig(CommonParameter.circlePicPost, this.mContext);
            this.mImageList.clear();
            List<ImageDataBean> list3 = (List) new Gson().fromJson(strConfig4, new TypeToken<List<ImageDataBean>>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.9
            }.getType());
            this.mImageList = list3;
            if (list3 == null || list3.size() <= 0) {
                this.mPicRecycler.setVisibility(8);
            } else {
                this.mPicRecycler.setVisibility(0);
                ArrayList arrayList2 = new ArrayList();
                Iterator<ImageDataBean> it = this.mImageList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(it.next().getS_img());
                }
                arrayList2.add("");
                this.mPicAdapter.setList(arrayList2);
            }
            List list4 = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.circleOptionPost, this.mContext), new TypeToken<List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean>>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.10
            }.getType());
            if (list4 != null && list4.size() > 0) {
                this.mLyVote.setVisibility(0);
                this.mLyAddViewVote.removeAllViews();
                for (int i2 = 0; i2 < list4.size(); i2++) {
                    addOptionViews(((CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list4.get(i2)).getOption(), (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list4.get(i2));
                }
            }
            if (SharePreferencesUtils.readStrConfig(CommonParameter.circleOptionType, this.mContext).equals(obj)) {
                this.mTvVoteType.setText(charSequence);
            } else {
                this.mTvVoteType.setText("单选");
            }
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.o1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15389c.lambda$initData$6();
                    }
                });
                return;
            } else {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.p1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15392c.lambda$initData$7();
                    }
                });
                return;
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            this.mLyCircleTouPiao.setVisibility(0);
            this.mImgCloseVote.setVisibility(0);
        } else {
            this.mLyCircleTouPiao.setVisibility(8);
            this.mImgCloseVote.setVisibility(8);
        }
        this.mBtnAdminPic.setVisibility(getIntent().getStringExtra("isRich").equals("1") ? 0 : 8);
        this.mHandler.sendEmptyMessage(0);
        this.mImageList.clear();
        this.mImageList = (List) getIntent().getSerializableExtra("mImageList");
        this.mImageAdminList.clear();
        this.mImageAdminList = (List) getIntent().getSerializableExtra("isRichImg");
        this.mLinkDataList.clear();
        this.mLinkDataList = (List) getIntent().getSerializableExtra("mLinkList");
        List list5 = (List) getIntent().getSerializableExtra("pushCircleList");
        if (list5 == null || list5.size() <= 0) {
            this.mPushRecycler.setVisibility(8);
        } else {
            this.mPushRecycler.setVisibility(0);
            this.mPushAdapter.setList(list5);
        }
        String str2 = this.htmlContent;
        this.htmlContent = str2.substring(1, str2.length() - 1);
        List<ImageDataBean> list6 = this.mImageAdminList;
        if (list6 != null && list6.size() > 0) {
            Matcher matcher = Pattern.compile("\\[image\\]").matcher(this.htmlContent);
            int i3 = 0;
            while (matcher.find()) {
                this.htmlContent = this.htmlContent.replaceFirst("\\[image\\]", "<img class=\"images\" data-id=\"" + this.mImageAdminList.get(i3).getId() + "\" src=\"" + this.mImageAdminList.get(i3).getB_img() + "\" style=\"max-width:100%\">");
                i3++;
                str = str;
            }
        }
        String str3 = str;
        this.mBtnFinish.setEnabled(true);
        this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_bg);
        List<ImageDataBean> list7 = this.mImageList;
        if (list7 != null && !list7.isEmpty()) {
            this.mPicRecycler.setVisibility(0);
            ArrayList arrayList3 = new ArrayList();
            Iterator<ImageDataBean> it2 = this.mImageList.iterator();
            while (it2.hasNext()) {
                arrayList3.add(it2.next().getS_img());
            }
            arrayList3.add("");
            this.mPicAdapter.setList(arrayList3);
        }
        ArrayList arrayList4 = (ArrayList) getIntent().getSerializableExtra("bookList");
        if (arrayList4 != null) {
            this.mBookRecycler.setVisibility(0);
            this.mBookAdp.setList(arrayList4);
        }
        List<LinkDataBean> list8 = this.mLinkDataList;
        if (list8 != null && list8.size() > 0) {
            Matcher matcher2 = Pattern.compile("\\[link\\]").matcher(this.htmlContent);
            int i4 = 0;
            while (matcher2.find()) {
                this.htmlContent = this.htmlContent.replaceFirst("\\[link\\]", "<a style=\"color:#0062BA;text-decoration:none;background:url(file:///android_res/drawable/lianjietu.png);background-size:20px 20px;background-repeat:no-repeat;padding-left:22px;box-sizing:border-box;background-position:0% 50%;\" data-id=\"" + this.mLinkDataList.get(i4).getId() + "\" href=\"" + this.mLinkDataList.get(i4).getUrl() + " \" class=\"editor-link\">" + this.mLinkDataList.get(i4).getTitle() + "</a>");
                i4++;
            }
        }
        this.sourceview_content.setHtml(this.htmlContent);
        this.mEtTitle.setText(this.htmlTitle);
        List list9 = (List) getIntent().getSerializableExtra("options");
        if (list9 != null && list9.size() > 0) {
            this.mLyVote.setVisibility(0);
            this.mLyAddViewVote.removeAllViews();
            for (int i5 = 0; i5 < list9.size(); i5++) {
                addOptionViews(((CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list9.get(i5)).getOption(), (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) list9.get(i5));
            }
        }
        if (getIntent().getExtras().getString("choice", str3).equals("2")) {
            this.mTvVoteType.setText("多选");
        } else {
            this.mTvVoteType.setText("单选");
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.m1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15383c.lambda$initData$4();
                }
            });
        } else {
            this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.n1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15386c.lambda$initData$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionViews$32(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionViews$33(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(ImageView imageView, View view) {
        if (this.isEdit) {
            AlertToast("不可修改匿名状态");
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.ic_circle_anonymous_single_select, R.attr.ic_circle_anonymous_single_not_select});
        if (!this.enableAnonymousPush || this.pushTimes <= 0) {
            if (this.pushTimes <= 0) {
                AlertToast("匿名贴发布次数已用完");
            }
        } else if (view.getTag() == null) {
            imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
            view.setTag(Boolean.TRUE);
        } else {
            Object tag = view.getTag();
            Boolean bool = Boolean.TRUE;
            if (tag == bool) {
                imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(1));
                view.setTag(Boolean.FALSE);
            } else {
                imageView.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                view.setTag(bool);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        this.mLyFormatBar.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view, boolean z2) {
        if (z2) {
            this.mLyFormatBar.post(new Runnable() { // from class: com.psychiatrygarden.forum.i1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15362c.lambda$init$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$3(View view, MotionEvent motionEvent) {
        this.mEtTitle.findFocus();
        this.mEtTitle.setFocusable(true);
        this.mEtTitle.setFocusableInTouchMode(true);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$4() {
        this.sourceview_content.setFontView("#303030");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$5() {
        this.sourceview_content.setFontView("#7380A9");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$6() {
        this.sourceview_content.setFontView("#141516");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$7() {
        this.sourceview_content.setFontView("#7380A9");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$10() {
        this.mBtnFinish.setEnabled(false);
        this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_gray_bg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$11() {
        this.sourceview_content.setFontView("#141516");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12() {
        this.sourceview_content.setFontView("#7380A9");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13(long j2) {
        this.strLeth = j2;
        if (this.mEtTitle.getText().toString().replaceAll("\n", "").replaceAll(" ", "").toString().length() < 10 || this.strLeth < 10) {
            this.mBtnFinish.post(new Runnable() { // from class: com.psychiatrygarden.forum.z0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15423c.lambda$setListenerForWidget$10();
                }
            });
        } else {
            this.mBtnFinish.post(new Runnable() { // from class: com.psychiatrygarden.forum.y0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15420c.lambda$setListenerForWidget$9();
                }
            });
        }
        if (j2 > 0) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.b1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15319c.lambda$setListenerForWidget$11();
                    }
                });
            } else {
                this.sourceview_content.post(new Runnable() { // from class: com.psychiatrygarden.forum.c1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15324c.lambda$setListenerForWidget$12();
                    }
                });
            }
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.sourceview_content.setFontView("#C2C6CB");
        } else {
            this.sourceview_content.setFontView("#454C64");
        }
        if (j2 > com.heytap.mcssdk.constant.a.f7153q) {
            AlertToast("超出内容长度！");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14() {
        this.mLyFormatBar.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15() {
        this.mLyFormatBar.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$16(boolean z2) {
        if (!z2) {
            this.mLyFormatBar.post(new Runnable() { // from class: com.psychiatrygarden.forum.x0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15417c.lambda$setListenerForWidget$15();
                }
            });
        } else {
            this.mHandler.sendEmptyMessage(0);
            this.mLyFormatBar.post(new Runnable() { // from class: com.psychiatrygarden.forum.w0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15412c.lambda$setListenerForWidget$14();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(String str) {
        this.htmlContent = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$18(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$setListenerForWidget$19(View view) {
        new UrlLinkActivity(this.mContext, new UrlLinkActivity.OnDialogClickListener() { // from class: com.psychiatrygarden.forum.u0
            @Override // com.psychiatrygarden.widget.UrlLinkActivity.OnDialogClickListener
            public final void onConfirmButtonClick(String str, String str2, String str3) {
                this.f15406a.lambda$setListenerForWidget$18(str, str2, str3);
            }
        }, "", "", "").show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$20(View view) {
        showChoosePic(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$21(View view) {
        showChoosePic(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$22(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$setListenerForWidget$23(String str, String str2, String str3) {
        new UrlLinkActivity(this.mContext, new UrlLinkActivity.OnDialogClickListener() { // from class: com.psychiatrygarden.forum.h1
            @Override // com.psychiatrygarden.widget.UrlLinkActivity.OnDialogClickListener
            public final void onConfirmButtonClick(String str4, String str5, String str6) {
                this.f15357a.lambda$setListenerForWidget$22(str4, str5, str6);
            }
        }, str, str3, str2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$24(View view) {
        Log.e("book_data", "aaaa===>" + this.mBookAdp.getData().size());
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mBookAdp.getData());
        final PushBookCircleDialogFragment pushBookCircleDialogFragmentNewInstance = PushBookCircleDialogFragment.newInstance(arrayList);
        pushBookCircleDialogFragmentNewInstance.setOnChoosedLisenter(new PushBookCircleDialogFragment.OnChoosedLisenter() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.12
            @Override // com.psychiatrygarden.widget.PushBookCircleDialogFragment.OnChoosedLisenter
            public void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<PushBookData> mChooseCircleItems) {
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < PushCircleAndArticleAct.this.mBookAdp.getData().size(); i2++) {
                    PushBookData item = PushCircleAndArticleAct.this.mBookAdp.getItem(i2);
                    if (oldCircleIds.contains(item.getId()) && !newCircleIds.contains(item.getId())) {
                        arrayList2.add(item);
                    }
                }
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    PushCircleAndArticleAct.this.mBookAdp.remove((PushBookAdp) arrayList2.get(i3));
                }
                for (PushBookData pushBookData : mChooseCircleItems) {
                    if (!oldCircleIds.contains(pushBookData.getId())) {
                        PushCircleAndArticleAct.this.mBookAdp.addData((PushBookAdp) pushBookData);
                    }
                }
                pushBookCircleDialogFragmentNewInstance.dismiss();
                PushCircleAndArticleAct.this.mBookRecycler.setVisibility(PushCircleAndArticleAct.this.mBookAdp.getData().size() <= 0 ? 8 : 0);
            }
        });
        getSupportFragmentManager().executePendingTransactions();
        if (pushBookCircleDialogFragmentNewInstance.isAdded() || pushBookCircleDialogFragmentNewInstance.isRemoving() || pushBookCircleDialogFragmentNewInstance.isVisible()) {
            return;
        }
        pushBookCircleDialogFragmentNewInstance.show(getSupportFragmentManager(), "pushBookFragment");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$25(View view) {
        final PushCircleDialog pushCircleDialog = new PushCircleDialog(this.mContext, this.mPushAdapter.getData());
        pushCircleDialog.setOnChoosedLisenter(new PushCircleDialog.OnChoosedLisenter() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.13
            @Override // com.psychiatrygarden.widget.PushCircleDialog.OnChoosedLisenter
            public void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<CirclrListBean.DataBean> mChooseCircleItems) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < PushCircleAndArticleAct.this.mPushAdapter.getData().size(); i2++) {
                    CirclrListBean.DataBean item = PushCircleAndArticleAct.this.mPushAdapter.getItem(i2);
                    if (oldCircleIds.contains(item.getOrigin_id()) && !newCircleIds.contains(item.getOrigin_id())) {
                        arrayList.add(item);
                    }
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    PushCircleAndArticleAct.this.mPushAdapter.remove((PushCircleAdp) arrayList.get(i3));
                }
                for (CirclrListBean.DataBean dataBean : mChooseCircleItems) {
                    if (!oldCircleIds.contains(dataBean.getOrigin_id())) {
                        PushCircleAndArticleAct.this.mPushAdapter.addData((PushCircleAdp) dataBean);
                    }
                }
                pushCircleDialog.dismiss();
                PushCircleAndArticleAct.this.mPushRecycler.setVisibility(PushCircleAndArticleAct.this.mPushAdapter.getData().size() <= 0 ? 8 : 0);
            }
        });
        pushCircleDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$26(View view) {
        this.mLyVote.setVisibility(8);
        this.mLyAddViewVote.removeAllViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$27(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$28(View view) {
        if (!this.isEdit) {
            showVoteDialog();
        } else if (UserConfig.isLogin() && UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            showVoteDialog();
        } else {
            ToastUtil.shortToast(this.mContext, "投票内容禁止重新编辑");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$29(View view) {
        pushTieziData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9() {
        this.mBtnFinish.setEnabled(true);
        this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_bg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChoosePic$30() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChoosePic$31(boolean z2, List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = ((ImageItem) list.get(i2)).path;
            String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            LogUtils.d("BitmapFactoryType", "图片类型1：" + mimeTypeFromFile);
            if (mimeTypeFromFile.toUpperCase().equals("IMAGE/WEBP")) {
                AlertToast("不支持此文件格式，请选择其它图片上传！");
                return;
            } else {
                if (ImageFactory.getImageSize(str) > 10.0f) {
                    NewToast.showShort(this.mContext, "请选择小于10M的图片上传", 0).show();
                    return;
                }
                getImageData(str, z2);
            }
        }
    }

    public static void newIntent(Context context, boolean isEdit, String htmlContent, String htmlTitle, int module_type) {
        Intent intent = new Intent(context, (Class<?>) PushCircleAndArticleAct.class);
        intent.putExtra("isEdit", isEdit);
        intent.putExtra("htmlContent", htmlContent);
        intent.putExtra("htmlTitle", htmlTitle);
        intent.putExtra("module_type", module_type);
        context.startActivity(intent);
    }

    private void pushData(String content, String virtual_user_id) {
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
        if (arrayList.size() > 0) {
            ajaxParams.put("type", "2");
            ajaxParams.put("options", "" + new Gson().toJson(arrayList));
            ajaxParams.put("choice", this.mTvVoteType.getText().toString().equals("单选") ? "1" : "2");
        } else {
            ajaxParams.put("type", "1");
        }
        ajaxParams.put("content", content);
        ajaxParams.put("title", this.mEtTitle.getText().toString().trim());
        ajaxParams.put(PLVErrorCodeLinkMicBase.LINK_MODULE, new Gson().toJson(this.mLinkDataListTemp));
        ajaxParams.put("img_url", new Gson().toJson(this.imageDataTemp));
        ajaxParams.put("rich_img_url", new Gson().toJson(this.imageAdminDataTemp));
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", virtual_user_id);
        }
        if (this.enableAnonymousPush && this.pushTimes > 0 && findViewById(R.id.iv_anonymous_push).getTag() == Boolean.TRUE) {
            ajaxParams.put("is_anonymous", "1");
        } else {
            ajaxParams.put("is_anonymous", "0");
        }
        if (this.mPushAdapter.getData().size() > 100) {
            ToastUtil.shortToast(this, "最多只能添加100个帖子");
            return;
        }
        if (this.mBookAdp.getData().size() > 10) {
            ToastUtil.shortToast(this, "最多只能添加10本书籍");
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<CirclrListBean.DataBean> it = this.mPushAdapter.getData().iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().getOrigin_id());
        }
        if (arrayList2.size() > 0) {
            ajaxParams.put("push_ids", new Gson().toJson(arrayList2));
        }
        ArrayList arrayList3 = new ArrayList();
        for (PushBookData pushBookData : this.mBookAdp.getData()) {
            Log.e("book_id", "id====>" + pushBookData.getId());
            arrayList3.add(pushBookData.getId());
        }
        if (arrayList3.size() > 0) {
            ajaxParams.put("ebook_ids", new Gson().toJson(arrayList3));
        }
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumpostingApi;
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        } else {
            str = NetworkRequestsURL.postArticle;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AnonymousClass17());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showChoosePic(final boolean isNine) {
        if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
            new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.forum.j1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f15367a.lambda$showChoosePic$30();
                }
            })).show();
            return;
        }
        int size = this.mPicAdapter.getData().size() - 1;
        int i2 = size > 0 ? 9 - size : 9;
        AndroidImagePicker androidImagePicker = AndroidImagePicker.getInstance();
        if (!isNine) {
            i2 = 1;
        }
        androidImagePicker.setSelectLimit(i2);
        AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.forum.k1
            @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
            public final void onImagePickComplete(List list) {
                this.f15372a.lambda$showChoosePic$31(isNine, list);
            }
        });
    }

    private void showVoteDialog() {
        final PushCircleVoteDialog pushCircleVoteDialog = new PushCircleVoteDialog(this.mContext, getVoteStr(), this.mTvVoteType.getText().toString());
        pushCircleVoteDialog.setOnChoosedLisenter(new PushCircleVoteDialog.OnChoosedLisenter() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.14
            @Override // com.psychiatrygarden.widget.PushCircleVoteDialog.OnChoosedLisenter
            public void onCircleChoosed(List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> votes, String isSingle) {
                PushCircleAndArticleAct.this.mLyVote.setVisibility(0);
                PushCircleAndArticleAct.this.mLyAddViewVote.removeAllViews();
                PushCircleAndArticleAct.this.mTvVoteType.setText(isSingle.equals("1") ? "单选" : "多选");
                for (int i2 = 0; i2 < votes.size(); i2++) {
                    PushCircleAndArticleAct.this.addOptionViews(votes.get(i2));
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
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15409c.lambda$addOptionViews$32(view);
            }
        });
        this.mLyAddViewVote.addView(relativeLayout);
    }

    public void getImageData(final String imgUrl, final boolean isNine) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("image", new File(imgUrl));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.getforumuploadImageApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.16
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(PushCircleAndArticleAct.this.mContext, "上传失败！", 0).show();
                PushCircleAndArticleAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass16) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        NewToast.showShort(PushCircleAndArticleAct.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                        AndroidImagePicker.getInstance().clearImageSets();
                    } else {
                        if (PushCircleAndArticleAct.this.isFinishing() || Looper.myLooper() != Looper.getMainLooper()) {
                            return;
                        }
                        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("url");
                        ImageDataBean imageDataBean = new ImageDataBean();
                        imageDataBean.setId(jCurrentThreadTimeMillis);
                        imageDataBean.setB_img(strOptString);
                        imageDataBean.setS_img(strOptString);
                        if (isNine) {
                            if (PushCircleAndArticleAct.this.mPicAdapter.getData().size() == 0) {
                                PushCircleAndArticleAct.this.mPicRecycler.setVisibility(0);
                                PushCircleAndArticleAct.this.mPicAdapter.addData((NinePicAdp) "");
                            }
                            if (PushCircleAndArticleAct.this.mPicAdapter.getData().size() < 10) {
                                PushCircleAndArticleAct.this.mPicAdapter.addData(0, (int) strOptString);
                                if (PushCircleAndArticleAct.this.mPicAdapter.getData().size() > 9) {
                                    Log.e("last_index", "index====>" + PushCircleAndArticleAct.this.mPicAdapter.getData().lastIndexOf(""));
                                    PushCircleAndArticleAct.this.mPicAdapter.removeAt(PushCircleAndArticleAct.this.mPicAdapter.getData().size() + (-1));
                                }
                            }
                            if (PushCircleAndArticleAct.this.mImageList == null) {
                                PushCircleAndArticleAct.this.mImageList = new ArrayList();
                            }
                            PushCircleAndArticleAct.this.mImageList.add(imageDataBean);
                            PushCircleAndArticleAct pushCircleAndArticleAct = PushCircleAndArticleAct.this;
                            SharePreferencesUtils.saveImageList(pushCircleAndArticleAct.mContext, CommonParameter.circleImage, pushCircleAndArticleAct.mImageList);
                        } else {
                            PushCircleAndArticleAct.this.sourceview_content.insertImage(strOptString + "?x-oss-process=image/resize,h_4096,m_lfit", Long.valueOf(jCurrentThreadTimeMillis), 0L, 0L);
                            if (PushCircleAndArticleAct.this.mImageAdminList == null) {
                                PushCircleAndArticleAct.this.mImageAdminList = new ArrayList();
                            }
                            PushCircleAndArticleAct.this.mImageAdminList.add(imageDataBean);
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                PushCircleAndArticleAct.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.isEdit = getIntent().getBooleanExtra("isEdit", false);
        this.isEitAnonymous = getIntent().getBooleanExtra("is_anonymous", false);
        this.htmlContent = getIntent().getStringExtra("htmlContent");
        this.htmlTitle = getIntent().getStringExtra("htmlTitle");
        this.moduleType = getIntent().getIntExtra("module_type", 12);
        this.mBtnBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBtnFinish = (TextView) findViewById(R.id.btn_finish);
        this.mEtTitle = (EditText) findViewById(R.id.et_title);
        this.sourceview_content = (CircleRichEditor) findViewById(R.id.sourceview_content);
        this.mPicRecycler = (RecyclerView) findViewById(R.id.pic_view);
        this.mLyCircleBook = (LinearLayout) findViewById(R.id.circle_book);
        this.mLyPushCircle = (LinearLayout) findViewById(R.id.push_circle);
        this.mLyCircleTouPiao = (LinearLayout) findViewById(R.id.circle_toupai);
        this.mBtnPic = (ImageView) findViewById(R.id.circle_paizhao);
        this.mBtnLink = (ImageView) findViewById(R.id.circle_lianjie);
        this.mBtnAdminPic = (ImageView) findViewById(R.id.img_admin_pic);
        this.mPushRecycler = (RecyclerView) findViewById(R.id.push_recyclerview);
        this.mLyAddViewVote = (LinearLayout) findViewById(R.id.ly_add_vote_view);
        this.mLyVote = (LinearLayout) findViewById(R.id.ly_vote);
        this.mImgCloseVote = (ImageView) findViewById(R.id.btn_cancel_vote);
        this.mTvVoteType = (TextView) findViewById(R.id.tv_vote_type);
        this.mLyFormatBar = (LinearLayout) findViewById(R.id.format_bar);
        this.mBookRecycler = (RecyclerView) findViewById(R.id.book_recyclerview);
        this.rlAnonymous = (RelativeLayout) findViewById(R.id.rl_anonymous);
        final ImageView imageView = (ImageView) findViewById(R.id.iv_anonymous_push);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.e1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15338c.lambda$init$0(imageView, view);
            }
        });
        NinePicAdp ninePicAdp = new NinePicAdp(this, false);
        this.mPicAdapter = ninePicAdp;
        this.mPicRecycler.setAdapter(ninePicAdp);
        PushBookAdp pushBookAdp = new PushBookAdp(true, new ArrayList());
        this.mBookAdp = pushBookAdp;
        this.mBookRecycler.setAdapter(pushBookAdp);
        this.mEtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.psychiatrygarden.forum.f1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                this.f15350c.lambda$init$2(view, z2);
            }
        });
        this.mEtTitle.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.forum.g1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f15353c.lambda$init$3(view, motionEvent);
            }
        });
        this.mBookAdp.setOnItemChoosedLisenter(new PushBookAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.2
            @Override // com.psychiatrygarden.forum.PushBookAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, PushBookData item) {
                PushCircleAndArticleAct.this.mBookAdp.removeAt(pos);
            }

            @Override // com.psychiatrygarden.forum.PushBookAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, PushBookData item) {
            }
        });
        this.mPicAdapter.setOnPicItemClickListener(new NinePicAdp.OnPicItemClickListener() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.3
            @Override // com.psychiatrygarden.forum.NinePicAdp.OnPicItemClickListener
            public void addPicClick(int pos, String url) {
                Log.e("last_index", "点击图片===》" + pos + ";url=" + url);
                PushCircleAndArticleAct.this.showChoosePic(true);
            }

            @Override // com.psychiatrygarden.forum.NinePicAdp.OnPicItemClickListener
            public void delPicClick(int pos, String url) {
                for (int i2 = 0; i2 < PushCircleAndArticleAct.this.mImageList.size(); i2++) {
                    if (((ImageDataBean) PushCircleAndArticleAct.this.mImageList.get(i2)).getB_img().equals(url)) {
                        PushCircleAndArticleAct.this.mImageList.remove(i2);
                    }
                }
                PushCircleAndArticleAct.this.mPicAdapter.remove(pos);
                if (PushCircleAndArticleAct.this.mPicAdapter.getData().size() == 1) {
                    PushCircleAndArticleAct.this.mPicRecycler.setVisibility(8);
                    PushCircleAndArticleAct.this.mPicAdapter.getData().clear();
                } else if (PushCircleAndArticleAct.this.mPicAdapter.getData().size() < 9) {
                    if (TextUtils.isEmpty(PushCircleAndArticleAct.this.mPicAdapter.getData().get(PushCircleAndArticleAct.this.mPicAdapter.getData().size() - 1))) {
                        return;
                    }
                    PushCircleAndArticleAct.this.mPicAdapter.addData(PushCircleAndArticleAct.this.mPicAdapter.getData().size(), (int) "");
                }
            }
        });
        this.mPushAdapter = new PushCircleAdp(true, false);
        OnItemDragListener onItemDragListener = new OnItemDragListener() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.4
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                ViewCompat.animate(viewHolder.itemView).setDuration(200L).scaleX(1.0f).scaleY(1.0f).start();
                Log.e("sort_circle", new Gson().toJson(PushCircleAndArticleAct.this.mPushAdapter.getData()));
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
        this.mPushAdapter.setOnItemChoosedLisenter(new PushCircleAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.5
            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, CirclrListBean.DataBean item) {
                PushCircleAndArticleAct.this.mPushAdapter.removeAt(pos);
            }

            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, CirclrListBean.DataBean item) {
            }
        });
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        super.initwriteStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_two_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_two_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
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
            data.getBundleExtra("bundleIntent").getString("cid");
            pushData(data.getBundleExtra("bundleIntent").getString("bodys"), data.getBundleExtra("bundleIntent").getString("virtual_user_id"));
            return;
        }
        if (this.isEdit) {
            editArticle(data.getExtras().getString("bodys"));
            return;
        }
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            data.getExtras().getString("cid");
            pushData(data.getExtras().getString("bodys"), "");
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
        if (!TextUtils.isEmpty(this.mEtTitle.getText().toString()) || !TextUtils.isEmpty(this.htmlContent) || this.isEdit || this.mBookAdp.getData().size() > 0 || this.mPushAdapter.getData().size() > 0 || this.mImageList.size() > 0 || this.mLyAddViewVote.getChildCount() > 0) {
            dialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
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

    public void pushTieziData() {
        String strReplace;
        boolean z2;
        String strTrim = this.htmlContent.replaceAll("<br/>", "").replace("<div>", "\n").replace("</div>", "").replace("<br>", "").replace("&nbsp;", "").replace("\n\t\t\t\t", "").replace(StrPool.CR, "").trim();
        if (TextUtils.isEmpty(this.mEtTitle.getText().toString().replace("\n", "").replaceAll(" ", ""))) {
            AlertToast("标题不能为空");
            return;
        }
        if (this.mEtTitle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() < 10 || this.mEtTitle.getText().toString().replace("\n", "").replaceAll(" ", "").toString().length() > 50) {
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
        this.mLinkDataListTemp.clear();
        Matcher matcher3 = Pattern.compile("<a[^>].*?</a>").matcher(strTrim);
        Pattern patternCompile = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher3.find()) {
            Matcher matcher4 = patternCompile.matcher(matcher3.group());
            if (matcher4.find()) {
                for (int i2 = 0; i2 < this.mLinkDataList.size(); i2++) {
                    if (this.mLinkDataList.get(i2).getId() == Long.parseLong(matcher4.group().substring(9, matcher4.group().length() - 1))) {
                        this.mLinkDataListTemp.add(this.mLinkDataList.get(i2));
                    }
                }
                strTrim = strTrim.replace(matcher3.group(), "[link]");
            } else {
                strTrim = strTrim.replace(matcher3.group(), "");
            }
        }
        this.imageDataTemp.clear();
        this.imageAdminDataTemp.clear();
        Matcher matcher5 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(strTrim);
        Pattern patternCompile2 = Pattern.compile("data-id=[\"].*?[\"]");
        while (matcher5.find()) {
            Matcher matcher6 = patternCompile2.matcher(matcher5.group());
            while (matcher6.find()) {
                for (int i3 = 0; i3 < this.mImageAdminList.size(); i3++) {
                    if (this.mImageAdminList.get(i3).getId() == Long.parseLong(matcher6.group().substring(9, matcher6.group().length() - 1))) {
                        this.imageAdminDataTemp.add(this.mImageAdminList.get(i3).getB_img());
                    }
                }
            }
            strTrim = strTrim.replace(matcher5.group(), "[image]");
        }
        StringBuilder sb = new StringBuilder(Pattern.compile(REGEX_HTML, 2).matcher(strTrim).replaceAll(""));
        for (int i4 = 0; i4 < this.mImageList.size(); i4++) {
            this.imageDataTemp.add(this.mImageList.get(i4).getB_img());
        }
        if (this.isEdit) {
            editArticle(sb.toString());
            return;
        }
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushData(sb.toString(), "");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("cid", "");
        bundle.putString("bodys", "" + sb.toString());
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 546);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_push_circle_and_article);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15391c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mEtTitle.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.forum.PushCircleAndArticleAct.11
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() > 50) {
                    NewToast.showShort(PushCircleAndArticleAct.this.mContext, "超出字数限制", 0).show();
                    return;
                }
                if (s2.length() < 10 || PushCircleAndArticleAct.this.strLeth < 10) {
                    PushCircleAndArticleAct.this.mBtnFinish.setEnabled(false);
                    PushCircleAndArticleAct.this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_gray_bg);
                } else {
                    PushCircleAndArticleAct.this.mBtnFinish.setEnabled(true);
                    PushCircleAndArticleAct.this.mBtnFinish.setBackgroundResource(R.drawable.shape_push_circle_finish_bg);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.sourceview_content.setOnTextLengthChangeListener(new RichEditor.OnTextLengthChangeListener() { // from class: com.psychiatrygarden.forum.s1
            @Override // jp.wasabeef.richeditor.RichEditor.OnTextLengthChangeListener
            public final void onTextLengthChange(long j2) {
                this.f15401a.lambda$setListenerForWidget$13(j2);
            }
        });
        this.sourceview_content.setOnFocusChangeListener(new RichEditor.OnFocusChangeListener() { // from class: com.psychiatrygarden.forum.t1
            @Override // jp.wasabeef.richeditor.RichEditor.OnFocusChangeListener
            public final void onFocusChange(boolean z2) {
                this.f15404a.lambda$setListenerForWidget$16(z2);
            }
        });
        this.sourceview_content.setOnTextChangeListener(new RichEditor.OnTextChangeListener() { // from class: com.psychiatrygarden.forum.u1
            @Override // jp.wasabeef.richeditor.RichEditor.OnTextChangeListener
            public final void onTextChange(String str) {
                this.f15407a.lambda$setListenerForWidget$17(str);
            }
        });
        this.mBtnLink.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.v1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15410c.lambda$setListenerForWidget$19(view);
            }
        });
        this.mBtnPic.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.w1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15413c.lambda$setListenerForWidget$20(view);
            }
        });
        this.mBtnAdminPic.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15394c.lambda$setListenerForWidget$21(view);
            }
        });
        this.sourceview_content.setOnLinkClickListener(new RichEditor.OnLinkClickListener() { // from class: com.psychiatrygarden.forum.r0
            @Override // jp.wasabeef.richeditor.RichEditor.OnLinkClickListener
            public final void onLinkClick(String str, String str2, String str3) {
                this.f15397a.lambda$setListenerForWidget$23(str, str2, str3);
            }
        });
        this.mLyCircleBook.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15400c.lambda$setListenerForWidget$24(view);
            }
        });
        this.mLyPushCircle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15403c.lambda$setListenerForWidget$25(view);
            }
        });
        this.mImgCloseVote.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.a1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15313c.lambda$setListenerForWidget$26(view);
            }
        });
        this.mLyCircleTouPiao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.l1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15378c.lambda$setListenerForWidget$27(view);
            }
        });
        this.mLyVote.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15395c.lambda$setListenerForWidget$28(view);
            }
        });
        this.mBtnFinish.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.r1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15398c.lambda$setListenerForWidget$29(view);
            }
        });
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.sourceview_content.setEditorBackgroundColor("#FFFFFF", ContextCompat.getColor(this, R.color.bg_backgroud));
        } else {
            this.sourceview_content.setEditorBackgroundColor("#121622", ContextCompat.getColor(this, R.color.app_bg_night));
        }
    }

    public static void newIntent(Context context, boolean isEdit, List<PushBookData> mBookList, int module_type) {
        Intent intent = new Intent(context, (Class<?>) PushCircleAndArticleAct.class);
        intent.putExtra("isEdit", isEdit);
        intent.putExtra("bookList", (Serializable) mBookList);
        intent.putExtra("module_type", module_type);
        context.startActivity(intent);
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
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.d1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15330c.lambda$addOptionViews$33(view);
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
