package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.courselist.CourseListChpterActivity;
import com.psychiatrygarden.activity.courselist.CourseMoreActivity;
import com.psychiatrygarden.adapter.CourseListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 4912;
    public CourseListAdapter adapter;
    private String c_id = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.CourseListFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 4912) {
                if (!CourseListFragment.this.swip.isRefreshing()) {
                    CourseListFragment.this.swip.setRefreshing(true);
                }
                CourseListFragment.this.mGetData();
            }
        }
    };
    private SwipeRefreshLayout swip;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLazyInitView$0() {
        this.swip.setRefreshing(true);
        mGetData();
    }

    public static CourseListFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseListFragment courseListFragment = new CourseListFragment();
        courseListFragment.setArguments(bundle);
        return courseListFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.getString("c_id") != null) {
            this.c_id = arguments.getString("c_id");
        }
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.myListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.swip);
        this.swip = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.swip.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.swip.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swip.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.swip.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        CourseListAdapter courseListAdapter = new CourseListAdapter(this.c_id);
        this.adapter = courseListAdapter;
        recyclerView.setAdapter(courseListAdapter);
        this.adapter.setEmptyView(R.layout.adapter_empty_txt_view);
        this.adapter.setOnItemChildClickListenr(new CourseListAdapter.OnItemChildClickListenr() { // from class: com.psychiatrygarden.fragmenthome.CourseListFragment.2
            @Override // com.psychiatrygarden.adapter.CourseListAdapter.OnItemChildClickListenr
            public void onItemChildClickMethod(CourseList2Bean.DataBean.DataChildBean childBean) {
                if (CourseListFragment.this.isLogin()) {
                    CourseListFragment courseListFragment = CourseListFragment.this;
                    courseListFragment.pointCount(((BaseFragment) courseListFragment).mContext, "4");
                    Intent intent = new Intent(CourseListFragment.this.getActivity(), (Class<?>) CourseListChpterActivity.class);
                    intent.putExtra("vidteaching", childBean);
                    CourseListFragment.this.startActivity(intent);
                }
            }

            @Override // com.psychiatrygarden.adapter.CourseListAdapter.OnItemChildClickListenr
            public void onItemClickMethod(String c_id, String title) {
                if (CourseListFragment.this.isLogin()) {
                    Intent intent = new Intent(CourseListFragment.this.getActivity(), (Class<?>) CourseMoreActivity.class);
                    intent.putExtra("c_id", "" + c_id);
                    intent.putExtra("title", "" + title);
                    CourseListFragment.this.startActivity(intent);
                }
            }
        });
    }

    public void mDoData() {
        try {
            CourseList2Bean courseList2Bean = new CourseList2Bean();
            if (this.c_id.equals("")) {
                courseList2Bean = (CourseList2Bean) new Gson().fromJson(SharePreferencesUtils.readStrConfig("courseData_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()), getActivity()), CourseList2Bean.class);
            } else {
                String strConfig = SharePreferencesUtils.readStrConfig("courseMoreData_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()) + StrPool.UNDERLINE + this.c_id, getActivity());
                courseList2Bean.setCode(new JSONObject(strConfig).optInt("code"));
                courseList2Bean.setMessage(new JSONObject(strConfig).optString("message"));
                new CourseList2Bean.DataBean();
                CourseList2Bean.DataBean dataBean = (CourseList2Bean.DataBean) new Gson().fromJson(new JSONObject(strConfig).optString("data"), CourseList2Bean.DataBean.class);
                ArrayList arrayList = new ArrayList();
                arrayList.add(dataBean);
                courseList2Bean.setData(arrayList);
            }
            if (courseList2Bean.getCode() == 200) {
                ArrayList arrayList2 = new ArrayList();
                if (courseList2Bean.getData().size() > 0) {
                    for (int i2 = 0; i2 < courseList2Bean.getData().size(); i2++) {
                        if (courseList2Bean.getData().get(i2).getItems().size() > 0) {
                            arrayList2.add(courseList2Bean.getData().get(i2));
                        }
                    }
                }
                this.adapter.setList(arrayList2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void mGetData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        if ("".equals(this.c_id)) {
            str = NetworkRequestsURL.courselistApi;
        } else {
            str = NetworkRequestsURL.moreApi;
            ajaxParams.put("c_id", "" + this.c_id);
        }
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CourseListFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseListFragment.this.swip.setRefreshing(false);
                CourseListFragment.this.mDoData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                if (CourseListFragment.this.c_id.equals("")) {
                    SharePreferencesUtils.writeStrConfig("courseData_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CourseListFragment.this.getActivity()), s2, CourseListFragment.this.getActivity());
                } else {
                    SharePreferencesUtils.writeStrConfig("courseMoreData_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CourseListFragment.this.getActivity()) + StrPool.UNDERLINE + CourseListFragment.this.c_id, s2, CourseListFragment.this.getActivity());
                }
                CourseListFragment.this.mDoData();
                CourseListFragment.this.swip.setRefreshing(false);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("qiehuan")) {
            this.mHandler.sendEmptyMessageDelayed(4912, 200L);
        }
        if ("JumpZHibo".equals(str)) {
            this.mHandler.sendEmptyMessageDelayed(4912, 200L);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        this.swip.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.s3
            @Override // java.lang.Runnable
            public final void run() {
                this.f15980c.lambda$onLazyInitView$0();
            }
        });
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(4912, 200L);
    }
}
