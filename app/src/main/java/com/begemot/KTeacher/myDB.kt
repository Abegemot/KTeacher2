/**
 * Created by Dad on 26-Aug-17.
 */
package com.begemot.KTeacher

import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.begemot.KTeacher.myDB.Companion.reopen
import com.begemot.klib.KHelp
import org.jetbrains.anko.db.*



class myDB(ctx: Context,lang:String="") : ManagedSQLiteOpenHelper(ctx, "MyDatabase$lang", null, 1) {
    companion object {
        private var instance: myDB? = null
        public val X = KHelp("myDB")

        @Synchronized
        fun getInstance(ctx: Context,lang: String=""): myDB {
            X.warn("get instance lang=$lang")

            if (instance == null) {
                instance = myDB(ctx.getApplicationContext(),lang)
                X.warn("create instance lang=$lang")
            }
            return instance!!
        }

        @Synchronized
        fun reopen(){
            instance?.close()
            instance=null

        }


    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        X.warn("onCreateDB")
        db.createTable("PROBA", true, Pair("PEPE", TEXT), Pair("ID", INTEGER))

        envelopeX(Unit) {

            //var ds: SQLiteDatabase = DB2.writableDatabase


            db.execSQL(KLesson.DBCreate)
            db.insert(KLesson.tName,null,KLesson.values("1 PRIMERA LLICO Z"))
            db.insert(KLesson.tName,null,KLesson.values("2 PRIMERA LLICO X"))
            db.insert(KLesson.tName,null,KLesson.values("3 PRIMERA LLICO V"))
            db.insert(KLesson.tName,null,KLesson.values("4 PRIMERA LLICO Y"))
            db.insert(KLesson.tName,null,KLesson.values("5 PRIMERA LLICO W"))
            db.insert(KLesson.tName,null,KLesson.values("6 PRIMERA LLICO U"))

            db.execSQL(KKindOfExercice.DBCreate)
            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(0L,"Select from a set of russian words to form a frase",""))
            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(1L,"Form par of words to find antonims",""))
            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(2L,"who knows what",""))

            db.execSQL(KExercise.DBCreate)
            X.warn("after DBCreate")
            db.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            db.insert(KExercise.tName,null,KExercise.values(0,1,"segon exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            db.insert(KExercise.tName,null,KExercise.values(0,1,"tercer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            db.insert(KExercise.tName,null,KExercise.values(1,1,"cuart exercisi","exercisi",S1 = kotlin.ByteArray(0)))




        }
        X.warn("End CreateLessons")




    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        X.warn("onUpgradeDB")
        //db.dropTable("User", true)
    }
}

// Access property for Context
val Context.database: myDB
    get() = myDB.getInstance(getApplicationContext() )


class DBHelp(ctx: Context,lang: String) {
    companion object {
        private lateinit var DB2: myDB
        private var instance: DBHelp? = null
        var KOE:Array<String> =emptyArray()

        val X = KHelp("DBHelp")
        private var isOpen: Boolean = false

        @Synchronized
        fun getInstance(ctx: Context,lang: String=""): DBHelp {
            X.warn("$lang")
            if (instance == null) {
                instance = DBHelp(ctx.getApplicationContext(),lang  )
                DB2 = myDB.getInstance(ctx,lang)
                KOE=ctx.resources.getStringArray(R.array.kind_of_ex)
                X.warn("create instance $lang")
            }
            return instance!!
        }

        fun reopen(){
            //KOE=null
            myDB.reopen()
            instance=null
        }

    }

    fun getKOE():Array<String>{
        //testing bug -->  val rowParser = classParser<KExercise>()
        return KOE
    }

    fun getKindEx(I:Int):String{
        return KOE.get(I)
    }

    fun deleteTable(tableName:String){
        var ds: SQLiteDatabase = DB2.writableDatabase
       // val nR = envelopeX(0L){   ds.delete("$tableName").toLong()   }
        val nR = envelopeX(0L){   ds.dropTable("$tableName",false)  }
        X.warn("deleteTable  $tableName  returned = $nR")
    }

