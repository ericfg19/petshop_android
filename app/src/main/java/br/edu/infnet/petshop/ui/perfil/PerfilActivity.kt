package br.edu.infnet.petshop.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.ServicoModel
import br.edu.infnet.petshop.databinding.ActivityPerfilBinding
import br.edu.infnet.petshop.ui.listaservicos.ServicoItemAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_perfil.recyclerViewHistory

class PerfilActivity : AppCompatActivity() {

    private var servicoItemAdapter: RecyclerView.Adapter<ServicoItemAdapter.ViewHolder>? = null
    lateinit var binding: ActivityPerfilBinding
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Firebase.firestore
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()

        val arrayList = ArrayList<ServicoModel>()
        db.collection("agendamentos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var imagem: Int = R.drawable.banho_logo
                    if(document.data["title"].toString() == "Tosa") {
                        imagem = R.drawable.tosa_logo
                    }
                    if(document.data["title"].toString() == "Vacina") {
                        imagem = R.drawable.vacina_logo
                    }
                    if(document.data["title"].toString() == "Consulta") {
                        imagem = R.drawable.consulta_logo
                    }
                    if(document.data["title"].toString() == "Taxi Dog") {
                        imagem = R.drawable.taxi
                    }
                    var servico = ServicoModel(document.data["title"].toString(),
                        document.data["attendance"].toString(),
                        document.data["days"].toString(),
                        document.data["hour"].toString(),
                        document.data["value"].toString(),
                        imagem)

                    arrayList.add(servico)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        recyclerViewHistory.adapter = servicoItemAdapter
        recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = PerfilServicoItemAdapter(arrayList, this.context)
        }

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
                startAdSense()
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }

        }


    }

    fun startAdSense() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

}