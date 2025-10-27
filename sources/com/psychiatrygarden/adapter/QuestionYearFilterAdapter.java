package com.psychiatrygarden.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionYearFilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String endYear;
    private String startYear;

    public QuestionYearFilterAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.startYear = "";
        this.endYear = "";
    }

    public void setSelectYear(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String dataBean) throws NumberFormatException {
        int color;
        Drawable colorDrawable;
        int color2;
        Drawable colorDrawable2;
        TextView textView = (TextView) helper.getView(R.id.tv_year);
        String str = "#1a1a1a";
        textView.setTextColor(Color.parseColor("#1a1a1a"));
        textView.setText(dataBean);
        textView.setLayoutParams(new RelativeLayout.LayoutParams((ScreenUtil.getScreenWidth((Activity) getContext()) - ScreenUtil.getPxByDp(getContext(), 35)) / 6, -2));
        try {
            if (TextUtils.isEmpty(this.startYear)) {
                if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                    str = "#7380A9";
                }
                textView.setTextColor(Color.parseColor(str));
                textView.setBackgroundColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT) : 0);
                return;
            }
            int i2 = Integer.parseInt(this.startYear);
            int i3 = Integer.parseInt(dataBean);
            if (TextUtils.isEmpty(this.endYear)) {
                if (i2 == i3) {
                    textView.setBackgroundResource(R.drawable.ffcd6151_left_4);
                    textView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                    return;
                } else {
                    textView.setBackgroundColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT) : 0);
                    if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                        str = "#7380A9";
                    }
                    textView.setTextColor(Color.parseColor(str));
                    return;
                }
            }
            int i4 = Integer.parseInt(this.endYear);
            String str2 = "#f2f2f2";
            if (i4 > i2) {
                if (i2 == i3 || i4 == i3) {
                    color2 = Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT);
                } else {
                    if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                        str = "#7380A9";
                    }
                    color2 = Color.parseColor(str);
                }
                if (i2 < i3 && i3 < i4) {
                    if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                        str2 = "#2E3241";
                    }
                    colorDrawable2 = new ColorDrawable(Color.parseColor(str2));
                } else if (i2 == i3) {
                    colorDrawable2 = ContextCompat.getDrawable(getContext(), R.drawable.ffcd6151_left_4);
                } else if (i4 == i3) {
                    colorDrawable2 = ContextCompat.getDrawable(getContext(), R.drawable.ffcd6151_right_4);
                } else {
                    colorDrawable2 = new ColorDrawable(SkinManager.getCurrentSkinType(getContext()) == 0 ? Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT) : 0);
                }
                textView.setTextColor(color2);
                textView.setBackground(colorDrawable2);
                return;
            }
            if (i4 >= i2) {
                if (i2 == i3) {
                    textView.setBackgroundResource(R.drawable.ffcd6151_4);
                    textView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                    return;
                } else {
                    textView.setBackgroundColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT) : 0);
                    if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                        str = "#7380A9";
                    }
                    textView.setTextColor(Color.parseColor(str));
                    return;
                }
            }
            if (i2 == i3 || i4 == i3) {
                color = -1;
            } else {
                if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                    str = "#7380A9";
                }
                color = Color.parseColor(str);
            }
            if (i2 == i3) {
                colorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ffcd6151_left_4);
            } else if (i4 == i3) {
                colorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ffcd6151_right_4);
            } else if (i4 >= i3 || i3 >= i2) {
                colorDrawable = new ColorDrawable(SkinManager.getCurrentSkinType(getContext()) == 0 ? Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT) : 0);
            } else {
                if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                    str2 = "#2E3241";
                }
                colorDrawable = new ColorDrawable(Color.parseColor(str2));
            }
            textView.setTextColor(color);
            textView.setBackground(colorDrawable);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
