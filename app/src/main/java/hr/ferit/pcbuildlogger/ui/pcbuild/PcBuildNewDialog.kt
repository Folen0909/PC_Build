package hr.ferit.pcbuildlogger.ui.pcbuild

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import hr.ferit.pcbuildlogger.R
import hr.ferit.pcbuildlogger.data.model.PcBuild
import hr.ferit.pcbuildlogger.utiliites.DialogListener
import java.lang.ClassCastException

class PcBuildNewDialog : DialogFragment() {

    private lateinit var listener: DialogListener
    lateinit var etBuildName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_new_build, null)
        if (view != null) {
            etBuildName = view.findViewById(R.id.et_new_build_name)
        }
        builder.setView(view)
            .setCancelable(true)
            .setTitle("New build")
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }.setPositiveButton("Add") { _, _ ->
                if (etBuildName.text.toString().trim { it <= ' ' } != "") {
                    run {
                        listener.onDialogPositiveClick(etBuildName.text.toString())
                    }
                } else {
                    Toast.makeText(activity, "Error! Build name needed!", Toast.LENGTH_SHORT).show()
                }
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() + "must implement DialogListener."))
        }
    }
}