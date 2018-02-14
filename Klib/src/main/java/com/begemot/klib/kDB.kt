package com.begemot.klib
/**
 * Created by Dad on 26-Aug-17.
 */
/**
 * Created by dad on 25/01/2018.
 */

import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.begemot.klib.BuildConfig.DEBUG
import com.begemot.klib.DBHelp.Companion.DB2
//import com.begemot.KTeacher.myDB.Companion.getInstance
//import com.begemot.KTeacher.myDB.Companion.reopen
import com.begemot.klib.KHelp
//import com.begemot.klib.KLevel.Companion.qtSelect
//import com.begemot.klib.KLevel.Companion.tName
import org.jetbrains.anko.db.*




class myDB(ctx: Context,lang:String="") : ManagedSQLiteOpenHelper(ctx, "MyDatabase$lang", null, 1) {
    private var L=lang

    companion object {
        private var instance: myDB? = null
        public val X = KHelp("myDB")

        @Synchronized
        fun getInstance(ctx: Context,lang: String=""): myDB {
            if(DEBUG)X.warn("get instance lang=$lang")

            if (instance == null) {
                instance = myDB(ctx.getApplicationContext(),lang)
                if(DEBUG)X.warn("create instance lang=$lang")
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
        if(DEBUG)X.warn("onCreateDB")
        //if(L.equals("en"))

        envelopeX(Unit) {

            //var ds: SQLiteDatabase = DB2.writableDatabase
            db.execSQL(KLesson.DBCreate)
            db.execSQL(KExercise.DBCreate)
            createLevels(db)
            if(L.equals("en")) {

                db.insert(KLesson.tName, null, KLesson.values("First Lesson"))
                db.insert(KLesson.tName, null, KLesson.values("Second Lesson"))
                return@envelopeX
            }
            if(L.equals("es")) {
                db.insert(KLesson.tName, null, KLesson.values("Primera Lección"))
                db.insert(KLesson.tName, null, KLesson.values("Segunda Lección"))
                return@envelopeX
            }



//            db.execSQL(KKindOfExercice.DBCreate)
//            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(0L,"Select from a set of russian words to form a frase",""))
//            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(1L,"Form par of words to find antonims",""))
//            db.insert(KKindOfExercice.tName,null,KKindOfExercice.values(2L,"who knows what",""))

//            db.execSQL(KExercise.DBCreate)
//            if(DEBUG)X.warn("after DBCreate")
//            db.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
//            db.insert(KExercise.tName,null,KExercise.values(0,1,"segon exercisi","exercisi",S1 = kotlin.ByteArray(0)))
//            db.insert(KExercise.tName,null,KExercise.values(0,1,"tercer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
//            db.insert(KExercise.tName,null,KExercise.values(1,1,"cuart exercisi","exercisi",S1 = kotlin.ByteArray(0)))




        }
        if(DEBUG)X.warn("End CreateLessons")




    }
    fun createLevels(db: SQLiteDatabase){
        X.err("1  CreateLevels ")
        db.dropTable("KLEVEL",true)
        db.execSQL(KLevel.DBCreate)
        for (i in 1..21){
            try {
                db.insert(KLevel.tName, null, KLevel.values(KLevel(i.toLong(),desc = "desc $i")))
            } catch (e: Exception) {
                X.err("error  ${e.message}")
            }
        }
        X.err("2  CreateLevels ")



    }


/*    inline fun< reified T:MI  > loadAll():List<T>{
        X.err("enter   ")
        //var s=T::qtSelect
       // val L2: List<T> = envelopeX(emptyList()) {
            val s=T::class.java.newInstance()
            X.err("tname   ${s.tName}    tselect ${s.tSelect}")

            val L2=DBHelp.DB2.use { select(s.tName, *s.tSelect).exec { parseList(classParser<T>()) }
            }
 //       }
        X.err("zzzzzzzSIZE Lessons: ${L2.size}")
        //for (item in L2) if(DEBUG)X.warn(item.toString())
        return L2

        // }
        return emptyList()
    }*/






    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        if(DEBUG)X.warn("onUpgradeDB")
        //db.dropTable("User", true)
    }
}

// Access property for Context
val Context.database: myDB  get() = myDB.getInstance(getApplicationContext() )


class DBHelp(ctx: Context) {
    companion object {
        private var dbLang:String=""
        @PublishedApi internal lateinit var DB2: myDB
        private var instance: DBHelp? = null
        var KOE:Array<String> =emptyArray()

        val X = KHelp("DBHelp")
        private var isOpen: Boolean = false


        fun getInternalDB():myDB{
            return DB2
        }


        @Synchronized
        fun getInstance2(ctx: Context,lang: String=""): DBHelp {
            if(DEBUG)X.warn("$lang")
            if (instance == null) {

                instance   = DBHelp(ctx.getApplicationContext()  )
                DB2        = myDB.getInstance(ctx,lang)
                KOE        = ctx.resources.getStringArray(R.array.kind_of_ex)



                if(DEBUG)X.warn("create instance $lang")
            }
            return instance!!
        }


        @Synchronized
        fun getInstance(ctx: Context): DBHelp {
            if (instance == null) {

                instance   = DBHelp(ctx.getApplicationContext() )
                DB2        = myDB.getInstance(ctx, dbLang)
                KOE        = ctx.resources.getStringArray(R.array.kind_of_ex)
                if(DEBUG)X.warn("create instance $dbLang")
            }
            return instance!!
        }




        fun reopen(lang:String){
            dbLang=lang
            myDB.reopen()
            instance=null
        }


    }

