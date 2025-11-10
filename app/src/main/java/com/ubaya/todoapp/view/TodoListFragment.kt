package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentTodoListBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.DetailViewModel
import com.ubaya.todoapp.viewmodel.ListTodoViewModel

class TodoListFragment : Fragment(), TodoItemListener {
    private lateinit var binding: FragmentTodoListBinding
    private var adapter: TodoListAdapter = TodoListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize view model
        viewModel = ViewModelProvider(this)[ListTodoViewModel::class.java]
        viewModel.refresh() // Select Todo
        observeViewModel()

        // init recView
        binding.recViewTodo.layoutManager = LinearLayoutManager(context)
        binding.recViewTodo.adapter = adapter

        binding.btnFab.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            it.findNavController().navigate(action)
        }
    }

    fun observeViewModel() {
        // memantau perubahan pada todoLD
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            // mengupdate adapter agar isi recView muncul / diperbarui
            adapter.updateTodoList(it)

            if(it.isEmpty()) {
                binding.recViewTodo.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "Your todo still empty."
            } else {
                binding.recViewTodo.visibility = View.VISIBLE
            }
        })

        // memantau perubahan pada progress bar
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        // memantau perubahan pada errorLD
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "Unable to show todo."
            }
        })

    }

    override fun onTodoCheck(todo: Todo) {
        viewModel.clearTask(todo)
    }
}