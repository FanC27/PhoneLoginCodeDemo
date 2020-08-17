package com.example.phonelogincodedemo

import android.widget.Toast
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener

object BmobSMSUtils {
     const val SUCCESS = 0
     const val FAULT = 1
    fun requestSMSCode(phone: String,callback:(Int) -> Unit){
        BmobSMS.requestSMSCode(phone,"",object: QueryListener<Int>() {
            override fun done(p0: Int?, p1: BmobException?) {
                if (p1 == null){
                    //发送成功
                    callback(SUCCESS)
                }else{
                    //发送失败
                    callback(FAULT)
                }
            }
        })
    }

    fun verifySmsCode(phone:String,code:String,callback: (Int) -> Unit){
        BmobSMS.verifySmsCode(phone,code,object : UpdateListener() {
            override fun done(p0: BmobException?) {
                if (p0 == null){
                    //成功
                    callback(SUCCESS)
                }else{
                    //失败
                    callback(FAULT)
                }
            }
        })
    }

}