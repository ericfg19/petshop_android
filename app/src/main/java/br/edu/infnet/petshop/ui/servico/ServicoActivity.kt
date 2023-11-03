package br.edu.infnet.petshop.ui.servico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.ServicoModel
import br.edu.infnet.petshop.databinding.ActivityServicoBinding
import br.edu.infnet.petshop.ui.home.HomeActivity
import br.edu.infnet.petshop.utils.ServicoEnum
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ServicoActivity : AppCompatActivity() {

    lateinit var binding: ActivityServicoBinding
    private var TAG = "DataBase"
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore
        super.onCreate(savedInstanceState)
        binding = ActivityServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val servicoId = ServicoEnum.getById(intent.getStringExtra("SERVICO")!!.toInt())
        servicoId?.image?.let { binding.imagemServico.setImageResource(it) }
        binding.tituloServico.text = servicoId?.title
        binding.subtituloServico.text = "${servicoId?.days}, ${servicoId?.hour}"
        binding.btnAgendar.setOnClickListener {
            val servico = ServicoModel(
                binding.tituloServico.text.toString(),
                "Atendimento",
                binding.diaServico.text.toString(),
                binding.horarioServico.text.toString(),
                servicoId!!.value,
                1
            )

            db.collection("agendamentos")
                .add(servico)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}