package org.eclipse.jetty.util;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.StrPool;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes9.dex */
public class StringMap extends AbstractMap implements Externalizable {
    public static final boolean CASE_INSENSTIVE = true;
    protected static final int __HASH_WIDTH = 17;
    protected HashSet _entrySet;
    protected boolean _ignoreCase;
    protected NullEntry _nullEntry;
    protected Object _nullValue;
    protected Node _root;
    protected Set _umEntrySet;
    protected int _width;

    public static class Node implements Map.Entry {
        char[] _char;
        Node[] _children;
        String _key;
        Node _next;
        char[] _ochar;
        Object _value;

        public Node() {
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this._key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this._value;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            Object obj2 = this._value;
            this._value = obj;
            return obj2;
        }

        public Node split(StringMap stringMap, int i2) {
            Node node = new Node();
            char[] cArr = this._char;
            int length = cArr.length - i2;
            this._char = new char[i2];
            node._char = new char[length];
            System.arraycopy(cArr, 0, this._char, 0, i2);
            System.arraycopy(cArr, i2, node._char, 0, length);
            char[] cArr2 = this._ochar;
            if (cArr2 != null) {
                this._ochar = new char[i2];
                node._ochar = new char[length];
                System.arraycopy(cArr2, 0, this._ochar, 0, i2);
                System.arraycopy(cArr2, i2, node._ochar, 0, length);
            }
            node._key = this._key;
            node._value = this._value;
            this._key = null;
            this._value = null;
            if (stringMap._entrySet.remove(this)) {
                stringMap._entrySet.add(node);
            }
            node._children = this._children;
            int i3 = stringMap._width;
            Node[] nodeArr = new Node[i3];
            this._children = nodeArr;
            nodeArr[node._char[0] % i3] = node;
            char[] cArr3 = node._ochar;
            if (cArr3 != null) {
                char c3 = cArr3[0];
                if (nodeArr[c3 % i3] != node) {
                    nodeArr[c3 % i3] = node;
                }
            }
            return node;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            toString(sb);
            return sb.toString();
        }

        public Node(boolean z2, String str, int i2) {
            int length = str.length() - i2;
            this._char = new char[length];
            this._ochar = new char[length];
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt = str.charAt(i2 + i3);
                this._char[i3] = cCharAt;
                if (z2) {
                    if (Character.isUpperCase(cCharAt)) {
                        cCharAt = Character.toLowerCase(cCharAt);
                    } else if (Character.isLowerCase(cCharAt)) {
                        cCharAt = Character.toUpperCase(cCharAt);
                    }
                    this._ochar[i3] = cCharAt;
                }
            }
        }

