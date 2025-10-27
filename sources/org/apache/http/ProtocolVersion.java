package org.apache.http;

import java.io.Serializable;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class ProtocolVersion implements Serializable, Cloneable {
    private static final long serialVersionUID = 8950662842175091068L;
    protected final int major;
    protected final int minor;
    protected final String protocol;

    public ProtocolVersion(String str, int i2, int i3) {
        if (str == null) {
            throw new IllegalArgumentException("Protocol name must not be null.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Protocol major version number must not be negative.");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("Protocol minor version number may not be negative");
        }
        this.protocol = str;
        this.major = i2;
        this.minor = i3;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int compareToVersion(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) {
            throw new IllegalArgumentException("Protocol version must not be null.");
        }
        if (this.protocol.equals(protocolVersion.protocol)) {
            int major = getMajor() - protocolVersion.getMajor();
            return major == 0 ? getMinor() - protocolVersion.getMinor() : major;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Versions for different protocols cannot be compared. ");
        stringBuffer.append(this);
        stringBuffer.append(" ");
        stringBuffer.append(protocolVersion);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProtocolVersion)) {
            return false;
        }
        ProtocolVersion protocolVersion = (ProtocolVersion) obj;
        return this.protocol.equals(protocolVersion.protocol) && this.major == protocolVersion.major && this.minor == protocolVersion.minor;
    }

    public ProtocolVersion forVersion(int i2, int i3) {
        return (i2 == this.major && i3 == this.minor) ? this : new ProtocolVersion(this.protocol, i2, i3);
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final boolean greaterEquals(ProtocolVersion protocolVersion) {
        return isComparable(protocolVersion) && compareToVersion(protocolVersion) >= 0;
    }

    public final int hashCode() {
        return (this.protocol.hashCode() ^ (this.major * 100000)) ^ this.minor;
    }

    public boolean isComparable(ProtocolVersion protocolVersion) {
        return protocolVersion != null && this.protocol.equals(protocolVersion.protocol);
    }

    public final boolean lessEquals(ProtocolVersion protocolVersion) {
        return isComparable(protocolVersion) && compareToVersion(protocolVersion) <= 0;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(16);
        charArrayBuffer.append(this.protocol);
        charArrayBuffer.append('/');
        charArrayBuffer.append(Integer.toString(this.major));
        charArrayBuffer.append('.');
        charArrayBuffer.append(Integer.toString(this.minor));
        return charArrayBuffer.toString();
    }
}
