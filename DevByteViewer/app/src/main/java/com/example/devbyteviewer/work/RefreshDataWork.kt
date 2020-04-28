package com.example.devbyteviewer.work

import android.content.Context
import android.media.AudioRecord.SUCCESS
import android.view.PixelCopy.SUCCESS
import androidx.work.CoroutineWorker
import androidx.work.Operation.SUCCESS
import androidx.work.WorkerParameters
import com.example.devbyteviewer.database.getDatabase
import com.example.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException


class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try {
            repository.refreshVideos()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}