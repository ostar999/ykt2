package com.psychiatrygarden.activity.answer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
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
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.EditNoteActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.activity.answer.AnswerNQuestionFragment;
import com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.answer.compose.ComposeActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBean;
import com.psychiatrygarden.bean.SubmitFavoritesBean;
import com.psychiatrygarden.bean.SubmitFavoritesBeanDao;
import com.psychiatrygarden.bean.WrongBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.BiaoqianInterface;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.AnalyPopWindow;
import com.psychiatrygarden.widget.BiaoPupWindow;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.eclipse.jetty.servlet.ServletHandler;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AnswerNQuestionFragment extends BaseFragment implements AnalysisAdapter.AnaImClickIml {
    private AnalysisAdapter analysisAdapter;
    private LinearLayout ansline;
    private TextView biaotxt;
    private ColorStateList blackColors;
    private Button btn_comment;
    private String chapter_id;
    private Drawable drawable;
    private TextView eitv;
    private ColorStateList grayColors;
    private ImageView imgtitle;
    private boolean isXitong;
    private boolean iszhongyibank;
    private LinearLayout line_viewok;
    private LinearLayout lineout;
    private LinearLayout ll_more_columns;
    private LinearLayout mRadioGroup_content;
    public RefreshLayout mRefresh;
    private TextView nitv;
    private TextView pagenumtv;
    private int position;
    private RelativeLayout qbrel;
    private QuestionInfoBean questionInfoBean;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private Button questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private TextView questiondetails_btn_pushAnswer;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_contents;
    private TextView questiondetails_tv_outline;
    private TextView questiondetails_tv_statistics;
    private RecyclerView recyansitem;
    private LinearLayout schildline;
    private TextView sourcetv;
    private TextView titletv;
    private String total;
    private TextView tv_answer_analyze;
    private TextView tv_correction;
    private String type;
    private TextView typeStr;
    private WebView web;
    private TextView zuocuol;
    private TextView zuoduil;
    private long question_id = 0;
    private AnalysisBean.DataBean mAnalysisBean = new AnalysisBean.DataBean();
    private final List<AnalysisBean.DataBean> dataAnaList = new ArrayList();
    private QuestionDataStaticSetListBean.DataBean mDataBean = new QuestionDataStaticSetListBean.DataBean();
    private String typechild = ServletHandler.__DEFAULT_SERVLET;
    private int page = 1;

    /* renamed from: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment$10, reason: invalid class name */
    public class AnonymousClass10 implements OnSelectListener {
        public AnonymousClass10() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSelect$0(RefreshLayout refreshLayout) {
        }

        @Override // com.lxj.xpopup.interfaces.OnSelectListener
        public void onSelect(int position, String text) {
            AnswerNQuestionFragment.this.mRefresh.resetNoMoreData();
            AnswerNQuestionFragment.this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.answer.b0
                @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    AnswerNQuestionFragment.AnonymousClass10.lambda$onSelect$0(refreshLayout);
                }
            });
            AnswerNQuestionFragment.this.typechild = text;
            AnswerNQuestionFragment.this.page = 1;
            AnswerNQuestionFragment.this.initAnalyAdapterData();
        }
    }

    /* renamed from: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment$5, reason: invalid class name */
    public class AnonymousClass5 extends AjaxCallBack<String> {
        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list, View view) {
            AnswerNQuestionFragment.this.lambda$initBiaoQianData$13(list, view);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            try {
                AnswerNQuestionFragment.this.biaotxt.setText("标签：？");
                AnswerNQuestionFragment.this.mRadioGroup_content.removeAllViews();
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
                    AnswerNQuestionFragment.this.initBiaoQianData(data);
                    AnswerNQuestionFragment.this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.c0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11013c.lambda$onSuccess$0(data, view);
                        }
                    });
                } else {
                    AnswerNQuestionFragment.this.biaotxt.setText("标签：？");
                    AnswerNQuestionFragment.this.mRadioGroup_content.removeAllViews();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            AnswerNQuestionFragment.this.initSubViewData();
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$0(ArrayList arrayList, int i2) {
        CommonUtil.doPicture(getActivity(), arrayList, i2, null, R.drawable.kaodianhuanyuan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$10(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            pushComment(bundle);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$11(View view) {
        if (isLogin()) {
            new DialogInput(getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.answer.v
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f11066a.lambda$initQuestionType$10(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$2(View view) {
        goToAnalysis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$3(View view) {
        boolean z2;
        if (CommonUtil.isFastClick()) {
            return;
        }
        QuestionDataStaticSetListBean.DataBean dataBean = this.mDataBean;
        if (dataBean != null) {
            z = 1 == dataBean.getIs_comment();
            z2 = 1 == this.mDataBean.getIs_praise();
        } else {
            z2 = false;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CommMentList2Activity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", z);
        intent.putExtra("isZantongTrue", z2);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$4(View view) {
        doClickSimeThings("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$5(View view) {
        doClickSimeThings("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$6(View view) throws SQLException {
        changeCollectData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$7(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.question_id + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$8(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.Comment_library_Red_Dot, false, getActivity());
        EventBus.getDefault().post(CommonParameter.Comment_library_Red_Dot);
        Intent intent = new Intent(getActivity(), (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$9(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("module_type", 1);
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewComzantong", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshView$1(RefreshLayout refreshLayout) {
        this.page++;
        getAllAnalysisData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$15(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, this.questionInfoBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$14(List list, boolean z2) {
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
        postBiaoqianData(this.question_id + "", sb.toString());
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.question_id + "");
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
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AnswerNQuestionFragment.this.hideProgressDialog();
                AnswerNQuestionFragment.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        AnswerNQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                        AnswerNQuestionFragment.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(AnswerNQuestionFragment.this.mDataBean.getComment_count())));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(AnswerNQuestionFragment.this.getActivity()).setMessage(jSONObject.optString("message")).show();
                    } else {
                        AnswerNQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                    AnswerNQuestionFragment.this.AlertToast("数据异常！");
                }
                AnswerNQuestionFragment.this.hideProgressDialog();
            }
        });
    }

    public void bigOrAns() {
        String str;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, getActivity(), "").equals(CommonParameter.Xueshuo)) {
            str = "大纲：" + this.questionInfoBean.getXuehsuodagang();
            if (TextUtils.isEmpty(this.questionInfoBean.getXuehsuodagang())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else {
            str = "大纲：" + this.questionInfoBean.getZhuanshuodagang();
            if (TextUtils.isEmpty(this.questionInfoBean.getZhuanshuodagang())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        Matcher matcher = Pattern.compile("考试大纲未要求").matcher(str);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.grayColors, null), matcher.start(0), matcher.end(0), 34);
        }
        this.questiondetails_tv_outline.setText(spannableStringBuilder);
    }

    public void changeCollectData() throws SQLException {
        if (getFavoritesBeanData() != null) {
            AlertToast("取消收藏成功");
            noCollectimg();
            ProjectApp.mDaoSession.getFavoritesBeanDao().deleteByKey(Long.valueOf(this.question_id));
            ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().queryBuilder().where(SubmitFavoritesBeanDao.Properties.Question_app_id.eq(this.question_id + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            clearCollection();
            return;
        }
        AlertToast("收藏成功");
        havaCollectimg();
        ProjectApp.mDaoSession.getFavoritesBeanDao().insertOrReplace(new FavoritesBean(this.questionInfoBean.getQuestion_id(), this.questionInfoBean.getChapter_parent_id(), this.questionInfoBean.getChapter_id(), this.questionInfoBean.getYear(), this.questionInfoBean.getS_num(), this.questionInfoBean.getNumber_number(), this.questionInfoBean.getMedia_img(), this.questionInfoBean.getIsxueshuo(), this.questionInfoBean.getIszhuanshuo(), this.questionInfoBean.getIsgaopinkaodian(), this.questionInfoBean.getIs_practice(), this.questionInfoBean.getUnit(), this.questionInfoBean.getPart_id(), this.questionInfoBean.getPart_parent_id(), this.questionInfoBean.getPart_num_am(), this.questionInfoBean.getPart_num_pm(), this.questionInfoBean.getType()));
        ProjectApp.mDaoSession.getSubmitFavoritesBeanDao().insertOrReplace(new SubmitFavoritesBean(Long.valueOf(this.question_id), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.question_id + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
    }

    public void changeNoteData(View v2) {
        NotesBean notesBeanData = getNotesBeanData();
        if (notesBeanData != null) {
            CommonUtil.dialogNote(v2, getActivity(), notesBeanData.getContent(), this.question_id);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) EditNoteActivity.class);
        intent.putExtra("question_id", this.question_id);
        startActivity(intent);
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.2
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

    public void doClickSimeThings(String isRight) {
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadout));
        this.line_viewok.setVisibility(8);
        this.schildline.setVisibility(0);
        this.qbrel.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadint));
        this.qbrel.setVisibility(0);
        doPushAnsData(isRight);
        initSubViewData();
    }

    public void doPushAnsData(String isRight) {
        if ("1".equals(isRight)) {
            ProjectApp.mDaoSession.getWrongBeanDao().deleteByKey(Long.valueOf(this.question_id));
            QuestionDataStaticSetListBean.DataBean dataBean = this.mDataBean;
            if (dataBean != null) {
                dataBean.getAnswer().setRight_count(this.mDataBean.getAnswer().getRight_count() + 1);
            }
        } else {
            ProjectApp.mDaoSession.getWrongBeanDao().insertOrReplace(new WrongBean(Long.valueOf(this.question_id), this.questionInfoBean.getChapter_parent_id(), this.questionInfoBean.getChapter_id(), this.questionInfoBean.getYear(), this.questionInfoBean.getS_num(), this.questionInfoBean.getNumber_number(), this.questionInfoBean.getMedia_img(), this.questionInfoBean.getIsxueshuo(), this.questionInfoBean.getIszhuanshuo(), this.questionInfoBean.getIsgaopinkaodian(), this.questionInfoBean.getIs_practice(), this.questionInfoBean.getUnit(), this.questionInfoBean.getPart_id(), this.questionInfoBean.getPart_parent_id(), this.questionInfoBean.getPart_num_am(), this.questionInfoBean.getPart_num_pm(), this.questionInfoBean.getType()));
            QuestionDataStaticSetListBean.DataBean dataBean2 = this.mDataBean;
            if (dataBean2 != null) {
                dataBean2.getAnswer().setWrong_count(this.mDataBean.getAnswer().getWrong_count() + 1);
            }
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int i2 = calendar.get(1);
        int i3 = calendar.get(2) + 1;
        int i4 = calendar.get(5);
        ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().insertOrReplace(new AnsweredQuestionBean(Long.valueOf(this.question_id), isRight, this.questionInfoBean.getChapter_parent_id(), this.questionInfoBean.getChapter_id(), isRight, this.questionInfoBean.getAnswer(), this.questionInfoBean.getNumber(), this.questionInfoBean.getNumber_number(), null, this.questionInfoBean.getMedia_img(), this.questionInfoBean.getYear(), this.questionInfoBean.getIsxueshuo(), this.questionInfoBean.getIszhuanshuo(), this.questionInfoBean.getIsgaopinkaodian(), i2 + "", i3 + "", i4 + "", this.questionInfoBean.getIs_practice(), this.questionInfoBean.getUnit(), this.questionInfoBean.getPart_id(), this.questionInfoBean.getPart_parent_id(), this.questionInfoBean.getPart_num_am(), this.questionInfoBean.getPart_num_pm(), this.questionInfoBean.getType()));
        ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(this.question_id), isRight, isRight, SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.questionInfoBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
    }

    public void getAllAnalysisData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.question_id);
        ajaxParams.put("type", "" + this.typechild);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put(DatabaseManager.SIZE, com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put("break_point", AndroidBaseUtils.GetUTCTime().toString());
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.qanalistApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.4
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
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        new ArrayList();
                        List list = (List) new Gson().fromJson(new JSONObject(s2).optString("data"), new TypeToken<List<AnalysisBean.DataBean>>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.4.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            AnswerNQuestionFragment.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            AnswerNQuestionFragment.this.analysisAdapter.addData((Collection) list);
                            AnswerNQuestionFragment.this.mRefresh.finishLoadMore();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getAnasisData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.question_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.myAnalysisForQuestionApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AnswerNQuestionFragment.this.initAnalyAdapterData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    try {
                        AnalysisBean analysisBean = (AnalysisBean) new Gson().fromJson(s2, AnalysisBean.class);
                        if (analysisBean.getCode().equals("200")) {
                            AnswerNQuestionFragment.this.mAnalysisBean = analysisBean.getData();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } finally {
                    AnswerNQuestionFragment.this.initSubViewData();
                    AnswerNQuestionFragment.this.initAnalyAdapterData();
                }
            }
        });
    }

    public void getBiaoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.question_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mlabelUrl, ajaxParams, new AnonymousClass5());
    }

    public FavoritesBean getFavoritesBeanData() {
        return ProjectApp.mDaoSession.getFavoritesBeanDao().loadByRowId(this.question_id);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_answer_nquestion;
    }

    public NotesBean getNotesBeanData() {
        return ProjectApp.mDaoSession.getNotesBeanDao().loadByRowId(this.question_id);
    }

    public void getRestoreStr(String str, TextView mTextV, String number, int type, String source_filter) {
        String str2;
        String str3 = str;
        Matcher matcher = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str3);
        ColorStateList colorStateList = ContextCompat.getColorStateList(this.mContext, SkinManager.getCurrentSkinType(getActivity()) == 0 ? R.color.app_theme_red : R.color.jiucuo_night);
        ColorStateList colorStateList2 = ContextCompat.getColorStateList(this.mContext, SkinManager.getCurrentSkinType(getActivity()) == 0 ? R.color.black : R.color.question_color_night);
        int i2 = 34;
        int i3 = 0;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP) && type == 1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str3);
            while (matcher.find()) {
                ColorStateList colorStateList3 = colorStateList;
                ColorStateList colorStateList4 = colorStateList;
                int i4 = i2;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), matcher.start(0), matcher.end(0), i4);
                i2 = i4;
                i3 = 0;
                colorStateList = colorStateList4;
            }
            int i5 = i3;
            int i6 = i2;
            Matcher matcher2 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str3);
            while (matcher2.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher2.start(i5), matcher2.end(i5), i6);
            }
            Matcher matcher3 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str3);
            while (matcher3.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher3.start(i5), matcher3.end(i5), i6);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        int i7 = 0;
        String str4 = type == 1 ? NetworkRequestsURL.CommentIameUrl : type == 2 ? NetworkRequestsURL.CommentIameUrl2 : "";
        final ArrayList arrayList = new ArrayList();
        int i8 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String str5 = str3.substring(i7, matcher.end(i7) + i8) + "&" + str3.substring(matcher.end(i7) + i8);
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity());
            if ("考研真题".equals(source_filter)) {
                strConfig = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
            } else if ("执医真题".equals(source_filter)) {
                strConfig = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR;
            }
            if (type == 1 || type == 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(str4);
                sb.append(strConfig);
                sb.append("/");
                sb.append(number);
                str2 = str5;
                sb.append(strGroup.substring(1, strGroup.length() - 1));
                sb.append("-");
                sb.append(i8 + 1);
                sb.append(".jpg?x-oss-process=style/water_mark");
                arrayList.add(sb.toString());
            } else {
                str2 = str5;
            }
            i8++;
            str3 = str2;
            i7 = 0;
        }
        Matcher matcher4 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P\\d+(-P?(\\d+)?)?+[)）]").matcher(str3);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str3);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        int i9 = 0;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i10 = 0;
        while (matcher4.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher4.end(i9), matcher4.end(i9) + 1, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.activity.answer.k
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f11036a.lambda$getRestoreStr$0(arrayList, i10);
                }
            }), matcher4.start(i9), matcher4.end(i9), 33);
            mTextV.setHighlightColor(Color.parseColor("#00ffffff"));
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i10++;
            i9 = 0;
        }
        Matcher matcher5 = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(str3);
        while (matcher5.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher5.start(0), matcher5.end(0), 34);
        }
        Matcher matcher6 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str3);
        while (matcher6.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList2, null), matcher6.start(0), matcher6.end(0), 34);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.9
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
                        if (questionDataStaticSetListBean.getData().get(i2).getQuestion_id().equals(AnswerNQuestionFragment.this.question_id + "")) {
                            AnswerNQuestionFragment.this.mDataBean = questionDataStaticSetListBean.getData().get(i2);
                            AnswerNQuestionFragment.this.initStaticData();
                            return;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public AnsweredQuestionBean getmAnsweredQuestionBean() {
        AnsweredQuestionBean answeredQuestionBeanLoad = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().load(Long.valueOf(this.question_id));
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) != 2 || answeredQuestionBeanLoad != null) {
            return answeredQuestionBeanLoad;
        }
        AnsweredQuestionBean answeredQuestionBean = new AnsweredQuestionBean();
        answeredQuestionBean.setQuestion_id(Long.valueOf(this.question_id));
        answeredQuestionBean.setAnswer("1");
        answeredQuestionBean.setIs_right("2");
        return answeredQuestionBean;
    }

    public void goToAnalysis() {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) ComposeActivity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("mAnalysisBean", this.mAnalysisBean);
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.AnaImClickIml
    public void gotoAnalysisView() {
        goToAnalysis();
    }

    public void havaCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_yes_night);
        }
    }

    public void havaCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.youpinglun_night);
        }
    }

    public void haveNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edited_night);
        }
    }

    public void haveParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan);
        } else {
            this.questiondetails_btn_zantong.setImageResource(R.drawable.youdianzan_night);
        }
    }

    public void initAnalyAdapterData() {
        this.dataAnaList.clear();
        AnalysisBean.DataBean dataBean = new AnalysisBean.DataBean();
        dataBean.setViewType(0);
        this.dataAnaList.add(dataBean);
        this.dataAnaList.add(this.mAnalysisBean);
        this.analysisAdapter.setList(this.dataAnaList);
    }

    public void initAnsData() {
        String explain = this.questionInfoBean.getExplain();
        if ("".equals(explain)) {
            this.tv_answer_analyze.setVisibility(8);
            this.questiondetails_tv_contents.setVisibility(8);
            return;
        }
        this.tv_answer_analyze.setVisibility(0);
        this.questiondetails_tv_contents.setVisibility(0);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(explain);
        Matcher matcher = Pattern.compile("[(（]*.[A-E]+(\\s+)?对.*?[)）]").matcher(explain);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher.start(0), matcher.end(0), 34);
        }
        Matcher matcher2 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(explain);
        while (matcher2.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher2.start(0), matcher2.end(0), 34);
        }
        this.questiondetails_tv_contents.setText(spannableStringBuilder);
    }

    public void initBiaoQianData(final List<BiaoqianBeab.DataBean> dataBiao) {
        if (this.mContext == null || getActivity() == null) {
            return;
        }
        if (dataBiao == null || dataBiao.size() <= 0) {
            this.mRadioGroup_content.removeAllViews();
            this.biaotxt.setText("标签：？");
            return;
        }
        this.biaotxt.setText("标签：");
        this.mRadioGroup_content.removeAllViews();
        if (dataBiao.get(0).getCount() < 3) {
            TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11051c.lambda$initBiaoQianData$12(dataBiao, view);
                }
            });
            this.mRadioGroup_content.addView(textView);
            return;
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (dataBiao.get(i2).getCount() >= 3) {
                TextView textView2 = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.biaoqianview, (ViewGroup) null).findViewById(R.id.bqview);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.rightMargin = 20;
                textView2.setText(String.format(Locale.CHINA, "%s %d", dataBiao.get(i2).getLabel(), Integer.valueOf(dataBiao.get(i2).getCount())));
                try {
                    if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                        textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                textView2.setLayoutParams(layoutParams);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.r
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11054c.lambda$initBiaoQianData$13(dataBiao, view);
                    }
                });
                this.mRadioGroup_content.addView(textView2);
            }
        }
    }

    public void initQuestionType() {
        initRefreshView();
        initStaticData();
        if ("1".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("单选题");
        } else if ("2".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("多选题");
        } else if ("3".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("填空题");
        } else if ("4".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("名词解释");
        } else if ("5".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("简答题");
        } else if (com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO.equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("论述题");
        } else if ("7".equals(this.questionInfoBean.getType())) {
            this.typeStr.setText("病例分析");
        }
        if (TextUtils.isEmpty(this.questionInfoBean.getSource())) {
            this.sourcetv.setVisibility(8);
        } else {
            this.sourcetv.setVisibility(0);
            this.sourcetv.setText("来源：" + this.questionInfoBean.getSource());
        }
        titlenum();
        String title = this.questionInfoBean.getTitle();
        try {
            title = this.questionInfoBean.getTitle().replaceAll("<p.*?>", "").replaceAll("</p>", "");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if ("3".equals(this.questionInfoBean.getType())) {
            this.web.setVisibility(0);
            this.web.getSettings().setJavaScriptEnabled(true);
            this.titletv.setVisibility(8);
            if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                title = title.replace("color:#444", "color:#64729F").replace("color:#fff", "color:#121622").replace("dashed #888", "dashed #64729F");
            }
            this.web.setBackgroundColor(0);
            this.web.loadDataWithBaseURL(null, CommonUtil.getHtmlData(getActivity(), title), "text/html; charset=utf-8", "utf-8", null);
            this.web.setWebViewClient(new MyWebViewClient());
        } else {
            this.web.setVisibility(8);
            this.titletv.setVisibility(0);
            if (this.questionInfoBean.getNumber() == null || "诊断学".equals(this.questionInfoBean.getSubject_name()) || this.type.equals("year") || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals("40")) {
                this.titletv.setText(title);
            } else {
                this.titletv.setText(this.questionInfoBean.getNumber() + " " + title);
            }
        }
        initSubViewData();
        initTitleImg();
        getStaticsData();
        getBiaoData();
        bigOrAns();
        initRestoreData();
        this.questiondetails_btn_pushAnswer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11071c.lambda$initQuestionType$2(view);
            }
        });
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11075c.lambda$initQuestionType$3(view);
            }
        });
        AnalysisAdapter analysisAdapter = new AnalysisAdapter(this.dataAnaList);
        this.analysisAdapter = analysisAdapter;
        this.recyansitem.setAdapter(analysisAdapter);
        this.analysisAdapter.setmAnaImClickIml(this);
        getAnasisData();
        initAnsData();
        this.zuoduil.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11080c.lambda$initQuestionType$4(view);
            }
        });
        this.zuocuol.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11082c.lambda$initQuestionType$5(view);
            }
        });
        this.questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws SQLException {
                this.f11002c.lambda$initQuestionType$6(view);
            }
        });
        this.questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11040c.changeNoteData(view);
            }
        });
        this.tv_correction.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11042c.lambda$initQuestionType$7(view);
            }
        });
        this.questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11045c.lambda$initQuestionType$8(view);
            }
        });
        this.questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11047c.lambda$initQuestionType$9(view);
            }
        });
        this.btn_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11049c.lambda$initQuestionType$11(view);
            }
        });
        try {
            CommonUtil.copySelect(getActivity(), this.titletv);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_content_ques1);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_content_ques);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_contents);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void initRefreshView() {
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.answer.s
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11057c.lambda$initRefreshView$1(refreshLayout);
            }
        });
    }

    public void initRestoreData() {
        try {
            if (TextUtils.isEmpty(this.questionInfoBean.getRestore())) {
                return;
            }
            String[] strArrSplit = this.questionInfoBean.getRestore().split("\\[\\$delimiter\\$]");
            String str = "九版还原";
            if (strArrSplit.length < 2) {
                if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP)) {
                    str = "考点还原";
                }
                this.nitv.setText(str);
                this.eitv.setVisibility(8);
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr(strArrSplit[0], this.questiondetails_tv_content_ques, this.questionInfoBean.getNumber(), 2, this.questionInfoBean.getSource_filter());
                return;
            }
            if (!TextUtils.isEmpty(strArrSplit[1])) {
                this.eitv.setText((SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR) || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP)) ? "八版还原" : "考点还原");
                this.eitv.setVisibility(0);
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr(strArrSplit[1], this.questiondetails_tv_content_ques, this.questionInfoBean.getNumber(), 1, this.questionInfoBean.getSource_filter());
            }
            if (TextUtils.isEmpty(strArrSplit[0])) {
                return;
            }
            if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_JOININ_GROUP)) {
                str = "考点还原";
            }
            this.nitv.setText(str);
            this.questiondetails_tv_content_ques1.setVisibility(0);
            getRestoreStr(strArrSplit[0], this.questiondetails_tv_content_ques1, this.questionInfoBean.getNumber(), 2, this.questionInfoBean.getSource_filter());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initStaticData() {
        double d3;
        try {
            if (this.mContext != null && getActivity() != null) {
                QuestionDataStaticSetListBean.DataBean dataBean = this.mDataBean;
                if (dataBean != null) {
                    String number = "0";
                    if (dataBean.getRight_count() + this.mDataBean.getWrong_count() > 0) {
                        double right_count = (this.mDataBean.getRight_count() * 100) / (this.mDataBean.getRight_count() + this.mDataBean.getWrong_count());
                        d3 = right_count;
                        number = CommonUtil.getNumber(right_count);
                    } else {
                        d3 = 0.0d;
                    }
                    this.questiondetails_btn_commentNum.setText(this.mDataBean.getComment_count() + "评论");
                    if (this.mDataBean.getAnswer().getRight_count() + this.mDataBean.getAnswer().getWrong_count() > 0) {
                        String str = "本题" + this.mDataBean.getCollection_count() + "人收藏，全部考生作答" + (this.mDataBean.getRight_count() + this.mDataBean.getWrong_count()) + "次，对" + this.mDataBean.getRight_count() + "次，正确率" + number + "%，本人作答{" + (this.mDataBean.getAnswer().getRight_count() + this.mDataBean.getAnswer().getWrong_count()) + "}次，对{" + this.mDataBean.getAnswer().getRight_count() + "}次，正确率{" + CommonUtil.getNumber((this.mDataBean.getAnswer().getRight_count() * 100) / (this.mDataBean.getAnswer().getRight_count() + this.mDataBean.getAnswer().getWrong_count())) + "%}";
                        if (d3 > (this.mDataBean.getAnswer().getRight_count() * 100) / (this.mDataBean.getAnswer().getRight_count() + this.mDataBean.getAnswer().getWrong_count())) {
                            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                        } else {
                            this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10572282).outerColor(-16777216).format() : ColorPhrase.from(str).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                        }
                    } else {
                        String str2 = "本题" + this.mDataBean.getCollection_count() + "人收藏，全部考生作答" + (this.mDataBean.getRight_count() + this.mDataBean.getWrong_count()) + "次，对" + this.mDataBean.getRight_count() + "次，正确率" + number + "%，本人作答{?}次，对{?}次，正确率{?%}";
                        this.questiondetails_tv_statistics.setText(SkinManager.getCurrentSkinType(getActivity()) == 0 ? ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-1094337).outerColor(-16777216).format() : ColorPhrase.from(str2).withSeparator(StrPool.EMPTY_JSON).innerColor(-10194273).outerColor(-10194273).format());
                    }
                    this.questiondetails_layout_diff.removeAllViews();
                    TextView textView = new TextView(getActivity());
                    textView.setTextSize(12.0f);
                    if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                        textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
                    } else {
                        textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                    }
                    textView.setText("难度：");
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.rightMargin = 10;
                    textView.setLayoutParams(layoutParams);
                    this.questiondetails_layout_diff.addView(textView);
                    int i2 = d3 > 95.0d ? 1 : d3 > 80.0d ? 2 : d3 > 60.0d ? 3 : d3 > 30.0d ? 4 : 5;
                    for (int i3 = 0; i3 < 5; i3++) {
                        ImageView imageView = new ImageView(getActivity());
                        if (i3 < i2) {
                            if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                                imageView.setBackground(MyNightUtil.getDrawable(getActivity(), R.drawable.icon_star_yellow));
                            } else {
                                imageView.setBackground(MyNightUtil.getDrawable(getActivity(), R.drawable.icon_star_yellow_night));
                            }
                        } else if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                            imageView.setBackground(MyNightUtil.getDrawable(getActivity(), R.drawable.icon_star_gary));
                        } else {
                            imageView.setBackground(MyNightUtil.getDrawable(getActivity(), R.drawable.icon_star_gary_night));
                        }
                        this.questiondetails_layout_diff.addView(imageView);
                    }
                    if (this.mDataBean.getIs_comment() == 0) {
                        noCommingimg();
                    } else {
                        havaCommingimg();
                    }
                    if (this.mDataBean.getIs_praise() == 0) {
                        noParise();
                    } else {
                        haveParise();
                    }
                } else {
                    this.questiondetails_btn_commentNum.setText("?评论");
                    this.questiondetails_tv_statistics.setText("本题?人收藏，全部考生作答?次，对?次，正确率?%，本人作答?次，对?次，正确率?%");
                    this.questiondetails_layout_diff.removeAllViews();
                    TextView textView2 = new TextView(getActivity());
                    textView2.setTextSize(12.0f);
                    if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                        textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.black));
                    } else {
                        textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
                    }
                    textView2.setText("难度：?");
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.rightMargin = 10;
                    textView2.setLayoutParams(layoutParams2);
                    this.questiondetails_layout_diff.addView(textView2);
                }
                if (getNotesBeanData() == null) {
                    noNoteimg();
                } else {
                    haveNoteimg();
                }
                if (getFavoritesBeanData() == null) {
                    noCollectimg();
                } else {
                    havaCollectimg();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initStyle() {
        String str = SkinManager.getCurrentSkinType(getActivity()) == 1 ? " spans.style.color = '#64729F';" : " spans.style.color = '#444444';";
        this.web.loadUrl("javascript:(function(){var objs = document.getElementsByTagName('span'); for(var i=0;i<objs.length;i++)  {var spans = objs[i];   " + str + "}})()");
    }

    public void initSubViewData() {
        AnalysisBean.DataBean dataBean = this.mAnalysisBean;
        if (dataBean == null || "".equals(dataBean.getId())) {
            setViewVisiable(8);
            this.questiondetails_btn_pushAnswer.setVisibility(0);
            this.mRefresh.setEnableRefresh(false);
            this.mRefresh.setEnableLoadMore(false);
            if (getmAnsweredQuestionBean() != null) {
                setViewVisiable(0);
                this.questiondetails_btn_pushAnswer.setVisibility(8);
                this.ansline.setVisibility(8);
                return;
            }
            return;
        }
        if (getmAnsweredQuestionBean() == null) {
            setViewVisiable(0);
            this.qbrel.setVisibility(8);
            this.line_viewok.setVisibility(0);
            this.schildline.setVisibility(8);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
        } else {
            setViewVisiable(0);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
        }
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setEnableLoadMore(false);
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.questionInfoBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(this.questionInfoBean.getTitle_img())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.8
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                AnswerNQuestionFragment.this.imgtitle.setImageResource(R.drawable.imgplacehodel_image);
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                AnswerNQuestionFragment.this.imgtitle.setImageDrawable(resource);
                return true;
            }
        }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.7
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (resource != null) {
                    float intrinsicWidth = resource.getIntrinsicWidth();
                    float intrinsicHeight = resource.getIntrinsicHeight();
                    float width = AnswerNQuestionFragment.this.imgtitle.getWidth();
                    if (width == 0.0f) {
                        width = AnswerNQuestionFragment.this.imgtitle.getResources().getDisplayMetrics().widthPixels;
                    }
                    int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                    ViewGroup.LayoutParams layoutParams = AnswerNQuestionFragment.this.imgtitle.getLayoutParams();
                    if (i2 >= 4096) {
                        layoutParams.height = R2.color.N_stretchTextColor;
                        AnswerNQuestionFragment.this.imgtitle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        layoutParams.height = -1;
                    }
                    AnswerNQuestionFragment.this.imgtitle.setLayoutParams(layoutParams);
                }
            }
        });
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11064c.lambda$initTitleImg$15(view);
            }
        });
    }

    public void initViewClick() {
        this.questiondetails_btn_pushAnswer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadout));
        this.questiondetails_btn_pushAnswer.setVisibility(8);
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadint));
        this.line_viewok.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.question_id = getArguments().getLong("question_id");
        this.total = getArguments().getString("total");
        this.position = getArguments().getInt("position");
        this.chapter_id = getArguments().getString("chapter_id");
        this.isXitong = getArguments().getBoolean("isXitong");
        this.iszhongyibank = getArguments().getBoolean("iszhongyibank");
        this.type = getArguments().getString("type");
        this.questionInfoBean = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(this.question_id);
        this.tv_correction = (TextView) holder.get(R.id.tv_correction);
        this.web = (WebView) holder.get(R.id.web);
        this.schildline = (LinearLayout) holder.get(R.id.schildline);
        this.ansline = (LinearLayout) holder.get(R.id.ansline);
        this.nitv = (TextView) holder.get(R.id.nitv);
        this.eitv = (TextView) holder.get(R.id.eitv);
        this.typeStr = (TextView) holder.get(R.id.typeStr);
        this.pagenumtv = (TextView) holder.get(R.id.pagenumtv);
        this.titletv = (TextView) holder.get(R.id.titletv);
        this.sourcetv = (TextView) holder.get(R.id.sourcetv);
        this.imgtitle = (ImageView) holder.get(R.id.imgtitle);
        this.line_viewok = (LinearLayout) holder.get(R.id.line_viewok);
        this.zuoduil = (TextView) holder.get(R.id.zuoduil);
        this.zuocuol = (TextView) holder.get(R.id.zuocuol);
        this.btn_comment = (Button) holder.get(R.id.btn_comment);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.mRadioGroup_content = (LinearLayout) holder.get(R.id.mRadioGroup_content);
        this.sourcetv = (TextView) holder.get(R.id.sourcetv);
        this.biaotxt = (TextView) holder.get(R.id.biaotxt);
        this.questiondetails_tv_contents = (TextView) holder.get(R.id.questiondetails_tv_contents);
        this.tv_answer_analyze = (TextView) holder.get(R.id.tv_answer_analyze);
        this.questiondetails_btn_pushAnswer = (TextView) holder.get(R.id.questiondetails_btn_pushAnswer);
        this.questiondetails_tv_outline = (TextView) holder.get(R.id.questiondetails_tv_outline);
        this.lineout = (LinearLayout) holder.get(R.id.lineout);
        this.qbrel = (RelativeLayout) holder.get(R.id.qbrel);
        this.questiondetails_tv_content_ques = (TextView) holder.get(R.id.questiondetails_tv_content_ques);
        this.questiondetails_tv_content_ques1 = (TextView) holder.get(R.id.questiondetails_tv_content_ques1);
        this.mRefresh = (RefreshLayout) holder.get(R.id.refreshLayout);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyansitem);
        this.recyansitem = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyansitem.setNestedScrollingEnabled(false);
        this.recyansitem.setHasFixedSize(true);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.back_font_gray);
        } else {
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
            this.grayColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan);
        } else {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan_night);
        }
        initQuestionType();
    }

    public void noCollectimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no);
        } else {
            this.questiondetails_btn_collect.setImageResource(R.drawable.icon_collect_no_night);
        }
    }

    public void noCommingimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg);
        } else {
            this.questiondetails_btn_centerMsg.setImageResource(R.drawable.question_msg_night);
        }
    }

    public void noNoteimg() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit);
        } else {
            this.questiondetails_btn_edit.setImageResource(R.drawable.btn_edit_night);
        }
    }

    public void noParise() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
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

    public void onEventMainThread(AnalysisBean.DataBean sDaa) {
        if ((this.question_id + "").equals(sDaa.getQuestion_id())) {
            this.mAnalysisBean = sDaa;
            initAnalyAdapterData();
            initSubViewData();
        }
    }

    public void postBiaoqianData(String question_id, String label_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.muserLabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerNQuestionFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        return;
                    }
                    NewToast.showShort(AnswerNQuestionFragment.this.getActivity(), jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void setViewVisiable(int view) {
        if (view == 0 && "3".equals(this.questionInfoBean.getType())) {
            initStyle();
        }
        this.lineout.setVisibility(view);
        this.qbrel.setVisibility(view);
        this.ansline.setVisibility(view);
        this.questiondetails_layout_diff.setVisibility(view);
        this.questiondetails_btn_commentNum.setVisibility(view);
    }

    @Override // com.psychiatrygarden.activity.answer.adapter.AnalysisAdapter.AnaImClickIml
    public void showToastView(View v2) {
        new XPopup.Builder(getActivity()).hasShadowBg(Boolean.FALSE).isCenterHorizontal(true).atView(v2).asCustom(new AnalyPopWindow(getActivity(), new String[]{"默认排序", "热度排序", "时间排序", "我赞同的"}, new int[0], new AnonymousClass10())).show();
    }

    /* renamed from: showlog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$initBiaoQianData$13(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupWindow(this.mContext, dataBiao, new BiaoqianInterface() { // from class: com.psychiatrygarden.activity.answer.t
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianInterface
            public final void mBiaoianLinster(List list, boolean z2) {
                this.f11062a.lambda$showlog$14(list, z2);
            }
        })).toggle();
    }

    public void titlenum() {
        String sortNum;
        if (this.type.equals("year")) {
            sortNum = this.questionInfoBean.getNumber_number() + "";
        } else if (this.iszhongyibank) {
            if ("".equals(CommonUtil.getSortNum(this.chapter_id, this.question_id))) {
                sortNum = (this.position + 1) + "";
            } else {
                sortNum = CommonUtil.getSortNum(this.chapter_id, this.question_id);
            }
        } else if (this.isXitong) {
            if ("".equals(CommonUtil.getSortNum(this.chapter_id, this.question_id))) {
                sortNum = (this.position + 1) + "";
            } else {
                sortNum = CommonUtil.getSortNum(this.chapter_id, this.question_id);
            }
        } else if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, getActivity(), "").equals(CommonParameter.Xueshuo)) {
            sortNum = this.questionInfoBean.getS_num_xue() + "";
        } else {
            sortNum = this.questionInfoBean.getS_num() + "";
        }
        this.pagenumtv.setText(CharacterParser.getSpannableColorSize(sortNum + " /" + this.total, 0, sortNum.length(), SkinManager.getCurrentSkinType(getActivity()) == 1 ? "#64729F" : "#000000"));
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("QuestionDetailActivity_note_add" + this.question_id)) {
            haveNoteimg();
        }
        if (str.equals("QuestionDetailActivity_note_delete" + this.question_id)) {
            noNoteimg();
        }
    }
}
