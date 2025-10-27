package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.DrawerPopupView;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.yikaobang.yixue.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function5;

/* loaded from: classes6.dex */
public class ChooseChapterDialog extends DrawerPopupView {
    private String courseId;
    private String courseTitle;
    private String currentSee;
    private String currentVid;
    private OnCourseSelectListener mSelectListener;

    public interface OnCourseSelectListener {
        void onCourseSelect(CourseDirectoryContentItem item, String rootChapterId, String childChapterId, String rootTitle, String childTitle);
    }

    public ChooseChapterDialog(@NonNull Context context, String vid, String courseId, String courseTitle, OnCourseSelectListener listener) {
        super(context);
        this.mSelectListener = listener;
        this.courseId = courseId;
        this.currentVid = vid;
        this.courseTitle = courseTitle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        findViewById(R.id.root).setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$2(String str, String str2, String str3, String str4, CourseDirectoryContentItem courseDirectoryContentItem) {
        OnCourseSelectListener onCourseSelectListener;
        if (TextUtils.isEmpty(courseDirectoryContentItem.getVid()) || (onCourseSelectListener = this.mSelectListener) == null) {
            return null;
        }
        onCourseSelectListener.onCourseSelect(courseDirectoryContentItem, str, str2, str3, str4);
        dismiss();
        return null;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_layout_course_directory;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16420c.lambda$onCreate$0(view);
            }
        });
        ((TextView) findViewById(R.id.tv_course_title)).setText(this.courseTitle);
        DetailCourseDirectoryWidget detailCourseDirectoryWidget = (DetailCourseDirectoryWidget) findViewById(R.id.directory);
        detailCourseDirectoryWidget.updateVideoProgressLand(this.currentVid, this.currentSee);
        detailCourseDirectoryWidget.initParams(this.courseId, "2", true, true, this.currentVid, true);
        detailCourseDirectoryWidget.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.f1
            @Override // java.lang.Runnable
            public final void run() {
                this.f16470c.lambda$onCreate$1();
            }
        }, 500L);
        detailCourseDirectoryWidget.setClickListenerCallBack(new Function5() { // from class: com.psychiatrygarden.widget.g1
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return this.f16500c.lambda$onCreate$2((String) obj, (String) obj2, (String) obj3, (String) obj4, (CourseDirectoryContentItem) obj5);
            }
        });
    }

    public void setCurrentSee(String currentSee) {
        this.currentSee = currentSee;
    }
}
