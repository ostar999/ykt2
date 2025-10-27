package com.psychiatrygarden.activity.courselist.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.courselist.CourseLivingDownNewActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumLiveBean;
import com.psychiatrygarden.activity.courselist.widget.CurriculumControlView;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.MemCenterBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CurriculumLiveFragment extends BaseFragment {
    public String activity_id;
    public LiveQuickAdapter adapter;
    public String course_id;
    public String cover;
    public String have_chapter;
    public String is_hide_teacher;
    public RecyclerView recycleId;
    public SmartRefreshLayout refreshLayout;
    public String title;
    public String type;
    public int page = 1;
    public ArrayList<String> set_meal_ids = new ArrayList<>();
    public String isPremision = "0";

    public class LiveQuickAdapter extends BaseQuickAdapter<CurriculumLiveBean.DataDTO, BaseViewHolder> {
        public LiveQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, CurriculumLiveBean.DataDTO curriculumLiveBean) {
            CurriculumLiveFragment curriculumLiveFragment = CurriculumLiveFragment.this;
            String str = curriculumLiveFragment.isPremision;
            String str2 = curriculumLiveFragment.course_id;
            FragmentActivity activity = curriculumLiveFragment.getActivity();
            final CurriculumLiveFragment curriculumLiveFragment2 = CurriculumLiveFragment.this;
            new CurriculumControlView(str, str2, activity, baseViewHolder, curriculumLiveBean, curriculumLiveFragment2.is_hide_teacher, new CurriculumControlView.CurricuIml() { // from class: com.psychiatrygarden.activity.courselist.fragment.l3
                @Override // com.psychiatrygarden.activity.courselist.widget.CurriculumControlView.CurricuIml
                public final void clickMethod() {
                    curriculumLiveFragment2.verifyPermissions();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLiveListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", "" + this.course_id);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        String str = NetworkRequestsURL.curriculumdetailUrl;
        if ("我的收藏".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyCollectionUrl;
        } else if ("我的笔记".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyNoteUrl;
        } else if ("我的评论".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyCommentUrl;
        } else {
            ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        }
        ajaxParams.put("type", "" + this.type);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumLiveFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CurriculumLiveFragment.this.refreshLayout.finishRefresh(false);
                CurriculumLiveFragment.this.refreshLayout.finishLoadMore(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                CurriculumLiveFragment.this.refreshLayout.finishRefresh();
                CurriculumLiveFragment.this.refreshLayout.finishLoadMore();
                try {
                    CurriculumLiveBean curriculumLiveBean = (CurriculumLiveBean) new Gson().fromJson(t2, CurriculumLiveBean.class);
                    if (curriculumLiveBean.getCode().equals("200")) {
                        CurriculumLiveFragment curriculumLiveFragment = CurriculumLiveFragment.this;
                        if (curriculumLiveFragment.page == 1) {
                            if (!TextUtils.isEmpty(curriculumLiveFragment.cover)) {
                                Iterator<CurriculumLiveBean.DataDTO> it = curriculumLiveBean.getData().iterator();
                                while (it.hasNext()) {
                                    it.next().setCover(CurriculumLiveFragment.this.cover);
                                }
                            }
                            CurriculumLiveFragment.this.adapter.setList(curriculumLiveBean.getData());
                            return;
                        }
                        if (curriculumLiveBean.getData().size() <= 0) {
                            CurriculumLiveFragment.this.refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            CurriculumLiveFragment.this.adapter.addData((Collection) curriculumLiveBean.getData());
                            CurriculumLiveFragment.this.refreshLayout.finishLoadMore();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
        getMemData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
        getLiveListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_curriculum_schedule_card;
    }

    public void getMemData() {
        final AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        ajaxParams.put("id", "" + this.course_id);
        ArrayList<String> arrayList = this.set_meal_ids;
        if (arrayList != null && arrayList.size() > 0) {
            ajaxParams.put("set_meal_id", new Gson().toJson(this.set_meal_ids));
        }
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.vipApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumLiveFragment.1
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
                String str;
                super.onSuccess((AnonymousClass1) s2);
                try {
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() != 200) {
                        ToastUtil.shortToast(CurriculumLiveFragment.this.getActivity(), memCenterBean.getMessage() + "");
                        return;
                    }
                    CurriculumLiveFragment.this.activity_id = memCenterBean.getData().getId() + "";
                    String str2 = "" + memCenterBean.getData().getPass();
                    if (TextUtils.isEmpty(ajaxParams.getParam().get("module_name"))) {
                        str = "1";
                    } else {
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CurriculumLiveFragment.this.getActivity());
                        String strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + ajaxParams.getParam().get("id") + strConfig + ajaxParams.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                        str2 = memCenterBean.getData().getPermission() + "";
                        str = strMD5Encode;
                    }
                    if (str2.equals(str)) {
                        CurriculumLiveFragment.this.isPremision = "1";
                    } else {
                        CurriculumLiveFragment.this.isPremision = "0";
                    }
                    CurriculumLiveFragment.this.getLiveListData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.set_meal_ids = getArguments().getStringArrayList("set_meal_id");
        this.is_hide_teacher = getArguments().getString("is_hide_teacher");
        this.have_chapter = getArguments().getString("have_chapter");
        this.course_id = getArguments().getString("course_id");
        this.type = getArguments().getString("type");
        this.activity_id = getArguments().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
        this.cover = getArguments().getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
        this.title = getArguments().getString("title");
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleId);
        this.recycleId = recyclerView;
        recyclerView.setPadding(0, 0, 0, 0);
        this.recycleId.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.j3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11972c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.k3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11979c.lambda$initViews$1(refreshLayout);
            }
        });
        this.refreshLayout.autoRefresh();
        LiveQuickAdapter liveQuickAdapter = new LiveQuickAdapter(R.layout.layout_curriculum_live);
        this.adapter = liveQuickAdapter;
        this.recycleId.setAdapter(liveQuickAdapter);
        this.adapter.setEmptyView(R.layout.adapter_empty_txt_view);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        LiveQuickAdapter liveQuickAdapter;
        super.onEventMainThread(str);
        if (!"downimg2".equals(str) || !isAdded() || getActivity() == null || (liveQuickAdapter = this.adapter) == null || liveQuickAdapter.getData() == null) {
            return;
        }
        if ("0".equals(this.isPremision)) {
            verifyPermissions();
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CourseLivingDownNewActivity.class);
        intent.putExtra("course_id", this.course_id);
        intent.putExtra("type", "" + this.type);
        intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        intent.putExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "" + this.cover);
        intent.putExtra("title", "" + this.title);
        intent.putExtra("is_hide_teacher", "" + this.is_hide_teacher);
        startActivity(intent);
    }

    public void verifyPermissions() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        ajaxParams.put("id", "" + this.course_id);
        ArrayList<String> arrayList = this.set_meal_ids;
        if (arrayList != null && arrayList.size() > 0) {
            ajaxParams.put("set_meal_id", new Gson().toJson(this.set_meal_ids));
        }
        MemInterface.getInstance().getMemData(getActivity(), ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumLiveFragment.3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public void mUShareListener() {
                ToastUtil.shortToast(CurriculumLiveFragment.this.getActivity(), "您已经有权限，请下拉刷新页面！");
            }
        });
    }

    public void onEventMainThread(EventBusMessage<String> str) {
        if ("refreshDuration".equals(str.getKey())) {
            this.refreshLayout.autoRefresh();
        }
    }
}
