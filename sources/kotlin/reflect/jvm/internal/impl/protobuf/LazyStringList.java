package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.List;

/* loaded from: classes8.dex */
public interface LazyStringList extends ProtocolStringList {
    void add(ByteString byteString);

    ByteString getByteString(int i2);

    List<?> getUnderlyingElements();

    LazyStringList getUnmodifiableView();
}
