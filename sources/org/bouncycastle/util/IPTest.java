package org.bouncycastle.util;

import cn.hutool.core.text.StrPool;
import junit.framework.TestCase;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes9.dex */
public class IPTest extends TestCase {
    private static final String[] validIP4v = {StringUtil.ALL_INTERFACES, "255.255.255.255", "192.168.0.0"};
    private static final String[] invalidIP4v = {"0.0.0.0.1", "256.255.255.255", "1", "A.B.C", "1:.4.6.5"};
    private static final String[] validIP6v = {"0:0:0:0:0:0:0:0", "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF", "0:1:2:3:FFFF:5:FFFF:1"};
    private static final String[] invalidIP6v = {"0.0.0.0:1", "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFFF"};

    private void testIP(String[] strArr, String[] strArr2) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (!IPAddress.isValid(strArr[i2])) {
                TestCase.fail("Valid input string not accepted: " + strArr[i2] + StrPool.DOT);
            }
        }
        for (int i3 = 0; i3 < strArr2.length; i3++) {
            if (IPAddress.isValid(strArr2[i3])) {
                TestCase.fail("Invalid input string accepted: " + strArr2[i3] + StrPool.DOT);
            }
        }
    }

    public String getName() {
        return "IPTest";
    }

    public void testIPv4() {
        testIP(validIP4v, invalidIP4v);
    }

    public void testIPv6() {
        testIP(validIP6v, invalidIP6v);
    }
}
