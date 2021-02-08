package com.github.data.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun today(): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
    }
}