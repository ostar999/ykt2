package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.Arrays;

/* loaded from: classes.dex */
public class CircularFlow extends VirtualLayout {
    private static float DEFAULT_ANGLE = 0.0f;
    private static int DEFAULT_RADIUS = 0;
    private static final String TAG = "CircularFlow";
    private float[] mAngles;
    ConstraintLayout mContainer;
    private int mCountAngle;
    private int mCountRadius;
    private int[] mRadius;
    private String mReferenceAngles;
    private Float mReferenceDefaultAngle;
    private Integer mReferenceDefaultRadius;
    private String mReferenceRadius;
    int mViewCenter;

    public CircularFlow(Context context) {
        super(context);
    }

    private void addAngle(String angleString) {
        float[] fArr;
        if (angleString == null || angleString.length() == 0 || this.myContext == null || (fArr = this.mAngles) == null) {
            return;
        }
        if (this.mCountAngle + 1 > fArr.length) {
            this.mAngles = Arrays.copyOf(fArr, fArr.length + 1);
        }
        this.mAngles[this.mCountAngle] = Integer.parseInt(angleString);
        this.mCountAngle++;
    }

    private void addRadius(String radiusString) {
        int[] iArr;
        if (radiusString == null || radiusString.length() == 0 || this.myContext == null || (iArr = this.mRadius) == null) {
            return;
        }
        if (this.mCountRadius + 1 > iArr.length) {
            this.mRadius = Arrays.copyOf(iArr, iArr.length + 1);
        }
        this.mRadius[this.mCountRadius] = (int) (Integer.parseInt(radiusString) * this.myContext.getResources().getDisplayMetrics().density);
        this.mCountRadius++;
    }

