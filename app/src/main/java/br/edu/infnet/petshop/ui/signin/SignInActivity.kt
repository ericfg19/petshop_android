package br.edu.infnet.petshop.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.api.MeowFact
import br.edu.infnet.petshop.api.MeowFactsService
import br.edu.infnet.petshop.databinding.ActivitySignInBinding
import br.edu.infnet.petshop.ui.home.HomeActivity
import br.edu.infnet.petshop.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_sign_in.factText
import kotlinx.android.synthetic.main.activity_sign_in.txtFinal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val RC_SIGN_IN = 123

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)

        binding.signInButton.visibility = View.VISIBLE

        binding.signInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        var extras = intent.extras

        binding.btnSignUp.setOnClickListener {
            goToSignUpActivity()
        }

        binding.btnLogin.setOnClickListener {
            var textViewEmail = findViewById<EditText>(R.id.editTextEmail).text.toString()
            var textViewPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

            if (extras != null) {
                if(textViewEmail == extras.getString("email") && textViewPassword == extras.getString("password")) {
                    alert("Login realizado com sucesso!")
                    goToHomeActivity()
                    finish()
                }else {
                    alert("Email ou senha incorretos")
                }
            }else {
                alert("Email ou senha incorretos!")
            }
        }

        //ini retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://meowfacts.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MeowFactsService::class.java)

        val call = service.getMeowFact()

        call.enqueue(object : Callback<MeowFact> {
            override fun onResponse(call: Call<MeowFact>, response: Response<MeowFact>) {
                if (response.isSuccessful) {
                    val meowFact = response.body()
                    if (meowFact != null) {
                        // Exibir a frase da textview
                        factText.text = meowFact.fact
                        // Faça algo com a frase, como exibi-la em um TextView

                    }
                }
            }

            override fun onFailure(call: Call<MeowFact>, t: Throwable) {
                // Trate o erro aqui
            }
        })

        //end retrofit
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            goToHomeActivity()
        } catch (e: ApiException) {

        }
    }

    private fun alert(stringAlert: String) {
        Toast.makeText(this, stringAlert, Toast.LENGTH_LONG).show()
    }

    private fun goToHomeActivity() {
        var servicosActivity = Intent(this, HomeActivity::class.java)
        startActivity(servicosActivity)
    }

    private fun goToSignUpActivity() {
        var signUpActivity = Intent(this, SignUpActivity::class.java)
        startActivity(signUpActivity)
    }
}