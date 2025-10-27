package cn.hutool.core.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class FixedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -629171177321416095L;
    private int capacity;
    private Consumer<Map.Entry<K, V>> removeListener;

    public FixedLinkedHashMap(int i2) {
        super(i2 + 1, 1.0f, true);
        this.capacity = i2;
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override // java.util.LinkedHashMap
    public boolean removeEldestEntry(Map.Entry<K, V> entry) {
        if (size() <= this.capacity) {
            return false;
        }
        Consumer<Map.Entry<K, V>> consumer = this.removeListener;
        if (consumer == null) {
            return true;
        }
        consumer.accept(entry);
        return true;
    }

    public void setCapacity(int i2) {
        this.capacity = i2;
    }

    public void setRemoveListener(Consumer<Map.Entry<K, V>> consumer) {
        this.removeListener = consumer;
    }
}
