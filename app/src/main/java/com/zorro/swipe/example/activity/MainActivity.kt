package com.zorro.swipe.example.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nineoldandroids.view.ViewHelper

import com.zorro.swipe.SwipeLayout
import com.zorro.swipe.example.R
import com.zorro.swipe.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.apply {
            //sample1
            sample1.swipeLayout1.apply {
                showMode = SwipeLayout.ShowMode.PullOut
                addDrag(SwipeLayout.DragEdge.Left, sample1.bottomWrapper)
                addDrag(SwipeLayout.DragEdge.Right, sample1.bottomWrapper2)
                addDrag(SwipeLayout.DragEdge.Top, sample1.startBottom)
                addDrag(SwipeLayout.DragEdge.Bottom, sample1.startBottom)
                surfaceView?.apply {
                    setOnClickListener {
                        Toast.makeText(this@MainActivity, "click on surface", Toast.LENGTH_SHORT)
                            .show()
                    }
                    setOnLongClickListener {
                        Toast.makeText(
                            this@MainActivity,
                            "longClick on surface",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                }
                addRevealListener(R.id.start_bottom, object : SwipeLayout.OnRevealListener {
                    override fun onReveal(
                        child: View,
                        edge: SwipeLayout.DragEdge,
                        fraction: Float,
                        distance: Int
                    ) {
                        val star = child.findViewById<View>(R.id.iv_collect)
                        val d = (child.height / 2 - star.height / 2).toFloat()
                        ViewHelper.setTranslationY(star, d * fraction)
                        ViewHelper.setScaleX(star, fraction + 0.6f)
                        ViewHelper.setScaleY(star, fraction + 0.6f)
                    }
                })
            }
            sample1.archive.setOnClickListener {
                Toast.makeText(this@MainActivity, "archive", Toast.LENGTH_SHORT).show()
            }
            sample1.delete.setOnClickListener {
                Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
            }
            sample1.search.setOnClickListener {
                Toast.makeText(this@MainActivity, "search", Toast.LENGTH_SHORT).show()
            }
            sample1.collect.setOnClickListener {
                Toast.makeText(this@MainActivity, "collect", Toast.LENGTH_SHORT).show()
            }
            sample1.ivDelete.setOnClickListener {
                Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
            }
            //sample2
            sample2.swipeLayout2.apply {
                showMode = SwipeLayout.ShowMode.LayDown
                addDrag(SwipeLayout.DragEdge.Right, sample2.llAction)
            }
            sample2.open.setOnClickListener {
                sample2.swipeLayout2.open()
                Toast.makeText(this@MainActivity, "open", Toast.LENGTH_SHORT).show()
            }
            sample2.search.setOnClickListener {
                sample2.swipeLayout2.close()
                Toast.makeText(this@MainActivity, "search", Toast.LENGTH_SHORT).show()
            }
            sample2.collect.setOnClickListener {
                Toast.makeText(this@MainActivity, "collect", Toast.LENGTH_SHORT).show()
            }
            sample2.ivDelete.setOnClickListener {
                Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
            }
            //sample3
            sample3.swipeLayout3.apply {
                addDrag(SwipeLayout.DragEdge.Top, sample3.llAction)
                surfaceView?.apply {
                    setOnClickListener {
                        Toast.makeText(this@MainActivity, "click on surface", Toast.LENGTH_SHORT)
                            .show()
                    }
                    setOnLongClickListener {
                        Toast.makeText(
                            this@MainActivity,
                            "longClick on surface",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                }
                addRevealListener(R.id.ll_action, object : SwipeLayout.OnRevealListener {
                    override fun onReveal(
                        child: View,
                        edge: SwipeLayout.DragEdge,
                        fraction: Float,
                        distance: Int
                    ) {
                        val star = child.findViewById<View>(R.id.collect)
                        val d = (child.height / 2 - star.height / 2).toFloat()
                        ViewHelper.setTranslationY(star, d * fraction)
                        ViewHelper.setScaleX(star, fraction + 0.6f)
                        ViewHelper.setScaleY(star, fraction + 0.6f)
                        val c = evaluate(
                            fraction,
                            Color.parseColor("#dddddd"),
                            Color.parseColor("#4C535B")
                        ) as Int
                        child.setBackgroundColor(c)
                    }
                })
            }
            sample3.collect.setOnClickListener {
                Toast.makeText(this@MainActivity, "collect", Toast.LENGTH_SHORT).show()
            }
            btnRecycler.setOnClickListener {
                startActivity(Intent(this@MainActivity, RecyclerViewExample::class.java))
            }
        }
    }

    /*
     * Color transition method.
     */
    fun evaluate(fraction: Float, startValue: Any, endValue: Any): Any {
        val startInt = startValue as Int
        val startA = startInt shr 24 and 0xff
        val startR = startInt shr 16 and 0xff
        val startG = startInt shr 8 and 0xff
        val startB = startInt and 0xff
        val endInt = endValue as Int
        val endA = endInt shr 24 and 0xff
        val endR = endInt shr 16 and 0xff
        val endG = endInt shr 8 and 0xff
        val endB = endInt and 0xff
        return (startA + (fraction * (endA - startA)).toInt() shl 24) or
                (startR + (fraction * (endR - startR)).toInt() shl 16) or
                (startG + (fraction * (endG - startG)).toInt() shl 8) or
                (startB + (fraction * (endB - startB)).toInt())
    }
}