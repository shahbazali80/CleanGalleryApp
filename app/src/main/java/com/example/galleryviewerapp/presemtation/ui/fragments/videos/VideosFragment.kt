package com.example.galleryviewerapp.presemtation.ui.fragments.videos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryviewerapp.databinding.FragmentVideosBinding
import com.example.galleryviewerapp.presemtation.adapters.MediaFileListAdapter
import com.example.galleryviewerapp.presemtation.helper.PermissionManager
import com.example.galleryviewerapp.presemtation.states.GalleryViewState
import com.example.galleryviewerapp.presemtation.ui.MainActivity
import com.example.galleryviewerapp.presemtation.utils.gone
import com.example.galleryviewerapp.presemtation.utils.visible
import com.example.galleryviewerapp.presemtation.viewmodels.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideosFragment : Fragment() {
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context

    private val viewModel: VideosViewModel by activityViewModels()

    private lateinit var adapter: MediaFileListAdapter

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val allGranted = result.values.all { it }
            if (allGranted) viewModel.loadVideos()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        mContext = requireContext()

        setRV()
        fetchVideos()
    }

    private fun setRV() {
        binding.apply {
            adapter = MediaFileListAdapter { index ->
                viewModel.saveSelectedVideoIndex(index)
                (requireActivity() as MainActivity).openMediaViewFragment(true)
            }
            rvList.layoutManager = GridLayoutManager(mContext, 3)
            rvList.adapter = adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchVideos() {
        binding.apply {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.videos.collectLatest { state ->
                        when (state) {
                            is GalleryViewState.Loading -> {
                                tvList.text = "Loading..."
                            }
                            is GalleryViewState.Success -> {
                                val list = state.data
                                adapter.setList(list)
                                if (adapter.itemCount > 0) {
                                    tvList.gone()
                                    rvList.visible()
                                } else {
                                    rvList.gone()
                                    tvList.visible()
                                    tvList.text = "No file found"
                                }
                            }
                            is GalleryViewState.Error -> {
                                tvList.text = state.message
                            }
                        }
                    }
                }
            }
        }

        if (PermissionManager.hasAllPermissions(mContext)) {
            viewModel.loadVideos()
        } else {
            permissionLauncher.launch(PermissionManager.getRequiredPermissions())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}