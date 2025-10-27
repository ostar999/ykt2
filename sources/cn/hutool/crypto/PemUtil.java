package cn.hutool.crypto;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

/* loaded from: classes.dex */
public class PemUtil {
    public static byte[] readPem(InputStream inputStream) {
        PemObject pemObject = readPemObject(inputStream);
        if (pemObject != null) {
            return pemObject.getContent();
        }
        return null;
    }

    public static Key readPemKey(InputStream inputStream) {
        PemObject pemObject = readPemObject(inputStream);
        String type = pemObject.getType();
        if (!CharSequenceUtil.isNotBlank(type)) {
            return null;
        }
        if (type.endsWith("EC PRIVATE KEY")) {
            try {
                return KeyUtil.generatePrivateKey("EC", pemObject.getContent());
            } catch (Exception unused) {
                return KeyUtil.generatePrivateKey("EC", ECKeyUtil.createOpenSSHPrivateKeySpec(pemObject.getContent()));
            }
        }
        if (type.endsWith("PRIVATE KEY")) {
            return KeyUtil.generateRSAPrivateKey(pemObject.getContent());
        }
        if (type.endsWith("EC PUBLIC KEY")) {
            try {
                return KeyUtil.generatePublicKey("EC", pemObject.getContent());
            } catch (Exception unused2) {
                return KeyUtil.generatePublicKey("EC", ECKeyUtil.createOpenSSHPublicKeySpec(pemObject.getContent()));
            }
        }
        if (type.endsWith("PUBLIC KEY")) {
            return KeyUtil.generateRSAPublicKey(pemObject.getContent());
        }
        if (type.endsWith("CERTIFICATE")) {
            return KeyUtil.readPublicKeyFromCert(IoUtil.toStream(pemObject.getContent()));
        }
        return null;
    }

    public static PemObject readPemObject(InputStream inputStream) {
        return readPemObject(IoUtil.getUtf8Reader(inputStream));
    }

    public static PrivateKey readPemPrivateKey(InputStream inputStream) {
        return (PrivateKey) readPemKey(inputStream);
    }

    public static PublicKey readPemPublicKey(InputStream inputStream) {
        return (PublicKey) readPemKey(inputStream);
    }

    @Deprecated
    public static PrivateKey readSm2PemPrivateKey(InputStream inputStream) {
        return readPemPrivateKey(inputStream);
    }

    public static String toPem(String str, byte[] bArr) throws IOException {
        StringWriter stringWriter = new StringWriter();
        writePemObject(str, bArr, stringWriter);
        return stringWriter.toString();
    }

    public static void writePemObject(String str, byte[] bArr, OutputStream outputStream) throws IOException {
        writePemObject(new PemObject(str, bArr), outputStream);
    }

    public static PemObject readPemObject(Reader reader) throws Throwable {
        PemReader pemReader = null;
        try {
            try {
                PemReader pemReader2 = new PemReader(reader);
                try {
                    PemObject pemObject = pemReader2.readPemObject();
                    IoUtil.close((Closeable) pemReader2);
                    return pemObject;
                } catch (IOException e2) {
                    e = e2;
                    throw new IORuntimeException(e);
                } catch (Throwable th) {
                    th = th;
                    pemReader = pemReader2;
                    IoUtil.close((Closeable) pemReader);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void writePemObject(String str, byte[] bArr, Writer writer) throws IOException {
        writePemObject(new PemObject(str, bArr), writer);
    }

    public static void writePemObject(PemObjectGenerator pemObjectGenerator, OutputStream outputStream) throws IOException {
        writePemObject(pemObjectGenerator, IoUtil.getUtf8Writer(outputStream));
    }

    public static void writePemObject(PemObjectGenerator pemObjectGenerator, Writer writer) throws IOException {
        PemWriter pemWriter = new PemWriter(writer);
        try {
            try {
                pemWriter.writeObject(pemObjectGenerator);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) pemWriter);
        }
    }
}
