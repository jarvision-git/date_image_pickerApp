package com.example.dateimagepicker


import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dateimagepicker.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    val today = Calendar.getInstance()
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
             var path=uri
            binding.ivImgPicker.setImageURI(path)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dateSetListener=DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            today.set(Calendar.YEAR, year)
            today.set(Calendar.MONTH, month)
            today.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }



        binding.tvDatePicker.setOnClickListener {

                DatePickerDialog(
                    this@MainActivity,
                    dateSetListener,
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        binding.ivImgPicker.setOnClickListener {
            launch()

        }


        }

    private fun launch(){

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))



    }

    private fun updateDateInView(){
        val myFormat="dd/MM/yyyy"
        val sdf= SimpleDateFormat(myFormat, Locale.getDefault())
        binding.tvDatePicker.setText(sdf.format((today.time)).toString())

    }
}