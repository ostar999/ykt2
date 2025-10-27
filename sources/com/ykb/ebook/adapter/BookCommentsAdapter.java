package com.ykb.ebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.bumptech.glide.Glide;
import com.ruffian.library.widget.RImageView;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.SubFloorComments;
import com.ykb.ebook.weight.FloorCommentListenter;
import com.ykb.ebook.weight.FloorViews;
import com.ykb.ebook.weight.SubFloorFactorys;

/* loaded from: classes6.dex */
public class BookCommentsAdapter extends BaseQuickAdapter<BookReview, BookViewHolder> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private OnItemActionLisenter onItemActionLisenter;

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        FloorViews floor;
        RImageView imgAvatar;
        RImageView imgPicture;
        ImageView ivReviewFace;
        View line;
        LinearLayout lyContentView;
        LinearLayout lyMoreView;
        RelativeLayout lyTitleView;
        ImageView personalVipIv;
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
        TextView tvViewAll;
        View v_line;

        public BookViewHolder(@NonNull View view) {
            super(view);
            this.line = view.findViewById(R.id.line);
            this.v_line = view.findViewById(R.id.v_line);
            this.imgAvatar = (RImageView) view.findViewById(R.id.img_avatar);
            this.lyTitleView = (RelativeLayout) view.findViewById(R.id.ly_title_view);
            this.tvTitleView = (TextView) view.findViewById(R.id.tv_newest_review);
            this.lyMoreView = (LinearLayout) view.findViewById(R.id.ly_look_more);
            this.tvMoreValue = (TextView) view.findViewById(R.id.tv_more_value);
            this.lyContentView = (LinearLayout) view.findViewById(R.id.ly_content_view);
            this.tvSupport = (TextView) view.findViewById(R.id.tv_support);
            this.tvOpposition = (TextView) view.findViewById(R.id.tv_opposition);
            this.imgPicture = (RImageView) view.findViewById(R.id.img_picture);
            this.tvNickName = (TextView) view.findViewById(R.id.tv_nick_name);
            this.personalVipIv = (ImageView) view.findViewById(R.id.personalVipIv);
            this.tvHospital = (TextView) view.findViewById(R.id.tv_hospital);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvComment = (TextView) view.findViewById(R.id.tv_comment);
            this.tvReply = (TextView) view.findViewById(R.id.tv_reply);
            this.tvViewAll = (TextView) view.findViewById(R.id.tv_view_all);
            this.floor = (FloorViews) view.findViewById(R.id.floor);
            this.tvRate = (TextView) view.findViewById(R.id.tv_high);
            this.ivReviewFace = (ImageView) view.findViewById(R.id.iv_review_face);
        }
    }

    public static abstract class OnItemActionLisenter {
        public abstract void onItemClickAction(int i2, BookReview bookReview);

        public abstract void onItemFloorOpposeAction(int i2, BookReview bookReview);

        public abstract void onItemFloorSupportAction(int i2, BookReview bookReview);

        public abstract void onItemHotAndNewsAction(boolean z2);

        public abstract void onItemOpposeAction(int i2, BookReview bookReview);

        public abstract void onItemPicAction(int i2, BookReview bookReview);

        public abstract void onItemReplyAction(int i2, BookReview bookReview);

        public abstract void onItemSupportAction(int i2, BookReview bookReview);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$7() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(BookReview bookReview, View view) {
        if (!"0".equals(bookReview.is_logout())) {
            Toast.makeText(getContext(), "该用户已注销", 0).show();
            return;
        }
        String str = (String) view.getTag();
        Intent intent = new Intent();
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage(getContext().getPackageName());
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("ebook://ykb_user_info/?user_id=" + str));
        view.getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$1(BookViewHolder bookViewHolder, BookReview bookReview, View view) {
        view.setVisibility(8);
        bookViewHolder.tvComment.setMaxLines(999);
        bookReview.set_zhankai(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(BookReview bookReview, BookViewHolder bookViewHolder, int i2, View view) {
        Log.e("click_item", "点击了赞同");
        if (bookReview.isOpposition() == 1 || this.onItemActionLisenter == null) {
            return;
        }
        if (bookReview.isSupport() == 0) {
            Toast_pop(bookViewHolder.tvSupport, 0);
        }
        this.onItemActionLisenter.onItemSupportAction(i2, bookReview);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(BookReview bookReview, BookViewHolder bookViewHolder, int i2, View view) {
        if (bookReview.isSupport() == 1 || this.onItemActionLisenter == null) {
            return;
        }
        if (bookReview.isOpposition() == 0) {
            Toast_pop(bookViewHolder.tvOpposition, 1);
        }
        this.onItemActionLisenter.onItemOpposeAction(i2, bookReview);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$4(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemReplyAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$5(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemPicAction(i2, bookReview);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$6(int i2, BookReview bookReview, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.onItemClickAction(i2, bookReview);
        }
    }

    public void Toast_pop(View view, int i2) {
        ImageView imageView = new ImageView(view.getContext());
        if (i2 == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        final PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (i2 == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.ykb.ebook.adapter.a
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                BookCommentsAdapter.lambda$Toast_pop$7();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        if (i2 == 0) {
            popupWindow.showAtLocation(view, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(view, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new Runnable() { // from class: com.ykb.ebook.adapter.b
            @Override // java.lang.Runnable
            public final void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    public void setOnItemActionLisenter(OnItemActionLisenter onItemActionLisenter) {
        this.onItemActionLisenter = onItemActionLisenter;
    }

    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter
    public void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, final int i2, @Nullable final BookReview bookReview) {
        Context context;
        int i3;
        String str;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        bookViewHolder.lyTitleView.setBackground(new ColorDrawable(getContext().getColor(colorMode == 2 ? R.color.color_121622 : colorMode == 1 ? R.color.color_FEEEC6 : R.color.white)));
        if (i2 == 0) {
            bookViewHolder.lyTitleView.setVisibility(0);
            bookViewHolder.lyContentView.setVisibility(8);
            bookViewHolder.tvTitleView.setText(bookReview.getShowName());
            bookReview.setSuspend(true);
        } else if (i2 > 0 && i2 <= getItems().size() - 1) {
            int type = bookReview.getType();
            int type2 = getItems().get(i2 - 1).getType();
            bookViewHolder.line.setVisibility(type == type2 ? 8 : 0);
            bookViewHolder.lyTitleView.setVisibility(type == type2 ? 8 : 0);
            bookReview.setSuspend(type != type2);
            bookViewHolder.lyContentView.setVisibility(!(bookViewHolder.lyTitleView.getVisibility() == 0) ? 0 : 8);
            com.ykb.ebook.util.Log log = com.ykb.ebook.util.Log.INSTANCE;
            StringBuilder sb = new StringBuilder();
            sb.append("position = ");
            sb.append(i2);
            sb.append(" = ");
            sb.append(type == type2);
            log.logD("type", sb.toString());
            bookViewHolder.tvTitleView.setText(bookReview.getShowName());
        }
        int color = Color.parseColor(readConfig.getColorMode() == 2 ? "#1C2134" : "#EEEEEE");
        if (colorMode == 1) {
            color = getContext().getColor(R.color.color_D6C9A9);
        }
        bookViewHolder.line.setBackground(new ColorDrawable(color));
        bookViewHolder.v_line.setBackground(new ColorDrawable(color));
        TextView textView = bookViewHolder.tvViewAll;
        if (colorMode == 2) {
            context = getContext();
            i3 = R.drawable.bg_tv_line_night;
        } else if (colorMode == 1) {
            context = getContext();
            i3 = R.drawable.bg_tv_line_yellow;
        } else {
            context = getContext();
            i3 = R.drawable.bg_tv_line;
        }
        textView.setBackground(context.getDrawable(i3));
        bookViewHolder.tvTitleView.setTextColor(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        if (bookReview.getOtherView() == 3) {
            bookViewHolder.lyMoreView.setVisibility(0);
            bookViewHolder.tvMoreValue.setText(bookReview.getShowName());
        } else {
            bookViewHolder.lyMoreView.setVisibility(8);
        }
        com.ykb.ebook.util.Log log2 = com.ykb.ebook.util.Log.INSTANCE;
        log2.logD("position = ", i2 + "  text = " + bookReview.getComment());
        SubFloorComments subFloorComments = new SubFloorComments(bookReview.getReplyList());
        if (subFloorComments.size() > 0) {
            bookViewHolder.floor.setVisibility(0);
        } else {
            bookViewHolder.floor.setVisibility(8);
        }
        bookViewHolder.floor.setComments(subFloorComments, false, this);
        bookViewHolder.floor.setFactory(new SubFloorFactorys());
        bookViewHolder.floor.setIslouzhu("1");
        if (readConfig.getColorMode() != 2) {
            bookViewHolder.floor.setBoundDrawer(getContext().getResources().getDrawable(R.drawable.ebook_bondcomm));
        } else {
            bookViewHolder.floor.setBoundDrawer(getContext().getResources().getDrawable(R.drawable.ebook_bondcomm_night));
        }
        bookViewHolder.floor.setmCommentListenter(new FloorCommentListenter() { // from class: com.ykb.ebook.adapter.BookCommentsAdapter.1
            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commListOppose(String str2, String str3, String str4) {
                bookReview.setId(str3);
                BookCommentsAdapter.this.onItemActionLisenter.onItemFloorOpposeAction(i2, bookReview);
            }

            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commListPraise(String str2, View view, String str3, String str4) {
            }

            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commListPraiseFaile() {
            }

            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commListReply(BookReview bookReview2, View view) {
                if (BookCommentsAdapter.this.onItemActionLisenter != null) {
                    BookCommentsAdapter.this.onItemActionLisenter.onItemReplyAction(i2, bookReview2);
                }
            }

            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commListSupport(String str2, String str3, String str4) {
                bookReview.setId(str3);
                BookCommentsAdapter.this.onItemActionLisenter.onItemFloorSupportAction(-1, bookReview);
            }

            @Override // com.ykb.ebook.weight.FloorCommentListenter
            public void commentListenerData(BookReview bookReview2, View view) {
                if (BookCommentsAdapter.this.onItemActionLisenter != null) {
                    BookCommentsAdapter.this.onItemActionLisenter.onItemClickAction(-1, bookReview2);
                }
            }
        });
        bookViewHolder.floor.init();
        Glide.with(getContext()).load(bookReview.getAvatar()).placeholder(R.drawable.personal_headimg_icon).into(bookViewHolder.imgAvatar);
        bookViewHolder.tvSupport.setText("赞同(" + bookReview.getSupportCount() + ")");
        int i4 = bookReview.getSupportCount().matches(RegexPool.NUMBERS) ? Integer.parseInt(bookReview.getSupportCount()) : 0;
        int i5 = bookReview.getOppositionCount().matches(RegexPool.NUMBERS) ? Integer.parseInt(bookReview.getOppositionCount()) : 0;
        if (i4 > i5) {
            bookViewHolder.tvSupport.setTextColor(readConfig.getColorMode() != 2 ? getContext().getColor(R.color.color_81cb30) : Color.parseColor("#6AA064"));
            bookViewHolder.tvOpposition.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else if (i4 < i5) {
            bookViewHolder.tvOpposition.setTextColor(readConfig.getColorMode() != 2 ? getContext().getColor(R.color.color_F95843) : Color.parseColor("#B2575C"));
            bookViewHolder.tvSupport.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        } else {
            bookViewHolder.tvSupport.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
            bookViewHolder.tvOpposition.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
        }
        bookViewHolder.tvOpposition.setText("反对(" + bookReview.getOppositionCount() + ")");
        bookViewHolder.tvNickName.setText(bookReview.getNickname());
        if (bookReview.getIdentity().equals("0") || bookReview.getIdentity().equals("")) {
            bookViewHolder.personalVipIv.setVisibility(8);
        } else {
            bookViewHolder.personalVipIv.setVisibility(0);
            if (readConfig.getColorMode() == 0 || readConfig.getColorMode() == 1) {
                bookViewHolder.personalVipIv.setImageResource(bookReview.getIdentity().equals("2") ? R.drawable.personal_vip_blue : R.drawable.personal_vip);
            } else if (readConfig.getColorMode() == 2) {
                bookViewHolder.personalVipIv.setImageResource(bookReview.getIdentity().equals("2") ? R.drawable.personal_vip_blue : R.drawable.personal_vip_night);
            }
        }
        bookViewHolder.tvTime.setText(bookReview.getCtime());
        bookViewHolder.tvHospital.setText(bookReview.getSchool());
        bookViewHolder.tvTime.setTextColor(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        bookViewHolder.tvHospital.setTextColor(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        bookViewHolder.tvComment.setText(bookReview.getComment());
        bookViewHolder.tvComment.setTextColor(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        bookViewHolder.imgPicture.setVisibility(bookReview.getPicture().isEmpty() ? 8 : 0);
        if (!TextUtils.isEmpty(bookReview.getPicture())) {
            Glide.with(getContext()).load(bookReview.getPicture()).into(bookViewHolder.imgPicture);
        }
        if (!TextUtils.isEmpty(bookReview.getAvatar())) {
            Glide.with(getContext()).load(bookReview.getAvatar()).into(bookViewHolder.imgAvatar);
        }
        bookViewHolder.imgAvatar.setTag(bookReview.getUserId());
        bookViewHolder.imgAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26227c.lambda$onBindViewHolder$0(bookReview, view);
            }
        });
        bookViewHolder.tvRate.setVisibility(bookReview.getReviewType().equals("2") ? 0 : 8);
        bookViewHolder.ivReviewFace.setVisibility(bookReview.getReviewType().equals("2") ? 0 : 8);
        bookViewHolder.tvRate.setTextColor(getContext().getColor(readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        if (bookReview.getRate().equals("1")) {
            bookViewHolder.tvRate.setText("高评价");
            bookViewHolder.ivReviewFace.setImageResource(R.drawable.icon_good_review);
        } else if (bookReview.getRate().equals("2")) {
            bookViewHolder.tvRate.setText("中评价");
            bookViewHolder.ivReviewFace.setImageResource(R.drawable.icon_middle_review);
        } else if (bookReview.getRate().equals("3")) {
            bookViewHolder.tvRate.setText("低评价");
            bookViewHolder.ivReviewFace.setImageResource(R.drawable.icon_low_review);
        }
        TextView textView2 = bookViewHolder.tvReply;
        if (bookReview.getReplyCount() > 0) {
            str = bookReview.getReplyCount() + "回复 ";
        } else {
            str = "回复";
        }
        textView2.setText(str);
        if (bookReview.getReplyCount() > 0) {
            bookViewHolder.tvReply.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_303030 : R.color.color_7380a9));
            bookViewHolder.tvReply.setBackgroundResource(readConfig.getColorMode() == 2 ? R.drawable.shape_reply_selected_bg_night : R.drawable.shape_reply_selected_bg);
        } else {
            bookViewHolder.tvReply.setTextColor(getContext().getColor(readConfig.getColorMode() != 2 ? R.color.color_c2c6cb : R.color.color_575F79));
            bookViewHolder.tvReply.setBackgroundResource(R.color.transparent);
        }
        if (readConfig.getColorMode() == 2) {
            bookViewHolder.tvViewAll.setTextColor(getContext().getColor(R.color.color_575F79));
            bookViewHolder.tvViewAll.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ContextCompat.getDrawable(getContext(), R.mipmap.icon_indicator_bottomix_night), (Drawable) null);
        } else {
            bookViewHolder.tvViewAll.setTextColor(getContext().getColor(R.color.color_909090));
            bookViewHolder.tvViewAll.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ContextCompat.getDrawable(getContext(), R.mipmap.icon_indicator_bottomix), (Drawable) null);
        }
        if (bookReview.is_zhankai()) {
            bookViewHolder.tvViewAll.setVisibility(8);
            bookViewHolder.tvComment.setMaxLines(999);
        } else {
            bookViewHolder.tvViewAll.setVisibility(0);
            bookViewHolder.tvComment.setMaxLines(5);
        }
        bookViewHolder.tvViewAll.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookCommentsAdapter.lambda$onBindViewHolder$1(bookViewHolder, bookReview, view);
            }
        });
        bookViewHolder.tvSupport.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26231c.lambda$onBindViewHolder$2(bookReview, bookViewHolder, i2, view);
            }
        });
        bookViewHolder.tvOpposition.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26235c.lambda$onBindViewHolder$3(bookReview, bookViewHolder, i2, view);
            }
        });
        bookViewHolder.tvReply.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26239c.lambda$onBindViewHolder$4(i2, bookReview, view);
            }
        });
        bookViewHolder.imgPicture.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26242c.lambda$onBindViewHolder$5(i2, bookReview, view);
            }
        });
        bookViewHolder.lyContentView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26245c.lambda$onBindViewHolder$6(i2, bookReview, view);
            }
        });
        StringBuilder sb2 = new StringBuilder();
        sb2.append("position = ");
        sb2.append(i2);
        sb2.append(" more visible = ");
        sb2.append(bookViewHolder.lyContentView.getVisibility() == 0);
        log2.logD("visible", sb2.toString());
        Layout layout = bookViewHolder.tvComment.getLayout();
        if (layout != null) {
            bookViewHolder.tvViewAll.setVisibility(layout.getLineCount() >= 5 ? 0 : 8);
        } else {
            bookViewHolder.tvViewAll.setVisibility(8);
        }
    }

    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter
    @NonNull
    public BookViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i2) {
        return new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book_reviews, (ViewGroup) null));
    }
}
