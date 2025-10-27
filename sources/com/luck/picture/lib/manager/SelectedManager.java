package com.luck.picture.lib.manager;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SelectedManager {
    public static final int ADD_SUCCESS = 0;
    public static final int INVALID = -1;
    public static final int REMOVE = 1;
    private static LocalMediaFolder currentLocalMediaFolder;
    private static final ArrayList<LocalMedia> selectedResult = new ArrayList<>();
    private static final ArrayList<LocalMedia> selectedPreviewResult = new ArrayList<>();

    public static void addResult(LocalMedia localMedia) {
        selectedResult.add(localMedia);
    }

    public static void addSelectedPreviewResult(ArrayList<LocalMedia> arrayList) {
        clearExternalPreviewData();
        selectedPreviewResult.addAll(arrayList);
    }

    public static void clear() {
        selectedResult.clear();
    }

    public static void clearExternalPreviewData() {
        selectedPreviewResult.clear();
    }

    public static int getCount() {
        return selectedResult.size();
    }

    public static LocalMediaFolder getCurrentLocalMediaFolder() {
        return currentLocalMediaFolder;
    }

    public static ArrayList<LocalMedia> getSelectedPreviewResult() {
        return selectedPreviewResult;
    }

    public static ArrayList<LocalMedia> getSelectedResult() {
        return selectedResult;
    }

    public static String getTopResultMimeType() {
        ArrayList<LocalMedia> arrayList = selectedResult;
        return arrayList.size() > 0 ? arrayList.get(0).getMimeType() : "";
    }

    public static void removeResult(LocalMedia localMedia) {
        selectedResult.remove(localMedia);
    }

    public static void setCurrentLocalMediaFolder(LocalMediaFolder localMediaFolder) {
        currentLocalMediaFolder = localMediaFolder;
    }
}
