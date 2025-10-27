package com.aliyun.player.alivcplayerexpand.view.choice;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class AlivcActionListDialog extends Dialog {
    private static final int ANIMATION_DURATION = 200;
    private static final String TAG = "com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog";
    private View mContentView;
    private boolean mIsAnimating;
    OnChoiceItemListener onChoiceItemListener;

    public static class BottomListCheckBuilder {
        private AlivcActionListDialog alivcCheckItemDialog;
        private int mCheckedIndex;
        private Context mContext;
        private List<BottomCheckListItemData> mItems = new ArrayList();
        private DialogInterface.OnDismissListener onBottomDialogDismissListener;
        private OnCheckItemClickListener onCheckItemClickListener;
        private RecyclerView recyclerView;

        public static class BottomCheckListItemData {
            String tag;
            String type;
            String value;

            public BottomCheckListItemData(String str, String str2, String str3) {
                this.type = str;
                this.value = str2;
                this.tag = str3;
            }
        }

        public class CheckListAdapter extends RecyclerView.Adapter<ViewHolder> {

            public class ViewHolder extends RecyclerView.ViewHolder {
                LinearLayout bottomDialogListItem;
                TextView type;
                TextView value;

                public ViewHolder(View view) {
                    super(view);
                    this.bottomDialogListItem = (LinearLayout) view.findViewById(R.id.bottom_dialog_list_item);
                    this.type = (TextView) view.findViewById(R.id.bottom_dialog_list_item_type);
                    this.value = (TextView) view.findViewById(R.id.bottom_dialog_list_item_value);
                }
            }

            public CheckListAdapter() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return BottomListCheckBuilder.this.mItems.size();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i2) {
                return super.getItemViewType(i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(ViewHolder viewHolder, final int i2) {
                viewHolder.type.setText(((BottomCheckListItemData) BottomListCheckBuilder.this.mItems.get(i2)).type);
                viewHolder.value.setText(((BottomCheckListItemData) BottomListCheckBuilder.this.mItems.get(i2)).value);
                viewHolder.bottomDialogListItem.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.BottomListCheckBuilder.CheckListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        BottomListCheckBuilder.this.onCheckItemClickListener.onClick(BottomListCheckBuilder.this.alivcCheckItemDialog, view, i2, ((BottomCheckListItemData) BottomListCheckBuilder.this.mItems.get(i2)).tag);
                    }
                });
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alivc_check_list_item, viewGroup, false));
            }
        }

        public interface OnCheckItemClickListener {
            void onClick(AlivcActionListDialog alivcActionListDialog, View view, int i2, String str);
        }

        public BottomListCheckBuilder(Context context) {
            this.mContext = context;
        }

        private View buildViews() {
            View viewInflate = View.inflate(this.mContext, getContentViewLayoutId(), null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_close_bottom_check);
            RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.check_list_view);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.BottomListCheckBuilder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (BottomListCheckBuilder.this.alivcCheckItemDialog != null) {
                        BottomListCheckBuilder.this.alivcCheckItemDialog.dismiss();
                    }
                }
            });
            recyclerView.getLayoutParams().height = getListMaxHeight();
            this.alivcCheckItemDialog.setOnChoiceItemListener(new OnChoiceItemListener() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.BottomListCheckBuilder.2
                @Override // com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.OnChoiceItemListener
                public void onShow() {
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
            recyclerView.setAdapter(new CheckListAdapter());
            return viewInflate;
        }

        public BottomListCheckBuilder addItem(String str, String str2) {
            this.mItems.add(new BottomCheckListItemData(str, str2, str));
            return this;
        }

        public AlivcActionListDialog build() {
            this.alivcCheckItemDialog = new AlivcActionListDialog(this.mContext);
            this.alivcCheckItemDialog.setContentView(buildViews(), new ViewGroup.LayoutParams(-1, -2));
            DialogInterface.OnDismissListener onDismissListener = this.onBottomDialogDismissListener;
            if (onDismissListener != null) {
                this.alivcCheckItemDialog.setOnDismissListener(onDismissListener);
            }
            return this.alivcCheckItemDialog;
        }

        public int getContentViewLayoutId() {
            return R.layout.alivc_check_list_view_layout;
        }

        public int getListMaxHeight() {
            return (int) (ScreenUtils.getHeight(this.mContext) * 0.5d);
        }

        public BottomListCheckBuilder setCheckedIndex(int i2) {
            this.mCheckedIndex = i2;
            return this;
        }

        public BottomListCheckBuilder setOnBottomDialogDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onBottomDialogDismissListener = onDismissListener;
            return this;
        }

        public BottomListCheckBuilder setOnCheckItemClickListener(OnCheckItemClickListener onCheckItemClickListener) {
            this.onCheckItemClickListener = onCheckItemClickListener;
            return this;
        }
    }

    public interface OnChoiceItemListener {
        void onShow();
    }

    public AlivcActionListDialog(@NonNull Context context) {
        super(context, R.style.BottomCheckDialog);
        this.mIsAnimating = false;
    }

    private void animateDown() {
        if (this.mContentView == null) {
            return;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setDuration(200L);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                AlivcActionListDialog.this.mIsAnimating = false;
                AlivcActionListDialog.this.mContentView.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcActionListDialog.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AlivcActionListDialog.super.dismiss();
                        } catch (Exception e2) {
                            Log.w(AlivcActionListDialog.TAG, "dismiss error\n" + Log.getStackTraceString(e2));
                        }
                    }
                });
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                AlivcActionListDialog.this.mIsAnimating = true;
            }
        });
        this.mContentView.startAnimation(animationSet);
    }

    private void animateUp() {
        if (this.mContentView != null) {
            return;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setDuration(200L);
        animationSet.setFillAfter(true);
        this.mContentView.startAnimation(animationSet);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        if (this.mIsAnimating) {
            return;
        }
        animateDown();
    }

    public View getContentView() {
        return this.mContentView;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -2;
        attributes.gravity = 81;
        int width = ScreenUtils.getWidth(getContext());
        int height = ScreenUtils.getHeight(getContext());
        if (width >= height) {
            width = height;
        }
        attributes.width = width;
        getWindow().setAttributes(attributes);
        setCanceledOnTouchOutside(true);
    }

    @Override // android.app.Dialog
    public void setContentView(int i2) {
        this.mContentView = LayoutInflater.from(getContext()).inflate(i2, (ViewGroup) null);
        super.setContentView(i2);
    }

    public void setOnChoiceItemListener(OnChoiceItemListener onChoiceItemListener) {
        this.onChoiceItemListener = onChoiceItemListener;
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        animateUp();
        OnChoiceItemListener onChoiceItemListener = this.onChoiceItemListener;
        if (onChoiceItemListener != null) {
            onChoiceItemListener.onShow();
        }
    }

    public AlivcActionListDialog(@NonNull Context context, int i2) {
        super(context, i2);
        this.mIsAnimating = false;
    }

    @Override // android.app.Dialog
    public void setContentView(@NonNull View view, @Nullable ViewGroup.LayoutParams layoutParams) {
        this.mContentView = view;
        super.setContentView(view, layoutParams);
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        this.mContentView = view;
    }
}
