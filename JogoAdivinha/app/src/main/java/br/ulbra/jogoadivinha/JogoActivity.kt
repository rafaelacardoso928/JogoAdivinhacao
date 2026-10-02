package br.ulbra.jogoadivinha

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs
import kotlin.random.Random

class JogoActivity : AppCompatActivity() {

    private var numeroSecreto = 0
    private var tentativas = 0
    private var maximo = 0
    private var nome = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_jogo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Recebe os dados da MainActivity
        nome = intent.getStringExtra("nome") ?: "Jogador"

        maximo = intent.getIntExtra("maximo", 10)

        // Sorteia o número secreto
        numeroSecreto = Random.nextInt(1, maximo + 1)

        // Componentes da tela
        val txtBoasVindas =
            findViewById<TextView>(R.id.txtBoasVindas)

        val edtPalpite =
            findViewById<EditText>(R.id.edtPalpite)

        val btnChutar =
            findViewById<Button>(R.id.btnChutar)

        val txtResultado =
            findViewById<TextView>(R.id.txtResultado)

        val txtTentativas =
            findViewById<TextView>(R.id.txtTentativas)

        val btnJogarNovamente =
            findViewById<Button>(R.id.btnJogarNovamente)

        // Mensagem inicial
        txtBoasVindas.text =
            "$nome, pensei num número de 1 a $maximo!"

        txtTentativas.text =
            "Tentativas: 0"

        // Botão CHUTAR
        btnChutar.setOnClickListener {

            val textoPalpite =
                edtPalpite.text.toString()

            if (textoPalpite.isEmpty()) {

                Toast.makeText(
                    this,
                    "Digite um palpite!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val palpite =
                textoPalpite.toIntOrNull()

            if (palpite == null) {

                Toast.makeText(
                    this,
                    "Digite apenas números!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (palpite < 1 || palpite > maximo) {

                Toast.makeText(
                    this,
                    "Digite um número entre 1 e $maximo!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Conta a tentativa
            tentativas++

            txtTentativas.text =
                "Tentativas: $tentativas"

            // Verifica se acertou
            if (palpite == numeroSecreto) {

                txtResultado.text =
                    "$nome acertou em $tentativas tentativas!"

                btnChutar.isEnabled = false
                edtPalpite.isEnabled = false

                btnJogarNovamente.visibility =
                    Button.VISIBLE

            } else {

                // Calcula a diferença
                val diferenca =
                    abs(numeroSecreto - palpite)

                // 10% do limite
                val limiteQuente =
                    maximo * 0.10

                val temperatura =
                    if (diferenca <= limiteQuente) {
                        "Quente!"
                    } else {
                        "Frio!"
                    }

                // Diz se o número é maior ou menor
                val direcao =
                    if (numeroSecreto > palpite) {
                        "O número é MAIOR."
                    } else {
                        "O número é MENOR."
                    }

                txtResultado.text =
                    "$temperatura $direcao"
            }

            edtPalpite.text.clear()
        }

        // Botão JOGAR DE NOVO
        btnJogarNovamente.setOnClickListener {

            finish()
        }
    }
}

