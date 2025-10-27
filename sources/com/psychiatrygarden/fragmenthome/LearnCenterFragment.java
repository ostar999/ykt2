package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.CourseCombineDireListActivity;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.HomeTabStatus;
import com.psychiatrygarden.bean.LearnListItemData;
import com.psychiatrygarden.bean.LiveInfo;
import com.psychiatrygarden.bean.PackageInfo;
import com.psychiatrygarden.bean.StartDayMsg;
import com.psychiatrygarden.bean.VideoInfo;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.LearnCenterFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class LearnCenterFragment extends BaseFragment {
    private static final String TAG = "LearnCenterFragment";
    private LearnCenterAdapter adapter;
    private CustomEmptyView emptyView;
    private RecyclerView recyclerview;
    private SmartRefreshLayout refresh;
    private TextView tvLearnDay;
    private TextView tvTodayLearnH;
    private TextView tvTodayLearnHHint;
    private TextView tvTodayLearnM;
    private TextView tvTotalLearnH;
    private TextView tvTotalLearnHHint;
    private TextView tvTotalLearnM;
    private int page = 1;
    private final int pageSize = 20;
    private boolean isUserVisible = false;
    private boolean isTabCourse = false;

    public class LearnCenterAdapter extends BaseMultiItemQuickAdapter<LearnListItemData, BaseViewHolder> {
        public LearnCenterAdapter() {
            addItemType(1, R.layout.item_learn_center);
            addItemType(2, R.layout.item_learn_center);
            addItemType(4, R.layout.item_learn_center);
            addItemType(3, R.layout.item_learn_center_combine);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterCombineContainView$2(LearnListItemData learnListItemData, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
            if (learnListItemData.getIsOpen() == 0) {
                learnListItemData.setOpen(1);
                if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
            } else {
                learnListItemData.setOpen(0);
                if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.close2img_night);
                } else {
                    imageView.setImageResource(R.drawable.close2img);
                }
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterCombineContainView$3(LearnListItemData learnListItemData, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
            if (learnListItemData.getIsOpen() == 0) {
                learnListItemData.setOpen(1);
                if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
            } else {
                learnListItemData.setOpen(0);
                if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.close2img_night);
                } else {
                    imageView.setImageResource(R.drawable.close2img);
                }
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$learnCenterCombineContainView$4(View view) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterCombineContainView$5(LearnListItemData learnListItemData, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            CourseCombineDireListActivity.Companion companion = CourseCombineDireListActivity.INSTANCE;
            Context context = getContext();
            String course_id = learnListItemData.getCourse_id();
            Objects.requireNonNull(course_id);
            companion.startActivity(context, course_id);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterCombineContainView$6(LearnListItemData learnListItemData, View view) {
            CourseCombineDireListActivity.Companion companion = CourseCombineDireListActivity.INSTANCE;
            Context context = getContext();
            String course_id = learnListItemData.getCourse_id();
            Objects.requireNonNull(course_id);
            companion.startActivity(context, course_id);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterContainView$0(boolean z2, LearnListItemData learnListItemData, View view) {
            if (!z2) {
                LiveInfo live_info = learnListItemData.getLive_info();
                CommonUtil.launchLiving(LearnCenterFragment.this.getActivity(), live_info.getUser_id(), live_info.getApp_id(), live_info.getApp_secret(), live_info.getRoom_id(), learnListItemData.getCourse_id(), live_info.getLive_id());
            } else {
                VideoInfo video_info = learnListItemData.getVideo_info();
                TreeNodeUtilKt.initWaitPlayList(video_info.getId(), video_info.getVid(), video_info.getTitle(), video_info.getCurrent_see(), "1");
                getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                NavigationUtilKt.goToAliPlayerVideoPlayActivity(LearnCenterFragment.this.getActivity(), video_info.getId(), learnListItemData.getCourse_id(), video_info.getVid(), learnListItemData.getCover(), "1");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$learnCenterContainView$1(LearnListItemData learnListItemData, View view) {
            CourseDirectoryActivity.goToCourseDirectoryActivity(LearnCenterFragment.this.getActivity(), learnListItemData.getCourse_id(), learnListItemData.getType(), learnListItemData.getTitle(), 0);
        }

        public void learnCenterCombineContainView(BaseViewHolder baseViewHolder, final LearnListItemData dataDTO) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.titleTv);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tvTimeEnd);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.jidoortv);
            final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.imgopenbottom);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recyall);
            recyclerView.setLayoutManager(new LinearLayoutManager(LearnCenterFragment.this.getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            textView.setText(CommonUtil.setSpanImg(((BaseFragment) LearnCenterFragment.this).mContext, SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1 ? R.drawable.moreimg_night_new : R.drawable.moreimg_new, 0, 1, 33, "&  " + dataDTO.getTitle()));
            List<PackageInfo> package_info = dataDTO.getPackage_info();
            final BaseQuickAdapter<PackageInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<PackageInfo, BaseViewHolder>(R.layout.layout_curriculum_all_child_item, package_info) { // from class: com.psychiatrygarden.fragmenthome.LearnCenterFragment.LearnCenterAdapter.1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return dataDTO.getIsOpen() == 0 ? Math.min(super.getItemCount(), 3) : super.getItemCount();
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, PackageInfo item) throws Resources.NotFoundException, NumberFormatException {
                    TextView textView4 = (TextView) helper.getView(R.id.title);
                    ImageView imageView2 = (ImageView) helper.getView(R.id.statusTv);
                    textView4.setCompoundDrawables(null, null, null, null);
                    imageView2.setVisibility(8);
                    if (((Animatable) imageView2.getDrawable()).isRunning()) {
                        ((Animatable) imageView2.getDrawable()).stop();
                    }
                    boolean zEquals = "1".equals(item.getType());
                    int i2 = R.drawable.allsimg_night_new;
                    if (zEquals && !TextUtils.isEmpty(item.getStart_live_time()) && !TextUtils.isEmpty(item.getEnd_live_time())) {
                        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                        long j2 = Long.parseLong(item.getStart_live_time());
                        long j3 = Long.parseLong(item.getEnd_live_time());
                        if (jCurrentTimeMillis > j2 && jCurrentTimeMillis < j3) {
                            imageView2.setVisibility(0);
                            imageView2.setImageResource(LearnCenterFragment.this.isNight() ? R.drawable.load_lv_anim_new_night : R.drawable.load_lv_anim_new);
                            ((Animatable) imageView2.getDrawable()).start();
                            textView4.setCompoundDrawables(null, null, null, null);
                        } else if (jCurrentTimeMillis < j2) {
                            CommonUtil.mDoDrawable(LearnCenterFragment.this.getActivity(), textView4, LearnCenterFragment.this.isNight() ? R.drawable.atmostimg_night_new : R.drawable.atmostimg_new, 0);
                        } else {
                            FragmentActivity activity = LearnCenterFragment.this.getActivity();
                            if (!LearnCenterFragment.this.isNight()) {
                                i2 = R.drawable.allsimg_new;
                            }
                            CommonUtil.mDoDrawable(activity, textView4, i2, 0);
                        }
                    } else if ("2".equals(item.getType())) {
                        FragmentActivity activity2 = LearnCenterFragment.this.getActivity();
                        if (!LearnCenterFragment.this.isNight()) {
                            i2 = R.drawable.allsimg_new;
                        }
                        CommonUtil.mDoDrawable(activity2, textView4, i2, 0);
                    } else if ("3".equals(item.getType())) {
                        CommonUtil.mDoDrawable(LearnCenterFragment.this.getActivity(), textView4, SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1 ? R.drawable.hj2img_night_new : R.drawable.hj2img_new, 0);
                    }
                    textView4.setText(Html.fromHtml(item.getTitle()));
                }
            };
            recyclerView.setAdapter(baseQuickAdapter);
            if (package_info == null || package_info.size() <= 3) {
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.d8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LearnCenterFragment.LearnCenterAdapter.lambda$learnCenterCombineContainView$4(view);
                    }
                });
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                if (dataDTO.getIsOpen() == 0) {
                    if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.close2img_night);
                    } else {
                        imageView.setImageResource(R.drawable.close2img);
                    }
                } else if (SkinManager.getCurrentSkinType(LearnCenterFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15463c.lambda$learnCenterCombineContainView$2(dataDTO, imageView, baseQuickAdapter, view);
                    }
                });
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15502c.lambda$learnCenterCombineContainView$3(dataDTO, imageView, baseQuickAdapter, view);
                    }
                });
            }
            if (package_info != null) {
                textView3.setText(package_info.size() + "门课程");
            }
            if (!TextUtils.isEmpty(dataDTO.getDeadline())) {
                if (Long.parseLong(dataDTO.getDeadline()) == -1) {
                    textView2.setText("有效期永久");
                } else {
                    textView2.setText("有效期" + CommonUtil.getDate(dataDTO.getDeadline()));
                }
            }
            baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.e8
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                    this.f15564c.lambda$learnCenterCombineContainView$5(dataDTO, baseQuickAdapter2, view, i2);
                }
            });
            BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15590c.lambda$learnCenterCombineContainView$6(dataDTO, view);
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x01c9  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x01cc  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void learnCenterContainView(com.chad.library.adapter.base.viewholder.BaseViewHolder r21, com.psychiatrygarden.bean.LearnListItemData r22) throws java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 612
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.LearnCenterFragment.LearnCenterAdapter.learnCenterContainView(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.bean.LearnListItemData):void");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, LearnListItemData dataDTO) throws NumberFormatException {
            int customerItemViewType = BaseViewHolderUtilKt.getCustomerItemViewType(baseViewHolder);
            if (customerItemViewType != 1 && customerItemViewType != 2) {
                if (customerItemViewType == 3) {
                    learnCenterCombineContainView(baseViewHolder, dataDTO);
                    return;
                } else if (customerItemViewType != 4) {
                    return;
                }
            }
            learnCenterContainView(baseViewHolder, dataDTO);
        }
    }

    private void getLearnList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.getMethod(this.mContext, NetworkRequestsURL.courseLearnCenterList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.LearnCenterFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LearnCenterFragment.this.refresh.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    String strOptString = jSONObject.optString("data");
                    String strOptString2 = jSONObject.optString("code");
                    if (LearnCenterFragment.this.page == 1) {
                        LearnCenterFragment.this.refresh.finishRefresh(true);
                    } else {
                        LearnCenterFragment.this.refresh.finishLoadMore(true);
                    }
                    if (!"200".equals(strOptString2)) {
                        LearnCenterFragment.this.refresh.finishRefresh(false);
                        LearnCenterFragment.this.toastOnUiThread(jSONObject.optString("message"));
                        return;
                    }
                    List list = (List) new Gson().fromJson(strOptString, new TypeToken<List<LearnListItemData>>() { // from class: com.psychiatrygarden.fragmenthome.LearnCenterFragment.2.1
                    }.getType());
                    int size = list.size();
                    if (LearnCenterFragment.this.page != 1) {
                        if (size > 0) {
                            LearnCenterFragment.this.adapter.addData((Collection) list);
                            return;
                        } else {
                            LearnCenterFragment.this.refresh.setNoMoreData(true);
                            return;
                        }
                    }
                    if (size == 0) {
                        LearnCenterFragment.this.adapter.setList(new ArrayList());
                        LearnCenterFragment.this.adapter.setEmptyView(LearnCenterFragment.this.emptyView);
                        LearnCenterFragment.this.emptyView.uploadEmptyViewResUi();
                        LearnCenterFragment.this.refresh.setNoMoreData(true);
                    }
                    if (size > 0) {
                        LearnCenterFragment.this.adapter.setList(list);
                    }
                } catch (Exception e2) {
                    LearnCenterFragment.this.refresh.finishRefresh(false);
                    Log.e("Error: Line-313:", e2.getMessage());
                }
            }
        });
    }

    private void getStartData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.courseLearnCenterDay, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.LearnCenterFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                LearnCenterFragment.this.toastOnUiThread(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    String strOptString = jSONObject.optString("data");
                    if ("200".equals(jSONObject.optString("code"))) {
                        LearnCenterFragment.this.updateLearnTime((StartDayMsg) new Gson().fromJson(strOptString, StartDayMsg.class));
                    } else {
                        LearnCenterFragment.this.toastOnUiThread(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    Log.e("Error: Line-313:", e2.getMessage());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemeRedColor() {
        return getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_red_color}).getColor(0, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemeSecondColor() {
        return getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.second_txt_color}).getColor(0, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNight() {
        return SkinManager.getCurrentSkinType(getActivity()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        if (UserConfig.isLogin()) {
            this.page = 1;
            getLearnList();
            getStartData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        if (UserConfig.isLogin()) {
            this.page++;
            getLearnList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (UserConfig.isLogin()) {
            this.page = 1;
            getLearnList();
        }
    }

    public static LearnCenterFragment newInstance() {
        Bundle bundle = new Bundle();
        LearnCenterFragment learnCenterFragment = new LearnCenterFragment();
        learnCenterFragment.setArguments(bundle);
        return learnCenterFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLearnTime(StartDayMsg dayMsg) throws NumberFormatException {
        if (dayMsg != null) {
            String today_duration = dayMsg.getToday_duration();
            String all_duration = dayMsg.getAll_duration();
            if (!TextUtils.isEmpty(today_duration)) {
                long j2 = Long.parseLong(today_duration);
                long j3 = j2 / 3600;
                this.tvTodayLearnH.setVisibility(j3 == 0 ? 8 : 0);
                this.tvTodayLearnHHint.setVisibility(j3 == 0 ? 8 : 0);
                this.tvTodayLearnH.setText(j3 + "");
                this.tvTodayLearnM.setText(((j2 % 3600) / 60) + "");
            }
            this.tvLearnDay.setText(dayMsg.getContinue_days());
            if (TextUtils.isEmpty(all_duration)) {
                return;
            }
            long j4 = Long.parseLong(all_duration);
            long j5 = j4 / 3600;
            this.tvTotalLearnH.setVisibility(j5 == 0 ? 8 : 0);
            this.tvTotalLearnHHint.setVisibility(j5 == 0 ? 8 : 0);
            this.tvTotalLearnH.setText(j5 + "");
            this.tvTotalLearnM.setText(((j4 % 3600) / 60) + "");
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_learn_center;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.tvTodayLearnH = (TextView) holder.get(R.id.tvTodayLearnH);
        this.tvTodayLearnHHint = (TextView) holder.get(R.id.tvTodayLearnHHint);
        this.tvTodayLearnM = (TextView) holder.get(R.id.tvTodayLearnM);
        this.tvLearnDay = (TextView) holder.get(R.id.tvLearnDay);
        this.tvTotalLearnH = (TextView) holder.get(R.id.tvTotalLearnH);
        this.tvTotalLearnHHint = (TextView) holder.get(R.id.tvTotalLearnHHint);
        this.tvTotalLearnM = (TextView) holder.get(R.id.tvTotalLearnM);
        this.recyclerview = (RecyclerView) holder.get(R.id.recyclerLearnCenter);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.learnCenterRefresh);
        this.refresh = smartRefreshLayout;
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.y7
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f16140c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.z7
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f16162c.lambda$initViews$1(refreshLayout);
            }
        });
        this.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        LearnCenterAdapter learnCenterAdapter = new LearnCenterAdapter();
        this.adapter = learnCenterAdapter;
        this.recyclerview.setAdapter(learnCenterAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.changeEmptyViewNewBgTwoColor();
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.a8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15438c.lambda$initViews$2(view);
            }
        });
        this.adapter.setEmptyView(this.emptyView);
        this.emptyView.uploadEmptyViewResUi();
        if (UserConfig.isLogin()) {
            this.page = 1;
            getStartData();
            getLearnList();
        }
    }

    @Subscribe
    public void onEventMainThread(HomeTabStatus homeTabStatus) {
        if ("homeTab".equals(homeTabStatus.mEvestr) || "homeTab3".equals(homeTabStatus.mEvestr)) {
            this.isTabCourse = homeTabStatus.position == 1;
        }
        if (this.isTabCourse && this.isUserVisible && UserConfig.isLogin()) {
            getStartData();
            this.page = 1;
            getLearnList();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserVisible = isVisibleToUser;
        if (isVisibleToUser && UserConfig.isLogin()) {
            getStartData();
            this.page = 1;
            getLearnList();
        }
    }
}
