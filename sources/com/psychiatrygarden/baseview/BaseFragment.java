package com.psychiatrygarden.baseview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.LoadDialogFragment;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public abstract class BaseFragment extends SupportFragment {
    public static ArrayList<SnsPlatform> platformsData = new ArrayList<>();
    private LoadDialogFragment loadingPopWindow;
    protected Context mContext;
    private View mRoot;
    private ViewHolder mViewHolder;

    private void initPlatforms() {
        platformsData.clear();
        ArrayList<SnsPlatform> arrayList = platformsData;
        SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
        arrayList.add(share_media.toSnsPlatform());
        platformsData.add(share_media.toSnsPlatform());
        platformsData.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platformsData.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$1(String str) {
        showProgressDialog();
        LoadDialogFragment loadDialogFragment = this.loadingPopWindow;
        if (loadDialogFragment == null || loadDialogFragment.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
            return;
        }
        this.loadingPopWindow.setDialog_text(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showToast, reason: merged with bridge method [inline-methods] */
    public void lambda$toastOnUiThread$0(String msg) {
        if (getActivity() == null || TextUtils.isEmpty(msg)) {
            return;
        }
        NewToast.showShort(getActivity(), msg);
    }

    public void AlertToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        NewToast.showShort(ProjectApp.instance(), msg, 0).show();
    }

    public boolean checkOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @LayoutRes
    public abstract int getLayoutId();

    public ViewHolder getViewHolder() {
        return this.mViewHolder;
    }

    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), activity);
        startActivity(intent);
    }

    public void hideProgressDialog() {
        LoadDialogFragment loadDialogFragment;
        if (getActivity() == null || (loadDialogFragment = this.loadingPopWindow) == null || !loadDialogFragment.isAdded()) {
            return;
        }
        this.loadingPopWindow.dismissAllowingStateLoss();
        this.loadingPopWindow = null;
    }

    public abstract void initViews(ViewHolder holder, View root);

    public boolean isLogin() {
        if (!UserConfig.getUserId().equals("")) {
            return true;
        }
        startActivity(new Intent(getActivity(), (Class<?>) LoginActivity.class));
        return false;
    }

    public boolean isNightMode() {
        return SkinManager.getCurrentSkinType(this.mContext) == 1;
    }

    public void mStartAnim(ImageView loadAnim) {
        ((Animatable) loadAnim.getDrawable()).start();
    }

    public void mStopAnim(ImageView loadAnim) {
        ((Animatable) loadAnim.getDrawable()).stop();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = this.mRoot;
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.mRoot);
            }
        } else {
            ViewHolder viewHolder = new ViewHolder(inflater, container, getLayoutId());
            this.mViewHolder = viewHolder;
            this.mRoot = viewHolder.getRootView();
            ViewHolder viewHolder2 = this.mViewHolder;
            initViews(viewHolder2, viewHolder2.getRootView());
        }
        return this.mViewHolder.getRootView();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mViewHolder.getRootView() != null && this.mViewHolder.getRootView().getParent() != null) {
            ((ViewGroup) this.mViewHolder.getRootView().getParent()).removeView(this.mViewHolder.getRootView());
        }
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    public void onEventMainThread(String str) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        initPlatforms();
        if (EventBus.getDefault().isRegistered(this)) {
            return;
        }
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    public void pointCount(Context mContext, String type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", type);
        YJYHttpUtils.get(mContext, NetworkRequestsURL.pointCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.baseview.BaseFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
            }
        });
    }

    public void showProgressDialog() {
        if (isAdded()) {
            if (this.loadingPopWindow == null) {
                this.loadingPopWindow = new LoadDialogFragment();
            }
            getChildFragmentManager().executePendingTransactions();
            if (this.loadingPopWindow.isAdded() || this.loadingPopWindow.isRemoving() || this.loadingPopWindow.isVisible()) {
                return;
            }
            this.loadingPopWindow.show(getChildFragmentManager(), "loadingfragment");
        }
    }

    public void toastOnUiThread(final String msg) {
        if (checkOnMainThread()) {
            lambda$toastOnUiThread$0(msg);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.psychiatrygarden.baseview.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15295c.lambda$toastOnUiThread$0(msg);
                }
            });
        }
    }

    public void goActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showProgressDialog(final String text) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.baseview.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15293c.lambda$showProgressDialog$1(text);
                }
            });
        }
    }
}
