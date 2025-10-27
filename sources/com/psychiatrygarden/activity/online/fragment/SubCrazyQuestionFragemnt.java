package com.psychiatrygarden.activity.online.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.NoteNewActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.online.AnswerQuestionActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.QuestionStatDataBean;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshQuestionBottomIconEvent;
import com.psychiatrygarden.event.UpdateQuestionCutEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConfigUtils;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.english.PopNoteList;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.tag.FlowLayout;
import com.psychiatrygarden.widget.tag.TagAdapter;
import com.psychiatrygarden.widget.tag.TagFlowLayout;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubCrazyQuestionFragemnt extends BaseFragment implements QuestionDataCallBack<String> {
    private String chapter_id;
    private String chapter_parent_id;
    private RelativeLayout commeview;
    private String identity_id;
    private WebView img_webview;
    private ImageView imgtitle;
    private LinearLayout line_viewok;
    private LinearLayout llay_kuangbei_zhenti;
    private ImageView mCutQuestionFlag;
    private TagFlowLayout mFlowLayout;
    private ImageView mImgAd;
    private ImageView mImgCloseAd;
    private RelativeLayout mLyAdView;
    private QuestionDetailBean mUpdateBean;
    private String module_type = "4";
    private LinearLayout questionans;
    private LinearLayout questiondetails_bottom_layout;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private Button questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_statistics;
    private long startTime;
    private QuestionDetailBean tagQuestionDetailBean;
    private TextView textView_difficulty;
    private TextView tv_frequency;
    private String type;
    private android.webkit.WebView webview;
    private android.webkit.WebView webviewhint;

    private String getHtmlData(String bodyHTML) {
        return "<html>" + (SkinManager.getCurrentSkinType(this.mContext) == 0 ? "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#ffffff;}</style></head>" : "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:auto; height:auto;} \n body{background-color:#121622;}</style></head>") + "<body>" + bodyHTML + "</body></html>";
    }

    public static SubCrazyQuestionFragemnt getInstance(Bundle args) {
        SubCrazyQuestionFragemnt subCrazyQuestionFragemnt = new SubCrazyQuestionFragemnt();
        subCrazyQuestionFragemnt.setArguments(args);
        return subCrazyQuestionFragemnt;
    }

    private void initAd(final Context mContext) {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_QUESTION_AD, mContext, ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            this.mLyAdView.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            this.mLyAdView.setVisibility(8);
            return;
        }
        this.mLyAdView.setVisibility(0);
        if (dataAd.getForce().equals("1")) {
            this.mImgCloseAd.setVisibility(8);
        } else {
            this.mImgCloseAd.setVisibility(0);
            this.mImgCloseAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.i2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13249c.lambda$initAd$16(mContext, view);
                }
            });
        }
        if (dataAd.getAds() == null || dataAd.getAds().size() <= 0) {
            return;
        }
        GlideApp.with(mContext).load((Object) GlideUtils.generateUrl(dataAd.getAds().get(0).getImg())).placeholder(R.mipmap.ic_order_default).into(this.mImgAd);
        this.mImgAd.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.j2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubCrazyQuestionFragemnt.lambda$initAd$17(dataAd, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$12(String str) {
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getId());
        intent.putExtra("notestr", str);
        intent.putExtra("module_type", this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getTagData$14(View view, int i2, FlowLayout flowLayout) {
        if (CommonUtil.isFastClick()) {
            return true;
        }
        QuestionDataRequest.getIntance(getActivity()).questionInfo(this.mUpdateBean.getRelation().get(i2).getQuestion_id(), "1", this);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getTagData$15(Set set) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAd$16(Context context, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_BOTTOM_AD, Long.valueOf(System.currentTimeMillis()), context);
        this.mLyAdView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initAd$17(HomepageSmallAdBean.DataDTO.DataAd dataAd, View view) {
        PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataAd.getAds().get(0).getExtra()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$13(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(this.imgtitle, this.mUpdateBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("obj_id", this.mUpdateBean.getId());
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getId()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra("iscomValu", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$10(QuestionNoteBean questionNoteBean) {
        this.mUpdateBean.getStatData().setIs_note(1);
        haveNoteimg();
        this.mUpdateBean.setNote(questionNoteBean.getContent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$11(View view) {
        if (this.mUpdateBean.getStatData() == null) {
            AlertToast("原题加载中,请稍后再试！");
        } else if (this.mUpdateBean.getStatData().getIs_note() == 0) {
            new DialogNoteInput(getContext(), this.module_type, this.mUpdateBean.getId(), new onDialogNoteListener() { // from class: com.psychiatrygarden.activity.online.fragment.f2
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f13223a.lambda$initViews$10(questionNoteBean);
                }
            }).show();
        } else {
            new XPopup.Builder(getActivity()).moveUpToKeyboard(Boolean.FALSE).enableDrag(true).asCustom(new PopNoteList(this.mContext, this.module_type, this.mUpdateBean.getId())).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("obj_id", this.mUpdateBean.getId());
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getId()));
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra("iscomValu", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CommMentList2Activity.class);
        intent.putExtra("obj_id", this.mUpdateBean.getId());
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getId()));
        intent.putExtra("module_type", Integer.parseInt(this.module_type));
        intent.putExtra("comment_type", "2");
        intent.putExtra("title", "评论");
        intent.putExtra("isCommentTrue", this.mUpdateBean.getStatData().getIs_comment() != 0);
        intent.putExtra("isZantongTrue", this.mUpdateBean.getStatData().getIs_praise() != 0);
        intent.putExtra("commentEnum", DiscussStatus.QuestionBankComments);
        intent.putExtra("viewType", "1");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$initViews$5(View view) {
        if (isLogin()) {
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.h2
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f13240a.lambda$initViews$4(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        initViewClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) throws JSONException {
        doClickSimeThings("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) throws JSONException {
        doClickSimeThings("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) throws JSONException {
        if (this.mUpdateBean.getStatData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        String knowledge_id = this.mUpdateBean.getKnowledge_id();
        this.mUpdateBean.setModule_type(TextUtils.isEmpty(knowledge_id) ? this.module_type : "110");
        QuestionDetailBean questionDetailBean = this.mUpdateBean;
        questionDetailBean.setIs_redo(TextUtils.isEmpty(questionDetailBean.getIs_redo()) ? "0" : this.mUpdateBean.getIs_redo());
        this.mUpdateBean.setUnit_title(ProjectApp.unit_title);
        this.mUpdateBean.setUnit_id(getArguments().getString("unit_id", ""));
        this.mUpdateBean.setExam_title(ProjectApp.exam_title);
        this.mUpdateBean.setIdentity_id(ProjectApp.identity_id);
        this.mUpdateBean.setIdentity_title(ProjectApp.identity_title);
        this.mUpdateBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        this.mUpdateBean.setChapter_parent_id(this.chapter_parent_id);
        if (!TextUtils.isEmpty(knowledge_id)) {
            QuestionDetailBean questionDetailBean2 = this.mUpdateBean;
            questionDetailBean2.setChapter_parent_id(questionDetailBean2.getChapter_parent_id());
        }
        String json = ProjectApp.gson.toJson(this.mUpdateBean);
        AjaxParams ajaxParams = new AjaxParams();
        if (this.mUpdateBean.getStatData().getIs_collection() == 0) {
            havaCollectimg();
            AlertToast("收藏成功！");
            this.mUpdateBean.getStatData().setIs_collection(1);
            this.mUpdateBean.getStatData().setCollection_count(this.mUpdateBean.getStatData().getCollection_count() + 1);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("question_id", this.mUpdateBean.getId());
                jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity(), "1"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            jSONArray.put(jSONObject);
            ajaxParams.put("collection", jSONArray.toString());
            ajaxParams.put("module_type", "" + this.module_type);
            QuestionDataRequest.getIntance(getActivity()).questionDoCollectData(ajaxParams, this);
            String str = "[\"" + this.mUpdateBean.getId() + "\"]";
            String str2 = "[\"" + this.mUpdateBean.getTitle() + "\"]";
            AliyunEvent aliyunEvent = AliyunEvent.CollectionQuestion;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
        } else {
            noCollectimg();
            AlertToast("取消收藏成功！");
            this.mUpdateBean.getStatData().setIs_collection(0);
            if (this.mUpdateBean.getStatData().getCollection_count() > 0) {
                this.mUpdateBean.getStatData().setCollection_count(this.mUpdateBean.getStatData().getCollection_count() - 1);
            }
            ajaxParams.put("question_id", this.mUpdateBean.getId());
            ajaxParams.put("module_type", this.module_type);
            QuestionDataRequest.getIntance(getActivity()).cleancollection(ajaxParams, this);
            String str3 = "[\"" + this.mUpdateBean.getId() + "\"]";
            String str4 = "[\"" + this.mUpdateBean.getTitle() + "\"]";
            AliyunEvent aliyunEvent2 = AliyunEvent.CancelCollectionQuestion;
            CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
        }
        initStaticData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUserVisibleHint$0() {
        initAd(this.mContext);
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.mUpdateBean.getId());
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("module_type", this.module_type);
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
        QuestionDataRequest.getIntance(getActivity()).pushComment(ajaxParams, this);
    }

    private void refreshCommentIcon(int isComment) {
        if (isComment == 0) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
                return;
            } else {
                this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    private void refreshPraiseIcon(int isPraise) {
        if (isPraise == 0) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse);
                return;
            } else {
                this.questiondetails_btn_zantong.setImageResource(R.drawable.dianzancourse_night);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    private void setFontSize() {
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getActivity(), 2);
        int pxBySp = ScreenUtil.getPxBySp(this.mContext, 2);
        Button button = this.questiondetails_btn_commentNum;
        int i2 = intConfig - 2;
        float f2 = pxBySp * i2;
        button.setTextSize(0, ((Float) button.getTag()).floatValue() - f2);
        TextView textView = this.questiondetails_tv_statistics;
        textView.setTextSize(0, ((Float) textView.getTag()).floatValue() - f2);
        TextView textView2 = this.tv_frequency;
        textView2.setTextSize(0, ((Float) textView2.getTag()).floatValue() - f2);
        TextView textView3 = this.textView_difficulty;
        textView3.setTextSize(0, ((Float) textView3.getTag()).floatValue() - f2);
        int i3 = 100 - (i2 * 10);
        this.webview.getSettings().setTextZoom(i3);
        this.webviewhint.getSettings().setTextZoom(i3);
        getTagData();
    }

    public void dialogNote(final String content) {
        new XPopup.Builder(getActivity()).asCustom(new NoteNewPopWindow(this.mContext, content, new NoteNewPopWindow.NoteClickIml() { // from class: com.psychiatrygarden.activity.online.fragment.e2
            @Override // com.psychiatrygarden.activity.online.popwindow.NoteNewPopWindow.NoteClickIml
            public final void mDoClickMethod() {
                this.f13214a.lambda$dialogNote$12(content);
            }
        })).toggle();
    }

    public void doClickSimeThings(String isRight) throws JSONException {
        if (!CommonUtil.isNetworkConnected(getActivity())) {
            AlertToast("请您检查网络是否连接");
            return;
        }
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadout));
        this.line_viewok.setVisibility(8);
        this.questiondetails_bottom_layout.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadint));
        this.questiondetails_bottom_layout.setVisibility(0);
        this.mUpdateBean.setUser_answer(isRight);
        initSubViewData();
        if (this.mUpdateBean.getStatData().getAnswer() != null) {
            if (isRight.equals("1")) {
                this.mUpdateBean.getStatData().getAnswer().setRight_count(this.mUpdateBean.getStatData().getAnswer().getRight_count() + 1);
            } else {
                this.mUpdateBean.getStatData().getAnswer().setWrong_count(this.mUpdateBean.getStatData().getAnswer().getWrong_count() + 1);
            }
        }
        submitQuestionData();
        initStaticData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_sub_crazy;
    }

    public void getNoteData(final View v2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.mUpdateBean.getId());
        ajaxParams.put("module_type", this.module_type);
        QuestionDataRequest.getIntance(getActivity()).questionNoteData(ajaxParams, this);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getId());
        ajaxParams.put("module_type", this.module_type + "");
        QuestionDataRequest.getIntance(getActivity()).questionStatData(ajaxParams, this);
    }

    public void getTagData() {
        try {
            String[] strArr = new String[this.mUpdateBean.getRelation().size()];
            for (int i2 = 0; i2 < this.mUpdateBean.getRelation().size(); i2++) {
                strArr[i2] = this.mUpdateBean.getRelation().get(i2).getNumber();
            }
            this.mFlowLayout.setAdapter(new TagAdapter<String>(strArr) { // from class: com.psychiatrygarden.activity.online.fragment.SubCrazyQuestionFragemnt.1
                @Override // com.psychiatrygarden.widget.tag.TagAdapter
                public View getView(FlowLayout parent, int position, String s2) {
                    TextView textView = (TextView) LayoutInflater.from(((BaseFragment) SubCrazyQuestionFragemnt.this).mContext).inflate(R.layout.adapter_kuangbei_timu, (ViewGroup) SubCrazyQuestionFragemnt.this.mFlowLayout, false);
                    int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, SubCrazyQuestionFragemnt.this.getActivity(), 2);
                    textView.setTextSize(0, textView.getTextSize() - ((intConfig - 2) * ScreenUtil.getPxBySp(((BaseFragment) SubCrazyQuestionFragemnt.this).mContext, 2)));
                    textView.setText(s2);
                    return textView;
                }
            });
            this.mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.c2
                @Override // com.psychiatrygarden.widget.tag.TagFlowLayout.OnTagClickListener
                public final boolean onTagClick(View view, int i3, FlowLayout flowLayout) {
                    return this.f13198a.lambda$getTagData$14(view, i3, flowLayout);
                }
            });
            this.mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() { // from class: com.psychiatrygarden.activity.online.fragment.l2
                @Override // com.psychiatrygarden.widget.tag.TagFlowLayout.OnSelectListener
                public final void onSelected(Set set) {
                    SubCrazyQuestionFragemnt.lambda$getTagData$15(set);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void initStaticData() {
        double right_count;
        if (this.mUpdateBean.getStatData().getAnswer() != null) {
            if (this.mUpdateBean.getStatData().getRight_count() + this.mUpdateBean.getStatData().getWrong_count() > 0) {
                right_count = (this.mUpdateBean.getStatData().getRight_count() * 100) / (this.mUpdateBean.getStatData().getRight_count() + this.mUpdateBean.getStatData().getWrong_count());
                CommonUtil.getNumber(right_count);
            } else {
                right_count = 0.0d;
            }
            this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mUpdateBean.getStatData().getComment_count())));
            if (this.mUpdateBean.getStatData().getAnswer().getRight_count() + this.mUpdateBean.getStatData().getAnswer().getWrong_count() > 0) {
                String str = "统计：" + this.mUpdateBean.getStatData().getStat_info() + "本人作答{" + (this.mUpdateBean.getStatData().getAnswer().getRight_count() + this.mUpdateBean.getStatData().getAnswer().getWrong_count()) + "}次，对{" + this.mUpdateBean.getStatData().getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.mUpdateBean.getStatData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getStatData().getAnswer().getRight_count() + this.mUpdateBean.getStatData().getAnswer().getWrong_count())) + "%}";
                if (right_count > (this.mUpdateBean.getStatData().getAnswer().getRight_count() * 100) / (this.mUpdateBean.getStatData().getAnswer().getRight_count() + this.mUpdateBean.getStatData().getAnswer().getWrong_count())) {
                    this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                } else {
                    this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                }
            } else {
                String str2 = "统计：" + this.mUpdateBean.getStatData().getStat_info() + "本人作答{0}次，对{0}次，正确率{0%}";
                this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(this.mContext) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
            }
            if (this.questiondetails_layout_diff.getChildCount() > 1) {
                LinearLayout linearLayout = this.questiondetails_layout_diff;
                linearLayout.removeViews(1, linearLayout.getChildCount() - 1);
            }
            int i2 = right_count > 95.0d ? 1 : right_count > 80.0d ? 2 : right_count > 60.0d ? 3 : right_count > 30.0d ? 4 : 5;
            for (int i3 = 0; i3 < 5; i3++) {
                ImageView imageView = new ImageView(this.mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.rightMargin = ScreenUtil.getPxByDp(this.mContext, 5);
                imageView.setLayoutParams(layoutParams);
                if (i3 < i2) {
                    if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                        imageView.setImageResource(R.drawable.icon_star_yellow);
                    } else {
                        imageView.setImageResource(R.drawable.icon_star_yellow_nights);
                    }
                } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    imageView.setImageResource(R.drawable.icon_star_gary);
                } else {
                    imageView.setImageResource(R.drawable.icon_star_gary_night);
                }
                this.questiondetails_layout_diff.addView(imageView);
            }
            if (this.mUpdateBean.getStatData().getIs_note() == 0) {
                noNoteimg();
            } else {
                haveNoteimg();
            }
            if (this.mUpdateBean.getStatData().getIs_collection() == 0) {
                noCollectimg();
            } else {
                havaCollectimg();
            }
            refreshCommentIcon(this.mUpdateBean.getStatData().getIs_comment());
            refreshPraiseIcon(this.mUpdateBean.getStatData().getIs_praise());
        } else {
            if (this.mUpdateBean.getStatData() != null) {
                this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mUpdateBean.getStatData().getComment_count())));
            } else {
                this.questiondetails_btn_commentNum.setText("?评论");
            }
            this.questiondetails_tv_statistics.setText(this.mUpdateBean.getStatData().getStat_info() + "本人作答0次，对0次，正确率0%");
        }
        if (ConfigUtils.isHidden(2)) {
            this.questiondetails_tv_statistics.setVisibility(8);
        }
        if (ConfigUtils.isHidden(1)) {
            this.questiondetails_layout_diff.setVisibility(8);
        }
    }

    public void initSubViewData() {
        if (this.mUpdateBean.getUser_answer().equals("")) {
            this.questiondetails_layout_diff.setVisibility(8);
            this.questiondetails_tv_statistics.setVisibility(8);
            this.questionans.setVisibility(0);
            this.questiondetails_bottom_layout.setVisibility(8);
            this.commeview.setVisibility(8);
            this.llay_kuangbei_zhenti.setVisibility(8);
        } else {
            this.questiondetails_layout_diff.setVisibility(0);
            this.questiondetails_tv_statistics.setVisibility(0);
            this.questionans.setVisibility(8);
            this.questiondetails_bottom_layout.setVisibility(0);
            this.commeview.setVisibility(0);
            this.llay_kuangbei_zhenti.setVisibility(0);
        }
        if (ConfigUtils.isHidden(1)) {
            this.questiondetails_layout_diff.setVisibility(8);
        }
        if (ConfigUtils.isHidden(2)) {
            this.questiondetails_tv_statistics.setVisibility(8);
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.mUpdateBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            this.img_webview.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        this.img_webview.setVisibility(0);
        this.img_webview.loadDataWithBaseURL(null, "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + this.mUpdateBean.getTitle_img() + " /></body></html>", "text/html", "utf-8", null);
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.k2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13268c.lambda$initTitleImg$13(view);
            }
        });
    }

    public void initTitleView(String bodyHTML) {
        String strReplaceAll = !TextUtils.isEmpty(bodyHTML) ? bodyHTML.replaceAll("#@", "<span style=\"color:#fff;position: relative;vertical-align: -5px;border-bottom: 1px dashed #888;margin: 0 5px;\"><b style=\"top: -5px;position: relative;\">").replaceAll("@#", "</b></span>") : bodyHTML;
        StringBuilder sb = new StringBuilder("<p style=\"color:#444;line-height:34px;font-size:16px;margin:0;padding:0;\">");
        sb.append(this.mUpdateBean.getSort());
        sb.append(StrPool.DOT);
        sb.append("<span style=\"color:#e85353\">");
        sb.append("&nbsp;");
        sb.append(this.mUpdateBean.getPage());
        sb.append("</span>&nbsp;");
        sb.append(strReplaceAll);
        sb.append("<p>");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.webview.loadDataWithBaseURL(null, getHtmlData(sb.toString().replace("color:#fff", "color:#000")), "text/html; charset=utf-8", "utf-8", null);
        } else {
            this.webview.loadDataWithBaseURL(null, getHtmlData(sb.toString().replace("dashed #888", "dashed #64729F").replace("color:#fff", "#64729F").replace("color:#444", "color:#64729F")), "text/html; charset=utf-8", "utf-8", null);
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.webviewhint.loadDataWithBaseURL(null, getHtmlData(sb.toString()), "text/html; charset=utf-8", "utf-8", null);
        } else {
            this.webviewhint.loadDataWithBaseURL(null, getHtmlData(sb.toString().replace("color:#444", "color:#64729F").replace("color:#fff", "color:#121622").replace("dashed #888", "dashed #64729F")), "text/html; charset=utf-8", "utf-8", null);
        }
    }

    public void initViewClick() {
        this.questionans.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadout));
        this.questionans.setVisibility(8);
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadint));
        this.line_viewok.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000L);
        this.webview.startAnimation(alphaAnimation);
        this.webview.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if ((getActivity() instanceof AnswerQuestionActivity) && getArguments() != null) {
            this.mUpdateBean = ((AnswerQuestionActivity) getActivity()).getQuestionDetailBean(getArguments().getInt("position", 0));
            this.module_type = getArguments().getString("module_type");
            this.identity_id = getArguments().getString("identity_id", "");
            this.chapter_id = getArguments().getString("chapter_id", "");
            this.chapter_parent_id = getArguments().getString("chapter_parent_id", "");
            this.type = getArguments().getString("type");
        }
        if (this.mUpdateBean == null) {
            return;
        }
        boolean z2 = getArguments().getBoolean("fromQuestionCombine", false);
        ImageView imageView = (ImageView) holder.get(R.id.iv_cut_flag);
        this.mCutQuestionFlag = imageView;
        imageView.setVisibility((!TextUtils.equals("1", this.mUpdateBean.getIs_cut()) || z2) ? 8 : 0);
        if ("1".equals(this.mUpdateBean.getIs_cut()) && !z2) {
            this.mCutQuestionFlag.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.ic_cut_day : R.drawable.ic_cut_night);
        }
        this.webview = (android.webkit.WebView) holder.get(R.id.webview);
        TextView textView = (TextView) holder.get(R.id.tv_question_new);
        this.webviewhint = (android.webkit.WebView) holder.get(R.id.webviewhint);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.textView_difficulty = (TextView) holder.get(R.id.textView_difficulty);
        this.tv_frequency = (TextView) holder.get(R.id.tv_frequency);
        this.questionans = (LinearLayout) holder.get(R.id.questionans);
        this.mFlowLayout = (TagFlowLayout) holder.get(R.id.id_flowlayout);
        this.llay_kuangbei_zhenti = (LinearLayout) holder.get(R.id.llay_kuangbei_zhenti);
        this.questiondetails_bottom_layout = (LinearLayout) holder.get(R.id.questiondetails_bottom_layout);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.mLyAdView = (RelativeLayout) holder.get(R.id.ly_ad_view);
        this.mImgAd = (ImageView) holder.get(R.id.img_ad);
        this.mImgCloseAd = (ImageView) holder.get(R.id.btn_close);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_zantong);
        LinearLayout linearLayout2 = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_centerMsg);
        LinearLayout linearLayout3 = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_collect);
        LinearLayout linearLayout4 = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
        this.img_webview = (WebView) holder.get(R.id.img_webview);
        this.commeview = (RelativeLayout) holder.get(R.id.commeview);
        TextView textView2 = (TextView) holder.get(R.id.zuoduil);
        TextView textView3 = (TextView) holder.get(R.id.zuocuol);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.m2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13284c.lambda$initViews$1(view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.n2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13292c.lambda$initViews$2(view);
            }
        });
        this.imgtitle = (ImageView) holder.get(R.id.imgtitle);
        TextView textView4 = (TextView) holder.get(R.id.btn_comment);
        this.line_viewok = (LinearLayout) holder.get(R.id.line_viewok);
        initSubViewData();
        if (this.mUpdateBean.getUser_answer().equals("")) {
            this.webviewhint.setVisibility(0);
            this.webview.setVisibility(8);
        } else {
            this.webviewhint.setVisibility(8);
            this.webview.setVisibility(0);
        }
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.o2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13300c.lambda$initViews$3(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.p2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13309c.lambda$initViews$5(view);
            }
        });
        initTitleView(this.mUpdateBean.getTitle());
        this.questionans.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.q2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13318c.lambda$initViews$6(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.r2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13326c.lambda$initViews$7(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.s2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13336c.lambda$initViews$8(view);
            }
        });
        initTitleImg();
        if (this.mUpdateBean.getIs_new().equals("1")) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(4);
        }
        initStaticData();
        if (this.mUpdateBean.getStatData().getAnswer() == null) {
            getStaticsData();
        }
        getTagData();
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.t2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13346c.lambda$initViews$9(view);
            }
        });
        linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.d2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13206c.lambda$initViews$11(view);
            }
        });
        Button button = this.questiondetails_btn_commentNum;
        button.setTag(Float.valueOf(button.getTextSize()));
        TextView textView5 = this.questiondetails_tv_statistics;
        textView5.setTag(Float.valueOf(textView5.getTextSize()));
        TextView textView6 = this.tv_frequency;
        textView6.setTag(Float.valueOf(textView6.getTextSize()));
        TextView textView7 = this.textView_difficulty;
        textView7.setTag(Float.valueOf(textView7.getTextSize()));
        setFontSize();
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1) {
            pushComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NoteEventBean noteEventBean) {
        if (noteEventBean.question_id.equals(this.mUpdateBean.getId())) {
            String str = noteEventBean.content;
            if (str == null || "".equals(str)) {
                this.mUpdateBean.getStatData().setIs_note(0);
                noNoteimg();
            } else {
                this.mUpdateBean.getStatData().setIs_note(1);
                haveNoteimg();
            }
            this.mUpdateBean.setNote(noteEventBean.content);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.getanswerApi)) {
            ToastUtil.shortToast(getActivity(), strMsg);
            return;
        }
        if (!requstUrl.equals(NetworkRequestsURL.mPutComment)) {
            ToastUtil.shortToast(getActivity(), strMsg);
        } else if (errorNo == 401) {
            new CusomNewDialog(this.mContext).setMessage(strMsg).show();
        } else {
            ToastUtil.shortToast(getActivity(), strMsg);
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            this.startTime = jCurrentTimeMillis;
            SharePreferencesUtils.writeLongConfig("question_startTime", Long.valueOf(jCurrentTimeMillis), this.mContext);
            LogUtils.e("question_load_time", "startTime===>" + this.startTime);
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.g2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13231c.lambda$setUserVisibleHint$0();
                }
            }, 600L);
        }
    }

    public void submitQuestionData() throws JSONException {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long jLongValue = SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue() == 0 ? this.startTime : SharePreferencesUtils.readLongConfig("question_startTime", this.mContext, 0L).longValue();
        this.startTime = jLongValue;
        long j2 = jCurrentTimeMillis - jLongValue;
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            if (getArguments() != null) {
                jSONObject.put("paper_id", getArguments().getBoolean("fromQuestionCombine", false) ? Integer.parseInt(getArguments().getString("paperId", "0")) : 0);
            }
            jSONObject.put("answer", this.mUpdateBean.getUser_answer().trim());
            jSONObject.put("question_id", this.mUpdateBean.getId());
            jSONObject.put("is_right", this.mUpdateBean.getIs_right().trim());
            jSONObject.put("app_id", this.mUpdateBean.getApp_id());
            jSONObject.put("identity_id", this.identity_id);
            jSONObject.put("chapter_id", this.chapter_id);
            jSONObject.put("chapter_parent_id", this.chapter_parent_id);
            jSONObject.put("duration", j2 + "");
            jSONObject.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
            jSONArray.put(0, jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogUtils.e("question_load_time", "答题时长：提交答案接口==》" + j2);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("answer", jSONArray.toString());
        ajaxParams.put("module_type", this.module_type);
        ajaxParams.put("question_category_id", getArguments().getString("question_bank_id", ""));
        QuestionDataRequest.getIntance(getActivity()).questionPutAnswerData(ajaxParams, this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(s2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (requstUrl.equals(NetworkRequestsURL.getstatApi)) {
            QuestionStatDataBean questionStatDataBean = (QuestionStatDataBean) new Gson().fromJson(s2, QuestionStatDataBean.class);
            if (questionStatDataBean == null || !questionStatDataBean.getCode().equals("200") || questionStatDataBean.getData().getAnswer() == null) {
                return;
            }
            this.mUpdateBean.setStatData(questionStatDataBean.getData());
            initStaticData();
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.getnoteApi)) {
            this.mUpdateBean.setNote(jSONObject.optJSONObject("data").optString("content"));
            dialogNote(jSONObject.optJSONObject("data").optString("content"));
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.mPutComment)) {
            ProjectApp.comment_content = "";
            ProjectApp.comment_b_img = "";
            ProjectApp.comment_s_img = "";
            ProjectApp.commentvideoPath = "";
            ProjectApp.commentvideoImage = "";
            ProjectApp.commentvideoId = "";
            ProjectApp.commentvideoImagePath = "";
            this.mUpdateBean.getStatData().setComment_count(this.mUpdateBean.getStatData().getComment_count() + 1);
            this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mUpdateBean.getStatData().getComment_count())));
            ToastUtil.shortToast(getActivity(), jSONObject.optString("message"));
            EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
            CommonUtil.showFristDialog(new JSONObject(s2));
            return;
        }
        if (requstUrl.equals(NetworkRequestsURL.getanswerApi)) {
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_ANSWER_REFRESH, this.mUpdateBean, this.type));
                    return;
                }
                return;
            } catch (JSONException e3) {
                e3.printStackTrace();
                return;
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.questionInfoApi)) {
            try {
                JSONObject jSONObject2 = new JSONObject(s2);
                if (jSONObject2.optInt("code") != 200) {
                    ToastUtil.shortToast(getActivity(), jSONObject2.optString("message"));
                    return;
                }
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject2.optString("data")), QuestionDetailBean.class);
                this.tagQuestionDetailBean = questionDetailBean;
                if (TextUtils.isEmpty(questionDetailBean.getSort())) {
                    this.tagQuestionDetailBean.setSort("1");
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("question_id", this.tagQuestionDetailBean.getId());
                ajaxParams.put("module_type", "1");
                QuestionDataRequest.getIntance(getActivity()).questionUserAnswer(ajaxParams, this);
                return;
            } catch (JSONException e4) {
                e4.printStackTrace();
                return;
            }
        }
        if (requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            try {
                JSONObject jSONObject3 = new JSONObject(s2);
                if (jSONObject3.optInt("code") != 200) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(this.tagQuestionDetailBean);
                    ProjectApp.showQuestionList = new Gson().toJson(arrayList);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    bundle.putString("module_type", "1");
                    bundle.putString("subject_title", this.tagQuestionDetailBean.getChapter_parent_title());
                    bundle.putString("chapter_title", this.tagQuestionDetailBean.getChapter_title());
                    AnswerQuestionActivity.gotoActivity(getActivity(), bundle);
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject3.getJSONArray("data").length() > 0) {
                    this.tagQuestionDetailBean.setUser_answer(jSONObject3.getJSONArray("data").getJSONObject(0).optString("answer"));
                }
                arrayList2.add(this.tagQuestionDetailBean);
                ProjectApp.showQuestionList = new Gson().toJson(arrayList2);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("position", 0);
                bundle2.putString("module_type", "1");
                bundle2.putString("subject_title", this.tagQuestionDetailBean.getChapter_parent_title());
                bundle2.putString("chapter_title", this.tagQuestionDetailBean.getChapter_title());
                AnswerQuestionActivity.gotoActivity(getActivity(), bundle2);
                return;
            } catch (JSONException e5) {
                e5.printStackTrace();
                return;
            }
        }
        return;
        e2.printStackTrace();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshQuestionBottomIconEvent event) {
        if (event.getQuestionId().equals(this.mUpdateBean.getId())) {
            this.mUpdateBean.getStatData().setIs_praise(event.getIsPraise());
            this.mUpdateBean.getStatData().setIs_comment(event.getIsComment());
            refreshPraiseIcon(this.mUpdateBean.getStatData().getIs_praise());
            refreshCommentIcon(this.mUpdateBean.getStatData().getIs_comment());
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateQuestionCutEvent event) {
        if (TextUtils.equals(event.getQuestionId(), this.mUpdateBean.getId())) {
            if (this.questionans != null) {
                this.mUpdateBean.setIs_cut(event.getIsCut());
            }
            this.mCutQuestionFlag.setVisibility("1".equals(event.getIsCut()) ? 0 : 8);
            this.mCutQuestionFlag.setImageResource(SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.ic_cut_day : R.drawable.ic_cut_night);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    @Override // com.psychiatrygarden.baseview.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onEventMainThread(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -393026403(0xffffffffe892e49d, float:-5.5494614E24)
            if (r0 == r1) goto L19
            r1 = -16147779(0xffffffffff099abd, float:-1.8290768E38)
            if (r0 == r1) goto Lf
            goto L23
        Lf:
            java.lang.String r0 = "EVENT_QUESTION_FONT_SIZE"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L23
            r3 = 0
            goto L24
        L19:
            java.lang.String r0 = "EVENT_REFRESH_COMMENT_NUM"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L23
            r3 = 1
            goto L24
        L23:
            r3 = -1
        L24:
            if (r3 == 0) goto L27
            goto L2a
        L27:
            r2.setFontSize()
        L2a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.SubCrazyQuestionFragemnt.onEventMainThread(java.lang.String):void");
    }
}
