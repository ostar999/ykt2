package com.psychiatrygarden.activity.courselist.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.source.VidSts;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CourseVideoListBean;
import com.psychiatrygarden.activity.courselist.bean.ViedeoStatusChangeBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.utils.DataRepository;
import com.psychiatrygarden.activity.courselist.viewmodel.CourseChapterViewModel;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.RefreshCourseStateEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.SkinGrakImagView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseNoNeChapterFragment extends BaseFragment {
    private BaseQuickAdapter<CourseVideoListBean.DataBean, BaseViewHolder> adapter;
    private String type;
    private CourseChapterViewModel viewModel;
    List<CourseVideoListBean.DataBean> data = new ArrayList();
    private AlertDialog netChangeDialog = null;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final RequestOptions options = new RequestOptions().transforms(new CenterCrop()).placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(false).dontAnimate();

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<CourseVideoListBean.DataBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0() {
            ActivityCompat.requestPermissions(CourseNoNeChapterFragment.this.getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TextView textView, ImageView imageView, CourseVideoListBean.DataBean dataBean, View view) {
            if (ContextCompat.checkSelfPermission(((BaseFragment) CourseNoNeChapterFragment.this).mContext, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
                new XPopup.Builder(CourseNoNeChapterFragment.this.getActivity()).asCustom(new RequestMediaPermissionPop(CourseNoNeChapterFragment.this.getActivity(), new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.n2
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f11996a.lambda$convert$0();
                    }
                })).show();
                return;
            }
            if (!CommonUtil.isNetworkConnected(((BaseFragment) CourseNoNeChapterFragment.this).mContext)) {
                ToastUtil.shortToast(ProjectApp.instance(), "请检查您的网络！");
                return;
            }
            if (!CommonUtil.isWifi(((BaseFragment) CourseNoNeChapterFragment.this).mContext) && !UserConfig.isCanDownloadBy4g(((BaseFragment) CourseNoNeChapterFragment.this).mContext)) {
                CourseNoNeChapterFragment.this.showOpenDownloadDialog();
                return;
            }
            textView.setVisibility(0);
            imageView.setVisibility(8);
            textView.setText("下载0%");
            dataBean.setStutas(1);
            VideoDownBean videoDownBean = new VideoDownBean();
            videoDownBean.obj_id = dataBean.getId();
            videoDownBean.setcId(CourseNoNeChapterFragment.this.getArguments().getString("vidteaching_id"));
            videoDownBean.setChapter_id("" + CourseNoNeChapterFragment.this.getArguments().getString("chapter_id"));
            videoDownBean.setParent_id("" + CourseNoNeChapterFragment.this.getArguments().getString("parent_id"));
            videoDownBean.setVid(dataBean.getVid());
            videoDownBean.setmTitle(dataBean.getTitle());
            videoDownBean.setThumb(dataBean.getThumb());
            CourseNoNeChapterFragment.this.viewModel.putData(videoDownBean);
            CourseNoNeChapterFragment.this.getResultVid(dataBean, 1);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder helper, final CourseVideoListBean.DataBean item) {
            SkinGrakImagView skinGrakImagView = (SkinGrakImagView) helper.getView(R.id.iv_store_img);
            TextView textView = (TextView) helper.getView(R.id.tv_course_time);
            final TextView textView2 = (TextView) helper.getView(R.id.mdownimgTxt);
            final ImageView imageView = (ImageView) helper.getView(R.id.mdownimg);
            TextView textView3 = (TextView) helper.getView(R.id.course_title);
            TextView textView4 = (TextView) helper.getView(R.id.tv_course_play_num);
            TextView textView5 = (TextView) helper.getView(R.id.tv_course_comment);
            GlideApp.with(((BaseFragment) CourseNoNeChapterFragment.this).mContext).load((Object) GlideUtils.generateUrl(item.getThumb())).apply((BaseRequestOptions<?>) CourseNoNeChapterFragment.this.options).into(skinGrakImagView);
            if (!item.getWatch_permission().equals("1")) {
                imageView.setVisibility(8);
            } else if (item.getStutas() < 1) {
                imageView.setVisibility(0);
                textView2.setVisibility(8);
            } else {
                imageView.setVisibility(8);
                textView2.setVisibility(0);
                int stutas = item.getStutas();
                if (stutas == 1) {
                    textView2.setText("下载" + item.getProgress() + "%");
                } else if (stutas == 2) {
                    textView2.setText("下载出错");
                } else if (stutas == 3) {
                    textView2.setText("等待下载");
                } else if (stutas == 4) {
                    textView2.setText("暂停下载" + item.getProgress() + "%");
                } else if (stutas == 5) {
                    textView2.setText("下载完成");
                }
            }
            textView3.setText(item.getTitle());
            textView4.setText(item.getSign_time());
            textView5.setText(String.format(Locale.CHINA, "%s评论", item.getComment_count()));
            if (item.getIs_see().equals("1")) {
                textView.setBackgroundResource(0);
                textView.setText("已打卡");
            } else {
                textView.setBackgroundResource(R.drawable.gry_deek_rond);
                textView.setText(item.getDuration_text());
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.o2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12002c.lambda$convert$1(textView2, imageView, item, view);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status;

        static {
            int[] iArr = new int[AliyunDownloadMediaInfo.Status.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status = iArr;
            try {
                iArr[AliyunDownloadMediaInfo.Status.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Prepare.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Wait.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Start.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Stop.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Complete.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Error.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        List<CourseVideoListBean.DataBean> list = this.data;
        if (list == null) {
            return;
        }
        goToSkin(list.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(List list) {
        if (!TextUtils.equals(this.type, "all") || this.data.isEmpty()) {
            this.data = list;
        }
        putData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$5(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, ObservableEmitter observableEmitter) throws Exception {
        if (ProjectApp.database.getVideoDownDao().getVideoDownBean(aliyunDownloadMediaInfo.getVid(), getArguments().getString("vidteaching_id"), getArguments().getString("chapter_id"), getArguments().getString("parent_id")) == null) {
            observableEmitter.onNext(Boolean.FALSE);
            return;
        }
        for (int i2 = 0; i2 < this.data.size(); i2++) {
            if (this.data.get(i2).getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                this.data.get(i2).setProgress(aliyunDownloadMediaInfo.getProgress());
                this.data.get(i2).setmSavePath(aliyunDownloadMediaInfo.getSavePath());
                switch (AnonymousClass3.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                    case 1:
                    case 2:
                        this.data.get(i2).setStutas(0);
                        break;
                    case 3:
                        this.data.get(i2).setStutas(3);
                        break;
                    case 4:
                        this.data.get(i2).setStutas(1);
                        break;
                    case 5:
                        this.data.get(i2).setStutas(4);
                        break;
                    case 6:
                        this.data.get(i2).setStutas(5);
                        break;
                    case 7:
                        this.data.get(i2).setStutas(2);
                        break;
                }
            }
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$6(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.adapter.setList(this.data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$7(final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (this.data.size() > 0) {
            this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.e2
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                    this.f11942a.lambda$mRefreshDownloadData$5(aliyunDownloadMediaInfo, observableEmitter);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.f2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f11948c.lambda$mRefreshDownloadData$6((Boolean) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshVideoStatus$8(ViedeoStatusChangeBean viedeoStatusChangeBean) {
        if (this.data.size() > 0) {
            for (int i2 = 0; i2 < this.data.size(); i2++) {
                if (("" + this.data.get(i2).getId()).equals(viedeoStatusChangeBean.getId()) && this.data.get(i2).getVid().equals(viedeoStatusChangeBean.getVid()) && getArguments().getString("vidteaching_id").equals(viedeoStatusChangeBean.getC_id())) {
                    this.data.get(i2).setNote(viedeoStatusChangeBean.getNote());
                    this.data.get(i2).setCollection(viedeoStatusChangeBean.getCollect());
                    this.data.get(i2).setIs_see(viedeoStatusChangeBean.getIssee());
                    this.data.get(i2).setWatch_permission(viedeoStatusChangeBean.getWatch_permission());
                }
            }
            this.adapter.setList(this.data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEventMainThread$12(List list, int i2) {
        this.viewModel.updateVideoStatus((CourseVideoListBean.DataBean) list.get(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$10(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            this.adapter.setList(this.data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putData$9(ObservableEmitter observableEmitter) throws Exception {
        List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo(getArguments().getString("vidteaching_id"), getArguments().getString("chapter_id"), getArguments().getString("parent_id"));
        if (videoDownLoadInfo != null && videoDownLoadInfo.size() > 0) {
            for (int i2 = 0; i2 < videoDownLoadInfo.size(); i2++) {
                for (int i3 = 0; i3 < this.data.size(); i3++) {
                    if (videoDownLoadInfo.get(i2).getVid().equals(this.data.get(i3).getVid())) {
                        this.data.get(i3).setStutas(videoDownLoadInfo.get(i2).getmStatus());
                        this.data.get(i3).setProgress(videoDownLoadInfo.get(i2).getmProgress());
                        this.data.get(i3).setmSavePath(videoDownLoadInfo.get(i2).getmSavePath());
                    }
                }
            }
        }
        observableEmitter.onNext(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$11(List list) {
        this.viewModel.setcDataBean(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$3(TextView textView, ImageView imageView, CourseVideoListBean.DataBean dataBean, DialogInterface dialogInterface, int i2) {
        this.netChangeDialog = null;
        dialogInterface.dismiss();
        textView.setVisibility(0);
        imageView.setVisibility(8);
        textView.setText("下载0%");
        dataBean.setStutas(1);
        VideoDownBean videoDownBean = new VideoDownBean();
        videoDownBean.obj_id = dataBean.getId();
        videoDownBean.setcId(getArguments().getString("vidteaching_id"));
        videoDownBean.setChapter_id("" + getArguments().getString("chapter_id"));
        videoDownBean.setParent_id("" + getArguments().getString("parent_id"));
        videoDownBean.setVid(dataBean.getVid());
        videoDownBean.setmTitle(dataBean.getTitle());
        videoDownBean.setThumb(dataBean.getThumb());
        this.viewModel.putData(videoDownBean);
        getResultVid(dataBean, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$4(DialogInterface dialogInterface, int i2) {
        this.netChangeDialog = null;
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$2() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    public static CourseNoNeChapterFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseNoNeChapterFragment courseNoNeChapterFragment = new CourseNoNeChapterFragment();
        courseNoNeChapterFragment.setArguments(bundle);
        return courseNoNeChapterFragment;
    }

    private void showNetChangeDialog(final CourseVideoListBean.DataBean item, final TextView mdownimgTxt, final ImageView mdownimg) {
        if (this.netChangeDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle("网络切换为4G");
            builder.setMessage("是否继续下载？");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.b2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    this.f11925c.lambda$showNetChangeDialog$3(mdownimgTxt, mdownimg, item, dialogInterface, i2);
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.c2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    this.f11933c.lambda$showNetChangeDialog$4(dialogInterface, i2);
                }
            });
            this.netChangeDialog = builder.create();
        }
        if (this.netChangeDialog.isShowing()) {
            return;
        }
        this.netChangeDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.fragment.g2
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f11953a.lambda$showOpenDownloadDialog$2();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_coursenone;
    }

    public void getResultVid(final CourseVideoListBean.DataBean item, final int type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", "" + item.getId());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseDataurl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseNoNeChapterFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CourseNoNeChapterFragment.this.isAdded()) {
                    CourseNoNeChapterFragment.this.hideProgressDialog();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (CourseNoNeChapterFragment.this.isAdded()) {
                    CourseNoNeChapterFragment.this.showProgressDialog();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("akId");
                        String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("akSecret");
                        String strOptString3 = new JSONObject(s2).optJSONObject("data").optString("st");
                        if (type == 1) {
                            VidSts vidSts = new VidSts();
                            vidSts.setQuality(QualityValue.QUALITY_FLUENT, false);
                            vidSts.setVid(item.getVid());
                            vidSts.setAccessKeyId(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString));
                            vidSts.setAccessKeySecret(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString2));
                            vidSts.setSecurityToken(DesUtil.decode(CommonParameter.DES_KEY_ALI, strOptString3));
                            ProjectApp.downloadManager.prepareDownload(vidSts);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (CourseNoNeChapterFragment.this.isAdded()) {
                    CourseNoNeChapterFragment.this.hideProgressDialog();
                }
            }
        });
    }

    public void goToSkin(CourseVideoListBean.DataBean item) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), AliPlayerVideoPlayActivity.class);
        intent.putExtra("obj_id", item.getId() + "");
        intent.putExtra("chapter_id", "" + getArguments().getString("vidteaching_id"));
        intent.putExtra("collection", item.getCollection());
        intent.putExtra("free_watch_time", Long.parseLong(item.getFree_watch_time()));
        intent.putExtra("note", item.getNote());
        intent.putExtra("watch_permission", item.getWatch_permission());
        intent.putExtra("expire_str", item.getExpire_str());
        intent.putExtra("is_see", item.getIs_see());
        intent.putExtra("module_type", 15);
        intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + item.getActivity_id());
        intent.putExtra("vid", item.getVid());
        intent.putExtra("commentEnum", DiscussStatus.LessonList);
        this.mContext.startActivity(intent);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.question_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.viewModel = (CourseChapterViewModel) ViewModelProviders.of(this).get(CourseChapterViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.type = arguments.getString("type");
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.adapter_course_chapter, this.data);
        this.adapter = anonymousClass1;
        recyclerView.setAdapter(anonymousClass1);
        this.adapter.setEmptyView(R.layout.layout_empty_view);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.h2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11958c.lambda$initViews$0(baseQuickAdapter, view, i2);
            }
        });
        this.viewModel.getChapterVideoInfo().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.i2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11964a.lambda$initViews$1((List) obj);
            }
        });
        setData();
        mRefreshDownloadData();
        mRefreshVideoStatus();
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.j2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11971a.lambda$mRefreshDownloadData$7((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    public void mRefreshVideoStatus() {
        YkBManager.getInstance().changeVideoStatus().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.m2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11990a.lambda$mRefreshVideoStatus$8((ViedeoStatusChangeBean) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.viewModel.onCleared();
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshCourseStateEvent event) {
        final List<CourseVideoListBean.DataBean> data = this.adapter.getData();
        String vid = event.getVid();
        final int i2 = 0;
        while (true) {
            if (i2 >= data.size()) {
                i2 = -1;
                break;
            }
            if (TextUtils.equals(data.get(i2).getId() + "", vid)) {
                if (event.isRefreshCollection()) {
                    data.get(i2).setCollection(event.isCollect() ? "1" : "0");
                } else if (event.isRefreshDaKa()) {
                    data.get(i2).setIs_see(event.isDaKa() ? "1" : "0");
                }
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            new Thread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.a2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11917c.lambda$onEventMainThread$12(data, i2);
                }
            }).start();
        }
    }

    public void putData() {
        this.compositeDisposable.add(Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.k2
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f11978a.lambda$putData$9(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.l2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f11984c.lambda$putData$10((Boolean) obj);
            }
        }));
    }

    public void setData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("vidteaching_id", "" + getArguments().getString("vidteaching_id"));
        ajaxParams.put("type", "" + getArguments().getString("type"));
        if (getArguments().getString("series").equals("1")) {
            ajaxParams.put("chapter_id", "" + getArguments().getString("parent_id"));
        } else {
            ajaxParams.put("chapter_id", "" + getArguments().getString("chapter_id"));
        }
        new DataRepository().getChapterVideo(ajaxParams).observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.d2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f11937a.lambda$setData$11((List) obj);
            }
        });
    }
}
