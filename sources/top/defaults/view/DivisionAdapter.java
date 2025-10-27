package top.defaults.view;

import java.util.List;
import top.defaults.view.PickerView;

/* loaded from: classes9.dex */
public class DivisionAdapter extends PickerView.Adapter<Division> {
    private List<? extends Division> divisions;

    @Override // top.defaults.view.PickerView.Adapter
    public int getItemCount() {
        List<? extends Division> list = this.divisions;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setDivisions(List<? extends Division> list) {
        this.divisions = list;
        notifyDataSetChanged();
    }

    @Override // top.defaults.view.PickerView.Adapter
    public Division getItem(int i2) {
        return this.divisions.get(i2);
    }
}
