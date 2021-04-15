package world.shanya.serialport.message

import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.util.*

object MessageUtil {

    private val receivedUser = User("1","Receiver",null,true)
    private val sendUser = User("0","Sender",null,true)
    val messagesListAdapter = MessagesListAdapter<Message>("0",null)

    fun receivedMessage(text: String) {
        messagesListAdapter.addToStart(Message(UUID.randomUUID().toString(), receivedUser, text), true)
    }

    fun sendMessage(text: String) {
        messagesListAdapter.addToStart(Message(UUID.randomUUID().toString(), sendUser, text), true)
    }
}