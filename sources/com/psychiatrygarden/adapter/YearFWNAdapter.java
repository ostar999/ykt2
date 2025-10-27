package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.psychiatrygarden.bean.QuestionYearBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class YearFWNAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<QuestionYearBean> mList;
    private String type;

    public static class ViewHolder {
        TextView tv_question_num;
        TextView tv_year;
    }

    public YearFWNAdapter(Context context, List<QuestionYearBean> list, String type) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
        this.context = context;
        this.type = type;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.adapter_year_fwn, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.tv_year = (TextView) convertView.findViewById(R.id.tv_year);
            viewHolder.tv_question_num = (TextView) convertView.findViewById(R.id.tv_question_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_year.setText(this.mList.get(position).getYear());
        String str = this.type;
        str.hashCode();
        switch (str) {
            case "note":
                viewHolder.tv_question_num.setText(String.format("%s条笔记", this.mList.get(position).getTotal()));
                return convertView;
            case "error":
                viewHolder.tv_question_num.setText(String.format("错%s题", this.mList.get(position).getTotal()));
                return convertView;
            case "collect":
                viewHolder.tv_question_num.setText(String.format("收藏%s题", this.mList.get(position).getTotal()));
                return convertView;
            default:
                return convertView;
        }
    }

    public void setListData(List<QuestionYearBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
