package com.easefun.polyv.livecloudclass.modules.chatroom.chatmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreAdapter;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.model.interact.PLVChatFunctionVO;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class PLVLCChatMoreLayout extends FrameLayout {
    public static final String CHAT_FUNCTION_TYPE_BULLETIN = "CHAT_FUNCTION_TYPE_BULLETIN";
    public static final String CHAT_FUNCTION_TYPE_EFFECT = "CHAT_FUNCTION_TYPE_EFFECT";
    public static final String CHAT_FUNCTION_TYPE_MESSAGE = "CHAT_FUNCTION_TYPE_MESSAGE";
    public static final String CHAT_FUNCTION_TYPE_ONLY_TEACHER = "CHAT_FUNCTION_TYPE_ONLY_TEACHER";
    public static final String CHAT_FUNCTION_TYPE_OPEN_CAMERA = "CHAT_FUNCTION_TYPE_OPEN_CAMERA";
    public static final String CHAT_FUNCTION_TYPE_SEND_IMAGE = "CHAT_FUNCTION_TYPE_SEND_IMAGE";
    public static final int LAYOUT_SPAN_COUNT = 4;
    private PLVLCChatMoreAdapter adapter;
    private RecyclerView chatMoreRv;
    private final ArrayList<PLVChatFunctionVO> functionList;
    private PLVLCChatFunctionListener functionListener;

    public PLVLCChatMoreLayout(Context context) {
        super(context);
        this.functionList = PLVSugarUtil.arrayListOf(new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_ONLY_TEACHER, R.drawable.plvlc_chatroom_btn_view_message_selector, "只看讲师", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_SEND_IMAGE, R.drawable.plvlc_chatroom_btn_img_send, "发送图片", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_OPEN_CAMERA, R.drawable.plvlc_chatroom_btn_camera, "拍摄", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_BULLETIN, R.drawable.plvlc_chatroom_btn_bulletin_show, "公告", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_MESSAGE, R.drawable.plvlc_chatroom_btn_message, "消息", false));
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_chatroom_chat_more_layout, (ViewGroup) this, true);
        this.chatMoreRv = (RecyclerView) findViewById(R.id.plvlc_chat_more_rv);
        this.chatMoreRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        PLVLCChatMoreAdapter pLVLCChatMoreAdapter = new PLVLCChatMoreAdapter(4, getContext());
        this.adapter = pLVLCChatMoreAdapter;
        pLVLCChatMoreAdapter.setData(this.functionList);
        this.adapter.setListener(new PLVLCChatMoreAdapter.OnItemClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreLayout.1
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreAdapter.OnItemClickListener
            public void onItemClick(String str) {
                if (PLVLCChatMoreLayout.this.functionListener != null) {
                    PLVLCChatMoreLayout.this.functionListener.onFunctionCallback(str, String.format("{\"event\" : \"%s\"}", str));
                }
            }
        });
        this.chatMoreRv.setAdapter(this.adapter);
    }

    @Nullable
    public PLVChatFunctionVO getFunctionByType(String str) {
        Iterator<PLVChatFunctionVO> it = this.functionList.iterator();
        while (it.hasNext()) {
            PLVChatFunctionVO next = it.next();
            if (str.equals(next.getType())) {
                return next;
            }
        }
        return null;
    }

    public void setFunctionListener(PLVLCChatFunctionListener pLVLCChatFunctionListener) {
        this.functionListener = pLVLCChatFunctionListener;
    }

    public void updateFunctionNew(String str, boolean z2, boolean z3) {
        Iterator<PLVChatFunctionVO> it = this.functionList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PLVChatFunctionVO next = it.next();
            if (str.equals(next.getType())) {
                next.setShow(z2);
                break;
            }
        }
        this.adapter.updateFunctionList(this.functionList);
    }

    public void updateFunctionShow(String str, boolean z2) {
        Iterator<PLVChatFunctionVO> it = this.functionList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PLVChatFunctionVO next = it.next();
            if (str.equals(next.getType())) {
                next.setShow(z2);
                break;
            }
        }
        this.adapter.updateFunctionList(this.functionList);
    }

    public void updateFunctionStatus(@NonNull PLVChatFunctionVO pLVChatFunctionVO) {
        int i2 = 0;
        while (true) {
            if (i2 < this.functionList.size()) {
                if (pLVChatFunctionVO.getType() != null && pLVChatFunctionVO.getType().equals(this.functionList.get(i2).getType())) {
                    this.functionList.set(i2, pLVChatFunctionVO);
                    break;
                }
                i2++;
            } else {
                break;
            }
        }
        this.adapter.updateFunctionList(this.functionList);
    }

    public void updateFunctionView(PLVWebviewUpdateAppStatusVO pLVWebviewUpdateAppStatusVO) {
        if (pLVWebviewUpdateAppStatusVO == null || pLVWebviewUpdateAppStatusVO.getValue() == null || pLVWebviewUpdateAppStatusVO.getValue().getDataArray() == null) {
            return;
        }
        for (PLVWebviewUpdateAppStatusVO.Value.Function function : pLVWebviewUpdateAppStatusVO.getValue().getDataArray()) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.functionList.size()) {
                    i2 = -1;
                    break;
                } else if (function.getEvent().equals(this.functionList.get(i2).getType())) {
                    break;
                } else {
                    i2++;
                }
            }
            PLVChatFunctionVO pLVChatFunctionVO = new PLVChatFunctionVO(function.getEvent(), function.getTitle(), function.isShow(), function.getIcon());
            if (i2 < 0) {
                this.functionList.add(pLVChatFunctionVO);
            } else {
                this.functionList.set(i2, pLVChatFunctionVO);
            }
        }
        PLVLCChatMoreAdapter pLVLCChatMoreAdapter = this.adapter;
        if (pLVLCChatMoreAdapter != null) {
            pLVLCChatMoreAdapter.updateFunctionList(this.functionList);
        }
    }

    public PLVLCChatMoreLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.functionList = PLVSugarUtil.arrayListOf(new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_ONLY_TEACHER, R.drawable.plvlc_chatroom_btn_view_message_selector, "只看讲师", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_SEND_IMAGE, R.drawable.plvlc_chatroom_btn_img_send, "发送图片", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_OPEN_CAMERA, R.drawable.plvlc_chatroom_btn_camera, "拍摄", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_BULLETIN, R.drawable.plvlc_chatroom_btn_bulletin_show, "公告", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_MESSAGE, R.drawable.plvlc_chatroom_btn_message, "消息", false));
        initView();
    }

    public PLVLCChatMoreLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.functionList = PLVSugarUtil.arrayListOf(new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_ONLY_TEACHER, R.drawable.plvlc_chatroom_btn_view_message_selector, "只看讲师", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_SEND_IMAGE, R.drawable.plvlc_chatroom_btn_img_send, "发送图片", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_OPEN_CAMERA, R.drawable.plvlc_chatroom_btn_camera, "拍摄", false), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_BULLETIN, R.drawable.plvlc_chatroom_btn_bulletin_show, "公告", true), new PLVChatFunctionVO(CHAT_FUNCTION_TYPE_MESSAGE, R.drawable.plvlc_chatroom_btn_message, "消息", false));
        initView();
    }
}
