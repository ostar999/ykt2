package com.psychiatrygarden.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.psychiatrygarden.bean.Bean;
import com.psychiatrygarden.widget.PinnedSectionListView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class AdapterListView2 extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private Context context;
    private ArrayList<Bean> list;

    public static class ViewHolder {
        public TextView findTv;
        LinearLayout findr1;
        public TextView title;
        public TextView tv_today_score;
    }

    public AdapterListView2(Context context, ArrayList<Bean> list) {
        setList(list);
        this.context = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    public ArrayList<Bean> getList() {
        return this.list;
    }

    @Override // android.widget.Adapter
    @SuppressLint({"NewApi"})
    public View getView(int position, View converView, ViewGroup viewGrop) {
        View viewInflate;
        ViewHolder viewHolder;
        String strReplace;
        if (converView == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.context).inflate(R.layout.todaypoints, (ViewGroup) null);
            viewHolder.findTv = (TextView) viewInflate.findViewById(R.id.findTv);
            viewHolder.title = (TextView) viewInflate.findViewById(R.id.title);
            viewHolder.tv_today_score = (TextView) viewInflate.findViewById(R.id.tv_today_score);
            viewHolder.findTv = (TextView) viewInflate.findViewById(R.id.findTv);
            viewHolder.findr1 = (LinearLayout) viewInflate.findViewById(R.id.findr1);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = converView;
            viewHolder = (ViewHolder) converView.getTag();
        }
        Bean item = getItem(position);
        if (item.type == 1) {
            viewHolder.findTv.setText(item.text);
            if (item.text.equals("")) {
                viewHolder.findTv.setVisibility(8);
            } else {
                viewHolder.findTv.setVisibility(0);
            }
            viewHolder.findr1.setVisibility(8);
        } else {
            viewHolder.findTv.setVisibility(8);
            viewHolder.findr1.setVisibility(0);
            viewHolder.title.setText(item.getTitle());
            try {
                if (item.getScore().equals("0")) {
                    strReplace = "<font  color='#bababa'>" + item.getScore() + "</font>";
                } else {
                    strReplace = item.getScore().replace("分", "<font  color='#bababa'>分</font>");
                }
                viewHolder.tv_today_score.setText(Html.fromHtml(strReplace));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return viewInflate;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // com.psychiatrygarden.widget.PinnedSectionListView.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public void refresh(ArrayList<Bean> arr) {
        setList(arr);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<Bean> list) {
        if (list == null) {
            list = new ArrayList<>(0);
        }
        this.list = list;
    }

    @Override // android.widget.Adapter
    public Bean getItem(int position) {
        return this.list.get(position);
    }
}
