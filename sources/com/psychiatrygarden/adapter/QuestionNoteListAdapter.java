package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.util.XPopupUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.gradview.NineGridTestLayout;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.RelativeDateFormat;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionNoteListAdapter extends BaseQuickAdapter<QuestionNoteBean, BaseViewHolder> implements LoadMoreModule {
    private boolean fromVideo;
    private boolean landScape;

    public QuestionNoteListAdapter(int layoutResId, @Nullable List<QuestionNoteBean> data) {
        super(layoutResId, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convert$0(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView((RoundedImageView) view, view.getTag()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convert$1(View view) {
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView((RoundedImageView) view, view.getTag()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.show();
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public QuestionNoteListAdapter(int layoutResId, @Nullable List<QuestionNoteBean> data, boolean fromVideo, boolean landScape) {
        super(layoutResId, data);
        this.landScape = landScape;
        this.fromVideo = fromVideo;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, QuestionNoteBean dataBean) {
        float f2;
        TextView textView = (TextView) helper.getView(R.id.tv_note_content);
        NineGridTestLayout nineGridTestLayout = (NineGridTestLayout) helper.getView(R.id.ningrids);
        TextView textView2 = (TextView) helper.getView(R.id.tv_note_time);
        textView.setText(dataBean.getContent());
        textView2.setText(RelativeDateFormat.format(dataBean.getCtime()));
        nineGridTestLayout.setVisibility((this.fromVideo || dataBean.getImg() == null || dataBean.getImg().size() <= 0 || TextUtils.isEmpty(dataBean.getImg().get(0).getS_img())) ? 8 : 0);
        if (this.fromVideo) {
            RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.img_one);
            RoundedImageView roundedImageView2 = (RoundedImageView) helper.getView(R.id.img_two);
            View view = helper.getView(R.id.line);
            roundedImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            roundedImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            textView.setVisibility(TextUtils.isEmpty(dataBean.getContent()) ? 8 : 0);
            List<ImagesBean> img = dataBean.getImg();
            if (img == null || img.isEmpty()) {
                roundedImageView.setVisibility(8);
                roundedImageView2.setVisibility(8);
                view.setVisibility(8);
            } else if (img.size() == 1) {
                roundedImageView.setVisibility(0);
                view.setVisibility(8);
                roundedImageView2.setVisibility(8);
                roundedImageView.setTag(img.get(0).getB_img());
                GlideUtils.loadImage(getContext(), img.get(0).getB_img(), roundedImageView);
            } else if (img.size() == 2) {
                roundedImageView.setVisibility(0);
                roundedImageView2.setVisibility(0);
                view.setVisibility(0);
                roundedImageView.setTag(img.get(0).getB_img());
                roundedImageView2.setTag(img.get(1).getB_img());
                GlideUtils.loadImage(getContext(), img.get(0).getB_img(), roundedImageView);
                GlideUtils.loadImage(getContext(), img.get(1).getB_img(), roundedImageView2);
            }
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ke
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    QuestionNoteListAdapter.lambda$convert$0(view2);
                }
            });
            roundedImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.le
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    QuestionNoteListAdapter.lambda$convert$1(view2);
                }
            });
            return;
        }
        if (dataBean.getImg() == null || dataBean.getImg().size() <= 0 || TextUtils.isEmpty(dataBean.getImg().get(0).getS_img())) {
            return;
        }
        float windowWidth = XPopupUtils.getWindowWidth(getContext()) - XPopupUtils.dp2px(getContext(), 51.0f);
        float s_width = dataBean.getImg().get(0).getS_width();
        float s_height = dataBean.getImg().get(0).getS_height();
        if (s_height >= s_width * 2.0f) {
            f2 = (windowWidth / 3.0f) * 2.0f;
        } else if (s_height >= s_width) {
            f2 = (s_height * ((windowWidth / 2.0f) - 50.0f)) / s_width;
        } else if (s_width >= s_height * 2.0f) {
            f2 = ((windowWidth * 2.0f) / 3.0f) / 2.0f;
        } else {
            f2 = (((double) (s_width / s_height)) <= 1.1d ? ((windowWidth / 5.0f) * 3.0f) * 4.0f : ((windowWidth / 5.0f) * 3.0f) * 3.0f) / 5.0f;
        }
        ViewGroup.LayoutParams layoutParams = nineGridTestLayout.getLayoutParams();
        layoutParams.height = (int) f2;
        nineGridTestLayout.setLayoutParams(layoutParams);
        nineGridTestLayout.setDownImgUrl(dataBean.getImg().get(0).getB_img());
        if (!this.fromVideo) {
            nineGridTestLayout.setmImagesBean(dataBean.getImg().get(0), 1);
            nineGridTestLayout.setIsShowAll(false);
            return;
        }
        nineGridTestLayout.setIsShowAll(true);
        ArrayList arrayList = new ArrayList();
        Iterator<ImagesBean> it = dataBean.getImg().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getB_img());
        }
        nineGridTestLayout.setUrlList(arrayList);
    }
}
