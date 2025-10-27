package com.yddmi.doctor.pages.product;

import android.widget.ImageView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J2\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0014J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0016¨\u0006\u000e"}, d2 = {"Lcom/yddmi/doctor/pages/product/BannerAdapter;", "Lcom/zhpan/bannerview/BaseBannerAdapter;", "", "()V", "bindData", "", "holder", "Lcom/zhpan/bannerview/BaseViewHolder;", "data", "position", "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "getLayoutId", "viewType", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class BannerAdapter extends BaseBannerAdapter<String> {
    @Override // com.zhpan.bannerview.BaseBannerAdapter
    public int getLayoutId(int viewType) {
        return R.layout.product_banner;
    }

    @Override // com.zhpan.bannerview.BaseBannerAdapter
    public void bindData(@Nullable BaseViewHolder<String> holder, @Nullable String data, int position, int pageSize) {
        if (holder != null) {
            ImageView imgv = (ImageView) holder.findViewById(R.id.imgv);
            LogExtKt.logd("图片加载地址：" + data, YddConfig.TAG);
            Intrinsics.checkNotNullExpressionValue(imgv, "imgv");
            int i2 = R.drawable.set_psw_bg;
            ImageViewExtKt.load(imgv, data, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        }
    }
}
