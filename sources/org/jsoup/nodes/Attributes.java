package org.jsoup.nodes;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class Attributes implements Iterable<Attribute>, Cloneable {
    protected static final String dataPrefix = "data-";
    private LinkedHashMap<String, Attribute> attributes = null;

    public class Dataset extends AbstractMap<String, String> {

        public class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Attributes.this.attributes.values().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    Attribute next = this.attrIter.next();
                    this.attr = next;
                    if (next.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Iterator
            public void remove() {
                Attributes.this.attributes.remove(this.attr.getKey());
            }

            @Override // java.util.Iterator
            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(5), this.attr.getValue());
            }
        }

        public class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i2 = 0;
                while (new DatasetIterator().hasNext()) {
                    i2++;
                }
                return i2;
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        private Dataset() {
            if (Attributes.this.attributes == null) {
                Attributes.this.attributes = new LinkedHashMap(2);
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public String put(String str, String str2) {
            String strDataKey = Attributes.dataKey(str);
            String value = Attributes.this.hasKey(strDataKey) ? ((Attribute) Attributes.this.attributes.get(strDataKey)).getValue() : null;
            Attributes.this.attributes.put(strDataKey, new Attribute(strDataKey, str2));
            return value;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dataKey(String str) {
        return dataPrefix + str;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() == 0) {
            return;
        }
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<>(attributes.size());
        }
        this.attributes.putAll(attributes.attributes);
    }

    public List<Attribute> asList() {
        if (this.attributes == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.attributes.size());
        Iterator<Map.Entry<String, Attribute>> it = this.attributes.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Attributes)) {
            return false;
        }
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        LinkedHashMap<String, Attribute> linkedHashMap2 = ((Attributes) obj).attributes;
        return linkedHashMap == null ? linkedHashMap2 == null : linkedHashMap.equals(linkedHashMap2);
    }

    public String get(String str) {
        Attribute attribute;
        Validate.notEmpty(str);
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        return (linkedHashMap == null || (attribute = linkedHashMap.get(str.toLowerCase())) == null) ? "" : attribute.getValue();
    }

    public boolean hasKey(String str) {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        return linkedHashMap != null && linkedHashMap.containsKey(str.toLowerCase());
    }

    public int hashCode() {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap != null) {
            return linkedHashMap.hashCode();
        }
        return 0;
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        html(sb, new Document("").outputSettings());
        return sb.toString();
    }

    @Override // java.lang.Iterable
    public Iterator<Attribute> iterator() {
        return asList().iterator();
    }

    public void put(String str, String str2) {
        put(new Attribute(str, str2));
    }

    public void remove(String str) {
        Validate.notEmpty(str);
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap == null) {
            return;
        }
        linkedHashMap.remove(str.toLowerCase());
    }

    public int size() {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap == null) {
            return 0;
        }
        return linkedHashMap.size();
    }

    public String toString() {
        return html();
    }

    public Attributes clone() {
        if (this.attributes == null) {
            return new Attributes();
        }
        try {
            Attributes attributes = (Attributes) super.clone();
            attributes.attributes = new LinkedHashMap<>(this.attributes.size());
            Iterator<Attribute> it = iterator();
            while (it.hasNext()) {
                Attribute next = it.next();
                attributes.attributes.put(next.getKey(), next.clone());
            }
            return attributes;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void put(Attribute attribute) {
        Validate.notNull(attribute);
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<>(2);
        }
        this.attributes.put(attribute.getKey(), attribute);
    }

    public void html(StringBuilder sb, Document.OutputSettings outputSettings) {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap == null) {
            return;
        }
        Iterator<Map.Entry<String, Attribute>> it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Attribute value = it.next().getValue();
            sb.append(" ");
            value.html(sb, outputSettings);
        }
    }
}
