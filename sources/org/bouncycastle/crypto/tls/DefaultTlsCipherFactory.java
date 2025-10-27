package org.bouncycastle.crypto.tls;

import java.io.IOException;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;

/* loaded from: classes9.dex */
public class DefaultTlsCipherFactory implements TlsCipherFactory {
    public BlockCipher createAESBlockCipher() {
        return new CBCBlockCipher(new AESFastEngine());
    }

    public TlsCipher createAESCipher(TlsClientContext tlsClientContext, int i2, int i3) throws IOException {
        return new TlsBlockCipher(tlsClientContext, createAESBlockCipher(), createAESBlockCipher(), createDigest(i3), createDigest(i3), i2);
    }

    @Override // org.bouncycastle.crypto.tls.TlsCipherFactory
    public TlsCipher createCipher(TlsClientContext tlsClientContext, int i2, int i3) throws IOException {
        if (i2 == 7) {
            return createDESedeCipher(tlsClientContext, 24, i3);
        }
        if (i2 == 8) {
            return createAESCipher(tlsClientContext, 16, i3);
        }
        if (i2 == 9) {
            return createAESCipher(tlsClientContext, 32, i3);
        }
        throw new TlsFatalAlert((short) 80);
    }

    public BlockCipher createDESedeBlockCipher() {
        return new CBCBlockCipher(new DESedeEngine());
    }

    public TlsCipher createDESedeCipher(TlsClientContext tlsClientContext, int i2, int i3) throws IOException {
        return new TlsBlockCipher(tlsClientContext, createDESedeBlockCipher(), createDESedeBlockCipher(), createDigest(i3), createDigest(i3), i2);
    }

    public Digest createDigest(int i2) throws IOException {
        if (i2 == 1) {
            return new MD5Digest();
        }
        if (i2 == 2) {
            return new SHA1Digest();
        }
        if (i2 == 3) {
            return new SHA256Digest();
        }
        if (i2 == 4) {
            return new SHA384Digest();
        }
        throw new TlsFatalAlert((short) 80);
    }
}
