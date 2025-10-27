package com.ykb.ebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ruffian.library.widget.RImageView;
import com.ykb.ebook.R;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.weight.PinnedSectionListView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class BookReviewAdps extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private Context mContext;
    private OnItemActionLisenter onItemActionLisenter;
    private List<BookReview> mDatas = new ArrayList();
    boolean isHaveHot = false;
    public boolean isShowNews = false;

    public static abstract class OnItemActionLisenter {
        public abstract void onItemClickAction(int i2, BookReview bookReview);

        public abstract void onItemHotAndNewsAction(boolean z2);

        public abstract void onItemOpposeAction(int i2, BookReview bookReview);

        public abstract void onItemPicAction(int i2, BookReview bookReview);

        public abstract void onItemReplyAction(int i2, BookReview bookReview);

        public abstract void onItemSupportAction(int i2, BookReview bookReview);
    }

    public class ViewHolder {
        TextView btnHot;
        TextView btnNews;
        RImageView imgAvatar;
        RImageView imgPicture;
        ImageView ivReviewFace;
        LinearLayout lyContentView;
        LinearLayout lyMoreView;
        LinearLayout lyTabView;
        LinearLayout lyTitleView;
        TextView tvComment;
        TextView tvHospital;
        TextView tvMoreValue;
        TextView tvNickName;
        TextView tvOpposition;
        TextView tvRate;
        TextView tvReply;
        TextView tvSupport;
        TextView tvTime;
        TextView tvTitleView;

        public ViewHolder() {
        }
    }

    public BookReviewAdps(Context context) {
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(ViewHolder viewHolder, View view) {
        viewHolder.btnHot.setTextColor(this.mContext.getColor(R.color.color_303030));
        viewHolder.btnHot.setBackgroundResource(R.drawable.shape_white_bg);
        viewHolder.btnNews.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
        viewHolder.btnNews.setBackgroundResource(R.drawable.shape_hot_news_tab_normal_bg);
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemHotAndNewsAction(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$1(ViewHolder viewHolder, View view) {
        viewHolder.btnNews.setTextColor(this.mContext.getColor(R.color.color_303030));
        viewHolder.btnNews.setBackgroundResource(R.drawable.shape_white_bg);
        viewHolder.btnHot.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
        viewHolder.btnHot.setBackgroundResource(R.drawable.shape_hot_news_tab_normal_bg);
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemHotAndNewsAction(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$2(View view) {
        String str = (String) view.getTag();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage(view.getContext().getPackageName());
        intent.setData(Uri.parse("ebook://ykb_user_info/?user_id=" + str));
        view.getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$3(int i2, BookReview bookReview, View view) {
        Log.e("click_item", "点击了赞同");
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemSupportAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$4(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemOpposeAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$5(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemReplyAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$6(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemPicAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$7(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemClickAction(i2, bookReview);
        }
    }

    public void addData(List<BookReview> list) {
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mDatas.size();
    }

    public List<BookReview> getData() {
        return this.mDatas;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        return this.mDatas.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i2) {
        return ((BookReview) getItem(i2)).getType();
    }

    @Override // android.widget.Adapter
    public View getView(final int i2, View view, ViewGroup viewGroup) {
        View viewInflate;
        final ViewHolder viewHolder;
        String str;
        if (view == null) {
            viewHolder = new ViewHolder();
            viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.item_book_reviews, (ViewGroup) null);
            viewHolder.imgAvatar = (RImageView) viewInflate.findViewById(R.id.img_avatar);
            viewHolder.lyTitleView = (LinearLayout) viewInflate.findViewById(R.id.ly_title_view);
            viewHolder.tvTitleView = (TextView) viewInflate.findViewById(R.id.tv_newest_review);
            viewHolder.lyMoreView = (LinearLayout) viewInflate.findViewById(R.id.ly_look_more);
            viewHolder.tvMoreValue = (TextView) viewInflate.findViewById(R.id.tv_more_value);
            viewHolder.lyContentView = (LinearLayout) viewInflate.findViewById(R.id.ly_content_view);
            viewHolder.tvSupport = (TextView) viewInflate.findViewById(R.id.tv_support);
            viewHolder.tvOpposition = (TextView) viewInflate.findViewById(R.id.tv_opposition);
            viewHolder.imgPicture = (RImageView) viewInflate.findViewById(R.id.img_picture);
            viewHolder.tvNickName = (TextView) viewInflate.findViewById(R.id.tv_nick_name);
            viewHolder.tvHospital = (TextView) viewInflate.findViewById(R.id.tv_hospital);
            viewHolder.tvTime = (TextView) viewInflate.findViewById(R.id.tv_time);
            viewHolder.tvComment = (TextView) viewInflate.findViewById(R.id.tv_comment);
            viewHolder.tvReply = (TextView) viewInflate.findViewById(R.id.tv_reply);
            viewHolder.lyTabView = (LinearLayout) viewInflate.findViewById(R.id.ly_tab_view);
            viewHolder.btnHot = (TextView) viewInflate.findViewById(R.id.btn_hot);
            viewHolder.btnNews = (TextView) viewInflate.findViewById(R.id.btn_news);
            viewHolder.tvRate = (TextView) viewInflate.findViewById(R.id.tv_high);
            viewHolder.ivReviewFace = (ImageView) viewInflate.findViewById(R.id.iv_review_face);
            viewInflate.setTag(viewHolder);
        } else {
            viewInflate = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        final BookReview bookReview = this.mDatas.get(i2);
        if (bookReview.getType() == 1) {
            viewHolder.lyTitleView.setVisibility(0);
            viewHolder.lyContentView.setVisibility(8);
            viewHolder.tvTitleView.setText(bookReview.getShowName());
            if (bookReview.getShowName().contains("最热评论")) {
                viewHolder.lyTabView.setVisibility(0);
                viewHolder.btnHot.setTextColor(this.mContext.getColor(R.color.color_303030));
                viewHolder.btnHot.setBackgroundResource(R.drawable.shape_white_bg);
                viewHolder.btnNews.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
                viewHolder.btnNews.setBackgroundResource(R.drawable.shape_hot_news_tab_normal_bg);
            } else if (this.isShowNews) {
                viewHolder.lyTabView.setVisibility(0);
                viewHolder.btnNews.setTextColor(this.mContext.getColor(R.color.color_303030));
                viewHolder.btnNews.setBackgroundResource(R.drawable.shape_white_bg);
                viewHolder.btnHot.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
                viewHolder.btnHot.setBackgroundResource(R.drawable.shape_hot_news_tab_normal_bg);
            } else {
                viewHolder.lyTabView.setVisibility(8);
            }
            viewHolder.btnHot.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f26263c.lambda$getView$0(viewHolder, view2);
                }
            });
            viewHolder.btnNews.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f26265c.lambda$getView$1(viewHolder, view2);
                }
            });
        } else {
            viewHolder.lyTitleView.setVisibility(8);
            viewHolder.lyContentView.setVisibility(0);
        }
        if (bookReview.getOtherView() == 3) {
            viewHolder.lyMoreView.setVisibility(0);
            viewHolder.tvMoreValue.setText(bookReview.getShowName());
        } else {
            viewHolder.lyMoreView.setVisibility(8);
        }
        Glide.with(this.mContext).load(bookReview.getAvatar()).placeholder(R.drawable.personal_headimg_icon).into(viewHolder.imgAvatar);
        viewHolder.tvSupport.setText("赞同(" + bookReview.getSupportCount() + ")");
        if (bookReview.isSupport() == 1) {
            viewHolder.tvSupport.setTextColor(this.mContext.getColor(R.color.color_81cb30));
        } else {
            viewHolder.tvSupport.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
        }
        viewHolder.tvOpposition.setText("反对(" + bookReview.getOppositionCount() + ")");
        if (bookReview.isOpposition() == 1) {
            viewHolder.tvOpposition.setTextColor(this.mContext.getColor(R.color.color_81cb30));
        } else {
            viewHolder.tvOpposition.setTextColor(this.mContext.getColor(R.color.color_bfbfbf));
        }
        viewHolder.tvNickName.setText(bookReview.getNickname());
        viewHolder.tvTime.setText(bookReview.getCtime());
        viewHolder.tvHospital.setText(bookReview.getSchool());
        viewHolder.tvComment.setText(bookReview.getComment());
        viewHolder.imgPicture.setVisibility(bookReview.getPicture().isEmpty() ? 8 : 0);
        if (!TextUtils.isEmpty(bookReview.getPicture())) {
            Glide.with(this.mContext).load(bookReview.getPicture()).into(viewHolder.imgAvatar);
        }
        viewHolder.imgAvatar.setTag(bookReview.getUserId());
        viewHolder.imgAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdps.lambda$getView$2(view2);
            }
        });
        viewHolder.tvRate.setVisibility(bookReview.getReviewType().equals("2") ? 0 : 8);
        viewHolder.ivReviewFace.setVisibility(bookReview.getReviewType().equals("2") ? 0 : 8);
        if (bookReview.getRate().equals("1")) {
            viewHolder.tvRate.setText("高评价");
            viewHolder.ivReviewFace.setImageResource(R.drawable.icon_good_review);
        } else if (bookReview.getRate().equals("2")) {
            viewHolder.tvRate.setText("中评价");
            viewHolder.ivReviewFace.setImageResource(R.drawable.icon_middle_review);
        } else if (bookReview.getRate().equals("3")) {
            viewHolder.tvRate.setText("低评价");
            viewHolder.ivReviewFace.setImageResource(R.drawable.icon_low_review);
        }
        TextView textView = viewHolder.tvReply;
        if (bookReview.getReplyCount() > 0) {
            str = "回复（" + bookReview.getReplyCount() + "）";
        } else {
            str = "回复";
        }
        textView.setText(str);
        if (bookReview.getReplyCount() > 0) {
            viewHolder.tvReply.setTextColor(this.mContext.getColor(R.color.color_303030));
            viewHolder.tvReply.setBackgroundResource(R.drawable.shape_reply_selected_bg);
        } else {
            viewHolder.tvReply.setTextColor(this.mContext.getColor(R.color.color_c2c6cb));
            viewHolder.tvReply.setBackgroundResource(R.color.transparent);
        }
        viewHolder.tvSupport.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f26267c.lambda$getView$3(i2, bookReview, view2);
            }
        });
        viewHolder.tvOpposition.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f26270c.lambda$getView$4(i2, bookReview, view2);
            }
        });
        viewHolder.tvReply.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f26273c.lambda$getView$5(i2, bookReview, view2);
            }
        });
        viewHolder.imgPicture.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f26276c.lambda$getView$6(i2, bookReview, view2);
            }
        });
        viewHolder.lyContentView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f26279c.lambda$getView$7(i2, bookReview, view2);
            }
        });
        return viewInflate;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // com.ykb.ebook.weight.PinnedSectionListView.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int i2) {
        return i2 == 1;
    }

    public void setData(List<BookReview> list) {
        this.mDatas.clear();
        this.mDatas = list;
        notifyDataSetChanged();
    }

    public void setOnItemActionLisenter(OnItemActionLisenter onItemActionLisenter) {
        this.onItemActionLisenter = onItemActionLisenter;
    }
}
