package org.apache.commons.compress.archivers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/* loaded from: classes9.dex */
public final class Lister {
    private static final ArchiveStreamFactory factory = new ArchiveStreamFactory();

    public static void main(String[] strArr) throws Exception {
        if (strArr.length == 0) {
            usage();
            return;
        }
        System.out.println("Analysing " + strArr[0]);
        File file = new File(strArr[0]);
        if (!file.isFile()) {
            System.err.println(file + " doesn't exist or is a directory");
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        ArchiveInputStream archiveInputStreamCreateArchiveInputStream = strArr.length > 1 ? factory.createArchiveInputStream(strArr[1], bufferedInputStream) : factory.createArchiveInputStream(bufferedInputStream);
        System.out.println("Created " + archiveInputStreamCreateArchiveInputStream.toString());
        while (true) {
            ArchiveEntry nextEntry = archiveInputStreamCreateArchiveInputStream.getNextEntry();
            if (nextEntry == null) {
                archiveInputStreamCreateArchiveInputStream.close();
                bufferedInputStream.close();
                return;
            }
            System.out.println(nextEntry.getName());
        }
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [archive-type]");
    }
}
