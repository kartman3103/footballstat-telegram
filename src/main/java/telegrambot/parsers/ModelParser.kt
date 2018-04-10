package telegrambot.parsers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import telegrambot.model.Chat
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import java.util.*

@Component
open class ModelParser {
    val requiredNode = { name : String, jsonNode : JsonNode -> jsonNode.get(name) ?:
            throw ModelInvalidationException("Cannot find json node with name: $name")
    }

    val node = { name : String, jsonNode : JsonNode -> jsonNode.get(name) }
    val parseTree = { json : String -> ObjectMapper().readTree(json) }
    val parseUser = { json : String -> parseTree(json).get("result").let { parseUser(it) } }

    fun parseUser(jsonNode : JsonNode) : User {
        return jsonNode.let {
            User(
                requiredNode("id", it).longValue(),
                requiredNode("is_bot", it).booleanValue(),
                requiredNode("first_name", it).textValue(),
                node("username", it)?.textValue(),
                node("language_code", it)?.textValue()
            )
        }
    }

    fun parseChat(json : String) : Chat {
        return parseTree(json).let {
            Chat(
                requiredNode("id", it).longValue(),
                requiredNode("type", it).textValue(),
                node("first_name", it)?.textValue(),
                node("last_name", it)?.textValue()
            )
        }
    }

    fun parseMessage(json: String) : Message {
        return parseTree(json).let {
            Message(
                requiredNode("message_id", it).longValue(),
                requiredNode("date", it).intValue(),
                parseChat(requiredNode("chat", it).toString()),
                node("text", it)?.textValue(),
                node("from", it)?.let { parseUser(it) }
            )
        }
    }

    fun parseUpdate(json : String) : Update {
        return parseTree(json).let {
            Update(
                requiredNode("update_id", it).longValue(),
                parseMessage(requiredNode("message", it).toString())
            )
        }
    }

    fun parseUpdates(json: String) : List<Update> {
        requiredNode("result", ObjectMapper().readTree(json)).let {
            val updates = ArrayList<Update>()
            for (element in it.elements()) {
                parseUpdate(element.toString()).let {
                    updates.add(it)
                }
            }
            return updates
        }
    }
}