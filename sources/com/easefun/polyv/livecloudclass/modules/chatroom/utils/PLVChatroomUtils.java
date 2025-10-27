package com.easefun.polyv.livecloudclass.modules.chatroom.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.EditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmojiListAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter;
import com.easefun.polyv.livecommon.module.utils.span.PLVFaceManager;
import com.easefun.polyv.livecommon.ui.widget.gif.RelativeImageSpan;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChatroomUtils {
    private static int emojiLength;

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendEmo(String str, EditText editText) throws Resources.NotFoundException {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (editText.getText().length() + spannableStringBuilder.length() >= 200) {
            Log.e("ChatroomUtils", "appendEmo fail because exceed maxLength 200");
            return;
        }
        int textSize = (int) editText.getTextSize();
        try {
            Drawable drawable = editText.getResources().getDrawable(PLVFaceManager.getInstance().getFaceId(str));
            RelativeImageSpan relativeImageSpan = new RelativeImageSpan(drawable, 3);
            int i2 = (int) (textSize * 1.5d);
            drawable.setBounds(0, 0, i2, i2);
            spannableStringBuilder.setSpan(relativeImageSpan, 0, spannableStringBuilder.length(), 33);
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            if (selectionStart != selectionEnd) {
                editText.getText().replace(selectionStart, selectionEnd, spannableStringBuilder);
            } else {
                editText.getText().insert(selectionStart, spannableStringBuilder);
            }
        } catch (Exception unused) {
            ToastUtils.showShort("添加表情失败！");
        }
    }

    public static void deleteEmoText(EditText editText) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        if (selectionEnd > 0) {
            if (selectionStart != selectionEnd) {
                editText.getText().delete(selectionStart, selectionEnd);
            } else if (isEmo(selectionEnd, editText)) {
                editText.getText().delete(selectionEnd - emojiLength, selectionEnd);
            } else {
                editText.getText().delete(selectionEnd - 1, selectionEnd);
            }
        }
    }

    public static void initEmojiList(RecyclerView recyclerView, final EditText editText) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 6, 1, false));
        recyclerView.addItemDecoration(new PLVLCEmojiListAdapter.GridSpacingItemDecoration(6, ConvertUtils.dp2px(4.0f), true));
        PLVLCEmojiListAdapter pLVLCEmojiListAdapter = new PLVLCEmojiListAdapter();
        pLVLCEmojiListAdapter.setOnViewActionListener(new PLVLCEmojiListAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.utils.PLVChatroomUtils.1
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmojiListAdapter.OnViewActionListener
            public void onEmojiViewClick(String str) throws Resources.NotFoundException {
                if (editText.isEnabled()) {
                    PLVChatroomUtils.appendEmo(str, editText);
                }
            }
        });
        recyclerView.setAdapter(pLVLCEmojiListAdapter);
    }

    public static void initEmojiPersonalList(RecyclerView recyclerView, int i2, List<PLVEmotionImageVO.EmotionImage> list, PLVLCEmotionPersonalListAdapter.OnViewActionListener onViewActionListener) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), i2, 1, false));
        PLVLCEmotionPersonalListAdapter pLVLCEmotionPersonalListAdapter = new PLVLCEmotionPersonalListAdapter(list);
        recyclerView.addItemDecoration(new PLVLCEmotionPersonalListAdapter.GridSpacingItemDecoration(i2, ConvertUtils.dp2px(10.0f), true));
        pLVLCEmotionPersonalListAdapter.setOnViewActionListener(onViewActionListener);
        recyclerView.setAdapter(pLVLCEmotionPersonalListAdapter);
    }

    private static boolean isEmo(int i2, EditText editText) {
        String string = editText.getText().subSequence(0, i2).toString();
        int iLastIndexOf = string.lastIndexOf(93);
        int iLastIndexOf2 = string.lastIndexOf(91);
        if (iLastIndexOf == i2 - 1 && iLastIndexOf - iLastIndexOf2 >= 2) {
            String strSubstring = string.substring(iLastIndexOf2);
            emojiLength = strSubstring.length();
            if (PLVFaceManager.getInstance().getFaceId(strSubstring) != -1) {
                return true;
            }
        }
        return false;
    }
}
