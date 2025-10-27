package com.psychiatrygarden.adapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.bean.HandoutNewBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class HandoutSearchAdapter extends BaseAdapter {
    private List<HandoutNewBean.DataBean.TopBean> list;
    private ArrayMap<Integer, Boolean> marginInitMap = new ArrayMap<>();
    private String searchContent;

    public static class HandoutSearchViewHolder {
        TextView commentNum;
        TextView content;
        TextView time;
        TextView title;

        public HandoutSearchViewHolder(View view) {
            TextView textView = (TextView) view.findViewById(R.id.title);
            this.title = textView;
            textView.setLineSpacing(CommonUtil.dip2px(view.getContext(), 3.0f), 1.0f);
            this.title.setTextSize(16.0f);
            TextView textView2 = (TextView) view.findViewById(R.id.tv_desc);
            this.content = textView2;
            textView2.setTextSize(14.0f);
            this.time = (TextView) view.findViewById(R.id.time);
            this.commentNum = (TextView) view.findViewById(R.id.commnum);
        }
    }

    public HandoutSearchAdapter(List<HandoutNewBean.DataBean.TopBean> data) {
        if (data == null || data.isEmpty()) {
            this.list = new ArrayList(0);
        } else {
            this.list = data;
        }
        this.marginInitMap.clear();
    }

    private Drawable buildPreTag(ViewGroup parent) {
        int color = ContextCompat.getColor(parent.getContext(), SkinManager.getCurrentSkinType(parent.getContext()) == 0 ? R.color.dominant_color : R.color.dominant_color_night);
        TextView textView = new TextView(parent.getContext());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        int iDip2px = CommonUtil.dip2px(parent.getContext(), 2.0f);
        textView.setPadding(iDip2px, 0, iDip2px, 0);
        textView.setText("经验");
        textView.setTextSize(11.0f);
        textView.setTextColor(color);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(CommonUtil.dip2px(parent.getContext(), 3.0f));
        gradientDrawable.setStroke(CommonUtil.dip2px(parent.getContext(), 0.6f), color);
        textView.setBackground(gradientDrawable);
        textView.setDrawingCacheEnabled(true);
        textView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.buildDrawingCache();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(textView.getDrawingCache());
        textView.destroyDrawingCache();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(parent.getContext().getResources(), bitmapCreateBitmap);
        bitmapDrawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        return bitmapDrawable;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<HandoutNewBean.DataBean.TopBean> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        List<HandoutNewBean.DataBean.TopBean> list = this.list;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.list.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        HandoutSearchViewHolder handoutSearchViewHolder;
        View viewInflate;
        HandoutNewBean.DataBean.TopBean topBean = this.list.get(position);
        if (convertView == null) {
            viewInflate = View.inflate(parent.getContext(), R.layout.item_search_handout, null);
            handoutSearchViewHolder = new HandoutSearchViewHolder(viewInflate);
            viewInflate.setTag(handoutSearchViewHolder);
        } else {
            handoutSearchViewHolder = (HandoutSearchViewHolder) convertView.getTag();
            viewInflate = convertView;
        }
        int i2 = 2;
        if (TextUtils.isEmpty(this.searchContent)) {
            handoutSearchViewHolder.title.setText(topBean.getTitle());
        } else {
            if (this.marginInitMap.get(Integer.valueOf(position)) == null) {
                int i3 = 0;
                while (i3 * handoutSearchViewHolder.title.getPaint().measureText(" ") <= CommonUtil.dip2px(parent.getContext(), 6.0f)) {
                    i3++;
                }
                StringBuilder sb = new StringBuilder();
                for (int i4 = 0; i4 < i3; i4++) {
                    sb.append(" ");
                }
                sb.append(topBean.getTitle());
                topBean.setTitle(sb.toString());
                this.marginInitMap.put(Integer.valueOf(position), Boolean.TRUE);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(" " + topBean.getTitle());
            spannableStringBuilder.setSpan(new VerticalImageSpan(buildPreTag(parent)), 0, 1, 18);
            String[] strArrSplit = this.searchContent.split("\\s+");
            int length = strArrSplit.length;
            int i5 = 0;
            while (i5 < length) {
                String strReplace = strArrSplit[i5].replace("\\s+", "");
                if (!TextUtils.isEmpty(strReplace)) {
                    Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, i2).matcher(spannableStringBuilder);
                    while (matcher.find()) {
                        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, ContextCompat.getColorStateList(parent.getContext(), SkinManager.getCurrentSkinType(parent.getContext()) == 0 ? R.color.dominant_color : R.color.dominant_color_night), null), matcher.start(0), matcher.end(0), 34);
                        strArrSplit = strArrSplit;
                    }
                }
                i5++;
                strArrSplit = strArrSplit;
                i2 = 2;
            }
            handoutSearchViewHolder.title.setText(spannableStringBuilder);
        }
        handoutSearchViewHolder.time.setText(topBean.getCtime());
        handoutSearchViewHolder.commentNum.setText(String.format("%s评论", topBean.getComment_count()));
        if (TextUtils.isEmpty(topBean.getContent())) {
            handoutSearchViewHolder.content.setVisibility(8);
        } else {
            handoutSearchViewHolder.content.setVisibility(0);
            if (TextUtils.isEmpty(this.searchContent)) {
                handoutSearchViewHolder.content.setText(topBean.getContent());
            } else {
                int color = ContextCompat.getColor(parent.getContext(), SkinManager.getCurrentSkinType(parent.getContext()) == 0 ? R.color.dominant_color : R.color.dominant_color_night);
                SpannableString spannableString = new SpannableString(topBean.getContent());
                Matcher matcher2 = Pattern.compile(StrPool.BRACKET_START + this.searchContent + StrPool.BRACKET_END, 2).matcher(topBean.getContent());
                while (matcher2.find()) {
                    spannableString.setSpan(new ForegroundColorSpan(color), matcher2.start(), matcher2.end(), 33);
                }
                handoutSearchViewHolder.content.setText(spannableString);
            }
        }
        return viewInflate;
    }

    public void setSearchContent(String content) {
        this.searchContent = content;
    }
}
