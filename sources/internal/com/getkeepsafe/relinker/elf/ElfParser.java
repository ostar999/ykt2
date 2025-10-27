package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes8.dex */
public class ElfParser implements Elf, Closeable {
    private final int MAGIC = 1179403647;
    private final FileChannel channel;

    public ElfParser(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.channel = new FileInputStream(file).getChannel();
    }

    private long offsetFromVma(Elf.Header header, long j2, long j3) throws IOException {
        for (long j4 = 0; j4 < j2; j4++) {
            Elf.ProgramHeader programHeader = header.getProgramHeader(j4);
            if (programHeader.type == 1) {
                long j5 = programHeader.vaddr;
                if (j5 <= j3 && j3 <= programHeader.memsz + j5) {
                    return (j3 - j5) + programHeader.offset;
                }
            }
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.channel.close();
    }

    public Elf.Header parseHeader() throws IOException {
        this.channel.position(0L);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        if (readWord(byteBufferAllocate, 0L) != 1179403647) {
            throw new IllegalArgumentException("Invalid ELF Magic!");
        }
        short s2 = readByte(byteBufferAllocate, 4L);
        boolean z2 = readByte(byteBufferAllocate, 5L) == 2;
        if (s2 == 1) {
            return new Elf32Header(z2, this);
        }
        if (s2 == 2) {
            return new Elf64Header(z2, this);
        }
        throw new IllegalStateException("Invalid class type!");
    }

    public List<String> parseNeededDependencies() throws IOException {
        long j2;
        Elf.DynamicStructure dynamicStructure;
        this.channel.position(0L);
        ArrayList arrayList = new ArrayList();
        Elf.Header header = parseHeader();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = header.phnum;
        int i2 = 0;
        if (j3 == WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            j3 = header.getSectionHeader(0).info;
        }
        long j4 = 0;
        while (true) {
            if (j4 >= j3) {
                j2 = 0;
                break;
            }
            Elf.ProgramHeader programHeader = header.getProgramHeader(j4);
            if (programHeader.type == 2) {
                j2 = programHeader.offset;
                break;
            }
            j4++;
        }
        if (j2 == 0) {
            return Collections.unmodifiableList(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        long j5 = 0;
        do {
            dynamicStructure = header.getDynamicStructure(j2, i2);
            long j6 = dynamicStructure.tag;
            if (j6 == 1) {
                arrayList2.add(Long.valueOf(dynamicStructure.val));
            } else if (j6 == 5) {
                j5 = dynamicStructure.val;
            }
            i2++;
        } while (dynamicStructure.tag != 0);
        if (j5 == 0) {
            throw new IllegalStateException("String table offset not found!");
        }
        long jOffsetFromVma = offsetFromVma(header, j3, j5);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList.add(readString(byteBufferAllocate, ((Long) it.next()).longValue() + jOffsetFromVma));
        }
        return arrayList;
    }

    public void read(ByteBuffer byteBuffer, long j2, int i2) throws IOException {
        byteBuffer.position(0);
        byteBuffer.limit(i2);
        long j3 = 0;
        while (j3 < i2) {
            int i3 = this.channel.read(byteBuffer, j2 + j3);
            if (i3 == -1) {
                throw new EOFException();
            }
            j3 += i3;
        }
        byteBuffer.position(0);
    }

    public short readByte(ByteBuffer byteBuffer, long j2) throws IOException {
        read(byteBuffer, j2, 1);
        return (short) (byteBuffer.get() & 255);
    }

    public int readHalf(ByteBuffer byteBuffer, long j2) throws IOException {
        read(byteBuffer, j2, 2);
        return byteBuffer.getShort() & 65535;
    }

    public long readLong(ByteBuffer byteBuffer, long j2) throws IOException {
        read(byteBuffer, j2, 8);
        return byteBuffer.getLong();
    }

    public String readString(ByteBuffer byteBuffer, long j2) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j3 = 1 + j2;
            short s2 = readByte(byteBuffer, j2);
            if (s2 == 0) {
                return sb.toString();
            }
            sb.append((char) s2);
            j2 = j3;
        }
    }

    public long readWord(ByteBuffer byteBuffer, long j2) throws IOException {
        read(byteBuffer, j2, 4);
        return byteBuffer.getInt() & InternalZipConstants.ZIP_64_LIMIT;
    }
}
