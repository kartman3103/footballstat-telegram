package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import telegrambot.message.ModelMessageBuilder
import java.util.*

@Component
open class UpdatesScheduler {
    @Autowired
    private lateinit var botController : BotController

    @Autowired
    private lateinit var footballstatProvider : FootballstatProvider

    @Autowired
    private lateinit var modelMessageBuilder : ModelMessageBuilder

    var offset : Int = -1

    @Scheduled(fixedRate = 5000)
    fun listenBot() : Unit {
        val updates = botController.getUpdates(offset)

        if (!updates.isEmpty()) {
            updates.forEach {
                if (Objects.equals(it.message.text, "/availableleagues")) {
                    val availableLeagues = footballstatProvider.availableLeagues()
                    val formattedLeagues = modelMessageBuilder.availableLeaguesMessage(availableLeagues)

                    botController.sendMessage(it.message.chat.id, formattedLeagues)

                }
                else {
                    val parts = it.message.text?.split("_")
                    if (parts != null && parts[0] == "/lg") {
                        val league = footballstatProvider.getLeague(parts[1], Integer.parseInt(parts[2]))
                        val formattedLeague = modelMessageBuilder.leagueMessage(league)

                        botController.sendMessage(it.message.chat.id, formattedLeague)
                    }
                }
                offset = it.id + 1
            }
        }
    }
}