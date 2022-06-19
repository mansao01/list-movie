package com.mansao.submissionandroidjetpackpro.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.databinding.ActivityLoginBinding
import com.mansao.submissionandroidjetpackpro.ui.home.HomeActivity
import com.mansao.submissionandroidjetpackpro.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.tvToRegister.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        binding.apply {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail.error = "Fill the email"
                edtEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.error = "Email Not Valid"
                edtEmail.requestFocus()
            }

            if (password.isEmpty()) {
                edtPassword.error = "Fill the password"
                edtPassword.requestFocus()
            } else if (password.length < 6) {
                edtPassword.error = "Fill the password"
                edtPassword.requestFocus()
            }

            loginFirebase(email, password)
        }
    }

    private fun loginFirebase(email: String, password: String) {

        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.EXTRA_PROFILE, email)
                        startActivity(intent)
                        Toast.makeText(this, "Hello $email", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
}