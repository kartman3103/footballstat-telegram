package telegrambot.controller

import org.apache.http.client.utils.URIBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import telegrambot.parsers.TelegramModelParser
import telegrambot.url.TelegramUrlDealer
import java.nio.charset.Charset

@Component
open class BotController {
    @Autowired
    private lateinit var telegramHttpController: HttpController

    @Autowired
    private lateinit var telegramUrlDealer: TelegramUrlDealer

    @Autowired
    private lateinit var telegramModelParser: TelegramModelParser

    open fun getMe() : User? {
        val uriBuilder = URIBuilder(telegramUrlDealer.getMe)
        val url = uriBuilder.build().toString()

        val content : String = telegramHttpController.makeContentGET(url, Charset.defaultCharset())
        return telegramModelParser.parseUserResponse(content)
    }

    open fun getUpdates(offset : Int) : List<Update> {
        val uriBuilder = URIBuilder(telegramUrlDealer.getUpdates)
        uriBuilder.addParameter("offset", offset.toString())

        val url = uriBuilder.build().toString()
        val content : String = telegramHttpController.makeContentGET(url, Charset.defaultCharset())
        return telegramModelParser.parseUpdates(content)
    }

    open fun sendMessage(chatId : Long, message : String) : Message {
        val uriBuilder = URIBuilder(telegramUrlDealer.sendMessage)
        uriBuilder.addParameter("chat_id", chatId.toString())
        uriBuilder.addParameter("text", message)

        val url = uriBuilder.build().toString()
        val content = telegramHttpController.makeContentGET(url, Charset.defaultCharset())
        return telegramModelParser.parseMessageResponse(content)
    }
}