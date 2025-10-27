package com.psychiatrygarden.activity.answer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.EditNoteActivity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionOptionBean;
import com.psychiatrygarden.bean.QuestionOptionBeanDao;
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
import com.psychiatrygarden.utils.CharacterParser;
import com.psychiatrygarden.utils.ColorPhrase;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.AutoHeightViewPager;
import com.psychiatrygarden.widget.BiaoPupWindow;
import com.psychiatrygarden.widget.CirclePoint;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AnswerQuestionFragment extends BaseFragment {
    private boolean ISPractice;
    private TextView biaotxt;
    private ColorStateList blackColors;
    private Button btn_comment;
    private String chapter_id;
    private CirclePoint circlePoint;
    private Drawable drawable;
    private TextView eitv;
    private View eitvline;
    private ColorStateList grayColors;
    private AppCompatImageView imgtitle;
    private boolean isXitong;
    private boolean iszhongyibank;
    private LinearLayout lineout;
    private RelativeLayout lineviewtype;
    private LinearLayout ll_more_columns;
    private QuestionDataStaticSetListBean.DataBean mDataBean;
    private List<QuestionOptionBean> mQuestionOptionBean;
    private LinearLayout mRadioGroup_content;
    private TextView nitv;
    private TextView pagenumtv;
    private int position;
    private BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> qAdapter;
    private RelativeLayout qbrel;
    private RecyclerView qlistview;
    private QuestionInfoBean questionInfoBean;
    private long question_id = 0;
    private ImageView questiondetails_btn_centerMsg;
    private ImageView questiondetails_btn_collect;
    private Button questiondetails_btn_commentNum;
    private ImageView questiondetails_btn_edit;
    private TextView questiondetails_btn_pushAnswer;
    private ImageView questiondetails_btn_zantong;
    private LinearLayout questiondetails_layout_diff;
    private TextView questiondetails_tv_Answer;
    private TextView questiondetails_tv_content_ques;
    private TextView questiondetails_tv_content_ques1;
    private TextView questiondetails_tv_contents;
    private TextView questiondetails_tv_outline;
    private TextView questiondetails_tv_statistics;
    private ColorStateList redColors;
    private RelativeLayout rl_question_video;
    private TextView sourcetv;
    private TextView titletv;
    private String total;
    private TextView tv_correction;
    private String type;
    private TextView typeStr;
    private AutoHeightViewPager viewpager_question_video;
    private WebView webView;

    /* renamed from: com.psychiatrygarden.activity.answer.AnswerQuestionFragment$13, reason: invalid class name */
    public class AnonymousClass13 extends BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> {
        public AnonymousClass13(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(QuestionOptionBean questionOptionBean, View view) {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(((BaseFragment) AnswerQuestionFragment.this).mContext).setSingleSrcView(null, questionOptionBean.getImg()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder helper, final QuestionOptionBean item) {
            ImageView imageView = (ImageView) helper.getView(R.id.QuestionOptions_item_img_select);
            TextView textView = (TextView) helper.getView(R.id.QuestionOptions_item_tv_content);
            ImageView imageView2 = (ImageView) helper.getView(R.id.optionimg);
            textView.setText(item.getKey() + StrPool.DOT + item.getValue());
            String type = item.getType();
            if (TextUtils.isEmpty(item.getImg())) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                GlideApp.with(imageView2.getContext()).load((Object) GlideUtils.generateUrl(item.getImg())).override(200, 200).into(imageView2);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.m0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11043c.lambda$convert$0(item, view);
                    }
                });
            }
            if (type == null || type.equals("0")) {
                if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.black));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_no));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.question_color_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_no_night));
                    return;
                }
            }
            if (type.equals("1")) {
                if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.black));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_yes));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.question_color_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_yes_night));
                    return;
                }
            }
            if (type.equals("2")) {
                if ("2".equals(AnswerQuestionFragment.this.getmAnsweredQuestionBean().getIs_right())) {
                    if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                        textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.black));
                        imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.wx));
                        return;
                    } else {
                        textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.question_color_night));
                        imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.wx));
                        return;
                    }
                }
                if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.green_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_ok));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.green_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_ok_night));
                    return;
                }
            }
            if (type.equals("3")) {
                if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_error));
                    return;
                } else {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_error_night));
                    return;
                }
            }
            if (type.equals("4")) {
                if (SkinManager.getCurrentSkinType(AnswerQuestionFragment.this.getActivity()) == 0) {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.red_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_ok_lack));
                } else {
                    textView.setTextColor(MyNightUtil.getColor(AnswerQuestionFragment.this.getActivity(), R.color.red_theme_night));
                    imageView.setImageDrawable(MyNightUtil.getDrawable(AnswerQuestionFragment.this.getActivity(), R.drawable.icon_options_select_ok_lack_night));
                }
            }
        }
    }

    private boolean hasAdded(AnsweredQuestionBean info, List<AnsweredQuestionBean> listSubmitDataList) {
        for (AnsweredQuestionBean answeredQuestionBean : listSubmitDataList) {
            if (info.getQuestion_id().equals(answeredQuestionBean.getQuestion_id()) && info.getChapter_id().equals(answeredQuestionBean.getChapter_id()) && info.getChapter_parent_id().equals(answeredQuestionBean.getChapter_parent_id())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$7(ArrayList arrayList, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, i2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionList$8(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) == 0 || this.ISPractice) {
            if (getmAnsweredQuestionBean() == null || getmAnsweredQuestionBean().getAnswer() == null) {
                doSelectOption(i2);
                this.qAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (getmAnsweredQuestionBean() == null || hasAdded(getmAnsweredQuestionBean(), ProjectApp.listSubmitData)) {
            doSelectOption(i2);
            this.qAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$0(View view) {
        List<QuestionOptionBean> list = this.mQuestionOptionBean;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mQuestionOptionBean.size(); i3++) {
            if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i3).getType()) && this.mQuestionOptionBean.get(i3).getType().equals("1")) {
                i2++;
            }
        }
        if (i2 == 0) {
            AlertToast("请选择答案");
            return;
        }
        if (this.questionInfoBean.getAnswer().replaceAll(",", "").toString().length() > 1 && i2 < 2) {
            AlertToast("此题为多选题");
            return;
        }
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) == 0 || this.ISPractice) {
            doPushAnsData();
            initSubViewData();
            BaseQuickAdapter<QuestionOptionBean, BaseViewHolder> baseQuickAdapter = this.qAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < this.mQuestionOptionBean.size(); i5++) {
            if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i5).getType()) && !this.mQuestionOptionBean.get(i5).getType().equals("0")) {
                i4++;
            }
        }
        if (i4 > 0) {
            EventBus.getDefault().post("mIndex");
        } else {
            AlertToast("请选择选项！");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.question_id + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initQuestionType$3(View view) {
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
    public /* synthetic */ void lambda$initQuestionType$4(View view) {
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
    public /* synthetic */ void lambda$initQuestionType$5(View view) {
        if (isLogin()) {
            new DialogInput(getActivity(), new onDialogClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.3
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public void onclickStringBack(String str, String b_img, String s_img) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", str);
                    bundle.putInt("result", 1);
                    bundle.putString("b_img", b_img);
                    bundle.putString("s_img", s_img);
                    if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        AnswerQuestionFragment.this.pushComment(bundle);
                        return;
                    }
                    Intent intent = new Intent(AnswerQuestionFragment.this.getActivity(), (Class<?>) CorpCupActivity.class);
                    intent.putExtra("bundleIntent", bundle);
                    AnswerQuestionFragment.this.startActivityForResult(intent, 1);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTitleImg$6(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, this.questionInfoBean.getTitle_img()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushComment(Bundle b3) {
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
        YJYHttpUtils.post(getActivity().getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AnswerQuestionFragment.this.hideProgressDialog();
                AnswerQuestionFragment.this.AlertToast("请求服务器失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        AnswerQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                        AnswerQuestionFragment.this.questiondetails_btn_commentNum.setText(AnswerQuestionFragment.this.mDataBean.getComment_count() + "评论");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(AnswerQuestionFragment.this.getActivity()).setMessage(jSONObject.optString("message")).show();
                    } else {
                        AnswerQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception unused) {
                    AnswerQuestionFragment.this.AlertToast("数据异常！");
                }
                AnswerQuestionFragment.this.hideProgressDialog();
            }
        });
    }

    public void bigOrAns() {
        String str;
        String str2;
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            str = "答案：" + this.questionInfoBean.getAnswer().replaceAll(",", "");
        } else {
            str = "答案：" + this.questionInfoBean.getAnswer().replaceAll(",", "");
        }
        this.questiondetails_tv_Answer.setText(Html.fromHtml(str));
        if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, getActivity(), "").equals(CommonParameter.Xueshuo)) {
            str2 = "大纲：" + this.questionInfoBean.getXuehsuodagang();
            if (TextUtils.isEmpty(this.questionInfoBean.getXuehsuodagang())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        } else {
            str2 = "大纲：" + this.questionInfoBean.getZhuanshuodagang();
            if (TextUtils.isEmpty(this.questionInfoBean.getZhuanshuodagang())) {
                this.questiondetails_tv_outline.setVisibility(8);
            } else {
                this.questiondetails_tv_outline.setVisibility(0);
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
        Matcher matcher = Pattern.compile("考试大纲未要求").matcher(str2);
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

    /* renamed from: changeNoteData, reason: merged with bridge method [inline-methods] */
    public void lambda$initQuestionType$1(View v2) {
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
        YJYHttpUtils.post(getActivity().getApplicationContext(), NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.5
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

    public void doChangeColor() {
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mQuestionOptionBean.size()) {
                break;
            }
            if ("1".equals(this.mQuestionOptionBean.get(i2).getType())) {
                z2 = true;
                break;
            }
            i2++;
        }
        if (z2) {
            if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#64729F"));
                return;
            } else {
                this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#000000"));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#38456D"));
        } else {
            this.questiondetails_btn_pushAnswer.setTextColor(Color.parseColor("#B7B7B7"));
        }
    }

    public void doPushAnsData() {
        StringBuilder sb = new StringBuilder();
        List<QuestionOptionBean> list = this.mQuestionOptionBean;
        if (list == null || list.size() <= 0) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mQuestionOptionBean.size(); i3++) {
            if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i3).getType()) && this.mQuestionOptionBean.get(i3).getType().equals("1")) {
                sb.append(this.mQuestionOptionBean.get(i3).getKey());
                sb.append(",");
                i2++;
            }
        }
        if (i2 == 0) {
            NewToast.showShort(getActivity(), "请选择答案", 0).show();
            return;
        }
        String strTrim = this.questionInfoBean.getAnswer().replace(",", "").trim();
        String strTrim2 = sb.toString().replace(",", "").trim();
        char[] charArray = strTrim.toCharArray();
        char[] charArray2 = strTrim2.toCharArray();
        Arrays.sort(charArray);
        Arrays.sort(charArray2);
        String str = new String(charArray).equals(new String(charArray2)) ? "1" : "0";
        if (TextUtils.equals(str, "1")) {
            ProjectApp.mDaoSession.getWrongBeanDao().deleteByKey(Long.valueOf(this.question_id));
            QuestionDataStaticSetListBean.DataBean dataBean = this.mDataBean;
            if (dataBean != null && dataBean.getAnswer() != null) {
                this.mDataBean.getAnswer().setRight_count(this.mDataBean.getAnswer().getRight_count() + 1);
            }
        } else {
            ProjectApp.mDaoSession.getWrongBeanDao().insertOrReplace(new WrongBean(Long.valueOf(this.question_id), this.questionInfoBean.getChapter_parent_id(), this.questionInfoBean.getChapter_id(), this.questionInfoBean.getYear(), this.questionInfoBean.getS_num(), this.questionInfoBean.getNumber_number(), this.questionInfoBean.getMedia_img(), this.questionInfoBean.getIsxueshuo(), this.questionInfoBean.getIszhuanshuo(), this.questionInfoBean.getIsgaopinkaodian(), this.questionInfoBean.getIs_practice(), this.questionInfoBean.getUnit(), this.questionInfoBean.getPart_id(), this.questionInfoBean.getPart_parent_id(), this.questionInfoBean.getPart_num_am(), this.questionInfoBean.getPart_num_pm(), this.questionInfoBean.getType()));
            QuestionDataStaticSetListBean.DataBean dataBean2 = this.mDataBean;
            if (dataBean2 != null && dataBean2.getAnswer() != null) {
                this.mDataBean.getAnswer().setWrong_count(this.mDataBean.getAnswer().getWrong_count() + 1);
            }
        }
        Time time = new Time();
        time.setToNow();
        int i4 = time.year;
        int i5 = time.month + 1;
        int i6 = time.monthDay;
        ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().insertOrReplace(new AnsweredQuestionBean(Long.valueOf(this.question_id), sb.substring(0, sb.length() - 1), this.questionInfoBean.getChapter_parent_id(), this.questionInfoBean.getChapter_id(), str, this.questionInfoBean.getAnswer(), this.questionInfoBean.getNumber(), this.questionInfoBean.getNumber_number(), null, this.questionInfoBean.getMedia_img(), this.questionInfoBean.getYear(), this.questionInfoBean.getIsxueshuo(), this.questionInfoBean.getIszhuanshuo(), this.questionInfoBean.getIsgaopinkaodian(), i4 + "", i5 + "", i6 + "", this.questionInfoBean.getIs_practice(), this.questionInfoBean.getUnit(), this.questionInfoBean.getPart_id(), this.questionInfoBean.getPart_parent_id(), this.questionInfoBean.getPart_num_am(), this.questionInfoBean.getPart_num_pm(), this.questionInfoBean.getType()));
        ProjectApp.mDaoSession.getSubmitAnsweredQuestionBeanDao().insertOrReplace(new SubmitAnsweredQuestionBean(Long.valueOf(this.question_id), sb.substring(0, sb.length() - 1), str, SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), this.questionInfoBean.getQuestion_id() + "mengdepeng" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())));
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) == 1 && !this.ISPractice && !hasAdded(getmAnsweredQuestionBean(), ProjectApp.listSubmitData)) {
            ProjectApp.listSubmitData.add(getmAnsweredQuestionBean());
        }
        initStaticData();
    }

    public void doSelectOption(int position) {
        int i2 = 0;
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) == 0 || this.ISPractice) {
            if (this.questionInfoBean.getAnswer().replaceAll(",", "").length() > 1) {
                this.mQuestionOptionBean.get(position).setType(this.mQuestionOptionBean.get(position).getType() != "1" ? "1" : "0");
            } else {
                while (i2 < this.mQuestionOptionBean.size()) {
                    this.mQuestionOptionBean.get(i2).setType("0");
                    i2++;
                }
                this.mQuestionOptionBean.get(position).setType("1");
            }
            doChangeColor();
            return;
        }
        if (this.questionInfoBean.getAnswer().replaceAll(",", "").length() > 1) {
            this.mQuestionOptionBean.get(position).setType(this.mQuestionOptionBean.get(position).getType() != "1" ? "1" : "0");
            StringBuilder sb = new StringBuilder();
            while (i2 < this.mQuestionOptionBean.size()) {
                if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i2).getType()) && this.mQuestionOptionBean.get(i2).getType().equals("1")) {
                    sb.append(this.mQuestionOptionBean.get(i2).getKey());
                    sb.append(",");
                }
                i2++;
            }
            if (!TextUtils.isEmpty(sb.toString())) {
                doPushAnsData();
            }
            doChangeColor();
            return;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.mQuestionOptionBean.size(); i4++) {
            if (!TextUtils.isEmpty(this.mQuestionOptionBean.get(i4).getType()) && this.mQuestionOptionBean.get(i4).getType().equals("1")) {
                i3++;
            }
        }
        if (i3 <= 0) {
            this.mQuestionOptionBean.get(position).setType("1");
            doPushAnsData();
            EventBus.getDefault().post("mIndex");
        } else {
            while (i2 < this.mQuestionOptionBean.size()) {
                this.mQuestionOptionBean.get(i2).setType("0");
                i2++;
            }
            this.mQuestionOptionBean.get(position).setType("1");
            doPushAnsData();
        }
    }

    public void getBiaoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.question_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mlabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    AnswerQuestionFragment.this.biaotxt.setText("标签：？");
                    AnswerQuestionFragment.this.mRadioGroup_content.removeAllViews();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    BiaoqianBeab biaoqianBeab = (BiaoqianBeab) new Gson().fromJson(s2, BiaoqianBeab.class);
                    if (biaoqianBeab.getCode().equals("200")) {
                        final List<BiaoqianBeab.DataBean> data = biaoqianBeab.getData();
                        AnswerQuestionFragment.this.initBiaoQianData(data);
                        AnswerQuestionFragment.this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.6.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v2) {
                                AnswerQuestionFragment.this.showlog(data, v2);
                            }
                        });
                    } else {
                        AnswerQuestionFragment.this.biaotxt.setText("标签：？");
                        AnswerQuestionFragment.this.mRadioGroup_content.removeAllViews();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public FavoritesBean getFavoritesBeanData() {
        return ProjectApp.mDaoSession.getFavoritesBeanDao().loadByRowId(this.question_id);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return "40".equals(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity())) ? R.layout.fragment_answer_question_benke : R.layout.fragment_answer_question_new;
    }

    public NotesBean getNotesBeanData() {
        return ProjectApp.mDaoSession.getNotesBeanDao().loadByRowId(this.question_id);
    }

    public void getRestoreStr(String str, TextView mTextV, String number, int type, String source_filter) throws Resources.NotFoundException {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        String str2 = str;
        Matcher matcher = Pattern.compile("[\\(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[\\)）]").matcher(str2);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            colorStateList = getActivity().getResources().getColorStateList(R.color.app_theme_red);
            colorStateList2 = getActivity().getResources().getColorStateList(R.color.black);
        } else {
            colorStateList = getActivity().getResources().getColorStateList(R.color.jiucuo_night);
            colorStateList2 = getActivity().getResources().getColorStateList(R.color.question_color_night);
        }
        ColorStateList colorStateList3 = colorStateList2;
        int i2 = 34;
        int i3 = 1;
        int i4 = 0;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP) && type == 1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            while (matcher.find()) {
                int i5 = i4;
                int i6 = i2;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher.start(i5), matcher.end(i5), i6);
                i4 = i5;
                i2 = i6;
            }
            int i7 = i4;
            int i8 = i2;
            Matcher matcher2 = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?对.*?[\\)）]").matcher(str2);
            while (matcher2.find()) {
                int i9 = i8;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher2.start(i7), matcher2.end(i7), i9);
                i8 = i9;
            }
            int i10 = i8;
            Matcher matcher3 = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?错.*?[\\)）]").matcher(str2);
            while (matcher3.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher3.start(i7), matcher3.end(i7), i10);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        int i11 = 0;
        String str3 = type != 1 ? type != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i12 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String str4 = str2.substring(i11, matcher.end(i11) + i12) + "&" + str2.substring(matcher.end(i11) + i12, str2.length());
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity());
            if ("考研真题".equals(source_filter)) {
                strConfig = Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
            } else if ("执医真题".equals(source_filter)) {
                strConfig = Constants.VIA_REPORT_TYPE_SET_AVATAR;
            }
            if (type == i3) {
                arrayList.add(str3 + strConfig + "/" + number + strGroup.substring(1, strGroup.length() - 1) + "-" + (i12 + 1) + ".jpg?x-oss-process=style/water_mark");
            } else if (type == 2) {
                arrayList.add(str3 + strConfig + "/" + number + strGroup.substring(1, strGroup.length() - 1) + "-" + (i12 + 1) + ".jpg?x-oss-process=style/water_mark");
            }
            i12++;
            str2 = str4;
            i3 = 1;
            i11 = 0;
        }
        Matcher matcher4 = Pattern.compile("[\\(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[\\)）]").matcher(str2);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        int i13 = 0;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i14 = 0;
        while (matcher4.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, 1), matcher4.end(i13), matcher4.end(i13) + 1, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.activity.answer.d0
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f11020a.lambda$getRestoreStr$7(arrayList, i14);
                }
            }), matcher4.start(i13), matcher4.end(i13), 33);
            mTextV.setHighlightColor(Color.parseColor("#00ffffff"));
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i14++;
            i13 = 0;
        }
        Matcher matcher5 = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?对.*?[\\)）]").matcher(str2);
        while (matcher5.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher5.start(0), matcher5.end(0), 34);
        }
        Matcher matcher6 = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?错.*?[\\)）]").matcher(str2);
        while (matcher6.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList3, null), matcher6.start(0), matcher6.end(0), 34);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        ajaxParams.put("module_type", "1");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.11
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
                        if (questionDataStaticSetListBean.getData().get(i2).getQuestion_id().equals(AnswerQuestionFragment.this.question_id + "")) {
                            AnswerQuestionFragment.this.mDataBean = questionDataStaticSetListBean.getData().get(i2);
                            AnswerQuestionFragment.this.initStaticData();
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
        AnsweredQuestionBean answeredQuestionBeanLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().loadByRowId(this.question_id);
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) != 2 || this.ISPractice || answeredQuestionBeanLoadByRowId != null) {
            return answeredQuestionBeanLoadByRowId;
        }
        AnsweredQuestionBean answeredQuestionBean = new AnsweredQuestionBean();
        answeredQuestionBean.setQuestion_id(Long.valueOf(this.question_id));
        answeredQuestionBean.setAnswer(this.questionInfoBean.getAnswer());
        answeredQuestionBean.setIs_right("2");
        return answeredQuestionBean;
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

    public void initAnsData() {
        String explain = this.questionInfoBean.getExplain();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(explain);
        Matcher matcher = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?对.*?[\\)）]").matcher(explain);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, this.blackColors, null), matcher.start(0), matcher.end(0), 34);
        }
        Matcher matcher2 = Pattern.compile("[\\(（]*.[A-E]{1,}(\\s+)?错.*?[\\)）]").matcher(explain);
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
                textView.setTextColor(getActivity().getResources().getColor(R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(getActivity().getResources().getColor(R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.9
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    AnswerQuestionFragment.this.showlog(dataBiao, v2);
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
                textView2.setText(dataBiao.get(i2).getLabel() + " " + dataBiao.get(i2).getCount());
                try {
                    if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
                        textView2.setTextColor(Color.parseColor(dataBiao.get(i2).getColor()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                textView2.setLayoutParams(layoutParams);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.10
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        AnswerQuestionFragment.this.showlog(dataBiao, v2);
                    }
                });
                this.mRadioGroup_content.addView(textView2);
            }
        }
    }

    public void initQuestionList() {
        AnonymousClass13 anonymousClass13 = new AnonymousClass13(R.layout.item_questionoptions_listview, this.mQuestionOptionBean);
        this.qAdapter = anonymousClass13;
        this.qlistview.setAdapter(anonymousClass13);
        this.qAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.answer.l0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11041c.lambda$initQuestionList$8(baseQuickAdapter, view, i2);
            }
        });
    }

    public void initQuestionType() {
        initSubViewData();
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
        } else if (Constants.VIA_SHARE_TYPE_INFO.equals(this.questionInfoBean.getType())) {
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
        initQuestionList();
        initAnsData();
        bigOrAns();
        initRestoreData();
        initVideoData();
        initTitleImg();
        getStaticsData();
        getBiaoData();
        this.questiondetails_btn_pushAnswer.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11024c.lambda$initQuestionType$0(view);
            }
        });
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                boolean z2;
                if (CommonUtil.isFastClick()) {
                    return;
                }
                if (AnswerQuestionFragment.this.mDataBean != null) {
                    z = 1 == AnswerQuestionFragment.this.mDataBean.getIs_comment();
                    z2 = 1 == AnswerQuestionFragment.this.mDataBean.getIs_praise();
                } else {
                    z2 = false;
                }
                Intent intent = new Intent(AnswerQuestionFragment.this.getActivity(), (Class<?>) CommMentList2Activity.class);
                intent.putExtra("question_id", AnswerQuestionFragment.this.question_id);
                intent.putExtra("module_type", 1);
                intent.putExtra("comment_type", "2");
                intent.putExtra("isCommentTrue", z);
                intent.putExtra("isZantongTrue", z2);
                AnswerQuestionFragment.this.startActivity(intent);
            }
        });
        this.questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) throws SQLException {
                AnswerQuestionFragment.this.changeCollectData();
            }
        });
        this.questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11026c.lambda$initQuestionType$1(view);
            }
        });
        this.tv_correction.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11028c.lambda$initQuestionType$2(view);
            }
        });
        this.questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11031c.lambda$initQuestionType$3(view);
            }
        });
        this.questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11033c.lambda$initQuestionType$4(view);
            }
        });
        this.btn_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11035c.lambda$initQuestionType$5(view);
            }
        });
        try {
            CommonUtil.copySelect(getActivity(), this.titletv);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_content_ques1);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_content_ques);
            CommonUtil.copySelect(getActivity(), this.questiondetails_tv_contents);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initRestoreData() {
        try {
            if (TextUtils.isEmpty(this.questionInfoBean.getRestore())) {
                return;
            }
            String[] strArrSplit = this.questionInfoBean.getRestore().split("\\[\\$delimiter\\$\\]");
            String str = "九版还原";
            if (strArrSplit.length < 2) {
                if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP)) {
                    str = "考点还原";
                }
                this.nitv.setText(str);
                this.eitv.setVisibility(8);
                this.eitvline.setVisibility(8);
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.questiondetails_tv_content_ques.setVisibility(0);
                getRestoreStr(strArrSplit[0], this.questiondetails_tv_content_ques, this.questionInfoBean.getNumber(), 2, this.questionInfoBean.getSource_filter());
                return;
            }
            if (TextUtils.isEmpty(strArrSplit[0])) {
                this.questiondetails_tv_content_ques1.setVisibility(8);
                this.eitv.setVisibility(8);
                this.eitvline.setVisibility(8);
            } else {
                if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR) && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP)) {
                    str = "考点还原";
                }
                this.nitv.setText(str);
                this.questiondetails_tv_content_ques1.setVisibility(0);
                getRestoreStr(strArrSplit[0], this.questiondetails_tv_content_ques1, this.questionInfoBean.getNumber(), 2, this.questionInfoBean.getSource_filter());
            }
            if (TextUtils.isEmpty(strArrSplit[1])) {
                return;
            }
            this.eitv.setText((SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ) || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR) || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP)) ? "八版还原" : "考点还原");
            if (!TextUtils.isEmpty(strArrSplit[0])) {
                this.eitv.setVisibility(0);
                this.eitvline.setVisibility(0);
            }
            this.questiondetails_tv_content_ques.setVisibility(0);
            getRestoreStr(strArrSplit[1], this.questiondetails_tv_content_ques, this.questionInfoBean.getNumber(), 1, this.questionInfoBean.getSource_filter());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initSelectedAnsed() {
        String strTrim = this.questionInfoBean.getAnswer().replace(",", "").trim();
        String strTrim2 = getmAnsweredQuestionBean().getAnswer().replace(",", "").trim();
        if (TextUtils.equals(strTrim2, "0")) {
            return;
        }
        if (strTrim.length() <= 1) {
            char[] charArray = strTrim.toCharArray();
            if (charArray[0] - 'A' >= this.mQuestionOptionBean.size() || charArray[0] - 'A' < 0) {
                return;
            }
            if (strTrim2 == null || strTrim2.equals("")) {
                this.mQuestionOptionBean.get(charArray[0] - 'A').setType("4");
                return;
            }
            this.mQuestionOptionBean.get(charArray[0] - 'A').setType("2");
            if (TextUtils.equals(strTrim, strTrim2)) {
                return;
            }
            char[] charArray2 = strTrim2.toCharArray();
            if (charArray2[0] - 'A' < this.mQuestionOptionBean.size()) {
                char c3 = charArray2[0];
                if (c3 - 'A' < 0) {
                    return;
                }
                this.mQuestionOptionBean.get(c3 - 'A').setType("3");
                return;
            }
            return;
        }
        if (strTrim2 == null || strTrim2.equals("")) {
            for (char c4 : strTrim.toCharArray()) {
                int i2 = c4 - 'A';
                if (i2 >= this.mQuestionOptionBean.size() || i2 < 0) {
                    return;
                }
                this.mQuestionOptionBean.get(i2).setType("4");
            }
            return;
        }
        char[] charArray3 = strTrim.toCharArray();
        for (char c5 : strTrim2.toCharArray()) {
            int i3 = c5 - 'A';
            if (i3 >= this.mQuestionOptionBean.size() || i3 < 0) {
                return;
            }
            this.mQuestionOptionBean.get(i3).setType("3");
            for (char c6 : charArray3) {
                int i4 = c6 - 'A';
                if (i4 >= this.mQuestionOptionBean.size() || i4 < 0) {
                    return;
                }
                if (this.mQuestionOptionBean.get(i4).getKey().trim().equals(this.mQuestionOptionBean.get(i3).getKey().trim())) {
                    this.mQuestionOptionBean.get(i4).setType("2");
                } else if (!this.mQuestionOptionBean.get(i4).getType().equals("2")) {
                    this.mQuestionOptionBean.get(i4).setType("4");
                }
            }
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
                    this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(this.mDataBean.getComment_count())));
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
                        textView.setTextColor(getActivity().getResources().getColor(R.color.black));
                    } else {
                        textView.setTextColor(getActivity().getResources().getColor(R.color.question_color_night));
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
                        textView2.setTextColor(getActivity().getResources().getColor(R.color.black));
                    } else {
                        textView2.setTextColor(getActivity().getResources().getColor(R.color.question_color_night));
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

    public void initSubViewData() {
        if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, getActivity(), 0) == 0 || this.ISPractice) {
            if (getmAnsweredQuestionBean() == null) {
                setViewVisiable(8);
                this.questiondetails_btn_pushAnswer.setVisibility(0);
                return;
            } else {
                setViewVisiable(0);
                this.questiondetails_btn_pushAnswer.setVisibility(8);
                initSelectedAnsed();
                return;
            }
        }
        if (getmAnsweredQuestionBean() == null) {
            setViewVisiable(8);
            if (this.questionInfoBean.getAnswer().replace(",", "").length() > 1) {
                this.questiondetails_btn_pushAnswer.setVisibility(0);
                return;
            } else {
                this.questiondetails_btn_pushAnswer.setVisibility(8);
                return;
            }
        }
        if (!hasAdded(getmAnsweredQuestionBean(), ProjectApp.listSubmitData)) {
            setViewVisiable(0);
            this.questiondetails_btn_pushAnswer.setVisibility(8);
            initSelectedAnsed();
        } else {
            initTestSelect();
            setViewVisiable(8);
            if (this.questionInfoBean.getAnswer().replace(",", "").length() > 1) {
                this.questiondetails_btn_pushAnswer.setVisibility(0);
            } else {
                this.questiondetails_btn_pushAnswer.setVisibility(8);
            }
        }
    }

    public void initTestSelect() {
        if (getmAnsweredQuestionBean().getAnswer().replace(",", "").trim() != null) {
            char[] charArray = getmAnsweredQuestionBean().getAnswer().replace(",", "").trim().toCharArray();
            int length = charArray.length;
            for (char c3 : charArray) {
                if (c3 < 'a') {
                    this.mQuestionOptionBean.get(c3 - 'A').setType("1");
                } else {
                    this.mQuestionOptionBean.get(c3 - 'a').setType("1");
                }
            }
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.questionInfoBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            this.webView.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        this.imgtitle.setBackground(new ColorDrawable(0));
        this.webView.setVisibility(0);
        this.webView.loadDataWithBaseURL(null, "<html><head><title></title></head><body><img style='height:auto;max-width:100%' src=" + this.questionInfoBean.getTitle_img() + " /></body></html>", "text/html", "utf-8", null);
        this.imgtitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11039c.lambda$initTitleImg$6(view);
            }
        });
    }

    public void initVideoData() {
        String[] strArrSplit;
        if (this.questionInfoBean.getMedia_img().equals("")) {
            this.rl_question_video.setVisibility(8);
            return;
        }
        this.rl_question_video.setVisibility(0);
        try {
            this.circlePoint.removeAllViews();
            String media_img = this.questionInfoBean.getMedia_img();
            if (TextUtils.equals(media_img, "") || (strArrSplit = media_img.split(",")) == null || strArrSplit.length <= 0) {
                return;
            }
            if (strArrSplit.length > 1) {
                this.circlePoint.setCount(strArrSplit.length);
                this.circlePoint.initViewData();
                this.circlePoint.invalidate();
            }
            this.viewpager_question_video.setAdapter(new QuestionVideoViewpagerAdapter(getActivity(), strArrSplit, this.question_id, 10));
            this.viewpager_question_video.setCurrentItem(0);
            final int length = strArrSplit.length;
            this.viewpager_question_video.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.12
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (length > 1) {
                        AnswerQuestionFragment.this.circlePoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.total = getArguments().getString("total");
        this.question_id = getArguments().getLong("question_id");
        this.position = getArguments().getInt("position");
        this.chapter_id = getArguments().getString("chapter_id");
        this.ISPractice = getArguments().getBoolean("ISPractice");
        this.isXitong = getArguments().getBoolean("isXitong");
        this.iszhongyibank = getArguments().getBoolean("iszhongyibank");
        this.type = getArguments().getString("type");
        this.questionInfoBean = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(this.question_id);
        this.mQuestionOptionBean = ProjectApp.mTiKuDaoSession.getQuestionOptionBeanDao().queryBuilder().where(QuestionOptionBeanDao.Properties.Question_id.eq(Long.valueOf(this.question_id)), new WhereCondition[0]).list();
        for (int i2 = 0; i2 < this.mQuestionOptionBean.size(); i2++) {
            this.mQuestionOptionBean.get(i2).setType("0");
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.drawable = getActivity().getResources().getDrawable(R.drawable.huanyuan);
        } else {
            this.drawable = getActivity().getResources().getDrawable(R.drawable.huanyuan_night);
        }
        this.lineviewtype = (RelativeLayout) holder.get(R.id.lineviewtype);
        this.tv_correction = (TextView) holder.get(R.id.tv_correction);
        this.nitv = (TextView) holder.get(R.id.nitv);
        this.eitv = (TextView) holder.get(R.id.eitv);
        this.eitvline = holder.get(R.id.eitvline);
        this.btn_comment = (Button) holder.get(R.id.btn_comment);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.imgtitle = (AppCompatImageView) holder.get(R.id.imgtitle);
        this.rl_question_video = (RelativeLayout) holder.get(R.id.rl_question_video);
        this.viewpager_question_video = (AutoHeightViewPager) holder.get(R.id.viewpager_question_video);
        this.circlePoint = (CirclePoint) holder.get(R.id.circlepoint);
        this.qbrel = (RelativeLayout) holder.get(R.id.qbrel);
        this.typeStr = (TextView) holder.get(R.id.typeStr);
        this.pagenumtv = (TextView) holder.get(R.id.pagenumtv);
        this.titletv = (TextView) holder.get(R.id.titletv);
        this.qlistview = (RecyclerView) holder.get(R.id.qlistview);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_tv_contents = (TextView) holder.get(R.id.questiondetails_tv_contents);
        this.questiondetails_tv_outline = (TextView) holder.get(R.id.questiondetails_tv_outline);
        this.questiondetails_tv_Answer = (TextView) holder.get(R.id.questiondetails_tv_Answer);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_btn_pushAnswer = (TextView) holder.get(R.id.questiondetails_btn_pushAnswer);
        this.questiondetails_tv_content_ques = (TextView) holder.get(R.id.questiondetails_tv_content_ques);
        this.questiondetails_tv_content_ques1 = (TextView) holder.get(R.id.questiondetails_tv_content_ques1);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.mRadioGroup_content = (LinearLayout) holder.get(R.id.mRadioGroup_content);
        this.sourcetv = (TextView) holder.get(R.id.sourcetv);
        this.biaotxt = (TextView) holder.get(R.id.biaotxt);
        this.lineout = (LinearLayout) holder.get(R.id.lineout);
        this.webView = (WebView) holder.get(R.id.webView);
        this.qlistview.setHasFixedSize(true);
        this.qlistview.setNestedScrollingEnabled(false);
        this.qlistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.redColors = getActivity().getResources().getColorStateList(R.color.app_theme_red);
            this.blackColors = getActivity().getResources().getColorStateList(R.color.black);
            this.grayColors = getActivity().getResources().getColorStateList(R.color.back_font_gray);
        } else {
            this.redColors = getActivity().getResources().getColorStateList(R.color.jiucuo_night);
            this.blackColors = getActivity().getResources().getColorStateList(R.color.question_color_night);
            this.grayColors = getActivity().getResources().getColorStateList(R.color.question_color_night);
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("QuestionDetailActivity_note_add" + this.question_id)) {
            haveNoteimg();
        }
        if (str.equals("QuestionDetailActivity_note_delete" + this.question_id)) {
            noNoteimg();
        }
    }

    public void postBiaoqianData(String question_id, String label_id, final List<BiaoqianBeab.DataBean> dataBiao) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        YJYHttpUtils.post(getActivity().getApplicationContext(), NetworkRequestsURL.muserLabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        return;
                    }
                    NewToast.showShort(AnswerQuestionFragment.this.getActivity(), jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void setViewVisiable(int view) {
        this.lineout.setVisibility(view);
        this.qbrel.setVisibility(view);
        this.questiondetails_layout_diff.setVisibility(view);
        this.questiondetails_btn_commentNum.setVisibility(view);
    }

    public void showlog(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupWindow(getActivity(), dataBiao, new BiaoqianInterface() { // from class: com.psychiatrygarden.activity.answer.AnswerQuestionFragment.7
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianInterface
            public void mBiaoianLinster(List<BiaoqianBeab.DataBean> dataBiao2, boolean isTrue) {
                AnswerQuestionFragment.this.initBiaoQianData(dataBiao2);
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList = new ArrayList();
                if (dataBiao2 == null || dataBiao2.size() <= 0) {
                    return;
                }
                for (int i2 = 0; i2 < dataBiao2.size(); i2++) {
                    if (dataBiao2.get(i2).getUser_label().equals("1")) {
                        arrayList.add(dataBiao2.get(i2).getId());
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
                AnswerQuestionFragment.this.postBiaoqianData(AnswerQuestionFragment.this.question_id + "", sb.toString(), dataBiao2);
            }
        })).toggle();
    }

    public void titlenum() {
        String sortNum;
        String title;
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
        if (this.questionInfoBean.getNumber() == null || "诊断学".equals(this.questionInfoBean.getSubject_name()) || this.type.equals("year") || SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals("40")) {
            title = this.questionInfoBean.getTitle();
        } else {
            title = this.questionInfoBean.getNumber() + " " + this.questionInfoBean.getTitle();
        }
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()).equals("40")) {
            this.lineviewtype.setVisibility(0);
            this.pagenumtv.setText(CharacterParser.getSpannableColorSize(sortNum + " /" + this.total, 0, sortNum.length(), SkinManager.getCurrentSkinType(getActivity()) == 1 ? "#64729F" : "#000000"));
        } else {
            title = sortNum + ". " + title;
            this.lineviewtype.setVisibility(8);
        }
        this.titletv.setText(title + "");
    }
}
