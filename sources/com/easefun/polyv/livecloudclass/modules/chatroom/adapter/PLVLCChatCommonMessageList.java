package com.easefun.polyv.livecloudclass.modules.chatroom.adapter;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageViewerFragment;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCChatCommonMessageList {
    private PLVChatImageViewerFragment chatImageViewerFragment;
    private PLVMessageRecyclerView chatMsgRv;
    private boolean isLandscapeLastScrollChanged;
    private boolean isLandscapeLayout;
    private PLVLCMessageAdapter messageAdapter;
    private int lastPosition = -1;
    private int lastOffset = -1;

    public PLVLCChatCommonMessageList(final Context context) {
        PLVMessageRecyclerView pLVMessageRecyclerView = new PLVMessageRecyclerView(context);
        this.chatMsgRv = pLVMessageRecyclerView;
        pLVMessageRecyclerView.setOverScrollMode(2);
        this.chatMsgRv.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                super.onScrollStateChanged(recyclerView, i2);
                View childAt = PLVLCChatCommonMessageList.this.chatMsgRv.getLayoutManager().getChildAt(0);
                if (childAt != null) {
                    PLVLCChatCommonMessageList pLVLCChatCommonMessageList = PLVLCChatCommonMessageList.this;
                    pLVLCChatCommonMessageList.isLandscapeLastScrollChanged = pLVLCChatCommonMessageList.isLandscapeLayout;
                    PLVLCChatCommonMessageList.this.lastOffset = childAt.getTop();
                    PLVLCChatCommonMessageList pLVLCChatCommonMessageList2 = PLVLCChatCommonMessageList.this;
                    pLVLCChatCommonMessageList2.lastPosition = pLVLCChatCommonMessageList2.chatMsgRv.getLayoutManager().getPosition(childAt);
                }
                if (PLVLCChatCommonMessageList.this.chatMsgRv.canScrollVertically(1)) {
                    return;
                }
                PLVLCChatCommonMessageList.this.lastPosition = -1;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                super.onScrolled(recyclerView, i2, i3);
                if (PLVLCChatCommonMessageList.this.chatMsgRv.canScrollVertically(1)) {
                    return;
                }
                PLVLCChatCommonMessageList.this.lastPosition = -1;
            }
        });
        PLVMessageRecyclerView.setLayoutManager(this.chatMsgRv);
        PLVLCMessageAdapter pLVLCMessageAdapter = new PLVLCMessageAdapter();
        this.messageAdapter = pLVLCMessageAdapter;
        pLVLCMessageAdapter.setOnViewActionListener(new PLVLCMessageAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList.2
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCMessageAdapter.OnViewActionListener
            public void onChatImgClick(int i2, View view, String str, boolean z2) {
                if (z2) {
                    PLVLCChatCommonMessageList pLVLCChatCommonMessageList = PLVLCChatCommonMessageList.this;
                    pLVLCChatCommonMessageList.chatImageViewerFragment = PLVChatImageViewerFragment.show((AppCompatActivity) context, pLVLCChatCommonMessageList.messageAdapter.getDataList().get(i2), R.id.content);
                } else {
                    PLVLCChatCommonMessageList pLVLCChatCommonMessageList2 = PLVLCChatCommonMessageList.this;
                    pLVLCChatCommonMessageList2.chatImageViewerFragment = PLVChatImageViewerFragment.show((AppCompatActivity) context, pLVLCChatCommonMessageList2.messageAdapter.getDataList(), (PLVBaseViewData<PLVBaseEvent>) PLVLCChatCommonMessageList.this.messageAdapter.getDataList().get(i2), R.id.content);
                }
            }
        });
        this.chatMsgRv.setAdapter(this.messageAdapter);
    }

    public void addChatHistoryToList(List<PLVBaseViewData<PLVBaseEvent>> list, boolean z2, boolean z3) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z3 && this.messageAdapter.addDataListChangedAtFirst(list)) {
            if (z2) {
                this.chatMsgRv.scrollToPosition(this.messageAdapter.getItemCount() - 1);
            } else {
                this.chatMsgRv.scrollToPosition(0);
            }
        }
    }

    public void addChatMessageToList(List<PLVBaseViewData> list, boolean z2, boolean z3) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z3 && this.messageAdapter.addDataListChangedAtLast(list)) {
            if (z2) {
                this.chatMsgRv.scrollToPosition(this.messageAdapter.getItemCount() - 1);
            } else {
                this.chatMsgRv.scrollToBottomOrShowMore(list.size());
            }
        }
    }

    public void addChatMessageToListHead(List<PLVBaseViewData> list, boolean z2, boolean z3) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z3 && this.messageAdapter.addDataListChangedAtHead(list)) {
            if (z2) {
                this.chatMsgRv.scrollToPosition(this.messageAdapter.getItemCount() - 1);
            } else {
                this.chatMsgRv.scrollToPosition(0);
            }
        }
    }

    public void addOnUnreadCountChangeListener(PLVMessageRecyclerView.OnUnreadCountChangeListener onUnreadCountChangeListener) {
        this.chatMsgRv.addOnUnreadCountChangeListener(onUnreadCountChangeListener);
    }

    public void addUnreadView(View view) {
        this.chatMsgRv.addUnreadView(view);
    }

    public boolean attachToParent(ViewGroup viewGroup, boolean z2) {
        int iDp2px;
        if (ScreenUtils.isLandscape() != z2 || this.chatMsgRv.getParent() == viewGroup) {
            return false;
        }
        this.isLandscapeLayout = z2;
        if (this.chatMsgRv.getParent() != null) {
            ((ViewGroup) this.chatMsgRv.getParent()).removeView(this.chatMsgRv);
        }
        viewGroup.addView(this.chatMsgRv, 0, new ViewGroup.LayoutParams(-1, -1));
        this.messageAdapter.setLandscapeLayout(z2);
        if (this.chatMsgRv.getItemDecorationCount() > 0) {
            for (int i2 = 0; i2 < this.chatMsgRv.getItemDecorationCount(); i2++) {
                this.chatMsgRv.removeItemDecorationAt(i2);
            }
        }
        if (z2) {
            this.chatMsgRv.setVerticalFadingEdgeEnabled(true);
            this.chatMsgRv.setStackFromEnd(true);
            this.chatMsgRv.setFadingEdgeLength(ConvertUtils.dp2px(26.0f));
            this.chatMsgRv.addItemDecoration(new PLVMessageRecyclerView.SpacesItemDecoration(ConvertUtils.dp2px(4.0f), 0));
        } else {
            this.chatMsgRv.setVerticalFadingEdgeEnabled(false);
            this.chatMsgRv.setStackFromEnd(false);
            this.chatMsgRv.setFadingEdgeLength(ConvertUtils.dp2px(0.0f));
            this.chatMsgRv.addItemDecoration(new PLVMessageRecyclerView.SpacesItemDecoration(ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(16.0f)));
        }
        this.chatMsgRv.setAdapter(this.messageAdapter);
        int i3 = this.lastPosition;
        if (i3 != -1) {
            int iDp2px2 = this.lastOffset;
            if (this.isLandscapeLastScrollChanged == z2) {
                if (z2) {
                    if (i3 != 0) {
                        iDp2px = ConvertUtils.dp2px(4.0f);
                    }
                    ((LinearLayoutManager) this.chatMsgRv.getLayoutManager()).scrollToPositionWithOffset(i3, iDp2px2);
                } else {
                    iDp2px = ConvertUtils.dp2px(16.0f);
                }
                iDp2px2 -= iDp2px;
                ((LinearLayoutManager) this.chatMsgRv.getLayoutManager()).scrollToPositionWithOffset(i3, iDp2px2);
            } else if (z2) {
                iDp2px2 = ((iDp2px2 >= 0 ? iDp2px2 / 4 : iDp2px2 / 2) - ConvertUtils.dp2px(4.0f)) + ConvertUtils.dp2px(4.3333335f);
                ((LinearLayoutManager) this.chatMsgRv.getLayoutManager()).scrollToPositionWithOffset(i3, iDp2px2);
            } else {
                iDp2px2 = iDp2px2 >= 0 ? iDp2px2 * 4 : iDp2px2 * 2;
                iDp2px = ConvertUtils.dp2px(16.0f);
                iDp2px2 -= iDp2px;
                ((LinearLayoutManager) this.chatMsgRv.getLayoutManager()).scrollToPositionWithOffset(i3, iDp2px2);
            }
        } else {
            this.chatMsgRv.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList.3
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCChatCommonMessageList.this.chatMsgRv.scrollToPosition(PLVLCChatCommonMessageList.this.messageAdapter.getItemCount() - 1);
                }
            });
        }
        return true;
    }

    public boolean canScrollVertically(int i2) {
        return this.chatMsgRv.canScrollVertically(i2);
    }

    public void changeDisplayType(int i2) {
        this.messageAdapter.changeDisplayType(i2);
    }

    public int getItemCount() {
        return this.messageAdapter.getItemCount();
    }

    public boolean isLandscapeLayout() {
        return this.isLandscapeLayout;
    }

    public boolean onBackPressed() {
        PLVChatImageViewerFragment pLVChatImageViewerFragment = this.chatImageViewerFragment;
        if (pLVChatImageViewerFragment == null || !pLVChatImageViewerFragment.isVisible()) {
            return false;
        }
        this.chatImageViewerFragment.hide();
        return true;
    }

    public void removeAllChatMessage(boolean z2) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z2) {
            this.messageAdapter.removeAllDataChanged();
        }
    }

    public void removeChatMessage(int i2, int i3, boolean z2) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z2) {
            this.messageAdapter.removeDataChanged(i2, i3);
        }
    }

    public void scrollToPosition(int i2) {
        this.chatMsgRv.scrollToPosition(i2);
    }

    public void setMsgIndex(int i2) {
        this.messageAdapter.setMsgIndex(i2);
    }

    public void removeChatMessage(String str, boolean z2) {
        if (this.chatMsgRv.getParent() != null && this.isLandscapeLayout == z2) {
            this.messageAdapter.removeDataChanged(str);
        }
    }
}
