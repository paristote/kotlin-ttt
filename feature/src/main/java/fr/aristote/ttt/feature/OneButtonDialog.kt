package fr.aristote.ttt.feature

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.DialogInterface.OnDismissListener
import android.os.Bundle

class OneButtonDialog : DialogFragment(), OnClickListener, OnDismissListener {

    companion object {
        const val MESSAGE = "message"
        const val BUTTON_LABEL = "button_label"
    }

    private var onFinishedBlock: () -> Unit = {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments.getString(MESSAGE)
        val buttonLabel = arguments.getString(BUTTON_LABEL)
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
                .setPositiveButton(buttonLabel, this)
        return builder.create()
    }

    fun onFinish(block: () -> Unit) {
        onFinishedBlock = block
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        onFinishedBlock.invoke()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onFinishedBlock.invoke()
    }
}
