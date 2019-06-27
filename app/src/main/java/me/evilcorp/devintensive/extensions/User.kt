package me.evilcorp.devintensive.extensions

import me.evilcorp.devintensive.models.User
import me.evilcorp.devintensive.models.UserView
import me.evilcorp.devintensive.utils.Utils

fun User.toUserView() : UserView {

    // возвращаем Imya_Familiya
    val nickName = Utils.transliteration("$firstName $lastName")
    // возвращаем IF
    val initials = Utils.toInitials(firstName, lastName)
    // возвращаем "был несколько секунд/минут/часов/дней назад"
    val status = if (lastVisit == null) "Ещё ни разу не был" else if (isOnline) "online" else "Последний раз был ${lastVisit.humanizeDiff()}"


    // прямая ссылка на датакласс User
    return UserView(id, fullName = "$firstName $lastName", nickName = nickName, avatar = avatar, initials = initials, status = status)
}