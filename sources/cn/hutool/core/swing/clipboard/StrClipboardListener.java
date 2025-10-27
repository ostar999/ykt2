package cn.hutool.core.swing.clipboard;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.Serializable;

/* loaded from: classes.dex */
public abstract class StrClipboardListener implements ClipboardListener, Serializable {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.swing.clipboard.ClipboardListener
    public Transferable onChange(Clipboard clipboard, Transferable transferable) {
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return onChange(clipboard, ClipboardUtil.getStr(transferable));
        }
        return null;
    }

    public abstract Transferable onChange(Clipboard clipboard, String str);
}
