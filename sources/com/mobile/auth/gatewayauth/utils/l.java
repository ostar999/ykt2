package com.mobile.auth.gatewayauth.utils;

import android.R;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.ColorInt;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public class l {
    private static int a(View view, ViewGroup viewGroup) {
        int i2 = 0;
        while (i2 < viewGroup.getChildCount() && viewGroup.getChildAt(i2) != view) {
            try {
                i2++;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        }
        return i2;
    }

    public static void a(Activity activity) {
        try {
            Window window = activity.getWindow();
            window.setFlags(1024, 1024);
            View childAt = ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0);
            window.clearFlags(67108864);
            window.clearFlags(134217728);
            if (childAt != null) {
                childAt.setPadding(0, 0, 0, 0);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @TargetApi(21)
    public static void a(Activity activity, @ColorInt int i2) {
        try {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Activity activity, boolean z2) {
        try {
            View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (z2) {
                if (systemUiVisibility == 0) {
                    systemUiVisibility = 8192;
                } else if ((systemUiVisibility & 8192) == 0) {
                    systemUiVisibility |= 8192;
                }
            } else if (systemUiVisibility == 8192) {
                systemUiVisibility = 0;
            } else {
                int i2 = systemUiVisibility & 8192;
                if (i2 != 0) {
                    systemUiVisibility = i2;
                }
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(final Window window) {
        try {
            window.getDecorView().setSystemUiVisibility(2);
            window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() { // from class: com.mobile.auth.gatewayauth.utils.l.1
                @Override // android.view.View.OnSystemUiVisibilityChangeListener
                public void onSystemUiVisibilityChange(int i2) {
                    try {
                        window.getDecorView().setSystemUiVisibility(R2.dimen.material_filled_edittext_font_1_3_padding_bottom);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean a(int i2) {
        try {
            return Color.alpha(i2) == 0;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static boolean a(View view) {
        try {
            try {
                Rect rect = new Rect();
                if (!(view.getGlobalVisibleRect(rect) && (rect.bottom - rect.top >= view.getMeasuredHeight()) && (rect.right - rect.left >= view.getMeasuredWidth()))) {
                    return true;
                }
                View view2 = view;
                while (view2.getParent() instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2.getParent();
                    if (viewGroup.getVisibility() != 0) {
                        return true;
                    }
                    for (int iA = a(view2, viewGroup) + 1; iA < viewGroup.getChildCount(); iA++) {
                        Rect rect2 = new Rect();
                        view.getGlobalVisibleRect(rect2);
                        View childAt = viewGroup.getChildAt(iA);
                        Rect rect3 = new Rect();
                        childAt.getGlobalVisibleRect(rect3);
                        if (Rect.intersects(rect2, rect3)) {
                            return true;
                        }
                    }
                    view2 = viewGroup;
                }
                return false;
            } catch (Exception e2) {
                i.a(e2);
                return false;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    @TargetApi(21)
    public static void b(Activity activity, @ColorInt int i2) {
        try {
            Window window = activity.getWindow();
            if (a(i2)) {
                window.clearFlags(134217728);
                window.getDecorView().setSystemUiVisibility(R2.attr.ic_info_desc_had_collection);
                window.addFlags(Integer.MIN_VALUE);
            } else {
                window.addFlags(Integer.MIN_VALUE);
                window.clearFlags(134217728);
            }
            window.setNavigationBarColor(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void b(Window window) {
        try {
            window.setFlags(8, 8);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean b(Activity activity) {
        try {
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            return rotation == 0 || rotation == 2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void c(Activity activity, int i2) {
        try {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            View childAt = ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0);
            int i3 = 1;
            if (i2 != 1) {
                i3 = 1024;
                if (i2 != 1024) {
                    i3 = 0;
                } else {
                    ActionBar actionBar = activity.getActionBar();
                    if (actionBar != null) {
                        actionBar.hide();
                    }
                }
            }
            window.clearFlags(67108864);
            window.clearFlags(134217728);
            if (childAt != null) {
                childAt.setFitsSystemWindows(false);
                childAt.setPadding(0, 0, 0, 0);
            }
            decorView.setSystemUiVisibility(i3);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void c(Window window) {
        try {
            window.clearFlags(8);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
