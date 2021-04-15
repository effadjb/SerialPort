package world.shanya.serialport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.azhon.appupdate.utils.ApkUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import world.shanya.serialport.fragment.KeyboardFragment
import world.shanya.serialport.fragment.MessageFragment
import world.shanya.serialport.fragment.TerminalFragment
import world.shanya.serialport.tools.SPName
import world.shanya.serialport.tools.SPUtil
import world.shanya.serialport.tools.SharedPreferencesUtil

private const val AUTO_CONNECT = "AUTO_CONNECT"

class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: MyViewModel
    private lateinit var serialPort: SerialPort

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serialPort = SerialPortBuilder
                .isDebug(true)
                .autoConnect(true)
                .build(this)

        myViewModel = ViewModelProvider(this, NewInstanceFactory())[MyViewModel::class.java]

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

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int) =
                    when (position) {
                        0 -> MessageFragment()
                        1 -> KeyboardFragment()
                        else -> TerminalFragment()
                    }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                title = when (tab?.position) {
                    0 -> "通信"
                    1 -> "按键"
                    else -> "自定义键盘"
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
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
        }
        return super.onOptionsItemSelected(item)
    }
}