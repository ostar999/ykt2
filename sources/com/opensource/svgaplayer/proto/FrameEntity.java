package com.opensource.svgaplayer.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.util.List;
import okio.ByteString;

/* loaded from: classes4.dex */
public final class FrameEntity extends Message<FrameEntity, Builder> {
    public static final ProtoAdapter<FrameEntity> ADAPTER = new ProtoAdapter_FrameEntity();
    public static final Float DEFAULT_ALPHA = Float.valueOf(0.0f);
    public static final String DEFAULT_CLIPPATH = "";
    private static final long serialVersionUID = 0;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
    public final Float alpha;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#STRING", tag = 4)
    public final String clipPath;

    @WireField(adapter = "com.opensource.svgaplayer.proto.Layout#ADAPTER", tag = 2)
    public final Layout layout;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity#ADAPTER", label = WireField.Label.REPEATED, tag = 5)
    public final List<ShapeEntity> shapes;

    @WireField(adapter = "com.opensource.svgaplayer.proto.Transform#ADAPTER", tag = 3)
    public final Transform transform;

    public static final class Builder extends Message.Builder<FrameEntity, Builder> {
        public Float alpha;
        public String clipPath;
        public Layout layout;
        public List<ShapeEntity> shapes = Internal.newMutableList();
        public Transform transform;

        public Builder alpha(Float f2) {
            this.alpha = f2;
            return this;
        }

        public Builder clipPath(String str) {
            this.clipPath = str;
            return this;
        }

        public Builder layout(Layout layout) {
            this.layout = layout;
            return this;
        }

        public Builder shapes(List<ShapeEntity> list) {
            Internal.checkElementsNotNull(list);
            this.shapes = list;
            return this;
        }

        public Builder transform(Transform transform) {
            this.transform = transform;
            return this;
        }

        @Override // com.squareup.wire.Message.Builder
        public FrameEntity build() {
            return new FrameEntity(this.alpha, this.layout, this.transform, this.clipPath, this.shapes, super.buildUnknownFields());
        }
    }

