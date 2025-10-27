package com.psychiatrygarden.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.lang.RegexPool;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestListAdapter extends BaseAdapter {
    private Context context;
    Handler mHandler;
    List<ExesQuestionBean> questBeans;
    int type;

    public static class ViewHoder {
        private TextView tvIndex;

        public ViewHoder(View view) {
            this.tvIndex = (TextView) view.findViewById(R.id.textView1);
        }
    }

    public QuestListAdapter(Context context, List<ExesQuestionBean> mqBeans, Handler mHandler, int type) {
        this.context = context;
        this.questBeans = mqBeans;
        this.mHandler = mHandler;
        this.type = type;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.questBeans.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int arg0) {
        return this.questBeans.get(arg0);
    }

    @Override // android.widget.Adapter
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override // android.widget.Adapter
    public View getView(final int arg0, View view, ViewGroup arg2) {
        ViewHoder viewHoder;
        String strValueOf;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.questrinlist, (ViewGroup) null);
            viewHoder = new ViewHoder(view);
            view.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) view.getTag();
        }
        boolean z2 = SkinManager.getCurrentSkinType(this.context) == 0;
        List<ExesQuestionBean> list = this.questBeans;
        int i2 = R.color.gray_font;
        int i3 = R.drawable.grey_yuan;
        if (list != null) {
            int i4 = this.type;
            int i5 = R.color.white;
            if (i4 == 2) {
                if (list.get(arg0).getIsRight().equals("1")) {
                    viewHoder.tvIndex.setBackgroundResource(z2 ? R.drawable.green_yuan : R.drawable.green_yuan_night);
                } else {
                    viewHoder.tvIndex.setBackgroundResource(z2 ? R.drawable.red_yuan : R.drawable.red_yuan_night_new);
                }
                viewHoder.tvIndex.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            } else if (list.get(arg0).getOwnAns() == null || this.questBeans.get(arg0).getOwnAns().equals("")) {
                TextView textView = viewHoder.tvIndex;
                if (!z2) {
                    i3 = R.drawable.grey_yuan_night;
                }
                textView.setBackgroundResource(i3);
                TextView textView2 = viewHoder.tvIndex;
                Context context = this.context;
                if (!z2) {
                    i2 = R.color.question_color_night;
                }
                textView2.setTextColor(ContextCompat.getColor(context, i2));
            } else {
                viewHoder.tvIndex.setBackgroundResource(z2 ? R.drawable.gray_yuan2 : R.drawable.gray_yuan2_night);
                TextView textView3 = viewHoder.tvIndex;
                Context context2 = this.context;
                if (!z2) {
                    i5 = R.color.question_color_night;
                }
                textView3.setTextColor(ContextCompat.getColor(context2, i5));
            }
        } else {
            viewHoder.tvIndex.setBackgroundResource(R.drawable.grey_yuan);
            viewHoder.tvIndex.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font));
        }
        int i6 = arg0 + 1;
        String number = this.questBeans.get(arg0).getNumber();
        if (number == null || !number.matches(RegexPool.NUMBERS)) {
            TextView textView4 = viewHoder.tvIndex;
            if (i6 < 10) {
                strValueOf = "0" + i6;
            } else {
                strValueOf = String.valueOf(i6);
            }
            textView4.setText(strValueOf);
        } else {
            viewHoder.tvIndex.setText(number);
        }
        return view;
    }

    public void updateData(List<ExesQuestionBean> questBeans) {
        this.questBeans = questBeans;
        notifyDataSetChanged();
    }
}
