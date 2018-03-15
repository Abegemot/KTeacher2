package com.begemot.klib

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import java.sql.SQLException

private val X = KHelp("CacheServer")
data class KCachedObj(val id:Int=0, val position:Int=0, val idObj:Int=0, val kind:Int=0,val str:String="", val obj: ByteArray? = null ):MI {

    override val tName: String             get() = sa.tName
    override val tSelect: Array<String>    get() = sa.tSelect
    override val tDBCreate: String         get() = sa.tDBCreate

    companion object sa {

       const val ID = "ID"
       const val POSITION = "POSITION"
       const val IDOBJ    = "IDOBJ"
       const val KIND = "KIND"
       const val STR = "STR"
       const val OBJ = "OBJ"


        const val tName ="KCACHEDOBJ"
        val tSelect =arrayOf("$ID","$POSITION","$IDOBJ","$KIND","$STR","$OBJ")
        val t= tSelect
        const val tDBCreate="""CREATE TABLE if not exists $tName ( $ID integer PRIMARY KEY ,$POSITION integer default 0 unique on conflict replace ,
                                                             $IDOBJ integer default 0,$KIND integer default 0,
                                                             $STR text, $OBJ blob default null)"""

        const val tIndexCreate="CREATE UNIQUE INDEX if not exists idx_cache on $tName($IDOBJ,$KIND)"

        /*fun tSubset(vararg ifields:Int):Array<String>{
            for (i in ifields){

            }
        }*/

        fun svalues(KC: KCachedObj)  : ContentValues {
            val A= ContentValues()
            A.put(tSelect[0],KC.id)
            A.put(tSelect[1],KC.obj)
            return A
        }

        fun pairs(KC: KCachedObj):Array<Pair<String,Any?>>{
            return  arrayOf(ID to KC.id, POSITION to KC.position, IDOBJ to KC.idObj, KIND to KC.kind, STR to KC.str, OBJ to KC.obj)
        }

        val parser = rowParser {
            id: Int,pos:Int,idObj:Int,kind:Int,str:String,obj:ByteArray? ->   KCachedObj(id,pos,idObj,kind,str,obj)
        }




    }
}



class CacheServer(val sizecache:Int,val sDBH:DBHelp){

       init {
           val ds: SQLiteDatabase = sDBH.database()
           //delete()
           ds.execSQL(KCachedObj.tDBCreate)
           ds.execSQL(KCachedObj.tIndexCreate)
       }


       fun delete(){
           if(sDBH.tableEX(KCachedObj.tName)) sDBH.deleteTable(KCachedObj.tName)
           //sDBH.database().execSQL(KCachedObj.tDBCreate)
           //sDBH.database().execSQL(KCachedObj.tIndexCreate)

       }

       fun sscreate(){
           var ds: SQLiteDatabase = DBHelp.DB2.writableDatabase
           X.err("start")

           //val p = KCachedObj.parser
           //val ps = parseList(p)


           if(sDBH.tableEX(KCachedObj.tName)) sDBH.deleteTable(KCachedObj.tName)
           ds.execSQL(KCachedObj.tDBCreate)

           ds.execSQL(KCachedObj.tIndexCreate)

           X.err("created")

          /* for(n in 1..size) {
               val kc = KCachedObj(n, 0, 0, 0,"comment ")
               ds.insert(KCachedObj.tName, *KCachedObj.pairs(kc))
           }
           val lc=sDBH.loadAllWhere<KCachedObj>("id<100",KCachedObj.parser)
           lc.forEach{ X.err("${it.toString()}")}*/
           storeObject(kind = 1,idobj = 10,sval = "apples")
           storeObject(kind = 1,idobj = 11,sval = "bananas")
           storeObject(kind = 1,idobj = 12,sval = "oranges")
         //  sDBH.loadAllWhere<KCachedObj>(rp=KCachedObj.parser).forEach{ X.err("${it.toString()}")}


           storeObject(kind = 1,idobj = 13,sval = "apricots")
        //   sDBH.loadAllWhere<KCachedObj>(rp=KCachedObj.parser).forEach{ X.err("${it.toString()}")}


           storeObject(kind = 1,idobj = 14,sval = "ananas")
          // sDBH.loadAllWhere<KCachedObj>(rp=KCachedObj.parser).forEach{ X.err("${it.toString()}")}

           storeObject(kind = 1,idobj = 15,sval = "pineaples")
       //    sDBH.loadAllWhere<KCachedObj>(rp=KCachedObj.parser).forEach{ X.err("${it.toString()}")}

           storeObject(kind = 1,idobj = 16,sval = "mandarines")
          // sDBH.loadAllWhere<KCachedObj>(rp=KCachedObj.parser).forEach{ X.err("${it.toString()}")}

           X.err("fins aqui")
           getObject(14,1)



           X.err("fin")
       }

