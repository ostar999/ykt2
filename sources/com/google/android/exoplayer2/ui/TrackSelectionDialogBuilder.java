package com.google.android.exoplayer2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public final class TrackSelectionDialogBuilder {
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private final DialogCallback callback;
    private final Context context;
    private boolean isDisabled;
    private final MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
    private List<DefaultTrackSelector.SelectionOverride> overrides;
    private final int rendererIndex;
    private boolean showDisableOption;

    @StyleRes
    private int themeResId;
    private final CharSequence title;

    @Nullable
    private Comparator<Format> trackFormatComparator;

    @Nullable
    private TrackNameProvider trackNameProvider;

    public interface DialogCallback {
        void onTracksSelected(boolean z2, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i2, DialogCallback dialogCallback) {
        this.context = context;
        this.title = charSequence;
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = i2;
        this.callback = dialogCallback;
        this.overrides = Collections.emptyList();
    }

    @Nullable
    private Dialog buildForAndroidX() throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        try {
            Class cls = Integer.TYPE;
            Object objNewInstance = AlertDialog.Builder.class.getConstructor(Context.class, cls).newInstance(this.context, Integer.valueOf(this.themeResId));
            View viewInflate = LayoutInflater.from((Context) AlertDialog.Builder.class.getMethod("getContext", new Class[0]).invoke(objNewInstance, new Object[0])).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
            DialogInterface.OnClickListener upDialogView = setUpDialogView(viewInflate);
            AlertDialog.Builder.class.getMethod("setTitle", CharSequence.class).invoke(objNewInstance, this.title);
            AlertDialog.Builder.class.getMethod("setView", View.class).invoke(objNewInstance, viewInflate);
            AlertDialog.Builder.class.getMethod("setPositiveButton", cls, DialogInterface.OnClickListener.class).invoke(objNewInstance, Integer.valueOf(android.R.string.ok), upDialogView);
            AlertDialog.Builder.class.getMethod("setNegativeButton", cls, DialogInterface.OnClickListener.class).invoke(objNewInstance, Integer.valueOf(android.R.string.cancel), null);
            return (Dialog) AlertDialog.Builder.class.getMethod("create", new Class[0]).invoke(objNewInstance, new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    private Dialog buildForPlatform() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context, this.themeResId);
        View viewInflate = LayoutInflater.from(builder.getContext()).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
        return builder.setTitle(this.title).setView(viewInflate).setPositiveButton(android.R.string.ok, setUpDialogView(viewInflate)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(DefaultTrackSelector defaultTrackSelector, DefaultTrackSelector.Parameters parameters, int i2, TrackGroupArray trackGroupArray, boolean z2, List list) {
        defaultTrackSelector.setParameters(TrackSelectionUtil.updateParametersWithOverride(parameters, i2, trackGroupArray, z2, list.isEmpty() ? null : (DefaultTrackSelector.SelectionOverride) list.get(0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpDialogView$1(TrackSelectionView trackSelectionView, DialogInterface dialogInterface, int i2) {
        this.callback.onTracksSelected(trackSelectionView.getIsDisabled(), trackSelectionView.getOverrides());
    }

    private DialogInterface.OnClickListener setUpDialogView(View view) {
        final TrackSelectionView trackSelectionView = (TrackSelectionView) view.findViewById(R.id.exo_track_selection_view);
        trackSelectionView.setAllowMultipleOverrides(this.allowMultipleOverrides);
        trackSelectionView.setAllowAdaptiveSelections(this.allowAdaptiveSelections);
        trackSelectionView.setShowDisableOption(this.showDisableOption);
        TrackNameProvider trackNameProvider = this.trackNameProvider;
        if (trackNameProvider != null) {
            trackSelectionView.setTrackNameProvider(trackNameProvider);
        }
        trackSelectionView.init(this.mappedTrackInfo, this.rendererIndex, this.isDisabled, this.overrides, this.trackFormatComparator, null);
        return new DialogInterface.OnClickListener() { // from class: com.google.android.exoplayer2.ui.i0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f6941c.lambda$setUpDialogView$1(trackSelectionView, dialogInterface, i2);
            }
        };
    }

    public Dialog build() throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Dialog dialogBuildForAndroidX = buildForAndroidX();
        return dialogBuildForAndroidX == null ? buildForPlatform() : dialogBuildForAndroidX;
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean z2) {
        this.allowAdaptiveSelections = z2;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean z2) {
        this.allowMultipleOverrides = z2;
        return this;
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean z2) {
        this.isDisabled = z2;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(@Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        return setOverrides(selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride));
    }

    public TrackSelectionDialogBuilder setOverrides(List<DefaultTrackSelector.SelectionOverride> list) {
        this.overrides = list;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean z2) {
        this.showDisableOption = z2;
        return this;
    }

    public TrackSelectionDialogBuilder setTheme(@StyleRes int i2) {
        this.themeResId = i2;
        return this;
    }

    public void setTrackFormatComparator(@Nullable Comparator<Format> comparator) {
        this.trackFormatComparator = comparator;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(@Nullable TrackNameProvider trackNameProvider) {
        this.trackNameProvider = trackNameProvider;
        return this;
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, final DefaultTrackSelector defaultTrackSelector, final int i2) {
        this.context = context;
        this.title = charSequence;
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(defaultTrackSelector.getCurrentMappedTrackInfo());
        this.mappedTrackInfo = mappedTrackInfo;
        this.rendererIndex = i2;
        final TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
        final DefaultTrackSelector.Parameters parameters = defaultTrackSelector.getParameters();
        this.isDisabled = parameters.getRendererDisabled(i2);
        DefaultTrackSelector.SelectionOverride selectionOverride = parameters.getSelectionOverride(i2, trackGroups);
        this.overrides = selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride);
        this.callback = new DialogCallback() { // from class: com.google.android.exoplayer2.ui.j0
            @Override // com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.DialogCallback
            public final void onTracksSelected(boolean z2, List list) {
                TrackSelectionDialogBuilder.lambda$new$0(defaultTrackSelector, parameters, i2, trackGroups, z2, list);
            }
        };
    }
}
