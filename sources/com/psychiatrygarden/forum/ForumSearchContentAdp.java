package com.psychiatrygarden.forum;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ForumSearchBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class ForumSearchContentAdp extends BaseMultiItemQuickAdapter<ForumSearchBean, BaseViewHolder> {
    private String searchContent;

    public ForumSearchContentAdp() {
        addItemType(1, R.layout.activity_circleitem);
        addItemType(2, R.layout.activity_circleitem);
        addItemType(3, R.layout.item_forum_search_comment);
        addItemType(4, R.layout.item_forum_search_book);
        addItemType(5, R.layout.item_material_list);
    }

    private void getImageData(String content, TextView mTextView, CirclrListBean.DataBean dataBean) throws Resources.NotFoundException {
        Resources resources;
        int i2;
        String str = "\\s+";
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            resources = getContext().getResources();
            i2 = R.color.main_theme_color_night;
        } else {
            resources = getContext().getResources();
            i2 = R.color.main_theme_color;
        }
        ColorStateList colorStateList = resources.getColorStateList(i2);
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str2 = this.searchContent;
            int i3 = 0;
            if (str2 != null && !"".equals(str2)) {
                String[] strArrSplit = this.searchContent.split("\\s+");
                int length = strArrSplit.length;
                int i4 = 0;
                while (i4 < length) {
                    String strReplace = strArrSplit[i4].replace(str, "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            Matcher matcher2 = matcher;
                            String str3 = str;
                            int i5 = i3;
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher2.start(i5), matcher2.end(i5), 34);
                            i3 = i5;
                            matcher = matcher2;
                            i4 = i4;
                            length = length;
                            strArrSplit = strArrSplit;
                            str = str3;
                        }
                    }
                    i4++;
                    i3 = i3;
                    length = length;
                    strArrSplit = strArrSplit;
                    str = str;
                }
            }
            int i6 = i3;
            if (dataBean.getIcon_img() != null) {
                for (int i7 = i6; i7 < dataBean.getIcon_img().size(); i7++) {
                    if (!dataBean.getIcon_img().get(i7).isEmpty()) {
                        spannableStringBuilder.insert(i6, (CharSequence) (StrPool.BRACKET_START + dataBean.getIcon_img().get(i7) + StrPool.BRACKET_END));
                    }
                }
            }
            Matcher matcher3 = Pattern.compile("\\[[^\\]]+\\]").matcher(spannableStringBuilder);
            while (matcher3.find()) {
                String strGroup = matcher3.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new StickerSpan(new URLImageParser(mTextView, getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher3.start(), matcher3.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    private void getImageDataDesc(String content, TextView mTextView, CirclrListBean.DataBean dataBean) {
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

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, ForumSearchBean item) throws Resources.NotFoundException {
        int itemType = item.getItemType();
        if (itemType == 1 || itemType == 2) {
            TextView textView = (TextView) helper.getView(R.id.title);
            TextView textView2 = (TextView) helper.getView(R.id.tv_desc);
            textView2.setVisibility(0);
            helper.setText(R.id.time, item.getCircleInfo().getComment_time()).setText(R.id.commnum, item.getCircleInfo().getComment_count() + " 评论");
            String strReplaceAll = item.getCircleInfo().getTitle().replaceAll("\\[image\\]", "").replaceAll("\\[link\\]", "");
            if (item.getType() == 2) {
                getImageData(strReplaceAll, textView, item.getCircleInfo());
                getImageDataDesc(item.getCircleInfo().getIntroduction(), textView2, item.getCircleInfo());
                return;
            } else {
                getImageData(strReplaceAll, textView, item.getCircleInfo());
                getImageDataDesc(item.getCircleInfo().getContent().replaceAll("\\[image\\]", "").replaceAll("\\[link\\]", ""), textView2, item.getCircleInfo());
                return;
            }
        }
        if (itemType == 3) {
            CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.img_head);
            TextView textView3 = (TextView) helper.getView(R.id.tv_nickname);
            ImageView imageView = (ImageView) helper.getView(R.id.img_v);
            ImageView imageView2 = (ImageView) helper.getView(R.id.img_auth);
            TextView textView4 = (TextView) helper.getView(R.id.tv_hospital);
            TextView textView5 = (TextView) helper.getView(R.id.tv_time);
            TextView textView6 = (TextView) helper.getView(R.id.tv_content);
            textView3.setText(item.getCircleInfo().getNickname());
            textView3.setTextColor(Color.parseColor(item.getCircleInfo().getUser_identity_color()));
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getCircleInfo().getAvatar())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.weiloginappimg)).into(circleImageView);
            if (TextUtils.isEmpty(item.getCircleInfo().getUser_identity())) {
                imageView2.setVisibility(8);
            } else if (item.getCircleInfo().getIs_authentication().equals("1") || item.getCircleInfo().getUser_identity().equals("1")) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
            if (item.getCircleInfo().getIs_svip().equals("1")) {
                imageView.setVisibility(0);
                imageView.setImageResource(R.drawable.svip333img);
            } else if (item.getCircleInfo().getIs_vip().equals("1")) {
                imageView.setVisibility(0);
                imageView.setImageResource(R.drawable.vipimg);
            } else {
                imageView.setVisibility(8);
            }
            getImageDataDesc(item.getCircleInfo().getContent(), textView6, item.getCircleInfo());
            textView4.setText(item.getCircleInfo().getSchool());
            textView5.setText(item.getCircleInfo().getCtime());
            return;
        }
        if (itemType == 4) {
            RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.img_cover);
            TextView textView7 = (TextView) helper.getView(R.id.tv_title);
            TextView textView8 = (TextView) helper.getView(R.id.tv_desc);
            TextView textView9 = (TextView) helper.getView(R.id.tv_read_count);
            TextView textView10 = (TextView) helper.getView(R.id.tv_comment_count);
            View view = helper.getView(R.id.line);
            GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getCircleInfo().getThumbnail())).into(roundedImageView);
            textView8.setText(item.getCircleInfo().getDescribe());
            textView9.setText(item.getCircleInfo().getRead_count() + "人阅读");
            textView10.setText(item.getCircleInfo().getBook_review_count() + " 评论");
            view.setVisibility(helper.getLayoutPosition() == getData().size() - 1 ? 8 : 0);
            getImageDataDesc(item.getCircleInfo().getTitle(), textView7, item.getCircleInfo());
            return;
        }
        if (itemType != 5) {
            return;
        }
        ImageView imageView3 = (ImageView) helper.getView(R.id.iv_file_type);
        ImageView imageView4 = (ImageView) helper.getView(R.id.ic_lock);
        TextView textView11 = (TextView) helper.getView(R.id.tv_file_name);
        TextView textView12 = (TextView) helper.getView(R.id.tv_author);
        TextView textView13 = (TextView) helper.getView(R.id.tv_file_size);
        TextView textView14 = (TextView) helper.getView(R.id.tv_download_count);
        textView13.setText(item.getCircleInfo().getSize());
        textView14.setText(item.getCircleInfo().getDownload_count() + "次下载");
        textView12.setText(item.getCircleInfo().getNickname());
        if (TextUtils.isEmpty(item.getCircleInfo().getIs_rights()) || !item.getCircleInfo().getIs_rights().equals("1")) {
            imageView4.setVisibility(0);
        } else {
            imageView4.setVisibility(8);
        }
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getCircleInfo().getIcon())).into(imageView3);
        getImageDataDesc(item.getCircleInfo().getTitle() + item.getCircleInfo().getType_name(), textView11, item.getCircleInfo());
    }
}
