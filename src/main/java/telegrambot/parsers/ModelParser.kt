package telegrambot.parsers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import telegrambot.model.*
import java.io.IOException
import java.util.*

@Component
open class ModelParser {
    fun parseUser(json : String?) : User? {
        return parseTree(json)?.get("result")?.let {
            User(
                it.get("id").longValue(),
                it.get("is_bot").booleanValue(),
                it.get("first_name").textValue(),
                it.get("username")?.textValue(),
                it.get("language_code")?.textValue()
            )
        }
    }

    fun parseChat(json : String?) : Chat? {
        return parseTree(json)?.let {
            Chat(
                it.get("id").longValue(),
                it.get("type").textValue(),
                it.get("first_name")?.textValue(),
                it.get("last_name")?.textValue()
            )
        }
    }

    fun parseMessage(json: String?) : Message? {
        return parseTree(json)?.let {
            Message(
                it.get("message_id").longValue(),
                it.get("date").intValue(),
                parseChat(it.get("chat")?.toString()),
                it.get("text")?.textValue(),
                parseUser(it.get("from")?.toString())
            )
        }
    }

    fun parseUpdate(json : String?) : Update? {
        return parseTree(json)?.let {
            Update(
                it.get("update_id").longValue(),
                parseMessage(it.get("message").toString())
            )
        }
    }

    fun parseUpdates(json: String?) : List<Update>? {
        return parseTree(json)?.get("result")?.let {
            val updates = ArrayList<Update>()
            for (element in it.elements()) {
                parseUpdate(element.toString())?.let {
                    updates.add(it)
                }
            }
            return updates
        }
    }

    private fun parseTree(json : String?) : JsonNode? {
        return json?.let {
            try {
                ObjectMapper().readTree(it)
            } catch (ex : IOException) { null }
        }
    }
}