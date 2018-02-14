package com.begemot.klib


import io.grpc.ManagedChannelBuilder
import io.grpc.myproto.addLessonerGrpc
import io.grpc.myproto.getExerciseR
//import io.grpc.netty.NegotiationType
//import io.grpc.netty.NettyChannelBuilder
import java.util.concurrent.TimeUnit



/**
 * Created by dad on 21/10/2017.
 */


class myCloud{
     companion object {


         fun exportLesson(ID: Long,dbh:DBHelp) {
             getExercise(8, dbh)
         }


         fun getExercise(ide:Int,dbh:DBHelp){
             val channel = ManagedChannelBuilder.forAddress("192.168.1.141", 8088).usePlaintext(true).build()
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
             }



         }

     }


}