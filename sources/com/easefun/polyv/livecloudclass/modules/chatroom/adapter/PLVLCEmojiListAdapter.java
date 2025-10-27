package com.easefun.polyv.livecloudclass.modules.chatroom.adapter;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.span.PLVFaceManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCEmojiListAdapter extends RecyclerView.Adapter<EmojiItemViewHolder> {
    private List<String> emoLists = new ArrayList(PLVFaceManager.getInstance().getFaceMap().keySet());
    private OnViewActionListener onViewActionListener;

    public class EmojiItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView emo;
        private String item;

        public EmojiItemViewHolder(View view) {
            super(view);
            this.emo = (ImageView) view.findViewById(R.id.emoji_iv);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmojiListAdapter.EmojiItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (PLVLCEmojiListAdapter.this.onViewActionListener != null) {
                        PLVLCEmojiListAdapter.this.onViewActionListener.onEmojiViewClick((String) PLVLCEmojiListAdapter.this.emoLists.get(EmojiItemViewHolder.this.getVHPosition()));
                    }
                }
            });
        }

        public int getVHPosition() {
            for (int i2 = 0; i2 < PLVLCEmojiListAdapter.this.emoLists.size(); i2++) {
                if (PLVLCEmojiListAdapter.this.emoLists.get(i2) == this.item) {
                    return i2;
                }
            }
            return 0;
        }

        public void processData(String str, int i2) throws Resources.NotFoundException {
            this.item = str;
            this.emo.setImageDrawable(this.itemView.getContext().getResources().getDrawable(PLVFaceManager.getInstance().getFaceId(str)));
        }
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int i2, int i3, boolean z2) {
            this.spanCount = i2;
            this.spacing = i3;
            this.includeEdge = z2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i2 = this.spanCount;
            int i3 = childAdapterPosition % i2;
            if (this.includeEdge) {
                int i4 = this.spacing;
                rect.left = i4 - ((i3 * i4) / i2);
                rect.right = ((i3 + 1) * i4) / i2;
                if (childAdapterPosition < i2) {
                    rect.top = i4;
                }
                rect.bottom = i4;
                return;
            }
            int i5 = this.spacing;
            rect.left = (i3 * i5) / i2;
            rect.right = i5 - (((i3 + 1) * i5) / i2);
            if (childAdapterPosition >= i2) {
                rect.top = i5;
            }
        }
    }

    public interface OnViewActionListener {
        void onEmojiViewClick(String str);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.emoLists.size();
    }

    public void setOnViewActionListener(OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(EmojiItemViewHolder emojiItemViewHolder, int i2) throws Resources.NotFoundException {
        emojiItemViewHolder.processData(this.emoLists.get(i2), i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public EmojiItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return new EmojiItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_chatroom_emoji_item, viewGroup, false));
    }
}
