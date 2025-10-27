package com.psychiatrygarden.activity.courselist;

import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0012\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/ActCourseDownload;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "init", "", "onEventMainThread", "str", "", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ActCourseDownload extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, new CourseDownloadFmt()).commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setNewStyleStatusBarColor();
        setContentView(R.layout.act_course_download);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
