package com.tencent.liteav;

import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.basic.module.StatusBucket;
import com.tencent.liteav.basic.module.TXCStatus;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class a {
    public static void a(String str, ArrayList<String> arrayList) {
        StatusBucket status = TXCAudioEngine.getInstance().getStatus(1);
        TXCStatus.a(str, 14003, Integer.valueOf(status.getIntStatus("18446744073709551615", 10001)));
        TXCStatus.a(str, R2.dimen.mtrl_calendar_action_padding, Integer.valueOf(status.getIntStatus("18446744073709551615", 10002)));
        TXCStatus.a(str, R2.id.cb_color_one, Integer.valueOf(status.getIntStatus("18446744073709551615", 10000)));
        TXCStatus.a(str, 14002, Integer.valueOf(status.getIntStatus("18446744073709551615", 10003)));
        TXCStatus.a(str, R2.id.category_layout, Integer.valueOf(status.getIntStatus("18446744073709551615", 10004)));
        TXCStatus.a(str, R2.id.cb_color_three, Integer.valueOf(status.getIntStatus("18446744073709551615", 10005)));
        TXCStatus.a(str, R2.id.rl_ask_bar, Integer.valueOf(status.getIntStatus("18446744073709551615", 10006)));
        if (arrayList == null) {
            return;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            int intStatus = status.getIntStatus(next, R2.drawable.ic_combine_question_ai2_night);
            int intStatus2 = status.getIntStatus(next, R2.drawable.ic_combine_question_ai3);
            TXCStatus.a(next, R2.id.rl_bg_container, Integer.valueOf(intStatus));
            TXCStatus.a(next, R2.id.rl_bootom, Integer.valueOf(intStatus2));
            int i2 = 0;
            TXCStatus.a(next, 2001, Integer.valueOf(intStatus2 > 0 ? intStatus / intStatus2 : 0));
            TXCStatus.a(next, 2002, Integer.valueOf(intStatus2));
            TXCStatus.a(next, 2005, Integer.valueOf(intStatus2));
            TXCStatus.a(next, 2004, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_load_data_failed_night)));
            TXCStatus.a(next, R2.attr.in_circle_color, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_local_download_success)));
            TXCStatus.a(next, R2.attr.indeterminateAnimationType, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_local_file_exist)));
            TXCStatus.a(next, 2014, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_login_check_checked)));
            TXCStatus.a(next, 2008, Integer.valueOf(status.getIntStatus(next, 10112)));
            TXCStatus.a(next, 2010, Integer.valueOf(status.getIntStatus(next, 10106)));
            TXCStatus.a(next, 2007, Integer.valueOf(status.getIntStatus(next, 10105)));
            TXCStatus.a(next, 2011, Integer.valueOf(status.getIntStatus(next, 10107)));
            TXCStatus.a(next, R2.id.right_image, Integer.valueOf(status.getIntStatus(next, 10103)));
            TXCStatus.a(next, 18002, Integer.valueOf(status.getIntStatus(next, 10104)));
            TXCStatus.a(next, R2.id.right_to_left, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_normal_day)));
            TXCStatus.a(next, R2.id.rightcheck, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_normal_night)));
            TXCStatus.a(next, R2.id.rightimg, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_note_day)));
            TXCStatus.a(next, R2.id.rlFriendNotice, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_note_night)));
            TXCStatus.a(next, R2.id.ring, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_private_msg_day)));
            int intStatus3 = status.getIntStatus(next, R2.drawable.ic_empty_data_normal_night);
            int intStatus4 = status.getIntStatus(next, R2.drawable.ic_empty_data_normal_day);
            if (intStatus4 > 0) {
                i2 = (intStatus3 * 100) / intStatus4;
            }
            TXCStatus.a(next, R2.id.rl, Integer.valueOf(i2));
            TXCStatus.a(next, R2.id.rl_analyze_user, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_error_question_day)));
            TXCStatus.a(next, R2.attr.indeterminateProgressStyle, Integer.valueOf(status.getIntStatus(next, 10110)));
            TXCStatus.a(next, R2.attr.indexPrefixes, Integer.valueOf(status.getIntStatus(next, 10111)));
            TXCStatus.a(next, R2.id.rl_apply_num, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_error_question_night)));
            TXCStatus.a(next, R2.id.rl_askbar_layout, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_combine_question_ai2)));
            TXCStatus.a(next, R2.id.right_ly, Integer.valueOf(status.getIntStatus(next, 10100)));
            TXCStatus.a(next, 2019, Integer.valueOf(status.getIntStatus(next, 10100)));
            TXCStatus.a(next, 2020, Integer.valueOf(status.getIntStatus(next, 10101)));
            TXCStatus.a(next, R2.id.rlRichpushTitleBar, Integer.valueOf(status.getIntStatus(next, 10102)));
            TXCStatus.a(next, R2.id.rightlayout, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_private_msg_night)));
            TXCStatus.a(next, R2.id.rightswitch, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_wait_update_day_svg)));
            TXCStatus.a(next, R2.id.rimg, Integer.valueOf(status.getIntStatus(next, R2.drawable.ic_empty_data_wait_update_night_svg)));
            TXCStatus.a(next, 2021, Integer.valueOf(status.getIntStatus(next, 10109)));
        }
    }
}
