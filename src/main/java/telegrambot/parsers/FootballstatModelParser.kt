package telegrambot.parsers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import model.football.LeagueInfo
import org.springframework.stereotype.Component
import telegrambot.model.football.TLeague
import telegrambot.model.football.TTeam

@Component
open class FootballstatModelParser {
    private val mapper = jacksonObjectMapper()

    fun parseAvailableLeagues(json : String) : List<LeagueInfo> {
        val typeReference : TypeReference<List<LeagueInfo>> =
                object : TypeReference<List<LeagueInfo>>(){}
        return mapper.readValue(json, typeReference)
    }

    fun parseLeague(json : String) : TLeague {
        return mapper.readTree(json).let {
            TLeague(
                requiredNode("name", it).textValue(),
                requiredNode("matchDay", it).intValue(),
                requiredNode("teams", it).map {
                    parseTeam(it)
                }
            )
        }
    }

    private fun parseTeam(it: JsonNode): TTeam {
        val allStatistic = it.get("allStatistic")
        return TTeam(
            Integer.parseInt(requiredNode("id", it).textValue()),
            requiredNode("name", it).textValue(),
            requiredNode("position", allStatistic).intValue(),
            requiredNode("points", allStatistic).intValue()
        )
    }
}