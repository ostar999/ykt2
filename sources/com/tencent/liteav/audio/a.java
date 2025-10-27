package com.tencent.liteav.audio;

import com.tencent.liteav.basic.log.TXCLog;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private HashMap<String, HashSet<Integer>> f18165a;

    /* renamed from: com.tencent.liteav.audio.a$a, reason: collision with other inner class name */
    public static class C0323a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f18166a = new a();
    }

    public static a a() {
        return C0323a.f18166a;
    }

    private a() {
        this.f18165a = new HashMap<>();
    }

    public void a(String str, boolean z2, int i2) {
        HashSet<Integer> hashSet = this.f18165a.get(str);
        if (hashSet == null) {
            hashSet = new HashSet<>();
            this.f18165a.put(str, hashSet);
        }
        hashSet.add(Integer.valueOf(i2));
        TXCAudioEngine.getInstance().startRemoteAudio(str, z2);
        TXCLog.i("AudioPlayManager", "startRemoteAudio tinyId:" + str + ", sessionId:" + i2);
    }

    public void a(String str, int i2) {
        HashSet<Integer> hashSet = this.f18165a.get(str);
        if (hashSet == null) {
            return;
        }
        hashSet.remove(Integer.valueOf(i2));
        if (hashSet.isEmpty()) {
            this.f18165a.remove(str);
            TXCAudioEngine.getInstance().stopRemoteAudio(str);
            TXCLog.i("AudioPlayManager", "stopRemoteAudio. tinyId:" + str + ", sessionId:" + i2);
            return;
        }
        TXCLog.i("AudioPlayManager", "ignore stopRemoteAudio. because the same user is playing in other session. tinyId:" + str + ", cur sessionId:" + i2 + ", other sessionId:" + hashSet.iterator().next().intValue());
    }

    public void a(int i2) {
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, HashSet<Integer>> entry : this.f18165a.entrySet()) {
            String key = entry.getKey();
            HashSet<Integer> value = entry.getValue();
            value.remove(Integer.valueOf(i2));
            if (value.isEmpty()) {
                hashSet.add(key);
                TXCAudioEngine.getInstance().stopRemoteAudio(key);
                TXCLog.i("AudioPlayManager", "stopPlay, tinyId:" + key);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.f18165a.remove((String) it.next());
        }
    }
}
