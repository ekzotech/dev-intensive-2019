package ru.skillbranch.devintensive.extensions

fun String.truncate(count : Int = 16): String {
    val res = this.trim()
    if (count > -1) {
        return if (res.length > count) {
            res.take(count).trimEnd() + "..."
        } else {
            res
        }
    }
    return res
}

fun String.stripHtml(): String {
    return replace("<.*?>".toRegex() , "").replace("&.*?;".toRegex(), "").replace("\\s+".toRegex(), " ")
}