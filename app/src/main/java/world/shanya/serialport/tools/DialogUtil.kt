package world.shanya.serialport.tools

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.widget.Button
import kotlinx.android.synthetic.main.button_info_dialog.view.*
import world.shanya.serialport.R

object DialogUtil {
    fun createDialog(context: Context, button: Button) {
        val layoutInflater = LayoutInflater.from(context)
        val dialogView = layoutInflater.inflate(R.layout.button_info_dialog, null)
        dialogView.editTextTextButtonName.text = Editable.Factory.getInstance()
            .newEditable(SharedPreferencesUtil.getString(context,"${button.id} Name"))
        dialogView.editTextTextDownData.text = Editable.Factory.getInstance()
            .newEditable(SharedPreferencesUtil.getString(context,"${button.id} DownData"))
        dialogView.editTextTextUpData.text = Editable.Factory.getInstance()
            .newEditable(SharedPreferencesUtil.getString(context,"${button.id} UpData"))
        val downDataType = SharedPreferencesUtil.getString(context, "${button.id} DownDataType")
        if (downDataType == "") {
            dialogView.radioGroupDownSend.check(R.id.radioButtonDownStr)
        } else {
            if (downDataType == "Hex") {
                dialogView.radioGroupDownSend.check(R.id.radioButtonDownHex)
            } else {
                dialogView.radioGroupDownSend.check(R.id.radioButtonDownStr)
            }
        }
        val upDataType = SharedPreferencesUtil.getString(context, "${button.id} UpDataType")
        if (upDataType == "") {
            dialogView.radioGroupUpSend.check(R.id.radioButtonUpStr)
        } else {
            if (upDataType == "Hex") {
                dialogView.radioGroupUpSend.check(R.id.radioButtonUphex)
            } else {
                dialogView.radioGroupUpSend.check(R.id.radioButtonUpStr)
            }
        }

        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Yes") { _, _ ->
                button.text = dialogView.editTextTextButtonName.text
                SharedPreferencesUtil.putString(
                    context,
                    "${button.id} Name",
                    dialogView.editTextTextButtonName.text.toString())
                SharedPreferencesUtil.putString(
                    context,
                    "${button.id} DownData",
                    dialogView.editTextTextDownData.text.toString())
                SharedPreferencesUtil.putString(
                    context,
                    "${button.id} UpData",
                    dialogView.editTextTextUpData.text.toString())
                if (dialogView.radioGroupDownSend.checkedRadioButtonId == R.id.radioButtonDownHex) {
                    SharedPreferencesUtil.putString(
                        context,
                        "${button.id} DownDataType",
                        "Hex")
                } else {
                    SharedPreferencesUtil.putString(
                        context,
                        "${button.id} DownDataType",
                        "Str")
                }
                if (dialogView.radioGroupUpSend.checkedRadioButtonId == R.id.radioButtonUphex) {
                    SharedPreferencesUtil.putString(
                        context,
                        "${button.id} UpDataType",
                        "Hex")
                } else {
                    SharedPreferencesUtil.putString(
                        context,
                        "${button.id} UpDataType",
                        "Str")
                }
            }
            .setNegativeButton("No") { _, _ ->

            }
        builder.show()
    }
}