package world.shanya.serialportassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_keyboard.*
import world.shanya.serialportassistant.MyViewModel
import world.shanya.serialportassistant.R
import world.shanya.serialport.SerialPort
import world.shanya.serialport.SerialPortBuilder
import world.shanya.serialportassistant.SerialPortConstText
import world.shanya.serialportassistant.tools.DialogUtil
import world.shanya.serialportassistant.tools.SharedPreferencesUtil


class KeyboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyboard, container, false)
    }

    private lateinit var myViewModel: MyViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        myViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]

        val buttons = arrayListOf<Button>(button1,button2,button3,button4,button5,
            button6,button7,button8,button9,button10,
            button11,button12,button13,button14,button15)

        myViewModel.keyboardColorLiveData.observe(requireActivity(), Observer {
            for (button in buttons) {
                button.setBackgroundColor(it)
            }
        })

        for (button in buttons){
            button.text = SharedPreferencesUtil.getString(requireActivity(),"${button.id} Name")
            myViewModel.sendDownData[button.id] =
                SharedPreferencesUtil.getString(requireActivity(), "${button.id} DownData")
                    .toString()
            myViewModel.sendUpData[button.id] =
                SharedPreferencesUtil.getString(requireActivity(), "${button.id} UpData")
                    .toString()
            button.setOnClickListener(ButtonListener())
            button.setOnTouchListener(ButtonListener())
        }

    }

    inner class ButtonListener: View.OnClickListener,View.OnTouchListener {

        override fun onClick(v: View?) {
            if (switchKeyboard.isChecked){
                DialogUtil.createDialog(requireActivity(), myViewModel, v as Button)
            }
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (!switchKeyboard.isChecked){
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> {
                        if (myViewModel.sendDownType[v?.id] == "Hex") {
                            SerialPortBuilder.setSendDataType(SerialPort.SEND_HEX)
                        } else {
                            SerialPortBuilder.setSendDataType(SerialPort.SEND_STRING)
                        }
                        SerialPortBuilder.sendData(myViewModel.sendDownData[v?.id].toString())
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        if (myViewModel.sendUpType[v?.id] == "Hex") {
                            SerialPortBuilder.setSendDataType(SerialPort.SEND_HEX)
                        } else {
                            SerialPortBuilder.setSendDataType(SerialPort.SEND_STRING)
                        }
                        SerialPortBuilder.sendData(myViewModel.sendUpData[v?.id].toString())
                        return false
                    }
                    MotionEvent.ACTION_CANCEL -> {

                        return false
                    }
                }
            }

            return false
        }
    }



}