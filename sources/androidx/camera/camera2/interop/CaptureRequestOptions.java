package androidx.camera.camera2.interop;

import android.hardware.camera2.CaptureRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.interop.CaptureRequestOptions;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.ReadableConfig;
import androidx.camera.core.impl.d0;
import java.util.Set;

@RequiresApi(21)
@ExperimentalCamera2Interop
/* loaded from: classes.dex */
public class CaptureRequestOptions implements ReadableConfig {
    private final Config mConfig;

    @RequiresApi(21)
    public static final class Builder implements ExtendableBuilder<CaptureRequestOptions> {
        private final MutableOptionsBundle mMutableOptionsBundle = MutableOptionsBundle.create();

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Builder from(@NonNull final Config config) {
            final Builder builder = new Builder();
            config.findOptions(Camera2ImplConfig.CAPTURE_REQUEST_ID_STEM, new Config.OptionMatcher() { // from class: androidx.camera.camera2.interop.i
                @Override // androidx.camera.core.impl.Config.OptionMatcher
                public final boolean onOptionMatched(Config.Option option) {
                    return CaptureRequestOptions.Builder.lambda$from$0(this.f1464a, config, option);
                }
            });
            return builder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$from$0(Builder builder, Config config, Config.Option option) {
            builder.getMutableConfig().insertOption(option, config.getOptionPriority(option), config.retrieveOption(option));
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NonNull
        public <ValueT> Builder clearCaptureRequestOption(@NonNull CaptureRequest.Key<ValueT> key) {
            this.mMutableOptionsBundle.removeOption(Camera2ImplConfig.createCaptureRequestOption(key));
            return this;
        }

        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public MutableConfig getMutableConfig() {
            return this.mMutableOptionsBundle;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NonNull
        public <ValueT> Builder setCaptureRequestOption(@NonNull CaptureRequest.Key<ValueT> key, @NonNull ValueT valuet) {
            this.mMutableOptionsBundle.insertOption(Camera2ImplConfig.createCaptureRequestOption(key), valuet);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        public CaptureRequestOptions build() {
            return new CaptureRequestOptions(OptionsBundle.from(this.mMutableOptionsBundle));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public CaptureRequestOptions(@NonNull Config config) {
        this.mConfig = config;
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ boolean containsOption(Config.Option option) {
        return d0.a(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
        d0.b(this, str, optionMatcher);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public <ValueT> ValueT getCaptureRequestOption(@NonNull CaptureRequest.Key<ValueT> key) {
        return (ValueT) this.mConfig.retrieveOption(Camera2ImplConfig.createCaptureRequestOption(key), null);
    }

    @Override // androidx.camera.core.impl.ReadableConfig
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Config getConfig() {
        return this.mConfig;
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
        return d0.c(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Set getPriorities(Config.Option option) {
        return d0.d(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Set listOptions() {
        return d0.e(this);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOption(Config.Option option) {
        return d0.f(this, option);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOption(Config.Option option, Object obj) {
        return d0.g(this, option, obj);
    }

    @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
    public /* synthetic */ Object retrieveOptionWithPriority(Config.Option option, Config.OptionPriority optionPriority) {
        return d0.h(this, option, optionPriority);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public <ValueT> ValueT getCaptureRequestOption(@NonNull CaptureRequest.Key<ValueT> key, @Nullable ValueT valuet) {
        return (ValueT) this.mConfig.retrieveOption(Camera2ImplConfig.createCaptureRequestOption(key), valuet);
    }
}
