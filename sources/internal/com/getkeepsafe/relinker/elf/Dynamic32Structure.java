package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes8.dex */
public class Dynamic32Structure extends Elf.DynamicStructure {
    public Dynamic32Structure(ElfParser elfParser, Elf.Header header, long j2, int i2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = j2 + (i2 * 8);
        this.tag = elfParser.readWord(byteBufferAllocate, j3);
        this.val = elfParser.readWord(byteBufferAllocate, j3 + 4);
    }
}
