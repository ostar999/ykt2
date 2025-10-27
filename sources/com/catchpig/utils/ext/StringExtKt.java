package com.catchpig.utils.ext;

import java.util.regex.Pattern;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0002\b\u0005\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0004\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0005\u001a\u00020\u0001*\u0004\u0018\u00010\u0002\u001a\f\u0010\u0006\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0007"}, d2 = {"isNotNull", "", "", "isNumber", "isPhoneNumber", "isPswVerify", "isVerifyCode", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class StringExtKt {
    public static final boolean isNotNull(@Nullable CharSequence charSequence) {
        return charSequence != null;
    }

    public static final boolean isNumber(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        return Pattern.compile("[0-9]*").matcher(charSequence).matches();
    }

    public static final boolean isPhoneNumber(@Nullable CharSequence charSequence) {
        return !(charSequence == null || charSequence.length() == 0) && 11 == charSequence.length();
    }

    public static final boolean isPswVerify(@Nullable CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return false;
        }
        int length = charSequence.length();
        return 6 <= length && length < 21;
    }

    public static final boolean isVerifyCode(@Nullable CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return false;
        }
        return 4 == charSequence.length() || 6 == charSequence.length() || 8 == charSequence.length();
    }
}
