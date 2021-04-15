package world.shanya.serialport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.ejlchina.okhttps.GsonMsgConvertor
import com.ejlchina.okhttps.HTTP
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import world.example.serialport.update.UpdateInfo

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val http = HTTP.builder()
            .baseUrl("https://gitee.com/Shanya/Test/raw/master")
            .addMsgConvertor(GsonMsgConvertor())
            .build()

        var updateInfo: UpdateInfo? = null

        val downloadManager = DownloadManager.getInstance(this)

        button.setOnClickListener {
            http.async("/update.json")
                .setOnResponse { httpResult ->
                    updateInfo = httpResult.body.toBean(UpdateInfo::class.java)
                    updateInfo?.let {
                        MainScope().launch {
                            val updateConfiguration = UpdateConfiguration()
                            updateConfiguration.isForcedUpgrade = it.mandatoryUpdate == 1

                            downloadManager.setApkName(it.fileName)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setConfiguration(updateConfiguration)
                                .setApkUrl(it.downloadUrl)
                                .setApkVersionCode(it.versionCode)
                                .setApkDescription(it.updateContent)
                                .download()
                        }

                    }
                }
                .get()
        }
    }
}