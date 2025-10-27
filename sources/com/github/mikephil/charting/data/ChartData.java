package com.github.mikephil.charting.data;

import android.graphics.Typeface;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ChartData<T extends IDataSet<? extends Entry>> {
    protected List<T> mDataSets;
    protected float mLeftAxisMax;
    protected float mLeftAxisMin;
    protected float mRightAxisMax;
    protected float mRightAxisMin;
    protected float mXMax;
    protected float mXMin;
    protected float mYMax;
    protected float mYMin;

    public ChartData() {
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        this.mDataSets = new ArrayList();
    }

    private List<T> arrayToList(T[] tArr) {
        ArrayList arrayList = new ArrayList();
        for (T t2 : tArr) {
            arrayList.add(t2);
        }
        return arrayList;
    }

    public void addDataSet(T t2) {
        if (t2 == null) {
            return;
        }
        calcMinMax(t2);
        this.mDataSets.add(t2);
    }

    public void addEntry(Entry entry, int i2) {
        if (this.mDataSets.size() <= i2 || i2 < 0) {
            Log.e("addEntry", "Cannot add Entry because dataSetIndex too high or too low.");
            return;
        }
        T t2 = this.mDataSets.get(i2);
        if (t2.addEntry(entry)) {
            calcMinMax(entry, t2.getAxisDependency());
        }
    }

    public void calcMinMax() {
        List<T> list = this.mDataSets;
        if (list == null) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            calcMinMax(it.next());
        }
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        IDataSet firstLeft = getFirstLeft(this.mDataSets);
        if (firstLeft != null) {
            this.mLeftAxisMax = firstLeft.getYMax();
            this.mLeftAxisMin = firstLeft.getYMin();
            for (T t2 : this.mDataSets) {
                if (t2.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                    if (t2.getYMin() < this.mLeftAxisMin) {
                        this.mLeftAxisMin = t2.getYMin();
                    }
                    if (t2.getYMax() > this.mLeftAxisMax) {
                        this.mLeftAxisMax = t2.getYMax();
                    }
                }
            }
        }
        IDataSet firstRight = getFirstRight(this.mDataSets);
        if (firstRight != null) {
            this.mRightAxisMax = firstRight.getYMax();
            this.mRightAxisMin = firstRight.getYMin();
            for (T t3 : this.mDataSets) {
                if (t3.getAxisDependency() == YAxis.AxisDependency.RIGHT) {
                    if (t3.getYMin() < this.mRightAxisMin) {
                        this.mRightAxisMin = t3.getYMin();
                    }
                    if (t3.getYMax() > this.mRightAxisMax) {
                        this.mRightAxisMax = t3.getYMax();
                    }
                }
            }
        }
    }

    public void calcMinMaxY(float f2, float f3) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().calcMinMaxY(f2, f3);
        }
        calcMinMax();
    }

    public void clearValues() {
        List<T> list = this.mDataSets;
        if (list != null) {
            list.clear();
        }
        notifyDataChanged();
    }

    public boolean contains(T t2) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            if (it.next().equals(t2)) {
                return true;
            }
        }
        return false;
    }

    public int[] getColors() {
        if (this.mDataSets == null) {
            return null;
        }
        int size = 0;
        for (int i2 = 0; i2 < this.mDataSets.size(); i2++) {
            size += this.mDataSets.get(i2).getColors().size();
        }
        int[] iArr = new int[size];
        int i3 = 0;
        for (int i4 = 0; i4 < this.mDataSets.size(); i4++) {
            Iterator<Integer> it = this.mDataSets.get(i4).getColors().iterator();
            while (it.hasNext()) {
                iArr[i3] = it.next().intValue();
                i3++;
            }
        }
        return iArr;
    }

    public T getDataSetByIndex(int i2) {
        List<T> list = this.mDataSets;
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return null;
        }
        return this.mDataSets.get(i2);
    }

    public T getDataSetByLabel(String str, boolean z2) {
        int dataSetIndexByLabel = getDataSetIndexByLabel(this.mDataSets, str, z2);
        if (dataSetIndexByLabel < 0 || dataSetIndexByLabel >= this.mDataSets.size()) {
            return null;
        }
        return this.mDataSets.get(dataSetIndexByLabel);
    }

    public int getDataSetCount() {
        List<T> list = this.mDataSets;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public T getDataSetForEntry(Entry entry) {
        if (entry == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.mDataSets.size(); i2++) {
            T t2 = this.mDataSets.get(i2);
            for (int i3 = 0; i3 < t2.getEntryCount(); i3++) {
                if (entry.equalTo(t2.getEntryForXValue(entry.getX(), entry.getY()))) {
                    return t2;
                }
            }
        }
        return null;
    }

    public int getDataSetIndexByLabel(List<T> list, String str, boolean z2) {
        int i2 = 0;
        if (z2) {
            while (i2 < list.size()) {
                if (str.equalsIgnoreCase(list.get(i2).getLabel())) {
                    return i2;
                }
                i2++;
            }
            return -1;
        }
        while (i2 < list.size()) {
            if (str.equals(list.get(i2).getLabel())) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public String[] getDataSetLabels() {
        String[] strArr = new String[this.mDataSets.size()];
        for (int i2 = 0; i2 < this.mDataSets.size(); i2++) {
            strArr[i2] = this.mDataSets.get(i2).getLabel();
        }
        return strArr;
    }

    public List<T> getDataSets() {
        return this.mDataSets;
    }

    public int getEntryCount() {
        Iterator<T> it = this.mDataSets.iterator();
        int entryCount = 0;
        while (it.hasNext()) {
            entryCount += it.next().getEntryCount();
        }
        return entryCount;
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataSetIndex() >= this.mDataSets.size()) {
            return null;
        }
        return this.mDataSets.get(highlight.getDataSetIndex()).getEntryForXValue(highlight.getX(), highlight.getY());
    }

    public T getFirstLeft(List<T> list) {
        for (T t2 : list) {
            if (t2.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                return t2;
            }
        }
        return null;
    }

    public T getFirstRight(List<T> list) {
        for (T t2 : list) {
            if (t2.getAxisDependency() == YAxis.AxisDependency.RIGHT) {
                return t2;
            }
        }
        return null;
    }

    public int getIndexOfDataSet(T t2) {
        return this.mDataSets.indexOf(t2);
    }

    public T getMaxEntryCountSet() {
        List<T> list = this.mDataSets;
        if (list == null || list.isEmpty()) {
            return null;
        }
        T t2 = this.mDataSets.get(0);
        for (T t3 : this.mDataSets) {
            if (t3.getEntryCount() > t2.getEntryCount()) {
                t2 = t3;
            }
        }
        return t2;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public float getYMin() {
        return this.mYMin;
    }

    public boolean isHighlightEnabled() {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            if (!it.next().isHighlightEnabled()) {
                return false;
            }
        }
        return true;
    }

    public void notifyDataChanged() {
        calcMinMax();
    }

    public boolean removeDataSet(T t2) {
        if (t2 == null) {
            return false;
        }
        boolean zRemove = this.mDataSets.remove(t2);
        if (zRemove) {
            calcMinMax();
        }
        return zRemove;
    }

    public boolean removeEntry(Entry entry, int i2) {
        T t2;
        if (entry == null || i2 >= this.mDataSets.size() || (t2 = this.mDataSets.get(i2)) == null) {
            return false;
        }
        boolean zRemoveEntry = t2.removeEntry(entry);
        if (zRemoveEntry) {
            calcMinMax();
        }
        return zRemoveEntry;
    }

    public void setDrawValues(boolean z2) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setDrawValues(z2);
        }
    }

    public void setHighlightEnabled(boolean z2) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setHighlightEnabled(z2);
        }
    }

    public void setValueFormatter(ValueFormatter valueFormatter) {
        if (valueFormatter == null) {
            return;
        }
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setValueFormatter(valueFormatter);
        }
    }

    public void setValueTextColor(int i2) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setValueTextColor(i2);
        }
    }

    public void setValueTextColors(List<Integer> list) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setValueTextColors(list);
        }
    }

    public void setValueTextSize(float f2) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setValueTextSize(f2);
        }
    }

    public void setValueTypeface(Typeface typeface) {
        Iterator<T> it = this.mDataSets.iterator();
        while (it.hasNext()) {
            it.next().setValueTypeface(typeface);
        }
    }

    public float getYMax(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2 = this.mLeftAxisMax;
            return f2 == -3.4028235E38f ? this.mRightAxisMax : f2;
        }
        float f3 = this.mRightAxisMax;
        return f3 == -3.4028235E38f ? this.mLeftAxisMax : f3;
    }

    public float getYMin(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2 = this.mLeftAxisMin;
            return f2 == Float.MAX_VALUE ? this.mRightAxisMin : f2;
        }
        float f3 = this.mRightAxisMin;
        return f3 == Float.MAX_VALUE ? this.mLeftAxisMin : f3;
    }

    public boolean removeDataSet(int i2) {
        if (i2 >= this.mDataSets.size() || i2 < 0) {
            return false;
        }
        return removeDataSet((ChartData<T>) this.mDataSets.get(i2));
    }

    public boolean removeEntry(float f2, int i2) {
        Entry entryForXValue;
        if (i2 < this.mDataSets.size() && (entryForXValue = this.mDataSets.get(i2).getEntryForXValue(f2, Float.NaN)) != null) {
            return removeEntry(entryForXValue, i2);
        }
        return false;
    }

    public ChartData(T... tArr) {
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        this.mDataSets = arrayToList(tArr);
        notifyDataChanged();
    }

    public ChartData(List<T> list) {
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        this.mDataSets = list;
        notifyDataChanged();
    }

    public void calcMinMax(Entry entry, YAxis.AxisDependency axisDependency) {
        if (this.mYMax < entry.getY()) {
            this.mYMax = entry.getY();
        }
        if (this.mYMin > entry.getY()) {
            this.mYMin = entry.getY();
        }
        if (this.mXMax < entry.getX()) {
            this.mXMax = entry.getX();
        }
        if (this.mXMin > entry.getX()) {
            this.mXMin = entry.getX();
        }
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax < entry.getY()) {
                this.mLeftAxisMax = entry.getY();
            }
            if (this.mLeftAxisMin > entry.getY()) {
                this.mLeftAxisMin = entry.getY();
                return;
            }
            return;
        }
        if (this.mRightAxisMax < entry.getY()) {
            this.mRightAxisMax = entry.getY();
        }
        if (this.mRightAxisMin > entry.getY()) {
            this.mRightAxisMin = entry.getY();
        }
    }

    public void calcMinMax(T t2) {
        if (this.mYMax < t2.getYMax()) {
            this.mYMax = t2.getYMax();
        }
        if (this.mYMin > t2.getYMin()) {
            this.mYMin = t2.getYMin();
        }
        if (this.mXMax < t2.getXMax()) {
            this.mXMax = t2.getXMax();
        }
        if (this.mXMin > t2.getXMin()) {
            this.mXMin = t2.getXMin();
        }
        if (t2.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax < t2.getYMax()) {
                this.mLeftAxisMax = t2.getYMax();
            }
            if (this.mLeftAxisMin > t2.getYMin()) {
                this.mLeftAxisMin = t2.getYMin();
                return;
            }
            return;
        }
        if (this.mRightAxisMax < t2.getYMax()) {
            this.mRightAxisMax = t2.getYMax();
        }
        if (this.mRightAxisMin > t2.getYMin()) {
            this.mRightAxisMin = t2.getYMin();
        }
    }
}
