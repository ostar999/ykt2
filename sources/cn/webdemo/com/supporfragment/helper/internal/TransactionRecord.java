package cn.webdemo.com.supporfragment.helper.internal;

import android.view.View;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class TransactionRecord {
    public ArrayList<SharedElement> sharedElementList;
    public String tag;
    public int targetFragmentEnter = Integer.MIN_VALUE;
    public int currentFragmentPopExit = Integer.MIN_VALUE;
    public int currentFragmentPopEnter = Integer.MIN_VALUE;
    public int targetFragmentExit = Integer.MIN_VALUE;
    public boolean dontAddToBackStack = false;

    public static class SharedElement {
        public View sharedElement;
        public String sharedName;

        public SharedElement(View view, String str) {
            this.sharedElement = view;
            this.sharedName = str;
        }
    }
}
