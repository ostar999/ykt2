package com.aliyun.player.alivcplayerexpand.dialogfragment;

import android.view.View;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.svideo.common.base.BaseDialogFragment;

/* loaded from: classes2.dex */
public class AlivcShareDialogFragment extends BaseDialogFragment {
    public static AlivcShareDialogFragment newInstance() {
        return new AlivcShareDialogFragment();
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public void bindView(View view) {
        view.findViewById(R.id.alivc_tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.dialogfragment.AlivcShareDialogFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AlivcShareDialogFragment.this.dismiss();
            }
        });
        view.findViewById(R.id.iv_wx).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.dialogfragment.AlivcShareDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AlivcShareDialogFragment.this.dismiss();
            }
        });
        view.findViewById(R.id.iv_wb).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.dialogfragment.AlivcShareDialogFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AlivcShareDialogFragment.this.dismiss();
            }
        });
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getDialogAnimationRes() {
        return R.style.Dialog_Animation;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getLayoutRes() {
        return R.layout.alivc_video_dialogfragment_share;
    }
}
