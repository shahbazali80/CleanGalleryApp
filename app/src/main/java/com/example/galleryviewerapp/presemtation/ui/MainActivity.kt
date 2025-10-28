package com.example.galleryviewerapp.presemtation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import com.example.galleryviewerapp.databinding.ActivityMainBinding
import com.example.galleryviewerapp.presemtation.adapters.ViewPagerAdapter
import com.example.galleryviewerapp.presemtation.viewmodels.ImagesViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    private lateinit var viewAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setTabs()
    }

    private fun setTabs() {
        binding.apply {
            viewAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            viewPager.adapter = viewAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Images"
                    }

                    1 -> {
                        tab.text = "Videos"
                    }
                }
            }.attach()
        }
    }
}