       fun getObject(id:Int,kind:Int):Pair<Boolean,Any?>{

            //X.err("1")
            //lateinit var p:Pair<Boolean,Any?>
            val L=sDBH.loadAllWhere<KCachedObj>(sWhere = "KIND='$kind' and IDOBJ='$id'", tParser = KCachedObj.parser)
            //X.err("2")

            if(L.isEmpty()){
              //  X.err("empty not found")
                return Pair(false,null)

            }else{
                val ko=L[0] as KCachedObj
                delayCachedItem(ko.id)
                //listAll()
                //X.err(" xSizeObj  ${ko.obj?.size}")
                return Pair(true,ko.obj)
            }
        }

fun delayCachedItem(id:Int){

          //listAll()
           var ds: SQLiteDatabase = sDBH.database()
           try {
               with(ds){
                   val l=select(KCachedObj.tName,"count(*)").whereArgs("ID={id}","id" to id+1).exec { parseSingle(IntParser) }
                   X.err("Te antecesor?: $l  id= $id")
                   if (l>0){

                       var sSQL=""" UPDATE ${KCachedObj.tName}  set id=-1, position=-1 where id=$id; """
                       X.err(sSQL)
                       execSQL(sSQL)

                       sSQL=""" UPDATE ${KCachedObj.tName}  set id=${id}, position=${(id) % sizecache} where id=${id+1}; """
                       X.err(sSQL)
                       execSQL(sSQL)

                       sSQL=""" UPDATE ${KCachedObj.tName}  set id=${id+1}, position=${(id+1) % sizecache} where position=-1; """
                       X.err(sSQL)
                       execSQL(sSQL)
                   }
               }
           } catch (e: SQLException) {
               X.err("augh!! ",e)
           }
       }


        fun listAll(){

            //val tSelect2 =arrayOf("$ID","$POSITION","$IDOBJ","$KIND","$STR")
            val parser = rowParser {
                id: Int,pos:Int,idObj:Int,kind:Int,str:String ->   KCachedObj(id,pos,idObj,kind,str)
            }
            val tSelect2 =KCachedObj.tSelect.copyOfRange(0,5)
            val L=sDBH.loadAllWhere<KCachedObj>(sWhere = "KIND='1'",tSel = tSelect2, tParser = parser)
            val SL=L.sortedWith(compareBy({it.id}))

            //X.err("")
            SL.forEach{X.err("${it.toString()}")}
        }


       fun storeObject(idobj:Int=0,kind:Int=0,obj:ByteArray?=null,sval:String){

           val sSQL="""
           insert into ${KCachedObj.tName}(${KCachedObj.ID},${KCachedObj.POSITION},${KCachedObj.IDOBJ},${KCachedObj.KIND},${KCachedObj.STR})
                select    (coalesce(max(id), -1) + 1), (coalesce(max(id), -1) + 1) % $sizecache,'$idobj','$kind', '$sval'
           from ${KCachedObj.tName}


           """
           val ds: SQLiteDatabase = DBHelp.DB2.writableDatabase

           val sSQL2="""
           insert into ${KCachedObj.tName}(${KCachedObj.ID},${KCachedObj.POSITION},${KCachedObj.IDOBJ},${KCachedObj.KIND},${KCachedObj.STR},${KCachedObj.OBJ})
                select    (coalesce(max(id), -1) + 1), (coalesce(max(id), -1) + 1) % $sizecache, '$idobj','$kind', ?,?
           from ${KCachedObj.tName}"""


           var statement=ds.compileStatement(sSQL2)
           var value=sval
           var vvv=obj

           statement.bindString(1,value)
           if (obj!=null) {
               statement.bindBlob(2,vvv)
               X.err(" size blob stored ${vvv?.size}")
           }
           try {
               statement.executeInsert()
           //    ds.execSQL(sSQL)
           } catch (e: Exception) {

               X.err("store Object err: ${e.message}")
           }
           listAll()
       }




}