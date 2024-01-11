package com.gregorian_hijri.data.repository

import androidx.lifecycle.LiveData
import com.gregorian_hijri.data.model.ConverterResponse
import com.gregorian_hijri.data.database.EventStorageDao
import com.gregorian_hijri.data.database.EventStorageEntity
import com.gregorian_hijri.data.network.ApiClient
import retrofit2.Response

class Repository(private  val userDao: EventStorageDao) {

    private val api = ApiClient.getInstance()
    val readAllEvents: LiveData<List<EventStorageEntity>> =userDao.readAllEvents()
    suspend fun dateConverter(date: String) : Response<ConverterResponse> {
        return  api.dateConverter(date)
    }
    fun deleteEvent(id:String){
        userDao.deleteEvent(id)
    }
    fun addUpdateEvent(event: EventStorageEntity){
        userDao.addEvent(event)
    }
}