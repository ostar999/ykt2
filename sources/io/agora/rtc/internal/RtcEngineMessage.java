package io.agora.rtc.internal;

import android.text.TextUtils;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.video.AgoraImage;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes8.dex */
public class RtcEngineMessage {
    public static short AGORA_UI_SERVER;

    public static class MediaAppContext extends Marshallable {
        MediaNetworkInfo networkInfo;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            MediaNetworkInfo mediaNetworkInfo = this.networkInfo;
            if (mediaNetworkInfo != null) {
                mediaNetworkInfo.marshall(this);
            }
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class MediaNetworkInfo extends Marshallable {
        int asu;
        int frequency;
        int linkspeed;
        int networkSubtype;
        int networkType;
        int rssi;
        int signalLevel;
        String localIp4 = "";
        String gatewayIp4 = "";
        String localIp6 = "";
        String gatewayIp6 = "";
        String ssid = "";
        String bssid = "";
        ArrayList<String> dnsList = null;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        public void marshall(Marshallable m2) {
            m2.pushBytes(this.localIp4.getBytes());
            m2.pushBytes(this.gatewayIp4.getBytes());
            m2.pushBytes(this.localIp6.getBytes());
            m2.pushBytes(this.gatewayIp6.getBytes());
            m2.pushInt(this.networkType);
            m2.pushInt(this.networkSubtype);
            m2.pushInt(this.signalLevel);
            m2.pushInt(this.rssi);
            m2.pushInt(this.asu);
            m2.pushInt(this.frequency);
            m2.pushInt(this.linkspeed);
            String str = this.ssid;
            if (str == null || !(str instanceof String)) {
                m2.pushBytes("".getBytes());
            } else {
                m2.pushBytes(str.getBytes());
            }
            String str2 = this.bssid;
            if (str2 != null) {
                m2.pushBytes(str2.getBytes());
            } else {
                m2.pushBytes("".getBytes());
            }
            ArrayList<String> arrayList = this.dnsList;
            if (arrayList != null) {
                m2.pushStringArray(arrayList);
            } else {
                m2.pushStringArray(new ArrayList<>());
            }
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            marshall(this);
            return super.marshall();
        }
    }

    public static class MediaResSetupTime extends Marshallable {
        int elapsed;
        boolean firstSuccess;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.elapsed = popInt();
            this.firstSuccess = popBool().booleanValue();
        }
    }

