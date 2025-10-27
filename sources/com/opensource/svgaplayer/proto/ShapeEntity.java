package com.opensource.svgaplayer.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireEnum;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import okio.ByteString;

/* loaded from: classes4.dex */
public final class ShapeEntity extends Message<ShapeEntity, Builder> {
    public static final ProtoAdapter<ShapeEntity> ADAPTER = new ProtoAdapter_ShapeEntity();
    public static final ShapeType DEFAULT_TYPE = ShapeType.SHAPE;
    private static final long serialVersionUID = 0;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$EllipseArgs#ADAPTER", tag = 4)
    public final EllipseArgs ellipse;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$RectArgs#ADAPTER", tag = 3)
    public final RectArgs rect;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeArgs#ADAPTER", tag = 2)
    public final ShapeArgs shape;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeStyle#ADAPTER", tag = 10)
    public final ShapeStyle styles;

    @WireField(adapter = "com.opensource.svgaplayer.proto.Transform#ADAPTER", tag = 11)
    public final Transform transform;

    @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeType#ADAPTER", tag = 1)
    public final ShapeType type;

    public static final class Builder extends Message.Builder<ShapeEntity, Builder> {
        public EllipseArgs ellipse;
        public RectArgs rect;
        public ShapeArgs shape;
        public ShapeStyle styles;
        public Transform transform;
        public ShapeType type;

        public Builder ellipse(EllipseArgs ellipseArgs) {
            this.ellipse = ellipseArgs;
            this.shape = null;
            this.rect = null;
            return this;
        }

        public Builder rect(RectArgs rectArgs) {
            this.rect = rectArgs;
            this.shape = null;
            this.ellipse = null;
            return this;
        }

        public Builder shape(ShapeArgs shapeArgs) {
            this.shape = shapeArgs;
            this.rect = null;
            this.ellipse = null;
            return this;
        }

        public Builder styles(ShapeStyle shapeStyle) {
            this.styles = shapeStyle;
            return this;
        }

        public Builder transform(Transform transform) {
            this.transform = transform;
            return this;
        }

        public Builder type(ShapeType shapeType) {
            this.type = shapeType;
            return this;
        }

        @Override // com.squareup.wire.Message.Builder
        public ShapeEntity build() {
            return new ShapeEntity(this.type, this.styles, this.transform, this.shape, this.rect, this.ellipse, super.buildUnknownFields());
        }
    }

    public static final class EllipseArgs extends Message<EllipseArgs, Builder> {
        public static final ProtoAdapter<EllipseArgs> ADAPTER = new ProtoAdapter_EllipseArgs();
        public static final Float DEFAULT_RADIUSX;
        public static final Float DEFAULT_RADIUSY;
        public static final Float DEFAULT_X;
        public static final Float DEFAULT_Y;
        private static final long serialVersionUID = 0;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
        public final Float radiusX;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)
        public final Float radiusY;

        /* renamed from: x, reason: collision with root package name */
        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
        public final Float f10691x;

        /* renamed from: y, reason: collision with root package name */
        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
        public final Float f10692y;

        public static final class Builder extends Message.Builder<EllipseArgs, Builder> {
            public Float radiusX;
            public Float radiusY;

            /* renamed from: x, reason: collision with root package name */
            public Float f10693x;

            /* renamed from: y, reason: collision with root package name */
            public Float f10694y;

            public Builder radiusX(Float f2) {
                this.radiusX = f2;
                return this;
            }

            public Builder radiusY(Float f2) {
                this.radiusY = f2;
                return this;
            }

            public Builder x(Float f2) {
                this.f10693x = f2;
                return this;
            }

            public Builder y(Float f2) {
                this.f10694y = f2;
                return this;
            }

            @Override // com.squareup.wire.Message.Builder
            public EllipseArgs build() {
                return new EllipseArgs(this.f10693x, this.f10694y, this.radiusX, this.radiusY, super.buildUnknownFields());
            }
        }

        public static final class ProtoAdapter_EllipseArgs extends ProtoAdapter<EllipseArgs> {
            public ProtoAdapter_EllipseArgs() {
                super(FieldEncoding.LENGTH_DELIMITED, EllipseArgs.class);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.squareup.wire.ProtoAdapter
            public EllipseArgs decode(ProtoReader protoReader) throws IOException {
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
                        builder.radiusX(ProtoAdapter.FLOAT.decode(protoReader));
                    } else if (iNextTag != 4) {
                        FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                        builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                    } else {
                        builder.radiusY(ProtoAdapter.FLOAT.decode(protoReader));
                    }
                }
            }

