package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import cn.hutool.core.img.ImgUtil;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.purchase.zoom.PhotoView;
import com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.psychiatrygarden.widget.glideUtil.progress.GlideRequest;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes5.dex */
public class LoadImageAdapter extends PagerAdapter {
    Context context;
    boolean longpic;
    private Activity mActivity;
    Bitmap mBitmap;
    private LinkedList<View> mCacheViews;
    Handler mFinishHandler;
    private List<String> mImages;
    private LayoutInflater mInflater;
    ViewHolder mViewHolder = null;
    boolean methodTrue;

    public static final class ViewHolder {
        public SubsamplingScaleImageView imageSub;
        public PhotoView mImageView;
        public ProgressBar progressBar;
    }

    public LoadImageAdapter(Activity activity, List<String> images, Handler mFinishHandler, boolean methodTrue, boolean longpic) {
        this.mInflater = null;
        this.mActivity = null;
        this.mCacheViews = null;
        this.mImages = images;
        this.mInflater = LayoutInflater.from(activity);
        this.mActivity = activity;
        this.mCacheViews = new LinkedList<>();
        this.mFinishHandler = mFinishHandler;
        this.methodTrue = methodTrue;
        this.longpic = longpic;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$0(View view, float f2, float f3) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 1;
        this.mFinishHandler.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$1(View view) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 1;
        this.mFinishHandler.sendMessage(messageObtain);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        releaseImageViewResouce(((ViewHolder) view.getTag()).mImageView);
        container.removeView(view);
        this.mCacheViews.add(view);
    }

    public void displayImage(final String model, final SubsamplingScaleImageView imageView, final View loading) {
        GlideApp.with(ProjectApp.instance()).downloadOnly().load((Object) GlideUtils.generateUrl(model)).listener(new RequestListener<File>() { // from class: com.psychiatrygarden.adapter.LoadImageAdapter.3
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, Object model2, Target<File> target, boolean isFirstResource) {
                loading.setVisibility(8);
                imageView.setImage(ImageSource.resource(R.drawable.kaodianhuanyuan));
                return true;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(File resource, Object model2, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                loading.setVisibility(8);
                return false;
            }
        }).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.psychiatrygarden.adapter.LoadImageAdapter.2
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((File) resource, (Transition<? super File>) transition);
            }

            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
            }
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.mImages.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View viewRemoveFirst;
        if (this.mCacheViews.size() == 0) {
            viewRemoveFirst = LayoutInflater.from(this.mActivity).inflate(R.layout.viewparger_item, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            this.mViewHolder = viewHolder;
            viewHolder.mImageView = (PhotoView) viewRemoveFirst.findViewById(R.id.mphotoview);
            this.mViewHolder.progressBar = (ProgressBar) viewRemoveFirst.findViewById(R.id.progressBar);
            this.mViewHolder.imageSub = (SubsamplingScaleImageView) viewRemoveFirst.findViewById(R.id.imageView);
            viewRemoveFirst.setTag(this.mViewHolder);
        } else {
            viewRemoveFirst = this.mCacheViews.removeFirst();
            this.mViewHolder = (ViewHolder) viewRemoveFirst.getTag();
        }
        if (!CommonUtil.isNetworkConnected(ProjectApp.instance())) {
            this.mViewHolder.mImageView.setImageResource(R.drawable.imgplacehodel_image);
            this.mViewHolder.progressBar.setVisibility(8);
        } else if (!this.methodTrue) {
            this.mViewHolder.imageSub.setVisibility(0);
            this.mViewHolder.mImageView.setVisibility(8);
            String str = this.mImages.get(position);
            ViewHolder viewHolder2 = this.mViewHolder;
            displayImage(str, viewHolder2.imageSub, viewHolder2.progressBar);
        } else if (this.mImages.get(position).contains(ImgUtil.IMAGE_TYPE_GIF) || this.mImages.get(position).contains("GIF")) {
            this.mViewHolder.progressBar.setVisibility(8);
            this.mViewHolder.mImageView.setVisibility(0);
            this.mViewHolder.imageSub.setVisibility(8);
            GlideApp.with(ProjectApp.instance()).asGif().load((Object) GlideUtils.generateUrl(this.mImages.get(position))).into(this.mViewHolder.mImageView);
        } else {
            this.mViewHolder.progressBar.setVisibility(8);
            this.mViewHolder.mImageView.setVisibility(8);
            this.mViewHolder.imageSub.setVisibility(0);
            GlideApp.with(ProjectApp.instance()).downloadOnly().load((Object) GlideUtils.generateUrl(this.mImages.get(position))).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.psychiatrygarden.adapter.LoadImageAdapter.1
                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    LoadImageAdapter.this.mViewHolder.progressBar.setVisibility(8);
                    ToastUtil.shortToast(LoadImageAdapter.this.mActivity, "加载失败！");
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    LoadImageAdapter.this.mViewHolder.progressBar.setVisibility(0);
                    LoadImageAdapter.this.mViewHolder.imageSub.setImage(ImageSource.resource(R.drawable.imgplacehodel));
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                    onResourceReady((File) resource, (Transition<? super File>) transition);
                }

                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                    LoadImageAdapter.this.mViewHolder.progressBar.setVisibility(8);
                    LoadImageAdapter.this.mViewHolder.imageSub.setImage(ImageSource.uri(Uri.fromFile(resource)));
                }
            });
        }
        this.mViewHolder.mImageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() { // from class: com.psychiatrygarden.adapter.da
            @Override // com.psychiatrygarden.activity.purchase.zoom.PhotoViewAttacher.OnViewTapListener
            public final void onViewTap(View view, float f2, float f3) {
                this.f14404a.lambda$instantiateItem$0(view, f2, f3);
            }
        });
        this.mViewHolder.imageSub.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ea
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14443c.lambda$instantiateItem$1(view);
            }
        });
        container.addView(viewRemoveFirst);
        return viewRemoveFirst;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void releaseImageViewResouce(PhotoView imageView) {
        Drawable drawable;
        Bitmap bitmap;
        if (imageView == null || (drawable = imageView.getDrawable()) == null || !(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled()) {
            return;
        }
        imageView.setImageBitmap(null);
    }
}
