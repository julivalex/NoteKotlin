package com.java.note.notekotlin.utils

object RequestCode {
    const val REQUEST_CODE_NEW_TASK = 1
}

object DateTimeConst {
    const val DATE = "date"
    const val TIME = "time"
    const val DATE_TIME = "dateTime"
}

object ModelTaskConst {
    const val TASK = "ModelTask"
}

object TabAdapterConst {
    const val CURRENT_TASK_FRAGMENT_POSITION = 0
    const val DONE_TASK_FRAGMENT_POSITION = 1
}

object Priority {
    const val PRIORITY_LOW = 0
    const val PRIORITY_NORMAL = 1
    const val PRIORITY_HIGH = 2

    const val STATUS_OVERDUE = 0
    const val STATUS_CURRENT = 1
    const val STATUS_DONE = 2

    val PRIORITY_LEVELS = arrayOf("Low Priority", "Normal Priority", "High Priority")
}