package com.psychiatrygarden.fragmenthome;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
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
import com.psychiatrygarden.activity.answer.AnswerDetailActivity;
import com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionInfoBeanDao;
import com.psychiatrygarden.bean.RecdQuestionBean;
import com.psychiatrygarden.bean.SectionBean;
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
import com.psychiatrygarden.widget.tag.FlowLayout;
import com.psychiatrygarden.widget.tag.TagAdapter;
import com.psychiatrygarden.widget.tag.TagFlowLayout;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubCrazilyQuestionFragment extends BaseFragment {
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
    private LinearLayout llay_kuangbei_zhenti;
    private TagFlowLayout mFlowLayout;
    private LinearLayout mRadioGroup_content;
    private SelectableTextHelper mSelectableTextHelper;
    private RecdQuestionBean.DataBean mUpdateBean;
    private String module_type = "1";
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
    private WebView webview;
    private WebView webviewhint;

    /* renamed from: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment$6, reason: invalid class name */
    public class AnonymousClass6 extends AjaxCallBack<String> {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list, View view) {
            SubCrazilyQuestionFragment.this.lambda$initBiaoQianData$22(list, view);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            try {
                SubCrazilyQuestionFragment.this.biaotxt.setText("标签：？");
                SubCrazilyQuestionFragment.this.mRadioGroup_content.removeAllViews();
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
                    SubCrazilyQuestionFragment.this.initBiaoQianData(data);
                    SubCrazilyQuestionFragment.this.ll_more_columns.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.nf
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f15877c.lambda$onSuccess$0(data, view);
                        }
                    });
                } else {
                    SubCrazilyQuestionFragment.this.biaotxt.setText("标签：？");
                    SubCrazilyQuestionFragment.this.mRadioGroup_content.removeAllViews();
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
    public /* synthetic */ void lambda$ObServerData$24(ObservableEmitter observableEmitter) throws Exception {
        submitAnswered();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerData$25(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$ObServerDataCollect$19(int i2, ObservableEmitter observableEmitter) throws Exception {
        if (i2 == 1) {
            submitFavorites();
        } else {
            clearCollection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ObServerDataCollect$20(Object obj) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$15(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$16(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogNote$17(String str, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) NoteNewActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        intent.putExtra("notestr", str);
        intent.putExtra("noteStatus", this.mUpdateBean.getmStaticsData().getIs_note() + "");
        intent.putExtra("module_type", "" + this.module_type);
        startActivity(intent);
        this.popupWindow_note.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialogNote$18() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreStr$13(ArrayList arrayList, int i2) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, i2).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getTagData$26(String[] strArr, View view, int i2, FlowLayout flowLayout) {
        List<QuestionInfoBean> list;
        if (CommonUtil.isFastClick()) {
            return true;
        }
        QueryBuilder<QuestionInfoBean> queryBuilder = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder();
        try {
            String str = strArr[i2];
            String[] strArrSplit = str.split("（");
            if (strArrSplit.length > 1) {
                list = queryBuilder.where(QuestionInfoBeanDao.Properties.Number.eq(strArrSplit[0].trim()), QuestionInfoBeanDao.Properties.Title.like("%" + strArrSplit[1].replace("）", "").trim() + "%")).list();
            } else {
                list = queryBuilder.where(QuestionInfoBeanDao.Properties.Number.eq(str), new WhereCondition[0]).list();
            }
            long[] jArr = {list.get(0).getQuestion_id().longValue()};
            Intent intent = new Intent(this.mContext, (Class<?>) AnswerDetailActivity.class);
            intent.putExtra("modletype", "subject");
            intent.putExtra("position", 0);
            intent.putExtra("totalCount", "1");
            intent.putExtra("list", jArr);
            intent.putExtra("json_question_data", "");
            intent.putExtra("chapter_id", list.get(0).getChapter_id());
            intent.putExtra("chapter_p_id", list.get(0).getChapter_parent_id());
            intent.putExtra("ISPractice", true);
            intent.putExtra("title", "error");
            intent.putExtra("subject_name", list.get(0).getSubject_name());
            SectionBean sectionBeanLoadByRowId = ProjectApp.mTiKuDaoSession.getSectionBeanDao().loadByRowId(Long.parseLong(list.get(0).getChapter_id()));
            if (sectionBeanLoadByRowId != null) {
                intent.putExtra("chapter_name", sectionBeanLoadByRowId.getTitle());
            } else {
                intent.putExtra("chapter_name", "");
            }
            this.mContext.startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getTagData$27(Set set) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAnsData$14(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$11(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRestoreData$12(CharSequence charSequence) {
        ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionCorrectionActivity.class);
        intent.putExtra("question_id", this.mUpdateBean.getQuestion_id());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", Integer.parseInt(this.module_type));
        intent.putExtra("comment_type", "2");
        intent.putExtra("isNewCom", true);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mycomment");
        intent.putExtra("iscomValu", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$10(View view) {
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
        intent.putExtra("module_type", "" + this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CommMentList2Activity.class);
        intent.putExtra("question_id", Long.parseLong(this.mUpdateBean.getQuestion_id()));
        intent.putExtra("module_type", Integer.parseInt(this.module_type));
        intent.putExtra("comment_type", "2");
        intent.putExtra("isCommentTrue", this.mUpdateBean.getmStaticsData().getIs_comment() != 0);
        intent.putExtra("isZantongTrue", this.mUpdateBean.getmStaticsData().getIs_praise() != 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(CharSequence charSequence) {
        try {
            ((ClipboardManager) this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Simple text", charSequence));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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
            new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.fragmenthome.ef
                @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                public final void onclickStringBack(String str, String str2, String str3) {
                    this.f15575a.lambda$initViews$4(str, str2, str3);
                }
            }, true).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        initViewClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) {
        doClickSimeThings("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) {
        doClickSimeThings("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) {
        if (this.mUpdateBean.getmStaticsData() == null) {
            AlertToast("原题加载中,请稍后再试！");
            return;
        }
        if (this.mUpdateBean.getmStaticsData().getIs_collection() == 0) {
            ObServerDataCollect(1);
            havaCollectimg();
            AlertToast("收藏成功！");
            this.mUpdateBean.getmStaticsData().setIs_collection(1);
            this.mUpdateBean.getmStaticsData().setCollection_count(this.mUpdateBean.getmStaticsData().getCollection_count() + 1);
        } else {
            ObServerDataCollect(2);
            noCollectimg();
            AlertToast("取消收藏成功！");
            this.mUpdateBean.getmStaticsData().setIs_collection(0);
            if (this.mUpdateBean.getmStaticsData().getCollection_count() > 0) {
                this.mUpdateBean.getmStaticsData().setCollection_count(this.mUpdateBean.getmStaticsData().getCollection_count() - 1);
            }
        }
        initStaticData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showlog$23(List list, boolean z2) {
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
        postBiaoqianData(this.mUpdateBean.getQuestion_id() + "", sb.toString(), list);
    }

    private void pushComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("obj_id", this.mUpdateBean.getQuestion_id() + "");
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
        showProgressDialog("发布中");
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SubCrazilyQuestionFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SubCrazilyQuestionFragment.this.questiondetails_btn_commentNum.setText(String.format(Locale.CHINA, "%d评论", Integer.valueOf(SubCrazilyQuestionFragment.this.mUpdateBean.getmStaticsData().getComment_count() + 1)));
                        SubCrazilyQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(((BaseFragment) SubCrazilyQuestionFragment.this).mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        SubCrazilyQuestionFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SubCrazilyQuestionFragment.this.hideProgressDialog();
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
        ajaxParams.put("module_type", "" + this.module_type);
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.mPostAnsweredURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.10
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
        ajaxParams.put("module_type", "" + this.module_type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mPostCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
            }
        });
    }

    public void ObServerData() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.le
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f15815a.lambda$ObServerData$24(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.we
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubCrazilyQuestionFragment.lambda$ObServerData$25(obj);
            }
        });
    }

    public void ObServerDataCollect(final int type) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.fragmenthome.hf
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f15649a.lambda$ObServerDataCollect$19(type, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.if
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SubCrazilyQuestionFragment.lambda$ObServerDataCollect$20(obj);
            }
        });
    }

    public void clearCollection() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id() + "");
        ajaxParams.put("module_type", "" + this.module_type);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mClearCollectionURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.3
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
        viewInflate.findViewById(R.id.llay_null).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.mf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15852c.lambda$dialogNote$15(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.me
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15851c.lambda$dialogNote$16(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ne
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15875c.lambda$dialogNote$17(content, view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_note = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_note.setOutsideTouchable(true);
        this.popupWindow_note.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.fragmenthome.oe
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubCrazilyQuestionFragment.lambda$dialogNote$18();
            }
        });
        this.popupWindow_note.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources()));
        this.popupWindow_note.showAtLocation(v2, 80, 0, 0);
    }

    public void doClickSimeThings(String isRight) {
        this.line_viewok.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadout));
        this.line_viewok.setVisibility(8);
        this.questiondetails_bottom_layout.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.ver_fadint));
        this.questiondetails_bottom_layout.setVisibility(0);
        if (this.module_type.equals("1")) {
            this.questiondetails_layout_answer.setVisibility(0);
        } else {
            this.questiondetails_layout_answer.setVisibility(8);
        }
        this.mUpdateBean.setOwnerAns(isRight);
        this.mUpdateBean.setIsRight(isRight);
        initSubViewData();
        ObServerData();
        if (isRight.equals("1")) {
            this.mUpdateBean.getmStaticsData().getAnswer().setRight_count(this.mUpdateBean.getmStaticsData().getAnswer().getRight_count() + 1);
        } else {
            this.mUpdateBean.getmStaticsData().getAnswer().setWrong_count(this.mUpdateBean.getmStaticsData().getAnswer().getWrong_count() + 1);
        }
        initStaticData();
    }

    public void getBiaoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", "" + this.module_type);
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.mlabelUrl, ajaxParams, new AnonymousClass6());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_subcrazily;
    }

    public void getNoteData(final View v2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", this.module_type);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mGetNoteUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.2
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
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        SubCrazilyQuestionFragment.this.dialogNote(v2, new JSONObject(s2).optJSONObject("data").optString("content"));
                    } else {
                        SubCrazilyQuestionFragment.this.AlertToast("获取笔记失败！");
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
        ColorStateList colorStateList3;
        int i2;
        String str2 = str;
        int i3 = type;
        Matcher matcher = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str2);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            colorStateList = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            colorStateList2 = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        ColorStateList colorStateList4 = colorStateList2;
        int i4 = 1;
        if ((this.collection_id.equals(Constants.VIA_REPORT_TYPE_CHAT_AUDIO) || this.collection_id.equals(Constants.VIA_REPORT_TYPE_CHAT_VIDEO)) && i3 == 1) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            int i5 = 0;
            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
            while (matcher.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher.start(0), matcher.end(0), 34);
            }
            Matcher matcher2 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?对.*?[)）]").matcher(str2);
            while (matcher2.find()) {
                int i6 = i5;
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList4, null), matcher2.start(i6), matcher2.end(i6), 34);
                i5 = i6;
            }
            int i7 = i5;
            Matcher matcher3 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?错.*?[)）]").matcher(str2);
            while (matcher3.find()) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList4, null), matcher3.start(i7), matcher3.end(i7), 34);
            }
            mTextV.setText(spannableStringBuilder);
            return;
        }
        int i8 = 0;
        String str3 = i3 != 1 ? i3 != 2 ? "" : NetworkRequestsURL.CommentIameUrl2 : NetworkRequestsURL.CommentIameUrl;
        final ArrayList arrayList = new ArrayList();
        int i9 = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            String str4 = str2.substring(i8, matcher.end(i8) + i9) + "&" + str2.substring(matcher.end(i8) + i9, str2.length());
            Matcher matcher4 = matcher;
            if (i3 != i4) {
                if (i3 == 2) {
                    if (this.mUpdateBean.getIs_real_question().equals("1")) {
                        arrayList.add(str3 + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/" + number + strGroup.substring(1, strGroup.length() - 1) + "-" + (i9 + 1) + ".jpg?x-oss-process=style/water_mark");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(NetworkRequestsURL.mCommentIameUrl3);
                        colorStateList3 = colorStateList4;
                        sb.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
                        sb.append("/");
                        sb.append(this.mUpdateBean.getQuestion_id());
                        sb.append(StrPool.UNDERLINE);
                        sb.append(strGroup.substring(1, strGroup.length() - 1));
                        sb.append("-");
                        sb.append(i9 + 1);
                        sb.append(".jpg?x-oss-process=style/water_mark");
                        arrayList.add(sb.toString());
                    }
                }
                colorStateList3 = colorStateList4;
            } else {
                colorStateList3 = colorStateList4;
                if (this.mUpdateBean.getIs_real_question().equals("1")) {
                    arrayList.add(str3 + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/" + number + strGroup.substring(1, strGroup.length() - 1) + "-" + (i9 + 1) + ".jpg?x-oss-process=style/water_mark");
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(NetworkRequestsURL.mCommentIameUrl3);
                    sb2.append(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
                    sb2.append("/");
                    sb2.append(this.mUpdateBean.getQuestion_id());
                    sb2.append(StrPool.UNDERLINE);
                    i2 = 1;
                    sb2.append(strGroup.substring(1, strGroup.length() - 1));
                    sb2.append("-");
                    sb2.append(i9 + 1);
                    sb2.append(".jpg?x-oss-process=style/water_mark");
                    arrayList.add(sb2.toString());
                    i9++;
                    str2 = str4;
                    i3 = type;
                    i4 = i2;
                    matcher = matcher4;
                    colorStateList4 = colorStateList3;
                    i8 = 0;
                }
            }
            i2 = 1;
            i9++;
            str2 = str4;
            i3 = type;
            i4 = i2;
            matcher = matcher4;
            colorStateList4 = colorStateList3;
            i8 = 0;
        }
        int i10 = i4;
        ColorStateList colorStateList5 = colorStateList4;
        Matcher matcher5 = Pattern.compile("[(（]([\\u4E00-\\u9FA5]+)?P[0-9]+(-P?([0-9]+)?)?+[)）]").matcher(str2);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
        int i11 = 34;
        spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), 0, 6, 34);
        Paint.FontMetrics fontMetrics = mTextV.getPaint().getFontMetrics();
        int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
        this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 4, (iCeil / 5) * 4);
        final int i12 = 0;
        while (matcher5.find()) {
            spannableStringBuilder2.setSpan(new StickerSpan(this.drawable, i10), matcher5.end(0), matcher5.end(0) + i10, 33);
            spannableStringBuilder2.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.fragmenthome.se
                @Override // com.psychiatrygarden.interfaceclass.DomoIml
                public final void clickToast() {
                    this.f15991a.lambda$getRestoreStr$13(arrayList, i12);
                }
            }), matcher5.start(0), matcher5.end(0), 33);
            mTextV.setHighlightColor(Color.parseColor("#00ffffff"));
            mTextV.setMovementMethod(LinkMovementMethod.getInstance());
            i12 += i10;
        }
        Matcher matcher6 = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?对.*?[)）]").matcher(str2);
        while (matcher6.find()) {
            int i13 = i11;
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList5, null), matcher6.start(0), matcher6.end(0), i13);
            i11 = i13;
        }
        int i14 = i11;
        Matcher matcher7 = Pattern.compile("[(（]*.[A-E]+(\\s+)?错.*?[)）]").matcher(str2);
        while (matcher7.find()) {
            spannableStringBuilder2.setSpan(new TextAppearanceSpan(null, 1, 0, colorStateList5, null), matcher7.start(0), matcher7.end(0), i14);
        }
        mTextV.setText(spannableStringBuilder2);
    }

    public void getStaticsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.mUpdateBean.getQuestion_id());
        ajaxParams.put("module_type", this.module_type + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mQuestionDataURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.12
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
                        if (questionDataStaticSetListBean.getData().get(i2).getQuestion_id().equals(SubCrazilyQuestionFragment.this.mUpdateBean.getQuestion_id())) {
                            SubCrazilyQuestionFragment.this.mUpdateBean.setmStaticsData(questionDataStaticSetListBean.getData().get(i2));
                            SubCrazilyQuestionFragment.this.initStaticData();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getTagData() {
        try {
            JSONArray jSONArray = new JSONArray(this.mUpdateBean.getSource());
            final String[] strArr = new String[jSONArray.length()];
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                strArr[i2] = jSONArray.optString(i2);
            }
            this.mFlowLayout.setAdapter(new TagAdapter<String>(strArr) { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.13
                @Override // com.psychiatrygarden.widget.tag.TagAdapter
                public View getView(FlowLayout parent, int position, String s2) {
                    TextView textView = (TextView) LayoutInflater.from(((BaseFragment) SubCrazilyQuestionFragment.this).mContext).inflate(R.layout.adapter_kuangbei_timu, (ViewGroup) SubCrazilyQuestionFragment.this.mFlowLayout, false);
                    textView.setText(s2);
                    return textView;
                }
            });
            this.mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.psychiatrygarden.fragmenthome.qe
                @Override // com.psychiatrygarden.widget.tag.TagFlowLayout.OnTagClickListener
                public final boolean onTagClick(View view, int i3, FlowLayout flowLayout) {
                    return this.f15945a.lambda$getTagData$26(strArr, view, i3, flowLayout);
                }
            });
            this.mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.re
                @Override // com.psychiatrygarden.widget.tag.TagFlowLayout.OnSelectListener
                public final void onSelected(Set set) {
                    SubCrazilyQuestionFragment.lambda$getTagData$27(set);
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
        Matcher matcher = Pattern.compile("[(（]*.[A-E]{1,}(\\s+)?对.*?[)）]").matcher(str);
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
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.pe
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f15920a.lambda$initAnsData$14(charSequence);
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
                textView.setTextColor(this.mContext.getResources().getColor(R.color.question_color));
            } else {
                textView.setBackgroundResource(R.drawable.gray_deek_round_bg_night);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.question_color_night));
            }
            textView.setText("点击为本题添加标签");
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ff
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15601c.lambda$initBiaoQianData$21(dataBiao, view);
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
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.gf
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15628c.lambda$initBiaoQianData$22(dataBiao, view);
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
                String[] strArrSplit = this.mUpdateBean.getRestore().split("\\[\\$delimiter\\$\\]");
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
            selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.kf
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f15725a.lambda$initRestoreData$11(charSequence);
                }
            });
            SelectableTextHelper selectableTextHelperBuild2 = new SelectableTextHelper.Builder(this.questiondetails_tv_content_ques).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
            this.mSelectableTextHelper = selectableTextHelperBuild2;
            selectableTextHelperBuild2.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.lf
                @Override // com.psychiatrygarden.widget.OnSelectListener
                public final void onTextSelected(CharSequence charSequence) {
                    this.f15816a.lambda$initRestoreData$12(charSequence);
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
                textView.setTextColor(this.mContext.getResources().getColor(R.color.black));
            } else {
                textView.setTextColor(this.mContext.getResources().getColor(R.color.question_color_night));
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
            this.llay_kuangbei_zhenti.setVisibility(8);
            return;
        }
        this.questiondetails_layout_diff.setVisibility(0);
        this.questiondetails_tv_statistics.setVisibility(0);
        this.questionans.setVisibility(8);
        this.questiondetails_bottom_layout.setVisibility(0);
        this.commeview.setVisibility(0);
        this.llay_kuangbei_zhenti.setVisibility(0);
        if (this.module_type.equals("1")) {
            this.questiondetails_layout_answer.setVisibility(0);
            this.lineout.setVisibility(0);
        } else {
            this.questiondetails_layout_answer.setVisibility(8);
            this.lineout.setVisibility(8);
        }
    }

    public void initTitleImg() {
        if (TextUtils.isEmpty(this.mUpdateBean.getTitle_img())) {
            this.imgtitle.setVisibility(8);
            return;
        }
        this.imgtitle.setVisibility(0);
        Glide.with(ProjectApp.instance()).load((Object) GlideUtils.generateUrl(this.mUpdateBean.getTitle_img())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.imgplacehodel_image).placeholder(R.drawable.imgplacehodel_image)).listener(new RequestListener<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.8
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model, Target<Drawable> target, boolean isFirstResource) {
                SubCrazilyQuestionFragment.this.imgtitle.setImageResource(R.drawable.imgplacehodel_image);
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                SubCrazilyQuestionFragment.this.imgtitle.setImageDrawable(resource);
                return true;
            }
        }).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.7
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (resource != null) {
                    float intrinsicWidth = resource.getIntrinsicWidth();
                    float intrinsicHeight = resource.getIntrinsicHeight();
                    float width = SubCrazilyQuestionFragment.this.imgtitle.getWidth();
                    if (width == 0.0f) {
                        width = SubCrazilyQuestionFragment.this.imgtitle.getResources().getDisplayMetrics().widthPixels;
                    }
                    int i2 = (int) ((intrinsicHeight / intrinsicWidth) * width);
                    ViewGroup.LayoutParams layoutParams = SubCrazilyQuestionFragment.this.imgtitle.getLayoutParams();
                    if (i2 >= 4096) {
                        layoutParams.height = R2.color.N_stretchTextColor;
                        SubCrazilyQuestionFragment.this.imgtitle.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        layoutParams.height = -1;
                    }
                    SubCrazilyQuestionFragment.this.imgtitle.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public void initTitleView(String bodyHTML) {
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.webview.loadDataWithBaseURL(null, getHtmlData(bodyHTML.replace("color:#fff", "color:#000")), "text/html; charset=utf-8", "utf-8", null);
        } else {
            this.webview.loadDataWithBaseURL(null, getHtmlData(bodyHTML.replace("dashed #888", "dashed #64729F").replace("color:#fff", "#64729F").replace("color:#444", "color:#64729F")), "text/html; charset=utf-8", "utf-8", null);
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.webviewhint.loadDataWithBaseURL(null, getHtmlData(bodyHTML), "text/html; charset=utf-8", "utf-8", null);
        } else {
            this.webviewhint.loadDataWithBaseURL(null, getHtmlData(bodyHTML.replace("color:#444", "color:#64729F").replace("color:#fff", "color:#121622").replace("dashed #888", "dashed #64729F")), "text/html; charset=utf-8", "utf-8", null);
        }
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
            this.viewpager_question_video.setAdapter(new QuestionVideoViewpagerAdapter(this.mContext, strArrSplit, Long.parseLong(this.mUpdateBean.getQuestion_id()), 10));
            final int length = strArrSplit.length;
            this.viewpager_question_video.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.5
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (length > 1) {
                        SubCrazilyQuestionFragment.this.circlepoint.setonPageScrolled(position, positionOffset, positionOffsetPixels);
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
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000L);
        this.webview.startAnimation(alphaAnimation);
        this.webview.setVisibility(0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        String str;
        SubQuestionMainActivity subQuestionMainActivity = (SubQuestionMainActivity) getActivity();
        Bundle arguments = getArguments();
        this.mUpdateBean = subQuestionMainActivity.mDataList.get(arguments != null ? arguments.getInt("positionT") : 0);
        this.collection_id = arguments != null ? arguments.getString("collection_id") : "0";
        if (arguments.getString("module_type") != null) {
            this.module_type = arguments.getString("module_type");
        }
        WeakReference weakReference = new WeakReference(this.drawable);
        if (weakReference.get() != null) {
            this.drawable = (Drawable) weakReference.get();
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan);
        } else {
            this.drawable = ContextCompat.getDrawable(this.mContext, R.drawable.huanyuan_night);
        }
        this.biaotxt = (TextView) holder.get(R.id.biaotxt);
        this.webview = (WebView) holder.get(R.id.webview);
        TextView textView = (TextView) holder.get(R.id.tv_correction);
        this.webviewhint = (WebView) holder.get(R.id.webviewhint);
        this.questiondetails_btn_commentNum = (Button) holder.get(R.id.questiondetails_btn_commentNum);
        this.questiondetails_layout_diff = (LinearLayout) holder.get(R.id.questiondetails_layout_diff);
        this.questiondetails_tv_statistics = (TextView) holder.get(R.id.questiondetails_tv_statistics);
        this.mRadioGroup_content = (LinearLayout) holder.get(R.id.mRadioGroup_content);
        this.ll_more_columns = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.questiondetails_tv_contents = (TextView) holder.get(R.id.questiondetails_tv_contents);
        this.questionans = (LinearLayout) holder.get(R.id.questionans);
        this.mFlowLayout = (TagFlowLayout) holder.get(R.id.id_flowlayout);
        this.llay_kuangbei_zhenti = (LinearLayout) holder.get(R.id.llay_kuangbei_zhenti);
        this.questiondetails_bottom_layout = (LinearLayout) holder.get(R.id.questiondetails_bottom_layout);
        this.questiondetails_btn_zantong = (ImageView) holder.get(R.id.questiondetails_btn_zantong);
        this.questiondetails_btn_centerMsg = (ImageView) holder.get(R.id.questiondetails_btn_centerMsg);
        this.questiondetails_btn_collect = (ImageView) holder.get(R.id.questiondetails_btn_collect);
        this.questiondetails_btn_edit = (ImageView) holder.get(R.id.questiondetails_btn_edit);
        this.questiondetails_layout_answer = (RelativeLayout) holder.get(R.id.questiondetails_layout_answer);
        this.commeview = (RelativeLayout) holder.get(R.id.commeview);
        this.questiondetails_tv_content_ques1 = (TextView) holder.get(R.id.questiondetails_tv_content_ques1);
        this.questiondetails_tv_content_ques = (TextView) holder.get(R.id.questiondetails_tv_content_ques);
        TextView textView2 = (TextView) holder.get(R.id.zuoduil);
        TextView textView3 = (TextView) holder.get(R.id.zuocuol);
        this.rl_question_video = (RelativeLayout) holder.get(R.id.rl_question_video);
        this.viewpager_question_video = (AutoHeightViewPager) holder.get(R.id.viewpager_question_video);
        this.circlepoint = (CirclePoint) holder.get(R.id.circlepoint);
        TextView textView4 = (TextView) holder.get(R.id.questiondetails_tv_outline);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.black);
        } else {
            this.redColors = ContextCompat.getColorStateList(this.mContext, R.color.jiucuo_night);
            this.blackColors = ContextCompat.getColorStateList(this.mContext, R.color.question_color_night);
        }
        this.redSelected = this.mContext.getResources().getColor(R.color.app_theme_red);
        this.redTransSelected = this.mContext.getResources().getColor(R.color.trans_app_theme_red);
        this.questiondetails_btn_zantong.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                Intent intent = new Intent(((BaseFragment) SubCrazilyQuestionFragment.this).mContext, (Class<?>) CommentNewActivity.class);
                intent.putExtra("question_id", Long.parseLong(SubCrazilyQuestionFragment.this.mUpdateBean.getQuestion_id()));
                intent.putExtra("module_type", Integer.parseInt(SubCrazilyQuestionFragment.this.module_type));
                intent.putExtra("comment_type", "2");
                intent.putExtra("isNewComzantong", true);
                intent.putExtra(com.alipay.sdk.authjs.a.f3174g, "mypraise");
                intent.putExtra("iscomValu", true);
                SubCrazilyQuestionFragment.this.startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.te
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16017c.lambda$initViews$0(view);
            }
        });
        this.questiondetails_btn_centerMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ue
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16050c.lambda$initViews$1(view);
            }
        });
        this.imgtitle = (ImageView) holder.get(R.id.imgtitle);
        this.lineout = (LinearLayout) holder.get(R.id.lineout);
        Button button = (Button) holder.get(R.id.btn_comment);
        this.line_viewok = (LinearLayout) holder.get(R.id.line_viewok);
        initSubViewData();
        if (this.mUpdateBean.getOwnerAns().equals("")) {
            this.webviewhint.setVisibility(0);
            this.webview.setVisibility(8);
        } else {
            this.webviewhint.setVisibility(8);
            this.webview.setVisibility(0);
        }
        this.questiondetails_btn_commentNum.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ve
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16080c.lambda$initViews$2(view);
            }
        });
        if (TextUtils.isEmpty(this.mUpdateBean.getOutlines_pm())) {
            textView4.setVisibility(8);
        } else {
            textView4.setVisibility(0);
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            str = "<font color='#dd594a'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        } else {
            str = "<font color='#B2575C'>[大纲考点]  </font>" + this.mUpdateBean.getOutlines_pm();
        }
        textView4.setText(Html.fromHtml(str));
        SelectableTextHelper selectableTextHelperBuild = new SelectableTextHelper.Builder(textView4).setSelectedColor(this.redTransSelected).setCursorHandleSizeInDp(20.0f).setCursorHandleColor(this.redSelected).build();
        this.mSelectableTextHelper = selectableTextHelperBuild;
        selectableTextHelperBuild.setSelectListener(new OnSelectListener() { // from class: com.psychiatrygarden.fragmenthome.xe
            @Override // com.psychiatrygarden.widget.OnSelectListener
            public final void onTextSelected(CharSequence charSequence) {
                this.f16122a.lambda$initViews$3(charSequence);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ye
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16147c.lambda$initViews$5(view);
            }
        });
        initTitleView(this.mUpdateBean.getTitle());
        this.questionans.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ze
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16170c.lambda$initViews$6(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.af
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15446c.lambda$initViews$7(view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.bf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15482c.lambda$initViews$8(view);
            }
        });
        initVideoData();
        initTitleImg();
        initStaticData();
        getBiaoData();
        initRestoreData();
        initAnsData();
        getStaticsData();
        getTagData();
        this.questiondetails_btn_collect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.cf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15513c.lambda$initViews$9(view);
            }
        });
        this.questiondetails_btn_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.df
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15551c.lambda$initViews$10(view);
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("notesave" + this.mUpdateBean.getQuestion_id())) {
            this.mUpdateBean.getmStaticsData().setIs_note(1);
            haveNoteimg();
        }
        if (str.equals("noteclear" + this.mUpdateBean.getQuestion_id())) {
            this.mUpdateBean.getmStaticsData().setIs_note(0);
            noNoteimg();
        }
    }

    public void postBiaoqianData(String question_id, String label_id, final List<BiaoqianBeab.DataBean> dataBiao) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", question_id);
        ajaxParams.put("label_id", label_id);
        ajaxParams.put("module_type", "" + this.module_type);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.muserLabelUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SubCrazilyQuestionFragment.9
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
                        return;
                    }
                    NewToast.showShort(((BaseFragment) SubCrazilyQuestionFragment.this).mContext, jSONObject.optString("message"), 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* renamed from: showlog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$initBiaoQianData$22(List<BiaoqianBeab.DataBean> dataBiao, View v2) {
        new XPopup.Builder(getActivity()).asCustom(new BiaoPupWindow(this.mContext, dataBiao, new BiaoqianInterface() { // from class: com.psychiatrygarden.fragmenthome.jf
            @Override // com.psychiatrygarden.interfaceclass.BiaoqianInterface
            public final void mBiaoianLinster(List list, boolean z2) {
                this.f15699a.lambda$showlog$23(list, z2);
            }
        })).toggle();
    }
}
