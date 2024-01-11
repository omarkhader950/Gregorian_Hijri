package com.gregorian_hijri.ui.archive

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.gregorian_hijri.base.BaseFragment
import com.gregorian_hijri.databinding.FragmentArchiveBinding
import com.gregorian_hijri.ui.archive.recyclerView.EventAdapter


class ArchiveFragment : BaseFragment() {
    private lateinit var eventAdapter : EventAdapter

    private lateinit var viewModel:ArchiveViewModel
    private lateinit var binding: FragmentArchiveBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentArchiveBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(this).get(ArchiveViewModel::class.java)
        eventAdapter= EventAdapter(viewModel,requireContext(),inflater)
        setView()
        observeDeleteAllButton()
        observeEvents()
        return binding.root

    }
    fun showDeleteAllDialog(){

        val dialog = AlertDialog.Builder(context)
            .setTitle("Are you sure to delete selected events ?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteAllEvents()
                Toast.makeText(context,"Delete Done", Toast.LENGTH_LONG).show()

            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()

    }
    fun observeDeleteAllButton(){
        viewModel.showDeleteAll.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.deleteAll.visibility = View.VISIBLE
            else
                binding.deleteAll.visibility = View.GONE
        })

    }
    fun observeEvents(){
        viewModel.readAllEvents.observe(viewLifecycleOwner, Observer {event->
            eventAdapter.setData(event)
        })

    }
    fun setView(){
        binding.recyclerView.adapter = eventAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)


        binding.deleteAll.setOnClickListener {
            showDeleteAllDialog()
        }
    }

}