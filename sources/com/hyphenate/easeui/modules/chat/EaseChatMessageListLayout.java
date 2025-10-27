package com.hyphenate.easeui.modules.chat;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.interfaces.OnItemLongClickListener;
import com.hyphenate.easeui.manager.EaseMessageTypeSetManager;
import com.hyphenate.easeui.manager.EaseThreadManager;
import com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet;
import com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout;
import com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle;
import com.hyphenate.easeui.modules.chat.model.EaseChatItemStyleHelper;
import com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenter;
import com.hyphenate.easeui.modules.chat.presenter.EaseChatMessagePresenterImpl;
import com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatMessageListLayout extends RelativeLayout implements IChatMessageListView, IRecyclerViewHandle, IChatMessageItemSet, IChatMessageListLayout {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String TAG = "EaseChatMessageListLayout";
    private ConcatAdapter baseAdapter;
    private boolean canUseRefresh;
    private EaseChatItemStyleHelper chatSetHelper;
    private EMConversation.EMConversationType conType;
    private EMConversation conversation;
    private OnChatErrorListener errorListener;
    private boolean isFrist;
    private LinearLayoutManager layoutManager;
    private LoadDataType loadDataType;
    private LoadMoreStatus loadMoreStatus;
    private EaseMessageAdapter messageAdapter;
    private MessageListItemClickListener messageListItemClickListener;
    private OnMessageTouchListener messageTouchListener;
    private String msgId;
    private int pageSize;
    private EaseChatMessagePresenter presenter;
    private int recyclerViewLastHeight;
    private RecyclerView rvList;
    private SwipeRefreshLayout srlRefresh;
    private String username;

    /* renamed from: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout$3, reason: invalid class name */
    public class AnonymousClass3 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGlobalLayout$0() {
            EaseChatMessageListLayout.this.smoothSeekToPosition(r0.messageAdapter.getData().size() - 1);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height = EaseChatMessageListLayout.this.rvList.getHeight();
            if (EaseChatMessageListLayout.this.recyclerViewLastHeight == 0) {
                EaseChatMessageListLayout.this.recyclerViewLastHeight = height;
            }
            if (EaseChatMessageListLayout.this.recyclerViewLastHeight != height && EaseChatMessageListLayout.this.messageAdapter.getData() != null && !EaseChatMessageListLayout.this.messageAdapter.getData().isEmpty()) {
                EaseChatMessageListLayout.this.post(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.i
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8659c.lambda$onGlobalLayout$0();
                    }
                });
            }
            EaseChatMessageListLayout.this.recyclerViewLastHeight = height;
        }
    }

    public enum LoadDataType {
        LOCAL,
        ROAM,
        HISTORY
    }

    public enum LoadMoreStatus {
        IS_LOADING,
        HAS_MORE,
        NO_MORE_DATA
    }

    public interface OnChatErrorListener {
        void onChatError(int i2, String str);
    }

    public interface OnMessageTouchListener {
        void onTouchItemOutside(View view, int i2);

        void onViewDragging();
    }

    public enum ShowType {
        NORMAL,
        LEFT
    }

    public EaseChatMessageListLayout(@NonNull Context context) {
        this(context, null);
    }

    private void checkConType() {
        if (isChatRoomCon()) {
            this.presenter.joinChatRoom(this.username);
        } else {
            loadData();
        }
    }

    private void finishRefresh() {
        if (this.presenter.isActive()) {
            runOnUi(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8651c.lambda$finishRefresh$0();
                }
            });
        }
    }

    private String getListFirstMessageId() {
        EMMessage eMMessage;
        try {
            eMMessage = this.messageAdapter.getData().get(0);
        } catch (Exception e2) {
            e2.printStackTrace();
            eMMessage = null;
        }
        if (eMMessage == null) {
            return null;
        }
        return eMMessage.getMsgId();
    }

    private String getListLastMessageId() {
        EMMessage eMMessage;
        try {
            eMMessage = this.messageAdapter.getData().get(this.messageAdapter.getData().size() - 1);
        } catch (Exception e2) {
            e2.printStackTrace();
            eMMessage = null;
        }
        if (eMMessage == null) {
            return null;
        }
        return eMMessage.getMsgId();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseChatMessageListLayout);
            this.chatSetHelper.setTextSize((int) typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseChatMessageListLayout_ease_chat_item_text_size, 0.0f));
            int i2 = R.styleable.EaseChatMessageListLayout_ease_chat_item_text_color;
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
            this.chatSetHelper.setTextColor(resourceId != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(i2, 0));
            this.chatSetHelper.setItemMinHeight((int) typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseChatMessageListLayout_ease_chat_item_min_height, 0.0f));
            this.chatSetHelper.setTimeTextSize((int) typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseChatMessageListLayout_ease_chat_item_time_text_size, 0.0f));
            int i3 = R.styleable.EaseChatMessageListLayout_ease_chat_item_time_text_color;
            this.chatSetHelper.setTimeTextColor(typedArrayObtainStyledAttributes.getResourceId(i3, -1) != -1 ? ContextCompat.getColor(context, resourceId) : typedArrayObtainStyledAttributes.getColor(i3, 0));
            this.chatSetHelper.setTimeBgDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseChatMessageListLayout_ease_chat_item_time_background));
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseChatMessageListLayout_ease_chat_item_avatar_default_src);
            int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseChatMessageListLayout_ease_chat_item_avatar_shape_type, 0);
            this.chatSetHelper.setAvatarDefaultSrc(drawable);
            this.chatSetHelper.setShapeType(integer);
            this.chatSetHelper.setReceiverBgDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseChatMessageListLayout_ease_chat_item_receiver_background));
            this.chatSetHelper.setSenderBgDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseChatMessageListLayout_ease_chat_item_sender_background));
            this.chatSetHelper.setShowNickname(typedArrayObtainStyledAttributes.getBoolean(R.styleable.EaseChatMessageListLayout_ease_chat_item_show_nickname, false));
            this.chatSetHelper.setItemShowType(typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseChatMessageListLayout_ease_chat_item_show_type, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void initListener() {
        this.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                EaseChatMessageListLayout.this.loadMorePreviousData();
            }
        });
        this.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i2) {
                super.onScrollStateChanged(recyclerView, i2);
                if (i2 != 0) {
                    if (EaseChatMessageListLayout.this.messageTouchListener != null) {
                        EaseChatMessageListLayout.this.messageTouchListener.onViewDragging();
                    }
                } else if (EaseChatMessageListLayout.this.loadDataType == LoadDataType.HISTORY && EaseChatMessageListLayout.this.loadMoreStatus == LoadMoreStatus.HAS_MORE && EaseChatMessageListLayout.this.layoutManager.findLastVisibleItemPosition() != 0 && EaseChatMessageListLayout.this.layoutManager.findLastVisibleItemPosition() == EaseChatMessageListLayout.this.layoutManager.getItemCount() - 1) {
                    EaseChatMessageListLayout.this.loadMoreHistoryData();
                }
            }
        });
        this.rvList.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass3());
        this.messageAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.4
            @Override // com.hyphenate.easeui.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (EaseChatMessageListLayout.this.messageTouchListener != null) {
                    EaseChatMessageListLayout.this.messageTouchListener.onTouchItemOutside(view, i2);
                }
            }
        });
        this.messageAdapter.setListItemClickListener(new MessageListItemClickListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.5
            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public boolean onBubbleClick(View view, EMMessage eMMessage) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    return EaseChatMessageListLayout.this.messageListItemClickListener.onBubbleClick(view, eMMessage);
                }
                return false;
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public boolean onBubbleLongClick(View view, EMMessage eMMessage) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    return EaseChatMessageListLayout.this.messageListItemClickListener.onBubbleLongClick(view, eMMessage);
                }
                return false;
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onMessageCreate(EMMessage eMMessage) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onMessageCreate(eMMessage);
                }
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onMessageError(EMMessage eMMessage, int i2, String str) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onMessageError(eMMessage, i2, str);
                }
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onMessageInProgress(EMMessage eMMessage, int i2) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onMessageInProgress(eMMessage, i2);
                }
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onMessageSuccess(EMMessage eMMessage) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onMessageSuccess(eMMessage);
                }
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public boolean onResendClick(EMMessage eMMessage) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    return EaseChatMessageListLayout.this.messageListItemClickListener.onResendClick(eMMessage);
                }
                return false;
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onUserAvatarClick(String str) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onUserAvatarClick(str);
                }
            }

            @Override // com.hyphenate.easeui.interfaces.MessageListItemClickListener
            public void onUserAvatarLongClick(String str) {
                if (EaseChatMessageListLayout.this.messageListItemClickListener != null) {
                    EaseChatMessageListLayout.this.messageListItemClickListener.onUserAvatarLongClick(str);
                }
            }
        });
    }

    private void initViews() {
        this.presenter.attachView(this);
        this.rvList = (RecyclerView) findViewById(R.id.message_list);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        this.srlRefresh = swipeRefreshLayout;
        swipeRefreshLayout.setEnabled(this.canUseRefresh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.layoutManager = linearLayoutManager;
        this.rvList.setLayoutManager(linearLayoutManager);
        this.baseAdapter = new ConcatAdapter((RecyclerView.Adapter<? extends RecyclerView.ViewHolder>[]) new RecyclerView.Adapter[0]);
        EaseMessageAdapter easeMessageAdapter = new EaseMessageAdapter();
        this.messageAdapter = easeMessageAdapter;
        this.baseAdapter.addAdapter(easeMessageAdapter);
        this.rvList.setAdapter(this.baseAdapter);
        registerChatType();
        initListener();
    }

    private boolean isSingleChat() {
        return this.conType == EMConversation.EMConversationType.Chat;
    }

    public static boolean isVisibleBottom(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return linearLayoutManager.getChildCount() > 0 && linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.getItemCount() - 1 && recyclerView.getScrollState() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishRefresh$0() {
        SwipeRefreshLayout swipeRefreshLayout = this.srlRefresh;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$joinChatRoomFail$1(int i2, String str) {
        OnChatErrorListener onChatErrorListener = this.errorListener;
        if (onChatErrorListener != null) {
            onChatErrorListener.onChatError(i2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreLocalMsgSuccess$2(List list) {
        smoothSeekToPosition(list.size() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreServerMsgSuccess$3(List list) {
        smoothSeekToPosition(list.size() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshMessage$4(int i2) {
        this.messageAdapter.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeMessage$5(EMMessage eMMessage) {
        List<EMMessage> data;
        int iLastIndexOf;
        if (!this.presenter.isActive() || (iLastIndexOf = (data = this.messageAdapter.getData()).lastIndexOf(eMMessage)) == -1) {
            return;
        }
        data.remove(iLastIndexOf);
        this.messageAdapter.notifyItemRemoved(iLastIndexOf);
        this.messageAdapter.notifyItemChanged(iLastIndexOf);
    }

    private void notifyDataSetChanged() {
        this.messageAdapter.notifyDataSetChanged();
    }

    private void registerChatType() {
        try {
            EaseMessageTypeSetManager.getInstance().registerMessageType(this.messageAdapter);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InstantiationException e3) {
            e3.printStackTrace();
        }
    }

    private void seekToPosition(int i2) {
        RecyclerView recyclerView;
        if (this.presenter.isDestroy() || (recyclerView = this.rvList) == null) {
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i2, 0);
        }
    }

    private void setMoveAnimation(final RecyclerView.LayoutManager layoutManager, final int i2) {
        if (i2 > 0) {
            i2--;
        }
        View viewFindViewByPosition = layoutManager.findViewByPosition(0);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(-(viewFindViewByPosition != null ? viewFindViewByPosition.getHeight() : 200), 0);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i2, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        valueAnimatorOfInt.setDuration(800L);
        valueAnimatorOfInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothSeekToPosition(int i2) {
        RecyclerView recyclerView;
        if (this.presenter.isDestroy() || (recyclerView = this.rvList) == null) {
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i2, 0);
        }
    }

    public void addData(List<EMMessage> list) {
        this.messageAdapter.addData((List) list);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addFooterAdapter(RecyclerView.Adapter adapter) {
        this.baseAdapter.addAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addHeaderAdapter(RecyclerView.Adapter adapter) {
        this.baseAdapter.addAdapter(0, adapter);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void addRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.rvList.addItemDecoration(itemDecoration);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void canUseDefaultRefresh(boolean z2) {
        this.canUseRefresh = z2;
        this.srlRefresh.setEnabled(z2);
    }

    @Override // com.hyphenate.easeui.modules.ILoadDataView
    public Context context() {
        return getContext();
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public EMConversation getCurrentConversation() {
        return this.conversation;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout
    public EaseMessageAdapter getMessageAdapter() {
        return this.messageAdapter;
    }

    public boolean haveNewMessages() {
        EMConversation eMConversation;
        EaseMessageAdapter easeMessageAdapter = this.messageAdapter;
        return (easeMessageAdapter == null || easeMessageAdapter.getData() == null || this.messageAdapter.getData().isEmpty() || (eMConversation = this.conversation) == null || eMConversation.getLastMessage() == null || this.conversation.getLastMessage().getMsgTime() <= this.messageAdapter.getData().get(this.messageAdapter.getData().size() - 1).getMsgTime()) ? false : true;
    }

    public void init(LoadDataType loadDataType, String str, int i2) {
        this.username = str;
        this.loadDataType = loadDataType;
        this.conType = EaseCommonUtils.getConversationType(i2);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(str, this.conType, true);
        this.conversation = conversation;
        this.presenter.setupWithConversation(conversation);
    }

    public boolean isChatRoomCon() {
        return this.conType == EMConversation.EMConversationType.ChatRoom;
    }

    public boolean isGroupChat() {
        return this.conType == EMConversation.EMConversationType.GroupChat;
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void joinChatRoomFail(final int i2, final String str) {
        if (this.presenter.isActive()) {
            runOnUi(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8654c.lambda$joinChatRoomFail$1(i2, str);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void joinChatRoomSuccess(EMChatRoom eMChatRoom) {
        loadData();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void lastMsgScrollToBottom(EMMessage eMMessage) {
        int iLastIndexOf = this.messageAdapter.getData().lastIndexOf(eMMessage);
        if (iLastIndexOf != -1) {
            this.messageAdapter.notifyItemChanged(iLastIndexOf);
            if (this.rvList.canScrollVertically(1)) {
                return;
            }
            View viewFindViewByPosition = this.rvList.getLayoutManager().findViewByPosition(this.messageAdapter.getItemCount() - 1);
            final int measuredHeight = viewFindViewByPosition != null ? viewFindViewByPosition.getMeasuredHeight() : 0;
            this.rvList.postDelayed(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.EaseChatMessageListLayout.6
                @Override // java.lang.Runnable
                public void run() {
                    View viewFindViewByPosition2 = EaseChatMessageListLayout.this.rvList.getLayoutManager().findViewByPosition(EaseChatMessageListLayout.this.messageAdapter.getItemCount() - 1);
                    EaseChatMessageListLayout.this.rvList.smoothScrollBy(0, (viewFindViewByPosition2 != null ? viewFindViewByPosition2.getMeasuredHeight() : 0) - measuredHeight);
                }
            }, 500L);
        }
    }

    public void loadData(String str) {
        loadData(this.pageSize, str);
    }

    public void loadDefaultData() {
        loadData(this.pageSize, null);
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadLocalMsgSuccess(List<EMMessage> list) {
        refreshToLatest();
    }

    public void loadMoreHistoryData() {
        String listLastMessageId = getListLastMessageId();
        if (this.loadDataType == LoadDataType.HISTORY) {
            this.loadMoreStatus = LoadMoreStatus.HAS_MORE;
            this.presenter.loadMoreLocalHistoryMessages(listLastMessageId, this.pageSize, EMConversation.EMSearchDirection.DOWN);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadMoreLocalHistoryMsgSuccess(List<EMMessage> list, EMConversation.EMSearchDirection eMSearchDirection) {
        if (eMSearchDirection == EMConversation.EMSearchDirection.UP) {
            finishRefresh();
            this.messageAdapter.addData(0, list);
            return;
        }
        this.messageAdapter.addData((List) list);
        if (list.size() >= this.pageSize) {
            this.loadMoreStatus = LoadMoreStatus.HAS_MORE;
        } else {
            this.loadMoreStatus = LoadMoreStatus.NO_MORE_DATA;
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadMoreLocalMsgSuccess(final List<EMMessage> list) {
        finishRefresh();
        this.presenter.refreshCurrentConversation();
        post(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f8657c.lambda$loadMoreLocalMsgSuccess$2(list);
            }
        });
    }

    public void loadMorePreviousData() {
        String listFirstMessageId = getListFirstMessageId();
        LoadDataType loadDataType = this.loadDataType;
        if (loadDataType == LoadDataType.ROAM) {
            this.presenter.loadMoreServerMessages(listFirstMessageId, this.pageSize);
        } else if (loadDataType == LoadDataType.HISTORY) {
            this.presenter.loadMoreLocalHistoryMessages(listFirstMessageId, this.pageSize, EMConversation.EMSearchDirection.UP);
        } else {
            this.presenter.loadMoreLocalMessages(listFirstMessageId, this.pageSize);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadMoreServerMsgSuccess(final List<EMMessage> list) {
        finishRefresh();
        this.presenter.refreshCurrentConversation();
        post(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f8649c.lambda$loadMoreServerMsgSuccess$3(list);
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadMsgFail(int i2, String str) {
        finishRefresh();
        OnChatErrorListener onChatErrorListener = this.errorListener;
        if (onChatErrorListener != null) {
            onChatErrorListener.onChatError(i2, str);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadNoLocalMsg() {
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadNoMoreLocalHistoryMsg() {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadNoMoreLocalMsg() {
        finishRefresh();
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void loadServerMsgSuccess(List<EMMessage> list) {
        this.presenter.refreshToLatest();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void moveToPosition(int i2) {
        seekToPosition(i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EMConversation eMConversation = this.conversation;
        if (eMConversation != null) {
            eMConversation.markAllMessagesAsRead();
        }
        EaseChatItemStyleHelper.getInstance().clear();
        EaseMessageTypeSetManager.getInstance().release();
    }

    @Override // com.hyphenate.easeui.modules.chat.presenter.IChatMessageListView
    public void refreshCurrentConSuccess(List<EMMessage> list, boolean z2) {
        this.messageAdapter.setData(list);
        if (z2) {
            seekToPosition(list.size() - 1);
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void refreshMessage(EMMessage eMMessage) {
        final int iLastIndexOf = this.messageAdapter.getData().lastIndexOf(eMMessage);
        if (iLastIndexOf != -1) {
            runOnUi(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8652c.lambda$refreshMessage$4(iLastIndexOf);
                }
            });
        }
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void refreshMessages() {
        this.presenter.refreshCurrentConversation();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void refreshToLatest() {
        try {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.rvList.getLayoutManager();
            int iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            EaseMessageAdapter easeMessageAdapter = this.messageAdapter;
            if (easeMessageAdapter != null && easeMessageAdapter.getData() != null && (iFindLastVisibleItemPosition == this.messageAdapter.getData().size() - 1 || this.messageAdapter.getData().size() - 1 == iFindFirstVisibleItemPosition)) {
                this.presenter.refreshToLatest();
            } else if (!this.isFrist) {
                this.presenter.refreshCurrentConversation();
            } else {
                this.isFrist = false;
                this.presenter.refreshToLatest();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.presenter.refreshToLatest();
        }
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeAdapter(RecyclerView.Adapter adapter) {
        this.baseAdapter.removeAdapter(adapter);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IRecyclerViewHandle
    public void removeMessage(final EMMessage eMMessage) {
        if (eMMessage == null || this.messageAdapter.getData() == null) {
            return;
        }
        this.conversation.removeMessage(eMMessage.getMsgId());
        EMClient.getInstance().translationManager().removeTranslationResult(eMMessage.getMsgId());
        runOnUi(new Runnable() { // from class: com.hyphenate.easeui.modules.chat.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f8647c.lambda$removeMessage$5(eMMessage);
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public void removeRVItemDecoration(@NonNull RecyclerView.ItemDecoration itemDecoration) {
        this.rvList.removeItemDecoration(itemDecoration);
    }

    public void runOnUi(Runnable runnable) {
        EaseThreadManager.getInstance().runOnMainThread(runnable);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setAvatarDefaultSrc(Drawable drawable) {
        this.chatSetHelper.setAvatarDefaultSrc(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setAvatarShapeType(int i2) {
        this.chatSetHelper.setShapeType(i2);
        notifyDataSetChanged();
    }

    public void setData(List<EMMessage> list) {
        this.messageAdapter.setData(list);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setItemReceiverBackground(Drawable drawable) {
        this.chatSetHelper.setReceiverBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setItemSenderBackground(Drawable drawable) {
        this.chatSetHelper.setSenderBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setItemShowType(ShowType showType) {
        if (isSingleChat()) {
            return;
        }
        this.chatSetHelper.setItemShowType(showType.ordinal());
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setItemTextColor(int i2) {
        this.chatSetHelper.setTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setItemTextSize(int i2) {
        this.chatSetHelper.setTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout
    public void setMessageListItemClickListener(MessageListItemClickListener messageListItemClickListener) {
        this.messageListItemClickListener = messageListItemClickListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout
    public void setOnChatErrorListener(OnChatErrorListener onChatErrorListener) {
        this.errorListener = onChatErrorListener;
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public /* synthetic */ void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        f1.b.a(this, onItemClickListener);
    }

    @Override // com.hyphenate.easeui.modules.interfaces.IRecyclerView
    public /* synthetic */ void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        f1.b.b(this, onItemLongClickListener);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout
    public void setOnMessageTouchListener(OnMessageTouchListener onMessageTouchListener) {
        this.messageTouchListener = onMessageTouchListener;
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageListLayout
    public void setPresenter(EaseChatMessagePresenter easeChatMessagePresenter) {
        this.presenter = easeChatMessagePresenter;
        if (getContext() instanceof AppCompatActivity) {
            ((AppCompatActivity) getContext()).getLifecycle().addObserver(easeChatMessagePresenter);
        }
        this.presenter.attachView(this);
        this.presenter.setupWithConversation(this.conversation);
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setTimeBackground(Drawable drawable) {
        this.chatSetHelper.setTimeBgDrawable(drawable);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setTimeTextColor(int i2) {
        this.chatSetHelper.setTimeTextColor(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void setTimeTextSize(int i2) {
        this.chatSetHelper.setTimeTextSize(i2);
        notifyDataSetChanged();
    }

    @Override // com.hyphenate.easeui.modules.chat.interfaces.IChatMessageItemSet
    public void showNickname(boolean z2) {
        this.chatSetHelper.setShowNickname(z2);
        notifyDataSetChanged();
    }

    public EaseChatMessageListLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void loadData(int i2, String str) {
        this.pageSize = i2;
        this.msgId = str;
        checkConType();
    }

    public EaseChatMessageListLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isFrist = true;
        this.pageSize = 10;
        this.canUseRefresh = true;
        LayoutInflater.from(context).inflate(R.layout.ease_chat_message_list, this);
        DarkModeHelper.isDarkMode(context);
        EaseChatItemStyleHelper.getInstance().clear();
        this.chatSetHelper = EaseChatItemStyleHelper.getInstance();
        this.presenter = new EaseChatMessagePresenterImpl();
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this.presenter);
        }
        initAttrs(context, attributeSet);
        initViews();
    }

    private void loadData() {
        if (!isSingleChat()) {
            this.chatSetHelper.setShowNickname(true);
        }
        EMConversation eMConversation = this.conversation;
        if (eMConversation != null) {
            eMConversation.markAllMessagesAsRead();
        }
        LoadDataType loadDataType = this.loadDataType;
        if (loadDataType == LoadDataType.ROAM) {
            this.presenter.loadServerMessages(this.pageSize);
        } else if (loadDataType == LoadDataType.HISTORY) {
            this.presenter.loadMoreLocalHistoryMessages(this.msgId, this.pageSize, EMConversation.EMSearchDirection.DOWN);
        } else {
            this.presenter.loadLocalMessages(this.pageSize);
        }
    }

    public void init(String str, int i2) {
        init(LoadDataType.LOCAL, str, i2);
    }
}
