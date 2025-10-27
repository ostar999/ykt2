package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.util.download.StorageUtil;
import com.aliyun.player.aliyunplayerbase.util.Formatter;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.CourseDownLoadActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.VidteachingBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class LessonCacheFragment extends BaseFragment {
    public BaseQuickAdapter<VidteachingBean.DataDTO, BaseViewHolder> adapter;
    public TextView contentTv;
    public RecyclerView reidvire;

    /* renamed from: com.psychiatrygarden.fragmenthome.LessonCacheFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<VidteachingBean.DataDTO, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(VidteachingBean.DataDTO dataDTO, View view) {
            Intent intent = new Intent(LessonCacheFragment.this.getActivity(), (Class<?>) CourseDownLoadActivity.class);
            intent.putExtra("series", "" + dataDTO.getSeries());
            intent.putExtra("vidteaching_id", "" + dataDTO.getId());
            intent.putExtra("chapter_id", "0");
            intent.putExtra("parent_id", "0");
            LessonCacheFragment.this.startActivity(intent);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, final VidteachingBean.DataDTO item) {
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.relids);
            ImageView imageView = (ImageView) holder.getView(R.id.selectTv);
            ImageView imageView2 = (ImageView) holder.getView(R.id.cover);
            ImageView imageView3 = (ImageView) holder.getView(R.id.croteimg);
            TextView textView = (TextView) holder.getView(R.id.title);
            imageView.setVisibility(8);
            imageView3.setVisibility(0);
            imageView3.setImageResource(SkinManager.getCurrentSkinType(((BaseFragment) LessonCacheFragment.this).mContext) == 1 ? R.drawable.icon_indicator_right_night : R.drawable.icon_indicator_right);
            Glide.with(((BaseFragment) LessonCacheFragment.this).mContext).load((Object) GlideUtils.generateUrl(item.getCover_img())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView2.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView2);
            textView.setText(item.getTitle());
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15715c.lambda$convert$0(item, view);
                }
            });
        }
    }

    private void calculationCache() {
        Iterator it = ((ArrayList) YkBManager.getInstance().mDownloadMediaLists).iterator();
        long size = 0;
        while (it.hasNext()) {
            size += ((AliyunDownloadMediaInfo) it.next()).getSize();
        }
        this.contentTv.setText(String.format(getResources().getString(R.string.alivc_player_video_cache_storage_tips), Formatter.getFileSizeDescription(size), Formatter.getFileSizeDescription(StorageUtil.getAvailableExternalMemorySize() * 1024)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getData$1() {
        final List<String> cids = ProjectApp.database.getVideoDownDao().getCids();
        if (cids == null || cids.size() <= 0) {
            return;
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.j8
            @Override // java.lang.Runnable
            public final void run() {
                this.f15688c.lambda$getData$0(cids);
            }
        });
    }

    public void getData() {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.i8
            @Override // java.lang.Runnable
            public final void run() {
                this.f15665c.lambda$getData$1();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_down_vids;
    }

    /* renamed from: getWebData, reason: merged with bridge method [inline-methods] */
    public void lambda$getData$0(List<String> ids) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("vidteaching_id", "" + new Gson().toJson(ids));
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.verdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.LessonCacheFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    VidteachingBean vidteachingBean = (VidteachingBean) new Gson().fromJson(s2, VidteachingBean.class);
                    if (vidteachingBean.getCode().equals("200")) {
                        LessonCacheFragment.this.adapter.setList(vidteachingBean.getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Context context;
        int i2;
        this.reidvire = (RecyclerView) holder.get(R.id.reidvire);
        this.contentTv = (TextView) holder.get(R.id.contentTv);
        this.reidvire.setLayoutManager(new LinearLayoutManager(getActivity()));
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.layout_curriculum_download_zreo);
        this.adapter = anonymousClass1;
        this.reidvire.setAdapter(anonymousClass1);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setGravity(17);
        linearLayout.setOrientation(1);
        ImageView imageView = new ImageView(getActivity());
        int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
        imageView.setImageResource(currentSkinType == 0 ? R.drawable.ic_empty_data_normal_day : R.drawable.ic_empty_data_normal_night);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtil.getPxByDp((Context) getActivity(), 100), ScreenUtil.getPxByDp((Context) getActivity(), 100));
        TextView textView = new TextView(getActivity());
        textView.setText("暂无数据");
        textView.setTextSize(14.0f);
        if (currentSkinType == 0) {
            context = getContext();
            i2 = R.color.forth_txt_color;
        } else {
            context = getContext();
            i2 = R.color.fourth_text_color_night;
        }
        textView.setTextColor(context.getColor(i2));
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.topMargin = ScreenUtil.getPxByDp((Context) getActivity(), 16);
        textView.setLayoutParams(layoutParams2);
        linearLayout.addView(imageView, layoutParams);
        linearLayout.addView(textView);
        this.adapter.setEmptyView(linearLayout);
        getData();
        calculationCache();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (!str.equals("cnm") || this.adapter == null) {
            return;
        }
        getData();
        calculationCache();
    }
}
