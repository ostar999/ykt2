package com.hyphenate.easeui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.modules.chat.EaseChatExtendMenu;
import com.hyphenate.easeui.modules.chat.interfaces.EaseChatExtendMenuItemClickListener;
import com.hyphenate.easeui.utils.DarkModeHelper;

/* loaded from: classes4.dex */
public class EaseChatExtendMenuAdapter extends EaseBaseChatExtendMenuAdapter<ViewHolder, EaseChatExtendMenu.ChatMenuItemModel> {
    private OnItemClickListener itemListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private RelativeLayout rlIcon;
        private TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.textView = (TextView) view.findViewById(R.id.text);
            this.rlIcon = (RelativeLayout) view.findViewById(R.id.rl_icon);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(EaseChatExtendMenu.ChatMenuItemModel chatMenuItemModel, int i2, View view) {
        EaseChatExtendMenuItemClickListener easeChatExtendMenuItemClickListener = chatMenuItemModel.clickListener;
        if (easeChatExtendMenuItemClickListener != null) {
            easeChatExtendMenuItemClickListener.onChatExtendMenuItemClick(chatMenuItemModel.id, view);
        }
        OnItemClickListener onItemClickListener = this.itemListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, i2);
        }
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseChatExtendMenuAdapter
    public int getItemLayoutId() {
        return R.layout.ease_chat_menu_item;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemListener = onItemClickListener;
    }

    @Override // com.hyphenate.easeui.adapter.EaseBaseChatExtendMenuAdapter
    public ViewHolder easeCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i2) {
        final EaseChatExtendMenu.ChatMenuItemModel chatMenuItemModel = (EaseChatExtendMenu.ChatMenuItemModel) this.mData.get(i2);
        viewHolder.imageView.setImageResource(chatMenuItemModel.image);
        viewHolder.rlIcon.setBackgroundColor(DarkModeHelper.isDarkMode(viewHolder.imageView.getContext()) ? Color.parseColor("#171C2D") : -1);
        viewHolder.textView.setTextColor(Color.parseColor(DarkModeHelper.isDarkMode(viewHolder.imageView.getContext()) ? "#7380a9" : "#ff999999"));
        viewHolder.textView.setText(chatMenuItemModel.name);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.adapter.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f8640c.lambda$onBindViewHolder$0(chatMenuItemModel, i2, view);
            }
        });
    }
}
