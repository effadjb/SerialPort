package world.shanya.serialportassistant

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import world.shanya.serialportassistant.tools.SerialPortText
import world.shanya.serialportassistant.tools.SharedPreferencesUtil

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        particleView.startAnim()
        particleView.setOnParticleAnimListener {
            textViewVersion.visibility = View.VISIBLE
            textViewVersionName.text = BuildConfig.VERSION_NAME.toString()
            textViewVersionName.visibility = View.VISIBLE
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, SerialPortText.REQUEST_ENABLE_BT)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
//            if (!BluetoothAdapter.getDefaultAdapter().isEnabled) {
//                BluetoothAdapter.getDefaultAdapter().enable()
//            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SerialPortText.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Thread.sleep(100)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                finish()
            }
        }
    }
}