package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.crypto.prng.ThreadedSeedGenerator;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class TlsProtocolHandler {
    private static final short CS_CERTIFICATE_REQUEST_RECEIVED = 5;
    private static final short CS_CERTIFICATE_VERIFY_SEND = 8;
    private static final short CS_CLIENT_CHANGE_CIPHER_SPEC_SEND = 9;
    private static final short CS_CLIENT_FINISHED_SEND = 10;
    private static final short CS_CLIENT_HELLO_SEND = 1;
    private static final short CS_CLIENT_KEY_EXCHANGE_SEND = 7;
    private static final short CS_DONE = 12;
    private static final short CS_SERVER_CERTIFICATE_RECEIVED = 3;
    private static final short CS_SERVER_CHANGE_CIPHER_SPEC_RECEIVED = 11;
    private static final short CS_SERVER_HELLO_DONE_RECEIVED = 6;
    private static final short CS_SERVER_HELLO_RECEIVED = 2;
    private static final short CS_SERVER_KEY_EXCHANGE_RECEIVED = 4;
    private static final String TLS_ERROR_MESSAGE = "Internal TLS error, this could be an attack";
    private ByteQueue alertQueue;
    private boolean appDataReady;
    private ByteQueue applicationDataQueue;
    private TlsAuthentication authentication;
    private CertificateRequest certificateRequest;
    private ByteQueue changeCipherSpecQueue;
    private Hashtable clientExtensions;
    private boolean closed;
    private short connection_state;
    private boolean failedWithError;
    private ByteQueue handshakeQueue;
    private TlsKeyExchange keyExchange;
    private int[] offeredCipherSuites;
    private short[] offeredCompressionMethods;
    private SecureRandom random;
    private RecordStream rs;
    private SecurityParameters securityParameters;
    private TlsClient tlsClient;
    private TlsClientContextImpl tlsClientContext;
    private TlsInputStream tlsInputStream;
    private TlsOutputStream tlsOutputStream;
    private static final Integer EXT_RenegotiationInfo = new Integer(65281);
    private static final byte[] emptybuf = new byte[0];

    public TlsProtocolHandler(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, outputStream, createSecureRandom());
    }

    public TlsProtocolHandler(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        this.applicationDataQueue = new ByteQueue();
        this.changeCipherSpecQueue = new ByteQueue();
        this.alertQueue = new ByteQueue();
        this.handshakeQueue = new ByteQueue();
        this.tlsInputStream = null;
        this.tlsOutputStream = null;
        this.closed = false;
        this.failedWithError = false;
        this.appDataReady = false;
        this.securityParameters = null;
        this.tlsClientContext = null;
        this.tlsClient = null;
        this.offeredCipherSuites = null;
        this.offeredCompressionMethods = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateRequest = null;
        this.connection_state = (short) 0;
        this.rs = new RecordStream(this, inputStream, outputStream);
        this.random = secureRandom;
    }

    private static boolean arrayContains(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    private static boolean arrayContains(short[] sArr, short s2) {
        for (short s3 : sArr) {
            if (s3 == s2) {
                return true;
            }
        }
        return false;
    }

    private static byte[] createRenegotiationInfo(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeOpaque8(bArr, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static SecureRandom createSecureRandom() {
        ThreadedSeedGenerator threadedSeedGenerator = new ThreadedSeedGenerator();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(threadedSeedGenerator.generateSeed(20, true));
        return secureRandom;
    }

    private void failWithError(short s2, short s3) throws IOException {
        if (this.closed) {
            throw new IOException(TLS_ERROR_MESSAGE);
        }
        this.closed = true;
        if (s2 == 2) {
            this.failedWithError = true;
        }
        sendAlert(s2, s3);
        this.rs.close();
        if (s2 == 2) {
            throw new IOException(TLS_ERROR_MESSAGE);
        }
    }

    private void processAlert() throws IOException {
        while (this.alertQueue.size() >= 2) {
            byte[] bArr = new byte[2];
            this.alertQueue.read(bArr, 0, 2, 0);
            this.alertQueue.removeData(2);
            short s2 = bArr[0];
            short s3 = bArr[1];
            if (s2 == 2) {
                this.failedWithError = true;
                this.closed = true;
                try {
                    this.rs.close();
                } catch (Exception unused) {
                }
                throw new IOException(TLS_ERROR_MESSAGE);
            }
            if (s3 == 0) {
                failWithError((short) 1, (short) 0);
            }
        }
    }

    private void processApplicationData() {
    }

    private void processChangeCipherSpec() throws IOException {
        while (this.changeCipherSpecQueue.size() > 0) {
            byte[] bArr = new byte[1];
            this.changeCipherSpecQueue.read(bArr, 0, 1, 0);
            this.changeCipherSpecQueue.removeData(1);
            if (bArr[0] != 1) {
                failWithError((short) 2, (short) 10);
            }
            if (this.connection_state != 10) {
                failWithError((short) 2, (short) 40);
            }
            this.rs.serverClientSpecReceived();
            this.connection_state = (short) 11;
        }
    }

    private void processHandshake() throws IOException {
        boolean z2;
        do {
            z2 = false;
            if (this.handshakeQueue.size() >= 4) {
                byte[] bArr = new byte[4];
                this.handshakeQueue.read(bArr, 0, 4, 0);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                short uint8 = TlsUtils.readUint8(byteArrayInputStream);
                int uint24 = TlsUtils.readUint24(byteArrayInputStream);
                int i2 = uint24 + 4;
                if (this.handshakeQueue.size() >= i2) {
                    byte[] bArr2 = new byte[uint24];
                    this.handshakeQueue.read(bArr2, 0, uint24, 4);
                    this.handshakeQueue.removeData(i2);
                    if (uint8 != 0 && uint8 != 20) {
                        this.rs.updateHandshakeData(bArr, 0, 4);
                        this.rs.updateHandshakeData(bArr2, 0, uint24);
                    }
                    processHandshakeMessage(uint8, bArr2);
                    z2 = true;
                }
            }
        } while (z2);
    }

    private void processHandshakeMessage(short s2, byte[] bArr) throws IOException {
        Certificate certificate;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        if (s2 == 0) {
            if (this.connection_state == 12) {
                sendAlert((short) 1, (short) 100);
                return;
            }
            return;
        }
        if (s2 != 2) {
            if (s2 != 20) {
                TlsCredentials clientCredentials = null;
                switch (s2) {
                    case 11:
                        if (this.connection_state != 2) {
                            failWithError((short) 2, (short) 10);
                        } else {
                            Certificate certificate2 = Certificate.parse(byteArrayInputStream);
                            assertEmpty(byteArrayInputStream);
                            this.keyExchange.processServerCertificate(certificate2);
                            TlsAuthentication authentication = this.tlsClient.getAuthentication();
                            this.authentication = authentication;
                            authentication.notifyServerCertificate(certificate2);
                        }
                        this.connection_state = (short) 3;
                        break;
                    case 12:
                        short s3 = this.connection_state;
                        if (s3 != 2) {
                            if (s3 != 3) {
                                failWithError((short) 2, (short) 10);
                            }
                            this.connection_state = (short) 4;
                            break;
                        } else {
                            this.keyExchange.skipServerCertificate();
                            this.authentication = null;
                        }
                        this.keyExchange.processServerKeyExchange(byteArrayInputStream);
                        assertEmpty(byteArrayInputStream);
                        this.connection_state = (short) 4;
                    case 13:
                        short s4 = this.connection_state;
                        if (s4 != 3) {
                            if (s4 != 4) {
                                failWithError((short) 2, (short) 10);
                            }
                            this.connection_state = (short) 5;
                            break;
                        } else {
                            this.keyExchange.skipServerKeyExchange();
                        }
                        if (this.authentication == null) {
                            failWithError((short) 2, (short) 40);
                        }
                        int uint8 = TlsUtils.readUint8(byteArrayInputStream);
                        short[] sArr = new short[uint8];
                        for (int i2 = 0; i2 < uint8; i2++) {
                            sArr[i2] = TlsUtils.readUint8(byteArrayInputStream);
                        }
                        byte[] opaque16 = TlsUtils.readOpaque16(byteArrayInputStream);
                        assertEmpty(byteArrayInputStream);
                        Vector vector = new Vector();
                        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(opaque16);
                        while (byteArrayInputStream2.available() > 0) {
                            vector.addElement(X500Name.getInstance(ASN1Object.fromByteArray(TlsUtils.readOpaque16(byteArrayInputStream2))));
                        }
                        CertificateRequest certificateRequest = new CertificateRequest(sArr, vector);
                        this.certificateRequest = certificateRequest;
                        this.keyExchange.validateCertificateRequest(certificateRequest);
                        this.connection_state = (short) 5;
                    case 14:
                        short s5 = this.connection_state;
                        if (s5 != 3) {
                            if (s5 != 4 && s5 != 5) {
                                failWithError((short) 2, (short) 40);
                                break;
                            }
                        } else {
                            this.keyExchange.skipServerKeyExchange();
                        }
                        assertEmpty(byteArrayInputStream);
                        this.connection_state = (short) 6;
                        CertificateRequest certificateRequest2 = this.certificateRequest;
                        if (certificateRequest2 == null) {
                            this.keyExchange.skipClientCredentials();
                        } else {
                            clientCredentials = this.authentication.getClientCredentials(certificateRequest2);
                            TlsKeyExchange tlsKeyExchange = this.keyExchange;
                            if (clientCredentials == null) {
                                tlsKeyExchange.skipClientCredentials();
                                certificate = Certificate.EMPTY_CHAIN;
                            } else {
                                tlsKeyExchange.processClientCredentials(clientCredentials);
                                certificate = clientCredentials.getCertificate();
                            }
                            sendClientCertificate(certificate);
                        }
                        sendClientKeyExchange();
                        this.connection_state = (short) 7;
                        if (clientCredentials != null && (clientCredentials instanceof TlsSignerCredentials)) {
                            sendCertificateVerify(((TlsSignerCredentials) clientCredentials).generateCertificateSignature(this.rs.getCurrentHash()));
                            this.connection_state = (short) 8;
                        }
                        this.rs.writeMessage((short) 20, new byte[]{1}, 0, 1);
                        this.connection_state = (short) 9;
                        byte[] bArrGeneratePremasterSecret = this.keyExchange.generatePremasterSecret();
                        SecurityParameters securityParameters = this.securityParameters;
                        securityParameters.masterSecret = TlsUtils.PRF(bArrGeneratePremasterSecret, "master secret", TlsUtils.concat(securityParameters.clientRandom, securityParameters.serverRandom), 48);
                        Arrays.fill(bArrGeneratePremasterSecret, (byte) 0);
                        this.rs.clientCipherSpecDecided(this.tlsClient.getCompression(), this.tlsClient.getCipher());
                        byte[] bArrPRF = TlsUtils.PRF(this.securityParameters.masterSecret, "client finished", this.rs.getCurrentHash(), 12);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        TlsUtils.writeUint8((short) 20, byteArrayOutputStream);
                        TlsUtils.writeOpaque24(bArrPRF, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        this.rs.writeMessage((short) 22, byteArray, 0, byteArray.length);
                        this.connection_state = (short) 10;
                        break;
                }
                return;
            }
            if (this.connection_state == 11) {
                byte[] bArr2 = new byte[12];
                TlsUtils.readFully(bArr2, byteArrayInputStream);
                assertEmpty(byteArrayInputStream);
                if (!Arrays.constantTimeAreEqual(TlsUtils.PRF(this.securityParameters.masterSecret, "server finished", this.rs.getCurrentHash(), 12), bArr2)) {
                    failWithError((short) 2, (short) 40);
                }
                this.connection_state = (short) 12;
                this.appDataReady = true;
                return;
            }
        } else if (this.connection_state == 1) {
            TlsUtils.checkVersion(byteArrayInputStream, this);
            byte[] bArr3 = new byte[32];
            this.securityParameters.serverRandom = bArr3;
            TlsUtils.readFully(bArr3, byteArrayInputStream);
            byte[] opaque8 = TlsUtils.readOpaque8(byteArrayInputStream);
            if (opaque8.length > 32) {
                failWithError((short) 2, (short) 47);
            }
            this.tlsClient.notifySessionID(opaque8);
            int uint16 = TlsUtils.readUint16(byteArrayInputStream);
            if (!arrayContains(this.offeredCipherSuites, uint16) || uint16 == 255) {
                failWithError((short) 2, (short) 47);
            }
            this.tlsClient.notifySelectedCipherSuite(uint16);
            short uint82 = TlsUtils.readUint8(byteArrayInputStream);
            if (!arrayContains(this.offeredCompressionMethods, uint82)) {
                failWithError((short) 2, (short) 47);
            }
            this.tlsClient.notifySelectedCompressionMethod(uint82);
            Hashtable hashtable = new Hashtable();
            if (byteArrayInputStream.available() > 0) {
                ByteArrayInputStream byteArrayInputStream3 = new ByteArrayInputStream(TlsUtils.readOpaque16(byteArrayInputStream));
                while (byteArrayInputStream3.available() > 0) {
                    Integer num = new Integer(TlsUtils.readUint16(byteArrayInputStream3));
                    byte[] opaque162 = TlsUtils.readOpaque16(byteArrayInputStream3);
                    if (!num.equals(EXT_RenegotiationInfo) && this.clientExtensions.get(num) == null) {
                        failWithError((short) 2, AlertDescription.unsupported_extension);
                    }
                    if (hashtable.containsKey(num)) {
                        failWithError((short) 2, (short) 47);
                    }
                    hashtable.put(num, opaque162);
                }
            }
            assertEmpty(byteArrayInputStream);
            Integer num2 = EXT_RenegotiationInfo;
            boolean zContainsKey = hashtable.containsKey(num2);
            if (zContainsKey && !Arrays.constantTimeAreEqual((byte[]) hashtable.get(num2), createRenegotiationInfo(emptybuf))) {
                failWithError((short) 2, (short) 40);
            }
            this.tlsClient.notifySecureRenegotiation(zContainsKey);
            if (this.clientExtensions != null) {
                this.tlsClient.processServerExtensions(hashtable);
            }
            this.keyExchange = this.tlsClient.getKeyExchange();
            this.connection_state = (short) 2;
            return;
        }
        failWithError((short) 2, (short) 10);
    }

    private void safeReadData() throws IOException {
        try {
            this.rs.readData();
        } catch (RuntimeException e2) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e2;
        } catch (TlsFatalAlert e3) {
            if (!this.closed) {
                failWithError((short) 2, e3.getAlertDescription());
            }
            throw e3;
        } catch (IOException e4) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e4;
        }
    }

    private void safeWriteMessage(short s2, byte[] bArr, int i2, int i3) throws IOException {
        try {
            this.rs.writeMessage(s2, bArr, i2, i3);
        } catch (RuntimeException e2) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e2;
        } catch (TlsFatalAlert e3) {
            if (!this.closed) {
                failWithError((short) 2, e3.getAlertDescription());
            }
            throw e3;
        } catch (IOException e4) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e4;
        }
    }

    private void sendAlert(short s2, short s3) throws IOException {
        this.rs.writeMessage((short) 21, new byte[]{(byte) s2, (byte) s3}, 0, 2);
    }

    private void sendCertificateVerify(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 15, byteArrayOutputStream);
        TlsUtils.writeUint24(bArr.length + 2, byteArrayOutputStream);
        TlsUtils.writeOpaque16(bArr, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.rs.writeMessage((short) 22, byteArray, 0, byteArray.length);
    }

    private void sendClientCertificate(Certificate certificate) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 11, byteArrayOutputStream);
        certificate.encode(byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.rs.writeMessage((short) 22, byteArray, 0, byteArray.length);
    }

    private void sendClientKeyExchange() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 16, byteArrayOutputStream);
        this.keyExchange.generateClientKeyExchange(byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.rs.writeMessage((short) 22, byteArray, 0, byteArray.length);
    }

    private static void writeExtension(OutputStream outputStream, Integer num, byte[] bArr) throws IOException {
        TlsUtils.writeUint16(num.intValue(), outputStream);
        TlsUtils.writeOpaque16(bArr, outputStream);
    }

    public void assertEmpty(ByteArrayInputStream byteArrayInputStream) throws IOException {
        if (byteArrayInputStream.available() > 0) {
            throw new TlsFatalAlert((short) 50);
        }
    }

    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        failWithError((short) 1, (short) 0);
    }

    public void connect(CertificateVerifyer certificateVerifyer) throws IOException {
        connect(new LegacyTlsClient(certificateVerifyer));
    }

    public void connect(TlsClient tlsClient) throws IOException {
        if (tlsClient == null) {
            throw new IllegalArgumentException("'tlsClient' cannot be null");
        }
        if (this.tlsClient != null) {
            throw new IllegalStateException("connect can only be called once");
        }
        SecurityParameters securityParameters = new SecurityParameters();
        this.securityParameters = securityParameters;
        byte[] bArr = new byte[32];
        securityParameters.clientRandom = bArr;
        this.random.nextBytes(bArr);
        TlsUtils.writeGMTUnixTime(this.securityParameters.clientRandom, 0);
        TlsClientContextImpl tlsClientContextImpl = new TlsClientContextImpl(this.random, this.securityParameters);
        this.tlsClientContext = tlsClientContextImpl;
        this.tlsClient = tlsClient;
        tlsClient.init(tlsClientContextImpl);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeVersion(byteArrayOutputStream);
        byteArrayOutputStream.write(this.securityParameters.clientRandom);
        TlsUtils.writeUint8((short) 0, byteArrayOutputStream);
        this.offeredCipherSuites = this.tlsClient.getCipherSuites();
        Hashtable clientExtensions = this.tlsClient.getClientExtensions();
        this.clientExtensions = clientExtensions;
        boolean z2 = clientExtensions == null || clientExtensions.get(EXT_RenegotiationInfo) == null;
        int length = this.offeredCipherSuites.length;
        if (z2) {
            length++;
        }
        TlsUtils.writeUint16(length * 2, byteArrayOutputStream);
        TlsUtils.writeUint16Array(this.offeredCipherSuites, byteArrayOutputStream);
        if (z2) {
            TlsUtils.writeUint16(255, byteArrayOutputStream);
        }
        short[] compressionMethods = this.tlsClient.getCompressionMethods();
        this.offeredCompressionMethods = compressionMethods;
        TlsUtils.writeUint8((short) compressionMethods.length, byteArrayOutputStream);
        TlsUtils.writeUint8Array(this.offeredCompressionMethods, byteArrayOutputStream);
        if (this.clientExtensions != null) {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            Enumeration enumerationKeys = this.clientExtensions.keys();
            while (enumerationKeys.hasMoreElements()) {
                Integer num = (Integer) enumerationKeys.nextElement();
                writeExtension(byteArrayOutputStream2, num, (byte[]) this.clientExtensions.get(num));
            }
            TlsUtils.writeOpaque16(byteArrayOutputStream2.toByteArray(), byteArrayOutputStream);
        }
        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 1, byteArrayOutputStream3);
        TlsUtils.writeUint24(byteArrayOutputStream.size(), byteArrayOutputStream3);
        byteArrayOutputStream3.write(byteArrayOutputStream.toByteArray());
        byte[] byteArray = byteArrayOutputStream3.toByteArray();
        safeWriteMessage((short) 22, byteArray, 0, byteArray.length);
        this.connection_state = (short) 1;
        while (this.connection_state != 12) {
            safeReadData();
        }
        this.tlsInputStream = new TlsInputStream(this);
        this.tlsOutputStream = new TlsOutputStream(this);
    }

    public void flush() throws IOException {
        this.rs.flush();
    }

    public InputStream getInputStream() {
        return this.tlsInputStream;
    }

    public OutputStream getOutputStream() {
        return this.tlsOutputStream;
    }

    public void processData(short s2, byte[] bArr, int i2, int i3) throws IOException {
        switch (s2) {
            case 20:
                this.changeCipherSpecQueue.addData(bArr, i2, i3);
                processChangeCipherSpec();
                break;
            case 21:
                this.alertQueue.addData(bArr, i2, i3);
                processAlert();
                break;
            case 22:
                this.handshakeQueue.addData(bArr, i2, i3);
                processHandshake();
                break;
            case 23:
                if (!this.appDataReady) {
                    failWithError((short) 2, (short) 10);
                }
                this.applicationDataQueue.addData(bArr, i2, i3);
                processApplicationData();
                break;
        }
    }

    public int readApplicationData(byte[] bArr, int i2, int i3) throws IOException {
        while (this.applicationDataQueue.size() == 0) {
            if (this.closed) {
                if (this.failedWithError) {
                    throw new IOException(TLS_ERROR_MESSAGE);
                }
                return -1;
            }
            safeReadData();
        }
        int iMin = Math.min(i3, this.applicationDataQueue.size());
        this.applicationDataQueue.read(bArr, i2, iMin, 0);
        this.applicationDataQueue.removeData(iMin);
        return iMin;
    }

    public void writeData(byte[] bArr, int i2, int i3) throws IOException {
        if (this.closed) {
            if (!this.failedWithError) {
                throw new IOException("Sorry, connection has been closed, you cannot write more data");
            }
            throw new IOException(TLS_ERROR_MESSAGE);
        }
        safeWriteMessage((short) 23, emptybuf, 0, 0);
        do {
            int iMin = Math.min(i3, 16384);
            safeWriteMessage((short) 23, bArr, i2, iMin);
            i2 += iMin;
            i3 -= iMin;
        } while (i3 > 0);
    }
}
