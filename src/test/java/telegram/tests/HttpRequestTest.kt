package telegram.tests

import org.apache.http.HttpHost
import org.apache.http.client.fluent.Executor
import org.apache.http.client.fluent.Request
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.config.RemoteConfig
import telegrambot.controller.HttpController
import telegrambot.url.TelegramUrlDealer
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/test-data.properties"))
class HttpRequestTest {
    @Autowired
    private lateinit var telegramHttpController: HttpController

    @Autowired
    private lateinit var telegramUrlDealer: TelegramUrlDealer

    @Autowired
    private lateinit var remoteConfig : RemoteConfig

    @Test
    fun pingTest() {
        val response = telegramHttpController.makeResponseGET(telegramUrlDealer.getMe)

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertTrue(content != null && content.length != 0)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test
    fun getUpdatesTest() {
        val response = telegramHttpController.makeContentGET(
                telegramUrlDealer.getUpdates,
                Charset.defaultCharset())

        Assert.assertNotNull(response)
    }

    @Value("\${test.chat.id}")
    private var chatId : Int? = null

    @Test
    fun sendMessageTest() {
        val message = "Welcome+to+football+statistic+world"
        val response = telegramHttpController.makeResponseGET("${telegramUrlDealer.sendMessage}?chat_id=$chatId&text=$message")

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertNotNull(content)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test
    fun proxyTest() {
        val proxyHost = HttpHost(remoteConfig.proxyIP, remoteConfig.proxyPort)
        val executor = Executor.newInstance().authPreemptive(proxyHost)

        val result = executor.execute(
                Request.Get(telegramUrlDealer.getMe).viaProxy(proxyHost)).returnResponse()

        Assert.assertTrue(result.statusLine.statusCode == 200)
    }
}