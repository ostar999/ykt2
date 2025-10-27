package com.unity3d.splash.services.ads.adunit;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class AdUnitTransparentActivity extends AdUnitActivity {
    @Override // com.unity3d.splash.services.ads.adunit.AdUnitActivity
    public void createLayout() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.createLayout();
        ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
    }

    @Override // com.unity3d.splash.services.ads.adunit.AdUnitActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
    }
}
