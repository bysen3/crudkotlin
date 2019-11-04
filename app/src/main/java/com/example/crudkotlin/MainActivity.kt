package com.example.crudkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var btnStore: Button? = null
    private var btnGetall: Button? = null
    private var etname: EditText? = null
    private var ethobby: EditText? = null
    private var etcity: EditText? = null
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        btnStore = findViewById(R.id.btnstore) as Button
        btnGetall = findViewById(R.id.btnget) as Button
        etname = findViewById(R.id.etname) as EditText
        ethobby = findViewById(R.id.ethobby) as EditText
        etcity = findViewById(R.id.etcity) as EditText

        btnStore!!.setOnClickListener {
            databaseHelper!!.addUser(etname!!.text.toString(), ethobby!!.text.toString(), etcity!!.text.toString())
            etname!!.setText("")
            ethobby!!.setText("")
            etcity!!.setText("")
            Toast.makeText(this@MainActivity, "Stored Successfully!", Toast.LENGTH_SHORT).show()
        }

        btnGetall!!.setOnClickListener {
            val intent = Intent(this@MainActivity, GetAllUsersActivity::class.java)
            startActivity(intent)
        }

    }
}