package br.edu.infnet.petshop.ui.listaservicos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.ui.servico.ServicoActivity
import br.edu.infnet.petshop.utils.ServicoEnum
import kotlinx.android.synthetic.main.servico_item_view.view.attendanceTv
import kotlinx.android.synthetic.main.servico_item_view.view.daysTv
import kotlinx.android.synthetic.main.servico_item_view.view.hourTv
import kotlinx.android.synthetic.main.servico_item_view.view.imageTv
import kotlinx.android.synthetic.main.servico_item_view.view.priceTv
import kotlinx.android.synthetic.main.servico_item_view.view.titleTv

class ServicoItemAdapter(
    private val arrayList: ArrayList<ServicoEnum>,
    val context: Context
) :
    RecyclerView.Adapter<ServicoItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(servicoEnum: ServicoEnum) {
            itemView.titleTv.text = servicoEnum.title
            itemView.attendanceTv.text = servicoEnum.attendance
            itemView.daysTv.text = servicoEnum.days
            itemView.hourTv.text = servicoEnum.hour
            itemView.priceTv.text = servicoEnum.value
            itemView.imageTv.setImageResource(servicoEnum.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.servico_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        val servico = arrayList[position]
        val bundle = Bundle()
        bundle.putParcelable("id", servico)
        val intent = Intent(this.context, ServicoActivity::class.java)
        intent.putExtra("SERVICO", servico.id.toString())

        holder.itemView.setOnClickListener {
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}