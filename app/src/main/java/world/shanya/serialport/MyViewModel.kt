package world.shanya.serialport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejlchina.okhttps.GsonMsgConvertor
import com.ejlchina.okhttps.HTTP
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import world.shanya.serialport.update.UpdateInfo

class MyViewModel : ViewModel() {

    private val _updateInfoLiveData = MutableLiveData<UpdateInfo>()
    val updateInfoLiveData : LiveData<UpdateInfo>
    get() = _updateInfoLiveData

    var showNewerToast = false

    init {
        _checkUpdate()
    }

    private fun _checkUpdate() {
        val http = HTTP.builder()
            .baseUrl("https://gitee.com/Shanya/Test/raw/master")
            .addMsgConvertor(GsonMsgConvertor())
            .build()
        showNewerToast = false
        http.async("/update.json")
            .setOnResponse { httpResult ->
                val updateInfo = httpResult.body.toBean(UpdateInfo::class.java)
                MainScope().launch {
                    _updateInfoLiveData.value = updateInfo
                }
            }
            .get()
    }

    fun checkUpdate() {
        _checkUpdate()
        showNewerToast = true
    }


}