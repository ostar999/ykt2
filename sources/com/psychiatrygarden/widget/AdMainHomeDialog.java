package com.psychiatrygarden.widget;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.psychiatrygarden.adapter.BannerHomeAdsAdapter;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes6.dex */
public class AdMainHomeDialog extends DialogFragment {
    private List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> adsDTOList = new ArrayList();

    private int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(Object obj, int i2) {
        pointCount(getContext(), "2");
        gotoActivity(this.adsDTOList.get(i2));
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        dismiss();
    }

    public static AdMainHomeDialog newInstance(String noticeList) {
        Bundle bundle = new Bundle();
        bundle.putString("noticeList", noticeList);
        AdMainHomeDialog adMainHomeDialog = new AdMainHomeDialog();
        adMainHomeDialog.setArguments(bundle);
        return adMainHomeDialog;
    }

    public void gotoActivity(HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO) {
        try {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(adsDTO.getExtra()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.requestFeature(1);
        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.transparent));
        int contextRect = getContextRect(getActivity());
        if (contextRect == 0) {
            contextRect = -1;
        }
        window.setLayout(-1, contextRect);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.0f;
        window.setAttributes(attributes);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View viewOnCreateView = super.onCreateView(inflater, container, savedInstanceState);
        if (viewOnCreateView == null) {
            viewOnCreateView = inflater.inflate(com.yikaobang.yixue.R.layout.layout_ad_pop_home, container, false);
        }
        Banner banner = (Banner) viewOnCreateView.findViewById(com.yikaobang.yixue.R.id.banner);
        try {
            HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(getArguments().getString("noticeList", ""), HomepageSmallAdBean.DataDTO.DataAd.class);
            if (dataAd != null && dataAd.getAds().size() > 0) {
                this.adsDTOList = dataAd.getAds();
                ArrayList<String> stringArrayList = getArguments().getStringArrayList("cacheImgList");
                if (stringArrayList != null && stringArrayList.size() == this.adsDTOList.size()) {
                    for (int i2 = 0; i2 < this.adsDTOList.size(); i2++) {
                        this.adsDTOList.get(i2).setImg(stringArrayList.get(i2));
                    }
                    LogUtils.d(getClass().getSimpleName(), "use_pre_cache_img_file");
                }
                banner.addBannerLifecycleObserver(this).setLoopTime(5000L).setBannerRound(20.0f).setAdapter(new BannerHomeAdsAdapter(this.adsDTOList)).setPageTransformer(new AlphaPageTransformer()).setIndicator(new RectangleIndicator(getContext())).setIndicatorHeight(ScreenUtil.getPxByDp(getContext(), 6)).setIndicatorGravity(1).setIndicatorNormalWidth(ScreenUtil.getPxByDp(getContext(), 6)).setIndicatorSelectedWidth(ScreenUtil.getPxByDp(getContext(), 15)).setIndicatorMargins(new IndicatorConfig.Margins(ScreenUtil.getPxByDp(getContext(), 12))).setIndicatorRadius(ScreenUtil.getPxByDp(getContext(), 3)).setIndicatorNormalColor(getResources().getColor(com.yikaobang.yixue.R.color.white)).setIndicatorSelectedColor(getResources().getColor(com.yikaobang.yixue.R.color.white));
                banner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.widget.b
                    @Override // com.youth.banner.listener.OnBannerListener
                    public final void OnBannerClick(Object obj, int i3) {
                        this.f16327a.lambda$onCreateView$0(obj, i3);
                    }
                });
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ((ImageView) viewOnCreateView.findViewById(com.yikaobang.yixue.R.id.close)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16354c.lambda$onCreateView$1(view);
            }
        });
        return viewOnCreateView;
    }

    public void pointCount(Context mContext, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", type);
        YJYHttpUtils.get(mContext, NetworkRequestsURL.pointCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.AdMainHomeDialog.1
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

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager manager, String tag) {
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static AdMainHomeDialog newInstance(ArrayList<String> cacheImgList, String noticeList) {
        Bundle bundle = new Bundle();
        bundle.putString("noticeList", noticeList);
        bundle.putStringArrayList("cacheImgList", cacheImgList);
        AdMainHomeDialog adMainHomeDialog = new AdMainHomeDialog();
        adMainHomeDialog.setArguments(bundle);
        return adMainHomeDialog;
    }
}
