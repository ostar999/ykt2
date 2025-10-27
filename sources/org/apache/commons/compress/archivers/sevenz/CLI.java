package org.apache.commons.compress.archivers.sevenz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class CLI {
    private static final byte[] BUF = new byte[8192];

    public enum Mode {
        LIST("Analysing") { // from class: org.apache.commons.compress.archivers.sevenz.CLI.Mode.1
            private String getContentMethods(SevenZArchiveEntry sevenZArchiveEntry) {
                StringBuilder sb = new StringBuilder();
                boolean z2 = true;
                for (SevenZMethodConfiguration sevenZMethodConfiguration : sevenZArchiveEntry.getContentMethods()) {
                    if (!z2) {
                        sb.append(", ");
                    }
                    sb.append(sevenZMethodConfiguration.getMethod());
                    if (sevenZMethodConfiguration.getOptions() != null) {
                        sb.append("(");
                        sb.append(sevenZMethodConfiguration.getOptions());
                        sb.append(")");
                    }
                    z2 = false;
                }
                return sb.toString();
            }

            @Override // org.apache.commons.compress.archivers.sevenz.CLI.Mode
            public void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) {
                System.out.print(sevenZArchiveEntry.getName());
                if (sevenZArchiveEntry.isDirectory()) {
                    System.out.print(" dir");
                } else {
                    System.out.print(" " + sevenZArchiveEntry.getCompressedSize() + "/" + sevenZArchiveEntry.getSize());
                }
                if (sevenZArchiveEntry.getHasLastModifiedDate()) {
                    System.out.print(" " + sevenZArchiveEntry.getLastModifiedDate());
                } else {
                    System.out.print(" no last modified date");
                }
                if (sevenZArchiveEntry.isDirectory()) {
                    System.out.println("");
                    return;
                }
                System.out.println(" " + getContentMethods(sevenZArchiveEntry));
            }
        },
        EXTRACT("Extracting") { // from class: org.apache.commons.compress.archivers.sevenz.CLI.Mode.2
            @Override // org.apache.commons.compress.archivers.sevenz.CLI.Mode
            public void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
                File file = new File(sevenZArchiveEntry.getName());
                if (sevenZArchiveEntry.isDirectory()) {
                    if (!file.isDirectory() && !file.mkdirs()) {
                        throw new IOException("Cannot create directory " + file);
                    }
                    System.out.println("created directory " + file);
                    return;
                }
                System.out.println("extracting to " + file);
                File parentFile = file.getParentFile();
                if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                    throw new IOException("Cannot create " + parentFile);
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    long size = sevenZArchiveEntry.getSize();
                    long j2 = 0;
                    while (j2 < size) {
                        int i2 = sevenZFile.read(CLI.BUF, 0, (int) Math.min(size - j2, CLI.BUF.length));
                        if (i2 < 1) {
                            throw new IOException("reached end of entry " + sevenZArchiveEntry.getName() + " after " + j2 + " bytes, expected " + size);
                        }
                        j2 += i2;
                        fileOutputStream.write(CLI.BUF, 0, i2);
                    }
                } finally {
                    fileOutputStream.close();
                }
            }
        };

        private final String message;

        public String getMessage() {
            return this.message;
        }

        public abstract void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) throws IOException;

        Mode(String str) {
            this.message = str;
        }
    }

    private static Mode grabMode(String[] strArr) {
        return strArr.length < 2 ? Mode.LIST : (Mode) Enum.valueOf(Mode.class, strArr[1].toUpperCase());
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length == 0) {
            usage();
            return;
        }
        Mode modeGrabMode = grabMode(strArr);
        System.out.println(modeGrabMode.getMessage() + " " + strArr[0]);
        File file = new File(strArr[0]);
        if (!file.isFile()) {
            System.err.println(file + " doesn't exist or is a directory");
        }
        SevenZFile sevenZFile = new SevenZFile(file);
        while (true) {
            try {
                SevenZArchiveEntry nextEntry = sevenZFile.getNextEntry();
                if (nextEntry == null) {
                    return;
                } else {
                    modeGrabMode.takeAction(sevenZFile, nextEntry);
                }
            } finally {
                sevenZFile.close();
            }
        }
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [list|extract]");
    }
}
