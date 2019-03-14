package ru.e2k.chechina.smstest

import android.text.Editable
import android.text.InputFilter
/*
объект для замены в mockito-EditText собственно текста
 */
internal  class MockEditable(public var str: String) : Editable {

    override  val length: Int = 0

    override fun get(index: Int):Char { return 'c'}

    override fun toString(): String {
        return str
    }



    override fun subSequence(i: Int, i1: Int): CharSequence {
        return str.subSequence(i, i1)
    }

    override fun replace(i: Int, i1: Int, charSequence: CharSequence, i2: Int, i3: Int): Editable {
        return this
    }

    override fun replace(i: Int, i1: Int, charSequence: CharSequence): Editable {
        return this
    }

    override fun insert(i: Int, charSequence: CharSequence, i1: Int, i2: Int): Editable {
        return this
    }

    override fun insert(i: Int, charSequence: CharSequence): Editable {
        return this
    }

    override fun delete(i: Int, i1: Int): Editable {
        return this
    }

    override fun append(charSequence: CharSequence): Editable {
        return this
    }

    override fun append(charSequence: CharSequence, i: Int, i1: Int): Editable {
        return this
    }

    override fun append(c: Char): Editable {
        return this
    }

    override fun clear() {}

    override fun clearSpans() {}

    override fun setFilters(inputFilters: Array<InputFilter>) {}

    override fun getFilters(): Array<InputFilter> {
        return emptyArray<InputFilter>()
    }

    override fun getChars(i: Int, i1: Int, chars: CharArray, i2: Int) {}

    override fun setSpan(o: Any, i: Int, i1: Int, i2: Int) {}

    override fun removeSpan(o: Any) {}

    override fun <T> getSpans(i: Int, i1: Int, aClass: Class<T>): Array<T>? {
        return null
    }

    override fun getSpanStart(o: Any): Int {
        return 0
    }

    override fun getSpanEnd(o: Any): Int {
        return 0
    }

    override fun getSpanFlags(o: Any): Int {
        return 0
    }

    override fun nextSpanTransition(i: Int, i1: Int, aClass: Class<*>): Int {
        return 0
    }
}