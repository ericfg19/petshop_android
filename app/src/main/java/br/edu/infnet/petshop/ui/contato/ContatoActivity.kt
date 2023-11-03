package br.edu.infnet.petshop.ui.contato

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.databinding.AcitivityContatoBinding
class ContatoActivity : AppCompatActivity() {

    lateinit var binding: AcitivityContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityContatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phoneNumber = binding.phoneCall.text.toString()
        val email = binding.emailAddress.text.toString()
        binding.phoneCall.setOnClickListener{
            val intentPhoneCall = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intentPhoneCall)
        }

        binding.emailAddress.setOnClickListener{
            val intentMail = Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intentMail)
        }
    }
}