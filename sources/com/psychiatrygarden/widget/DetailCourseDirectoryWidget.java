package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.event.HaveFreeWatchEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.CourseDataSpUtilKt;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LiveStatus;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.DetailCourseDirectoryWidget;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.ToastUtilsKt;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010D\u001a\u00020\u00142\u0006\u0010E\u001a\u00020\u00132\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\f0&H\u0002J\u001e\u0010G\u001a\u00020\u00142\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\f0&2\u0006\u0010E\u001a\u00020\u0013H\u0002J\b\u0010H\u001a\u00020\u0014H\u0002J\u0010\u0010I\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020JH\u0016J\b\u0010K\u001a\u00020\u0014H\u0002J\u0016\u0010L\u001a\u00020\u00142\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J@\u0010M\u001a\u00020\u00142\u0006\u0010N\u001a\u00020\u000f2\u0006\u0010O\u001a\u00020\u000f2\b\b\u0002\u0010P\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020(2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010Q\u001a\u00020(JJ\u0010R\u001a\u00020\u00142\u0006\u0010N\u001a\u00020\u000f2\u0006\u0010O\u001a\u00020\u000f2\b\b\u0002\u0010P\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020(2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010Q\u001a\u00020(2\b\b\u0002\u0010S\u001a\u00020(J\b\u0010T\u001a\u00020(H\u0002J\b\u0010U\u001a\u00020\u0014H\u0002J\u001e\u0010V\u001a\u00020\u00142\u0006\u0010W\u001a\u00020X2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0&H\u0002J2\u0010Y\u001a\u00020\u00142*\u0010Z\u001a&\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012J\u001c\u0010[\u001a\u00020\u00142\b\b\u0002\u0010\\\u001a\u00020(2\b\b\u0002\u0010]\u001a\u00020(H\u0002J\u0014\u0010^\u001a\u00020\u00142\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001cJ\u0016\u0010`\u001a\u00020\u00142\f\u0010a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001cH\u0002J\u0016\u0010b\u001a\u00020\u00142\u0006\u0010c\u001a\u00020\u000f2\u0006\u0010d\u001a\u00020\u000fJ\u0016\u0010e\u001a\u00020\u00142\u0006\u0010c\u001a\u00020\u000f2\u0006\u0010d\u001a\u00020\u000fR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082.¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000ej\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u0011\u001a&\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010'\u001a\u001e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020(0\u000ej\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020(`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010*\u001a\u001e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000ej\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0&0\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u00105\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082.¢\u0006\u0002\n\u0000R\u0016\u00108\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R*\u00109\u001a\u001e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000ej\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010:\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001c\u0010?\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010<\"\u0004\bA\u0010>R\u000e\u0010B\u001a\u00020CX\u0082.¢\u0006\u0002\n\u0000¨\u0006f"}, d2 = {"Lcom/psychiatrygarden/widget/DetailCourseDirectoryWidget;", "Landroid/widget/LinearLayout;", "Lcom/psychiatrygarden/widget/BaseContentWidget;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "adapter", "Lcom/psychiatrygarden/widget/treenode/TreeNodeAdapter;", "Lcom/psychiatrygarden/bean/CourseDirectoryTreeBean;", "allMap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "clickPlayListener2", "Lkotlin/Function5;", "Lcom/psychiatrygarden/bean/CourseDirectoryContentItem;", "", "getClickPlayListener2", "()Lkotlin/jvm/functions/Function5;", "setClickPlayListener2", "(Lkotlin/jvm/functions/Function5;)V", "courseType", "currentVid", "data", "", "Lcom/psychiatrygarden/bean/CourseDirectoryItemData;", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "expandFirstId", "expandFirstIdList", "", "expandSecondId", "expandSecondIdList", "firstExpandTree", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "freeSeeMap", "", "havePermission", "haveVidMap", "landscapeScreen", "lastLearnChapterId", "lastLearnVid", "lastVideoIdChapter", "listData", "mCourseId", "mFoldDire", CourseDataSpUtilKt.playingChapterKey, "playingVid", "positionScrollTo", "preClickTree", "rvCourseDirectory", "Landroidx/recyclerview/widget/RecyclerView;", "secondExpandTree", "seeMap", "setSeeDuration", "getSetSeeDuration", "()Ljava/lang/String;", "setSetSeeDuration", "(Ljava/lang/String;)V", "setVid", "getSetVid", "setSetVid", "viewGap", "Landroid/view/View;", "changeClickBg", "itemData", "treeNode", "clickCallBack", "getListData", com.umeng.socialize.tracker.a.f23806c, "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "initLastVideId", "initListLayout", "initParams", "courseId", "type", "fromViewPlayPage", "showTitle", "initParams2", "foldDire", "isNightTheme", "sendFoldExpandMessage", "setArrowSpin", "helper", "Lcom/psychiatrygarden/widget/treenode/ViewHolder;", "setClickListenerCallBack", "action", "setEmptyView", "nullData", "errorData", "updateDownloadedFlag", "vidList", "updateTreeNode", "listVid", "updateVideoProgress", "vid", "seeDuration", "updateVideoProgressLand", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailCourseDirectoryWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseDirectoryWidget.kt\ncom/psychiatrygarden/widget/DetailCourseDirectoryWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1386:1\n1855#2:1387\n1855#2:1388\n1855#2,2:1389\n1856#2:1391\n1856#2:1392\n*S KotlinDebug\n*F\n+ 1 DetailCourseDirectoryWidget.kt\ncom/psychiatrygarden/widget/DetailCourseDirectoryWidget\n*L\n1316#1:1387\n1327#1:1388\n1332#1:1389,2\n1327#1:1391\n1316#1:1392\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailCourseDirectoryWidget extends LinearLayout implements BaseContentWidget {
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;

    @NotNull
    private final HashMap<String, String> allMap;

    @NotNull
    private Function5<? super String, ? super String, ? super String, ? super String, ? super CourseDirectoryContentItem, Unit> clickPlayListener2;

    @NotNull
    private String courseType;

    @NotNull
    private String currentVid;

    @NotNull
    private List<? extends CourseDirectoryItemData> data;
    private CustomEmptyView emptyView;

    @NotNull
    private String expandFirstId;

    @NotNull
    private final List<String> expandFirstIdList;

    @NotNull
    private String expandSecondId;

    @NotNull
    private final List<String> expandSecondIdList;

    @Nullable
    private TreeNode<CourseDirectoryTreeBean> firstExpandTree;

    @NotNull
    private final HashMap<String, Boolean> freeSeeMap;
    private boolean havePermission;

    @NotNull
    private final HashMap<String, String> haveVidMap;
    private boolean landscapeScreen;

    @NotNull
    private String lastLearnChapterId;

    @NotNull
    private String lastLearnVid;

    @NotNull
    private List<String> lastVideoIdChapter;

    @NotNull
    private List<? extends TreeNode<CourseDirectoryTreeBean>> listData;

    @Nullable
    private String mCourseId;
    private boolean mFoldDire;

    @NotNull
    private String playingChapterId;

    @NotNull
    private String playingVid;
    private int positionScrollTo;

    @Nullable
    private TreeNode<CourseDirectoryTreeBean> preClickTree;
    private RecyclerView rvCourseDirectory;

    @Nullable
    private TreeNode<CourseDirectoryTreeBean> secondExpandTree;

    @NotNull
    private final HashMap<String, String> seeMap;

    @Nullable
    private String setSeeDuration;

    @Nullable
    private String setVid;
    private View viewGap;

    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016¨\u0006\r"}, d2 = {"com/psychiatrygarden/widget/DetailCourseDirectoryWidget$initListLayout$1", "Lcom/psychiatrygarden/widget/treenode/TreeNodeDelegate;", "Lcom/psychiatrygarden/bean/CourseDirectoryTreeBean;", "convert", "", "holder", "Lcom/psychiatrygarden/widget/treenode/ViewHolder;", "treeNode", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "getLayoutId", "", "isItemType", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$initListLayout$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06211 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        final /* synthetic */ int $colorCountLand;
        final /* synthetic */ int $colorFirst;
        final /* synthetic */ int $colorForth;
        final /* synthetic */ int $colorMainRed;
        final /* synthetic */ int $colorSecond;
        final /* synthetic */ int $colorSecondLineLand;
        final /* synthetic */ int $colorWaitLand;
        final /* synthetic */ int $colorWhiteLand;

        public C06211(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.$colorWaitLand = i2;
            this.$colorWhiteLand = i3;
            this.$colorForth = i4;
            this.$colorFirst = i5;
            this.$colorCountLand = i6;
            this.$colorSecond = i7;
            this.$colorSecondLineLand = i8;
            this.$colorMainRed = i9;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$1(TreeNode treeNode, DetailCourseDirectoryWidget this$0, C06211 this$1, View view) {
            Intrinsics.checkNotNullParameter(treeNode, "$treeNode");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                Context context = this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "context");
                ToastUtilsKt.toastOnUi$default(context, "暂无子章节", 0, 2, (Object) null);
                return;
            }
            if (this$0.firstExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(treeNode, this$0.firstExpandTree)) {
                TreeNode treeNode2 = this$0.firstExpandTree;
                Intrinsics.checkNotNull(treeNode2);
                treeNode2.isExpand();
            }
            this$1.adapter.expandOrCollapseTreeNode(treeNode);
            this$0.sendFoldExpandMessage();
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(@NotNull ViewHolder holder, @NotNull final TreeNode<CourseDirectoryTreeBean> treeNode) {
            RelativeLayout relativeLayout;
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(treeNode, "treeNode");
            RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.itemRootFirst);
            ImageView imageView = (ImageView) holder.getView(R.id.itemArrow);
            TextView textView = (TextView) holder.getView(R.id.itemFirstTitle);
            TextView textView2 = (TextView) holder.getView(R.id.itemTvFirstProgress);
            TextView textView3 = (TextView) holder.getView(R.id.itemFirstCount);
            TextView textView4 = (TextView) holder.getView(R.id.itemTvLaseLearn);
            TextView textView5 = (TextView) holder.getView(R.id.tvFreeSee);
            CourseDirectoryItemData item = treeNode.getValue().getItem();
            String chapter_id = item.getChapter_id();
            boolean zTreeNodeIsWaitPush = TreeNodeUtilKt.treeNodeIsWaitPush(treeNode);
            String waitPushTimeFormat = zTreeNodeIsWaitPush ? TreeNodeUtilKt.getWaitPushTimeFormat(TreeNodeUtilKt.getTreeNodeWaitPushTimeStr(treeNode)) : "";
            Boolean bool = (Boolean) DetailCourseDirectoryWidget.this.freeSeeMap.get(item.getChapter_id());
            boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
            textView.setText(treeNode.getValue().getItem().getTitle());
            if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                textView.setTextColor(zTreeNodeIsWaitPush ? this.$colorWaitLand : this.$colorWhiteLand);
            } else {
                textView.setTextColor(zTreeNodeIsWaitPush ? this.$colorForth : this.$colorFirst);
            }
            textView5.setVisibility((!zBooleanValue || DetailCourseDirectoryWidget.this.landscapeScreen) ? 8 : 0);
            if (TextUtils.isEmpty(chapter_id)) {
                relativeLayout = relativeLayout2;
            } else {
                String str = (String) DetailCourseDirectoryWidget.this.allMap.get(chapter_id);
                String str2 = (String) DetailCourseDirectoryWidget.this.seeMap.get(chapter_id);
                relativeLayout = relativeLayout2;
                String str3 = (String) DetailCourseDirectoryWidget.this.haveVidMap.get(chapter_id);
                List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
                imageView.setVisibility(children == null || children.isEmpty() ? 8 : 0);
                if (Intrinsics.areEqual("0", str)) {
                    textView3.setVisibility(8);
                    textView2.setVisibility(8);
                } else {
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        textView3.setText(str2 + '/' + str);
                    }
                    textView3.setVisibility(0);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        textView3.setTextColor(zTreeNodeIsWaitPush ? this.$colorWaitLand : this.$colorCountLand);
                    } else {
                        textView3.setTextColor(zTreeNodeIsWaitPush ? this.$colorForth : this.$colorSecond);
                    }
                    if (!DetailCourseDirectoryWidget.this.landscapeScreen) {
                        textView2.setVisibility(8);
                    } else if (zTreeNodeIsWaitPush) {
                        textView2.setText(waitPushTimeFormat);
                        textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorForth);
                        textView2.setVisibility(0);
                    } else if (!DetailCourseDirectoryWidget.this.havePermission || str2 == null || str3 == null) {
                        textView2.setVisibility(8);
                    } else {
                        textView2.setVisibility(0);
                        textView2.setText(TreeNodeUtilKt.getPercentStr(str2, str3, false));
                        textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorMainRed);
                        textView2.setVisibility(0);
                    }
                }
            }
            View convertView = holder.getConvertView();
            final DetailCourseDirectoryWidget detailCourseDirectoryWidget = DetailCourseDirectoryWidget.this;
            convertView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DetailCourseDirectoryWidget.C06211.convert$lambda$1(treeNode, detailCourseDirectoryWidget, this, view);
                }
            });
            boolean zContains = DetailCourseDirectoryWidget.this.lastVideoIdChapter.contains(treeNode.getValue().getItem().getChapter_id());
            if (treeNode.isExpand()) {
                DetailCourseDirectoryWidget.this.setArrowSpin(holder, treeNode);
                DetailCourseDirectoryWidget.this.firstExpandTree = treeNode;
                textView4.setVisibility(8);
                textView5.setVisibility(8);
                if (zTreeNodeIsWaitPush) {
                    textView4.setVisibility(DetailCourseDirectoryWidget.this.landscapeScreen ? 0 : 8);
                    textView4.setText("等待更新");
                    textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                }
                DetailCourseDirectoryWidget detailCourseDirectoryWidget2 = DetailCourseDirectoryWidget.this;
                String chapter_id2 = item.getChapter_id();
                Intrinsics.checkNotNullExpressionValue(chapter_id2, "itemData.chapter_id");
                detailCourseDirectoryWidget2.expandFirstId = chapter_id2;
                if (!DetailCourseDirectoryWidget.this.expandFirstIdList.contains(item.getChapter_id())) {
                    List list = DetailCourseDirectoryWidget.this.expandFirstIdList;
                    String chapter_id3 = item.getChapter_id();
                    Intrinsics.checkNotNullExpressionValue(chapter_id3, "itemData.chapter_id");
                    list.add(chapter_id3);
                }
            } else {
                textView5.setVisibility((!zBooleanValue || DetailCourseDirectoryWidget.this.landscapeScreen) ? 8 : 0);
                DetailCourseDirectoryWidget.this.setArrowSpin(holder, treeNode);
                if (!DetailCourseDirectoryWidget.this.landscapeScreen) {
                    textView4.setVisibility(8);
                } else if (zTreeNodeIsWaitPush) {
                    textView4.setText("等待更新");
                    textView4.setVisibility(0);
                    textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                } else {
                    textView4.setText("上次学习");
                    textView4.setVisibility((zContains && DetailCourseDirectoryWidget.this.havePermission) ? 0 : 8);
                    textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorMainRed);
                }
                if (DetailCourseDirectoryWidget.this.expandFirstIdList.contains(item.getChapter_id())) {
                    DetailCourseDirectoryWidget.this.expandFirstIdList.remove(item.getChapter_id());
                }
            }
            if (!zTreeNodeIsWaitPush && !zContains) {
                textView4.setVisibility(8);
            }
            int iDip2px = DpOrPxUtils.dip2px(DetailCourseDirectoryWidget.this.getContext(), 16.0f);
            if (!treeNode.isExpand() || treeNode.getChildren() == null || treeNode.getChildren().size() <= 0 || treeNode.getChildren().get(0).getCustomerLevel() != 2) {
                relativeLayout.setPadding(iDip2px, iDip2px, iDip2px, iDip2px);
            } else {
                relativeLayout.setPadding(iDip2px, iDip2px, iDip2px, 0);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_directory_first;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
            Intrinsics.checkNotNullParameter(treeNode, "treeNode");
            return treeNode.getCustomerLevel() == 0;
        }
    }

    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016¨\u0006\r"}, d2 = {"com/psychiatrygarden/widget/DetailCourseDirectoryWidget$initListLayout$2", "Lcom/psychiatrygarden/widget/treenode/TreeNodeDelegate;", "Lcom/psychiatrygarden/bean/CourseDirectoryTreeBean;", "convert", "", "holder", "Lcom/psychiatrygarden/widget/treenode/ViewHolder;", "treeNode", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "getLayoutId", "", "isItemType", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$initListLayout$2, reason: invalid class name */
    public static final class AnonymousClass2 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        final /* synthetic */ int $colorCountLand;
        final /* synthetic */ int $colorFirst;
        final /* synthetic */ int $colorForth;
        final /* synthetic */ int $colorMainRed;
        final /* synthetic */ int $colorSecond;
        final /* synthetic */ int $colorWaitLand;
        final /* synthetic */ int $colorWhiteLand;

        public AnonymousClass2(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.$colorWaitLand = i2;
            this.$colorWhiteLand = i3;
            this.$colorForth = i4;
            this.$colorFirst = i5;
            this.$colorCountLand = i6;
            this.$colorSecond = i7;
            this.$colorMainRed = i8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$1(DetailCourseDirectoryWidget this$0, String str, TreeNode treeNode, AnonymousClass2 this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(treeNode, "$treeNode");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            if (Intrinsics.areEqual("0", this$0.allMap.get(str))) {
                Context context = this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "context");
                ToastUtilsKt.toastOnUi$default(context, "暂无视频", 0, 2, (Object) null);
                return;
            }
            if (this$0.secondExpandTree != null) {
                TreeNode treeNode2 = this$0.secondExpandTree;
                Intrinsics.checkNotNull(treeNode2);
                if (!TreeNodeUtilKt.treeNodeEquals(treeNode2, treeNode)) {
                    TreeNode treeNode3 = this$0.secondExpandTree;
                    Intrinsics.checkNotNull(treeNode3);
                    treeNode3.isExpand();
                }
            }
            this$1.adapter.expandOrCollapseTreeNode(treeNode);
            this$0.sendFoldExpandMessage();
        }

        /* JADX WARN: Removed duplicated region for block: B:121:0x02c6  */
        /* JADX WARN: Removed duplicated region for block: B:122:0x02ca  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01c5  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x022d  */
        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void convert(@org.jetbrains.annotations.NotNull com.psychiatrygarden.widget.treenode.ViewHolder r20, @org.jetbrains.annotations.NotNull final com.psychiatrygarden.widget.treenode.TreeNode<com.psychiatrygarden.bean.CourseDirectoryTreeBean> r21) {
            /*
                Method dump skipped, instructions count: 719
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DetailCourseDirectoryWidget.AnonymousClass2.convert(com.psychiatrygarden.widget.treenode.ViewHolder, com.psychiatrygarden.widget.treenode.TreeNode):void");
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_directory_second;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
            Intrinsics.checkNotNullParameter(treeNode, "treeNode");
            return treeNode.getCustomerLevel() == 1;
        }
    }

    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016¨\u0006\r"}, d2 = {"com/psychiatrygarden/widget/DetailCourseDirectoryWidget$initListLayout$3", "Lcom/psychiatrygarden/widget/treenode/TreeNodeDelegate;", "Lcom/psychiatrygarden/bean/CourseDirectoryTreeBean;", "convert", "", "holder", "Lcom/psychiatrygarden/widget/treenode/ViewHolder;", "treeNode", "Lcom/psychiatrygarden/widget/treenode/TreeNode;", "getLayoutId", "", "isItemType", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$initListLayout$3, reason: invalid class name */
    public static final class AnonymousClass3 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        final /* synthetic */ int $colorFirst;
        final /* synthetic */ int $colorForth;
        final /* synthetic */ int $colorMainRed;
        final /* synthetic */ int $colorSecondLineLand;
        final /* synthetic */ int $colorThird;
        final /* synthetic */ int $colorWaitLand;
        final /* synthetic */ int $colorWhiteLand;
        final /* synthetic */ List<CourseDirectoryItemData> $data;
        final /* synthetic */ int $itemLineColor;
        final /* synthetic */ int $itemLineColorLand;
        final /* synthetic */ Drawable $landBottomRes;
        final /* synthetic */ Drawable $landCornersRes;
        final /* synthetic */ Drawable $landNormalRes;
        final /* synthetic */ Drawable $landPlayingBottomRes;
        final /* synthetic */ Drawable $landPlayingCornersRes;
        final /* synthetic */ Drawable $landPlayingNormalRes;
        final /* synthetic */ Drawable $landPlayingTopRes;
        final /* synthetic */ Drawable $landTopRes;
        final /* synthetic */ Drawable $shapeBgResBottom;
        final /* synthetic */ Drawable $shapeBgResCorners;
        final /* synthetic */ Drawable $shapeBgResNormal;
        final /* synthetic */ Drawable $shapeBgResTop;

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$initListLayout$3$WhenMappings */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[LiveStatus.values().length];
                try {
                    iArr[LiveStatus.NO_BEGIN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[LiveStatus.LIVING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[LiveStatus.CUTTING.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[LiveStatus.HAVE_VID.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass3(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6, Drawable drawable7, Drawable drawable8, Drawable drawable9, Drawable drawable10, Drawable drawable11, Drawable drawable12, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, List<? extends CourseDirectoryItemData> list) {
            this.$landPlayingCornersRes = drawable;
            this.$landCornersRes = drawable2;
            this.$shapeBgResCorners = drawable3;
            this.$landPlayingTopRes = drawable4;
            this.$landTopRes = drawable5;
            this.$shapeBgResTop = drawable6;
            this.$landPlayingBottomRes = drawable7;
            this.$landBottomRes = drawable8;
            this.$shapeBgResBottom = drawable9;
            this.$landPlayingNormalRes = drawable10;
            this.$landNormalRes = drawable11;
            this.$shapeBgResNormal = drawable12;
            this.$colorWaitLand = i2;
            this.$colorForth = i3;
            this.$colorWhiteLand = i4;
            this.$colorFirst = i5;
            this.$colorSecondLineLand = i6;
            this.$colorThird = i7;
            this.$colorMainRed = i8;
            this.$itemLineColorLand = i9;
            this.$itemLineColor = i10;
            this.$data = list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$0(DetailCourseDirectoryWidget this$0, CourseDirectoryContentItem itemData, boolean z2, List data, TreeNode treeNode, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(data, "$data");
            Intrinsics.checkNotNullParameter(treeNode, "$treeNode");
            if (!this$0.havePermission && Intrinsics.areEqual("0", itemData.getFree_watch())) {
                ToastUtil.shortToast(this$0.getContext(), this$0.getContext().getString(R.string.videoNoPermission));
                return;
            }
            if (z2) {
                ToastUtil.shortToast(this$0.getContext(), this$0.getContext().getString(R.string.videoWaitUpdate));
                return;
            }
            if (!Intrinsics.areEqual("2", itemData.getType())) {
                if (!Intrinsics.areEqual("1", itemData.getType()) || TextUtils.isEmpty(itemData.getVid())) {
                    return;
                }
                if (this$0.havePermission) {
                    String vid = itemData.getVid();
                    Intrinsics.checkNotNullExpressionValue(vid, "itemData.vid");
                    TreeNodeUtilKt.initWaitPlayList(data, vid);
                } else if (Intrinsics.areEqual("1", itemData.getFree_watch())) {
                    TreeNodeUtilKt.initWaitPlayList(itemData.getObj_id(), itemData.getVid(), itemData.getTitle(), itemData.getCurrent_see(), itemData.getType());
                }
                this$0.getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                Intrinsics.checkNotNullExpressionValue(itemData, "itemData");
                this$0.clickCallBack(treeNode, itemData);
                if (this$0.landscapeScreen) {
                    this$0.changeClickBg(itemData, treeNode);
                    return;
                }
                return;
            }
            int i2 = WhenMappings.$EnumSwitchMapping$0[TreeNodeUtilKt.getLivingStatus(itemData.getStart_live_time(), itemData.getEnd_live_time(), itemData.getVid()).ordinal()];
            if (i2 == 1) {
                if (!DateTimeUtilKt.timeWithinHalfAnHour(itemData.getStart_live_time())) {
                    ToastUtil.shortToast(this$0.getContext(), this$0.getContext().getString(R.string.livingNoBegin));
                    return;
                } else {
                    if (this$0.havePermission) {
                        CommonUtil.launchLiving(this$0.getContext(), itemData.getUser_id(), itemData.getApp_id(), itemData.getApp_secret(), itemData.getRoom_id(), itemData.getCourse_id(), itemData.getObj_id());
                        return;
                    }
                    return;
                }
            }
            if (i2 == 2) {
                if (this$0.havePermission) {
                    CommonUtil.launchLiving(this$0.getContext(), itemData.getUser_id(), itemData.getApp_id(), itemData.getApp_secret(), itemData.getRoom_id(), itemData.getCourse_id(), itemData.getObj_id());
                    return;
                }
                return;
            }
            if (i2 == 3) {
                ToastUtil.shortToast(this$0.getContext(), this$0.getContext().getString(R.string.livingCutting));
                return;
            }
            if (i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            if (this$0.havePermission) {
                String vid2 = itemData.getVid();
                Intrinsics.checkNotNullExpressionValue(vid2, "itemData.vid");
                TreeNodeUtilKt.initWaitPlayList(data, vid2);
            } else if (Intrinsics.areEqual("1", itemData.getFree_watch())) {
                TreeNodeUtilKt.initWaitPlayList(itemData.getVideo_id(), itemData.getVid(), itemData.getTitle(), itemData.getCurrent_see(), itemData.getType());
            }
            this$0.getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
            Intrinsics.checkNotNullExpressionValue(itemData, "itemData");
            this$0.clickCallBack(treeNode, itemData);
            if (this$0.landscapeScreen) {
                this$0.changeClickBg(itemData, treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(@NotNull ViewHolder holder, @NotNull final TreeNode<CourseDirectoryTreeBean> treeNode) throws NumberFormatException {
            int i2;
            int i3;
            View view;
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(treeNode, "treeNode");
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(DetailCourseDirectoryWidget.this.getContext(), 16.0f);
            View itemThirdTopViewTag = holder.getView(R.id.itemThirdViewTag);
            ImageView imageView = (ImageView) holder.getView(R.id.itemThirdImgCourseType);
            ImageView imageView2 = (ImageView) holder.getView(R.id.itemThirdImgCourseAnim);
            ImageView imageView3 = (ImageView) holder.getView(R.id.itemThirdImgCourseDownload);
            TextView textView = (TextView) holder.getView(R.id.tvDownload);
            TextView textView2 = (TextView) holder.getView(R.id.tvDate);
            TextView textView3 = (TextView) holder.getView(R.id.tvTime);
            TextView textView4 = (TextView) holder.getView(R.id.tvLearnProgress);
            TextView textView5 = (TextView) holder.getView(R.id.tvLiveStatus);
            TextView textView6 = (TextView) holder.getView(R.id.tvLastLearn);
            TextView textView7 = (TextView) holder.getView(R.id.tvTeacherName);
            TextView textView8 = (TextView) holder.getView(R.id.tvFreeSee);
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.itemRootThird);
            View view2 = holder.getView(R.id.thirdItemLine);
            final CourseDirectoryContentItem contentItem = treeNode.getValue().getContentItem();
            boolean zAreEqual = Intrinsics.areEqual(DetailCourseDirectoryWidget.this.playingVid, contentItem.getVid());
            Log.d("Position", "initListLayout:convert 位置：" + absoluteAdapterPosition + "  -----" + contentItem.getTitle() + ' ');
            final boolean zTreeNodeIsWaitPush = TreeNodeUtilKt.treeNodeIsWaitPush(treeNode);
            String waitPushTimeFormat = zTreeNodeIsWaitPush ? TreeNodeUtilKt.getWaitPushTimeFormat(TreeNodeUtilKt.getTreeNodeWaitPushTimeStr(treeNode)) : "";
            if (TreeNodeUtilKt.haveSecondPresent(treeNode)) {
                Intrinsics.checkNotNullExpressionValue(itemThirdTopViewTag, "itemThirdTopViewTag");
                ViewExtensionsKt.visible(itemThirdTopViewTag);
                relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
                relativeLayout2.setBackground(null);
                if (TreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                    view2.setVisibility(4);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        relativeLayout.setBackground(this.$landPlayingCornersRes);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        relativeLayout.setBackground(this.$landCornersRes);
                    } else {
                        relativeLayout.setBackground(this.$shapeBgResCorners);
                    }
                } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                    view2.setVisibility(0);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        relativeLayout.setBackground(this.$landPlayingTopRes);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        relativeLayout.setBackground(this.$landTopRes);
                    } else {
                        relativeLayout.setBackground(this.$shapeBgResTop);
                    }
                } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                    view2.setVisibility(4);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        relativeLayout.setBackground(this.$landPlayingBottomRes);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        relativeLayout.setBackground(this.$landBottomRes);
                    } else {
                        relativeLayout.setBackground(this.$shapeBgResBottom);
                    }
                } else {
                    view2.setVisibility(0);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        relativeLayout.setBackground(this.$landPlayingNormalRes);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        relativeLayout.setBackground(this.$landNormalRes);
                    } else {
                        relativeLayout.setBackground(this.$shapeBgResNormal);
                    }
                }
            } else {
                Intrinsics.checkNotNullExpressionValue(itemThirdTopViewTag, "itemThirdTopViewTag");
                ViewExtensionsKt.visible(itemThirdTopViewTag);
                relativeLayout.setPadding(0, 0, 0, 0);
                relativeLayout.setBackground(null);
                if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                    relativeLayout2.setBackground(this.$landPlayingNormalRes);
                } else {
                    relativeLayout2.setBackground(null);
                }
            }
            TextView textView9 = (TextView) holder.getView(R.id.itemThirdTvCourseTitle);
            boolean zAreEqual2 = Intrinsics.areEqual("1", contentItem.getFree_watch());
            textView8.setVisibility((!zAreEqual2 || DetailCourseDirectoryWidget.this.landscapeScreen) ? 8 : 0);
            textView9.setText(contentItem.getTitle());
            Object drawable = imageView2.getDrawable();
            Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.Animatable");
            if (((Animatable) drawable).isRunning()) {
                Object drawable2 = imageView2.getDrawable();
                Intrinsics.checkNotNull(drawable2, "null cannot be cast to non-null type android.graphics.drawable.Animatable");
                ((Animatable) drawable2).stop();
            }
            imageView2.setVisibility(8);
            imageView.setVisibility(0);
            CourseDirectoryContentItem contentItem2 = treeNode.getValue().getContentItem();
            String type = contentItem2.getType();
            String teacher_name = contentItem2.getTeacher_name();
            if (TextUtils.isEmpty(teacher_name) || Intrinsics.areEqual("1", contentItem2.getIs_hide_teacher())) {
                textView7.setVisibility(8);
            } else {
                textView7.setVisibility(0);
                textView7.setText(teacher_name);
            }
            if (Intrinsics.areEqual("2", type)) {
                boolean z2 = TreeNodeUtilKt.isDownload(contentItem2.getVid()) && DetailCourseDirectoryWidget.this.havePermission;
                imageView3.setVisibility(z2 ? 0 : 8);
                textView.setVisibility(z2 ? 0 : 8);
                textView4.setVisibility(8);
                textView6.setVisibility(8);
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                String startTimeStr = contentItem2.getStart_live_time();
                Intrinsics.checkNotNullExpressionValue(startTimeStr, "startTimeStr");
                long j2 = Long.parseLong(startTimeStr);
                String end_live_time = contentItem2.getEnd_live_time();
                Intrinsics.checkNotNullExpressionValue(end_live_time, "contentItem.end_live_time");
                long j3 = Long.parseLong(end_live_time);
                if (zTreeNodeIsWaitPush) {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_living_land_night);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_live_not_start_night : R.drawable.icon_live_not_start_day);
                    }
                    textView2.setText(waitPushTimeFormat);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView3.setVisibility(8);
                    textView2.setVisibility(0);
                    textView6.setVisibility(8);
                    textView5.setText("等待更新");
                    textView5.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView5.setVisibility(0);
                } else if (jCurrentTimeMillis < j2) {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_living_land_night);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_live_not_start_night : R.drawable.icon_live_not_start_day);
                    }
                    String[] livingDate = TreeNodeUtilKt.getLivingDate(contentItem.getStart_live_time(), contentItem.getEnd_live_time());
                    textView2.setText(livingDate[0]);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView3.setText(livingDate[1]);
                    textView3.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView3.setVisibility(0);
                    textView2.setVisibility(0);
                    textView6.setVisibility(8);
                    textView5.setText("即将直播");
                    textView5.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView5.setVisibility(0);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                } else if (jCurrentTimeMillis < j3) {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorFirst);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView2.setImageResource(R.drawable.living_anim_land);
                    } else {
                        imageView2.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.living_anim3_night : R.drawable.living_anim3_day);
                    }
                    Object drawable3 = imageView2.getDrawable();
                    Intrinsics.checkNotNull(drawable3, "null cannot be cast to non-null type android.graphics.drawable.Animatable");
                    ((Animatable) drawable3).start();
                    imageView2.setVisibility(0);
                    imageView.setVisibility(8);
                    String[] livingDate2 = TreeNodeUtilKt.getLivingDate(contentItem.getStart_live_time(), contentItem.getEnd_live_time());
                    textView2.setText(livingDate2[0]);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView3.setText(livingDate2[1]);
                    textView3.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView3.setVisibility(0);
                    textView2.setVisibility(0);
                    textView5.setText("正在直播");
                    textView5.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorMainRed);
                    textView5.setVisibility(0);
                    textView6.setVisibility(8);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                } else if (TextUtils.isEmpty(contentItem2.getVid())) {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorFirst);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_live_cutting_land);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_live_cutting_night : R.drawable.icon_live_cutting_day);
                    }
                    String[] livingDate3 = TreeNodeUtilKt.getLivingDate(contentItem.getStart_live_time(), contentItem.getEnd_live_time());
                    textView2.setText(livingDate3[0]);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorForth);
                    textView3.setText(livingDate3[1]);
                    textView3.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorForth);
                    textView3.setVisibility(0);
                    textView2.setVisibility(0);
                    textView5.setText("直播结束");
                    textView5.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorForth);
                    textView5.setVisibility(0);
                    textView6.setVisibility(8);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorForth);
                } else {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorFirst);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        imageView2.setImageResource(R.drawable.living_anim_land);
                        Object drawable4 = imageView2.getDrawable();
                        Intrinsics.checkNotNull(drawable4, "null cannot be cast to non-null type android.graphics.drawable.Animatable");
                        ((Animatable) drawable4).start();
                        imageView2.setVisibility(0);
                        imageView.setVisibility(8);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_living_land_day);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_live_video_night : R.drawable.icon_live_video_day);
                    }
                    String[] livingDate4 = TreeNodeUtilKt.getLivingDate(contentItem.getStart_live_time(), contentItem.getEnd_live_time());
                    if (TreeNodeUtilKt.timeIsCurrentYear(contentItem.getStart_live_time())) {
                        textView2.setText(livingDate4[0]);
                    } else {
                        String strSubstring = livingDate4[0].substring(2);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                        textView2.setText(strSubstring);
                    }
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView3.setText(livingDate4[1]);
                    textView3.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView3.setVisibility(8);
                    textView2.setVisibility(0);
                    textView5.setVisibility(8);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        textView6.setVisibility(Intrinsics.areEqual(DetailCourseDirectoryWidget.this.lastLearnVid, contentItem.getVid()) ? 0 : 8);
                    } else {
                        textView6.setVisibility(8);
                    }
                    textView6.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorMainRed);
                    boolean zIsDownload = TreeNodeUtilKt.isDownload(contentItem2.getVid());
                    imageView3.setVisibility(zIsDownload ? 0 : 8);
                    textView.setVisibility(zIsDownload ? 0 : 8);
                    if (Intrinsics.areEqual("1", contentItem2.getIs_end())) {
                        textView4.setVisibility(0);
                        textView4.setText("已完成");
                        textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorFirst);
                    } else {
                        textView4.setVisibility(0);
                        String percentStr = TreeNodeUtilKt.getPercentStr(contentItem2.getSee(), contentItem2.getDuration(), true);
                        textView4.setText(percentStr);
                        if (Intrinsics.areEqual("已完成", percentStr)) {
                            textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorFirst);
                        } else {
                            textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorMainRed);
                        }
                    }
                }
            } else if (Intrinsics.areEqual("1", type)) {
                textView5.setVisibility(8);
                if (zTreeNodeIsWaitPush) {
                    imageView3.setVisibility(8);
                    textView.setVisibility(8);
                    textView3.setVisibility(8);
                    textView4.setVisibility(8);
                    textView6.setVisibility(8);
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_record_video_land_night);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_record_video_update_night : R.drawable.icon_record_video_update_day);
                    }
                    textView2.setText(waitPushTimeFormat);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView2.setVisibility(0);
                    textView5.setText("等待更新");
                    textView5.setVisibility(0);
                    textView5.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWaitLand : this.$colorForth);
                } else {
                    textView9.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorFirst);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen && zAreEqual) {
                        imageView2.setImageResource(R.drawable.living_anim_land);
                        Object drawable5 = imageView2.getDrawable();
                        Intrinsics.checkNotNull(drawable5, "null cannot be cast to non-null type android.graphics.drawable.Animatable");
                        ((Animatable) drawable5).start();
                        imageView2.setVisibility(0);
                        imageView.setVisibility(8);
                    } else if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        imageView.setImageResource(R.drawable.icon_record_video_land_day);
                    } else {
                        imageView.setImageResource(DetailCourseDirectoryWidget.this.isNightTheme() ? R.drawable.icon_record_video_night : R.drawable.icon_record_video_day);
                    }
                    boolean zAreEqual3 = !TextUtils.isEmpty(contentItem.getChapter_id()) ? Intrinsics.areEqual(contentItem.getVid(), DetailCourseDirectoryWidget.this.lastLearnVid) && Intrinsics.areEqual(DetailCourseDirectoryWidget.this.lastLearnChapterId, contentItem.getChapter_id()) : Intrinsics.areEqual(contentItem.getVid(), DetailCourseDirectoryWidget.this.lastLearnVid);
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        textView6.setVisibility(zAreEqual3 ? 0 : 8);
                    } else {
                        textView6.setVisibility(8);
                    }
                    textView6.setText("上次学习");
                    textView6.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorWhiteLand : this.$colorMainRed);
                    boolean z3 = TreeNodeUtilKt.isDownload(contentItem2.getVid()) && DetailCourseDirectoryWidget.this.havePermission;
                    imageView3.setVisibility(z3 ? 0 : 8);
                    textView.setVisibility(z3 ? 0 : 8);
                    textView2.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView7.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                    textView2.setVisibility(8);
                    if (TextUtils.isEmpty(contentItem2.getDuration())) {
                        i2 = 0;
                        i3 = 8;
                        textView3.setVisibility(8);
                    } else {
                        String duration = contentItem2.getDuration();
                        Intrinsics.checkNotNullExpressionValue(duration, "contentItem.duration");
                        textView3.setText((((int) Double.parseDouble(duration)) / 60) + " 分钟");
                        textView3.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorThird);
                        i2 = 0;
                        textView3.setVisibility(0);
                        i3 = 8;
                    }
                    if (Intrinsics.areEqual("1", contentItem2.getIs_end())) {
                        textView4.setText("已完成");
                        textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorFirst);
                    } else {
                        String percentStr2 = TreeNodeUtilKt.getPercentStr(contentItem2.getSee(), contentItem2.getDuration(), true);
                        textView4.setText(percentStr2);
                        if (Intrinsics.areEqual("已完成", percentStr2)) {
                            textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorFirst);
                        } else {
                            textView4.setTextColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$colorSecondLineLand : this.$colorMainRed);
                        }
                    }
                    textView4.setVisibility((DetailCourseDirectoryWidget.this.havePermission || zAreEqual2) ? i2 : i3);
                }
            }
            view2.setBackgroundColor(DetailCourseDirectoryWidget.this.landscapeScreen ? this.$itemLineColorLand : this.$itemLineColor);
            View convertView = holder.getConvertView();
            final DetailCourseDirectoryWidget detailCourseDirectoryWidget = DetailCourseDirectoryWidget.this;
            final List<CourseDirectoryItemData> list = this.$data;
            convertView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.f6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    DetailCourseDirectoryWidget.AnonymousClass3.convert$lambda$0(detailCourseDirectoryWidget, contentItem, zTreeNodeIsWaitPush, list, treeNode, view3);
                }
            });
            if (treeNode.getParent() == null) {
                View view3 = DetailCourseDirectoryWidget.this.viewGap;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewGap");
                    view = null;
                } else {
                    view = view3;
                }
                ViewExtensionsKt.gone(view);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_directory_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(@NotNull TreeNode<CourseDirectoryTreeBean> treeNode) {
            Intrinsics.checkNotNullParameter(treeNode, "treeNode");
            return treeNode.getCustomerLevel() == 2;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseDirectoryWidget(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ DetailCourseDirectoryWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeClickBg(CourseDirectoryContentItem itemData, TreeNode<CourseDirectoryTreeBean> treeNode) {
        String vid = itemData.getVid();
        Intrinsics.checkNotNullExpressionValue(vid, "itemData.vid");
        this.playingVid = vid;
        TreeNode<CourseDirectoryTreeBean> treeNode2 = this.preClickTree;
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = null;
        if (treeNode2 != null) {
            TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter2 = this.adapter;
            if (treeNodeAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                treeNodeAdapter2 = null;
            }
            treeNodeAdapter2.notifyTreeNode(treeNode2);
        }
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter3 = this.adapter;
        if (treeNodeAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            treeNodeAdapter = treeNodeAdapter3;
        }
        treeNodeAdapter.notifyTreeNode(treeNode);
        this.preClickTree = treeNode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickCallBack(TreeNode<CourseDirectoryTreeBean> treeNode, CourseDirectoryContentItem itemData) {
        CourseDirectoryItemData item;
        if (treeNode.getParent() == null) {
            this.clickPlayListener2.invoke("", "", "", "", itemData);
            return;
        }
        if (treeNode.getParent().getCustomerLevel() != 1) {
            if (treeNode.getParent().getCustomerLevel() != 0 || (item = treeNode.getParent().getValue().getItem()) == null) {
                return;
            }
            Function5<? super String, ? super String, ? super String, ? super String, ? super CourseDirectoryContentItem, Unit> function5 = this.clickPlayListener2;
            String chapter_id = item.getChapter_id();
            Intrinsics.checkNotNullExpressionValue(chapter_id, "it.chapter_id");
            String title = item.getTitle();
            Intrinsics.checkNotNullExpressionValue(title, "it.title");
            function5.invoke(chapter_id, "", title, "", itemData);
            return;
        }
        CourseDirectoryItemData item2 = treeNode.getParent().getValue().getItem();
        if (item2 != null) {
            String childId = item2.getChapter_id();
            String childTitle = item2.getTitle();
            TreeNode<CourseDirectoryTreeBean> parent = treeNode.getParent().getParent();
            if (parent != null) {
                Intrinsics.checkNotNullExpressionValue(parent, "parent");
                String rootId = parent.getValue().getItem().getChapter_id();
                String rootTitle = parent.getValue().getItem().getTitle();
                Function5<? super String, ? super String, ? super String, ? super String, ? super CourseDirectoryContentItem, Unit> function52 = this.clickPlayListener2;
                Intrinsics.checkNotNullExpressionValue(rootId, "rootId");
                Intrinsics.checkNotNullExpressionValue(childId, "childId");
                Intrinsics.checkNotNullExpressionValue(rootTitle, "rootTitle");
                Intrinsics.checkNotNullExpressionValue(childTitle, "childTitle");
                function52.invoke(rootId, childId, rootTitle, childTitle, itemData);
            }
        }
    }

    private final void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.mCourseId);
        ajaxParams.put("type", this.courseType);
        YJYHttpUtils.get(getContext(), NetworkRequestsURL.courseDirectoryCombine, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.DetailCourseDirectoryWidget.getListData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@NotNull Throwable t2, int errorNo, @NotNull String strMsg) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(t2, "t");
                Intrinsics.checkNotNullParameter(strMsg, "strMsg");
                super.onFailure(t2, errorNo, strMsg);
                RecyclerView recyclerView = DetailCourseDirectoryWidget.this.rvCourseDirectory;
                if (recyclerView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                    recyclerView = null;
                }
                recyclerView.setVisibility(8);
                DetailCourseDirectoryWidget.setEmptyView$default(DetailCourseDirectoryWidget.this, false, true, 1, null);
                EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, false));
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) throws Resources.NotFoundException {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        RecyclerView recyclerView = DetailCourseDirectoryWidget.this.rvCourseDirectory;
                        if (recyclerView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                            recyclerView = null;
                        }
                        recyclerView.setVisibility(8);
                        DetailCourseDirectoryWidget.setEmptyView$default(DetailCourseDirectoryWidget.this, true, false, 2, null);
                        EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, false));
                        return;
                    }
                    List dateBeanList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$getListData$1$onSuccess$dateBeanList$1
                    }.getType());
                    DetailCourseDirectoryWidget detailCourseDirectoryWidget = DetailCourseDirectoryWidget.this;
                    Intrinsics.checkNotNullExpressionValue(dateBeanList, "dateBeanList");
                    detailCourseDirectoryWidget.data = dateBeanList;
                    if (DetailCourseDirectoryWidget.this.landscapeScreen) {
                        TreeNodeUtilKt.setSeeByVid(DetailCourseDirectoryWidget.this.data, DetailCourseDirectoryWidget.this.getSetVid(), DetailCourseDirectoryWidget.this.getSetSeeDuration());
                    }
                    if (DetailCourseDirectoryWidget.this.data.isEmpty()) {
                        RecyclerView recyclerView2 = DetailCourseDirectoryWidget.this.rvCourseDirectory;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                            recyclerView2 = null;
                        }
                        recyclerView2.setVisibility(8);
                        DetailCourseDirectoryWidget.setEmptyView$default(DetailCourseDirectoryWidget.this, true, false, 2, null);
                        DetailCourseDirectoryWidget.this.sendFoldExpandMessage();
                        EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, false));
                        return;
                    }
                    CustomEmptyView customEmptyView = DetailCourseDirectoryWidget.this.emptyView;
                    if (customEmptyView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                        customEmptyView = null;
                    }
                    customEmptyView.setVisibility(8);
                    RecyclerView recyclerView3 = DetailCourseDirectoryWidget.this.rvCourseDirectory;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                        recyclerView3 = null;
                    }
                    recyclerView3.setVisibility(0);
                    DetailCourseDirectoryWidget.this.initLastVideId();
                    if (!TextUtils.isEmpty(DetailCourseDirectoryWidget.this.playingVid) && DetailCourseDirectoryWidget.this.landscapeScreen) {
                        List<String> chapterIdByVideoId = TreeNodeUtilKt.getChapterIdByVideoId(dateBeanList, DetailCourseDirectoryWidget.this.playingVid, DetailCourseDirectoryWidget.this.playingChapterId);
                        if (!chapterIdByVideoId.isEmpty()) {
                            DetailCourseDirectoryWidget.this.expandFirstId = chapterIdByVideoId.get(0);
                            DetailCourseDirectoryWidget.this.expandSecondId = chapterIdByVideoId.get(1);
                        }
                    } else if (!TextUtils.isEmpty(DetailCourseDirectoryWidget.this.lastLearnVid)) {
                        List<String> chapterIdByVideoId2 = TreeNodeUtilKt.getChapterIdByVideoId(DetailCourseDirectoryWidget.this.data, DetailCourseDirectoryWidget.this.lastLearnVid, DetailCourseDirectoryWidget.this.lastLearnChapterId);
                        if (!chapterIdByVideoId2.isEmpty()) {
                            DetailCourseDirectoryWidget.this.expandFirstId = chapterIdByVideoId2.get(0);
                            DetailCourseDirectoryWidget.this.expandSecondId = chapterIdByVideoId2.get(1);
                        }
                    }
                    DetailCourseDirectoryWidget.this.freeSeeMap.clear();
                    TreeNodeUtilKt.initSeeAndAll(dateBeanList, DetailCourseDirectoryWidget.this.allMap, DetailCourseDirectoryWidget.this.seeMap, DetailCourseDirectoryWidget.this.haveVidMap, DetailCourseDirectoryWidget.this.freeSeeMap);
                    DetailCourseDirectoryWidget.this.initListLayout(dateBeanList);
                    if (!DetailCourseDirectoryWidget.this.freeSeeMap.isEmpty()) {
                        EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, true));
                    } else {
                        EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, false));
                    }
                } catch (Exception e2) {
                    RecyclerView recyclerView4 = DetailCourseDirectoryWidget.this.rvCourseDirectory;
                    if (recyclerView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                        recyclerView4 = null;
                    }
                    recyclerView4.setVisibility(8);
                    DetailCourseDirectoryWidget.setEmptyView$default(DetailCourseDirectoryWidget.this, false, true, 1, null);
                    Log.e("ErrorMsg-Line 214:", "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    EventBus.getDefault().post(new HaveFreeWatchEvent(DetailCourseDirectoryWidget.this.mCourseId, false));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initLastVideId() {
        this.lastLearnVid = CourseDataSpUtilKt.readLastLearnVid(this.mCourseId, getContext(), "");
        this.lastLearnChapterId = CourseDataSpUtilKt.readLastLearnChapterId(this.mCourseId, getContext(), "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initListLayout(List<? extends CourseDirectoryItemData> data) throws Resources.NotFoundException {
        Context context;
        int i2;
        Context context2;
        int i3;
        Context context3;
        int i4;
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter;
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter2;
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter3;
        if (isNightTheme()) {
            context = getContext();
            i2 = R.color.forth_txt_color_night;
        } else {
            context = getContext();
            i2 = R.color.forth_txt_color;
        }
        int color = context.getColor(i2);
        if (isNightTheme()) {
            context2 = getContext();
            i3 = R.color.main_theme_color_night;
        } else {
            context2 = getContext();
            i3 = R.color.main_theme_color;
        }
        int color2 = context2.getColor(i3);
        if (isNightTheme()) {
            context3 = getContext();
            i4 = R.color.first_txt_color_night;
        } else {
            context3 = getContext();
            i4 = R.color.first_txt_color;
        }
        int color3 = context3.getColor(i4);
        int color4 = isNightTheme() ? getContext().getColor(R.color.second_txt_color_night) : getContext().getColor(R.color.second_txt_color);
        int color5 = isNightTheme() ? getContext().getColor(R.color.third_txt_color_night) : getContext().getColor(R.color.third_txt_color);
        int color6 = isNightTheme() ? getResources().getColor(R.color.fourth_line_backgroup_color_night) : getContext().getColor(R.color.fourth_line_backgroup_color);
        int color7 = getResources().getColor(R.color.colorLandLine);
        Drawable drawable = getResources().getDrawable(R.drawable.shape_color22_normal, getContext().getTheme());
        Drawable drawable2 = getResources().getDrawable(R.drawable.shape_color22_top_corner12, getContext().getTheme());
        Drawable drawable3 = getResources().getDrawable(R.drawable.shape_color22_bottom_corner12, getContext().getTheme());
        Drawable drawable4 = getResources().getDrawable(R.drawable.shape_color22_corner12, getContext().getTheme());
        getResources().getColor(R.color.new_gray_line_color_night);
        Drawable drawable5 = getResources().getDrawable(R.drawable.shape_color2a_normal, getContext().getTheme());
        Drawable drawable6 = getResources().getDrawable(R.drawable.shape_color2a_top_corner12, getContext().getTheme());
        Drawable drawable7 = getResources().getDrawable(R.drawable.shape_color2a_bottom_corner12, getContext().getTheme());
        Drawable drawable8 = getResources().getDrawable(R.drawable.shape_color2a_corner12, getContext().getTheme());
        Drawable drawable9 = getResources().getDrawable(R.drawable.shape_project_normal_bg, getContext().getTheme());
        Drawable drawable10 = getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, getContext().getTheme());
        Drawable drawable11 = getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, getContext().getTheme());
        Drawable drawable12 = getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, getContext().getTheme());
        int color8 = getContext().getColor(R.color.white_color);
        int color9 = getContext().getColor(R.color.second_txt_color);
        int color10 = getContext().getColor(R.color.third_txt_color);
        int color11 = getContext().getColor(R.color.videoWaitUpdateColor_land);
        if (this.landscapeScreen) {
            this.listData = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData2(data, this.expandFirstId, this.expandSecondId);
        } else {
            if (!this.expandFirstIdList.contains(this.expandFirstId)) {
                this.expandFirstIdList.add(this.expandFirstId);
            }
            if (!this.expandSecondIdList.contains(this.expandSecondId)) {
                this.expandSecondIdList.add(this.expandSecondId);
            }
            if (this.mFoldDire) {
                this.listData = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData3(data, new ArrayList(), new ArrayList());
            } else {
                this.listData = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData3(data, this.expandFirstIdList, this.expandSecondIdList);
            }
        }
        if (!TextUtils.isEmpty(this.lastLearnVid)) {
            this.lastVideoIdChapter = TreeNodeUtilKt.getChapterIdByVideoId(data, this.lastLearnVid, this.lastLearnChapterId);
        }
        this.adapter = new TreeNodeAdapter<>(getContext(), this.listData);
        if (this.landscapeScreen) {
            this.positionScrollTo = TreeNodeUtilKt.getPlayingPosition(data, this.playingVid, this.playingChapterId);
        }
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter4 = this.adapter;
        if (treeNodeAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            treeNodeAdapter = null;
        } else {
            treeNodeAdapter = treeNodeAdapter4;
        }
        treeNodeAdapter.addItemViewDelegate(new C06211(color11, color8, color, color3, color9, color4, color10, color2));
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter5 = this.adapter;
        if (treeNodeAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            treeNodeAdapter2 = null;
        } else {
            treeNodeAdapter2 = treeNodeAdapter5;
        }
        treeNodeAdapter2.addItemViewDelegate(new AnonymousClass2(color11, color8, color, color3, color9, color4, color2));
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter6 = this.adapter;
        if (treeNodeAdapter6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            treeNodeAdapter3 = null;
        } else {
            treeNodeAdapter3 = treeNodeAdapter6;
        }
        treeNodeAdapter3.addItemViewDelegate(new AnonymousClass3(drawable4, drawable8, drawable12, drawable2, drawable6, drawable10, drawable3, drawable7, drawable11, drawable, drawable5, drawable9, color11, color, color8, color3, color10, color5, color2, color7, color6, data));
        RecyclerView recyclerView = this.rvCourseDirectory;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
            recyclerView = null;
        }
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter7 = this.adapter;
        if (treeNodeAdapter7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            treeNodeAdapter7 = null;
        }
        recyclerView.setAdapter(treeNodeAdapter7);
        RecyclerView recyclerView2 = this.rvCourseDirectory;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
            recyclerView2 = null;
        }
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.b6
            @Override // java.lang.Runnable
            public final void run() {
                DetailCourseDirectoryWidget.initListLayout$lambda$1(this.f16335c);
            }
        }, 300L);
        Log.d("Position", "initListLayout:计算的位置：" + this.positionScrollTo + ' ');
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.c6
            @Override // java.lang.Runnable
            public final void run() {
                DetailCourseDirectoryWidget.initListLayout$lambda$2(this.f16362c);
            }
        }, 700L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListLayout$lambda$1(DetailCourseDirectoryWidget this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendFoldExpandMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListLayout$lambda$2(DetailCourseDirectoryWidget this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            RecyclerView recyclerView = this$0.rvCourseDirectory;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                recyclerView = null;
            }
            recyclerView.scrollToPosition(this$0.positionScrollTo);
        } catch (Exception e2) {
            System.out.println((Object) ("Error:" + e2.getMessage()));
        }
    }

    public static /* synthetic */ void initParams$default(DetailCourseDirectoryWidget detailCourseDirectoryWidget, String str, String str2, boolean z2, boolean z3, String str3, boolean z4, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        boolean z5 = z2;
        boolean z6 = (i2 & 8) != 0 ? true : z3;
        if ((i2 & 16) != 0) {
            str3 = null;
        }
        detailCourseDirectoryWidget.initParams(str, str2, z5, z6, str3, (i2 & 32) != 0 ? true : z4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initParams2$lambda$0(DetailCourseDirectoryWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNightTheme() {
        return SkinManager.getCurrentSkinType(getContext()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendFoldExpandMessage() {
        EventBus.getDefault().post(EventBusConstant.DIRECTORY_FOLD_OR_EXPAND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setArrowSpin(ViewHolder helper, TreeNode<CourseDirectoryTreeBean> data) {
        ImageView imageView = (ImageView) helper.getView(R.id.itemArrow);
        if (data.isExpand()) {
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                imageView.setImageResource(R.drawable.icon_top_arrow_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.icon_top_arrow_day);
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
        } else {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
        }
    }

    private final void setEmptyView(boolean nullData, boolean errorData) throws Resources.NotFoundException {
        CustomEmptyView customEmptyView = this.emptyView;
        CustomEmptyView customEmptyView2 = null;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        customEmptyView.setVisibility(0);
        if (!nullData) {
            if (errorData) {
                CustomEmptyView customEmptyView3 = this.emptyView;
                if (customEmptyView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView3 = null;
                }
                customEmptyView3.setLoadFileResUi(getContext());
                if (this.landscapeScreen) {
                    CustomEmptyView customEmptyView4 = this.emptyView;
                    if (customEmptyView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    } else {
                        customEmptyView2 = customEmptyView4;
                    }
                    customEmptyView2.changeEmptyViewNightBg();
                    return;
                }
                return;
            }
            return;
        }
        CustomEmptyView customEmptyView5 = this.emptyView;
        if (customEmptyView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView5 = null;
        }
        customEmptyView5.uploadEmptyViewResUi();
        CustomEmptyView customEmptyView6 = this.emptyView;
        if (customEmptyView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView6 = null;
        }
        customEmptyView6.setEmptyTextStr("内容更新中");
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_wait_update_night_svg : R.drawable.ic_empty_data_wait_update_day_svg;
        CustomEmptyView customEmptyView7 = this.emptyView;
        if (customEmptyView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView7 = null;
        }
        customEmptyView7.setImgEmptyRes(i2);
        if (this.landscapeScreen) {
            CustomEmptyView customEmptyView8 = this.emptyView;
            if (customEmptyView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView2 = customEmptyView8;
            }
            customEmptyView2.changeEmptyViewNightBg();
        }
    }

    public static /* synthetic */ void setEmptyView$default(DetailCourseDirectoryWidget detailCourseDirectoryWidget, boolean z2, boolean z3, int i2, Object obj) throws Resources.NotFoundException {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        if ((i2 & 2) != 0) {
            z3 = false;
        }
        detailCourseDirectoryWidget.setEmptyView(z2, z3);
    }

    private final void updateTreeNode(List<String> listVid) {
        try {
            for (TreeNode<CourseDirectoryTreeBean> treeNode : this.listData) {
                if (treeNode.getCustomerLevel() == 2 && listVid.contains(treeNode.getValue().getContentItem().getVid())) {
                    TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = this.adapter;
                    if (treeNodeAdapter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                        treeNodeAdapter = null;
                    }
                    treeNodeAdapter.notifyTreeNode(treeNode);
                }
                if (treeNode.getCustomerLevel() == 0) {
                    List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
                    if (!(children == null || children.isEmpty())) {
                        List<TreeNode<CourseDirectoryTreeBean>> child = treeNode.getChildren();
                        Intrinsics.checkNotNullExpressionValue(child, "child");
                        Iterator it = child.iterator();
                        while (it.hasNext()) {
                            TreeNode treeNode2 = (TreeNode) it.next();
                            if (treeNode2.getCustomerLevel() == 1) {
                                List<TreeNode> child2 = treeNode2.getChildren();
                                List list = child2;
                                if (!(list == null || list.isEmpty())) {
                                    Intrinsics.checkNotNullExpressionValue(child2, "child2");
                                    for (TreeNode treeNode3 : child2) {
                                        if (treeNode3.getCustomerLevel() == 2 && listVid.contains(((CourseDirectoryTreeBean) treeNode3.getValue()).getContentItem().getVid())) {
                                            TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter2 = this.adapter;
                                            if (treeNodeAdapter2 == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                                treeNodeAdapter2 = null;
                                            }
                                            treeNodeAdapter2.notifyTreeNode(treeNode);
                                        }
                                    }
                                }
                            } else if (treeNode2.getCustomerLevel() == 2 && listVid.contains(((CourseDirectoryTreeBean) treeNode2.getValue()).getContentItem().getVid())) {
                                TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter3 = this.adapter;
                                if (treeNodeAdapter3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                    treeNodeAdapter3 = null;
                                }
                                treeNodeAdapter3.notifyTreeNode(treeNode);
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            Log.d("error:", "updateTreeNode: " + e2.getMessage() + ' ');
        }
    }

    @NotNull
    public final Function5<String, String, String, String, CourseDirectoryContentItem, Unit> getClickPlayListener2() {
        return this.clickPlayListener2;
    }

    @Nullable
    public final String getSetSeeDuration() {
        return this.setSeeDuration;
    }

    @Nullable
    public final String getSetVid() {
        return this.setVid;
    }

    @Override // com.psychiatrygarden.widget.BaseContentWidget
    public void initData(@NotNull GoodsDetailItem data) {
        Intrinsics.checkNotNullParameter(data, "data");
        String goodsId = data.getGoodsId();
        Intrinsics.checkNotNullExpressionValue(goodsId, "data.goodsId");
        initParams$default(this, goodsId, data.getCourseData().getType(), false, data.getCourseData().hasPermission(), null, false, 32, null);
    }

    public final void initParams(@NotNull String courseId, @NotNull String type, boolean fromViewPlayPage, boolean havePermission, @Nullable String currentVid, boolean showTitle) {
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        Intrinsics.checkNotNullParameter(type, "type");
        initParams2(courseId, type, fromViewPlayPage, havePermission, currentVid, showTitle, false);
    }

    public final void initParams2(@NotNull String courseId, @NotNull String type, boolean fromViewPlayPage, boolean havePermission, @Nullable String currentVid, boolean showTitle, boolean foldDire) {
        Intrinsics.checkNotNullParameter(courseId, "courseId");
        Intrinsics.checkNotNullParameter(type, "type");
        this.mFoldDire = foldDire;
        this.mCourseId = courseId;
        View viewFindViewById = findViewById(R.id.rvCourseDirectory);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvCourseDirectory)");
        this.rvCourseDirectory = (RecyclerView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.viewGap);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.viewGap)");
        this.viewGap = viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.emptyView);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.emptyView)");
        this.emptyView = (CustomEmptyView) viewFindViewById3;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_wait_update_night_svg : R.drawable.ic_empty_data_wait_update_day_svg;
        CustomEmptyView customEmptyView = this.emptyView;
        CustomEmptyView customEmptyView2 = null;
        if (customEmptyView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView = null;
        }
        customEmptyView.uploadEmptyViewResUi();
        CustomEmptyView customEmptyView3 = this.emptyView;
        if (customEmptyView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView3 = null;
        }
        customEmptyView3.setImgEmptyRes(i2);
        CustomEmptyView customEmptyView4 = this.emptyView;
        if (customEmptyView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView4 = null;
        }
        customEmptyView4.setEmptyTextStr("内容更新中");
        CustomEmptyView customEmptyView5 = this.emptyView;
        if (customEmptyView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            customEmptyView5 = null;
        }
        customEmptyView5.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DetailCourseDirectoryWidget.initParams2$lambda$0(this.f16307c, view);
            }
        });
        this.courseType = type;
        if (!TextUtils.isEmpty(currentVid)) {
            Intrinsics.checkNotNull(currentVid);
            this.playingVid = currentVid;
            this.playingChapterId = CourseDataSpUtilKt.readPlayingChapterId(courseId, getContext(), "");
        }
        this.havePermission = havePermission;
        if (fromViewPlayPage || !showTitle) {
            this.landscapeScreen = fromViewPlayPage;
            View viewFindViewById4 = findViewById(R.id.title);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById<View>(R.id.title)");
            ViewExtensionsKt.gone(viewFindViewById4);
            View viewFindViewById5 = findViewById(R.id.view_line_bottom);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById<View>(R.id.view_line_bottom)");
            ViewExtensionsKt.gone(viewFindViewById5);
            if (fromViewPlayPage) {
                findViewById(R.id.line).setBackground(new ColorDrawable(Color.parseColor("#252C46")));
                RecyclerView recyclerView = this.rvCourseDirectory;
                if (recyclerView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                    recyclerView = null;
                }
                recyclerView.setBackgroundColor(getContext().getColor(R.color.color_14));
                ((LinearLayout) findViewById(R.id.layoutRoot)).setBackground(new ColorDrawable(getContext().getColor(R.color.color_14)));
            }
            if (fromViewPlayPage) {
                View viewFindViewById6 = findViewById(R.id.line);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById<View>(R.id.line)");
                ViewExtensionsKt.visible(viewFindViewById6);
            } else {
                View viewFindViewById7 = findViewById(R.id.line);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById<View>(R.id.line)");
                ViewExtensionsKt.gone(viewFindViewById7);
            }
        } else {
            RecyclerView recyclerView2 = this.rvCourseDirectory;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvCourseDirectory");
                recyclerView2 = null;
            }
            recyclerView2.setBackground(getContext().getDrawable(R.drawable.bg_course_detail_directory));
            View viewFindViewById8 = findViewById(R.id.title);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<View>(R.id.title)");
            ViewExtensionsKt.visible(viewFindViewById8);
            View viewFindViewById9 = findViewById(R.id.line);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<View>(R.id.line)");
            ViewExtensionsKt.visible(viewFindViewById9);
        }
        if (this.landscapeScreen) {
            CustomEmptyView customEmptyView6 = this.emptyView;
            if (customEmptyView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView2 = customEmptyView6;
            }
            ViewExtensionsKt.gone(customEmptyView2);
        }
        getListData();
    }

    public final void setClickListenerCallBack(@NotNull Function5<? super String, ? super String, ? super String, ? super String, ? super CourseDirectoryContentItem, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.clickPlayListener2 = action;
    }

    public final void setClickPlayListener2(@NotNull Function5<? super String, ? super String, ? super String, ? super String, ? super CourseDirectoryContentItem, Unit> function5) {
        Intrinsics.checkNotNullParameter(function5, "<set-?>");
        this.clickPlayListener2 = function5;
    }

    public final void setSetSeeDuration(@Nullable String str) {
        this.setSeeDuration = str;
    }

    public final void setSetVid(@Nullable String str) {
        this.setVid = str;
    }

    public final void updateDownloadedFlag(@NotNull List<String> vidList) {
        Intrinsics.checkNotNullParameter(vidList, "vidList");
        Log.d("下载完成", "updateDownloadedFlag: " + vidList);
        if (!vidList.isEmpty()) {
            getListData();
        }
    }

    public final void updateVideoProgress(@NotNull String vid, @NotNull String seeDuration) {
        Intrinsics.checkNotNullParameter(vid, "vid");
        Intrinsics.checkNotNullParameter(seeDuration, "seeDuration");
        this.setVid = vid;
        this.setSeeDuration = seeDuration;
        getListData();
    }

    public final void updateVideoProgressLand(@NotNull String vid, @NotNull String seeDuration) {
        Intrinsics.checkNotNullParameter(vid, "vid");
        Intrinsics.checkNotNullParameter(seeDuration, "seeDuration");
        this.setVid = vid;
        this.setSeeDuration = seeDuration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DetailCourseDirectoryWidget(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.listData = new ArrayList();
        this.data = new ArrayList();
        this.lastVideoIdChapter = new ArrayList();
        this.allMap = new HashMap<>();
        this.haveVidMap = new HashMap<>();
        this.seeMap = new HashMap<>();
        this.freeSeeMap = new HashMap<>();
        this.lastLearnVid = "";
        this.lastLearnChapterId = "";
        this.expandFirstId = "";
        this.expandSecondId = "";
        this.currentVid = "";
        this.courseType = "0";
        this.havePermission = true;
        this.playingVid = "-1";
        this.playingChapterId = "-1";
        this.expandFirstIdList = new ArrayList();
        this.expandSecondIdList = new ArrayList();
        this.setVid = "";
        this.setSeeDuration = "";
        this.clickPlayListener2 = new Function5<String, String, String, String, CourseDirectoryContentItem, Unit>() { // from class: com.psychiatrygarden.widget.DetailCourseDirectoryWidget$clickPlayListener2$1
            @Override // kotlin.jvm.functions.Function5
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3, String str4, CourseDirectoryContentItem courseDirectoryContentItem) {
                invoke2(str, str2, str3, str4, courseDirectoryContentItem);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String rootChapterId, @NotNull String childChapterId, @NotNull String rootTitle, @NotNull String childTitle, @NotNull CourseDirectoryContentItem courseDirectoryContentItem) {
                Intrinsics.checkNotNullParameter(rootChapterId, "rootChapterId");
                Intrinsics.checkNotNullParameter(childChapterId, "childChapterId");
                Intrinsics.checkNotNullParameter(rootTitle, "rootTitle");
                Intrinsics.checkNotNullParameter(childTitle, "childTitle");
                Intrinsics.checkNotNullParameter(courseDirectoryContentItem, "courseDirectoryContentItem");
                System.out.println((Object) ("点击的vid:" + courseDirectoryContentItem.getVid()));
            }
        };
        View.inflate(context, R.layout.layout_course_detail_directory, this);
        if (isInEditMode()) {
            return;
        }
        initLastVideId();
    }
}
