package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.CurriculumScheduleCardActivity;
import com.psychiatrygarden.activity.setting.ClassReminderAdp;
import com.psychiatrygarden.bean.ClassReminderBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ClassReminderNoticeAct extends BaseActivity {
    private ClassReminderAdp mAdapter;
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;

    public static /* synthetic */ int access$008(ClassReminderNoticeAct classReminderNoticeAct) {
        int i2 = classReminderNoticeAct.mPage;
        classReminderNoticeAct.mPage = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        ajaxParams.put(DatabaseManager.SIZE, com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getClassNotice, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.ClassReminderNoticeAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ClassReminderNoticeAct.this.mRefresh.finishRefresh();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                ClassReminderNoticeAct.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        ClassReminderBean classReminderBean = (ClassReminderBean) new Gson().fromJson(t2, ClassReminderBean.class);
                        if (classReminderBean.getData() != null) {
                            if (ClassReminderNoticeAct.this.mPage == 1) {
                                ClassReminderNoticeAct.this.mAdapter.setNewData(classReminderBean.getData());
                                if (classReminderBean.getData().size() < 15) {
                                    ClassReminderNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                }
                            } else if (classReminderBean.getData() != null && classReminderBean.getData().size() > 0) {
                                ClassReminderNoticeAct.this.mAdapter.addData((Collection) classReminderBean.getData());
                                if (classReminderBean.getData().size() < 15) {
                                    ClassReminderNoticeAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    ClassReminderNoticeAct.this.mRefresh.finishLoadMore();
                                }
                            }
                        }
                    } else {
                        ClassReminderNoticeAct.this.AlertToast(jSONObject.getString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) ClassReminderNoticeAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ClassReminderAdp classReminderAdp = new ClassReminderAdp();
        this.mAdapter = classReminderAdp;
        this.mRecyclerView.setAdapter(classReminderAdp);
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.setting.ClassReminderNoticeAct.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ClassReminderNoticeAct.access$008(ClassReminderNoticeAct.this);
                ClassReminderNoticeAct.this.getData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ClassReminderNoticeAct.this.mPage = 1;
                ClassReminderNoticeAct.this.getData();
            }
        });
        this.mAdapter.setOnItemActionLisenter(new ClassReminderAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.setting.ClassReminderNoticeAct.2
            @Override // com.psychiatrygarden.activity.setting.ClassReminderAdp.OnItemActionLisenter
            public void setBtnClickAction(int pos, ClassReminderBean.ClassReminderListBean item) {
                if (!item.getType().equals("1") && !item.getType().equals("3")) {
                    CommonUtil.launchLiveing(ClassReminderNoticeAct.this, item.getPolyv_user_id(), item.getPolyv_app_id(), item.getPolyv_app_secret(), item.getRoom_id());
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(ProjectApp.instance(), CurriculumScheduleCardActivity.class);
                intent.putExtra("course_id", item.getCourse_id());
                intent.putExtra("have_chapter", item.getHave_chapter());
                intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, item.getActivity_id());
                intent.putExtra("title", "直播课程表");
                intent.putExtra("type", "1");
                intent.setFlags(268435456);
                ProjectApp.instance().startActivity(intent);
            }
        });
        this.mRefresh.autoRefresh();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_common_notice_list);
        setTitle("上课提醒");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
