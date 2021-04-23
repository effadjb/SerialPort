package world.shanya.serialportassistant

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.azhon.appupdate.utils.ApkUtil
import world.shanya.serialport.SerialPort
import world.shanya.serialport.SerialPortBuilder
import world.shanya.serialportassistant.tools.SerialPortText
import world.shanya.serialportassistant.tools.SharedPreferencesUtil

class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: MyViewModel
    private lateinit var serialPort: SerialPort

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val autoConnect = SharedPreferencesUtil.getString(this, SerialPortText.autoConnectSpName)

        serialPort = if (autoConnect == null) {
            SerialPortBuilder
                .autoConnect(false)
                .build(this)
        } else {
            if (autoConnect == "true") {
                SerialPortBuilder
                    .autoConnect(true)
                    .build(this)
            } else {
                SerialPortBuilder
                    .autoConnect(false)
                    .build(this)
            }
        }

        myViewModel = ViewModelProvider(this, NewInstanceFactory())[MyViewModel::class.java]

        val keyboardColorTemp = SharedPreferencesUtil.getString(this, SerialPortText.keyboardColorSpName)
        if (keyboardColorTemp != "") {
            myViewModel.keyboardColorLiveData.value = keyboardColorTemp?.toInt()
        }

        myViewModel.updateInfoLiveData.observe(this, Observer {
            it?.let {
                ApkUtil.deleteOldApk(this, externalCacheDir?.path + "/${it.apkName}")

                val updateConfiguration = UpdateConfiguration()
                updateConfiguration.isForcedUpgrade = it.forcedUpgrade == 1
                updateConfiguration.setEnableLog(false)
                updateConfiguration.isUsePlatform = false

                DownloadManager.getInstance(this)
                    .setSmallIcon(R.mipmap.icon)
                    .setApkName(it.apkName)
                    .setConfiguration(updateConfiguration)
                    .setShowNewerToast(myViewModel.showNewerToast)
                    .setApkUrl(it.apkUrl)
                    .setApkSize(it.apkSize)
                    .setApkVersionCode(it.apkVersionCode)
                    .setApkVersionName(it.apkVersionName)
                    .setApkDescription(it.apkDescription)
                    .setApkMD5(it.apkMD5)
                    .download()
            }
        })

        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.fragment))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuCheckUpdate -> {
                myViewModel.checkUpdate()
            }
            R.id.menuConnect -> {
                serialPort.openDiscoveryActivity()
            }
            R.id.menuSetting -> {
                findNavController(R.id.fragment).navigate(R.id.action_mainFragment_to_settingFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fragment).navigateUp()
    }
}