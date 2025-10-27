package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.LangUtils;

@Immutable
/* loaded from: classes9.dex */
public final class Scheme {
    private final int defaultPort;
    private final boolean layered;
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private String stringRep;

    public Scheme(String str, int i2, SchemeSocketFactory schemeSocketFactory) {
        if (str == null) {
            throw new IllegalArgumentException("Scheme name may not be null");
        }
        if (i2 <= 0 || i2 > 65535) {
            throw new IllegalArgumentException("Port is invalid: " + i2);
        }
        if (schemeSocketFactory == null) {
            throw new IllegalArgumentException("Socket factory may not be null");
        }
        this.name = str.toLowerCase(Locale.ENGLISH);
        this.socketFactory = schemeSocketFactory;
        this.defaultPort = i2;
        this.layered = schemeSocketFactory instanceof LayeredSchemeSocketFactory;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scheme)) {
            return false;
        }
        Scheme scheme = (Scheme) obj;
        return this.name.equals(scheme.name) && this.defaultPort == scheme.defaultPort && this.layered == scheme.layered;
    }

    public final int getDefaultPort() {
        return this.defaultPort;
    }

    public final String getName() {
        return this.name;
    }

    public final SchemeSocketFactory getSchemeSocketFactory() {
        return this.socketFactory;
    }

    @Deprecated
    public final SocketFactory getSocketFactory() {
        SchemeSocketFactory schemeSocketFactory = this.socketFactory;
        return schemeSocketFactory instanceof SchemeSocketFactoryAdaptor ? ((SchemeSocketFactoryAdaptor) schemeSocketFactory).getFactory() : this.layered ? new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory) schemeSocketFactory) : new SocketFactoryAdaptor(schemeSocketFactory);
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.defaultPort), this.name), this.layered);
    }

    public final boolean isLayered() {
        return this.layered;
    }

    public final int resolvePort(int i2) {
        return i2 <= 0 ? this.defaultPort : i2;
    }

    public final String toString() {
        if (this.stringRep == null) {
            this.stringRep = this.name + ':' + Integer.toString(this.defaultPort);
        }
        return this.stringRep;
    }

    @Deprecated
    public Scheme(String str, SocketFactory socketFactory, int i2) {
        if (str == null) {
            throw new IllegalArgumentException("Scheme name may not be null");
        }
        if (socketFactory == null) {
            throw new IllegalArgumentException("Socket factory may not be null");
        }
        if (i2 > 0 && i2 <= 65535) {
            this.name = str.toLowerCase(Locale.ENGLISH);
            if (socketFactory instanceof LayeredSocketFactory) {
                this.socketFactory = new LayeredSchemeSocketFactoryAdaptor((LayeredSocketFactory) socketFactory);
                this.layered = true;
            } else {
                this.socketFactory = new SchemeSocketFactoryAdaptor(socketFactory);
                this.layered = false;
            }
            this.defaultPort = i2;
            return;
        }
        throw new IllegalArgumentException("Port is invalid: " + i2);
    }
}
