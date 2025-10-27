package com.github.mikephil.charting.data;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

/* loaded from: classes3.dex */
public class PieData extends ChartData<IPieDataSet> {
    public PieData() {
    }

    public IPieDataSet getDataSet() {
        return (IPieDataSet) this.mDataSets.get(0);
    }

    @Override // com.github.mikephil.charting.data.ChartData
    public Entry getEntryForHighlight(Highlight highlight) {
        return getDataSet().getEntryForIndex((int) highlight.getX());
    }

    public float getYValueSum() {
        float y2 = 0.0f;
        for (int i2 = 0; i2 < getDataSet().getEntryCount(); i2++) {
            y2 += getDataSet().getEntryForIndex(i2).getY();
        }
        return y2;
    }

    public void setDataSet(IPieDataSet iPieDataSet) {
        this.mDataSets.clear();
        this.mDataSets.add(iPieDataSet);
        notifyDataChanged();
    }

    public PieData(IPieDataSet iPieDataSet) {
        super(iPieDataSet);
    }

    @Override // com.github.mikephil.charting.data.ChartData
    public IPieDataSet getDataSetByIndex(int i2) {
        if (i2 == 0) {
            return getDataSet();
        }
        return null;
    }

    @Override // com.github.mikephil.charting.data.ChartData
    public IPieDataSet getDataSetByLabel(String str, boolean z2) {
        if (z2) {
            if (str.equalsIgnoreCase(((IPieDataSet) this.mDataSets.get(0)).getLabel())) {
                return (IPieDataSet) this.mDataSets.get(0);
            }
            return null;
        }
        if (str.equals(((IPieDataSet) this.mDataSets.get(0)).getLabel())) {
            return (IPieDataSet) this.mDataSets.get(0);
        }
        return null;
    }
}
