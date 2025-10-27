package com.psychiatrygarden.activity.forum.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.forum.GiveLogAdapter;
import com.psychiatrygarden.activity.forum.bean.GiveLogBean;
import com.psychiatrygarden.activity.vip.bean.VipTipsBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class GivLogFragment extends BaseFragment {
    public static final int SVIP = 1;
    public static final int VIP = 0;
    private BaseQuickAdapter<GiveLogBean.DataBean.DataChildlistBean, BaseViewHolder> adapter;
    private TextView countCerivaelText;
    private RecyclerView recycleview;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView timeTxt;
    private TextView title;
    private TextView todayVericalText;
    private int type;

    private void getVip() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.vipToApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.GivLogFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    String strOptString = jSONObject.optJSONObject("data").optString("encryption");
                    if (TextUtils.isEmpty(strOptString)) {
                        return;
                    }
                    String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFYS, strOptString);
                    VipTipsBean vipTipsBean = new VipTipsBean();
                    vipTipsBean.setCode(jSONObject.optString("code"));
                    vipTipsBean.setData((VipTipsBean.DataDTO) new Gson().fromJson(strDecode, VipTipsBean.DataDTO.class));
                    vipTipsBean.setServer_time(jSONObject.optString("server_time"));
                    vipTipsBean.setMessage(jSONObject.optString("message"));
                    if (vipTipsBean.getCode().equals("200")) {
                        GivLogFragment.this.title.setText(GivLogFragment.this.type == 0 ? "我的VIP会员" : "我的SVIP会员");
                        GivLogFragment.this.loadData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void initHeaderView() {
        View viewInflate = getLayoutInflater().inflate(R.layout.layout_give_log_header, (ViewGroup) this.recycleview, false);
        this.title = (TextView) viewInflate.findViewById(R.id.title);
        this.timeTxt = (TextView) viewInflate.findViewById(R.id.timeTxt);
        this.todayVericalText = (TextView) viewInflate.findViewById(R.id.todayVericalText);
        this.countCerivaelText = (TextView) viewInflate.findViewById(R.id.countCerivaelText);
        this.adapter.addHeaderView(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        getVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("service_name", this.type == 0 ? "service_vip" : "service_svip");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getgiveLogApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.GivLogFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                GivLogFragment.this.smartRefreshLayout.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str;
                String str2;
                super.onSuccess((AnonymousClass2) s2);
                try {
                    GiveLogBean giveLogBean = (GiveLogBean) new Gson().fromJson(s2, GiveLogBean.class);
                    boolean zEquals = "200".equals(giveLogBean.code);
                    GivLogFragment.this.smartRefreshLayout.finishRefresh(zEquals);
                    if (zEquals) {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(giveLogBean.data.due_date);
                        Matcher matcher = Pattern.compile("\\d").matcher(giveLogBean.data.due_date);
                        if (SkinManager.getCurrentSkinType(GivLogFragment.this.getActivity()) == 1) {
                            str = "#B2575C";
                            str2 = "#575F79";
                        } else {
                            str = "#DD594A";
                            str2 = "#C2C2C2";
                        }
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(str)), matcher.start(), matcher.end(), 33);
                        }
                        GivLogFragment.this.timeTxt.setText(spannableStringBuilder);
                        GivLogFragment.this.todayVericalText.setText(Html.fromHtml("<font color='" + str + "'>" + giveLogBean.data.today_give_day + "</font>"));
                        GivLogFragment.this.countCerivaelText.setText(Html.fromHtml("<font color='" + str2 + "'>" + giveLogBean.data.total_give_day + "</font>"));
                        GivLogFragment.this.adapter.setList(giveLogBean.data.getList());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    GivLogFragment.this.smartRefreshLayout.finishRefresh(false);
                }
            }
        });
    }

    public static GivLogFragment newInstance(int type) {
        GivLogFragment givLogFragment = new GivLogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        givLogFragment.setArguments(bundle);
        return givLogFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_give_log;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if (getArguments() != null) {
            this.type = getArguments().getInt("type", 0);
        } else {
            this.type = 0;
        }
        this.smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.recycleview = (RecyclerView) holder.get(R.id.recycleview);
        if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            this.recycleview.setBackgroundResource(R.color.secondary_backgroup_color_night);
        } else {
            this.recycleview.setBackgroundResource(R.color.secondary_backgroup_color);
        }
        this.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.forum.fragment.a0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12372c.lambda$initViews$0(refreshLayout);
            }
        });
        RecyclerView recyclerView = this.recycleview;
        GiveLogAdapter giveLogAdapter = new GiveLogAdapter();
        this.adapter = giveLogAdapter;
        recyclerView.setAdapter(giveLogAdapter);
        this.adapter.setEmptyView(R.layout.layout_empty_view);
        initHeaderView();
        this.smartRefreshLayout.setEnableLoadMore(false);
        getVip();
    }
}