    public static class PActiveSpeaker extends Marshallable {
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
        }
    }

    public static class PAndroidContextInfo extends Marshallable {
        public String androidID;
        public String configDir;
        public String dataDir;
        public String device;
        public String deviceInfo;
        public String imei;
        public String macAddress;
        public String pluginDir;
        public String systemInfo;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.device.getBytes());
            pushBytes(this.deviceInfo.getBytes());
            pushBytes(this.systemInfo.getBytes());
            pushBytes(this.configDir.getBytes());
            pushBytes(this.dataDir.getBytes());
            pushBytes(this.pluginDir.getBytes());
            pushBytes(this.imei.getBytes());
            pushBytes(this.macAddress.getBytes());
            pushBytes(this.androidID.getBytes());
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.device = popString16UTF8();
            this.deviceInfo = popString16UTF8();
            this.systemInfo = popString16UTF8();
            this.configDir = popString16UTF8();
            this.dataDir = popString16UTF8();
            this.pluginDir = popString16UTF8();
            this.imei = popString16UTF8();
            this.macAddress = popString16UTF8();
            this.androidID = popString16UTF8();
        }
    }

    public static class PApiCallExecuted extends Marshallable {
        public String api;
        public int error;
        public String result;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.error = popInt();
            this.api = popString16UTF8();
            this.result = popString16UTF8();
        }
    }

    public static class PAudioRoutingChanged extends Marshallable {
        int routing;

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.routing = popInt();
        }
    }

    public static class PCameraExposureAreaChanged extends Marshallable {
        public int height;
        public int width;

        /* renamed from: x, reason: collision with root package name */
        public int f27138x;

        /* renamed from: y, reason: collision with root package name */
        public int f27139y;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.f27138x);
            pushInt(this.f27139y);
            pushInt(this.width);
            pushInt(this.height);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.f27138x = popInt();
            this.f27139y = popInt();
            this.width = popInt();
            this.height = popInt();
        }
    }

    public static class PCameraFocusAreaChanged extends Marshallable {
        public int height;
        public int width;

        /* renamed from: x, reason: collision with root package name */
        public int f27140x;

        /* renamed from: y, reason: collision with root package name */
        public int f27141y;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.f27140x);
            pushInt(this.f27141y);
            pushInt(this.width);
            pushInt(this.height);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.f27140x = popInt();
            this.f27141y = popInt();
            this.width = popInt();
            this.height = popInt();
        }
    }

    public static class PChannelMediaRelayConfiguration extends Marshallable {
        private void marshallChannelInfo(Marshallable m2, ChannelMediaInfo channelMediaInfo) {
            m2.pushString16(channelMediaInfo.channelName);
            m2.pushString16(channelMediaInfo.token);
            m2.pushInt(channelMediaInfo.uid);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(byte[] buf) {
            super.unmarshall(buf);
        }

        private void marshall(Marshallable m2, ChannelMediaRelayConfiguration configuration) {
            marshallChannelInfo(m2, configuration.getSrcChannelMediaInfo());
            pushShort((short) configuration.getDestChannelMediaInfos().size());
            Iterator<String> it = configuration.getDestChannelMediaInfos().keySet().iterator();
            while (it.hasNext()) {
                marshallChannelInfo(m2, configuration.getDestChannelMediaInfos().get(it.next()));
            }
        }

        public byte[] marshall(ChannelMediaRelayConfiguration configuration) {
            marshall(this, configuration);
            return super.marshall();
        }
    }

    public static class PClientRoleChanged extends Marshallable {
        int newRole;
        int oldRole;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.oldRole = popInt();
            this.newRole = popInt();
        }
    }

    public static class PConnectionState extends Marshallable {
        public int reason;
        public int state;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.state);
            pushInt(this.reason);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.state = popInt();
            this.reason = popInt();
        }
    }

    public static class PCrossChannelEvent extends Marshallable {
        public int code;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.code);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.code = popInt();
        }
    }

    public static class PCrossChannelState extends Marshallable {
        public int code;
        public int state;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.state);
            pushInt(this.code);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.state = popInt();
            this.code = popInt();
        }
    }

    public static class PError extends Marshallable {
        public int err;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.err = popInt();
        }
    }

    public static class PFirstLocalAudioFrame extends Marshallable {
        public int elapsed;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.elapsed = popInt();
        }
    }

    public static class PFirstLocalVideoFrame extends Marshallable {
        public int elapsed;
        public int height;
        public int width;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.width);
            pushInt(this.height);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.width = popInt();
            this.height = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PFirstRemoteAudioFrame extends Marshallable {
        public int elapsed;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PFirstRemoteVideoDecoded extends Marshallable {
        public int elapsed;
        public int height;
        public int uid;
        public int width;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.width);
            pushInt(this.height);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.width = popInt();
            this.height = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PFirstRemoteVideoFrame extends Marshallable {
        public int elapsed;
        public int height;
        public int uid;
        public int width;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.width);
            pushInt(this.height);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.width = popInt();
            this.height = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PHostInRequest extends Marshallable {
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
        }
    }

    public static class PHostInResponse extends Marshallable {
        public boolean accepted;
        public int error;
        public int ownerUid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.ownerUid = popInt();
            this.accepted = popBool().booleanValue();
            this.error = popInt();
        }
    }

    public static class PHostInStopped extends Marshallable {
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
        }
    }

    public static class PInjectStreamConfig extends Marshallable {
        private static final short SERVER_TYPE = 0;
        private static final short URI = 25;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(byte[] buf) {
            super.unmarshall(buf);
        }

        private void marshall(Marshallable m2, LiveInjectStreamConfig config) {
            m2.pushShort((short) 0);
            m2.pushShort(URI);
            m2.pushInt(config.width);
            m2.pushInt(config.height);
            m2.pushInt(config.videoGop);
            m2.pushInt(config.videoFramerate);
            m2.pushInt(config.videoBitrate);
            m2.pushInt(LiveInjectStreamConfig.AudioSampleRateType.getValue(config.audioSampleRate));
            m2.pushInt(config.audioBitrate);
            m2.pushInt(config.audioChannels);
        }

        public byte[] marshall(LiveInjectStreamConfig config) {
            marshall(this, config);
            return super.marshall();
        }
    }

    public static class PLiveTranscoding extends Marshallable {
        private static final short SERVER_TYPE = 0;
        private static final short URI = 23;

        private void marshallImage(Marshallable m2, AgoraImage image) {
            m2.pushString16(image.url);
            m2.pushInt(image.f27145x);
            m2.pushInt(image.f27146y);
            m2.pushInt(image.width);
            m2.pushInt(image.height);
        }

        private void marshallUserConfig(Marshallable m2, LiveTranscoding.TranscodingUser user) {
            m2.pushInt(user.uid);
            m2.pushInt(user.f27142x);
            m2.pushInt(user.f27143y);
            m2.pushInt(user.width);
            m2.pushInt(user.height);
            m2.pushInt(user.zOrder);
            m2.pushDouble(user.alpha);
            m2.pushInt(user.audioChannel);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(byte[] buf) {
            super.unmarshall(buf);
        }

        private void marshall(Marshallable m2, LiveTranscoding transcoding) {
            m2.pushShort((short) 0);
            m2.pushShort((short) 23);
            m2.pushInt(transcoding.width);
            m2.pushInt(transcoding.height);
            m2.pushInt(transcoding.videoGop);
            m2.pushInt(transcoding.videoFramerate);
            m2.pushInt(LiveTranscoding.VideoCodecProfileType.getValue(transcoding.videoCodecProfile));
            m2.pushInt(LiveTranscoding.VideoCodecType.getValue(transcoding.videoCodecType));
            m2.pushInt(transcoding.videoBitrate);
            if (transcoding.watermark == null) {
                transcoding.watermark = new AgoraImage();
            }
            marshallImage(m2, transcoding.watermark);
            if (transcoding.backgroundImage == null) {
                transcoding.backgroundImage = new AgoraImage();
            }
            marshallImage(m2, transcoding.backgroundImage);
            m2.pushBool(Boolean.valueOf(transcoding.lowLatency));
            m2.pushInt(LiveTranscoding.AudioSampleRateType.getValue(transcoding.audioSampleRate));
            m2.pushInt(transcoding.audioBitrate);
            m2.pushInt(transcoding.audioChannels);
            m2.pushInt(LiveTranscoding.AudioCodecProfileType.getValue(transcoding.audioCodecProfile));
            m2.pushInt(transcoding.backgroundColor & 16777215);
            if (TextUtils.isEmpty(transcoding.userConfigExtraInfo)) {
                transcoding.userConfigExtraInfo = "";
            }
            m2.pushString16(transcoding.userConfigExtraInfo);
            if (TextUtils.isEmpty(transcoding.metadata)) {
                transcoding.metadata = "";
            }
            m2.pushString16(transcoding.metadata);
            if (transcoding.getUsers() != null && transcoding.getUsers().size() > 0) {
                pushShort((short) transcoding.getUserCount());
                Iterator<LiveTranscoding.TranscodingUser> it = transcoding.getUsers().iterator();
                while (it.hasNext()) {
                    marshallUserConfig(m2, it.next());
                }
                return;
            }
            pushShort((short) 0);
        }

        public byte[] marshall(LiveTranscoding transcoding) {
            marshall(this, transcoding);
            return super.marshall();
        }
    }

    public static class PLocalAudioEnabled extends Marshallable {
        boolean enabled;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.enabled = popBool().booleanValue();
        }
    }

    public static class PLocalAudioStat extends Marshallable {
        public IRtcEngineEventHandler.LocalAudioStats stats = new IRtcEngineEventHandler.LocalAudioStats();

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.stats.numChannels);
            pushInt(this.stats.sentSampleRate);
            pushInt(this.stats.sentBitrate);
            pushShort((short) this.stats.txPacketLossRate);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.stats.numChannels = popInt();
            this.stats.sentSampleRate = popInt();
            this.stats.sentBitrate = popInt();
            this.stats.txPacketLossRate = popShort();
        }
    }

    public static class PLocalFallbackStatus extends Marshallable {
        boolean state;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.state = popBool().booleanValue();
        }
    }

    public static class PLocalVideoStat extends Marshallable {
        public IRtcEngineEventHandler.LocalVideoStats stats = new IRtcEngineEventHandler.LocalVideoStats();

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.stats.sentBitrate);
            pushInt(this.stats.sentFrameRate);
            pushInt(this.stats.encoderOutputFrameRate);
            pushInt(this.stats.rendererOutputFrameRate);
            pushInt(this.stats.targetBitrate);
            pushInt(this.stats.targetFrameRate);
            pushInt(this.stats.qualityAdaptIndication);
            pushInt(this.stats.encodedBitrate);
            pushInt(this.stats.encodedFrameWidth);
            pushInt(this.stats.encodedFrameHeight);
            pushInt(this.stats.encodedFrameCount);
            pushByte((byte) this.stats.codecType);
            pushShort((short) this.stats.txPacketLossRate);
            pushInt(this.stats.captureFrameRate);
            pushInt(this.stats.videoQualityPoint);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.stats.sentBitrate = popInt();
            this.stats.sentFrameRate = popInt();
            this.stats.encoderOutputFrameRate = popInt();
            this.stats.rendererOutputFrameRate = popInt();
            this.stats.targetBitrate = popInt();
            this.stats.targetFrameRate = popInt();
            this.stats.qualityAdaptIndication = popInt();
            this.stats.encodedBitrate = popInt();
            this.stats.encodedFrameWidth = popInt();
            this.stats.encodedFrameHeight = popInt();
            this.stats.encodedFrameCount = popInt();
            this.stats.codecType = popByte();
            this.stats.txPacketLossRate = popShort();
            this.stats.captureFrameRate = popInt();
            this.stats.videoQualityPoint = popInt();
        }
    }

    public static class PMediaEngineEvent extends Marshallable {
        int code;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.code = popInt();
        }
    }

    public static class PMediaReqConnectMedia2 extends Marshallable {
        public static final int uri = 66453504 | RtcEngineMessage.AGORA_UI_SERVER;
        String connInfo;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.connInfo.getBytes());
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaReqCreateChannel extends Marshallable {
        public static final int uri = 131072 | RtcEngineMessage.AGORA_UI_SERVER;
        String key;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.key.getBytes());
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaReqJoinMeida extends Marshallable {
        public static final int uri = 196608 | RtcEngineMessage.AGORA_UI_SERVER;
        int sid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.sid);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaReqLeaveChannel extends Marshallable {
        public static final int uri = 393216 | RtcEngineMessage.AGORA_UI_SERVER;
        int sid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.sid);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaReqLeaveLinkd extends Marshallable {
        public static final int uri = 262144 | RtcEngineMessage.AGORA_UI_SERVER;
        int sid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.sid);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaReqUserData extends Marshallable {
        public static final int uri = 327680 | RtcEngineMessage.AGORA_UI_SERVER;
        String key;
        String mobileinfo;
        int uid;
        String username;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.key.getBytes());
            pushBytes(this.username.getBytes());
            pushBytes(this.mobileinfo.getBytes());
            pushInt(this.uid);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }
    }

    public static class PMediaResAudioEffectFinished extends Marshallable {
        int soundId;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.soundId = popInt();
        }
    }

    public static class PMediaResAudioQuality extends Marshallable {
        short delay;
        short lost;
        int peer_uid;
        int quality;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.peer_uid = popInt();
            this.quality = popInt();
            this.delay = popShort();
            this.lost = popShort();
        }
    }

    public static class PMediaResFirstRemoteAudioDecoded extends Marshallable {
        public int elapsed;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PMediaResJoinMedia extends Marshallable {
        public String channel;
        public int elapsed;
        public boolean firstSuccess;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.channel = popString16UTF8();
            this.uid = popInt();
            this.elapsed = popInt();
            this.firstSuccess = popBool().booleanValue();
        }
    }

    public static class PMediaResLastmileProbeResult extends Marshallable {
        LastmileProbeOneWayResult downlinkReport;
        int rtt;
        short state;
        LastmileProbeOneWayResult uplinkReport;

        public static class LastmileProbeOneWayResult {
            public int availableBandwidth;
            public int jitter;
            public int packetLossRate;
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushShort(this.state);
            pushInt(this.uplinkReport.packetLossRate);
            pushInt(this.uplinkReport.jitter);
            pushInt(this.uplinkReport.availableBandwidth);
            pushInt(this.downlinkReport.packetLossRate);
            pushInt(this.downlinkReport.jitter);
            pushInt(this.downlinkReport.availableBandwidth);
            pushInt(this.rtt);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.state = popShort();
            this.uplinkReport = new LastmileProbeOneWayResult();
            this.downlinkReport = new LastmileProbeOneWayResult();
            this.uplinkReport.packetLossRate = popInt();
            this.uplinkReport.jitter = popInt();
            this.uplinkReport.availableBandwidth = popInt();
            this.downlinkReport.packetLossRate = popInt();
            this.downlinkReport.jitter = popInt();
            this.downlinkReport.availableBandwidth = popInt();
            this.rtt = popInt();
        }
    }

    public static class PMediaResLastmileQuality extends Marshallable {
        int quality;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.quality = popInt();
        }
    }

    public static class PMediaResLocalAudioStateChanged extends Marshallable {
        int error;
        int state;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.state = popInt();
            this.error = popInt();
        }
    }

    public static class PMediaResLocalVideoStateChanged extends Marshallable {
        int error;
        int localVideoState;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.localVideoState = popInt();
            this.error = popInt();
        }
    }

    public static class PMediaResNetworkQuality extends Marshallable {
        int rxQuality;
        int txQuality;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.txQuality = popInt();
            this.rxQuality = popInt();
        }
    }

    public static class PMediaResRtcStats extends Marshallable {
        int cpuAppUsage;
        int cpuTotalUsage;
        int gatewayRtt;
        int lastmileDelay;
        int memoryAppUsageInKbytes;
        int memoryAppUsageRatio;
        int memoryTotalUsageRatio;
        int rxAudioBytes;
        int rxAudioKBitRate;
        int rxKBitRate;
        int rxPacketLossRate;
        int rxVideoBytes;
        int rxVideoKBitRate;
        int totalDuration;
        int totalRxBytes;
        int totalTxBytes;
        int txAudioBytes;
        int txAudioKBitRate;
        int txKBitRate;
        int txPacketLossRate;
        int txVideoBytes;
        int txVideoKBitRate;
        int users;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.totalDuration = popInt();
            this.totalTxBytes = popInt();
            this.totalRxBytes = popInt();
            this.txAudioBytes = popInt();
            this.rxAudioBytes = popInt();
            this.txVideoBytes = popInt();
            this.rxVideoBytes = popInt();
            this.txKBitRate = popShort();
            this.rxKBitRate = popShort();
            this.txAudioKBitRate = popShort();
            this.rxAudioKBitRate = popShort();
            this.txVideoKBitRate = popShort();
            this.rxVideoKBitRate = popShort();
            this.lastmileDelay = popShort();
            this.txPacketLossRate = popShort();
            this.rxPacketLossRate = popShort();
            this.cpuTotalUsage = popInt();
            this.cpuAppUsage = popInt();
            this.users = popInt();
            this.gatewayRtt = popInt();
            this.memoryTotalUsageRatio = popInt();
            this.memoryAppUsageRatio = popInt();
            this.memoryAppUsageInKbytes = popInt();
        }
    }

    public static class PMediaResSpeakersReport extends Marshallable {
        int mixVolume;
        Speaker[] speakers;

        public static class Speaker {
            public String channelId;
            public int uid;
            public int vad;
            public int volume;
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.mixVolume);
            int length = this.speakers.length;
            pushShort((short) length);
            for (int i2 = 0; i2 < length; i2++) {
                pushInt(this.speakers[i2].uid);
                pushInt(this.speakers[i2].volume);
                pushInt(this.speakers[i2].vad);
                pushString16(this.speakers[i2].channelId);
            }
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.mixVolume = popInt();
            int iPopShort = popShort();
            if (iPopShort > 0) {
                this.speakers = new Speaker[iPopShort];
                for (int i2 = 0; i2 < iPopShort; i2++) {
                    this.speakers[i2] = new Speaker();
                    this.speakers[i2].uid = popInt();
                    this.speakers[i2].volume = popInt();
                    this.speakers[i2].vad = popInt();
                    this.speakers[i2].channelId = popString16UTF8();
                }
            }
        }
    }

    public static class PMediaResTransportQuality extends Marshallable {
        public int bitrate;
        public short delay;
        public boolean isAudio;
        public short lost;
        public int peer_uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.isAudio = popBool().booleanValue();
            this.peer_uid = popInt();
            this.bitrate = popInt();
            this.delay = popShort();
            this.lost = popShort();
        }
    }

    public static class PMediaResUserJoinedEvent extends Marshallable {
        int elapsed;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PMediaResUserOfflineEvent extends Marshallable {
        int reason;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.reason = popInt();
        }
    }

    public static class PMediaResUserState extends Marshallable {
        boolean state;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.state = popBool().booleanValue();
        }
    }

    public static class PNetworkTypeChanged extends Marshallable {
        public int type;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.type);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.type = popInt();
        }
    }

    public static class PPrivilegeWillExpire extends Marshallable {
        public String token;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.token.getBytes());
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.token = popString16UTF8();
        }
    }

    public static class PPublishAudioState extends Marshallable {
        public String channel;
        public int elapsed;
        public int newstate;
        public int oldstate;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.channel.getBytes());
            pushInt(this.oldstate);
            pushInt(this.newstate);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.channel = popString16UTF8();
            this.oldstate = popInt();
            this.newstate = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PPublishVideoState extends Marshallable {
        public String channel;
        public int elapsed;
        public int newstate;
        public int oldstate;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.channel.getBytes());
            pushInt(this.oldstate);
            pushInt(this.newstate);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.channel = popString16UTF8();
            this.oldstate = popInt();
            this.newstate = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PRemoteAudioStat extends Marshallable {
        public IRtcEngineEventHandler.RemoteAudioStats stats = new IRtcEngineEventHandler.RemoteAudioStats();

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.stats.uid);
            pushInt(this.stats.quality);
            pushInt(this.stats.networkTransportDelay);
            pushInt(this.stats.jitterBufferDelay);
            pushInt(this.stats.audioLossRate);
            pushInt(this.stats.numChannels);
            pushInt(this.stats.receivedSampleRate);
            pushInt(this.stats.receivedBitrate);
            pushInt(this.stats.totalFrozenTime);
            pushInt(this.stats.frozenRate);
            pushInt(this.stats.totalActiveTime);
            pushInt(this.stats.publishDuration);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.stats.uid = popInt();
            this.stats.quality = popInt();
            this.stats.networkTransportDelay = popInt();
            this.stats.jitterBufferDelay = popInt();
            this.stats.audioLossRate = popInt();
            this.stats.numChannels = popInt();
            this.stats.receivedSampleRate = popInt();
            this.stats.receivedBitrate = popInt();
            this.stats.totalFrozenTime = popInt();
            this.stats.frozenRate = popInt();
            this.stats.totalActiveTime = popInt();
            this.stats.publishDuration = popInt();
        }
    }

    public static class PRemoteAudioState extends Marshallable {
        public int elapsed;
        public int reason;
        public int state;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.state);
            pushInt(this.reason);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.state = popInt();
            this.reason = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PRemoteVideoStat extends Marshallable {
        public IRtcEngineEventHandler.RemoteVideoStats stats = new IRtcEngineEventHandler.RemoteVideoStats();

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.stats.uid);
            pushInt(this.stats.delay);
            pushInt(this.stats.width);
            pushInt(this.stats.height);
            pushInt(this.stats.receivedBitrate);
            pushInt(this.stats.decoderOutputFrameRate);
            pushInt(this.stats.rendererOutputFrameRate);
            pushInt(this.stats.packetLossRate);
            pushInt(this.stats.rxStreamType);
            pushInt(this.stats.totalFrozenTime);
            pushInt(this.stats.frozenRate);
            pushInt(this.stats.totalActiveTime);
            pushInt(this.stats.publishDuration);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.stats.uid = popInt();
            this.stats.delay = popInt();
            this.stats.width = popInt();
            this.stats.height = popInt();
            this.stats.receivedBitrate = popInt();
            this.stats.decoderOutputFrameRate = popInt();
            this.stats.rendererOutputFrameRate = popInt();
            this.stats.packetLossRate = popInt();
            this.stats.rxStreamType = popInt();
            this.stats.totalFrozenTime = popInt();
            this.stats.frozenRate = popInt();
            this.stats.totalActiveTime = popInt();
            this.stats.publishDuration = popInt();
        }
    }

    public static class PRemoteVideoStateExt extends Marshallable {
        public int elapsed;
        public int reason;
        public int state;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.state);
            pushInt(this.reason);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.state = popByte();
            this.reason = popByte();
            this.elapsed = popInt();
        }
    }

    public static class PRtmpStreamingState extends Marshallable {
        public int error;
        public int state;
        public String url;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.url = popString16UTF8();
            this.state = popInt();
            this.error = popInt();
        }
    }

    public static class PStreamInjectedStatus extends Marshallable {
        public int status;
        public int uid;
        public String url;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.url.getBytes());
            pushInt(this.uid);
            pushInt(this.status);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.url = popString16UTF8();
            this.uid = popInt();
            this.status = popInt();
        }
    }

    public static class PStreamMessage extends Marshallable {
        byte[] payload;
        int streamId;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.streamId = popInt();
            this.payload = popBytes();
        }
    }

    public static class PStreamMessageError extends Marshallable {
        int cached;
        int error;
        int missed;
        int streamId;
        int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.streamId = popInt();
            this.error = popInt();
            this.missed = popInt();
            this.cached = popInt();
        }
    }

    public static class PStreamPublished extends Marshallable {
        public int error;
        public String url;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.url = popString16UTF8();
            this.error = popInt();
        }
    }

    public static class PStreamUnPublished extends Marshallable {
        public String url;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.url = popString16UTF8();
        }
    }

    public static class PSubscribeAudioState extends Marshallable {
        public String channel;
        public int elapsed;
        public int newstate;
        public int oldstate;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.channel.getBytes());
            pushInt(this.uid);
            pushInt(this.oldstate);
            pushInt(this.newstate);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.channel = popString16UTF8();
            this.uid = popInt();
            this.oldstate = popInt();
            this.newstate = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PSubscribeVideoState extends Marshallable {
        public String channel;
        public int elapsed;
        public int newstate;
        public int oldstate;
        public int uid;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushBytes(this.channel.getBytes());
            pushInt(this.uid);
            pushInt(this.oldstate);
            pushInt(this.newstate);
            pushInt(this.elapsed);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.channel = popString16UTF8();
            this.uid = popInt();
            this.oldstate = popInt();
            this.newstate = popInt();
            this.elapsed = popInt();
        }
    }

    public static class PUserAccountInfo extends Marshallable {
        public int uid;
        public String userAccount;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushBytes(this.userAccount.getBytes());
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.userAccount = popString16UTF8();
        }
    }

    public static class PUserTransportStat extends Marshallable {
        public int delay;
        public boolean isAudio;
        public int lost;
        public int peer_uid;
        public int rxKBitRate;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.isAudio = popBool().booleanValue();
            this.peer_uid = popInt();
            this.delay = popShort();
            this.lost = popShort();
            this.rxKBitRate = popShort();
        }
    }

    public static class PVideoNetOptions extends Marshallable {
        short bitrate;
        short frameRate;
        short height;
        short width;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        public void marshall(Marshallable m2) {
            m2.pushShort(this.width);
            m2.pushShort(this.height);
            m2.pushShort(this.frameRate);
            m2.pushShort(this.bitrate);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.width = popShort();
            this.height = popShort();
            this.frameRate = popShort();
            this.bitrate = popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            marshall(this);
            return super.marshall();
        }
    }

    public static class PVideoSizeChanged extends Marshallable {
        public int height;
        public int rotation;
        public int uid;
        public int width;

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ ByteBuffer getBuffer() {
            return super.getBuffer();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void marshall(ByteBuffer buf) {
            super.marshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popAll() {
            return super.popAll();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ Boolean popBool() {
            return super.popBool();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte popByte() {
            return super.popByte();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes() {
            return super.popBytes();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ byte[] popBytes32() {
            return super.popBytes32();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int popInt() {
            return super.popInt();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ long popInt64() {
            return super.popInt64();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ int[] popIntArray() {
            return super.popIntArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short popShort() {
            return super.popShort();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ short[] popShortArray() {
            return super.popShortArray();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16() {
            return super.popString16();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ String popString16UTF8() {
            return super.popString16UTF8();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBool(Boolean val) {
            super.pushBool(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushByte(byte val) {
            super.pushByte(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes(byte[] buf) {
            super.pushBytes(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushBytes32(byte[] buf) {
            super.pushBytes32(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushDouble(double val) {
            super.pushDouble(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt(int val) {
            super.pushInt(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushInt64(long val) {
            super.pushInt64(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(int[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShort(short val) {
            super.pushShort(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushShortArray(short[] vals) {
            super.pushShortArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushString16(String val) {
            super.pushString16(val);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushStringArray(ArrayList vals) {
            super.pushStringArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void unmarshall(ByteBuffer buf) {
            super.unmarshall(buf);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public byte[] marshall() {
            pushInt(this.uid);
            pushInt(this.width);
            pushInt(this.height);
            pushInt(this.rotation);
            return super.marshall();
        }

        @Override // io.agora.rtc.internal.Marshallable
        public /* bridge */ /* synthetic */ void pushIntArray(Integer[] vals) {
            super.pushIntArray(vals);
        }

        @Override // io.agora.rtc.internal.Marshallable
        public void unmarshall(byte[] buf) {
            super.unmarshall(buf);
            this.uid = popInt();
            this.width = popInt();
            this.height = popInt();
            this.rotation = popInt();
        }
    }
}
