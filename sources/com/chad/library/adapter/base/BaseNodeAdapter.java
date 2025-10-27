package com.chad.library.adapter.base;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.DiffUtil;
import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.livescenes.upload.PLVDocumentUploadConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0010\b\u0002\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0016J\u0016\u0010\n\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0016J\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0014\u001a\u00020\u000b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0016J\u000e\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J2\u0010\u0017\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J<\u0010\u0017\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u001d\u001a\u00020\u00192\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J2\u0010\u001e\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J2\u0010\u001f\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J<\u0010\u001f\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010 \u001a\u00020\u00192\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J2\u0010!\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007JR\u0010\"\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010#\u001a\u00020\u00192\b\b\u0002\u0010$\u001a\u00020\u00192\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u001cH\u0007J2\u0010'\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J\u000e\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u0002J\u0010\u0010(\u001a\u00020\b2\b\b\u0001\u0010\r\u001a\u00020\bJ-\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u0019H\u0002¢\u0006\u0002\u0010-J\u0010\u0010.\u001a\u00020\u00192\u0006\u0010/\u001a\u00020\bH\u0014J\u0016\u00100\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002J\u001e\u00100\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0002J$\u00100\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u00020\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fJ\u0016\u00103\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u00104\u001a\u00020\u0002J\u0016\u00103\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u00020\bJ\u001c\u00105\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fJ\u001e\u00106\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0002J\u0010\u00107\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\bH\u0016J\u0010\u00108\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0002J\u0010\u00109\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0002J\u0018\u0010:\u001a\u00020\u000b2\u0006\u0010;\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u001e\u0010<\u001a\u00020\u000b2\u0006\u0010=\u001a\u00020>2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016J\"\u0010<\u001a\u00020\u000b2\u000e\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00042\b\u0010?\u001a\u0004\u0018\u00010@H\u0016J\u0018\u0010A\u001a\u00020\u000b2\u000e\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000fH\u0016J\u0018\u0010B\u001a\u00020\u000b2\u000e\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0004H\u0016R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lcom/chad/library/adapter/base/BaseNodeAdapter;", "Lcom/chad/library/adapter/base/BaseProviderMultiAdapter;", "Lcom/chad/library/adapter/base/entity/node/BaseNode;", "nodeList", "", "(Ljava/util/List;)V", "fullSpanNodeTypeSet", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "addData", "", "data", "position", "newData", "", "addFooterNodeProvider", com.umeng.analytics.pro.d.M, "Lcom/chad/library/adapter/base/provider/BaseNodeProvider;", "addFullSpanNodeProvider", "addItemProvider", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "addNodeProvider", "collapse", PLVDocumentUploadConstant.PPTConvertType.ANIMATE, "", "notify", "parentPayload", "", "isChangeChildCollapse", "collapseAndChild", "expand", "isChangeChildExpand", "expandAndChild", "expandAndCollapseOther", "isExpandedChild", "isCollapseChild", "expandPayload", "collapsePayload", "expandOrCollapse", "findParentNode", "node", "flatData", "list", "isExpanded", "(Ljava/util/Collection;Ljava/lang/Boolean;)Ljava/util/List;", "isFixedViewType", "type", "nodeAddData", "parentNode", "childIndex", "nodeRemoveData", "childNode", "nodeReplaceChildData", "nodeSetData", "removeAt", "removeChildAt", "removeNodesAt", PLVRxEncryptDataFunction.SET_DATA_METHOD, "index", "setDiffNewData", "diffResult", "Landroidx/recyclerview/widget/DiffUtil$DiffResult;", "commitCallback", "Ljava/lang/Runnable;", "setList", "setNewInstance", "com.github.CymChad.brvah"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class BaseNodeAdapter extends BaseProviderMultiAdapter<BaseNode> {

    @NotNull
    private final HashSet<Integer> fullSpanNodeTypeSet;

    public BaseNodeAdapter() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ BaseNodeAdapter(List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list);
    }

    private final int collapse(@IntRange(from = 0) int position, boolean isChangeChildCollapse, boolean animate, boolean notify, Object parentPayload) {
        BaseNode baseNode = getData().get(position);
        if (baseNode instanceof BaseExpandNode) {
            BaseExpandNode baseExpandNode = (BaseExpandNode) baseNode;
            if (baseExpandNode.getIsExpanded()) {
                int headerLayoutCount = position + getHeaderLayoutCount();
                baseExpandNode.setExpanded(false);
                List<BaseNode> childNode = baseNode.getChildNode();
                if (childNode == null || childNode.isEmpty()) {
                    notifyItemChanged(headerLayoutCount, parentPayload);
                    return 0;
                }
                List<BaseNode> childNode2 = baseNode.getChildNode();
                Intrinsics.checkNotNull(childNode2);
                List<BaseNode> listFlatData = flatData(childNode2, isChangeChildCollapse ? Boolean.FALSE : null);
                int size = listFlatData.size();
                getData().removeAll(listFlatData);
                if (notify) {
                    if (animate) {
                        notifyItemChanged(headerLayoutCount, parentPayload);
                        notifyItemRangeRemoved(headerLayoutCount + 1, size);
                    } else {
                        notifyDataSetChanged();
                    }
                }
                return size;
            }
        }
        return 0;
    }

    public static /* synthetic */ int collapse$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, boolean z4, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapse");
        }
        if ((i3 & 2) != 0) {
            z2 = false;
        }
        boolean z5 = z2;
        boolean z6 = (i3 & 4) != 0 ? true : z3;
        boolean z7 = (i3 & 8) != 0 ? true : z4;
        if ((i3 & 16) != 0) {
            obj = null;
        }
        return baseNodeAdapter.collapse(i2, z5, z6, z7, obj);
    }

    public static /* synthetic */ int collapseAndChild$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapseAndChild");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = true;
        }
        if ((i3 & 8) != 0) {
            obj = null;
        }
        return baseNodeAdapter.collapseAndChild(i2, z2, z3, obj);
    }

    private final int expand(@IntRange(from = 0) int position, boolean isChangeChildExpand, boolean animate, boolean notify, Object parentPayload) {
        BaseNode baseNode = getData().get(position);
        if (baseNode instanceof BaseExpandNode) {
            BaseExpandNode baseExpandNode = (BaseExpandNode) baseNode;
            if (!baseExpandNode.getIsExpanded()) {
                int headerLayoutCount = getHeaderLayoutCount() + position;
                baseExpandNode.setExpanded(true);
                List<BaseNode> childNode = baseNode.getChildNode();
                if (childNode == null || childNode.isEmpty()) {
                    notifyItemChanged(headerLayoutCount, parentPayload);
                    return 0;
                }
                List<BaseNode> childNode2 = baseNode.getChildNode();
                Intrinsics.checkNotNull(childNode2);
                List<BaseNode> listFlatData = flatData(childNode2, isChangeChildExpand ? Boolean.TRUE : null);
                int size = listFlatData.size();
                getData().addAll(position + 1, listFlatData);
                if (notify) {
                    if (animate) {
                        notifyItemChanged(headerLayoutCount, parentPayload);
                        notifyItemRangeInserted(headerLayoutCount + 1, size);
                    } else {
                        notifyDataSetChanged();
                    }
                }
                return size;
            }
        }
        return 0;
    }

    public static /* synthetic */ int expand$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, boolean z4, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expand");
        }
        if ((i3 & 2) != 0) {
            z2 = false;
        }
        boolean z5 = z2;
        boolean z6 = (i3 & 4) != 0 ? true : z3;
        boolean z7 = (i3 & 8) != 0 ? true : z4;
        if ((i3 & 16) != 0) {
            obj = null;
        }
        return baseNodeAdapter.expand(i2, z5, z6, z7, obj);
    }

    public static /* synthetic */ int expandAndChild$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandAndChild");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = true;
        }
        if ((i3 & 8) != 0) {
            obj = null;
        }
        return baseNodeAdapter.expandAndChild(i2, z2, z3, obj);
    }

    public static /* synthetic */ void expandAndCollapseOther$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, boolean z4, boolean z5, Object obj, Object obj2, int i3, Object obj3) {
        if (obj3 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandAndCollapseOther");
        }
        baseNodeAdapter.expandAndCollapseOther(i2, (i3 & 2) != 0 ? false : z2, (i3 & 4) != 0 ? true : z3, (i3 & 8) != 0 ? true : z4, (i3 & 16) == 0 ? z5 : true, (i3 & 32) != 0 ? null : obj, (i3 & 64) == 0 ? obj2 : null);
    }

    public static /* synthetic */ int expandOrCollapse$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expandOrCollapse");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = true;
        }
        if ((i3 & 8) != 0) {
            obj = null;
        }
        return baseNodeAdapter.expandOrCollapse(i2, z2, z3, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<BaseNode> flatData(Collection<? extends BaseNode> list, Boolean isExpanded) {
        BaseNode footerNode;
        ArrayList arrayList = new ArrayList();
        for (BaseNode baseNode : list) {
            arrayList.add(baseNode);
            if (baseNode instanceof BaseExpandNode) {
                if (Intrinsics.areEqual(isExpanded, Boolean.TRUE) || ((BaseExpandNode) baseNode).getIsExpanded()) {
                    List<BaseNode> childNode = baseNode.getChildNode();
                    if (!(childNode == null || childNode.isEmpty())) {
                        arrayList.addAll(flatData(childNode, isExpanded));
                    }
                }
                if (isExpanded != null) {
                    ((BaseExpandNode) baseNode).setExpanded(isExpanded.booleanValue());
                }
            } else {
                List<BaseNode> childNode2 = baseNode.getChildNode();
                if (!(childNode2 == null || childNode2.isEmpty())) {
                    arrayList.addAll(flatData(childNode2, isExpanded));
                }
            }
            if ((baseNode instanceof NodeFooterImp) && (footerNode = ((NodeFooterImp) baseNode).getFooterNode()) != null) {
                arrayList.add(footerNode);
            }
        }
        return arrayList;
    }

    public static /* synthetic */ List flatData$default(BaseNodeAdapter baseNodeAdapter, Collection collection, Boolean bool, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: flatData");
        }
        if ((i2 & 2) != 0) {
            bool = null;
        }
        return baseNodeAdapter.flatData(collection, bool);
    }

    private final int removeChildAt(int position) {
        if (position >= getData().size()) {
            return 0;
        }
        BaseNode baseNode = getData().get(position);
        List<BaseNode> childNode = baseNode.getChildNode();
        if (childNode == null || childNode.isEmpty()) {
            return 0;
        }
        if (!(baseNode instanceof BaseExpandNode)) {
            List<BaseNode> childNode2 = baseNode.getChildNode();
            Intrinsics.checkNotNull(childNode2);
            List listFlatData$default = flatData$default(this, childNode2, null, 2, null);
            getData().removeAll(listFlatData$default);
            return listFlatData$default.size();
        }
        if (!((BaseExpandNode) baseNode).getIsExpanded()) {
            return 0;
        }
        List<BaseNode> childNode3 = baseNode.getChildNode();
        Intrinsics.checkNotNull(childNode3);
        List listFlatData$default2 = flatData$default(this, childNode3, null, 2, null);
        getData().removeAll(listFlatData$default2);
        return listFlatData$default2.size();
    }

    private final int removeNodesAt(int position) {
        if (position >= getData().size()) {
            return 0;
        }
        int iRemoveChildAt = removeChildAt(position);
        getData().remove(position);
        int i2 = iRemoveChildAt + 1;
        Object obj = (BaseNode) getData().get(position);
        if (!(obj instanceof NodeFooterImp) || ((NodeFooterImp) obj).getFooterNode() == null) {
            return i2;
        }
        getData().remove(position);
        return i2 + 1;
    }

    public final void addFooterNodeProvider(@NotNull BaseNodeProvider provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        addFullSpanNodeProvider(provider);
    }

    public final void addFullSpanNodeProvider(@NotNull BaseNodeProvider provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        this.fullSpanNodeTypeSet.add(Integer.valueOf(provider.getItemViewType()));
        addItemProvider(provider);
    }

    @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
    public void addItemProvider(@NotNull BaseItemProvider<BaseNode> provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        if (!(provider instanceof BaseNodeProvider)) {
            throw new IllegalStateException("Please add BaseNodeProvider, no BaseItemProvider!");
        }
        super.addItemProvider(provider);
    }

    public final void addNodeProvider(@NotNull BaseNodeProvider provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        addItemProvider(provider);
    }

    @JvmOverloads
    public final int collapse(@IntRange(from = 0) int i2) {
        return collapse$default(this, i2, false, false, null, 14, null);
    }

    @JvmOverloads
    public final int collapse(@IntRange(from = 0) int i2, boolean z2) {
        return collapse$default(this, i2, z2, false, null, 12, null);
    }

    @JvmOverloads
    public final int collapse(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        return collapse$default(this, i2, z2, z3, null, 8, null);
    }

    @JvmOverloads
    public final int collapseAndChild(@IntRange(from = 0) int i2) {
        return collapseAndChild$default(this, i2, false, false, null, 14, null);
    }

    @JvmOverloads
    public final int collapseAndChild(@IntRange(from = 0) int i2, boolean z2) {
        return collapseAndChild$default(this, i2, z2, false, null, 12, null);
    }

    @JvmOverloads
    public final int collapseAndChild(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        return collapseAndChild$default(this, i2, z2, z3, null, 8, null);
    }

    @JvmOverloads
    public final int collapseAndChild(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return collapse(position, true, animate, notify, parentPayload);
    }

    @JvmOverloads
    public final int expand(@IntRange(from = 0) int i2) {
        return expand$default(this, i2, false, false, null, 14, null);
    }

    @JvmOverloads
    public final int expand(@IntRange(from = 0) int i2, boolean z2) {
        return expand$default(this, i2, z2, false, null, 12, null);
    }

    @JvmOverloads
    public final int expand(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        return expand$default(this, i2, z2, z3, null, 8, null);
    }

    @JvmOverloads
    public final int expandAndChild(@IntRange(from = 0) int i2) {
        return expandAndChild$default(this, i2, false, false, null, 14, null);
    }

    @JvmOverloads
    public final int expandAndChild(@IntRange(from = 0) int i2, boolean z2) {
        return expandAndChild$default(this, i2, z2, false, null, 12, null);
    }

    @JvmOverloads
    public final int expandAndChild(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        return expandAndChild$default(this, i2, z2, z3, null, 8, null);
    }

    @JvmOverloads
    public final int expandAndChild(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return expand(position, true, animate, notify, parentPayload);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2) {
        expandAndCollapseOther$default(this, i2, false, false, false, false, null, null, 126, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2, boolean z2) {
        expandAndCollapseOther$default(this, i2, z2, false, false, false, null, null, 124, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        expandAndCollapseOther$default(this, i2, z2, z3, false, false, null, null, 120, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2, boolean z2, boolean z3, boolean z4) {
        expandAndCollapseOther$default(this, i2, z2, z3, z4, false, null, null, 112, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2, boolean z2, boolean z3, boolean z4, boolean z5) {
        expandAndCollapseOther$default(this, i2, z2, z3, z4, z5, null, null, 96, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int i2, boolean z2, boolean z3, boolean z4, boolean z5, @Nullable Object obj) {
        expandAndCollapseOther$default(this, i2, z2, z3, z4, z5, obj, null, 64, null);
    }

    @JvmOverloads
    public final void expandAndCollapseOther(@IntRange(from = 0) int position, boolean isExpandedChild, boolean isCollapseChild, boolean animate, boolean notify, @Nullable Object expandPayload, @Nullable Object collapsePayload) {
        int i2;
        int size;
        int iExpand = expand(position, isExpandedChild, animate, notify, expandPayload);
        if (iExpand == 0) {
            return;
        }
        int iFindParentNode = findParentNode(position);
        int i3 = iFindParentNode == -1 ? 0 : iFindParentNode + 1;
        if (position - i3 > 0) {
            int i4 = i3;
            i2 = position;
            do {
                int iCollapse = collapse(i4, isCollapseChild, animate, notify, collapsePayload);
                i4++;
                i2 -= iCollapse;
            } while (i4 < i2);
        } else {
            i2 = position;
        }
        if (iFindParentNode == -1) {
            size = getData().size() - 1;
        } else {
            List<BaseNode> childNode = getData().get(iFindParentNode).getChildNode();
            size = iFindParentNode + (childNode != null ? childNode.size() : 0) + iExpand;
        }
        int i5 = i2 + iExpand;
        if (i5 < size) {
            int i6 = i5 + 1;
            while (i6 <= size) {
                int iCollapse2 = collapse(i6, isCollapseChild, animate, notify, collapsePayload);
                i6++;
                size -= iCollapse2;
            }
        }
    }

    @JvmOverloads
    public final int expandOrCollapse(@IntRange(from = 0) int i2) {
        return expandOrCollapse$default(this, i2, false, false, null, 14, null);
    }

    @JvmOverloads
    public final int expandOrCollapse(@IntRange(from = 0) int i2, boolean z2) {
        return expandOrCollapse$default(this, i2, z2, false, null, 12, null);
    }

    @JvmOverloads
    public final int expandOrCollapse(@IntRange(from = 0) int i2, boolean z2, boolean z3) {
        return expandOrCollapse$default(this, i2, z2, z3, null, 8, null);
    }

    @JvmOverloads
    public final int expandOrCollapse(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        BaseNode baseNode = getData().get(position);
        if (baseNode instanceof BaseExpandNode) {
            return ((BaseExpandNode) baseNode).getIsExpanded() ? collapse(position, false, animate, notify, parentPayload) : expand(position, false, animate, notify, parentPayload);
        }
        return 0;
    }

    public final int findParentNode(@NotNull BaseNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        int iIndexOf = getData().indexOf(node);
        if (iIndexOf != -1 && iIndexOf != 0) {
            for (int i2 = iIndexOf - 1; -1 < i2; i2--) {
                List<BaseNode> childNode = getData().get(i2).getChildNode();
                boolean z2 = false;
                if (childNode != null && childNode.contains(node)) {
                    z2 = true;
                }
                if (z2) {
                    return i2;
                }
            }
        }
        return -1;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || this.fullSpanNodeTypeSet.contains(Integer.valueOf(type));
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, @NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(data, "data");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode != null) {
            childNode.add(data);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).getIsExpanded()) {
                addData(getData().indexOf(parentNode) + childNode.size(), data);
            }
        }
    }

    public final void nodeRemoveData(@NotNull BaseNode parentNode, int childIndex) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode == null || childIndex >= childNode.size()) {
            return;
        }
        if ((parentNode instanceof BaseExpandNode) && !((BaseExpandNode) parentNode).getIsExpanded()) {
            childNode.remove(childIndex);
        } else {
            remove(getData().indexOf(parentNode) + 1 + childIndex);
            childNode.remove(childIndex);
        }
    }

    public final void nodeReplaceChildData(@NotNull BaseNode parentNode, @NotNull Collection<? extends BaseNode> newData) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(newData, "newData");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode != null) {
            if ((parentNode instanceof BaseExpandNode) && !((BaseExpandNode) parentNode).getIsExpanded()) {
                childNode.clear();
                childNode.addAll(newData);
                return;
            }
            int iIndexOf = getData().indexOf(parentNode);
            int iRemoveChildAt = removeChildAt(iIndexOf);
            childNode.clear();
            childNode.addAll(newData);
            List listFlatData$default = flatData$default(this, newData, null, 2, null);
            int i2 = iIndexOf + 1;
            getData().addAll(i2, listFlatData$default);
            int headerLayoutCount = i2 + getHeaderLayoutCount();
            if (iRemoveChildAt == listFlatData$default.size()) {
                notifyItemRangeChanged(headerLayoutCount, iRemoveChildAt);
            } else {
                notifyItemRangeRemoved(headerLayoutCount, iRemoveChildAt);
                notifyItemRangeInserted(headerLayoutCount, listFlatData$default.size());
            }
        }
    }

    public final void nodeSetData(@NotNull BaseNode parentNode, int childIndex, @NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(data, "data");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode == null || childIndex >= childNode.size()) {
            return;
        }
        if ((parentNode instanceof BaseExpandNode) && !((BaseExpandNode) parentNode).getIsExpanded()) {
            childNode.set(childIndex, data);
        } else {
            setData(getData().indexOf(parentNode) + 1 + childIndex, data);
            childNode.set(childIndex, data);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void removeAt(int position) {
        notifyItemRangeRemoved(position + getHeaderLayoutCount(), removeNodesAt(position));
        compatibilityDataSizeChanged(0);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setDiffNewData(@Nullable List<BaseNode> list, @Nullable Runnable commitCallback) {
        if (hasEmptyView()) {
            setNewInstance(list);
        } else {
            super.setDiffNewData(flatData$default(this, list != null ? list : new ArrayList(), null, 2, null), commitCallback);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setList(@Nullable Collection<? extends BaseNode> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        super.setList(flatData$default(this, list, null, 2, null));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setNewInstance(@Nullable List<BaseNode> list) {
        super.setNewInstance(flatData$default(this, list != null ? list : new ArrayList(), null, 2, null));
    }

    public BaseNodeAdapter(@Nullable List<BaseNode> list) {
        super(null);
        this.fullSpanNodeTypeSet = new HashSet<>();
        List<BaseNode> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        getData().addAll(flatData$default(this, list2, null, 2, null));
    }

    public static /* synthetic */ int collapse$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: collapse");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = true;
        }
        if ((i3 & 8) != 0) {
            obj = null;
        }
        return baseNodeAdapter.collapse(i2, z2, z3, obj);
    }

    public static /* synthetic */ int expand$default(BaseNodeAdapter baseNodeAdapter, int i2, boolean z2, boolean z3, Object obj, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: expand");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = true;
        }
        if ((i3 & 8) != 0) {
            obj = null;
        }
        return baseNodeAdapter.expand(i2, z2, z3, obj);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setData(int index, @NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(data, "data");
        int iRemoveNodesAt = removeNodesAt(index);
        List listFlatData$default = flatData$default(this, CollectionsKt__CollectionsKt.arrayListOf(data), null, 2, null);
        getData().addAll(index, listFlatData$default);
        if (iRemoveNodesAt == listFlatData$default.size()) {
            notifyItemRangeChanged(index + getHeaderLayoutCount(), iRemoveNodesAt);
        } else {
            notifyItemRangeRemoved(getHeaderLayoutCount() + index, iRemoveNodesAt);
            notifyItemRangeInserted(index + getHeaderLayoutCount(), listFlatData$default.size());
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void addData(int position, @NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(data, "data");
        addData(position, (Collection<? extends BaseNode>) CollectionsKt__CollectionsKt.arrayListOf(data));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void addData(@NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(data, "data");
        addData((Collection<? extends BaseNode>) CollectionsKt__CollectionsKt.arrayListOf(data));
    }

    public final int findParentNode(@IntRange(from = 0) int position) {
        if (position == 0) {
            return -1;
        }
        BaseNode baseNode = getData().get(position);
        for (int i2 = position - 1; -1 < i2; i2--) {
            List<BaseNode> childNode = getData().get(i2).getChildNode();
            boolean z2 = false;
            if (childNode != null && childNode.contains(baseNode)) {
                z2 = true;
            }
            if (z2) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void setDiffNewData(@NotNull DiffUtil.DiffResult diffResult, @NotNull List<BaseNode> list) {
        Intrinsics.checkNotNullParameter(diffResult, "diffResult");
        Intrinsics.checkNotNullParameter(list, "list");
        if (hasEmptyView()) {
            setNewInstance(list);
        } else {
            super.setDiffNewData(diffResult, flatData$default(this, list, null, 2, null));
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void addData(int position, @NotNull Collection<? extends BaseNode> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        super.addData(position, (Collection) flatData$default(this, newData, null, 2, null));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void addData(@NotNull Collection<? extends BaseNode> newData) {
        Intrinsics.checkNotNullParameter(newData, "newData");
        super.addData((Collection) flatData$default(this, newData, null, 2, null));
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, int childIndex, @NotNull BaseNode data) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(data, "data");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode != null) {
            childNode.add(childIndex, data);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).getIsExpanded()) {
                addData(getData().indexOf(parentNode) + 1 + childIndex, data);
            }
        }
    }

    public final void nodeRemoveData(@NotNull BaseNode parentNode, @NotNull BaseNode childNode) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(childNode, "childNode");
        List<BaseNode> childNode2 = parentNode.getChildNode();
        if (childNode2 != null) {
            if ((parentNode instanceof BaseExpandNode) && !((BaseExpandNode) parentNode).getIsExpanded()) {
                childNode2.remove(childNode);
            } else {
                remove((BaseNodeAdapter) childNode);
                childNode2.remove(childNode);
            }
        }
    }

    public final void nodeAddData(@NotNull BaseNode parentNode, int childIndex, @NotNull Collection<? extends BaseNode> newData) {
        Intrinsics.checkNotNullParameter(parentNode, "parentNode");
        Intrinsics.checkNotNullParameter(newData, "newData");
        List<BaseNode> childNode = parentNode.getChildNode();
        if (childNode != null) {
            childNode.addAll(childIndex, newData);
            if (!(parentNode instanceof BaseExpandNode) || ((BaseExpandNode) parentNode).getIsExpanded()) {
                addData(getData().indexOf(parentNode) + 1 + childIndex, newData);
            }
        }
    }

    @JvmOverloads
    public final int collapse(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return collapse(position, false, animate, notify, parentPayload);
    }

    @JvmOverloads
    public final int expand(@IntRange(from = 0) int position, boolean animate, boolean notify, @Nullable Object parentPayload) {
        return expand(position, false, animate, notify, parentPayload);
    }
}
