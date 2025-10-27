package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private final ConstraintLayout mConstraintLayout;
    ConstraintSet mDefaultConstraintSet;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    public static class State {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser parser) throws Resources.NotFoundException {
            this.mConstraintID = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(parser), R.styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.State_android_id) {
                    this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == R.styleable.State_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public void add(Variant size) {
            this.mVariants.add(size);
        }

        public int findMatch(float width, float height) {
            for (int i2 = 0; i2 < this.mVariants.size(); i2++) {
                if (this.mVariants.get(i2).match(width, height)) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public static class Variant {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        public Variant(Context context, XmlPullParser parser) throws Resources.NotFoundException {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(parser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.Variant_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxHeight);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMinHeight);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxWidth);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public boolean match(float widthDp, float heightDp) {
            if (!Float.isNaN(this.mMinWidth) && widthDp < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && heightDp < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || widthDp <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || heightDp <= this.mMaxHeight;
            }
            return false;
        }
    }

    public ConstraintLayoutStates(Context context, ConstraintLayout layout, int resourceID) throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
        this.mConstraintLayout = layout;
        load(context, resourceID);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r8, int r9) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException, java.lang.NumberFormatException {
        /*
            r7 = this;
            android.content.res.Resources r0 = r8.getResources()
            android.content.res.XmlResourceParser r9 = r0.getXml(r9)
            int r0 = r9.getEventType()     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            r1 = 0
        Ld:
            r2 = 1
            if (r0 == r2) goto L8b
            if (r0 == 0) goto L7a
            r3 = 2
            if (r0 == r3) goto L17
            goto L7d
        L17:
            java.lang.String r0 = r9.getName()     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            int r4 = r0.hashCode()     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            r5 = 4
            r6 = 3
            switch(r4) {
                case -1349929691: goto L4c;
                case 80204913: goto L42;
                case 1382829617: goto L39;
                case 1657696882: goto L2f;
                case 1901439077: goto L25;
                default: goto L24;
            }     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
        L24:
            goto L56
        L25:
            java.lang.String r2 = "Variant"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r0 == 0) goto L56
            r2 = r6
            goto L57
        L2f:
            java.lang.String r2 = "layoutDescription"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r0 == 0) goto L56
            r2 = 0
            goto L57
        L39:
            java.lang.String r4 = "StateSet"
            boolean r0 = r0.equals(r4)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r0 == 0) goto L56
            goto L57
        L42:
            java.lang.String r2 = "State"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r0 == 0) goto L56
            r2 = r3
            goto L57
        L4c:
            java.lang.String r2 = "ConstraintSet"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r0 == 0) goto L56
            r2 = r5
            goto L57
        L56:
            r2 = -1
        L57:
            if (r2 == r3) goto L6d
            if (r2 == r6) goto L62
            if (r2 == r5) goto L5e
            goto L7d
        L5e:
            r7.parseConstraintSet(r8, r9)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            goto L7d
        L62:
            androidx.constraintlayout.widget.ConstraintLayoutStates$Variant r0 = new androidx.constraintlayout.widget.ConstraintLayoutStates$Variant     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            r0.<init>(r8, r9)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            if (r1 == 0) goto L7d
            r1.add(r0)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            goto L7d
        L6d:
            androidx.constraintlayout.widget.ConstraintLayoutStates$State r1 = new androidx.constraintlayout.widget.ConstraintLayoutStates$State     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            r1.<init>(r8, r9)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintLayoutStates$State> r0 = r7.mStateList     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            int r2 = r1.mId     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            r0.put(r2, r1)     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            goto L7d
        L7a:
            r9.getName()     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
        L7d:
            int r0 = r9.next()     // Catch: java.io.IOException -> L82 org.xmlpull.v1.XmlPullParserException -> L87
            goto Ld
        L82:
            r8 = move-exception
            r8.printStackTrace()
            goto L8b
        L87:
            r8 = move-exception
            r8.printStackTrace()
        L8b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayoutStates.load(android.content.Context, int):void");
    }

    private void parseConstraintSet(Context context, XmlPullParser parser) throws NumberFormatException {
        ConstraintSet constraintSet = new ConstraintSet();
        int attributeCount = parser.getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            String attributeName = parser.getAttributeName(i2);
            String attributeValue = parser.getAttributeValue(i2);
            if (attributeName != null && attributeValue != null && "id".equals(attributeName)) {
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1) {
                    if (attributeValue.length() > 1) {
                        identifier = Integer.parseInt(attributeValue.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                constraintSet.load(context, parser);
                this.mConstraintSetMap.put(identifier, constraintSet);
                return;
            }
        }
    }

    public boolean needsToChange(int id, float width, float height) {
        int i2 = this.mCurrentStateId;
        if (i2 != id) {
            return true;
        }
        State stateValueAt = id == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i2);
        int i3 = this.mCurrentConstraintNumber;
        return (i3 == -1 || !stateValueAt.mVariants.get(i3).match(width, height)) && this.mCurrentConstraintNumber != stateValueAt.findMatch(width, height);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public void updateConstraints(int id, float width, float height) {
        int iFindMatch;
        int i2 = this.mCurrentStateId;
        if (i2 == id) {
            State stateValueAt = id == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i2);
            int i3 = this.mCurrentConstraintNumber;
            if ((i3 == -1 || !stateValueAt.mVariants.get(i3).match(width, height)) && this.mCurrentConstraintNumber != (iFindMatch = stateValueAt.findMatch(width, height))) {
                ConstraintSet constraintSet = iFindMatch == -1 ? this.mDefaultConstraintSet : stateValueAt.mVariants.get(iFindMatch).mConstraintSet;
                int i4 = iFindMatch == -1 ? stateValueAt.mConstraintID : stateValueAt.mVariants.get(iFindMatch).mConstraintID;
                if (constraintSet == null) {
                    return;
                }
                this.mCurrentConstraintNumber = iFindMatch;
                ConstraintsChangedListener constraintsChangedListener = this.mConstraintsChangedListener;
                if (constraintsChangedListener != null) {
                    constraintsChangedListener.preLayoutChange(-1, i4);
                }
                constraintSet.applyTo(this.mConstraintLayout);
                ConstraintsChangedListener constraintsChangedListener2 = this.mConstraintsChangedListener;
                if (constraintsChangedListener2 != null) {
                    constraintsChangedListener2.postLayoutChange(-1, i4);
                    return;
                }
                return;
            }
            return;
        }
        this.mCurrentStateId = id;
        State state = this.mStateList.get(id);
        int iFindMatch2 = state.findMatch(width, height);
        ConstraintSet constraintSet2 = iFindMatch2 == -1 ? state.mConstraintSet : state.mVariants.get(iFindMatch2).mConstraintSet;
        int i5 = iFindMatch2 == -1 ? state.mConstraintID : state.mVariants.get(iFindMatch2).mConstraintID;
        if (constraintSet2 == null) {
            Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + id + ", dim =" + width + ", " + height);
            return;
        }
        this.mCurrentConstraintNumber = iFindMatch2;
        ConstraintsChangedListener constraintsChangedListener3 = this.mConstraintsChangedListener;
        if (constraintsChangedListener3 != null) {
            constraintsChangedListener3.preLayoutChange(id, i5);
        }
        constraintSet2.applyTo(this.mConstraintLayout);
        ConstraintsChangedListener constraintsChangedListener4 = this.mConstraintsChangedListener;
        if (constraintsChangedListener4 != null) {
            constraintsChangedListener4.postLayoutChange(id, i5);
        }
    }
}
