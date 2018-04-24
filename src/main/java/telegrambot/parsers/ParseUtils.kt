package telegrambot.parsers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

val requiredNode = { name : String, jsonNode : JsonNode -> jsonNode.get(name) ?:
        throw ModelInvalidationException("Cannot find json node with name: $name")
}

val node = {
    name : String, jsonNode : JsonNode -> jsonNode.get(name)
}

val parseResult = {
    json : String -> requiredNode("result", ObjectMapper().readTree(json))
}