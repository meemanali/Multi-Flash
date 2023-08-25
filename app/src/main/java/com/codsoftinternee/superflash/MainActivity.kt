package com.codsoftinternee.superflash

import android.app.AlertDialog
import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.codsoftinternee.superflash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFrontFlashOn = false
    private var isBackFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.toggle1.setOnClickListener { updateUI() }

        binding.imageView1.setOnClickListener { setFlash() }
    }

    private fun setFlash() {
        val camManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val isBackFlash = binding.toggle1.text == getString(R.string.back_flash)
        val flashType = if (isBackFlash) 0 else 1
        var isFlashOn = if (isBackFlash) isBackFlashOn else isFrontFlashOn

        try {
            isFlashOn = if (isFlashOn) {
//                camManager.setTorchMode(camManager.cameraIdList[flashType], false)
                binding.imageView1.setImageResource(R.drawable.off_bulb)
                false
            } else {
//                camManager.setTorchMode(camManager.cameraIdList[flashType], true)
                binding.imageView1.setImageResource(R.drawable.on_bulb)
                true
            }
        } catch (e: Exception) {
            AlertDialog.Builder(this).setTitle("Error").setMessage(e.localizedMessage)
                .setPositiveButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }.show()
        }

        if (isBackFlash) isBackFlashOn = isFlashOn
        else isFrontFlashOn = isFlashOn

        binding.imageView1.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.scale_bounce_anim
            )
        )
    }

    private fun updateUI() {
        if (binding.toggle1.text == R.string.back_flash.toString())
            updateImages(isBackFlashOn)
        else
            updateImages(isFrontFlashOn)
    }

    private fun updateImages(status: Boolean) {
        if (status) binding.imageView1.setImageResource(R.drawable.on_bulb)
        else binding.imageView1.setImageResource(R.drawable.off_bulb)
    }
}