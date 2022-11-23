package com.example.mybookkeeperassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.ManageDB
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class Register : AppCompatActivity() {
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
    lateinit var userName:EditText
    lateinit var phone:EditText
    lateinit var email:EditText
    lateinit var login:Button
    lateinit var register:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        userName = register_username
        password = register_password
        confirmPassword = register_repassword
        phone = register_phone
        email = register_email
        login = register_login
        register = register_register
        login.setOnClickListener {
            finish()
        }
        register.setOnClickListener {
            val username_text = userName.text.toString()
            val password_text = password.text.toString()
            val repassword_text = confirmPassword.text.toString()
            val phone_text = phone.text.toString()
            val email_text = email.text.toString()
            if (!isEmpty(username_text,password_text,repassword_text,email_text,phone_text)){
                Toast.makeText(this,"Please fill all required information",Toast.LENGTH_SHORT).show()

            }
            if (!isEmail(email_text)){
                Toast.makeText(this,"Invalid Email Format",Toast.LENGTH_SHORT).show()
                email.setText("")
            }
            if (!phoneChect(phone_text)){
                Toast.makeText(this,"Invalid Phone Format or it has been registered",Toast.LENGTH_SHORT).show()
                phone.setText("")
            }
            if (!passwordCheck(password_text,repassword_text)){
                confirmPassword.setText("")
                Toast.makeText(this,"confirm password is different from the password",Toast.LENGTH_SHORT).show()
            }
            if (isEmail(email_text)&&phoneChect(phone_text)&&passwordCheck(password_text,repassword_text)&&isEmpty(username_text,password_text,repassword_text,email_text,phone_text)){
                val date = Date()
                val d = SimpleDateFormat("yyyy-MM-dd")
                val dateTime = d.format(date)
                ManageDB.insertToUser(username_text,password_text,phone_text,email_text,this,0f,dateTime)
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }




        }
    }
    fun isEmpty(userName:String,password: String,rePassword: String,phone: String,strEmail:String):Boolean{
        return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(rePassword) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(strEmail)
    }

    fun isEmail(strEmail:String): Boolean {
        //use the regular formation to test the information
        val strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"
        if(TextUtils.isEmpty(strEmail)){
            return false;
        }else{
            return strEmail.matches(Regex(strPattern))
        }
    }

    fun passwordCheck(password:String,rePassword:String):Boolean{
        return password == rePassword
    }

    fun phoneChect(phone:String):Boolean{
        val repeat = ManageDB.checkRepeatPhone(phone)

        return  phone.length == 11 && repeat
    }


}