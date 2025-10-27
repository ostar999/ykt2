package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes9.dex */
public class Program64Header extends Elf.ProgramHeader {
    public Program64Header(ElfParser elfParser, Elf.Header header, long j2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = header.phoff + (j2 * header.phentsize);
        this.type = elfParser.readWord(byteBufferAllocate, j3);
        this.offset = elfParser.readLong(byteBufferAllocate, 8 + j3);
        this.vaddr = elfParser.readLong(byteBufferAllocate, 16 + j3);
        this.memsz = elfParser.readLong(byteBufferAllocate, j3 + 40);
    }
}
