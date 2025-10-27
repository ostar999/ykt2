package org.eclipse.jetty.util.component;

import java.lang.ref.WeakReference;
import java.util.EventListener;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class Container {
    private static final Logger LOG = Log.getLogger((Class<?>) Container.class);
    private final CopyOnWriteArrayList<Listener> _listeners = new CopyOnWriteArrayList<>();

    public interface Listener extends EventListener {
        void add(Relationship relationship);

        void addBean(Object obj);

        void remove(Relationship relationship);

        void removeBean(Object obj);
    }

    public static class Relationship {
        private final WeakReference<Object> _child;
        private Container _container;
        private final WeakReference<Object> _parent;
        private String _relationship;

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Relationship)) {
                return false;
            }
            Relationship relationship = (Relationship) obj;
            return relationship._parent.get() == this._parent.get() && relationship._child.get() == this._child.get() && relationship._relationship.equals(this._relationship);
        }

        public Object getChild() {
            return this._child.get();
        }

        public Container getContainer() {
            return this._container;
        }

        public Object getParent() {
            return this._parent.get();
        }

        public String getRelationship() {
            return this._relationship;
        }

        public int hashCode() {
            return this._parent.hashCode() + this._child.hashCode() + this._relationship.hashCode();
        }

        public String toString() {
            return this._parent + "---" + this._relationship + "-->" + this._child;
        }

        private Relationship(Container container, Object obj, Object obj2, String str) {
            this._container = container;
            this._parent = new WeakReference<>(obj);
            this._child = new WeakReference<>(obj2);
            this._relationship = str;
        }
    }

    private void add(Object obj, Object obj2, String str) {
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("Container " + obj + " + " + obj2 + " as " + str, new Object[0]);
        }
        if (this._listeners != null) {
            Relationship relationship = new Relationship(obj, obj2, str);
            for (int i2 = 0; i2 < LazyList.size(this._listeners); i2++) {
                ((Listener) LazyList.get(this._listeners, i2)).add(relationship);
            }
        }
    }

    private void remove(Object obj, Object obj2, String str) {
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("Container " + obj + " - " + obj2 + " as " + str, new Object[0]);
        }
        if (this._listeners != null) {
            Relationship relationship = new Relationship(obj, obj2, str);
            for (int i2 = 0; i2 < LazyList.size(this._listeners); i2++) {
                ((Listener) LazyList.get(this._listeners, i2)).remove(relationship);
            }
        }
    }

    public void addBean(Object obj) {
        if (this._listeners != null) {
            for (int i2 = 0; i2 < LazyList.size(this._listeners); i2++) {
                ((Listener) LazyList.get(this._listeners, i2)).addBean(obj);
            }
        }
    }

    public void addEventListener(Listener listener) {
        this._listeners.add(listener);
    }

    public void removeBean(Object obj) {
        if (this._listeners != null) {
            for (int i2 = 0; i2 < LazyList.size(this._listeners); i2++) {
                ((Listener) LazyList.get(this._listeners, i2)).removeBean(obj);
            }
        }
    }

    public void removeEventListener(Listener listener) {
        this._listeners.remove(listener);
    }

    public void update(Object obj, Object obj2, Object obj3, String str) {
        if (obj2 != null && !obj2.equals(obj3)) {
            remove(obj, obj2, str);
        }
        if (obj3 == null || obj3.equals(obj2)) {
            return;
        }
        add(obj, obj3, str);
    }

    public void update(Object obj, Object obj2, Object obj3, String str, boolean z2) {
        if (obj2 != null && !obj2.equals(obj3)) {
            remove(obj, obj2, str);
            if (z2) {
                removeBean(obj2);
            }
        }
        if (obj3 == null || obj3.equals(obj2)) {
            return;
        }
        if (z2) {
            addBean(obj3);
        }
        add(obj, obj3, str);
    }

    public void update(Object obj, Object[] objArr, Object[] objArr2, String str) {
        update(obj, objArr, objArr2, str, false);
    }

    public void update(Object obj, Object[] objArr, Object[] objArr2, String str, boolean z2) {
        Object[] objArr3 = null;
        if (objArr2 != null) {
            Object[] objArr4 = new Object[objArr2.length];
            int length = objArr2.length;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                boolean z3 = true;
                if (objArr != null) {
                    int length2 = objArr.length;
                    while (true) {
                        int i3 = length2 - 1;
                        if (length2 <= 0) {
                            break;
                        }
                        Object obj2 = objArr2[i2];
                        if (obj2 != null && obj2.equals(objArr[i3])) {
                            objArr[i3] = null;
                            z3 = false;
                        }
                        length2 = i3;
                    }
                }
                if (z3) {
                    objArr4[i2] = objArr2[i2];
                }
                length = i2;
            }
            objArr3 = objArr4;
        }
        if (objArr != null) {
            int length3 = objArr.length;
            while (true) {
                int i4 = length3 - 1;
                if (length3 <= 0) {
                    break;
                }
                Object obj3 = objArr[i4];
                if (obj3 != null) {
                    remove(obj, obj3, str);
                    if (z2) {
                        removeBean(objArr[i4]);
                    }
                }
                length3 = i4;
            }
        }
        if (objArr3 != null) {
            for (int i5 = 0; i5 < objArr3.length; i5++) {
                Object obj4 = objArr3[i5];
                if (obj4 != null) {
                    if (z2) {
                        addBean(obj4);
                    }
                    add(obj, objArr3[i5], str);
                }
            }
        }
    }
}
