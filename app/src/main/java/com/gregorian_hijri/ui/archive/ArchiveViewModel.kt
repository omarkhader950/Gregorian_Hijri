package com.gregorian_hijri.ui.archive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gregorian_hijri.data.repository.Repository
import com.gregorian_hijri.data.database.EventStorageDatabase
import com.gregorian_hijri.data.database.EventStorageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchiveViewModel(application: Application)  : AndroidViewModel(application) {
    val readAllEvents: LiveData<List<EventStorageEntity>>
    private val repository: Repository
    var checkEventMap =mutableMapOf<String, Boolean>()
    var showDeleteAll = MutableLiveData<Boolean>(false)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {

        val userDao = EventStorageDatabase.getDatabase(application).eSDao()
        repository = Repository(userDao)
        readAllEvents = repository.readAllEvents
    }

    fun editEvent(event: EventStorageEntity){

        coroutineScope.launch {

            repository.addUpdateEvent(event)

        }


    }
    fun deleteEvent(ID:String){

        coroutineScope.launch {
            repository.deleteEvent(id = ID)
        }
        showDeleteAll.value = checkEventMap.values.filter { it }.isEmpty()


    }

    fun deleteAllEvents(){
        showDeleteAll.value = false
        coroutineScope.launch {
            checkEventMap.filter { it.value  }.forEach {
                repository.deleteEvent(it.key.toString())
            }

        }

    }

}