package cn.hutool.core.text.csv;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class CsvRow implements List<String> {
    final List<String> fields;
    final Map<String, Integer> headerMap;
    private final long originalLineNumber;

    public CsvRow(long j2, Map<String, Integer> map, List<String> list) throws IllegalArgumentException {
        Assert.notNull(list, "fields must be not null!", new Object[0]);
        this.originalLineNumber = j2;
        this.headerMap = map;
        this.fields = list;
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends String> collection) {
        return this.fields.addAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.fields.clear();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.fields.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.fields.containsAll(collection);
    }

    public String getByName(String str) throws IllegalArgumentException {
        Assert.notNull(this.headerMap, "No header available!", new Object[0]);
        Integer num = this.headerMap.get(str);
        if (num != null) {
            return get(num.intValue());
        }
        return null;
    }

    public int getFieldCount() {
        return this.fields.size();
    }

    public Map<String, String> getFieldMap() {
        if (this.headerMap == null) {
            throw new IllegalStateException("No header available");
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.headerMap.size(), 1.0f);
        Iterator<Map.Entry<String, Integer>> it = this.headerMap.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            Integer num = this.headerMap.get(key);
            linkedHashMap.put(key, num == null ? null : get(num.intValue()));
        }
        return linkedHashMap;
    }

    public long getOriginalLineNumber() {
        return this.originalLineNumber;
    }

    public List<String> getRawList() {
        return this.fields;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.fields.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.fields.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<String> iterator() {
        return this.fields.iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.fields.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<String> listIterator() {
        return this.fields.listIterator();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.fields.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.fields.retainAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.fields.size();
    }

    @Override // java.util.List
    public List<String> subList(int i2, int i3) {
        return this.fields.subList(i2, i3);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.fields.toArray();
    }

    public <T> T toBean(Class<T> cls) {
        return (T) BeanUtil.toBeanIgnoreError(getFieldMap(), cls);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CsvRow{");
        sb.append("originalLineNumber=");
        sb.append(this.originalLineNumber);
        sb.append(", ");
        sb.append("fields=");
        if (this.headerMap != null) {
            sb.append('{');
            Iterator<Map.Entry<String, String>> it = getFieldMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                sb.append(next.getKey());
                sb.append('=');
                if (next.getValue() != null) {
                    sb.append(next.getValue());
                }
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append('}');
        } else {
            sb.append(this.fields.toString());
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.List
    public boolean addAll(int i2, Collection<? extends String> collection) {
        return this.fields.addAll(i2, collection);
    }

    @Override // java.util.List
    public String get(int i2) {
        if (i2 >= this.fields.size()) {
            return null;
        }
        return this.fields.get(i2);
    }

    @Override // java.util.List
    public ListIterator<String> listIterator(int i2) {
        return this.fields.listIterator(i2);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.fields.remove(obj);
    }

    @Override // java.util.List
    public String set(int i2, String str) {
        return this.fields.set(i2, str);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.fields.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(String str) {
        return this.fields.add(str);
    }

    @Override // java.util.List
    public String remove(int i2) {
        return this.fields.remove(i2);
    }

    @Override // java.util.List
    public void add(int i2, String str) {
        this.fields.add(i2, str);
    }
}
