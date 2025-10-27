package com.easefun.polyv.livecloudclass.modules.chatroom;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.utils.PLVChatroomUtils;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.utils.span.PLVTextFaceLoader;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewerFragment;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVInputFragment;
import com.easefun.polyv.livescenes.chatroom.PolyvQuestionMessage;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVTAnswerEvent;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

/* loaded from: classes3.dex */
public class PLVLCQuizFragment extends PLVInputFragment implements View.OnClickListener {
    private PLVChatImageViewerFragment chatImageViewerFragment;
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    private ImageView deleteMsgIv;
    private ViewGroup emojiLy;
    private RecyclerView emojiRv;
    private EditText inputEt;
    private PLVLCMessageAdapter messageAdapter;
    private PLVMessageRecyclerView quizMsgRv;
    private TextView sendMsgTv;
    private ImageView toggleEmojiIv;
    private IPLVChatroomContract.IChatroomView chatroomView = new PLVAbsChatroomView() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment.2
        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public int getQuizEmojiSize() {
            return ConvertUtils.dp2px(16.0f);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onAnswerEvent(@NonNull PLVTAnswerEvent pLVTAnswerEvent) {
            super.onAnswerEvent(pLVTAnswerEvent);
            PLVLCQuizFragment.this.addQuizMessageToList(new PLVBaseViewData(pLVTAnswerEvent, 6, new PLVSpecialTypeTag(pLVTAnswerEvent.getUserId())));
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLocalQuestionMessage(@Nullable PolyvQuestionMessage polyvQuestionMessage) {
            super.onLocalQuestionMessage(polyvQuestionMessage);
            if (polyvQuestionMessage == null) {
                return;
            }
            final PLVBaseViewData pLVBaseViewData = new PLVBaseViewData(polyvQuestionMessage, 5, new PLVSpecialTypeTag(polyvQuestionMessage.getUserId()));
            if (PLVLCQuizFragment.this.isShowKeyBoard(new PLVInputFragment.OnceHideKeyBoardListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment.2.1
                @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment.OnceHideKeyBoardListener
                public void call() {
                    PLVLCQuizFragment.this.addQuizMessageToList(pLVBaseViewData);
                }
            })) {
                return;
            }
            PLVLCQuizFragment.this.addQuizMessageToList(pLVBaseViewData);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
            super.setPresenter(iChatroomPresenter);
            PLVLCQuizFragment.this.chatroomPresenter = iChatroomPresenter;
        }
    };
    private TextWatcher inputTextWatcher = new TextWatcher() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment.4
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            if (charSequence == null || charSequence.length() <= 0) {
                PLVLCQuizFragment.this.sendMsgTv.setSelected(false);
                PLVLCQuizFragment.this.sendMsgTv.setEnabled(false);
            } else {
                PLVLCQuizFragment.this.sendMsgTv.setEnabled(true);
                PLVLCQuizFragment.this.sendMsgTv.setSelected(true);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void addQuizMessageToList(final PLVBaseViewData pLVBaseViewData) {
        this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment.3
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCQuizFragment.this.messageAdapter != null && PLVLCQuizFragment.this.messageAdapter.addDataChangedAtLast(pLVBaseViewData)) {
                    PLVLCQuizFragment.this.quizMsgRv.scrollToPosition(PLVLCQuizFragment.this.messageAdapter.getItemCount() - 1);
                }
            }
        });
    }

    private void addQuizTipsToList() {
        PLVTAnswerEvent pLVTAnswerEvent = new PLVTAnswerEvent();
        pLVTAnswerEvent.setContent("同学，您好！请问有什么问题吗？");
        pLVTAnswerEvent.setObjects(PLVTextFaceLoader.messageToSpan(pLVTAnswerEvent.getContent(), ConvertUtils.dp2px(14.0f), getContext()));
        PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
        pLVSocketUserBean.setUserType("teacher");
        pLVSocketUserBean.setNick(PLVSocketUserConstant.ACTOR_TEACHER);
        pLVSocketUserBean.setActor(PLVSocketUserConstant.ACTOR_TEACHER);
        pLVSocketUserBean.setPic(PLVSocketUserConstant.TEACHER_AVATAR_URL);
        pLVTAnswerEvent.setUser(pLVSocketUserBean);
        addQuizMessageToList(new PLVBaseViewData(pLVTAnswerEvent, 6, new PLVSpecialTypeTag(null)));
    }

    private void initView() {
        if (this.chatroomPresenter == null) {
            return;
        }
        PLVMessageRecyclerView pLVMessageRecyclerView = (PLVMessageRecyclerView) findViewById(R.id.chat_msg_rv);
        this.quizMsgRv = pLVMessageRecyclerView;
        pLVMessageRecyclerView.addItemDecoration(new PLVMessageRecyclerView.SpacesItemDecoration(ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(16.0f)));
        PLVMessageRecyclerView.setLayoutManager(this.quizMsgRv);
        PLVLCMessageAdapter pLVLCMessageAdapter = new PLVLCMessageAdapter();
        this.messageAdapter = pLVLCMessageAdapter;
        pLVLCMessageAdapter.setOnViewActionListener(new PLVLCMessageAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCQuizFragment.1
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter.OnViewActionListener
            public void onChatImgClick(int i2, View view, String str, boolean z2) {
                if (z2) {
                    PLVLCQuizFragment pLVLCQuizFragment = PLVLCQuizFragment.this;
                    pLVLCQuizFragment.chatImageViewerFragment = PLVChatImageViewerFragment.show((AppCompatActivity) pLVLCQuizFragment.getContext(), PLVLCQuizFragment.this.messageAdapter.getDataList().get(i2), android.R.id.content);
                } else {
                    PLVLCQuizFragment pLVLCQuizFragment2 = PLVLCQuizFragment.this;
                    pLVLCQuizFragment2.chatImageViewerFragment = PLVChatImageViewerFragment.show((AppCompatActivity) pLVLCQuizFragment2.getContext(), PLVLCQuizFragment.this.messageAdapter.getDataList(), (PLVBaseViewData<PLVBaseEvent>) PLVLCQuizFragment.this.messageAdapter.getDataList().get(i2), android.R.id.content);
                }
            }
        });
        this.messageAdapter.setMsgIndex(this.chatroomPresenter.getViewIndex(this.chatroomView));
        this.quizMsgRv.setAdapter(this.messageAdapter);
        addQuizTipsToList();
        EditText editText = (EditText) findViewById(R.id.input_et);
        this.inputEt = editText;
        editText.addTextChangedListener(this.inputTextWatcher);
        ImageView imageView = (ImageView) findViewById(R.id.toggle_emoji_iv);
        this.toggleEmojiIv = imageView;
        imageView.setOnClickListener(this);
        this.emojiLy = (ViewGroup) findViewById(R.id.emoji_ly);
        TextView textView = (TextView) findViewById(R.id.send_msg_tv);
        this.sendMsgTv = textView;
        textView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.delete_msg_iv);
        this.deleteMsgIv = imageView2;
        imageView2.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.emoji_rv);
        this.emojiRv = recyclerView;
        PLVChatroomUtils.initEmojiList(recyclerView, this.inputEt);
        addPopupButton(this.toggleEmojiIv);
        addPopupLayout(this.emojiLy);
    }

    private boolean sendQuestionMessage(String str) {
        if (str.trim().length() == 0) {
            ToastUtils.showLong(R.string.plv_chat_toast_send_text_empty);
            return false;
        }
        PolyvQuestionMessage polyvQuestionMessage = new PolyvQuestionMessage(str);
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
        if (iChatroomPresenter == null) {
            return false;
        }
        int iSendQuestionMessage = iChatroomPresenter.sendQuestionMessage(polyvQuestionMessage);
        if (iSendQuestionMessage > 0) {
            this.inputEt.setText("");
            hideSoftInputAndPopupLayout();
            return true;
        }
        ToastUtils.showShort(getString(R.string.plv_chat_toast_send_quiz_failed) + ":" + iSendQuestionMessage);
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int attachContainerViewId() {
        return R.id.plvlc_chatroom_input_layout_container;
    }

    public IPLVChatroomContract.IChatroomView getChatroomView() {
        return this.chatroomView;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int inputLayoutId() {
        return R.id.bottom_input_ly;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int inputViewId() {
        return R.id.input_et;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public boolean onBackPressed() {
        PLVChatImageViewerFragment pLVChatImageViewerFragment = this.chatImageViewerFragment;
        if (pLVChatImageViewerFragment == null || !pLVChatImageViewerFragment.isVisible()) {
            return super.onBackPressed();
        }
        this.chatImageViewerFragment.hide();
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.toggle_emoji_iv) {
            togglePopupLayout(this.toggleEmojiIv, this.emojiLy);
        } else if (id == R.id.delete_msg_iv) {
            PLVChatroomUtils.deleteEmoText(this.inputEt);
        } else if (id == R.id.send_msg_tv) {
            sendQuestionMessage(this.inputEt.getText().toString());
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.plvlc_chatroom_quiz_fragment, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public boolean onSendMsg(String str) {
        return sendQuestionMessage(str);
    }
}
