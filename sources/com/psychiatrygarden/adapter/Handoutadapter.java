package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.psychiatrygarden.bean.HandoutNewBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.widget.SkinGrakImagView;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class Handoutadapter extends BaseAdapter {
    private String content;
    private Context context;
    private List<HandoutNewBean.DataBean.TopBean> list;
    private ColorStateList redColors;

    public static class childViewHoder {
        TextView handoutClass;
        TextView handoutComment;
        TextView handoutName;
        ImageView handoutimgUrl;
        ImageView imageView1;
        ImageView imageView10;
        ImageView imageView2;
        ImageView image_View2;
        ImageView imagetop;
        ImageView imagetop2;
        ImageView img_view;
        TextView item2_class;
        TextView item2_comment;
        TextView item2_title;
        LinearLayout lin_img;
        LinearLayout relvel_item1;
        RelativeLayout relvel_item2;
        TextView textred1;
        TextView txtred;

        public childViewHoder(View view) {
            this.imageView10 = (ImageView) view.findViewById(R.id.imageView10);
            this.txtred = (TextView) view.findViewById(R.id.txtred);
            this.textred1 = (TextView) view.findViewById(R.id.textred1);
            this.imagetop = (ImageView) view.findViewById(R.id.imagetop);
            this.imagetop2 = (ImageView) view.findViewById(R.id.imagetop2);
            this.img_view = (ImageView) view.findViewById(R.id.img_view);
            this.image_View2 = (ImageView) view.findViewById(R.id.image_View2);
            this.imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            this.imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            this.handoutimgUrl = (ImageView) view.findViewById(R.id.handoutimgUrl);
            this.handoutName = (TextView) view.findViewById(R.id.handoutName);
            this.handoutClass = (TextView) view.findViewById(R.id.handoutClass);
            this.handoutComment = (TextView) view.findViewById(R.id.handoutComment);
            this.relvel_item1 = (LinearLayout) view.findViewById(R.id.relvel_item1);
            this.relvel_item2 = (RelativeLayout) view.findViewById(R.id.relvel_item2);
            this.item2_title = (TextView) view.findViewById(R.id.item2_title);
            this.item2_class = (TextView) view.findViewById(R.id.item2_class);
            this.lin_img = (LinearLayout) view.findViewById(R.id.lin_img);
            this.item2_comment = (TextView) view.findViewById(R.id.item2_comment);
        }
    }

    public Handoutadapter(Context context, List<HandoutNewBean.DataBean.TopBean> list) {
        this.context = context;
        this.list = list;
        initColor();
    }

    private void initColor() {
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            this.redColors = ContextCompat.getColorStateList(this.context, R.color.red);
        } else {
            this.redColors = ContextCompat.getColorStateList(this.context, R.color.red_theme_night);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    public void getImageData(String content, String zhidingimg, String zuixinimg, String zuireimg, TextView mTextView) {
        String[] strArrSplit;
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
                    spannableStringBuilder.setSpan(new StickerSpan(new URLImageParser(mTextView, this.context, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
            try {
                if (TextUtils.isEmpty(this.content) || (strArrSplit = this.content.split("\\s")) == null || strArrSplit.length <= 0) {
                    return;
                }
                for (String str : strArrSplit) {
                    if (!TextUtils.isEmpty(str)) {
                        Matcher matcher2 = Pattern.compile(str.trim()).matcher(stringBuffer);
                        if (matcher2.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.redColors, null), matcher2.start(), matcher2.end(), 34);
                        }
                        Matcher matcher3 = Pattern.compile(str.toLowerCase().trim()).matcher(stringBuffer);
                        if (matcher3.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.redColors, null), matcher3.start(), matcher3.end(), 34);
                        }
                        Matcher matcher4 = Pattern.compile(str.toUpperCase().trim()).matcher(stringBuffer);
                        if (matcher4.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.redColors, null), matcher4.start(), matcher4.end(), 34);
                        }
                    }
                    mTextView.setText(spannableStringBuilder);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            mTextView.setText(content);
        }
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        View view;
        Object obj;
        if (convertView == null) {
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.activity_handlist, (ViewGroup) null);
            childviewhoder = new childViewHoder(viewInflate);
            viewInflate.setTag(childviewhoder);
            view = viewInflate;
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
            view = convertView;
        }
        childViewHoder childviewhoder2 = childviewhoder;
        List<String> cover = this.list.get(position).getCover();
        String is_long = this.list.get(position).getIs_long();
        if (cover.size() > 1 || is_long.equals("1")) {
            childviewhoder2.relvel_item2.setVisibility(0);
            childviewhoder2.relvel_item1.setVisibility(8);
            boolean zEquals = is_long.equals("1");
            int i2 = R.drawable.imgplacehodel_image;
            if (zEquals) {
                childviewhoder2.imageView10.setVisibility(0);
                childviewhoder2.lin_img.setVisibility(8);
                try {
                    Glide.with(childviewhoder2.imageView10.getContext()).load((Object) GlideUtils.generateUrl(cover.get(0))).placeholder(R.drawable.imgplacehodel_image).error(R.drawable.imgplacehodel_image).transform(new RoundedCorners(CommonUtil.dip2px(childviewhoder2.imageView10.getContext(), CommonUtil.dip2px(childviewhoder2.imageView10.getContext(), 2.0f)))).into(childviewhoder2.imageView10);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                childviewhoder2.lin_img.removeAllViews();
                childviewhoder2.imageView10.setVisibility(8);
                childviewhoder2.lin_img.setVisibility(0);
                int i3 = 0;
                while (i3 < cover.size()) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((CommonUtil.getScreenWidth(this.context) - 20) / 3, -1);
                    if (cover.size() < 2) {
                        if (i3 != 0) {
                            layoutParams.leftMargin = 10;
                        }
                    } else if (i3 < cover.size() - 1) {
                        layoutParams.leftMargin = 10;
                        layoutParams.rightMargin = 10;
                    }
                    layoutParams.weight = 1.0f;
                    SkinGrakImagView skinGrakImagView = new SkinGrakImagView(this.context);
                    skinGrakImagView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(skinGrakImagView.getContext()).load((Object) GlideUtils.generateUrl(cover.get(i3))).placeholder(i2).error(i2).transform(new RoundedCorners(CommonUtil.dip2px(skinGrakImagView.getContext(), CommonUtil.dip2px(skinGrakImagView.getContext(), 2.0f)))).into(skinGrakImagView);
                    childviewhoder2.lin_img.addView(skinGrakImagView, layoutParams);
                    i3++;
                    i2 = R.drawable.imgplacehodel_image;
                }
            }
            childviewhoder2.item2_comment.setText(String.format("%s评论", this.list.get(position).getComment_count()));
            try {
                getImageData(this.list.get(position).getTitle(), this.list.get(position).getTop_img(), this.list.get(position).getNew_img(), this.list.get(position).getHot_img(), childviewhoder2.item2_title);
                if (this.list.get(position).getAuthor_looked().equals("1")) {
                    childviewhoder2.textred1.setVisibility(0);
                } else {
                    childviewhoder2.textred1.setVisibility(8);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                childviewhoder2.item2_title.setText(this.list.get(position).getTitle());
            }
            childviewhoder2.item2_class.setText(this.list.get(position).getCname());
            try {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    if (this.list.get(position).getIs_read().equals("1")) {
                        childviewhoder2.item2_title.setTextColor(ContextCompat.getColor(this.context, R.color.line_txt_color));
                    } else {
                        childviewhoder2.item2_title.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                    }
                } else if (this.list.get(position).getIs_read().equals("1")) {
                    childviewhoder2.item2_title.setTextColor(ContextCompat.getColor(this.context, R.color.line_txt_color_night));
                } else {
                    childviewhoder2.item2_title.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else {
            childviewhoder2.relvel_item1.setVisibility(0);
            childviewhoder2.relvel_item2.setVisibility(8);
            if (!cover.isEmpty()) {
                Glide.with(childviewhoder2.handoutimgUrl.getContext()).load((Object) GlideUtils.generateUrl(cover.get(0))).transform(new RoundedCorners(CommonUtil.dip2px(childviewhoder2.handoutimgUrl.getContext(), CommonUtil.dip2px(childviewhoder2.handoutimgUrl.getContext(), 2.0f)))).into(childviewhoder2.handoutimgUrl);
            }
            try {
                obj = "1";
                try {
                    getImageData(this.list.get(position).getTitle(), this.list.get(position).getTop_img(), this.list.get(position).getNew_img(), this.list.get(position).getHot_img(), childviewhoder2.handoutName);
                    if (this.list.get(position).getAuthor_looked().equals(obj)) {
                        childviewhoder2.txtred.setVisibility(0);
                    } else {
                        childviewhoder2.txtred.setVisibility(8);
                    }
                } catch (Exception unused) {
                    childviewhoder2.handoutName.setText(this.list.get(position).getTitle());
                }
            } catch (Exception unused2) {
                obj = "1";
                childviewhoder2.handoutName.setText(this.list.get(position).getTitle());
            }
            try {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    if (this.list.get(position).getIs_read().equals(obj)) {
                        childviewhoder2.handoutName.setTextColor(ContextCompat.getColor(this.context, R.color.line_txt_color));
                    } else {
                        childviewhoder2.handoutName.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                    }
                } else if (this.list.get(position).getIs_read().equals(obj)) {
                    childviewhoder2.handoutName.setTextColor(ContextCompat.getColor(this.context, R.color.line_txt_color_night));
                } else {
                    childviewhoder2.handoutName.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            childviewhoder2.handoutClass.setText(this.list.get(position).getCname());
            childviewhoder2.handoutComment.setText(String.format("%s评论", this.list.get(position).getComment_count()));
        }
        return view;
    }

    public Handoutadapter(Context context, List<HandoutNewBean.DataBean.TopBean> list, String content) {
        this.context = context;
        this.list = list;
        this.content = content;
        initColor();
    }
}
