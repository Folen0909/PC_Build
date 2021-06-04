package hr.ferit.pcbuildlogger.ui.pcbuild

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import hr.ferit.pcbuildlogger.data.model.PcBuild
import hr.ferit.pcbuildlogger.databinding.FragmentPcBuildBinding
import hr.ferit.pcbuildlogger.utiliites.DialogListener
import hr.ferit.pcbuildlogger.utiliites.OnPcBuildClickListener

class PcBuildFragment: Fragment(), OnPcBuildClickListener {

    private lateinit var pcBuildBinding: FragmentPcBuildBinding
    private lateinit var onPcBuildClickListener: OnPcBuildClickListener
    private lateinit var buildsReference: DatabaseReference
    private lateinit var userId: String

    private val database = Firebase.database("https://pc-build-logger-default-rtdb.europe-west1.firebasedatabase.app/")
    private var pcBuilds: MutableList<PcBuild> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pcBuildBinding = FragmentPcBuildBinding.inflate(inflater, container, false)
        pcBuildBinding.btnAddBuild.setOnClickListener { createNewPcBuild() }
        setUpRecyclerView()
        setUpDataBaseConnection()
        return pcBuildBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onPcBuildClickListener = this
    }

    override fun onResume() {
        super.onResume()
        (pcBuildBinding.rvBuilds.adapter as PcBuildAdapter).refreshData(pcBuilds)
    }

    private fun createNewPcBuild() {
        PcBuildNewDialog().show(parentFragmentManager, "New build")
    }

    override fun onPcBuildDelete(pcBuild: PcBuild) {
        buildsReference.child(pcBuild.key.toString()).removeValue()
    }

    override fun onPcBuildDetails(pcBuild: PcBuild) {
        Toast.makeText(context, pcBuild.name, Toast.LENGTH_SHORT).show()
    }

    private fun setUpDataBaseConnection() {
        userId = requireActivity().intent.getStringExtra("user_id").toString()
        buildsReference = database.getReference("$userId/builds")
        buildsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pcBuilds.clear()
                for (snap in snapshot.children) {
                    val build = snap.getValue<PcBuild>()
                    if (build != null) {
                        build.key = snap.key
                        pcBuilds.add(build)
                    }
                }
                pcBuildBinding.rvBuilds.adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun setUpRecyclerView() {
        pcBuildBinding.rvBuilds.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        pcBuildBinding.rvBuilds.adapter = PcBuildAdapter(pcBuilds, onPcBuildClickListener)
    }

    companion object {
        const val TAG = "PC Build"
        fun create(): PcBuildFragment {
            return PcBuildFragment()
        }
    }

    fun onDialogPositiveClick(data: String) {
        val pcBuild = PcBuild(data)
        val uploadKey = buildsReference.push().key
        if (uploadKey != null) {
            buildsReference.child(uploadKey).setValue(pcBuild)
        }
    }
}