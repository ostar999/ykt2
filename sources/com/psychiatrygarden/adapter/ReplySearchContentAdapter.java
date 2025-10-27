package com.psychiatrygarden.adapter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.CommentSearchBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class ReplySearchContentAdapter extends BaseQuickAdapter<CommentSearchBean.DataBean, BaseViewHolder> implements LoadMoreModule {
    private String searchContent;

    public ReplySearchContentAdapter(int layoutResId, @Nullable List<CommentSearchBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void getImageData(String content, TextView mTextView, CommentSearchBean.DataBean dataBean) {
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

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, CommentSearchBean.DataBean dataBean) {
        TextView textView = (TextView) helper.getView(R.id.tv_content);
        TextView textView2 = (TextView) helper.getView(R.id.tv_nickname);
        RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.img_head);
        TextView textView3 = (TextView) helper.getView(R.id.tv_school);
        ImageView imageView = (ImageView) helper.getView(R.id.img_auth);
        textView2.setText(dataBean.getNickname());
        textView2.setTextColor(Color.parseColor(dataBean.getUser_identity_color()));
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(dataBean.getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.weiloginappimg)).into(roundedImageView);
        textView3.setText(dataBean.getSchool() + " " + dataBean.getCtime());
        if (TextUtils.isEmpty(dataBean.getUser_identity()) || dataBean.getUser_identity().equals("0")) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(8);
        }
        if (dataBean.getUser_identity().equals("1") || dataBean.getIs_authentication().equals("1")) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        getImageData(dataBean.getContent().replaceAll("<font.*?>", "").replaceAll("</font>", ""), textView, dataBean);
    }
}
