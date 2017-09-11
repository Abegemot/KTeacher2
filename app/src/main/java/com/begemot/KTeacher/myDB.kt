/**
 * Created by Dad on 26-Aug-17.
 */
package com.begemot.kteacher

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.begemot.klib.KHelp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.warn


class myDB(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: myDB? = null
//      private val log = AnkoLogger("MYPOS")
        //private val X = KHelp("${this.javaClass.simpleName}")
        private val X = KHelp("myDB")
        @Synchronized
        fun getInstance(ctx: Context): myDB {
            if (instance == null) {
                instance = myDB(ctx.getApplicationContext())
                X.warn("create instance")
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        X.warn("onCreateDB")
        db.createTable("PROBA", true, Pair("PEPE", TEXT), Pair("ID", INTEGER))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        X.warn("onUpgradeDB")
        //db.dropTable("User", true)
    }
}

// Access property for Context
val Context.database: myDB
    get() = myDB.getInstance(getApplicationContext())


class DBHelp(ctx: Context) {
    companion object {
        private lateinit var DB2: myDB
        private var instance: DBHelp? = null
       //private val log = AnkoLogger("MYPOS")
        //private val X = KHelp("${this.javaClass.simpleName}")
       private val X = KHelp("DBHelp")
        private var isOpen: Boolean = false

        @Synchronized
        fun getInstance(ctx: Context): DBHelp {
            if (instance == null) {
                instance = DBHelp(ctx.getApplicationContext())
                DB2 = myDB.getInstance(ctx)
                X.warn("create instance")
            }
            return instance!!
        }
    }


    fun deleteTable(tableName:String){
        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR = envelopeX(0L){   ds.delete("$tableName").toLong()   }
        X.warn("deleteTable  $tableName  returned = $nR")
    }

    fun createLessons(){
        X.warn("Begin CreateLessons")
        envelopeX(Unit) {
            DB2.use { createTable("LESSONS", true, "ID" to INTEGER + PRIMARY_KEY, "NAME" to TEXT) }
            // DB2!!.use { insert("LESSONS","true", "NAME" to "kkkkk")}
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert("LESSONS", "NAME" to "1 PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "2 PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "3 PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "4 PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "5 PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "6 PRIMERA LLICO")
        }
        X.warn("End CreateLessons")
    }

    fun addLesson(lesson: String): KLesson {
        X.warn("addLesson")
        var nR: Long = 0
        var k: KLesson = KLesson("pepe", 10)
        nR = envelopeX(0L){
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert("LESSONS", "NAME" to lesson)
        }
        X.warn("Numero retornat per InsertLesson $nR  nameLesson = $lesson")
        k.idLeson = nR
        k.nameLeson = lesson
        return k
    }

    fun deleteLesson(id:Long) {
        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR = envelopeX(0L){     ds.delete("LESSONS","ID=$id").toLong()    }
        X.warn("returned = $nR")
    }

    fun deleteAllLessons(){
         deleteTable("LESSONS")
    }

    fun loadLessons(): List<KLesson> {
        X.warn("LoadLesons")
        val L2: List<KLesson> = envelopeX(emptyList()) {    DB2.use { select("LESSONS", "NAME", "ID").exec { parseList(classParser<KLesson>()) } } }
        X.warn("SIZE Lessons: ${L2.size}")
        for (item in L2) X.warn(item.toString())
        return L2
    }


    fun createKindOfExercises() {

        X.warn("createKindOfExcercises")
           envelopeX(Unit) {
               DB2.use { createTable("KINDOFEX", true, "ID" to INTEGER + PRIMARY_KEY, "T1" to TEXT, "T2" to TEXT) }
               // DB2!!.use { insert("LESSONS","true", "NAME" to "kkkkk")}
               var ds: SQLiteDatabase = DB2!!.writableDatabase
               ds.insert("KINDOFEX", "T1" to "Select from a set of russian words to form a frase", "T2" to "")
               ds.insert("KINDOFEX", "T1" to "Form par of words to find antonims", "T2" to "")
               ds.insert("KINDOFEX", "T1" to "who knows what", "T2" to "")
           }
     }

    fun loadKindOfExercises(): List<KKindOfExercice> {
        X.warn( "LoadKindOfExcercises" )
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        val L2: List<KKindOfExercice> = envelopeX(emptyList()) {  DB2.use { select("KINDOFEX", "ID", "T1", "T2").exec { parseList(classParser<KKindOfExercice>()) } } }
        X.warn("SIZE List KindOfLessons: ${L2.size}")
        for (item in L2) X.warn(item.toString())
        return L2
    }



    fun deleteKindOfExercises() {
        deleteTable("KINDOFEX")
//        var ds: SQLiteDatabase = DB2.writableDatabase
//        val nR = envelopeX(0L){   ds.delete("KINDOFEX").toLong() }
//        log.warn("deleteKindOfExercises :  $nR")
    }




    fun createExerciseTable(){

        val rowParser = classParser<KExercise>()
        X.warn("enter createExerciseTable")
        var l:List<Any> =envelopeX(emptyList()) {
            X.warn("enter enve  lope")
            DB2.use { createTable("KEXERCISE", true, "ID" to INTEGER + PRIMARY_KEY, "KINDOF" to INTEGER, "T1" to TEXT, "T2" to TEXT) }
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert("KEXERCISE", "T1" to "Primer exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Segon  exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Tercer exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Cuaert exercisi", "T2" to "")
            ds.close()

            DB2.use { select("KEXERCISE", "ID", "T1","T2").exec { parseList(rowParser) }}
        }
       X.warn("leaving createExerciseTable: ${l.size}")
    }

    fun deleteExerciceTable(){
        deleteTable(KExercise.tName())
    }


    fun loadLessonExercises(lesonID: Long): List<KExercise> {
        X.warn ( "loadExcercisesOfALesson" )
        val L2: List<KExercise> = envelopeX(emptyList()) {
            DB2.use {  select(KExercise.tName(),*KExercise.tSelect).exec { parseList(classParser<KExercise>()) }  }

        }
        X.warn("SIZE List OfExcecises Of X Lesson: $lesonID =  ${L2.size}")
        for (item in L2)  X.warn(item.toString())
        return L2
    }

    fun printAll(){
        X.warn("begin printAll")

        X.warn("end printAll")

    }


    fun CEA(){
        X.warn("entering CEA ")

        /*createLessons();
        loadLessons()
        addLesson("MY LESSON")
        deleteLesson(10)
        deleteAllLessons()

        createKindOfExercises()
        deleteKindOfExercises()
        createKindOfExercises()
        loadKindOfExercises()

        createExerciseTable()
        deleteExerciceTable()*/
        loadLessonExercises(1)

        X.warn("leaving CEA ")
    }


    fun <T> envelopeX (default: T, letter: () -> T) = try {
        letter()
    } catch (e: Exception) {
        X.warn("envelope exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
        default
    }


 }









