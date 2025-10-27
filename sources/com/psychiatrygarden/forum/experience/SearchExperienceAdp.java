package com.psychiatrygarden.forum.experience;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class SearchExperienceAdp extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> {
    private boolean fromHomeStyle;

    public SearchExperienceAdp() {
        super(R.layout.activity_handlist);
    }

    private void getImageData(String content, String zhidingimg, String zuixinimg, String zuireimg, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            StringBuffer stringBuffer = new StringBuffer();
            if (TextUtils.isEmpty(zhidingimg)) {
                stringBuffer.append("");
            } else {
                stringBuffer.append(StrPool.BRACKET_START);
                stringBuffer.append(zhidingimg);
                stringBuffer.append("] ");
            }
            if (TextUtils.isEmpty(zuixinimg)) {
                stringBuffer.append("");
            } else {
                stringBuffer.append(StrPool.BRACKET_START);
                stringBuffer.append(zuixinimg);
                stringBuffer.append("] ");
            }
            if (TextUtils.isEmpty(zuireimg)) {
                stringBuffer.append("");
            } else {
                stringBuffer.append(StrPool.BRACKET_START);
                stringBuffer.append(zuireimg);
                stringBuffer.append("] ");
            }
            stringBuffer.append(content);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^]]+]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new StickerSpan(new URLImageParser(mTextView, getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    public SearchExperienceAdp(boolean homeStyle) {
        super(R.layout.activity_handlist_home_style);
        this.fromHomeStyle = homeStyle;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, CirclrListBean.DataBean item) {
        TextView textView;
        char c3;
        holder.setGone(R.id.textView1, holder.getLayoutPosition() == getData().size() - 1 && this.fromHomeStyle && getData().size() < 5);
        TextView textView2 = (TextView) holder.getView(R.id.handoutClass);
        ImageView imageView = (ImageView) holder.getView(R.id.img_school);
        TextView textView3 = (TextView) holder.getView(R.id.handoutComment);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.imageView10);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.lin_img);
        RoundedImageView roundedImageView2 = (RoundedImageView) holder.getView(R.id.handoutimgUrl);
        TextView textView4 = (TextView) holder.getView(R.id.item2_title);
        TextView textView5 = (TextView) holder.getView(R.id.item2_class);
        TextView textView6 = (TextView) holder.getView(R.id.item2_comment);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.relvel_item1);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.relvel_item2);
        TextView textView7 = (TextView) holder.getView(R.id.handoutName);
        TextView textView8 = (TextView) holder.getView(R.id.txtred);
        TextView textView9 = (TextView) holder.getView(R.id.textred1);
        List<String> cover = item.getCover();
        String is_long = item.getIs_long();
        if (cover.size() <= 1 && !is_long.equals("1")) {
            linearLayout2.setVisibility(0);
            relativeLayout.setVisibility(8);
            if (cover.size() > 0) {
                roundedImageView2.setVisibility(0);
                Glide.with(roundedImageView2.getContext()).load((Object) GlideUtils.generateUrl(cover.get(0))).transform(new RoundedCorners(CommonUtil.dip2px(roundedImageView2.getContext(), CommonUtil.dip2px(roundedImageView2.getContext(), 2.0f)))).into(roundedImageView2);
            } else {
                roundedImageView2.setVisibility(8);
            }
            try {
                getImageData(item.getTitle(), item.getTop_img(), item.getNew_img(), item.getHot_img(), textView7);
                if (item.getAuthor_looked().equals("1")) {
                    textView8.setVisibility(0);
                } else {
                    textView8.setVisibility(8);
                }
            } catch (Exception unused) {
                textView7.setText(item.getTitle());
            }
            try {
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    if (item.getIs_read().equals("1")) {
                        textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.line_txt_color));
                    } else {
                        textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.first_txt_color));
                    }
                } else if (item.getIs_read().equals("1")) {
                    textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.line_txt_color_night));
                } else {
                    textView7.setTextColor(ContextCompat.getColor(getContext(), R.color.first_txt_color_night));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (TextUtils.isEmpty(item.getCname())) {
                c3 = 0;
                textView2.setVisibility(8);
                imageView.setVisibility(8);
            } else {
                textView2.setText(item.getCname());
                c3 = 0;
                textView2.setVisibility(0);
            }
            Object[] objArr = new Object[1];
            objArr[c3] = item.getComment_count();
            textView3.setText(String.format("%s 评论", objArr));
            return;
        }
        relativeLayout.setVisibility(0);
        linearLayout2.setVisibility(8);
        if (is_long.equals("1")) {
            roundedImageView.setVisibility(0);
            linearLayout.setVisibility(8);
            try {
                Glide.with(roundedImageView.getContext()).load((Object) GlideUtils.generateUrl(cover.get(0))).placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).transform(new RoundedCorners(CommonUtil.dip2px(roundedImageView.getContext(), CommonUtil.dip2px(roundedImageView.getContext(), 2.0f)))).into(roundedImageView);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            linearLayout.removeAllViews();
            roundedImageView.setVisibility(8);
            linearLayout.setVisibility(0);
            int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 56.0d)) / 3;
            for (int i2 = 0; i2 < cover.size(); i2++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, -1);
                layoutParams.rightMargin = UIUtil.dip2px(getContext(), 10.0d);
                RoundedImageView roundedImageView3 = new RoundedImageView(getContext());
                roundedImageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                roundedImageView3.setCornerRadius(CommonUtil.dip2px(roundedImageView3.getContext(), 4.0f));
                Glide.with(roundedImageView3.getContext()).load((Object) GlideUtils.generateUrl(cover.get(i2))).placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).centerCrop().transform(new RoundedCorners(CommonUtil.dip2px(roundedImageView3.getContext(), CommonUtil.dip2px(roundedImageView3.getContext(), 2.0f)))).into(roundedImageView3);
                linearLayout.addView(roundedImageView3, layoutParams);
            }
        }
        textView6.setText(String.format("%s 评论", item.getComment_count()));
        try {
            getImageData(item.getTitle(), item.getTop_img(), item.getNew_img(), item.getHot_img(), textView4);
            if (item.getAuthor_looked().equals("1")) {
                textView9.setVisibility(0);
            } else {
                textView9.setVisibility(8);
            }
            textView = textView4;
        } catch (Exception e4) {
            e4.printStackTrace();
            textView = textView4;
            textView.setText(item.getTitle());
        }
        textView5.setText(item.getCname());
        try {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                if (item.getIs_read().equals("1")) {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.line_txt_color));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.first_txt_color));
                }
            } else if (item.getIs_read().equals("1")) {
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.line_txt_color_night));
            } else {
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.first_txt_color_night));
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
    }
}
