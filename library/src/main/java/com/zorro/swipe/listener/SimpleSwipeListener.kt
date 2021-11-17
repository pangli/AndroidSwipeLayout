package com.zorro.swipe.listener

import com.zorro.swipe.SwipeLayout
import com.zorro.swipe.SwipeLayout.SwipeListener

open class SimpleSwipeListener : SwipeListener {
    override fun onStartOpen(layout: SwipeLayout) {}
    override fun onOpen(layout: SwipeLayout) {}
    override fun onStartClose(layout: SwipeLayout) {}
    override fun onClose(layout: SwipeLayout) {}
    override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {}
    override fun onHandRelease(layout: SwipeLayout, xValue: Float, yValue: Float) {}
}