package com.maliha.i202606

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.maliha.i202606.databinding.LoginPgBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:LoginPgBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.login_pg)
        supportActionBar?.hide()

        binding = LoginPgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //var forgotbtn = findViewById<Button>(R.id.forgot_pwd_btn)
        binding.forgotPwdBtn.setOnClickListener {
            startActivity(
                Intent(this, ForgotPwd::class.java));
        }

//        var loginBtn = findViewById<Button>(R.id.login_btn)
//        binding.loginBtn.setOnClickListener {
//            startActivity(
//                Intent(this, MainPage::class.java));
//        }

//        var signup = findViewById<Button>(R.id.signup_btn)
        binding.signupBtn.setOnClickListener{
            startActivity(Intent(this, SignUp::class.java))
        }

        binding.loginBtn.setOnClickListener{
            val email = binding.emailEdittxt.text.toString()
            val pwd = binding.pwdEdittxt.text.toString()

            //validation of fields
            if (email.isNotEmpty() && pwd.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, MainPage::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, "Failed to sign in", Toast.LENGTH_SHORT)
                    }
                }
            }
            else
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT)
        }
    }
}