    public static final class ProtoAdapter_FrameEntity extends ProtoAdapter<FrameEntity> {
        public ProtoAdapter_FrameEntity() {
            super(FieldEncoding.LENGTH_DELIMITED, FrameEntity.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        public FrameEntity decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long jBeginMessage = protoReader.beginMessage();
            while (true) {
                int iNextTag = protoReader.nextTag();
                if (iNextTag == -1) {
                    protoReader.endMessage(jBeginMessage);
                    return builder.build();
                }
                if (iNextTag == 1) {
                    builder.alpha(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag == 2) {
                    builder.layout(Layout.ADAPTER.decode(protoReader));
                } else if (iNextTag == 3) {
                    builder.transform(Transform.ADAPTER.decode(protoReader));
                } else if (iNextTag == 4) {
                    builder.clipPath(ProtoAdapter.STRING.decode(protoReader));
                } else if (iNextTag != 5) {
                    FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                    builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                } else {
                    builder.shapes.add(ShapeEntity.ADAPTER.decode(protoReader));
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, FrameEntity frameEntity) throws IOException {
            Float f2 = frameEntity.alpha;
            if (f2 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
            }
            Layout layout = frameEntity.layout;
            if (layout != null) {
                Layout.ADAPTER.encodeWithTag(protoWriter, 2, layout);
            }
            Transform transform = frameEntity.transform;
            if (transform != null) {
                Transform.ADAPTER.encodeWithTag(protoWriter, 3, transform);
            }
            String str = frameEntity.clipPath;
            if (str != null) {
                ProtoAdapter.STRING.encodeWithTag(protoWriter, 4, str);
            }
            ShapeEntity.ADAPTER.asRepeated().encodeWithTag(protoWriter, 5, frameEntity.shapes);
            protoWriter.writeBytes(frameEntity.unknownFields());
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(FrameEntity frameEntity) {
            Float f2 = frameEntity.alpha;
            int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
            Layout layout = frameEntity.layout;
            int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (layout != null ? Layout.ADAPTER.encodedSizeWithTag(2, layout) : 0);
            Transform transform = frameEntity.transform;
            int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (transform != null ? Transform.ADAPTER.encodedSizeWithTag(3, transform) : 0);
            String str = frameEntity.clipPath;
            return iEncodedSizeWithTag3 + (str != null ? ProtoAdapter.STRING.encodedSizeWithTag(4, str) : 0) + ShapeEntity.ADAPTER.asRepeated().encodedSizeWithTag(5, frameEntity.shapes) + frameEntity.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public FrameEntity redact(FrameEntity frameEntity) {
            Builder builderNewBuilder = frameEntity.newBuilder();
            Layout layout = builderNewBuilder.layout;
            if (layout != null) {
                builderNewBuilder.layout = Layout.ADAPTER.redact(layout);
            }
            Transform transform = builderNewBuilder.transform;
            if (transform != null) {
                builderNewBuilder.transform = Transform.ADAPTER.redact(transform);
            }
            Internal.redactElements(builderNewBuilder.shapes, ShapeEntity.ADAPTER);
            builderNewBuilder.clearUnknownFields();
            return builderNewBuilder.build();
        }
    }

    public FrameEntity(Float f2, Layout layout, Transform transform, String str, List<ShapeEntity> list) {
        this(f2, layout, transform, str, list, ByteString.EMPTY);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FrameEntity)) {
            return false;
        }
        FrameEntity frameEntity = (FrameEntity) obj;
        return unknownFields().equals(frameEntity.unknownFields()) && Internal.equals(this.alpha, frameEntity.alpha) && Internal.equals(this.layout, frameEntity.layout) && Internal.equals(this.transform, frameEntity.transform) && Internal.equals(this.clipPath, frameEntity.clipPath) && this.shapes.equals(frameEntity.shapes);
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = unknownFields().hashCode() * 37;
        Float f2 = this.alpha;
        int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
        Layout layout = this.layout;
        int iHashCode3 = (iHashCode2 + (layout != null ? layout.hashCode() : 0)) * 37;
        Transform transform = this.transform;
        int iHashCode4 = (iHashCode3 + (transform != null ? transform.hashCode() : 0)) * 37;
        String str = this.clipPath;
        int iHashCode5 = ((iHashCode4 + (str != null ? str.hashCode() : 0)) * 37) + this.shapes.hashCode();
        this.hashCode = iHashCode5;
        return iHashCode5;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.alpha != null) {
            sb.append(", alpha=");
            sb.append(this.alpha);
        }
        if (this.layout != null) {
            sb.append(", layout=");
            sb.append(this.layout);
        }
        if (this.transform != null) {
            sb.append(", transform=");
            sb.append(this.transform);
        }
        if (this.clipPath != null) {
            sb.append(", clipPath=");
            sb.append(this.clipPath);
        }
        if (!this.shapes.isEmpty()) {
            sb.append(", shapes=");
            sb.append(this.shapes);
        }
        StringBuilder sbReplace = sb.replace(0, 2, "FrameEntity{");
        sbReplace.append('}');
        return sbReplace.toString();
    }

    public FrameEntity(Float f2, Layout layout, Transform transform, String str, List<ShapeEntity> list, ByteString byteString) {
        super(ADAPTER, byteString);
        this.alpha = f2;
        this.layout = layout;
        this.transform = transform;
        this.clipPath = str;
        this.shapes = Internal.immutableCopyOf("shapes", list);
    }

    @Override // com.squareup.wire.Message
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.alpha = this.alpha;
        builder.layout = this.layout;
        builder.transform = this.transform;
        builder.clipPath = this.clipPath;
        builder.shapes = Internal.copyOf("shapes", this.shapes);
        builder.addUnknownFields(unknownFields());
        return builder;
    }
}
