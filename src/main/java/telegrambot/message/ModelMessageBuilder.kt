package telegrambot.message

import model.football.LeagueInfo
import org.springframework.stereotype.Component
import telegrambot.model.football.TLeague

@Component
open class ModelMessageBuilder {
    fun availableLeaguesMessage(availableLeagues : List<LeagueInfo>) : String {
        val stringBuilder = StringBuilder()
        for (i in 0..availableLeagues.count() - 1) {
            val league = availableLeagues[i]
            stringBuilder.append("${i + 1}. ${league.name} /lg_${league.id}_${league.toursPlayed} \n")
        }
        return stringBuilder.toString()
    }

    fun leagueMessage(league : TLeague) : String {
        val stringBuilder = StringBuilder("<b>${league.name}</b> \n")
        league.teams.forEach {
            stringBuilder.append("${it.position}. ${it.name} - ${it.points} \n")
        }
        return stringBuilder.toString()
    }
}