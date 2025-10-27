package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.psychiatrygarden.utils.NewToast;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class LineView extends View {
    public static final int SHOW_POPUPS_All = 1;
    public static final int SHOW_POPUPS_MAXMIN_ONLY = 2;
    public static final int SHOW_POPUPS_NONE = 3;
    private final int BACKGROUND_LINE_COLOR;
    private final int BOTTOM_TEXT_COLOR;
    private final int DOT_INNER_CIR_RADIUS;
    private final int DOT_OUTER_CIR_RADIUS;
    private final int LINE_OFFSET_LENGTH;
    private final int MIN_HORIZONTAL_GRID_NUM;
    private final int MIN_TOP_LINE_LENGTH;
    private final int MIN_VERTICAL_GRID_NUM;
    private int TOPMARGEENLENGTH;
    private int TOPMARGEN;
    private final int YCOORD_TEXT_LEFT_MARGIN;
    private Runnable animator;
    private boolean autoSetGridWidth;
    private int backgroundGridWidth;
    private final int bottomLineLength;
    private int bottomTextDescent;
    private int bottomTextHeight;
    private ArrayList<String> bottomTextList;
    private Paint bottomTextPaint;
    private final int bottomTextTopMargin;
    private final int bottomTriangleHeight;
    private String[] colorArray;
    private ArrayList<ArrayList<Integer>> dataLists;
    private ArrayList<ArrayList<Dot>> drawDotLists;
    private String firstYear;
    private boolean mShowYCoordinate;
    private int mViewHeight;
    private ArrayList<ArrayList<String>> originalDataLists;
    private final int popupBottomMargin;
    private Paint popupTextPaint;
    private final int popupTopPadding;
    private String secondYear;
    private Dot selectedDot;
    public boolean showPopup;
    private int sideLineLength;
    private int topLineLength;
    private int verticalGridNum;
    private ArrayList<Integer> xCoordinateList;
    private Paint ycoordTextPaint;
    private List<String> years;

    public class Dot {
        int data;
        int linenumber;
        int targetX;
        int targetY;
        int velocity;

        /* renamed from: x, reason: collision with root package name */
        int f16263x;

        /* renamed from: y, reason: collision with root package name */
        int f16264y;

        public Dot(int x2, int y2, int targetX, int targetY, Integer data, int linenumber) {
            this.velocity = LineView.this.dip2px(LineView.this.getContext(), 18.0f);
            this.f16263x = x2;
            this.f16264y = y2;
            this.linenumber = linenumber;
            setTargetData(targetX, targetY, data, linenumber);
        }

        private int updateSelf(int origin, int target, int velocity) {
            if (origin < target) {
                origin += velocity;
            } else if (origin > target) {
                origin -= velocity;
            }
            return Math.abs(target - origin) < velocity ? target : origin;
        }

        public Point getPoint() {
            return new Point(this.f16263x, this.f16264y);
        }

        public boolean isAtRest() {
            return this.f16263x == this.targetX && this.f16264y == this.targetY;
        }

        public Dot setTargetData(int targetX, int targetY, Integer data, int linenumber) {
            this.targetX = targetX;
            this.targetY = targetY;
            this.data = data.intValue();
            this.linenumber = linenumber;
            return this;
        }

        public void update() {
            this.f16263x = updateSelf(this.f16263x, this.targetX, this.velocity);
            this.f16264y = updateSelf(this.f16264y, this.targetY, this.velocity);
        }
    }

    public class YCoordData {
        private int data;

        /* renamed from: y, reason: collision with root package name */
        private int f16265y;

        public YCoordData() {
        }

        public int getData() {
            return this.data;
        }

        public int getY() {
            return this.f16265y;
        }

        public void setData(int data) {
            this.data = data;
        }

        public void setY(int y2) {
            this.f16265y = y2;
        }
    }

    public LineView(Context context) {
        this(context, null);
    }

    private void drawBackgroundLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(getContext(), 1.0f));
        paint.setColor(this.BACKGROUND_LINE_COLOR);
        canvas.drawText("(分数)", dip2px(getContext(), 7.0f), dip2px(getContext(), 30.0f) + this.TOPMARGEENLENGTH, this.ycoordTextPaint);
        canvas.drawText("西综综合测试", dip2px(getContext(), 7.0f), dip2px(getContext(), 15.0f) + this.TOPMARGEENLENGTH, this.ycoordTextPaint);
        for (int i2 = 0; i2 < 7; i2++) {
            int lineY = getLineY((this.verticalGridNum * (7 - i2)) / 7);
            Path path = new Path();
            path.moveTo(this.bottomLineLength, this.TOPMARGEENLENGTH + lineY);
            path.lineTo(getWidth() - this.bottomLineLength, this.TOPMARGEENLENGTH + lineY);
            canvas.drawPath(path, getDotLinePaint());
            if (this.mShowYCoordinate) {
                canvas.drawText((Math.rint((this.verticalGridNum * i2) / 7) + "").substring(0, r2.length() - 2), 10.0f, lineY + this.TOPMARGEN, this.ycoordTextPaint);
            }
        }
        if (this.bottomTextList != null) {
            for (int i3 = 0; i3 < this.bottomTextList.size(); i3++) {
                int i4 = this.sideLineLength + (this.backgroundGridWidth * i3);
                if (this.mShowYCoordinate) {
                    i4 += this.YCOORD_TEXT_LEFT_MARGIN;
                }
                canvas.drawText(this.bottomTextList.get(i3), i4, ((this.mViewHeight - this.bottomTextDescent) - this.LINE_OFFSET_LENGTH) + dip2px(getContext(), 8.0f), this.bottomTextPaint);
            }
        }
    }

    private void drawDots(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Paint paint2 = new Paint(paint);
        paint2.setColor(Color.parseColor("#FFFFFF"));
        ArrayList<ArrayList<Dot>> arrayList = this.drawDotLists;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < this.drawDotLists.size(); i2++) {
            paint.setColor(Color.parseColor(this.colorArray[i2 % 3]));
            Iterator<Dot> it = this.drawDotLists.get(i2).iterator();
            while (it.hasNext()) {
                Dot next = it.next();
                canvas.drawCircle(next.f16263x + this.LINE_OFFSET_LENGTH, next.f16264y + this.TOPMARGEN, this.DOT_OUTER_CIR_RADIUS, paint);
                canvas.drawCircle(next.f16263x + this.LINE_OFFSET_LENGTH, next.f16264y + this.TOPMARGEN, this.DOT_INNER_CIR_RADIUS, paint2);
            }
        }
    }

    private void drawIllustrate(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Paint paint2 = new Paint(paint);
        paint2.setColor(Color.parseColor("#FFFFFF"));
        paint.setColor(Color.parseColor(this.colorArray[0]));
        canvas.drawCircle(dip2px(getContext(), 180.0f), dip2px(getContext(), 18.0f), this.DOT_OUTER_CIR_RADIUS, paint);
        canvas.drawCircle(dip2px(getContext(), 180.0f), dip2px(getContext(), 18.0f), this.DOT_INNER_CIR_RADIUS, paint2);
        Paint paint3 = new Paint();
        paint3.setTextSize(dip2px(getContext(), 10.0f));
        paint3.setColor(Color.parseColor(this.colorArray[0]));
        canvas.drawText(this.firstYear, dip2px(getContext(), 185.0f), dip2px(getContext(), 22.0f), paint3);
        paint.setColor(Color.parseColor(this.colorArray[1]));
        paint3.setColor(Color.parseColor(this.colorArray[1]));
        canvas.drawCircle(dip2px(getContext(), 90.0f), dip2px(getContext(), 18.0f), this.DOT_OUTER_CIR_RADIUS, paint);
        canvas.drawCircle(dip2px(getContext(), 90.0f), dip2px(getContext(), 18.0f), this.DOT_INNER_CIR_RADIUS, paint2);
        canvas.drawText(this.secondYear, dip2px(getContext(), 95.0f), dip2px(getContext(), 22.0f), paint3);
    }

    private void drawLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(dip2px(getContext(), 1.0f));
        for (int i2 = 0; i2 < this.drawDotLists.size(); i2++) {
            paint.setColor(Color.parseColor(this.colorArray[i2 % 3]));
            for (int i3 = 0; i3 < this.drawDotLists.get(i2).size() - 1; i3++) {
                canvas.drawLine(this.drawDotLists.get(i2).get(i3).f16263x + this.LINE_OFFSET_LENGTH, this.drawDotLists.get(i2).get(i3).f16264y + this.TOPMARGEN, this.drawDotLists.get(i2).get(r10).f16263x + this.LINE_OFFSET_LENGTH, this.drawDotLists.get(i2).get(r10).f16264y + this.TOPMARGEN, paint);
            }
        }
    }

    private void drawShadow(Canvas canvas) {
        Paint[] paintArr = new Paint[2];
        int[] iArr = {Color.parseColor("#ff8733"), Color.parseColor("#0066c0")};
        for (int i2 = 0; i2 < 2; i2++) {
            Paint paint = new Paint();
            paintArr[i2] = paint;
            paint.setColor(iArr[i2]);
            paintArr[i2].setStyle(Paint.Style.FILL);
            paintArr[i2].setStrokeWidth(4.0f);
        }
        Path[] pathArr = new Path[2];
        int iDip2px = this.YCOORD_TEXT_LEFT_MARGIN + dip2px(getContext(), 18.0f);
        int lineY = getLineY(this.verticalGridNum) + this.LINE_OFFSET_LENGTH;
        for (int i3 = 0; i3 < this.drawDotLists.size(); i3++) {
            Path path = new Path();
            pathArr[i3] = path;
            float f2 = lineY;
            path.moveTo(iDip2px, f2);
            paintArr[i3].setStyle(Paint.Style.FILL);
            paintArr[i3].setAlpha(30);
            for (int i4 = 0; i4 < this.drawDotLists.get(i3).size(); i4++) {
                float f3 = this.drawDotLists.get(i3).get(i4).f16263x + this.LINE_OFFSET_LENGTH;
                pathArr[i3].lineTo(f3, this.drawDotLists.get(i3).get(i4).f16264y + this.TOPMARGEN);
                if (i4 == this.drawDotLists.get(i3).size() - 1) {
                    pathArr[i3].lineTo(f3, f2);
                }
            }
            paintArr[i3].setShader(this.drawDotLists.get(i3).size() == 0 ? null : new LinearGradient(this.drawDotLists.get(i3).get(this.drawDotLists.get(i3).size() - 1).f16263x + this.LINE_OFFSET_LENGTH, this.drawDotLists.get(i3).get(this.drawDotLists.get(i3).size() - 1).f16264y + this.TOPMARGEN, this.drawDotLists.get(i3).get(this.drawDotLists.get(i3).size() - 1).f16263x + this.LINE_OFFSET_LENGTH, f2, iArr[i3], Color.parseColor("#FFFFFF"), Shader.TileMode.CLAMP));
            canvas.drawPath(pathArr[i3], paintArr[i3]);
        }
    }

    private Paint getDotLinePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#e1e1e1"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(getContext(), 0.5f));
        paint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f, 10.0f, 5.0f}, 1.0f));
        return paint;
    }

    private int getHorizontalGridNum() {
        int size;
        ArrayList<String> arrayList = this.bottomTextList;
        if (arrayList != null) {
            size = arrayList.size() - 1;
        } else {
            this.bottomTextList = new ArrayList<>();
            size = 0;
            int i2 = 0;
            while (i2 < 20) {
                ArrayList<String> arrayList2 = this.bottomTextList;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                i2++;
                sb.append(i2);
                arrayList2.add(sb.toString());
            }
        }
        if (size < 1) {
            return 1;
        }
        return size;
    }

    private int getLineY(int i2) {
        int i3 = this.topLineLength;
        return i3 + (((((((this.mViewHeight - i3) - this.bottomTextHeight) - this.bottomTextTopMargin) - this.bottomLineLength) - this.bottomTextDescent) * i2) / this.verticalGridNum) + this.TOPMARGEENLENGTH;
    }

    public static Integer getMaxInteger(ArrayList<ArrayList<Integer>> dataLists) {
        int iIntValue = ((Integer) Collections.max(dataLists.get(0))).intValue();
        int iIntValue2 = ((Integer) Collections.max(dataLists.get(1))).intValue();
        return iIntValue > iIntValue2 ? Integer.valueOf(iIntValue) : Integer.valueOf(iIntValue2);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int size = View.MeasureSpec.getSize(measureSpec);
        int mode = View.MeasureSpec.getMode(measureSpec);
        return mode != Integer.MIN_VALUE ? mode != 1073741824 ? preferred : size : Math.min(preferred, size);
    }

    private int getPopupHeight() {
        Rect rect = new Rect();
        this.popupTextPaint.getTextBounds(Constants.VIA_SHARE_TYPE_MINI_PROGRAM, 0, 1, rect);
        return new Rect((-rect.width()) / 2, (((-rect.height()) - this.bottomTriangleHeight) - (this.popupTopPadding * 2)) - this.popupBottomMargin, rect.width() / 2, this.popupTopPadding - this.popupBottomMargin).height();
    }

    private int getVerticalGridlNum() {
        return R2.attr.arcLabelPaddingBottom;
    }

    private int measureHeight(int measureSpec) {
        return getMeasurement(measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        return getMeasurement(measureSpec, (this.backgroundGridWidth * getHorizontalGridNum()) + (this.sideLineLength * 2));
    }

    private void refreshAfterDataChanged() {
        int verticalGridlNum = getVerticalGridlNum();
        this.verticalGridNum = verticalGridlNum;
        refreshTopLineLength(verticalGridlNum);
        refreshDrawDotList(this.verticalGridNum);
    }

    private void refreshDrawDotList(int verticalGridNum) {
        ArrayList<Integer> arrayList;
        ArrayList<ArrayList<Integer>> arrayList2 = this.dataLists;
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            if (this.drawDotLists.size() == 0) {
                for (int i2 = 0; i2 < this.dataLists.size(); i2++) {
                    this.drawDotLists.add(new ArrayList<>());
                }
            }
            ArrayList<ArrayList<Integer>> arrayList3 = this.dataLists;
            if (arrayList3 != null && arrayList3.size() > 0 && (arrayList = this.xCoordinateList) != null && arrayList.size() > 0) {
                for (int i3 = 0; i3 < this.dataLists.size(); i3++) {
                    int size = this.drawDotLists.get(i3).isEmpty() ? 0 : this.drawDotLists.get(i3).size();
                    for (int i4 = 0; i4 < this.dataLists.get(i3).size(); i4++) {
                        int iIntValue = this.xCoordinateList.get(i4).intValue();
                        int lineY = getLineY(verticalGridNum - this.dataLists.get(i3).get(i4).intValue()) - dip2px(getContext(), 5.0f);
                        if (i4 > size - 1) {
                            this.drawDotLists.get(i3).add(new Dot(iIntValue, 0, iIntValue, lineY, this.dataLists.get(i3).get(i4), i3));
                        } else {
                            this.drawDotLists.get(i3).set(i4, this.drawDotLists.get(i3).get(i4).setTargetData(iIntValue, lineY, this.dataLists.get(i3).get(i4), i3));
                        }
                    }
                    int size2 = this.drawDotLists.get(i3).size() - this.dataLists.get(i3).size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        this.drawDotLists.get(i3).remove(this.drawDotLists.get(i3).size() - 1);
                    }
                }
            }
        }
        removeCallbacks(this.animator);
        post(this.animator);
    }

    private void refreshTopLineLength(int verticalGridNum) {
        if ((((this.mViewHeight - this.topLineLength) - this.bottomTextHeight) - this.bottomTextTopMargin) / (verticalGridNum + 2) < getPopupHeight()) {
            this.topLineLength = getPopupHeight() + this.DOT_OUTER_CIR_RADIUS + this.DOT_INNER_CIR_RADIUS + 2;
        } else {
            this.topLineLength = this.MIN_TOP_LINE_LENGTH;
        }
    }

    private void refreshXCoordinateList(int horizontalGridNum) {
        this.xCoordinateList.clear();
        for (int i2 = 0; i2 < horizontalGridNum + 1; i2++) {
            this.xCoordinateList.add(Integer.valueOf(this.sideLineLength + (this.backgroundGridWidth * i2)));
        }
    }

    private int updateSelf(int origin, int target, int velocity) {
        if (origin < target) {
            origin += velocity;
        } else if (origin > target) {
            origin -= velocity;
        }
        return Math.abs(target - origin) < velocity ? target : origin;
    }

    public void addYears(List<String> years) {
        this.years = years;
    }

    public int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        ArrayList<ArrayList<Integer>> arrayList = this.dataLists;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        drawBackgroundLines(canvas);
        drawShadow(canvas);
        drawLines(canvas);
        drawDots(canvas);
        drawIllustrate(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int iMeasureWidth = measureWidth(widthMeasureSpec);
        this.mViewHeight = measureHeight(heightMeasureSpec);
        refreshAfterDataChanged();
        setMeasuredDimension(iMeasureWidth, this.mViewHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1) {
            Point point = new Point();
            point.x = (int) event.getX();
            point.y = (int) event.getY();
            Region region = new Region();
            int iDip2px = dip2px(getContext(), 15.0f);
            for (int i2 = 0; i2 < this.drawDotLists.size(); i2++) {
                ArrayList<Dot> arrayList = this.drawDotLists.get(i2);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    Dot dot = arrayList.get(i3);
                    int i4 = dot.f16263x;
                    int i5 = dot.f16264y;
                    region.set(i4 - iDip2px, i5 - iDip2px, i4 + iDip2px, i5 + iDip2px);
                    if (region.contains(point.x, point.y)) {
                        if (this.originalDataLists.get(i2).get(i3) == null || this.originalDataLists.get(i2).get(i3).equals("")) {
                            NewToast.showShort(getContext(), "0", 0).show();
                        } else {
                            NewToast.showShort(getContext(), this.originalDataLists.get(i2).get(i3) + "", 0).show();
                        }
                    }
                }
            }
        }
        return true;
    }

    public void setBottomTextList(ArrayList<String> bottomTextList) {
        this.dataLists = null;
        this.bottomTextList = bottomTextList;
        Rect rect = new Rect();
        this.bottomTextDescent = 0;
        Iterator<String> it = bottomTextList.iterator();
        String str = "";
        int iWidth = 0;
        while (it.hasNext()) {
            String next = it.next();
            this.bottomTextPaint.getTextBounds(next, 0, next.length(), rect);
            if (this.bottomTextHeight < rect.height()) {
                this.bottomTextHeight = rect.height();
            }
            if (this.autoSetGridWidth && iWidth < rect.width()) {
                iWidth = rect.width();
                str = next;
            }
            if (this.bottomTextDescent < Math.abs(rect.bottom)) {
                this.bottomTextDescent = Math.abs(rect.bottom);
            }
        }
        if (this.autoSetGridWidth) {
            if (this.backgroundGridWidth < iWidth) {
                this.backgroundGridWidth = ((int) this.bottomTextPaint.measureText(str, 0, 1)) + iWidth;
            }
            int i2 = iWidth / 2;
            if (this.sideLineLength < i2) {
                this.sideLineLength = i2;
            }
        }
        refreshXCoordinateList(getHorizontalGridNum());
    }

    public void setDataList(ArrayList<ArrayList<Integer>> dataLists) {
        this.dataLists = dataLists;
        Iterator<ArrayList<Integer>> it = dataLists.iterator();
        while (it.hasNext()) {
            if (it.next().size() > this.bottomTextList.size()) {
                throw new RuntimeException("dacer.LineView error: dataList.size() > bottomTextList.size() !!!");
            }
        }
        refreshAfterDataChanged();
        this.showPopup = true;
        setMinimumWidth(0);
        postInvalidate();
    }

    public void setOriginaDatalListsList(ArrayList<ArrayList<String>> originalDataLists) {
        this.originalDataLists = originalDataLists;
    }

    public void setShowYCoordinate(boolean showYCoordinate) {
        this.mShowYCoordinate = showYCoordinate;
    }

    public void setYearTexts(String first, String second) {
        this.firstYear = first;
        this.secondYear = second;
    }

    public int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.autoSetGridWidth = true;
        this.bottomTextHeight = 0;
        this.xCoordinateList = new ArrayList<>();
        this.drawDotLists = new ArrayList<>();
        this.bottomTextPaint = new Paint();
        this.ycoordTextPaint = new Paint();
        this.colorArray = new String[]{"#FF8733", "#0066c0", "#1abc9c"};
        this.popupTextPaint = new Paint();
        this.bottomTriangleHeight = dip2px(getContext(), 2.0f);
        this.mShowYCoordinate = true;
        this.TOPMARGEENLENGTH = dip2px(getContext(), 8.0f);
        this.topLineLength = dip2px(getContext(), 12.0f);
        this.sideLineLength = (dip2px(getContext(), 27.0f) / 3) * 2;
        this.backgroundGridWidth = dip2px(getContext(), 27.0f);
        this.popupTopPadding = dip2px(getContext(), 2.0f);
        this.popupBottomMargin = dip2px(getContext(), 5.0f);
        this.bottomTextTopMargin = dip2px(getContext(), 5.0f);
        this.bottomLineLength = dip2px(getContext(), 25.0f);
        this.DOT_INNER_CIR_RADIUS = dip2px(getContext(), 2.0f);
        this.DOT_OUTER_CIR_RADIUS = dip2px(getContext(), 3.0f);
        this.MIN_TOP_LINE_LENGTH = dip2px(getContext(), 12.0f);
        this.LINE_OFFSET_LENGTH = dip2px(getContext(), 10.0f);
        this.MIN_VERTICAL_GRID_NUM = R2.attr.arcLabelPaddingBottom;
        this.MIN_HORIZONTAL_GRID_NUM = 1;
        this.BACKGROUND_LINE_COLOR = Color.parseColor("#EEEEEE");
        int color = Color.parseColor("#9B9A9B");
        this.BOTTOM_TEXT_COLOR = color;
        this.YCOORD_TEXT_LEFT_MARGIN = dip2px(getContext(), 10.0f);
        this.showPopup = true;
        this.years = new ArrayList();
        this.firstYear = "";
        this.secondYear = "";
        this.animator = new Runnable() { // from class: com.psychiatrygarden.widget.LineView.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = LineView.this.drawDotLists.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    Iterator it2 = ((ArrayList) it.next()).iterator();
                    while (it2.hasNext()) {
                        Dot dot = (Dot) it2.next();
                        dot.update();
                        if (!dot.isAtRest()) {
                            z2 = true;
                        }
                    }
                }
                if (z2) {
                    LineView.this.postDelayed(this, 25L);
                }
                LineView.this.invalidate();
            }
        };
        this.TOPMARGEN = dip2px(getContext(), 12.0f);
        this.popupTextPaint.setAntiAlias(true);
        this.popupTextPaint.setColor(-1);
        this.popupTextPaint.setTextSize(sp2px(getContext(), 13.0f));
        this.popupTextPaint.setStrokeWidth(5.0f);
        this.popupTextPaint.setTextAlign(Paint.Align.CENTER);
        this.bottomTextPaint.setAntiAlias(true);
        this.bottomTextPaint.setTextSize(sp2px(getContext(), 12.0f));
        this.bottomTextPaint.setTextAlign(Paint.Align.CENTER);
        this.bottomTextPaint.setStyle(Paint.Style.FILL);
        this.bottomTextPaint.setColor(color);
        this.ycoordTextPaint.setAntiAlias(true);
        this.ycoordTextPaint.setTextSize(sp2px(getContext(), 12.0f));
        this.ycoordTextPaint.setTextAlign(Paint.Align.LEFT);
        this.ycoordTextPaint.setStyle(Paint.Style.FILL);
        this.ycoordTextPaint.setColor(color);
    }
}
