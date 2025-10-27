package com.hyphenate.easeui.modules.chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.modules.chat.EaseInputEditText;
import com.hyphenate.easeui.modules.chat.interfaces.EaseChatPrimaryMenuListener;
import com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu;
import com.hyphenate.easeui.utils.DarkModeHelper;

/* loaded from: classes4.dex */
public class EaseChatPrimaryMenu extends RelativeLayout implements IChatPrimaryMenu, View.OnClickListener, EaseInputEditText.OnEditTextChangeListener, TextWatcher {
    protected Activity activity;
    private CheckBox buttonMore;
    private FrameLayout buttonPressToSpeak;
    private Button buttonSend;
    private ImageView buttonSetModeKeyboard;
    private ImageView buttonSetModeVoice;
    private EaseInputEditText editText;
    private FrameLayout edittext_layout;
    private ImageView faceChecked;
    private RelativeLayout faceLayout;
    private ImageView faceNormal;
    protected InputMethodManager inputManager;
    private boolean isDarkMode;
    private EaseChatPrimaryMenuListener listener;
    private EaseInputMenuStyle menuType;
    private LinearLayout rlBottom;

    public EaseChatPrimaryMenu(Context context) {
        this(context, null);
    }

    private void checkMenuType() {
        EaseInputMenuStyle easeInputMenuStyle = this.menuType;
        if (easeInputMenuStyle == EaseInputMenuStyle.DISABLE_VOICE) {
            this.buttonSetModeVoice.setVisibility(8);
            this.buttonSetModeKeyboard.setVisibility(8);
            this.buttonPressToSpeak.setVisibility(8);
            return;
        }
        if (easeInputMenuStyle == EaseInputMenuStyle.DISABLE_EMOJICON) {
            this.faceLayout.setVisibility(8);
            return;
        }
        if (easeInputMenuStyle == EaseInputMenuStyle.DISABLE_VOICE_EMOJICON) {
            this.buttonSetModeVoice.setVisibility(8);
            this.buttonSetModeKeyboard.setVisibility(8);
            this.buttonPressToSpeak.setVisibility(8);
            this.faceLayout.setVisibility(8);
            return;
        }
        if (easeInputMenuStyle == EaseInputMenuStyle.ONLY_TEXT) {
            this.buttonSetModeVoice.setVisibility(8);
            this.buttonSetModeKeyboard.setVisibility(8);
            this.buttonPressToSpeak.setVisibility(8);
            this.faceLayout.setVisibility(8);
            this.buttonMore.setVisibility(8);
        }
    }

    private void checkSendButton() {
        if (TextUtils.isEmpty(this.editText.getText().toString().trim())) {
            this.buttonMore.setVisibility(0);
            this.buttonSend.setVisibility(8);
        } else {
            this.buttonMore.setVisibility(8);
            this.buttonSend.setVisibility(0);
        }
    }

