package com.example.brainbuster.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.brainbuster.R
import com.example.brainbuster.databinding.ActivityDashboardBinding
import com.example.brainbuster.fragment.*
import com.example.brainbuster.helper.Utils

class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.blackIconStatusBar(this, R.color.white)

        initView()

        //home fragment is loaded when user first open the app
        defaultClick()

    }

    private fun defaultClick() {
        binding.ivHomeNav.setColorFilter(resources.getColor(R.color.violetPrimary1))
        binding.tvHomeNav.setTextColor(getColor(R.color.violetPrimary1))

        binding.ivFavouritesNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
        binding.tvFavouritesNav.setTextColor(getColor(R.color.gray_light_dark))

        binding.ivProfileNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
        binding.tvProfileNav.setTextColor(getColor(R.color.gray_light_dark))

        binding.ivMoreNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
        binding.tvMoreNav.setTextColor(getColor(R.color.gray_light_dark))

        addMainfragment(HomeFragment(),"HomeFragment")
    }

    private fun initView() {

        binding.lvHome.setOnClickListener {

            binding.ivHomeNav.setColorFilter(resources.getColor(R.color.violetPrimary1))
            binding.tvHomeNav.setTextColor(getColor(R.color.violetPrimary1))

            binding.ivFavouritesNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvFavouritesNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivProfileNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvProfileNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivMoreNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvMoreNav.setTextColor(getColor(R.color.gray_light_dark))

            addMainfragment(HomeFragment(),"HomeFragment")

        }

        binding.lvFavourites.setOnClickListener {

            binding.ivFavouritesNav.setColorFilter(resources.getColor(R.color.violetPrimary1))
            binding.tvFavouritesNav.setTextColor(getColor(R.color.violetPrimary1))

            binding.ivHomeNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvHomeNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivProfileNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvProfileNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivMoreNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvMoreNav.setTextColor(getColor(R.color.gray_light_dark))

            addMainfragment(FavouritesFragment(),"FavouritesFragment")

        }

        binding.lvProfile.setOnClickListener {

            binding.ivProfileNav.setColorFilter(resources.getColor(R.color.violetPrimary1))
            binding.tvProfileNav.setTextColor(getColor(R.color.violetPrimary1))

            binding.ivHomeNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvHomeNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivFavouritesNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvFavouritesNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivMoreNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvMoreNav.setTextColor(getColor(R.color.gray_light_dark))

            addMainfragment(ProfileFragment(),"ProfileFragment")

        }

        binding.lvMore.setOnClickListener {

            binding.ivMoreNav.setColorFilter(resources.getColor(R.color.violetPrimary1))
            binding.tvMoreNav.setTextColor(getColor(R.color.violetPrimary1))

            binding.ivHomeNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvHomeNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivFavouritesNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvFavouritesNav.setTextColor(getColor(R.color.gray_light_dark))

            binding.ivProfileNav.setColorFilter(resources.getColor(R.color.gray_light_dark))
            binding.tvProfileNav.setTextColor(getColor(R.color.gray_light_dark))

            addMainfragment(MoreFragment(),"MoreFragment")

        }




    }

    private fun addMainfragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_main, fragment, tag)
        fragmentTransaction.commit()
    }

    private fun addFullfragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_full, fragment, tag)
        fragmentTransaction.commit()
    }
}