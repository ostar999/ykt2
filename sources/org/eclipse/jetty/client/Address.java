package org.eclipse.jetty.client;

import java.net.InetSocketAddress;

/* loaded from: classes9.dex */
public class Address {
    private final String host;
    private final int port;

    public Address(String str, int i2) {
        if (str == null) {
            throw new IllegalArgumentException("Host is null");
        }
        this.host = str.trim();
        this.port = i2;
    }

    public static Address from(String str) throws NumberFormatException {
        int iIndexOf = str.indexOf(58);
        int i2 = 0;
        if (iIndexOf >= 0) {
            String strSubstring = str.substring(0, iIndexOf);
            i2 = Integer.parseInt(str.substring(iIndexOf + 1));
            str = strSubstring;
        }
        return new Address(str, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return this.host.equals(address.host) && this.port == address.port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int hashCode() {
        return (this.host.hashCode() * 31) + this.port;
    }

    public InetSocketAddress toSocketAddress() {
        return new InetSocketAddress(getHost(), getPort());
    }

    public String toString() {
        return this.host + ":" + this.port;
    }
}
