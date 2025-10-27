package cn.webdemo.com.supporfragment.anim;

import android.os.Parcel;
import android.os.Parcelable;
import cn.webdemo.com.supporfragment.R;

/* loaded from: classes.dex */
public class DefaultHorizontalAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultHorizontalAnimator> CREATOR = new Parcelable.Creator<DefaultHorizontalAnimator>() { // from class: cn.webdemo.com.supporfragment.anim.DefaultHorizontalAnimator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultHorizontalAnimator createFromParcel(Parcel parcel) {
            return new DefaultHorizontalAnimator(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultHorizontalAnimator[] newArray(int i2) {
            return new DefaultHorizontalAnimator[i2];
        }
    };

    public DefaultHorizontalAnimator() {
        this.enter = R.anim.h_fragment_enter;
        this.exit = R.anim.h_fragment_exit;
        this.popEnter = R.anim.h_fragment_pop_enter;
        this.popExit = R.anim.h_fragment_pop_exit;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
    }

    public DefaultHorizontalAnimator(Parcel parcel) {
        super(parcel);
    }
}
