package com.example.galleryviewerapp.presemtation.ui.media

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.galleryviewerapp.databinding.ActivityMediaViewBinding
import com.example.galleryviewerapp.domain.model.MediaType
import com.example.galleryviewerapp.presemtation.utils.Constants.MEDIA_TYPE_INTENT
import com.example.galleryviewerapp.presemtation.utils.Constants.MEDIA_URI_INTENT
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
        val uriString = intent.getStringExtra(MEDIA_URI_INTENT)
        val type = intent.getStringExtra(MEDIA_TYPE_INTENT)

        if (uriString == null || type == null) {
            showToast("No media data")
            return
        }

        val fileUri = uriString.toUri()
        when (type) {
            MediaType.VIDEO.name -> showVideo(fileUri)
            MediaType.IMAGE.name -> showImage(fileUri)
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
}