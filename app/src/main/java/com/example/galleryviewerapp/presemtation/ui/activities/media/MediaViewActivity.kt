package com.example.galleryviewerapp.presemtation.ui.activities.media

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.galleryviewerapp.data.repository.SharedRepository
import com.example.galleryviewerapp.databinding.ActivityMediaViewBinding
import com.example.galleryviewerapp.domain.model.MediaType
import com.example.galleryviewerapp.presemtation.utils.loadImages
import com.example.galleryviewerapp.presemtation.utils.showToast
import com.example.galleryviewerapp.presemtation.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaViewBinding

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

        showMediaFileInfo()
    }

    private fun showMediaFileInfo() {
        val mediaFile = SharedRepository.mMediaFile

        if (mediaFile == null) {
            showToast("No media data")
            return
        }

        val fileUri = mediaFile.uri.toString().toUri()
        when (mediaFile.type) {
            MediaType.VIDEO -> showVideo(fileUri)
            MediaType.IMAGE -> showImage(fileUri)
        }
    }

    private fun showImage(fileUri: Uri) {
        binding.apply {
            videoView.visible(false)
            ivImg.visible()

            loadImages(fileUri,ivImg)
        }
    }

    private fun showVideo(fileUri: Uri) {
        binding.apply {
            ivImg.visible(false)
            videoView.visible()

            videoView.setVideoURI(fileUri)

            videoView.post {
                videoView.setOnPreparedListener { mp ->
                    mp.isLooping = false

                    val videoWidth = mp.videoWidth
                    val videoHeight = mp.videoHeight
                    if (videoWidth > 0 && videoHeight > 0) {
                        val lp = videoView.layoutParams as ConstraintLayout.LayoutParams
                        lp.dimensionRatio = "$videoWidth:$videoHeight"
                        videoView.layoutParams = lp
                    }

                    mp.start()
                }
            }

            videoView.requestFocus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedRepository.clearMediaFile()
    }
}