package com.example.galleryviewerapp.presemtation.ui.fragments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.galleryviewerapp.databinding.FragmentMediaViewBinding
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.presemtation.adapters.MediaPagerAdapter
import com.example.galleryviewerapp.presemtation.states.GalleryViewState
import com.example.galleryviewerapp.presemtation.utils.Constants.VIDE0_FRAGMENT
import com.example.galleryviewerapp.presemtation.viewmodels.ImagesViewModel
import com.example.galleryviewerapp.presemtation.viewmodels.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaViewFragment : Fragment() {

    private var _binding: FragmentMediaViewBinding? = null
    private val binding get() = _binding!!

    private val imagesViewModel: ImagesViewModel by activityViewModels()
    private val videoViewModel: VideosViewModel by activityViewModels()
    private lateinit var mediaList: List<MediaFile>
    private lateinit var adapter: MediaPagerAdapter

    private var isVideoFragment: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            isVideoFragment = it.getBoolean(VIDE0_FRAGMENT, false)
        }

        fetchMediaList()
    }

    private fun fetchMediaList() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val flow = if (isVideoFragment) videoViewModel.videos else imagesViewModel.images

                val startIndex = if (isVideoFragment) videoViewModel.selectedIndex.value
                else imagesViewModel.selectedIndex.value

                flow.collect { state ->
                    if (state is GalleryViewState.Success) {
                        mediaList = state.data
                        setupViewPager(mediaList, startIndex)
                    }
                }
            }
        }
    }

    private fun setupViewPager(list: List<MediaFile>, startIndex: Int) {
        adapter = MediaPagerAdapter(mediaList = list)
        binding.viewPager.adapter = adapter

        binding.viewPager.setCurrentItem(startIndex, false)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (isVideoFragment)
                    videoViewModel.saveSelectedVideoIndex(position)
                else
                    imagesViewModel.saveSelectedImageIndex(position)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}