package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

/* loaded from: classes9.dex */
public class BasicHeaderElement implements HeaderElement, Cloneable {
    private final String name;
    private final NameValuePair[] parameters;
    private final String value;

    public BasicHeaderElement(String str, String str2, NameValuePair[] nameValuePairArr) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = str;
        this.value = str2;
        if (nameValuePairArr != null) {
            this.parameters = nameValuePairArr;
        } else {
            this.parameters = new NameValuePair[0];
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeaderElement)) {
            return false;
        }
        BasicHeaderElement basicHeaderElement = (BasicHeaderElement) obj;
        return this.name.equals(basicHeaderElement.name) && LangUtils.equals(this.value, basicHeaderElement.value) && LangUtils.equals((Object[]) this.parameters, (Object[]) basicHeaderElement.parameters);
    }

    @Override // org.apache.http.HeaderElement
    public String getName() {
        return this.name;
    }

    @Override // org.apache.http.HeaderElement
    public NameValuePair getParameter(int i2) {
        return this.parameters[i2];
    }

    @Override // org.apache.http.HeaderElement
    public NameValuePair getParameterByName(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        int i2 = 0;
        while (true) {
            NameValuePair[] nameValuePairArr = this.parameters;
            if (i2 >= nameValuePairArr.length) {
                return null;
            }
            NameValuePair nameValuePair = nameValuePairArr[i2];
            if (nameValuePair.getName().equalsIgnoreCase(str)) {
                return nameValuePair;
            }
            i2++;
        }
    }

    @Override // org.apache.http.HeaderElement
    public int getParameterCount() {
        return this.parameters.length;
    }

    @Override // org.apache.http.HeaderElement
    public NameValuePair[] getParameters() {
        return (NameValuePair[]) this.parameters.clone();
    }

    @Override // org.apache.http.HeaderElement
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int iHashCode = LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
        int i2 = 0;
        while (true) {
            NameValuePair[] nameValuePairArr = this.parameters;
            if (i2 >= nameValuePairArr.length) {
                return iHashCode;
            }
            iHashCode = LangUtils.hashCode(iHashCode, nameValuePairArr[i2]);
            i2++;
        }
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        charArrayBuffer.append(this.name);
        if (this.value != null) {
            charArrayBuffer.append("=");
            charArrayBuffer.append(this.value);
        }
        for (int i2 = 0; i2 < this.parameters.length; i2++) {
            charArrayBuffer.append("; ");
            charArrayBuffer.append(this.parameters[i2]);
        }
        return charArrayBuffer.toString();
    }

    public BasicHeaderElement(String str, String str2) {
        this(str, str2, null);
    }
}
