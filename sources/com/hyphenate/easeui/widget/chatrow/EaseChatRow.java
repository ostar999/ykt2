package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseBaseAdapter;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.model.EaseChatItemStyleHelper;
import com.hyphenate.easeui.modules.chat.model.EaseChatSetStyle;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseDateUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.util.EMLog;
import java.util.Date;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes4.dex */
public abstract class EaseChatRow extends LinearLayout {
    protected static final String TAG = "EaseChatRow";
    protected TextView ackedView;
    protected Object adapter;
    protected View bubbleLayout;
    protected EaseChatCallback chatCallback;
    protected Context context;
    protected TextView deliveredView;
    protected LayoutInflater inflater;
    protected boolean isSender;
    private EaseChatRowActionCallback itemActionCallback;
    protected MessageListItemClickListener itemClickListener;
    private Handler mainThreadHandler;
    protected EMMessage message;
    protected TextView percentageView;
    protected int position;
    protected ProgressBar progressBar;
    protected boolean showSenderType;
    protected ImageView statusView;
    protected TextView timeStampView;
    protected ImageView userAvatarView;
    protected TextView usernickView;

    /* renamed from: com.hyphenate.easeui.widget.chatrow.EaseChatRow$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$Status;

        static {
            int[] iArr = new int[EMMessage.Status.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$Status = iArr;
            try {
                iArr[EMMessage.Status.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[EMMessage.Status.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[EMMessage.Status.FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[EMMessage.Status.INPROGRESS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public class EaseChatCallback implements EMCallBack {
        private EaseChatCallback() {
        }

        @Override // com.hyphenate.EMCallBack
        public void onError(final int i2, final String str) {
            EaseChatRow.this.mainThreadHandler.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    EaseChatRow.this.onMessageError();
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if (messageListItemClickListener != null) {
                        messageListItemClickListener.onMessageError(easeChatRow.message, i2, str);
                    }
                }
            });
        }

        @Override // com.hyphenate.EMCallBack
        public void onProgress(final int i2, String str) {
            EaseChatRow.this.mainThreadHandler.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    EaseChatRow.this.onMessageInProgress();
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if (messageListItemClickListener != null) {
                        messageListItemClickListener.onMessageInProgress(easeChatRow.message, i2);
                    }
                }
            });
        }

        @Override // com.hyphenate.EMCallBack
        public void onSuccess() {
            EaseChatRow.this.mainThreadHandler.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    EaseChatRow.this.onMessageSuccess();
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if (messageListItemClickListener != null) {
                        messageListItemClickListener.onMessageSuccess(easeChatRow.message);
                    }
                }
            });
        }
    }

    public interface EaseChatRowActionCallback {
        void onBubbleClick(EMMessage eMMessage);

        void onDetachedFromWindow();

        void onResendClick(EMMessage eMMessage);
    }

    public EaseChatRow(Context context, boolean z2) {
        super(context);
        this.context = context;
        this.isSender = z2;
        this.inflater = LayoutInflater.from(context);
        initView();
    }

    private EaseChatItemStyleHelper getItemStyleHelper() {
        return EaseChatItemStyleHelper.getInstance();
    }

    private void initView() {
        this.showSenderType = this.isSender;
        EaseChatItemStyleHelper itemStyleHelper = getItemStyleHelper();
        if (itemStyleHelper != null && itemStyleHelper.getStyle() != null) {
            if (itemStyleHelper.getStyle().getItemShowType() == 1) {
                this.showSenderType = false;
            } else if (itemStyleHelper.getStyle().getItemShowType() == 2) {
                this.showSenderType = true;
            }
        }
        onInflateView();
        this.timeStampView = (TextView) findViewById(R.id.timestamp);
        this.userAvatarView = (ImageView) findViewById(R.id.iv_userhead);
        this.bubbleLayout = findViewById(R.id.bubble);
        this.usernickView = (TextView) findViewById(R.id.tv_userid);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.statusView = (ImageView) findViewById(R.id.msg_status);
        this.ackedView = (TextView) findViewById(R.id.tv_ack);
        this.deliveredView = (TextView) findViewById(R.id.tv_delivered);
        if (DarkModeHelper.isDarkMode(getContext())) {
            TextView textView = this.timeStampView;
            if (textView != null) {
                textView.setTextColor(Color.parseColor("#575F79"));
            }
            TextView textView2 = this.ackedView;
            if (textView2 != null) {
                textView2.setTextColor(Color.parseColor("#575F79"));
            }
        }
        setLayoutStyle();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        onFindViewById();
    }

    private void setClickListener() {
        this.chatCallback = new EaseChatCallback();
        View view = this.bubbleLayout;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if ((messageListItemClickListener == null || !messageListItemClickListener.onBubbleClick(view2, easeChatRow.message)) && EaseChatRow.this.itemActionCallback != null) {
                        EaseChatRow.this.itemActionCallback.onBubbleClick(EaseChatRow.this.message);
                    }
                }
            });
            this.bubbleLayout.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if (messageListItemClickListener != null) {
                        return messageListItemClickListener.onBubbleLongClick(view2, easeChatRow.message);
                    }
                    return false;
                }
            });
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRow.itemClickListener;
                    if ((messageListItemClickListener == null || !messageListItemClickListener.onResendClick(easeChatRow.message)) && EaseChatRow.this.itemActionCallback != null) {
                        EaseChatRow.this.itemActionCallback.onResendClick(EaseChatRow.this.message);
                    }
                }
            });
        }
        ImageView imageView2 = this.userAvatarView;
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    if (easeChatRow.itemClickListener != null) {
                        if (easeChatRow.message.direct() == EMMessage.Direct.SEND) {
                            EaseChatRow.this.itemClickListener.onUserAvatarClick(EMClient.getInstance().getCurrentUser());
                        } else {
                            EaseChatRow easeChatRow2 = EaseChatRow.this;
                            easeChatRow2.itemClickListener.onUserAvatarClick(easeChatRow2.message.getFrom());
                        }
                    }
                }
            });
            this.userAvatarView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRow.5
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    EaseChatRow easeChatRow = EaseChatRow.this;
                    if (easeChatRow.itemClickListener == null) {
                        return false;
                    }
                    if (easeChatRow.message.direct() == EMMessage.Direct.SEND) {
                        EaseChatRow.this.itemClickListener.onUserAvatarLongClick(EMClient.getInstance().getCurrentUser());
                        return true;
                    }
                    try {
                        String stringAttribute = EaseChatRow.this.message.getStringAttribute("nickname", "");
                        if (TextUtils.isEmpty(stringAttribute)) {
                            EaseChatRow easeChatRow2 = EaseChatRow.this;
                            easeChatRow2.itemClickListener.onUserAvatarLongClick(easeChatRow2.message.getFrom());
                        } else {
                            EaseChatRow.this.itemClickListener.onUserAvatarLongClick(stringAttribute);
                        }
                        return true;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return true;
                    }
                }
            });
        }
    }

    private void setItemStyle() {
        EaseChatItemStyleHelper itemStyleHelper = getItemStyleHelper();
        if (itemStyleHelper != null) {
            EaseChatSetStyle style = itemStyleHelper.getStyle();
            if (this.userAvatarView != null) {
                setAvatarOptions(style);
            }
            if (this.usernickView != null) {
                if (style.getItemShowType() == 1 || style.getItemShowType() == 2) {
                    this.usernickView.setVisibility(0);
                } else {
                    this.usernickView.setVisibility((style.isShowNickname() && this.message.direct() == EMMessage.Direct.RECEIVE) ? 0 : 8);
                }
            }
            if (this.bubbleLayout == null || this.message.getType() != EMMessage.Type.TXT || style.getItemMinHeight() == 0) {
                return;
            }
            this.bubbleLayout.setMinimumHeight(style.getItemMinHeight());
        }
    }

    private void setUpBaseView() throws NumberFormatException {
        TextView textView = (TextView) findViewById(R.id.timestamp);
        if (textView != null) {
            setTimestamp(textView);
        }
        setItemStyle();
        if (this.userAvatarView != null) {
            setAvatarAndNick();
        }
        if (EMClient.getInstance().getOptions().getRequireDeliveryAck() && this.deliveredView != null && isSender()) {
            if (this.message.isDelivered()) {
                this.deliveredView.setVisibility(0);
            } else {
                this.deliveredView.setVisibility(4);
            }
        }
        if (EMClient.getInstance().getOptions().getRequireAck() && this.ackedView != null && isSender()) {
            if (!this.message.isAcked()) {
                this.ackedView.setVisibility(4);
                return;
            }
            TextView textView2 = this.deliveredView;
            if (textView2 != null) {
                textView2.setVisibility(4);
            }
            this.ackedView.setVisibility(0);
        }
    }

    public boolean isSender() {
        return this.isSender;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.itemActionCallback.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    public abstract void onFindViewById();

    public abstract void onInflateView();

    public void onMessageCreate() {
        EMLog.i(TAG, "onMessageCreate");
    }

    public void onMessageError() {
        TextView textView = this.ackedView;
        if (textView != null) {
            textView.setVisibility(4);
        }
        TextView textView2 = this.deliveredView;
        if (textView2 != null) {
            textView2.setVisibility(4);
        }
        EMLog.e(TAG, "onMessageError");
    }

    public void onMessageInProgress() {
        EMLog.i(TAG, "onMessageInProgress");
    }

    public void onMessageSuccess() {
        EMLog.i(TAG, "onMessageSuccess");
    }

    public abstract void onSetUpView();

    public void onViewUpdate(EMMessage eMMessage) {
        int i2 = AnonymousClass6.$SwitchMap$com$hyphenate$chat$EMMessage$Status[eMMessage.status().ordinal()];
        if (i2 == 1) {
            onMessageCreate();
            MessageListItemClickListener messageListItemClickListener = this.itemClickListener;
            if (messageListItemClickListener != null) {
                messageListItemClickListener.onMessageCreate(eMMessage);
                return;
            }
            return;
        }
        if (i2 == 2) {
            onMessageSuccess();
            return;
        }
        if (i2 == 3) {
            onMessageError();
        } else if (i2 != 4) {
            EMLog.i(TAG, ServletHandler.__DEFAULT_SERVLET);
        } else {
            onMessageInProgress();
        }
    }

    public EaseAvatarOptions provideAvatarOptions() {
        return EaseIM.getInstance().getAvatarOptions();
    }

    public void resetViewState() {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        TextView textView = this.ackedView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        TextView textView2 = this.deliveredView;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    public void setAvatarAndNick() throws NumberFormatException {
        if (isSender()) {
            EaseUserUtils.setUserAvatar(this.context, EMClient.getInstance().getCurrentUser(), this.userAvatarView);
            if (EaseChatItemStyleHelper.getInstance().getStyle().getItemShowType() != EaseChatMessageListLayout.ShowType.NORMAL.ordinal()) {
                EaseUserUtils.setUserNick(this.message.getFrom(), this.usernickView);
                return;
            }
            return;
        }
        String stringAttribute = this.message.getStringAttribute("nickname", "");
        String stringAttribute2 = this.message.getStringAttribute("avatar", "");
        if (TextUtils.isEmpty(stringAttribute)) {
            EaseUserUtils.setUserNick(this.message.getFrom(), this.usernickView);
        } else {
            this.usernickView.setText(stringAttribute);
        }
        if (TextUtils.isEmpty(stringAttribute2)) {
            EaseUserUtils.setUserAvatar(this.context, this.message.getFrom(), this.userAvatarView);
        } else {
            Glide.with(this.context).load(stringAttribute2).into(this.userAvatarView);
        }
    }

    public void setAvatarOptions(EaseChatSetStyle easeChatSetStyle) {
        if (!easeChatSetStyle.isShowAvatar()) {
            this.userAvatarView.setVisibility(8);
            return;
        }
        this.userAvatarView.setVisibility(0);
        ImageView imageView = this.userAvatarView;
        if (imageView instanceof EaseImageView) {
            EaseImageView easeImageView = (EaseImageView) imageView;
            if (easeChatSetStyle.getAvatarDefaultSrc() != null) {
                easeImageView.setImageDrawable(easeChatSetStyle.getAvatarDefaultSrc());
            }
            easeImageView.setShapeType(easeChatSetStyle.getShapeType());
            if (easeChatSetStyle.getAvatarSize() != 0.0f) {
                ViewGroup.LayoutParams layoutParams = easeImageView.getLayoutParams();
                layoutParams.width = (int) easeChatSetStyle.getAvatarSize();
                layoutParams.height = (int) easeChatSetStyle.getAvatarSize();
            }
            if (easeChatSetStyle.getBorderWidth() != 0.0f) {
                easeImageView.setBorderWidth((int) easeChatSetStyle.getBorderWidth());
            }
            if (easeChatSetStyle.getBorderColor() != 0) {
                easeImageView.setBorderColor(easeChatSetStyle.getBorderColor());
            }
            if (easeChatSetStyle.getAvatarRadius() != 0.0f) {
                easeImageView.setRadius((int) easeChatSetStyle.getAvatarRadius());
            }
        }
        EaseAvatarOptions easeAvatarOptionsProvideAvatarOptions = provideAvatarOptions();
        if (easeAvatarOptionsProvideAvatarOptions != null) {
            ImageView imageView2 = this.userAvatarView;
            if (imageView2 instanceof EaseImageView) {
                EaseImageView easeImageView2 = (EaseImageView) imageView2;
                if (easeAvatarOptionsProvideAvatarOptions.getAvatarShape() != 0) {
                    easeImageView2.setShapeType(easeAvatarOptionsProvideAvatarOptions.getAvatarShape());
                }
                if (easeAvatarOptionsProvideAvatarOptions.getAvatarBorderWidth() != 0) {
                    easeImageView2.setBorderWidth(easeAvatarOptionsProvideAvatarOptions.getAvatarBorderWidth());
                }
                if (easeAvatarOptionsProvideAvatarOptions.getAvatarBorderColor() != 0) {
                    easeImageView2.setBorderColor(easeAvatarOptionsProvideAvatarOptions.getAvatarBorderColor());
                }
                if (easeAvatarOptionsProvideAvatarOptions.getAvatarRadius() != 0) {
                    easeImageView2.setRadius(easeAvatarOptionsProvideAvatarOptions.getAvatarRadius());
                }
            }
        }
    }

    public void setLayoutStyle() {
        EaseChatItemStyleHelper itemStyleHelper = getItemStyleHelper();
        if (itemStyleHelper != null) {
            EaseChatSetStyle style = itemStyleHelper.getStyle();
            if (this.bubbleLayout != null) {
                try {
                    if (isSender()) {
                        Drawable senderBgDrawable = style.getSenderBgDrawable();
                        if (senderBgDrawable != null) {
                            this.bubbleLayout.setBackground(senderBgDrawable.getConstantState().newDrawable());
                        }
                    } else {
                        Drawable receiverBgDrawable = style.getReceiverBgDrawable();
                        if (receiverBgDrawable != null) {
                            this.bubbleLayout.setBackground(receiverBgDrawable.getConstantState().newDrawable());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (this.timeStampView != null) {
                if (style.getTimeBgDrawable() != null) {
                    this.timeStampView.setBackground(style.getTimeBgDrawable().getConstantState().newDrawable());
                }
                if (style.getTimeTextSize() != 0) {
                    this.timeStampView.setTextSize(0, style.getTimeTextSize());
                }
                if (style.getTimeTextColor() != 0) {
                    this.timeStampView.setTextColor(style.getTimeTextColor());
                }
            }
            TextView textView = (TextView) findViewById(R.id.tv_chatcontent);
            if (textView != null) {
                if (style.getTextSize() != 0) {
                    textView.setTextSize(0, style.getTextSize());
                }
                if (style.getTextColor() != 0) {
                    textView.setTextColor(style.getTextColor());
                }
            }
        }
    }

    public void setTimestamp(TextView textView) {
        Object obj = this.adapter;
        if (obj != null) {
            int i2 = this.position;
            if (i2 == 0) {
                textView.setText(EaseDateUtils.getTimestampString(getContext(), new Date(this.message.getMsgTime())));
                textView.setVisibility(0);
                return;
            }
            EMMessage eMMessage = obj instanceof BaseAdapter ? (EMMessage) ((BaseAdapter) obj).getItem(i2 - 1) : null;
            Object obj2 = this.adapter;
            if (obj2 instanceof EaseBaseAdapter) {
                eMMessage = (EMMessage) ((EaseBaseAdapter) obj2).getItem(this.position - 1);
            }
            if (eMMessage != null && EaseDateUtils.isCloseEnough(this.message.getMsgTime(), eMMessage.getMsgTime())) {
                textView.setVisibility(8);
            } else {
                textView.setText(EaseDateUtils.getTimestampString(getContext(), new Date(this.message.getMsgTime())));
                textView.setVisibility(0);
            }
        }
    }

    public void setUpView(EMMessage eMMessage, int i2, MessageListItemClickListener messageListItemClickListener, EaseChatRowActionCallback easeChatRowActionCallback) throws NumberFormatException {
        this.message = eMMessage;
        this.position = i2;
        this.itemClickListener = messageListItemClickListener;
        this.itemActionCallback = easeChatRowActionCallback;
        setUpBaseView();
        onSetUpView();
        setClickListener();
    }

    public void updateView(EMMessage eMMessage) {
        if (this.chatCallback == null) {
            this.chatCallback = new EaseChatCallback();
        }
        eMMessage.setMessageStatusCallback(this.chatCallback);
        onViewUpdate(eMMessage);
    }

    public EaseChatRow(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context);
        this.context = context;
        this.message = eMMessage;
        this.isSender = eMMessage.direct() == EMMessage.Direct.SEND;
        this.position = i2;
        this.adapter = obj;
        this.inflater = LayoutInflater.from(context);
        initView();
    }

    public void setTimestamp(EMMessage eMMessage) {
        if (this.position == 0) {
            this.timeStampView.setText(EaseDateUtils.getTimestampString(getContext(), new Date(this.message.getMsgTime())));
            this.timeStampView.setVisibility(0);
        } else if (eMMessage != null && EaseDateUtils.isCloseEnough(this.message.getMsgTime(), eMMessage.getMsgTime())) {
            this.timeStampView.setVisibility(8);
        } else {
            this.timeStampView.setText(EaseDateUtils.getTimestampString(getContext(), new Date(this.message.getMsgTime())));
            this.timeStampView.setVisibility(0);
        }
    }
}
