package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentCreateTodoBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.DetailViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewModel: DetailViewModel

    // ngeload view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize view model
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val notes = binding.txtNotes.text.toString()
            val todo = Todo(title, notes)

            viewModel.addTodo(todo)
            // alternatif Toast
            Snackbar.make(it, "Todo Created", Snackbar.LENGTH_SHORT).show()
            // mendestroy stack sebelumnya
            it.findNavController().popBackStack()
        }
    }
}