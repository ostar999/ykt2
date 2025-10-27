package com.hyphenate.easeui.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseAdapterDelegatesManager {
    private SparseArrayCompat<String> dataTypeWithTags = new SparseArrayCompat<>();
    private SparseArrayCompat<EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder>> delegates = new SparseArrayCompat<>();
    public EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> fallbackDelegate;
    private boolean hasConsistItemType;

    public EaseAdapterDelegatesManager(boolean z2) {
        this.hasConsistItemType = z2;
    }

    private Type getParameterizedType(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return genericSuperclass;
        }
        if (cls.getName().equals("java.lang.Object")) {
            return null;
        }
        return getParameterizedType(cls.getSuperclass());
    }

    private String getTagByViewType(int i2) {
        if (this.dataTypeWithTags.containsKey(i2)) {
            String str = this.dataTypeWithTags.get(i2);
            return (!TextUtils.isEmpty(str) && str.contains(":")) ? str.split(":")[1] : str;
        }
        int itemViewType = i2 - (this.hasConsistItemType ? this.fallbackDelegate.getItemViewType() : this.delegates.size());
        if (this.fallbackDelegate.getTags().size() <= itemViewType) {
            return null;
        }
        return this.fallbackDelegate.getTags().get(itemViewType);
    }

    private List<Integer> indexesOfValue(SparseArrayCompat<String> sparseArrayCompat, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < sparseArrayCompat.size(); i2++) {
            if (TextUtils.equals(sparseArrayCompat.valueAt(i2), str)) {
                arrayList.add(Integer.valueOf(sparseArrayCompat.keyAt(i2)));
            }
        }
        return arrayList;
    }

    private Object targetItem(Object obj) {
        return obj;
    }

    private String targetTag(Object obj) {
        if (!(obj instanceof EMMessage) || this.delegates.isEmpty()) {
            return "";
        }
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.delegates.size()) {
                z2 = true;
                break;
            }
            if (!(this.delegates.get(this.delegates.indexOfKey(i2)) instanceof EaseMessageAdapterDelegate)) {
                break;
            }
            i2++;
        }
        return z2 ? ((EMMessage) obj).direct().toString() : "";
    }

    private String typeWithTag(Class<?> cls, String str) {
        if (TextUtils.isEmpty(str)) {
            return cls.getName();
        }
        return cls.getName() + ":" + str;
    }

    public EaseAdapterDelegatesManager addDelegate(EaseAdapterDelegate<?, ?> easeAdapterDelegate, String str) {
        Type parameterizedType = getParameterizedType(easeAdapterDelegate.getClass());
        if (!(parameterizedType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(String.format("Please set the correct generic parameters on %s.", easeAdapterDelegate.getClass().getName()));
        }
        String strTypeWithTag = typeWithTag((Class) ((ParameterizedType) parameterizedType).getActualTypeArguments()[0], str);
        int itemViewType = this.hasConsistItemType ? easeAdapterDelegate.getItemViewType() : this.delegates.size();
        this.delegates.put(itemViewType, easeAdapterDelegate);
        this.dataTypeWithTags.put(itemViewType, strTypeWithTag);
        return this;
    }

    public List<EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder>> getAllDelegates() {
        ArrayList arrayList = new ArrayList();
        if (!this.delegates.isEmpty()) {
            for (int i2 = 0; i2 < this.delegates.size(); i2++) {
                arrayList.add(this.delegates.valueAt(i2));
            }
        }
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate = this.fallbackDelegate;
        if (easeAdapterDelegate != null) {
            arrayList.add(easeAdapterDelegate);
        }
        return arrayList;
    }

    @Nullable
    public EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> getDelegate(int i2) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate = this.delegates.get(i2);
        return easeAdapterDelegate == null ? this.fallbackDelegate : easeAdapterDelegate;
    }

    public int getDelegateViewType(EaseAdapterDelegate easeAdapterDelegate) {
        int iIndexOfValue = this.delegates.indexOfValue(easeAdapterDelegate);
        if (iIndexOfValue > 0) {
            return this.delegates.keyAt(iIndexOfValue);
        }
        return -1;
    }

    public int getItemViewType(Object obj, int i2) {
        Class<?> cls = targetItem(obj).getClass();
        String strTargetTag = targetTag(obj);
        Iterator<Integer> it = indexesOfValue(this.dataTypeWithTags, typeWithTag(cls, strTargetTag)).iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate = this.delegates.get(iIntValue);
            if (easeAdapterDelegate != null && easeAdapterDelegate.getTags().contains(strTargetTag) && easeAdapterDelegate.isForViewType(obj, i2)) {
                return this.hasConsistItemType ? easeAdapterDelegate.getItemViewType() : iIntValue;
            }
        }
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate2 = this.fallbackDelegate;
        if (easeAdapterDelegate2 != null && easeAdapterDelegate2.isForViewType(obj, i2)) {
            return (this.hasConsistItemType ? this.fallbackDelegate.getItemViewType() : this.delegates.size()) + (this.fallbackDelegate.getTags().contains(strTargetTag) ? this.fallbackDelegate.getTags().indexOf(strTargetTag) : 0);
        }
        throw new NullPointerException("No EaseAdapterDelegate added that matches position = " + i2 + " item = " + targetItem(obj) + " in data source.");
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        for (int i2 = 0; i2 < this.delegates.size(); i2++) {
            EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate = this.delegates.get(i2);
            if (easeAdapterDelegate != null) {
                easeAdapterDelegate.onAttachedToRecyclerView(recyclerView);
            }
        }
    }

    public void onBindViewHolder(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder, int i2, Object obj) {
        int itemViewType = viewHolder.getAdapter().getItemViewType(i2);
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(itemViewType);
        if (delegate != null) {
            delegate.onBindViewHolder(viewHolder, i2, targetItem(obj));
            return;
        }
        throw new NullPointerException("No delegate found for item at position = " + i2 + " for viewType = " + itemViewType);
    }

    public EaseBaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(i2);
        if (delegate != null) {
            return (EaseBaseRecyclerViewAdapter.ViewHolder) delegate.onCreateViewHolder(viewGroup, getTagByViewType(i2));
        }
        throw new NullPointerException("No EaseAdapterDelegate added for ViewType " + i2);
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        for (int i2 = 0; i2 < this.delegates.size(); i2++) {
            EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> easeAdapterDelegate = this.delegates.get(i2);
            if (easeAdapterDelegate != null) {
                easeAdapterDelegate.onDetachedFromRecyclerView(recyclerView);
            }
        }
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder viewHolder) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(viewHolder.getItemViewType());
        return delegate != null && delegate.onFailedToRecycleView(viewHolder);
    }

    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(viewHolder.getItemViewType());
        if (delegate != null) {
            delegate.onViewAttachedToWindow(viewHolder);
        }
    }

    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(viewHolder.getItemViewType());
        if (delegate != null) {
            delegate.onViewDetachedFromWindow(viewHolder);
        }
    }

    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(viewHolder.getItemViewType());
        if (delegate != null) {
            delegate.onViewRecycled(viewHolder);
        }
    }

    public void onBindViewHolder(@NonNull EaseBaseRecyclerViewAdapter.ViewHolder viewHolder, int i2, @NonNull List<Object> list, Object obj) {
        int itemViewType = viewHolder.getAdapter().getItemViewType(i2);
        EaseAdapterDelegate<Object, EaseBaseRecyclerViewAdapter.ViewHolder> delegate = getDelegate(itemViewType);
        if (delegate != null) {
            delegate.onBindViewHolder(viewHolder, i2, list, targetItem(obj));
            return;
        }
        throw new NullPointerException("No delegate found for item at position = " + i2 + " for viewType = " + itemViewType);
    }
}
