package ozwaiqo.randomizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var txtResultNumber: TextView
    private lateinit var edtTxtMin: EditText
    private lateinit var edtTxtMax: EditText
    private lateinit var checkBoxInclusive: CheckBox
    private lateinit var btnCount: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        txtResultNumber = findViewById(R.id.txt_result_number)
        edtTxtMin = findViewById(R.id.edt_txt_min)
        edtTxtMax = findViewById(R.id.edt_txt_max)
        checkBoxInclusive = findViewById(R.id.checkbox_inclusive)
        btnCount = findViewById(R.id.btn_count)


        btnCount.setOnClickListener {
            txtResultNumber.text = getRandomNumber().toString()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getRandomNumber() : Int {

        return if (edtTxtIsValid()){
            if (checkBoxInclusive.isChecked){
                Random.nextInt(edtTxtMin.text.toString().toInt(), edtTxtMax.text.toString().toInt() + 1)
            } else {
                Random.nextInt(edtTxtMin.text.toString().toInt() + 1, edtTxtMax.text.toString().toInt())
            }

        } else {
            edtTxtMin.setText("1")
            edtTxtMax.setText("10")
            checkBoxInclusive.isChecked = true

            Toast.makeText(applicationContext, "Fix the values", Toast.LENGTH_SHORT).show()
            Random.nextInt(edtTxtMin.text.toString().toInt(), edtTxtMax.text.toString().toInt() + 1)
        }
    }

    private fun edtTxtIsValid() : Boolean {
        var fieldsAreNumeric = true
        var maxIsGreaterThanMin = false
        var differenceIsValid = false

        try {
            edtTxtMin.text.toString().toInt()
            edtTxtMax.text.toString().toInt()
        } catch (e: NumberFormatException) {
            fieldsAreNumeric = false
        }

        if (fieldsAreNumeric){
            maxIsGreaterThanMin =
                edtTxtMax.text.toString().toInt() > edtTxtMin.text.toString().toInt()

            differenceIsValid = if (checkBoxInclusive.isChecked){
                abs(edtTxtMax.text.toString().toInt() - edtTxtMin.text.toString().toInt()) >= 1
            } else {
                abs(edtTxtMax.text.toString().toInt() - edtTxtMin.text.toString().toInt()) >= 2
            }

        }

        val notEmpty = edtTxtMin.text.toString() != "" && edtTxtMax.text.toString() != ""

        return fieldsAreNumeric && maxIsGreaterThanMin && differenceIsValid && notEmpty
    }

}