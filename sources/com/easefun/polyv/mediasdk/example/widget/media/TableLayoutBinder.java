package com.easefun.polyv.mediasdk.example.widget.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.easefun.polyv.mediasdk.player.R;

/* loaded from: classes3.dex */
public class TableLayoutBinder {
    private Context mContext;
    public TableLayout mTableLayout;
    public ViewGroup mTableView;

    public static class ViewHolder {
        public TextView mNameTextView;
        public TextView mValueTextView;

        private ViewHolder() {
        }

        public void setName(String str) {
            TextView textView = this.mNameTextView;
            if (textView != null) {
                textView.setText(str);
            }
        }

        public void setValue(String str) {
            TextView textView = this.mValueTextView;
            if (textView != null) {
                textView.setText(str);
            }
        }
    }

    public TableLayoutBinder(Context context) {
        this(context, R.layout.table_media_info);
    }

    public View appendRow(int i2, String str, String str2) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.mContext).inflate(i2, (ViewGroup) this.mTableLayout, false);
        setNameValueText(viewGroup, str, str2);
        this.mTableLayout.addView(viewGroup);
        return viewGroup;
    }

    public View appendRow1(String str, String str2) {
        return appendRow(R.layout.table_media_info_row1, str, str2);
    }

    public View appendRow2(String str, String str2) {
        return appendRow(R.layout.table_media_info_row2, str, str2);
    }

    public View appendSection(String str) {
        return appendRow(R.layout.table_media_info_section, str, null);
    }

    public AlertDialog.Builder buildAlertDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setView(buildLayout());
        return builder;
    }

    public ViewGroup buildLayout() {
        return this.mTableView;
    }

    public ViewHolder obtainViewHolder(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null) {
            return viewHolder;
        }
        ViewHolder viewHolder2 = new ViewHolder();
        viewHolder2.mNameTextView = (TextView) view.findViewById(R.id.name);
        viewHolder2.mValueTextView = (TextView) view.findViewById(R.id.value);
        view.setTag(viewHolder2);
        return viewHolder2;
    }

    public void setNameValueText(View view, String str, String str2) {
        ViewHolder viewHolderObtainViewHolder = obtainViewHolder(view);
        viewHolderObtainViewHolder.setName(str);
        viewHolderObtainViewHolder.setValue(str2);
    }

    public void setValueText(View view, String str) {
        obtainViewHolder(view).setValue(str);
    }

    public TableLayoutBinder(Context context, int i2) {
        this.mContext = context;
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(i2, (ViewGroup) null);
        this.mTableView = viewGroup;
        this.mTableLayout = (TableLayout) viewGroup.findViewById(R.id.table);
    }

    public View appendRow1(int i2, String str) {
        return appendRow1(this.mContext.getString(i2), str);
    }

    public View appendRow2(int i2, String str) {
        return appendRow2(this.mContext.getString(i2), str);
    }

    public View appendSection(int i2) {
        return appendSection(this.mContext.getString(i2));
    }

    public TableLayoutBinder(Context context, TableLayout tableLayout) {
        this.mContext = context;
        this.mTableView = tableLayout;
        this.mTableLayout = tableLayout;
    }
}
