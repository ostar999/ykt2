package com.psychiatrygarden.activity.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.widget.PopupWindow;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.widget.english.EnglishTextView;
import com.psychiatrygarden.widget.english.PopWord;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class EnglishTranAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean isClick;
    private PopWord mPopWordCard;

    /* renamed from: com.psychiatrygarden.activity.online.adapter.EnglishTranAdapter$1, reason: invalid class name */
    public class AnonymousClass1 implements EnglishTextView.OnWordClickListener {
        final /* synthetic */ EnglishTextView val$customtextview;

        public AnonymousClass1(final EnglishTextView val$customtextview) {
            this.val$customtextview = val$customtextview;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWordClickListener$0(EnglishTextView englishTextView) throws IllegalStateException {
            EnglishTranAdapter.this.mPopWordCard.releasePlayer();
            englishTextView.setClickedRectList(new HashMap());
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        public void onDataInitListener(Map<String, Map<RectF, String>> sentenceLines) {
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        @RequiresApi(api = 17)
        public void onWordClickListener(Map<RectF, String> rectF, String word) {
            if (EnglishTranAdapter.this.mPopWordCard != null && EnglishTranAdapter.this.mPopWordCard.isShow()) {
                EnglishTranAdapter.this.mPopWordCard.dismiss();
                return;
            }
            EnglishTranAdapter englishTranAdapter = EnglishTranAdapter.this;
            Context context = englishTranAdapter.getContext();
            float[] rawY = this.val$customtextview.getRawY();
            int pxByDp = ScreenUtil.getPxByDp(EnglishTranAdapter.this.getContext(), 88);
            int screenHeight = ScreenUtil.getScreenHeight((Activity) EnglishTranAdapter.this.getContext()) - ScreenUtil.getPxByDp(EnglishTranAdapter.this.getContext(), 103);
            final EnglishTextView englishTextView = this.val$customtextview;
            englishTranAdapter.mPopWordCard = new PopWord(context, rawY, pxByDp, screenHeight, new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.online.adapter.f
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() throws IllegalStateException {
                    this.f13109c.lambda$onWordClickListener$0(englishTextView);
                }
            }, word, "");
            EnglishTranAdapter.this.mPopWordCard.show(this.val$customtextview.getRootView());
        }
    }

    public EnglishTranAdapter(@Nullable List<String> data, boolean isClick) {
        super(R.layout.item_english_word_tran, data);
        this.isClick = isClick;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, String item) {
        EnglishTextView englishTextView = (EnglishTextView) helper.getView(R.id.customtextview);
        englishTextView.setIsClick(this.isClick);
        englishTextView.setTextSize(16 - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2) - 2) * 2));
        englishTextView.setText(StringUtil.replaceCharacter(item));
        englishTextView.setOnWordClickListener(new AnonymousClass1(englishTextView));
    }
}
