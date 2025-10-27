package cn.hutool.core.img.gif;

import com.yikaobang.yixue.R2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

/* loaded from: classes.dex */
public class AnimatedGifEncoder {
    protected int colorDepth;
    protected byte[] colorTab;
    protected int height;
    protected BufferedImage image;
    protected byte[] indexedPixels;
    protected OutputStream out;
    protected byte[] pixels;
    protected int transIndex;
    protected int width;
    protected Color transparent = null;
    protected boolean transparentExactMatch = false;
    protected Color background = null;
    protected int repeat = -1;
    protected int delay = 0;
    protected boolean started = false;
    protected boolean[] usedEntry = new boolean[256];
    protected int palSize = 7;
    protected int dispose = -1;
    protected boolean closeStream = false;
    protected boolean firstFrame = true;
    protected boolean sizeSet = false;
    protected int sample = 10;

    public boolean addFrame(BufferedImage bufferedImage) {
        if (bufferedImage == null || !this.started) {
            return false;
        }
        try {
            if (!this.sizeSet) {
                setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            }
            this.image = bufferedImage;
            getImagePixels();
            analyzePixels();
            if (this.firstFrame) {
                writeLSD();
                writePalette();
                if (this.repeat >= 0) {
                    writeNetscapeExt();
                }
            }
            writeGraphicCtrlExt();
            writeImageDesc();
            if (!this.firstFrame) {
                writePalette();
            }
            writePixels();
            this.firstFrame = false;
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public void analyzePixels() {
        byte[] bArr = this.pixels;
        int length = bArr.length;
        int i2 = length / 3;
        this.indexedPixels = new byte[i2];
        NeuQuant neuQuant = new NeuQuant(bArr, length, this.sample);
        this.colorTab = neuQuant.process();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            byte[] bArr2 = this.colorTab;
            if (i4 >= bArr2.length) {
                break;
            }
            byte b3 = bArr2[i4];
            int i5 = i4 + 2;
            bArr2[i4] = bArr2[i5];
            bArr2[i5] = b3;
            this.usedEntry[i4 / 3] = false;
            i4 += 3;
        }
        int i6 = 0;
        while (i3 < i2) {
            byte[] bArr3 = this.pixels;
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            int map = neuQuant.map(bArr3[i6] & 255, bArr3[i7] & 255, bArr3[i8] & 255);
            this.usedEntry[map] = true;
            this.indexedPixels[i3] = (byte) map;
            i3++;
            i6 = i8 + 1;
        }
        this.pixels = null;
        this.colorDepth = 8;
        this.palSize = 7;
        Color color = this.transparent;
        if (color != null) {
            this.transIndex = this.transparentExactMatch ? findExact(color) : findClosest(color);
        }
    }

    public int findClosest(Color color) {
        if (this.colorTab == null) {
            return -1;
        }
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int length = this.colorTab.length;
        int i2 = 0;
        int i3 = 16777216;
        int i4 = 0;
        while (i2 < length) {
            byte[] bArr = this.colorTab;
            int i5 = i2 + 1;
            int i6 = red - (bArr[i2] & 255);
            int i7 = i5 + 1;
            int i8 = green - (bArr[i5] & 255);
            int i9 = blue - (bArr[i7] & 255);
            int i10 = (i6 * i6) + (i8 * i8) + (i9 * i9);
            int i11 = i7 / 3;
            if (this.usedEntry[i11] && i10 < i3) {
                i3 = i10;
                i4 = i11;
            }
            i2 = i7 + 1;
        }
        return i4;
    }

    public int findExact(Color color) {
        if (this.colorTab == null) {
            return -1;
        }
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int length = this.colorTab.length / 3;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 3;
            if (this.usedEntry[i2]) {
                byte[] bArr = this.colorTab;
                if (red == (bArr[i3] & 255) && green == (bArr[i3 + 1] & 255) && blue == (bArr[i3 + 2] & 255)) {
                    return i2;
                }
            }
        }
        return -1;
    }

    public boolean finish() throws IOException {
        boolean z2;
        if (!this.started) {
            return false;
        }
        this.started = false;
        try {
            this.out.write(59);
            this.out.flush();
            if (this.closeStream) {
                this.out.close();
            }
            z2 = true;
        } catch (IOException unused) {
            z2 = false;
        }
        this.transIndex = 0;
        this.out = null;
        this.image = null;
        this.pixels = null;
        this.indexedPixels = null;
        this.colorTab = null;
        this.closeStream = false;
        this.firstFrame = true;
        return z2;
    }

