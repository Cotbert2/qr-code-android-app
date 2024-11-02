package com.mgsoftware.qrcode

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

private const val CAMERA_REQUEST_DOCE = 101
var QRURL = "https://google.com"

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        findViewById<Button>(R.id.Button).setOnClickListener{
            val intentweb = Intent(this, Page_Activity ::class.java).apply {
                putExtra(EXTRA_MESSAGE,QRURL )
            }
            startActivity(intentweb)
            //Toast.makeText(this, "THINGS TODO", Toast.LENGTH_SHORT).show()
        }


        setUpPermission()
        codeScanner()

        //vars and const in the onCreat method


        // findViewById<Button>(R.id.buttonScanner).setOnClickListener(object: View.OnClickListener{
        //    override fun onClick(v: View?) {
        //        activateScan()
        //    }
        //})


    }

    private fun codeScanner(){
        codeScanner = CodeScanner(this, findViewById(R.id.scannerView))

        codeScanner.apply {
            camera= CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                //A callback to the Scanner
                runOnUiThread{
                    findViewById<TextView>(R.id.textView).text = it.text
                    QRURL=it.text
                    enableButton()
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("Main","[Error]: ${it.message}")
                }
            }
        }

        findViewById<CodeScannerView>(R.id.scannerView).setOnClickListener{
            codeScanner.startPreview()
        }

    }

    public fun activateScan () {
        IntentIntegrator(this).initiateScan();
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var result : IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)


    }

    override fun onResume() {
        super.onResume()
        //codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    fun setUpPermission() {
        val permission= ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_DOCE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_DOCE -> {
                if( grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                   // Toast.makeText(this, "Enable the Camera permission to scann the QR code", Toast.LENGTH_SHORT).show()
                }else {

                }
            }
        }
    }

    fun enableButton(){
        findViewById<Button>(R.id.Button).isEnabled = true
    }
}