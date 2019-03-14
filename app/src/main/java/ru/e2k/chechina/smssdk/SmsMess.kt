package ru.e2k.chechina.smssdk

//класс для хранения смс-сообщений
class SmsMess {
    var name: String? = null //телефон
    var txtsms: String? = null //сообщение
    var id:Int= 0;


    constructor(id:Int ,name: String, txtsms: String) {
        this.name = name
        this.txtsms = txtsms
        this.id = id
    }

    override fun toString(): String {
        return "СМС { id = $id, name='$name' , txtsms='$txtsms' }"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val message = o as SmsMess?

        if (if (name != null) name != message!!.name else message!!.name != null) return false
        return if (txtsms != null) txtsms == message.txtsms else message.txtsms == null

    }


}
