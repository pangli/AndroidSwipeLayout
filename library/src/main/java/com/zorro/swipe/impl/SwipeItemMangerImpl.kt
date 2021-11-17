package com.zorro.swipe.impl

import android.view.View

import com.zorro.swipe.interfaces.SwipeAdapterInterface
import com.zorro.swipe.interfaces.SwipeItemMangerInterface
import com.zorro.swipe.SwipeLayout

import com.zorro.swipe.SwipeLayout.OnLayout
import com.zorro.swipe.listener.SimpleSwipeListener
import com.zorro.swipe.util.Attributes
import java.lang.IllegalStateException
import java.util.ArrayList
import java.util.HashSet

/**
 * SwipeItemMangerImpl is a helper class to help all the adapters to maintain open status.
 */
class SwipeItemMangerImpl(private val swipeAdapterInterface: SwipeAdapterInterface?) :
    SwipeItemMangerInterface {
    private var defaultMode: Attributes.Mode = Attributes.Mode.Single
    private val invalidPosition = -1
    private var mOpenPosition = invalidPosition
    private var mOpenPositions: MutableSet<Int> = HashSet()
    private var mShownLayouts: MutableSet<SwipeLayout> = HashSet()
    override var mode: Attributes.Mode
        get() = defaultMode
        set(value) {
            defaultMode = value
            mOpenPositions.clear()
            mShownLayouts.clear()
            mOpenPosition = invalidPosition
        }

    fun bind(view: View, position: Int) {
        swipeAdapterInterface?.apply {
            val resId = swipeAdapterInterface.getSwipeLayoutResourceId(position)
            val swipeLayout = view.findViewById<View>(resId)
                ?: throw IllegalStateException("can not find SwipeLayout in target view")
            swipeLayout as SwipeLayout
            if (swipeLayout.getTag(resId) == null) {
                val onLayoutListener = OnLayoutListener(position)
                val swipeMemory = SwipeMemory(position)
                swipeLayout.addSwipeListener(swipeMemory)
                swipeLayout.addOnLayoutListener(onLayoutListener)
                swipeLayout.setTag(resId, ValueBox(position, swipeMemory, onLayoutListener))
                mShownLayouts.add(swipeLayout)
            } else {
                val valueBox = swipeLayout.getTag(resId) as ValueBox
                valueBox.swipeMemory.setPosition(position)
                valueBox.onLayoutListener.setPosition(position)
                valueBox.position = position
            }
        }
    }

    override fun openItem(position: Int) {
        if (defaultMode == Attributes.Mode.Multiple) {
            if (!mOpenPositions.contains(position)) mOpenPositions.add(position)
        } else {
            mOpenPosition = position
        }
        swipeAdapterInterface?.notifyDatasetChanged()
    }

    override fun closeItem(position: Int) {
        if (defaultMode == Attributes.Mode.Multiple) {
            mOpenPositions.remove(position)
        } else {
            if (mOpenPosition == position) mOpenPosition = invalidPosition
        }
        swipeAdapterInterface?.notifyDatasetChanged()
    }

    override fun closeAllExcept(layout: SwipeLayout) {
        for (s in mShownLayouts) {
            if (s != layout) s.close()
        }
    }

    override fun closeAllItems() {
        if (defaultMode == Attributes.Mode.Multiple) {
            mOpenPositions.clear()
        } else {
            mOpenPosition = invalidPosition
        }
        for (s in mShownLayouts) {
            s.close()
        }
    }

    override fun removeShownLayouts(layout: SwipeLayout) {
        mShownLayouts.remove(layout)
    }

    override val openItems: List<Int>
        get() = if (defaultMode == Attributes.Mode.Multiple) {
            ArrayList(mOpenPositions)
        } else {
            listOf(mOpenPosition)
        }
    override val openLayouts: List<SwipeLayout>
        get() = ArrayList(mShownLayouts)

    override fun isOpen(position: Int): Boolean {
        return if (defaultMode == Attributes.Mode.Multiple) {
            mOpenPositions.contains(position)
        } else {
            mOpenPosition == position
        }
    }

    internal inner class ValueBox(
        var position: Int,
        var swipeMemory: SwipeMemory,
        var onLayoutListener: OnLayoutListener
    )

    internal inner class OnLayoutListener(private var position: Int) : OnLayout {
        fun setPosition(position: Int) {
            this.position = position
        }

        override fun onLayout(v: SwipeLayout) {
            if (isOpen(position)) {
                v.open(false, false)
            } else {
                v.close(false, false)
            }
        }
    }

    internal inner class SwipeMemory(private var position: Int) : SimpleSwipeListener() {
        override fun onClose(layout: SwipeLayout) {
            if (defaultMode == Attributes.Mode.Multiple) {
                mOpenPositions.remove(position)
            } else {
                mOpenPosition = invalidPosition
            }
        }

        override fun onStartOpen(layout: SwipeLayout) {
            if (defaultMode == Attributes.Mode.Single) {
                closeAllExcept(layout)
            }
        }

        override fun onOpen(layout: SwipeLayout) {
            if (defaultMode == Attributes.Mode.Multiple) mOpenPositions.add(position) else {
                closeAllExcept(layout)
                mOpenPosition = position
            }
        }

        fun setPosition(position: Int) {
            this.position = position
        }
    }
}