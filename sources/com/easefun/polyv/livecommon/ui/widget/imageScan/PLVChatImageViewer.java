package com.easefun.polyv.livecommon.ui.widget.imageScan;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVUrlTag;
import com.hjq.permissions.Permission;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import com.plv.thirdpart.blankj.utilcode.util.FileUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChatImageViewer extends FrameLayout {
    private static final String TAG = "PLVChatImageViewer";
    private CompositeDisposable compositeDisposable;
    private int currentPosition;
    private View.OnClickListener imgOnClickListener;
    private List<PLVUrlTag> imgUrlTags;
    private ImageView ivDownload;
    private PLVImageViewPagerAdapter<PLVUrlTag, PLVChatImageContainerWidget> pagerAdapter;
    private TextView tvPage;
    private View view;
    private ViewPager vpImageViewer;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(Permission.WRITE_EXTERNAL_STORAGE);
            PLVFastPermission.getInstance().start((Activity) PLVChatImageViewer.this.getContext(), arrayList, new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.1.1
                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onAllGranted() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
                    PLVChatImageViewer.this.downloadImg();
                }

                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                    if (deniedForeverP == null || deniedForeverP.isEmpty()) {
                        PLVChatImageViewer.this.toast("请允许存储权限后再保存图片");
                    } else {
                        new AlertDialog.Builder(PLVChatImageViewer.this.getContext()).setTitle("提示").setMessage("保存图片所需的存储权限被拒绝，请到应用设置的权限管理中恢复").setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.1.1.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                PLVFastPermission.getInstance().jump2Settings(PLVChatImageViewer.this.getContext());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.1.1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                PLVCommonLog.d(PLVChatImageViewer.TAG, "cancel");
                            }
                        }).setCancelable(false).show();
                    }
                }
            });
        }
    }

    public PLVChatImageViewer(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadImg() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        int i2 = this.currentPosition;
        if (i2 > -1) {
            final String url = this.imgUrlTags.get(i2).getUrl();
            if (url == null) {
                toast("图片保存失败(null)");
                return;
            }
            final String strSubstring = url.substring(url.lastIndexOf(47) + 1);
            final String strCreatePath = PLVSDCardUtils.createPath(getContext(), "PLVChatImg");
            if (this.compositeDisposable == null) {
                this.compositeDisposable = new CompositeDisposable();
            }
            this.compositeDisposable.add(Observable.just(1).map(new Function<Integer, File>() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.5
                @Override // io.reactivex.functions.Function
                public File apply(Integer integer) throws Exception {
                    try {
                        File file = new File(url);
                        if (file.isFile()) {
                            if (file.exists()) {
                                return file;
                            }
                        }
                    } catch (Exception e2) {
                        PLVCommonLog.e(PLVChatImageViewer.TAG, "downloadImg：" + e2.getMessage());
                    }
                    return PLVImageLoader.getInstance().saveImageAsFile(PLVChatImageViewer.this.getContext(), url, PLVChatImageContainerWidget.LOADIMG_MOUDLE_TAG + PLVChatImageViewer.this.imgUrlTags.get(PLVChatImageViewer.this.currentPosition));
                }
            }).map(new Function<File, Boolean>() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.4
                @Override // io.reactivex.functions.Function
                public Boolean apply(File file) throws Exception {
                    return file.getAbsolutePath().equals(new File(strCreatePath, strSubstring).getAbsolutePath()) ? Boolean.TRUE : Boolean.valueOf(FileUtils.copyFile(file, new File(strCreatePath, strSubstring), new FileUtils.OnReplaceListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.4.1
                        @Override // com.plv.thirdpart.blankj.utilcode.util.FileUtils.OnReplaceListener
                        public boolean onReplace() {
                            return true;
                        }
                    }));
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.2
                @Override // io.reactivex.functions.Consumer
                public void accept(Boolean aBoolean) throws Exception {
                    String str;
                    PLVChatImageViewer pLVChatImageViewer = PLVChatImageViewer.this;
                    if (aBoolean.booleanValue()) {
                        str = "图片保存在：" + new File(strCreatePath, strSubstring).getAbsolutePath();
                    } else {
                        str = "图片保存失败(saveFailed)";
                    }
                    pLVChatImageViewer.toast(str);
                }
            }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.3
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable throwable) throws Exception {
                    PLVChatImageViewer.this.toast("图片保存失败(loadFailed)");
                }
            }));
        }
    }

    private void init(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.plv_image_view_pager_layout, this);
        this.view = viewInflate;
        this.vpImageViewer = (ViewPager) viewInflate.findViewById(R.id.vp_image_viewer);
        this.tvPage = (TextView) this.view.findViewById(R.id.tv_page);
        ImageView imageView = (ImageView) this.view.findViewById(R.id.iv_download);
        this.ivDownload = imageView;
        imageView.setOnClickListener(new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toast(String message) {
        ToastUtils.showShort(message);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            this.compositeDisposable = null;
        }
    }

    @Override // android.view.View
    public void onVisibilityChanged(@NonNull View changedView, int visibility) {
        CompositeDisposable compositeDisposable;
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0 || (compositeDisposable = this.compositeDisposable) == null) {
            return;
        }
        compositeDisposable.dispose();
        this.compositeDisposable = null;
    }

    public void setDataList(final List<PLVUrlTag> dataList, int curPosition) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        this.imgUrlTags = dataList;
        PLVImageViewPagerAdapter<PLVUrlTag, PLVChatImageContainerWidget> pLVImageViewPagerAdapter = this.pagerAdapter;
        if (pLVImageViewPagerAdapter == null) {
            PLVImageViewPagerAdapter<PLVUrlTag, PLVChatImageContainerWidget> pLVImageViewPagerAdapter2 = new PLVImageViewPagerAdapter<>(getContext());
            this.pagerAdapter = pLVImageViewPagerAdapter2;
            pLVImageViewPagerAdapter2.setOnImgClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.6
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (PLVChatImageViewer.this.imgOnClickListener != null) {
                        PLVChatImageViewer.this.imgOnClickListener.onClick(v2);
                    }
                }
            });
            this.pagerAdapter.bindData(this.imgUrlTags);
            this.vpImageViewer.setAdapter(this.pagerAdapter);
            this.vpImageViewer.clearOnPageChangeListeners();
            this.vpImageViewer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewer.7
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                    PLVCommonLog.d(PLVChatImageViewer.TAG, "onPageScrollStateChanged:" + state);
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    PLVCommonLog.d(PLVChatImageViewer.TAG, "onPageScrolled position:" + position + " positionOffset:" + positionOffset + " positionOffsetPixels:" + positionOffsetPixels);
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    PLVChatImageViewer.this.currentPosition = position;
                    PLVChatImageViewer.this.tvPage.setText((position + 1) + "/" + PLVChatImageViewer.this.imgUrlTags.size());
                }
            });
        } else {
            pLVImageViewPagerAdapter.bindData(dataList);
            this.pagerAdapter.notifyDataSetChanged();
        }
        this.vpImageViewer.setCurrentItem(curPosition, false);
        this.currentPosition = curPosition;
        this.tvPage.setText((curPosition + 1) + "/" + this.imgUrlTags.size());
    }

    public void setOnClickImgListener(View.OnClickListener listener) {
        this.imgOnClickListener = listener;
    }

    public PLVChatImageViewer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVChatImageViewer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentPosition = -1;
        init(context);
    }
}
