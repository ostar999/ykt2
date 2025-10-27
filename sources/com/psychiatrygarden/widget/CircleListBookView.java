package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.GlideUtils;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CircleListBookView extends LinearLayout {
    private RoundedImageView imgPic;
    private RelativeLayout mLyItem;
    private TextView mTvScroe;
    private TextView mTvTitle;

    public CircleListBookView(Context context) {
        this(context, null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_circle_ebook_more, this);
        this.imgPic = (RoundedImageView) findViewById(R.id.iv_book);
        this.mTvTitle = (TextView) findViewById(R.id.tv_title);
        this.mLyItem = (RelativeLayout) findViewById(R.id.ly_item);
        this.mTvScroe = (TextView) findViewById(R.id.tv_scroe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(String str, String str2, String str3, String str4, View view) {
        if (!UserConfig.isLogin()) {
            getContext().startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        } else {
            if ("1".equals(str)) {
                getContext().startActivity(new Intent(getContext(), (Class<?>) MemberCenterActivity.class));
                return;
            }
            Intent intent = new Intent(getContext(), (Class<?>) CircleInfoActivity.class);
            intent.putExtra("channel_id", str2);
            intent.putExtra("article_id", str3);
            intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("commentCount", str4);
            getContext().startActivity(intent);
        }
    }

    public void setData(PushBookData data, final String channelId, final String articleId, final String commentCount, final String noAccess) {
        TextView textView = this.mTvTitle;
        if (textView != null) {
            textView.setText(data.getTitle());
            if (TextUtils.isEmpty(data.getRate()) || data.getRate().equals("0")) {
                this.mTvScroe.setText("暂无");
            } else {
                this.mTvScroe.setText(data.getRate() + "分");
            }
            GlideUtils.loadImage(getContext(), data.getThumbnail(), this.imgPic);
            this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16883c.lambda$setData$0(noAccess, channelId, articleId, commentCount, view);
                }
            });
        }
    }

    public CircleListBookView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleListBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
