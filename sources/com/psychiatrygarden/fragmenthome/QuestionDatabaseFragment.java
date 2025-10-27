package com.psychiatrygarden.fragmenthome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import com.beizi.fusion.widget.ScrollClickView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.ContactCustomerServiceNewActivity;
import com.psychiatrygarden.activity.HandoutListActivity;
import com.psychiatrygarden.activity.material.InformationProjectAct;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.EventSortBean;
import com.psychiatrygarden.bean.SortValueBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionDatabaseFragment extends BaseFragment {
    public static final int FRISTPOSITION = 1;
    public static final int ZREOPOSITION = 0;
    private LinearLayout line_c_filtrate;
    private LinearLayout linecicle;
    private LinearLayout linepai;
    private PopupWindow popupWindow_filtrate;
    private TextView seachid;
    private TextView titleluntan;
    private TextView tv_circle;
    private TextView tv_filtrateTxt;
    private TextView tv_handout;
    private TextView tv_paixu;
    private boolean isCircle = true;
    private List<SortValueBean.DataBean> dataData = new ArrayList();
    private SupportFragment[] mFragments = new SupportFragment[2];
    private boolean isInited = false;
    private final View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ka
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            this.f15718c.lambda$new$0(view);
        }
    };
    private boolean isShowJingyan = false;
    private final BroadcastReceiver loadDataReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            QuestionDatabaseFragment.this.isShowJingyan = true;
            QuestionDatabaseFragment.this.isSelect(false, true);
            QuestionDatabaseFragment questionDatabaseFragment = QuestionDatabaseFragment.this;
            questionDatabaseFragment.showHideFragment(questionDatabaseFragment.mFragments[1]);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$1(View view) {
        this.popupWindow_filtrate.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$2(CommAdapter commAdapter, AdapterView adapterView, View view, int i2, long j2) {
        for (int i3 = 0; i3 < this.dataData.size(); i3++) {
            this.dataData.get(i3).setIsSelect("0");
        }
        this.dataData.get(i2).setIsSelect("1");
        commAdapter.notifyDataSetChanged();
        SharePreferencesUtils.writeStrConfig(CommonParameter.sortvalue, this.dataData.get(i2).getSort_type(), getActivity());
        EventBus.getDefault().post(new EventSortBean(CommonParameter.sortvalue, ""));
        this.popupWindow_filtrate.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$3(View view) {
        this.popupWindow_filtrate.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$4(LinearLayout linearLayout) {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha = 0.7f;
        getActivity().getWindow().setAttributes(attributes);
        linearLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.ver_fadint));
        linearLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialogFiltrate$5() {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha = 1.0f;
        getActivity().getWindow().setAttributes(attributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        String name = getTopFragment().getClass().getName();
        int id = view.getId();
        if (id == R.id.tv_circle) {
            isSelect(true, false);
            SupportFragment[] supportFragmentArr = this.mFragments;
            showHideFragment(supportFragmentArr[0], supportFragmentArr[1]);
            LogUtils.e("current_frag", name);
            return;
        }
        if (id == R.id.tv_handout) {
            isSelect(false, true);
            SupportFragment[] supportFragmentArr2 = this.mFragments;
            showHideFragment(supportFragmentArr2[1], supportFragmentArr2[0]);
            LogUtils.e("current_frag", ":" + name);
            return;
        }
        if (id == R.id.tv_paixu) {
            if (this.isCircle) {
                return;
            }
            List<SortValueBean.DataBean> list = this.dataData;
            if (list == null || list.size() <= 0) {
                getShaixuanData(view);
                return;
            } else {
                dialogFiltrate(view);
                return;
            }
        }
        if (id == R.id.tv_search || id == R.id.seachid) {
            if (this.isCircle) {
                InformationProjectAct.newIntent(getActivity());
                return;
            } else {
                goActivity(HandoutListActivity.class);
                return;
            }
        }
        if (id == R.id.customimg) {
            if (isLogin()) {
                startActivity(new Intent(getActivity(), (Class<?>) ContactCustomerServiceNewActivity.class));
            }
        } else {
            if (id != R.id.line_filtrate || CommonUtil.isFastClick()) {
                return;
            }
            Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
            intent.putExtra("flag", false);
            intent.putExtra("appbeanname", "");
            startActivity(intent);
        }
    }

    public static QuestionDatabaseFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionDatabaseFragment questionDatabaseFragment = new QuestionDatabaseFragment();
        questionDatabaseFragment.setArguments(bundle);
        return questionDatabaseFragment;
    }

    public void dialogFiltrate(View v2) {
        View viewInflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.layout_jingyanpop, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.lineview);
        final LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.lineanmo);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.fa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15593c.lambda$dialogFiltrate$1(view);
            }
        });
        ListView listView = (ListView) viewInflate.findViewById(R.id.listView);
        TextView textView = (TextView) viewInflate.findViewById(R.id.close);
        List<SortValueBean.DataBean> list = this.dataData;
        if (list != null && list.size() > 0) {
            final CommAdapter<SortValueBean.DataBean> commAdapter = new CommAdapter<SortValueBean.DataBean>(this.dataData, getActivity(), R.layout.layout_handout_pop) { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragment.2
                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, SortValueBean.DataBean dataBean, int position) {
                    vHolder.setText(R.id.tv_filtrate1, dataBean.getTitle());
                    ImageView imageView = (ImageView) vHolder.getView(R.id.iv_filtrate1);
                    if (dataBean.getIsSelect().equals("0")) {
                        imageView.setVisibility(8);
                    } else {
                        imageView.setVisibility(0);
                    }
                }
            };
            listView.setAdapter((ListAdapter) commAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.ga
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                    this.f15623c.lambda$dialogFiltrate$2(commAdapter, adapterView, view, i2, j2);
                }
            });
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ha
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15642c.lambda$dialogFiltrate$3(view);
            }
        });
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1);
        this.popupWindow_filtrate = popupWindow;
        popupWindow.setFocusable(true);
        this.popupWindow_filtrate.setOutsideTouchable(true);
        v2.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.ia
            @Override // java.lang.Runnable
            public final void run() {
                this.f15666c.lambda$dialogFiltrate$4(linearLayout2);
            }
        });
        this.popupWindow_filtrate.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.fragmenthome.ja
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f15691c.lambda$dialogFiltrate$5();
            }
        });
        this.popupWindow_filtrate.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow_filtrate.showAtLocation(v2, 80, 0, 0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_database;
    }

    public void getShaixuanData(final View v2) {
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.getSortListUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionDatabaseFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                QuestionDatabaseFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    SortValueBean sortValueBean = (SortValueBean) new Gson().fromJson(s2, SortValueBean.class);
                    if (sortValueBean.getCode().equals("200")) {
                        QuestionDatabaseFragment.this.dataData = sortValueBean.getData();
                        for (int i2 = 0; i2 < QuestionDatabaseFragment.this.dataData.size(); i2++) {
                            if (i2 == 0) {
                                ((SortValueBean.DataBean) QuestionDatabaseFragment.this.dataData.get(i2)).setIsSelect("1");
                            }
                        }
                        QuestionDatabaseFragment.this.dialogFiltrate(v2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                QuestionDatabaseFragment.this.hideProgressDialog();
            }
        });
    }

    public void goFragment(boolean isNotice) {
        ArrayList arrayList = new ArrayList();
        if ("1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.isHideExp, getActivity()))) {
            arrayList.add(CircleNewFrament.newInstance(12));
        } else {
            arrayList.add(CircleNewFrament.newInstance(12));
            arrayList.add(HandoutListFargment.newInstance(isNotice));
            this.tv_circle.setText("帖子");
            this.tv_handout.setText("经验");
        }
        this.mFragments = new SupportFragment[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            this.mFragments[i2] = (SupportFragment) arrayList.get(i2);
        }
        if (this.isShowJingyan) {
            loadMultipleRootFragment(R.id.frame_contain, 1, this.mFragments);
        } else {
            loadMultipleRootFragment(R.id.frame_contain, 0, this.mFragments);
        }
        this.isInited = true;
    }

    public void initTxt() {
        try {
            if (this.tv_filtrateTxt != null) {
                this.tv_filtrateTxt.setText(String.format(Locale.CHINA, " [%s]", SharePreferencesUtils.readStrConfig(CommonParameter.app_mark, getActivity(), "")));
            }
            SharePreferencesUtils.writeIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT, 6, getActivity());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.loadDataReceiver, new IntentFilter("jumpToJingyan"));
        SharePreferencesUtils.writeStrConfig(CommonParameter.sortvalue, "1", getActivity());
        this.titleluntan = (TextView) holder.get(R.id.titleluntan);
        this.tv_handout = (TextView) holder.get(R.id.tv_handout);
        this.line_c_filtrate = (LinearLayout) holder.get(R.id.line_filtrate);
        this.tv_filtrateTxt = (TextView) holder.get(R.id.tv_filtrateTxt);
        this.tv_circle = (TextView) holder.get(R.id.tv_circle);
        this.seachid = (TextView) holder.get(R.id.seachid);
        this.linepai = (LinearLayout) holder.get(R.id.linepai);
        this.linecicle = (LinearLayout) holder.get(R.id.linecicle);
        this.tv_handout.setOnClickListener(this.mOnclick);
        this.tv_circle.setOnClickListener(this.mOnclick);
        this.line_c_filtrate.setOnClickListener(this.mOnclick);
        this.tv_paixu = (TextView) holder.get(R.id.tv_paixu);
        TextView textView = (TextView) holder.get(R.id.tv_search);
        TextView textView2 = (TextView) holder.get(R.id.customimg);
        textView2.setVisibility(8);
        this.tv_paixu.setOnClickListener(this.mOnclick);
        textView.setOnClickListener(this.mOnclick);
        textView2.setOnClickListener(this.mOnclick);
        this.seachid.setOnClickListener(this.mOnclick);
        isSelect(true, false);
        if (!"1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.isHideExp, getActivity()))) {
            this.titleluntan.setVisibility(8);
            this.linecicle.setVisibility(0);
        } else {
            this.titleluntan.setVisibility(0);
            this.linecicle.setVisibility(8);
            this.titleluntan.setText("论坛");
        }
    }

    public void isSelect(boolean circleFragmentTrue, boolean handoutFragmentTrue) {
        this.tv_circle.setSelected(circleFragmentTrue);
        this.tv_handout.setSelected(handoutFragmentTrue);
        this.isCircle = circleFragmentTrue;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, getActivity()).equals("z")) {
            this.tv_paixu.setVisibility(8);
            return;
        }
        this.linepai.setVisibility(0);
        if (this.isCircle) {
            this.tv_paixu.setVisibility(8);
        } else if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("40")) {
            this.tv_paixu.setVisibility(8);
        } else {
            this.tv_paixu.setVisibility(0);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.loadDataReceiver);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("xitongxiaoxi")) {
            initTxt();
        }
        if (str.equals("up")) {
            this.linecicle.setVisibility(8);
            this.linepai.setVisibility(8);
            this.line_c_filtrate.setVisibility(8);
            if (this.seachid.getVisibility() != 0) {
                CommonUtil.setShowAnimation(this.seachid, 300);
                return;
            }
            return;
        }
        if (str.equals(ScrollClickView.DIR_DOWN)) {
            if (this.seachid.getVisibility() != 8) {
                CommonUtil.setHideAnimation(this.line_c_filtrate, this.linecicle, this.linepai, this.seachid, this.titleluntan, 300);
            } else {
                this.seachid.setVisibility(8);
                this.seachid.setClickable(false);
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        goFragment(false);
        initTxt();
    }
}
