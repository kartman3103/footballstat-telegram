package telegrambot.parsers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import telegrambot.model.Chat
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import java.io.IOException
import java.util.*

@Component
open class ModelParser {
    val node = { name : String, jsonNode : JsonNode -> jsonNode.get(name) }

    val requiredNode = { name : String, jsonNode : JsonNode -> jsonNode.get(name) ?:
            throw ModelInvalidationException("Cannot find json node with name: $name")
    }

    fun parseUser(json : String?) : User? {
        return parseTree(json)?.get("result")?.let {
            User(
                requiredNode("id", it).longValue(),
                requiredNode("is_bot", it).booleanValue(),
                requiredNode("first_name", it).textValue(),
                node("username", it)?.textValue(),
                node("language_code", it)?.textValue()
            )
        }
    }

    fun parseChat(json : String?) : Chat? {
        return parseTree(json)?.let {
            Chat(
                requiredNode("id", it).longValue(),
                requiredNode("type", it).textValue(),
                node("first_name", it)?.textValue(),
                node("last_name", it)?.textValue()
            )
        }
    }

    fun parseMessage(json: String?) : Message? {
        return parseTree(json)?.let {
            val chat = parseChat(requiredNode("chat", it).toString())
                    ?: throw ModelInvalidationException("Cannot find json node with name: chat")

            Message(
                requiredNode("message_id", it).longValue(),
                requiredNode("date", it).intValue(),
                chat,
                node("text", it)?.textValue(),
                parseUser(node("from", it)?.toString())
            )
        }
    }

    fun parseUpdate(json : String?) : Update? {
        return parseTree(json)?.let {
            Update(
                requiredNode("update_id", it).longValue(),
                parseMessage(requiredNode("message", it).toString())
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