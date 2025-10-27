package com.psychiatrygarden.activity.online;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.SelectIdentityPartShadowPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class SelectIdentityNewActivity extends BaseActivity {
    private BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> adapter;
    public String identity_id;
    private SmartRefreshLayout mRefreshLayout;
    public String selectedText;
    public String title;
    public List<SelectIdentityBean.DataBean> data = new ArrayList();
    public int position = 0;
    private boolean isMatchIndetity = false;

    /* renamed from: com.psychiatrygarden.activity.online.SelectIdentityNewActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder> {
        final /* synthetic */ int val$normalTextColor;
        final /* synthetic */ int val$selectTextColor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int layoutResId, List data, final int val$selectTextColor, final int val$normalTextColor) {
            super(layoutResId, data);
            this.val$selectTextColor = val$selectTextColor;
            this.val$normalTextColor = val$normalTextColor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(SelectIdentityBean.DataBean dataBean, View view) {
            if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
                return;
            }
            SelectIdentityNewActivity.this.selectedText = dataBean.getIdentity_id();
            if (TextUtils.isEmpty(dataBean.getChildren().get(0).getCategory()) && !"1".equals(dataBean.getIs_last())) {
                notifyDataSetChanged();
                try {
                    Intent intent = new Intent(SelectIdentityNewActivity.this, (Class<?>) SelectIdentityNewActivity.class);
                    intent.putExtra("identity_id", "" + dataBean.getIdentity_id());
                    intent.putExtra("title", "" + dataBean.getTitle());
                    intent.putExtra("data", (Serializable) dataBean.getChildren());
                    intent.putExtra("position", SelectIdentityNewActivity.this.position + 1);
                    SelectIdentityNewActivity.this.startActivity(intent);
                    return;
                } catch (Exception e2) {
                    LogUtils.e("select", e2.toString());
                    return;
                }
            }
            SharePreferencesUtils.writeStrConfig((SelectIdentityNewActivity.this.position + 1) + "", "", SelectIdentityNewActivity.this);
            SharePreferencesUtils.writeStrConfig((SelectIdentityNewActivity.this.position + 2) + "", "", SelectIdentityNewActivity.this);
            EventBus.getDefault().post("sussful");
            SharePreferencesUtils.writeStrConfig(CommonParameter.identity_id, dataBean.getIdentity_id() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.App_Id, dataBean.getApp_id() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.app_mark, "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.Student_Type, dataBean.getApp_type() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.App_Name, dataBean.getTitle() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.am_pm, dataBean.getAm_pm() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_unit, dataBean.getIs_hidden_unit() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.isHideExp, dataBean.getIs_hidden_exp() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_course, dataBean.getIs_hidden_course() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_easy_option, dataBean.getIs_hidden_wrong_option() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_statistics_card, dataBean.getIs_hidden_statistics_card() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_shop, dataBean.getIs_hidden_shop() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore_img, dataBean.getIs_hidden_restore_img() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_question_type, dataBean.getIs_hidden_question_type() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_correction_error, dataBean.getIs_hidden_correction_error() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.catalogue_q, new Gson().toJson(dataBean.getChildren()), SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_difficulty, dataBean.getIs_hidden_difficulty() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_stat, dataBean.getIs_hidden_stat() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_label, dataBean.getIs_hidden_label() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_outlines, dataBean.getIs_hidden_outlines() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_restore, dataBean.getIs_hidden_restore() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis, dataBean.getIs_hidden_analysis() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options, dataBean.getIs_hidden_options() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_options_update, dataBean.getIs_hidden_options_update() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.is_hidden_analysis_update, dataBean.getIs_hidden_analysis_update() + "", SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.removeConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, SelectIdentityNewActivity.this.mContext), SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.removeContainConfig(CommonParameter.SEARCH_CUT_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, SelectIdentityNewActivity.this.mContext), SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.removeContainConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, SelectIdentityNewActivity.this.mContext), SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.removeContainConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, SelectIdentityNewActivity.this.mContext), SelectIdentityNewActivity.this.mContext);
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_NEW_HOME, TextUtils.equals("0", dataBean.getIs_hidden_index()), SelectIdentityNewActivity.this.mContext);
            SelectIdentityNewActivity.this.saveApptitle(dataBean.getApp_id());
            SelectIdentityNewActivity.this.submitPoint(dataBean.getIdentity_id());
            Intent intent2 = new Intent(SelectIdentityNewActivity.this, (Class<?>) HomePageNewActivity.class);
            intent2.addFlags(268468224);
            SelectIdentityNewActivity.this.startActivity(intent2);
            SelectIdentityNewActivity.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
            if (!TextUtils.isEmpty(UserConfig.getUserId())) {
                EventBus.getDefault().post("personData");
            }
            SelectIdentityNewActivity.this.finish();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder baseViewHolder, final SelectIdentityBean.DataBean dataBean) throws Resources.NotFoundException {
            TextView textView = (TextView) baseViewHolder.getView(R.id.text);
            View view = baseViewHolder.getView(R.id.view);
            textView.setText(dataBean.getTitle());
            view.setVisibility(8);
            textView.setBackgroundResource(R.drawable.shape_identity_gray);
            if ("0".equals(dataBean.getParent_id())) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.selectindentimg);
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    CommonUtil.mDoDrawable(SelectIdentityNewActivity.this, textView, R.drawable.selectindentimg, 2);
                } else {
                    drawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.first_text_color_night), PorterDuff.Mode.MULTIPLY);
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                }
                textView.setTextColor(dataBean.getIdentity_id().equals(SelectIdentityNewActivity.this.selectedText) ? this.val$selectTextColor : this.val$normalTextColor);
                textView.setSelected(dataBean.getIdentity_id().equals(SelectIdentityNewActivity.this.selectedText));
            } else {
                if (dataBean.getIs_last().equals("1")) {
                    textView.setCompoundDrawables(null, null, null, null);
                } else {
                    Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.selectindentimg);
                    if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                        CommonUtil.mDoDrawable(SelectIdentityNewActivity.this, textView, R.drawable.selectindentimg, 2);
                    } else {
                        drawable2.setColorFilter(ContextCompat.getColor(getContext(), R.color.first_text_color_night), PorterDuff.Mode.MULTIPLY);
                        textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
                    }
                }
                textView.setTextColor(dataBean.getIdentity_id().equals(SelectIdentityNewActivity.this.selectedText) ? this.val$selectTextColor : this.val$normalTextColor);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.n2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f13456c.lambda$convert$0(dataBean, view2);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.online.SelectIdentityNewActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(SelectIdentityBean selectIdentityBean, View view) throws Resources.NotFoundException {
            if (SkinManager.getCurrentSkinType(SelectIdentityNewActivity.this) == 0) {
                SelectIdentityNewActivity selectIdentityNewActivity = SelectIdentityNewActivity.this;
                CommonUtil.mDoDrawable(selectIdentityNewActivity, ((BaseActivity) selectIdentityNewActivity).mTxtActionbarTitle, R.drawable.roteimg, 2);
            } else {
                SelectIdentityNewActivity selectIdentityNewActivity2 = SelectIdentityNewActivity.this;
                CommonUtil.mDoDrawable(selectIdentityNewActivity2, ((BaseActivity) selectIdentityNewActivity2).mTxtActionbarTitle, R.drawable.roteimg_night, 2);
            }
            XPopup.Builder builderAtView = new XPopup.Builder(SelectIdentityNewActivity.this).atView(((BaseActivity) SelectIdentityNewActivity.this).titlebar_layout);
            SelectIdentityNewActivity selectIdentityNewActivity3 = SelectIdentityNewActivity.this;
            builderAtView.asCustom(new SelectIdentityPartShadowPopWindow(selectIdentityNewActivity3, selectIdentityNewActivity3.title, selectIdentityBean.getData(), new SelectIdentityPartShadowPopWindow.SelectPartDow() { // from class: com.psychiatrygarden.activity.online.SelectIdentityNewActivity.2.1
                @Override // com.psychiatrygarden.widget.SelectIdentityPartShadowPopWindow.SelectPartDow
                public void dismissToListen() throws Resources.NotFoundException {
                    if (SkinManager.getCurrentSkinType(SelectIdentityNewActivity.this) == 0) {
                        SelectIdentityNewActivity selectIdentityNewActivity4 = SelectIdentityNewActivity.this;
                        CommonUtil.mDoDrawable(selectIdentityNewActivity4, ((BaseActivity) selectIdentityNewActivity4).mTxtActionbarTitle, R.drawable.qiehuanimg, 2);
                    } else {
                        SelectIdentityNewActivity selectIdentityNewActivity5 = SelectIdentityNewActivity.this;
                        CommonUtil.mDoDrawable(selectIdentityNewActivity5, ((BaseActivity) selectIdentityNewActivity5).mTxtActionbarTitle, R.drawable.qiehuanimg_night, 2);
                    }
                }

                @Override // com.psychiatrygarden.widget.SelectIdentityPartShadowPopWindow.SelectPartDow
                public void mSelectPartDowe(String titleStr, String identity_id, List<SelectIdentityBean.DataBean> data) throws Resources.NotFoundException {
                    SharePreferencesUtils.writeStrConfig("0", identity_id, SelectIdentityNewActivity.this);
                    if (SkinManager.getCurrentSkinType(SelectIdentityNewActivity.this) == 0) {
                        SelectIdentityNewActivity selectIdentityNewActivity4 = SelectIdentityNewActivity.this;
                        CommonUtil.mDoDrawable(selectIdentityNewActivity4, ((BaseActivity) selectIdentityNewActivity4).mTxtActionbarTitle, R.drawable.qiehuanimg, 2);
                    } else {
                        SelectIdentityNewActivity selectIdentityNewActivity5 = SelectIdentityNewActivity.this;
                        CommonUtil.mDoDrawable(selectIdentityNewActivity5, ((BaseActivity) selectIdentityNewActivity5).mTxtActionbarTitle, R.drawable.qiehuanimg_night, 2);
                    }
                    SelectIdentityNewActivity selectIdentityNewActivity6 = SelectIdentityNewActivity.this;
                    selectIdentityNewActivity6.title = titleStr;
                    selectIdentityNewActivity6.setTitle(titleStr);
                    SelectIdentityNewActivity.this.adapter.setList(data);
                }
            })).show();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            SelectIdentityNewActivity.this.mRefreshLayout.finishRefresh(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                SelectIdentityNewActivity.this.mRefreshLayout.finishRefresh();
                final SelectIdentityBean selectIdentityBean = (SelectIdentityBean) new Gson().fromJson(s2, SelectIdentityBean.class);
                if (TextUtils.equals("", SelectIdentityNewActivity.this.selectedText)) {
                    SelectIdentityNewActivity.this.adapter.setList(selectIdentityBean.getData());
                    return;
                }
                for (int i2 = 0; i2 < selectIdentityBean.getData().size(); i2++) {
                    if (SelectIdentityNewActivity.this.selectedText.equals(selectIdentityBean.getData().get(i2).getIdentity_id())) {
                        SelectIdentityNewActivity.this.isMatchIndetity = true;
                        SelectIdentityNewActivity.this.data = selectIdentityBean.getData().get(i2).getChildren();
                        SelectIdentityNewActivity.this.title = "" + selectIdentityBean.getData().get(i2).getTitle();
                        SelectIdentityNewActivity.this.identity_id = "" + selectIdentityBean.getData().get(i2).getIdentity_id();
                        SelectIdentityNewActivity selectIdentityNewActivity = SelectIdentityNewActivity.this;
                        selectIdentityNewActivity.position = 1;
                        selectIdentityNewActivity.selectedText = SharePreferencesUtils.readStrConfig(SelectIdentityNewActivity.this.position + "", SelectIdentityNewActivity.this);
                        if (SkinManager.getCurrentSkinType(SelectIdentityNewActivity.this) == 0) {
                            SelectIdentityNewActivity selectIdentityNewActivity2 = SelectIdentityNewActivity.this;
                            CommonUtil.mDoDrawable(selectIdentityNewActivity2, ((BaseActivity) selectIdentityNewActivity2).mTxtActionbarTitle, R.drawable.qiehuanimg, 2);
                        } else {
                            SelectIdentityNewActivity selectIdentityNewActivity3 = SelectIdentityNewActivity.this;
                            CommonUtil.mDoDrawable(selectIdentityNewActivity3, ((BaseActivity) selectIdentityNewActivity3).mTxtActionbarTitle, R.drawable.qiehuanimg_night, 2);
                        }
                        ((BaseActivity) SelectIdentityNewActivity.this).mTxtActionbarTitle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.o2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) throws Resources.NotFoundException {
                                this.f13462c.lambda$onSuccess$0(selectIdentityBean, view);
                            }
                        });
                        SelectIdentityNewActivity.this.setTitle(SelectIdentityNewActivity.this.title + "");
                        SelectIdentityNewActivity.this.adapter.setList(SelectIdentityNewActivity.this.data);
                        return;
                    }
                    SelectIdentityNewActivity.this.isMatchIndetity = false;
                }
                if (SelectIdentityNewActivity.this.isMatchIndetity) {
                    return;
                }
                SelectIdentityNewActivity.this.adapter.setList(selectIdentityBean.getData());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(RefreshLayout refreshLayout) {
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveApptitle(String app_id) {
        String str;
        app_id.hashCode();
        switch (app_id) {
            case "10":
                str = "专硕";
                break;
            case "12":
                str = "执业医师";
                break;
            case "13":
                str = "执业助理医师";
                break;
            case "20":
                str = "中医专硕";
                break;
            case "21":
                str = "中医执业医师";
                break;
            case "40":
                str = "本科";
                break;
            default:
                str = "";
                break;
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.app_title, str, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submitPoint(String identityId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", identityId);
        YJYHttpUtils.get(this, NetworkRequestsURL.addUserQuestionIdentity, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.SelectIdentityNewActivity.3
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

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(UserConfig.getUserId())) {
            ajaxParams.put("user_id", UserConfig.getUserId());
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.subjectV2Api, ajaxParams, new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.app_theme_red, R.attr.first_text_color});
        int color = typedArrayObtainStyledAttributes.getColor(0, getColor(R.color.app_theme_red));
        int color2 = typedArrayObtainStyledAttributes.getColor(1, getColor(R.color.first_text_color));
        typedArrayObtainStyledAttributes.recycle();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_select_identity_item, this.data, color, color2);
        this.adapter = anonymousClass1;
        recyclerView.setAdapter(anonymousClass1);
        View view = new View(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        layoutParams.height = ScreenUtil.getPxByDp((Context) this, 10);
        view.setLayoutParams(layoutParams);
        this.adapter.addHeaderView(view);
        View view2 = new View(this);
        ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(-1, -2);
        layoutParams2.height = ScreenUtil.getPxByDp((Context) this, 20);
        view2.setLayoutParams(layoutParams2);
        this.adapter.addFooterView(view2);
        this.data = (List) getIntent().getExtras().getSerializable("data");
        this.title = getIntent().getExtras().getString("title", "");
        this.identity_id = getIntent().getStringExtra("identity_id");
        this.position = getIntent().getExtras().getInt("position", 0);
        this.selectedText = SharePreferencesUtils.readStrConfig(this.position + "", this);
        List<SelectIdentityBean.DataBean> list = this.data;
        if (list == null || list.size() <= 0) {
            this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.online.l2
                @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    this.f13440c.lambda$init$0(refreshLayout);
                }
            });
            this.mRefreshLayout.autoRefresh();
        } else {
            this.adapter.setList(this.data);
        }
        if ("".equals(this.title)) {
            if ("".equals(this.selectedText)) {
                this.mBtnActionbarBack.setVisibility(8);
            } else {
                this.mBtnActionbarBack.setVisibility(0);
            }
            setTitle("选择专业");
        } else {
            setTitle(this.title);
            this.mBtnActionbarBack.setVisibility(0);
        }
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.m2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f13448c.lambda$init$1(view3);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("sussful")) {
            SharePreferencesUtils.writeStrConfig(this.position + "", this.selectedText, this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("")) {
            finish();
            return true;
        }
        if (this.title.equals("")) {
            ToastUtil.shortToast(this, "身份已重置,请选择身份！");
            return true;
        }
        finish();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_select_identity_show_frist);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
