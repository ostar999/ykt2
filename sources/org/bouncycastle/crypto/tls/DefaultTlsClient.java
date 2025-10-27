package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;

/* loaded from: classes9.dex */
public abstract class DefaultTlsClient implements TlsClient {
    protected TlsCipherFactory cipherFactory;
    protected TlsClientContext context;
    protected int selectedCipherSuite;
    protected int selectedCompressionMethod;

    public DefaultTlsClient() {
        this(new DefaultTlsCipherFactory());
    }

    public DefaultTlsClient(TlsCipherFactory tlsCipherFactory) {
        this.cipherFactory = tlsCipherFactory;
    }

    public TlsKeyExchange createDHEKeyExchange(int i2) {
        return new TlsDHEKeyExchange(this.context, i2);
    }

    public TlsKeyExchange createDHKeyExchange(int i2) {
        return new TlsDHKeyExchange(this.context, i2);
    }

    public TlsKeyExchange createECDHEKeyExchange(int i2) {
        return new TlsECDHEKeyExchange(this.context, i2);
    }

    public TlsKeyExchange createECDHKeyExchange(int i2) {
        return new TlsECDHKeyExchange(this.context, i2);
    }

    public TlsKeyExchange createRSAKeyExchange() {
        return new TlsRSAKeyExchange(this.context);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0043  */
    @Override // org.bouncycastle.crypto.tls.TlsClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.bouncycastle.crypto.tls.TlsCipher getCipher() throws java.io.IOException {
        /*
            r4 = this;
            int r0 = r4.selectedCipherSuite
            r1 = 10
            r2 = 2
            if (r0 == r1) goto L43
            r1 = 13
            if (r0 == r1) goto L43
            r1 = 16
            if (r0 == r1) goto L43
            r1 = 19
            if (r0 == r1) goto L43
            r1 = 22
            if (r0 == r1) goto L43
            switch(r0) {
                case 47: goto L3c;
                case 48: goto L3c;
                case 49: goto L3c;
                case 50: goto L3c;
                case 51: goto L3c;
                default: goto L1a;
            }
        L1a:
            switch(r0) {
                case 53: goto L31;
                case 54: goto L31;
                case 55: goto L31;
                case 56: goto L31;
                case 57: goto L31;
                default: goto L1d;
            }
        L1d:
            switch(r0) {
                case 49155: goto L43;
                case 49156: goto L3c;
                case 49157: goto L31;
                default: goto L20;
            }
        L20:
            switch(r0) {
                case 49160: goto L43;
                case 49161: goto L3c;
                case 49162: goto L31;
                default: goto L23;
            }
        L23:
            switch(r0) {
                case 49165: goto L43;
                case 49166: goto L3c;
                case 49167: goto L31;
                default: goto L26;
            }
        L26:
            switch(r0) {
                case 49170: goto L43;
                case 49171: goto L3c;
                case 49172: goto L31;
                default: goto L29;
            }
        L29:
            org.bouncycastle.crypto.tls.TlsFatalAlert r0 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r1 = 80
            r0.<init>(r1)
            throw r0
        L31:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r4.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r4.context
            r3 = 9
        L37:
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L3c:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r4.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r4.context
            r3 = 8
            goto L37
        L43:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r4.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r4.context
            r3 = 7
            goto L37
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.DefaultTlsClient.getCipher():org.bouncycastle.crypto.tls.TlsCipher");
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public int[] getCipherSuites() {
        return new int[]{57, 56, 51, 50, 22, 19, 53, 47, 10};
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public Hashtable getClientExtensions() {
        return null;
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public TlsCompression getCompression() throws IOException {
        if (this.selectedCompressionMethod == 0) {
            return new TlsNullCompression();
        }
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public short[] getCompressionMethods() {
        return new short[]{0};
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public TlsKeyExchange getKeyExchange() throws IOException {
        int i2 = this.selectedCipherSuite;
        if (i2 != 10) {
            if (i2 != 13) {
                if (i2 != 16) {
                    if (i2 != 19) {
                        if (i2 != 22) {
                            switch (i2) {
                                case 47:
                                    break;
                                case 48:
                                    break;
                                case 49:
                                    break;
                                case 50:
                                    break;
                                case 51:
                                    break;
                                default:
                                    switch (i2) {
                                        case 53:
                                            break;
                                        case 54:
                                            break;
                                        case 55:
                                            break;
                                        case 56:
                                            break;
                                        case 57:
                                            break;
                                        default:
                                            switch (i2) {
                                                case CipherSuite.TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA /* 49155 */:
                                                case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA /* 49156 */:
                                                case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA /* 49157 */:
                                                    return createECDHKeyExchange(16);
                                                default:
                                                    switch (i2) {
                                                        case CipherSuite.TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA /* 49160 */:
                                                        case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA /* 49161 */:
                                                        case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA /* 49162 */:
                                                            return createECDHEKeyExchange(17);
                                                        default:
                                                            switch (i2) {
                                                                case CipherSuite.TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA /* 49165 */:
                                                                case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA /* 49166 */:
                                                                case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA /* 49167 */:
                                                                    return createECDHKeyExchange(18);
                                                                default:
                                                                    switch (i2) {
                                                                        case CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA /* 49170 */:
                                                                        case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA /* 49171 */:
                                                                        case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA /* 49172 */:
                                                                            return createECDHEKeyExchange(19);
                                                                        default:
                                                                            throw new TlsFatalAlert((short) 80);
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                        }
                        return createDHEKeyExchange(5);
                    }
                    return createDHEKeyExchange(3);
                }
                return createDHKeyExchange(9);
            }
            return createDHKeyExchange(7);
        }
        return createRSAKeyExchange();
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void init(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void notifySecureRenegotiation(boolean z2) throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void notifySelectedCipherSuite(int i2) {
        this.selectedCipherSuite = i2;
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void notifySelectedCompressionMethod(short s2) {
        this.selectedCompressionMethod = s2;
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void notifySessionID(byte[] bArr) {
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public void processServerExtensions(Hashtable hashtable) {
    }
}
