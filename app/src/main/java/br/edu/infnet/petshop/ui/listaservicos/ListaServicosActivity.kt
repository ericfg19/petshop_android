package br.edu.infnet.petshop.ui.listaservicos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.petshop.databinding.ActivityListaServicosBinding
import br.edu.infnet.petshop.utils.ServicoEnum

class ListaServicosActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaServicosBinding
    private var servicoItemAdapter: RecyclerView.Adapter<ServicoItemAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaServicosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayList = ArrayList<ServicoEnum>()
        val recyclerView = binding.listaServicos

        arrayList.add(ServicoEnum.BANHO)
        arrayList.add(ServicoEnum.TOSA)
        arrayList.add(ServicoEnum.VACINA)
        arrayList.add(ServicoEnum.CONSULTA)
        arrayList.add(ServicoEnum.TAXI_DOG)

        recyclerView.adapter = servicoItemAdapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ServicoItemAdapter(arrayList, this.context)
        }
    }
}