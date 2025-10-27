package org.eclipse.jetty.security;

/* loaded from: classes9.dex */
public enum UserDataConstraint {
    None,
    Integral,
    Confidential;

    public static UserDataConstraint get(int i2) {
        if (i2 >= -1 && i2 <= 2) {
            return i2 == -1 ? None : values()[i2];
        }
        throw new IllegalArgumentException("Expected -1, 0, 1, or 2, not: " + i2);
    }

    public UserDataConstraint combine(UserDataConstraint userDataConstraint) {
        return compareTo(userDataConstraint) < 0 ? this : userDataConstraint;
    }
}
