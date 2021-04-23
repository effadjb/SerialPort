package world.shanya.serialportassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_setting.*
import top.defaults.colorpicker.ColorPickerPopup
import world.shanya.serialportassistant.MyViewModel
import world.shanya.serialportassistant.R
import world.shanya.serialportassistant.tools.SerialPortText
import world.shanya.serialportassistant.tools.SharedPreferencesUtil


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    private lateinit var myViewModel: MyViewModel

    override fun onStart() {
        super.onStart()
        val autoConnect = SharedPreferencesUtil.getString(
            requireActivity(),
            SerialPortText.autoConnectSpName)
        if (autoConnect == null) {
            autoConnectSwitch.isChecked = false
        } else {
            autoConnectSwitch.isChecked = autoConnect == "true"
        }

        keyboardCurrentColor.setBackgroundColor(myViewModel.keyboardColorLiveData.value ?: 0xFF6200EE.toInt())
        keyboardTextCurrentColor.setBackgroundColor(myViewModel.keyboardColorTextLiveData.value ?: 0xFFBBEAF8.toInt())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]
        autoConnectSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                SharedPreferencesUtil.putString(requireActivity(),
                    SerialPortText.autoConnectSpName,"true")
            } else {
                SharedPreferencesUtil.putString(requireActivity(),
                    SerialPortText.autoConnectSpName,"false")
            }
        }

        keyBoardColor.setOnClickListener {
            ColorPickerPopup.Builder(requireActivity())
                .initialColor(0xFF6200EE.toInt())
                .enableAlpha(true)
                .okTitle("确定")
                .cancelTitle("取消")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(object : ColorPickerPopup.ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        myViewModel.keyboardColorLiveData.value = color
                        keyboardCurrentColor.setBackgroundColor(color)
                        SharedPreferencesUtil.putString(
                            requireActivity(),
                            SerialPortText.keyboardColorSpName,
                            color.toString())
                    }
                })
        }

        keyBoardTextColor.setOnClickListener {
            ColorPickerPopup.Builder(requireActivity())
                    .initialColor(0xFFBBEAF8.toInt())
                    .enableAlpha(true)
                    .okTitle("确定")
                    .cancelTitle("取消")
                    .showIndicator(true)
                    .showValue(true)
                    .build()
                    .show(object : ColorPickerPopup.ColorPickerObserver() {
                        override fun onColorPicked(color: Int) {
                            myViewModel.keyboardColorTextLiveData.value = color
                            keyboardTextCurrentColor.setBackgroundColor(color)
                            SharedPreferencesUtil.putString(
                                    requireActivity(),
                                    SerialPortText.keyboardTextColorSpName,
                                    color.toString())
                        }
                    })
        }

    }
}