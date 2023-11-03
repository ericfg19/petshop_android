package br.edu.infnet.petshop.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.databinding.ActivitySignUpBinding
import br.edu.infnet.petshop.ui.signin.SignInActivity
import com.google.android.material.textfield.TextInputLayout


class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val passwordConfirmationLiveData = MutableLiveData<String>()

    private val isValidLiveData = MediatorLiveData<Boolean>().apply { this.value = false
        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            val passwordConfirmation = passwordConfirmationLiveData.value
            this.value = validateForm(email, password, passwordConfirmation)
        }

        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            val passwordConfirmation = passwordConfirmationLiveData.value
            this.value = validateForm(email, password, passwordConfirmation)
        }

        addSource(passwordConfirmationLiveData) { passwordConfirmation ->
            val email = emailLiveData.value
            val password = passwordLiveData.value
            this.value = validateForm(email, password, passwordConfirmation)
        }
    }

    private fun validateForm(email: String?, password: String?, passwordConfirmation: String?): Boolean? {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank()
        val isValidPasswordConfirmation = passwordConfirmation != null && passwordConfirmation.isNotBlank()
        val isEqualPasswordAndPasswordConfirmation = password == passwordConfirmation

        return isValidEmail && isValidPassword && isValidPasswordConfirmation && isEqualPasswordAndPasswordConfirmation
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btnSignUp = findViewById<Button>(R.id.buttonSignUp)
        var editTextEmail = findViewById<TextInputLayout>(R.id.editTextEmail)
        var editTextPassword = findViewById<TextInputLayout>(R.id.editTextPassword)
        var editTextConfirmPassword = findViewById<TextInputLayout>(R.id.editTextConfirmPassword)

        editTextEmail.editText?.doOnTextChanged{ text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        editTextPassword.editText?.doOnTextChanged{ text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        editTextConfirmPassword.editText?.doOnTextChanged{ text, _, _, _ ->
            passwordConfirmationLiveData.value = text?.toString()
        }

        isValidLiveData.observe(this) { isValid ->
            btnSignUp.isEnabled = isValid
        }

        btnSignUp.setOnClickListener {
            alert("Cadastro realizado com sucesso!")
            goToSignInActivity(emailLiveData.value.toString(), passwordConfirmationLiveData.value.toString())
        }
    }

    private fun alert(stringAlert: String) {
        Toast.makeText(this, stringAlert, Toast.LENGTH_LONG).show()
    }

    private fun goToSignInActivity(email: String, password: String) {
        var signInActivity = Intent(this, SignInActivity::class.java)
        signInActivity.putExtra("email", email)
        signInActivity.putExtra("password", password)
        startActivity(signInActivity)
    }
}