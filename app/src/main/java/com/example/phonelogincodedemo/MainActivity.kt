package com.example.phonelogincodedemo

import android.content.Intent
import android.content.SyncStatusObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.Toast
import cn.bmob.v3.Bmob
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Bmob.initialize(this, "2d350ee156d40fbc48491de53979a422");
        //添加字数监听事件
        mEdit.addTextChangedListener(object : TextWatcher {
            private var deletePause = true
            override fun afterTextChanged(p0: Editable?) {
                //判断是否可以点击
                mLogin.isEnabled = (p0?.toString()?.length == 13)
                //判断当为删除模式下时使自动加空格代码无效
                if (!deletePause) return
                //设置号码格式123 4567 8978
                p0?.toString()?.length.also {
                    if (it == 3 || it == 8) {
                        mEdit.append(" ")
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                deletePause = p3 == 1
            }
        })

        //添加按钮跳转验证事件
        mLogin.setOnClickListener {
            //跳转
            Intent().apply {
                //添加跳转视图
                setClass(this@MainActivity,SmsLogin::class.java)
                //传输数据
                putExtra("Phone",getPhoneNumber(mEdit.editableText))
                //跳转
                startActivity(this)
            }
        }
    }

    //去除空格获取手机号
    private fun getPhoneNumber(editable: Editable): String{
        SpannableStringBuilder(editable.toString()).apply {
            editable.delete(3,4)
            editable.delete(7,8)
            return editable.toString()
        }
    }
}