package com.ykb.ebook.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes7.dex */
public class SubFloorComments {
    private List<BookReview> list;

    public SubFloorComments(List<BookReview> list) {
        ArrayList arrayList = new ArrayList();
        this.list = arrayList;
        if (list == null) {
            this.list = new ArrayList();
            return;
        }
        if (arrayList.size() > 0) {
            this.list.clear();
        }
        this.list = list;
    }

    public BookReview get(int i2) {
        return this.list.get(i2);
    }

    public String getFloorNum() {
        return this.list.get(r0.size() - 1).getFloorNum();
    }

    public Iterator<BookReview> iterator() {
        List<BookReview> list = this.list;
        if (list == null) {
            return null;
        }
        return list.iterator();
    }

    public int size() {
        List<BookReview> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
