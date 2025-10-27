package com.google.android.exoplayer2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public final class AtomicFile {
    private static final String TAG = "AtomicFile";
    private final File backupName;
    private final File baseName;

    public static final class AtomicFileOutputStream extends OutputStream {
        private boolean closed = false;
        private final FileOutputStream fileOutputStream;

        public AtomicFileOutputStream(File file) throws FileNotFoundException {
            this.fileOutputStream = new FileOutputStream(file);
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            flush();
            try {
                this.fileOutputStream.getFD().sync();
            } catch (IOException e2) {
                Log.w(AtomicFile.TAG, "Failed to sync file descriptor:", e2);
            }
            this.fileOutputStream.close();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.fileOutputStream.flush();
        }

        @Override // java.io.OutputStream
        public void write(int i2) throws IOException {
            this.fileOutputStream.write(i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.fileOutputStream.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) throws IOException {
            this.fileOutputStream.write(bArr, i2, i3);
        }
    }

    public AtomicFile(File file) {
        this.baseName = file;
        this.backupName = new File(String.valueOf(file.getPath()).concat(".bak"));
    }

    private void restoreBackup() {
        if (this.backupName.exists()) {
            this.baseName.delete();
            this.backupName.renameTo(this.baseName);
        }
    }

    public void delete() {
        this.baseName.delete();
        this.backupName.delete();
    }

    public void endWrite(OutputStream outputStream) throws IOException {
        outputStream.close();
        this.backupName.delete();
    }

    public boolean exists() {
        return this.baseName.exists() || this.backupName.exists();
    }

    public InputStream openRead() throws FileNotFoundException {
        restoreBackup();
        return new FileInputStream(this.baseName);
    }

    public OutputStream startWrite() throws IOException {
        if (this.baseName.exists()) {
            if (this.backupName.exists()) {
                this.baseName.delete();
            } else if (!this.baseName.renameTo(this.backupName)) {
                String strValueOf = String.valueOf(this.baseName);
                String strValueOf2 = String.valueOf(this.backupName);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 37 + strValueOf2.length());
                sb.append("Couldn't rename file ");
                sb.append(strValueOf);
                sb.append(" to backup file ");
                sb.append(strValueOf2);
                Log.w(TAG, sb.toString());
            }
        }
        try {
            return new AtomicFileOutputStream(this.baseName);
        } catch (FileNotFoundException e2) {
            File parentFile = this.baseName.getParentFile();
            if (parentFile == null || !parentFile.mkdirs()) {
                String strValueOf3 = String.valueOf(this.baseName);
                StringBuilder sb2 = new StringBuilder(strValueOf3.length() + 16);
                sb2.append("Couldn't create ");
                sb2.append(strValueOf3);
                throw new IOException(sb2.toString(), e2);
            }
            try {
                return new AtomicFileOutputStream(this.baseName);
            } catch (FileNotFoundException e3) {
                String strValueOf4 = String.valueOf(this.baseName);
                StringBuilder sb3 = new StringBuilder(strValueOf4.length() + 16);
                sb3.append("Couldn't create ");
                sb3.append(strValueOf4);
                throw new IOException(sb3.toString(), e3);
            }
        }
    }
}