    fun getKOE():Array<String>{
        //testing bug -->
        // val rowParser = classParser<KExercise>()
        return KOE
    }

    fun getKindEx(I:Int):String{
        return KOE.get(I)
    }


    fun getDBNAme():String{
        return DB2.databaseName
    }

    fun deleteTable(tableName:String){
        var ds: SQLiteDatabase = DB2.writableDatabase
        // val nR = envelopeX(0L){   ds.delete("$tableName").toLong()   }
        val nR = envelopeX(0L){   ds.dropTable("$tableName",false)  }
        if(DEBUG)X.warn("deleteTable  $tableName  returned = $nR")
    }

    /*  fun createLessons(){
          if(DEBUG)X.warn("Begin CreateLessons")
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
          if(DEBUG)X.warn("End CreateLessons")
      }*/

    fun addLesson(lesson: String): KLesson {
        if(DEBUG)X.warn("addLesson")
        var nR: Long = 0
        var k: KLesson = KLesson(10,name="pepe")
        nR = envelopeX(0L){
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert(KLesson.tName, "NAME" to lesson)
        }
        if(DEBUG)X.warn("Numero retornat per InsertLesson $nR  nameLesson = $lesson")
        k.id = nR
        k.name = lesson
        return k
    }

    fun addLesson(lesson: KLesson): KLesson {
        X.err("${lesson.toString()}")
        if(DEBUG)X.warn("addLesson")
        var nR: Long = 0
        nR = envelopeX(0L){  DB2.writableDatabase.insert(KLesson.tName, *lesson.getAA())      }
        if(DEBUG)X.warn("Numero retornat per InsertLesson $nR  nameLesson = $lesson")
        lesson.id = nR
        return lesson
    }


    fun deleteLesson(id:Long) {
        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR = envelopeX(0L){     ds.delete(KLesson.tName,"ID=$id").toLong()    }
        if(DEBUG)X.warn("returned = $nR")
    }

    /*fun deleteAllLessons(){
         deleteTable(KLesson.tName)
    }*/

    fun loadLessons(): List<KLesson> {
        X.err("${KLesson.tName}   ${KLesson.tSelect.toString()}")
        if(DEBUG)X.warn("LoadLesons in ${DB2.databaseName}")
        val L2: List<KLesson> = envelopeX(emptyList()) {    DB2.use { select(KLesson.tName,*KLesson.tSelect).exec {
            parseList(classParser<KLesson>())
        } } }
        if(DEBUG)X.warn("SIZE Lessons: ${L2.size}")
        //for (item in L2) if(DEBUG)X.warn(item.toString())
        X.err("${L2.size}")
        return L2
    }

    inline fun<reified T:MI> loadAll(): MutableList<T> {
        val s=T::class.java.newInstance()
        val tName=s.tName
        val tSelect=s.tSelect
        X.err("LoadAll in ${DB2.databaseName}")
        val L2: List<T> = envelopeX(emptyList()) {    DB2.use { select(tName,*tSelect).exec { parseList(classParser<T>()) } } }
        X.err("SIZE Lessons: ${L2.size}")
        //for (item in L2) if(DEBUG)X.warn(item.toString())
        return L2.toMutableList()
    }

