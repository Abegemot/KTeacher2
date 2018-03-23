package com.begemot.KTeacher.koin

import com.begemot.KTeacher.KApp
import com.begemot.klib.DBHelp.Companion.X
import com.begemot.klib.myProviderHelper
import io.grpc.myproto.Subject

interface SubjectRepository {
    fun giveHello():String
    fun getSubject(id:Int):Subject

}


class SubjectRepositoryImp() : SubjectRepository {

    //val myViewModel:SubjectViewModel by viewModel()
    //val ms: myProviderHelper = getMyProviderHelper()

    override fun giveHello() = "Hello Koin"

    override fun getSubject(id: Int):Subject {

        var DBP=myProviderHelper.GPH()
        return DBP.getSubject(id)


        /*val su=Subject.newBuilder()

        X.err("subjectrepository getsubject $id")
         su.id=144
         su.name="koin 10000000  $id"
         su.translated="koin trans 10000000"
         su.romanized="romanized koin 10000000"
         return su.build()*/
    }

}