package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes8.dex */
public class Dynamic64Structure extends Elf.DynamicStructure {
    public Dynamic64Structure(ElfParser elfParser, Elf.Header header, long j2, int i2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = j2 + (i2 * 16);
        this.tag = elfParser.readLong(byteBufferAllocate, j3);
        this.val = elfParser.readLong(byteBufferAllocate, j3 + 8);
    }
}
