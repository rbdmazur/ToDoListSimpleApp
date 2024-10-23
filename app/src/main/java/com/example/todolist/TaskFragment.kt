package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.todolist.databinding.FragmentTaskBinding
import com.example.todolist.model.Task
import kotlinx.coroutines.launch
import java.util.Date


class TaskFragment : Fragment() {

    private val args: TaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by viewModels() {
        TaskViewModelFactory(args.id)
    }
    private var _binding: FragmentTaskBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "cannot access view in task fragment"
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.task.collect { crime ->
                    crime?.let { updateUi(it) }
                }
            }
        }
    }

    private fun updateUi(task: Task) {
        binding.apply {
            titleTextView.text = task.title
            descriptionTextView.text = task.title
            dateButton.text = task.date.toString()
            solvedCheckBox.isChecked = task.isComplete
        }
    }

}