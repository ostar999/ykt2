package com.psychiatrygarden.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class CBAlignTextView extends AppCompatTextView {
    private static List<Character> punctuation;
    private final char SPACE;
    private List<Integer> addCharPosition;
    private boolean inProcess;
    private boolean isAddListener;
    private boolean isAddPadding;
    private boolean isConvert;
    private CharSequence newText;
    private CharSequence oldText;

    static {
        ArrayList arrayList = new ArrayList();
        punctuation = arrayList;
        arrayList.clear();
        punctuation.add(',');
        punctuation.add('.');
        punctuation.add('?');
        punctuation.add('!');
        punctuation.add(';');
        punctuation.add((char) 65292);
        punctuation.add((char) 12290);
        punctuation.add((char) 65311);
        punctuation.add((char) 65281);
        punctuation.add((char) 65307);
        punctuation.add((char) 65289);
        punctuation.add((char) 12305);
        punctuation.add(')');
        punctuation.add(']');
        punctuation.add('}');
    }

    public CBAlignTextView(Context context) {
        super(context);
        this.addCharPosition = new ArrayList();
        this.SPACE = ' ';
        this.oldText = "";
        this.newText = "";
        this.inProcess = false;
        this.isAddPadding = false;
        this.isConvert = false;
        this.isAddListener = false;
        addLayoutListener();
    }

    private void addLayoutListener() {
        this.isAddListener = true;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.utils.CBAlignTextView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (CBAlignTextView.this.getWidth() == 0) {
                    return;
                }
                CBAlignTextView.this.process(true);
                CBAlignTextView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CBAlignTextView.this.isAddListener = false;
            }
        });
    }

    private void copy(String text) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
            int iIndexOf = this.newText.toString().indexOf(text);
            int length = text.length() + iIndexOf;
            StringBuilder sb = new StringBuilder(text);
            for (int size = this.addCharPosition.size() - 1; size >= 0; size--) {
                int iIntValue = this.addCharPosition.get(size).intValue();
                if (iIntValue < length && iIntValue >= iIndexOf) {
                    sb.deleteCharAt(iIntValue - iIndexOf);
                }
            }
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, sb.toString()));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void process(boolean setText) {
        if (this.inProcess || TextUtils.isEmpty(this.oldText) || getVisibility() != 0) {
            return;
        }
        this.addCharPosition.clear();
        if (this.isConvert) {
            this.oldText = CBAlignTextViewUtil.replacePunctuation(this.oldText.toString());
        }
        if (getWidth() == 0) {
            return;
        }
        if (this.isAddPadding) {
            this.newText = processText(getPaint(), this.oldText.toString(), (getWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            int iMeasureText = (int) getPaint().measureText(" ");
            this.newText = processText(getPaint(), this.oldText.toString(), ((getWidth() - getPaddingLeft()) - getPaddingRight()) - iMeasureText);
            setPadding(getPaddingLeft() + iMeasureText, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            this.isAddPadding = true;
        }
        this.inProcess = true;
        if (setText) {
            setText(this.newText);
        }
    }

    private String processLine(Paint paint, String text, int width, int addCharacterStartPosition) {
        int i2;
        if (text == null || text.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(text);
        float fMeasureText = paint.measureText("中");
        float fMeasureText2 = paint.measureText(" ");
        float f2 = width;
        int i3 = ((int) (f2 / fMeasureText)) - 1;
        if (i3 <= 0) {
            return "";
        }
        int i4 = i3;
        int i5 = 0;
        while (i4 < sb.length()) {
            float f3 = f2 - fMeasureText2;
            if (paint.measureText(sb.substring(i5, i4 + 1)) > f3) {
                float fMeasureText3 = f3 - paint.measureText(sb.substring(i5, i4));
                ArrayList arrayList = new ArrayList();
                while (i5 < i4) {
                    if (punctuation.contains(Character.valueOf(sb.charAt(i5)))) {
                        arrayList.add(Integer.valueOf(i5 + 1));
                    }
                    i5++;
                }
                int i6 = (int) (fMeasureText3 / fMeasureText2);
                char c3 = ' ';
                if (arrayList.size() > 0) {
                    int i7 = 0;
                    i2 = 0;
                    while (i7 < arrayList.size() && i6 > 0) {
                        int size = i6 / (arrayList.size() - i7);
                        int iIntValue = ((Integer) arrayList.get(i7 / arrayList.size())).intValue();
                        int i8 = 0;
                        while (i8 < size) {
                            int i9 = iIntValue + i8;
                            sb.insert(i9, c3);
                            this.addCharPosition.add(Integer.valueOf(i9 + addCharacterStartPosition));
                            i2++;
                            i6--;
                            i8++;
                            c3 = ' ';
                        }
                        i7++;
                        c3 = ' ';
                    }
                } else {
                    i2 = 0;
                }
                int i10 = i4 + i2;
                sb.insert(i10, ' ');
                this.addCharPosition.add(Integer.valueOf(i10 + addCharacterStartPosition));
                i5 = i10 + 1;
                i4 = i10 + i3;
            }
            i4++;
        }
        return sb.toString();
    }

    private String processText(Paint paint, String text, int width) {
        if (text == null || text.length() == 0) {
            return "";
        }
        String[] strArrSplit = text.split("\\n");
        StringBuilder sb = new StringBuilder();
        for (String str : strArrSplit) {
            sb.append('\n');
            sb.append(processLine(paint, str, width, sb.length() - 1));
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    public CharSequence getRealText() {
        return this.oldText;
    }

    @Override // android.widget.TextView
    public boolean onTextContextMenuItem(int id) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (id != 16908321) {
            return super.onTextContextMenuItem(id);
        }
        if (isFocused()) {
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            int iMax = Math.max(0, Math.min(selectionStart, selectionEnd));
            int iMax2 = Math.max(0, Math.max(selectionStart, selectionEnd));
            try {
                Class<? super Object> superclass = getClass().getSuperclass();
                Class<?> cls = Integer.TYPE;
                Method declaredMethod = superclass.getDeclaredMethod("getTransformedText", cls, cls);
                declaredMethod.setAccessible(true);
                copy(((CharSequence) declaredMethod.invoke(this, Integer.valueOf(iMax), Integer.valueOf(iMax2))).toString());
                Method declaredMethod2 = superclass.getDeclaredMethod("stopTextActionMode", new Class[0]);
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(this, new Object[0]);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    public void setPunctuationConvert(boolean convert) {
        this.isConvert = convert;
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        if (this.inProcess || text == null || text.equals(this.newText)) {
            this.inProcess = false;
            super.setText(text, type);
            return;
        }
        this.oldText = text;
        if (!this.isAddListener) {
            addLayoutListener();
        }
        process(false);
        super.setText(this.newText, type);
    }

    public CBAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addCharPosition = new ArrayList();
        this.SPACE = ' ';
        this.oldText = "";
        this.newText = "";
        this.inProcess = false;
        this.isAddPadding = false;
        this.isConvert = false;
        this.isAddListener = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CBAlignTextView);
        this.isConvert = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        addLayoutListener();
    }
}
