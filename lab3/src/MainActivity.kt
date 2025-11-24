package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import java.io.*

class MainActivity : AppCompatActivity() {

  private val prefKey = "pref_key"
  private val internalFile = "internal.txt"
  private val externalFile = "external.txt"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // SharedPreferences
    val prefEdit = findViewById<EditText>(R.id.prefEdit)
    val prefSave = findViewById<Button>(R.id.prefSave)
    val prefLoad = findViewById<Button>(R.id.prefLoad)

    prefSave.setOnClickListener {
      val prefs = getPreferences(MODE_PRIVATE)
      prefs.edit { putString(prefKey, prefEdit.text.toString()) }
      Toast.makeText(this, "Saved to preferences", Toast.LENGTH_SHORT).show()
    }
    prefLoad.setOnClickListener {
      val prefs = getPreferences(MODE_PRIVATE)
      prefEdit.setText(prefs.getString(prefKey, ""))
    }

    // Internal Storage
    val internalEdit = findViewById<EditText>(R.id.internalEdit)
    val internalSave = findViewById<Button>(R.id.internalSave)
    val internalLoad = findViewById<Button>(R.id.internalLoad)

    internalSave.setOnClickListener {
      try {
        openFileOutput(internalFile, MODE_PRIVATE).use {
          it.write(internalEdit.text.toString().toByteArray())
        }
        Toast.makeText(this, "Saved to internal storage", Toast.LENGTH_SHORT).show()
      } catch (e: IOException) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
      }
    }
    internalLoad.setOnClickListener {
      try {
        openFileInput(internalFile).use {
          internalEdit.setText(it.readBytes().toString(Charsets.UTF_8))
        }
      } catch (e: IOException) {
        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
      }
    }

    // External Storage
    val externalEdit = findViewById<EditText>(R.id.externalEdit)
    val externalSave = findViewById<Button>(R.id.externalSave)
    val externalLoad = findViewById<Button>(R.id.externalLoad)

    externalSave.setOnClickListener {
      if (isExternalStorageWritable()) {
        saveExternalFile(externalEdit.text.toString())
      } else {
        Toast.makeText(this, "External storage not writable", Toast.LENGTH_SHORT).show()
      }
    }
    externalLoad.setOnClickListener {
      if (isExternalStorageReadable()) {
        externalEdit.setText(loadExternalFile())
      } else {
        Toast.makeText(this, "External storage not readable", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun saveExternalFile(text: String) {
    try {
      val file = File(getExternalFilesDir(null), externalFile)
      FileOutputStream(file).use { it.write(text.toByteArray()) }
      Toast.makeText(this, "Saved to external storage", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
      Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
  }

  private fun loadExternalFile(): String {
    return try {
      val file = File(getExternalFilesDir(null), externalFile)
      FileInputStream(file).use { it.readBytes().toString(Charsets.UTF_8) }
    } catch (e: IOException) {
      ""
    }
  }

  private fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
  }

  private fun isExternalStorageReadable(): Boolean {
    val state = Environment.getExternalStorageState()
    return state == Environment.MEDIA_MOUNTED || state == Environment.MEDIA_MOUNTED_READ_ONLY
  }
}
