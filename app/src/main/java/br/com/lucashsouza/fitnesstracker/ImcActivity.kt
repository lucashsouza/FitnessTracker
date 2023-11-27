package br.com.lucashsouza.fitnesstracker

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import co.tiagoaguiar.fitnesstracker.R

class ImcActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        etWeight = findViewById(R.id.edit_imc_weight)
        etHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_send)
        btnSend.setOnClickListener {

            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = etWeight.text.toString().toInt()
            val height = etHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            Log.d("Teste", "resultado: ${result}")

            val imcResponseId = imcResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(R.string.calc)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    Toast.makeText(this, "Botão clicado!", Toast.LENGTH_SHORT).show()
                })
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> return R.string.imc_extreme_weight
        }
    }

    private fun validate(): Boolean {
        return etWeight.text.toString().isNotEmpty()
                && etHeight.text.toString().isNotEmpty()
                && !etWeight.text.toString().startsWith("0")
                && !etHeight.text.toString().startsWith("0")
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))
    }

}