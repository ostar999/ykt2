package cn.hutool.core.swing.clipboard;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ImageSelection implements Transferable, Serializable {
    private static final long serialVersionUID = 1;
    private final Image image;

    public ImageSelection(Image image) {
        this.image = image;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.awt.datatransfer.UnsupportedFlavorException */
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException {
        if (DataFlavor.imageFlavor.equals(dataFlavor)) {
            return this.image;
        }
        throw new UnsupportedFlavorException(dataFlavor);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        return DataFlavor.imageFlavor.equals(dataFlavor);
    }
}
