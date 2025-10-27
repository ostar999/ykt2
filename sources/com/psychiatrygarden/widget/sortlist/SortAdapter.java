package com.psychiatrygarden.widget.sortlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.bean.SortModel;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.SkinGrakImagView;
import com.psychiatrygarden.widget.swipemenu.BaseSwipListAdapter;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class SortAdapter extends BaseSwipListAdapter implements SectionIndexer {
    private List<SortModel> list;
    private Context mContext;

    public static final class ViewHolder {
        SkinGrakImagView iv_avatar;
        TextView tvLetter;
        TextView tvTitle;
    }

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    private String getAlpha(String str) {
        String upperCase = str.trim().substring(0, 1).toUpperCase();
        return upperCase.matches("[A-Z]") ? upperCase : upperCase.equals("↑") ? "↑" : DictionaryFactory.SHARP;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int section) {
        for (int i2 = 0; i2 < getCount(); i2++) {
            if (this.list.get(i2).getSortLetters().toUpperCase().charAt(0) == section) {
                return i2;
            }
        }
        return -1;
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int position) {
        return this.list.get(position).getSortLetters().charAt(0);
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return null;
    }

    @Override // com.psychiatrygarden.widget.swipemenu.BaseSwipListAdapter
    public boolean getSwipEnableByPosition(int position) {
        return (this.list.get(position).getNickname().equals("产品客服") || this.list.get(position).getNickname().equals("技术支持")) ? false : true;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View view, ViewGroup arg2) {
        View viewInflate;
        ViewHolder viewHolder;
        SortModel sortModel = this.list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.item_sort_friends_list, (ViewGroup) null);
            viewHolder.tvTitle = (TextView) viewInflate.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) viewInflate.findViewById(R.id.catalog);
            viewHolder.iv_avatar = (SkinGrakImagView) viewInflate.findViewById(R.id.iv_avatar);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position == getPositionForSection(getSectionForPosition(position))) {
            viewHolder.tvLetter.setVisibility(0);
            viewHolder.tvLetter.setText(sortModel.getSortLetters());
            if (sortModel.getSortLetters().equals("↑")) {
                viewHolder.tvLetter.setVisibility(8);
            }
        } else {
            viewHolder.tvLetter.setVisibility(8);
        }
        viewHolder.tvTitle.setText(this.list.get(position).getNickname());
        GlideUtils.loadImage(this.mContext, this.list.get(position).getAvatar(), viewHolder.iv_avatar);
        return viewInflate;
    }

    public boolean isShowSort(int pos) {
        return true;
    }

    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
