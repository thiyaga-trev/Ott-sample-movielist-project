package com.thiyaga.ottsampleproject.utils

import androidx.annotation.IntDef
import com.thiyaga.ottsampleproject.utils.Orientation.Companion.LANDSCAPE
import com.thiyaga.ottsampleproject.utils.Orientation.Companion.PORTRAIT

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(PORTRAIT, LANDSCAPE)
annotation class Orientation {
    companion object {
        const val PORTRAIT = 1
        const val LANDSCAPE = 2
    }
}