            @Override // com.squareup.wire.ProtoAdapter
            public void encode(ProtoWriter protoWriter, EllipseArgs ellipseArgs) throws IOException {
                Float f2 = ellipseArgs.f10691x;
                if (f2 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
                }
                Float f3 = ellipseArgs.f10692y;
                if (f3 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
                }
                Float f4 = ellipseArgs.radiusX;
                if (f4 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f4);
                }
                Float f5 = ellipseArgs.radiusY;
                if (f5 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f5);
                }
                protoWriter.writeBytes(ellipseArgs.unknownFields());
            }

            @Override // com.squareup.wire.ProtoAdapter
            public int encodedSize(EllipseArgs ellipseArgs) {
                Float f2 = ellipseArgs.f10691x;
                int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
                Float f3 = ellipseArgs.f10692y;
                int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
                Float f4 = ellipseArgs.radiusX;
                int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f4) : 0);
                Float f5 = ellipseArgs.radiusY;
                return iEncodedSizeWithTag3 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f5) : 0) + ellipseArgs.unknownFields().size();
            }

            @Override // com.squareup.wire.ProtoAdapter
            public EllipseArgs redact(EllipseArgs ellipseArgs) {
                Builder builderNewBuilder = ellipseArgs.newBuilder();
                builderNewBuilder.clearUnknownFields();
                return builderNewBuilder.build();
            }
        }

        static {
            Float fValueOf = Float.valueOf(0.0f);
            DEFAULT_X = fValueOf;
            DEFAULT_Y = fValueOf;
            DEFAULT_RADIUSX = fValueOf;
            DEFAULT_RADIUSY = fValueOf;
        }

        public EllipseArgs(Float f2, Float f3, Float f4, Float f5) {
            this(f2, f3, f4, f5, ByteString.EMPTY);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EllipseArgs)) {
                return false;
            }
            EllipseArgs ellipseArgs = (EllipseArgs) obj;
            return unknownFields().equals(ellipseArgs.unknownFields()) && Internal.equals(this.f10691x, ellipseArgs.f10691x) && Internal.equals(this.f10692y, ellipseArgs.f10692y) && Internal.equals(this.radiusX, ellipseArgs.radiusX) && Internal.equals(this.radiusY, ellipseArgs.radiusY);
        }

        public int hashCode() {
            int i2 = this.hashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = unknownFields().hashCode() * 37;
            Float f2 = this.f10691x;
            int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
            Float f3 = this.f10692y;
            int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
            Float f4 = this.radiusX;
            int iHashCode4 = (iHashCode3 + (f4 != null ? f4.hashCode() : 0)) * 37;
            Float f5 = this.radiusY;
            int iHashCode5 = iHashCode4 + (f5 != null ? f5.hashCode() : 0);
            this.hashCode = iHashCode5;
            return iHashCode5;
        }

        @Override // com.squareup.wire.Message
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.f10691x != null) {
                sb.append(", x=");
                sb.append(this.f10691x);
            }
            if (this.f10692y != null) {
                sb.append(", y=");
                sb.append(this.f10692y);
            }
            if (this.radiusX != null) {
                sb.append(", radiusX=");
                sb.append(this.radiusX);
            }
            if (this.radiusY != null) {
                sb.append(", radiusY=");
                sb.append(this.radiusY);
            }
            StringBuilder sbReplace = sb.replace(0, 2, "EllipseArgs{");
            sbReplace.append('}');
            return sbReplace.toString();
        }

        public EllipseArgs(Float f2, Float f3, Float f4, Float f5, ByteString byteString) {
            super(ADAPTER, byteString);
            this.f10691x = f2;
            this.f10692y = f3;
            this.radiusX = f4;
            this.radiusY = f5;
        }

        @Override // com.squareup.wire.Message
        public Builder newBuilder() {
            Builder builder = new Builder();
            builder.f10693x = this.f10691x;
            builder.f10694y = this.f10692y;
            builder.radiusX = this.radiusX;
            builder.radiusY = this.radiusY;
            builder.addUnknownFields(unknownFields());
            return builder;
        }
    }

    public static final class ProtoAdapter_ShapeEntity extends ProtoAdapter<ShapeEntity> {
        public ProtoAdapter_ShapeEntity() {
            super(FieldEncoding.LENGTH_DELIMITED, ShapeEntity.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        public ShapeEntity decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long jBeginMessage = protoReader.beginMessage();
            while (true) {
                int iNextTag = protoReader.nextTag();
                if (iNextTag == -1) {
                    protoReader.endMessage(jBeginMessage);
                    return builder.build();
                }
                if (iNextTag == 1) {
                    try {
                        builder.type(ShapeType.ADAPTER.decode(protoReader));
                    } catch (ProtoAdapter.EnumConstantNotFoundException e2) {
                        builder.addUnknownField(iNextTag, FieldEncoding.VARINT, Long.valueOf(e2.value));
                    }
                } else if (iNextTag == 2) {
                    builder.shape(ShapeArgs.ADAPTER.decode(protoReader));
                } else if (iNextTag == 3) {
                    builder.rect(RectArgs.ADAPTER.decode(protoReader));
                } else if (iNextTag == 4) {
                    builder.ellipse(EllipseArgs.ADAPTER.decode(protoReader));
                } else if (iNextTag == 10) {
                    builder.styles(ShapeStyle.ADAPTER.decode(protoReader));
                } else if (iNextTag != 11) {
                    FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                    builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                } else {
                    builder.transform(Transform.ADAPTER.decode(protoReader));
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, ShapeEntity shapeEntity) throws IOException {
            ShapeType shapeType = shapeEntity.type;
            if (shapeType != null) {
                ShapeType.ADAPTER.encodeWithTag(protoWriter, 1, shapeType);
            }
            ShapeStyle shapeStyle = shapeEntity.styles;
            if (shapeStyle != null) {
                ShapeStyle.ADAPTER.encodeWithTag(protoWriter, 10, shapeStyle);
            }
            Transform transform = shapeEntity.transform;
            if (transform != null) {
                Transform.ADAPTER.encodeWithTag(protoWriter, 11, transform);
            }
            ShapeArgs shapeArgs = shapeEntity.shape;
            if (shapeArgs != null) {
                ShapeArgs.ADAPTER.encodeWithTag(protoWriter, 2, shapeArgs);
            }
            RectArgs rectArgs = shapeEntity.rect;
            if (rectArgs != null) {
                RectArgs.ADAPTER.encodeWithTag(protoWriter, 3, rectArgs);
            }
            EllipseArgs ellipseArgs = shapeEntity.ellipse;
            if (ellipseArgs != null) {
                EllipseArgs.ADAPTER.encodeWithTag(protoWriter, 4, ellipseArgs);
            }
            protoWriter.writeBytes(shapeEntity.unknownFields());
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(ShapeEntity shapeEntity) {
            ShapeType shapeType = shapeEntity.type;
            int iEncodedSizeWithTag = shapeType != null ? ShapeType.ADAPTER.encodedSizeWithTag(1, shapeType) : 0;
            ShapeStyle shapeStyle = shapeEntity.styles;
            int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (shapeStyle != null ? ShapeStyle.ADAPTER.encodedSizeWithTag(10, shapeStyle) : 0);
            Transform transform = shapeEntity.transform;
            int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (transform != null ? Transform.ADAPTER.encodedSizeWithTag(11, transform) : 0);
            ShapeArgs shapeArgs = shapeEntity.shape;
            int iEncodedSizeWithTag4 = iEncodedSizeWithTag3 + (shapeArgs != null ? ShapeArgs.ADAPTER.encodedSizeWithTag(2, shapeArgs) : 0);
            RectArgs rectArgs = shapeEntity.rect;
            int iEncodedSizeWithTag5 = iEncodedSizeWithTag4 + (rectArgs != null ? RectArgs.ADAPTER.encodedSizeWithTag(3, rectArgs) : 0);
            EllipseArgs ellipseArgs = shapeEntity.ellipse;
            return iEncodedSizeWithTag5 + (ellipseArgs != null ? EllipseArgs.ADAPTER.encodedSizeWithTag(4, ellipseArgs) : 0) + shapeEntity.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public ShapeEntity redact(ShapeEntity shapeEntity) {
            Builder builderNewBuilder = shapeEntity.newBuilder();
            ShapeStyle shapeStyle = builderNewBuilder.styles;
            if (shapeStyle != null) {
                builderNewBuilder.styles = ShapeStyle.ADAPTER.redact(shapeStyle);
            }
            Transform transform = builderNewBuilder.transform;
            if (transform != null) {
                builderNewBuilder.transform = Transform.ADAPTER.redact(transform);
            }
            ShapeArgs shapeArgs = builderNewBuilder.shape;
            if (shapeArgs != null) {
                builderNewBuilder.shape = ShapeArgs.ADAPTER.redact(shapeArgs);
            }
            RectArgs rectArgs = builderNewBuilder.rect;
            if (rectArgs != null) {
                builderNewBuilder.rect = RectArgs.ADAPTER.redact(rectArgs);
            }
            EllipseArgs ellipseArgs = builderNewBuilder.ellipse;
            if (ellipseArgs != null) {
                builderNewBuilder.ellipse = EllipseArgs.ADAPTER.redact(ellipseArgs);
            }
            builderNewBuilder.clearUnknownFields();
            return builderNewBuilder.build();
        }
    }

    public static final class RectArgs extends Message<RectArgs, Builder> {
        public static final ProtoAdapter<RectArgs> ADAPTER = new ProtoAdapter_RectArgs();
        public static final Float DEFAULT_CORNERRADIUS;
        public static final Float DEFAULT_HEIGHT;
        public static final Float DEFAULT_WIDTH;
        public static final Float DEFAULT_X;
        public static final Float DEFAULT_Y;
        private static final long serialVersionUID = 0;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 5)
        public final Float cornerRadius;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)
        public final Float height;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
        public final Float width;

        /* renamed from: x, reason: collision with root package name */
        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
        public final Float f10695x;

        /* renamed from: y, reason: collision with root package name */
        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
        public final Float f10696y;

        public static final class Builder extends Message.Builder<RectArgs, Builder> {
            public Float cornerRadius;
            public Float height;
            public Float width;

            /* renamed from: x, reason: collision with root package name */
            public Float f10697x;

            /* renamed from: y, reason: collision with root package name */
            public Float f10698y;

            public Builder cornerRadius(Float f2) {
                this.cornerRadius = f2;
                return this;
            }

            public Builder height(Float f2) {
                this.height = f2;
                return this;
            }

            public Builder width(Float f2) {
                this.width = f2;
                return this;
            }

            public Builder x(Float f2) {
                this.f10697x = f2;
                return this;
            }

            public Builder y(Float f2) {
                this.f10698y = f2;
                return this;
            }

            @Override // com.squareup.wire.Message.Builder
            public RectArgs build() {
                return new RectArgs(this.f10697x, this.f10698y, this.width, this.height, this.cornerRadius, super.buildUnknownFields());
            }
        }

        public static final class ProtoAdapter_RectArgs extends ProtoAdapter<RectArgs> {
            public ProtoAdapter_RectArgs() {
                super(FieldEncoding.LENGTH_DELIMITED, RectArgs.class);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.squareup.wire.ProtoAdapter
            public RectArgs decode(ProtoReader protoReader) throws IOException {
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
                    } else if (iNextTag == 4) {
                        builder.height(ProtoAdapter.FLOAT.decode(protoReader));
                    } else if (iNextTag != 5) {
                        FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                        builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                    } else {
                        builder.cornerRadius(ProtoAdapter.FLOAT.decode(protoReader));
                    }
                }
            }

            @Override // com.squareup.wire.ProtoAdapter
            public void encode(ProtoWriter protoWriter, RectArgs rectArgs) throws IOException {
                Float f2 = rectArgs.f10695x;
                if (f2 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
                }
                Float f3 = rectArgs.f10696y;
                if (f3 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
                }
                Float f4 = rectArgs.width;
                if (f4 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f4);
                }
                Float f5 = rectArgs.height;
                if (f5 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f5);
                }
                Float f6 = rectArgs.cornerRadius;
                if (f6 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 5, f6);
                }
                protoWriter.writeBytes(rectArgs.unknownFields());
            }

            @Override // com.squareup.wire.ProtoAdapter
            public int encodedSize(RectArgs rectArgs) {
                Float f2 = rectArgs.f10695x;
                int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
                Float f3 = rectArgs.f10696y;
                int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
                Float f4 = rectArgs.width;
                int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f4) : 0);
                Float f5 = rectArgs.height;
                int iEncodedSizeWithTag4 = iEncodedSizeWithTag3 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f5) : 0);
                Float f6 = rectArgs.cornerRadius;
                return iEncodedSizeWithTag4 + (f6 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(5, f6) : 0) + rectArgs.unknownFields().size();
            }

            @Override // com.squareup.wire.ProtoAdapter
            public RectArgs redact(RectArgs rectArgs) {
                Builder builderNewBuilder = rectArgs.newBuilder();
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
            DEFAULT_CORNERRADIUS = fValueOf;
        }

        public RectArgs(Float f2, Float f3, Float f4, Float f5, Float f6) {
            this(f2, f3, f4, f5, f6, ByteString.EMPTY);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RectArgs)) {
                return false;
            }
            RectArgs rectArgs = (RectArgs) obj;
            return unknownFields().equals(rectArgs.unknownFields()) && Internal.equals(this.f10695x, rectArgs.f10695x) && Internal.equals(this.f10696y, rectArgs.f10696y) && Internal.equals(this.width, rectArgs.width) && Internal.equals(this.height, rectArgs.height) && Internal.equals(this.cornerRadius, rectArgs.cornerRadius);
        }

        public int hashCode() {
            int i2 = this.hashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = unknownFields().hashCode() * 37;
            Float f2 = this.f10695x;
            int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
            Float f3 = this.f10696y;
            int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
            Float f4 = this.width;
            int iHashCode4 = (iHashCode3 + (f4 != null ? f4.hashCode() : 0)) * 37;
            Float f5 = this.height;
            int iHashCode5 = (iHashCode4 + (f5 != null ? f5.hashCode() : 0)) * 37;
            Float f6 = this.cornerRadius;
            int iHashCode6 = iHashCode5 + (f6 != null ? f6.hashCode() : 0);
            this.hashCode = iHashCode6;
            return iHashCode6;
        }

        @Override // com.squareup.wire.Message
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.f10695x != null) {
                sb.append(", x=");
                sb.append(this.f10695x);
            }
            if (this.f10696y != null) {
                sb.append(", y=");
                sb.append(this.f10696y);
            }
            if (this.width != null) {
                sb.append(", width=");
                sb.append(this.width);
            }
            if (this.height != null) {
                sb.append(", height=");
                sb.append(this.height);
            }
            if (this.cornerRadius != null) {
                sb.append(", cornerRadius=");
                sb.append(this.cornerRadius);
            }
            StringBuilder sbReplace = sb.replace(0, 2, "RectArgs{");
            sbReplace.append('}');
            return sbReplace.toString();
        }

        public RectArgs(Float f2, Float f3, Float f4, Float f5, Float f6, ByteString byteString) {
            super(ADAPTER, byteString);
            this.f10695x = f2;
            this.f10696y = f3;
            this.width = f4;
            this.height = f5;
            this.cornerRadius = f6;
        }

        @Override // com.squareup.wire.Message
        public Builder newBuilder() {
            Builder builder = new Builder();
            builder.f10697x = this.f10695x;
            builder.f10698y = this.f10696y;
            builder.width = this.width;
            builder.height = this.height;
            builder.cornerRadius = this.cornerRadius;
            builder.addUnknownFields(unknownFields());
            return builder;
        }
    }

    public static final class ShapeArgs extends Message<ShapeArgs, Builder> {
        public static final ProtoAdapter<ShapeArgs> ADAPTER = new ProtoAdapter_ShapeArgs();
        public static final String DEFAULT_D = "";
        private static final long serialVersionUID = 0;

        /* renamed from: d, reason: collision with root package name */
        @WireField(adapter = "com.squareup.wire.ProtoAdapter#STRING", tag = 1)
        public final String f10699d;

        public static final class Builder extends Message.Builder<ShapeArgs, Builder> {

            /* renamed from: d, reason: collision with root package name */
            public String f10700d;

            public Builder d(String str) {
                this.f10700d = str;
                return this;
            }

            @Override // com.squareup.wire.Message.Builder
            public ShapeArgs build() {
                return new ShapeArgs(this.f10700d, super.buildUnknownFields());
            }
        }

        public static final class ProtoAdapter_ShapeArgs extends ProtoAdapter<ShapeArgs> {
            public ProtoAdapter_ShapeArgs() {
                super(FieldEncoding.LENGTH_DELIMITED, ShapeArgs.class);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.squareup.wire.ProtoAdapter
            public ShapeArgs decode(ProtoReader protoReader) throws IOException {
                Builder builder = new Builder();
                long jBeginMessage = protoReader.beginMessage();
                while (true) {
                    int iNextTag = protoReader.nextTag();
                    if (iNextTag == -1) {
                        protoReader.endMessage(jBeginMessage);
                        return builder.build();
                    }
                    if (iNextTag != 1) {
                        FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                        builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                    } else {
                        builder.d(ProtoAdapter.STRING.decode(protoReader));
                    }
                }
            }

            @Override // com.squareup.wire.ProtoAdapter
            public void encode(ProtoWriter protoWriter, ShapeArgs shapeArgs) throws IOException {
                String str = shapeArgs.f10699d;
                if (str != null) {
                    ProtoAdapter.STRING.encodeWithTag(protoWriter, 1, str);
                }
                protoWriter.writeBytes(shapeArgs.unknownFields());
            }

            @Override // com.squareup.wire.ProtoAdapter
            public int encodedSize(ShapeArgs shapeArgs) {
                String str = shapeArgs.f10699d;
                return (str != null ? ProtoAdapter.STRING.encodedSizeWithTag(1, str) : 0) + shapeArgs.unknownFields().size();
            }

            @Override // com.squareup.wire.ProtoAdapter
            public ShapeArgs redact(ShapeArgs shapeArgs) {
                Builder builderNewBuilder = shapeArgs.newBuilder();
                builderNewBuilder.clearUnknownFields();
                return builderNewBuilder.build();
            }
        }

        public ShapeArgs(String str) {
            this(str, ByteString.EMPTY);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ShapeArgs)) {
                return false;
            }
            ShapeArgs shapeArgs = (ShapeArgs) obj;
            return unknownFields().equals(shapeArgs.unknownFields()) && Internal.equals(this.f10699d, shapeArgs.f10699d);
        }

        public int hashCode() {
            int i2 = this.hashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = unknownFields().hashCode() * 37;
            String str = this.f10699d;
            int iHashCode2 = iHashCode + (str != null ? str.hashCode() : 0);
            this.hashCode = iHashCode2;
            return iHashCode2;
        }

        @Override // com.squareup.wire.Message
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.f10699d != null) {
                sb.append(", d=");
                sb.append(this.f10699d);
            }
            StringBuilder sbReplace = sb.replace(0, 2, "ShapeArgs{");
            sbReplace.append('}');
            return sbReplace.toString();
        }

        public ShapeArgs(String str, ByteString byteString) {
            super(ADAPTER, byteString);
            this.f10699d = str;
        }

        @Override // com.squareup.wire.Message
        public Builder newBuilder() {
            Builder builder = new Builder();
            builder.f10700d = this.f10699d;
            builder.addUnknownFields(unknownFields());
            return builder;
        }
    }

    public static final class ShapeStyle extends Message<ShapeStyle, Builder> {
        public static final ProtoAdapter<ShapeStyle> ADAPTER = new ProtoAdapter_ShapeStyle();
        public static final LineCap DEFAULT_LINECAP;
        public static final Float DEFAULT_LINEDASHI;
        public static final Float DEFAULT_LINEDASHII;
        public static final Float DEFAULT_LINEDASHIII;
        public static final LineJoin DEFAULT_LINEJOIN;
        public static final Float DEFAULT_MITERLIMIT;
        public static final Float DEFAULT_STROKEWIDTH;
        private static final long serialVersionUID = 0;

        @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeStyle$RGBAColor#ADAPTER", tag = 1)
        public final RGBAColor fill;

        @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeStyle$LineCap#ADAPTER", tag = 4)
        public final LineCap lineCap;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 7)
        public final Float lineDashI;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 8)
        public final Float lineDashII;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 9)
        public final Float lineDashIII;

        @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeStyle$LineJoin#ADAPTER", tag = 5)
        public final LineJoin lineJoin;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 6)
        public final Float miterLimit;

        @WireField(adapter = "com.opensource.svgaplayer.proto.ShapeEntity$ShapeStyle$RGBAColor#ADAPTER", tag = 2)
        public final RGBAColor stroke;

        @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
        public final Float strokeWidth;

        public static final class Builder extends Message.Builder<ShapeStyle, Builder> {
            public RGBAColor fill;
            public LineCap lineCap;
            public Float lineDashI;
            public Float lineDashII;
            public Float lineDashIII;
            public LineJoin lineJoin;
            public Float miterLimit;
            public RGBAColor stroke;
            public Float strokeWidth;

            public Builder fill(RGBAColor rGBAColor) {
                this.fill = rGBAColor;
                return this;
            }

            public Builder lineCap(LineCap lineCap) {
                this.lineCap = lineCap;
                return this;
            }

            public Builder lineDashI(Float f2) {
                this.lineDashI = f2;
                return this;
            }

            public Builder lineDashII(Float f2) {
                this.lineDashII = f2;
                return this;
            }

            public Builder lineDashIII(Float f2) {
                this.lineDashIII = f2;
                return this;
            }

            public Builder lineJoin(LineJoin lineJoin) {
                this.lineJoin = lineJoin;
                return this;
            }

            public Builder miterLimit(Float f2) {
                this.miterLimit = f2;
                return this;
            }

            public Builder stroke(RGBAColor rGBAColor) {
                this.stroke = rGBAColor;
                return this;
            }

            public Builder strokeWidth(Float f2) {
                this.strokeWidth = f2;
                return this;
            }

            @Override // com.squareup.wire.Message.Builder
            public ShapeStyle build() {
                return new ShapeStyle(this.fill, this.stroke, this.strokeWidth, this.lineCap, this.lineJoin, this.miterLimit, this.lineDashI, this.lineDashII, this.lineDashIII, super.buildUnknownFields());
            }
        }

        public enum LineCap implements WireEnum {
            LineCap_BUTT(0),
            LineCap_ROUND(1),
            LineCap_SQUARE(2);

            public static final ProtoAdapter<LineCap> ADAPTER = ProtoAdapter.newEnumAdapter(LineCap.class);
            private final int value;

            LineCap(int i2) {
                this.value = i2;
            }

            public static LineCap fromValue(int i2) {
                if (i2 == 0) {
                    return LineCap_BUTT;
                }
                if (i2 == 1) {
                    return LineCap_ROUND;
                }
                if (i2 != 2) {
                    return null;
                }
                return LineCap_SQUARE;
            }

            @Override // com.squareup.wire.WireEnum
            public int getValue() {
                return this.value;
            }
        }

        public enum LineJoin implements WireEnum {
            LineJoin_MITER(0),
            LineJoin_ROUND(1),
            LineJoin_BEVEL(2);

            public static final ProtoAdapter<LineJoin> ADAPTER = ProtoAdapter.newEnumAdapter(LineJoin.class);
            private final int value;

            LineJoin(int i2) {
                this.value = i2;
            }

            public static LineJoin fromValue(int i2) {
                if (i2 == 0) {
                    return LineJoin_MITER;
                }
                if (i2 == 1) {
                    return LineJoin_ROUND;
                }
                if (i2 != 2) {
                    return null;
                }
                return LineJoin_BEVEL;
            }

            @Override // com.squareup.wire.WireEnum
            public int getValue() {
                return this.value;
            }
        }

        public static final class ProtoAdapter_ShapeStyle extends ProtoAdapter<ShapeStyle> {
            public ProtoAdapter_ShapeStyle() {
                super(FieldEncoding.LENGTH_DELIMITED, ShapeStyle.class);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.squareup.wire.ProtoAdapter
            public ShapeStyle decode(ProtoReader protoReader) throws IOException {
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
                            builder.fill(RGBAColor.ADAPTER.decode(protoReader));
                            break;
                        case 2:
                            builder.stroke(RGBAColor.ADAPTER.decode(protoReader));
                            break;
                        case 3:
                            builder.strokeWidth(ProtoAdapter.FLOAT.decode(protoReader));
                            break;
                        case 4:
                            try {
                                builder.lineCap(LineCap.ADAPTER.decode(protoReader));
                                break;
                            } catch (ProtoAdapter.EnumConstantNotFoundException e2) {
                                builder.addUnknownField(iNextTag, FieldEncoding.VARINT, Long.valueOf(e2.value));
                                break;
                            }
                        case 5:
                            try {
                                builder.lineJoin(LineJoin.ADAPTER.decode(protoReader));
                                break;
                            } catch (ProtoAdapter.EnumConstantNotFoundException e3) {
                                builder.addUnknownField(iNextTag, FieldEncoding.VARINT, Long.valueOf(e3.value));
                                break;
                            }
                        case 6:
                            builder.miterLimit(ProtoAdapter.FLOAT.decode(protoReader));
                            break;
                        case 7:
                            builder.lineDashI(ProtoAdapter.FLOAT.decode(protoReader));
                            break;
                        case 8:
                            builder.lineDashII(ProtoAdapter.FLOAT.decode(protoReader));
                            break;
                        case 9:
                            builder.lineDashIII(ProtoAdapter.FLOAT.decode(protoReader));
                            break;
                        default:
                            FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                            builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                            break;
                    }
                }
            }

            @Override // com.squareup.wire.ProtoAdapter
            public void encode(ProtoWriter protoWriter, ShapeStyle shapeStyle) throws IOException {
                RGBAColor rGBAColor = shapeStyle.fill;
                if (rGBAColor != null) {
                    RGBAColor.ADAPTER.encodeWithTag(protoWriter, 1, rGBAColor);
                }
                RGBAColor rGBAColor2 = shapeStyle.stroke;
                if (rGBAColor2 != null) {
                    RGBAColor.ADAPTER.encodeWithTag(protoWriter, 2, rGBAColor2);
                }
                Float f2 = shapeStyle.strokeWidth;
                if (f2 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f2);
                }
                LineCap lineCap = shapeStyle.lineCap;
                if (lineCap != null) {
                    LineCap.ADAPTER.encodeWithTag(protoWriter, 4, lineCap);
                }
                LineJoin lineJoin = shapeStyle.lineJoin;
                if (lineJoin != null) {
                    LineJoin.ADAPTER.encodeWithTag(protoWriter, 5, lineJoin);
                }
                Float f3 = shapeStyle.miterLimit;
                if (f3 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 6, f3);
                }
                Float f4 = shapeStyle.lineDashI;
                if (f4 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 7, f4);
                }
                Float f5 = shapeStyle.lineDashII;
                if (f5 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 8, f5);
                }
                Float f6 = shapeStyle.lineDashIII;
                if (f6 != null) {
                    ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 9, f6);
                }
                protoWriter.writeBytes(shapeStyle.unknownFields());
            }

            @Override // com.squareup.wire.ProtoAdapter
            public int encodedSize(ShapeStyle shapeStyle) {
                RGBAColor rGBAColor = shapeStyle.fill;
                int iEncodedSizeWithTag = rGBAColor != null ? RGBAColor.ADAPTER.encodedSizeWithTag(1, rGBAColor) : 0;
                RGBAColor rGBAColor2 = shapeStyle.stroke;
                int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (rGBAColor2 != null ? RGBAColor.ADAPTER.encodedSizeWithTag(2, rGBAColor2) : 0);
                Float f2 = shapeStyle.strokeWidth;
                int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f2) : 0);
                LineCap lineCap = shapeStyle.lineCap;
                int iEncodedSizeWithTag4 = iEncodedSizeWithTag3 + (lineCap != null ? LineCap.ADAPTER.encodedSizeWithTag(4, lineCap) : 0);
                LineJoin lineJoin = shapeStyle.lineJoin;
                int iEncodedSizeWithTag5 = iEncodedSizeWithTag4 + (lineJoin != null ? LineJoin.ADAPTER.encodedSizeWithTag(5, lineJoin) : 0);
                Float f3 = shapeStyle.miterLimit;
                int iEncodedSizeWithTag6 = iEncodedSizeWithTag5 + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(6, f3) : 0);
                Float f4 = shapeStyle.lineDashI;
                int iEncodedSizeWithTag7 = iEncodedSizeWithTag6 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(7, f4) : 0);
                Float f5 = shapeStyle.lineDashII;
                int iEncodedSizeWithTag8 = iEncodedSizeWithTag7 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(8, f5) : 0);
                Float f6 = shapeStyle.lineDashIII;
                return iEncodedSizeWithTag8 + (f6 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(9, f6) : 0) + shapeStyle.unknownFields().size();
            }

            @Override // com.squareup.wire.ProtoAdapter
            public ShapeStyle redact(ShapeStyle shapeStyle) {
                Builder builderNewBuilder = shapeStyle.newBuilder();
                RGBAColor rGBAColor = builderNewBuilder.fill;
                if (rGBAColor != null) {
                    builderNewBuilder.fill = RGBAColor.ADAPTER.redact(rGBAColor);
                }
                RGBAColor rGBAColor2 = builderNewBuilder.stroke;
                if (rGBAColor2 != null) {
                    builderNewBuilder.stroke = RGBAColor.ADAPTER.redact(rGBAColor2);
                }
                builderNewBuilder.clearUnknownFields();
                return builderNewBuilder.build();
            }
        }

        public static final class RGBAColor extends Message<RGBAColor, Builder> {
            public static final ProtoAdapter<RGBAColor> ADAPTER = new ProtoAdapter_RGBAColor();
            public static final Float DEFAULT_A;
            public static final Float DEFAULT_B;
            public static final Float DEFAULT_G;
            public static final Float DEFAULT_R;
            private static final long serialVersionUID = 0;

            /* renamed from: a, reason: collision with root package name */
            @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)
            public final Float f10701a;

            /* renamed from: b, reason: collision with root package name */
            @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)
            public final Float f10702b;

            /* renamed from: g, reason: collision with root package name */
            @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
            public final Float f10703g;

            /* renamed from: r, reason: collision with root package name */
            @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
            public final Float f10704r;

            public static final class Builder extends Message.Builder<RGBAColor, Builder> {

                /* renamed from: a, reason: collision with root package name */
                public Float f10705a;

                /* renamed from: b, reason: collision with root package name */
                public Float f10706b;

                /* renamed from: g, reason: collision with root package name */
                public Float f10707g;

                /* renamed from: r, reason: collision with root package name */
                public Float f10708r;

                public Builder a(Float f2) {
                    this.f10705a = f2;
                    return this;
                }

                public Builder b(Float f2) {
                    this.f10706b = f2;
                    return this;
                }

                public Builder g(Float f2) {
                    this.f10707g = f2;
                    return this;
                }

                public Builder r(Float f2) {
                    this.f10708r = f2;
                    return this;
                }

                @Override // com.squareup.wire.Message.Builder
                public RGBAColor build() {
                    return new RGBAColor(this.f10708r, this.f10707g, this.f10706b, this.f10705a, super.buildUnknownFields());
                }
            }

            public static final class ProtoAdapter_RGBAColor extends ProtoAdapter<RGBAColor> {
                public ProtoAdapter_RGBAColor() {
                    super(FieldEncoding.LENGTH_DELIMITED, RGBAColor.class);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.squareup.wire.ProtoAdapter
                public RGBAColor decode(ProtoReader protoReader) throws IOException {
                    Builder builder = new Builder();
                    long jBeginMessage = protoReader.beginMessage();
                    while (true) {
                        int iNextTag = protoReader.nextTag();
                        if (iNextTag == -1) {
                            protoReader.endMessage(jBeginMessage);
                            return builder.build();
                        }
                        if (iNextTag == 1) {
                            builder.r(ProtoAdapter.FLOAT.decode(protoReader));
                        } else if (iNextTag == 2) {
                            builder.g(ProtoAdapter.FLOAT.decode(protoReader));
                        } else if (iNextTag == 3) {
                            builder.b(ProtoAdapter.FLOAT.decode(protoReader));
                        } else if (iNextTag != 4) {
                            FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                            builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                        } else {
                            builder.a(ProtoAdapter.FLOAT.decode(protoReader));
                        }
                    }
                }

                @Override // com.squareup.wire.ProtoAdapter
                public void encode(ProtoWriter protoWriter, RGBAColor rGBAColor) throws IOException {
                    Float f2 = rGBAColor.f10704r;
                    if (f2 != null) {
                        ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
                    }
                    Float f3 = rGBAColor.f10703g;
                    if (f3 != null) {
                        ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
                    }
                    Float f4 = rGBAColor.f10702b;
                    if (f4 != null) {
                        ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f4);
                    }
                    Float f5 = rGBAColor.f10701a;
                    if (f5 != null) {
                        ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f5);
                    }
                    protoWriter.writeBytes(rGBAColor.unknownFields());
                }

                @Override // com.squareup.wire.ProtoAdapter
                public int encodedSize(RGBAColor rGBAColor) {
                    Float f2 = rGBAColor.f10704r;
                    int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
                    Float f3 = rGBAColor.f10703g;
                    int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
                    Float f4 = rGBAColor.f10702b;
                    int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f4) : 0);
                    Float f5 = rGBAColor.f10701a;
                    return iEncodedSizeWithTag3 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f5) : 0) + rGBAColor.unknownFields().size();
                }

                @Override // com.squareup.wire.ProtoAdapter
                public RGBAColor redact(RGBAColor rGBAColor) {
                    Builder builderNewBuilder = rGBAColor.newBuilder();
                    builderNewBuilder.clearUnknownFields();
                    return builderNewBuilder.build();
                }
            }

            static {
                Float fValueOf = Float.valueOf(0.0f);
                DEFAULT_R = fValueOf;
                DEFAULT_G = fValueOf;
                DEFAULT_B = fValueOf;
                DEFAULT_A = fValueOf;
            }

            public RGBAColor(Float f2, Float f3, Float f4, Float f5) {
                this(f2, f3, f4, f5, ByteString.EMPTY);
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof RGBAColor)) {
                    return false;
                }
                RGBAColor rGBAColor = (RGBAColor) obj;
                return unknownFields().equals(rGBAColor.unknownFields()) && Internal.equals(this.f10704r, rGBAColor.f10704r) && Internal.equals(this.f10703g, rGBAColor.f10703g) && Internal.equals(this.f10702b, rGBAColor.f10702b) && Internal.equals(this.f10701a, rGBAColor.f10701a);
            }

            public int hashCode() {
                int i2 = this.hashCode;
                if (i2 != 0) {
                    return i2;
                }
                int iHashCode = unknownFields().hashCode() * 37;
                Float f2 = this.f10704r;
                int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
                Float f3 = this.f10703g;
                int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
                Float f4 = this.f10702b;
                int iHashCode4 = (iHashCode3 + (f4 != null ? f4.hashCode() : 0)) * 37;
                Float f5 = this.f10701a;
                int iHashCode5 = iHashCode4 + (f5 != null ? f5.hashCode() : 0);
                this.hashCode = iHashCode5;
                return iHashCode5;
            }

            @Override // com.squareup.wire.Message
            public String toString() {
                StringBuilder sb = new StringBuilder();
                if (this.f10704r != null) {
                    sb.append(", r=");
                    sb.append(this.f10704r);
                }
                if (this.f10703g != null) {
                    sb.append(", g=");
                    sb.append(this.f10703g);
                }
                if (this.f10702b != null) {
                    sb.append(", b=");
                    sb.append(this.f10702b);
                }
                if (this.f10701a != null) {
                    sb.append(", a=");
                    sb.append(this.f10701a);
                }
                StringBuilder sbReplace = sb.replace(0, 2, "RGBAColor{");
                sbReplace.append('}');
                return sbReplace.toString();
            }

            public RGBAColor(Float f2, Float f3, Float f4, Float f5, ByteString byteString) {
                super(ADAPTER, byteString);
                this.f10704r = f2;
                this.f10703g = f3;
                this.f10702b = f4;
                this.f10701a = f5;
            }

            @Override // com.squareup.wire.Message
            public Builder newBuilder() {
                Builder builder = new Builder();
                builder.f10708r = this.f10704r;
                builder.f10707g = this.f10703g;
                builder.f10706b = this.f10702b;
                builder.f10705a = this.f10701a;
                builder.addUnknownFields(unknownFields());
                return builder;
            }
        }

        static {
            Float fValueOf = Float.valueOf(0.0f);
            DEFAULT_STROKEWIDTH = fValueOf;
            DEFAULT_LINECAP = LineCap.LineCap_BUTT;
            DEFAULT_LINEJOIN = LineJoin.LineJoin_MITER;
            DEFAULT_MITERLIMIT = fValueOf;
            DEFAULT_LINEDASHI = fValueOf;
            DEFAULT_LINEDASHII = fValueOf;
            DEFAULT_LINEDASHIII = fValueOf;
        }

        public ShapeStyle(RGBAColor rGBAColor, RGBAColor rGBAColor2, Float f2, LineCap lineCap, LineJoin lineJoin, Float f3, Float f4, Float f5, Float f6) {
            this(rGBAColor, rGBAColor2, f2, lineCap, lineJoin, f3, f4, f5, f6, ByteString.EMPTY);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ShapeStyle)) {
                return false;
            }
            ShapeStyle shapeStyle = (ShapeStyle) obj;
            return unknownFields().equals(shapeStyle.unknownFields()) && Internal.equals(this.fill, shapeStyle.fill) && Internal.equals(this.stroke, shapeStyle.stroke) && Internal.equals(this.strokeWidth, shapeStyle.strokeWidth) && Internal.equals(this.lineCap, shapeStyle.lineCap) && Internal.equals(this.lineJoin, shapeStyle.lineJoin) && Internal.equals(this.miterLimit, shapeStyle.miterLimit) && Internal.equals(this.lineDashI, shapeStyle.lineDashI) && Internal.equals(this.lineDashII, shapeStyle.lineDashII) && Internal.equals(this.lineDashIII, shapeStyle.lineDashIII);
        }

        public int hashCode() {
            int i2 = this.hashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = unknownFields().hashCode() * 37;
            RGBAColor rGBAColor = this.fill;
            int iHashCode2 = (iHashCode + (rGBAColor != null ? rGBAColor.hashCode() : 0)) * 37;
            RGBAColor rGBAColor2 = this.stroke;
            int iHashCode3 = (iHashCode2 + (rGBAColor2 != null ? rGBAColor2.hashCode() : 0)) * 37;
            Float f2 = this.strokeWidth;
            int iHashCode4 = (iHashCode3 + (f2 != null ? f2.hashCode() : 0)) * 37;
            LineCap lineCap = this.lineCap;
            int iHashCode5 = (iHashCode4 + (lineCap != null ? lineCap.hashCode() : 0)) * 37;
            LineJoin lineJoin = this.lineJoin;
            int iHashCode6 = (iHashCode5 + (lineJoin != null ? lineJoin.hashCode() : 0)) * 37;
            Float f3 = this.miterLimit;
            int iHashCode7 = (iHashCode6 + (f3 != null ? f3.hashCode() : 0)) * 37;
            Float f4 = this.lineDashI;
            int iHashCode8 = (iHashCode7 + (f4 != null ? f4.hashCode() : 0)) * 37;
            Float f5 = this.lineDashII;
            int iHashCode9 = (iHashCode8 + (f5 != null ? f5.hashCode() : 0)) * 37;
            Float f6 = this.lineDashIII;
            int iHashCode10 = iHashCode9 + (f6 != null ? f6.hashCode() : 0);
            this.hashCode = iHashCode10;
            return iHashCode10;
        }

        @Override // com.squareup.wire.Message
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.fill != null) {
                sb.append(", fill=");
                sb.append(this.fill);
            }
            if (this.stroke != null) {
                sb.append(", stroke=");
                sb.append(this.stroke);
            }
            if (this.strokeWidth != null) {
                sb.append(", strokeWidth=");
                sb.append(this.strokeWidth);
            }
            if (this.lineCap != null) {
                sb.append(", lineCap=");
                sb.append(this.lineCap);
            }
            if (this.lineJoin != null) {
                sb.append(", lineJoin=");
                sb.append(this.lineJoin);
            }
            if (this.miterLimit != null) {
                sb.append(", miterLimit=");
                sb.append(this.miterLimit);
            }
            if (this.lineDashI != null) {
                sb.append(", lineDashI=");
                sb.append(this.lineDashI);
            }
            if (this.lineDashII != null) {
                sb.append(", lineDashII=");
                sb.append(this.lineDashII);
            }
            if (this.lineDashIII != null) {
                sb.append(", lineDashIII=");
                sb.append(this.lineDashIII);
            }
            StringBuilder sbReplace = sb.replace(0, 2, "ShapeStyle{");
            sbReplace.append('}');
            return sbReplace.toString();
        }

        public ShapeStyle(RGBAColor rGBAColor, RGBAColor rGBAColor2, Float f2, LineCap lineCap, LineJoin lineJoin, Float f3, Float f4, Float f5, Float f6, ByteString byteString) {
            super(ADAPTER, byteString);
            this.fill = rGBAColor;
            this.stroke = rGBAColor2;
            this.strokeWidth = f2;
            this.lineCap = lineCap;
            this.lineJoin = lineJoin;
            this.miterLimit = f3;
            this.lineDashI = f4;
            this.lineDashII = f5;
            this.lineDashIII = f6;
        }

        @Override // com.squareup.wire.Message
        public Builder newBuilder() {
            Builder builder = new Builder();
            builder.fill = this.fill;
            builder.stroke = this.stroke;
            builder.strokeWidth = this.strokeWidth;
            builder.lineCap = this.lineCap;
            builder.lineJoin = this.lineJoin;
            builder.miterLimit = this.miterLimit;
            builder.lineDashI = this.lineDashI;
            builder.lineDashII = this.lineDashII;
            builder.lineDashIII = this.lineDashIII;
            builder.addUnknownFields(unknownFields());
            return builder;
        }
    }

    public enum ShapeType implements WireEnum {
        SHAPE(0),
        RECT(1),
        ELLIPSE(2),
        KEEP(3);

        public static final ProtoAdapter<ShapeType> ADAPTER = ProtoAdapter.newEnumAdapter(ShapeType.class);
        private final int value;

        ShapeType(int i2) {
            this.value = i2;
        }

        public static ShapeType fromValue(int i2) {
            if (i2 == 0) {
                return SHAPE;
            }
            if (i2 == 1) {
                return RECT;
            }
            if (i2 == 2) {
                return ELLIPSE;
            }
            if (i2 != 3) {
                return null;
            }
            return KEEP;
        }

        @Override // com.squareup.wire.WireEnum
        public int getValue() {
            return this.value;
        }
    }

    public ShapeEntity(ShapeType shapeType, ShapeStyle shapeStyle, Transform transform, ShapeArgs shapeArgs, RectArgs rectArgs, EllipseArgs ellipseArgs) {
        this(shapeType, shapeStyle, transform, shapeArgs, rectArgs, ellipseArgs, ByteString.EMPTY);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShapeEntity)) {
            return false;
        }
        ShapeEntity shapeEntity = (ShapeEntity) obj;
        return unknownFields().equals(shapeEntity.unknownFields()) && Internal.equals(this.type, shapeEntity.type) && Internal.equals(this.styles, shapeEntity.styles) && Internal.equals(this.transform, shapeEntity.transform) && Internal.equals(this.shape, shapeEntity.shape) && Internal.equals(this.rect, shapeEntity.rect) && Internal.equals(this.ellipse, shapeEntity.ellipse);
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = unknownFields().hashCode() * 37;
        ShapeType shapeType = this.type;
        int iHashCode2 = (iHashCode + (shapeType != null ? shapeType.hashCode() : 0)) * 37;
        ShapeStyle shapeStyle = this.styles;
        int iHashCode3 = (iHashCode2 + (shapeStyle != null ? shapeStyle.hashCode() : 0)) * 37;
        Transform transform = this.transform;
        int iHashCode4 = (iHashCode3 + (transform != null ? transform.hashCode() : 0)) * 37;
        ShapeArgs shapeArgs = this.shape;
        int iHashCode5 = (iHashCode4 + (shapeArgs != null ? shapeArgs.hashCode() : 0)) * 37;
        RectArgs rectArgs = this.rect;
        int iHashCode6 = (iHashCode5 + (rectArgs != null ? rectArgs.hashCode() : 0)) * 37;
        EllipseArgs ellipseArgs = this.ellipse;
        int iHashCode7 = iHashCode6 + (ellipseArgs != null ? ellipseArgs.hashCode() : 0);
        this.hashCode = iHashCode7;
        return iHashCode7;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.type != null) {
            sb.append(", type=");
            sb.append(this.type);
        }
        if (this.styles != null) {
            sb.append(", styles=");
            sb.append(this.styles);
        }
        if (this.transform != null) {
            sb.append(", transform=");
            sb.append(this.transform);
        }
        if (this.shape != null) {
            sb.append(", shape=");
            sb.append(this.shape);
        }
        if (this.rect != null) {
            sb.append(", rect=");
            sb.append(this.rect);
        }
        if (this.ellipse != null) {
            sb.append(", ellipse=");
            sb.append(this.ellipse);
        }
        StringBuilder sbReplace = sb.replace(0, 2, "ShapeEntity{");
        sbReplace.append('}');
        return sbReplace.toString();
    }

    public ShapeEntity(ShapeType shapeType, ShapeStyle shapeStyle, Transform transform, ShapeArgs shapeArgs, RectArgs rectArgs, EllipseArgs ellipseArgs, ByteString byteString) {
        super(ADAPTER, byteString);
        if (Internal.countNonNull(shapeArgs, rectArgs, ellipseArgs) > 1) {
            throw new IllegalArgumentException("at most one of shape, rect, ellipse may be non-null");
        }
        this.type = shapeType;
        this.styles = shapeStyle;
        this.transform = transform;
        this.shape = shapeArgs;
        this.rect = rectArgs;
        this.ellipse = ellipseArgs;
    }

    @Override // com.squareup.wire.Message
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.type = this.type;
        builder.styles = this.styles;
        builder.transform = this.transform;
        builder.shape = this.shape;
        builder.rect = this.rect;
        builder.ellipse = this.ellipse;
        builder.addUnknownFields(unknownFields());
        return builder;
    }
}
