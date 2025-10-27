package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;

/* loaded from: classes.dex */
public final /* synthetic */ class n {
    public static boolean a(@NonNull Config.OptionPriority optionPriority, @NonNull Config.OptionPriority optionPriority2) {
        Config.OptionPriority optionPriority3 = Config.OptionPriority.ALWAYS_OVERRIDE;
        if (optionPriority == optionPriority3 && optionPriority2 == optionPriority3) {
            return true;
        }
        Config.OptionPriority optionPriority4 = Config.OptionPriority.REQUIRED;
        return optionPriority == optionPriority4 && optionPriority2 == optionPriority4;
    }

    @NonNull
    public static Config b(@Nullable Config config, @Nullable Config config2) {
        if (config == null && config2 == null) {
            return OptionsBundle.emptyBundle();
        }
        MutableOptionsBundle mutableOptionsBundleFrom = config2 != null ? MutableOptionsBundle.from(config2) : MutableOptionsBundle.create();
        if (config != null) {
            for (Config.Option<?> option : config.listOptions()) {
                mutableOptionsBundleFrom.insertOption(option, config.getOptionPriority(option), config.retrieveOption(option));
            }
        }
        return OptionsBundle.from(mutableOptionsBundleFrom);
    }
}
