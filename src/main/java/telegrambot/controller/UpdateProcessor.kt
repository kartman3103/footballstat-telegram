package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.message.ModelMessageBuilder
import telegrambot.model.Update
import java.util.*

@Component
open class UpdateProcessor {
    @Autowired
    private lateinit var footballstatProvider : FootballstatProvider

    @Autowired
    private lateinit var modelMessageBuilder : ModelMessageBuilder

    fun proccessUpdate(it: Update) : String {
        if (Objects.equals(it.message.text, "/availableleagues")) {
            return processAvailableLeagues()
        } else {
            val parts = it.message.text?.split("_")
            if (parts != null && parts[0] == "/lg") {
                return processLeague(parts[1], Integer.parseInt(parts[2]))
            }
        }
        throw UnsupportedOperationException("Unsupported command ${it.message.text}")
    }

    private fun processLeague(id : String, matchDay : Int) : String {
        val league = footballstatProvider.getLeague(id, matchDay)
        return modelMessageBuilder.leagueMessage(league)
    }

    private fun processAvailableLeagues(): String {
        val availableLeagues = footballstatProvider.availableLeagues()
        return modelMessageBuilder.availableLeaguesMessage(availableLeagues)
    }
}