    fun createLessons(){
        X.warn("Begin CreateLessons")
        envelopeX(Unit) {

            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.execSQL(KLesson.DBCreate)
            ds.insert(KLesson.tName,null,KLesson.values("1 PRIMERA LLICO Z"))
            ds.insert(KLesson.tName,null,KLesson.values("2 PRIMERA LLICO X"))
            ds.insert(KLesson.tName,null,KLesson.values("3 PRIMERA LLICO V"))
            ds.insert(KLesson.tName,null,KLesson.values("4 PRIMERA LLICO Y"))
            ds.insert(KLesson.tName,null,KLesson.values("5 PRIMERA LLICO W"))
            ds.insert(KLesson.tName,null,KLesson.values("6 PRIMERA LLICO U"))
        }
        X.warn("End CreateLessons")
    }

    fun addLesson(lesson: String): KLesson {
        X.warn("addLesson")
        var nR: Long = 0
        var k: KLesson = KLesson(10,"pepe")
        nR = envelopeX(0L){
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert(KLesson.tName, "NAME" to lesson)
        }
        X.warn("Numero retornat per InsertLesson $nR  nameLesson = $lesson")
        k.id = nR
        k.name = lesson
        return k
    }

    fun deleteLesson(id:Long) {
        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR = envelopeX(0L){     ds.delete(KLesson.tName,"ID=$id").toLong()    }
        X.warn("returned = $nR")
    }

    fun deleteAllLessons(){
         deleteTable(KLesson.tName)
    }

    fun loadLessons(): List<KLesson> {

        X.warn("LoadLesons in ${DB2.databaseName}")
        val L2: List<KLesson> = envelopeX(emptyList()) {    DB2.use { select(KLesson.tName,*KLesson.tSelect).exec { parseList(classParser<KLesson>()) } } }
        X.warn("SIZE Lessons: ${L2.size}")
        //for (item in L2) X.warn(item.toString())
        return L2
    }


    fun createKindOfExercises() {

        X.warn("createKindOfExcercises")
           envelopeX(Unit) {
               var ds: SQLiteDatabase = DB2.writableDatabase
               ds.execSQL(KKindOfExercice.DBCreate)
               //DB2.use { createTable("KINDOFEX", true, "ID" to INTEGER + PRIMARY_KEY, "T1" to TEXT, "T2" to TEXT) }
               //var ds: SQLiteDatabase = DB2!!.writableDatabase
               ds.insert(KKindOfExercice.tName,null,KKindOfExercice.values(0L,"Select from a set of russian words to form a frase",""))
               ds.insert(KKindOfExercice.tName,null,KKindOfExercice.values(1L,"Form par of words to find antonims",""))
               ds.insert(KKindOfExercice.tName,null,KKindOfExercice.values(2L,"who knows what",""))

               //ds.insert("KINDOFEX", "T1" to "Select from a set of russian words to form a frase", "T2" to "")
               //ds.insert("KINDOFEX", "T1" to "Form par of words to find antonims", "T2" to "")
               //ds.insert("KINDOFEX", "T1" to "who knows what", "T2" to "")
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

        //val rowParser = classParser<KExercise>()
        X.warn("enter createExerciseTable")
        var l:List<Any> =envelopeX(emptyList()) {
            X.warn("enter enve  lope")
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.execSQL(KExercise.DBCreate)
            X.warn("after DBCreate")
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"segon exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"tercer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(1,1,"cuart exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.close()
            X.warn("after Insert")
            //DB2.use { select(KExercise.tName, *KExercise.tSelect).exec { parseList(rowParser) }}
            DB2.use { select(KExercise.tName, *KExercise.tSelect).exec { getListKE() }}
        }
       X.warn("leaving createExerciseTable: ${l.size}")
    }

    fun insertExerciseToLesson(idLesson:Long){
        X.warn("enter ")
        envelopeX(0) {
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(10)))
            ds.close()
        }
        X.warn(" exit " )
    }

    fun createEX1Examples(ctx:Context){
        //this.context

        val examplesOriginal   : Array<String> = ctx.resources.getStringArray(R.array.examples_original)
        val examplesTranslated : Array<String> = ctx.resources.getStringArray(R.array.examples_translated)

        var I=0
        for(item in examplesOriginal){
            val KE=KExercise(1,2,1,item,examplesTranslated[I])
            I++
            insertExerciseToLesson(KE)
        }

    }



