package cn.webdemo.com.supporfragment.anim;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class DefaultNoAnimator extends FragmentAnimator implements Parcelable {
    public static final Parcelable.Creator<DefaultNoAnimator> CREATOR = new Parcelable.Creator<DefaultNoAnimator>() { // from class: cn.webdemo.com.supporfragment.anim.DefaultNoAnimator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultNoAnimator createFromParcel(Parcel parcel) {
            return new DefaultNoAnimator(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultNoAnimator[] newArray(int i2) {
            return new DefaultNoAnimator[i2];
        }
    };

    public DefaultNoAnimator() {
        this.enter = 0;
        this.exit = 0;
        this.popEnter = 0;
        this.popExit = 0;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // cn.webdemo.com.supporfragment.anim.FragmentAnimator, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
    }

    public DefaultNoAnimator(Parcel parcel) {
        super(parcel);
    }
}
