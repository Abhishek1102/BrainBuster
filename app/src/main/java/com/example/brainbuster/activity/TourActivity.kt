package com.example.brainbuster.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.brainbuster.R
import com.example.brainbuster.fragment.TourOneFragment
import com.example.brainbuster.fragment.TourThreeFragment
import com.example.brainbuster.fragment.TourTwoFragment
import com.example.brainbuster.helper.Utils

class TourActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager

    private var viewPagerTourAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour)

        Utils.blackIconStatusBar(this, R.color.white)

        viewPager = findViewById(R.id.viewPager_tour)
        viewPagerTourAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerTourAdapter!!.add(TourOneFragment())
        viewPagerTourAdapter!!.add(TourTwoFragment())
        viewPagerTourAdapter!!.add(TourThreeFragment())

        viewPager.adapter = viewPagerTourAdapter

        initView()
    }

    private fun initView() {

    }

    internal class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {
        private val fragments: MutableList<Fragment> = ArrayList()
        fun add(fragment: Fragment) {
            fragments.add(fragment)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }

    private fun addfragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_tour, fragment, tag)
        fragmentTransaction.commit()
    }
}