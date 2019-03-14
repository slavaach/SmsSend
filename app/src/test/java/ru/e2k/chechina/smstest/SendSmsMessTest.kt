package ru.e2k.chechina.smstest

import android.os.Parcel
import android.os.Parcelable
import android.telephony.SmsManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito

import java.util.ArrayList
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.e2k.chechina.smssdk.SendSmsMess
import ru.e2k.chechina.smssdk.SmsMess

/*
тест для SendSms
 */


@RunWith(MockitoJUnitRunner::class)
class SendSmsMessTest() : Parcelable {



    @InjectMocks
     val Wr =   SendSmsMess




    @Test
    fun getSmsTest() {
        var smsMesseges : MutableList<SmsMess> = ArrayList()
        var m = SmsMess(id = 34,  name  = "+79163435607", txtsms= "mockit проба");
        smsMesseges.add(0, m);
        Assert.assertEquals(SendSmsMess.getSms("getMessages"), smsMesseges)

    }

    @Test(expected = SendTextMessageNormal::class)
    fun sendSmsTest() {
        // Mockito.`when`(manager!!.sendTextMessage("+79163435607" ,null , "Тест пробы" , null , null )).then({})
        var manager = mock(SmsManager::class.java)
        Mockito.doThrow(SendTextMessageNormal()).`when`(manager).sendTextMessage("+79163435607" ,null , "Тест пробы" , null , null )
        val sms = SmsMess(0, "+79163435607", "Тест пробы")
        Wr.sendSms(sms , manager)

        verify(manager).sendTextMessage("+79163435607" ,null , "Тест пробы" , null , null )

    }




    @Test
    fun printHistoryTest() {


        Wr.printHistory("Тест печати" , "c:/")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }


}

 class SendTextMessageNormal : RuntimeException()