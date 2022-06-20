package com.mansao.submissionandroidjetpackpro.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.databinding.ActivityRegisterBinding
import com.mansao.submissionandroidjetpackpro.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        binding.apply {
            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register() {
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

            registerFirebase(email, password)
        }
    }

    private fun registerFirebase(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.registered),
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onPause() {
        super.onPause()
        auth.signOut()
    }
}