package telegrambot.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import telegrambot.model.Chat
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import java.io.IOException

@Component
open class ModelParser {
    fun parseUser(json : String?) : User? {
        return try {
            with(ObjectMapper().readTree(json)) {
                User(
                    get("id").longValue(),
                    get("is_bot").booleanValue(),
                    get("first_name").textValue(),
                    get("last_name").textValue(),
                    get("language_code").textValue()
                )
            }
        }
        catch (ex : IOException) { null }
        catch (ex : NullPointerException) { null }
    }

    fun parseChat(json : String?) : Chat? {
        return try {
            with(ObjectMapper().readTree(json)) {
                Chat(
                    get("id").longValue(),
                    get("type").textValue(),
                    get("first_name").textValue(),
                    get("last_name").textValue()
                )
            }
        }
        catch (ex : IOException) { null }
        catch (ex : NullPointerException) { null }
    }

    fun parseMessage(json: String?) : Message? {
        return try {
            with(ObjectMapper().readTree(json)) {
                val chat = parseChat(get("chat").toString())!!
                Message(
                    get("message_id").longValue(),
                    get("date").intValue(),
                    chat,
                    get("text")?.textValue(),
                    parseUser(get("from")?.toString())
                )
            }
        }
        catch (ex : IOException) { null }
        catch (ex : NullPointerException) { null }
    }

    fun parseUpdate(json: String?) : Update? {
        return try {
            with(ObjectMapper().readTree(json)) {
                Update(
                    get("update_id").longValue(),
                    parseMessage(get("message").toString())
                )
            }
        }
        catch (ex : IOException) { null }
        catch (ex : NullPointerException) { null }
    }
}