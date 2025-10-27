package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public class TrackSelectionView extends LinearLayout {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final ComponentListener componentListener;
    private final CheckedTextView defaultView;
    private final CheckedTextView disableView;
    private final LayoutInflater inflater;
    private boolean isDisabled;

    @Nullable
    private TrackSelectionListener listener;
    private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private final SparseArray<DefaultTrackSelector.SelectionOverride> overrides;
    private int rendererIndex;
    private final int selectableItemBackgroundResourceId;
    private TrackGroupArray trackGroups;

    @Nullable
    private Comparator<TrackInfo> trackInfoComparator;
    private TrackNameProvider trackNameProvider;
    private CheckedTextView[][] trackViews;

    public class ComponentListener implements View.OnClickListener {
        private ComponentListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TrackSelectionView.this.onClick(view);
        }
    }

    public static final class TrackInfo {
        public final Format format;
        public final int groupIndex;
        public final int trackIndex;

        public TrackInfo(int i2, int i3, Format format) {
            this.groupIndex = i2;
            this.trackIndex = i3;
            this.format = format;
        }
    }

    public interface TrackSelectionListener {
        void onTrackSelectionChanged(boolean z2, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionView(Context context) {
        this(context, null);
    }

    private static int[] getTracksAdding(int[] iArr, int i2) {
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length + 1);
        iArrCopyOf[iArrCopyOf.length - 1] = i2;
        return iArrCopyOf;
    }

    private static int[] getTracksRemoving(int[] iArr, int i2) {
        int[] iArr2 = new int[iArr.length - 1];
        int i3 = 0;
        for (int i4 : iArr) {
            if (i4 != i2) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$init$0(Comparator comparator, TrackInfo trackInfo, TrackInfo trackInfo2) {
        return comparator.compare(trackInfo.format, trackInfo2.format);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(View view) {
        if (view == this.disableView) {
            onDisableViewClicked();
        } else if (view == this.defaultView) {
            onDefaultViewClicked();
        } else {
            onTrackViewClicked(view);
        }
        updateViewStates();
        TrackSelectionListener trackSelectionListener = this.listener;
        if (trackSelectionListener != null) {
            trackSelectionListener.onTrackSelectionChanged(getIsDisabled(), getOverrides());
        }
    }

    private void onDefaultViewClicked() {
        this.isDisabled = false;
        this.overrides.clear();
    }

    private void onDisableViewClicked() {
        this.isDisabled = true;
        this.overrides.clear();
    }

    private void onTrackViewClicked(View view) {
        this.isDisabled = false;
        TrackInfo trackInfo = (TrackInfo) Assertions.checkNotNull(view.getTag());
        int i2 = trackInfo.groupIndex;
        int i3 = trackInfo.trackIndex;
        DefaultTrackSelector.SelectionOverride selectionOverride = this.overrides.get(i2);
        Assertions.checkNotNull(this.mappedTrackInfo);
        if (selectionOverride == null) {
            if (!this.allowMultipleOverrides && this.overrides.size() > 0) {
                this.overrides.clear();
            }
            this.overrides.put(i2, new DefaultTrackSelector.SelectionOverride(i2, i3));
            return;
        }
        int i4 = selectionOverride.length;
        int[] iArr = selectionOverride.tracks;
        boolean zIsChecked = ((CheckedTextView) view).isChecked();
        boolean zShouldEnableAdaptiveSelection = shouldEnableAdaptiveSelection(i2);
        boolean z2 = zShouldEnableAdaptiveSelection || shouldEnableMultiGroupSelection();
        if (zIsChecked && z2) {
            if (i4 == 1) {
                this.overrides.remove(i2);
                return;
            } else {
                this.overrides.put(i2, new DefaultTrackSelector.SelectionOverride(i2, getTracksRemoving(iArr, i3)));
                return;
            }
        }
        if (zIsChecked) {
            return;
        }
        if (zShouldEnableAdaptiveSelection) {
            this.overrides.put(i2, new DefaultTrackSelector.SelectionOverride(i2, getTracksAdding(iArr, i3)));
        } else {
            this.overrides.put(i2, new DefaultTrackSelector.SelectionOverride(i2, i3));
        }
    }

    @RequiresNonNull({"mappedTrackInfo"})
    private boolean shouldEnableAdaptiveSelection(int i2) {
        return this.allowAdaptiveSelections && this.trackGroups.get(i2).length > 1 && this.mappedTrackInfo.getAdaptiveSupport(this.rendererIndex, i2, false) != 0;
    }

    private boolean shouldEnableMultiGroupSelection() {
        return this.allowMultipleOverrides && this.trackGroups.length > 1;
    }

    private void updateViewStates() {
        this.disableView.setChecked(this.isDisabled);
        this.defaultView.setChecked(!this.isDisabled && this.overrides.size() == 0);
        for (int i2 = 0; i2 < this.trackViews.length; i2++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = this.overrides.get(i2);
            int i3 = 0;
            while (true) {
                CheckedTextView[] checkedTextViewArr = this.trackViews[i2];
                if (i3 < checkedTextViewArr.length) {
                    if (selectionOverride != null) {
                        this.trackViews[i2][i3].setChecked(selectionOverride.containsTrack(((TrackInfo) Assertions.checkNotNull(checkedTextViewArr[i3].getTag())).trackIndex));
                    } else {
                        checkedTextViewArr[i3].setChecked(false);
                    }
                    i3++;
                }
            }
        }
    }

    private void updateViews() {
        for (int childCount = getChildCount() - 1; childCount >= 3; childCount--) {
            removeViewAt(childCount);
        }
        if (this.mappedTrackInfo == null) {
            this.disableView.setEnabled(false);
            this.defaultView.setEnabled(false);
            return;
        }
        this.disableView.setEnabled(true);
        this.defaultView.setEnabled(true);
        TrackGroupArray trackGroups = this.mappedTrackInfo.getTrackGroups(this.rendererIndex);
        this.trackGroups = trackGroups;
        this.trackViews = new CheckedTextView[trackGroups.length][];
        boolean zShouldEnableMultiGroupSelection = shouldEnableMultiGroupSelection();
        int i2 = 0;
        while (true) {
            TrackGroupArray trackGroupArray = this.trackGroups;
            if (i2 >= trackGroupArray.length) {
                updateViewStates();
                return;
            }
            TrackGroup trackGroup = trackGroupArray.get(i2);
            boolean zShouldEnableAdaptiveSelection = shouldEnableAdaptiveSelection(i2);
            CheckedTextView[][] checkedTextViewArr = this.trackViews;
            int i3 = trackGroup.length;
            checkedTextViewArr[i2] = new CheckedTextView[i3];
            TrackInfo[] trackInfoArr = new TrackInfo[i3];
            for (int i4 = 0; i4 < trackGroup.length; i4++) {
                trackInfoArr[i4] = new TrackInfo(i2, i4, trackGroup.getFormat(i4));
            }
            Comparator<TrackInfo> comparator = this.trackInfoComparator;
            if (comparator != null) {
                Arrays.sort(trackInfoArr, comparator);
            }
            for (int i5 = 0; i5 < i3; i5++) {
                if (i5 == 0) {
                    addView(this.inflater.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
                }
                CheckedTextView checkedTextView = (CheckedTextView) this.inflater.inflate((zShouldEnableAdaptiveSelection || zShouldEnableMultiGroupSelection) ? android.R.layout.simple_list_item_multiple_choice : android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
                checkedTextView.setBackgroundResource(this.selectableItemBackgroundResourceId);
                checkedTextView.setText(this.trackNameProvider.getTrackName(trackInfoArr[i5].format));
                checkedTextView.setTag(trackInfoArr[i5]);
                if (this.mappedTrackInfo.getTrackSupport(this.rendererIndex, i2, i5) == 4) {
                    checkedTextView.setFocusable(true);
                    checkedTextView.setOnClickListener(this.componentListener);
                } else {
                    checkedTextView.setFocusable(false);
                    checkedTextView.setEnabled(false);
                }
                this.trackViews[i2][i5] = checkedTextView;
                addView(checkedTextView);
            }
            i2++;
        }
    }

    public boolean getIsDisabled() {
        return this.isDisabled;
    }

    public List<DefaultTrackSelector.SelectionOverride> getOverrides() {
        ArrayList arrayList = new ArrayList(this.overrides.size());
        for (int i2 = 0; i2 < this.overrides.size(); i2++) {
            arrayList.add(this.overrides.valueAt(i2));
        }
        return arrayList;
    }

    public void init(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, boolean z2, List<DefaultTrackSelector.SelectionOverride> list, @Nullable final Comparator<Format> comparator, @Nullable TrackSelectionListener trackSelectionListener) {
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = i2;
        this.isDisabled = z2;
        this.trackInfoComparator = comparator == null ? null : new Comparator() { // from class: com.google.android.exoplayer2.ui.k0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return TrackSelectionView.lambda$init$0(comparator, (TrackSelectionView.TrackInfo) obj, (TrackSelectionView.TrackInfo) obj2);
            }
        };
        this.listener = trackSelectionListener;
        int size = this.allowMultipleOverrides ? list.size() : Math.min(list.size(), 1);
        for (int i3 = 0; i3 < size; i3++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = list.get(i3);
            this.overrides.put(selectionOverride.groupIndex, selectionOverride);
        }
        updateViews();
    }

    public void setAllowAdaptiveSelections(boolean z2) {
        if (this.allowAdaptiveSelections != z2) {
            this.allowAdaptiveSelections = z2;
            updateViews();
        }
    }

    public void setAllowMultipleOverrides(boolean z2) {
        if (this.allowMultipleOverrides != z2) {
            this.allowMultipleOverrides = z2;
            if (!z2 && this.overrides.size() > 1) {
                for (int size = this.overrides.size() - 1; size > 0; size--) {
                    this.overrides.remove(size);
                }
            }
            updateViews();
        }
    }

    public void setShowDisableOption(boolean z2) {
        this.disableView.setVisibility(z2 ? 0 : 8);
    }

    public void setTrackNameProvider(TrackNameProvider trackNameProvider) {
        this.trackNameProvider = (TrackNameProvider) Assertions.checkNotNull(trackNameProvider);
        updateViews();
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        super(context, attributeSet, i2);
        setOrientation(1);
        this.overrides = new SparseArray<>();
        setSaveFromParentEnabled(false);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        this.selectableItemBackgroundResourceId = resourceId;
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        this.inflater = layoutInflaterFrom;
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.trackGroups = TrackGroupArray.EMPTY;
        CheckedTextView checkedTextView = (CheckedTextView) layoutInflaterFrom.inflate(android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.disableView = checkedTextView;
        checkedTextView.setBackgroundResource(resourceId);
        checkedTextView.setText(R.string.exo_track_selection_none);
        checkedTextView.setEnabled(false);
        checkedTextView.setFocusable(true);
        checkedTextView.setOnClickListener(componentListener);
        checkedTextView.setVisibility(8);
        addView(checkedTextView);
        addView(layoutInflaterFrom.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
        CheckedTextView checkedTextView2 = (CheckedTextView) layoutInflaterFrom.inflate(android.R.layout.simple_list_item_single_choice, (ViewGroup) this, false);
        this.defaultView = checkedTextView2;
        checkedTextView2.setBackgroundResource(resourceId);
        checkedTextView2.setText(R.string.exo_track_selection_auto);
        checkedTextView2.setEnabled(false);
        checkedTextView2.setFocusable(true);
        checkedTextView2.setOnClickListener(componentListener);
        addView(checkedTextView2);
    }
}
