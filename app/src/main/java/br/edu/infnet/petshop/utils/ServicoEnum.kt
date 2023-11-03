package br.edu.infnet.petshop.utils

import android.os.Parcelable
import br.edu.infnet.petshop.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ServicoEnum(
    var id: Int, val title: String,
    val attendance: String,
    val days: String,
    val hour: String,
    val value: String,
    val image: Int
) : Parcelable {
    BANHO(0, "Banho", "Atendimento:", "Segunda a sexta", "8h-15h", "R$50,00", R.drawable.banho_logo),
    TOSA(1, "Tosa", "Atendimento:", "Segunda a sexta", "8h-15h", "R$70,00", R.drawable.tosa_logo),
    VACINA(2, "Vacina","Atendimento:", "Segunda a sexta", "8h-15h", "Valor de acordo com a vacina", R.drawable.vacina_logo),
    CONSULTA(3, "Consulta", "Atendimento:", "Segunda a sexta", "8h-15h", "R$50,00", R.drawable.consulta_logo),
    TAXI_DOG(4, "Taxi Dog", "Atendimento:", "Segunda a sexta", "8h-15h", "Valor de acordo com a distancia", R.drawable.taxi);

    companion object {
        fun getById(id: Int): ServicoEnum? {
            var enumR: ServicoEnum? = null
            for (enum in values()) {
                if (enum.id == id) {
                    enumR = enum
                }
            }
            return enumR
        }
    }
}