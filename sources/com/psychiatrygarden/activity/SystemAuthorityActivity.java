package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SystemAuthorityActivity extends BaseActivity {
    public BaseQuickAdapter<SysAuthBean, BaseViewHolder> adapter;
    public List<SysAuthBean> mlists = new ArrayList();
    public RecyclerView recycle;

    public static class SysAuthBean implements Serializable {
        private String hint;
        private SecendBean mSecendBean;
        private String name;

        public static class SecendBean implements Serializable {
            private String doThings;
            private String doThingsContent;
            private String hintweb;
            private String title;
            private String titleContent;
            private String whyThings;
            private String whyThingsContent;

            public String getDoThings() {
                return this.doThings;
            }

            public String getDoThingsContent() {
                return this.doThingsContent;
            }

            public String getHintweb() {
                return this.hintweb;
            }

            public String getTitle() {
                return this.title;
            }

            public String getTitleContent() {
                return this.titleContent;
            }

            public String getWhyThings() {
                return this.whyThings;
            }

            public String getWhyThingsContent() {
                return this.whyThingsContent;
            }

            public SecendBean setDoThings(String doThings) {
                this.doThings = doThings;
                return this;
            }

            public void setDoThingsContent(String doThingsContent) {
                this.doThingsContent = doThingsContent;
            }

            public void setHintweb(String hintweb) {
                this.hintweb = hintweb;
            }

            public SecendBean setTitle(String title) {
                this.title = title;
                return this;
            }

            public SecendBean setTitleContent(String titleContent) {
                this.titleContent = titleContent;
                return this;
            }

            public void setWhyThings(String whyThings) {
                this.whyThings = whyThings;
            }

            public void setWhyThingsContent(String whyThingsContent) {
                this.whyThingsContent = whyThingsContent;
            }
        }

        public String getHint() {
            return this.hint;
        }

        public String getName() {
            return this.name;
        }

        public SecendBean getmSecendBean() {
            return this.mSecendBean;
        }

        public SysAuthBean setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public SysAuthBean setName(String name) {
            this.name = name;
            return this;
        }

        public void setmSecendBean(SecendBean mSecendBean) {
            this.mSecendBean = mSecendBean;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) SenSysAuthorActivity.class);
        intent.putExtra(NotificationCompat.CATEGORY_SYSTEM, this.mlists.get(i2).getmSecendBean());
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SysAuthBean sysAuthBean = new SysAuthBean();
        sysAuthBean.setName(String.format(Locale.CHINA, "允许%s访问相册权限", getString(R.string.app_name)));
        sysAuthBean.setHint("实现您图片的取用与上传");
        SysAuthBean.SecendBean secendBean = new SysAuthBean.SecendBean();
        secendBean.setTitle("为什么需要获取我的相册");
        secendBean.setTitleContent(String.format(Locale.CHINA, "我们访问您的相册可以帮助您能顺利读取您的相册图片，确保本地图片可以上传至%s功能可行，便于您进行头像更换、发表和分享帖子或与客服沟通时证明您所遇到的问题", getString(R.string.app_name)));
        secendBean.setDoThings("如何进行权限设置？能否停止授权？");
        secendBean.setDoThingsContent("您可以随时在手机上通过相关设置，进行权限的授予或收回。");
        secendBean.setWhyThings("如何保证我的信息安全？");
        secendBean.setWhyThingsContent("我们会采取符合业界标准、合理可行的安全防护措施保护您提供个人信息安全，防止个人信息遭到未经授权、访问、公开披露、使用、修改、损坏或丢失。我们有行业先进的数据为核心，围绕数据生命周期进行的数据安全管理体系，从组织建设、制度设计、人员管理、产品技术等方面多维度提升整个系统的安全性。关于我们对于您个人信息的手机、使用、共享的详尽方式以及我们所能提供的个人信息安全技术措施，您可查看相应产品或服务的隐私政策。");
        secendBean.setHintweb("阅读隐私政策");
        sysAuthBean.setmSecendBean(secendBean);
        this.mlists.add(sysAuthBean);
        SysAuthBean sysAuthBean2 = new SysAuthBean();
        sysAuthBean2.setName(String.format(Locale.CHINA, "允许%s访问相机权限", getString(R.string.app_name)));
        sysAuthBean2.setHint("实现拍摄照片并上传");
        SysAuthBean.SecendBean secendBean2 = new SysAuthBean.SecendBean();
        secendBean2.setTitle("为什么需要获取我的相机");
        secendBean2.setTitleContent(String.format(Locale.CHINA, "我们访问您的相机是为了使您进行拍照可以上传至%s功能可行，便于您进行头像更换、发表和分享帖子或与客服沟通时证明您所遇到的问题", getString(R.string.app_name)));
        secendBean2.setDoThings("如何进行权限设置？能否停止授权？");
        secendBean2.setDoThingsContent("您可以随时在手机上通过相关设置，进行权限的授予或收回。");
        secendBean2.setWhyThings("如何保证我的信息安全？");
        secendBean2.setWhyThingsContent("我们会采取符合业界标准、合理可行的安全防护措施保护您提供个人信息安全，防止个人信息遭到未经授权、访问、公开披露、使用、修改、损坏或丢失。我们有行业先进的数据为核心，围绕数据生命周期进行的数据安全管理体系，从组织建设、制度设计、人员管理、产品技术等方面多维度提升整个系统的安全性。关于我们对于您个人信息的手机、使用、共享的详尽方式以及我们所能提供的个人信息安全技术措施，您可查看相应产品或服务的隐私政策。");
        secendBean2.setHintweb("阅读隐私政策");
        sysAuthBean2.setmSecendBean(secendBean2);
        this.mlists.add(sysAuthBean2);
        SysAuthBean sysAuthBean3 = new SysAuthBean();
        sysAuthBean3.setName(getString(R.string.app_phone_permission));
        sysAuthBean3.setHint("实现设备唯一，保持账号只能一个设备登录的安全");
        SysAuthBean.SecendBean secendBean3 = new SysAuthBean.SecendBean();
        secendBean3.setTitle("为什么需要获取我的电话权限");
        secendBean3.setTitleContent("我们访问您的电话是为了获取设备唯一码，进一步的使您的账号登录保持唯一性，也可以根据您设备的唯一码确认您的第一台注册设备，防止账号被盗或者手机号注销之后无法提供相应证明导致无法更改，导致记录丢失问题。");
        secendBean3.setDoThings("如何进行权限设置？能否停止授权？");
        secendBean3.setDoThingsContent("您可以随时在手机上通过相关设置，进行权限的授予或收回。");
        secendBean3.setWhyThings("如何保证我的信息安全？");
        secendBean3.setWhyThingsContent("我们会采取符合业界标准、合理可行的安全防护措施保护您提供个人信息安全，防止个人信息遭到未经授权、访问、公开披露、使用、修改、损坏或丢失。我们有行业先进的数据为核心，围绕数据生命周期进行的数据安全管理体系，从组织建设、制度设计、人员管理、产品技术等方面多维度提升整个系统的安全性。关于我们对于您个人信息的手机、使用、共享的详尽方式以及我们所能提供的个人信息安全技术措施，您可查看相应产品或服务的隐私政策。");
        secendBean3.setHintweb("阅读隐私政策");
        sysAuthBean3.setmSecendBean(secendBean3);
        this.mlists.add(sysAuthBean3);
        SysAuthBean sysAuthBean4 = new SysAuthBean();
        sysAuthBean4.setName(getString(R.string.app_storage_permission));
        sysAuthBean4.setHint("实现视频、文件下载");
        SysAuthBean.SecendBean secendBean4 = new SysAuthBean.SecendBean();
        secendBean4.setTitle("为什么需要获取我的存储权限");
        secendBean4.setTitleContent("我们访问您的存储权限是为了方便您下载文件、视频等功能");
        secendBean4.setDoThings("如何进行权限设置？能否停止授权？");
        secendBean4.setDoThingsContent("您可以随时在手机上通过相关设置，进行权限的授予或收回。");
        secendBean4.setWhyThings("如何保证我的信息安全？");
        secendBean4.setWhyThingsContent("我们会采取符合业界标准、合理可行的安全防护措施保护您提供个人信息安全，防止个人信息遭到未经授权、访问、公开披露、使用、修改、损坏或丢失。我们有行业先进的数据为核心，围绕数据生命周期进行的数据安全管理体系，从组织建设、制度设计、人员管理、产品技术等方面多维度提升整个系统的安全性。关于我们对于您个人信息的手机、使用、共享的详尽方式以及我们所能提供的个人信息安全技术措施，您可查看相应产品或服务的隐私政策。");
        secendBean4.setHintweb("阅读隐私政策");
        sysAuthBean4.setmSecendBean(secendBean4);
        this.mlists.add(sysAuthBean4);
        SysAuthBean sysAuthBean5 = new SysAuthBean();
        sysAuthBean5.setName(getString(R.string.app_location_permission));
        sysAuthBean5.setHint("方便您在聊天中发送位置时能够获取精准位置信息");
        SysAuthBean.SecendBean secendBean5 = new SysAuthBean.SecendBean();
        secendBean5.setTitle("为什么需要获取我的位置权限");
        secendBean5.setTitleContent("我们访问您的位置权限是为了方便您在聊天中发送位置时能够获取精准位置信息");
        secendBean5.setDoThings("如何进行权限设置？能否停止授权？");
        secendBean5.setDoThingsContent("您可以随时在手机上通过相关设置，进行权限的授予或收回。");
        secendBean5.setWhyThings("如何保证我的信息安全？");
        secendBean5.setWhyThingsContent("我们会采取符合业界标准、合理可行的安全防护措施保护您提供个人信息安全，防止个人信息遭到未经授权、访问、公开披露、使用、修改、损坏或丢失。我们有行业先进的数据为核心，围绕数据生命周期进行的数据安全管理体系，从组织建设、制度设计、人员管理、产品技术等方面多维度提升整个系统的安全性。关于我们对于您个人信息的手机、使用、共享的详尽方式以及我们所能提供的个人信息安全技术措施，您可查看相应产品或服务的隐私政策。");
        secendBean5.setHintweb("阅读隐私政策");
        sysAuthBean5.setmSecendBean(secendBean5);
        this.mlists.add(sysAuthBean5);
        BaseQuickAdapter<SysAuthBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SysAuthBean, BaseViewHolder>(R.layout.layout_sys_auth_item, this.mlists) { // from class: com.psychiatrygarden.activity.SystemAuthorityActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, SysAuthBean item) {
                TextView textView = (TextView) helper.getView(R.id.title);
                TextView textView2 = (TextView) helper.getView(R.id.hint);
                textView.setText(item.name);
                textView2.setText(item.hint);
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycle.setAdapter(baseQuickAdapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.po
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f13553c.lambda$init$0(baseQuickAdapter2, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统权限");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_sys_auth);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
