package cn.webdemo.com.supporfragment;

import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import cn.webdemo.com.supporfragment.helper.internal.TransactionRecord;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class ExtraTransaction {

    public interface DontAddToBackStackTransaction {
        void add(ISupportFragment iSupportFragment);

        void replace(ISupportFragment iSupportFragment);

        void start(ISupportFragment iSupportFragment);
    }

    public static final class ExtraTransactionImpl<T extends ISupportFragment> extends ExtraTransaction implements DontAddToBackStackTransaction {
        private FragmentActivity mActivity;
        private Fragment mFragment;
        private boolean mFromActivity;
        private TransactionRecord mRecord = new TransactionRecord();
        private T mSupportF;
        private TransactionDelegate mTransactionDelegate;

        /* JADX WARN: Multi-variable type inference failed */
        public ExtraTransactionImpl(FragmentActivity fragmentActivity, T t2, TransactionDelegate transactionDelegate, boolean z2) {
            this.mActivity = fragmentActivity;
            this.mSupportF = t2;
            this.mFragment = (Fragment) t2;
            this.mTransactionDelegate = transactionDelegate;
            this.mFromActivity = z2;
        }

        private FragmentManager getFragmentManager() {
            Fragment fragment = this.mFragment;
            return fragment == null ? this.mActivity.getSupportFragmentManager() : fragment.getFragmentManager();
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction.DontAddToBackStackTransaction
        public void add(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public ExtraTransaction addSharedElement(View view, String str) {
            TransactionRecord transactionRecord = this.mRecord;
            if (transactionRecord.sharedElementList == null) {
                transactionRecord.sharedElementList = new ArrayList<>();
            }
            this.mRecord.sharedElementList.add(new TransactionRecord.SharedElement(view, str));
            return this;
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public DontAddToBackStackTransaction dontAddToBackStack() {
            this.mRecord.dontAddToBackStack = true;
            return this;
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void loadRootFragment(int i2, ISupportFragment iSupportFragment) {
            loadRootFragment(i2, iSupportFragment, true, false);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void popTo(String str, boolean z2) {
            popTo(str, z2, null, Integer.MAX_VALUE);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void popToChild(String str, boolean z2) {
            popToChild(str, z2, null, Integer.MAX_VALUE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void remove(ISupportFragment iSupportFragment, boolean z2) {
            this.mTransactionDelegate.remove(getFragmentManager(), (Fragment) iSupportFragment, z2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction, cn.webdemo.com.supporfragment.ExtraTransaction.DontAddToBackStackTransaction
        public void replace(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 10);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public ExtraTransaction setCustomAnimations(@AnimRes int i2, @AnimRes int i3) {
            TransactionRecord transactionRecord = this.mRecord;
            transactionRecord.targetFragmentEnter = i2;
            transactionRecord.currentFragmentPopExit = i3;
            transactionRecord.currentFragmentPopEnter = 0;
            transactionRecord.targetFragmentExit = 0;
            return this;
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public ExtraTransaction setTag(String str) {
            this.mRecord.tag = str;
            return this;
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction, cn.webdemo.com.supporfragment.ExtraTransaction.DontAddToBackStackTransaction
        public void start(ISupportFragment iSupportFragment) {
            start(iSupportFragment, 0);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startDontHideSelf(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, 0, 2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startForResult(ISupportFragment iSupportFragment, int i2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, i2, 0, 1);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startForResultDontHideSelf(ISupportFragment iSupportFragment, int i2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, i2, 0, 3);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startWithPop(ISupportFragment iSupportFragment) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.startWithPop(getFragmentManager(), this.mSupportF, iSupportFragment);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startWithPopTo(ISupportFragment iSupportFragment, String str, boolean z2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.startWithPopTo(getFragmentManager(), this.mSupportF, iSupportFragment, str, z2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void loadRootFragment(int i2, ISupportFragment iSupportFragment, boolean z2, boolean z3) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.loadRootTransaction(getFragmentManager(), i2, iSupportFragment, z2, z3);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void popTo(String str, boolean z2, Runnable runnable, int i2) {
            this.mTransactionDelegate.popTo(str, z2, runnable, getFragmentManager(), i2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void popToChild(String str, boolean z2, Runnable runnable, int i2) {
            if (this.mFromActivity) {
                popTo(str, z2, runnable, i2);
            } else {
                this.mTransactionDelegate.popTo(str, z2, runnable, this.mFragment.getChildFragmentManager(), i2);
            }
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void start(ISupportFragment iSupportFragment, int i2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, i2, 0);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public void startDontHideSelf(ISupportFragment iSupportFragment, int i2) {
            iSupportFragment.getSupportDelegate().mTransactionRecord = this.mRecord;
            this.mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), this.mSupportF, iSupportFragment, 0, i2, 2);
        }

        @Override // cn.webdemo.com.supporfragment.ExtraTransaction
        public ExtraTransaction setCustomAnimations(@AnimRes int i2, @AnimRes int i3, @AnimRes int i4, @AnimRes int i5) {
            TransactionRecord transactionRecord = this.mRecord;
            transactionRecord.targetFragmentEnter = i2;
            transactionRecord.currentFragmentPopExit = i3;
            transactionRecord.currentFragmentPopEnter = i4;
            transactionRecord.targetFragmentExit = i5;
            return this;
        }
    }

    @RequiresApi(22)
    public abstract ExtraTransaction addSharedElement(View view, String str);

    public abstract DontAddToBackStackTransaction dontAddToBackStack();

    public abstract void loadRootFragment(int i2, ISupportFragment iSupportFragment);

    public abstract void loadRootFragment(int i2, ISupportFragment iSupportFragment, boolean z2, boolean z3);

    public abstract void popTo(String str, boolean z2);

    public abstract void popTo(String str, boolean z2, Runnable runnable, int i2);

    public abstract void popToChild(String str, boolean z2);

    public abstract void popToChild(String str, boolean z2, Runnable runnable, int i2);

    public abstract void remove(ISupportFragment iSupportFragment, boolean z2);

    public abstract void replace(ISupportFragment iSupportFragment);

    public abstract ExtraTransaction setCustomAnimations(@AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3);

    public abstract ExtraTransaction setCustomAnimations(@AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4, @AnimRes @AnimatorRes int i5);

    public abstract ExtraTransaction setTag(String str);

    public abstract void start(ISupportFragment iSupportFragment);

    public abstract void start(ISupportFragment iSupportFragment, int i2);

    public abstract void startDontHideSelf(ISupportFragment iSupportFragment);

    public abstract void startDontHideSelf(ISupportFragment iSupportFragment, int i2);

    public abstract void startForResult(ISupportFragment iSupportFragment, int i2);

    public abstract void startForResultDontHideSelf(ISupportFragment iSupportFragment, int i2);

    public abstract void startWithPop(ISupportFragment iSupportFragment);

    public abstract void startWithPopTo(ISupportFragment iSupportFragment, String str, boolean z2);
}
