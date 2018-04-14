package telegrambot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.config.RemoteConfig
import javax.annotation.PostConstruct


@Component
open class TelegramUrlDealer {
    @Autowired
    private lateinit var remoteConfig : RemoteConfig

    @PostConstruct
    fun init() {
        botUrl = with(remoteConfig) {
            "${telegramUrl}/${botId}:${token}"
        }

        getMe = "${botUrl}/getMe"
        getUpdates = "${botUrl}/getUpdates"
        sendMessage = "${botUrl}/sendMessage"
    }

    lateinit var botUrl : String

    lateinit var getMe : String

    lateinit var getUpdates : String

    lateinit var sendMessage : String
}