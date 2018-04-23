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
open class TelegramModelParser {
    private val requiredNode = { name : String, jsonNode : JsonNode -> jsonNode.get(name) ?:
            throw ModelInvalidationException("Cannot find json node with name: $name")
    }

    private val node = {
        name : String, jsonNode : JsonNode -> jsonNode.get(name)
    }

    private val parseResult = {
        json : String -> requiredNode("result", ObjectMapper().readTree(json))
    }

    val parseUserResponse = {
        json : String -> parseResult(json).let { parseUser(it) }
    }

    val parseMessageResponse = {
        json : String -> parseResult(json).let { parseMessage(it) }
    }

    fun parseUpdates(json: String) : List<Update> {
        requiredNode("result", ObjectMapper().readTree(json)).let {
            val updates = ArrayList<Update>()
            for (element in it.elements()) {
                parseUpdate(element).let {
                    updates.add(it)
                }
            }
            return updates
        }
    }

    private fun parseUser(jsonNode : JsonNode) : User {
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

    private fun parseChat(jsonNode: JsonNode) : Chat {
        return jsonNode.let {
            Chat(
                requiredNode("id", it).longValue(),
                requiredNode("type", it).textValue(),
                node("first_name", it)?.textValue(),
                node("last_name", it)?.textValue()
            )
        }
    }

    private fun parseMessage(jsonNode : JsonNode) : Message {
        return jsonNode.let {
            Message(
                requiredNode("message_id", it).longValue(),
                requiredNode("date", it).intValue(),
                parseChat(requiredNode("chat", it)),
                node("text", it)?.textValue(),
                node("from", it)?.let { parseUser(it) }
            )
        }
    }

    private fun parseUpdate(jsonNode: JsonNode) : Update {
        return jsonNode.let {
            Update(
                requiredNode("update_id", it).intValue(),
                parseMessage(requiredNode("message", it))
            )
        }
    }
}