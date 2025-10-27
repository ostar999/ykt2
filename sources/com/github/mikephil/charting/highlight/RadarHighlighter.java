package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

/* loaded from: classes3.dex */
public class RadarHighlighter extends PieRadarHighlighter<RadarChart> {
    public RadarHighlighter(RadarChart radarChart) {
        super(radarChart);
    }

    @Override // com.github.mikephil.charting.highlight.PieRadarHighlighter
    public Highlight getClosestHighlight(int i2, float f2, float f3) {
        List<Highlight> highlightsAtIndex = getHighlightsAtIndex(i2);
        float fDistanceToCenter = ((RadarChart) this.mChart).distanceToCenter(f2, f3) / ((RadarChart) this.mChart).getFactor();
        Highlight highlight = null;
        float f4 = Float.MAX_VALUE;
        for (int i3 = 0; i3 < highlightsAtIndex.size(); i3++) {
            Highlight highlight2 = highlightsAtIndex.get(i3);
            float fAbs = Math.abs(highlight2.getY() - fDistanceToCenter);
            if (fAbs < f4) {
                highlight = highlight2;
                f4 = fAbs;
            }
        }
        return highlight;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.github.mikephil.charting.data.BaseEntry, com.github.mikephil.charting.data.Entry] */
    public List<Highlight> getHighlightsAtIndex(int i2) {
        int i3 = i2;
        this.mHighlightBuffer.clear();
        float phaseX = ((RadarChart) this.mChart).getAnimator().getPhaseX();
        float phaseY = ((RadarChart) this.mChart).getAnimator().getPhaseY();
        float sliceAngle = ((RadarChart) this.mChart).getSliceAngle();
        float factor = ((RadarChart) this.mChart).getFactor();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        int i4 = 0;
        while (i4 < ((RadarData) ((RadarChart) this.mChart).getData()).getDataSetCount()) {
            IRadarDataSet dataSetByIndex = ((RadarData) ((RadarChart) this.mChart).getData()).getDataSetByIndex(i4);
            ?? entryForIndex = dataSetByIndex.getEntryForIndex(i3);
            float f2 = i3;
            Utils.getPosition(((RadarChart) this.mChart).getCenterOffsets(), (entryForIndex.getY() - ((RadarChart) this.mChart).getYChartMin()) * factor * phaseY, (sliceAngle * f2 * phaseX) + ((RadarChart) this.mChart).getRotationAngle(), mPPointF);
            this.mHighlightBuffer.add(new Highlight(f2, entryForIndex.getY(), mPPointF.f6566x, mPPointF.f6567y, i4, dataSetByIndex.getAxisDependency()));
            i4++;
            i3 = i2;
        }
        return this.mHighlightBuffer;
    }
}
