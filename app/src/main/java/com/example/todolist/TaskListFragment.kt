package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentTaskListBinding
import com.example.todolist.model.Task
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlin.random.Random


class TaskListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentTaskListBinding? = null
    private val binding: FragmentTaskListBinding
        get() = checkNotNull(_binding) {
            "TaskListFragment view is not initialized"
        }
    private val viewModel: TaskListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.listView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect { tasks ->
                    binding.listView.adapter = TaskListAdapter(tasks) { taskId ->
                        findNavController().navigate(
                            TaskListFragmentDirections.showTaskDetails(taskId)
                        )
                    }
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.task_list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.add_menu_item -> {
                addNewTask()
                true
            }
            else -> false
        }
    }


    private fun addNewTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newTask = Task(
                UUID.randomUUID(),
                "Some title",
                "Some description",
                Date(),
                false
            )
            viewModel.addTask(newTask)
            findNavController().navigate(
                TaskListFragmentDirections.showTaskDetails(newTask.id)
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}