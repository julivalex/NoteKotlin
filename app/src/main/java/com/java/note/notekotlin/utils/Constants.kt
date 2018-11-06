package com.java.note.notekotlin.utils

object RequestCode {
    const val REQUEST_CODE_NEW_TASK = 1
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

    val PRIORITY_LEVELS = arrayOf("Low Priority", "Normal Priority", "High Priority")
}

object Status {
    const val STATUS_OVERDUE = 0
    const val STATUS_CURRENT = 1
    const val STATUS_DONE = 2
}

object Database {
    const val NAME = "note_database"
    const val VERSION = 1

    const val TASKS_TABLE = "tasks_table"

    object Column {
        const val TASK_ID = "_id"
        const val TASK_TITLE = "task_title"
        const val TASK_DATE = "task_date"
        const val TASK_PRIORITY = "task_priority"
        const val TASK_STATUS = "task_status"
        const val TASK_TIME_STAMP = "task_time_stamp"
    }


    fun createScript(): String =
        "create table $TASKS_TABLE (" +
                "${Column.TASK_ID} integer primary key autoincrement," +
                "${Column.TASK_TITLE} text not null," +
                "${Column.TASK_DATE} long," +
                "${Column.TASK_PRIORITY} integer," +
                "${Column.TASK_STATUS} integer," +
                "${Column.TASK_TIME_STAMP} long" + ");"


    fun deleteScript(): String = "drop table $TASKS_TABLE"
}

object Selection {
    const val STATUS: String = "${Database.Column.TASK_STATUS} = ?"
    const val TIME_STAMP: String = "${Database.Column.TASK_TIME_STAMP} = ?"
    const val SELECTION_LIKE_TITLE = "${Database.Column.TASK_TITLE} LIKE ?"
}