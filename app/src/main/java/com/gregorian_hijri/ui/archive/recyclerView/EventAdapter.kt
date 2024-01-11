package com.gregorian_hijri.ui.archive.recyclerView

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gregorian_hijri.R
import com.gregorian_hijri.data.database.EventStorageEntity
import com.gregorian_hijri.ui.archive.ArchiveViewModel

class EventAdapter(viewMddel : ArchiveViewModel, context: Context, inflater: LayoutInflater,) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    private  var viewModel = viewMddel
    private  var context = context
    private  var inflater= inflater
    private var eventList= listOf<EventStorageEntity>()
    class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val eventTitle : TextView = itemView.findViewById(R.id.title_item)
        val eventDescreption : TextView = itemView.findViewById(R.id.des_item)
        val eventHijri : TextView = itemView.findViewById(R.id.hijri_item)
        val eventGeorgian : TextView = itemView.findViewById(R.id.gregorian_item)
        val eventDelete : ImageView = itemView.findViewById(R.id.delete_btn)
        val eventEdit : ImageView = itemView.findViewById(R.id.edit_btn)
        val eventCheckBox : CheckBox = itemView.findViewById(R.id.item_checkbox)


    }
    override fun getItemCount() = eventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.event_items,
            parent,false)
        return EventViewHolder(viewLayout)



    }




    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.eventTitle.text = currentItem.event_name
        holder.eventDescreption.text = currentItem.event_description
        holder.eventGeorgian.text = currentItem.Gregorian_date
        holder.eventHijri.text = currentItem.Hijri_date
        holder.eventDelete.setOnClickListener {
            showDeleteDialog(currentItem.event_name,currentItem.ID)
        }
        holder.eventEdit.setOnClickListener {
            showUpdateDialog(currentItem)

        }
        holder.eventCheckBox.isChecked = viewModel.checkEventMap[currentItem.ID]?:false
        holder.eventCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            Log.i("asfmklxcl",isChecked.toString())
            viewModel.checkEventMap[currentItem.ID] = isChecked

            viewModel.showDeleteAll.value= viewModel.checkEventMap.containsValue(true)
        }

    }
    fun setData(eventList: List<EventStorageEntity>){
        this.eventList = eventList
        notifyDataSetChanged()
    }
    fun showDeleteDialog(title:String,id:String){

        val dialog = AlertDialog.Builder(context)
            .setTitle("Are you sure to delete ${title} ?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteEvent(id)
                Toast.makeText(context,"Delete Done", Toast.LENGTH_LONG).show()

            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()

    }
    fun showUpdateDialog(item: EventStorageEntity){
        val dialogLayout = inflater.inflate(R.layout.alert_dialog, null)
        val inputEditTextFieldTitle =  dialogLayout.findViewById<EditText>(R.id.title_editText)
        val inputEditTextFieldDes =  dialogLayout.findViewById<EditText>(R.id.des_editText)
        inputEditTextFieldTitle.setText(item.event_name)
        inputEditTextFieldDes.setText(item.event_description)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("Update") { _, _ ->
                item.event_name = inputEditTextFieldTitle.text.toString()
                item.event_description = inputEditTextFieldDes.text.toString()
                viewModel.editEvent(item)
                Toast.makeText(context,"Update Done", Toast.LENGTH_LONG).show()

            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()

    }

}