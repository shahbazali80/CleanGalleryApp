package com.example.galleryviewerapp.presemtation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryviewerapp.databinding.MediaItemLayoutBinding
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.presemtation.utils.loadImages

class MediaFileListAdapter(
    private val onFileItemClick: (MediaFile) -> Unit
) : RecyclerView.Adapter<MediaFileListAdapter.ViewHolder>() {

    private val files = mutableListOf<MediaFile>()
    private lateinit var mContext: Context

    inner class ViewHolder(val binding: MediaItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
         return ViewHolder(
            MediaItemLayoutBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaFile = files[position]

        val file = mediaFile.uri.toString().toUri()
        mContext.loadImages(file, holder.binding.ivThumbnail)

        holder.itemView.setOnClickListener { onFileItemClick(mediaFile) }
    }

    override fun getItemCount() = files.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<MediaFile>) {
        files.clear()
        files.addAll(newList)
        notifyDataSetChanged()
    }
}