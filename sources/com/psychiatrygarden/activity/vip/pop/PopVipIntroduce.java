package com.psychiatrygarden.activity.vip.pop;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.vip.bean.MemPurchasrBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class PopVipIntroduce extends CenterPopupView implements View.OnClickListener {
    private int currentPostion;
    private List<View> listViews;
    private List<MemPurchasrBean.DataBean.PrivilegeBean> privilege;
    private TextView tv_page;
    private ViewPager viewpager;

    public class ScaleTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.8f;

        public ScaleTransformer() {
        }

        @Override // androidx.viewpager.widget.ViewPager.PageTransformer
        public void transformPage(View page, float position) {
            if (position < -1.0f || position > 1.0f) {
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            } else if (position <= 1.0f) {
                if (position < 0.0f) {
                    float f2 = (position * 0.25f) + 1.0f;
                    page.setScaleX(f2);
                    page.setScaleY(f2);
                } else {
                    float f3 = 1.0f - (position * 0.2f);
                    page.setScaleX(f3);
                    page.setScaleY(f3);
                }
            }
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> list;

        public ViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            List<View> list = this.list;
            if (list == null || list.size() <= 0) {
                return 0;
            }
            return this.list.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object object) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(this.list.get(position));
            return this.list.get(position);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public PopVipIntroduce(@NonNull Context context, List<MemPurchasrBean.DataBean.PrivilegeBean> privilege, int currentPostion) {
        super(context);
        this.currentPostion = 0;
        this.listViews = new ArrayList();
        this.privilege = privilege;
        this.currentPostion = currentPostion;
    }

    private void initViewPager() throws Resources.NotFoundException {
        if (this.privilege.size() > 0) {
            for (int i2 = 0; i2 < this.privilege.size(); i2++) {
                View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.vip_introduce_info, (ViewGroup) null);
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_pop_vip_icon);
                TextView textView = (TextView) viewInflate.findViewById(R.id.tv_pop_vip_title);
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_rights_description);
                TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_instructions);
                textView.setText(this.privilege.get(i2).getLabel());
                textView2.setText(this.privilege.get(i2).getRights());
                textView3.setText(this.privilege.get(i2).getUsage());
                Glide.with(this).load((Object) GlideUtils.generateUrl(this.privilege.get(i2).getImg_1())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.mipmap.icon_vip_small_default).placeholder(R.mipmap.icon_vip_small_default).skipMemoryCache(true)).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
                this.listViews.add(viewInflate);
            }
            this.tv_page.setText((this.currentPostion + 1) + "/" + this.privilege.size());
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.listViews);
        this.viewpager.setOffscreenPageLimit(3);
        this.viewpager.setAdapter(viewPagerAdapter);
        this.viewpager.setPageMargin(-ScreenUtil.getPxByDp(getContext(), 15));
        this.viewpager.setCurrentItem(this.currentPostion);
        this.viewpager.setLayoutParams(new LinearLayout.LayoutParams(-1, ScreenUtil.getPxByDp(getContext(), 30) + (ScreenUtil.getScreenHeight((Activity) getContext()) / 2)));
        this.viewpager.setPageTransformer(false, new ScaleTransformer());
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.vip.pop.PopVipIntroduce.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i3, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                PopVipIntroduce.this.currentPostion = i3;
                PopVipIntroduce.this.tv_page.setText((PopVipIntroduce.this.currentPostion + 1) + "/" + PopVipIntroduce.this.privilege.size());
            }
        });
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_vip_introduce;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return CommonUtil.getScreenWidth(getContext());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() throws Resources.NotFoundException {
        super.onCreate();
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.tv_page = (TextView) findViewById(R.id.tv_page);
        ((LinearLayout) findViewById(R.id.ll_pop_vip)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14116c.onClick(view);
            }
        });
        initViewPager();
    }
}
