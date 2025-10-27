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
public final class MovieParams extends Message<MovieParams, Builder> {
    public static final ProtoAdapter<MovieParams> ADAPTER = new ProtoAdapter_MovieParams();
    public static final Integer DEFAULT_FPS;
    public static final Integer DEFAULT_FRAMES;
    public static final Float DEFAULT_VIEWBOXHEIGHT;
    public static final Float DEFAULT_VIEWBOXWIDTH;
    private static final long serialVersionUID = 0;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#INT32", tag = 3)
    public final Integer fps;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#INT32", tag = 4)
    public final Integer frames;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)
    public final Float viewBoxHeight;

    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)
    public final Float viewBoxWidth;

    public static final class Builder extends Message.Builder<MovieParams, Builder> {
        public Integer fps;
        public Integer frames;
        public Float viewBoxHeight;
        public Float viewBoxWidth;

        public Builder fps(Integer num) {
            this.fps = num;
            return this;
        }

        public Builder frames(Integer num) {
            this.frames = num;
            return this;
        }

        public Builder viewBoxHeight(Float f2) {
            this.viewBoxHeight = f2;
            return this;
        }

        public Builder viewBoxWidth(Float f2) {
            this.viewBoxWidth = f2;
            return this;
        }

        @Override // com.squareup.wire.Message.Builder
        public MovieParams build() {
            return new MovieParams(this.viewBoxWidth, this.viewBoxHeight, this.fps, this.frames, super.buildUnknownFields());
        }
    }

    public static final class ProtoAdapter_MovieParams extends ProtoAdapter<MovieParams> {
        public ProtoAdapter_MovieParams() {
            super(FieldEncoding.LENGTH_DELIMITED, MovieParams.class);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        public MovieParams decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long jBeginMessage = protoReader.beginMessage();
            while (true) {
                int iNextTag = protoReader.nextTag();
                if (iNextTag == -1) {
                    protoReader.endMessage(jBeginMessage);
                    return builder.build();
                }
                if (iNextTag == 1) {
                    builder.viewBoxWidth(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag == 2) {
                    builder.viewBoxHeight(ProtoAdapter.FLOAT.decode(protoReader));
                } else if (iNextTag == 3) {
                    builder.fps(ProtoAdapter.INT32.decode(protoReader));
                } else if (iNextTag != 4) {
                    FieldEncoding fieldEncodingPeekFieldEncoding = protoReader.peekFieldEncoding();
                    builder.addUnknownField(iNextTag, fieldEncodingPeekFieldEncoding, fieldEncodingPeekFieldEncoding.rawProtoAdapter().decode(protoReader));
                } else {
                    builder.frames(ProtoAdapter.INT32.decode(protoReader));
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, MovieParams movieParams) throws IOException {
            Float f2 = movieParams.viewBoxWidth;
            if (f2 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f2);
            }
            Float f3 = movieParams.viewBoxHeight;
            if (f3 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f3);
            }
            Integer num = movieParams.fps;
            if (num != null) {
                ProtoAdapter.INT32.encodeWithTag(protoWriter, 3, num);
            }
            Integer num2 = movieParams.frames;
            if (num2 != null) {
                ProtoAdapter.INT32.encodeWithTag(protoWriter, 4, num2);
            }
            protoWriter.writeBytes(movieParams.unknownFields());
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(MovieParams movieParams) {
            Float f2 = movieParams.viewBoxWidth;
            int iEncodedSizeWithTag = f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f2) : 0;
            Float f3 = movieParams.viewBoxHeight;
            int iEncodedSizeWithTag2 = iEncodedSizeWithTag + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f3) : 0);
            Integer num = movieParams.fps;
            int iEncodedSizeWithTag3 = iEncodedSizeWithTag2 + (num != null ? ProtoAdapter.INT32.encodedSizeWithTag(3, num) : 0);
            Integer num2 = movieParams.frames;
            return iEncodedSizeWithTag3 + (num2 != null ? ProtoAdapter.INT32.encodedSizeWithTag(4, num2) : 0) + movieParams.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public MovieParams redact(MovieParams movieParams) {
            Builder builderNewBuilder = movieParams.newBuilder();
            builderNewBuilder.clearUnknownFields();
            return builderNewBuilder.build();
        }
    }

    static {
        Float fValueOf = Float.valueOf(0.0f);
        DEFAULT_VIEWBOXWIDTH = fValueOf;
        DEFAULT_VIEWBOXHEIGHT = fValueOf;
        DEFAULT_FPS = 0;
        DEFAULT_FRAMES = 0;
    }

    public MovieParams(Float f2, Float f3, Integer num, Integer num2) {
        this(f2, f3, num, num2, ByteString.EMPTY);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MovieParams)) {
            return false;
        }
        MovieParams movieParams = (MovieParams) obj;
        return unknownFields().equals(movieParams.unknownFields()) && Internal.equals(this.viewBoxWidth, movieParams.viewBoxWidth) && Internal.equals(this.viewBoxHeight, movieParams.viewBoxHeight) && Internal.equals(this.fps, movieParams.fps) && Internal.equals(this.frames, movieParams.frames);
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = unknownFields().hashCode() * 37;
        Float f2 = this.viewBoxWidth;
        int iHashCode2 = (iHashCode + (f2 != null ? f2.hashCode() : 0)) * 37;
        Float f3 = this.viewBoxHeight;
        int iHashCode3 = (iHashCode2 + (f3 != null ? f3.hashCode() : 0)) * 37;
        Integer num = this.fps;
        int iHashCode4 = (iHashCode3 + (num != null ? num.hashCode() : 0)) * 37;
        Integer num2 = this.frames;
        int iHashCode5 = iHashCode4 + (num2 != null ? num2.hashCode() : 0);
        this.hashCode = iHashCode5;
        return iHashCode5;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.viewBoxWidth != null) {
            sb.append(", viewBoxWidth=");
            sb.append(this.viewBoxWidth);
        }
        if (this.viewBoxHeight != null) {
            sb.append(", viewBoxHeight=");
            sb.append(this.viewBoxHeight);
        }
        if (this.fps != null) {
            sb.append(", fps=");
            sb.append(this.fps);
        }
        if (this.frames != null) {
            sb.append(", frames=");
            sb.append(this.frames);
        }
        StringBuilder sbReplace = sb.replace(0, 2, "MovieParams{");
        sbReplace.append('}');
        return sbReplace.toString();
    }

    public MovieParams(Float f2, Float f3, Integer num, Integer num2, ByteString byteString) {
        super(ADAPTER, byteString);
        this.viewBoxWidth = f2;
        this.viewBoxHeight = f3;
        this.fps = num;
        this.frames = num2;
    }

    @Override // com.squareup.wire.Message
    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.viewBoxWidth = this.viewBoxWidth;
        builder.viewBoxHeight = this.viewBoxHeight;
        builder.fps = this.fps;
        builder.frames = this.frames;
        builder.addUnknownFields(unknownFields());
        return builder;
    }
}
