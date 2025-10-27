package org.eclipse.jetty.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferUtil;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class HttpGenerator extends AbstractGenerator {
    private static final int CHUNK_SPACE = 12;
    private static final byte[] CONNECTION_;
    private static final byte[] CONNECTION_CLOSE;
    private static final byte[] CONNECTION_KEEP_ALIVE;
    private static final byte[] CONTENT_LENGTH_0;
    private static final byte[] CRLF;
    private static final byte[] LAST_CHUNK;
    private static byte[] SERVER;
    private static final byte[] TRANSFER_ENCODING_CHUNKED;
    private boolean _bufferChunked;
    protected boolean _bypass;
    private boolean _needCRLF;
    private boolean _needEOC;
    private static final Logger LOG = Log.getLogger((Class<?>) HttpGenerator.class);
    private static final Status[] __status = new Status[508];

    public static class Status {
        Buffer _reason;
        Buffer _responseLine;
        Buffer _schemeCode;

        private Status() {
        }
    }

    static {
        int length = HttpVersions.HTTP_1_1_BUFFER.length();
        for (int i2 = 0; i2 < __status.length; i2++) {
            HttpStatus.Code code = HttpStatus.getCode(i2);
            if (code != null) {
                String message = code.getMessage();
                int i3 = length + 5;
                int length2 = message.length() + i3 + 2;
                byte[] bArr = new byte[length2];
                HttpVersions.HTTP_1_1_BUFFER.peek(0, bArr, 0, length);
                bArr[length + 0] = 32;
                bArr[length + 1] = (byte) ((i2 / 100) + 48);
                bArr[length + 2] = (byte) (((i2 % 100) / 10) + 48);
                bArr[length + 3] = (byte) ((i2 % 10) + 48);
                bArr[length + 4] = 32;
                for (int i4 = 0; i4 < message.length(); i4++) {
                    bArr[i3 + i4] = (byte) message.charAt(i4);
                }
                bArr[message.length() + i3] = 13;
                bArr[length + 6 + message.length()] = 10;
                Status[] statusArr = __status;
                Status status = new Status();
                statusArr[i2] = status;
                status._reason = new ByteArrayBuffer(bArr, i3, (length2 - length) - 7, 0);
                statusArr[i2]._schemeCode = new ByteArrayBuffer(bArr, 0, i3, 0);
                statusArr[i2]._responseLine = new ByteArrayBuffer(bArr, 0, length2, 0);
            }
        }
        LAST_CHUNK = new byte[]{TarConstants.LF_NORMAL, 13, 10, 13, 10};
        CONTENT_LENGTH_0 = StringUtil.getBytes("Content-Length: 0\r\n");
        CONNECTION_KEEP_ALIVE = StringUtil.getBytes("Connection: keep-alive\r\n");
        CONNECTION_CLOSE = StringUtil.getBytes("Connection: close\r\n");
        CONNECTION_ = StringUtil.getBytes("Connection: ");
        CRLF = StringUtil.getBytes("\r\n");
        TRANSFER_ENCODING_CHUNKED = StringUtil.getBytes("Transfer-Encoding: chunked\r\n");
        SERVER = StringUtil.getBytes("Server: Jetty(7.0.x)\r\n");
    }

    public HttpGenerator(Buffers buffers, EndPoint endPoint) {
        super(buffers, endPoint);
        this._bypass = false;
        this._needCRLF = false;
        this._needEOC = false;
        this._bufferChunked = false;
    }

    private int flushMask() {
        Buffer buffer;
        Buffer buffer2 = this._header;
        int i2 = 0;
        int i3 = (buffer2 == null || buffer2.length() <= 0) ? 0 : 4;
        Buffer buffer3 = this._buffer;
        int i4 = i3 | ((buffer3 == null || buffer3.length() <= 0) ? 0 : 2);
        if (this._bypass && (buffer = this._content) != null && buffer.length() > 0) {
            i2 = 1;
        }
        return i4 | i2;
    }

    public static Buffer getReasonBuffer(int i2) {
        Status[] statusArr = __status;
        Status status = i2 < statusArr.length ? statusArr[i2] : null;
        if (status != null) {
            return status._reason;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void prepareBuffers() {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpGenerator.prepareBuffers():void");
    }

    public static void setServerVersion(String str) {
        SERVER = StringUtil.getBytes("Server: Jetty(" + str + ")\r\n");
    }

    @Override // org.eclipse.jetty.http.Generator
    public void addContent(Buffer buffer, boolean z2) throws IOException {
        Buffer buffer2;
        Buffer buffer3;
        if (this._noContent) {
            throw new IllegalStateException("NO CONTENT");
        }
        if (this._last || this._state == 4) {
            LOG.warn("Ignoring extra content {}", buffer);
            buffer.clear();
            return;
        }
        this._last = z2;
        Buffer buffer4 = this._content;
        if ((buffer4 != null && buffer4.length() > 0) || this._bufferChunked) {
            if (this._endp.isOutputShutdown()) {
                throw new EofException();
            }
            flushBuffer();
            Buffer buffer5 = this._content;
            if (buffer5 != null && buffer5.length() > 0) {
                if (this._bufferChunked) {
                    buffer3 = this._buffers.getBuffer(this._content.length() + 12 + buffer.length());
                    buffer3.put(this._content);
                    byte[] bArr = HttpTokens.CRLF;
                    buffer3.put(bArr);
                    BufferUtil.putHexInt(buffer3, buffer.length());
                    buffer3.put(bArr);
                    buffer3.put(buffer);
                } else {
                    buffer3 = this._buffers.getBuffer(this._content.length() + buffer.length());
                    buffer3.put(this._content);
                    buffer3.put(buffer);
                }
                buffer = buffer3;
            }
        }
        this._content = buffer;
        this._contentWritten += buffer.length();
        if (this._head) {
            buffer.clear();
            this._content = null;
            return;
        }
        if (this._endp != null && (((buffer2 = this._buffer) == null || buffer2.length() == 0) && this._content.length() > 0 && (this._last || (isCommitted() && this._content.length() > 1024)))) {
            this._bypass = true;
            return;
        }
        if (this._bufferChunked) {
            return;
        }
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        this._content.skip(this._buffer.put(this._content));
        if (this._content.length() == 0) {
            this._content = null;
        }
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public void complete() throws IOException {
        if (this._state == 4) {
            return;
        }
        super.complete();
        if (this._state < 3) {
            this._state = 3;
            if (this._contentLength == -2) {
                this._needEOC = true;
            }
        }
        flushBuffer();
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x02b0 A[Catch: ArrayIndexOutOfBoundsException -> 0x0528, TryCatch #0 {ArrayIndexOutOfBoundsException -> 0x0528, blocks: (B:21:0x0039, B:23:0x0052, B:26:0x005c, B:28:0x0082, B:30:0x00a4, B:32:0x00a9, B:75:0x0197, B:77:0x019d, B:79:0x01a1, B:82:0x01c7, B:84:0x01dc, B:185:0x0358, B:87:0x01e3, B:95:0x01f6, B:96:0x01fc, B:98:0x0202, B:100:0x020f, B:102:0x021b, B:104:0x0224, B:105:0x022f, B:107:0x023f, B:114:0x024d, B:115:0x0254, B:118:0x0260, B:120:0x026a, B:121:0x026f, B:127:0x027f, B:129:0x0288, B:128:0x0285, B:130:0x0291, B:132:0x0297, B:133:0x029e, B:135:0x02a2, B:137:0x02a8, B:139:0x02b0, B:141:0x02b6, B:142:0x02ba, B:144:0x02c2, B:146:0x02c8, B:148:0x02ce, B:150:0x02d4, B:152:0x02e2, B:154:0x02e5, B:156:0x02f3, B:160:0x02fd, B:162:0x0306, B:184:0x0352, B:161:0x0303, B:163:0x030c, B:165:0x0310, B:167:0x0316, B:169:0x031d, B:171:0x0323, B:172:0x0327, B:174:0x032f, B:176:0x0335, B:178:0x033b, B:181:0x0344, B:183:0x034d, B:182:0x034a, B:188:0x0378, B:239:0x043c, B:242:0x0444, B:244:0x044b, B:246:0x0457, B:247:0x045d, B:248:0x0464, B:249:0x0465, B:250:0x046c, B:252:0x0472, B:254:0x0479, B:256:0x047f, B:259:0x0489, B:261:0x048d, B:263:0x0496, B:265:0x04bd, B:267:0x04c6, B:269:0x04ed, B:271:0x050a, B:273:0x0510, B:275:0x0516, B:276:0x051d, B:194:0x0388, B:196:0x038e, B:202:0x039c, B:203:0x03a5, B:204:0x03b1, B:206:0x03b9, B:208:0x03bf, B:214:0x03cd, B:215:0x03d3, B:217:0x03d7, B:219:0x03dd, B:221:0x03e3, B:224:0x03ed, B:226:0x03f1, B:227:0x0415, B:229:0x041d, B:234:0x0427, B:236:0x042f, B:238:0x0435, B:31:0x00a7, B:33:0x00b5, B:35:0x00b9, B:37:0x00c2, B:42:0x00cc, B:43:0x00d2, B:45:0x00d9, B:49:0x00e1, B:51:0x0115, B:53:0x013a, B:58:0x0163, B:61:0x0169, B:63:0x0171, B:64:0x0174, B:66:0x017a, B:72:0x018c, B:74:0x0194, B:52:0x0135, B:54:0x0142, B:56:0x0146, B:57:0x014e), top: B:281:0x0039 }] */
    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void completeHeader(org.eclipse.jetty.http.HttpFields r28, boolean r29) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpGenerator.completeHeader(org.eclipse.jetty.http.HttpFields, boolean):void");
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public int flushBuffer() throws IOException {
        Buffer buffer;
        Boolean bool;
        Buffer buffer2;
        Buffer buffer3;
        try {
            if (this._state == 0) {
                throw new IllegalStateException("State==HEADER");
            }
            prepareBuffers();
            if (this._endp == null) {
                if (this._needCRLF && (buffer3 = this._buffer) != null) {
                    buffer3.put(HttpTokens.CRLF);
                }
                if (this._needEOC && (buffer2 = this._buffer) != null && !this._head) {
                    buffer2.put(LAST_CHUNK);
                }
                this._needCRLF = false;
                this._needEOC = false;
                return 0;
            }
            int iFlushMask = flushMask();
            int iFlush = -1;
            int i2 = 0;
            while (true) {
                switch (iFlushMask) {
                    case 0:
                        Buffer buffer4 = this._header;
                        if (buffer4 != null) {
                            buffer4.clear();
                        }
                        this._bypass = false;
                        this._bufferChunked = false;
                        Buffer buffer5 = this._buffer;
                        if (buffer5 != null) {
                            buffer5.clear();
                            if (this._contentLength == -2) {
                                this._buffer.setPutIndex(12);
                                this._buffer.setGetIndex(12);
                                Buffer buffer6 = this._content;
                                if (buffer6 != null && buffer6.length() < this._buffer.space() && this._state != 3) {
                                    this._buffer.put(this._content);
                                    this._content.clear();
                                    this._content = null;
                                }
                            }
                        }
                        if (this._needCRLF || this._needEOC || !((buffer = this._content) == null || buffer.length() == 0)) {
                            prepareBuffers();
                        } else {
                            if (this._state == 3) {
                                this._state = 4;
                            }
                            if (this._state == 4 && (bool = this._persistent) != null && !bool.booleanValue() && this._status != 100 && this._method == null) {
                                this._endp.shutdownOutput();
                            }
                        }
                        iFlush = 0;
                        break;
                    case 1:
                        iFlush = this._endp.flush(this._content);
                        break;
                    case 2:
                        iFlush = this._endp.flush(this._buffer);
                        break;
                    case 3:
                        iFlush = this._endp.flush(this._buffer, this._content, null);
                        break;
                    case 4:
                        iFlush = this._endp.flush(this._header);
                        break;
                    case 5:
                        iFlush = this._endp.flush(this._header, this._content, null);
                        break;
                    case 6:
                        iFlush = this._endp.flush(this._header, this._buffer, null);
                        break;
                    case 7:
                        throw new IllegalStateException();
                }
                if (iFlush > 0) {
                    i2 += iFlush;
                }
                int iFlushMask2 = flushMask();
                if (iFlush > 0 || (iFlushMask2 != 0 && iFlushMask == 0)) {
                    iFlushMask = iFlushMask2;
                }
            }
            return i2;
        } catch (IOException e2) {
            LOG.ignore(e2);
            if (e2 instanceof EofException) {
                throw e2;
            }
            throw new EofException(e2);
        }
    }

    public int getBytesBuffered() {
        Buffer buffer = this._header;
        int length = buffer == null ? 0 : buffer.length();
        Buffer buffer2 = this._buffer;
        int length2 = length + (buffer2 == null ? 0 : buffer2.length());
        Buffer buffer3 = this._content;
        return length2 + (buffer3 != null ? buffer3.length() : 0);
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public boolean isBufferFull() {
        Buffer buffer;
        return super.isBufferFull() || this._bufferChunked || this._bypass || (this._contentLength == -2 && (buffer = this._buffer) != null && buffer.space() < 12);
    }

    public boolean isEmpty() {
        Buffer buffer;
        Buffer buffer2;
        Buffer buffer3 = this._header;
        return (buffer3 == null || buffer3.length() == 0) && ((buffer = this._buffer) == null || buffer.length() == 0) && ((buffer2 = this._content) == null || buffer2.length() == 0);
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public boolean isRequest() {
        return this._method != null;
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public boolean isResponse() {
        return this._method == null;
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator
    public int prepareUncheckedAddContent() throws IOException {
        if (this._noContent || this._last || this._state == 4) {
            return -1;
        }
        Buffer buffer = this._content;
        if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
            flushBuffer();
            if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
                throw new IllegalStateException("FULL");
            }
        }
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        this._contentWritten -= this._buffer.length();
        if (this._head) {
            return Integer.MAX_VALUE;
        }
        return this._buffer.space() - (this._contentLength == -2 ? 12 : 0);
    }

    @Override // org.eclipse.jetty.http.AbstractGenerator, org.eclipse.jetty.http.Generator
    public void reset() {
        EndPoint endPoint;
        Boolean bool = this._persistent;
        if (bool != null && !bool.booleanValue() && (endPoint = this._endp) != null && !endPoint.isOutputShutdown()) {
            try {
                this._endp.shutdownOutput();
            } catch (IOException e2) {
                LOG.ignore(e2);
            }
        }
        super.reset();
        Buffer buffer = this._buffer;
        if (buffer != null) {
            buffer.clear();
        }
        Buffer buffer2 = this._header;
        if (buffer2 != null) {
            buffer2.clear();
        }
        if (this._content != null) {
            this._content = null;
        }
        this._bypass = false;
        this._needCRLF = false;
        this._needEOC = false;
        this._bufferChunked = false;
        this._method = null;
        this._uri = null;
        this._noContent = false;
    }

    public void send1xx(int i2) throws IOException {
        if (this._state != 0) {
            return;
        }
        if (i2 < 100 || i2 > 199) {
            throw new IllegalArgumentException("!1xx");
        }
        Status status = __status[i2];
        if (status == null) {
            throw new IllegalArgumentException(i2 + "?");
        }
        if (this._header == null) {
            this._header = this._buffers.getHeader();
        }
        this._header.put(status._responseLine);
        this._header.put(HttpTokens.CRLF);
        while (this._header.length() > 0) {
            try {
                int iFlush = this._endp.flush(this._header);
                if (iFlush < 0) {
                    throw new EofException();
                }
                if (iFlush == 0) {
                    Thread.sleep(100L);
                }
            } catch (InterruptedException e2) {
                LOG.debug(e2);
                throw new InterruptedIOException(e2.toString());
            }
        }
    }

    public void sendResponse(Buffer buffer) throws IOException {
        Buffer buffer2;
        if (this._noContent || this._state != 0 || (((buffer2 = this._content) != null && buffer2.length() > 0) || this._bufferChunked || this._head)) {
            throw new IllegalStateException();
        }
        this._last = true;
        this._content = buffer;
        this._bypass = true;
        this._state = 3;
        long length = buffer.length();
        this._contentWritten = length;
        this._contentLength = length;
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = Integer.valueOf(this._state);
        Buffer buffer = this._header;
        objArr[2] = Integer.valueOf(buffer == null ? -1 : buffer.length());
        Buffer buffer2 = this._buffer;
        objArr[3] = Integer.valueOf(buffer2 == null ? -1 : buffer2.length());
        Buffer buffer3 = this._content;
        objArr[4] = Integer.valueOf(buffer3 != null ? buffer3.length() : -1);
        return String.format("%s{s=%d,h=%d,b=%d,c=%d}", objArr);
    }
}
