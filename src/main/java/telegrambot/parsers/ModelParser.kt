package telegrambot.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import telegrambot.model.User

@Component
open class ModelParser {
    fun parseUser(json : String) : User {
        return with(ObjectMapper().readTree(json)) {
            User(
                get("id").longValue(),
                get("is_bot").booleanValue(),
                get("first_name").textValue(),
                get("last_name").textValue(),
                get("language_code").textValue()
            )
        }
    }
}