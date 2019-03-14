package ru.e2k.chechina.smstest

import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import ru.e2k.chechina.smssdk.SmsMess


/*
тест для  SmsMess
 */


class SmsMessTest {
    var ms: SmsMess? = null

    @Before
    @Throws(Exception::class)
    fun setUp(){
        ms = SmsMess(1 ,"13245678" , "сообщение")
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        ms = SmsMess(1 ,"13245678" , "сообщение")
    }

    @Test
    @Throws(Exception::class)
    fun getName() {
        Assert.assertEquals(ms!!.name , "13245678")
    }

    @Test
    fun setName() {
        ms!!.name = "2324578"
        Assert.assertEquals(ms!!.name , "2324578")
    }

    @Test
    fun getTxtsms() {
        Assert.assertEquals(ms!!.txtsms , "сообщение")
    }

    @Test
    fun setTxtsms() {
        ms!!.txtsms = "сообщение2"
        Assert.assertEquals(ms!!.txtsms , "сообщение2")
    }

    @Test
    fun getId() {
        Assert.assertEquals(ms!!.id , 1)
    }

    @Test
    fun setId() {
        ms!!.id = 2
        Assert.assertEquals(ms!!.id , 2)
    }

    @Test
    fun test_toString() {
        Assert.assertEquals(ms!!.toString() , "СМС { id = 1, name='13245678' , txtsms='сообщение' }")
    }

    @Test
    fun equals() {
        var ms2 =  SmsMess(1 ,"13245678" , "сообщение")
        Assert.assertEquals( ms!!.equals(ms2) , true)
    }
    @Test
    fun non_equals() {
        var ms2 =  SmsMess(3 ,"13245678" , "сообщение3")
        Assert.assertEquals( ms!!.equals(ms2) , false)
    }
}