package io.socket.parser;

import io.socket.hasbinary.HasBinary;
import io.socket.parser.Binary;
import io.socket.parser.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes8.dex */
public final class IOParser implements Parser {
    private static final Logger logger = Logger.getLogger(IOParser.class.getName());

    public static class BinaryReconstructor {
        List<byte[]> buffers = new ArrayList();
        public Packet reconPack;

        public BinaryReconstructor(Packet packet) {
            this.reconPack = packet;
        }

        public void finishReconstruction() {
            this.reconPack = null;
            this.buffers = new ArrayList();
        }

        public Packet takeBinaryData(byte[] bArr) {
            this.buffers.add(bArr);
            int size = this.buffers.size();
            Packet packet = this.reconPack;
            if (size != packet.attachments) {
                return null;
            }
            List<byte[]> list = this.buffers;
            Packet packetReconstructPacket = Binary.reconstructPacket(packet, (byte[][]) list.toArray(new byte[list.size()][]));
            finishReconstruction();
            return packetReconstructPacket;
        }
    }

    public static final class Encoder implements Parser.Encoder {
        private void encodeAsBinary(Packet packet, Parser.Encoder.Callback callback) {
            Binary.DeconstructedPacket deconstructedPacketDeconstructPacket = Binary.deconstructPacket(packet);
            String strEncodeAsString = encodeAsString(deconstructedPacketDeconstructPacket.packet);
            ArrayList arrayList = new ArrayList(Arrays.asList(deconstructedPacketDeconstructPacket.buffers));
            arrayList.add(0, strEncodeAsString);
            callback.call(arrayList.toArray());
        }

        private String encodeAsString(Packet packet) {
            StringBuilder sb = new StringBuilder("" + packet.type);
            int i2 = packet.type;
            if (5 == i2 || 6 == i2) {
                sb.append(packet.attachments);
                sb.append("-");
            }
            String str = packet.nsp;
            if (str != null && str.length() != 0 && !"/".equals(packet.nsp)) {
                sb.append(packet.nsp);
                sb.append(",");
            }
            int i3 = packet.id;
            if (i3 >= 0) {
                sb.append(i3);
            }
            Object obj = packet.data;
            if (obj != null) {
                sb.append(obj);
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoded %s as %s", packet, sb));
            }
            return sb.toString();
        }

        @Override // io.socket.parser.Parser.Encoder
        public void encode(Packet packet, Parser.Encoder.Callback callback) {
            int i2 = packet.type;
            if ((i2 == 2 || i2 == 3) && HasBinary.hasBinary(packet.data)) {
                packet.type = packet.type == 2 ? 5 : 6;
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoding packet %s", packet));
            }
            int i3 = packet.type;
            if (5 == i3 || 6 == i3) {
                encodeAsBinary(packet, callback);
            } else {
                callback.call(new String[]{encodeAsString(packet)});
            }
        }
    }

    private IOParser() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Packet<String> error() {
        return new Packet<>(4, "parser error");
    }

    public static final class Decoder implements Parser.Decoder {
        private Parser.Decoder.Callback onDecodedCallback;
        BinaryReconstructor reconstructor = null;

        /* JADX WARN: Code restructure failed: missing block: B:44:0x00b3, code lost:
        
            r1.id = java.lang.Integer.parseInt(r3.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00c2, code lost:
        
            return io.socket.parser.IOParser.error();
         */
        /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static io.socket.parser.Packet decodeString(java.lang.String r8) {
            /*
                Method dump skipped, instructions count: 277
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.socket.parser.IOParser.Decoder.decodeString(java.lang.String):io.socket.parser.Packet");
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(String str) {
            Parser.Decoder.Callback callback;
            Packet packetDecodeString = decodeString(str);
            int i2 = packetDecodeString.type;
            if (5 != i2 && 6 != i2) {
                Parser.Decoder.Callback callback2 = this.onDecodedCallback;
                if (callback2 != null) {
                    callback2.call(packetDecodeString);
                    return;
                }
                return;
            }
            BinaryReconstructor binaryReconstructor = new BinaryReconstructor(packetDecodeString);
            this.reconstructor = binaryReconstructor;
            if (binaryReconstructor.reconPack.attachments != 0 || (callback = this.onDecodedCallback) == null) {
                return;
            }
            callback.call(packetDecodeString);
        }

        @Override // io.socket.parser.Parser.Decoder
        public void destroy() {
            BinaryReconstructor binaryReconstructor = this.reconstructor;
            if (binaryReconstructor != null) {
                binaryReconstructor.finishReconstruction();
            }
            this.onDecodedCallback = null;
        }

        @Override // io.socket.parser.Parser.Decoder
        public void onDecoded(Parser.Decoder.Callback callback) {
            this.onDecodedCallback = callback;
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(byte[] bArr) {
            BinaryReconstructor binaryReconstructor = this.reconstructor;
            if (binaryReconstructor != null) {
                Packet packetTakeBinaryData = binaryReconstructor.takeBinaryData(bArr);
                if (packetTakeBinaryData != null) {
                    this.reconstructor = null;
                    Parser.Decoder.Callback callback = this.onDecodedCallback;
                    if (callback != null) {
                        callback.call(packetTakeBinaryData);
                        return;
                    }
                    return;
                }
                return;
            }
            throw new RuntimeException("got binary data when not reconstructing a packet");
        }
    }
}
