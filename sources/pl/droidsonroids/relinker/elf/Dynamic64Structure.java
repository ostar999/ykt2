package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes9.dex */
public class Dynamic64Structure extends Elf.DynamicStructure {
    public Dynamic64Structure(ElfParser elfParser, Elf.Header header, long j2, int i2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j3 = j2 + (i2 * 16);
        this.tag = elfParser.readLong(byteBufferAllocate, j3);
        this.val = elfParser.readLong(byteBufferAllocate, j3 + 8);
    }
}
