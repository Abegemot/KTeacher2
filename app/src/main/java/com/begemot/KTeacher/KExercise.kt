package com.begemot.kteacher

/**
 * Created by Dad on 28-Aug-17.
 */
data class KExercise(var IdExercise:Long=0, var TL1:String="", var TL2:String=""){

     companion object {
         fun tName()   : String = "KEXERCISE"
         val tSelect: Array<String> = arrayOf("ID","T1","T2")

     }

}