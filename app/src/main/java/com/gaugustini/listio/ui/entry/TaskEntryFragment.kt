package com.gaugustini.listio.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gaugustini.listio.databinding.FragmentTaskEntryBinding
import com.gaugustini.listio.util.text
import com.gaugustini.listio.util.toDateString
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEntryFragment : Fragment() {

    private val viewModel: TaskEntryViewModel by viewModels()
    private val args: TaskEntryFragmentArgs by navArgs()
    private lateinit var binding: FragmentTaskEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskEntryBinding.inflate(inflater, container, false)

        val isEditing = args.taskId > 0

        setTask(isEditing)
        clickListeners()

        return binding.root
    }

    private fun setTask(editing: Boolean) {
        if (editing) {
            viewModel.getTask(args.taskId).observe(viewLifecycleOwner) {
                binding.task = it
                binding.editing = true
            }
        }
    }

    private fun clickListeners() {
        binding.date.editText?.setOnClickListener { datePicker() }
        binding.hour.editText?.setOnClickListener { timePicker() }
        binding.btnCancel.setOnClickListener { cancelClick() }
        binding.btnSave.setOnClickListener { saveClick() }
    }

    private fun datePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            binding.date.text = it.toDateString()
        }
        datePicker.show(childFragmentManager, null)
    }

    private fun timePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val minute =
                if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
            val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

            binding.hour.text = "$hour:$minute"
        }

        timePicker.show(childFragmentManager, null)
    }

    private fun cancelClick() {
        findNavController().navigate(
            TaskEntryFragmentDirections.actionEntryToHome()
        )
    }

    private fun saveClick() {
        if (isEntryValid()) {
            viewModel.entryTask(
                args.taskId,
                binding.title.editText?.text.toString(),
                binding.date.editText?.text.toString(),
                binding.hour.editText?.text.toString(),
                binding.description.editText?.text.toString()
            )

            findNavController().navigate(
                TaskEntryFragmentDirections.actionEntryToHome()
            )
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.title.editText?.text.toString(),
            binding.date.editText?.text.toString(),
            binding.hour.editText?.text.toString(),
        )
    }

}
