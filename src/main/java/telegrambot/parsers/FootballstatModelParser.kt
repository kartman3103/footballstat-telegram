package telegrambot.parsers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import model.football.League
import model.football.LeagueInfo
import org.springframework.stereotype.Component

@Component
open class FootballstatModelParser {
    private val mapper = jacksonObjectMapper()

    init {
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
    }

    fun parseAvailableLeagues(json : String) : List<LeagueInfo> {
        val typeReference : TypeReference<List<LeagueInfo>> =
                object : TypeReference<List<LeagueInfo>>(){}
        return mapper.readValue(json, typeReference)
    }

    fun parseLeague(json : String) : League {
        val typeReference : TypeReference<League> = object : TypeReference<League>(){}
        return mapper.readValue(json, typeReference)
    }
}