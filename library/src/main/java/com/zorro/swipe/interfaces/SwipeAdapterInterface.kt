package com.zorro.swipe.interfaces

interface SwipeAdapterInterface {
    fun getSwipeLayoutResourceId(position: Int): Int
    fun notifyDatasetChanged()
}