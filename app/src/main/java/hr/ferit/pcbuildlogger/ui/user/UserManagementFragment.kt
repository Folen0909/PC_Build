package hr.ferit.pcbuildlogger.ui.user

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hr.ferit.pcbuildlogger.R

import hr.ferit.pcbuildlogger.databinding.FragmentUserManagementBinding
import hr.ferit.pcbuildlogger.ui.tabs.TabAdapter
import hr.ferit.pcbuildlogger.ui.tabs.TabsActivity

class UserManagementFragment: Fragment() {

    lateinit var userBinding: FragmentUserManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = FragmentUserManagementBinding.inflate(inflater, container, false)

        userBinding.btnUserAction.setOnClickListener { loginUser() }
        userBinding.tvRegister.setOnClickListener{ switchType() }

        return userBinding.root
    }


    private fun switchType() {
        if (userBinding.tvTitle.text == getString(R.string.user_login)) {
            userBinding.tvTitle.text = getString(R.string.user_register)
            userBinding.tvNewUser.text = getString(R.string.existing_user)
            userBinding.tvRegister.text = getString(R.string.user_login)
            userBinding.btnUserAction.text = getString(R.string.user_register)
            userBinding.btnUserAction.setOnClickListener { registerUser() }
        } else {
            userBinding.tvTitle.text = getString(R.string.user_login)
            userBinding.tvNewUser.text = getString(R.string.new_user)
            userBinding.tvRegister.text = getString(R.string.user_register)
            userBinding.btnUserAction.text = getString(R.string.user_login)
            userBinding.btnUserAction.setOnClickListener { loginUser() }
        }
    }

    private fun registerUser() {
        when {
            checkEmail() -> {
                postToast("Enter email!")
            }
            checkPassword() -> {
                postToast("Enter password!")
            }
            else -> {
                val email: String = userBinding.etUserEmail.text.toString().trim { it <= ' '}
                val password: String = userBinding.etUserPassword.text.toString().trim { it <= ' '}
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                postToast("Registered successfully!")
                            }
                            else {
                                postToast(task.exception!!.message.toString())
                            }
                        }
            }
        }
    }

    private fun loginUser() {
        when {
            checkEmail() -> {
                postToast("Enter email!")
            }
            checkPassword() -> {
                postToast("Enter password!")
            }
            else -> {
                val email: String = userBinding.etUserEmail.text.toString().trim { it <= ' '}
                val password: String = userBinding.etUserPassword.text.toString().trim { it <= ' '}
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                postToast("Logged in successfully!")
                                //TODO do something with user
                            }
                            else {
                                postToast(task.exception!!.message.toString())
                            }
                        }
            }
        }
    }

    private fun checkEmail(): Boolean {
         return TextUtils.isEmpty(userBinding.etUserEmail.toString().trim { it <= ' '})
    }

    private fun checkPassword(): Boolean {
        return TextUtils.isEmpty(userBinding.etUserPassword.toString().trim() { it <= ' ' })
    }

    private fun postToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "User Management"
        fun create(): UserManagementFragment {
            return UserManagementFragment()
        }
    }
}