    private void anchorReferences() {
        this.mContainer = (ConstraintLayout) getParent();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            View viewById = this.mContainer.getViewById(this.mIds[i2]);
            if (viewById != null) {
                int i3 = DEFAULT_RADIUS;
                float f2 = DEFAULT_ANGLE;
                int[] iArr = this.mRadius;
                if (iArr == null || i2 >= iArr.length) {
                    Integer num = this.mReferenceDefaultRadius;
                    if (num == null || num.intValue() == -1) {
                        Log.e(TAG, "Added radius to view with id: " + this.mMap.get(Integer.valueOf(viewById.getId())));
                    } else {
                        this.mCountRadius++;
                        if (this.mRadius == null) {
                            this.mRadius = new int[1];
                        }
                        int[] radius = getRadius();
                        this.mRadius = radius;
                        radius[this.mCountRadius - 1] = i3;
                    }
                } else {
                    i3 = iArr[i2];
                }
                float[] fArr = this.mAngles;
                if (fArr == null || i2 >= fArr.length) {
                    Float f3 = this.mReferenceDefaultAngle;
                    if (f3 == null || f3.floatValue() == -1.0f) {
                        Log.e(TAG, "Added angle to view with id: " + this.mMap.get(Integer.valueOf(viewById.getId())));
                    } else {
                        this.mCountAngle++;
                        if (this.mAngles == null) {
                            this.mAngles = new float[1];
                        }
                        float[] angles = getAngles();
                        this.mAngles = angles;
                        angles[this.mCountAngle - 1] = f2;
                    }
                } else {
                    f2 = fArr[i2];
                }
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewById.getLayoutParams();
                layoutParams.circleAngle = f2;
                layoutParams.circleConstraint = this.mViewCenter;
                layoutParams.circleRadius = i3;
                viewById.setLayoutParams(layoutParams);
            }
        }
        applyLayoutFeatures();
    }

    private float[] removeAngle(float[] angles, int index) {
        return (angles == null || index < 0 || index >= this.mCountAngle) ? angles : removeElementFromArray(angles, index);
    }

    public static int[] removeElementFromArray(int[] array, int index) {
        int[] iArr = new int[array.length - 1];
        int i2 = 0;
        for (int i3 = 0; i3 < array.length; i3++) {
            if (i3 != index) {
                iArr[i2] = array[i3];
                i2++;
            }
        }
        return iArr;
    }

    private int[] removeRadius(int[] radius, int index) {
        return (radius == null || index < 0 || index >= this.mCountRadius) ? radius : removeElementFromArray(radius, index);
    }

    private void setAngles(String idList) {
        if (idList == null) {
            return;
        }
        int i2 = 0;
        this.mCountAngle = 0;
        while (true) {
            int iIndexOf = idList.indexOf(44, i2);
            if (iIndexOf == -1) {
                addAngle(idList.substring(i2).trim());
                return;
            } else {
                addAngle(idList.substring(i2, iIndexOf).trim());
                i2 = iIndexOf + 1;
            }
        }
    }

    private void setRadius(String idList) {
        if (idList == null) {
            return;
        }
        int i2 = 0;
        this.mCountRadius = 0;
        while (true) {
            int iIndexOf = idList.indexOf(44, i2);
            if (iIndexOf == -1) {
                addRadius(idList.substring(i2).trim());
                return;
            } else {
                addRadius(idList.substring(i2, iIndexOf).trim());
                i2 = iIndexOf + 1;
            }
        }
    }

    public void addViewToCircularFlow(View view, int radius, float angle) {
        if (containsId(view.getId())) {
            return;
        }
        addView(view);
        this.mCountAngle++;
        float[] angles = getAngles();
        this.mAngles = angles;
        angles[this.mCountAngle - 1] = angle;
        this.mCountRadius++;
        int[] radius2 = getRadius();
        this.mRadius = radius2;
        radius2[this.mCountRadius - 1] = (int) (radius * this.myContext.getResources().getDisplayMetrics().density);
        anchorReferences();
    }

    public float[] getAngles() {
        return Arrays.copyOf(this.mAngles, this.mCountAngle);
    }

    public int[] getRadius() {
        return Arrays.copyOf(this.mRadius, this.mCountRadius);
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper
    public void init(AttributeSet attrs) {
        super.init(attrs);
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_circularflow_viewCenter) {
                    this.mViewCenter = typedArrayObtainStyledAttributes.getResourceId(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_circularflow_angles) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mReferenceAngles = string;
                    setAngles(string);
                } else if (index == R.styleable.ConstraintLayout_Layout_circularflow_radiusInDP) {
                    String string2 = typedArrayObtainStyledAttributes.getString(index);
                    this.mReferenceRadius = string2;
                    setRadius(string2);
                } else if (index == R.styleable.ConstraintLayout_Layout_circularflow_defaultAngle) {
                    Float fValueOf = Float.valueOf(typedArrayObtainStyledAttributes.getFloat(index, DEFAULT_ANGLE));
                    this.mReferenceDefaultAngle = fValueOf;
                    setDefaultAngle(fValueOf.floatValue());
                } else if (index == R.styleable.ConstraintLayout_Layout_circularflow_defaultRadius) {
                    Integer numValueOf = Integer.valueOf(typedArrayObtainStyledAttributes.getDimensionPixelSize(index, DEFAULT_RADIUS));
                    this.mReferenceDefaultRadius = numValueOf;
                    setDefaultRadius(numValueOf.intValue());
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public boolean isUpdatable(View view) {
        return containsId(view.getId()) && indexFromId(view.getId()) != -1;
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public void onAttachedToWindow() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        super.onAttachedToWindow();
        String str = this.mReferenceAngles;
        if (str != null) {
            this.mAngles = new float[1];
            setAngles(str);
        }
        String str2 = this.mReferenceRadius;
        if (str2 != null) {
            this.mRadius = new int[1];
            setRadius(str2);
        }
        Float f2 = this.mReferenceDefaultAngle;
        if (f2 != null) {
            setDefaultAngle(f2.floatValue());
        }
        Integer num = this.mReferenceDefaultRadius;
        if (num != null) {
            setDefaultRadius(num.intValue());
        }
        anchorReferences();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public int removeView(View view) {
        int iRemoveView = super.removeView(view);
        if (iRemoveView == -1) {
            return iRemoveView;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mContainer);
        constraintSet.clear(view.getId(), 8);
        constraintSet.applyTo(this.mContainer);
        float[] fArr = this.mAngles;
        if (iRemoveView < fArr.length) {
            this.mAngles = removeAngle(fArr, iRemoveView);
            this.mCountAngle--;
        }
        int[] iArr = this.mRadius;
        if (iRemoveView < iArr.length) {
            this.mRadius = removeRadius(iArr, iRemoveView);
            this.mCountRadius--;
        }
        anchorReferences();
        return iRemoveView;
    }

    public void setDefaultAngle(float angle) {
        DEFAULT_ANGLE = angle;
    }

    public void setDefaultRadius(int radius) {
        DEFAULT_RADIUS = radius;
    }

    public void updateAngle(View view, float angle) {
        if (!isUpdatable(view)) {
            Log.e(TAG, "It was not possible to update angle to view with id: " + view.getId());
            return;
        }
        int iIndexFromId = indexFromId(view.getId());
        if (iIndexFromId > this.mAngles.length) {
            return;
        }
        float[] angles = getAngles();
        this.mAngles = angles;
        angles[iIndexFromId] = angle;
        anchorReferences();
    }

    public void updateRadius(View view, int radius) {
        if (!isUpdatable(view)) {
            Log.e(TAG, "It was not possible to update radius to view with id: " + view.getId());
            return;
        }
        int iIndexFromId = indexFromId(view.getId());
        if (iIndexFromId > this.mRadius.length) {
            return;
        }
        int[] radius2 = getRadius();
        this.mRadius = radius2;
        radius2[iIndexFromId] = (int) (radius * this.myContext.getResources().getDisplayMetrics().density);
        anchorReferences();
    }

    public void updateReference(View view, int radius, float angle) {
        if (!isUpdatable(view)) {
            Log.e(TAG, "It was not possible to update radius and angle to view with id: " + view.getId());
            return;
        }
        int iIndexFromId = indexFromId(view.getId());
        if (getAngles().length > iIndexFromId) {
            float[] angles = getAngles();
            this.mAngles = angles;
            angles[iIndexFromId] = angle;
        }
        if (getRadius().length > iIndexFromId) {
            int[] radius2 = getRadius();
            this.mRadius = radius2;
            radius2[iIndexFromId] = (int) (radius * this.myContext.getResources().getDisplayMetrics().density);
        }
        anchorReferences();
    }

    public CircularFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularFlow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static float[] removeElementFromArray(float[] array, int index) {
        float[] fArr = new float[array.length - 1];
        int i2 = 0;
        for (int i3 = 0; i3 < array.length; i3++) {
            if (i3 != index) {
                fArr[i2] = array[i3];
                i2++;
            }
        }
        return fArr;
    }
}
