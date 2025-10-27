package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class BasicHeaderValueFormatter implements HeaderValueFormatter {
    public static final BasicHeaderValueFormatter DEFAULT = new BasicHeaderValueFormatter();
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";

    public static final String formatElements(HeaderElement[] headerElementArr, boolean z2, HeaderValueFormatter headerValueFormatter) {
        if (headerValueFormatter == null) {
            headerValueFormatter = DEFAULT;
        }
        return headerValueFormatter.formatElements(null, headerElementArr, z2).toString();
    }

    public static final String formatHeaderElement(HeaderElement headerElement, boolean z2, HeaderValueFormatter headerValueFormatter) {
        if (headerValueFormatter == null) {
            headerValueFormatter = DEFAULT;
        }
        return headerValueFormatter.formatHeaderElement(null, headerElement, z2).toString();
    }

    public static final String formatNameValuePair(NameValuePair nameValuePair, boolean z2, HeaderValueFormatter headerValueFormatter) {
        if (headerValueFormatter == null) {
            headerValueFormatter = DEFAULT;
        }
        return headerValueFormatter.formatNameValuePair(null, nameValuePair, z2).toString();
    }

    public static final String formatParameters(NameValuePair[] nameValuePairArr, boolean z2, HeaderValueFormatter headerValueFormatter) {
        if (headerValueFormatter == null) {
            headerValueFormatter = DEFAULT;
        }
        return headerValueFormatter.formatParameters(null, nameValuePairArr, z2).toString();
    }

    public void doFormatValue(CharArrayBuffer charArrayBuffer, String str, boolean z2) {
        if (!z2) {
            for (int i2 = 0; i2 < str.length() && !z2; i2++) {
                z2 = isSeparator(str.charAt(i2));
            }
        }
        if (z2) {
            charArrayBuffer.append('\"');
        }
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (isUnsafe(cCharAt)) {
                charArrayBuffer.append('\\');
            }
            charArrayBuffer.append(cCharAt);
        }
        if (z2) {
            charArrayBuffer.append('\"');
        }
    }

    public int estimateElementsLen(HeaderElement[] headerElementArr) {
        if (headerElementArr == null || headerElementArr.length < 1) {
            return 0;
        }
        int length = (headerElementArr.length - 1) * 2;
        for (HeaderElement headerElement : headerElementArr) {
            length += estimateHeaderElementLen(headerElement);
        }
        return length;
    }

    public int estimateHeaderElementLen(HeaderElement headerElement) {
        if (headerElement == null) {
            return 0;
        }
        int length = headerElement.getName().length();
        String value = headerElement.getValue();
        if (value != null) {
            length += value.length() + 3;
        }
        int parameterCount = headerElement.getParameterCount();
        if (parameterCount > 0) {
            for (int i2 = 0; i2 < parameterCount; i2++) {
                length += estimateNameValuePairLen(headerElement.getParameter(i2)) + 2;
            }
        }
        return length;
    }

    public int estimateNameValuePairLen(NameValuePair nameValuePair) {
        if (nameValuePair == null) {
            return 0;
        }
        int length = nameValuePair.getName().length();
        String value = nameValuePair.getValue();
        return value != null ? length + value.length() + 3 : length;
    }

    public int estimateParametersLen(NameValuePair[] nameValuePairArr) {
        if (nameValuePairArr == null || nameValuePairArr.length < 1) {
            return 0;
        }
        int length = (nameValuePairArr.length - 1) * 2;
        for (NameValuePair nameValuePair : nameValuePairArr) {
            length += estimateNameValuePairLen(nameValuePair);
        }
        return length;
    }

    public boolean isSeparator(char c3) {
        return SEPARATORS.indexOf(c3) >= 0;
    }

    public boolean isUnsafe(char c3) {
        return UNSAFE_CHARS.indexOf(c3) >= 0;
    }

    @Override // org.apache.http.message.HeaderValueFormatter
    public CharArrayBuffer formatElements(CharArrayBuffer charArrayBuffer, HeaderElement[] headerElementArr, boolean z2) {
        if (headerElementArr != null) {
            int iEstimateElementsLen = estimateElementsLen(headerElementArr);
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(iEstimateElementsLen);
            } else {
                charArrayBuffer.ensureCapacity(iEstimateElementsLen);
            }
            for (int i2 = 0; i2 < headerElementArr.length; i2++) {
                if (i2 > 0) {
                    charArrayBuffer.append(", ");
                }
                formatHeaderElement(charArrayBuffer, headerElementArr[i2], z2);
            }
            return charArrayBuffer;
        }
        throw new IllegalArgumentException("Header element array must not be null.");
    }

    @Override // org.apache.http.message.HeaderValueFormatter
    public CharArrayBuffer formatHeaderElement(CharArrayBuffer charArrayBuffer, HeaderElement headerElement, boolean z2) {
        if (headerElement != null) {
            int iEstimateHeaderElementLen = estimateHeaderElementLen(headerElement);
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(iEstimateHeaderElementLen);
            } else {
                charArrayBuffer.ensureCapacity(iEstimateHeaderElementLen);
            }
            charArrayBuffer.append(headerElement.getName());
            String value = headerElement.getValue();
            if (value != null) {
                charArrayBuffer.append('=');
                doFormatValue(charArrayBuffer, value, z2);
            }
            int parameterCount = headerElement.getParameterCount();
            if (parameterCount > 0) {
                for (int i2 = 0; i2 < parameterCount; i2++) {
                    charArrayBuffer.append("; ");
                    formatNameValuePair(charArrayBuffer, headerElement.getParameter(i2), z2);
                }
            }
            return charArrayBuffer;
        }
        throw new IllegalArgumentException("Header element must not be null.");
    }

    @Override // org.apache.http.message.HeaderValueFormatter
    public CharArrayBuffer formatNameValuePair(CharArrayBuffer charArrayBuffer, NameValuePair nameValuePair, boolean z2) {
        if (nameValuePair != null) {
            int iEstimateNameValuePairLen = estimateNameValuePairLen(nameValuePair);
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(iEstimateNameValuePairLen);
            } else {
                charArrayBuffer.ensureCapacity(iEstimateNameValuePairLen);
            }
            charArrayBuffer.append(nameValuePair.getName());
            String value = nameValuePair.getValue();
            if (value != null) {
                charArrayBuffer.append('=');
                doFormatValue(charArrayBuffer, value, z2);
            }
            return charArrayBuffer;
        }
        throw new IllegalArgumentException("NameValuePair must not be null.");
    }

    @Override // org.apache.http.message.HeaderValueFormatter
    public CharArrayBuffer formatParameters(CharArrayBuffer charArrayBuffer, NameValuePair[] nameValuePairArr, boolean z2) {
        if (nameValuePairArr != null) {
            int iEstimateParametersLen = estimateParametersLen(nameValuePairArr);
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(iEstimateParametersLen);
            } else {
                charArrayBuffer.ensureCapacity(iEstimateParametersLen);
            }
            for (int i2 = 0; i2 < nameValuePairArr.length; i2++) {
                if (i2 > 0) {
                    charArrayBuffer.append("; ");
                }
                formatNameValuePair(charArrayBuffer, nameValuePairArr[i2], z2);
            }
            return charArrayBuffer;
        }
        throw new IllegalArgumentException("Parameters must not be null.");
    }
}
