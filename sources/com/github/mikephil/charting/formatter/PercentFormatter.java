package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class PercentFormatter extends ValueFormatter {
    public DecimalFormat mFormat;
    private PieChart pieChart;

    public PercentFormatter() {
        this.mFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getFormattedValue(float f2) {
        return this.mFormat.format(f2) + " %";
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getPieLabel(float f2, PieEntry pieEntry) {
        PieChart pieChart = this.pieChart;
        return (pieChart == null || !pieChart.isUsePercentValuesEnabled()) ? this.mFormat.format(f2) : getFormattedValue(f2);
    }

    public PercentFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }
}