    public void getImagePixels() {
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        int type = this.image.getType();
        if (width != this.width || height != this.height || type != 5) {
            BufferedImage bufferedImage = new BufferedImage(this.width, this.height, 5);
            Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
            graphics2DCreateGraphics.setColor(this.background);
            graphics2DCreateGraphics.fillRect(0, 0, this.width, this.height);
            graphics2DCreateGraphics.drawImage(this.image, 0, 0, (ImageObserver) null);
            this.image = bufferedImage;
        }
        this.pixels = this.image.getRaster().getDataBuffer().getData();
    }

    public boolean isColorUsed(Color color) {
        return findExact(color) != -1;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setBackground(Color color) {
        this.background = color;
    }

    public void setDelay(int i2) {
        this.delay = Math.round(i2 / 10.0f);
    }

    public void setDispose(int i2) {
        if (i2 >= 0) {
            this.dispose = i2;
        }
    }

    public void setFrameRate(float f2) {
        if (f2 != 0.0f) {
            this.delay = Math.round(100.0f / f2);
        }
    }

    public void setQuality(int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        this.sample = i2;
    }

    public void setRepeat(int i2) {
        if (i2 >= 0) {
            this.repeat = i2;
        }
    }

    public void setSize(int i2, int i3) {
        if (!this.started || this.firstFrame) {
            this.width = i2;
            this.height = i3;
            if (i2 < 1) {
                this.width = 320;
            }
            if (i3 < 1) {
                this.height = 240;
            }
            this.sizeSet = true;
        }
    }

    public void setTransparent(Color color) {
        setTransparent(color, false);
    }

    public boolean start(OutputStream outputStream) {
        boolean z2 = false;
        if (outputStream == null) {
            return false;
        }
        this.closeStream = false;
        this.out = outputStream;
        try {
            writeString("GIF89a");
            z2 = true;
        } catch (IOException unused) {
        }
        this.started = z2;
        return z2;
    }

    public void writeGraphicCtrlExt() throws IOException {
        int i2;
        int i3;
        this.out.write(33);
        this.out.write(R2.attr.actionModePasteDrawable);
        this.out.write(4);
        if (this.transparent == null) {
            i2 = 0;
            i3 = 0;
        } else {
            i2 = 1;
            i3 = 2;
        }
        int i4 = this.dispose;
        if (i4 >= 0) {
            i3 = i4 & 7;
        }
        this.out.write(i2 | (i3 << 2) | 0 | 0);
        writeShort(this.delay);
        this.out.write(this.transIndex);
        this.out.write(0);
    }

    public void writeImageDesc() throws IOException {
        this.out.write(44);
        writeShort(0);
        writeShort(0);
        writeShort(this.width);
        writeShort(this.height);
        if (this.firstFrame) {
            this.out.write(0);
        } else {
            this.out.write(this.palSize | 128);
        }
    }

    public void writeLSD() throws IOException {
        writeShort(this.width);
        writeShort(this.height);
        this.out.write(this.palSize | 240);
        this.out.write(0);
        this.out.write(0);
    }

    public void writeNetscapeExt() throws IOException {
        this.out.write(33);
        this.out.write(255);
        this.out.write(11);
        writeString("NETSCAPE2.0");
        this.out.write(3);
        this.out.write(1);
        writeShort(this.repeat);
        this.out.write(0);
    }

    public void writePalette() throws IOException {
        OutputStream outputStream = this.out;
        byte[] bArr = this.colorTab;
        outputStream.write(bArr, 0, bArr.length);
        int length = 768 - this.colorTab.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.out.write(0);
        }
    }

    public void writePixels() throws IOException {
        new LZWEncoder(this.width, this.height, this.indexedPixels, this.colorDepth).encode(this.out);
    }

    public void writeShort(int i2) throws IOException {
        this.out.write(i2 & 255);
        this.out.write((i2 >> 8) & 255);
    }

    public void writeString(String str) throws IOException {
        for (int i2 = 0; i2 < str.length(); i2++) {
            this.out.write((byte) str.charAt(i2));
        }
    }

    public void setTransparent(Color color, boolean z2) {
        this.transparent = color;
        this.transparentExactMatch = z2;
    }

    public boolean start(String str) {
        boolean z2 = false;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(str, new String[0]), new OpenOption[0]));
            this.out = bufferedOutputStream;
            boolean zStart = start(bufferedOutputStream);
            this.closeStream = true;
            z2 = zStart;
        } catch (IOException unused) {
        }
        this.started = z2;
        return z2;
    }
}
