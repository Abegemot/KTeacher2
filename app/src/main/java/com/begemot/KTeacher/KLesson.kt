package com.begemot.kteacher

/**
 * Created by Dad on 23-Aug-17.
 */
data class KLesson(var nameLeson: String = "", var idLeson: Long =0){
    override fun toString(): String {
        return nameLeson.toString()
    }
}
