package com.catchpig.utils.ext;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import cn.hutool.core.text.StrPool;
import com.alipay.sdk.authjs.a;
import com.mobile.auth.gatewayauth.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005\u001a5\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00010\b\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u0002¨\u0006\u000e"}, d2 = {"hidePassword", "", "Landroid/widget/EditText;", "keepDecimal", Constant.LOGIN_ACTIVITY_NUMBER, "", "keepDecimalListener", a.f3170c, "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "text", "showPassword", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nEditTextExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EditTextExt.kt\ncom/catchpig/utils/ext/EditTextExtKt\n+ 2 TextView.kt\nandroidx/core/widget/TextViewKt\n*L\n1#1,76:1\n65#2,16:77\n93#2,3:93\n65#2,16:96\n93#2,3:112\n*S KotlinDebug\n*F\n+ 1 EditTextExt.kt\ncom/catchpig/utils/ext/EditTextExtKt\n*L\n13#1:77,16\n13#1:93,3\n40#1:96,16\n40#1:112,3\n*E\n"})
/* loaded from: classes.dex */
public final class EditTextExtKt {
    public static final void hidePassword(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editText.setSelection(editText.getText().toString().length());
    }

    public static final void keepDecimal(@NotNull final EditText editText, final int i2) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        editText.addTextChangedListener(new TextWatcher() { // from class: com.catchpig.utils.ext.EditTextExtKt$keepDecimal$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                String strValueOf = String.valueOf(s2);
                if (strValueOf.length() > 0) {
                    String strSubstring = strValueOf.substring(0, 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    if (Intrinsics.areEqual(strSubstring, StrPool.DOT)) {
                        String str = '0' + strValueOf;
                        editText.setText(str);
                        editText.setSelection(str.length());
                        return;
                    }
                    if (StringsKt__StringsKt.contains$default((CharSequence) strValueOf, (CharSequence) StrPool.DOT, false, 2, (Object) null)) {
                        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) strValueOf, new String[]{StrPool.DOT}, false, 0, 6, (Object) null);
                        if (((String) listSplit$default.get(1)).length() > i2) {
                            String str2 = ((String) listSplit$default.get(0)) + '.' + ((Object) ((String) listSplit$default.get(1)).subSequence(0, i2));
                            editText.setText(str2);
                            editText.setSelection(str2.length());
                        }
                    }
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            }
        });
    }

    public static final void keepDecimalListener(@NotNull final EditText editText, final int i2, @NotNull final Function1<? super String, Unit> callback) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        editText.addTextChangedListener(new TextWatcher() { // from class: com.catchpig.utils.ext.EditTextExtKt$keepDecimalListener$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                String strValueOf = String.valueOf(s2);
                if (strValueOf.length() > 0) {
                    String strSubstring = strValueOf.substring(0, 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    if (Intrinsics.areEqual(strSubstring, StrPool.DOT)) {
                        String str = '0' + strValueOf;
                        editText.setText(str);
                        editText.setSelection(str.length());
                        return;
                    }
                    if (StringsKt__StringsKt.contains$default((CharSequence) strValueOf, (CharSequence) StrPool.DOT, false, 2, (Object) null)) {
                        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) strValueOf, new String[]{StrPool.DOT}, false, 0, 6, (Object) null);
                        if (((String) listSplit$default.get(1)).length() > i2) {
                            String str2 = ((String) listSplit$default.get(0)) + '.' + ((Object) ((String) listSplit$default.get(1)).subSequence(0, i2));
                            editText.setText(str2);
                            editText.setSelection(str2.length());
                            return;
                        }
                    }
                    callback.invoke(strValueOf);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            }
        });
    }

    public static final void showPassword(@NotNull EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        editText.setSelection(editText.getText().toString().length());
    }
}
