package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    var incorrectCount = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        when(question) {
            Question.NAME -> if (answer.toLowerCase()[0] == answer[0]) {
                return "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
            }
            Question.PROFESSION -> if (answer.toLowerCase()[0] != answer[0]) {
                return "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
            }
            Question.MATERIAL -> if (answer.replace("[^0-9]".toRegex(), "").isNotEmpty()) {
                return "Материал не должен содержать цифр\n${question.question}" to status.color
            }
            Question.BDAY -> if (answer.replace("[0-9]".toRegex(), "").isNotEmpty()) {
                return "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
            }
            Question.SERIAL -> if (answer.replace("[0-9]".toRegex(), "").isNotEmpty() || answer.length != 7) {
                return "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
            }
            Question.IDLE -> Log.d("M_Bender", "Не выполняем валидацию")
        }
    return if (question.answers.contains(answer.toLowerCase())) {
        question = question.nextQuestion()
        "Отлично - ты справился\n${question.question}" to status.color
    } else {
        if (incorrectCount < 3) {
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        } else {
            incorrectCount = 0
            question = Question.NAME
            status = Status.NORMAL
            "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        }
    }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        // возвращаем следующий тип enum
        // дошли до последнего - возвращаем первый
        fun nextStatus():Status {
            return if(this.ordinal < values().lastIndex) {
                values() [this.ordinal +1]
            } else {
                values() [0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion():Question
    }
}