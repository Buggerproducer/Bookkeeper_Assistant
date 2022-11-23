package com.example.mybookkeeperassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.ManageDB
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    lateinit var register:Button
    lateinit var login: Button
    lateinit var phone:EditText
    lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //initialize the view
        initView()
    }

    private fun initView() {
        register = login_logout
        login = login_login
        phone = login_phone
        password = login_password
        register.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val isLogin = ManageDB.loginCheck(phone.text.toString(),password.text.toString())
            if (isLogin){
                val user_id = ManageDB.getUserID(phone.text.toString())

                val intent = Intent(this,Index::class.java)
                intent.putExtra("userID",user_id)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Invalid Phone number or password",Toast.LENGTH_SHORT).show()
                phone.setText("")
                password.setText("")
            }
        }
    }


}