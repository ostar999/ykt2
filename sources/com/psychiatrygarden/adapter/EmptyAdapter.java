package com.psychiatrygarden.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.PinnedSectionListView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class EmptyAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private TextView tvContent;

    @Override // android.widget.Adapter
    public int getCount() {
        return 1;
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewInflate = LayoutInflater.from(ProjectApp.instance()).inflate(R.layout.adapter_empty_view, (ViewGroup) null);
        this.tvContent = (TextView) viewInflate.findViewById(R.id.tv_content);
        viewInflate.setLayoutParams(new AbsListView.LayoutParams(CommonUtil.getScreenWidth(ProjectApp.instance()), CommonUtil.getScreenHeight(ProjectApp.instance()) / 3));
        return viewInflate;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // com.psychiatrygarden.widget.PinnedSectionListView.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    public void setText(String text) {
        TextView textView = this.tvContent;
        if (textView != null) {
            textView.setText(text);
        }
    }
}
