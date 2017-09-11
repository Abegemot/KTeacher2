package com.begemot.klib

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn


/**
 * Created by Dad on 02-Sep-17.
 */



class KHelp(s:String) {
    var sClassName: String = s
    val LOG = AnkoLogger("MYPOS")
    private fun iwarn(s: String) {
        LOG.warn("$s")
    }

    companion object {
        //fun create(s:String): KHelp = KHelp(s:String)
        //var sClass:String="cerillo"
//        val LOG = AnkoLogger("MYPOS")
        //fun setClassName(s:String) = { sClass=s }
//        private fun iwarn(s:String )={LOG.warn("$s")}
        //fun lamaraqueetvaparir(){ LOG.warn("COLLONS") }
        @JvmStatic public fun main(args: Array<String>) {
            println("Hello, world!  Test")
        }

        private fun getCallerName(): String {
            val    index      = 4     // <== Index in call stack array
            val    methodName = "Log" //
            var caller = "NONE"
            val stacktrace = Thread.currentThread().stackTrace
           // for (i in stacktrace.indices) {
         //       Log.e("Method ", "[" + i + "]" + stacktrace[i].methodName)
          //  }
            if (stacktrace.size >= 5) {
                caller = stacktrace[5].methodName
            }
            return caller
        }


    }

     fun warn(s: String) {
        iwarn("$sClassName:${getCallerName()}  $s")
    }
}