package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Config;
import java.util.Set;

@RequiresApi(21)
/* loaded from: classes.dex */
public class CameraConfigs {
    private static final CameraConfig EMPTY_CONFIG = new EmptyCameraConfig();

    public static final class EmptyCameraConfig implements CameraConfig {
        private final Identifier mIdentifier = Identifier.create(new Object());

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ boolean containsOption(Config.Option option) {
            return d0.a(this, option);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
            d0.b(this, str, optionMatcher);
        }

        @Override // androidx.camera.core.impl.CameraConfig
        @NonNull
        public Identifier getCompatibilityId() {
            return this.mIdentifier;
        }

        @Override // androidx.camera.core.impl.ReadableConfig
        @NonNull
        public Config getConfig() {
            return OptionsBundle.emptyBundle();
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
            return d0.c(this, option);
        }

        @Override // androidx.camera.core.impl.ReadableConfig, androidx.camera.core.impl.Config
        public /* synthetic */ Set getPriorities(Config.Option option) {
            return d0.d(this, option);
        }

        @Override // androidx.camera.core.impl.CameraConfig
        public /* synthetic */ SessionProcessor getSessionProcessor() {
            return b.a(this);
        }

        @Override // androidx.camera.core.impl.CameraConfig
        public /* synthetic */ SessionProcessor getSessionProcessor(SessionProcessor sessionProcessor) {
            return b.b(this, sessionProcessor);
        }

        @Override // androidx.camera.core.impl.CameraConfig
        public /* synthetic */ int getUseCaseCombinationRequiredRule() {
            return b.c(this);
        }

        @Override // androidx.camera.core.impl.CameraConfig
        public /* synthetic */ UseCaseConfigFactory getUseCaseConfigFactory() {
            return b.d(this);
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
    }

    private CameraConfigs() {
    }

    @NonNull
    public static CameraConfig emptyConfig() {
        return EMPTY_CONFIG;
    }
}
