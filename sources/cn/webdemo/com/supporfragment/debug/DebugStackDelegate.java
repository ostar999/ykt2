package cn.webdemo.com.supporfragment.debug;

import android.R;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentationMagician;
import com.umeng.analytics.pro.am;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DebugStackDelegate implements SensorEventListener {
    private FragmentActivity mActivity;
    private SensorManager mSensorManager;
    private AlertDialog mStackDialog;

    public class StackViewTouchListener implements View.OnTouchListener {
        private int clickLimitValue;
        private float dX;
        private float downX;
        private boolean isClickState;
        private View stackView;
        private float dY = 0.0f;
        private float downY = 0.0f;

        public StackViewTouchListener(View view, int i2) {
            this.stackView = view;
            this.clickLimitValue = i2;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.isClickState = true;
                this.downX = rawX;
                this.downY = rawY;
                this.dX = this.stackView.getX() - motionEvent.getRawX();
                this.dY = this.stackView.getY() - motionEvent.getRawY();
            } else if (action == 1) {
                if (rawX - this.downX < this.clickLimitValue && this.isClickState) {
                    this.stackView.performClick();
                }
            } else if (action != 2) {
                if (action != 3) {
                    return false;
                }
                if (rawX - this.downX < this.clickLimitValue) {
                    this.stackView.performClick();
                }
            } else if (Math.abs(rawX - this.downX) >= this.clickLimitValue || Math.abs(rawY - this.downY) >= this.clickLimitValue || !this.isClickState) {
                this.isClickState = false;
                this.stackView.setX(motionEvent.getRawX() + this.dX);
                this.stackView.setY(motionEvent.getRawY() + this.dY);
            } else {
                this.isClickState = true;
            }
            return true;
        }
    }

    public DebugStackDelegate(FragmentActivity fragmentActivity) {
        this.mActivity = fragmentActivity;
    }

    private void addDebugFragmentRecord(List<DebugFragmentRecord> list, Fragment fragment) {
        CharSequence charSequenceSpan;
        if (fragment != null) {
            int backStackEntryCount = fragment.getFragmentManager().getBackStackEntryCount();
            CharSequence simpleName = fragment.getClass().getSimpleName();
            if (backStackEntryCount == 0) {
                charSequenceSpan = span(simpleName);
            } else {
                for (int i2 = 0; i2 < backStackEntryCount; i2++) {
                    FragmentManager.BackStackEntry backStackEntryAt = fragment.getFragmentManager().getBackStackEntryAt(i2);
                    if ((backStackEntryAt.getName() != null && backStackEntryAt.getName().equals(fragment.getTag())) || (backStackEntryAt.getName() == null && fragment.getTag() == null)) {
                        break;
                    }
                    if (i2 == backStackEntryCount - 1) {
                        simpleName = span(simpleName);
                    }
                }
                charSequenceSpan = simpleName;
            }
            list.add(new DebugFragmentRecord(charSequenceSpan, getChildFragmentRecords(fragment)));
        }
    }

    private List<DebugFragmentRecord> getChildFragmentRecords(Fragment fragment) {
        ArrayList arrayList = new ArrayList();
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragment.getChildFragmentManager());
        if (activeFragments == null || activeFragments.size() < 1) {
            return null;
        }
        for (int size = activeFragments.size() - 1; size >= 0; size--) {
            addDebugFragmentRecord(arrayList, activeFragments.get(size));
        }
        return arrayList;
    }

    private List<DebugFragmentRecord> getFragmentRecords() {
        ArrayList arrayList = new ArrayList();
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(this.mActivity.getSupportFragmentManager());
        if (activeFragments == null || activeFragments.size() < 1) {
            return null;
        }
        Iterator<Fragment> it = activeFragments.iterator();
        while (it.hasNext()) {
            addDebugFragmentRecord(arrayList, it.next());
        }
        return arrayList;
    }

    private void processChildLog(List<DebugFragmentRecord> list, StringBuilder sb, int i2) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            DebugFragmentRecord debugFragmentRecord = list.get(i3);
            for (int i4 = 0; i4 < i2; i4++) {
                sb.append("\t\t\t");
            }
            if (i3 == 0) {
                sb.append("\t子栈顶\t\t");
                sb.append(debugFragmentRecord.fragmentName);
                sb.append("\n\n");
            } else {
                if (i3 == list.size() - 1) {
                    sb.append("\t子栈底\t\t");
                    sb.append(debugFragmentRecord.fragmentName);
                    sb.append("\n\n");
                    processChildLog(debugFragmentRecord.childFragmentRecord, sb, i2 + 1);
                    return;
                }
                sb.append("\t↓\t\t\t");
                sb.append(debugFragmentRecord.fragmentName);
                sb.append("\n\n");
            }
            processChildLog(debugFragmentRecord.childFragmentRecord, sb, i2);
        }
    }

    @NonNull
    private CharSequence span(CharSequence charSequence) {
        return ((Object) charSequence) + " *";
    }

    public void logFragmentRecords(String str) {
        List<DebugFragmentRecord> fragmentRecords = getFragmentRecords();
        if (fragmentRecords == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int size = fragmentRecords.size() - 1; size >= 0; size--) {
            DebugFragmentRecord debugFragmentRecord = fragmentRecords.get(size);
            if (size == fragmentRecords.size() - 1) {
                sb.append("═══════════════════════════════════════════════════════════════════════════════════\n");
                if (size == 0) {
                    sb.append("\t栈顶\t\t\t");
                    sb.append(debugFragmentRecord.fragmentName);
                    sb.append("\n");
                    sb.append("═══════════════════════════════════════════════════════════════════════════════════");
                } else {
                    sb.append("\t栈顶\t\t\t");
                    sb.append(debugFragmentRecord.fragmentName);
                    sb.append("\n\n");
                }
            } else {
                if (size == 0) {
                    sb.append("\t栈底\t\t\t");
                    sb.append(debugFragmentRecord.fragmentName);
                    sb.append("\n\n");
                    processChildLog(debugFragmentRecord.childFragmentRecord, sb, 1);
                    sb.append("═══════════════════════════════════════════════════════════════════════════════════");
                    Log.i(str, sb.toString());
                    return;
                }
                sb.append("\t↓\t\t\t");
                sb.append(debugFragmentRecord.fragmentName);
                sb.append("\n\n");
            }
            processChildLog(debugFragmentRecord.childFragmentRecord, sb, 1);
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i2) {
    }

    public void onCreate(int i2) {
        if (i2 != 1) {
            return;
        }
        SensorManager sensorManager = (SensorManager) this.mActivity.getSystemService(am.ac);
        this.mSensorManager = sensorManager;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 3);
    }

    public void onDestroy() {
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    public void onPostCreate(int i2) {
        if (i2 != 2) {
            return;
        }
        View viewFindViewById = this.mActivity.findViewById(R.id.content);
        if (viewFindViewById instanceof FrameLayout) {
            ImageView imageView = new ImageView(this.mActivity);
            imageView.setImageResource(cn.webdemo.com.supporfragment.R.drawable.fragmentation_ic_stack);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = GravityCompat.END;
            int iApplyDimension = (int) TypedValue.applyDimension(1, 18.0f, this.mActivity.getResources().getDisplayMetrics());
            layoutParams.topMargin = iApplyDimension * 7;
            layoutParams.rightMargin = iApplyDimension;
            imageView.setLayoutParams(layoutParams);
            ((FrameLayout) viewFindViewById).addView(imageView);
            imageView.setOnTouchListener(new StackViewTouchListener(imageView, iApplyDimension / 4));
            imageView.setOnClickListener(new View.OnClickListener() { // from class: cn.webdemo.com.supporfragment.debug.DebugStackDelegate.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    DebugStackDelegate.this.showFragmentStackHierarchyView();
                }
            });
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float[] fArr = sensorEvent.values;
        if (type == 1) {
            float f2 = 12;
            if (Math.abs(fArr[0]) >= f2 || Math.abs(fArr[1]) >= f2 || Math.abs(fArr[2]) >= f2) {
                showFragmentStackHierarchyView();
            }
        }
    }

    public void showFragmentStackHierarchyView() {
        AlertDialog alertDialog = this.mStackDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            DebugHierarchyViewContainer debugHierarchyViewContainer = new DebugHierarchyViewContainer(this.mActivity);
            debugHierarchyViewContainer.bindFragmentRecords(getFragmentRecords());
            debugHierarchyViewContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mActivity).setView(debugHierarchyViewContainer).setPositiveButton(R.string.cancel, (DialogInterface.OnClickListener) null).setCancelable(true).create();
            this.mStackDialog = alertDialogCreate;
            alertDialogCreate.show();
        }
    }
}
