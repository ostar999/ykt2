package top.defaults.view;

import java.util.List;
import top.defaults.view.PickerView;

/* loaded from: classes9.dex */
public interface Division extends PickerView.PickerItem {

    public static class Helper {
        public static String getCanonicalName(Division division) {
            StringBuilder sb = new StringBuilder(division.getText());
            while (division.getParent() != null) {
                division = division.getParent();
                sb.insert(0, division.getText());
            }
            return sb.toString();
        }
    }

    List<Division> getChildren();

    String getName();

    Division getParent();
}
