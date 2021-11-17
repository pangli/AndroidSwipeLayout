package com.zorro.swipe.adapter


import android.annotation.SuppressLint
import com.zorro.swipe.interfaces.SwipeItemMangerInterface
import com.zorro.swipe.interfaces.SwipeAdapterInterface
import com.zorro.swipe.impl.SwipeItemMangerImpl
import androidx.recyclerview.widget.RecyclerView
import com.zorro.swipe.SwipeLayout
import com.zorro.swipe.util.Attributes

abstract class RecyclerSwipeAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(),
    SwipeItemMangerInterface, SwipeAdapterInterface {
    var mItemManger = SwipeItemMangerImpl(this)

    @SuppressLint("NotifyDataSetChanged")
    override fun notifyDatasetChanged() {
        super.notifyDataSetChanged()
    }

    override fun openItem(position: Int) {
        mItemManger.openItem(position)
    }

    override fun closeItem(position: Int) {
        mItemManger.closeItem(position)
    }

    override fun closeAllExcept(layout: SwipeLayout) {
        mItemManger.closeAllExcept(layout)
    }

    override fun closeAllItems() {
        mItemManger.closeAllItems()
    }

    override val openItems: List<Int>
        get() = mItemManger.openItems
    override val openLayouts: List<SwipeLayout>
        get() = mItemManger.openLayouts

    override fun removeShownLayouts(layout: SwipeLayout) {
        mItemManger.removeShownLayouts(layout)
    }

    override fun isOpen(position: Int): Boolean {
        return mItemManger.isOpen(position)
    }

    override var mode: Attributes.Mode
        get() = mItemManger.mode
        set(mode) {
            mItemManger.mode = mode
        }
}