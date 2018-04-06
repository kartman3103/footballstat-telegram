package telegrambot.parsers

import org.springframework.stereotype.Component
import telegrambot.model.User

@Component
open class ModelParser {
    

    fun parseUser(json : String) : User {
        return User(3, false, "", "", "")
    }
}