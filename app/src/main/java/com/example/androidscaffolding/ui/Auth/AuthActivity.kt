package com.example.firebasetest.ui.Auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidscaffolding.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    // 전역으로 사용할 FirebaseAuth 생성
    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)

        // 전역 변수 설정
        auth = Firebase.auth

        // to 회원가입 창
        binding.signupOKBtn.setOnClickListener {
            createAccount(binding.EmailEt.text.toString(), binding.PasswordEt.text.toString())
        }

        // 로그인 버튼
        binding.loginBtn.setOnClickListener {
            signIn(binding.EmailEt.text.toString(),binding.PasswordEt.text.toString())
        }

        /*
        // 기존 프래그먼트 : loginBtn 버튼 클릭 -> 메인 액티비티 실행
        loginBtn.setOnClickListener {
            var startMainActivityIntent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(startMainActivityIntent)
            finish() // 액티비티 호출 후 현재 액티비티 종료
        }

        // 새 프래그먼트 : loginBtn 버튼 클릭 -> 메인 액티비티 실행
        override fun onCreate(savedInstanceState: Bundle?) {
            // 결과를 전달 받는다.
            var result = getIntent().getIntExtra("key", 0)  		// int 타입을 받을 경우
            var nickname = getIntent().getStringExtra("key")		// string 타입을 받을 경우
        }
*/


        // 인플레이트
        setContentView(binding.root)

        // 호스트
        //       val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        //      val navController = navHostFragment.navController


        //      OpeningDialogFragment().show(supportFragmentManager, "GAME_DIALOG")
    }


    // 계정 생성: 회원가입 함수 createAccount -> onCreate 에 할당
    private fun createAccount(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // 로그인
    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) {
                        task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser)

                    } else {
                        Toast.makeText(
                            baseContext, "로그인 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }


    }

    // 유저 정보 넘겨준 뒤, 메인 액티비티 호출
    fun moveMainPage(user:FirebaseUser?){
        if(user!= null){
         //   startActivity(Intent(this,Main.MainActivity::class.java))
            finish()
        }
    }
}

