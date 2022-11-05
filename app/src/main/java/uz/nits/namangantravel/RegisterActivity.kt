package uz.nits.namangantravel

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import uz.nits.namangantravel.databinding.RegisterActivityLayoutBinding
import uz.nits.namangantravel.utils.DataStorage

class RegisterActivity : Activity(), View.OnClickListener {
    private lateinit var dataStorage: DataStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: RegisterActivityLayoutBinding
    private lateinit var dialogProgressbar: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        dataStorage = DataStorage()
        dialogProgressbar =
            dataStorage.createDialogProgressbar(this, resources.getString(R.string.please_waiting))

// OnClickListener --------------------------------------------
        binding.registerBtnId.setOnClickListener(this)
        binding.loginTvId.setOnClickListener(this)
// ------------------------------------------------------------
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.registerBtnId -> {
                if (dataStorage.checkForInternet(this)) {
                    val email = binding.registerEmailEditId.text.toString()
                    val password = binding.registerPasswordEditId.text.toString()
                    val confirmPassword = binding.registerConfirmPasswordEditId.text.toString()
                    if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                        if (password.length > 5) {
                            if (password == confirmPassword) {
                                dialogProgressbar.show()
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            dialogProgressbar.hide()
                                            Toast.makeText(
                                                this,
                                                "Siz ro'yhatdan muvaffaqiyatli o'tdingiz",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            finish()
                                        } else {
                                            dialogProgressbar.hide()
                                            Toast.makeText(
                                                this,
                                                "Email formatini to'g'ri kiriting!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Parolni tasdiqlash bir xil emas",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Parol kamida 6ta belgidan iborat bo'lishi kerak",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "Bo'sh joylarni to'ldiring", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Internetga ulanishda xatolik mavjud!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.loginTvId -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}