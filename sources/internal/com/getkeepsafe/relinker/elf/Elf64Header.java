package internal.com.getkeepsafe.relinker.elf;

import internal.com.getkeepsafe.relinker.elf.Elf;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes8.dex */
public class Elf64Header extends Elf.Header {
    private final ElfParser parser;

    public Elf64Header(boolean z2, ElfParser elfParser) throws IOException {
        this.bigEndian = z2;
        this.parser = elfParser;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(z2 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = elfParser.readHalf(byteBufferAllocate, 16L);
        this.phoff = elfParser.readLong(byteBufferAllocate, 32L);
        this.shoff = elfParser.readLong(byteBufferAllocate, 40L);
        this.phentsize = elfParser.readHalf(byteBufferAllocate, 54L);
        this.phnum = elfParser.readHalf(byteBufferAllocate, 56L);
        this.shentsize = elfParser.readHalf(byteBufferAllocate, 58L);
        this.shnum = elfParser.readHalf(byteBufferAllocate, 60L);
        this.shstrndx = elfParser.readHalf(byteBufferAllocate, 62L);
    }

    @Override // internal.com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(long j2, int i2) throws IOException {
        return new Dynamic64Structure(this.parser, this, j2, i2);
    }

    @Override // internal.com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(long j2) throws IOException {
        return new Program64Header(this.parser, this, j2);
    }

    @Override // internal.com.getkeepsafe.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(int i2) throws IOException {
        return new Section64Header(this.parser, this, i2);
    }
}
