package world.shanya.serialportassistant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejlchina.okhttps.GsonMsgConvertor
import com.ejlchina.okhttps.HTTP
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import world.shanya.serialport.SerialPortBuilder
import world.shanya.serialportassistant.message.MessageUtil
import world.shanya.serialportassistant.tools.SerialPortText
import world.shanya.serialportassistant.update.UpdateInfo

class MyViewModel : ViewModel() {

    private val _updateInfoLiveData = MutableLiveData<UpdateInfo>()
    val updateInfoLiveData : LiveData<UpdateInfo>
    get() = _updateInfoLiveData

    var showNewerToast = false

    val keyboardColorLiveData = MutableLiveData<Int>()

    val sendDownData = HashMap<Int, String>()
    val sendUpData = HashMap<Int, String>()
    val sendDownType = HashMap<Int, String>()
    val sendUpType = HashMap<Int, String>()

    init {
        _checkUpdate()
        SerialPortBuilder.setReceivedDataListener {
            MessageUtil.receivedMessage(it)
        }
    }

    private fun _checkUpdate() {
        val http = HTTP.builder()
            .baseUrl(SerialPortText.updateBaseUrl)
            .addMsgConvertor(GsonMsgConvertor())
            .build()
        showNewerToast = false
        http.async(SerialPortText.updateUrl)
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