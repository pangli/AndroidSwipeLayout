package com.zorro.swipe.example.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zorro.swipe.example.adapter.RecyclerViewAdapter
import com.zorro.swipe.example.databinding.RecyclerviewBinding
import com.zorro.swipe.util.Attributes
import java.util.*

class RecyclerViewExample : Activity() {
    private lateinit var binding: RecyclerviewBinding
    private lateinit var mAdapter: RecyclerViewAdapter
    private lateinit var mDataSet: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDataSet = arrayListOf(
            "Alabama",
            "Alaska",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
        )
        initView()
    }

    private fun initView() {
        mAdapter = RecyclerViewAdapter(mDataSet)
        mAdapter.mode = Attributes.Mode.Single
        val manager = LinearLayoutManager(this)
        binding.recyclerView.apply {
            layoutManager = manager
            adapter = mAdapter
            addOnScrollListener(onScrollListener)
        }
    }


    private var onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.e("ListView", "onScrolled")
                mAdapter.closeAllItems()
            }
        }
}