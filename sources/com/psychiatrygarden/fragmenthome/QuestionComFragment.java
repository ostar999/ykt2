package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CombineQuestionActivity;
import com.psychiatrygarden.activity.CombineQuestionMainNewActivity;
import com.psychiatrygarden.activity.ContactCustomerServiceNewActivity;
import com.psychiatrygarden.activity.SearchQuestionActivity;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.QuestionCombineDataEvent;
import com.psychiatrygarden.event.ShowQuestionCombineEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionComFragment extends BaseFragment implements View.OnClickListener {
    public static final int FRISTPOSITION = 1;
    public static final int ZREOPOSITION = 0;
    private TextView customimg;
    private ImageView ivStartCombineQuestionShortcut;
    private ImageView ivStartQuestionCombine;
    private ImageView line_filtrate;
    private LinearLayout linechange;
    private ImageView mImgQuestion;
    private ImageView mImgSetList;
    private LinearLayout mLyQuestion;
    private LinearLayout mLySetList;
    private View questionMask;
    private boolean showButton;
    private boolean showShortCut;
    String start_timestamp;
    private TextView title;
    private TextView tvQuestion;
    private TextView tvSetList;
    private TextView tv_search;
    private TextView tv_woyaoshangchuan;
    private SupportFragment[] mFragments = new SupportFragment[1];
    private boolean hasData = false;

    private void getQuestionCategory() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionCategory, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionComFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionComFragment.this.goFragment();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.questionCategoryData, jSONObject.optString("data"), ((BaseFragment) QuestionComFragment.this).mContext);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                QuestionComFragment.this.goFragment();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (isLogin()) {
            pointCount(this.mContext, "3");
            CombineQuestionMainNewActivity.gotoCombineQuestionMain(getContext(), "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (isLogin()) {
            startActivity(new Intent(view.getContext(), (Class<?>) CombineQuestionActivity.class));
        }
    }

    public static QuestionComFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionComFragment questionComFragment = new QuestionComFragment();
        questionComFragment.setArguments(bundle);
        return questionComFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_questioncom;
    }

    public void goFragment() {
        SharePreferencesUtils.readStrConfig(CommonParameter.is_hidden_unit, getActivity());
        ArrayList arrayList = new ArrayList();
        arrayList.add(QuestionHomeNewFragment.newInstance());
        if (arrayList.size() > 1) {
            this.title.setVisibility(8);
            this.linechange.setVisibility(0);
        } else {
            this.title.setText("题库");
            this.title.setTypeface(Typeface.DEFAULT_BOLD);
            this.title.setVisibility(0);
            this.linechange.setVisibility(8);
        }
        this.mFragments = new SupportFragment[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            this.mFragments[i2] = (SupportFragment) arrayList.get(i2);
        }
        loadMultipleRootFragment(R.id.questioncom, 0, this.mFragments);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.customimg = (TextView) holder.get(R.id.customimg);
        this.title = (TextView) holder.get(R.id.title);
        this.linechange = (LinearLayout) holder.get(R.id.linechange);
        this.mLyQuestion = (LinearLayout) holder.get(R.id.ly_question);
        this.tvQuestion = (TextView) holder.get(R.id.tvQuestion);
        this.mImgQuestion = (ImageView) holder.get(R.id.img_question);
        this.mLySetList = (LinearLayout) holder.get(R.id.ly_set_list);
        this.tvSetList = (TextView) holder.get(R.id.tvSetList);
        this.mImgSetList = (ImageView) holder.get(R.id.img_set_list);
        this.tv_search = (TextView) holder.get(R.id.tv_search);
        this.line_filtrate = (ImageView) holder.get(R.id.line_filtrate);
        this.tv_woyaoshangchuan = (TextView) holder.get(R.id.tv_woyaoshangchuan);
        this.ivStartQuestionCombine = (ImageView) holder.get(R.id.iv_start_combine_question);
        this.questionMask = holder.get(R.id.question_mask);
        this.ivStartQuestionCombine.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.da
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15542c.lambda$initViews$0(view);
            }
        });
        ImageView imageView = (ImageView) holder.get(R.id.iv_start_combine_question_shortcut);
        this.ivStartCombineQuestionShortcut = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ea
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15567c.lambda$initViews$1(view);
            }
        });
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity(), "").equals("40")) {
            this.tvQuestion.setText("选择题");
            this.tvSetList.setText("主观题");
        } else {
            this.tvQuestion.setText("题库");
            this.tvSetList.setText("题单");
        }
        this.tvQuestion.setTypeface(Typeface.defaultFromStyle(1));
        this.tv_woyaoshangchuan.setOnClickListener(this);
        this.mLySetList.setOnClickListener(this);
        this.mLyQuestion.setOnClickListener(this);
        this.line_filtrate.setOnClickListener(this);
        this.tv_search.setOnClickListener(this);
        this.customimg.setOnClickListener(this);
        this.questionMask.setOnClickListener(this);
        if (ProjectApp.newHomeStyle) {
            this.customimg.setVisibility(8);
        } else {
            this.customimg.setVisibility(0);
        }
        isSelect(true, false);
        getQuestionCategory();
    }

    public void isSelect(boolean mQuestionHomeJumpFragment, boolean mQuestionSetListFragment) {
        this.tvQuestion.setSelected(mQuestionHomeJumpFragment);
        this.tvSetList.setSelected(mQuestionSetListFragment);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.ly_question) {
            this.tvQuestion.setTypeface(Typeface.defaultFromStyle(1));
            this.tvSetList.setTypeface(Typeface.defaultFromStyle(0));
            this.mImgQuestion.setVisibility(0);
            this.mImgSetList.setVisibility(4);
            this.tvQuestion.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_16));
            this.tvSetList.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_14));
            isSelect(true, false);
            SupportFragment[] supportFragmentArr = this.mFragments;
            showHideFragment(supportFragmentArr[0], supportFragmentArr[1]);
            this.tv_search.setVisibility(0);
            this.line_filtrate.setVisibility(0);
            this.tv_woyaoshangchuan.setVisibility(8);
            SupportFragment[] supportFragmentArr2 = this.mFragments;
            if (supportFragmentArr2.length > 0) {
                SupportFragment supportFragment = supportFragmentArr2[0];
                if (supportFragment instanceof QuestionHomeNewFragment) {
                    EventBus.getDefault().post(new ShowQuestionCombineEvent(((QuestionHomeNewFragment) supportFragment).isCurrentCombineQuestion(), this.showShortCut));
                    return;
                }
                return;
            }
            return;
        }
        if (id == R.id.ly_set_list) {
            this.tvQuestion.setTypeface(Typeface.defaultFromStyle(0));
            this.tvSetList.setTypeface(Typeface.defaultFromStyle(1));
            this.mImgQuestion.setVisibility(4);
            this.mImgSetList.setVisibility(0);
            this.tvQuestion.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_14));
            this.tvSetList.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_16));
            isSelect(false, true);
            SupportFragment[] supportFragmentArr3 = this.mFragments;
            showHideFragment(supportFragmentArr3[1], supportFragmentArr3[0]);
            this.tv_search.setVisibility(8);
            this.line_filtrate.setVisibility(0);
            this.tv_woyaoshangchuan.setVisibility(8);
            EventBus.getDefault().post(new ShowQuestionCombineEvent(false, false));
            return;
        }
        if (id == R.id.tv_search) {
            if (isLogin()) {
                startActivity(new Intent(getActivity(), (Class<?>) SearchQuestionActivity.class));
                return;
            }
            return;
        }
        if (id == R.id.customimg) {
            if (isLogin()) {
                startActivity(new Intent(getActivity(), (Class<?>) ContactCustomerServiceNewActivity.class));
            }
        } else if (id != R.id.line_filtrate) {
            if (id == R.id.tv_woyaoshangchuan) {
                new XPopup.Builder(getActivity()).asImageViewer(null, Integer.valueOf(R.drawable.tidannew), new ImageLoaderUtils()).isShowSaveButton(false).show();
            }
        } else {
            if (CommonUtil.isFastClick()) {
                return;
            }
            Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
            intent.putExtra("flag", false);
            intent.putExtra("appbeanname", "");
            startActivity(intent);
        }
    }

    @Subscribe
    public void onEventMainThread(QuestionCombineDataEvent event) {
        this.hasData = event.getHasData();
        if (!this.showButton) {
            this.ivStartQuestionCombine.setVisibility(8);
            this.ivStartCombineQuestionShortcut.setVisibility(8);
            return;
        }
        if (this.showShortCut) {
            this.ivStartQuestionCombine.setVisibility(8);
            this.ivStartCombineQuestionShortcut.setVisibility(0);
            return;
        }
        SupportFragment[] supportFragmentArr = this.mFragments;
        if (supportFragmentArr != null && supportFragmentArr.length > 0) {
            SupportFragment supportFragment = supportFragmentArr[0];
            if (supportFragment instanceof QuestionHomeNewFragment) {
                if (((QuestionHomeNewFragment) supportFragment).checkCurrentIsCombineQuestion()) {
                    this.ivStartQuestionCombine.setVisibility(0);
                } else {
                    this.ivStartQuestionCombine.setVisibility(8);
                }
            }
        }
        this.ivStartCombineQuestionShortcut.setVisibility(8);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        super.onSupportInvisible();
        CommonUtil.addLog(getClass().getSimpleName(), "题库首页", this.start_timestamp, System.currentTimeMillis() + "");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
        this.start_timestamp = System.currentTimeMillis() + "";
    }

    @Subscribe
    public void onEventMainThread(ShowQuestionCombineEvent event) {
        this.showButton = event.getShow();
        if (event.getShow() && UserConfig.isLogin()) {
            if (event.getShowShortCut()) {
                this.ivStartQuestionCombine.setVisibility(8);
                this.ivStartCombineQuestionShortcut.setVisibility(0);
                return;
            }
            this.ivStartCombineQuestionShortcut.setVisibility(8);
            SupportFragment[] supportFragmentArr = this.mFragments;
            if (supportFragmentArr != null && supportFragmentArr.length > 0) {
                SupportFragment supportFragment = supportFragmentArr[0];
                if (supportFragment instanceof QuestionHomeNewFragment) {
                    if (((QuestionHomeNewFragment) supportFragment).checkCurrentIsCombineQuestion()) {
                        this.ivStartQuestionCombine.setVisibility(0);
                    } else {
                        this.ivStartQuestionCombine.setVisibility(8);
                    }
                }
            }
            if (this.showShortCut || this.hasData) {
                return;
            }
            this.ivStartQuestionCombine.setVisibility(8);
            this.ivStartCombineQuestionShortcut.setVisibility(8);
            return;
        }
        this.showShortCut = this.ivStartCombineQuestionShortcut.getVisibility() == 0;
        this.ivStartQuestionCombine.setVisibility(8);
        this.ivStartCombineQuestionShortcut.setVisibility(8);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String mEveStr) {
        if (mEveStr.equals("jumpToQuestionList")) {
            this.tvQuestion.setTypeface(Typeface.defaultFromStyle(0));
            this.tvSetList.setTypeface(Typeface.defaultFromStyle(1));
            this.mImgQuestion.setVisibility(4);
            this.mImgSetList.setVisibility(0);
            this.tvQuestion.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_14));
            this.tvSetList.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.sp_16));
            isSelect(false, true);
            SupportFragment[] supportFragmentArr = this.mFragments;
            showHideFragment(supportFragmentArr[1], supportFragmentArr[0]);
            this.tv_search.setVisibility(8);
            this.customimg.setVisibility(0);
            this.line_filtrate.setVisibility(0);
            this.tv_woyaoshangchuan.setVisibility(8);
            return;
        }
        if (mEveStr.equals("show_home_bot_mask")) {
            this.questionMask.setVisibility(0);
        } else if (mEveStr.equals("dismiss_home_bot_mask")) {
            this.questionMask.setVisibility(8);
        }
    }
}
