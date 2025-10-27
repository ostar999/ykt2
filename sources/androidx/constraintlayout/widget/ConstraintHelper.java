package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class ConstraintHelper extends View {
    protected int mCount;
    protected Helper mHelperWidget;
    protected int[] mIds;
    protected HashMap<Integer, String> mMap;
    protected String mReferenceIds;
    protected String mReferenceTags;
    protected boolean mUseViewMeasure;
    private View[] mViews;
    protected Context myContext;

    public ConstraintHelper(Context context) {
        super(context);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(null);
    }

    private void addID(String idString) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        if (idString == null || idString.length() == 0 || this.myContext == null) {
            return;
        }
        String strTrim = idString.trim();
        if (getParent() instanceof ConstraintLayout) {
        }
        int iFindId = findId(strTrim);
        if (iFindId != 0) {
            this.mMap.put(Integer.valueOf(iFindId), strTrim);
            addRscID(iFindId);
            return;
        }
        Log.w("ConstraintHelper", "Could not find id of \"" + strTrim + "\"");
    }

    private void addRscID(int id) {
        if (id == getId()) {
            return;
        }
        int i2 = this.mCount + 1;
        int[] iArr = this.mIds;
        if (i2 > iArr.length) {
            this.mIds = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.mIds;
        int i3 = this.mCount;
        iArr2[i3] = id;
        this.mCount = i3 + 1;
    }

    private void addTag(String tagString) {
        if (tagString == null || tagString.length() == 0 || this.myContext == null) {
            return;
        }
        String strTrim = tagString.trim();
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        if (constraintLayout == null) {
            Log.w("ConstraintHelper", "Parent not a ConstraintLayout");
            return;
        }
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if ((layoutParams instanceof ConstraintLayout.LayoutParams) && strTrim.equals(((ConstraintLayout.LayoutParams) layoutParams).constraintTag)) {
                if (childAt.getId() == -1) {
                    Log.w("ConstraintHelper", "to use ConstraintTag view " + childAt.getClass().getSimpleName() + " must have an ID");
                } else {
                    addRscID(childAt.getId());
                }
            }
        }
    }

    private int[] convertReferenceString(View view, String referenceIdString) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        String[] strArrSplit = referenceIdString.split(",");
        view.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i2 = 0;
        for (String str : strArrSplit) {
            int iFindId = findId(str.trim());
            if (iFindId != 0) {
                iArr[i2] = iFindId;
                i2++;
            }
        }
        return i2 != strArrSplit.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    private int findId(String referenceId) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        int iFindId = 0;
        if (isInEditMode() && constraintLayout != null) {
            Object designInformation = constraintLayout.getDesignInformation(0, referenceId);
            if (designInformation instanceof Integer) {
                iFindId = ((Integer) designInformation).intValue();
            }
        }
        if (iFindId == 0 && constraintLayout != null) {
            iFindId = findId(constraintLayout, referenceId);
        }
        if (iFindId == 0) {
            try {
                iFindId = R.id.class.getField(referenceId).getInt(null);
            } catch (Exception unused) {
            }
        }
        return iFindId == 0 ? this.myContext.getResources().getIdentifier(referenceId, "id", this.myContext.getPackageName()) : iFindId;
    }

    public void addView(View view) {
        if (view == this) {
            return;
        }
        if (view.getId() == -1) {
            Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have an id");
        } else {
            if (view.getParent() == null) {
                Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have a parent");
                return;
            }
            this.mReferenceIds = null;
            addRscID(view.getId());
            requestLayout();
        }
    }

    public void applyLayoutFeatures(ConstraintLayout container) {
        int visibility = getVisibility();
        float elevation = getElevation();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            View viewById = container.getViewById(this.mIds[i2]);
            if (viewById != null) {
                viewById.setVisibility(visibility);
                if (elevation > 0.0f) {
                    viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                }
            }
        }
    }

    public void applyLayoutFeaturesInConstraintSet(ConstraintLayout container) {
    }

    public boolean containsId(final int id) {
        for (int i2 : this.mIds) {
            if (i2 == id) {
                return true;
            }
        }
        return false;
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.mIds, this.mCount);
    }

    public View[] getViews(ConstraintLayout layout) {
        View[] viewArr = this.mViews;
        if (viewArr == null || viewArr.length != this.mCount) {
            this.mViews = new View[this.mCount];
        }
        for (int i2 = 0; i2 < this.mCount; i2++) {
            this.mViews[i2] = layout.getViewById(this.mIds[i2]);
        }
        return this.mViews;
    }

    public int indexFromId(final int id) {
        int i2 = -1;
        for (int i3 : this.mIds) {
            i2++;
            if (i3 == id) {
                return i2;
            }
        }
        return i2;
    }

    public void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.mReferenceIds = string;
                    setIds(string);
                } else if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_tags) {
                    String string2 = typedArrayObtainStyledAttributes.getString(index);
                    this.mReferenceTags = string2;
                    setReferenceTags(string2);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget child, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> mapIdToWidget) {
        ConstraintSet.Layout layout = constraint.layout;
        int[] iArr = layout.mReferenceIds;
        if (iArr != null) {
            setReferencedIds(iArr);
        } else {
            String str = layout.mReferenceIdString;
            if (str != null) {
                if (str.length() > 0) {
                    ConstraintSet.Layout layout2 = constraint.layout;
                    layout2.mReferenceIds = convertReferenceString(this, layout2.mReferenceIdString);
                } else {
                    constraint.layout.mReferenceIds = null;
                }
            }
        }
        if (child == null) {
            return;
        }
        child.removeAllIds();
        if (constraint.layout.mReferenceIds == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            int[] iArr2 = constraint.layout.mReferenceIds;
            if (i2 >= iArr2.length) {
                return;
            }
            ConstraintWidget constraintWidget = mapIdToWidget.get(iArr2[i2]);
            if (constraintWidget != null) {
                child.add(constraintWidget);
            }
            i2++;
        }
    }

    @Override // android.view.View
    public void onAttachedToWindow() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        super.onAttachedToWindow();
        String str = this.mReferenceIds;
        if (str != null) {
            setIds(str);
        }
        String str2 = this.mReferenceTags;
        if (str2 != null) {
            setReferenceTags(str2);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mUseViewMeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public int removeView(View view) {
        int i2;
        int id = view.getId();
        int i3 = -1;
        if (id == -1) {
            return -1;
        }
        this.mReferenceIds = null;
        int i4 = 0;
        while (true) {
            if (i4 >= this.mCount) {
                break;
            }
            if (this.mIds[i4] == id) {
                int i5 = i4;
                while (true) {
                    i2 = this.mCount;
                    if (i5 >= i2 - 1) {
                        break;
                    }
                    int[] iArr = this.mIds;
                    int i6 = i5 + 1;
                    iArr[i5] = iArr[i6];
                    i5 = i6;
                }
                this.mIds[i2 - 1] = 0;
                this.mCount = i2 - 1;
                i3 = i4;
            } else {
                i4++;
            }
        }
        requestLayout();
        return i3;
    }

    public void resolveRtl(ConstraintWidget widget, boolean isRtl) {
    }

    public void setIds(String idList) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        this.mReferenceIds = idList;
        if (idList == null) {
            return;
        }
        int i2 = 0;
        this.mCount = 0;
        while (true) {
            int iIndexOf = idList.indexOf(44, i2);
            if (iIndexOf == -1) {
                addID(idList.substring(i2));
                return;
            } else {
                addID(idList.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    public void setReferenceTags(String tagList) {
        this.mReferenceTags = tagList;
        if (tagList == null) {
            return;
        }
        int i2 = 0;
        this.mCount = 0;
        while (true) {
            int iIndexOf = tagList.indexOf(44, i2);
            if (iIndexOf == -1) {
                addTag(tagList.substring(i2));
                return;
            } else {
                addTag(tagList.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    public void setReferencedIds(int[] ids) {
        this.mReferenceIds = null;
        this.mCount = 0;
        for (int i2 : ids) {
            addRscID(i2);
        }
    }

    @Override // android.view.View
    public void setTag(int key, Object tag) {
        super.setTag(key, tag);
        if (tag == null && this.mReferenceIds == null) {
            addRscID(key);
        }
    }

    public void updatePostConstraints(ConstraintLayout container) {
    }

    public void updatePostLayout(ConstraintLayout container) {
    }

    public void updatePostMeasure(ConstraintLayout container) {
    }

    public void updatePreDraw(ConstraintLayout container) {
    }

    public void updatePreLayout(ConstraintLayout container) {
        String str;
        int iFindId;
        if (isInEditMode()) {
            setIds(this.mReferenceIds);
        }
        Helper helper = this.mHelperWidget;
        if (helper == null) {
            return;
        }
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            int i3 = this.mIds[i2];
            View viewById = container.getViewById(i3);
            if (viewById == null && (iFindId = findId(container, (str = this.mMap.get(Integer.valueOf(i3))))) != 0) {
                this.mIds[i2] = iFindId;
                this.mMap.put(Integer.valueOf(iFindId), str);
                viewById = container.getViewById(iFindId);
            }
            if (viewById != null) {
                this.mHelperWidget.add(container.getViewWidget(viewById));
            }
        }
        this.mHelperWidget.updateConstraints(container.mLayoutWidget);
    }

    public void validateParams() {
        if (this.mHelperWidget == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).widget = (ConstraintWidget) this.mHelperWidget;
        }
    }

    public ConstraintHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(attrs);
    }

    public void applyLayoutFeatures() {
        ViewParent parent = getParent();
        if (parent == null || !(parent instanceof ConstraintLayout)) {
            return;
        }
        applyLayoutFeatures((ConstraintLayout) parent);
    }

    private int findId(ConstraintLayout container, String idString) throws Resources.NotFoundException {
        Resources resources;
        String resourceEntryName;
        if (idString == null || container == null || (resources = this.myContext.getResources()) == null) {
            return 0;
        }
        int childCount = container.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = container.getChildAt(i2);
            if (childAt.getId() != -1) {
                try {
                    resourceEntryName = resources.getResourceEntryName(childAt.getId());
                } catch (Resources.NotFoundException unused) {
                    resourceEntryName = null;
                }
                if (idString.equals(resourceEntryName)) {
                    return childAt.getId();
                }
            }
        }
        return 0;
    }

    public ConstraintHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIds = new int[32];
        this.mUseViewMeasure = false;
        this.mViews = null;
        this.mMap = new HashMap<>();
        this.myContext = context;
        init(attrs);
    }

    public void updatePreLayout(ConstraintWidgetContainer container, Helper helper, SparseArray<ConstraintWidget> map) {
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.mCount; i2++) {
            helper.add(map.get(this.mIds[i2]));
        }
    }
}
