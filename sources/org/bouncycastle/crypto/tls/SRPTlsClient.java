package org.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public abstract class SRPTlsClient implements TlsClient {
    public static final Integer EXT_SRP = new Integer(12);
    protected TlsCipherFactory cipherFactory;
    protected TlsClientContext context;
    protected byte[] identity;
    protected byte[] password;
    protected int selectedCipherSuite;
    protected int selectedCompressionMethod;

    public SRPTlsClient(TlsCipherFactory tlsCipherFactory, byte[] bArr, byte[] bArr2) {
        this.cipherFactory = tlsCipherFactory;
        this.identity = Arrays.clone(bArr);
        this.password = Arrays.clone(bArr2);
    }

    public SRPTlsClient(byte[] bArr, byte[] bArr2) {
        this(new DefaultTlsCipherFactory(), bArr, bArr2);
    }

    public TlsKeyExchange createSRPKeyExchange(int i2) {
        return new TlsSRPKeyExchange(this.context, i2, this.identity, this.password);
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public TlsCipher getCipher() throws IOException {
        TlsCipherFactory tlsCipherFactory;
        TlsClientContext tlsClientContext;
        int i2;
        switch (this.selectedCipherSuite) {
            case CipherSuite.TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA /* 49178 */:
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA /* 49179 */:
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA /* 49180 */:
                tlsCipherFactory = this.cipherFactory;
                tlsClientContext = this.context;
                i2 = 7;
                break;
            case CipherSuite.TLS_SRP_SHA_WITH_AES_128_CBC_SHA /* 49181 */:
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA /* 49182 */:
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA /* 49183 */:
                tlsCipherFactory = this.cipherFactory;
                tlsClientContext = this.context;
                i2 = 8;
                break;
            case CipherSuite.TLS_SRP_SHA_WITH_AES_256_CBC_SHA /* 49184 */:
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA /* 49185 */:
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA /* 49186 */:
                tlsCipherFactory = this.cipherFactory;
                tlsClientContext = this.context;
                i2 = 9;
                break;
            default:
                throw new TlsFatalAlert((short) 80);
        }
        return tlsCipherFactory.createCipher(tlsClientContext, i2, 2);
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public int[] getCipherSuites() {
        return new int[]{CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA, CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_SRP_SHA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_SRP_SHA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA};
    }

    @Override // org.bouncycastle.crypto.tls.TlsClient
    public Hashtable getClientExtensions() throws IOException {
        Hashtable hashtable = new Hashtable();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeOpaque8(this.identity, byteArrayOutputStream);
        hashtable.put(EXT_SRP, byteArrayOutputStream.toByteArray());
        return hashtable;
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
        int i2;
        switch (this.selectedCipherSuite) {
            case CipherSuite.TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA /* 49178 */:
            case CipherSuite.TLS_SRP_SHA_WITH_AES_128_CBC_SHA /* 49181 */:
            case CipherSuite.TLS_SRP_SHA_WITH_AES_256_CBC_SHA /* 49184 */:
                i2 = 21;
                break;
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA /* 49179 */:
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA /* 49182 */:
            case CipherSuite.TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA /* 49185 */:
                i2 = 23;
                break;
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA /* 49180 */:
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA /* 49183 */:
            case CipherSuite.TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA /* 49186 */:
                i2 = 22;
                break;
            default:
                throw new TlsFatalAlert((short) 80);
        }
        return createSRPKeyExchange(i2);
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
