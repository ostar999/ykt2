package com.psychiatrygarden.widget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.PopupInfo;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.CommentListAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommentListenter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SubFloorFactory<T> {
    private boolean fromVideo;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$Toast_pop$8() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$0(View view) {
        ToastUtil.shortToast(view.getContext(), "已认证");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildSubFloor$1(CommentListenter commentListenter, CommentBean.DataBean.HotBean.ReplyBean replyBean, TextView textView, Object obj, View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        commentListenter.commListSupport(replyBean.getModule_type(), replyBean.getIs_praise(), replyBean.getId(), replyBean.getObj_id());
        if (replyBean.getIs_oppose().equals("1")) {
            return;
        }
        if (replyBean.getIs_del().equals("1")) {
            NewToast.showShort(view.getContext(), "评论已删除", 0).show();
            return;
        }
        String str = "0";
        if (replyBean.getIs_praise().equals("1")) {
            replyBean.setIs_praise("0");
            try {
                String praise_num = replyBean.getPraise_num();
                if (TextUtils.isEmpty(praise_num)) {
                    praise_num = "0";
                }
                if (praise_num.trim().equals("0")) {
                    replyBean.setPraise_num("0");
                } else {
                    replyBean.setPraise_num((Integer.parseInt(praise_num) - 1) + "");
                }
                textView.setText(String.format("赞同(%s)", replyBean.getPraise_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(textView, 0);
            replyBean.setIs_praise("1");
            try {
                String praise_num2 = replyBean.getPraise_num();
                if (!TextUtils.isEmpty(praise_num2)) {
                    str = praise_num2;
                }
                replyBean.setPraise_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已赞同(%s)", replyBean.getPraise_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (obj instanceof CommentListAdapter) {
            ((CommentListAdapter) obj).notifyDataSetChanged();
        } else if (obj instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) obj).notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildSubFloor$2(CommentListenter commentListenter, CommentBean.DataBean.HotBean.ReplyBean replyBean, TextView textView, Object obj, View view) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        commentListenter.commListOppose(replyBean.getModule_type(), replyBean.getIs_oppose(), replyBean.getId(), replyBean.getObj_id());
        if (replyBean.getIs_praise().equals("1")) {
            return;
        }
        if (replyBean.getIs_del().equals("1")) {
            NewToast.showShort(view.getContext(), "评论已删除", 0).show();
            return;
        }
        String str = "0";
        if (replyBean.getIs_oppose().equals("1")) {
            replyBean.setIs_oppose("0");
            try {
                String oppose_num = replyBean.getOppose_num();
                if (TextUtils.isEmpty(oppose_num)) {
                    oppose_num = "0";
                }
                if (oppose_num.trim().equals("0")) {
                    replyBean.setOppose_num("0");
                } else {
                    replyBean.setOppose_num((Integer.parseInt(oppose_num) - 1) + "");
                }
                textView.setText(String.format("反对(%s)", replyBean.getOppose_num()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast_pop(textView, 1);
            replyBean.setIs_oppose("1");
            try {
                String oppose_num2 = replyBean.getOppose_num();
                if (!TextUtils.isEmpty(oppose_num2)) {
                    str = oppose_num2;
                }
                replyBean.setOppose_num((Integer.parseInt(str) + 1) + "");
                textView.setText(String.format("已反对(%s)", replyBean.getOppose_num()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (obj instanceof CommentListAdapter) {
            ((CommentListAdapter) obj).notifyDataSetChanged();
        } else if (obj instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) obj).notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$3(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentListenter commentListenter, View view) {
        if (replyBean.getStatus().equals("1")) {
            CommonUtil.mToastShow(view.getContext());
        } else {
            commentListenter.commListReply(replyBean, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$4(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentListenter commentListenter, View view, ImageView imageView, LinearLayout linearLayout, TextView textView, Object obj, View view2) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (replyBean.getIs_praise().equals("1")) {
            commentListenter.commListPraiseFaile();
        } else {
            commentListenter.commListPraise(replyBean.getModule_type(), replyBean.getIs_praise(), view, replyBean.getId(), replyBean.getObj_id());
            imageView.setBackgroundResource(R.drawable.icon_praise_red_left);
            replyBean.setIs_praise("1");
            linearLayout.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.welcome_loading));
            try {
                String praise_num = replyBean.getPraise_num();
                if (TextUtils.isEmpty(praise_num)) {
                    praise_num = "0";
                }
                replyBean.setPraise_num((Integer.parseInt(praise_num) + 1) + "");
                textView.setText(replyBean.getPraise_num());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (obj instanceof CommentListAdapter) {
            ((CommentListAdapter) obj).notifyDataSetChanged();
        } else if (obj instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) obj).notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$5(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentListenter commentListenter, View view) {
        try {
            if (TextUtils.isEmpty(replyBean.getVideo_id())) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(replyBean.getC_imgs().getB_img());
                ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView(null, Integer.valueOf(R.drawable.imgplacehodel_image)).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                xPopupImageLoader.popupInfo = new PopupInfo();
                xPopupImageLoader.setImageUrls(new ArrayList(arrayList)).setSrcView(null, 0).show();
            } else {
                commentListenter.commListReply(replyBean, null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$6(CommentBean.DataBean.HotBean.ReplyBean replyBean, View view) {
        if (CommonUtil.isFastClick() || ProjectApp.isSerachClick) {
            return;
        }
        if (replyBean.getIs_logout().equals("1")) {
            ToastUtils.showShort("该用户已注销");
            return;
        }
        if (replyBean.getIs_anonymous().equals("1") || UserConfig.getUserId().equals("")) {
            return;
        }
        Intent intent = new Intent(view.getContext(), (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", replyBean.getUser_id());
        intent.putExtra("jiav", replyBean.getUser_identity());
        intent.addFlags(67108864);
        view.getContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$buildSubFloor$7(CommentBean.DataBean.HotBean.ReplyBean replyBean, CommentListenter commentListenter, View view, View view2) {
        if (ProjectApp.isSerachClick) {
            return;
        }
        if (replyBean.getIs_del().equals("0")) {
            commentListenter.commentListenerData(replyBean, view);
        } else {
            NewToast.showShort(view.getContext(), "评论已删除", 0).show();
        }
    }

    public void Toast_pop(View v2, int flag) {
        ImageView imageView = new ImageView(v2.getContext());
        if (flag == 0) {
            imageView.setBackgroundResource(R.drawable.zan_animation);
        } else {
            imageView.setBackgroundResource(R.drawable.cai_animation);
        }
        PopupWindow popupWindow = new PopupWindow(imageView, -2, -2);
        if (flag == 0) {
            popupWindow.setAnimationStyle(R.style.popshowzan);
        } else {
            popupWindow.setAnimationStyle(R.style.popshowcai);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.bi
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                SubFloorFactory.lambda$Toast_pop$8();
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
        new Handler().postDelayed(new com.psychiatrygarden.activity.z3(popupWindow), 1000L);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0447  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View buildSubFloor(final com.psychiatrygarden.bean.CommentBean.DataBean.HotBean.ReplyBean r24, android.view.ViewGroup r25, final com.psychiatrygarden.utils.CommentListenter r26, boolean r27, final T r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 1196
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.SubFloorFactory.buildSubFloor(com.psychiatrygarden.bean.CommentBean$DataBean$HotBean$ReplyBean, android.view.ViewGroup, com.psychiatrygarden.utils.CommentListenter, boolean, java.lang.Object, java.lang.String):android.view.View");
    }

    public View buildSubHideFloor(CommentBean.DataBean.HotBean.ReplyBean cmt, ViewGroup group) {
        View viewInflate = ((LayoutInflater) group.getContext().getSystemService("layout_inflater")).inflate(R.layout.inclue_comment_list, (ViewGroup) null);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.show_sub_floor_content);
        RelativeLayout relativeLayout2 = (RelativeLayout) viewInflate.findViewById(R.id.hide_sub_floor_content);
        relativeLayout.setVisibility(8);
        relativeLayout2.setVisibility(0);
        TextView textView = (TextView) viewInflate.findViewById(R.id.hide_text);
        if (SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.biz_tie_comment_expand_arrow, 0, 0, 0);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.biz_tie_comment_expand_arrow_night, 0, 0, 0);
        }
        if (this.fromVideo) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.iv_arrow_down_floor, 0, 0, 0);
            textView.setTextColor(Color.parseColor("#C2C6CB"));
        }
        viewInflate.findViewById(R.id.hide_pb).setVisibility(8);
        return viewInflate;
    }

    public void setFromVideo(boolean fromVideo) {
        this.fromVideo = fromVideo;
    }
}
