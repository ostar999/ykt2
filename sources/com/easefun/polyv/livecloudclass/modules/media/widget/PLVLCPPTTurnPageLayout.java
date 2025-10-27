package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.plv.livescenes.document.model.PLVPPTStatus;

/* loaded from: classes3.dex */
public class PLVLCPPTTurnPageLayout extends RelativeLayout implements View.OnClickListener {
    public static final String PPT_TURN_PAGE_GO_BACK = "pptBtnBack";
    public static final String PPT_TURN_PAGE_NEXT = "gotoNextStep";
    public static final String PPT_TURN_PAGE_PREVIOUS = "gotoPreviousStep";
    private int autoId;
    private int currentPage;
    private boolean isPreviousClickFlag;
    private int maxPage;
    private OnPPTTurnPageListener onPPTTurnPageListener;
    private LinearLayout plvlcPptTurnPageLayout;
    private ImageView plvlcPptTurnPageNextIv;
    private ImageView plvlcPptTurnPagePreviousIv;
    private TextView plvlcPptTurnPageProgressTv;
    private PLVTriangleIndicateTextView plvlcPptTurnPageTipTv;
    private int teacherMaxPage;

    public interface OnPPTTurnPageListener {
        void onPPTTurnPage(String str);
    }

    public PLVLCPPTTurnPageLayout(@NonNull Context context) {
        this(context, null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_ppt_turn_page_layout, this);
        this.plvlcPptTurnPageTipTv = (PLVTriangleIndicateTextView) findViewById(R.id.plvlc_ppt_turn_page_tip_tv);
        this.plvlcPptTurnPageLayout = (LinearLayout) findViewById(R.id.plvlc_ppt_turn_page_layout);
        this.plvlcPptTurnPagePreviousIv = (ImageView) findViewById(R.id.plvlc_ppt_turn_page_previous_iv);
        this.plvlcPptTurnPageProgressTv = (TextView) findViewById(R.id.plvlc_ppt_turn_page_progress_tv);
        this.plvlcPptTurnPageNextIv = (ImageView) findViewById(R.id.plvlc_ppt_turn_page_next_iv);
        this.plvlcPptTurnPageProgressTv.setOnClickListener(this);
        this.plvlcPptTurnPagePreviousIv.setOnClickListener(this);
        this.plvlcPptTurnPageNextIv.setOnClickListener(this);
        this.plvlcPptTurnPagePreviousIv.setSelected(true);
        this.plvlcPptTurnPageNextIv.setSelected(true);
    }

    private void updateStep() {
        int i2 = this.currentPage + 1;
        if (i2 <= 1) {
            this.plvlcPptTurnPagePreviousIv.setSelected(false);
            this.currentPage = 0;
        } else {
            this.plvlcPptTurnPagePreviousIv.setSelected(true);
        }
        if (this.currentPage >= this.teacherMaxPage) {
            this.plvlcPptTurnPageNextIv.setSelected(false);
            this.currentPage = this.teacherMaxPage;
        } else {
            this.plvlcPptTurnPageNextIv.setSelected(true);
        }
        this.plvlcPptTurnPageProgressTv.setText(String.format("%s/%s", Integer.valueOf(i2), Integer.valueOf(this.maxPage)));
    }

    public void goBackTeacherPage() {
        this.plvlcPptTurnPageTipTv.setVisibility(4);
        OnPPTTurnPageListener onPPTTurnPageListener = this.onPPTTurnPageListener;
        if (onPPTTurnPageListener != null) {
            onPPTTurnPageListener.onPPTTurnPage(PPT_TURN_PAGE_GO_BACK);
        }
    }

    public void gotoNextStep() {
        OnPPTTurnPageListener onPPTTurnPageListener;
        if (this.currentPage >= this.teacherMaxPage) {
            return;
        }
        if (this.isPreviousClickFlag) {
            this.plvlcPptTurnPageTipTv.setVisibility(4);
        } else {
            this.plvlcPptTurnPageTipTv.setVisibility(0);
            this.isPreviousClickFlag = true;
        }
        this.currentPage++;
        if (!this.plvlcPptTurnPageNextIv.isSelected() || (onPPTTurnPageListener = this.onPPTTurnPageListener) == null) {
            return;
        }
        onPPTTurnPageListener.onPPTTurnPage(PPT_TURN_PAGE_NEXT);
    }

    public void gotoPreviousStep() {
        OnPPTTurnPageListener onPPTTurnPageListener;
        if (this.isPreviousClickFlag) {
            this.plvlcPptTurnPageTipTv.setVisibility(4);
        } else {
            this.plvlcPptTurnPageTipTv.setVisibility(0);
            this.isPreviousClickFlag = true;
        }
        this.currentPage--;
        if (!this.plvlcPptTurnPagePreviousIv.isSelected() || (onPPTTurnPageListener = this.onPPTTurnPageListener) == null) {
            return;
        }
        onPPTTurnPageListener.onPPTTurnPage(PPT_TURN_PAGE_PREVIOUS);
    }

    public void initPageData(@NonNull PLVPPTStatus pLVPPTStatus) {
        if (pLVPPTStatus == null || pLVPPTStatus.getMaxTeacherOp() == null) {
            return;
        }
        int pageId = pLVPPTStatus.getMaxTeacherOp().getPageId();
        this.teacherMaxPage = pageId;
        this.currentPage = pageId;
        this.maxPage = pLVPPTStatus.getTotal();
        updateStep();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.plvlc_ppt_turn_page_previous_iv) {
            gotoPreviousStep();
        } else if (view.getId() == R.id.plvlc_ppt_turn_page_next_iv) {
            gotoNextStep();
        } else if (view.getId() == R.id.plvlc_ppt_turn_page_progress_tv) {
            goBackTeacherPage();
        }
    }

    public void setOnPPTTurnPageListener(OnPPTTurnPageListener onPPTTurnPageListener) {
        this.onPPTTurnPageListener = onPPTTurnPageListener;
    }

    public void updatePageData(PLVPPTStatus pLVPPTStatus) {
        if (pLVPPTStatus == null || pLVPPTStatus.getMaxTeacherOp() == null) {
            return;
        }
        this.teacherMaxPage = pLVPPTStatus.getMaxTeacherOp().getPageId();
        if (this.autoId != pLVPPTStatus.getAutoId()) {
            this.maxPage = pLVPPTStatus.getTotal();
            this.currentPage = pLVPPTStatus.getPageId();
        }
        updateStep();
    }

    public PLVLCPPTTurnPageLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCPPTTurnPageLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.currentPage = 0;
        this.autoId = -1;
        this.isPreviousClickFlag = false;
        init();
    }
}
