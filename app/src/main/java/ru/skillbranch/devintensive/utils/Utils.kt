package ru.skillbranch.devintensive.utils

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import ru.skillbranch.devintensive.R

val translitMap = mapOf(
    "а" to "a",
    "б" to "b",
    "в" to "v",
    "г" to "g",
    "д" to "d",
    "е" to "e",
    "ё" to "e",
    "ж" to "zh",
    "з" to "z",
    "и" to "i",
    "й" to "i",
    "к" to "k",
    "л" to "l",
    "м" to "m",
    "н" to "n",
    "о" to "o",
    "п" to "p",
    "р" to "r",
    "с" to "s",
    "т" to "t",
    "у" to "u",
    "ф" to "f",
    "х" to "h",
    "ц" to "c",
    "ч" to "ch",
    "ш" to "sh",
    "щ" to "sh'",
    "ъ" to "",
    "ы" to "i",
    "ь" to "",
    "э" to "e",
    "ю" to "yu",
    "я" to "ya",
    "А" to "A",
    "Б" to "B",
    "В" to "V",
    "Г" to "G",
    "Д" to "D",
    "Е" to "E",
    "Ё" to "E",
    "Ж" to "Zh",
    "З" to "Z",
    "И" to "I",
    "Й" to "I",
    "К" to "K",
    "Л" to "L",
    "М" to "M",
    "Н" to "N",
    "О" to "O",
    "П" to "P",
    "Р" to "R",
    "С" to "S",
    "Т" to "T",
    "У" to "U",
    "Ф" to "F",
    "Х" to "H",
    "Ц" to "C",
    "Ч" to "Ch",
    "Ш" to "Sh",
    "Щ" to "Sh'",
    "Ъ" to "",
    "Ы" to "I",
    "Ь" to "",
    "Э" to "E",
    "Ю" to "Yu",
    "Я" to "Ya"
)

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)?.takeUnless(String::isBlank)
        val lastName = parts?.getOrNull(1)?.takeUnless(String::isBlank)

//        return Pair(firstName, lastName)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
//        val parts: List<String>? = payload?.split(" ")
//
//        var firstName = parts?.getOrNull(0)
//        var lastName = parts?.getOrNull(1)
//
//        translitMap.forEach { (from, to) -> firstName = firstName?.replace(from, to) }
//        if (lastName != null) {
//            translitMap.forEach { (from, to) -> lastName = lastName?.replace(from, to) }
//        }
//
//        return "$firstName${if (lastName != null) divider+lastName else ""}"
        var result = payload.replace(" ", divider)
        translitMap.forEach { (from, to) -> result = result.replace(from, to)}
        return result


    }

    // возвращаем первые буквы имени и фамилии
    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstCharOfFirstName = firstName?.firstOrNull()?.takeUnless(Char::isWhitespace)?.toUpperCase()
        val firstCharOfLastName = lastName?.firstOrNull()?.takeUnless(Char::isWhitespace)?.toUpperCase()

        val result: String?

        if (firstCharOfFirstName == null && firstCharOfLastName == null) {
            result = null
        } else if (firstCharOfFirstName == null) {
            result = firstCharOfLastName.toString()
        } else if (firstCharOfLastName == null) {
            result = firstCharOfFirstName.toString()
        } else {
            result = firstCharOfFirstName.toString() + firstCharOfLastName.toString()
        }

        return result

    }

    fun isRepositoryValid(repository: String): Boolean {
        if (repository.isEmpty()) return true
        val regex = Regex("^(https://)?(www\\.)?(github\\.com/)(?!(${githubReposExcludeSet.joinToString("|")})(?=/|\$))[a-zA-Z\\d](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){0,38}(/)?$")
        return repository.contains(regex)
    }

    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, value, true)
        return value.data
    }

    fun getCurrntModeColor(context: Context, attrColor: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attrColor, value, true)
        return value.data
    }

    fun isNightModeActive(context: Context) : Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

    private val githubReposExcludeSet = setOf(
        "enterprise",
        "features",
        "topics",
        "collections",
        "trending",
        "events",
        "marketplace",
        "pricing",
        "nonprofit",
        "customer-stories",
        "security",
        "login",
        "join"
    )
}