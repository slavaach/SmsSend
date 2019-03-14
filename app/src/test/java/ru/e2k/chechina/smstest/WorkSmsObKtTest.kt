package ru.e2k.chechina.smstest

import android.widget.EditText
import org.junit.Assert
import org.junit.Test
import rx.Scheduler
import org.mockito.Mockito.*
import ru.e2k.chechina.smssdk.SmsMess
import java.util.ArrayList
import ru.e2k.chechina.smssdk.WorkSmsOb
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
/*
тест для WorkSmsObKt
запускае основную процедуру. Остальные внутри.
 */


@RunWith(MockitoJUnitRunner::class)
        class WorkSmsObKtTest {

    @InjectMocks
    val Wr =  WorkSmsOb

    @Test
    fun workSms() {
        var smsMesseges : MutableList<SmsMess> = ArrayList()
        var m = SmsMess(0 , "+79163435607" , "Тест пробы");
        var sh : Scheduler = rx.schedulers.Schedulers.immediate()
        var ioScheduler: Scheduler = rx.schedulers.Schedulers.newThread()

        smsMesseges.add( m);
        var text = mock(EditText::class.java)
        Mockito.doThrow(SendTextMessageNormal()).`when`(text).setText("Проба теста")
         `when`(text.text).thenReturn(MockEditable("Проба теста"))


        WorkSmsOb.bCansel = false
        WorkSmsOb.WorkSms(text  , sh ,ioScheduler)
        TimeUnit.SECONDS .sleep(50)
        WorkSmsOb.bCansel = false

        Assert.assertEquals( text.text.toString() , "Проба теста")
        //verify(text).setText("сервис запущен 12.03.19 15:15:33") время не предсказать...
    }


}

