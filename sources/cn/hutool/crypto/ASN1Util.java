package cn.hutool.crypto;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IORuntimeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.util.ASN1Dump;

/* loaded from: classes.dex */
public class ASN1Util {
    public static ASN1Object decode(InputStream inputStream) {
        try {
            return new ASN1InputStream(inputStream).readObject();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static byte[] encode(String str, ASN1Encodable... aSN1EncodableArr) {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        encodeTo(str, fastByteArrayOutputStream, aSN1EncodableArr);
        return fastByteArrayOutputStream.toByteArray();
    }

    public static byte[] encodeDer(ASN1Encodable... aSN1EncodableArr) {
        return encode(ASN1Encodable.DER, aSN1EncodableArr);
    }

    public static void encodeTo(String str, OutputStream outputStream, ASN1Encodable... aSN1EncodableArr) {
        ASN1Sequence dLSequence;
        str.hashCode();
        switch (str) {
            case "DL":
                dLSequence = new DLSequence(aSN1EncodableArr);
                break;
            case "BER":
                dLSequence = new BERSequence(aSN1EncodableArr);
                break;
            case "DER":
                dLSequence = new DERSequence(aSN1EncodableArr);
                break;
            default:
                throw new CryptoException("Unsupported ASN1 encoding: {}", str);
        }
        try {
            dLSequence.encodeTo(outputStream);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getDumpStr(InputStream inputStream) {
        return ASN1Dump.dumpAsString(decode(inputStream));
    }
}
