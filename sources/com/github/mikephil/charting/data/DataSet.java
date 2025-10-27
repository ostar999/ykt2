package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class DataSet<T extends Entry> extends BaseDataSet<T> {
    protected List<T> mValues;
    protected float mXMax;
    protected float mXMin;
    protected float mYMax;
    protected float mYMin;

    public enum Rounding {
        UP,
        DOWN,
        CLOSEST
    }

    public DataSet(List<T> list, String str) {
        super(str);
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mValues = list;
        if (list == null) {
            this.mValues = new ArrayList();
        }
        calcMinMax();
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public boolean addEntry(T t2) {
        if (t2 == null) {
            return false;
        }
        List<T> values = getValues();
        if (values == null) {
            values = new ArrayList<>();
        }
        calcMinMax(t2);
        return values.add(t2);
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public void addEntryOrdered(T t2) {
        if (t2 == null) {
            return;
        }
        if (this.mValues == null) {
            this.mValues = new ArrayList();
        }
        calcMinMax(t2);
        if (this.mValues.size() > 0) {
            if (this.mValues.get(r0.size() - 1).getX() > t2.getX()) {
                this.mValues.add(getEntryIndex(t2.getX(), t2.getY(), Rounding.UP), t2);
                return;
            }
        }
        this.mValues.add(t2);
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public void calcMinMax() {
        List<T> list = this.mValues;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        Iterator<T> it = this.mValues.iterator();
        while (it.hasNext()) {
            calcMinMax(it.next());
        }
    }

    public void calcMinMaxX(T t2) {
        if (t2.getX() < this.mXMin) {
            this.mXMin = t2.getX();
        }
        if (t2.getX() > this.mXMax) {
            this.mXMax = t2.getX();
        }
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public void calcMinMaxY(float f2, float f3) {
        List<T> list = this.mValues;
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        int entryIndex = getEntryIndex(f3, Float.NaN, Rounding.UP);
        for (int entryIndex2 = getEntryIndex(f2, Float.NaN, Rounding.DOWN); entryIndex2 <= entryIndex; entryIndex2++) {
            calcMinMaxY(this.mValues.get(entryIndex2));
        }
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public void clear() {
        this.mValues.clear();
        notifyDataSetChanged();
    }

    public abstract DataSet<T> copy();

    public void copy(DataSet dataSet) {
        super.copy((BaseDataSet) dataSet);
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public List<T> getEntriesForXValue(float f2) {
        ArrayList arrayList = new ArrayList();
        int size = this.mValues.size() - 1;
        int i2 = 0;
        while (true) {
            if (i2 > size) {
                break;
            }
            int i3 = (size + i2) / 2;
            T t2 = this.mValues.get(i3);
            if (f2 == t2.getX()) {
                while (i3 > 0 && this.mValues.get(i3 - 1).getX() == f2) {
                    i3--;
                }
                int size2 = this.mValues.size();
                while (i3 < size2) {
                    T t3 = this.mValues.get(i3);
                    if (t3.getX() != f2) {
                        break;
                    }
                    arrayList.add(t3);
                    i3++;
                }
            } else if (f2 > t2.getX()) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        return arrayList;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public int getEntryCount() {
        return this.mValues.size();
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public T getEntryForIndex(int i2) {
        return this.mValues.get(i2);
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public T getEntryForXValue(float f2, float f3, Rounding rounding) {
        int entryIndex = getEntryIndex(f2, f3, rounding);
        if (entryIndex > -1) {
            return this.mValues.get(entryIndex);
        }
        return null;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public int getEntryIndex(Entry entry) {
        return this.mValues.indexOf(entry);
    }

    public List<T> getValues() {
        return this.mValues;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public float getXMax() {
        return this.mXMax;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public float getXMin() {
        return this.mXMin;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public float getYMax() {
        return this.mYMax;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public float getYMin() {
        return this.mYMin;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public boolean removeEntry(T t2) {
        List<T> list;
        if (t2 == null || (list = this.mValues) == null) {
            return false;
        }
        boolean zRemove = list.remove(t2);
        if (zRemove) {
            calcMinMax();
        }
        return zRemove;
    }

    public void setValues(List<T> list) {
        this.mValues = list;
        notifyDataSetChanged();
    }

    public String toSimpleString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append("DataSet, label: ");
        sb.append(getLabel() == null ? "" : getLabel());
        sb.append(", entries: ");
        sb.append(this.mValues.size());
        sb.append("\n");
        stringBuffer.append(sb.toString());
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(toSimpleString());
        for (int i2 = 0; i2 < this.mValues.size(); i2++) {
            stringBuffer.append(this.mValues.get(i2).toString() + " ");
        }
        return stringBuffer.toString();
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public int getEntryIndex(float f2, float f3, Rounding rounding) {
        int i2;
        T t2;
        List<T> list = this.mValues;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int size = this.mValues.size() - 1;
        int i3 = 0;
        while (i3 < size) {
            int i4 = (i3 + size) / 2;
            float x2 = this.mValues.get(i4).getX() - f2;
            int i5 = i4 + 1;
            float x3 = this.mValues.get(i5).getX() - f2;
            float fAbs = Math.abs(x2);
            float fAbs2 = Math.abs(x3);
            if (fAbs2 >= fAbs) {
                if (fAbs >= fAbs2) {
                    double d3 = x2;
                    if (d3 < 0.0d) {
                        if (d3 < 0.0d) {
                        }
                    }
                }
                size = i4;
            }
            i3 = i5;
        }
        if (size == -1) {
            return size;
        }
        float x4 = this.mValues.get(size).getX();
        if (rounding == Rounding.UP) {
            if (x4 < f2 && size < this.mValues.size() - 1) {
                size++;
            }
        } else if (rounding == Rounding.DOWN && x4 > f2 && size > 0) {
            size--;
        }
        if (Float.isNaN(f3)) {
            return size;
        }
        while (size > 0 && this.mValues.get(size - 1).getX() == x4) {
            size--;
        }
        float y2 = this.mValues.get(size).getY();
        loop2: while (true) {
            i2 = size;
            do {
                size++;
                if (size >= this.mValues.size()) {
                    break loop2;
                }
                t2 = this.mValues.get(size);
                if (t2.getX() != x4) {
                    break loop2;
                }
            } while (Math.abs(t2.getY() - f3) >= Math.abs(y2 - f3));
            y2 = f3;
        }
        return i2;
    }

    @Override // com.github.mikephil.charting.interfaces.datasets.IDataSet
    public T getEntryForXValue(float f2, float f3) {
        return (T) getEntryForXValue(f2, f3, Rounding.CLOSEST);
    }

    public void calcMinMaxY(T t2) {
        if (t2.getY() < this.mYMin) {
            this.mYMin = t2.getY();
        }
        if (t2.getY() > this.mYMax) {
            this.mYMax = t2.getY();
        }
    }

    public void calcMinMax(T t2) {
        if (t2 == null) {
            return;
        }
        calcMinMaxX(t2);
        calcMinMaxY(t2);
    }
}
