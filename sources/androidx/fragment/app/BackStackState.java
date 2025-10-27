package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressLint({"BanParcelableUsage"})
/* loaded from: classes.dex */
class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>() { // from class: androidx.fragment.app.BackStackState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState[] newArray(int i2) {
            return new BackStackState[i2];
        }
    };
    final List<String> mFragments;
    final List<BackStackRecordState> mTransactions;

    public BackStackState(List<String> list, List<BackStackRecordState> list2) {
        this.mFragments = list;
        this.mTransactions = list2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @NonNull
    public List<BackStackRecord> instantiate(@NonNull FragmentManager fragmentManager, Map<String, Fragment> map) {
        HashMap map2 = new HashMap(this.mFragments.size());
        for (String str : this.mFragments) {
            Fragment fragment = map.get(str);
            if (fragment != null) {
                map2.put(fragment.mWho, fragment);
            } else {
                FragmentState savedState = fragmentManager.getFragmentStore().setSavedState(str, null);
                if (savedState != null) {
                    Fragment fragmentInstantiate = savedState.instantiate(fragmentManager.getFragmentFactory(), fragmentManager.getHost().getContext().getClassLoader());
                    map2.put(fragmentInstantiate.mWho, fragmentInstantiate);
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator<BackStackRecordState> it = this.mTransactions.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().instantiate(fragmentManager, map2));
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeStringList(this.mFragments);
        parcel.writeTypedList(this.mTransactions);
    }

    public BackStackState(@NonNull Parcel parcel) {
        this.mFragments = parcel.createStringArrayList();
        this.mTransactions = parcel.createTypedArrayList(BackStackRecordState.CREATOR);
    }
}
