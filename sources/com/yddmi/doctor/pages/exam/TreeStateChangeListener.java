package com.yddmi.doctor.pages.exam;

import com.yddmi.doctor.entity.result.SkillHome;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lcom/yddmi/doctor/pages/exam/TreeStateChangeListener;", "", "onClose", "", "treeItem", "Lcom/yddmi/doctor/entity/result/SkillHome;", "position", "", "onOpen", "onOpenOnlyOne", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface TreeStateChangeListener {
    void onClose(@NotNull SkillHome treeItem, int position);

    void onOpen(@NotNull SkillHome treeItem, int position);

    void onOpenOnlyOne(@NotNull SkillHome treeItem, int position);
}
