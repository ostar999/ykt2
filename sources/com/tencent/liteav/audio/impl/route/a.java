package com.tencent.liteav.audio.impl.route;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: com.tencent.liteav.audio.impl.route.a$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18213a;

        static {
            int[] iArr = new int[EnumC0325a.values().length];
            f18213a = iArr;
            try {
                iArr[EnumC0325a.VOICE_CHAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18213a[EnumC0325a.VOICE_PLAYBACK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18213a[EnumC0325a.MEDIA_PLAY_AND_RECORD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f18213a[EnumC0325a.MEDIA_PLAYBACK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* renamed from: com.tencent.liteav.audio.impl.route.a$a, reason: collision with other inner class name */
    public enum EnumC0325a {
        STOPPED,
        VOICE_CHAT,
        MEDIA_PLAY_AND_RECORD,
        MEDIA_PLAYBACK,
        VOICE_PLAYBACK,
        IDLE;

        public boolean a() {
            return this == MEDIA_PLAY_AND_RECORD || this == MEDIA_PLAYBACK;
        }

        public boolean b() {
            return this == VOICE_CHAT || this == VOICE_PLAYBACK;
        }
    }

    public static int a(EnumC0325a enumC0325a) {
        int i2 = AnonymousClass1.f18213a[enumC0325a.ordinal()];
        return (i2 == 1 || i2 == 2) ? 3 : 0;
    }
}
