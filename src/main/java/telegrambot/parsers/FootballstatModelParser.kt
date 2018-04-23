package telegrambot.parsers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import model.football.LeagueInfo
import org.springframework.stereotype.Component

@Component
open class FootballstatModelParser {
    val mapper = jacksonObjectMapper()

    fun parseAvailableLeagues(json : String) : List<LeagueInfo> {
        val typeReference : TypeReference<List<LeagueInfo>> =
                object : TypeReference<List<LeagueInfo>>(){}
        return mapper.readValue(json, typeReference)
    }
}