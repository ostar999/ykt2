package com.psychiatrygarden.fragmenthome;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.text.StrPool;
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
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.activity.SubQuestionMainActivity;
import com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import com.psychiatrygarden.bean.RecdQuestionBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BiaoqianInterface;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.AutoHeightViewPager;
import com.psychiatrygarden.widget.BiaoPupWindow;
import com.psychiatrygarden.widget.CirclePoint;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.OnSelectListener;
import com.psychiatrygarden.widget.SelectableTextHelper;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
public class SubjectiveQuestionFragment extends BaseFragment {
    private TextView biaotxt;
    private ColorStateList blackColors;
    private CirclePoint circlepoint;
    private String collection_id;
    private RelativeLayout commeview;
    private Drawable drawable;
    private ImageView imgtitle;
    private LinearLayout line_viewok;
    private LinearLayout lineout;
    private LinearLayout ll_more_columns;
    private QuestionVideoViewpagerAdapter mQuestionVideo;
    private LinearLayout mRadioGroup_content;
    private SelectableTextHelper mSelectableTextHelper;
    private RecdQuestionBean.DataBean mUpdateBean;
    private PopupWindow popupWindow_note;
    private LinearLayout questionans;
    private LinearLayout questiondetails_bottom_layout;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private Button questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private ImageView questiondetails_btn_zantong;
    private RelativeLayout questiondetails_layout_answer;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_contents;
    private TextView questiondetails_tv_statistics;
    private ColorStateList redColors;
    int redSelected;
    int redTransSelected;
    private RelativeLayout rl_question_video;
    private AutoHeightViewPager viewpager_question_video;

