package com.example.mvvm_reading_api.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_reading_api.model.Todo
import com.example.mvvm_reading_api.model.TodosApi
import kotlinx.coroutines.launch


sealed interface TodoUIState{
    data class Success(val todos: List<Todo>): TodoUIState
    object Error: TodoUIState
    object Loading: TodoUIState
}



class TodoViewModel:ViewModel() {
    var todoUIState : TodoUIState by mutableStateOf<TodoUIState>(TodoUIState.Loading)
        private set

    init {
        /*todos.add("Test 1")
        todos.add("Test 2")
        todos.add("Test 3")*/
        getTodosList()
    }
    private fun getTodosList(){
        viewModelScope.launch{
            var todosApi: TodosApi? =null
            try {
                todosApi = TodosApi!!.getInstance()
                //todos.clear()
                todoUIState= TodoUIState.Success(todosApi.getTodos())
            }catch (e:Exception){
                Log.d("TODOVIEWMODEL", e.message.toString())
                todoUIState = TodoUIState.Error
            }
        }
    }
}