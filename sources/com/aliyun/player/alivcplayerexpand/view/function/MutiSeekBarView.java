package com.aliyun.player.alivcplayerexpand.view.function;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.bean.SummaryPoint;
import com.aliyun.player.alivcplayerexpand.view.function.AdvVideoView;
import com.aliyun.svideo.common.utils.DensityUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MutiSeekBarView extends AppCompatSeekBar implements View.OnTouchListener {
    private int mAdvNumber;
    private AdvPosition mAdvPosition;
    private int mAdvSeekColor;
    private long mAdvTime;
    private int mAdvWidth;
    private final Paint mCirclePaint;
    private OnIconClickListener mIconClickListener;
    private final List<SummaryPoint> mIcons;
    private int mPaddingLeft;
    private int mPaddingRight;
    private Paint mPaint;
    private int mPaintStrokeWidth;
    private int mPointY;
    private int mSourceSeekColor;
    private long mSourceTime;
    private int mSourceWidth;
    private long mTotalTime;
    private int mViewWidth;

    /* renamed from: com.aliyun.player.alivcplayerexpand.view.function.MutiSeekBarView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition;

        static {
            int[] iArr = new int[AdvPosition.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition = iArr;
            try {
                iArr[AdvPosition.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.START_END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.START_MIDDLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.MIDDLE_END.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.ONLY_START.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.ONLY_MIDDLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[AdvPosition.ONLY_END.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public enum AdvPosition {
        ONLY_START(0),
        ONLY_MIDDLE(1),
        ONLY_END(2),
        START_END(3),
        START_MIDDLE(4),
        MIDDLE_END(5),
        ALL(6);

        AdvPosition(int i2) {
        }
    }

    public static class Icon {
        public String id;
        public RectF mRectF;
        public int position;
        public float radius;
        public int time;

        public Icon(int i2, float f2, int i3, String str) {
            this.time = i2;
            this.radius = f2;
            this.position = i3;
            this.id = str;
            float f3 = i2;
            this.mRectF = new RectF(f3 - (f2 * 2.0f), 0.0f, f3, 0.0f);
        }
    }

    public interface OnIconClickListener {
        void onIconClick(SummaryPoint summaryPoint);
    }

    public MutiSeekBarView(Context context) {
        super(context);
        this.mPaintStrokeWidth = 2;
        this.mSourceSeekColor = getResources().getColor(R.color.alivc_common_font_white_light);
        this.mAdvSeekColor = getResources().getColor(R.color.alivc_player_theme_blue);
        this.mIcons = new ArrayList();
        this.mCirclePaint = new Paint(1);
        init();
    }

    private boolean betweenMiddleAndEnd(int i2) {
        long j2 = i2;
        long j3 = this.mSourceTime;
        long j4 = this.mAdvTime;
        return j2 > (j3 / 2) + (j4 * 2) && j2 < j3 + (j4 * 2);
    }

    private boolean betweenStartAndMiddle(int i2) {
        long j2 = i2;
        long j3 = this.mAdvTime;
        return j2 > j3 && j2 < (this.mSourceTime / 2) + j3;
    }

    private void calculateScale() {
        this.mTotalTime = calculateTotal();
    }

    private long calculateTotal() {
        if (this.mAdvPosition == null) {
            return 0L;
        }
        setMax((int) ((this.mAdvNumber * this.mAdvTime) + this.mSourceTime));
        setCurrentProgress(0);
        return (this.mAdvNumber * this.mAdvTime) + this.mSourceTime;
    }

    private void drawAdvLine(int i2, int i3, Canvas canvas) {
        this.mPaint.setColor(this.mAdvSeekColor);
        float f2 = i2;
        int i4 = this.mPointY;
        canvas.drawLine(f2, i4, i3, i4, this.mPaint);
    }

    private void drawSourceLine(int i2, int i3, Canvas canvas) {
        this.mPaint.setColor(this.mSourceSeekColor);
        float f2 = i2;
        int i4 = this.mPointY;
        canvas.drawLine(f2, i4, i3, i4, this.mPaint);
    }

    private boolean inVideoPositionBeforeMiddle(int i2) {
        AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == AdvPosition.ALL || advPosition == AdvPosition.START_MIDDLE) {
            long j2 = i2;
            long j3 = this.mSourceTime;
            long j4 = this.mAdvTime;
            return j2 >= (j3 / 2) + j4 && j2 <= (j3 / 2) + (j4 * 2);
        }
        if (advPosition == AdvPosition.START_END || advPosition == AdvPosition.ONLY_START || advPosition == AdvPosition.ONLY_END) {
            return false;
        }
        long j5 = i2;
        long j6 = this.mSourceTime;
        return j5 >= j6 / 2 && j5 <= (j6 / 2) + this.mAdvTime;
    }

    private void init() {
        this.mCirclePaint.setColor(-1);
        this.mCirclePaint.setStrokeWidth(TypedValue.applyDimension(1, 6.0f, getContext().getResources().getDisplayMetrics()));
        this.mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mCirclePaint.setAntiAlias(true);
        this.mPaint = new Paint(1);
        int iDip2px = DensityUtils.dip2px(getContext(), 2.0f);
        this.mPaintStrokeWidth = iDip2px;
        this.mPaint.setStrokeWidth(iDip2px);
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        setOnTouchListener(this);
    }

    private boolean isVideoPositionInEnd(long j2) {
        AdvPosition advPosition = this.mAdvPosition;
        return (advPosition == AdvPosition.ALL || advPosition == AdvPosition.START_MIDDLE) ? j2 >= this.mSourceTime + (this.mAdvTime * 2) : (advPosition == AdvPosition.ONLY_START || advPosition == AdvPosition.ONLY_MIDDLE || advPosition == AdvPosition.START_END || advPosition == AdvPosition.MIDDLE_END) ? j2 >= this.mSourceTime + this.mAdvTime : j2 >= this.mSourceTime;
    }

    private boolean isVideoPositionInMiddle(long j2) {
        AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == AdvPosition.ALL || advPosition == AdvPosition.START_MIDDLE) {
            long j3 = this.mSourceTime;
            long j4 = this.mAdvTime;
            return j2 >= (j3 / 2) + j4 && j2 <= (j3 / 2) + (j4 * 2);
        }
        if (advPosition == AdvPosition.START_END || advPosition == AdvPosition.ONLY_START || advPosition == AdvPosition.ONLY_END) {
            return false;
        }
        long j5 = this.mSourceTime;
        return j2 >= j5 / 2 && j2 <= (j5 / 2) + this.mAdvTime;
    }

    private boolean isVideoPositionInStart(long j2) {
        return j2 >= 0 && j2 <= this.mAdvTime;
    }

    public void addIcons(List<SummaryPoint> list) {
        this.mIcons.clear();
        this.mIcons.addAll(list);
        invalidate();
    }

    public void calculateWidth() {
        long j2 = this.mTotalTime;
        if (j2 == 0) {
            return;
        }
        int i2 = this.mViewWidth;
        int i3 = this.mPaddingRight;
        int i4 = this.mPaddingLeft;
        this.mAdvWidth = (int) ((((i2 - i3) - i4) * this.mAdvTime) / j2);
        this.mSourceWidth = (int) ((((i2 - i3) - i4) * this.mSourceTime) / j2);
        invalidate();
    }

    public AdvVideoView.IntentPlayVideo getIntentPlayVideo(int i2, int i3) {
        long j2 = i3;
        return isVideoPositionInStart(j2) ? AdvVideoView.IntentPlayVideo.START_ADV : isVideoPositionInMiddle(j2) ? AdvVideoView.IntentPlayVideo.MIDDLE_ADV : (betweenStartAndMiddle(i2) && betweenMiddleAndEnd(i3)) ? AdvVideoView.IntentPlayVideo.MIDDLE_ADV_SEEK : (isVideoPositionInEnd(j2) && betweenMiddleAndEnd(i2)) ? AdvVideoView.IntentPlayVideo.END_ADV : (betweenStartAndMiddle(i2) && betweenMiddleAndEnd(i3)) ? AdvVideoView.IntentPlayVideo.MIDDLE_ADV_SEEK : (betweenStartAndMiddle(i2) && isVideoPositionInEnd(j2)) ? AdvVideoView.IntentPlayVideo.MIDDLE_END_ADV_SEEK : (betweenStartAndMiddle(i3) && betweenStartAndMiddle(i3)) ? AdvVideoView.IntentPlayVideo.REVERSE_SOURCE : AdvVideoView.IntentPlayVideo.NORMAL;
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public void onDraw(Canvas canvas) {
        AdvPosition advPosition = this.mAdvPosition;
        if (advPosition == null) {
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[advPosition.ordinal()]) {
            case 1:
                int i2 = this.mPaddingLeft;
                drawAdvLine(i2, this.mAdvWidth + i2, canvas);
                int i3 = this.mAdvWidth;
                int i4 = this.mPaddingLeft;
                drawSourceLine(i3 + i4, i3 + (this.mSourceWidth / 2) + i4, canvas);
                int i5 = this.mAdvWidth;
                int i6 = this.mSourceWidth;
                int i7 = this.mPaddingLeft;
                drawAdvLine((i6 / 2) + i5 + i7, (i5 * 2) + (i6 / 2) + i7, canvas);
                int i8 = this.mAdvWidth;
                int i9 = this.mSourceWidth;
                int i10 = this.mPaddingLeft;
                drawSourceLine((i8 * 2) + (i9 / 2) + i10, (i8 * 2) + i9 + i10, canvas);
                int i11 = this.mAdvWidth;
                int i12 = this.mSourceWidth;
                int i13 = this.mPaddingLeft;
                drawAdvLine((i11 * 2) + i12 + i13, (i11 * 3) + i12 + i13, canvas);
                break;
            case 2:
                drawAdvLine((int) (getX() + this.mPaddingLeft), (int) (getX() + this.mAdvWidth + this.mPaddingLeft), canvas);
                int i14 = this.mAdvWidth;
                int i15 = this.mPaddingLeft;
                drawSourceLine(i14 + i15, i14 + this.mSourceWidth + i15, canvas);
                int i16 = this.mAdvWidth;
                int i17 = this.mSourceWidth;
                int i18 = this.mPaddingLeft;
                drawAdvLine(i16 + i17 + i18, (i16 * 2) + i17 + i18, canvas);
                break;
            case 3:
                int i19 = this.mPaddingLeft;
                drawSourceLine(i19, this.mAdvWidth + i19, canvas);
                int i20 = this.mAdvWidth;
                int i21 = this.mPaddingLeft;
                drawSourceLine(i20 + i21, i20 + (this.mSourceWidth / 2) + i21, canvas);
                int i22 = this.mAdvWidth;
                int i23 = this.mSourceWidth;
                int i24 = this.mPaddingLeft;
                drawAdvLine((i23 / 2) + i22 + i24, (i22 * 2) + (i23 / 2) + i24, canvas);
                int i25 = this.mAdvWidth;
                int i26 = this.mSourceWidth;
                int i27 = this.mPaddingLeft;
                drawSourceLine((i25 * 2) + (i26 / 2) + i27, (i25 * 2) + i26 + i27, canvas);
                break;
            case 4:
                int i28 = this.mPaddingLeft;
                drawSourceLine(i28, (this.mSourceWidth / 2) + i28, canvas);
                int i29 = this.mSourceWidth;
                int i30 = this.mPaddingLeft;
                drawAdvLine((i29 / 2) + i30, (i29 / 2) + this.mAdvWidth + i30, canvas);
                int i31 = this.mSourceWidth;
                int i32 = this.mAdvWidth;
                int i33 = this.mPaddingLeft;
                drawSourceLine((i31 / 2) + i32 + i33, i31 + i32 + i33, canvas);
                int i34 = this.mSourceWidth;
                int i35 = this.mAdvWidth;
                int i36 = this.mPaddingLeft;
                drawAdvLine(i34 + i35 + i36, i34 + (i35 * 2) + i36, canvas);
                break;
            case 5:
                int i37 = this.mPaddingLeft;
                drawAdvLine(i37, this.mAdvWidth + i37, canvas);
                int i38 = this.mAdvWidth;
                int i39 = this.mPaddingLeft;
                drawSourceLine(i38 + i39, i38 + this.mSourceWidth + i39, canvas);
                break;
            case 6:
                int i40 = this.mPaddingLeft;
                drawSourceLine(i40, (this.mSourceWidth / 2) + i40, canvas);
                int i41 = this.mSourceWidth;
                int i42 = this.mPaddingLeft;
                drawAdvLine((i41 / 2) + i42, (i41 / 2) + this.mAdvWidth + i42, canvas);
                int i43 = this.mSourceWidth;
                int i44 = this.mAdvWidth;
                int i45 = this.mPaddingLeft;
                drawSourceLine((i43 / 2) + i44 + i45, i43 + i44 + i45, canvas);
                break;
            case 7:
                int i46 = this.mPaddingLeft;
                drawSourceLine(i46, this.mSourceWidth + i46, canvas);
                int i47 = this.mSourceWidth;
                int i48 = this.mPaddingLeft;
                drawAdvLine(i47 + i48, i47 + this.mAdvWidth + i48, canvas);
                break;
            default:
                drawSourceLine(this.mPaddingLeft, this.mSourceWidth, canvas);
                break;
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.mViewWidth = i4 - i2;
        this.mPointY = (i5 - i3) / 2;
        calculateWidth();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    public void setAdvSeekColor(int i2) {
        this.mAdvSeekColor = i2;
    }

    public void setCurrentProgress(int i2) {
        setProgress(i2);
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.mIconClickListener = onIconClickListener;
    }

    public void setSourceSeekColor(int i2) {
        this.mSourceSeekColor = i2;
    }

    public void setTime(long j2, long j3, AdvPosition advPosition) {
        this.mAdvTime = j2;
        this.mAdvPosition = advPosition;
        this.mSourceTime = j3;
        switch (AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[advPosition.ordinal()]) {
            case 1:
                this.mAdvNumber = 3;
                break;
            case 2:
            case 3:
            case 4:
                this.mAdvNumber = 2;
                break;
            case 5:
            case 6:
            case 7:
                this.mAdvNumber = 1;
                break;
            default:
                this.mAdvNumber = 0;
                break;
        }
        calculateScale();
        calculateWidth();
        invalidate();
    }

    public long startPlayPosition(long j2) {
        long j3;
        long j4;
        switch (AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$function$MutiSeekBarView$AdvPosition[this.mAdvPosition.ordinal()]) {
            case 1:
                if (!isVideoPositionInStart(j2)) {
                    if (isVideoPositionInMiddle(j2)) {
                        j3 = this.mSourceTime / 2;
                        j4 = this.mAdvTime;
                    } else {
                        if (!isVideoPositionInEnd(j2)) {
                            return j2;
                        }
                        j3 = this.mSourceTime;
                        j4 = this.mAdvTime * 2;
                    }
                    return j3 + j4;
                }
                return 0L;
            case 2:
                if (!isVideoPositionInStart(j2)) {
                    if (!isVideoPositionInEnd(j2)) {
                        return j2;
                    }
                    j3 = this.mSourceTime;
                    j4 = this.mAdvTime;
                    return j3 + j4;
                }
                return 0L;
            case 3:
                if (!isVideoPositionInStart(j2)) {
                    if (!isVideoPositionInMiddle(j2)) {
                        return j2;
                    }
                    j3 = this.mSourceTime / 2;
                    j4 = this.mAdvTime;
                    return j3 + j4;
                }
                return 0L;
            case 4:
                if (isVideoPositionInMiddle(j2)) {
                    return this.mSourceTime / 2;
                }
                if (!isVideoPositionInEnd(j2)) {
                    return j2;
                }
                j3 = this.mSourceTime;
                j4 = this.mAdvTime;
                return j3 + j4;
            case 5:
                if (!isVideoPositionInStart(j2)) {
                    return j2;
                }
                return 0L;
            case 6:
                return isVideoPositionInMiddle(j2) ? this.mSourceTime / 2 : j2;
            case 7:
                return isVideoPositionInEnd(j2) ? this.mSourceTime : j2;
            default:
                return j2;
        }
    }

    public MutiSeekBarView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaintStrokeWidth = 2;
        this.mSourceSeekColor = getResources().getColor(R.color.alivc_common_font_white_light);
        this.mAdvSeekColor = getResources().getColor(R.color.alivc_player_theme_blue);
        this.mIcons = new ArrayList();
        this.mCirclePaint = new Paint(1);
        init();
    }

    public MutiSeekBarView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPaintStrokeWidth = 2;
        this.mSourceSeekColor = getResources().getColor(R.color.alivc_common_font_white_light);
        this.mAdvSeekColor = getResources().getColor(R.color.alivc_player_theme_blue);
        this.mIcons = new ArrayList();
        this.mCirclePaint = new Paint(1);
        init();
    }
}
