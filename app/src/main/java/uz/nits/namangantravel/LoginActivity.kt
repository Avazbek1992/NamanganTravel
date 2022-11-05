package uz.nits.namangantravel

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import uz.nits.namangantravel.databinding.LoginActivityLayoutBinding
import uz.nits.namangantravel.utils.DataStorage

class LoginActivity : Activity(), View.OnClickListener {
    private lateinit var binding: LoginActivityLayoutBinding
    private lateinit var dataStorage: DataStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogProgressbar: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        dataStorage = DataStorage()
        dialogProgressbar =
            dataStorage.createDialogProgressbar(this, resources.getString(R.string.please_waiting))

        binding = LoginActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
// OnClickListener --------------------------------------------
        binding.loginBtnId.setOnClickListener(this)
        binding.registerTvId.setOnClickListener(this)
// ------------------------------------------------------------
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.loginBtnId -> {
                if (dataStorage.checkForInternet(this)) {
                    val email = binding.loginEmailEditId.text.toString()
                    val password = binding.loginPasswordEditId.text.toString()
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        dialogProgressbar.show()
                        firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    dialogProgressbar.hide()
                                    dataStorage.saveSharedPreferences(this, email, password)
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } else {
                                    dialogProgressbar.hide()
                                    Toast.makeText(
                                        this,
                                        "Login yoki parol noto'g'ri kiritdingiz!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Bo'sh joylarni to'ldiring", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Internetga ulanishda xatolik mavjud!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.registerTvId -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }
}