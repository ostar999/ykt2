package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ProperRatingBar extends LinearLayout {
    private static final boolean DF_CLICKABLE = false;
    private static final int DF_DEFAULT_TICKS = 3;
    private static final int DF_SYMBOLIC_TEXT_NORMAL_COLOR = -16777216;
    private static final int DF_SYMBOLIC_TEXT_SELECTED_COLOR = -7829368;
    private static final int DF_SYMBOLIC_TEXT_SIZE_RES = 2131167384;
    private static final int DF_SYMBOLIC_TEXT_STYLE = 0;
    private static final int DF_SYMBOLIC_TICK_RES = 2131887571;
    private static final int DF_TICK_SPACING_RES = 2131167383;
    private static final int DF_TOTAL_TICKS = 5;
    final int LEFT;
    final int RIGHT;
    private boolean clickable;
    private int customTextNormalColor;
    private int customTextSelectedColor;
    private int customTextSize;
    private int customTextStyle;
    private int lastSelectedTickIndex;
    private RatingListener listener;
    private View.OnClickListener mTickClickedListener;
    private int rating;
    private String symbolicTick;
    private Drawable tickNormalDrawable;
    private Drawable tickSelectedDrawable;
    private int tickSpacing;
    private int totalTicks;
    private boolean useSymbolicTick;

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.psychiatrygarden.widget.ProperRatingBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean clickable;
        int rating;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.rating);
            parcel.writeByte(this.clickable ? (byte) 1 : (byte) 0);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.rating = in.readInt();
            this.clickable = in.readByte() == 1;
        }
    }

    public interface TicksIterator {
        void onTick(View tick, int position);
    }

    public ProperRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.useSymbolicTick = false;
        this.listener = null;
        this.RIGHT = 0;
        this.LEFT = 1;
        this.mTickClickedListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ProperRatingBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                ProperRatingBar.this.lastSelectedTickIndex = ((Integer) v2.getTag(R.id.prb_tick_tag_id)).intValue();
                ProperRatingBar properRatingBar = ProperRatingBar.this;
                properRatingBar.rating = properRatingBar.lastSelectedTickIndex + 1;
                ProperRatingBar.this.redrawTicks();
                if (ProperRatingBar.this.listener != null) {
                    ProperRatingBar.this.listener.onRatePicked(ProperRatingBar.this);
                }
            }
        };
        init(context, attrs);
    }

    private void addDrawableTick(Context context, int position) {
        ImageView imageView = new ImageView(context);
        int i2 = this.tickSpacing;
        imageView.setPadding(i2, i2, i2, i2);
        updateTicksClickParameters(imageView, position);
        addView(imageView);
    }

    private void addSymbolicTick(Context context, int position) {
        TextView textView = new TextView(context);
        textView.setText(this.symbolicTick);
        textView.setTextSize(0, this.customTextSize);
        int i2 = this.customTextStyle;
        if (i2 != 0) {
            textView.setTypeface(Typeface.DEFAULT, i2);
        }
        updateTicksClickParameters(textView, position);
        addView(textView);
    }

    private void addTick(Context context, int position) {
        if (this.useSymbolicTick) {
            addSymbolicTick(context, position);
        } else {
            addDrawableTick(context, position);
        }
    }

    private void addTicks(Context context) {
        removeAllViews();
        for (int i2 = 0; i2 < this.totalTicks; i2++) {
            addTick(context, i2);
        }
        redrawTicks();
    }

    private void afterInit() {
        int i2 = this.rating;
        int i3 = this.totalTicks;
        if (i2 > i3) {
            this.rating = i3;
        }
        this.lastSelectedTickIndex = this.rating - 1;
        if (this.tickNormalDrawable == null || this.tickSelectedDrawable == null) {
            this.useSymbolicTick = true;
        }
        addTicks(getContext());
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ProperRatingBar);
        this.totalTicks = typedArrayObtainStyledAttributes.getInt(10, 5);
        this.rating = typedArrayObtainStyledAttributes.getInt(3, 3);
        this.clickable = typedArrayObtainStyledAttributes.getBoolean(2, false);
        String string = typedArrayObtainStyledAttributes.getString(4);
        this.symbolicTick = string;
        if (string == null) {
            this.symbolicTick = context.getString(R.string.prb_default_symbolic_string);
        }
        this.customTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, context.getResources().getDimensionPixelOffset(R.dimen.prb_symbolic_tick_default_text_size));
        this.customTextStyle = typedArrayObtainStyledAttributes.getInt(1, 0);
        this.customTextNormalColor = typedArrayObtainStyledAttributes.getColor(5, -16777216);
        this.customTextSelectedColor = typedArrayObtainStyledAttributes.getColor(6, DF_SYMBOLIC_TEXT_SELECTED_COLOR);
        this.tickNormalDrawable = typedArrayObtainStyledAttributes.getDrawable(7);
        this.tickSelectedDrawable = typedArrayObtainStyledAttributes.getDrawable(8);
        this.tickSpacing = typedArrayObtainStyledAttributes.getDimensionPixelOffset(9, context.getResources().getDimensionPixelOffset(R.dimen.prb_drawable_tick_default_spacing));
        afterInit();
        typedArrayObtainStyledAttributes.recycle();
    }

    private void iterateTicks(TicksIterator iterator) {
        if (iterator == null) {
            throw new IllegalArgumentException("Iterator can't be null!");
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            iterator.onTick(getChildAt(i2), i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redrawTickSelection(ImageView tick, boolean isSelected) {
        if (isSelected) {
            tick.setImageDrawable(this.tickSelectedDrawable);
        } else {
            tick.setImageDrawable(this.tickNormalDrawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redrawTicks() {
        iterateTicks(new TicksIterator() { // from class: com.psychiatrygarden.widget.ProperRatingBar.2
            @Override // com.psychiatrygarden.widget.ProperRatingBar.TicksIterator
            public void onTick(View tick, int position) {
                if (ProperRatingBar.this.useSymbolicTick) {
                    ProperRatingBar properRatingBar = ProperRatingBar.this;
                    properRatingBar.redrawTickSelection((TextView) tick, position <= properRatingBar.lastSelectedTickIndex);
                } else {
                    ProperRatingBar properRatingBar2 = ProperRatingBar.this;
                    properRatingBar2.redrawTickSelection((ImageView) tick, position <= properRatingBar2.lastSelectedTickIndex);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTicksClickParameters(View tick, int position) {
        if (!this.clickable) {
            tick.setOnClickListener(null);
        } else {
            tick.setTag(R.id.prb_tick_tag_id, Integer.valueOf(position));
            tick.setOnClickListener(this.mTickClickedListener);
        }
    }

    public RatingListener getListener() {
        return this.listener;
    }

    public int getRating() {
        return this.rating;
    }

    public String getSymbolicTick() {
        return this.symbolicTick;
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.clickable;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setRating(savedState.rating);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.rating = this.rating;
        savedState.clickable = this.clickable;
        return savedState;
    }

    public void removeRatingListener() {
        this.listener = null;
    }

    @Override // android.view.View
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        iterateTicks(new TicksIterator() { // from class: com.psychiatrygarden.widget.ProperRatingBar.3
            @Override // com.psychiatrygarden.widget.ProperRatingBar.TicksIterator
            public void onTick(View tick, int position) {
                ProperRatingBar.this.updateTicksClickParameters(tick, position);
            }
        });
    }

    public void setListener(RatingListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null!");
        }
        this.listener = listener;
    }

    public void setRating(int rating) {
        int i2 = this.totalTicks;
        if (rating > i2) {
            rating = i2;
        }
        this.rating = rating;
        this.lastSelectedTickIndex = rating - 1;
        redrawTicks();
    }

    public void setSymbolicTick(String tick) {
        this.symbolicTick = tick;
        afterInit();
    }

    public void toggleClickable() {
        setClickable(!this.clickable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redrawTickSelection(TextView tick, boolean isSelected) {
        if (isSelected) {
            tick.setTextColor(this.customTextSelectedColor);
        } else {
            tick.setTextColor(this.customTextNormalColor);
        }
    }
}
