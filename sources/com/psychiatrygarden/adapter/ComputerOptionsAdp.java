package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ComputerOptionsAdp extends BaseQuickAdapter<ExesQuestionBean.OptionBean, BaseViewHolder> {
    private String questionType;

    public ComputerOptionsAdp() {
        super(R.layout.item_computer_options);
        this.questionType = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(ExesQuestionBean.OptionBean optionBean, View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(optionBean.getImg());
        ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(getContext()).setSingleSrcView(null, Integer.valueOf(R.mipmap.ic_order_default)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
        xPopupImageLoader.popupInfo = new PopupInfo();
        xPopupImageLoader.setImageUrls(arrayList).setSrcView(null, 0).show();
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, final ExesQuestionBean.OptionBean item) {
        ImageView imageView = (ImageView) holder.getView(R.id.optionimg);
        TextView textView = (TextView) holder.getView(R.id.tv_content);
        ImageView imageView2 = (ImageView) holder.getView(R.id.img_select);
        if (TextUtils.isEmpty(item.getTitle())) {
            textView.setText(item.getKey());
        } else {
            textView.setText(item.getKey() + StrPool.DOT + item.getTitle());
        }
        if (TextUtils.isEmpty(item.getImg())) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            GlideUtils.loadImageWithPlaceHolder(getContext(), item.getImg(), imageView, R.mipmap.ic_order_default);
        }
        boolean z2 = SkinManager.getCurrentSkinType(getContext()) == 1;
        if (this.questionType.equals("2") || this.questionType.equals(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO) || this.questionType.equals("18") || this.questionType.equals(Constants.VIA_ACT_TYPE_NINETEEN)) {
            if (item.getType().equals("1")) {
                imageView2.setImageResource(z2 ? R.mipmap.icon_computer_more_options_select_yes_night : R.mipmap.icon_computer_more_options_select_yes);
            } else {
                imageView2.setImageResource(z2 ? R.mipmap.icon_computer_more_options_select_no_night : R.mipmap.icon_computer_more_options_select_no);
            }
        } else if (item.getType().equals("1")) {
            imageView2.setImageResource(z2 ? R.mipmap.icon_computer_options_select_yes_night : R.mipmap.icon_computer_options_select_yes);
        } else {
            imageView2.setImageResource(z2 ? R.mipmap.icon_computer_options_select_no_night : R.mipmap.icon_computer_options_select_no);
        }
        imageView.setTag(item.getImg());
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.m3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14746c.lambda$convert$0(item, view);
            }
        });
    }
}
