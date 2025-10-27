package org.apache.http.impl.conn;

import cn.hutool.core.text.StrPool;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class Wire {
    private final Log log;

    public Wire(Log log) {
        this.log = log;
    }

    private void wire(String str, InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i2 = inputStream.read();
            if (i2 == -1) {
                break;
            }
            if (i2 == 13) {
                sb.append("[\\r]");
            } else if (i2 == 10) {
                sb.append("[\\n]\"");
                sb.insert(0, "\"");
                sb.insert(0, str);
                this.log.debug(sb.toString());
                sb.setLength(0);
            } else if (i2 < 32 || i2 > 127) {
                sb.append("[0x");
                sb.append(Integer.toHexString(i2));
                sb.append(StrPool.BRACKET_END);
            } else {
                sb.append((char) i2);
            }
        }
        if (sb.length() > 0) {
            sb.append('\"');
            sb.insert(0, '\"');
            sb.insert(0, str);
            this.log.debug(sb.toString());
        }
    }

    public boolean enabled() {
        return this.log.isDebugEnabled();
    }

    public void input(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input may not be null");
        }
        wire("<< ", inputStream);
    }

    public void output(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Output may not be null");
        }
        wire(">> ", inputStream);
    }

    public void input(byte[] bArr, int i2, int i3) throws IOException {
        if (bArr != null) {
            wire("<< ", new ByteArrayInputStream(bArr, i2, i3));
            return;
        }
        throw new IllegalArgumentException("Input may not be null");
    }

    public void output(byte[] bArr, int i2, int i3) throws IOException {
        if (bArr != null) {
            wire(">> ", new ByteArrayInputStream(bArr, i2, i3));
            return;
        }
        throw new IllegalArgumentException("Output may not be null");
    }

    public void input(byte[] bArr) throws IOException {
        if (bArr != null) {
            wire("<< ", new ByteArrayInputStream(bArr));
            return;
        }
        throw new IllegalArgumentException("Input may not be null");
    }

    public void output(byte[] bArr) throws IOException {
        if (bArr != null) {
            wire(">> ", new ByteArrayInputStream(bArr));
            return;
        }
        throw new IllegalArgumentException("Output may not be null");
    }

    public void input(int i2) throws IOException {
        input(new byte[]{(byte) i2});
    }

    public void output(int i2) throws IOException {
        output(new byte[]{(byte) i2});
    }

    @Deprecated
    public void input(String str) throws IOException {
        if (str != null) {
            input(str.getBytes());
            return;
        }
        throw new IllegalArgumentException("Input may not be null");
    }

    @Deprecated
    public void output(String str) throws IOException {
        if (str != null) {
            output(str.getBytes());
            return;
        }
        throw new IllegalArgumentException("Output may not be null");
    }
}
