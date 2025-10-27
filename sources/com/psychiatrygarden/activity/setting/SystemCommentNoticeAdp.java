package com.psychiatrygarden.activity.setting;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SystemCommentNoticeAdp extends BaseQuickAdapter<MyMessageCommentBean.DataBean, BaseViewHolder> {
    private SystemCommentNoticeAct mActivity;

    public SystemCommentNoticeAdp(SystemCommentNoticeAct activity) {
        super(R.layout.item_comment_notice);
        this.mActivity = activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convert$0(MyMessageCommentBean.DataBean dataBean, View view) {
        if ((dataBean.getModule_type() == 12 || dataBean.getModule_type() == 16) && dataBean.getTarget_params() != null && "1".equals(dataBean.getTarget_params().getIs_vip()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            Intent intent = new Intent(ProjectApp.instance(), (Class<?>) MemberCenterActivity.class);
            intent.setFlags(268435456);
            ProjectApp.instance().startActivity(intent);
        } else {
            PublicMethodActivity.getInstance().mCommentMethod(dataBean.getModule_type() + "", dataBean.getTarget_params());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convert$1(MyMessageCommentBean.DataBean dataBean, View view) {
        if (dataBean.getModule_type() == 12 || dataBean.getModule_type() == 16) {
            if (dataBean.getTarget_params() != null && "1".equals(dataBean.getTarget_params().getIs_vip()) && UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                Intent intent = new Intent(ProjectApp.instance(), (Class<?>) MemberCenterActivity.class);
                intent.setFlags(268435456);
                ProjectApp.instance().startActivity(intent);
                return;
            }
        } else if (dataBean.getModule_type() == 101 && dataBean.getTarget_params() != null && dataBean.getTarget_params().getIs_del().equals("1")) {
            ToastUtil.shortToast(ProjectApp.instance(), "抱歉，资料已隐藏或删除");
            return;
        }
        try {
            PublicMethodActivity.getInstance().mCommentMethod(dataBean.getModule_type() + "", dataBean.getTarget_params());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, final MyMessageCommentBean.DataBean dataBean) {
        try {
            String str = dataBean.getNickname() + "：" + dataBean.getContent();
            if (dataBean.getC_imgs() != null && !TextUtils.isEmpty(dataBean.getC_imgs().getS_img())) {
                str = str + "[图片]";
            }
            if (dataBean.getC_imgs() != null && !TextUtils.isEmpty(dataBean.getVideo_id())) {
                str = str + "[视频]";
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#145FB3")), 0, dataBean.getNickname().length(), 34);
            } else {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#104C8F")), 0, dataBean.getNickname().length(), 34);
            }
            holder.setText(R.id.title, dataBean.getTitle()).setText(R.id.ctime, dataBean.getTimestr()).setText(R.id.tofrom, dataBean.getTo_from()).setText(R.id.description, spannableStringBuilder);
            TextView textView = (TextView) holder.getView(R.id.atmostlun);
            TextView textView2 = (TextView) holder.getView(R.id.redyuan);
            if (dataBean.getModule_type() == 3) {
                textView.setText("查看原文");
            } else if (dataBean.getModule_type() == 12 || dataBean.getModule_type() == 16) {
                textView.setText("查看帖子");
            } else if (dataBean.getModule_type() == 100) {
                textView.setText("查看图书");
            } else if (dataBean.getModule_type() == 101) {
                textView.setText("查看资料");
            } else if (dataBean.getModule_type() == 8) {
                textView.setText("查看视频");
            } else {
                textView.setText("查看原题");
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.u0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SystemCommentNoticeAdp.lambda$convert$0(dataBean, view);
                }
            });
            ((TextView) holder.getView(R.id.tofrom)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.v0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SystemCommentNoticeAdp.lambda$convert$1(dataBean, view);
                }
            });
            if (dataBean.getIs_read().equals("0")) {
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
