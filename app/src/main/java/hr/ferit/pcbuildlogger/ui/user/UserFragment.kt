package hr.ferit.pcbuildlogger.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        userBinding.btnUserLogout.setOnClickListener { logout() }
        userBinding.tvUserEmail.text = requireActivity().intent.getStringExtra("email")
        userBinding.tvUserId.text = requireActivity().intent.getStringExtra("user_id")

        return userBinding.root
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        requireActivity().startActivity(Intent(activity, UserManagementActivity::class.java))
        requireActivity().finish()
    }

    companion object {
        const val TAG = "User"
        fun create(): UserFragment {
            return UserFragment()
        }
    }
}