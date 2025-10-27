package com.psychiatrygarden.activity.purchase.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.psychiatrygarden.activity.purchase.beans.GroupInfo;
import com.psychiatrygarden.activity.purchase.beans.ProductInfo;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class ShopcartExpandableListViewAdapter extends BaseExpandableListAdapter {
    private CheckInterface checkInterface;
    private Map<String, List<ProductInfo>> children;
    private Context context;
    private List<GroupInfo> groups;
    private ModifyCountInterface modifyCountInterface;

    public interface CheckInterface {
        void checkChild(int groupPosition, int childPosition, boolean isChecked);

        void checkGroup(int groupPosition, boolean isChecked);
    }

    public static class ChildHolder {
        CheckBox cb_check;
        ImageView iv_adapter_list_pic;
        TextView iv_decrease;
        TextView iv_increase;
        TextView tv_count;
        TextView tv_price;
        TextView tv_product_desc;
        TextView tv_type;

        private ChildHolder() {
        }
    }

    public static class GroupHolder {
        CheckBox cb_check;
        TextView tv_group_name;

        private GroupHolder() {
        }
    }

    public interface ModifyCountInterface {
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }

    public ShopcartExpandableListViewAdapter(List<GroupInfo> groups, Map<String, List<ProductInfo>> children, Context context) {
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChildView$1(ProductInfo productInfo, ChildHolder childHolder, int i2, int i3, View view) {
        CheckBox checkBox = (CheckBox) view;
        productInfo.setChoosed(checkBox.isChecked());
        childHolder.cb_check.setChecked(checkBox.isChecked());
        this.checkInterface.checkChild(i2, i3, checkBox.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChildView$2(int i2, int i3, ChildHolder childHolder, View view) {
        this.modifyCountInterface.doIncrease(i2, i3, childHolder.tv_count, childHolder.cb_check.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChildView$3(int i2, int i3, ChildHolder childHolder, View view) {
        this.modifyCountInterface.doDecrease(i2, i3, childHolder.tv_count, childHolder.cb_check.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getGroupView$0(GroupInfo groupInfo, int i2, View view) {
        CheckBox checkBox = (CheckBox) view;
        groupInfo.setChoosed(checkBox.isChecked());
        this.checkInterface.checkGroup(i2, checkBox.isChecked());
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.children.get(this.groups.get(groupPosition).getId()).get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return 0L;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder childHolder;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(this.context, R.layout.item_shopcart_product, null);
            childHolder.cb_check = (CheckBox) convertView.findViewById(R.id.check_box);
            childHolder.tv_product_desc = (TextView) convertView.findViewById(R.id.tv_intro);
            childHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            childHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            childHolder.iv_increase = (TextView) convertView.findViewById(R.id.tv_add);
            childHolder.iv_decrease = (TextView) convertView.findViewById(R.id.tv_reduce);
            childHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_num);
            childHolder.iv_adapter_list_pic = (ImageView) convertView.findViewById(R.id.iv_adapter_list_pic);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        final ProductInfo productInfo = (ProductInfo) getChild(groupPosition, childPosition);
        if (productInfo != null) {
            childHolder.tv_type.setText(productInfo.getDesc());
            childHolder.tv_product_desc.setText(productInfo.getName());
            childHolder.tv_price.setText(String.format("￥%s", productInfo.getPrice() + ""));
            childHolder.tv_count.setText(String.valueOf(productInfo.getCount()));
            childHolder.cb_check.setChecked(productInfo.isChoosed());
            new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.imgplacehodel_image).showImageForEmptyUri(R.drawable.imgplacehodel_image).showImageOnFail(R.drawable.imgplacehodel_image).cacheInMemory(true).cacheOnDisc(true).build();
            GlideUtils.loadImage(childHolder.iv_adapter_list_pic.getContext(), productInfo.getImageUrl(), childHolder.iv_adapter_list_pic, R.drawable.imgplacehodel_image, R.drawable.imgplacehodel_image);
            final ChildHolder childHolder2 = childHolder;
            childHolder.cb_check.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.adapter.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13679c.lambda$getChildView$1(productInfo, childHolder2, groupPosition, childPosition, view);
                }
            });
            childHolder.iv_increase.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.adapter.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13684c.lambda$getChildView$2(groupPosition, childPosition, childHolder, view);
                }
            });
            childHolder.iv_decrease.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.adapter.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13688c.lambda$getChildView$3(groupPosition, childPosition, childHolder, view);
                }
            });
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.children.get(this.groups.get(groupPosition).getId()).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.groups.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return 0L;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = View.inflate(this.context, R.layout.item_shopcart_group, null);
            groupHolder.cb_check = (CheckBox) convertView.findViewById(R.id.determine_chekbox);
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_source_name);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        final GroupInfo groupInfo = (GroupInfo) getGroup(groupPosition);
        if (groupInfo != null) {
            groupHolder.tv_group_name.setText(groupInfo.getName());
            groupHolder.cb_check.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.adapter.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f13676c.lambda$getGroupView$0(groupInfo, groupPosition, view);
                }
            });
            groupHolder.cb_check.setChecked(groupInfo.isChoosed());
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }
}
