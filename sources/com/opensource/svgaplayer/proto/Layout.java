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
public final class Layout extends Message<Layout, Builder> {
    public static final ProtoAdapter<Layout> ADAPTER = new ProtoAdapter_Layout();
    public static final Float DEFAULT_HEIGHT;
    public static final Float DEFAULT_WIDTH;
    public static final Float DEFAULT_X;
    public static final Float DEFAULT_Y;
    private static final long serialVersionUID = 0;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)
    public final Float height;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
    public final Float width;

    /* renamed from: x, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
    public final Float f10687x;

    /* renamed from: y, reason: collision with root package name */
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
    public final Float f10688y;

    public static final class Builder extends Message.Builder<Layout, Builder> {
        public Float height;
        public Float width;

        /* renamed from: x, reason: collision with root package name */
        public Float f10689x;

        /* renamed from: y, reason: collision with root package name */
        public Float f10690y;

        public Builder height(Float f2) {
            this.height = f2;
            return this;
        }

        public Builder width(Float f2) {
            this.width = f2;
            return this;
        }

        public Builder x(Float f2) {
            this.f10689x = f2;
            return this;
        }

        public Builder y(Float f2) {
            this.f10690y = f2;
            return this;
        }

        @Override // com.squareup.wire.Message.Builder
        public Layout build() {
            return new Layout(this.f10689x, this.f10690y, this.width, this.height, super.buildUnknownFields());
        }
    }

    public static final class ProtoAdapter_Layout extends ProtoAdapter<Layout> {
        public ProtoAdapter_Layout() {
            super(FieldEncoding.LENGTH_DELIMITED, Layout.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        public Layout decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long jBeginMessage = protoReader.beginMessage();
            while (true) {
                int iNextTag = protoReader.nextTag();
                if (iNextTag == -1) {
                    protoReader.endMessage(jBeginMessage);
                    return builder.build();
                }
                if (iNextTag == 1) {
                    builder.x(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag == 2) {
                    builder.y(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag == 3) {
                    builder.width(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag != 4) {
                    FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                    builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                } else {
                    builder.height(ProtoAdapter.FLOAT.decode(protoReader));
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, Layout layout) throws IOException {
            Float f2 = layout.f10687x;
            if (f2 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
            }
            Float f3 = layout.f10688y;
            if (f3 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
            }
            Float f4 = layout.width;
            if (f4 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f4);
            }
            Float f5 = layout.height;
            if (f5 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f5);
            }
            protoWriter.writeBytes(layout.unknownFields());
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(Layout layout) {
            Float f2 = layout.f10687x;
            int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
            Float f3 = layout.f10688y;
            int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
            Float f4 = layout.width;
            int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f4) : 0);
            Float f5 = layout.height;
            return iEncodedSizeWithTag3 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f5) : 0) + layout.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public Layout redact(Layout layout) {
            Builder builderNewBuilder = layout.newBuilder();
            builderNewBuilder.clearUnknownFields();
            return builderNewBuilder.build();
        }
    }

    static {
        Float fValueOf = Float.valueOf(0.0f);
        DEFAULT_X = fValueOf;
        DEFAULT_Y = fValueOf;
        DEFAULT_WIDTH = fValueOf;
        DEFAULT_HEIGHT = fValueOf;
    }

    public Layout(Float f2, Float f3, Float f4, Float f5) {
        this(f2, f3, f4, f5, ByteString.EMPTY);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Layout)) {
            return false;
        }
        Layout layout = (Layout) obj;
        return unknownFields().equals(layout.unknownFields()) && Internal.equals(this.f10687x, layout.f10687x) && Internal.equals(this.f10688y, layout.f10688y) && Internal.equals(this.width, layout.width) && Internal.equals(this.height, layout.height);
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = unknownFields().hashCode() * 37;
        Float f2 = this.f10687x;
        int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
        Float f3 = this.f10688y;
        int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
        Float f4 = this.width;
        int iHashCode4 = (iHashCode3 + (f4 != null ? f4.hashCode() : 0)) * 37;
        Float f5 = this.height;
        int iHashCode5 = iHashCode4 + (f5 != null ? f5.hashCode() : 0);
        this.hashCode = iHashCode5;
        return iHashCode5;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.f10687x != null) {
            sb.append(", x=");
            sb.append(this.f10687x);
        }
        if (this.f10688y != null) {
            sb.append(", y=");
            sb.append(this.f10688y);
        }
        if (this.width != null) {
            sb.append(", width=");
            sb.append(this.width);
        }
        if (this.height != null) {
            sb.append(", height=");
            sb.append(this.height);
        }
        StringBuilder sbReplace = sb.replace(0, 2, "Layout{");
        sbReplace.append('}');
        return sbReplace.toString();
    }

    public Layout(Float f2, Float f3, Float f4, Float f5, ByteString byteString) {
        super(ADAPTER, byteString);
        this.f10687x = f2;
        this.f10688y = f3;
        this.width = f4;
        this.height = f5;
    }

    @Override // com.squareup.wire.Message
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.f10689x = this.f10687x;
        builder.f10690y = this.f10688y;
        builder.width = this.width;
        builder.height = this.height;
        builder.addUnknownFields(unknownFields());
        return builder;
    }
}
