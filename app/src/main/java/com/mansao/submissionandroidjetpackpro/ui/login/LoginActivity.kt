package com.mansao.submissionandroidjetpackpro.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

            when {
                email.isEmpty() -> {
                    edtEmail.error = resources.getString(R.string.fill_email)
                    edtEmail.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    edtEmail.error = resources.getString(R.string.not_valid_email)
                    edtEmail.requestFocus()
                }
                password.isEmpty() -> {
                    edtPassword.error = resources.getString(R.string.fill_password)
                    edtPassword.requestFocus()
                }
                password.length < 6 -> {
                    edtPassword.error = resources.getString(R.string.not_valid_password)
                    edtPassword.requestFocus()
                }
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
                        startActivity(intent)
                        Toast.makeText(
                            this,
                            StringBuilder("${resources.getString(R.string.hello)} : ").append(email),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
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