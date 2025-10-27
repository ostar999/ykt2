package cn.hutool.core.img.gif;

import cn.hutool.core.io.IoUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GifDecoder {
    protected static final int MAX_STACK_SIZE = 4096;
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_OPEN_ERROR = 2;
    protected int[] act;
    protected int bgColor;
    protected int bgIndex;
    protected int frameCount;
    protected ArrayList<GifFrame> frames;
    protected int[] gct;
    protected boolean gctFlag;
    protected int gctSize;
    protected int height;
    protected int ih;
    protected BufferedImage image;
    protected BufferedInputStream in;
    protected boolean interlace;
    protected int iw;
    protected int ix;
    protected int iy;
    protected int lastBgColor;
    protected BufferedImage lastImage;
    protected Rectangle lastRect;
    protected int[] lct;
    protected boolean lctFlag;
    protected int lctSize;
    protected int pixelAspect;
    protected byte[] pixelStack;
    protected byte[] pixels;
    protected short[] prefix;
    protected int status;
    protected byte[] suffix;
    protected int transIndex;
    protected int width;
    protected int loopCount = 1;
    protected byte[] block = new byte[256];
    protected int blockSize = 0;
    protected int dispose = 0;
    protected int lastDispose = 0;
    protected boolean transparency = false;
    protected int delay = 0;

    public static class GifFrame {
        public int delay;
        public BufferedImage image;

        public GifFrame(BufferedImage bufferedImage, int i2) {
            this.image = bufferedImage;
            this.delay = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v26, types: [short] */
    /* JADX WARN: Type inference failed for: r2v28 */
    public void decodeImageData() throws IOException {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        short s2;
        int i7 = this.iw * this.ih;
        byte[] bArr = this.pixels;
        if (bArr == null || bArr.length < i7) {
            this.pixels = new byte[i7];
        }
        if (this.prefix == null) {
            this.prefix = new short[4096];
        }
        if (this.suffix == null) {
            this.suffix = new byte[4096];
        }
        if (this.pixelStack == null) {
            this.pixelStack = new byte[4097];
        }
        int i8 = read();
        int i9 = 1 << i8;
        int i10 = i9 + 1;
        int i11 = i9 + 2;
        int i12 = i8 + 1;
        int i13 = (1 << i12) - 1;
        for (int i14 = 0; i14 < i9; i14++) {
            this.prefix[i14] = 0;
            this.suffix[i14] = (byte) i14;
        }
        int i15 = i12;
        int i16 = i13;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int block = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        int i24 = -1;
        int i25 = i11;
        while (i17 < i7) {
            if (i18 != 0) {
                i2 = i12;
                i3 = i10;
                int i26 = i23;
                i4 = i9;
                i5 = i26;
            } else if (i19 < i15) {
                if (block == 0) {
                    block = readBlock();
                    if (block <= 0) {
                        break;
                    } else {
                        i21 = 0;
                    }
                }
                i20 += (this.block[i21] & 255) << i19;
                i19 += 8;
                i21++;
                block--;
            } else {
                int i27 = i20 & i16;
                i20 >>= i15;
                i19 -= i15;
                if (i27 > i25 || i27 == i10) {
                    break;
                }
                if (i27 == i9) {
                    i15 = i12;
                    i25 = i11;
                    i16 = i13;
                    i24 = -1;
                } else if (i24 == -1) {
                    this.pixelStack[i18] = this.suffix[i27];
                    i24 = i27;
                    i23 = i24;
                    i18++;
                    i12 = i12;
                } else {
                    i2 = i12;
                    if (i27 == i25) {
                        i6 = i27;
                        this.pixelStack[i18] = (byte) i23;
                        s2 = i24;
                        i18++;
                    } else {
                        i6 = i27;
                        s2 = i6;
                    }
                    while (s2 > i9) {
                        this.pixelStack[i18] = this.suffix[s2];
                        s2 = this.prefix[s2];
                        i18++;
                        i9 = i9;
                    }
                    i4 = i9;
                    byte[] bArr2 = this.suffix;
                    i5 = bArr2[s2] & 255;
                    if (i25 >= 4096) {
                        this.pixelStack[i18] = (byte) i5;
                        i18++;
                        i9 = i4;
                        i23 = i5;
                        i12 = i2;
                    } else {
                        int i28 = i18 + 1;
                        i3 = i10;
                        byte b3 = (byte) i5;
                        this.pixelStack[i18] = b3;
                        this.prefix[i25] = (short) i24;
                        bArr2[i25] = b3;
                        i25++;
                        if ((i25 & i16) == 0 && i25 < 4096) {
                            i15++;
                            i16 += i25;
                        }
                        i18 = i28;
                        i24 = i6;
                    }
                }
            }
            i18--;
            this.pixels[i22] = this.pixelStack[i18];
            i17++;
            i22++;
            i9 = i4;
            i10 = i3;
            i23 = i5;
            i12 = i2;
        }
        for (int i29 = i22; i29 < i7; i29++) {
            this.pixels[i29] = 0;
        }
    }

    public boolean err() {
        return this.status != 0;
    }

    public int getDelay(int i2) {
        this.delay = -1;
        if (i2 >= 0 && i2 < this.frameCount) {
            this.delay = this.frames.get(i2).delay;
        }
        return this.delay;
    }

    public BufferedImage getFrame(int i2) {
        if (i2 < 0 || i2 >= this.frameCount) {
            return null;
        }
        return this.frames.get(i2).image;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public Dimension getFrameSize() {
        return new Dimension(this.width, this.height);
    }

    public BufferedImage getImage() {
        return getFrame(0);
    }

    public int getLoopCount() {
        return this.loopCount;
    }

    public void init() {
        this.status = 0;
        this.frameCount = 0;
        this.frames = new ArrayList<>();
        this.gct = null;
        this.lct = null;
    }

    public int read(BufferedInputStream bufferedInputStream) throws IOException {
        init();
        if (bufferedInputStream != null) {
            this.in = bufferedInputStream;
            readHeader();
            if (!err()) {
                readContents();
                if (this.frameCount < 0) {
                    this.status = 1;
                }
            }
        } else {
            this.status = 2;
        }
        IoUtil.close((Closeable) bufferedInputStream);
        return this.status;
    }

    public int readBlock() throws IOException {
        int i2 = read();
        this.blockSize = i2;
        int i3 = 0;
        if (i2 > 0) {
            while (true) {
                try {
                    int i4 = this.blockSize;
                    if (i3 >= i4) {
                        break;
                    }
                    int i5 = this.in.read(this.block, i3, i4 - i3);
                    if (i5 == -1) {
                        break;
                    }
                    i3 += i5;
                } catch (IOException unused) {
                }
            }
            if (i3 < this.blockSize) {
                this.status = 1;
            }
        }
        return i3;
    }

    public int[] readColorTable(int i2) throws IOException {
        int i3;
        int i4 = i2 * 3;
        byte[] bArr = new byte[i4];
        try {
            i3 = this.in.read(bArr);
        } catch (IOException unused) {
            i3 = 0;
        }
        if (i3 < i4) {
            this.status = 1;
            return null;
        }
        int[] iArr = new int[256];
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            int i7 = i5 + 1;
            int i8 = i7 + 1;
            iArr[i6] = ((bArr[i5] & 255) << 16) | (-16777216) | ((bArr[i7] & 255) << 8) | (bArr[i8] & 255);
            i5 = i8 + 1;
        }
        return iArr;
    }

    public void readContents() throws IOException {
        boolean z2 = false;
        while (!z2 && !err()) {
            int i2 = read();
            if (i2 != 0) {
                if (i2 == 33) {
                    int i3 = read();
                    if (i3 == 249) {
                        readGraphicControlExt();
                    } else if (i3 != 255) {
                        skip();
                    } else {
                        readBlock();
                        StringBuilder sb = new StringBuilder();
                        for (int i4 = 0; i4 < 11; i4++) {
                            sb.append((char) this.block[i4]);
                        }
                        if ("NETSCAPE2.0".equals(sb.toString())) {
                            readNetscapeExt();
                        } else {
                            skip();
                        }
                    }
                } else if (i2 == 44) {
                    readImage();
                } else if (i2 != 59) {
                    this.status = 1;
                } else {
                    z2 = true;
                }
            }
        }
    }

    public void readGraphicControlExt() {
        read();
        int i2 = read();
        int i3 = (i2 & 28) >> 2;
        this.dispose = i3;
        if (i3 == 0) {
            this.dispose = 1;
        }
        this.transparency = (i2 & 1) != 0;
        this.delay = readShort() * 10;
        this.transIndex = read();
        read();
    }

    public void readHeader() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 6; i2++) {
            sb.append((char) read());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.status = 1;
            return;
        }
        readLSD();
        if (!this.gctFlag || err()) {
            return;
        }
        int[] colorTable = readColorTable(this.gctSize);
        this.gct = colorTable;
        this.bgColor = colorTable[this.bgIndex];
    }

    public void readImage() throws IOException {
        this.ix = readShort();
        this.iy = readShort();
        this.iw = readShort();
        this.ih = readShort();
        int i2 = read();
        int i3 = 0;
        boolean z2 = (i2 & 128) != 0;
        this.lctFlag = z2;
        this.interlace = (i2 & 64) != 0;
        int i4 = 2 << (i2 & 7);
        this.lctSize = i4;
        if (z2) {
            int[] colorTable = readColorTable(i4);
            this.lct = colorTable;
            this.act = colorTable;
        } else {
            this.act = this.gct;
            if (this.bgIndex == this.transIndex) {
                this.bgColor = 0;
            }
        }
        if (this.transparency) {
            int[] iArr = this.act;
            int i5 = this.transIndex;
            int i6 = iArr[i5];
            iArr[i5] = 0;
            i3 = i6;
        }
        if (this.act == null) {
            this.status = 1;
        }
        if (err()) {
            return;
        }
        decodeImageData();
        skip();
        if (err()) {
            return;
        }
        this.frameCount++;
        this.image = new BufferedImage(this.width, this.height, 3);
        setPixels();
        this.frames.add(new GifFrame(this.image, this.delay));
        if (this.transparency) {
            this.act[this.transIndex] = i3;
        }
        resetFrame();
    }

    public void readLSD() {
        this.width = readShort();
        this.height = readShort();
        int i2 = read();
        this.gctFlag = (i2 & 128) != 0;
        this.gctSize = 2 << (i2 & 7);
        this.bgIndex = read();
        this.pixelAspect = read();
    }

    public void readNetscapeExt() throws IOException {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.loopCount = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    public int readShort() {
        return read() | (read() << 8);
    }

    public void resetFrame() {
        this.lastDispose = this.dispose;
        this.lastRect = new Rectangle(this.ix, this.iy, this.iw, this.ih);
        this.lastImage = this.image;
        this.lastBgColor = this.bgColor;
        this.lct = null;
    }

    public void setPixels() {
        int i2;
        int[] data = this.image.getRaster().getDataBuffer().getData();
        int i3 = this.lastDispose;
        int i4 = 0;
        if (i3 > 0) {
            if (i3 == 3) {
                int i5 = this.frameCount - 2;
                if (i5 > 0) {
                    this.lastImage = getFrame(i5 - 1);
                } else {
                    this.lastImage = null;
                }
            }
            BufferedImage bufferedImage = this.lastImage;
            if (bufferedImage != null) {
                System.arraycopy(bufferedImage.getRaster().getDataBuffer().getData(), 0, data, 0, this.width * this.height);
                if (this.lastDispose == 2) {
                    Graphics2D graphics2DCreateGraphics = this.image.createGraphics();
                    graphics2DCreateGraphics.setColor(this.transparency ? new Color(0, 0, 0, 0) : new Color(this.lastBgColor));
                    graphics2DCreateGraphics.setComposite(AlphaComposite.Src);
                    graphics2DCreateGraphics.fill(this.lastRect);
                    graphics2DCreateGraphics.dispose();
                }
            }
        }
        int i6 = 8;
        int i7 = 1;
        int i8 = 0;
        while (true) {
            int i9 = this.ih;
            if (i4 >= i9) {
                return;
            }
            if (this.interlace) {
                if (i8 >= i9) {
                    i7++;
                    if (i7 == 2) {
                        i8 = 4;
                    } else if (i7 == 3) {
                        i8 = 2;
                        i6 = 4;
                    } else if (i7 == 4) {
                        i6 = 2;
                        i8 = 1;
                    }
                }
                i2 = i8 + i6;
            } else {
                i2 = i8;
                i8 = i4;
            }
            int i10 = i8 + this.iy;
            if (i10 < this.height) {
                int i11 = this.width;
                int i12 = i10 * i11;
                int i13 = this.ix + i12;
                int i14 = this.iw;
                int i15 = i13 + i14;
                if (i12 + i11 < i15) {
                    i15 = i12 + i11;
                }
                int i16 = i14 * i4;
                while (i13 < i15) {
                    int i17 = i16 + 1;
                    int i18 = this.act[this.pixels[i16] & 255];
                    if (i18 != 0) {
                        data[i13] = i18;
                    }
                    i13++;
                    i16 = i17;
                }
            }
            i4++;
            i8 = i2;
        }
    }

    public void skip() throws IOException {
        do {
            readBlock();
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    public int read(InputStream inputStream) throws IOException {
        init();
        if (inputStream != null) {
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            this.in = (BufferedInputStream) inputStream;
            readHeader();
            if (!err()) {
                readContents();
                if (this.frameCount < 0) {
                    this.status = 1;
                }
            }
        } else {
            this.status = 2;
        }
        IoUtil.close((Closeable) inputStream);
        return this.status;
    }

    public int read(String str) {
        this.status = 0;
        try {
            String lowerCase = str.trim().toLowerCase();
            if (!lowerCase.contains("file:") && lowerCase.indexOf(":/") <= 0) {
                this.in = new BufferedInputStream(Files.newInputStream(Paths.get(lowerCase, new String[0]), new OpenOption[0]));
            } else {
                this.in = new BufferedInputStream(new URL(lowerCase).openStream());
            }
            this.status = read(this.in);
        } catch (IOException unused) {
            this.status = 2;
        }
        return this.status;
    }

    public int read() {
        try {
            return this.in.read();
        } catch (IOException unused) {
            this.status = 1;
            return 0;
        }
    }
}
