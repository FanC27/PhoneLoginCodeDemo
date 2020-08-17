package com.example.phonelogincodedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sms_login.*

class SmsLogin : AppCompatActivity() {
    private val numberArray: Array<TextView> by lazy {
        arrayOf(sl1,sl2,sl3,sl4,sl5,sl6)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_login)

        //将号码传给标题
        intent.getStringExtra("Phone").also {
            smsPhone.text = it

        }

        SmsEdit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //将验证码写入框中
                for ((i,item) in p0?.withIndex()!!){
                    numberArray[i].text = item.toString()
                }
                //如果小于六位，显示后面的空白
                for (i in p0?.length..5){
                    numberArray[i].text = ""
                }
                //到六位跳转
                if (p0?.length == 6){
                    BmobSMSUtils.verifySmsCode(smsPhone.text.toString(),p0.toString()){
                        if (it == BmobSMSUtils.SUCCESS){
                            //成功
                            startActivity(Intent(this@SmsLogin,HomeActivity::class.java))
                        }else{
                            //失败
                            Toast.makeText(this@SmsLogin,"验证码错误，请重试",Toast.LENGTH_SHORT).show()
                            SmsEdit.text.clear()
                        }
                    }
                }
            }
        })

    }

    //发送验证码
    override fun onResume() {
        super.onResume()
        //发送验证码
        BmobSMSUtils.requestSMSCode(smsPhone.text.toString()){
            if (it == BmobSMSUtils.SUCCESS){
                //发送成功
                Toast.makeText(this,"验证码发送成功", Toast.LENGTH_SHORT).show()
            }else{
                //发送失败
                Toast.makeText(this,"验证码发送失败，请重试", Toast.LENGTH_SHORT).show()
            }
        }
    }

}