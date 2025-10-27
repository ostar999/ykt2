package com.psychiatrygarden.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.CharPool;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.catchpig.mvvm.utils.DateUtil;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.ykb.ebook.util.DateUtilKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a \u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001\u001a4\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b\u001a<\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0001\u001a\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\u0006\u0010\r\u001a\u00020\b\u001a\"\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\u0006\u0010\u000f\u001a\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001\u001a.\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\b\u001a\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001\u001a\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00170\u0001H\u0002\u001a%\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u001c\u001a$\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\b2\b\u0010 \u001a\u0004\u0018\u00010\b2\b\u0010!\u001a\u0004\u0018\u00010\b\u001a\u001c\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\b2\b\u0010%\u001a\u0004\u0018\u00010\bH\u0002\u001a\"\u0010&\u001a\u00020\b2\b\u0010$\u001a\u0004\u0018\u00010\b2\b\u0010%\u001a\u0004\u0018\u00010\b2\u0006\u0010'\u001a\u00020#\u001a(\u0010(\u001a\u00020\u00162\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\b\u001a\u001e\u0010)\u001a\u00020\u00162\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\b\u001a\u0014\u0010*\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010+\u001a\u00020\b2\b\u0010,\u001a\u0004\u0018\u00010\b\u001a¤\u0001\u0010-\u001a\u00020.2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\"\u0010/\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00102\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00103\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00104\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020#00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020#`1\u001aª\u0001\u00105\u001a\b\u0012\u0004\u0012\u00020\u00160\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\"\u0010/\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00102\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00103\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b`12\"\u00104\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020#00j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020#`1\u001a:\u00106\u001a\u00020.2\b\u00107\u001a\u0004\u0018\u00010\b2\b\u0010!\u001a\u0004\u0018\u00010\b2\b\u00108\u001a\u0004\u0018\u00010\b2\b\u00109\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010:\u001a\u0004\u0018\u00010\b\u001a\u001c\u00106\u001a\u00020.2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\u0006\u0010!\u001a\u00020\b\u001a\u0010\u0010;\u001a\u00020#2\u0006\u0010<\u001a\u00020\u0005H\u0002\u001a\u0010\u0010=\u001a\u00020#2\b\u0010>\u001a\u0004\u0018\u00010\b\u001a\u0010\u0010?\u001a\u00020#2\u0006\u0010,\u001a\u00020\bH\u0002\u001a(\u0010@\u001a\u00020.2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010A\u001a\u0004\u0018\u00010\b\u001aB\u0010B\u001a\u00020.2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010A\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010C\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b\u001a\u0010\u0010D\u001a\u00020#2\b\u0010E\u001a\u0004\u0018\u00010\b\u001a\u0014\u0010F\u001a\u00020#2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010G\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010H\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010I\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010J\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0012\u0010K\u001a\u00020#*\u00020L2\u0006\u0010M\u001a\u00020N\u001a\u0010\u0010O\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010P\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010Q\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a\u0010\u0010R\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u0002\u001a \u0010S\u001a\u00020#*\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\u0010T\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002¨\u0006U"}, d2 = {"courseDirectoryItemToTreeNodeData", "", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "Lcom/psychiatrygarden/bean/CourseDirectoryTreeBean;", "listData", "Lcom/psychiatrygarden/bean/CourseDirectoryItemData;", "courseDirectoryItemToTreeNodeData2", "expandFirstId", "", "expandSecondId", "courseDirectoryItemToTreeNodeData3", "getChapterId", "data", "chapterId", "getChapterIdByVid", "updateVid", "dataList", "getChapterIdByVideoId", "vId", "lastChapterId", "getCourseTitleFromDirectory", "getHaveVidNum", "", "Lcom/psychiatrygarden/bean/CourseDirectoryContentItem;", "getLivingDate", "", "startTimeStr", "endTimeStr", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "getLivingStatus", "Lcom/psychiatrygarden/utils/LiveStatus;", "livingStartTime", "livingEndTime", "vid", "getPercent90", "", "dur", "allDur", "getPercentStr", "finish90", "getPlayingPosition", "getPositionByChapterId", "getTreeNodeWaitPushTimeStr", "getWaitPushTimeFormat", "pushTime", "initSeeAndAll", "", "allMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "seeMap", "haveVidMap", "freeSeeMap", "initSeeAndAll2", "initWaitPlayList", "obj_id", "title", "current_ducation", "type", "isAllVideoList", "item", "isDownload", "videoId", "isWaitPush", "setSeeByVid", "setSee", "setSeeByVidTreeNode", "chapterRootId", "timeIsCurrentYear", "timeStr", "treeNodeIsWaitPush", "allSelect", "haveSecondPresent", "isBottomRadiusItem", "isFirstItem", "isNight", "Lcom/psychiatrygarden/utils/SkinManager;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "isOnlyOneItem", "isSecondList_last", "isTopRadiusItem", "noOneSelect", "treeNodeEquals", "treeNode2", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTreeNodeUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TreeNodeUtil.kt\ncom/psychiatrygarden/utils/TreeNodeUtilKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1293:1\n1855#2:1294\n1855#2,2:1295\n1856#2:1297\n1855#2:1298\n1855#2,2:1299\n1856#2:1301\n1855#2,2:1302\n*S KotlinDebug\n*F\n+ 1 TreeNodeUtil.kt\ncom/psychiatrygarden/utils/TreeNodeUtilKt\n*L\n105#1:1294\n107#1:1295,2\n105#1:1297\n131#1:1298\n133#1:1299,2\n131#1:1301\n812#1:1302,2\n*E\n"})
/* loaded from: classes6.dex */
public final class TreeNodeUtilKt {
    public static final boolean allSelect(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getChildren() == null || treeNode.getChildren().size() <= 0) {
            return false;
        }
        List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "children");
        Iterator<T> it = children.iterator();
        while (it.hasNext()) {
            TreeNode treeNode2 = (TreeNode) it.next();
            if (treeNode2.getChildren() != null && treeNode2.getChildren().size() > 0) {
                List children2 = treeNode2.getChildren();
                Intrinsics.checkNotNullExpressionValue(children2, "treeNode.children");
                Iterator it2 = children2.iterator();
                while (it2.hasNext()) {
                    if (!((CourseDirectoryTreeBean) ((TreeNode) it2.next()).getValue()).isSelect()) {
                        return false;
                    }
                }
            } else if (!((CourseDirectoryTreeBean) treeNode2.getValue()).isSelect()) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00cf  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.util.List<com.psychiatrygarden.widget.treenode.TreeNode<com.psychiatrygarden.bean.CourseDirectoryTreeBean>> courseDirectoryItemToTreeNodeData(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r17) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.courseDirectoryItemToTreeNodeData(java.util.List):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f8  */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v7, types: [boolean, int] */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.util.List<com.psychiatrygarden.widget.treenode.TreeNode<com.psychiatrygarden.bean.CourseDirectoryTreeBean>> courseDirectoryItemToTreeNodeData2(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r17, @org.jetbrains.annotations.Nullable java.lang.String r18, @org.jetbrains.annotations.Nullable java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.courseDirectoryItemToTreeNodeData2(java.util.List, java.lang.String, java.lang.String):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00fe  */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v7, types: [boolean, int] */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.util.List<com.psychiatrygarden.widget.treenode.TreeNode<com.psychiatrygarden.bean.CourseDirectoryTreeBean>> courseDirectoryItemToTreeNodeData3(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r17, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r18, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> r19) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.courseDirectoryItemToTreeNodeData3(java.util.List, java.util.List, java.util.List):java.util.List");
    }

    @NotNull
    public static final List<String> getChapterId(@NotNull List<? extends CourseDirectoryItemData> data, @NotNull String chapterId) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        ArrayList arrayList = new ArrayList();
        int size = data.size();
        for (int i2 = 0; i2 < size; i2++) {
            CourseDirectoryItemData courseDirectoryItemData = data.get(i2);
            String rootId = courseDirectoryItemData.getChapter_id();
            if (Intrinsics.areEqual(rootId, chapterId)) {
                arrayList.add(chapterId);
                arrayList.add("");
                return arrayList;
            }
            if (courseDirectoryItemData.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getChildren(), "itemData.children");
                if (!r6.isEmpty()) {
                    List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                    int size2 = children.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        if (Intrinsics.areEqual(children.get(i3).getChapter_id(), chapterId)) {
                            Intrinsics.checkNotNullExpressionValue(rootId, "rootId");
                            arrayList.add(rootId);
                            arrayList.add(chapterId);
                            return arrayList;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return new ArrayList();
    }

    @NotNull
    public static final List<String> getChapterIdByVid(@NotNull String updateVid, @NotNull List<? extends CourseDirectoryItemData> dataList) {
        Intrinsics.checkNotNullParameter(updateVid, "updateVid");
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(updateVid)) {
            return arrayList;
        }
        int size = dataList.size();
        for (int i2 = 0; i2 < size; i2++) {
            CourseDirectoryItemData courseDirectoryItemData = dataList.get(i2);
            if (courseDirectoryItemData.getContent() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getContent(), "itemData.content");
                if (!r5.isEmpty()) {
                    int size2 = courseDirectoryItemData.getContent().size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        if (courseDirectoryItemData.getContent().get(i3).getVid() != null && Intrinsics.areEqual(updateVid, courseDirectoryItemData.getContent().get(i3).getVid())) {
                            String chapter_id = courseDirectoryItemData.getChapter_id();
                            Intrinsics.checkNotNullExpressionValue(chapter_id, "itemData.chapter_id");
                            arrayList.add(chapter_id);
                        }
                    }
                    return arrayList;
                }
            }
            if (courseDirectoryItemData.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getChildren(), "itemData.children");
                if (!r5.isEmpty()) {
                    List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                    int size3 = children.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        CourseDirectoryItemData courseDirectoryItemData2 = children.get(i4);
                        if (courseDirectoryItemData2.getContent() != null) {
                            Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData2.getContent(), "childItem.content");
                            if (!r10.isEmpty()) {
                                int size4 = courseDirectoryItemData2.getContent().size();
                                for (int i5 = 0; i5 < size4; i5++) {
                                    CourseDirectoryContentItem courseDirectoryContentItem = courseDirectoryItemData2.getContent().get(i5);
                                    if (courseDirectoryContentItem.getVid() != null && Intrinsics.areEqual(updateVid, courseDirectoryContentItem.getVid())) {
                                        String chapter_id2 = courseDirectoryItemData.getChapter_id();
                                        Intrinsics.checkNotNullExpressionValue(chapter_id2, "itemData.chapter_id");
                                        arrayList.add(chapter_id2);
                                        String chapter_id3 = courseDirectoryItemData2.getChapter_id();
                                        Intrinsics.checkNotNullExpressionValue(chapter_id3, "childItem.chapter_id");
                                        arrayList.add(chapter_id3);
                                        return arrayList;
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return arrayList;
    }

    @NotNull
    public static final List<String> getChapterIdByVideoId(@NotNull List<? extends CourseDirectoryItemData> list, @Nullable String str, @Nullable String str2) {
        List<? extends CourseDirectoryItemData> data = list;
        Intrinsics.checkNotNullParameter(data, "data");
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            Intrinsics.checkNotNull(str);
            if (StringsKt__StringsKt.trim((CharSequence) str).toString().length() != 0) {
                int size = list.size();
                String str3 = "";
                int i2 = 0;
                while (i2 < size) {
                    CourseDirectoryItemData courseDirectoryItemData = data.get(i2);
                    String chapter_id = courseDirectoryItemData.getChapter_id();
                    Intrinsics.checkNotNullExpressionValue(chapter_id, "itemData.chapter_id");
                    if (courseDirectoryItemData.getChildren() != null) {
                        Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getChildren(), "itemData.children");
                        if (!r11.isEmpty()) {
                            List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                            int size2 = children.size();
                            int i3 = 0;
                            while (i3 < size2) {
                                CourseDirectoryItemData courseDirectoryItemData2 = children.get(i3);
                                String chapter_id2 = courseDirectoryItemData2.getChapter_id();
                                Intrinsics.checkNotNullExpressionValue(chapter_id2, "childItem.chapter_id");
                                List<CourseDirectoryContentItem> content = courseDirectoryItemData2.getContent();
                                if (content != null && (!content.isEmpty())) {
                                    int size3 = content.size();
                                    for (int i4 = 0; i4 < size3; i4++) {
                                        if (TextUtils.isEmpty(str2) || Intrinsics.areEqual("0", str2)) {
                                            if (str != null && content.get(i4).getVid() != null && Intrinsics.areEqual(str, content.get(i4).getVid())) {
                                                arrayList.add(chapter_id);
                                                arrayList.add(chapter_id2);
                                            }
                                        } else if (str != null && content.get(i4).getVid() != null && Intrinsics.areEqual(str, content.get(i4).getVid()) && Intrinsics.areEqual(str2, chapter_id2)) {
                                            arrayList.add(chapter_id);
                                            arrayList.add(chapter_id2);
                                        }
                                    }
                                }
                                i3++;
                                str3 = chapter_id2;
                            }
                        }
                    }
                    if (courseDirectoryItemData.getContent() != null) {
                        Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getContent(), "itemData.content");
                        if (!r0.isEmpty()) {
                            int size4 = courseDirectoryItemData.getContent().size();
                            for (int i5 = 0; i5 < size4; i5++) {
                                if (TextUtils.isEmpty(str2) || Intrinsics.areEqual("0", str2)) {
                                    if (courseDirectoryItemData.getContent().get(i5).getVid() != null && Intrinsics.areEqual(str, courseDirectoryItemData.getContent().get(i5).getVid())) {
                                        arrayList.add(chapter_id);
                                        arrayList.add(str3);
                                    }
                                } else if (courseDirectoryItemData.getContent().get(i5).getVid() != null && Intrinsics.areEqual(str, courseDirectoryItemData.getContent().get(i5).getVid()) && Intrinsics.areEqual(chapter_id, str2)) {
                                    arrayList.add(chapter_id);
                                    arrayList.add(str3);
                                }
                            }
                        }
                    }
                    i2++;
                    data = list;
                }
                return arrayList;
            }
        }
        arrayList.add("0");
        arrayList.add("0");
        return arrayList;
    }

    @NotNull
    public static final List<String> getCourseTitleFromDirectory(@NotNull List<? extends CourseDirectoryItemData> dataList) {
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        ArrayList arrayList = new ArrayList();
        if (dataList.size() == 1 && isAllVideoList(dataList.get(0))) {
            List<CourseDirectoryContentItem> content = dataList.get(0).getContent();
            if (content.size() <= 0) {
                arrayList.add("");
                arrayList.add("");
                return arrayList;
            }
            String title = content.get(0).getCourse_title();
            String cover = content.get(0).getCourse_cover();
            Intrinsics.checkNotNullExpressionValue(title, "title");
            arrayList.add(title);
            Intrinsics.checkNotNullExpressionValue(cover, "cover");
            arrayList.add(cover);
            return arrayList;
        }
        int size = dataList.size();
        for (int i2 = 0; i2 < size; i2++) {
            CourseDirectoryItemData courseDirectoryItemData = dataList.get(i2);
            List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
            if (children == null || !(!children.isEmpty())) {
                List<CourseDirectoryContentItem> content2 = courseDirectoryItemData.getContent();
                if (content2.size() > 0) {
                    String title2 = content2.get(0).getCourse_title();
                    String cover2 = content2.get(0).getCourse_cover();
                    Intrinsics.checkNotNullExpressionValue(title2, "title");
                    arrayList.add(title2);
                    Intrinsics.checkNotNullExpressionValue(cover2, "cover");
                    arrayList.add(cover2);
                    return arrayList;
                }
            } else {
                int size2 = children.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    List<CourseDirectoryContentItem> content3 = children.get(i3).getContent();
                    if (content3.size() > 0) {
                        String title3 = content3.get(0).getCourse_title();
                        String cover3 = content3.get(0).getCourse_cover();
                        Intrinsics.checkNotNullExpressionValue(title3, "title");
                        arrayList.add(title3);
                        Intrinsics.checkNotNullExpressionValue(cover3, "cover");
                        arrayList.add(cover3);
                        return arrayList;
                    }
                }
            }
        }
        arrayList.add("");
        arrayList.add("");
        return arrayList;
    }

    private static final int getHaveVidNum(List<? extends CourseDirectoryContentItem> list) {
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (!TextUtils.isEmpty(list.get(i3).getVid())) {
                i2++;
            }
        }
        return i2;
    }

    @NotNull
    public static final String[] getLivingDate(@Nullable String str, @Nullable String str2) {
        String[] strArr = {"", ""};
        if (!(str == null || str.length() == 0)) {
            if (!(str2 == null || str2.length() == 0)) {
                long j2 = Long.parseLong(str);
                long j3 = Long.parseLong(str2);
                String longTimeStampToString = !timeIsCurrentYear(str) ? DateUtilKt.formatLongTimeStampToString(1000 * j2, DatePattern.CHINESE_DATE_PATTERN) : DateUtilKt.formatLongTimeStampToString(1000 * j2, "MM月dd日");
                long j4 = 1000;
                String str3 = DateUtilKt.formatLongTimeStampToString(j2 * j4, DateUtil.TIME_FORMAT_WITH_HM) + CharPool.DASHED + DateUtilKt.formatLongTimeStampToString(j3 * j4, DateUtil.TIME_FORMAT_WITH_HM);
                strArr[0] = longTimeStampToString;
                strArr[1] = str3;
            }
        }
        return strArr;
    }

    @NotNull
    public static final LiveStatus getLivingStatus(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            Intrinsics.checkNotNull(str);
            long j2 = Long.parseLong(str);
            Intrinsics.checkNotNull(str2);
            long j3 = Long.parseLong(str2);
            if (jCurrentTimeMillis < j2) {
                return LiveStatus.NO_BEGIN;
            }
            if (jCurrentTimeMillis < j3) {
                return LiveStatus.LIVING;
            }
            if (TextUtils.isEmpty(str3) || Intrinsics.areEqual("0", str3)) {
                return LiveStatus.CUTTING;
            }
            if (!TextUtils.isEmpty(str3)) {
                return LiveStatus.HAVE_VID;
            }
        }
        return LiveStatus.NO_BEGIN;
    }

    private static final boolean getPercent90(String str, String str2) {
        if (str == null || str2 == null || Intrinsics.areEqual("0", str2) || Intrinsics.areEqual("0", str) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        double d3 = (Double.parseDouble(str) / Double.parseDouble(str2)) * 100;
        if (d3 < 0.01d) {
            d3 = 0.01d;
        }
        if (d3 > 100.0d) {
            d3 = 100.0d;
        }
        return d3 > 90.0d;
    }

    @NotNull
    public static final String getPercentStr(@Nullable String str, @Nullable String str2, boolean z2) {
        if (str == null || str2 == null || Intrinsics.areEqual("0", str2) || Intrinsics.areEqual("0", str) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "未学习";
        }
        double d3 = (Double.parseDouble(str) / Double.parseDouble(str2)) * 100;
        if (d3 < 0.01d) {
            d3 = 0.01d;
        }
        if ((z2 && d3 > 90.0d) || d3 >= 100.0d) {
            return "已完成";
        }
        if (d3 >= 1.0d) {
            return "已学习" + ((int) d3) + '%';
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str3 = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(d3)}, 1));
        Intrinsics.checkNotNullExpressionValue(str3, "format(format, *args)");
        return "已学习" + str3 + '%';
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final int getPlayingPosition(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r20, @org.jetbrains.annotations.Nullable java.lang.String r21, @org.jetbrains.annotations.Nullable java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.getPlayingPosition(java.util.List, java.lang.String, java.lang.String):int");
    }

    public static final int getPositionByChapterId(@NotNull List<? extends CourseDirectoryItemData> data, @Nullable String str) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int size = data.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            CourseDirectoryItemData courseDirectoryItemData = data.get(i3);
            String chapter_id = courseDirectoryItemData.getChapter_id();
            if (!TextUtils.isEmpty(chapter_id)) {
                i2++;
            }
            if (Intrinsics.areEqual(chapter_id, str)) {
                return i2 - 1;
            }
            if (courseDirectoryItemData.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getChildren(), "itemData.children");
                if (!r5.isEmpty()) {
                    List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                    int size2 = children.size();
                    int i4 = 0;
                    for (int i5 = 0; i5 < size2; i5++) {
                        i4++;
                        if (Intrinsics.areEqual(children.get(i5).getChapter_id(), str)) {
                            return (i4 + i2) - 1;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    @NotNull
    public static final String getTreeNodeWaitPushTimeStr(@NotNull TreeNode<CourseDirectoryTreeBean> data) {
        TreeNode<CourseDirectoryTreeBean> parent;
        TreeNode<CourseDirectoryTreeBean> parent2;
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getCustomerLevel() == 0) {
            String pushTime = data.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(pushTime, "pushTime");
            return pushTime;
        }
        if (data.getCustomerLevel() == 1 && (parent2 = data.getParent()) != null) {
            String pushTimeParent = parent2.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(pushTimeParent, "pushTimeParent");
            if (isWaitPush(pushTimeParent)) {
                String publish_time = parent2.getValue().getItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time, "it.value.item.publish_time");
                return publish_time;
            }
            String publish_time2 = data.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(publish_time2, "data.value.item.publish_time");
            return publish_time2;
        }
        if (data.getCustomerLevel() != 2) {
            return "";
        }
        TreeNode<CourseDirectoryTreeBean> parent3 = data.getParent();
        if (parent3 == null) {
            String publish_time3 = data.getValue().getContentItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(publish_time3, "data.value.contentItem.publish_time");
            return publish_time3;
        }
        if (parent3.getCustomerLevel() == 0) {
            String publish_time4 = parent3.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(publish_time4, "it.value.item.publish_time");
            if (isWaitPush(publish_time4)) {
                String publish_time5 = parent3.getValue().getItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time5, "{\n                      …ime\n                    }");
                return publish_time5;
            }
            String publish_time6 = data.getValue().getContentItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(publish_time6, "{\n                      …ime\n                    }");
            return publish_time6;
        }
        if (parent3.getCustomerLevel() != 1 || (parent = parent3.getParent()) == null) {
            return "";
        }
        String publish_time7 = parent.getValue().getItem().getPublish_time();
        Intrinsics.checkNotNullExpressionValue(publish_time7, "rootParent.value.item.publish_time");
        boolean zIsWaitPush = isWaitPush(publish_time7);
        String publish_time8 = parent3.getValue().getItem().getPublish_time();
        Intrinsics.checkNotNullExpressionValue(publish_time8, "it.value.item.publish_time");
        boolean zIsWaitPush2 = isWaitPush(publish_time8);
        if (zIsWaitPush) {
            String publish_time9 = parent.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(publish_time9, "rootParent.value.item.publish_time");
            return publish_time9;
        }
        if (zIsWaitPush2) {
            parent3.getValue().getItem().getPublish_time();
        }
        String publish_time10 = data.getValue().getContentItem().getPublish_time();
        Intrinsics.checkNotNullExpressionValue(publish_time10, "data.value.contentItem.publish_time");
        return publish_time10;
    }

    @NotNull
    public static final String getWaitPushTimeFormat(@Nullable String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        try {
            return timeIsCurrentYear(str) ? DateUtilKt.formatLongTimeStampToString(Long.parseLong(str) * 1000, DateUtil.TIME_FORMAT_MONTH_DAY_HM) : DateUtilKt.formatLongTimeStampToString(Long.parseLong(str) * 1000, "yyyy-MM-dd HH:mm");
        } catch (Exception e2) {
            String message = e2.getMessage();
            Intrinsics.checkNotNull(message);
            Log.e("Error", message);
            return "";
        }
    }

    public static final boolean haveSecondPresent(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getParent() == null) {
            return false;
        }
        return !treeNode.getParent().isRoot();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x021b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void initSeeAndAll(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r27, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r28, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r29, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r30, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.Boolean> r31) {
        /*
            Method dump skipped, instructions count: 816
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.initSeeAndAll(java.util.List, java.util.HashMap, java.util.HashMap, java.util.HashMap, java.util.HashMap):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x022f A[SYNTHETIC] */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.util.List<java.lang.Integer> initSeeAndAll2(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r30, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r31, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r32, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.String> r33, @org.jetbrains.annotations.NotNull java.util.HashMap<java.lang.String, java.lang.Boolean> r34) {
        /*
            Method dump skipped, instructions count: 860
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.initSeeAndAll2(java.util.List, java.util.HashMap, java.util.HashMap, java.util.HashMap, java.util.HashMap):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void initWaitPlayList(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.CourseDirectoryItemData> r17, @org.jetbrains.annotations.NotNull java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.TreeNodeUtilKt.initWaitPlayList(java.util.List, java.lang.String):void");
    }

    public static /* synthetic */ void initWaitPlayList$default(String str, String str2, String str3, String str4, String str5, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            str5 = "";
        }
        initWaitPlayList(str, str2, str3, str4, str5);
    }

    private static final boolean isAllVideoList(CourseDirectoryItemData courseDirectoryItemData) {
        return TextUtils.isEmpty(courseDirectoryItemData.getChapter_id());
    }

    public static final boolean isBottomRadiusItem(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(children.size() - 1));
    }

    public static final boolean isDownload(@Nullable String str) {
        if (str == null) {
            return false;
        }
        int size = YkBManager.getInstance().mDownloadMediaLists.size();
        for (int i2 = 0; i2 < size; i2++) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = YkBManager.getInstance().mDownloadMediaLists.get(i2);
            String vid = aliyunDownloadMediaInfo.getVid();
            System.out.println((Object) ("download  vis: " + vid + " -----  videoId: " + str));
            if (Intrinsics.areEqual(str, vid) && (aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete || aliyunDownloadMediaInfo.getProgress() == 100)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isFirstItem(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        List<TreeNode<CourseDirectoryTreeBean>> children;
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        TreeNode<CourseDirectoryTreeBean> parent = treeNode.getParent();
        if (parent == null || (children = parent.getChildren()) == null || children.size() < 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(0));
    }

    public static final boolean isNight(@NotNull SkinManager skinManager, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(skinManager, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return SkinManager.getCurrentSkinType(context) == 1;
    }

    public static final boolean isOnlyOneItem(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getParent().getChildren();
        return children != null && children.size() == 1;
    }

    public static final boolean isSecondList_last(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getParent() == null) {
            return false;
        }
        try {
            if (treeNode.getParent().getCustomerLevel() == 0 && treeNode.getCustomerLevel() == 1) {
                List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getParent().getChildren();
                if (children.size() > 0 && Intrinsics.areEqual(treeNode.getValue().getItem().getChapter_id(), children.get(children.size() - 1).getValue().getItem().getChapter_id())) {
                    if (Intrinsics.areEqual(treeNode.getValue().getItem().getCourse_id(), children.get(children.size() - 1).getValue().getItem().getCourse_id())) {
                        return true;
                    }
                }
            }
        } catch (Exception e2) {
            System.out.println((Object) e2.getMessage());
        }
        return false;
    }

    public static final boolean isTopRadiusItem(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getParent().getChildren();
        if (children == null || children.size() <= 1) {
            return false;
        }
        return treeNodeEquals(treeNode, children.get(0));
    }

    private static final boolean isWaitPush(String str) {
        return !TextUtils.isEmpty(str) && Long.parseLong(str) * ((long) 1000) > System.currentTimeMillis();
    }

    public static final boolean noOneSelect(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
            List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
            Intrinsics.checkNotNullExpressionValue(children, "children");
            Iterator<T> it = children.iterator();
            while (it.hasNext()) {
                TreeNode treeNode2 = (TreeNode) it.next();
                if (treeNode2.getChildren() != null && treeNode2.getChildren().size() > 0) {
                    List children2 = treeNode2.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children2, "treeNode.children");
                    Iterator it2 = children2.iterator();
                    while (it2.hasNext()) {
                        if (((CourseDirectoryTreeBean) ((TreeNode) it2.next()).getValue()).isSelect()) {
                            return false;
                        }
                    }
                } else if (((CourseDirectoryTreeBean) treeNode2.getValue()).isSelect()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final void setSeeByVid(@NotNull List<? extends CourseDirectoryItemData> data, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(data, "data");
        int size = data.size();
        for (int i2 = 0; i2 < size; i2++) {
            CourseDirectoryItemData courseDirectoryItemData = data.get(i2);
            if (courseDirectoryItemData.getChildren() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getChildren(), "itemData.children");
                if (!r4.isEmpty()) {
                    List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                    int size2 = children.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        List<CourseDirectoryContentItem> content = children.get(i3).getContent();
                        if (content != null && (!content.isEmpty())) {
                            int size3 = content.size();
                            for (int i4 = 0; i4 < size3; i4++) {
                                if (str != null && content.get(i4).getVid() != null && Intrinsics.areEqual(str, content.get(i4).getVid())) {
                                    content.get(i4).setSee(str2);
                                }
                            }
                        }
                    }
                }
            }
            if (courseDirectoryItemData.getContent() != null) {
                Intrinsics.checkNotNullExpressionValue(courseDirectoryItemData.getContent(), "itemData.content");
                if (!r4.isEmpty()) {
                    int size4 = courseDirectoryItemData.getContent().size();
                    for (int i5 = 0; i5 < size4; i5++) {
                        if (courseDirectoryItemData.getContent().get(i5).getVid() != null && Intrinsics.areEqual(str, courseDirectoryItemData.getContent().get(i5).getVid())) {
                            courseDirectoryItemData.getContent().get(i5).setSee(str2);
                        }
                    }
                }
            }
        }
    }

    public static final void setSeeByVidTreeNode(@NotNull List<? extends TreeNode<CourseDirectoryTreeBean>> data, @Nullable String str, @Nullable String str2, @NotNull String chapterRootId, @NotNull String chapterId) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(chapterRootId, "chapterRootId");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        int size = data.size();
        for (int i2 = 0; i2 < size; i2++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = data.get(i2);
            boolean z2 = true;
            if (treeNode.getCustomerLevel() == 0) {
                List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
                List<TreeNode<CourseDirectoryTreeBean>> list = children;
                if (list == null || list.isEmpty()) {
                    continue;
                } else {
                    int size2 = children.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        TreeNode<CourseDirectoryTreeBean> treeNode2 = children.get(i3);
                        if (treeNode2.getCustomerLevel() == 2) {
                            if (!(str == null || str.length() == 0) && Intrinsics.areEqual(str, treeNode2.getValue().getContentItem().getVid())) {
                                treeNode2.getValue().getContentItem().setSee(str2);
                                return;
                            }
                        } else if (treeNode2.getCustomerLevel() == 1) {
                            List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode2.getChildren();
                            List<TreeNode<CourseDirectoryTreeBean>> list2 = children2;
                            if (list2 == null || list2.isEmpty()) {
                                continue;
                            } else {
                                int size3 = children2.size();
                                for (int i4 = 0; i4 < size3; i4++) {
                                    TreeNode<CourseDirectoryTreeBean> treeNode3 = children2.get(i4);
                                    if (treeNode3.getCustomerLevel() == 2) {
                                        if (!(str == null || str.length() == 0) && Intrinsics.areEqual(str, treeNode3.getValue().getContentItem().getVid())) {
                                            treeNode3.getValue().getContentItem().setSee(str2);
                                            return;
                                        }
                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } else if (treeNode.getCustomerLevel() != 2) {
                continue;
            } else {
                if (str != null && str.length() != 0) {
                    z2 = false;
                }
                if (!z2 && Intrinsics.areEqual(str, treeNode.getValue().getContentItem().getVid())) {
                    treeNode.getValue().getContentItem().setSee(str2);
                    return;
                }
            }
        }
    }

    public static /* synthetic */ void setSeeByVidTreeNode$default(List list, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            str3 = "";
        }
        if ((i2 & 16) != 0) {
            str4 = "";
        }
        setSeeByVidTreeNode(list, str, str2, str3, str4);
    }

    public static final boolean timeIsCurrentYear(@Nullable String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return Intrinsics.areEqual(DateUtilKt.formatLongTimeStampToString(System.currentTimeMillis(), DatePattern.NORM_YEAR_PATTERN), DateUtilKt.formatLongTimeStampToString(Long.parseLong(str) * 1000, DatePattern.NORM_YEAR_PATTERN));
    }

    public static final boolean treeNodeEquals(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode, @Nullable TreeNode<CourseDirectoryTreeBean> treeNode2) {
        Intrinsics.checkNotNullParameter(treeNode, "<this>");
        if (treeNode2 == null) {
            return false;
        }
        boolean z2 = (treeNode2.getValue().getItem() == null || TextUtils.isEmpty(treeNode2.getValue().getItem().getChapter_id())) ? false : true;
        boolean z3 = (treeNode2.getValue().getContentItem() == null || TextUtils.isEmpty(treeNode2.getValue().getContentItem().getObj_id())) ? false : true;
        boolean z4 = (treeNode.getValue().getContentItem() == null || TextUtils.isEmpty(treeNode.getValue().getContentItem().getObj_id())) ? false : true;
        if (((treeNode.getValue().getItem() == null || TextUtils.isEmpty(treeNode.getValue().getItem().getChapter_id())) ? false : true) && z2) {
            return Intrinsics.areEqual(treeNode2.getValue().getItem().getChapter_id(), treeNode.getValue().getItem().getChapter_id());
        }
        if (z3 && z4) {
            return Intrinsics.areEqual(treeNode2.getValue().getContentItem().getObj_id(), treeNode.getValue().getContentItem().getObj_id());
        }
        return false;
    }

    public static final boolean treeNodeIsWaitPush(@NotNull TreeNode<CourseDirectoryTreeBean> data) {
        TreeNode<CourseDirectoryTreeBean> parent;
        TreeNode<CourseDirectoryTreeBean> parent2;
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getCustomerLevel() == 0) {
            String pushTime = data.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(pushTime, "pushTime");
            return isWaitPush(pushTime);
        }
        if (data.getCustomerLevel() == 1 && (parent2 = data.getParent()) != null) {
            String pushTimeParent = parent2.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(pushTimeParent, "pushTimeParent");
            if (isWaitPush(pushTimeParent)) {
                return true;
            }
            String pushTime2 = data.getValue().getItem().getPublish_time();
            Intrinsics.checkNotNullExpressionValue(pushTime2, "pushTime");
            return isWaitPush(pushTime2);
        }
        if (data.getCustomerLevel() == 2) {
            TreeNode<CourseDirectoryTreeBean> parent3 = data.getParent();
            if (parent3 == null) {
                String publish_time = data.getValue().getContentItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time, "data.value.contentItem.publish_time");
                return isWaitPush(publish_time);
            }
            if (parent3.getCustomerLevel() == 0) {
                String publish_time2 = parent3.getValue().getItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time2, "it.value.item.publish_time");
                if (isWaitPush(publish_time2)) {
                    return true;
                }
                String publish_time3 = data.getValue().getContentItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time3, "data.value.contentItem.publish_time");
                return isWaitPush(publish_time3);
            }
            if (parent3.getCustomerLevel() == 1 && (parent = parent3.getParent()) != null) {
                String publish_time4 = parent.getValue().getItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time4, "rootParent.value.item.publish_time");
                boolean zIsWaitPush = isWaitPush(publish_time4);
                String publish_time5 = parent3.getValue().getItem().getPublish_time();
                Intrinsics.checkNotNullExpressionValue(publish_time5, "it.value.item.publish_time");
                boolean zIsWaitPush2 = isWaitPush(publish_time5);
                if (!zIsWaitPush && !zIsWaitPush2) {
                    String publish_time6 = data.getValue().getContentItem().getPublish_time();
                    Intrinsics.checkNotNullExpressionValue(publish_time6, "data.value.contentItem.publish_time");
                    return isWaitPush(publish_time6);
                }
            }
        }
        return true;
    }

    public static final void initWaitPlayList(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !Intrinsics.areEqual("0", str2)) {
            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = new CurriculumScheduleCardBean.DataDTO.ChildrenDTO();
            childrenDTO.setId(str);
            childrenDTO.setVid(str2);
            childrenDTO.setTitle(str3);
            childrenDTO.setVideo_type(str5);
            if (TextUtils.isEmpty(str4)) {
                childrenDTO.setSee("0");
            } else {
                childrenDTO.setSee(str4);
            }
            arrayList.add(childrenDTO);
        }
        ProjectApp.mPlayerVideo.clear();
        ProjectApp.mPlayerVideo.addAll(arrayList);
    }
}
