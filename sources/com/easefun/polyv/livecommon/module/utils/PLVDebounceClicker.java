package com.easefun.polyv.livecommon.module.utils;

import android.util.Pair;
import android.view.View;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVDebounceClicker {
    private static final long DEFAULT_DEBOUNCE_INTERVAL = TimeUnit.MILLISECONDS.toMillis(300);
    private static final LinkedList<Pair<String, Long>> LISTENER_LIST = new LinkedList<>();

    public static class OnClickListener implements View.OnClickListener {
        private final long debounceMs;
        private final View.OnClickListener target;

        public OnClickListener(View.OnClickListener target) {
            this(target, PLVDebounceClicker.DEFAULT_DEBOUNCE_INTERVAL);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (PLVDebounceClicker.tryClick(this, this.debounceMs)) {
                this.target.onClick(v2);
            }
        }

        public OnClickListener(View.OnClickListener target, long debounceMs) {
            this.target = target;
            this.debounceMs = debounceMs;
        }
    }

    private static Pair<String, Long> find(@NonNull String key) {
        Iterator<Pair<String, Long>> it = LISTENER_LIST.iterator();
        while (it.hasNext()) {
            Pair<String, Long> next = it.next();
            if (key.equals(next.first)) {
                return next;
            }
        }
        return null;
    }

    private static void putItem(@NonNull String key, long debounceIntervalMs) {
        LISTENER_LIST.addLast(new Pair<>(key, Long.valueOf(System.currentTimeMillis() + debounceIntervalMs)));
    }

    private static void removeOutDateItem() {
        Iterator<Pair<String, Long>> it = LISTENER_LIST.iterator();
        while (it.hasNext()) {
            Long l2 = (Long) it.next().second;
            if (l2 == null || l2.longValue() < System.currentTimeMillis()) {
                it.remove();
            }
        }
    }

    public static boolean tryClick(@NonNull View.OnClickListener onClickListener) {
        return tryClick(onClickListener, DEFAULT_DEBOUNCE_INTERVAL);
    }

    public static boolean tryClick(@NonNull String key) {
        return tryClick(key, DEFAULT_DEBOUNCE_INTERVAL);
    }

    public static boolean tryClick(@NonNull View.OnClickListener onClickListener, long debounceIntervalMs) {
        return tryClick(onClickListener.getClass().getName(), debounceIntervalMs);
    }

    public static boolean tryClick(@NonNull String key, long debounceIntervalMs) {
        Object obj;
        removeOutDateItem();
        Pair<String, Long> pairFind = find(key);
        if (pairFind != null && (obj = pairFind.second) != null && ((Long) obj).longValue() >= System.currentTimeMillis()) {
            return false;
        }
        putItem(key, debounceIntervalMs);
        return true;
    }
}
