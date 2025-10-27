package org.eclipse.jetty.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import k.a;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class UncheckedPrintWriter extends PrintWriter {
    private static final Logger LOG = Log.getLogger((Class<?>) UncheckedPrintWriter.class);
    private boolean _autoFlush;
    private IOException _ioException;
    private boolean _isClosed;
    private String _lineSeparator;

    public UncheckedPrintWriter(Writer writer) {
        this(writer, false);
    }

    private void isOpen() throws IOException {
        if (this._ioException != null) {
            throw new RuntimeIOException(this._ioException);
        }
        if (this._isClosed) {
            throw new IOException("Stream closed");
        }
    }

    private void newLine() {
        try {
            synchronized (((PrintWriter) this).lock) {
                isOpen();
                ((PrintWriter) this).out.write(this._lineSeparator);
                if (this._autoFlush) {
                    ((PrintWriter) this).out.flush();
                }
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e2) {
            setError(e2);
        }
    }

    private void setError(Throwable th) {
        super.setError();
        if (th instanceof IOException) {
            this._ioException = (IOException) th;
        } else {
            IOException iOException = new IOException(String.valueOf(th));
            this._ioException = iOException;
            iOException.initCause(th);
        }
        LOG.debug(th);
    }

    @Override // java.io.PrintWriter
    public boolean checkError() {
        return this._ioException != null || super.checkError();
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            synchronized (((PrintWriter) this).lock) {
                ((PrintWriter) this).out.close();
                this._isClosed = true;
            }
        } catch (IOException e2) {
            setError(e2);
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Flushable
    public void flush() {
        try {
            synchronized (((PrintWriter) this).lock) {
                isOpen();
                ((PrintWriter) this).out.flush();
            }
        } catch (IOException e2) {
            setError(e2);
        }
    }

    @Override // java.io.PrintWriter
    public void print(boolean z2) {
        write(z2 ? a.f27523u : a.f27524v);
    }

    @Override // java.io.PrintWriter
    public void println() {
        newLine();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i2) {
        try {
            synchronized (((PrintWriter) this).lock) {
                isOpen();
                ((PrintWriter) this).out.write(i2);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e2) {
            setError(e2);
        }
    }

    public UncheckedPrintWriter(Writer writer, boolean z2) {
        super(writer, z2);
        this._isClosed = false;
        this._autoFlush = z2;
        this._lineSeparator = System.getProperty("line.separator");
    }

    @Override // java.io.PrintWriter
    public void print(char c3) {
        write(c3);
    }

    @Override // java.io.PrintWriter
    public void println(boolean z2) {
        synchronized (((PrintWriter) this).lock) {
            print(z2);
            println();
        }
    }

    @Override // java.io.PrintWriter
    public void print(int i2) {
        write(String.valueOf(i2));
    }

    @Override // java.io.PrintWriter
    public void print(long j2) {
        write(String.valueOf(j2));
    }

    @Override // java.io.PrintWriter
    public void print(float f2) {
        write(String.valueOf(f2));
    }

    public UncheckedPrintWriter(OutputStream outputStream) {
        this(outputStream, false);
    }

    @Override // java.io.PrintWriter
    public void print(double d3) {
        write(String.valueOf(d3));
    }

    @Override // java.io.PrintWriter
    public void println(char c3) {
        synchronized (((PrintWriter) this).lock) {
            print(c3);
            println();
        }
    }

    public UncheckedPrintWriter(OutputStream outputStream, boolean z2) {
        this(new BufferedWriter(new OutputStreamWriter(outputStream)), z2);
    }

    @Override // java.io.PrintWriter
    public void print(char[] cArr) {
        write(cArr);
    }

    @Override // java.io.PrintWriter
    public void setError() {
        setError(new IOException());
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i2, int i3) {
        try {
            synchronized (((PrintWriter) this).lock) {
                isOpen();
                ((PrintWriter) this).out.write(cArr, i2, i3);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e2) {
            setError(e2);
        }
    }

    @Override // java.io.PrintWriter
    public void print(String str) {
        if (str == null) {
            str = "null";
        }
        write(str);
    }

    @Override // java.io.PrintWriter
    public void print(Object obj) {
        write(String.valueOf(obj));
    }

    @Override // java.io.PrintWriter
    public void println(int i2) {
        synchronized (((PrintWriter) this).lock) {
            print(i2);
            println();
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr) {
        write(cArr, 0, cArr.length);
    }

    @Override // java.io.PrintWriter
    public void println(long j2) {
        synchronized (((PrintWriter) this).lock) {
            print(j2);
            println();
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i2, int i3) {
        try {
            synchronized (((PrintWriter) this).lock) {
                isOpen();
                ((PrintWriter) this).out.write(str, i2, i3);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e2) {
            setError(e2);
        }
    }

    @Override // java.io.PrintWriter
    public void println(float f2) {
        synchronized (((PrintWriter) this).lock) {
            print(f2);
            println();
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str) {
        write(str, 0, str.length());
    }

    @Override // java.io.PrintWriter
    public void println(double d3) {
        synchronized (((PrintWriter) this).lock) {
            print(d3);
            println();
        }
    }

    @Override // java.io.PrintWriter
    public void println(char[] cArr) {
        synchronized (((PrintWriter) this).lock) {
            print(cArr);
            println();
        }
    }

    @Override // java.io.PrintWriter
    public void println(String str) {
        synchronized (((PrintWriter) this).lock) {
            print(str);
            println();
        }
    }

    @Override // java.io.PrintWriter
    public void println(Object obj) {
        synchronized (((PrintWriter) this).lock) {
            print(obj);
            println();
        }
    }
}
