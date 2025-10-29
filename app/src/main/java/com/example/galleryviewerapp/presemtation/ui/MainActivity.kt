package com.example.galleryviewerapp.presemtation.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.galleryviewerapp.databinding.ActivityMainBinding
import com.example.galleryviewerapp.presemtation.adapters.ViewPagerAdapter
import com.example.galleryviewerapp.presemtation.ui.fragments.view.MediaViewFragment
import com.example.galleryviewerapp.presemtation.utils.Constants.VIDE0_FRAGMENT
import com.example.galleryviewerapp.presemtation.utils.gone
import com.example.galleryviewerapp.presemtation.utils.visible
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

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

        binding.apply {
            onBackPressedDispatcher.addCallback(this@MainActivity) {
                if (mediaContainer.isVisible) {
                    supportFragmentManager.popBackStack()
                    mediaContainer.gone()
                    tabLayout.visible()
                    viewPager.visible()
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
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

    fun openMediaViewFragment(isVideo: Boolean = false) {
        binding.apply {
            tabLayout.gone()
            viewPager.gone()

            val fragment = MediaViewFragment()
            fragment.arguments = Bundle().apply {
                putBoolean(VIDE0_FRAGMENT, isVideo)
            }

            supportFragmentManager.beginTransaction()
                .replace(mediaContainer.id, fragment)
                .addToBackStack(null)
                .commit()

            mediaContainer.visible()
        }
    }
}