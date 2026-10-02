package br.ulbra.jogoadivinha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

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

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnJogar = findViewById<Button>(R.id.btnJogar)

        btnJogar.setOnClickListener {

            val nome = edtNome.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(
                    this,
                    "Digite seu nome!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val radioSelecionado =
                radioGroup.checkedRadioButtonId

            if (radioSelecionado == -1) {
                Toast.makeText(
                    this,
                    "Escolha um limite!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val radioButton =
                findViewById<RadioButton>(radioSelecionado)

            val maximo =
                radioButton.text.toString().toInt()

            val intent =
                Intent(this, JogoActivity::class.java)

            intent.putExtra("nome", nome)
            intent.putExtra("maximo", maximo)

            startActivity(intent)
        }
    }
}
