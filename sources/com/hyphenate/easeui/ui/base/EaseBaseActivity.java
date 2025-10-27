package com.hyphenate.easeui.ui.base;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.hyphenate.easeui.utils.StatusBarCompat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class EaseBaseActivity extends AppCompatActivity {
    protected InputMethodManager inputMethodManager;

    private void setFitSystem(boolean z2) {
        View childAt;
        if (z2 && (childAt = ((ViewGroup) findViewById(R.id.content)).getChildAt(0)) != null) {
            childAt.setFitsSystemWindows(true);
        }
        Window window = getWindow();
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
    }

    public void back(View view) {
        finish();
    }

    public void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode == 2 || getCurrentFocus() == null) {
            return;
        }
        this.inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory("android.intent.category.LAUNCHER") && action.equals("android.intent.action.MAIN")) {
                finish();
                return;
            }
        }
        this.inputMethodManager = (InputMethodManager) getSystemService("input_method");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    public void setFitSystemForTheme(boolean z2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        setFitSystemForTheme(z2, com.hyphenate.easeui.R.color.white, true);
    }

    public void setFitSystemForTheme(boolean z2, @ColorRes int i2, boolean z3) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        setFitSystem(z2);
        StatusBarCompat.compat(this, ContextCompat.getColor(this, i2));
        StatusBarCompat.setLightStatusBar(this, z3);
    }
}
