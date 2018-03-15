package com.begemot.klib
import android.content.Context
import io.grpc.myproto.Subject


class myProviderHelper(ctx:Context){
    companion object {
        val X = KHelp("myProviderHelper")
        private var  instance: myProviderHelper? = null
        lateinit var dbhelp:DBHelp
        lateinit var cSrv:CacheServer


        @Synchronized
        fun getInstance(ctx: Context): myProviderHelper {
            if (instance == null) {
                X.err("")
                instance = myProviderHelper(ctx.getApplicationContext())
                dbhelp   = DBHelp(ctx.applicationContext)
                cSrv     = CacheServer(30, dbhelp)
            }
            return instance!!
        }

    }

        fun retrieveCachedObject(id:Int):Subject{
              return dbhelp.loadFromCache(id)
        }

        fun getSubject(id:Int):Subject{
           // X.err("1")

            val sub=Subject.newBuilder()
            //X.err("2")


            val p= cSrv.getObject(id,1)

            //X.err("3")


            if(p.first){
               // X.err("existeix en cache")


                val f=p.second as ByteArray
              //  X.err(" tamamy getSubject cache ${f.size}")

                sub.mergeFrom(p.second as ByteArray)
                //X.err("getsubj serialized size  ")

                return sub.build()
            }

            //X.err("No existeix en cache")

            val s = myCloud.getSubject(id.toInt())

            cSrv.storeObject(s.id,1,s.toByteArray(),s.translated.take(25))

            return s

        }

    }


val Context.DBP:myProviderHelper
    get() = myProviderHelper.getInstance(applicationContext)
