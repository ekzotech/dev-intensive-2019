package me.evilcorp.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)?.takeUnless(String::isBlank)
        val lastName = parts?.getOrNull(1)?.takeUnless(String::isBlank)

//        return Pair(firstName, lastName)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        TODO("not implemented")
    }

    // возвращаем первые буквы имени и фамилии
    fun toInitials(firstName: String?, lastName: String?): String? {
        TODO("not implemented")
    }
}