package org.eclipse.jetty.http;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.IOException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.io.bio.StreamEndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class HttpParser implements Parser {
    private static final Logger LOG = Log.getLogger((Class<?>) HttpParser.class);
    public static final int STATE_CHUNK = 6;
    public static final int STATE_CHUNKED_CONTENT = 3;
    public static final int STATE_CHUNK_PARAMS = 5;
    public static final int STATE_CHUNK_SIZE = 4;
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 0;
    public static final int STATE_END0 = -8;
    public static final int STATE_END1 = -7;
    public static final int STATE_EOF_CONTENT = 1;
    public static final int STATE_FIELD0 = -13;
    public static final int STATE_FIELD2 = -6;
    public static final int STATE_HEADER = -5;
    public static final int STATE_HEADER_IN_NAME = -3;
    public static final int STATE_HEADER_IN_VALUE = -1;
    public static final int STATE_HEADER_NAME = -4;
    public static final int STATE_HEADER_VALUE = -2;
    public static final int STATE_SEEKING_EOF = 7;
    public static final int STATE_SPACE1 = -12;
    public static final int STATE_SPACE2 = -9;
    public static final int STATE_START = -14;
    public static final int STATE_STATUS = -11;
    public static final int STATE_URI = -10;
    private Buffer _body;
    private Buffer _buffer;
    private final Buffers _buffers;
    private BufferCache.CachedBuffer _cached;
    protected int _chunkLength;
    protected int _chunkPosition;
    protected long _contentLength;
    protected long _contentPosition;
    protected final View _contentView;
    private final EndPoint _endp;
    protected byte _eol;
    private boolean _forceContentBuffer;
    private final EventHandler _handler;
    private boolean _headResponse;
    private Buffer _header;
    protected int _length;
    private String _multiLineValue;
    private boolean _persistent;
    private int _responseStatus;
    protected int _state;
    private final View.CaseInsensitive _tok0;
    private final View.CaseInsensitive _tok1;

    public static abstract class EventHandler {
        public abstract void content(Buffer buffer) throws IOException;

        public void earlyEOF() {
        }

        public void headerComplete() throws IOException {
        }

        public void messageComplete(long j2) throws IOException {
        }

        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
        }

        public abstract void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException;

        public abstract void startResponse(Buffer buffer, int i2, Buffer buffer2) throws IOException;
    }

    public HttpParser(Buffer buffer, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._endp = null;
        this._buffers = null;
        this._header = buffer;
        this._buffer = buffer;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive(this._header);
        this._tok1 = new View.CaseInsensitive(this._header);
    }

    public int available() throws IOException {
        View view = this._contentView;
        if (view != null && view.length() > 0) {
            return this._contentView.length();
        }
        if (this._endp.isBlocking()) {
            if (this._state <= 0) {
                return 0;
            }
            EndPoint endPoint = this._endp;
            return (!(endPoint instanceof StreamEndPoint) || ((StreamEndPoint) endPoint).getInputStream().available() <= 0) ? 0 : 1;
        }
        parseNext();
        View view2 = this._contentView;
        if (view2 == null) {
            return 0;
        }
        return view2.length();
    }

    public Buffer blockForContent(long j2) throws IOException {
        EndPoint endPoint;
        if (this._contentView.length() > 0) {
            return this._contentView;
        }
        if (getState() <= 0 || isState(7)) {
            return null;
        }
        try {
            parseNext();
            while (this._contentView.length() == 0 && !isState(0) && !isState(7) && (endPoint = this._endp) != null && endPoint.isOpen()) {
                if (!this._endp.isBlocking()) {
                    if (parseNext() <= 0) {
                        if (!this._endp.blockReadable(j2)) {
                            this._endp.close();
                            throw new EofException("timeout");
                        }
                    }
                }
                parseNext();
            }
            if (this._contentView.length() > 0) {
                return this._contentView;
            }
            return null;
        } catch (IOException e2) {
            this._endp.close();
            throw e2;
        }
    }

    public int fill() throws IOException {
        Buffer buffer;
        Buffer buffer2;
        if (this._buffer == null) {
            this._buffer = getHeaderBuffer();
        }
        if (this._state > 0) {
            Buffer buffer3 = this._buffer;
            Buffer buffer4 = this._header;
            if (buffer3 == buffer4 && buffer4 != null && !buffer4.hasContent() && (buffer2 = this._body) != null && buffer2.hasContent()) {
                Buffer buffer5 = this._body;
                this._buffer = buffer5;
                return buffer5.length();
            }
        }
        Buffer buffer6 = this._buffer;
        Buffer buffer7 = this._header;
        if (buffer6 == buffer7 && this._state > 0 && buffer7.length() == 0 && ((this._forceContentBuffer || this._contentLength - this._contentPosition > this._header.capacity()) && ((buffer = this._body) != null || this._buffers != null))) {
            if (buffer == null) {
                this._body = this._buffers.getBuffer();
            }
            this._buffer = this._body;
        }
        if (this._endp == null) {
            return -1;
        }
        Buffer buffer8 = this._buffer;
        if (buffer8 == this._body || this._state > 0) {
            buffer8.compact();
        }
        if (this._buffer.space() == 0) {
            LOG.warn("HttpParser Full for {} ", this._endp);
            this._buffer.clear();
            StringBuilder sb = new StringBuilder();
            sb.append("FULL ");
            sb.append(this._buffer == this._body ? TtmlNode.TAG_BODY : TtmlNode.TAG_HEAD);
            throw new HttpException(413, sb.toString());
        }
        try {
            return this._endp.fill(this._buffer);
        } catch (IOException e2) {
            LOG.debug(e2);
            if (e2 instanceof EofException) {
                throw e2;
            }
            throw new EofException(e2);
        }
    }

    public Buffer getBodyBuffer() {
        return this._body;
    }

    public long getContentLength() {
        return this._contentLength;
    }

    public long getContentRead() {
        return this._contentPosition;
    }

    public Buffer getHeaderBuffer() {
        if (this._header == null) {
            Buffer header = this._buffers.getHeader();
            this._header = header;
            this._tok0.update(header);
            this._tok1.update(this._header);
        }
        return this._header;
    }

    public int getState() {
        return this._state;
    }

    public boolean inContentState() {
        return this._state > 0;
    }

    public boolean inHeaderState() {
        return this._state < 0;
    }

    public boolean isChunking() {
        return this._contentLength == -2;
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isComplete() {
        return isState(0);
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isIdle() {
        return isState(-14);
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isMoreInBuffer() throws IOException {
        Buffer buffer;
        Buffer buffer2 = this._header;
        return (buffer2 != null && buffer2.hasContent()) || ((buffer = this._body) != null && buffer.hasContent());
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isPersistent() {
        return this._persistent;
    }

    public boolean isState(int i2) {
        return this._state == i2;
    }

    public void parse() throws IOException {
        if (this._state == 0) {
            reset();
        }
        if (this._state != -14) {
            throw new IllegalStateException("!START");
        }
        while (this._state != 0 && parseNext() >= 0) {
        }
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean parseAvailable() throws IOException {
        Buffer buffer;
        boolean z2 = parseNext() > 0;
        while (!isComplete() && (buffer = this._buffer) != null && buffer.length() > 0 && !this._contentView.hasContent()) {
            z2 |= parseNext() > 0;
        }
        return z2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x043b, code lost:
    
        r3 = r17._responseStatus;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x043d, code lost:
    
        if (r3 <= 0) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x0441, code lost:
    
        if (r3 == 304) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0445, code lost:
    
        if (r3 == 204) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x0449, code lost:
    
        if (r3 >= 200) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x044b, code lost:
    
        r17._contentLength = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x0456, code lost:
    
        if (r17._contentLength != (-3)) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x0458, code lost:
    
        if (r3 == 0) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x045c, code lost:
    
        if (r3 == 304) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x0460, code lost:
    
        if (r3 == 204) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0464, code lost:
    
        if (r3 >= 200) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0467, code lost:
    
        r17._contentLength = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x046c, code lost:
    
        r3 = 0;
        r17._contentLength = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x0471, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0473, code lost:
    
        r17._contentPosition = r3;
        r17._eol = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0477, code lost:
    
        if (r9 != 13) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x047f, code lost:
    
        if (r17._buffer.hasContent() == false) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x0489, code lost:
    
        if (r17._buffer.peek() != 10) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x048b, code lost:
    
        r17._eol = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0493, code lost:
    
        r2 = r17._contentLength;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x049a, code lost:
    
        if (r2 <= 2147483647L) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x049c, code lost:
    
        r2 = Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x04a0, code lost:
    
        r2 = (int) r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x04a2, code lost:
    
        if (r2 == (-2)) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x04a4, code lost:
    
        if (r2 == (-1)) goto L284;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x04a6, code lost:
    
        if (r2 == 0) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x04a8, code lost:
    
        r17._state = 2;
        r17._handler.headerComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x04b2, code lost:
    
        r17._handler.headerComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x04b9, code lost:
    
        if (r17._persistent != false) goto L281;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x04bb, code lost:
    
        r2 = r17._responseStatus;
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x04bf, code lost:
    
        if (r2 < 100) goto L280;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x04c3, code lost:
    
        if (r2 >= 200) goto L280;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x04c6, code lost:
    
        r2 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x04c8, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x04c9, code lost:
    
        r17._state = r2;
        r17._handler.messageComplete(r17._contentPosition);
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x04d3, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x04d4, code lost:
    
        r17._state = 1;
        r17._handler.headerComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x04dd, code lost:
    
        r17._state = 3;
        r17._handler.headerComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x04e6, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x06e5, code lost:
    
        r3 = r17._responseStatus;
     */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x06e7, code lost:
    
        if (r3 <= 0) goto L379;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x06eb, code lost:
    
        if (r17._headResponse == false) goto L379;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x06ef, code lost:
    
        if (r17._persistent != false) goto L377;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x06f3, code lost:
    
        if (r3 < 100) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x06f7, code lost:
    
        if (r3 >= 200) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x06fa, code lost:
    
        r3 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x06fc, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x06fd, code lost:
    
        r17._state = r3;
        r17._handler.messageComplete(r17._contentLength);
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x0706, code lost:
    
        r3 = r17._buffer.length();
        r5 = r17._state;
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x070e, code lost:
    
        r6 = r17._state;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x0710, code lost:
    
        if (r6 <= 0) goto L560;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x0712, code lost:
    
        if (r3 <= 0) goto L561;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x0714, code lost:
    
        if (r5 == r6) goto L385;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x0716, code lost:
    
        r4 = r4 + 1;
        r5 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:386:0x071b, code lost:
    
        if (r17._eol != 13) goto L558;
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x0725, code lost:
    
        if (r17._buffer.peek() != 10) goto L559;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0727, code lost:
    
        r17._eol = r17._buffer.get();
        r3 = r17._buffer.length();
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x0737, code lost:
    
        r17._eol = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x073b, code lost:
    
        switch(r17._state) {
            case 1: goto L564;
            case 2: goto L563;
            case 3: goto L483;
            case 4: goto L439;
            case 5: goto L418;
            case 6: goto L410;
            case 7: goto L398;
            default: goto L394;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x0750, code lost:
    
        if (r17._buffer.length() <= 2) goto L402;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0752, code lost:
    
        r17._state = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x0754, code lost:
    
        r17._endp.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x0760, code lost:
    
        if (r17._buffer.length() <= 0) goto L573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x076c, code lost:
    
        if (java.lang.Character.isWhitespace(r17._buffer.get()) != false) goto L576;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x076f, code lost:
    
        r17._state = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x0771, code lost:
    
        r17._endp.close();
        r17._buffer.clear();
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x077c, code lost:
    
        r17._buffer.clear();
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0782, code lost:
    
        r6 = r17._chunkLength - r17._chunkPosition;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0788, code lost:
    
        if (r6 != 0) goto L565;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x078a, code lost:
    
        r17._state = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x0791, code lost:
    
        if (r3 <= r6) goto L416;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0793, code lost:
    
        r3 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:416:0x0794, code lost:
    
        r2 = r17._buffer.get(r3);
        r17._contentPosition += r2.length();
        r17._chunkPosition += r2.length();
        r17._contentView.update(r2);
        r17._handler.content(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x07b8, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x07b9, code lost:
    
        r3 = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x07c0, code lost:
    
        if (r3 == 13) goto L424;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x07c4, code lost:
    
        if (r3 != 10) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x07ce, code lost:
    
        r17._eol = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x07d2, code lost:
    
        if (r17._chunkLength != 0) goto L438;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x07d4, code lost:
    
        if (r3 != 13) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x07dc, code lost:
    
        if (r17._buffer.hasContent() == false) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x07e6, code lost:
    
        if (r17._buffer.peek() != 10) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x07e8, code lost:
    
        r17._eol = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x07f2, code lost:
    
        if (r17._persistent == false) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x07f4, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x07f6, code lost:
    
        r2 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x07f7, code lost:
    
        r17._state = r2;
        r17._handler.messageComplete(r17._contentPosition);
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x0801, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x0802, code lost:
    
        r17._state = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x0806, code lost:
    
        r3 = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x080d, code lost:
    
        if (r3 == 13) goto L468;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:0x0811, code lost:
    
        if (r3 != 10) goto L444;
     */
    /* JADX WARN: Code restructure failed: missing block: B:444:0x0814, code lost:
    
        if (r3 <= 32) goto L466;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x0818, code lost:
    
        if (r3 != 59) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:449:0x081d, code lost:
    
        if (r3 < 48) goto L454;
     */
    /* JADX WARN: Code restructure failed: missing block: B:451:0x0821, code lost:
    
        if (r3 > 57) goto L454;
     */
    /* JADX WARN: Code restructure failed: missing block: B:452:0x0823, code lost:
    
        r17._chunkLength = (r17._chunkLength * 16) + (r3 - 48);
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x0831, code lost:
    
        if (r3 < 97) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x0835, code lost:
    
        if (r3 > 102) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x0837, code lost:
    
        r17._chunkLength = (r17._chunkLength * 16) + ((r3 + 10) - 97);
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x0845, code lost:
    
        if (r3 < 65) goto L567;
     */
    /* JADX WARN: Code restructure failed: missing block: B:462:0x0849, code lost:
    
        if (r3 > 70) goto L568;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x084b, code lost:
    
        r17._chunkLength = (r17._chunkLength * 16) + ((r3 + 10) - 65);
     */
    /* JADX WARN: Code restructure failed: missing block: B:465:0x086d, code lost:
    
        throw new java.io.IOException("bad chunk char: " + ((int) r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:466:0x086e, code lost:
    
        r17._state = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:468:0x0876, code lost:
    
        r17._eol = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x087d, code lost:
    
        if (r17._chunkLength != 0) goto L482;
     */
    /* JADX WARN: Code restructure failed: missing block: B:470:0x087f, code lost:
    
        if (r3 != 13) goto L476;
     */
    /* JADX WARN: Code restructure failed: missing block: B:472:0x0887, code lost:
    
        if (r17._buffer.hasContent() == false) goto L476;
     */
    /* JADX WARN: Code restructure failed: missing block: B:474:0x0891, code lost:
    
        if (r17._buffer.peek() != 10) goto L476;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x0893, code lost:
    
        r17._eol = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x089d, code lost:
    
        if (r17._persistent == false) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:478:0x089f, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:479:0x08a1, code lost:
    
        r2 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:480:0x08a2, code lost:
    
        r17._state = r2;
        r17._handler.messageComplete(r17._contentPosition);
     */
    /* JADX WARN: Code restructure failed: missing block: B:481:0x08ac, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:482:0x08ad, code lost:
    
        r17._state = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x08b1, code lost:
    
        r3 = r17._buffer.peek();
     */
    /* JADX WARN: Code restructure failed: missing block: B:484:0x08bd, code lost:
    
        if (r3 == 13) goto L494;
     */
    /* JADX WARN: Code restructure failed: missing block: B:485:0x08bf, code lost:
    
        if (r3 != 10) goto L487;
     */
    /* JADX WARN: Code restructure failed: missing block: B:487:0x08c2, code lost:
    
        if (r3 > 32) goto L490;
     */
    /* JADX WARN: Code restructure failed: missing block: B:488:0x08c4, code lost:
    
        r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:491:0x08cb, code lost:
    
        r17._chunkLength = 0;
        r17._chunkPosition = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:493:0x08d0, code lost:
    
        r17._state = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:494:0x08d3, code lost:
    
        r17._eol = r17._buffer.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:496:0x08de, code lost:
    
        r4 = r17._contentLength;
        r6 = r17._contentPosition;
        r4 = r4 - r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:497:0x08e7, code lost:
    
        if (r4 != 0) goto L504;
     */
    /* JADX WARN: Code restructure failed: missing block: B:499:0x08eb, code lost:
    
        if (r17._persistent == false) goto L501;
     */
    /* JADX WARN: Code restructure failed: missing block: B:500:0x08ed, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:501:0x08ef, code lost:
    
        r3 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x08f0, code lost:
    
        r17._state = r3;
        r17._handler.messageComplete(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:503:0x08f8, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:505:0x08fc, code lost:
    
        if (r3 <= r4) goto L507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:506:0x08fe, code lost:
    
        r3 = (int) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:507:0x08ff, code lost:
    
        r2 = r17._buffer.get(r3);
        r17._contentPosition += r2.length();
        r17._contentView.update(r2);
        r17._handler.content(r2);
        r2 = r17._contentPosition;
     */
    /* JADX WARN: Code restructure failed: missing block: B:508:0x091f, code lost:
    
        if (r2 != r17._contentLength) goto L578;
     */
    /* JADX WARN: Code restructure failed: missing block: B:510:0x0923, code lost:
    
        if (r17._persistent == false) goto L512;
     */
    /* JADX WARN: Code restructure failed: missing block: B:511:0x0925, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:512:0x0927, code lost:
    
        r4 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:513:0x0928, code lost:
    
        r17._state = r4;
        r17._handler.messageComplete(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:514:0x092f, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x0931, code lost:
    
        r2 = r17._buffer;
        r2 = r2.get(r2.length());
        r17._contentPosition += r2.length();
        r17._contentView.update(r2);
        r17._handler.content(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x0950, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:519:0x0957, code lost:
    
        r3 = r17._buffer.length();
     */
    /* JADX WARN: Code restructure failed: missing block: B:520:0x095a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x095b, code lost:
    
        r2 = r0;
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:522:0x095e, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:578:?, code lost:
    
        return 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0278 A[Catch: HttpException -> 0x095f, TryCatch #6 {HttpException -> 0x095f, blocks: (B:80:0x012c, B:81:0x0132, B:83:0x0136, B:85:0x013e, B:86:0x0153, B:88:0x0157, B:89:0x015f, B:90:0x0191, B:91:0x0196, B:96:0x01a1, B:98:0x01a5, B:99:0x01aa, B:100:0x01bd, B:102:0x01c1, B:104:0x01c9, B:105:0x01de, B:107:0x01e2, B:108:0x01ea, B:109:0x021c, B:116:0x022e, B:117:0x0238, B:119:0x023c, B:121:0x0240, B:122:0x0254, B:123:0x025a, B:125:0x025e, B:126:0x0272, B:127:0x0278, B:134:0x0289, B:136:0x0290, B:137:0x0295, B:138:0x02a9, B:140:0x02ad, B:142:0x02b1, B:143:0x02c5, B:144:0x02cb, B:146:0x02cf, B:147:0x02e3, B:152:0x02f1, B:154:0x02f5, B:156:0x02fd, B:158:0x0305, B:224:0x03f4, B:229:0x0400, B:231:0x040d, B:233:0x041f, B:234:0x043b, B:242:0x044b, B:256:0x0473, B:258:0x0479, B:260:0x0481, B:262:0x048b, B:263:0x0493, B:271:0x04a8, B:273:0x04b2, B:275:0x04bb, B:282:0x04c9, B:284:0x04d4, B:285:0x04dd, B:266:0x04a0, B:243:0x0450, B:253:0x0467, B:254:0x046c, B:160:0x0309, B:164:0x0318, B:166:0x031e, B:168:0x0328, B:176:0x033d, B:179:0x0343, B:182:0x034f, B:184:0x0354, B:185:0x0361, B:186:0x0362, B:188:0x036f, B:189:0x0373, B:191:0x037f, B:192:0x0382, B:195:0x038b, B:196:0x0393, B:197:0x0394, B:204:0x03a3, B:209:0x03aa, B:211:0x03b8, B:216:0x03cb, B:223:0x03da, B:167:0x0321, B:163:0x030f, B:287:0x04e7, B:291:0x04f4, B:293:0x04f8, B:295:0x052a, B:299:0x0539, B:294:0x050e, B:304:0x055b, B:306:0x055f, B:307:0x058a, B:311:0x059e, B:313:0x05ae, B:315:0x05ba, B:318:0x05d7, B:322:0x05ef, B:327:0x0605, B:332:0x0626, B:335:0x0636, B:336:0x0660, B:342:0x0671, B:343:0x0678, B:344:0x0679, B:346:0x0682, B:347:0x068c, B:349:0x0694, B:353:0x06b4, B:359:0x06c8, B:360:0x06cf, B:361:0x06d0, B:364:0x06db, B:365:0x06e5, B:367:0x06e9, B:369:0x06ed, B:378:0x06fd, B:379:0x0706, B:380:0x070e, B:384:0x0716, B:385:0x0719, B:387:0x071d, B:389:0x0727, B:518:0x0951, B:401:0x0754, B:409:0x077c, B:402:0x075a, B:404:0x0762, B:408:0x0771, B:410:0x0782, B:412:0x078a, B:416:0x0794, B:418:0x07b9, B:424:0x07ce, B:427:0x07d6, B:429:0x07de, B:431:0x07e8, B:432:0x07f0, B:436:0x07f7, B:438:0x0802, B:439:0x0806, B:452:0x0823, B:458:0x0837, B:463:0x084b, B:464:0x0857, B:465:0x086d, B:466:0x086e, B:468:0x0876, B:471:0x0881, B:473:0x0889, B:475:0x0893, B:476:0x089b, B:480:0x08a2, B:482:0x08ad, B:483:0x08b1, B:488:0x08c4, B:493:0x08d0, B:494:0x08d3, B:496:0x08de, B:498:0x08e9, B:502:0x08f0, B:504:0x08f9, B:506:0x08fe, B:507:0x08ff, B:509:0x0921, B:513:0x0928, B:516:0x0931), top: B:539:0x012c, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00be A[Catch: HttpException -> 0x0963, TryCatch #1 {HttpException -> 0x0963, blocks: (B:3:0x0004, B:6:0x0009, B:8:0x000d, B:9:0x0013, B:11:0x0019, B:13:0x0021, B:15:0x0029, B:57:0x00d8, B:59:0x00e1, B:60:0x00e9, B:62:0x00f3, B:65:0x00f9, B:66:0x00fc, B:69:0x010a, B:70:0x010e, B:71:0x0113, B:72:0x0114, B:207:0x03a7, B:407:0x076f, B:491:0x08cb, B:29:0x0069, B:31:0x006f, B:33:0x0077, B:35:0x007b, B:36:0x0099, B:40:0x00a1, B:42:0x00a7, B:43:0x00ac, B:47:0x00c2, B:49:0x00c8, B:52:0x00cf, B:53:0x00d4, B:55:0x00d6, B:44:0x00b4, B:45:0x00be, B:25:0x0059), top: B:531:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:542:0x06e5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f3 A[Catch: HttpException -> 0x0963, TryCatch #1 {HttpException -> 0x0963, blocks: (B:3:0x0004, B:6:0x0009, B:8:0x000d, B:9:0x0013, B:11:0x0019, B:13:0x0021, B:15:0x0029, B:57:0x00d8, B:59:0x00e1, B:60:0x00e9, B:62:0x00f3, B:65:0x00f9, B:66:0x00fc, B:69:0x010a, B:70:0x010e, B:71:0x0113, B:72:0x0114, B:207:0x03a7, B:407:0x076f, B:491:0x08cb, B:29:0x0069, B:31:0x006f, B:33:0x0077, B:35:0x007b, B:36:0x0099, B:40:0x00a1, B:42:0x00a7, B:43:0x00ac, B:47:0x00c2, B:49:0x00c8, B:52:0x00cf, B:53:0x00d4, B:55:0x00d6, B:44:0x00b4, B:45:0x00be, B:25:0x0059), top: B:531:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0196 A[Catch: HttpException -> 0x095f, TryCatch #6 {HttpException -> 0x095f, blocks: (B:80:0x012c, B:81:0x0132, B:83:0x0136, B:85:0x013e, B:86:0x0153, B:88:0x0157, B:89:0x015f, B:90:0x0191, B:91:0x0196, B:96:0x01a1, B:98:0x01a5, B:99:0x01aa, B:100:0x01bd, B:102:0x01c1, B:104:0x01c9, B:105:0x01de, B:107:0x01e2, B:108:0x01ea, B:109:0x021c, B:116:0x022e, B:117:0x0238, B:119:0x023c, B:121:0x0240, B:122:0x0254, B:123:0x025a, B:125:0x025e, B:126:0x0272, B:127:0x0278, B:134:0x0289, B:136:0x0290, B:137:0x0295, B:138:0x02a9, B:140:0x02ad, B:142:0x02b1, B:143:0x02c5, B:144:0x02cb, B:146:0x02cf, B:147:0x02e3, B:152:0x02f1, B:154:0x02f5, B:156:0x02fd, B:158:0x0305, B:224:0x03f4, B:229:0x0400, B:231:0x040d, B:233:0x041f, B:234:0x043b, B:242:0x044b, B:256:0x0473, B:258:0x0479, B:260:0x0481, B:262:0x048b, B:263:0x0493, B:271:0x04a8, B:273:0x04b2, B:275:0x04bb, B:282:0x04c9, B:284:0x04d4, B:285:0x04dd, B:266:0x04a0, B:243:0x0450, B:253:0x0467, B:254:0x046c, B:160:0x0309, B:164:0x0318, B:166:0x031e, B:168:0x0328, B:176:0x033d, B:179:0x0343, B:182:0x034f, B:184:0x0354, B:185:0x0361, B:186:0x0362, B:188:0x036f, B:189:0x0373, B:191:0x037f, B:192:0x0382, B:195:0x038b, B:196:0x0393, B:197:0x0394, B:204:0x03a3, B:209:0x03aa, B:211:0x03b8, B:216:0x03cb, B:223:0x03da, B:167:0x0321, B:163:0x030f, B:287:0x04e7, B:291:0x04f4, B:293:0x04f8, B:295:0x052a, B:299:0x0539, B:294:0x050e, B:304:0x055b, B:306:0x055f, B:307:0x058a, B:311:0x059e, B:313:0x05ae, B:315:0x05ba, B:318:0x05d7, B:322:0x05ef, B:327:0x0605, B:332:0x0626, B:335:0x0636, B:336:0x0660, B:342:0x0671, B:343:0x0678, B:344:0x0679, B:346:0x0682, B:347:0x068c, B:349:0x0694, B:353:0x06b4, B:359:0x06c8, B:360:0x06cf, B:361:0x06d0, B:364:0x06db, B:365:0x06e5, B:367:0x06e9, B:369:0x06ed, B:378:0x06fd, B:379:0x0706, B:380:0x070e, B:384:0x0716, B:385:0x0719, B:387:0x071d, B:389:0x0727, B:518:0x0951, B:401:0x0754, B:409:0x077c, B:402:0x075a, B:404:0x0762, B:408:0x0771, B:410:0x0782, B:412:0x078a, B:416:0x0794, B:418:0x07b9, B:424:0x07ce, B:427:0x07d6, B:429:0x07de, B:431:0x07e8, B:432:0x07f0, B:436:0x07f7, B:438:0x0802, B:439:0x0806, B:452:0x0823, B:458:0x0837, B:463:0x084b, B:464:0x0857, B:465:0x086d, B:466:0x086e, B:468:0x0876, B:471:0x0881, B:473:0x0889, B:475:0x0893, B:476:0x089b, B:480:0x08a2, B:482:0x08ad, B:483:0x08b1, B:488:0x08c4, B:493:0x08d0, B:494:0x08d3, B:496:0x08de, B:498:0x08e9, B:502:0x08f0, B:504:0x08f9, B:506:0x08fe, B:507:0x08ff, B:509:0x0921, B:513:0x0928, B:516:0x0931), top: B:539:0x012c, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int parseNext() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpParser.parseNext():int");
    }

    @Override // org.eclipse.jetty.http.Parser
    public void reset() {
        Buffer buffer;
        View view = this._contentView;
        view.setGetIndex(view.putIndex());
        this._state = this._persistent ? -14 : this._endp.isInputShutdown() ? 0 : 7;
        this._contentLength = -3L;
        this._contentPosition = 0L;
        this._length = 0;
        this._responseStatus = 0;
        if (this._eol == 13 && (buffer = this._buffer) != null && buffer.hasContent() && this._buffer.peek() == 10) {
            this._eol = this._buffer.get();
        }
        Buffer buffer2 = this._body;
        if (buffer2 != null && buffer2.hasContent()) {
            Buffer buffer3 = this._header;
            if (buffer3 == null) {
                getHeaderBuffer();
            } else {
                buffer3.setMarkIndex(-1);
                this._header.compact();
            }
            int iSpace = this._header.space();
            if (iSpace > this._body.length()) {
                iSpace = this._body.length();
            }
            Buffer buffer4 = this._body;
            buffer4.peek(buffer4.getIndex(), iSpace);
            Buffer buffer5 = this._body;
            buffer5.skip(this._header.put(buffer5.peek(buffer5.getIndex(), iSpace)));
        }
        Buffer buffer6 = this._header;
        if (buffer6 != null) {
            buffer6.setMarkIndex(-1);
            this._header.compact();
        }
        Buffer buffer7 = this._body;
        if (buffer7 != null) {
            buffer7.setMarkIndex(-1);
        }
        this._buffer = this._header;
        returnBuffers();
    }

    @Override // org.eclipse.jetty.http.Parser
    public void returnBuffers() {
        Buffers buffers;
        Buffers buffers2;
        Buffer buffer = this._body;
        if (buffer != null && !buffer.hasContent() && this._body.markIndex() == -1 && (buffers2 = this._buffers) != null) {
            Buffer buffer2 = this._buffer;
            Buffer buffer3 = this._body;
            if (buffer2 == buffer3) {
                this._buffer = this._header;
            }
            if (buffers2 != null) {
                buffers2.returnBuffer(buffer3);
            }
            this._body = null;
        }
        Buffer buffer4 = this._header;
        if (buffer4 == null || buffer4.hasContent() || this._header.markIndex() != -1 || (buffers = this._buffers) == null) {
            return;
        }
        Buffer buffer5 = this._buffer;
        Buffer buffer6 = this._header;
        if (buffer5 == buffer6) {
            this._buffer = null;
        }
        buffers.returnBuffer(buffer6);
        this._header = null;
    }

    public void setForceContentBuffer(boolean z2) {
        this._forceContentBuffer = z2;
    }

    public void setHeadResponse(boolean z2) {
        this._headResponse = z2;
    }

    @Override // org.eclipse.jetty.http.Parser
    public void setPersistent(boolean z2) {
        this._persistent = z2;
        if (z2) {
            return;
        }
        int i2 = this._state;
        if (i2 == 0 || i2 == -14) {
            this._state = 7;
        }
    }

    public void setState(int i2) {
        this._state = i2;
        this._contentLength = -3L;
    }

    public String toString(Buffer buffer) {
        return "state=" + this._state + " length=" + this._length + " buf=" + buffer.hashCode();
    }

    public String toString() {
        return String.format("%s{s=%d,l=%d,c=%d}", getClass().getSimpleName(), Integer.valueOf(this._state), Integer.valueOf(this._length), Long.valueOf(this._contentLength));
    }

    public HttpParser(Buffers buffers, EndPoint endPoint, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._buffers = buffers;
        this._endp = endPoint;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive();
        this._tok1 = new View.CaseInsensitive();
    }
}
