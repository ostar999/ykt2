package com.actionbarsherlock.widget.wheelview.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AbstractWheelAdapter implements WheelViewAdapter {
    private List<DataSetObserver> datasetObservers;

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public View getEmptyItem(View view, ViewGroup viewGroup) {
        return null;
    }

    public void notifyDataChangedEvent() {
        List<DataSetObserver> list = this.datasetObservers;
        if (list != null) {
            Iterator<DataSetObserver> it = list.iterator();
            while (it.hasNext()) {
                it.next().onChanged();
            }
        }
    }

    public void notifyDataInvalidatedEvent() {
        List<DataSetObserver> list = this.datasetObservers;
        if (list != null) {
            Iterator<DataSetObserver> it = list.iterator();
            while (it.hasNext()) {
                it.next().onInvalidated();
            }
        }
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.datasetObservers == null) {
            this.datasetObservers = new LinkedList();
        }
        this.datasetObservers.add(dataSetObserver);
    }

    @Override // com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        List<DataSetObserver> list = this.datasetObservers;
        if (list != null) {
            list.remove(dataSetObserver);
        }
    }
}
