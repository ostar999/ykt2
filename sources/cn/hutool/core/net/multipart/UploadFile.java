package cn.hutool.core.net.multipart;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;

/* loaded from: classes.dex */
public class UploadFile {
    private static final String TMP_FILE_PREFIX = "hutool-";
    private static final String TMP_FILE_SUFFIX = ".upload.tmp";
    private byte[] data;
    private final UploadFileHeader header;
    private final UploadSetting setting;
    private long size = -1;
    private File tempFile;

    public UploadFile(UploadFileHeader uploadFileHeader, UploadSetting uploadSetting) {
        this.header = uploadFileHeader;
        this.setting = uploadSetting;
    }

    private void assertValid() throws IOException {
        if (!isUploaded()) {
            throw new IOException(CharSequenceUtil.format("File [{}] upload fail", getFileName()));
        }
    }

    private boolean isAllowedExtension() {
        UploadSetting uploadSetting = this.setting;
        String[] strArr = uploadSetting.fileExts;
        boolean z2 = uploadSetting.isAllowFileExts;
        if (strArr == null || strArr.length == 0) {
            return z2;
        }
        String strExtName = FileUtil.extName(getFileName());
        for (String str : this.setting.fileExts) {
            if (strExtName.equalsIgnoreCase(str)) {
                return z2;
            }
        }
        return !z2;
    }

    public void delete() {
        File file = this.tempFile;
        if (file != null) {
            file.delete();
        }
        if (this.data != null) {
            this.data = null;
        }
    }

    public byte[] getFileContent() throws IOException {
        assertValid();
        byte[] bArr = this.data;
        if (bArr != null) {
            return bArr;
        }
        File file = this.tempFile;
        if (file != null) {
            return FileUtil.readBytes(file);
        }
        return null;
    }

    public InputStream getFileInputStream() throws IOException {
        assertValid();
        byte[] bArr = this.data;
        if (bArr != null) {
            return IoUtil.toBuffered(IoUtil.toStream(bArr));
        }
        File file = this.tempFile;
        if (file != null) {
            return IoUtil.toBuffered(IoUtil.toStream(file));
        }
        return null;
    }

    public String getFileName() {
        UploadFileHeader uploadFileHeader = this.header;
        if (uploadFileHeader == null) {
            return null;
        }
        return uploadFileHeader.getFileName();
    }

    public UploadFileHeader getHeader() {
        return this.header;
    }

    public boolean isInMemory() {
        return this.data != null;
    }

    public boolean isUploaded() {
        return this.size > 0;
    }

    public boolean processStream(MultipartRequestInputStream multipartRequestInputStream) throws IOException, IORuntimeException {
        if (!isAllowedExtension()) {
            this.size = multipartRequestInputStream.skipToBoundary();
            return false;
        }
        this.size = 0L;
        int i2 = this.setting.memoryThreshold;
        if (i2 > 0) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
            long j2 = i2;
            long jCopy = multipartRequestInputStream.copy(byteArrayOutputStream, j2);
            this.data = byteArrayOutputStream.toByteArray();
            if (jCopy <= j2) {
                this.size = r0.length;
                return true;
            }
        }
        File fileCreateTempFile = FileUtil.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFFIX, FileUtil.touch(this.setting.tmpUploadPath), false);
        this.tempFile = fileCreateTempFile;
        OutputStream outputStream = FileUtil.getOutputStream(fileCreateTempFile);
        byte[] bArr = this.data;
        if (bArr != null) {
            this.size = bArr.length;
            outputStream.write(bArr);
            this.data = null;
        }
        long j3 = this.setting.maxFileSize;
        try {
            if (j3 == -1) {
                this.size += multipartRequestInputStream.copy(outputStream);
                return true;
            }
            long j4 = this.size;
            long jCopy2 = j4 + multipartRequestInputStream.copy(outputStream, (j3 - j4) + 1);
            this.size = jCopy2;
            if (jCopy2 <= j3) {
                return true;
            }
            this.tempFile.delete();
            this.tempFile = null;
            multipartRequestInputStream.skipToBoundary();
            return false;
        } finally {
            IoUtil.close((Closeable) outputStream);
        }
    }

    public long size() {
        return this.size;
    }

    public File write(String str) throws IOException {
        if (this.data == null && this.tempFile == null) {
            return null;
        }
        return write(FileUtil.file(str));
    }

    public File write(File file) throws IOException, IORuntimeException, IllegalArgumentException {
        assertValid();
        if (file.isDirectory()) {
            file = new File(file, this.header.getFileName());
        }
        byte[] bArr = this.data;
        if (bArr != null) {
            FileUtil.writeBytes(bArr, file);
            this.data = null;
        } else {
            File file2 = this.tempFile;
            if (file2 != null) {
                if (file2.exists()) {
                    FileUtil.move(this.tempFile, file, true);
                } else {
                    throw new NoSuchFileException("Temp file: [" + this.tempFile.getAbsolutePath() + "] not exist!");
                }
            } else {
                throw new NullPointerException("Temp file is null !");
            }
        }
        return file;
    }
}
