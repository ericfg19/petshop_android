package br.edu.infnet.petshop.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.databinding.ActivityHomeBinding
import br.edu.infnet.petshop.ui.contato.ContatoActivity
import br.edu.infnet.petshop.ui.listaservicos.ListaServicosActivity
import br.edu.infnet.petshop.ui.perfil.PerfilActivity
import br.edu.infnet.petshop.ui.signin.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listaServicos.setOnClickListener{
            var serviceActivity = Intent(this, ListaServicosActivity::class.java)
            startActivity(serviceActivity)
        }

        binding.minhaConta.setOnClickListener{
            var accountActivity = Intent(this, PerfilActivity::class.java)
            startActivity(accountActivity)
        }

        binding.lojaInfo.setOnClickListener{
            var storeActivity = Intent(this, ContatoActivity::class.java)
            startActivity(storeActivity)
        }

        binding.btnSignOut.setOnClickListener{
            goToSignInActivity()
            finish()
        }
    }

    private fun goToSignInActivity() {
        var loginActivity = Intent(this, SignInActivity::class.java)
        startActivity(loginActivity)
    }

}