package io.github.dotocan.raketa.util.extensions

import android.util.Log

/**
 * Created by dotocan on 3.3.2018..
 */

fun Any.debug(any: Any = "No message provided") {
    Log.d(this.javaClass.simpleName + "`~", any.toString())
}