    private void initListener() {
        this.buttonSend.setOnClickListener(this);
        this.buttonSetModeKeyboard.setOnClickListener(this);
        this.buttonSetModeVoice.setOnClickListener(this);
        this.buttonMore.setOnClickListener(this);
        this.faceLayout.setOnClickListener(this);
        this.editText.setOnClickListener(this);
        this.editText.setOnEditTextChangeListener(this);
        this.editText.addTextChangedListener(this);
        this.buttonPressToSpeak.setOnTouchListener(new View.OnTouchListener() { // from class: com.hyphenate.easeui.modules.chat.j
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f8660c.lambda$initListener$0(view, motionEvent);
            }
        });
    }

    private void initViews() {
        this.rlBottom = (LinearLayout) findViewById(R.id.rl_bottom);
        this.buttonSetModeVoice = (ImageView) findViewById(R.id.btn_set_mode_voice);
        this.buttonSetModeKeyboard = (ImageView) findViewById(R.id.btn_set_mode_keyboard);
        this.buttonPressToSpeak = (FrameLayout) findViewById(R.id.btn_press_to_speak);
        this.edittext_layout = (FrameLayout) findViewById(R.id.edittext_layout);
        this.editText = (EaseInputEditText) findViewById(R.id.et_sendmessage);
        this.faceLayout = (RelativeLayout) findViewById(R.id.rl_face);
        this.faceNormal = (ImageView) findViewById(R.id.iv_face_normal);
        this.faceChecked = (ImageView) findViewById(R.id.iv_face_checked);
        this.buttonMore = (CheckBox) findViewById(R.id.btn_more);
        this.buttonSend = (Button) findViewById(R.id.btn_send);
        TextView textView = (TextView) findViewById(R.id.tv_talk);
        this.editText.requestFocus();
        showNormalStatus();
        initListener();
        if (!DarkModeHelper.isDarkMode(getContext())) {
            this.faceChecked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ease_chatting_setmode_keyboard_btn));
            this.faceNormal.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ease_chatting_biaoqing_btn_normal));
            return;
        }
        this.faceChecked.setImageDrawable(null);
        this.editText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ease_send_message_et_bg_night));
        this.buttonSetModeVoice.setImageResource(R.drawable.ease_chatting_setmode_voice_btn_night);
        ImageView imageView = this.faceChecked;
        Context context = getContext();
        int i2 = R.drawable.ease_chatting_setmode_keyboard_btn_night;
        imageView.setImageDrawable(ContextCompat.getDrawable(context, i2));
        this.faceNormal.setImageDrawable(ContextCompat.getDrawable(getContext(), i2));
        this.buttonMore.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ease_type_select_btn_night));
        this.buttonSetModeKeyboard.setImageResource(R.drawable.ease_chatting_setmode_keyboard_btn_night_new);
        this.buttonSend.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ease_chat_send_btn_selector_night));
        this.buttonSend.setTextColor(Color.parseColor("#7380A9"));
        textView.setTextColor(Color.parseColor("#7380A9"));
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ease_chat_press_speak_btn_night));
        this.rlBottom.setBackgroundColor(Color.parseColor("#1C2134"));
    }

    private boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initListener$0(View view, MotionEvent motionEvent) {
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            return easeChatPrimaryMenuListener.onPressToSpeakBtnTouch(view, motionEvent);
        }
        return false;
    }

    private void showNormalFaceImage() {
        this.faceNormal.setVisibility(0);
        this.faceChecked.setVisibility(4);
    }

    private void showSelectedFaceImage() {
        this.faceNormal.setVisibility(4);
        this.faceChecked.setVisibility(0);
    }

    private void showSendButton(CharSequence charSequence) {
        boolean zIsSpace = isSpace(charSequence.toString());
        if (TextUtils.isEmpty(charSequence) || zIsSpace) {
            this.buttonMore.setVisibility(0);
            this.buttonSend.setVisibility(8);
        } else {
            this.buttonMore.setVisibility(8);
            this.buttonSend.setVisibility(0);
        }
        checkMenuType();
    }

    private void showSoftKeyboard(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.requestFocus();
        this.inputManager.showSoftInput(editText, 1);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        Log.e("TAG", getClass().getSimpleName() + " afterTextChanged s:" + ((Object) editable));
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public EditText getEditText() {
        return this.editText;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void hideExtendStatus() {
        this.buttonMore.setChecked(false);
        showNormalFaceImage();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void hideSoftKeyboard() {
        EaseInputEditText easeInputEditText = this.editText;
        if (easeInputEditText == null) {
            return;
        }
        easeInputEditText.requestFocus();
        if (this.activity.getWindow().getAttributes().softInputMode == 2 || this.activity.getCurrentFocus() == null) {
            return;
        }
        this.inputManager.hideSoftInputFromWindow(this.activity.getCurrentFocus().getWindowToken(), 2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_send) {
            if (this.listener != null) {
                String string = this.editText.getText().toString();
                this.editText.setText("");
                this.listener.onSendBtnClicked(string);
                return;
            }
            return;
        }
        if (id == R.id.btn_set_mode_voice) {
            showVoiceStatus();
            return;
        }
        if (id == R.id.btn_set_mode_keyboard) {
            showTextStatus();
            return;
        }
        if (id == R.id.btn_more) {
            showMoreStatus();
        } else if (id == R.id.et_sendmessage) {
            showTextStatus();
        } else if (id == R.id.rl_face) {
            showEmojiconStatus();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseInputEditText.OnEditTextChangeListener
    public void onClickKeyboardSendBtn(String str) {
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onSendBtnClicked(str);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.editText.removeTextChangedListener(this);
    }

    @Override // com.hyphenate.easeui.modules.chat.EaseInputEditText.OnEditTextChangeListener
    public void onEditTextHasFocus(boolean z2) {
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onEditTextHasFocus(z2);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void onEmojiconDeleteEvent() {
        if (TextUtils.isEmpty(this.editText.getText())) {
            return;
        }
        this.editText.dispatchKeyEvent(new KeyEvent(0L, 0L, 0, 67, 0, 0, 0, 0, 6));
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void onEmojiconInputEvent(CharSequence charSequence) {
        this.editText.append(charSequence);
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        Log.e("TAG", getClass().getSimpleName() + " onTextChanged s:" + ((Object) charSequence));
        showSendButton(charSequence);
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onTyping(charSequence, i2, i3, i4);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void onTextInsert(CharSequence charSequence) {
        this.editText.getEditableText().insert(this.editText.getSelectionStart(), charSequence);
        showTextStatus();
    }

    public void setDarkMode(boolean z2) {
        this.isDarkMode = z2;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void setEaseChatPrimaryMenuListener(EaseChatPrimaryMenuListener easeChatPrimaryMenuListener) {
        this.listener = easeChatPrimaryMenuListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void setMenuBackground(Drawable drawable) {
        this.rlBottom.setBackground(drawable);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void setMenuShowType(EaseInputMenuStyle easeInputMenuStyle) {
        this.menuType = easeInputMenuStyle;
        checkMenuType();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void setSendButtonBackground(Drawable drawable) {
        this.buttonSend.setBackground(drawable);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void showEmojiconStatus() {
        this.buttonSetModeVoice.setVisibility(0);
        this.buttonSetModeKeyboard.setVisibility(8);
        this.edittext_layout.setVisibility(0);
        this.buttonPressToSpeak.setVisibility(8);
        this.buttonMore.setChecked(false);
        if (this.faceNormal.getVisibility() == 0) {
            hideSoftKeyboard();
            showSelectedFaceImage();
        } else {
            showSoftKeyboard(this.editText);
            showNormalFaceImage();
        }
        checkMenuType();
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onToggleEmojiconClicked(this.faceChecked.getVisibility() == 0);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void showMoreStatus() {
        if (this.buttonMore.isChecked()) {
            hideSoftKeyboard();
            this.buttonSetModeVoice.setVisibility(0);
            this.buttonSetModeKeyboard.setVisibility(8);
            this.edittext_layout.setVisibility(0);
            this.buttonPressToSpeak.setVisibility(8);
            showNormalFaceImage();
        } else {
            showTextStatus();
        }
        checkMenuType();
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onToggleExtendClicked(this.buttonMore.isChecked());
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void showNormalStatus() {
        hideSoftKeyboard();
        this.buttonSetModeVoice.setVisibility(0);
        this.buttonSetModeKeyboard.setVisibility(8);
        this.edittext_layout.setVisibility(0);
        this.buttonPressToSpeak.setVisibility(8);
        hideExtendStatus();
        checkSendButton();
        checkMenuType();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void showTextStatus() {
        this.buttonSetModeVoice.setVisibility(0);
        this.buttonSetModeKeyboard.setVisibility(8);
        this.edittext_layout.setVisibility(0);
        this.buttonPressToSpeak.setVisibility(8);
        hideExtendStatus();
        showSoftKeyboard(this.editText);
        checkSendButton();
        checkMenuType();
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onToggleTextBtnClicked();
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatPrimaryMenu
    public void showVoiceStatus() {
        hideSoftKeyboard();
        this.buttonSetModeVoice.setVisibility(8);
        this.buttonSetModeKeyboard.setVisibility(0);
        this.edittext_layout.setVisibility(8);
        this.buttonPressToSpeak.setVisibility(0);
        hideExtendStatus();
        checkMenuType();
        EaseChatPrimaryMenuListener easeChatPrimaryMenuListener = this.listener;
        if (easeChatPrimaryMenuListener != null) {
            easeChatPrimaryMenuListener.onToggleVoiceBtnClicked();
        }
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.menuType = EaseInputMenuStyle.All;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        this.activity = (Activity) context;
        this.inputManager = (InputMethodManager) context.getSystemService("input_method");
        initViews();
    }
}
