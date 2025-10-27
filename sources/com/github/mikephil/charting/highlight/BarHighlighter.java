package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointD;

/* loaded from: classes3.dex */
public class BarHighlighter extends ChartHighlighter<BarDataProvider> {
    public BarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    public int getClosestStackIndex(Range[] rangeArr, float f2) {
        if (rangeArr == null || rangeArr.length == 0) {
            return 0;
        }
        int i2 = 0;
        for (Range range : rangeArr) {
            if (range.contains(f2)) {
                return i2;
            }
            i2++;
        }
        int iMax = Math.max(rangeArr.length - 1, 0);
        if (f2 > rangeArr[iMax].to) {
            return iMax;
        }
        return 0;
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public BarLineScatterCandleBubbleData getData() {
        return ((BarDataProvider) this.mChart).getBarData();
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter
    public float getDistance(float f2, float f3, float f4, float f5) {
        return Math.abs(f2 - f4);
    }

    @Override // com.github.mikephil.charting.highlight.ChartHighlighter, com.github.mikephil.charting.highlight.IHighlighter
    public Highlight getHighlight(float f2, float f3) {
        Highlight highlight = super.getHighlight(f2, f3);
        if (highlight == null) {
            return null;
        }
        MPPointD valsForTouch = getValsForTouch(f2, f3);
        IBarDataSet iBarDataSet = (IBarDataSet) ((BarDataProvider) this.mChart).getBarData().getDataSetByIndex(highlight.getDataSetIndex());
        if (iBarDataSet.isStacked()) {
            return getStackedHighlight(highlight, iBarDataSet, (float) valsForTouch.f6564x, (float) valsForTouch.f6565y);
        }
        MPPointD.recycleInstance(valsForTouch);
        return highlight;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Highlight getStackedHighlight(Highlight highlight, IBarDataSet iBarDataSet, float f2, float f3) {
        BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForXValue(f2, f3);
        if (barEntry == null) {
            return null;
        }
        if (barEntry.getYVals() == null) {
            return highlight;
        }
        Range[] ranges = barEntry.getRanges();
        if (ranges.length <= 0) {
            return null;
        }
        int closestStackIndex = getClosestStackIndex(ranges, f3);
        MPPointD pixelForValues = ((BarDataProvider) this.mChart).getTransformer(iBarDataSet.getAxisDependency()).getPixelForValues(highlight.getX(), ranges[closestStackIndex].to);
        Highlight highlight2 = new Highlight(barEntry.getX(), barEntry.getY(), (float) pixelForValues.f6564x, (float) pixelForValues.f6565y, highlight.getDataSetIndex(), closestStackIndex, highlight.getAxis());
        MPPointD.recycleInstance(pixelForValues);
        return highlight2;
    }
}
