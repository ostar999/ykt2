package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/* loaded from: classes9.dex */
class Utils {
    public static int digit16(byte b3) throws DecoderException {
        int iDigit = Character.digit((char) b3, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + ((int) b3));
    }
}
