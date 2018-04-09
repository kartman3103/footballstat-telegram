package telegrambot.parsers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import org.springframework.stereotype.Component
import telegrambot.model.*
import java.io.IOException
import java.util.*

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

    fun parseUpdate(json : String?) : Update? {
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

    fun parseUpdateResponse(json : String?) : UpdateResponse? {
        return try {
            with(ObjectMapper().readTree(json)) {
                UpdateResponse(
                        get("ok").booleanValue(),
                        parseUpdates(get("result")))
            }
        }
        catch (ex : IOException) { null }
        catch (ex : NullPointerException) { null }
    }

    fun parseUpdates(jsonNode : JsonNode?) : List<Update> {
        if (jsonNode == null) {
            return emptyList()
        }

        val updates = ArrayList<Update>()
        for (element in jsonNode.elements()) {
            val parsedUpdate : Update? = parseUpdate(element.toString())
            if (parsedUpdate != null) {
                updates.add(parsedUpdate)
            }
        }
        return updates
    }
}