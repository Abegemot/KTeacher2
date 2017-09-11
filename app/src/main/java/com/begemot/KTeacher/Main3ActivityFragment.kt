package com.begemot.kteacher

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import com.begemot.klib.*

/**
 * A placeholder fragment containing a simple view.
 */
class Main3ActivityFragment : Fragment() {

    private val X = KHelp("${this.javaClass.simpleName}")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        X.warn("onCreateView")
        return inflater.inflate(R.layout.fragment_main3, container, false)
    }
}
