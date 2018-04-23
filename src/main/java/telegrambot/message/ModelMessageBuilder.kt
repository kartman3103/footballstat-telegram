package telegrambot.message

import model.football.LeagueInfo
import org.springframework.stereotype.Component

@Component
open class ModelMessageBuilder {
    fun getMessage(availableLeagues : List<LeagueInfo>) : String {
        val stringBuilder = StringBuilder()
        for (i in 0..availableLeagues.count() - 1) {
            val league = availableLeagues[i]
            stringBuilder.append("${i + 1}. ${league.name} /${league.id}_${league.toursPlayed} \n")
        }
        return stringBuilder.toString()
    }
}