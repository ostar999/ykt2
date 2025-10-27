package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class HorizontalBarHighlighter extends BarHighlighter {
    public HorizontalBarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public List<Highlight> buildHighlights(IDataSet iDataSet, int i2, float f2, DataSet.Rounding rounding) {
        Entry entryForXValue;
        ArrayList arrayList = new ArrayList();
        List<Entry> entriesForXValue = iDataSet.getEntriesForXValue(f2);
        if (entriesForXValue.size() == 0 && (entryForXValue = iDataSet.getEntryForXValue(f2, Float.NaN, rounding)) != null) {
            entriesForXValue = iDataSet.getEntriesForXValue(entryForXValue.getX());
        }
        if (entriesForXValue.size() == 0) {
            return arrayList;
        }
        for (Entry entry : entriesForXValue) {
            MPPointD pixelForValues = ((BarDataProvider) this.mChart).getTransformer(iDataSet.getAxisDependency()).getPixelForValues(entry.getY(), entry.getX());
            arrayList.add(new Highlight(entry.getX(), entry.getY(), (float) pixelForValues.f6564x, (float) pixelForValues.f6565y, i2, iDataSet.getAxisDependency()));
        }
        return arrayList;
    }

    @Override // com.github.mikephil.charting.highlight.BarHighlighter, com.github.mikephil.charting.highlight.ChartHighlighter
    public float getDistance(float f2, float f3, float f4, float f5) {
        return Math.abs(f3 - f5);
    }

    @Override // com.github.mikephil.charting.highlight.BarHighlighter, com.github.mikephil.charting.highlight.ChartHighlighter, com.github.mikephil.charting.highlight.IHighlighter
    public Highlight getHighlight(float f2, float f3) {
        BarData barData = ((BarDataProvider) this.mChart).getBarData();
        MPPointD valsForTouch = getValsForTouch(f3, f2);
        Highlight highlightForX = getHighlightForX((float) valsForTouch.f6565y, f3, f2);
        if (highlightForX == null) {
            return null;
        }
        IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(highlightForX.getDataSetIndex());
        if (iBarDataSet.isStacked()) {
            return getStackedHighlight(highlightForX, iBarDataSet, (float) valsForTouch.f6565y, (float) valsForTouch.f6564x);
        }
        MPPointD.recycleInstance(valsForTouch);
        return highlightForX;
    }
}
