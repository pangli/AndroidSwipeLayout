package com.zorro.swipe.example.adapter


import com.zorro.swipe.example.adapter.RecyclerViewAdapter.SimpleViewHolder
import com.zorro.swipe.SwipeLayout

import android.widget.Toast
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.zorro.swipe.adapter.RecyclerSwipeAdapter
import com.zorro.swipe.example.R

import com.zorro.swipe.example.databinding.RecyclerviewItemBinding

class RecyclerViewAdapter(private val mDataset: ArrayList<String>) :
    RecyclerSwipeAdapter<SimpleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val itemBinding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = mDataset[position]
//        holder.bind(item)
        holder.itemBinding.apply {
            swipe.showMode = SwipeLayout.ShowMode.LayDown
            delete.setOnClickListener { view ->
                mItemManger.removeShownLayouts(swipe)
                mDataset.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, mDataset.size)
                mItemManger.closeAllItems()
                Toast.makeText(
                    view.context,
                    "Deleted " + textData.text.toString() + "!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            tvPosition.text = (position + 1).toString() + "."
            textData.text = item + "        向左滑动展开，点击关闭"
            swipe.surfaceView?.setOnClickListener {
                mItemManger.closeAllItems()
            }
        }
        mItemManger.bind(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }


    class SimpleViewHolder(val itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
//    {
//        fun bind(s: String) {
//            itemBinding.apply {
//                swipe.showMode = SwipeLayout.ShowMode.LayDown
//                swipe.setOnDoubleClickListener(object :SwipeLayout.DoubleClickListener{
//                    override fun onDoubleClick(layout: SwipeLayout?, surface: Boolean) {
////                        Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show()
//                    }
//                })
//                delete.setOnClickListener { view ->
//                    mItemManger.removeShownLayouts(viewHolder.swipeLayout)
//                    mDataset.removeAt(position)
//                    notifyItemRemoved(position)
//                    notifyItemRangeChanged(position, mDataset.size)
//                    mItemManger.closeAllItems()
//                    Toast.makeText(
//                        view.context,
//                        "Deleted " + viewHolder.textViewData.text.toString() + "!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
}