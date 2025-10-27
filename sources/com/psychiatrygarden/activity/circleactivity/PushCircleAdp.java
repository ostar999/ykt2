package com.psychiatrygarden.activity.circleactivity;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class PushCircleAdp extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> implements LoadMoreModule, DraggableModule {
    private OnChooseItemLisenter chooseItemLisenter;
    private boolean isDetail;
    private boolean isEdit;
    private String searchContent;

    public static abstract class OnChooseItemLisenter {
        public abstract void onDelChoosed(int pos, CirclrListBean.DataBean item);

        public abstract void onItemChoosed(int pos, CirclrListBean.DataBean item);
    }

    public PushCircleAdp(boolean isEdit, boolean isDetail) {
        super(R.layout.item_push_circle);
        this.isEdit = isEdit;
        this.isDetail = isDetail;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, CirclrListBean.DataBean dataBean, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onItemChoosed(baseViewHolder.getLayoutPosition(), dataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, CirclrListBean.DataBean dataBean, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onDelChoosed(baseViewHolder.getLayoutPosition(), dataBean);
        }
    }

    @Override // com.chad.library.adapter.base.module.DraggableModule
    public /* synthetic */ BaseDraggableModule addDraggableModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.g.a(this, baseQuickAdapter);
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void getImageData(String content, TextView mTextView, CirclrListBean.DataBean dataBean) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str = this.searchContent;
            if (str != null && !"".equals(str)) {
                for (String str2 : this.searchContent.split("\\s+")) {
                    String strReplace = str2.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, getContext().getResources().getColorStateList(R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                        }
                    }
                }
            }
            for (int i2 = 0; i2 < dataBean.getIcon_img().size(); i2++) {
                if (!dataBean.getIcon_img().get(i2).isEmpty()) {
                    spannableStringBuilder.insert(0, (CharSequence) (StrPool.BRACKET_START + dataBean.getIcon_img().get(i2) + StrPool.BRACKET_END));
                }
            }
            Matcher matcher2 = Pattern.compile("\\[[^\\]]+\\]").matcher(spannableStringBuilder);
            while (matcher2.find()) {
                String strGroup = matcher2.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new StickerSpan(new URLImageParser(mTextView, getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher2.start(), matcher2.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    public void setOnItemChoosedLisenter(OnChooseItemLisenter lisenter) {
        this.chooseItemLisenter = lisenter;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, CirclrListBean.DataBean item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(final BaseViewHolder helper, final CirclrListBean.DataBean item) {
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.ly_item);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_select);
        ImageView imageView2 = (ImageView) helper.getView(R.id.btn_del);
        TextView textView = (TextView) helper.getView(R.id.tv_name);
        helper.setText(R.id.tv_time, item.getComment_time());
        helper.setText(R.id.tv_count, item.getComment_count() + "评论");
        imageView.setSelected(item.isSelected());
        getImageData(item.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""), textView, item);
        if (this.isDetail) {
            relativeLayout.setBackgroundResource(R.drawable.shape_push_circle_empty_bg);
            imageView.setVisibility(8);
            imageView2.setVisibility(8);
        } else {
            imageView.setVisibility(this.isEdit ? 8 : 0);
            imageView2.setVisibility(this.isEdit ? 0 : 8);
            if (this.isEdit) {
                relativeLayout.setBackgroundResource(R.drawable.shape_white_corners_12);
            } else {
                relativeLayout.setBackgroundResource(R.drawable.shape_push_circle_empty_bg);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.e3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11515c.lambda$convert$0(helper, item, view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.f3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11525c.lambda$convert$1(helper, item, view);
            }
        });
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, CirclrListBean.DataBean item, @NonNull List<?> payloads) {
        if (payloads == null || ((Integer) payloads.get(0)).intValue() != 1) {
            return;
        }
        ((ImageView) holder.getView(R.id.iv_select)).setSelected(item.isSelected());
    }
}
