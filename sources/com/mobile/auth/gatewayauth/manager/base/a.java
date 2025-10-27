package com.mobile.auth.gatewayauth.manager.base;

import android.util.SparseArray;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class a<T> extends SparseArray<T> {
    public a(int i2) {
        super(i2);
    }

    @Override // android.util.SparseArray
    public synchronized void append(int i2, T t2) {
        try {
            super.append(i2, t2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void clear() {
        try {
            super.clear();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void delete(int i2) {
        try {
            super.delete(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized T get(int i2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (T) super.get(i2);
    }

    @Override // android.util.SparseArray
    public synchronized T get(int i2, T t2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (T) super.get(i2, t2);
    }

    @Override // android.util.SparseArray
    public synchronized int indexOfKey(int i2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return super.indexOfKey(i2);
    }

    @Override // android.util.SparseArray
    public synchronized int indexOfValue(T t2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return super.indexOfValue(t2);
    }

    @Override // android.util.SparseArray
    public synchronized int keyAt(int i2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return super.keyAt(i2);
    }

    @Override // android.util.SparseArray
    public synchronized void put(int i2, T t2) {
        try {
            super.put(i2, t2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void remove(int i2) {
        try {
            super.remove(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void removeAt(int i2) {
        try {
            super.removeAt(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void removeAtRange(int i2, int i3) {
        try {
            super.removeAtRange(i2, i3);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized void setValueAt(int i2, T t2) {
        try {
            super.setValueAt(i2, t2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // android.util.SparseArray
    public synchronized int size() {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
        return super.size();
    }

    @Override // android.util.SparseArray
    public synchronized T valueAt(int i2) {
        try {
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return (T) super.valueAt(i2);
    }
}
