package com.opensource.svgaplayer.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import okio.ByteString;

/* loaded from: classes4.dex */
public final class Transform extends Message<Transform, Builder> {
    public static final ProtoAdapter<Transform> ADAPTER = new ProtoAdapter_Transform();
    public static final Float DEFAULT_A;
    public static final Float DEFAULT_B;
    public static final Float DEFAULT_C;
    public static final Float DEFAULT_D;
    public static final Float DEFAULT_TX;
    public static final Float DEFAULT_TY;
    private static final long serialVersionUID = 0;

    /* renamed from: a, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
    public final Float f10709a;

    /* renamed from: b, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
    public final Float f10710b;

    /* renamed from: c, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
    public final Float f10711c;

    /* renamed from: d, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)
    public final Float f10712d;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 5)
    public final Float tx;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 6)
    public final Float ty;

    public static final class Builder extends Message.Builder<Transform, Builder> {

        /* renamed from: a, reason: collision with root package name */
        public Float f10713a;

        /* renamed from: b, reason: collision with root package name */
        public Float f10714b;

        /* renamed from: c, reason: collision with root package name */
        public Float f10715c;

        /* renamed from: d, reason: collision with root package name */
        public Float f10716d;
        public Float tx;
        public Float ty;

        public Builder a(Float f2) {
            this.f10713a = f2;
            return this;
        }

        public Builder b(Float f2) {
            this.f10714b = f2;
            return this;
        }

        public Builder c(Float f2) {
            this.f10715c = f2;
            return this;
        }

        public Builder d(Float f2) {
            this.f10716d = f2;
            return this;
        }

        public Builder tx(Float f2) {
            this.tx = f2;
            return this;
        }

        public Builder ty(Float f2) {
            this.ty = f2;
            return this;
        }

        @Override // com.squareup.wire.Message.Builder
        public Transform build() {
            return new Transform(this.f10713a, this.f10714b, this.f10715c, this.f10716d, this.tx, this.ty, super.buildUnknownFields());
        }
    }

    public static final class ProtoAdapter_Transform extends ProtoAdapter<Transform> {
        public ProtoAdapter_Transform() {
            super(FieldEncoding.LENGTH_DELIMITED, Transform.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        public Transform decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long jBeginMessage = protoReader.beginMessage();
            while (true) {
                int iNextTag = protoReader.nextTag();
                if (iNextTag == -1) {
                    protoReader.endMessage(jBeginMessage);
                    return builder.build();
                }
                switch (iNextTag) {
                    case 1:
                        builder.a(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    case 2:
                        builder.b(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    case 3:
                        builder.c(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    case 4:
                        builder.d(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    case 5:
                        builder.tx(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    case 6:
                        builder.ty(ProtoAdapter.FLOAT.decode(protoReader));
                        break;
                    default:
                        FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                        builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                        break;
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, Transform transform) throws IOException {
            Float f2 = transform.f10709a;
            if (f2 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
            }
            Float f3 = transform.f10710b;
            if (f3 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
            }
            Float f4 = transform.f10711c;
            if (f4 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f4);
            }
            Float f5 = transform.f10712d;
            if (f5 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f5);
            }
            Float f6 = transform.tx;
            if (f6 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 5, f6);
            }
            Float f7 = transform.ty;
            if (f7 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 6, f7);
            }
            protoWriter.writeBytes(transform.unknownFields());
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(Transform transform) {
            Float f2 = transform.f10709a;
            int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
            Float f3 = transform.f10710b;
            int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
            Float f4 = transform.f10711c;
            int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f4) : 0);
            Float f5 = transform.f10712d;
            int iEncodedSizeWithTag4 = iEncodedSizeWithTag3 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f5) : 0);
            Float f6 = transform.tx;
            int iEncodedSizeWithTag5 = iEncodedSizeWithTag4 + (f6 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(5, f6) : 0);
            Float f7 = transform.ty;
            return iEncodedSizeWithTag5 + (f7 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(6, f7) : 0) + transform.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public Transform redact(Transform transform) {
            Builder builderNewBuilder = transform.newBuilder();
            builderNewBuilder.clearUnknownFields();
            return builderNewBuilder.build();
        }
    }

    static {
        Float fValueOf = Float.valueOf(0.0f);
        DEFAULT_A = fValueOf;
        DEFAULT_B = fValueOf;
        DEFAULT_C = fValueOf;
        DEFAULT_D = fValueOf;
        DEFAULT_TX = fValueOf;
        DEFAULT_TY = fValueOf;
    }

    public Transform(Float f2, Float f3, Float f4, Float f5, Float f6, Float f7) {
        this(f2, f3, f4, f5, f6, f7, ByteString.EMPTY);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Transform)) {
            return false;
        }
        Transform transform = (Transform) obj;
        return unknownFields().equals(transform.unknownFields()) && Internal.equals(this.f10709a, transform.f10709a) && Internal.equals(this.f10710b, transform.f10710b) && Internal.equals(this.f10711c, transform.f10711c) && Internal.equals(this.f10712d, transform.f10712d) && Internal.equals(this.tx, transform.tx) && Internal.equals(this.ty, transform.ty);
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = unknownFields().hashCode() * 37;
        Float f2 = this.f10709a;
        int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
        Float f3 = this.f10710b;
        int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
        Float f4 = this.f10711c;
        int iHashCode4 = (iHashCode3 + (f4 != null ? f4.hashCode() : 0)) * 37;
        Float f5 = this.f10712d;
        int iHashCode5 = (iHashCode4 + (f5 != null ? f5.hashCode() : 0)) * 37;
        Float f6 = this.tx;
        int iHashCode6 = (iHashCode5 + (f6 != null ? f6.hashCode() : 0)) * 37;
        Float f7 = this.ty;
        int iHashCode7 = iHashCode6 + (f7 != null ? f7.hashCode() : 0);
        this.hashCode = iHashCode7;
        return iHashCode7;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.f10709a != null) {
            sb.append(", a=");
            sb.append(this.f10709a);
        }
        if (this.f10710b != null) {
            sb.append(", b=");
            sb.append(this.f10710b);
        }
        if (this.f10711c != null) {
            sb.append(", c=");
            sb.append(this.f10711c);
        }
        if (this.f10712d != null) {
            sb.append(", d=");
            sb.append(this.f10712d);
        }
        if (this.tx != null) {
            sb.append(", tx=");
            sb.append(this.tx);
        }
        if (this.ty != null) {
            sb.append(", ty=");
            sb.append(this.ty);
        }
        StringBuilder sbReplace = sb.replace(0, 2, "Transform{");
        sbReplace.append('}');
        return sbReplace.toString();
    }

    public Transform(Float f2, Float f3, Float f4, Float f5, Float f6, Float f7, ByteString byteString) {
        super(ADAPTER, byteString);
        this.f10709a = f2;
        this.f10710b = f3;
        this.f10711c = f4;
        this.f10712d = f5;
        this.tx = f6;
        this.ty = f7;
    }

    @Override // com.squareup.wire.Message
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.f10713a = this.f10709a;
        builder.f10714b = this.f10710b;
        builder.f10715c = this.f10711c;
        builder.f10716d = this.f10712d;
        builder.tx = this.tx;
        builder.ty = this.ty;
        builder.addUnknownFields(unknownFields());
        return builder;
    }
}
