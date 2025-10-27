package com.bumptech.glide.load.resource;

import android.annotation.SuppressLint;
import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.HardwareConfigState;
import java.io.IOException;

@RequiresApi(api = 28)
/* loaded from: classes2.dex */
public abstract class ImageDecoderResourceDecoder<T> implements ResourceDecoder<ImageDecoder.Source, T> {
    private static final String TAG = "ImageDecoder";
    final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();

    public abstract Resource<T> decode(ImageDecoder.Source source, int i2, int i3, ImageDecoder.OnHeaderDecodedListener onHeaderDecodedListener) throws IOException;

    @Override // com.bumptech.glide.load.ResourceDecoder
    public final boolean handles(@NonNull ImageDecoder.Source source, @NonNull Options options) {
        return true;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    @Nullable
    public final Resource<T> decode(@NonNull ImageDecoder.Source source, final int i2, final int i3, @NonNull Options options) throws IOException {
        final DecodeFormat decodeFormat = (DecodeFormat) options.get(Downsampler.DECODE_FORMAT);
        final DownsampleStrategy downsampleStrategy = (DownsampleStrategy) options.get(DownsampleStrategy.OPTION);
        Option<Boolean> option = Downsampler.ALLOW_HARDWARE_CONFIG;
        final boolean z2 = options.get(option) != null && ((Boolean) options.get(option)).booleanValue();
        final PreferredColorSpace preferredColorSpace = (PreferredColorSpace) options.get(Downsampler.PREFERRED_COLOR_SPACE);
        return decode(source, i2, i3, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.bumptech.glide.load.resource.ImageDecoderResourceDecoder.1
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            @SuppressLint({"Override"})
            public void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                boolean z3 = false;
                if (ImageDecoderResourceDecoder.this.hardwareConfigState.isHardwareConfigAllowed(i2, i3, z2, false)) {
                    imageDecoder.setAllocator(3);
                } else {
                    imageDecoder.setAllocator(1);
                }
                if (decodeFormat == DecodeFormat.PREFER_RGB_565) {
                    imageDecoder.setMemorySizePolicy(0);
                }
                imageDecoder.setOnPartialImageListener(new ImageDecoder.OnPartialImageListener() { // from class: com.bumptech.glide.load.resource.ImageDecoderResourceDecoder.1.1
                    @Override // android.graphics.ImageDecoder.OnPartialImageListener
                    public boolean onPartialImage(@NonNull ImageDecoder.DecodeException decodeException) {
                        return false;
                    }
                });
                Size size = imageInfo.getSize();
                int width = i2;
                if (width == Integer.MIN_VALUE) {
                    width = size.getWidth();
                }
                int height = i3;
                if (height == Integer.MIN_VALUE) {
                    height = size.getHeight();
                }
                float scaleFactor = downsampleStrategy.getScaleFactor(size.getWidth(), size.getHeight(), width, height);
                int iRound = Math.round(size.getWidth() * scaleFactor);
                int iRound2 = Math.round(size.getHeight() * scaleFactor);
                if (Log.isLoggable(ImageDecoderResourceDecoder.TAG, 2)) {
                    Log.v(ImageDecoderResourceDecoder.TAG, "Resizing from [" + size.getWidth() + "x" + size.getHeight() + "] to [" + iRound + "x" + iRound2 + "] scaleFactor: " + scaleFactor);
                }
                imageDecoder.setTargetSize(iRound, iRound2);
                int i4 = Build.VERSION.SDK_INT;
                if (i4 < 28) {
                    if (i4 >= 26) {
                        imageDecoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
                    }
                } else {
                    if (preferredColorSpace == PreferredColorSpace.DISPLAY_P3 && imageInfo.getColorSpace() != null && imageInfo.getColorSpace().isWideGamut()) {
                        z3 = true;
                    }
                    imageDecoder.setTargetColorSpace(ColorSpace.get(z3 ? ColorSpace.Named.DISPLAY_P3 : ColorSpace.Named.SRGB));
                }
            }
        });
    }
}
