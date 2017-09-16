package com.begemot.KTeacher

/**
 * Created by Dad on 28-Aug-17.
 */
data class KKindOfExercice( var ID:Long=0,var T1:String=" ",var T2:String=" " ){
     override fun toString(): String { return T1.toString()   }
}