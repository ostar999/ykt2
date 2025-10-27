package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@ShowFirstParty
@KeepForSdk
@SafeParcelable.Class(creator = "BitmapTeleporterCreator")
/* loaded from: classes3.dex */
public class BitmapTeleporter extends AbstractSafeParcelable implements ReflectedParcelable {

    @KeepForSdk
    public static final Parcelable.Creator<BitmapTeleporter> CREATOR = new zaa();

    @SafeParcelable.Field(id = 3)
    private final int mType;

    @SafeParcelable.VersionField(id = 1)
    private final int zali;

    @SafeParcelable.Field(id = 2)
    private ParcelFileDescriptor zalj;
    private Bitmap zalk;
    private boolean zall;
    private File zalm;

    @SafeParcelable.Constructor
    public BitmapTeleporter(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) ParcelFileDescriptor parcelFileDescriptor, @SafeParcelable.Param(id = 3) int i3) {
        this.zali = i2;
        this.zalj = parcelFileDescriptor;
        this.mType = i3;
        this.zalk = null;
        this.zall = false;
    }

    private static void zaa(Closeable closeable) throws IOException {
        try {
            closeable.close();
        } catch (IOException e2) {
            Log.w("BitmapTeleporter", "Could not close stream", e2);
        }
    }

    private final FileOutputStream zabx() throws IOException {
        File file = this.zalm;
        if (file == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            File fileCreateTempFile = File.createTempFile("teleporter", ".tmp", file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                this.zalj = ParcelFileDescriptor.open(fileCreateTempFile, 268435456);
                fileCreateTempFile.delete();
                return fileOutputStream;
            } catch (FileNotFoundException unused) {
                throw new IllegalStateException("Temporary file is somehow already deleted");
            }
        } catch (IOException e2) {
            throw new IllegalStateException("Could not create temporary file", e2);
        }
    }

    @KeepForSdk
    public Bitmap get() throws IOException {
        if (!this.zall) {
            DataInputStream dataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(this.zalj));
            try {
                try {
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    int i2 = dataInputStream.readInt();
                    int i3 = dataInputStream.readInt();
                    Bitmap.Config configValueOf = Bitmap.Config.valueOf(dataInputStream.readUTF());
                    dataInputStream.read(bArr);
                    zaa(dataInputStream);
                    ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, configValueOf);
                    bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferWrap);
                    this.zalk = bitmapCreateBitmap;
                    this.zall = true;
                } catch (IOException e2) {
                    throw new IllegalStateException("Could not read from parcel file descriptor", e2);
                }
            } catch (Throwable th) {
                zaa(dataInputStream);
                throw th;
            }
        }
        return this.zalk;
    }

    @KeepForSdk
    public void release() throws IOException {
        if (this.zall) {
            return;
        }
        try {
            this.zalj.close();
        } catch (IOException e2) {
            Log.w("BitmapTeleporter", "Could not close PFD", e2);
        }
    }

    @KeepForSdk
    public void setTempDir(File file) {
        if (file == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.zalm = file;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) throws IOException {
        if (this.zalj == null) {
            Bitmap bitmap = this.zalk;
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(byteBufferAllocate);
            byte[] bArrArray = byteBufferAllocate.array();
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(zabx()));
            try {
                try {
                    dataOutputStream.writeInt(bArrArray.length);
                    dataOutputStream.writeInt(bitmap.getWidth());
                    dataOutputStream.writeInt(bitmap.getHeight());
                    dataOutputStream.writeUTF(bitmap.getConfig().toString());
                    dataOutputStream.write(bArrArray);
                } catch (IOException e2) {
                    throw new IllegalStateException("Could not write into unlinked file", e2);
                }
            } finally {
                zaa(dataOutputStream);
            }
        }
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zalj, i2 | 1, false);
        SafeParcelWriter.writeInt(parcel, 3, this.mType);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        this.zalj = null;
    }

    @KeepForSdk
    public BitmapTeleporter(Bitmap bitmap) {
        this.zali = 1;
        this.zalj = null;
        this.mType = 0;
        this.zalk = bitmap;
        this.zall = true;
    }
}
