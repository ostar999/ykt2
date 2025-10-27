package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes8.dex */
public class Program32Header extends Elf.ProgramHeader {
    public Program32Header(ElfParser elfParser, Elf.Header header, long j2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = header.phoff + (j2 * header.phentsize);
        this.type = elfParser.readWord(byteBufferAllocate, j3);
        this.offset = elfParser.readWord(byteBufferAllocate, 4 + j3);
        this.vaddr = elfParser.readWord(byteBufferAllocate, 8 + j3);
        this.memsz = elfParser.readWord(byteBufferAllocate, j3 + 20);
    }
}
