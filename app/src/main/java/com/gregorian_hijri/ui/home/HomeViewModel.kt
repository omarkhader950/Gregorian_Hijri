package com.gregorian_hijri.ui.home

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gregorian_hijri.data.repository.Repository
import com.gregorian_hijri.data.model.ConverterResponse
import com.gregorian_hijri.data.database.EventStorageDatabase
import com.gregorian_hijri.data.database.EventStorageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application)  : AndroidViewModel(application) {

    var selectedDate = ""
    private val repository: Repository

    var dateAfterConvert   : MutableLiveData<String> = MutableLiveData()
    var dateConverterResponse = MutableLiveData<ConverterResponse>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {

        val userDao = EventStorageDatabase.getDatabase(application).eSDao()
        repository = Repository(userDao)
    }
    fun getDateConveter(date: String,context: Context) {
        coroutineScope.launch {
            val response = repository.dateConverter(date)
            if (response.isSuccessful)
                dateConverterResponse.postValue(response.body())
            else
                Toast.makeText(context,"Please Try Again .... There is a Problem", Toast.LENGTH_SHORT).show()
        }



    }
    fun addEvent(event_description:String,event_name:String,Gregorian_date:String,Hijri_date:String,server_datetime:String,ID:String){
        coroutineScope.launch(Dispatchers.IO) {
            repository.addUpdateEvent(EventStorageEntity(event_description = event_description, event_name =event_name, Gregorian_date = Gregorian_date, Hijri_date = Hijri_date, server_datetime = server_datetime, ID = ID))
        } }

}