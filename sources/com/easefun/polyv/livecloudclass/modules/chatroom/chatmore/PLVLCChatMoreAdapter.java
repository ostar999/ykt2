package com.easefun.polyv.livecloudclass.modules.chatroom.chatmore;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.plv.livescenes.model.interact.PLVChatFunctionVO;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCChatMoreAdapter extends RecyclerView.Adapter<ChatMoreViewHolder> {
    private Context context;
    private List<PLVChatFunctionVO> functionList;
    private int itemWidth;
    private OnItemClickListener listener;

    public static class ChatMoreViewHolder extends RecyclerView.ViewHolder {
        ImageView iconIv;
        RelativeLayout itemLayout;
        TextView nameTv;
        ImageView newIv;

        public ChatMoreViewHolder(View view) {
            super(view);
            this.iconIv = (ImageView) view.findViewById(R.id.plvlc_chat_more_item_icon);
            this.nameTv = (TextView) view.findViewById(R.id.plvlc_chat_more_item_name);
            this.itemLayout = (RelativeLayout) view.findViewById(R.id.plvlc_chat_more_item);
            this.newIv = (ImageView) view.findViewById(R.id.plvlc_chat_more_item_new);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String str);
    }

    public PLVLCChatMoreAdapter(int i2, Context context) {
        this.context = context;
        this.itemWidth = ScreenUtils.getScreenWidth() / i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.functionList.size();
    }

    public void setData(@NonNull List<PLVChatFunctionVO> list) {
        this.functionList = new ArrayList(list);
    }

    public void setListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void updateFunctionList(@NonNull List<PLVChatFunctionVO> list) {
        ArrayList arrayList = new ArrayList();
        for (PLVChatFunctionVO pLVChatFunctionVO : list) {
            if (pLVChatFunctionVO.isShow()) {
                arrayList.add(pLVChatFunctionVO);
            }
        }
        this.functionList = arrayList;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ChatMoreViewHolder chatMoreViewHolder, int i2) {
        final PLVChatFunctionVO pLVChatFunctionVO = this.functionList.get(i2);
        if (TextUtils.isEmpty(pLVChatFunctionVO.getIcon())) {
            chatMoreViewHolder.iconIv.setImageResource(pLVChatFunctionVO.getImageResourceId());
        } else {
            PLVImageLoader.getInstance().loadImage(pLVChatFunctionVO.getIcon(), chatMoreViewHolder.iconIv);
        }
        chatMoreViewHolder.iconIv.setSelected(pLVChatFunctionVO.isSelected());
        chatMoreViewHolder.nameTv.setText(pLVChatFunctionVO.getName());
        chatMoreViewHolder.newIv.setVisibility(4);
        chatMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCChatMoreAdapter.this.listener != null) {
                    PLVLCChatMoreAdapter.this.listener.onItemClick(pLVChatFunctionVO.getType());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ChatMoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        ChatMoreViewHolder chatMoreViewHolder = new ChatMoreViewHolder(LayoutInflater.from(this.context).inflate(R.layout.plvlc_chatroom_chat_more_item, viewGroup, false));
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) chatMoreViewHolder.itemView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = this.itemWidth;
        chatMoreViewHolder.itemView.setLayoutParams(layoutParams);
        return chatMoreViewHolder;
    }
}