    inline fun<reified T:MI> loadAllWhere(sWhere:String): MutableList<T> {
        val s=T::class.java.newInstance()
        val tName=s.tName
        val tSelect=s.tSelect
        X.err("LoadAllWhere $sWhere   in ${DB2.databaseName}")
        val L2: List<T> = envelopeX(emptyList()) {    DB2.use { select(tName,*tSelect).whereArgs(sWhere).exec { parseList(classParser<T>()) } } }
        X.err("SIZE Lessons: ${L2.size}")
        //for (item in L2) if(DEBUG)X.warn(item.toString())
        return L2.toMutableList()
    }

    fun updateLesson(KL:KLesson){

        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR= envelopeX(0L){ds.update(KLesson.tName,KLesson.values(KL),"ID=${KL.id}",null)}


    }


    /* fun createKindOfExercises() {

         if(DEBUG)X.warn("createKindOfExcercises")
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
      }*/

    fun loadKindOfExercises(): List<KKindOfExercice> {
        if(DEBUG)X.warn( "LoadKindOfExcercises" )
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        val L2: List<KKindOfExercice> = envelopeX(emptyList()) {  DB2.use { select("KINDOFEX", "ID", "T1", "T2").exec { parseList(classParser<KKindOfExercice>()) } } }
        if(DEBUG)X.warn("SIZE List KindOfLessons: ${L2.size}")
        for (item in L2) if(DEBUG)X.warn(item.toString())
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
        if(DEBUG)X.warn("enter createExerciseTable")
        var l:List<Any> =envelopeX(emptyList()) {
            if(DEBUG)X.warn("enter enve  lope")
            var ds: SQLiteDatabase = DB2.writableDatabase
            //ds.execSQL(KExercise.DBCreate)
            if(DEBUG)X.warn("after DBCreate")
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"segon exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"tercer exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.insert(KExercise.tName,null,KExercise.values(1,1,"cuart exercisi","exercisi",S1 = kotlin.ByteArray(0)))
            ds.close()
            if(DEBUG)X.warn("after Insert")
            //DB2.use { select(KExercise.tName, *KExercise.tSelect).exec { parseList(rowParser) }}
            DB2.use { select(KExercise.tName, *KExercise.tSelect).exec { getListKE() }}
        }
        if(DEBUG)X.warn("leaving createExerciseTable: ${l.size}")
    }

    fun insertExerciseToLesson(idLesson:Long){
        if(DEBUG)X.warn("enter ")
        envelopeX(0) {
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert(KExercise.tName,null,KExercise.values(0,1,"primer exercisi","exercisi",S1 = kotlin.ByteArray(10)))
            ds.close()
        }
        if(DEBUG)X.warn(" exit " )
    }

    fun createEX1Examples(ctx:Context){
        //this.context
        if(existEX()){
            if(DEBUG)X.warn(("JA EXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXISTEIX LA TAULA EXERCISIS"))
            return
        }
        if(DEBUG)X.warn(("NO EXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXISTEIX LA TAULA EXERCISIS"))
        var ds: SQLiteDatabase = DB2.writableDatabase
        ds.execSQL(KExercise.DBCreate)
        var examplesOriginal   : Array<String> = ctx.resources.getStringArray(R.array.examples_original1)
        var examplesTranslated : Array<String> = ctx.resources.getStringArray(R.array.examples_translated1)

        writeExExamples(2,0,examplesOriginal,examplesTranslated)




        examplesOriginal    = ctx.resources.getStringArray(R.array.examples_original3)

        writeExExamples(1,2,examplesOriginal, null)

        examplesTranslated  = ctx.resources.getStringArray(R.array.examples_translated2)
        writeExExamples(1,1,examplesTranslated, null)



        /*var I=0
        for(item in examplesOriginal){
            val KE=KExercise(1,2,0,item,examplesTranslated[I])
            I++
            insertExerciseToLesson(KE)
        }*/

    }

    fun writeExExamples(IDLesson:Long,TEx:Int,arrOriginal:Array<String>,arrTrans:Array<String>?){
        var I=0
        var KE:KExercise
        var A:Array<String>
        for(item in arrOriginal){
            if(arrTrans==null)         KE=KExercise(1,IDLesson ,TEx ,item,"")
            else{
                A=arrTrans as Array<String>
                KE=KExercise(1,IDLesson ,TEx ,item,arrTrans[I])
            }

            I++
            insertExerciseToLesson(KE)
        }



    }



