package com.github.data.utils

import com.github.data.utils.DateUtils.today
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

internal class DateUtilsTest {

    @Test
    fun testCurrentDate() {
        val date = today()
        assert(date == SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time))
    }

}