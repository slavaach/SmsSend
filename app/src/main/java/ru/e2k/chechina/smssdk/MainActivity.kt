package ru.e2k.chechina.smssdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import ru.e2k.chechina.smssdk.WorkSmsOb.WorkSms
import ru.e2k.chechina.smssdk.WorkSmsOb.bCansel
/*
рожа для отражения запуска и остановки сервиса
В текстовое поле выводится лог
*/


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (findViewById(R.id.vsvs) as Button).setOnClickListener(this::vsvs) //запуск
        (findViewById(R.id.stop) as Button).setOnClickListener({bCansel = false}) //остановка

    }




    fun vsvs(v:View){
       WorkSms( findViewById(R.id.log) as EditText )
    }
}
