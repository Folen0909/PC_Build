package hr.ferit.pcbuildlogger.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.ferit.pcbuildlogger.R
import hr.ferit.pcbuildlogger.databinding.FragmentUserBinding

class UserFragment : Fragment() {


    lateinit var userBinding: FragmentUserBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userBinding = FragmentUserBinding.inflate(inflater, container, false)

        return userBinding.root
    }


    companion object {
        const val TAG = "User"
        fun create(): UserFragment {
            return UserFragment()
        }
    }
}