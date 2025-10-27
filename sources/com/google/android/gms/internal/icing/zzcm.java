package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzcm;
import com.google.android.gms.internal.icing.zzco;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class zzcm<MessageType extends zzcm<MessageType, BuilderType>, BuilderType extends zzco<MessageType, BuilderType>> implements zzfh {
    protected int zzga = 0;

    public static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzeb.checkNotNull(iterable);
        if (iterable instanceof zzeo) {
            List<?> listZzcd = ((zzeo) iterable).zzcd();
            zzeo zzeoVar = (zzeo) list;
            int size = list.size();
            for (Object obj : listZzcd) {
                if (obj == null) {
                    int size2 = zzeoVar.size() - size;
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(size2);
                    sb.append(" is null.");
                    String string = sb.toString();
                    for (int size3 = zzeoVar.size() - 1; size3 >= size; size3--) {
                        zzeoVar.remove(size3);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzct) {
                    zzeoVar.zzc((zzct) obj);
                } else {
                    zzeoVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzfq) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size4 = list.size();
        for (T t2 : iterable) {
            if (t2 == null) {
                int size5 = list.size() - size4;
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Element at index ");
                sb2.append(size5);
                sb2.append(" is null.");
                String string2 = sb2.toString();
                for (int size6 = list.size() - 1; size6 >= size4; size6--) {
                    list.remove(size6);
                }
                throw new NullPointerException(string2);
            }
            list.add(t2);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzbl()];
            zzdk zzdkVarZzb = zzdk.zzb(bArr);
            zzb(zzdkVarZzb);
            zzdkVarZzb.zzav();
            return bArr;
        } catch (IOException e2) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(name.length() + 62 + "byte array".length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e2);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzfh
    public final zzct zzad() {
        try {
            zzdb zzdbVarZzm = zzct.zzm(zzbl());
            zzb(zzdbVarZzm.zzas());
            return zzdbVarZzm.zzar();
        } catch (IOException e2) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(name.length() + 62 + "ByteString".length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e2);
        }
    }

    public int zzae() {
        throw new UnsupportedOperationException();
    }

    public void zzg(int i2) {
        throw new UnsupportedOperationException();
    }
}
