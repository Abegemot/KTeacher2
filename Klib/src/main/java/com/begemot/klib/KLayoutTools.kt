package com.begemot.klib

import android.widget.TextView
import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.textView
import org.jetbrains.anko.constraint.layout.constraintLayout
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.constraint.ConstraintSet.TOP
import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
//import android.widget.TextView
import com.begemot.klib.DBHelp.Companion.X

import com.begemot.klib.KT
import com.begemot.klib.myCloud

import io.grpc.myproto.Word
import io.grpc.myproto.WordSample
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
//import org.jetbrains.anko.constraint.layout._ConstraintLayout
import org.jetbrains.anko.sdk25.listeners.onScrollListener
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.coroutines.*
import org.jetbrains.anko.sdk25.listeners.onClick

/**
 * Created by dad on 21/02/2018.
 */




class KLayout() {
     companion object {


         fun _ConstraintLayout.kTextView(s: String, i: Int): TextView {

             return textView() {
                 id = i
                 text = s
                 setBackgroundColor(android.graphics.Color.BLUE)
                 textSize = 15f
                 padding = dip(5)
             }

         }
     }

}
