package telegrambot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
open class UrlDealer {
    @Autowired
    private lateinit var remoteConfig : RemoteConfig

    @PostConstruct
    fun init() {
        botUrl = with(remoteConfig) {
            "${telegramUrl}/${botId}:${token}"
        }

        getMe = "${botUrl}/getMe"
        getUpdates = "${botUrl}/getUpdates"
    }

    lateinit var botUrl : String

    lateinit var getMe : String

    lateinit var getUpdates : String
}