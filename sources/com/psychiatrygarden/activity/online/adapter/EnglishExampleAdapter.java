package com.psychiatrygarden.activity.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
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
public class EnglishExampleAdapter extends BaseQuickAdapter<QuestionDetailBean.EnglishLableBean.ExampleDTO, BaseViewHolder> {
    private PopWord mPopWordCard;

    /* renamed from: com.psychiatrygarden.activity.online.adapter.EnglishExampleAdapter$1, reason: invalid class name */
    public class AnonymousClass1 implements EnglishTextView.OnWordClickListener {
        final /* synthetic */ EnglishTextView val$customtextview;

        public AnonymousClass1(final EnglishTextView val$customtextview) {
            this.val$customtextview = val$customtextview;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWordClickListener$0(EnglishTextView englishTextView) throws IllegalStateException {
            EnglishExampleAdapter.this.mPopWordCard.releasePlayer();
            englishTextView.setClickedRectList(new HashMap());
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        public void onDataInitListener(Map<String, Map<RectF, String>> sentenceLines) {
        }

        @Override // com.psychiatrygarden.widget.english.EnglishTextView.OnWordClickListener
        @RequiresApi(api = 17)
        public void onWordClickListener(Map<RectF, String> rectF, String word) {
            if (EnglishExampleAdapter.this.mPopWordCard != null && EnglishExampleAdapter.this.mPopWordCard.isShow()) {
                EnglishExampleAdapter.this.mPopWordCard.dismiss();
                return;
            }
            EnglishExampleAdapter englishExampleAdapter = EnglishExampleAdapter.this;
            Context context = englishExampleAdapter.getContext();
            float[] rawY = this.val$customtextview.getRawY();
            int pxByDp = ScreenUtil.getPxByDp(EnglishExampleAdapter.this.getContext(), 88);
            int screenHeight = ScreenUtil.getScreenHeight((Activity) EnglishExampleAdapter.this.getContext()) - ScreenUtil.getPxByDp(EnglishExampleAdapter.this.getContext(), 103);
            final EnglishTextView englishTextView = this.val$customtextview;
            englishExampleAdapter.mPopWordCard = new PopWord(context, rawY, pxByDp, screenHeight, new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.activity.online.adapter.e
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() throws IllegalStateException {
                    this.f13107c.lambda$onWordClickListener$0(englishTextView);
                }
            }, word, "");
            EnglishExampleAdapter.this.mPopWordCard.show(this.val$customtextview.getRootView());
        }
    }

    public EnglishExampleAdapter(@Nullable List<QuestionDetailBean.EnglishLableBean.ExampleDTO> data) {
        super(R.layout.item_english_word_tran, data);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, QuestionDetailBean.EnglishLableBean.ExampleDTO item) {
        ((ImageView) helper.getView(R.id.iv_english_example_play)).setVisibility(0);
        EnglishTextView englishTextView = (EnglishTextView) helper.getView(R.id.customtextview);
        englishTextView.setIsClick(true);
        englishTextView.setTextSize(16 - ((SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2) - 2) * 2));
        if (TextUtils.isEmpty(item.getExample_translate())) {
            englishTextView.setText(StringUtil.replaceCharacter(item.getExample()));
        } else {
            englishTextView.setText(StringUtil.replaceCharacter(item.getExample() + "\n" + item.getExample_translate()));
        }
        englishTextView.setOnWordClickListener(new AnonymousClass1(englishTextView));
    }
}