    fun existEX():Boolean{
        try {
            DB2.use {  select(KExercise.tName,*KExercise.tSelectmin).whereSimple("IDL=?","9999").exec { getListminKE() }  }
        } catch (e: Exception) {
            return false
        }
        return true
    }



    fun insertExerciseToLesson(KE:KExercise):Long{
        if(DEBUG)X.warn("enter ")
        var nR:Long=0
        nR = envelopeX(0L) {
            var N:Long=0
            var ds: SQLiteDatabase = DB2.writableDatabase
            N=ds.insert(KExercise.tName,null,KExercise.values2(KE))
            ds.close()
            return@envelopeX N
        }
        if(DEBUG)X.warn(" exit " )
        return nR
    }

    fun updateExercise(KE:KExercise){

        var ds: SQLiteDatabase = DB2.writableDatabase

        val nR= envelopeX(0L){ds.update(KExercise.tName,KExercise.values2(KE),"ID=${KE.ID}",null)}


    }

    fun deleteExercise(id:Long) {
        var ds: SQLiteDatabase = DB2.writableDatabase
        val nR = envelopeX(0L){     ds.delete(KExercise.tName,"ID=$id").toLong()    }
        if(DEBUG)X.warn("returned = $nR")
    }

    fun deleteExerciceTable(){
        deleteTable(KExercise.tName)
        var ds: SQLiteDatabase = DB2.writableDatabase

        //  ds.select("pepe").whereArgs("(_id = {userId}) ","userId" to 42)
        //  ds.select("pepe").whereSimple("ID = ?", zipCode.toString())

    }


    fun loadLessonExercises(lesonID: Long): List<KExercise> {
        if(DEBUG)X.warn("entra  DB NAME: ${DB2.databaseName}")
        DB2.databaseName
        val L2: List<KExercise> = envelopeX(emptyList()) {
            DB2.use {  select(KExercise.tName,*KExercise.tSelectmin).whereSimple("IDL=?",lesonID.toString()).exec { getListminKE() }  }
        }
        if(DEBUG)X.warn("SIZE List OfExcecises Of X Lesson: $lesonID =  ${L2.size}")
        //for (item in L2)  if(DEBUG)X.warn(item.toString())
        return L2
    }

    fun loadExercise(exerciseID: Long): KExercise {
        if(DEBUG)X.warn("entra")
        val L2: KExercise = envelopeX(KExercise()) {
            DB2.use {  select(KExercise.tName,*KExercise.tSelect).whereSimple("ID=?",exerciseID.toString()).exec { getKEcercise() }  }
        }
        return L2
    }


    fun Cursor.getListKE():List<KExercise>  {
        if(DEBUG)X.warn("entra")
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
            if(DEBUG)X.warn("surt")
            return L
        }
        return emptyList()
    }

    fun Cursor.getListminKE():List<KExercise>  {
        if(DEBUG)X.warn("entra")
        val L:MutableList<KExercise> = mutableListOf<KExercise>()
        if(this.moveToFirst()){
            do {
                val KE:KExercise=KExercise()
                //"ID","IDL","TOE","T1","T2","S1"
                KE.ID=this.getLong(0)
                KE.IDLesson=this.getLong(1)
                KE.TypeOfEx=this.getInt(2)
                KE.TL1=this.getString(3)
//                KE.TL2=this.getString(4)
//                KE.S1=this.getBlob(5)
                L.add(KE)

            }while(this.moveToNext())
            if(DEBUG)X.warn("surt")
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
        if(DEBUG)X.warn("begin printAll")

        if(DEBUG)X.warn("end printAll")

    }


    fun CEA(){
        if(DEBUG)X.warn("entering CEA ")

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

        if(DEBUG)X.warn("leaving CEA ")
    }



    fun exportLesson(lesonID: Long){
        if(DEBUG)X.warn("Glups ...... ")
        myCloud.exportLesson(1L,this)


    }


}



fun <T> envelopeX (default: T, letter: () -> T) = try {
    letter()
} catch (e: Exception) {
    val X:KHelp=DBHelp.X
    //if(DEBUG)
        X.err("envelope exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
    default
}

