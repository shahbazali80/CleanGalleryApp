package com.example.galleryviewerapp.presemtation.ui.activities.media

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.galleryviewerapp.data.repository.SharedRepository
import com.example.galleryviewerapp.databinding.ActivityMediaViewBinding
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.domain.model.MediaType
import com.example.galleryviewerapp.presemtation.adapters.MediaPagerAdapter
import com.example.galleryviewerapp.presemtation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaViewBinding
    private var isVideoFile: Boolean = false
    private lateinit var mediaList: List<MediaFile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMediaViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViewPager()
    }

    private fun initViewPager() {
        val imageList = SharedRepository.imageList
        val videoList = SharedRepository.videoList

        val current = SharedRepository.mMediaFile
        isVideoFile = current?.type == MediaType.VIDEO

        mediaList = if (isVideoFile) videoList ?: emptyList() else imageList ?: emptyList()
        if (mediaList.isEmpty()) {
            showToast("No media data")
            return
        }

        val adapter = MediaPagerAdapter(
            mediaList = mediaList
        )

        binding.viewPager.adapter = adapter

        val startIndex = if (isVideoFile) SharedRepository.selectedVideoIndex
        else SharedRepository.selectedImageIndex

        binding.viewPager.setCurrentItem(startIndex, false)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val media = mediaList[position]
                SharedRepository.mMediaFile = media
                if (media.type == MediaType.VIDEO) {
                    SharedRepository.saveSelectedVideoIndex(position)
                    isVideoFile = true
                } else {
                    SharedRepository.saveSelectedImageIndex(position)
                    isVideoFile = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedRepository.clear()
    }
}