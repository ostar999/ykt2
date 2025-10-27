package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private static final int MAX_ITEMS_MEASURED = 15;

    @Nullable
    private final AccessibilityManager accessibilityManager;

    @NonNull
    private final ListPopupWindow modalListPopup;

    @NonNull
    private final Rect tempRect;

    public MaterialAutoCompleteTextView(@NonNull Context context) {
        this(context, null);
    }

    @Nullable
    private TextInputLayout findTextInputLayoutAncestor() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    private int measureContentWidth() {
        ListAdapter adapter = getAdapter();
        TextInputLayout textInputLayoutFindTextInputLayoutAncestor = findTextInputLayoutAncestor();
        int i2 = 0;
        if (adapter == null || textInputLayoutFindTextInputLayoutAncestor == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMin = Math.min(adapter.getCount(), Math.max(0, this.modalListPopup.getSelectedItemPosition()) + 15);
        View view = null;
        int iMax = 0;
        for (int iMax2 = Math.max(0, iMin - 15); iMax2 < iMin; iMax2++) {
            int itemViewType = adapter.getItemViewType(iMax2);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = adapter.getView(iMax2, view, textInputLayoutFindTextInputLayoutAncestor);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax = Math.max(iMax, view.getMeasuredWidth());
        }
        Drawable background = this.modalListPopup.getBackground();
        if (background != null) {
            background.getPadding(this.tempRect);
            Rect rect = this.tempRect;
            iMax += rect.left + rect.right;
        }
        return iMax + textInputLayoutFindTextInputLayoutAncestor.getEndIconView().getMeasuredWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T extends ListAdapter & Filterable> void updateText(Object obj) {
        setText(convertSelectionToString(obj), false);
    }

    @Override // android.widget.TextView
    @Nullable
    public CharSequence getHint() {
        TextInputLayout textInputLayoutFindTextInputLayoutAncestor = findTextInputLayoutAncestor();
        return (textInputLayoutFindTextInputLayoutAncestor == null || !textInputLayoutFindTextInputLayoutAncestor.isProvidingHint()) ? super.getHint() : textInputLayoutFindTextInputLayoutAncestor.getHint();
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayoutFindTextInputLayoutAncestor = findTextInputLayoutAncestor();
        if (textInputLayoutFindTextInputLayoutAncestor != null && textInputLayoutFindTextInputLayoutAncestor.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint("");
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), measureContentWidth()), View.MeasureSpec.getSize(i2)), getMeasuredHeight());
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public <T extends ListAdapter & Filterable> void setAdapter(@Nullable T t2) {
        super.setAdapter(t2);
        this.modalListPopup.setAdapter(getAdapter());
    }

    @Override // android.widget.AutoCompleteTextView
    public void showDropDown() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager == null || !accessibilityManager.isTouchExplorationEnabled()) {
            super.showDropDown();
        } else {
            this.modalListPopup.show();
        }
    }

    public MaterialAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i2, 0), attributeSet, i2);
        this.tempRect = new Rect();
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.MaterialAutoCompleteTextView, i2, R.style.Widget_AppCompat_AutoCompleteTextView, new int[0]);
        int i3 = R.styleable.MaterialAutoCompleteTextView_android_inputType;
        if (typedArrayObtainStyledAttributes.hasValue(i3) && typedArrayObtainStyledAttributes.getInt(i3, 0) == 0) {
            setKeyListener(null);
        }
        this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        ListPopupWindow listPopupWindow = new ListPopupWindow(context2);
        this.modalListPopup = listPopupWindow;
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(this);
        listPopupWindow.setInputMethodMode(2);
        listPopupWindow.setAdapter(getAdapter());
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.google.android.material.textfield.MaterialAutoCompleteTextView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i4, long j2) {
                MaterialAutoCompleteTextView.this.updateText(i4 < 0 ? MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItem() : MaterialAutoCompleteTextView.this.getAdapter().getItem(i4));
                AdapterView.OnItemClickListener onItemClickListener = MaterialAutoCompleteTextView.this.getOnItemClickListener();
                if (onItemClickListener != null) {
                    if (view == null || i4 < 0) {
                        view = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
                        i4 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
                        j2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
                    }
                    onItemClickListener.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.getListView(), view, i4, j2);
                }
                MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
            }
        });
        typedArrayObtainStyledAttributes.recycle();
    }
}
