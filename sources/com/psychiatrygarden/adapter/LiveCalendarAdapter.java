package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.LiveDataList;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.LiveStatus;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class LiveCalendarAdapter extends BaseQuickAdapter<LiveDataList, BaseViewHolder> {

    /* renamed from: com.psychiatrygarden.adapter.LiveCalendarAdapter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$utils$LiveStatus;

        static {
            int[] iArr = new int[LiveStatus.values().length];
            $SwitchMap$com$psychiatrygarden$utils$LiveStatus = iArr;
            try {
                iArr[LiveStatus.LIVING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.HAVE_VID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.CUTTING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$utils$LiveStatus[LiveStatus.NO_BEGIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public LiveCalendarAdapter() {
        super(R.layout.item_live_calendar_msg);
    }

    private void anim(Context context, ImageView imageView) {
        if (SkinManager.getCurrentSkinType(context) == 1) {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation_night);
        } else {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation);
        }
        ((AnimationDrawable) imageView.getBackground()).start();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, LiveDataList item) {
        View view = holder.getView(R.id.lastItemGap);
        TextView textView = (TextView) holder.getView(R.id.live_calendar_time);
        TextView textView2 = (TextView) holder.getView(R.id.live_calendar_title);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.live_calendar_teacher_icon);
        TextView textView3 = (TextView) holder.getView(R.id.live_calendar_teacher_name);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.live_calendar_flag);
        TextView textView4 = (TextView) holder.getView(R.id.live_calendar_flag2);
        ImageView imageView = (ImageView) holder.getView(R.id.liveCalendarAnim);
        int i2 = AnonymousClass1.$SwitchMap$com$psychiatrygarden$utils$LiveStatus[TreeNodeUtilKt.getLivingStatus(item.getStart_live_time(), item.getEnd_live_time(), item.getVideo_id()).ordinal()];
        if (i2 == 1) {
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                linearLayout.setBackground(getContext().getDrawable(R.drawable.icon_shadow_living_night));
            } else {
                linearLayout.setBackground(getContext().getDrawable(R.drawable.icon_shadow_living_day));
            }
            linearLayout.setVisibility(0);
            textView4.setVisibility(8);
            anim(getContext(), imageView);
        } else if (i2 == 2) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            textView4.setBackground(getContext().getDrawable(R.drawable.shape_main_theme_five_deep_color_corners_8));
            textView4.setText("查看回放");
            textView4.setTextColor(getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_red_color}).getColor(0, Color.parseColor("#F95843")));
        } else if (i2 == 3) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            textView4.setBackground(getContext().getDrawable(R.drawable.shape_new_bg_two_color_corners_8));
            textView4.setText("直播结束");
            textView4.setTextColor(getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.forth_txt_color}).getColor(0, Color.parseColor("#C2C6CB")));
        } else if (i2 == 4) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            textView4.setBackground(getContext().getDrawable(R.drawable.shape_new_bg_two_color_corners_8));
            textView4.setText("即将直播");
            textView4.setTextColor(getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.forth_txt_color}).getColor(0, Color.parseColor("#C2C6CB")));
        }
        textView.setText(item.getStart_time() + "-" + item.getEnd_time());
        textView2.setText(item.getTitle());
        textView3.setText(item.getTeacher());
        if (!TextUtils.isEmpty(item.getHead_img())) {
            GlideApp.with(getContext()).load(item.getHead_img()).into(circleImageView);
        }
        view.setVisibility(BaseViewHolderUtilKt.getCustomerBindAdapterPosition(holder) == getData().size() - 1 ? 0 : 8);
    }
}
