package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public final class i extends Dialog implements TextWatcher, View.OnClickListener {

    /* renamed from: d, reason: collision with root package name */
    private static int f24109d = 1627389952;

    /* renamed from: e, reason: collision with root package name */
    private static int f24110e = -1;

    /* renamed from: a, reason: collision with root package name */
    public boolean f24111a;

    /* renamed from: b, reason: collision with root package name */
    private Context f24112b;

    /* renamed from: c, reason: collision with root package name */
    private UnityPlayer f24113c;

    /* renamed from: f, reason: collision with root package name */
    private int f24114f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f24115g;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private static final int f24121a = View.generateViewId();

        /* renamed from: b, reason: collision with root package name */
        private static final int f24122b = View.generateViewId();

        /* renamed from: c, reason: collision with root package name */
        private static final int f24123c = View.generateViewId();
    }

    public i(Context context, UnityPlayer unityPlayer, String str, int i2, boolean z2, boolean z3, boolean z4, String str2, int i3, boolean z5, boolean z6) {
        super(context);
        this.f24112b = context;
        this.f24113c = unityPlayer;
        Window window = getWindow();
        this.f24111a = z6;
        window.requestFeature(1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        window.setBackgroundDrawable(new ColorDrawable(0));
        final View viewCreateSoftInputView = createSoftInputView();
        setContentView(viewCreateSoftInputView);
        window.setLayout(-1, -2);
        window.clearFlags(2);
        window.clearFlags(134217728);
        window.clearFlags(67108864);
        if (!this.f24111a) {
            window.addFlags(32);
            window.addFlags(262144);
        }
        EditText editText = (EditText) findViewById(a.f24122b);
        Button button = (Button) findViewById(a.f24121a);
        a(editText, str, i2, z2, z3, z4, str2, i3);
        button.setOnClickListener(this);
        this.f24114f = editText.getCurrentTextColor();
        a(z5);
        this.f24113c.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.unity3d.player.i.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                if (viewCreateSoftInputView.isShown()) {
                    Rect rect = new Rect();
                    i.this.f24113c.getWindowVisibleDisplayFrame(rect);
                    int[] iArr = new int[2];
                    i.this.f24113c.getLocationOnScreen(iArr);
                    Point point = new Point(rect.left - iArr[0], rect.height() - viewCreateSoftInputView.getHeight());
                    Point point2 = new Point();
                    i.this.getWindow().getWindowManager().getDefaultDisplay().getSize(point2);
                    int height = i.this.f24113c.getHeight() - point2.y;
                    int height2 = i.this.f24113c.getHeight() - point.y;
                    if (height2 != height + viewCreateSoftInputView.getHeight()) {
                        i.this.f24113c.reportSoftInputIsVisible(true);
                    } else {
                        i.this.f24113c.reportSoftInputIsVisible(false);
                    }
                    i.this.f24113c.reportSoftInputArea(new Rect(point.x, point.y, viewCreateSoftInputView.getWidth(), height2));
                }
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.unity3d.player.i.2
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z7) {
                if (z7) {
                    i.this.getWindow().setSoftInputMode(5);
                }
            }
        });
        editText.requestFocus();
    }

    private static int a(int i2, boolean z2, boolean z3, boolean z4) {
        int i3 = (z2 ? 32768 : 524288) | (z3 ? 131072 : 0) | (z4 ? 128 : 0);
        if (i2 < 0 || i2 > 11) {
            return i3;
        }
        int i4 = new int[]{1, R2.id.ll_question_comment, 12290, 17, 2, 3, R2.dimen.test_navigation_bar_elevation, 33, 1, R2.id.ll_start_page, 17, 8194}[i2];
        return (i4 & 2) != 0 ? i4 : i4 | i3;
    }

    private void a(EditText editText, String str, int i2, boolean z2, boolean z3, boolean z4, String str2, int i3) {
        editText.setImeOptions(6);
        editText.setText(str);
        editText.setHint(str2);
        editText.setHintTextColor(f24109d);
        editText.setInputType(a(i2, z2, z3, z4));
        editText.setImeOptions(33554432);
        if (i3 > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i3)});
        }
        editText.addTextChangedListener(this);
        editText.setSelection(editText.getText().length());
        editText.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z2) {
        ((EditText) findViewById(a.f24122b)).setSelection(0, 0);
        this.f24113c.reportSoftInputStr(str, 1, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() {
        EditText editText = (EditText) findViewById(a.f24122b);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString();
    }

    public final String a() {
        InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) this.f24112b.getSystemService("input_method")).getCurrentInputMethodSubtype();
        if (currentInputMethodSubtype == null) {
            return null;
        }
        String locale = currentInputMethodSubtype.getLocale();
        if (locale != null && !locale.equals("")) {
            return locale;
        }
        return currentInputMethodSubtype.getMode() + " " + currentInputMethodSubtype.getExtraValue();
    }

    public final void a(int i2) {
        EditText editText = (EditText) findViewById(a.f24122b);
        if (editText != null) {
            if (i2 > 0) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
            } else {
                editText.setFilters(new InputFilter[0]);
            }
        }
    }

    public final void a(int i2, int i3) {
        int i4;
        EditText editText = (EditText) findViewById(a.f24122b);
        if (editText == null || editText.getText().length() < (i4 = i3 + i2)) {
            return;
        }
        editText.setSelection(i2, i4);
    }

    public final void a(String str) {
        EditText editText = (EditText) findViewById(a.f24122b);
        if (editText != null) {
            editText.setText(str);
            editText.setSelection(str.length());
        }
    }

    public final void a(boolean z2) {
        this.f24115g = z2;
        EditText editText = (EditText) findViewById(a.f24122b);
        Button button = (Button) findViewById(a.f24121a);
        View viewFindViewById = findViewById(a.f24123c);
        if (z2) {
            editText.setBackgroundColor(0);
            editText.setTextColor(0);
            editText.setCursorVisible(false);
            editText.setHighlightColor(0);
            editText.setOnClickListener(this);
            editText.setLongClickable(false);
            button.setTextColor(0);
            viewFindViewById.setBackgroundColor(0);
            viewFindViewById.setOnClickListener(this);
            return;
        }
        editText.setBackgroundColor(f24110e);
        editText.setTextColor(this.f24114f);
        editText.setCursorVisible(true);
        editText.setOnClickListener(null);
        editText.setLongClickable(true);
        button.setClickable(true);
        button.setTextColor(this.f24114f);
        viewFindViewById.setBackgroundColor(f24110e);
        viewFindViewById.setOnClickListener(null);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        this.f24113c.reportSoftInputStr(editable.toString(), 0, false);
        EditText editText = (EditText) findViewById(a.f24122b);
        int selectionStart = editText.getSelectionStart();
        this.f24113c.reportSoftInputSelection(selectionStart, editText.getSelectionEnd() - selectionStart);
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    public final View createSoftInputView() {
        RelativeLayout relativeLayout = new RelativeLayout(this.f24112b);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        relativeLayout.setBackgroundColor(f24110e);
        relativeLayout.setId(a.f24123c);
        View view = new EditText(this.f24112b) { // from class: com.unity3d.player.i.3
            @Override // android.widget.TextView, android.view.View
            public final boolean onKeyPreIme(int i2, KeyEvent keyEvent) {
                if (i2 == 4) {
                    i iVar = i.this;
                    iVar.a(iVar.b(), true);
                    return true;
                }
                if (i2 == 84) {
                    return true;
                }
                return super.onKeyPreIme(i2, keyEvent);
            }

            @Override // android.widget.TextView
            public final void onSelectionChanged(int i2, int i3) {
                i.this.f24113c.reportSoftInputSelection(i2, i3 - i2);
            }

            @Override // android.widget.TextView, android.view.View
            public final void onWindowFocusChanged(boolean z2) {
                super.onWindowFocusChanged(z2);
                if (z2) {
                    ((InputMethodManager) i.this.f24112b.getSystemService("input_method")).showSoftInput(this, 0);
                }
            }
        };
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(0, a.f24121a);
        view.setLayoutParams(layoutParams);
        view.setId(a.f24122b);
        relativeLayout.addView(view);
        Button button = new Button(this.f24112b);
        button.setText(this.f24112b.getResources().getIdentifier(AliyunLogKey.KEY_OBJECT_KEY, TypedValues.Custom.S_STRING, "android"));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(11);
        button.setLayoutParams(layoutParams2);
        button.setId(a.f24121a);
        button.setBackgroundColor(0);
        relativeLayout.addView(button);
        ((EditText) relativeLayout.findViewById(a.f24122b)).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.unity3d.player.i.4
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 == 6) {
                    i iVar = i.this;
                    iVar.a(iVar.b(), false);
                }
                return false;
            }
        });
        relativeLayout.setPadding(16, 16, 16, 16);
        return relativeLayout;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.f24111a || !(motionEvent.getAction() == 4 || this.f24115g)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        a(b(), true);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        a(b(), false);
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }
}
