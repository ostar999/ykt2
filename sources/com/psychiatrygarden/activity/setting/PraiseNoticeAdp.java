package com.psychiatrygarden.activity.setting;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.Locale;

/* loaded from: classes5.dex */
public class PraiseNoticeAdp extends BaseQuickAdapter<MyMessageCommentBean.DataBean, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setDetailsAction(int pos, MyMessageCommentBean.DataBean item);

        public abstract void setOpposeAction(int pos, MyMessageCommentBean.DataBean item, TextView textView);

        public abstract void setReplayAction(int pos, MyMessageCommentBean.DataBean item);

        public abstract void setSupportAction(int pos, MyMessageCommentBean.DataBean item, TextView textView);

        public abstract void setToUserInfoAction(int pos, MyMessageCommentBean.DataBean item);
    }

    public PraiseNoticeAdp() {
        super(R.layout.item_praise_notice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(MyMessageCommentBean.DataBean dataBean, View view) {
        if ("1".equals(dataBean.getTarget_params().getIs_vip()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            this.mContext.startActivity(new Intent(this.mContext, (Class<?>) MemberCenterActivity.class));
            return;
        }
        Intent intent = new Intent(view.getContext(), (Class<?>) CircleInfoActivity.class);
        intent.putExtra("article_id", "" + dataBean.getTarget_params().getId());
        intent.putExtra("commentCount", "0");
        intent.putExtra("channel_id", "0");
        intent.putExtra("module_type", 12);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(MyMessageCommentBean.DataBean dataBean, View view) {
        if ("1".equals(dataBean.getTarget_params().getIs_vip()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            this.mContext.startActivity(new Intent(this.mContext, (Class<?>) MemberCenterActivity.class));
            return;
        }
        Intent intent = new Intent(view.getContext(), (Class<?>) CircleInfoActivity.class);
        intent.putExtra("article_id", "" + dataBean.getTarget_params().getId());
        intent.putExtra("commentCount", "0");
        intent.putExtra("channel_id", "0");
        intent.putExtra("module_type", 12);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$2(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, TextView textView, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setSupportAction(baseViewHolder.getLayoutPosition(), dataBean, textView);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$3(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, TextView textView, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setOpposeAction(baseViewHolder.getLayoutPosition(), dataBean, textView);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$4(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setReplayAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$5(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setDetailsAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$6(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setReplayAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$7(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setReplayAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$8(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setReplayAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$9(BaseViewHolder baseViewHolder, MyMessageCommentBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setToUserInfoAction(baseViewHolder.getLayoutPosition(), dataBean);
        }
        if (TextUtils.equals("1", dataBean.getIs_read())) {
            return;
        }
        dataBean.setIs_read("1");
        notifyItemChanged(baseViewHolder.getLayoutPosition());
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(final BaseViewHolder holder, final MyMessageCommentBean.DataBean item) {
        int i2;
        final TextView textView;
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.img_user_head);
        final TextView textView2 = (TextView) holder.getView(R.id.tv_support);
        TextView textView3 = (TextView) holder.getView(R.id.tv_oppose);
        TextView textView4 = (TextView) holder.getView(R.id.tv_reply);
        TextView textView5 = (TextView) holder.getView(R.id.tv_content_one);
        TextView textView6 = (TextView) holder.getView(R.id.tv_content_two);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_show_tips);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.ly_content);
        TextView textView7 = (TextView) holder.getView(R.id.tv_form_type);
        TextView textView8 = (TextView) holder.getView(R.id.tv_title);
        View view = holder.getView(R.id.redyuan);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.ly_user_info);
        view.setVisibility(TextUtils.equals("1", item.getIs_read()) ? 8 : 0);
        holder.setText(R.id.tv_nickname, item.getNickname());
        holder.setText(R.id.tv_time, item.getTimestr());
        String title = item.getTitle();
        if (item.getC_imgs() != null && !TextUtils.isEmpty(item.getC_imgs().getS_img())) {
            title = title + "[图片]";
        }
        if (!TextUtils.isEmpty(item.getVideo_id())) {
            title = title + "[视频]";
        }
        if (TextUtils.isEmpty(title)) {
            textView8.setVisibility(8);
        } else {
            textView8.setText(title);
            textView8.setVisibility(0);
        }
        if (item.getModule_type() != 12 && item.getModule_type() != 16) {
            textView6.setVisibility(8);
            linearLayout.setVisibility(0);
            holder.setText(R.id.tv_tips, item.getTo_from());
            if (item.getReply() == null || item.getReply().size() <= 0 || TextUtils.isEmpty(item.getReply().get(0).getNickname())) {
                textView5.setVisibility(8);
            } else {
                textView5.setVisibility(0);
                MyMessageCommentBean.ReplayBean replayBean = item.getReply().get(0);
                String str = replayBean.getNickname() + "：" + replayBean.getContent();
                if (replayBean.getC_imgs() != null && !TextUtils.isEmpty(replayBean.getC_imgs().getS_img())) {
                    str = str + "[图片]";
                }
                if (replayBean.getC_imgs() != null && !TextUtils.isEmpty(replayBean.getVideo_id())) {
                    str = str + "[视频]";
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#145FB3")), 0, replayBean.getNickname().length(), 34);
                } else {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#104C8F")), 0, replayBean.getNickname().length(), 34);
                }
                textView5.setText(spannableStringBuilder);
            }
            if (item.getModule_type() == 3) {
                textView7.setText("查看原文");
            } else if (item.getModule_type() == 14 || item.getModule_type() == 15) {
                textView7.setText("查看课单");
            } else if (item.getModule_type() == 100) {
                textView7.setText("查看图书");
            } else if (item.getModule_type() == 101) {
                textView7.setText("查看资料");
            } else if (item.getModule_type() == 8) {
                textView7.setText("查看视频");
            } else {
                textView7.setText("查看原题");
            }
        } else if (item.getReply() == null || item.getReply().size() <= 0 || TextUtils.isEmpty(item.getReply().get(0).getNickname())) {
            linearLayout2.setVisibility(8);
            textView5.setVisibility(0);
            textView5.setText(item.getTo_from());
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.g0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f13857c.lambda$convert$1(item, view2);
                }
            });
        } else {
            linearLayout2.setVisibility(0);
            textView5.setVisibility(0);
            linearLayout.setVisibility(8);
            MyMessageCommentBean.ReplayBean replayBean2 = item.getReply().get(0);
            String str2 = replayBean2.getNickname() + "：" + replayBean2.getContent();
            if (replayBean2.getC_imgs() != null && !TextUtils.isEmpty(replayBean2.getC_imgs().getS_img())) {
                str2 = str2 + "[图片]";
            }
            if (replayBean2.getC_imgs() != null && !TextUtils.isEmpty(replayBean2.getVideo_id())) {
                str2 = str2 + "[视频]";
            }
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                i2 = 0;
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(Color.parseColor("#145FB3")), 0, replayBean2.getNickname().length(), 34);
            } else {
                i2 = 0;
                spannableStringBuilder2.setSpan(new ForegroundColorSpan(Color.parseColor("#104C8F")), 0, replayBean2.getNickname().length(), 34);
            }
            textView5.setText(spannableStringBuilder2);
            if (TextUtils.isEmpty(item.getTo_from())) {
                textView6.setVisibility(8);
            } else {
                textView6.setVisibility(i2);
                textView6.setText(item.getTo_from());
                textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.f0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f13854c.lambda$convert$0(item, view2);
                    }
                });
            }
        }
        if (item.getReply_num().trim().equals("0")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray_font_new2));
            } else {
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.jiucuo_night));
            }
            textView4.setText("回复");
            textView4.setBackgroundResource(R.color.transparent);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            textView4.setPadding(0, 10, 0, 10);
            textView4.setLayoutParams(layoutParams);
        } else {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.app_theme_gray));
                textView4.setBackgroundResource(R.drawable.gray_round_bg);
            } else {
                textView4.setTextColor(Color.parseColor("#7380A9"));
                textView4.setBackgroundResource(R.drawable.gray_round_bg_night);
            }
            textView4.setText(String.format(Locale.CHINA, "%s 回复", item.getReply_num()));
        }
        if (Integer.parseInt(item.getPraise_num()) <= Integer.parseInt(item.getOppose_num())) {
            textView = textView3;
            if (Integer.parseInt(item.getPraise_num()) < Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                    textView2.setTextColor(this.mContext.getResources().getColor(R.color.gray_light));
                    textView.setTextColor(this.mContext.getResources().getColor(R.color.app_theme_red));
                } else {
                    textView2.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
                    textView.setTextColor(this.mContext.getResources().getColor(R.color.red_theme_night));
                }
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.gray_light));
                textView.setTextColor(this.mContext.getResources().getColor(R.color.gray_light));
            } else {
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
                textView.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
            }
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.green));
            textView = textView3;
            textView.setTextColor(this.mContext.getColor(R.color.gray_light));
        } else {
            textView = textView3;
            textView2.setTextColor(this.mContext.getResources().getColor(R.color.green_theme_night));
            textView.setTextColor(this.mContext.getResources().getColor(R.color.jiucuo_night));
        }
        if (item.getIs_praise().equals("1")) {
            textView2.setText(String.format("已赞同(%s)", item.getPraise_num()));
        } else {
            textView2.setText(String.format("赞同(%s)", item.getPraise_num()));
        }
        if ((item.getIs_oppose() + "").equals("1")) {
            textView.setText(String.format("已反对(%s)", item.getOppose_num()));
        } else {
            textView.setText(String.format("反对(%s)", item.getOppose_num()));
        }
        GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getAvatar())).placeholder(R.mipmap.ic_avatar_def).into(circleImageView);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13860c.lambda$convert$2(holder, item, textView2, view2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13865c.lambda$convert$3(holder, item, textView, view2);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13870c.lambda$convert$4(holder, item, view2);
            }
        });
        textView7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13874c.lambda$convert$5(holder, item, view2);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13878c.lambda$convert$6(holder, item, view2);
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13882c.lambda$convert$7(holder, item, view2);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13888c.lambda$convert$8(holder, item, view2);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13893c.lambda$convert$9(holder, item, view2);
            }
        });
    }
}
