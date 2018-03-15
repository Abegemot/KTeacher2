package com.begemot.klib


import com.google.protobuf.ByteString
import io.grpc.ManagedChannelBuilder
import io.grpc.myproto.*
import java.util.concurrent.TimeUnit



/**
 * Created by dad on 21/10/2017.
 */


class myCloud{
     companion object {
        val X = KHelp("myCloud")
        const val sHttp="35.195.81.78"
        const val iport=8089
        lateinit var byt_lang:ByteString


         fun setLang(s:String){
            byt_lang=Klang.toBytesFromString(s)
         }


         fun exportLesson(ID: Long,dbh:DBHelp) {
             getExercise(8, dbh)
         }

         fun getWord(id: Int):Word{
             return getWords(shallow = false,id = id)[0]
         }
         fun getSubject(id: Int):Subject{
             return getSubjects(shallow = false,id=id)[0]
         }

         /*fun getWords(s:String,endevant:Boolean=true):List<Word>{


  //           println("Test Words")

            // return getWords(sFrom = s,size = 10,shallow = true,id=0,forward = true,kl=Klang.toBytes(Klang.ZH,Klang.EN))
             return getWords(sFrom = s,size = 10,shallow = true,id=0,forward = true,kl=byt_lang)

             /*lateinit var LW:List<Word>
             X.err("getListWords :"+execute { LW=getWords(s,10,true,0,Klang.toBytes(Klang.ZH,Klang.ST))})
             //println("getWords :"+execute { KServer.getWords("b",20,false,0,Klang.toBytes(Klang.ZH,Klang.ST)).forEach{println(it)}})
             //println("getListWordsShallow :"+execute { LW=(KServer.getWords("b",200,true,0,Klang.toBytes(Klang.ZH,Klang.ST)))})

             LW.forEach{X.err(it.toString())}
             //println(LW)
             X.err("fin print nwords: ${LW.size} ")

             println("end test words")
*/

         }*/


         fun getWords(sFrom: String="", size:Int=10, shallow:Boolean=true, id:Int=0, forward:Boolean=true, kl:ByteString= byt_lang ):List<Word>{
             val channel = ManagedChannelBuilder.forAddress(sHttp, iport).usePlaintext(true).build()
             val blockingStub = KServiceGrpc.newBlockingStub(channel).withMaxInboundMessageSize(26116248 )
             val LWord: MutableList<Word> = mutableListOf()

             val startPoint=StartPoint.newBuilder()
             startPoint.start = sFrom
             startPoint.lang  = Lang.newBuilder().setLang(kl).build()
             startPoint.size  = size
             startPoint.shallow  = shallow
             startPoint.id    = id
             startPoint.fordward = forward

             var s=""
             with(startPoint){
                 s="start $start  lang ${lang.toString()}  size $size  shallow $shallow  id $id fordward $forward"
             }
             X.err(s)
             try {
                 // if(shallow) LWord.addAll(blockingStub.getListWordsShallow(startPoint.build()).wList)
                 LWord.addAll(blockingStub.getListWords(startPoint.build()).wList)

             }catch (e:Exception){
                 X.err("getWords exception ${e.message}")
             } finally {
                 channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
                 return LWord
             }
         }


         fun getSubjects(sFrom: String="", size:Int=15, shallow:Boolean=true, id:Int=0, forward:Boolean=true, kl:ByteString= byt_lang ):List<Subject>{
             val channel = ManagedChannelBuilder.forAddress(sHttp, iport).usePlaintext(true).build()
             val blockingStub = KServiceGrpc.newBlockingStub(channel).withMaxInboundMessageSize(26116248 )
             val LSubjects: MutableList<Subject> = mutableListOf()

             val startPoint=StartPoint.newBuilder()
             startPoint.start = sFrom
             startPoint.lang  = Lang.newBuilder().setLang(kl).build()
             startPoint.size  = size
             startPoint.shallow  = shallow
             startPoint.id    = id
             startPoint.fordward = forward

             var s=""
             with(startPoint){
                 s="start $start  size $size  shallow $shallow  id $id fordward $forward"
             }
             X.err(s)
             try {
                 // if(shallow) LWord.addAll(blockingStub.getListWordsShallow(startPoint.build()).wList)
                 LSubjects.addAll(blockingStub.getListSubjects(startPoint.build()).subjectList)

             }catch (e:Exception){
                 X.err("getWords exception ${e.message}")
             } finally {
                 channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
                 return LSubjects
             }
         }




         fun getExercise(ide:Int,dbh:DBHelp){
            /* val channel = ManagedChannelBuilder.forAddress("192.168.1.141", 8088).usePlaintext(true).build()
             val blockingStub = addLessonerGrpc.newBlockingStub(channel)
             val builder = getExerciseR.newBuilder()
             with(builder) {
                 id = 8
             }

             val request = builder.build()
             try {
                 println("Calling server")
                 val response = blockingStub.getExercise(request)
                 println("Server called")
                 //println("Response from server: ${response.ok}")

                 println(response.t1)
                 println(response.t2)
                 println(response.sBinary.size())


                 val KE= KExercise()
                 KE.IDLesson=1
                 KE.TypeOfEx=1
                 KE.TL1=response.t1
                 KE.TL2=response.t2
                 KE.S1=response.sBinary.toByteArray()

                 dbh.insertExerciseToLesson(KE)



             } finally {
                 channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
             }*/



         }

     }


}