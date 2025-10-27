package org.bouncycastle.jce;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class ProviderConfigurationPermission extends BasicPermission {
    private static final int ALL = 3;
    private static final String ALL_STR = "all";
    private static final int EC_IMPLICITLY_CA = 2;
    private static final String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
    private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
    private static final String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
    private final String actions;
    private final int permissionMask;

    public ProviderConfigurationPermission(String str) {
        super(str);
        this.actions = "all";
        this.permissionMask = 3;
    }

    public ProviderConfigurationPermission(String str, String str2) {
        super(str, str2);
        this.actions = str2;
        this.permissionMask = calculateMask(str2);
    }

    private int calculateMask(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(Strings.toLowerCase(str), " ,");
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.equals(THREAD_LOCAL_EC_IMPLICITLY_CA_STR)) {
                i2 |= 1;
            } else if (strNextToken.equals(EC_IMPLICITLY_CA_STR)) {
                i2 |= 2;
            } else if (strNextToken.equals("all")) {
                i2 |= 3;
            }
        }
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException("unknown permissions passed to mask");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProviderConfigurationPermission)) {
            return false;
        }
        ProviderConfigurationPermission providerConfigurationPermission = (ProviderConfigurationPermission) obj;
        return this.permissionMask == providerConfigurationPermission.permissionMask && getName().equals(providerConfigurationPermission.getName());
    }

    @Override // java.security.BasicPermission, java.security.Permission
    public String getActions() {
        return this.actions;
    }

    public int hashCode() {
        return getName().hashCode() + this.permissionMask;
    }

    @Override // java.security.BasicPermission, java.security.Permission
    public boolean implies(Permission permission) {
        if (!(permission instanceof ProviderConfigurationPermission) || !getName().equals(permission.getName())) {
            return false;
        }
        int i2 = this.permissionMask;
        int i3 = ((ProviderConfigurationPermission) permission).permissionMask;
        return (i2 & i3) == i3;
    }
}
