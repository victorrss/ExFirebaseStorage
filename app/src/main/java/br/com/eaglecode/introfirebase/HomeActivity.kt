package br.com.eaglecode.introfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var cr: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        config()
        read()
        btnSalvar.setOnClickListener {
            sendProd(getData())
        }
    }

    fun config() {
        db = FirebaseFirestore.getInstance()
        cr = db.collection("produto")
    }

    fun getData(): MutableMap<String, Any> {
        val prod: MutableMap<String, Any> = HashMap()
        prod["nome"] = edNome.text.toString()
        prod["qtd"] = edQtd.text.toString()
        prod["preco"] = edPreco.text.toString()
        return prod
    }

    fun sendProd(prod: MutableMap<String, Any>) {
        cr.document(prod["nome"].toString()).set(prod)
            .addOnSuccessListener {
                Log.i("HomeAct", "Cadastrado com sucesso")
            }.addOnFailureListener {
                Log.i("HomeAct", "Falha")
            }
    }

    fun update(key: String, prod: MutableMap<String, Any>) {
        cr.document(key).update(prod)
            .addOnSuccessListener {
                Log.i("HomeAct", "Atualizado com sucesso")
            }.addOnFailureListener {
                Log.i("HomeAct", "Falha")
            }
    }

    fun delete(key: String) {
        cr.document(key).delete()
            .addOnSuccessListener {
                Log.i("HomeAct", "Deletado com sucesso")
            }.addOnFailureListener {
                Log.i("HomeAct", "Falha")
            }
    }

    fun read() {
        cr.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    for (document in task.result!!)
                        Log.d("HomeAct", document.id + " => " + document.data)
                else
                    Log.w("HomeAct", "Error getting documents.", task.exception)
            }
            .addOnSuccessListener {
                Log.i("HomeAct", "Lido com sucesso")
            }.addOnFailureListener {
                Log.i("HomeAct", "Falha")
            }
    }
}