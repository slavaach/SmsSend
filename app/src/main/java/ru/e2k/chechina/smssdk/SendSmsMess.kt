package ru.e2k.chechina.smssdk

import android.app.PendingIntent
import android.os.Environment
import android.telephony.SmsManager
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import ru.e2k.chechina.smssdk.SendSmsMess.Send.txtSms
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/*
объект для работы с интернетом и файловой системой. Получает-отправляет смс-ки, записывает лог
Все установки в нем  - не выносила в отдельный объект
 */


open class SendSmsMess() {

    companion object  Send{
        val URL = "http://avoska.1gb.ru/soap/webserviceSEI"
        val NAMESPACE = "http://servis.web.chechina.e2k.com/"
        val METHOD_NAME_GET = "getMessages"
        val METHOD_NAME_SEND = "sayHelloTo"
        val SOAP_ACTION = ""
        var txtSms = ""
        val DELTA_SECOND_INTERNET = 10 //кол-во секунд задержки, что б не опрашивало без перерыва
        val DELTA_MINUT_MEMO = 3 //кол-во минут в файле лога
        //val manager = SmsManager.getDefault()

        //считываю смс для отправки с сервиса
        @Throws
        @JvmStatic
        fun getSms(methodName :String = METHOD_NAME_GET): MutableList<SmsMess> {
            var smsMesseges: MutableList<SmsMess> = ArrayList()

            // TimeUnit.SECONDS.sleep(5)

            val request = SoapObject(SendSmsMess.NAMESPACE, methodName)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.setOutputSoapObject(request)
            val androidHttpTransport = HttpTransportSE(SendSmsMess.URL)
            androidHttpTransport.call(SendSmsMess.SOAP_ACTION, envelope)
            val result = envelope.bodyIn as SoapObject //прочла что выдал сервис

            if (result != null) {
                for (n in 0..(result.propertyCount - 1)) { //формирую список сообщений
                    var stNum = (result.getProperty(n) as SoapObject).getProperty(1).toString()
                    //stNum = stNum.replace("+" , "") //сделала с +7, если делать через 8, то + не надо
                    stNum = stNum.replace("(", "")
                    stNum = stNum.replace(")", "")
                    stNum = stNum.replace("-", "")
                    var m = SmsMess((result.getProperty(n) as SoapObject).getProperty(0).toString().toInt(), stNum, (result.getProperty(n) as SoapObject).getProperty(2).toString());
                    smsMesseges.add(n, m);
                }
                // if (result.propertyCount ==0) throw ResultNullException("No Result") //сообщаю, что смсок нет
            } else {
                txtSms = "No Result"
                // throw ResultNullException("No Result") // буду печатать просто смс
            }
            return smsMesseges;
        }

        //отсылаю смс и удалю их на сервисе. Удаление идет после отсылки, что б при ошибке отослать еще раз.
        @JvmStatic
        fun sendSms(s: SmsMess , manager:SmsManager? = SmsManager.getDefault()): String {
            //val manager = SmsManager.getDefault()
            manager!!.sendTextMessage(s.name, null, s.txtsms, null, null)

            val request = SoapObject(SendSmsMess.NAMESPACE, SendSmsMess.METHOD_NAME_SEND)
            request.addProperty("id", s.id)
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.setOutputSoapObject(request)
            val androidHttpTransport = HttpTransportSE(SendSmsMess.URL)
            androidHttpTransport.call(SendSmsMess.SOAP_ACTION, envelope)
            return "Ура"
        }
        //сохраняю лог
        @Throws(Exception::class)
        @JvmStatic
        fun printHistory(doc: String , directory: String = Environment.getExternalStorageDirectory().toString()) {
            var fos: FileOutputStream? = null
            val sdf = SimpleDateFormat("dd_HH_mm_ss")
            val file_name = "smsSend" + sdf.format(Date()) + ".txt"

            val dir = File(directory + "/smsTest")
            if (!dir.exists()) dir.mkdirs()

            fos = FileOutputStream(File(dir, file_name))
            fos!!.write(doc.toByteArray())
            fos!!.close()

        }

    }
}