    /* renamed from: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment$5, reason: invalid class name */
    public class AnonymousClass5 extends AjaxCallBack<String> {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list, View view) {
            SubjectiveQuestionFragment.this.lambda$initBiaoQianData$24(list, view);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            try {
                SubjectiveQuestionFragment.this.biaotxt.setText("标签：？");
                SubjectiveQuestionFragment.this.mRadioGroup_content.removeAllViews();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass5) s2);
            try {
                BiaoqianBeab biaoqianBeab = (BiaoqianBeab) new Gson().fromJson(s2, BiaoqianBeab.class);
                if (biaoqianBeab.getCode().equals("200")) {
                    final List<BiaoqianBeab.DataBean> data = biaoqianBeab.getData();
                    SubjectiveQuestionFragment.this.initBiaoQianData(data);
                    SubjectiveQuestionFragment.this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.th
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f16021c.lambda$onSuccess$0(data, view);
                        }
                    });
                } else {
                    SubjectiveQuestionFragment.this.biaotxt.setText("标签：？");
                    SubjectiveQuestionFragment.this.mRadioGroup_content.removeAllViews();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private String getHtmlData(String bodyHTML) {
        return "<html>" + (SkinManager.getCurrentSkinType(this.mContext) == 0 ? "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#ffffff;}</style></head>" : "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#121622;}</style></head>") + "<body>" + bodyHTML + "</body></html>";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerData$26(ObservableEmitter observableEmitter) throws Exception {
        submitAnswered();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerData$27(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerDataCollect$20(int i2, ObservableEmitter observableEmitter) throws Exception {
        if (i2 == 1) {
            submitFavorites();
        } else {
            clearCollection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerDataCollect$21(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$16(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$17(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$18(String str, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        intent.putExtra("notestr", str);
        intent.putExtra("noteStatus", this.mUpdateBean.getmStaticsData().getIs_note() + "");
        startActivity(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$19() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$14(ArrayList arrayList, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, i2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$15(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$12(CharSequence charSequence) {
        try {
            ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$13(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$22(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(getActivity()).setSingleSrcView(this.imgtitle, this.mUpdateBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$10(View view) {
        if (this.mUpdateBean.getmStaticsData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.mUpdateBean.getmStaticsData().getIs_collection() == 0) {
            ObServerDataCollect(1);
            havaCollectimg();
            AlertToast("收藏成功！");
            this.mUpdateBean.getmStaticsData().setIs_collection(1);
            return;
        }
        ObServerDataCollect(2);
        noCollectimg();
        AlertToast("取消收藏成功！");
        this.mUpdateBean.getmStaticsData().setIs_collection(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$11(View view) {
        if (this.mUpdateBean.getmStaticsData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.mUpdateBean.getmStaticsData().getIs_note() != 0) {
            getNoteData(view);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        intent.putExtra("notestr", "");
        intent.putExtra("noteStatus", this.mUpdateBean.getmStaticsData().getIs_note() + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CommMentList2Activity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", this.mUpdateBean.getmStaticsData().getIs_comment() != 0);
        intent.putExtra("isZantongTrue", this.mUpdateBean.getmStaticsData().getIs_praise() != 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(CharSequence charSequence) {
        try {
            ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        if (isLogin()) {
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.fragmenthome.sh
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f15997a.lambda$initViews$5(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) {
        initViewClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) {
        doClickSimeThings("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) {
        doClickSimeThings("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$25(List list, boolean z2) {
        initBiaoQianData(list);
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (((BiaoqianBeab.DataBean) list.get(i2)).getUser_label().equals("1")) {
                arrayList.add(((BiaoqianBeab.DataBean) list.get(i2)).getId());
            }
        }
        if (arrayList.size() > 0) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                sb.append((String) arrayList.get(i3));
                if (i3 < arrayList.size() - 1) {
                    sb.append(",");
                }
            }
        }
        postBiaoqianData(this.mUpdateBean.getQuestion_id() + "", sb.toString());
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.mUpdateBean.getQuestion_id() + "");
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", "1");
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
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubjectiveQuestionFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SubjectiveQuestionFragment.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(SubjectiveQuestionFragment.this.mUpdateBean.getmStaticsData().getComment_count() + 1)));
                        SubjectiveQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(((BaseFragment) SubjectiveQuestionFragment.this).mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubjectiveQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubjectiveQuestionFragment.this.hideProgressDialog();
            }
        });
    }

    private void submitAnswered() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("answer", this.mUpdateBean.getIsRight().trim());
            jSONObject.put("question_id", this.mUpdateBean.getQuestion_id());
            jSONObject.put("is_right", this.mUpdateBean.getIsRight().trim());
            jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()));
            jSONArray.put(0, jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", jSONArray.toString());
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.mPostAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
            }
        });
    }

    private void submitFavorites() throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("question_id", this.mUpdateBean.getQuestion_id());
            jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()));
            jSONArray.put(0, jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("collection", jSONArray.toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
            }
        });
    }

    public void ObServerData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.ug
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f16054a.lambda$ObServerData$26(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.vg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubjectiveQuestionFragment.lambda$ObServerData$27(obj);
            }
        });
    }

    public void ObServerDataCollect(final int type) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.rg
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f15974a.lambda$ObServerDataCollect$20(type, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.ch
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubjectiveQuestionFragment.lambda$ObServerDataCollect$21(obj);
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id() + "");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.2
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
            }
        });
    }

    public void dialogNote(View v2, final String content) {
        View viewInflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.popu_note, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.popu_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.popu_edit);
        ((TextView) viewInflate.findViewById(R.id.tv_note_content)).setText(content);
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.oh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15900c.lambda$dialogNote$16(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ph
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15925c.lambda$dialogNote$17(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.qh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15950c.lambda$dialogNote$18(content, view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.fragmenthome.rh
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubjectiveQuestionFragment.lambda$dialogNote$19();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_note.showAtLocation(v2, 80, 0, 0);
    }

    public void doClickSimeThings(String isRight) {
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadout));
        this.line_viewok.setVisibility(8);
        this.questiondetails_bottom_layout.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadint));
        this.questiondetails_bottom_layout.setVisibility(0);
        this.questiondetails_layout_answer.setVisibility(0);
        this.mUpdateBean.setOwnerAns(isRight);
        this.mUpdateBean.setIsRight(isRight);
        initSubViewData();
        ObServerData();
    }

    public void getBiaoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.mlabelUrl, ajaxParams, new AnonymousClass5());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_subjective;
    }

    public void getNoteData(final View v2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mGetNoteUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SubjectiveQuestionFragment.this.dialogNote(v2, new JSONObject(s2).optJSONObject("data").optString("content"));
                    } else {
                        SubjectiveQuestionFragment.this.AlertToast("获取笔记失败！");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getRestoreStr(String str, TextView mTextV, String number, int type) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        String str2;
        Matcher matcher;
        int i2;
        String str3 = str;
        int i3 = type;
        Matcher matcher2 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str3);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        int i4 = 1;
        if ((this.collection_id.equals(Constants.VIA_REPORT_TYPE_CHAT_AUDIO) || this.collection_id.equals(Constants.VIA_REPORT_TYPE_CHAT_VIDEO)) && i3 == 1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str3);
            int i5 = 34;
            int i6 = 0;
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
            while (matcher2.find()) {
                int i7 = i6;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher2.start(i7), matcher2.end(i7), 34);
                i6 = i7;
                i5 = 34;
            }
            int i8 = i5;
            int i9 = i6;
            Matcher matcher3 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str3);
            while (matcher3.find()) {
                int i10 = i9;
                int i11 = i8;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher3.start(i10), matcher3.end(i10), i11);
                i9 = i10;
                i8 = i11;
            }
            int i12 = i9;
            int i13 = i8;
            Matcher matcher4 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str3);
            while (matcher4.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher4.start(i12), matcher4.end(i12), i13);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        int i14 = 0;
        String str4 = i3 != 1 ? i3 != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i15 = 0;
        while (matcher2.find()) {
            String strGroup = matcher2.group();
            String str5 = str3.substring(i14, matcher2.end(i14) + i15) + "&" + str3.substring(matcher2.end(i14) + i15, str3.length());
            if (i3 == i4 || i3 == 2) {
                str2 = str5;
                if (this.mUpdateBean.getIs_real_question().equals("1")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    matcher = matcher2;
                    sb.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
                    sb.append("/");
                    sb.append(number);
                    sb.append(strGroup.substring(1, strGroup.length() - 1));
                    sb.append("-");
                    sb.append(i15 + 1);
                    sb.append(".jpg?x-oss-process=style/water_mark");
                    arrayList.add(sb.toString());
                    i2 = 1;
                } else {
                    matcher = matcher2;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(NetworkRequestsURL.mCommentIameUrl3);
                    sb2.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
                    sb2.append("/");
                    sb2.append(this.mUpdateBean.getQuestion_id());
                    sb2.append(StrPool.UNDERLINE);
                    i2 = 1;
                    sb2.append(strGroup.substring(1, strGroup.length() - 1));
                    sb2.append("-");
                    sb2.append(i15 + 1);
                    sb2.append(".jpg?x-oss-process=style/water_mark");
                    arrayList.add(sb2.toString());
                }
            } else {
                str2 = str5;
                matcher = matcher2;
                i2 = i4;
            }
            i15++;
            str3 = str2;
            i4 = i2;
            matcher2 = matcher;
            i14 = 0;
            i3 = type;
        }
        Matcher matcher5 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str3);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str3);
        spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        int i16 = 0;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i17 = 0;
        while (matcher5.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher5.end(0), matcher5.end(0) + 1, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.fragmenthome.tg
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f16018a.lambda$getRestoreStr$14(arrayList, i17);
                }
            }), matcher5.start(0), matcher5.end(0), 33);
            mTextV.setHighlightColor(Color.parseColor("#00ffffff"));
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i17++;
        }
        Matcher matcher6 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str3);
        while (matcher6.find()) {
            int i18 = i16;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher6.start(i18), matcher6.end(i18), 34);
            i16 = i18;
        }
        int i19 = i16;
        Matcher matcher7 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str3);
        while (matcher7.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher7.start(i19), matcher7.end(i19), 34);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.11
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
                    QuestionDataStaticSetListBean questionDataStaticSetListBean = (QuestionDataStaticSetListBean) new Gson().fromJson(t2, QuestionDataStaticSetListBean.class);
                    if (!questionDataStaticSetListBean.getCode().equals("200") || questionDataStaticSetListBean.getData() == null || questionDataStaticSetListBean.getData().size() <= 0) {
                        return;
                    }
                    for (int i2 = 0; i2 < questionDataStaticSetListBean.getData().size(); i2++) {
                        if (questionDataStaticSetListBean.getData().get(i2).getQuestion_id().equals(SubjectiveQuestionFragment.this.mUpdateBean.getQuestion_id())) {
                            SubjectiveQuestionFragment.this.mUpdateBean.setmStaticsData(questionDataStaticSetListBean.getData().get(i2));
                            SubjectiveQuestionFragment.this.initStaticData();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void haveParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    public void initAnsData() {
        String str = "[答案解析]  " + this.mUpdateBean.getExplain();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.redColors, null), 0, 6, 34);
        Matcher matcher = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher.start(0), matcher.end(0), 34);
        }
        Matcher matcher2 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str);
        while (matcher2.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher2.start(0), matcher2.end(0), 34);
        }
        this.questiondetails_tv_contents.setText(spannableStringBuilder);
        SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(this.questiondetails_tv_contents).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
        this.mSelectableTextHelper = selectableTextHelperBuild;
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.sg
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f15996a.lambda$initAnsData$15(charSequence);
            }
        });
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (dataBiao == null || dataBiao.size() <= 0) {
            this.mRadioGroup_content.removeAllViews();
            this.biaotxt.setText("标签：？");
            return;
        }
        this.biaotxt.setText("标签：");
        this.mRadioGroup_content.removeAllViews();
        if (dataBiao.get(0).getCount() < 3) {
            TextView textView = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.mh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15854c.lambda$initBiaoQianData$23(dataBiao, view);
                }
            });
            this.mRadioGroup_content.addView(textView);
            return;
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (dataBiao.get(i2).getCount() >= 3) {
                TextView textView2 = (TextView) LayoutInflater.from(this.mContext).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.rightMargin = 20;
                textView2.setText(String.format(Locale.CHINA, "%s %d", dataBiao.get(i2).getLabel(), Integer.valueOf(dataBiao.get(i2).getCount())));
                try {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                textView2.setLayoutParams(layoutParams);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.nh
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15880c.lambda$initBiaoQianData$24(dataBiao, view);
                    }
                });
                this.mRadioGroup_content.addView(textView2);
            }
        }
    }

    public void initRestoreData() {
        try {
            if (TextUtils.isEmpty(this.mUpdateBean.getRestore())) {
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.questiondetails_tv_content_ques.setVisibility(8);
                return;
            }
            if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                String[] strArrSplit = this.mUpdateBean.getRestore().split("\\[\\$delimiter\\$]");
                if (strArrSplit.length >= 2) {
                    if (TextUtils.isEmpty(strArrSplit[1])) {
                        this.questiondetails_tv_content_ques.setVisibility(8);
                    } else {
                        this.questiondetails_tv_content_ques.setVisibility(0);
                        getRestoreStr("[八版还原] " + strArrSplit[1], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 1);
                    }
                    if (TextUtils.isEmpty(strArrSplit[0])) {
                        this.questiondetails_tv_content_ques1.setVisibility(8);
                    } else {
                        this.questiondetails_tv_content_ques1.setVisibility(0);
                        getRestoreStr("[九版还原] " + strArrSplit[0], this.questiondetails_tv_content_ques1, this.mUpdateBean.getNumber(), 2);
                    }
                } else {
                    this.questiondetails_tv_content_ques1.setVisibility(8);
                    this.questiondetails_tv_content_ques.setVisibility(0);
                    getRestoreStr("[九版还原] " + strArrSplit[0], this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 2);
                }
            } else {
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr("[八版还原] " + this.mUpdateBean.getRestore(), this.questiondetails_tv_content_ques, this.mUpdateBean.getNumber(), 1);
            }
            SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(this.questiondetails_tv_content_ques1).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
            this.mSelectableTextHelper = selectableTextHelperBuild;
            selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.wg
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f16103a.lambda$initRestoreData$12(charSequence);
                }
            });
            SelectableTextHelper selectableTextHelperBuild2 = new SelectableTextHelper.Builder(this.questiondetails_tv_content_ques).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
            this.mSelectableTextHelper = selectableTextHelperBuild2;
            selectableTextHelperBuild2.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.xg
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f16124a.lambda$initRestoreData$13(charSequence);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initStaticData() {
        String number;
        if (this.mUpdateBean.getmStaticsData() == null) {
            this.questiondetails_btn_commentNum.setText("?评论");
            this.questiondetails_tv_statistics.setText("统计：本题?人收藏，全部考生作答?次，对?次，正确率?%，本人作答?次，对?次，正确率?%");
            this.questiondetails_layout_diff.removeAllViews();
            TextView textView = new TextView(this.mContext);
            textView.setTextSize(12.0f);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
            } else {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("难度：?");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = 10;
            textView.setLayoutParams(layoutParams);
            this.questiondetails_layout_diff.addView(textView);
            return;
        }
        Double dValueOf = Double.valueOf(0.0d);
        if (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count() > 0) {
            dValueOf = Double.valueOf((this.mUpdateBean.getmStaticsData().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()));
            number = CommonUtil.getNumber(dValueOf.doubleValue());
        } else {
            number = "0";
        }
        this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mUpdateBean.getmStaticsData().getComment_count())));
        if (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count() > 0) {
            String str = "统计：本题" + this.mUpdateBean.getmStaticsData().getCollection_count() + "人收藏，全部考生作答" + (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()) + "次，对" + this.mUpdateBean.getmStaticsData().getRight_count() + "次，正确率" + number + "%，本人作答{" + (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count()) + "}次，对{" + this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count())) + "%}";
            if (dValueOf.doubleValue() > (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count())) {
                this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            } else {
                this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            }
        } else {
            String str2 = "统计：本题" + this.mUpdateBean.getmStaticsData().getCollection_count() + "人收藏，全部考生作答" + (this.mUpdateBean.getmStaticsData().getRight_count() + this.mUpdateBean.getmStaticsData().getWrong_count()) + "次，对" + this.mUpdateBean.getmStaticsData().getRight_count() + "次，正确率" + number + "%，本人作答{?}次，对{?}次，正确率{?%}";
            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
        }
        this.questiondetails_layout_diff.removeAllViews();
        TextView textView2 = new TextView(this.mContext);
        textView2.setTextSize(12.0f);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
        } else {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
        }
        textView2.setText("难度：");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.rightMargin = 10;
        textView2.setLayoutParams(layoutParams2);
        this.questiondetails_layout_diff.addView(textView2);
        int i2 = dValueOf.doubleValue() > 95.0d ? 1 : dValueOf.doubleValue() > 80.0d ? 2 : dValueOf.doubleValue() > 60.0d ? 3 : dValueOf.doubleValue() > 30.0d ? 4 : 5;
        for (int i3 = 0; i3 < 5; i3++) {
            ImageView imageView = new ImageView(this.mContext);
            if (i3 < i2) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_yellow));
                } else {
                    imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_yellow_night));
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_gary));
            } else {
                imageView.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.icon_star_gary_night));
            }
            this.questiondetails_layout_diff.addView(imageView);
        }
        if (this.mUpdateBean.getmStaticsData().getIs_note() == 0) {
            noNoteimg();
        } else {
            haveNoteimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_collection() == 0) {
            noCollectimg();
        } else {
            havaCollectimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_comment() == 0) {
            noCommingimg();
        } else {
            havaCommingimg();
        }
        if (this.mUpdateBean.getmStaticsData().getIs_praise() == 0) {
            noParise();
        } else {
            haveParise();
        }
    }

    public void initSubViewData() {
        if (this.mUpdateBean.getOwnerAns().equals("")) {
            this.questiondetails_layout_diff.setVisibility(8);
            this.questiondetails_tv_statistics.setVisibility(8);
            this.lineout.setVisibility(8);
            this.questionans.setVisibility(0);
            this.questiondetails_bottom_layout.setVisibility(8);
            this.commeview.setVisibility(8);
            this.questiondetails_layout_answer.setVisibility(8);
            return;
        }
        this.questiondetails_layout_diff.setVisibility(0);
        this.questiondetails_tv_statistics.setVisibility(0);
        this.lineout.setVisibility(0);
        this.questionans.setVisibility(8);
        this.questiondetails_bottom_layout.setVisibility(0);
        this.commeview.setVisibility(0);
        this.questiondetails_layout_answer.setVisibility(0);
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.mUpdateBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(this.mUpdateBean.getTitle_img())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.7
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                SubjectiveQuestionFragment.this.imgtitle.setImageResource(R.drawable.imgplacehodel_image);
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                SubjectiveQuestionFragment.this.imgtitle.setImageDrawable(resource);
                return true;
            }
        }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.6
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (resource != null) {
                    float intrinsicWidth = resource.getIntrinsicWidth();
                    float intrinsicHeight = resource.getIntrinsicHeight();
                    float width = SubjectiveQuestionFragment.this.imgtitle.getWidth();
                    if (width == 0.0f) {
                        width = SubjectiveQuestionFragment.this.imgtitle.getResources().getDisplayMetrics().widthPixels;
                    }
                    int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                    ViewGroup.LayoutParams layoutParams = SubjectiveQuestionFragment.this.imgtitle.getLayoutParams();
                    if (i2 >= 4096) {
                        layoutParams.height = R2.color.N_stretchTextColor;
                        SubjectiveQuestionFragment.this.imgtitle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        layoutParams.height = -1;
                    }
                    SubjectiveQuestionFragment.this.imgtitle.setLayoutParams(layoutParams);
                }
            }
        });
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.kh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15727c.lambda$initTitleImg$22(view);
            }
        });
    }

    public void initVideoData() {
        String[] strArrSplit;
        if (this.mUpdateBean.getMedia_img().equals("")) {
            this.rl_question_video.setVisibility(8);
            return;
        }
        this.rl_question_video.setVisibility(0);
        try {
            this.circlepoint.removeAllViews();
            String media_img = this.mUpdateBean.getMedia_img();
            if (TextUtils.equals(media_img, "") || (strArrSplit = media_img.split(",")) == null || strArrSplit.length <= 0) {
                return;
            }
            if (strArrSplit.length > 1) {
                this.circlepoint.setCount(strArrSplit.length);
                this.circlepoint.initViewData();
                this.circlepoint.invalidate();
            }
            QuestionVideoViewpagerAdapter questionVideoViewpagerAdapter = new QuestionVideoViewpagerAdapter(this.mContext, strArrSplit, Long.parseLong(this.mUpdateBean.getQuestion_id()), 10);
            this.mQuestionVideo = questionVideoViewpagerAdapter;
            this.viewpager_question_video.setAdapter(questionVideoViewpagerAdapter);
            final int length = strArrSplit.length;
            this.viewpager_question_video.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.4
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (length > 1) {
                        SubjectiveQuestionFragment.this.circlepoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initViewClick() {
        this.questionans.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadout));
        this.questionans.setVisibility(8);
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadint));
        this.line_viewok.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        String str;
        Bundle arguments = getArguments();
        this.mUpdateBean = ((SubQuestionMainActivity) getActivity()).mDataList.get(arguments != null ? arguments.getInt("positionT") : 0);
        this.collection_id = arguments != null ? arguments.getString("collection_id") : "0";
        WeakReference weakReference = new WeakReference(this.drawable);
        if (weakReference.get() != null) {
            this.drawable = (Drawable) weakReference.get();
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan);
        } else {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan_night);
        }
        this.biaotxt = (TextView) holder.get(R.id.biaotxt);
        TextView textView = (TextView) holder.get(R.id.tv_correction);
        TextView textView2 = (TextView) holder.get(R.id.questiondetails_tv_childTitle);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.mRadioGroup_content = (LinearLayout) holder.get(R.id.mRadioGroup_content);
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.questiondetails_tv_contents = (TextView) holder.get(R.id.questiondetails_tv_contents);
        this.questionans = (LinearLayout) holder.get(R.id.questionans);
        this.questiondetails_bottom_layout = (LinearLayout) holder.get(R.id.questiondetails_bottom_layout);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.questiondetails_layout_answer = (RelativeLayout) holder.get(R.id.questiondetails_layout_answer);
        this.commeview = (RelativeLayout) holder.get(R.id.commeview);
        this.questiondetails_tv_content_ques1 = (TextView) holder.get(R.id.questiondetails_tv_content_ques1);
        this.questiondetails_tv_content_ques = (TextView) holder.get(R.id.questiondetails_tv_content_ques);
        TextView textView3 = (TextView) holder.get(R.id.zuoduil);
        TextView textView4 = (TextView) holder.get(R.id.zuocuol);
        this.rl_question_video = (RelativeLayout) holder.get(R.id.rl_question_video);
        this.viewpager_question_video = (AutoHeightViewPager) holder.get(R.id.viewpager_question_video);
        this.circlepoint = (CirclePoint) holder.get(R.id.circlepoint);
        TextView textView5 = (TextView) holder.get(R.id.questiondetails_tv_outline);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        this.redSelected = ContextCompat.getColor(this.mContext, R.color.app_theme_red);
        this.redTransSelected = ContextCompat.getColor(this.mContext, R.color.trans_app_theme_red);
        this.questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.yg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16149c.lambda$initViews$0(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.bh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15484c.lambda$initViews$1(view);
            }
        });
        this.questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.dh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15553c.lambda$initViews$2(view);
            }
        });
        this.imgtitle = (ImageView) holder.get(R.id.imgtitle);
        this.lineout = (LinearLayout) holder.get(R.id.lineout);
        Button button = (Button) holder.get(R.id.btn_comment);
        this.line_viewok = (LinearLayout) holder.get(R.id.line_viewok);
        initSubViewData();
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.eh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15577c.lambda$initViews$3(view);
            }
        });
        if (TextUtils.isEmpty(this.mUpdateBean.getOutlines_pm())) {
            textView5.setVisibility(8);
        } else {
            textView5.setVisibility(0);
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            str = "<font color='#dd594a'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        } else {
            str = "<font color='#B2575C'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            textView5.setText(Html.fromHtml(str, 0));
        } else {
            textView5.setText(Html.fromHtml(str));
        }
        SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(textView5).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
        this.mSelectableTextHelper = selectableTextHelperBuild;
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.fh
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f15604a.lambda$initViews$4(charSequence);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.gh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15631c.lambda$initViews$6(view);
            }
        });
        try {
            ColorStateList colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.black);
            if (TextUtils.isEmpty(this.mUpdateBean.getNumber())) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getType_str() + "] " + this.mUpdateBean.getTitle());
                TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, CommonUtil.dip2px(this.mContext, 12.0f), colorStateList, null);
                StringBuilder sb = new StringBuilder();
                sb.append(this.mUpdateBean.getS_num_pm());
                sb.append(StrPool.DOT);
                spannableStringBuilder.setSpan(textAppearanceSpan, sb.toString().length(), (this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getType_str() + StrPool.BRACKET_END).length(), 34);
                textView2.setText(spannableStringBuilder);
            } else {
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getNumber() + "] " + this.mUpdateBean.getTitle());
                TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(null, 0, CommonUtil.dip2px(this.mContext, 12.0f), colorStateList, null);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mUpdateBean.getS_num_pm());
                sb2.append(StrPool.DOT);
                spannableStringBuilder2.setSpan(textAppearanceSpan2, sb2.toString().length(), (this.mUpdateBean.getS_num_pm() + ".[" + this.mUpdateBean.getNumber() + StrPool.BRACKET_END).length(), 34);
                textView2.setText(spannableStringBuilder2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.questionans.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.hh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15653c.lambda$initViews$7(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ih
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15676c.lambda$initViews$8(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.jh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15700c.lambda$initViews$9(view);
            }
        });
        initVideoData();
        initTitleImg();
        initStaticData();
        getBiaoData();
        initRestoreData();
        initAnsData();
        getStaticsData();
        this.questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.zg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16173c.lambda$initViews$10(view);
            }
        });
        this.questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ah
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15448c.lambda$initViews$11(view);
            }
        });
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noCommingimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    public void noParise() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        QuestionVideoViewpagerAdapter questionVideoViewpagerAdapter = this.mQuestionVideo;
        if (questionVideoViewpagerAdapter != null) {
            questionVideoViewpagerAdapter.destoryHandler();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("notesave")) {
            this.mUpdateBean.getmStaticsData().setIs_note(1);
            haveNoteimg();
        }
        if (str.equals("noteclear")) {
            this.mUpdateBean.getmStaticsData().setIs_note(0);
            noNoteimg();
        }
    }

    public void postBiaoqianData(String question_id, String label_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.muserLabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubjectiveQuestionFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        return;
                    }
                    NewToast.showShort(((BaseFragment) SubjectiveQuestionFragment.this).mContext, jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* renamed from: showlog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$initBiaoQianData$24(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupWindow(getActivity(), dataBiao, new BiaoqianInterface() { // from class: com.psychiatrygarden.fragmenthome.lh
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianInterface
            public final void mBiaoianLinster(List list, boolean z2) {
                this.f15817a.lambda$showlog$25(list, z2);
            }
        })).toggle();
    }
}
