package telegrambot.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import model.football.LeagueInfo
import org.springframework.stereotype.Component
import java.util.*

@Component
class FootballstatModelParser {
    fun parseAvailableLeagues(json : String) : List<LeagueInfo> {
        val result = ArrayList<LeagueInfo>()
        val leagues : ArrayNode = ObjectMapper().readTree(json) as ArrayNode
        for (league in leagues.elements()) {
            result.add(ObjectMapper().treeToValue(league, LeagueInfo::class.java))
        }
        return result
    }
}