package ru.e2k.chechina.smssdk

import android.widget.EditText
import rx.Scheduler
import ru.e2k.chechina.smssdk.SendSmsMess.Send.DELTA_MINUT_MEMO
import ru.e2k.chechina.smssdk.SendSmsMess.Send.DELTA_SECOND_INTERNET
import ru.e2k.chechina.smssdk.SendSmsMess.Send.getSms
import ru.e2k.chechina.smssdk.SendSmsMess.Send.printHistory
import ru.e2k.chechina.smssdk.SendSmsMess.Send.sendSms
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/*
Класс сервиса.
 */
object WorkSmsOb {
    var bCansel = true
    var iMemo = Date().time


    //функция для переодического запуска опроса (с переносом его в другой поток)
    fun WorkSms(editTextlog: EditText , mainThread: Scheduler = AndroidSchedulers.mainThread() ,ioScheduler: Scheduler = Schedulers.io() ) {
        val dr = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Date());

        editTextlog.setText("сервис запущен $dr")
        bCansel = true
        var iSec = Date().time //для запоминания времени предыдущего вызава интернет-сервиса
        Observable.just("1")
                .observeOn(mainThread)
                .doOnNext({ i ->
                    //пишем на экран что делаем

                    val dt = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(iSec);
                    editTextlog.setText(" опрос $dt   \n" + editTextlog.text.toString())
                })
                .observeOn(ioScheduler)
                .flatMap {
                    var iDelta = iSec + DELTA_SECOND_INTERNET * 1000 - Date().time
                    if (iDelta > 0) TimeUnit.MILLISECONDS.sleep(iDelta)//жду, что б не опрашивать слишком часто, при условии, что с предыдущего опроса прошло меньше 10 секунд
                    iSec = Date().time
                    Observable.from(getSms())
                }
                .doOnNext({ sms -> sendSms(sms) }) //отсылаем сообщения
                .observeOn(mainThread)
                .doOnError({ throwable ->
                    val dt = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Date());
                    editTextlog.setText("ошибка $dt " + throwable.message + "\n " + editTextlog.text.toString()) //пишем что произошло
                }).retryWhen({ retryHandler ->
                    retryHandler.flatMap { nothing ->
                        if (bCansel) {
                            Log(editTextlog)
                            Observable.just(null)
                        } else {
                            Stop(editTextlog)
                            Observable.empty()//обрываем если нажали кнопку стоп
                        }
                    }
                })
                .doOnCompleted {}.repeatWhen({ retryHandler ->
                    retryHandler.flatMap { nothing ->
                        if (bCansel) {
                            Log(editTextlog)
                            Observable.just(null)
                        } else {
                            Stop(editTextlog)
                            Observable.empty()
                        } //обрываем если нажали кнопку стоп
                    }
                }) //повторяем
                .subscribe({ s ->
                    //записываем отосланное сообщение в лог
                    editTextlog.setText(s.toString() + "\n" + editTextlog.text.toString())
                },
                        { editTextlog.setText(it.message + "\n" + editTextlog.text.toString()) }) //фиксируем в лог ошибки записи или остановки


    }
    //остановка по кнопке
    fun Stop(editTextlog: EditText) {
        val dr = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Date());
        editTextlog.setText("Сервис остановлен $dr \n " + editTextlog.text.toString())

        printHistory(editTextlog.text.toString())
        bCansel = true
    }
    //лог по времени
    @Throws(Exception::class)
    fun Log(editTextlog: EditText) {
        var iDelta = iMemo + DELTA_MINUT_MEMO * 1000 * 60 - Date().time

        if (iDelta < 0) {
            var dr = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Date());
            editTextlog.setText("Запомнила в файл лога $dr \n " + editTextlog.text.toString())
            printHistory(editTextlog.text.toString())
            dr = SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Date());
            editTextlog.setText("Новый лог $dr ")
            iMemo = Date().time
        }

    }
}