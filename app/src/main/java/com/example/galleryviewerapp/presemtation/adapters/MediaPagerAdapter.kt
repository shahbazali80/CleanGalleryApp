package com.example.galleryviewerapp.presemtation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryviewerapp.databinding.MediaItemPreviewLayoutBinding
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.domain.model.MediaType
import com.example.galleryviewerapp.presemtation.utils.gone
import com.example.galleryviewerapp.presemtation.utils.loadImages
import com.example.galleryviewerapp.presemtation.utils.showLog
import com.example.galleryviewerapp.presemtation.utils.visible

class MediaPagerAdapter(
    private val mediaList: List<MediaFile>
) : RecyclerView.Adapter<MediaPagerAdapter.MediaViewHolder>() {

    private lateinit var mContext: Context

    inner class MediaViewHolder(val binding: MediaItemPreviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        mContext = parent.context
        val binding =
            MediaItemPreviewLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = mediaList[position]
        val fileUri = media.uri.toString().toUri()

        holder.binding.apply {
            when (media.type) {
                MediaType.IMAGE -> {
                    ivImg.visible()
                    videoView.gone()
                    mContext.loadImages(fileUri, ivImg)
                }

                MediaType.VIDEO -> {
                    ivImg.gone()
                    videoView.visible()
                    videoView.setVideoURI(fileUri)
                    videoView.setOnPreparedListener { mp ->
                        mp.isLooping = false

                        val lp = videoView.layoutParams as ConstraintLayout.LayoutParams
                        lp.dimensionRatio = "${mp.videoWidth}:${mp.videoHeight}"
                        videoView.layoutParams = lp
                        mp.start()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = mediaList.size
}