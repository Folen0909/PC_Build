package hr.ferit.pcbuildlogger.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hr.ferit.pcbuildlogger.R
import hr.ferit.pcbuildlogger.databinding.ActivityUserManagementBinding
import hr.ferit.pcbuildlogger.ui.tabs.TabsActivity

class UserManagementActivity : AppCompatActivity() {

    lateinit var userManagementBinding: ActivityUserManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userManagementBinding = ActivityUserManagementBinding.inflate(layoutInflater)
        userManagementBinding.btnUserAction.setOnClickListener { loginUser() }
        userManagementBinding.tvRegister.setOnClickListener { switchType() }
        setContentView(userManagementBinding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startNewIntent(user)
        }
    }


    private fun switchType() {
        if (userManagementBinding.tvTitle.text == getString(R.string.user_login)) {
            userManagementBinding.tvTitle.text = getString(R.string.user_register)
            userManagementBinding.tvNewUser.text = getString(R.string.existing_user)
            userManagementBinding.tvRegister.text = getString(R.string.user_login)
            userManagementBinding.btnUserAction.text = getString(R.string.user_register)
            userManagementBinding.btnUserAction.setOnClickListener { registerUser() }
        } else {
            userManagementBinding.tvTitle.text = getString(R.string.user_login)
            userManagementBinding.tvNewUser.text = getString(R.string.new_user)
            userManagementBinding.tvRegister.text = getString(R.string.user_register)
            userManagementBinding.btnUserAction.text = getString(R.string.user_login)
            userManagementBinding.btnUserAction.setOnClickListener { loginUser() }
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
                val email: String = userManagementBinding.etUserEmail.text.toString().trim { it <= ' ' }
                val password: String = userManagementBinding.etUserPassword.text.toString().trim { it <= ' ' }
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                postToast("Registered successfully!")
                                startNewIntent(firebaseUser)
                            } else {
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
                val email: String = userManagementBinding.etUserEmail.text.toString().trim { it <= ' ' }
                val password: String = userManagementBinding.etUserPassword.text.toString().trim { it <= ' ' }
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                postToast("Logged in successfully!")
                                startNewIntent(firebaseUser)
                            } else {
                                postToast(task.exception!!.message.toString())
                            }
                        }
            }
        }
    }

    private fun startNewIntent(firebaseUser: FirebaseUser) {
        val intent = Intent(this@UserManagementActivity, TabsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", firebaseUser.uid)
        intent.putExtra("email", firebaseUser.email)
        startActivity(intent)
        finish()
    }

    private fun checkEmail(): Boolean {
        return TextUtils.isEmpty(userManagementBinding.etUserEmail.toString().trim { it <= ' ' })
    }

    private fun checkPassword(): Boolean {
        return TextUtils.isEmpty(userManagementBinding.etUserPassword.toString().trim { it <= ' ' })
    }

    private fun postToast(message: String) {
        Toast.makeText(this@UserManagementActivity, message, Toast.LENGTH_SHORT).show()
    }
}