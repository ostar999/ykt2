package com.psychiatrygarden.activity.rank.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.Random;

/* loaded from: classes5.dex */
public class ShareBoxPopWindow extends CenterPopupView {
    private int ShareType;
    private String imgurl;
    private ShareListener mShareListener;
    private int type;

    public interface ShareListener {
        void mShareCloseListener();

        void mShareDataListener();

        void mShareDataListener(int type);
    }

    public ShareBoxPopWindow(@NonNull Context context, int type, int ShareType, String imgurl) {
        super(context);
        this.type = type;
        this.ShareType = ShareType;
        this.imgurl = imgurl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mShareListener.mShareDataListener();
        this.mShareListener.mShareDataListener(this.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.mShareListener.mShareCloseListener();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_sharebox_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.img);
        ImageView imageView2 = (ImageView) findViewById(R.id.close);
        if (getResources().getConfiguration().orientation == 2) {
            imageView.getLayoutParams().width = UIUtil.dip2px(getContext(), 200.0d);
        } else {
            imageView.getLayoutParams().width = UIUtil.dip2px(getContext(), 250.0d);
        }
        if (this.type == 0) {
            this.type = new Random().nextInt(4);
        }
        int i2 = this.type;
        if (i2 == 0) {
            int i3 = this.ShareType;
            if (i3 != 1) {
                if (i3 == 0) {
                    imageView.setImageResource(R.drawable.qqpm);
                } else {
                    Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgurl)).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
                }
            }
        } else if (i2 == 1) {
            if (this.ShareType == 0) {
                imageView.setImageResource(R.drawable.qqpm);
            } else {
                Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgurl)).placeholder(R.drawable.default_img).into(imageView);
            }
        } else if (i2 == 2) {
            if (this.ShareType == 0) {
                imageView.setImageResource(R.drawable.weixinpm);
            } else {
                Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgurl)).placeholder(R.drawable.default_img).into(imageView);
            }
        } else if (i2 == 3) {
            if (this.ShareType == 0) {
                imageView.setImageResource(R.drawable.pengyouquanpm);
            } else {
                Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgurl)).placeholder(R.drawable.default_img).into(imageView);
            }
        } else if (i2 == 4) {
            if (this.ShareType == 0) {
                imageView.setImageResource(R.drawable.pengyouquanpm);
            } else {
                Glide.with(getContext()).load((Object) GlideUtils.generateUrl(this.imgurl)).placeholder(R.drawable.default_img).into(imageView);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.pop.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13782c.lambda$onCreate$0(view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.pop.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13783c.lambda$onCreate$1(view);
            }
        });
    }

    public void setmShareListener(ShareListener mShareListener) {
        this.mShareListener = mShareListener;
    }
}
