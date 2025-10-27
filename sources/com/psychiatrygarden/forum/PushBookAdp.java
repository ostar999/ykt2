package com.psychiatrygarden.forum;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class PushBookAdp extends BaseQuickAdapter<PushBookData, BaseViewHolder> {
    private OnChooseItemLisenter chooseItemLisenter;
    private List<String> mBookIdList;
    private boolean mIsEdit;
    private String searchContent;

    public static abstract class OnChooseItemLisenter {
        public abstract void onDelChoosed(int pos, PushBookData item);

        public abstract void onItemChoosed(int pos, PushBookData item);
    }

    public PushBookAdp(boolean isEdit, List<String> bookIdList) {
        super(isEdit ? R.layout.item_push_ebook_circleinfo : R.layout.item_push_book);
        ArrayList arrayList = new ArrayList();
        this.mBookIdList = arrayList;
        this.mIsEdit = isEdit;
        arrayList.addAll(bookIdList);
    }

    private void getImageDataDesc(String content, TextView mTextView) {
        String str = "\\s+";
        try {
            mTextView.getPaint().getTextSize();
            ColorStateList colorStateList = SkinManager.getCurrentSkinType(getContext()) == 1 ? getContext().getResources().getColorStateList(R.color.main_theme_color_night) : getContext().getResources().getColorStateList(R.color.main_theme_color);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str2 = this.searchContent;
            if (str2 != null && !"".equals(str2)) {
                String[] strArrSplit = this.searchContent.split("\\s+");
                int length = strArrSplit.length;
                int i2 = 0;
                while (i2 < length) {
                    String strReplace = strArrSplit[i2].replace(str, "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            String str3 = str;
                            Matcher matcher2 = matcher;
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher2.start(0), matcher2.end(0), 34);
                            matcher = matcher2;
                            str = str3;
                        }
                    }
                    i2++;
                    str = str;
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, PushBookData pushBookData, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onDelChoosed(baseViewHolder.getLayoutPosition(), pushBookData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, PushBookData pushBookData, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onItemChoosed(baseViewHolder.getLayoutPosition(), pushBookData);
        }
    }

    public void setOnItemChoosedLisenter(OnChooseItemLisenter lisenter) {
        this.chooseItemLisenter = lisenter;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public void updateSelected(List<String> bookIdList) {
        if (bookIdList != null) {
            this.mBookIdList.clear();
            this.mBookIdList.addAll(bookIdList);
            notifyDataSetChanged();
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, PushBookData item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull final BaseViewHolder holder, final PushBookData item) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.img_pic);
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        TextView textView2 = (TextView) holder.getView(R.id.tv_author);
        TextView textView3 = (TextView) holder.getView(R.id.tv_grade);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.ly_item);
        ImageView imageView = (ImageView) holder.getView(R.id.btn_del);
        ImageView imageView2 = (ImageView) holder.getView(R.id.iv_select);
        View view = holder.getView(R.id.line);
        if (this.mIsEdit) {
            TextView textView4 = (TextView) holder.getView(R.id.tv_label);
            textView2.setText(item.getAuthor());
            relativeLayout.setBackgroundResource(R.drawable.shape_white_corners_12);
            textView3.setVisibility(0);
            view.setVisibility(8);
            if (!TextUtils.isEmpty(item.getGrade()) && !item.getGrade().equals("0")) {
                textView3.setVisibility(0);
                textView3.setText(item.getGrade());
                textView4.setText("分");
                textView4.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#C49231" : "#FFC100"));
            } else {
                textView3.setVisibility(8);
                textView4.setText("暂无评分");
                textView4.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#606A8A" : "#7B7E83"));
            }
        } else {
            textView2.setText(item.getDescribe());
            textView3.setVisibility(8);
            view.setVisibility(0);
        }
        imageView2.setSelected(item.isSelected());
        getImageDataDesc(item.getTitle(), textView);
        GlideUtils.loadImage(getContext(), item.getThumbnail(), roundedImageView);
        imageView.setVisibility(this.mIsEdit ? 0 : 8);
        imageView2.setVisibility(this.mIsEdit ? 8 : 0);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15375c.lambda$convert$0(holder, item, view2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15380c.lambda$convert$1(holder, item, view2);
            }
        });
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, PushBookData item, @NonNull List<?> payloads) {
        if (payloads == null || ((Integer) payloads.get(0)).intValue() != 1) {
            return;
        }
        ((ImageView) holder.getView(R.id.iv_select)).setSelected(item.isSelected());
    }
}
