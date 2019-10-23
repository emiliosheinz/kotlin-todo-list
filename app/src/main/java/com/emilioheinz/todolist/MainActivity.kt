package com.emilioheinz.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import com.emilioheinz.todolist.R
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        add_todo_button.setOnClickListener {
            var todoContent = findViewById<EditText>(R.id.add_todo_input)

            val prefs = getSharedPreferences("com.emilioheinz.todolist", Context.MODE_PRIVATE)
            val editor = prefs.edit()

            val prevTodos = prefs.getStringSet("USER_TODOS", null)
            val mySet = HashSet<String>(prevTodos)

            mySet.add(todoContent.text.toString())

            editor.putStringSet("USER_TODOS", mySet)
            editor.commit()

            val currentTodos = prefs.getStringSet("USER_TODOS", null)

            var currentTodosArray = ArrayList<String>()

            currentTodos?.forEach { currentTodosArray.add(it) }

            val listView = findViewById<ListView>(R.id.todo_list_view)
            val listItems = arrayOfNulls<String>(currentTodosArray.size)

            for (i in 0 until currentTodosArray.size) {
                val todo = currentTodosArray[i]
                listItems[i] = todo
            }


            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
            listView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
