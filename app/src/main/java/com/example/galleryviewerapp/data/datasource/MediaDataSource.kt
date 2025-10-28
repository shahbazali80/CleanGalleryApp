package com.example.galleryviewerapp.data.datasource

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.galleryviewerapp.data.model.MediaEntity
import com.example.galleryviewerapp.presemtation.utils.Constants.IMAGE_FILE
import javax.inject.Inject

class MediaDataSource @Inject constructor(
     private val context: Context
) {
    fun fetchMedia(type: String): List<MediaEntity> {
        val collection = if (type == IMAGE_FILE)
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        else
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        val mediaList = mutableListOf<MediaEntity>()

        context.contentResolver.query(collection, projection, null, null, sortOrder)?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val name = cursor.getString(nameCol)
                val dateAdded = cursor.getLong(dateCol)
                val uri = ContentUris.withAppendedId(collection, id)
                mediaList.add(MediaEntity(id, uri, name, type, dateAdded))
            }
        }
        return mediaList
    }
}