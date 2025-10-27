package androidx.camera.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

@RequiresApi(21)
/* loaded from: classes.dex */
final class ForwardingLiveData<T> extends MediatorLiveData<T> {
    private LiveData<T> mLiveDataSource;

    @Override // androidx.lifecycle.LiveData
    public T getValue() {
        LiveData<T> liveData = this.mLiveDataSource;
        if (liveData == null) {
            return null;
        }
        return liveData.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSource(@NonNull LiveData<T> liveData) {
        LiveData<T> liveData2 = this.mLiveDataSource;
        if (liveData2 != null) {
            super.removeSource(liveData2);
        }
        this.mLiveDataSource = liveData;
        super.addSource(liveData, new Observer() { // from class: androidx.camera.view.e
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f1734a.setValue(obj);
            }
        });
    }
}
