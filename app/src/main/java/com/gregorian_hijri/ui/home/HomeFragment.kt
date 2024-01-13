package com.gregorian_hijri.ui.home


import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gregorian_hijri.R
import com.gregorian_hijri.base.BaseFragment
import com.gregorian_hijri.databinding.FragmentHomeBinding
import com.gregorian_hijri.extension.convertDateToLong
import com.gregorian_hijri.utils.calenderPermission
import java.util.Calendar
import java.util.TimeZone

class HomeFragment : BaseFragment() {

    val callbackId = 42
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelHome = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        setData(viewModelHome)
        calenderClick(viewModelHome)
        convertData(viewModelHome)
        addEvent(viewModelHome)


    }

    fun calenderClick(viewModelHome: HomeViewModel) {
        binding.openDialog.setOnClickListener {

            var calendar = Calendar.getInstance()
            var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            var month: Int = calendar.get(Calendar.MONTH)
            var year: Int = calendar.get(Calendar.YEAR)
            val picker =
                DatePickerDialog(requireContext(), { view, yearC, monthOfYear, dayOfMonth ->
                    day = dayOfMonth
                    month = monthOfYear
                    year = yearC
                    binding.dateView.setText("" + dayOfMonth + "-" + monthOfYear + "-" + yearC)
                    viewModelHome.selectedDate = "" + dayOfMonth + "-" + monthOfYear + "-" + yearC
                }, year, month, day)
            picker.show()
        }
    }

    fun convertData(viewModelHome: HomeViewModel) {
        binding.convertDate.setOnClickListener {
            if (binding.dateView.text.isNullOrEmpty())
                Toast.makeText(requireContext(), "Select Date First", Toast.LENGTH_LONG).show()
            else {
                viewModelHome.getDateConveter(viewModelHome.selectedDate, requireContext())
                viewModelHome.dateConverterResponse.observe(viewLifecycleOwner, Observer {

                    binding.convertedDate.text = it.data.hijri.date.toString()

                })
            }

        }
    }

    fun setData(viewModelHome: HomeViewModel) {
        calenderPermission(
            requireActivity(),
            callbackId,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
        binding.convertedDate.text = viewModelHome.dateAfterConvert.value

    }

    fun addEvent(viewModelHome: HomeViewModel) {
        binding.addEvent.setOnClickListener {

            if (binding.dateView.text.isNullOrEmpty() || binding.convertedDate.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Select Date First", Toast.LENGTH_LONG).show()
            } else {
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.alert_dialog, null)
                val inputEditTextFieldTitle =
                    dialogLayout.findViewById<EditText>(R.id.title_editText)
                val inputEditTextFieldDes = dialogLayout.findViewById<EditText>(R.id.des_editText)

                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogLayout)
                    .setPositiveButton("OK") { _, _ ->
                        if (calenderPermission(
                                requireActivity(),
                                callbackId,
                                Manifest.permission.READ_CALENDAR,
                                Manifest.permission.WRITE_CALENDAR
                            )
                        ) {

                            val cr: ContentResolver = requireContext().getContentResolver()
                            val values = ContentValues()

                            values.put(
                                CalendarContract.Events.DTSTART,
                                binding.dateView.text.toString().convertDateToLong()
                            )
                            values.put(
                                CalendarContract.Events.TITLE,
                                inputEditTextFieldTitle.text.toString()
                            )
                            values.put(
                                CalendarContract.Events.DESCRIPTION,
                                inputEditTextFieldDes.text.toString()
                            )
                            val timeZone: TimeZone = TimeZone.getDefault()
                            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID())
                            values.put(CalendarContract.Events.DURATION, "+P1H")
                            values.put(CalendarContract.Events.CALENDAR_ID, 1)

                            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
                            val eventID = uri?.lastPathSegment?.toLong() ?: 0

                            viewModelHome.addEvent(
                                event_description = inputEditTextFieldDes.text.toString(),
                                event_name = inputEditTextFieldTitle.text.toString(),
                                Gregorian_date = viewModelHome.selectedDate,
                                Hijri_date = binding.convertedDate.text.toString().trim(),
                                server_datetime = "",
                                ID = eventID.toString()
                            )
                            Toast.makeText(requireContext(), "Added Done", Toast.LENGTH_LONG).show()

                        }


                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()


            }

        }

    }


}

