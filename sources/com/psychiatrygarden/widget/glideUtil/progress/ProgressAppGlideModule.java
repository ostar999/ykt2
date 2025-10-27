package com.psychiatrygarden.widget.glideUtil.progress;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.ExifInterfaceImageHeaderParser;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import java.io.InputStream;
import java.util.Iterator;

@GlideModule
/* loaded from: classes6.dex */
public class ProgressAppGlideModule extends AppGlideModule {
    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.AppliesOptions
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 104857600));
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
    }

    @Override // com.bumptech.glide.module.LibraryGlideModule, com.bumptech.glide.module.RegistersComponents
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
        if (glide == null || glide.getRegistry() == null || glide.getRegistry().getImageHeaderParsers() == null) {
            return;
        }
        try {
            Iterator<ImageHeaderParser> it = glide.getRegistry().getImageHeaderParsers().iterator();
            while (it.hasNext()) {
                if (it.next() instanceof ExifInterfaceImageHeaderParser) {
                    it.remove();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