        private void toString(StringBuilder sb) {
            sb.append("{[");
            if (this._char != null) {
                int i2 = 0;
                while (true) {
                    char[] cArr = this._char;
                    if (i2 >= cArr.length) {
                        break;
                    }
                    sb.append(cArr[i2]);
                    i2++;
                }
            } else {
                sb.append(CharPool.DASHED);
            }
            sb.append(':');
            sb.append(this._key);
            sb.append('=');
            sb.append(this._value);
            sb.append(']');
            if (this._children != null) {
                for (int i3 = 0; i3 < this._children.length; i3++) {
                    sb.append('|');
                    Node node = this._children[i3];
                    if (node != null) {
                        node.toString(sb);
                    } else {
                        sb.append("-");
                    }
                }
            }
            sb.append('}');
            if (this._next != null) {
                sb.append(",\n");
                this._next.toString(sb);
            }
        }
    }

    public class NullEntry implements Map.Entry {
        private NullEntry() {
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return null;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return StringMap.this._nullValue;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            StringMap stringMap = StringMap.this;
            Object obj2 = stringMap._nullValue;
            stringMap._nullValue = obj;
            return obj2;
        }

        public String toString() {
            return "[:null=" + StringMap.this._nullValue + StrPool.BRACKET_END;
        }
    }

    public StringMap() {
        this._width = 17;
        this._root = new Node();
        this._ignoreCase = false;
        this._nullEntry = null;
        this._nullValue = null;
        HashSet hashSet = new HashSet(3);
        this._entrySet = hashSet;
        this._umEntrySet = Collections.unmodifiableSet(hashSet);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this._root = new Node();
        this._nullEntry = null;
        this._nullValue = null;
        this._entrySet.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return obj == null ? this._nullEntry != null : getEntry(obj.toString(), 0, obj.toString().length()) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        return this._umEntrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        return obj == null ? this._nullValue : obj instanceof String ? get((String) obj) : get(obj.toString());
    }

    public Map.Entry getBestEntry(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i4 = -1;
        for (int i5 = 0; i5 < i3; i5++) {
            char c3 = (char) bArr[i2 + i5];
            if (i4 == -1) {
                Node[] nodeArr = node._children;
                Node node2 = nodeArr == null ? null : nodeArr[c3 % this._width];
                if (node2 == null && i5 > 0) {
                    return node;
                }
                node = node2;
                i4 = 0;
            }
            while (node != null) {
                char[] cArr = node._char;
                if (cArr[i4] == c3 || (this._ignoreCase && node._ochar[i4] == c3)) {
                    i4++;
                    if (i4 == cArr.length) {
                        i4 = -1;
                    }
                } else {
                    if (i4 > 0) {
                        return null;
                    }
                    node = node._next;
                }
            }
            return null;
        }
        if (i4 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    public Map.Entry getEntry(String str, int i2, int i3) {
        if (str == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i4 = -1;
        for (int i5 = 0; i5 < i3; i5++) {
            char cCharAt = str.charAt(i2 + i5);
            if (i4 == -1) {
                Node[] nodeArr = node._children;
                node = nodeArr == null ? null : nodeArr[cCharAt % this._width];
                i4 = 0;
            }
            while (node != null) {
                char[] cArr = node._char;
                if (cArr[i4] == cCharAt || (this._ignoreCase && node._ochar[i4] == cCharAt)) {
                    i4++;
                    if (i4 == cArr.length) {
                        i4 = -1;
                    }
                } else {
                    if (i4 > 0) {
                        return null;
                    }
                    node = node._next;
                }
            }
            return null;
        }
        if (i4 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    public int getWidth() {
        return this._width;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return this._entrySet.isEmpty();
    }

    public boolean isIgnoreCase() {
        return this._ignoreCase;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        return obj == null ? put((String) null, obj2) : put(obj.toString(), obj2);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        boolean z2 = objectInput.readBoolean();
        HashMap map = (HashMap) objectInput.readObject();
        setIgnoreCase(z2);
        putAll(map);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        return obj == null ? remove((String) null) : remove(obj.toString());
    }

    public void setIgnoreCase(boolean z2) {
        if (this._root._children != null) {
            throw new IllegalStateException("Must be set before first put");
        }
        this._ignoreCase = z2;
    }

    public void setWidth(int i2) {
        this._width = i2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this._entrySet.size();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        HashMap map = new HashMap(this);
        objectOutput.writeBoolean(this._ignoreCase);
        objectOutput.writeObject(map);
    }

    public Object put(String str, Object obj) {
        if (str == null) {
            Object obj2 = this._nullValue;
            this._nullValue = obj;
            if (this._nullEntry == null) {
                NullEntry nullEntry = new NullEntry();
                this._nullEntry = nullEntry;
                this._entrySet.add(nullEntry);
            }
            return obj2;
        }
        Node node = this._root;
        Node node2 = null;
        Node node3 = null;
        int i2 = 0;
        int i3 = -1;
        while (true) {
            if (i2 >= str.length()) {
                break;
            }
            char cCharAt = str.charAt(i2);
            if (i3 == -1) {
                Node[] nodeArr = node._children;
                node2 = null;
                node3 = node;
                node = nodeArr == null ? null : nodeArr[cCharAt % this._width];
                i3 = 0;
            }
            while (node != null) {
                char[] cArr = node._char;
                if (cArr[i3] == cCharAt || (this._ignoreCase && node._ochar[i3] == cCharAt)) {
                    i3++;
                    if (i3 == cArr.length) {
                        node2 = null;
                        i3 = -1;
                        i2++;
                    } else {
                        node2 = null;
                        i2++;
                    }
                } else if (i3 == 0) {
                    node2 = node;
                    node = node._next;
                } else {
                    node.split(this, i3);
                    i2--;
                    i3 = -1;
                    i2++;
                }
            }
            node = new Node(this._ignoreCase, str, i2);
            if (node2 != null) {
                node2._next = node;
            } else if (node3 != null) {
                if (node3._children == null) {
                    node3._children = new Node[this._width];
                }
                Node[] nodeArr2 = node3._children;
                int i4 = this._width;
                nodeArr2[cCharAt % i4] = node;
                char[] cArr2 = node._ochar;
                int i5 = cArr2[0] % i4;
                if (cArr2 != null && node._char[0] % i4 != i5) {
                    Node node4 = nodeArr2[i5];
                    if (node4 == null) {
                        nodeArr2[i5] = node;
                    } else {
                        while (true) {
                            Node node5 = node4._next;
                            if (node5 == null) {
                                break;
                            }
                            node4 = node5;
                        }
                        node4._next = node;
                    }
                }
            } else {
                this._root = node;
            }
        }
        if (node == null) {
            return null;
        }
        if (i3 > 0) {
            node.split(this, i3);
        }
        Object obj3 = node._value;
        node._key = str;
        node._value = obj;
        this._entrySet.add(node);
        return obj3;
    }

    public Object remove(String str) {
        if (str == null) {
            Object obj = this._nullValue;
            NullEntry nullEntry = this._nullEntry;
            if (nullEntry != null) {
                this._entrySet.remove(nullEntry);
                this._nullEntry = null;
                this._nullValue = null;
            }
            return obj;
        }
        Node node = this._root;
        int i2 = -1;
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (i2 == -1) {
                Node[] nodeArr = node._children;
                node = nodeArr == null ? null : nodeArr[cCharAt % this._width];
                i2 = 0;
            }
            while (node != null) {
                char[] cArr = node._char;
                if (cArr[i2] != cCharAt && (!this._ignoreCase || node._ochar[i2] != cCharAt)) {
                    if (i2 > 0) {
                        return null;
                    }
                    node = node._next;
                } else {
                    i2++;
                    if (i2 == cArr.length) {
                        i2 = -1;
                    }
                }
            }
            return null;
        }
        if (i2 > 0) {
            return null;
        }
        if (node != null && node._key == null) {
            return null;
        }
        Object obj2 = node._value;
        this._entrySet.remove(node);
        node._value = null;
        node._key = null;
        return obj2;
    }

    public Object get(String str) {
        if (str == null) {
            return this._nullValue;
        }
        Map.Entry entry = getEntry(str, 0, str.length());
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    public StringMap(boolean z2) {
        this();
        this._ignoreCase = z2;
    }

    public Map.Entry getEntry(char[] cArr, int i2, int i3) {
        if (cArr == null) {
            return this._nullEntry;
        }
        Node node = this._root;
        int i4 = -1;
        for (int i5 = 0; i5 < i3; i5++) {
            char c3 = cArr[i2 + i5];
            if (i4 == -1) {
                Node[] nodeArr = node._children;
                node = nodeArr == null ? null : nodeArr[c3 % this._width];
                i4 = 0;
            }
            while (node != null) {
                char[] cArr2 = node._char;
                if (cArr2[i4] != c3 && (!this._ignoreCase || node._ochar[i4] != c3)) {
                    if (i4 > 0) {
                        return null;
                    }
                    node = node._next;
                } else {
                    i4++;
                    if (i4 == cArr2.length) {
                        i4 = -1;
                    }
                }
            }
            return null;
        }
        if (i4 > 0) {
            return null;
        }
        if (node == null || node._key != null) {
            return node;
        }
        return null;
    }

    public StringMap(boolean z2, int i2) {
        this();
        this._ignoreCase = z2;
        this._width = i2;
    }
}
