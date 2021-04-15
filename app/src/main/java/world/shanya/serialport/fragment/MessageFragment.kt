package world.shanya.serialport.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.message_type_dialog.view.*
import world.shanya.serialport.MyViewModel
import world.shanya.serialport.R
import world.shanya.serialport.SerialPort
import world.shanya.serialport.SerialPortBuilder
import world.shanya.serialport.message.MessageUtil


class MessageFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val myViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]

        messagesList.setAdapter(MessageUtil.messagesListAdapter)

        val serialPort = SerialPortBuilder.build(requireActivity())

        messageInputView.setInputListener {
            SerialPortBuilder.sendData(it.toString())
//            serialPort.sendData(it.toString())
            MessageUtil.sendMessage(it.toString())
            return@setInputListener true
        }

        messageInputView.setAttachmentsListener {
            val dialogView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.message_type_dialog, null)
            AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("选择数据格式")
                .setPositiveButton("保存"){_,_ ->
                    if (dialogView.radioGroupReceived.checkedRadioButtonId == R.id.radioButtonRecHex) {
                        SerialPortBuilder.setReadDataType(SerialPort.READ_HEX)
                    } else {
                        SerialPortBuilder.setReadDataType(SerialPort.READ_STRING)
                    }
                    if (dialogView.radioGroupSend.checkedRadioButtonId == R.id.radioButtonSendHex) {
                        SerialPortBuilder.setSendDataType(SerialPort.SEND_HEX)
                    } else {
                        SerialPortBuilder.setSendDataType(SerialPort.SEND_STRING)
                    }
                }
                .setNegativeButton("取消"){_,_ ->

                }
                .create()
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }


}