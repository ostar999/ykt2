package org.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import org.bouncycastle.jce.ProviderConfigurationPermission;
import org.bouncycastle.jce.interfaces.ConfigurableProvider;
import org.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import org.bouncycastle.jce.spec.ECParameterSpec;

/* loaded from: classes9.dex */
public class ProviderUtil {
    private static volatile ECParameterSpec ecImplicitCaParams;
    private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
    private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA);
    private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, ConfigurableProvider.EC_IMPLICITLY_CA);
    private static ThreadLocal threadSpec = new ThreadLocal();

    public static ECParameterSpec getEcImplicitlyCa() {
        ECParameterSpec eCParameterSpec = (ECParameterSpec) threadSpec.get();
        return eCParameterSpec != null ? eCParameterSpec : ecImplicitCaParams;
    }

    public static int getReadLimit(InputStream inputStream) throws IOException {
        if (inputStream instanceof ByteArrayInputStream) {
            return inputStream.available();
        }
        long j2 = MAX_MEMORY;
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    public static void setParameter(String str, Object obj) {
        SecurityManager securityManager = System.getSecurityManager();
        if (str.equals(ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            ECParameterSpec eCParameterSpecConvertSpec = ((obj instanceof ECParameterSpec) || obj == null) ? (ECParameterSpec) obj : EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj, false);
            if (eCParameterSpecConvertSpec == null) {
                threadSpec.remove();
                return;
            } else {
                threadSpec.set(eCParameterSpecConvertSpec);
                return;
            }
        }
        if (str.equals(ConfigurableProvider.EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            if ((obj instanceof ECParameterSpec) || obj == null) {
                ecImplicitCaParams = (ECParameterSpec) obj;
            } else {
                ecImplicitCaParams = EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj, false);
            }
        }
    }
}
