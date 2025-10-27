package org.eclipse.jetty.util.security;

import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes9.dex */
public class Constraint implements Cloneable, Serializable {
    public static final String ANY_ROLE = "*";
    public static final int DC_CONFIDENTIAL = 2;
    public static final int DC_FORBIDDEN = 3;
    public static final int DC_INTEGRAL = 1;
    public static final int DC_NONE = 0;
    public static final int DC_UNSET = -1;
    public static final String NONE = "NONE";
    public static final String __BASIC_AUTH = "BASIC";
    public static final String __CERT_AUTH = "CLIENT_CERT";
    public static final String __CERT_AUTH2 = "CLIENT-CERT";
    public static final String __DIGEST_AUTH = "DIGEST";
    public static final String __FORM_AUTH = "FORM";
    public static final String __NEGOTIATE_AUTH = "NEGOTIATE";
    public static final String __SPNEGO_AUTH = "SPNEGO";
    private String _name;
    private String[] _roles;
    private int _dataConstraint = -1;
    private boolean _anyRole = false;
    private boolean _authenticate = false;

    public Constraint() {
    }

    public static boolean validateMethod(String str) {
        if (str == null) {
            return false;
        }
        String strTrim = str.trim();
        return strTrim.equals(__FORM_AUTH) || strTrim.equals(__BASIC_AUTH) || strTrim.equals(__DIGEST_AUTH) || strTrim.equals(__CERT_AUTH) || strTrim.equals(__CERT_AUTH2) || strTrim.equals(__SPNEGO_AUTH) || strTrim.equals(__NEGOTIATE_AUTH);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean getAuthenticate() {
        return this._authenticate;
    }

    public int getDataConstraint() {
        return this._dataConstraint;
    }

    public String[] getRoles() {
        return this._roles;
    }

    public boolean hasDataConstraint() {
        return this._dataConstraint >= 0;
    }

    public boolean hasRole(String str) {
        if (this._anyRole) {
            return true;
        }
        String[] strArr = this._roles;
        if (strArr == null) {
            return false;
        }
        int length = strArr.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return false;
            }
            if (str.equals(this._roles[i2])) {
                return true;
            }
            length = i2;
        }
    }

    public boolean isAnyRole() {
        return this._anyRole;
    }

    public boolean isForbidden() {
        String[] strArr;
        return this._authenticate && !this._anyRole && ((strArr = this._roles) == null || strArr.length == 0);
    }

    public void setAuthenticate(boolean z2) {
        this._authenticate = z2;
    }

    public void setDataConstraint(int i2) {
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException("Constraint out of range");
        }
        this._dataConstraint = i2;
    }

    public void setName(String str) {
        this._name = str;
    }

    public void setRoles(String[] strArr) {
        this._roles = strArr;
        this._anyRole = false;
        if (strArr == null) {
            return;
        }
        int length = strArr.length;
        while (true) {
            boolean z2 = this._anyRole;
            if (z2) {
                return;
            }
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            this._anyRole = "*".equals(strArr[i2]) | z2;
            length = i2;
        }
    }

    public String toString() {
        String string;
        StringBuilder sb = new StringBuilder();
        sb.append("SC{");
        sb.append(this._name);
        sb.append(",");
        if (this._anyRole) {
            string = "*";
        } else {
            String[] strArr = this._roles;
            string = strArr == null ? "-" : Arrays.asList(strArr).toString();
        }
        sb.append(string);
        sb.append(",");
        int i2 = this._dataConstraint;
        sb.append(i2 == -1 ? "DC_UNSET}" : i2 == 0 ? "NONE}" : i2 == 1 ? "INTEGRAL}" : "CONFIDENTIAL}");
        return sb.toString();
    }

    public Constraint(String str, String str2) {
        setName(str);
        setRoles(new String[]{str2});
    }
}
