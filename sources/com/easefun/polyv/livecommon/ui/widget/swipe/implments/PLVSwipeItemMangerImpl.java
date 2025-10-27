package com.easefun.polyv.livecommon.ui.widget.swipe.implments;

import android.view.View;
import com.easefun.polyv.livecommon.ui.widget.swipe.PLVSimpleSwipeListener;
import com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeAdapterInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.util.PLVAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class PLVSwipeItemMangerImpl implements PLVSwipeItemMangerInterface {
    protected PLVSwipeAdapterInterface swipeAdapterInterface;
    private PLVAttributes.Mode mode = PLVAttributes.Mode.Single;
    public final int INVALID_POSITION = -1;
    protected int mOpenPosition = -1;
    protected Set<Integer> mOpenPositions = new HashSet();
    protected Set<PLVSwipeLayout> mShownLayouts = new HashSet();

    public class OnLayoutListener implements PLVSwipeLayout.OnLayout {
        private int position;

        public OnLayoutListener(int position) {
            this.position = position;
        }

        @Override // com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.OnLayout
        public void onLayout(PLVSwipeLayout v2) {
            if (PLVSwipeItemMangerImpl.this.isOpen(this.position)) {
                v2.open(false, false);
            } else {
                v2.close(false, false);
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public class SwipeMemory extends PLVSimpleSwipeListener {
        private int position;

        public SwipeMemory(int position) {
            this.position = position;
        }

        @Override // com.easefun.polyv.livecommon.ui.widget.swipe.PLVSimpleSwipeListener, com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.SwipeListener
        public void onClose(PLVSwipeLayout layout) {
            if (PLVSwipeItemMangerImpl.this.mode == PLVAttributes.Mode.Multiple) {
                PLVSwipeItemMangerImpl.this.mOpenPositions.remove(Integer.valueOf(this.position));
            } else {
                PLVSwipeItemMangerImpl.this.mOpenPosition = -1;
            }
        }

        @Override // com.easefun.polyv.livecommon.ui.widget.swipe.PLVSimpleSwipeListener, com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.SwipeListener
        public void onOpen(PLVSwipeLayout layout) {
            if (PLVSwipeItemMangerImpl.this.mode == PLVAttributes.Mode.Multiple) {
                PLVSwipeItemMangerImpl.this.mOpenPositions.add(Integer.valueOf(this.position));
                return;
            }
            PLVSwipeItemMangerImpl.this.closeAllExcept(layout);
            PLVSwipeItemMangerImpl.this.mOpenPosition = this.position;
        }

        @Override // com.easefun.polyv.livecommon.ui.widget.swipe.PLVSimpleSwipeListener, com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout.SwipeListener
        public void onStartOpen(PLVSwipeLayout layout) {
            if (PLVSwipeItemMangerImpl.this.mode == PLVAttributes.Mode.Single) {
                PLVSwipeItemMangerImpl.this.closeAllExcept(layout);
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public class ValueBox {
        OnLayoutListener onLayoutListener;
        int position;
        SwipeMemory swipeMemory;

        public ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    public PLVSwipeItemMangerImpl(PLVSwipeAdapterInterface swipeAdapterInterface) {
        if (swipeAdapterInterface == null) {
            throw new IllegalArgumentException("PLVSSwipeAdapterInterface can not be null");
        }
        this.swipeAdapterInterface = swipeAdapterInterface;
    }

    public void bind(View view, int position) {
        int swipeLayoutResourceId = this.swipeAdapterInterface.getSwipeLayoutResourceId(position);
        bindSL((PLVSwipeLayout) view.findViewById(swipeLayoutResourceId), position, swipeLayoutResourceId);
    }

    public void bindSL(PLVSwipeLayout swipeLayout, int position, int resId) {
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        if (swipeLayout.getTag(resId) != null) {
            ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
            valueBox.swipeMemory.setPosition(position);
            valueBox.onLayoutListener.setPosition(position);
            valueBox.position = position;
            return;
        }
        OnLayoutListener onLayoutListener = new OnLayoutListener(position);
        SwipeMemory swipeMemory = new SwipeMemory(position);
        swipeLayout.addSwipeListener(swipeMemory);
        swipeLayout.addOnLayoutListener(onLayoutListener);
        swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
        this.mShownLayouts.add(swipeLayout);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeAllExcept(PLVSwipeLayout layout) {
        for (PLVSwipeLayout pLVSwipeLayout : this.mShownLayouts) {
            if (pLVSwipeLayout != layout) {
                pLVSwipeLayout.close();
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeAllItems() {
        if (this.mode == PLVAttributes.Mode.Multiple) {
            this.mOpenPositions.clear();
        } else {
            this.mOpenPosition = -1;
        }
        Iterator<PLVSwipeLayout> it = this.mShownLayouts.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeItem(int position) {
        if (this.mode == PLVAttributes.Mode.Multiple) {
            this.mOpenPositions.remove(Integer.valueOf(position));
        } else if (this.mOpenPosition == position) {
            this.mOpenPosition = -1;
        }
        this.swipeAdapterInterface.notifyDatasetChanged();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public PLVAttributes.Mode getMode() {
        return this.mode;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mode == PLVAttributes.Mode.Multiple ? new ArrayList(this.mOpenPositions) : Collections.singletonList(Integer.valueOf(this.mOpenPosition));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public List<PLVSwipeLayout> getOpenLayouts() {
        return new ArrayList(this.mShownLayouts);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public boolean isOpen(int position) {
        return this.mode == PLVAttributes.Mode.Multiple ? this.mOpenPositions.contains(Integer.valueOf(position)) : this.mOpenPosition == position;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void openItem(int position) {
        if (this.mode != PLVAttributes.Mode.Multiple) {
            this.mOpenPosition = position;
        } else if (!this.mOpenPositions.contains(Integer.valueOf(position))) {
            this.mOpenPositions.add(Integer.valueOf(position));
        }
        this.swipeAdapterInterface.notifyDatasetChanged();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void removeShownLayouts(PLVSwipeLayout layout) {
        this.mShownLayouts.remove(layout);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void setMode(PLVAttributes.Mode mode) {
        this.mode = mode;
        this.mOpenPositions.clear();
        this.mShownLayouts.clear();
        this.mOpenPosition = -1;
    }
}
