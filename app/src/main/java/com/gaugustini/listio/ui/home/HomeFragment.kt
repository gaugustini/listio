package com.gaugustini.listio.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gaugustini.listio.R
import com.gaugustini.listio.databinding.FragmentHomeBinding
import com.gaugustini.listio.model.Task
import com.gaugustini.listio.ui.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setAdapter()
        fabClick()

        return binding.root
    }

    private fun setAdapter() {
        val adapter = TaskAdapter(
            onClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToEntry(it.id)
                )
            },
            onMenuClick = { view, task -> showPopMenuItem(view, task) }
        )

        binding.taskList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner) {
            binding.hasTasks = it.isEmpty()
            adapter.submitList(it)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showPopMenuItem(view: View, task: Task) {
        context?.let { context ->
            val popup = PopupMenu(context, view).apply {
                inflate(R.menu.menu_task_item)
                setOnMenuItemClickListener { menuItem: MenuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeToEntry(task.id)
                            )
                        }
                        R.id.action_delete -> {
                            viewModel.delete(task)
                        }
                    }
                    true
                }
            }

            if (popup.menu is MenuBuilder) {
                val menuBuilder = popup.menu as MenuBuilder
                menuBuilder.setOptionalIconsVisible(true)
                for (item in menuBuilder.visibleItems) {
                    val iconMarginPx =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            8f,
                            resources.displayMetrics
                        ).toInt()
                    if (item.icon != null) {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
            popup.show()
        }
    }

    private fun fabClick() {
        binding.fab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToEntry()
            )
        }
    }

}
