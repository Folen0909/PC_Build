package hr.ferit.pcbuildlogger.ui.pcbuild

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.ferit.pcbuildlogger.databinding.FragmentPcBuildBinding
import hr.ferit.pcbuildlogger.ui.user.UserFragment

class PCBuildFragment: Fragment() {

    lateinit var pcBuildBinding: FragmentPcBuildBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pcBuildBinding = FragmentPcBuildBinding.inflate(inflater, container, false)
        return pcBuildBinding.root
    }


    companion object {
        const val TAG = "PC Build"
        fun create(): PCBuildFragment {
            return PCBuildFragment()
        }
    }
}