package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

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