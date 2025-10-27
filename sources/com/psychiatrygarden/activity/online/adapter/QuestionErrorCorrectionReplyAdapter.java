package com.psychiatrygarden.activity.online.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.online.bean.QuestionErroCorrectionBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.MyExpendView;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionErrorCorrectionReplyAdapter extends BaseQuickAdapter<QuestionErroCorrectionBean.DataDTO, BaseViewHolder> {
    public QuestionErrorCorrectionReplyAdapter(@Nullable List<QuestionErroCorrectionBean.DataDTO> data) {
        super(R.layout.item_error_correction_reply, data);
        addChildClickViewIds(R.id.tv_reply);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$3() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(QuestionErroCorrectionBean.DataDTO dataDTO, View view) {
        Intent intent = new Intent(getContext(), (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", "" + dataDTO.getUser_id());
        getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(QuestionErroCorrectionBean.DataDTO dataDTO, TextView textView, View view) {
        if (ProjectApp.isSerachClick || dataDTO.getIs_oppose().equals("1")) {
            return;
        }
        String str = "0";
        if (dataDTO.getIs_praise().equals("1")) {
            putPraise(dataDTO.getId(), "0");
            dataDTO.setIs_praise("0");
            try {
                String praise_num = dataDTO.getPraise_num();
                if (TextUtils.isEmpty(praise_num)) {
                    praise_num = "0";
                }
                if (praise_num.trim().equals("0")) {
                    dataDTO.setPraise_num("0");
                } else {
                    dataDTO.setPraise_num((Integer.parseInt(praise_num) - 1) + "");
                }
                textView.setText("赞同(" + dataDTO.getPraise_num() + ")");
            } catch (Exception unused) {
            }
        } else {
            Toast_pop(textView, 0);
            putPraise(dataDTO.getId(), "1");
            dataDTO.setIs_praise("1");
            try {
                String praise_num2 = dataDTO.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                dataDTO.setPraise_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已赞同(%s)", dataDTO.getPraise_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$2(QuestionErroCorrectionBean.DataDTO dataDTO, TextView textView, View view) {
        if (ProjectApp.isSerachClick || dataDTO.getIs_praise().equals("1")) {
            return;
        }
        String str = "0";
        if (dataDTO.getIs_oppose().equals("1")) {
            putPraise(dataDTO.getId(), "0");
            dataDTO.setIs_oppose("0");
            try {
                String oppose_num = dataDTO.getOppose_num();
                if (TextUtils.isEmpty(oppose_num)) {
                    oppose_num = "0";
                }
                if (oppose_num.trim().equals("0")) {
                    dataDTO.setOppose_num("0");
                } else {
                    dataDTO.setOppose_num((Integer.parseInt(oppose_num) - 1) + "");
                }
                textView.setText(String.format("反对(%s)", dataDTO.getOppose_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(textView, 1);
            putPraise(dataDTO.getId(), "2");
            dataDTO.setIs_oppose("1");
            try {
                String oppose_num2 = dataDTO.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num2)) {
                    str = oppose_num2;
                }
                dataDTO.setOppose_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已反对(%s)", dataDTO.getOppose_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }

    public void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        final PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.online.adapter.n
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                QuestionErrorCorrectionReplyAdapter.lambda$Toast_pop$3();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        v2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        v2.getLocalVisibleRect(rect);
        if (flag == 0) {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        } else {
            popupWindow.showAtLocation(v2, 0, iArr[0] + (rect.centerX() / 2), iArr[1] - (rect.centerY() * 3));
        }
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.adapter.o
            @Override // java.lang.Runnable
            public final void run() {
                popupWindow.dismiss();
            }
        }, 1000L);
    }

    public void putPraise(String id, String action) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("action", action);
        YJYHttpUtils.post(getContext().getApplicationContext(), NetworkRequestsURL.errorCorrectionPraiseURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.adapter.QuestionErrorCorrectionReplyAdapter.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
            }
        });
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, final QuestionErroCorrectionBean.DataDTO item) {
        MyExpendView myExpendView = (MyExpendView) helper.getView(R.id.myexptervew);
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.rl_replay);
        TextView textView = (TextView) helper.getView(R.id.commentList_item_tv_userName);
        TextView textView2 = (TextView) helper.getView(R.id.commentList_item_tv_school);
        final TextView textView3 = (TextView) helper.getView(R.id.tv_support);
        final TextView textView4 = (TextView) helper.getView(R.id.tv_oppose);
        TextView textView5 = (TextView) helper.getView(R.id.tv_reply);
        CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.commentList_item_userIcon);
        textView2.setText(item.getSchool() + " " + item.getTimeStr());
        textView.setText(item.getNickname());
        if (TextUtils.isEmpty(item.getAvatar() + "")) {
            circleImageView.setImageResource(R.drawable.empty_photo);
        } else {
            GlideUtils.loadImage(circleImageView.getContext(), item.getAvatar(), circleImageView);
        }
        if (helper.getBindingAdapterPosition() == 0) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                relativeLayout.setBackgroundColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
            } else {
                relativeLayout.setBackgroundColor(Color.parseColor("#121622"));
            }
        } else if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            relativeLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
        } else {
            relativeLayout.setBackgroundColor(Color.parseColor("#1C2134"));
        }
        myExpendView.setText(item.getContent(), true);
        circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13125c.lambda$convert$0(item, view);
            }
        });
        try {
            if (Integer.parseInt(item.getPraise_num()) > Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.green_theme_night));
                    textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.jiucuo_night));
                }
            } else if (Integer.parseInt(item.getPraise_num()) < Integer.parseInt(item.getOppose_num())) {
                if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                    textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                    textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.app_theme_red));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.jiucuo_night));
                    textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.red_theme_night));
                }
            } else if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_light));
            } else {
                textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.jiucuo_night));
                textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.jiucuo_night));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (item.getIs_praise().equals("1")) {
            textView3.setText(String.format("已赞同(%s)", item.getPraise_num()));
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            } else {
                textView3.setTextColor(ContextCompat.getColor(getContext(), R.color.green_theme_night));
            }
        } else {
            textView3.setText(String.format("赞同(%s)", item.getPraise_num()));
        }
        if (item.getIs_oppose().equals("1")) {
            textView4.setText(String.format("已反对(%s)", item.getOppose_num()));
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.app_theme_red));
            } else {
                textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.red_theme_night));
            }
        } else {
            textView4.setText(String.format("反对(%s)", item.getOppose_num()));
        }
        if (item.getReply_num().trim().equals("0")) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_font_new2));
            } else {
                textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.jiucuo_night));
            }
            textView5.setText("回复");
            textView5.setBackgroundResource(R.color.transparent);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            textView5.setPadding(0, 10, 0, 10);
            textView5.setLayoutParams(layoutParams);
        } else {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_font));
                textView5.setBackgroundResource(R.drawable.gray_round_bg);
            } else {
                textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.question_color_night));
                textView5.setBackgroundResource(R.drawable.gray_round_bg_night);
            }
            textView5.setText(String.format("%s 回复", item.getReply_num()));
        }
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13127c.lambda$convert$1(item, textView3, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13130c.lambda$convert$2(item, textView4, view);
            }
        });
    }
}
