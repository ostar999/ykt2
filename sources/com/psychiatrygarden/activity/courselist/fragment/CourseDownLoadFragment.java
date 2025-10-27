package com.psychiatrygarden.activity.courselist.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.source.VidSts;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.courselist.utils.DataRepository;
import com.psychiatrygarden.activity.courselist.viewmodel.CourseChapterViewModel;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.TipPopWindow;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDownLoadFragment extends BaseFragment {
    private BaseQuickAdapter<VideoDownBean, BaseViewHolder> adapter;
    private TextView allselet;
    private TextView deleteTxt;
    private Disposable disposable;
    private TextView okdelete;
    private CourseChapterViewModel viewModel;
    private LinearLayout voidezuo;
    private AlertDialog netChangeDialog = null;
    private List<VideoDownBean> dataList = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDownLoadFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i2;
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (CourseDownLoadFragment.this.dataList.size() > 0) {
                    i2 = 0;
                    for (int i3 = 0; i3 < CourseDownLoadFragment.this.dataList.size(); i3++) {
                        if (((VideoDownBean) CourseDownLoadFragment.this.dataList.get(i3)).isSelect()) {
                            i2++;
                        }
                    }
                } else {
                    i2 = 0;
                }
                if (i2 == 0) {
                    CourseDownLoadFragment.this.okdelete.setText("确认删除");
                    CourseDownLoadFragment.this.allselet.setText("全选");
                    CourseDownLoadFragment.this.okdelete.setTextColor(ContextCompat.getColor(((BaseFragment) CourseDownLoadFragment.this).mContext, R.color.pingeg));
                } else {
                    if (i2 == CourseDownLoadFragment.this.dataList.size()) {
                        CourseDownLoadFragment.this.allselet.setText("取消全选");
                    } else {
                        CourseDownLoadFragment.this.allselet.setText("全选");
                    }
                    CourseDownLoadFragment.this.okdelete.setTextColor(ContextCompat.getColor(((BaseFragment) CourseDownLoadFragment.this).mContext, R.color.white));
                    CourseDownLoadFragment.this.okdelete.setText(String.format(Locale.CHINA, "确认删除（ %d ）", Integer.valueOf(i2)));
                }
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDownLoadFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<VideoDownBean, BaseViewHolder> {
        public AnonymousClass2(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(VideoDownBean videoDownBean, View view) {
            if (videoDownBean.getmStatus() == 2) {
                new XPopup.Builder(CourseDownLoadFragment.this.getActivity()).asCustom(new TipPopWindow(CourseDownLoadFragment.this.getActivity())).toggle();
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder helper, VideoDownBean item, @NonNull List payloads) throws Resources.NotFoundException {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder helper, final VideoDownBean item) throws Resources.NotFoundException {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_store_img);
            ImageView imageView2 = (ImageView) helper.getView(R.id.mdownimg);
            TextView textView = (TextView) helper.getView(R.id.course_title);
            TextView textView2 = (TextView) helper.getView(R.id.tv_course_status);
            TextView textView3 = (TextView) helper.getView(R.id.tv_course_mb);
            ImageView imageView3 = (ImageView) helper.getView(R.id.check_video);
            imageView3.setVisibility(item.isAllSelect ? 0 : 8);
            imageView3.setImageResource(!item.isSelect() ? R.drawable.uncheckvideo : SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0 ? R.drawable.selectedvideo : R.drawable.icon_options_select_ok_lack_night);
            textView.setText(item.getmTitle());
            Glide.with(((BaseFragment) CourseDownLoadFragment.this).mContext).load((Object) GlideUtils.generateUrl(CommonUtil.getVideoMd5key(item.getThumb().replaceAll("\\?auth_key=.*", "")))).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).transform(new RoundedCorners(4)).into(imageView);
            textView2.setCompoundDrawables(null, null, null, null);
            if (item.getmStatus() == 5) {
                item.setmStatus(5);
                textView2.setText("已完成");
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.playnewimg);
                } else {
                    imageView2.setImageResource(R.drawable.playnewimg_night);
                }
            } else if (item.getmStatus() == 3) {
                if (CourseDownLoadFragment.this.isHasWaite(item.getVid() + "")) {
                    item.setmStatus(3);
                    textView2.setText("等待下载");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.waitimg);
                    } else {
                        imageView2.setImageResource(R.drawable.waitimg_night);
                    }
                } else {
                    item.setmStatus(4);
                    textView2.setText("暂停");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.pimg);
                    } else {
                        imageView2.setImageResource(R.drawable.pimg_night);
                    }
                }
            } else if (item.getmStatus() == 1) {
                if (item.getmProgress() == 100) {
                    item.setmStatus(5);
                    textView2.setText("已完成");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.playnewimg);
                    } else {
                        imageView2.setImageResource(R.drawable.playnewimg_night);
                    }
                } else {
                    item.setmStatus(1);
                    textView2.setText("正在下载");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.dimg);
                    } else {
                        imageView2.setImageResource(R.drawable.dimg_night);
                    }
                }
            } else if (item.getmStatus() == 4) {
                item.setmStatus(4);
                textView2.setText("暂停");
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.pimg);
                } else {
                    imageView2.setImageResource(R.drawable.pimg_night);
                }
            } else if (item.getmStatus() == 2) {
                item.setmStatus(2);
                CommonUtil.mDoDrawable(CourseDownLoadFragment.this.getActivity(), textView2, R.drawable.wrong_layer_shape, 2);
                if (CommonUtil.isNetworkConnected(((BaseFragment) CourseDownLoadFragment.this).mContext)) {
                    textView2.setText("下载出错");
                } else {
                    textView2.setText("网络出错");
                }
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.wdimg);
                } else {
                    imageView2.setImageResource(R.drawable.wdimg);
                }
            }
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.z1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12082c.lambda$convert$0(item, view);
                }
            });
            long j2 = (item.getmSize() / 1024) / 1024;
            int i2 = (int) ((j2 * Float.parseFloat(item.getmProgress() + "")) / 100.0f);
            if (item.getmStatus() == 5) {
                textView3.setText(String.format(Locale.CHINA, "%d MB / %d MB", Long.valueOf(j2), Long.valueOf(j2)));
            } else {
                textView3.setText(String.format(Locale.CHINA, "%dMB / %dMB", Integer.valueOf(i2), Long.valueOf(j2)));
            }
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NonNull BaseViewHolder helper, VideoDownBean item, @NonNull List<?> payloads) throws Resources.NotFoundException {
            String str;
            if (payloads == null || payloads.size() <= 0) {
                return;
            }
            int iIntValue = ((Integer) payloads.get(0)).intValue();
            if (iIntValue != 0) {
                if (iIntValue == 999) {
                    ImageView imageView = (ImageView) helper.getView(R.id.check_video);
                    imageView.setVisibility(0);
                    imageView.setImageResource(!item.isSelect() ? R.drawable.uncheckvideo : SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0 ? R.drawable.selectedvideo : R.drawable.icon_options_select_ok_lack_night);
                    return;
                }
                return;
            }
            ImageView imageView2 = (ImageView) helper.getView(R.id.mdownimg);
            TextView textView = (TextView) helper.getView(R.id.tv_course_status);
            TextView textView2 = (TextView) helper.getView(R.id.tv_course_mb);
            textView.setCompoundDrawables(null, null, null, null);
            if (item.getmStatus() == 5) {
                item.setmStatus(5);
                textView.setText("已完成");
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.playnewimg);
                } else {
                    imageView2.setImageResource(R.drawable.playnewimg_night);
                }
            } else if (item.getmStatus() == 3) {
                if (CourseDownLoadFragment.this.isHasWaite(item.getVid() + "")) {
                    item.setmStatus(3);
                    textView.setText("等待下载");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.waitimg);
                    } else {
                        imageView2.setImageResource(R.drawable.waitimg_night);
                    }
                } else {
                    item.setmStatus(4);
                    textView.setText("暂停");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.pimg);
                    } else {
                        imageView2.setImageResource(R.drawable.pimg_night);
                    }
                }
            } else if (item.getmStatus() == 1) {
                if (item.getmProgress() == 100) {
                    item.setmStatus(5);
                    textView.setText("已完成");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.playnewimg);
                    } else {
                        imageView2.setImageResource(R.drawable.playnewimg_night);
                    }
                } else {
                    item.setmStatus(1);
                    textView.setText("正在下载");
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                        imageView2.setImageResource(R.drawable.dimg);
                    } else {
                        imageView2.setImageResource(R.drawable.dimg_night);
                    }
                }
            } else if (item.getmStatus() == 4) {
                item.setmStatus(4);
                textView.setText("暂停");
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.pimg);
                } else {
                    imageView2.setImageResource(R.drawable.pimg_night);
                }
            } else if (item.getmStatus() == 2) {
                item.setmStatus(2);
                CommonUtil.mDoDrawable(CourseDownLoadFragment.this.getActivity(), textView, R.drawable.wrong_layer_shape, 2);
                if (CommonUtil.isNetworkConnected(((BaseFragment) CourseDownLoadFragment.this).mContext)) {
                    textView.setText("下载出错");
                } else {
                    textView.setText("网络出错");
                }
                if (SkinManager.getCurrentSkinType(((BaseFragment) CourseDownLoadFragment.this).mContext) == 0) {
                    imageView2.setImageResource(R.drawable.wdimg);
                } else {
                    imageView2.setImageResource(R.drawable.wdimg);
                }
            }
            long j2 = (Long.parseLong(item.getmSize() + "") / 1024) / 1024;
            int i2 = (int) ((j2 * Float.parseFloat(item.getmProgress() + "")) / 100.0f);
            if (item.getmStatus() == 5) {
                str = String.format(Locale.CHINA, "%dMB / %dMB", Long.valueOf(j2), Long.valueOf(j2));
            } else {
                str = String.format(Locale.CHINA, "%dMB / %dMB", Integer.valueOf(i2), Long.valueOf(j2));
            }
            textView2.setText(str);
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDownLoadFragment$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
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

    private void changeDataStatus(List<VideoDownBean> videoDownBeans) {
        if (videoDownBeans != null && videoDownBeans.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                arrayList.add(this.adapter.getData().get(i2).getVid());
            }
            for (int i3 = 0; i3 < videoDownBeans.size(); i3++) {
                if (arrayList.contains(videoDownBeans.get(i3).getVid())) {
                    LogUtils.e("addData", "没有新的数据");
                } else {
                    this.adapter.addData((BaseQuickAdapter<VideoDownBean, BaseViewHolder>) videoDownBeans.get(i3));
                    LogUtils.e("addData", "添加新的数据");
                }
            }
        }
        if (YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0) {
            return;
        }
        List<AliyunDownloadMediaInfo> list = YkBManager.getInstance().mDownloadMediaLists;
        if (videoDownBeans == null || videoDownBeans.size() <= 0) {
            return;
        }
        for (int i4 = 0; i4 < list.size(); i4++) {
            for (int i5 = 0; i5 < videoDownBeans.size(); i5++) {
                if (list.get(i4).getVid().equals(videoDownBeans.get(i5).getVid())) {
                    videoDownBeans.get(i5).setmProgress(list.get(i4).getProgress());
                    switch (AnonymousClass4.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[list.get(i4).getStatus().ordinal()]) {
                        case 1:
                        case 2:
                            videoDownBeans.get(i5).setmStatus(0);
                            break;
                        case 3:
                            videoDownBeans.get(i5).setmStatus(3);
                            break;
                        case 4:
                            videoDownBeans.get(i5).setmStatus(1);
                            break;
                        case 5:
                            videoDownBeans.get(i5).setmStatus(4);
                            break;
                        case 6:
                            videoDownBeans.get(i5).setmStatus(5);
                            break;
                        case 7:
                            videoDownBeans.get(i5).setmStatus(2);
                            break;
                    }
                    this.adapter.notifyItemChanged(i4, 0);
                }
            }
        }
    }

    private boolean hasAdded(AliyunDownloadMediaInfo info, List<AliyunDownloadMediaInfo> listsItem) {
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : listsItem) {
            if (info.getFormat().equals(aliyunDownloadMediaInfo.getFormat()) && info.getQuality().equals(aliyunDownloadMediaInfo.getQuality()) && info.getVid().equals(aliyunDownloadMediaInfo.getVid()) && info.isEncripted() == aliyunDownloadMediaInfo.isEncripted()) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllCheck() {
        Iterator<VideoDownBean> it = this.dataList.iterator();
        while (it.hasNext()) {
            if (!it.next().isSelect()) {
                return false;
            }
        }
        return true;
    }

    private void itemClick(int position) {
        if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(getActivity()).asCustom(new RequestMediaPermissionPop(getActivity(), new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.p1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12012a.lambda$itemClick$15();
                }
            })).show();
            return;
        }
        try {
            VideoDownBean item = this.adapter.getItem(position);
            if (item.isAllSelect()) {
                item.setSelect(item.isSelect() ? false : true);
                this.adapter.notifyItemChanged(position, 999);
                this.mHandler.sendEmptyMessage(100);
                return;
            }
            if (item.getmStatus() != 5 && item.getmProgress() != 100) {
                if (item.getmStatus() == 1) {
                    isHaveData(item.getVid() + "", 1);
                    return;
                }
                isHaveData(item.getVid() + "", 2);
                return;
            }
            if (item.getmSavePath().equals("")) {
                NewToast.showShort(this.mContext, "当前视频不存在，请删除后重新下载", 0).show();
                return;
            }
            Intent intent = new Intent();
            intent.setClass(getActivity(), AliPlayerVideoPlayActivity.class);
            intent.putExtra("obj_id", item.obj_id + "");
            intent.putExtra("chapter_id", item.getChapter_id());
            intent.putExtra("collection", "");
            intent.putExtra("note", "");
            intent.putExtra("watch_permission", "");
            intent.putExtra("expire_str", "");
            intent.putExtra("is_see", "");
            intent.putExtra("module_type", 15);
            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "");
            intent.putExtra("vid", item.getVid());
            intent.putExtra("commentEnum", DiscussStatus.LessonList);
            getActivity().startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteData$10(String str, ObservableEmitter observableEmitter) throws Exception {
        ProjectApp.database.getVideoDownDao().deleteData(str);
        observableEmitter.onNext("删除成功");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteData$11(String str) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        getActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        itemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (view.getId() == R.id.check_video) {
            ImageView imageView = (ImageView) view;
            VideoDownBean videoDownBean = (VideoDownBean) baseQuickAdapter.getItem(i2);
            videoDownBean.setSelect(!videoDownBean.isSelect());
            imageView.setImageResource(!videoDownBean.isSelect() ? R.drawable.uncheckvideo : SkinManager.getCurrentSkinType(this.mContext) == 0 ? R.drawable.selectedvideo : R.drawable.icon_options_select_ok_lack_night);
            baseQuickAdapter.notifyItemChanged(i2, 999);
            this.mHandler.sendEmptyMessage(100);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(List list) {
        if (this.adapter.getData().size() > 0) {
            changeDataStatus(list);
            return;
        }
        List<VideoDownBean> listChangeStatus = changeStatus(list);
        this.dataList = listChangeStatus;
        Collections.sort(listChangeStatus);
        this.adapter.setList(this.dataList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        if (this.dataList.size() > 0) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2).getVid().equals(aliyunDownloadMediaInfo.getVid())) {
                    this.dataList.get(i2).setmProgress(aliyunDownloadMediaInfo.getProgress());
                    switch (AnonymousClass4.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                        case 1:
                        case 2:
                            this.dataList.get(i2).setmStatus(0);
                            break;
                        case 3:
                            this.dataList.get(i2).setmStatus(3);
                            break;
                        case 4:
                            this.dataList.get(i2).setmStatus(1);
                            break;
                        case 5:
                            this.dataList.get(i2).setmStatus(4);
                            break;
                        case 6:
                            this.dataList.get(i2).setmStatus(5);
                            break;
                        case 7:
                            this.dataList.get(i2).setmStatus(2);
                            break;
                    }
                    this.adapter.notifyItemChanged(i2, 0);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(View view) {
        if (this.dataList.size() > 0) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2).isAllSelect()) {
                    this.dataList.get(i2).setAllSelect(false);
                    this.voidezuo.setVisibility(8);
                    this.deleteTxt.setText("删除");
                } else {
                    this.dataList.get(i2).setAllSelect(true);
                    this.voidezuo.setVisibility(0);
                    this.deleteTxt.setText("取消");
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(List list) {
        this.viewModel.setcDataBean(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(List list) {
        this.viewModel.setCcDataBean(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$8(View view) {
        if (this.dataList.size() > 0) {
            Iterator<VideoDownBean> it = this.dataList.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (it.next().isSelect()) {
                    i2++;
                }
            }
            if (i2 == 0) {
                return;
            }
            if (i2 < this.dataList.size()) {
                ListIterator<VideoDownBean> listIterator = this.dataList.listIterator();
                while (listIterator.hasNext()) {
                    VideoDownBean next = listIterator.next();
                    if (next.isSelect()) {
                        if (TextUtils.isEmpty(next.getmSavePath()) && TextUtils.isEmpty(next.getmFormat()) && TextUtils.isEmpty(next.getmQuality())) {
                            deleteData(next.getVid());
                        } else {
                            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = new AliyunDownloadMediaInfo();
                            aliyunDownloadMediaInfo.setEncripted(Integer.parseInt("" + next.getIsEncripted()));
                            aliyunDownloadMediaInfo.setFormat(next.getmFormat());
                            aliyunDownloadMediaInfo.setVid(next.getVid());
                            aliyunDownloadMediaInfo.setQuality(next.getmQuality());
                            aliyunDownloadMediaInfo.setSavePath(next.getmSavePath());
                            deleteData(next.getVid());
                            ProjectApp.downloadManager.deleteFile(aliyunDownloadMediaInfo);
                        }
                        listIterator.remove();
                    } else {
                        next.setAllSelect(false);
                        next.setSelect(false);
                    }
                }
                this.voidezuo.setVisibility(8);
                this.deleteTxt.setText("删除");
            } else if (i2 == this.dataList.size()) {
                for (int i3 = 0; i3 < i2; i3++) {
                    try {
                        if (TextUtils.isEmpty(this.dataList.get(i3).getmSavePath()) && TextUtils.isEmpty(this.dataList.get(i3).getmFormat()) && TextUtils.isEmpty(this.dataList.get(i3).getmQuality())) {
                            deleteData(this.dataList.get(i3).getVid());
                        } else {
                            AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 = new AliyunDownloadMediaInfo();
                            aliyunDownloadMediaInfo2.setEncripted(Integer.parseInt("" + this.dataList.get(i3).getIsEncripted()));
                            aliyunDownloadMediaInfo2.setFormat(this.dataList.get(i3).getmFormat());
                            aliyunDownloadMediaInfo2.setVid(this.dataList.get(i3).getVid());
                            aliyunDownloadMediaInfo2.setQuality(this.dataList.get(i3).getmQuality());
                            aliyunDownloadMediaInfo2.setSavePath(this.dataList.get(i3).getmSavePath());
                            deleteData(this.dataList.get(i3).getVid());
                            ProjectApp.downloadManager.deleteFile(aliyunDownloadMediaInfo2);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                this.deleteTxt.setVisibility(8);
                this.dataList.clear();
                this.voidezuo.setVisibility(8);
            }
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put("vidteaching_id", "" + getArguments().getString("vidteaching_id"));
            ajaxParams.put("type", "all");
            ajaxParams.put("chapter_id", "" + getArguments().getString("chapter_id"));
            new DataRepository().getChapterVideo(ajaxParams).observe(getActivity(), new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.r1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f12028a.lambda$initViews$6((List) obj);
                }
            });
            new DataRepository().getChapterInfo(ajaxParams).observe(getActivity(), new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.s1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f12036a.lambda$initViews$7((List) obj);
                }
            });
            this.adapter.setList(this.dataList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$9(View view) {
        if (this.dataList.size() > 0) {
            if (!isAllCheck()) {
                for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                    this.dataList.get(i2).setSelect(true);
                    this.okdelete.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
                    this.okdelete.setText(String.format(Locale.CHINA, "确认删除（%d）", Integer.valueOf(this.dataList.size())));
                    this.allselet.setText("取消全选");
                    this.adapter.notifyItemChanged(i2, 999);
                }
                return;
            }
            for (int i3 = 0; i3 < this.dataList.size(); i3++) {
                this.dataList.get(i3).setSelect(false);
                this.okdelete.setText("确认删除");
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    this.okdelete.setTextColor(ContextCompat.getColor(this.mContext, R.color.pingeg));
                } else {
                    this.okdelete.setTextColor(ContextCompat.getColor(this.mContext, R.color.pingeg_night));
                }
                this.allselet.setText("全选");
                this.adapter.notifyItemChanged(i3, 999);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$itemClick$15() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$13(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, DialogInterface dialogInterface, int i2) {
        this.netChangeDialog = null;
        dialogInterface.dismiss();
        ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$14(DialogInterface dialogInterface, int i2) {
        this.netChangeDialog = null;
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$12() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    public static CourseDownLoadFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseDownLoadFragment courseDownLoadFragment = new CourseDownLoadFragment();
        courseDownLoadFragment.setArguments(bundle);
        return courseDownLoadFragment;
    }

    private void showNetChangeDialog(final AliyunDownloadMediaInfo info) {
        if (this.netChangeDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle("网络切换为4G");
            builder.setMessage("是否继续下载？");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.n1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    this.f11994c.lambda$showNetChangeDialog$13(info, dialogInterface, i2);
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.o1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    this.f12001c.lambda$showNetChangeDialog$14(dialogInterface, i2);
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
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.fragment.m1
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f11989a.lambda$showOpenDownloadDialog$12();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    public List<VideoDownBean> changeStatus(List<VideoDownBean> videoDownBeans) {
        if (YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            List<AliyunDownloadMediaInfo> list = YkBManager.getInstance().mDownloadMediaLists;
            if (videoDownBeans != null && videoDownBeans.size() > 0) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= videoDownBeans.size()) {
                            break;
                        }
                        if (list.get(i2).getVid().equals(videoDownBeans.get(i3).getVid())) {
                            videoDownBeans.get(i3).setmProgress(list.get(i2).getProgress());
                            switch (AnonymousClass4.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[list.get(i2).getStatus().ordinal()]) {
                                case 1:
                                case 2:
                                    videoDownBeans.get(i3).setmStatus(0);
                                    break;
                                case 3:
                                    videoDownBeans.get(i3).setmStatus(3);
                                    break;
                                case 4:
                                    videoDownBeans.get(i3).setmStatus(1);
                                    break;
                                case 5:
                                    videoDownBeans.get(i3).setmStatus(4);
                                    break;
                                case 6:
                                    videoDownBeans.get(i3).setmStatus(5);
                                    break;
                                case 7:
                                    videoDownBeans.get(i3).setmStatus(2);
                                    break;
                            }
                        } else {
                            i3++;
                        }
                    }
                }
            }
        }
        return videoDownBeans;
    }

    public void deleteData(final String vid) {
        this.disposable = Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.courselist.fragment.j1
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                CourseDownLoadFragment.lambda$deleteData$10(vid, observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.courselist.fragment.q1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                CourseDownLoadFragment.lambda$deleteData$11((String) obj);
            }
        });
    }

    public void getCourseAk(final String vid, final int type, final AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDownLoadFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                ProjectApp.instance().hideDialogWindow();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId"));
                        String strDecode2 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret"));
                        String strDecode3 = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st"));
                        VidSts vidSts = new VidSts();
                        vidSts.setQuality(QualityValue.QUALITY_FLUENT, false);
                        vidSts.setVid(vid);
                        vidSts.setAccessKeyId(strDecode);
                        vidSts.setAccessKeySecret(strDecode2);
                        vidSts.setSecurityToken(strDecode3);
                        vidSts.setRegion(GlobalPlayerConfig.mRegion);
                        ProjectApp.downloadManager.setmVidSts(vidSts);
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfo2 = aliyunDownloadMediaInfo;
                        if (aliyunDownloadMediaInfo2 == null) {
                            ProjectApp.downloadManager.prepareDownload(vidSts);
                        } else {
                            int i2 = type;
                            if (i2 == 1) {
                                ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfo2);
                            } else if (i2 == 2) {
                                if (!CommonUtil.isNetworkConnected(((BaseFragment) CourseDownLoadFragment.this).mContext)) {
                                    NewToast.showShort(((BaseFragment) CourseDownLoadFragment.this).mContext, "当前无网络连接", 0).show();
                                } else if (CommonUtil.isWifi(((BaseFragment) CourseDownLoadFragment.this).mContext) || UserConfig.isCanDownloadBy4g(((BaseFragment) CourseDownLoadFragment.this).mContext)) {
                                    ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
                                } else {
                                    CourseDownLoadFragment.this.showOpenDownloadDialog();
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_down_load;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.viewModel = (CourseChapterViewModel) ViewModelProviders.of(this).get(CourseChapterViewModel.class);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.listview);
        RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.voidezuo = (LinearLayout) holder.get(R.id.voidezuo);
        ImageView imageView = (ImageView) holder.get(R.id.include_btn_left);
        this.allselet = (TextView) holder.get(R.id.allselet);
        this.okdelete = (TextView) holder.get(R.id.okdelete);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.t1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12042c.lambda$initViews$0(view);
            }
        });
        TextView textView = (TextView) holder.get(R.id.deleteTxt);
        this.deleteTxt = textView;
        textView.setVisibility(0);
        ((TextView) holder.get(R.id.include_title_center)).setText("下载");
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.chaeitem, this.dataList);
        this.adapter = anonymousClass2;
        anonymousClass2.addChildClickViewIds(R.id.check_video);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.u1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12048c.lambda$initViews$1(baseQuickAdapter, view, i2);
            }
        });
        this.adapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.v1
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12055c.lambda$initViews$2(baseQuickAdapter, view, i2);
            }
        });
        recyclerView.setAdapter(this.adapter);
        this.adapter.setEmptyView(R.layout.adapter_empty_txt_view);
        this.viewModel.getDownloadInfo(getArguments().getString("vidteaching_id")).observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.w1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f12060a.lambda$initViews$3((List) obj);
            }
        });
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.fragment.x1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f12068a.lambda$initViews$4((AliyunDownloadMediaInfo) obj);
            }
        });
        this.deleteTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.y1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12076c.lambda$initViews$5(view);
            }
        });
        this.okdelete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11977c.lambda$initViews$8(view);
            }
        });
        this.allselet.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.l1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11983c.lambda$initViews$9(view);
            }
        });
    }

    public boolean isHasWaite(String vid) {
        if (ProjectApp.downloadManager.getWaitedList() == null || ProjectApp.downloadManager.getWaitedList().size() <= 0) {
            return false;
        }
        Iterator<AliyunDownloadMediaInfo> it = ProjectApp.downloadManager.getWaitedList().iterator();
        while (it.hasNext()) {
            if (vid.equals(it.next().getVid() + "")) {
                return true;
            }
        }
        return false;
    }

    public void isHaveData(String vid, int type) {
        AliyunDownloadMediaInfo aliyunDownloadMediaInfo;
        if (YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0) {
            aliyunDownloadMediaInfo = null;
        } else {
            for (int i2 = 0; i2 < YkBManager.getInstance().mDownloadMediaLists.size(); i2++) {
                if (vid.equals(YkBManager.getInstance().mDownloadMediaLists.get(i2).getVid())) {
                    aliyunDownloadMediaInfo = YkBManager.getInstance().mDownloadMediaLists.get(i2);
                    break;
                }
            }
            aliyunDownloadMediaInfo = null;
        }
        if (aliyunDownloadMediaInfo == null) {
            getCourseAk(vid, type, null);
            return;
        }
        if (ProjectApp.downloadManager.getmVidSts() == null) {
            getCourseAk(vid, type, aliyunDownloadMediaInfo);
            return;
        }
        if (type == 1) {
            ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfo);
            return;
        }
        if (type != 2) {
            return;
        }
        if (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
            aliyunDownloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        }
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfo);
        } else {
            showOpenDownloadDialog();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }
}
