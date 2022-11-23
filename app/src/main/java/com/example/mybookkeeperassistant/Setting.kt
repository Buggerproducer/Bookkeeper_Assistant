package com.example.mybookkeeperassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import database.ManageDB
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setting_back.setOnClickListener{
            finish()
        }
        setting_delete_all.setOnClickListener {
            showWarning()
        }
    }

    private fun showWarning() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Warning").setMessage("Are you sure to delete ALL RECORDS!")
            .setNegativeButton("Cancel", null)
            .setPositiveButton(
                "Sure"
            ) { dialogInterface, i ->
                ManageDB.deleteAll()
                Toast.makeText(this,"Delete Successfully",Toast.LENGTH_SHORT).show()

            }
        builder.create().show() //make the dialog visible
    }
}