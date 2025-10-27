package cn.hutool.core.swing.clipboard;

import cn.hutool.core.exceptions.UtilException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/* loaded from: classes.dex */
public class ClipboardUtil {
    public static Object get(DataFlavor dataFlavor) {
        return get(getClipboard().getContents((Object) null), dataFlavor);
    }

    public static Clipboard getClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public static Image getImage() {
        return (Image) get(DataFlavor.imageFlavor);
    }

    public static String getStr() {
        return (String) get(DataFlavor.stringFlavor);
    }

    public static void listen(ClipboardListener clipboardListener) {
        listen(clipboardListener, true);
    }

    public static void set(Transferable transferable) {
        set(transferable, null);
    }

    public static void setImage(Image image) {
        set(new ImageSelection(image), null);
    }

    public static void setStr(String str) {
        set(new StringSelection(str));
    }

    public static Object get(Transferable transferable, DataFlavor dataFlavor) {
        if (transferable == null || !transferable.isDataFlavorSupported(dataFlavor)) {
            return null;
        }
        try {
            return transferable.getTransferData(dataFlavor);
        } catch (UnsupportedFlavorException | IOException e2) {
            throw new UtilException((Throwable) e2);
        }
    }

    public static Image getImage(Transferable transferable) {
        return (Image) get(transferable, DataFlavor.imageFlavor);
    }

    public static String getStr(Transferable transferable) {
        return (String) get(transferable, DataFlavor.stringFlavor);
    }

    public static void listen(ClipboardListener clipboardListener, boolean z2) {
        listen(10, 100L, clipboardListener, z2);
    }

    public static void set(Transferable transferable, ClipboardOwner clipboardOwner) {
        getClipboard().setContents(transferable, clipboardOwner);
    }

    public static void listen(int i2, long j2, ClipboardListener clipboardListener, boolean z2) {
        ClipboardMonitor.INSTANCE.setTryCount(i2).setDelay(j2).addListener(clipboardListener).listen(z2);
    }
}
