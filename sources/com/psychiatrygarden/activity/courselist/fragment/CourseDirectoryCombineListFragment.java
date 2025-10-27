package com.psychiatrygarden.activity.courselist.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.CourseCombineDireListActivity;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.LearnListItemData;
import com.psychiatrygarden.bean.LiveInfo;
import com.psychiatrygarden.bean.PackageInfo;
import com.psychiatrygarden.bean.VideoInfo;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryCombineListFragment extends BaseFragment {
    private static final String TAG = "CourseDirectoryCombineL";
    private CourseCombineListAdapter adapter;
    private SmartRefreshLayout combineListRefresh;
    private String courseId = "";
    private CustomEmptyView emptyView;

    public class CourseCombineListAdapter extends BaseMultiItemQuickAdapter<LearnListItemData, BaseViewHolder> {
        public CourseCombineListAdapter() {
            addItemType(1, R.layout.item_course_directory_combine_list);
            addItemType(2, R.layout.item_course_directory_combine_list);
            addItemType(4, R.layout.item_course_directory_combine_list);
            addItemType(3, R.layout.item_course_directory_combine_list2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$layoutContainView$0(int i2, LearnListItemData learnListItemData, View view) {
            if (i2 == 1) {
                LiveInfo live_info = learnListItemData.getLive_info();
                CommonUtil.launchLiving(CourseDirectoryCombineListFragment.this.getActivity(), live_info.getUser_id(), live_info.getApp_id(), live_info.getApp_secret(), live_info.getRoom_id(), learnListItemData.getCourse_id(), live_info.getLive_id());
            } else {
                VideoInfo video_info = learnListItemData.getVideo_info();
                NavigationUtilKt.goToAliPlayerVideoPlayActivity(CourseDirectoryCombineListFragment.this.getActivity(), video_info.getId(), learnListItemData.getCourse_id(), video_info.getVid(), learnListItemData.getCover(), "1");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$layoutContainView$1(LearnListItemData learnListItemData, View view) {
            CourseDirectoryActivity.goToCourseDirectoryActivity(CourseDirectoryCombineListFragment.this.getActivity(), learnListItemData.getCourse_id(), learnListItemData.getType());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$layoutContainViewCombine$2(LearnListItemData learnListItemData, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
            if (learnListItemData.getIsOpen() == 0) {
                learnListItemData.setOpen(1);
                if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
            } else {
                learnListItemData.setOpen(0);
                if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.close2img_night);
                } else {
                    imageView.setImageResource(R.drawable.close2img);
                }
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$layoutContainViewCombine$3(LearnListItemData learnListItemData, ImageView imageView, BaseQuickAdapter baseQuickAdapter, View view) {
            if (learnListItemData.getIsOpen() == 0) {
                learnListItemData.setOpen(1);
                if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
            } else {
                learnListItemData.setOpen(0);
                if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.close2img_night);
                } else {
                    imageView.setImageResource(R.drawable.close2img);
                }
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$layoutContainViewCombine$4(View view) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$layoutContainViewCombine$5(LearnListItemData learnListItemData, View view) {
            CourseCombineDireListActivity.Companion companion = CourseCombineDireListActivity.INSTANCE;
            Context context = getContext();
            String course_id = learnListItemData.getCourse_id();
            Objects.requireNonNull(course_id);
            companion.startActivity(context, course_id);
        }

        public void layoutContainView(BaseViewHolder baseViewHolder, LearnListItemData itemData) throws NumberFormatException {
            TextView textView;
            StringBuilder sb;
            TextView textView2;
            int i2;
            int i3;
            int i4;
            final LearnListItemData learnListItemData;
            TextView textView3;
            LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.layoutProgressLearnCenter);
            LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.layoutLearnCenterPre);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.tvNo);
            TextView textView5 = (TextView) baseViewHolder.getView(R.id.tvLearnCenterItemName);
            SeekBar seekBar = (SeekBar) baseViewHolder.getView(R.id.learnSeekbar);
            TextView textView6 = (TextView) baseViewHolder.getView(R.id.tvProgress);
            TextView textView7 = (TextView) baseViewHolder.getView(R.id.tvSeeDay);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.itemLearnCenterAnimLive);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.itemLearnCenterPlay);
            TextView textView8 = (TextView) baseViewHolder.getView(R.id.tvLiveStatus);
            TextView textView9 = (TextView) baseViewHolder.getView(R.id.tvVideoName);
            TextView textView10 = (TextView) baseViewHolder.getView(R.id.tvTime);
            View view = baseViewHolder.getView(R.id.viewTag);
            TextView textView11 = (TextView) baseViewHolder.getView(R.id.tvNewGetFlag);
            final int i5 = Integer.parseInt(itemData.getType());
            textView5.setText(itemData.getTitle());
            int itemPosition = getItemPosition(itemData);
            if (itemPosition > 8) {
                sb = new StringBuilder();
                sb.append("");
                textView = textView9;
            } else {
                textView = textView9;
                sb = new StringBuilder();
                sb.append("0");
            }
            sb.append(itemPosition + 1);
            textView4.setText(sb.toString());
            boolean z2 = itemPosition == 0;
            boolean z3 = itemData.getLive_info() == null || TextUtils.isEmpty(itemData.getLive_info().getLive_id());
            boolean z4 = itemData.getVideo_info() == null || TextUtils.isEmpty(itemData.getVideo_info().getVid());
            if (z3 && z4) {
                linearLayout.setVisibility(8);
                linearLayout2.setVisibility(8);
                textView11.setVisibility(0);
                i4 = 8;
                textView3 = textView10;
                learnListItemData = itemData;
            } else {
                linearLayout.setVisibility(0);
                linearLayout2.setVisibility(0);
                textView11.setVisibility(8);
                String percent = itemData.getPercent();
                if (percent != null && !percent.isEmpty()) {
                    seekBar.setProgress(Integer.parseInt(percent.replace("%", "")));
                    textView6.setText(percent);
                }
                textView7.setText(itemData.getBefore_watch());
                seekBar.setFocusable(false);
                seekBar.setEnabled(false);
                seekBar.setClickable(false);
                int i6 = R.drawable.living_anim3_night;
                if (1 != i5 || z3) {
                    textView2 = textView;
                } else {
                    linearLayout.setVisibility(8);
                    imageView.setVisibility(0);
                    imageView.setImageResource(CourseDirectoryCombineListFragment.this.isNight() ? R.drawable.living_anim3_night : R.drawable.living_anim3_day);
                    ((Animatable) imageView.getDrawable()).start();
                    imageView.setVisibility(0);
                    imageView2.setVisibility(8);
                    textView8.setText("正在直播");
                    textView8.setTextColor(CourseDirectoryCombineListFragment.this.getThemeRedColor());
                    textView2 = textView;
                    textView2.setText(itemData.getLive_info().getTitle());
                }
                if (i5 == 1) {
                    i2 = 8;
                    linearLayout.setVisibility(8);
                    linearLayout2.setVisibility(8);
                    textView11.setVisibility(8);
                } else {
                    i2 = 8;
                }
                if (i5 != 2 || z3) {
                    i3 = 8;
                    if (i5 == 2) {
                        imageView.setVisibility(8);
                        imageView2.setVisibility(z2 ? 0 : 8);
                        imageView2.setImageResource(CourseDirectoryCombineListFragment.this.isNight() ? R.drawable.icon_learn_center_video_play_night : R.drawable.icon_learn_center_video_play_day);
                        textView8.setText(z2 ? "继续播放" : "上次学到");
                        CourseDirectoryCombineListFragment courseDirectoryCombineListFragment = CourseDirectoryCombineListFragment.this;
                        textView8.setTextColor(z2 ? courseDirectoryCombineListFragment.getThemeRedColor() : courseDirectoryCombineListFragment.getThemeSecondColor());
                        if (itemData.getVideo_info() != null) {
                            textView2.setText(itemData.getVideo_info().getTitle());
                        }
                    }
                } else {
                    linearLayout.setVisibility(i2);
                    imageView.setVisibility(0);
                    if (!CourseDirectoryCombineListFragment.this.isNight()) {
                        i6 = R.drawable.living_anim3_day;
                    }
                    imageView.setImageResource(i6);
                    ((Animatable) imageView.getDrawable()).start();
                    imageView.setVisibility(0);
                    imageView2.setVisibility(8);
                    textView8.setText("正在直播");
                    textView8.setTextColor(CourseDirectoryCombineListFragment.this.getThemeRedColor());
                    textView2.setText(itemData.getLive_info().getTitle());
                    i3 = 8;
                }
                i4 = i3;
                learnListItemData = itemData;
                linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.v
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f12051c.lambda$layoutContainView$0(i5, learnListItemData, view2);
                    }
                });
                textView3 = textView10;
            }
            textView3.setVisibility(i4);
            view.setVisibility(0);
            BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f12057c.lambda$layoutContainView$1(learnListItemData, view2);
                }
            });
        }

        public void layoutContainViewCombine(BaseViewHolder baseViewHolder, final LearnListItemData dataDTO) {
            StringBuilder sb;
            String str;
            TextView textView = (TextView) baseViewHolder.getView(R.id.titleTv);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tvTimeEnd);
            View view = baseViewHolder.getView(R.id.viewTag);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.tvNo);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.jidoortv);
            final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.imgopenbottom);
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recyall);
            recyclerView.setLayoutManager(new LinearLayoutManager(CourseDirectoryCombineListFragment.this.getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            int itemPosition = getItemPosition(dataDTO) + 1;
            if (itemPosition > 9) {
                sb = new StringBuilder();
                str = "";
            } else {
                sb = new StringBuilder();
                str = "0";
            }
            sb.append(str);
            sb.append(itemPosition);
            textView3.setText(sb.toString());
            textView.setText(CommonUtil.setSpanImg(((BaseFragment) CourseDirectoryCombineListFragment.this).mContext, SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1 ? R.drawable.moreimg_night_new : R.drawable.moreimg_new, 0, 1, 33, "&  " + dataDTO.getTitle()));
            List<PackageInfo> package_info = dataDTO.getPackage_info();
            final BaseQuickAdapter<PackageInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<PackageInfo, BaseViewHolder>(R.layout.layout_curriculum_all_child_item, package_info) { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment.CourseCombineListAdapter.1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return dataDTO.getIsOpen() == 0 ? Math.min(super.getItemCount(), 3) : super.getItemCount();
                }

                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, PackageInfo item) throws Resources.NotFoundException, NumberFormatException {
                    TextView textView5 = (TextView) helper.getView(R.id.title);
                    ImageView imageView2 = (ImageView) helper.getView(R.id.statusTv);
                    textView5.setCompoundDrawables(null, null, null, null);
                    imageView2.setVisibility(8);
                    if (((Animatable) imageView2.getDrawable()).isRunning()) {
                        ((Animatable) imageView2.getDrawable()).stop();
                    }
                    boolean zEquals = "1".equals(item.getType());
                    int i2 = R.drawable.allsimg_night_new;
                    if (zEquals && !TextUtils.isEmpty(item.getEnd_live_time()) && !TextUtils.isEmpty(item.getStart_live_time())) {
                        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                        long j2 = Long.parseLong(item.getStart_live_time());
                        long j3 = Long.parseLong(item.getEnd_live_time());
                        if (jCurrentTimeMillis > j2 && jCurrentTimeMillis < j3) {
                            imageView2.setVisibility(0);
                            imageView2.setImageResource(CourseDirectoryCombineListFragment.this.isNight() ? R.drawable.load_lv_anim_new_night : R.drawable.load_lv_anim_new);
                            ((Animatable) imageView2.getDrawable()).start();
                            textView5.setCompoundDrawables(null, null, null, null);
                        } else if (jCurrentTimeMillis < j2) {
                            CommonUtil.mDoDrawable(CourseDirectoryCombineListFragment.this.getActivity(), textView5, CourseDirectoryCombineListFragment.this.isNight() ? R.drawable.atmostimg_night_new : R.drawable.atmostimg_new, 0);
                        } else {
                            FragmentActivity activity = CourseDirectoryCombineListFragment.this.getActivity();
                            if (!CourseDirectoryCombineListFragment.this.isNight()) {
                                i2 = R.drawable.allsimg_new;
                            }
                            CommonUtil.mDoDrawable(activity, textView5, i2, 0);
                        }
                    } else if ("2".equals(item.getType())) {
                        FragmentActivity activity2 = CourseDirectoryCombineListFragment.this.getActivity();
                        if (!CourseDirectoryCombineListFragment.this.isNight()) {
                            i2 = R.drawable.allsimg_new;
                        }
                        CommonUtil.mDoDrawable(activity2, textView5, i2, 0);
                    } else if ("3".equals(item.getType())) {
                        CommonUtil.mDoDrawable(CourseDirectoryCombineListFragment.this.getActivity(), textView5, CourseDirectoryCombineListFragment.this.isNight() ? R.drawable.hj2img_night_new : R.drawable.hj2img_new, 0);
                    }
                    textView5.setText(Html.fromHtml(item.getTitle()));
                }
            };
            recyclerView.setAdapter(baseQuickAdapter);
            if (package_info == null || package_info.size() <= 3) {
                textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.z
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CourseDirectoryCombineListFragment.CourseCombineListAdapter.lambda$layoutContainViewCombine$4(view2);
                    }
                });
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                if (dataDTO.getIsOpen() == 0) {
                    if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.close2img_night);
                    } else {
                        imageView.setImageResource(R.drawable.close2img);
                    }
                } else if (SkinManager.getCurrentSkinType(CourseDirectoryCombineListFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.open2img_night);
                } else {
                    imageView.setImageResource(R.drawable.open2img);
                }
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.x
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f12063c.lambda$layoutContainViewCombine$2(dataDTO, imageView, baseQuickAdapter, view2);
                    }
                });
                textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.y
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f12070c.lambda$layoutContainViewCombine$3(dataDTO, imageView, baseQuickAdapter, view2);
                    }
                });
            }
            if (package_info != null) {
                textView4.setText(package_info.size() + "门课程");
            }
            textView2.setVisibility(8);
            view.setVisibility(0);
            BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f11913c.lambda$layoutContainViewCombine$5(dataDTO, view2);
                }
            });
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, LearnListItemData dataDTO) throws NumberFormatException {
            int customerItemViewType = BaseViewHolderUtilKt.getCustomerItemViewType(baseViewHolder);
            if (customerItemViewType != 1 && customerItemViewType != 2) {
                if (customerItemViewType == 3) {
                    layoutContainViewCombine(baseViewHolder, dataDTO);
                    return;
                } else if (customerItemViewType != 4) {
                    return;
                }
            }
            layoutContainView(baseViewHolder, dataDTO);
        }
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("type", "3");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCombine, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryCombineListFragment.this.adapter.setEmptyView(CourseDirectoryCombineListFragment.this.emptyView);
                CourseDirectoryCombineListFragment.this.combineListRefresh.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    CourseDirectoryCombineListFragment.this.combineListRefresh.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        CourseDirectoryCombineListFragment.this.adapter.setList(new ArrayList());
                        CourseDirectoryCombineListFragment.this.adapter.setEmptyView(CourseDirectoryCombineListFragment.this.emptyView);
                        CourseDirectoryCombineListFragment.this.emptyView.uploadEmptyViewResUi();
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<LearnListItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment.1.1
                    }.getType());
                    if (list.isEmpty()) {
                        CourseDirectoryCombineListFragment.this.adapter.setList(new ArrayList());
                        CourseDirectoryCombineListFragment.this.adapter.setEmptyView(CourseDirectoryCombineListFragment.this.emptyView);
                        CourseDirectoryCombineListFragment.this.emptyView.uploadEmptyViewResUi();
                    } else {
                        CourseDirectoryCombineListFragment.this.adapter.setList(list);
                    }
                    Log.d(CourseDirectoryCombineListFragment.TAG, "onSuccess日历直播: --" + list.size());
                } catch (Exception e2) {
                    Log.e("Error", e2.getMessage());
                    CourseDirectoryCombineListFragment.this.adapter.setList(new ArrayList());
                    CourseDirectoryCombineListFragment.this.adapter.setEmptyView(CourseDirectoryCombineListFragment.this.emptyView);
                    CourseDirectoryCombineListFragment.this.emptyView.setLoadFileResUi(CourseDirectoryCombineListFragment.this.getContext());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemeRedColor() {
        if (getContext() != null) {
            return getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_red_color}).getColor(0, -1);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemeSecondColor() {
        if (getContext() != null) {
            return getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.second_txt_color}).getColor(0, -1);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNight() {
        return SkinManager.getCurrentSkinType(getActivity()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        getListData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_combine_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.courseId = arguments.getString("course_id");
        }
        this.combineListRefresh = (SmartRefreshLayout) holder.get(R.id.combineListRefresh);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyclerCombineList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseCombineListAdapter courseCombineListAdapter = new CourseCombineListAdapter();
        this.adapter = courseCombineListAdapter;
        recyclerView.setAdapter(courseCombineListAdapter);
        this.combineListRefresh.setEnableLoadMore(false);
        CustomEmptyView customEmptyView = new CustomEmptyView(getContext(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12040c.lambda$initViews$0(view);
            }
        });
        this.adapter.setEmptyView(this.emptyView);
        this.combineListRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.u
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12045c.lambda$initViews$1(refreshLayout);
            }
        });
        getListData();
    }
}
