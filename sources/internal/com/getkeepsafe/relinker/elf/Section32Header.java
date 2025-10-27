package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes8.dex */
public class Section32Header extends Elf.SectionHeader {
    public Section32Header(ElfParser elfParser, Elf.Header header, int i2) throws IOException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.info = elfParser.readWord(byteBufferAllocate, header.shoff + (i2 * header.shentsize) + 28);
    }
}