    fun insertExerciseToLesson(KE:KExercise):Long{
        X.warn("enter ")
        var nR:Long=0
        nR = envelopeX(0L) {
            var N:Long=0
            var ds: SQLiteDatabase = DB2.writableDatabase
            N=ds.insert(KExercise.tName,null,KExercise.values2(KE))
            ds.close()
            return@envelopeX N
        }
        X.warn(" exit " )
        return nR
    }

    fun updateExercise(KE:KExercise){

        var ds: SQLiteDatabase = DB2.writableDatabase

        val nR= envelopeX(0L){ds.update(KExercise.tName,KExercise.values2(KE),"ID=${KE.ID}",null)}


    }



    fun deleteExerciceTable(){
        deleteTable(KExercise.tName)
        var ds: SQLiteDatabase = DB2.writableDatabase

      //  ds.select("pepe").whereArgs("(_id = {userId}) ","userId" to 42)
      //  ds.select("pepe").whereSimple("ID = ?", zipCode.toString())

    }


    fun loadLessonExercises(lesonID: Long): List<KExercise> {
        X.warn("entra  DB NAME: ${DB2.databaseName}")
        DB2.databaseName
        val L2: List<KExercise> = envelopeX(emptyList()) {
   //         DB2.use {  select(KExercise.tName,*KExercise.tSelect).whereSimple("IDL=?",lesonID.toString()).exec { parseList(classParser<KExercise>()) }  }
            DB2.use {  select(KExercise.tName,*KExercise.tSelect).whereSimple("IDL=?",lesonID.toString()).exec { getListKE() }  }
        }
        X.warn("SIZE List OfExcecises Of X Lesson: $lesonID =  ${L2.size}")
        //for (item in L2)  X.warn(item.toString())
        return L2
    }

    fun loadExercise(exerciseID: Long): KExercise {
        X.warn("entra")
        val L2: KExercise = envelopeX(KExercise()) {
            DB2.use {  select(KExercise.tName,*KExercise.tSelect).whereSimple("ID=?",exerciseID.toString()).exec { getKEcercise() }  }
        }
        return L2
    }


    fun Cursor.getListKE():List<KExercise>  {
        X.warn("entra")
        val L:MutableList<KExercise> = mutableListOf<KExercise>()
        if(this.moveToFirst()){
               do {
                   val KE:KExercise=KExercise()
                   //"ID","IDL","TOE","T1","T2","S1"
                   KE.ID=this.getLong(0)
                   KE.IDLesson=this.getLong(1)
                   KE.TypeOfEx=this.getInt(2)
                   KE.TL1=this.getString(3)
                   KE.TL2=this.getString(4)
                   KE.S1=this.getBlob(5)
                   L.add(KE)

               }while(this.moveToNext())
            X.warn("surt")
            return L
           }
           return emptyList()
    }

    fun Cursor.getKEcercise():KExercise {
        this.moveToFirst()
        val KE:KExercise=KExercise()
        //"ID","IDL","TOE","T1","T2","S1"
        KE.ID       =this.getLong(0)
        KE.IDLesson =this.getLong(1)
        KE.TypeOfEx =this.getInt(2)
        KE.TL1      =this.getString(3)
        KE.TL2      =this.getString(4)
        KE.S1       =this.getBlob(5)
        return KE
    }



    fun printAll(){
        X.warn("begin printAll")

        X.warn("end printAll")

    }


    fun CEA(){
        X.warn("entering CEA ")

        /*
        deleteAllLessons()
        createLessons()
        loadLessons()
        addLesson("MY LESSON")
        deleteLesson(10)

        deleteKindOfExercises()
        createKindOfExercises()
        loadKindOfExercises()

        deleteExerciceTable()
        createExerciseTable()

        loadLessonExercises(1)
        */
        deleteExerciceTable()
        createExerciseTable()

        X.warn("leaving CEA ")
    }




 }



fun <T> envelopeX (default: T, letter: () -> T) = try {
    letter()
} catch (e: Exception) {
    DBHelp.X.warn("envelope exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
    default
}






