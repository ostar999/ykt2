package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class EmojiconGridAdapter extends ArrayAdapter<EaseEmojicon> {
    private EaseEmojicon.Type emojiconType;

    public EmojiconGridAdapter(Context context, int i2, List<EaseEmojicon> list, EaseEmojicon.Type type) {
        super(context, i2, list);
        this.emojiconType = type;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.emojiconType == EaseEmojicon.Type.BIG_EXPRESSION ? View.inflate(getContext(), R.layout.ease_row_big_expression, null) : View.inflate(getContext(), R.layout.ease_row_expression, null);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_expression);
        TextView textView = (TextView) view.findViewById(R.id.tv_name);
        EaseEmojicon easeEmojicon = (EaseEmojicon) getItem(i2);
        if (textView != null && easeEmojicon.getName() != null) {
            textView.setText(easeEmojicon.getName());
        }
        if (EaseSmileUtils.DELETE_KEY.equals(easeEmojicon.getEmojiText())) {
            imageView.setImageResource(DarkModeHelper.isDarkMode(getContext()) ? R.drawable.ease_delete_expression_night : R.drawable.ease_delete_expression);
        } else if (easeEmojicon.getIcon() != 0) {
            imageView.setImageResource(easeEmojicon.getIcon());
        } else if (easeEmojicon.getIconPath() != null) {
            Glide.with(getContext()).load(easeEmojicon.getIconPath()).apply((BaseRequestOptions<?>) RequestOptions.placeholderOf(R.drawable.ease_default_expression)).into(imageView);
        }
        return view;
    }
}
