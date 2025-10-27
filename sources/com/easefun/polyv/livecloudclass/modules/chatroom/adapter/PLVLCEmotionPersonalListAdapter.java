package com.easefun.polyv.livecloudclass.modules.chatroom.adapter;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.plv.livescenes.model.PLVEmotionImageVO;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCEmotionPersonalListAdapter extends RecyclerView.Adapter<PersonalItemViewHolder> {
    private List<PLVEmotionImageVO.EmotionImage> emotionPersonalList;
    private OnViewActionListener onViewActionListener;

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
        void onEmotionViewClick(PLVEmotionImageVO.EmotionImage emotionImage);

        void onEmotionViewLongClick(PLVEmotionImageVO.EmotionImage emotionImage, View view);
    }

    public class PersonalItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView personalEmotionIv;
        private TextView personalEmotionNameTv;

        public PersonalItemViewHolder(View view) {
            super(view);
            this.personalEmotionIv = (ImageView) view.findViewById(R.id.emotion_iv);
            this.personalEmotionNameTv = (TextView) view.findViewById(R.id.emotion_name_tv);
        }
    }

    public PLVLCEmotionPersonalListAdapter(List<PLVEmotionImageVO.EmotionImage> list) {
        this.emotionPersonalList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.emotionPersonalList.size();
    }

    public void setOnViewActionListener(OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PersonalItemViewHolder personalItemViewHolder, int i2) {
        final PLVEmotionImageVO.EmotionImage emotionImage = this.emotionPersonalList.get(i2);
        PLVImageLoader.getInstance().loadImage(emotionImage.getUrl(), personalItemViewHolder.personalEmotionIv);
        personalItemViewHolder.personalEmotionNameTv.setText(emotionImage.getTitle());
        personalItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCEmotionPersonalListAdapter.this.onViewActionListener != null) {
                    PLVLCEmotionPersonalListAdapter.this.onViewActionListener.onEmotionViewClick(emotionImage);
                }
            }
        });
        personalItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (PLVLCEmotionPersonalListAdapter.this.onViewActionListener == null) {
                    return false;
                }
                PLVLCEmotionPersonalListAdapter.this.onViewActionListener.onEmotionViewLongClick(emotionImage, view);
                return true;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PersonalItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        return new PersonalItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plvlc_chatroom_emoji_personal_item, viewGroup, false));
    }
}
