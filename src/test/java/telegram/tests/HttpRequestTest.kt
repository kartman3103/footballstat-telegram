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
import telegrambot.controller.HttpController
import telegrambot.url.TelegramUrlDealer
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
@TestPropertySource(locations= arrayOf("classpath:config/test-data.properties"))
class HttpRequestTest {
    @Autowired
    private lateinit var httpController : HttpController

    @Autowired
    private lateinit var telegramUrlDealer: TelegramUrlDealer

    @Test
    fun pingTest() {
        val response = httpController.makeResponseGET(telegramUrlDealer.getMe)

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertTrue(content != null && content.length != 0)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test(expected = Exception::class)
    fun incorrectUrlPing() {
        val result = httpController.makeResponseGET("http://telegramIncorrectUrl:3030")
        Assert.assertNotNull(result)
    }

    @Test
    fun getUpdatesTest() {
        val response = httpController.makeContentGET(
                telegramUrlDealer.getUpdates,
                Charset.defaultCharset())

        Assert.assertNotNull(response)
    }

    @Value("\${test.chat.id}")
    private var chatId : Int? = null

    @Test
    fun sendMessageTest() {
        val message = "Welcome+to+football+statistic+world"
        val response = httpController.makeResponseGET("${telegramUrlDealer.sendMessage}?chat_id=$chatId&text=$message")

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertNotNull(content)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test
    fun proxyTest() {
        val proxyHost = HttpHost("18.217.146.114", 80)

        val executor = Executor.newInstance().authPreemptive(proxyHost)

        val result = executor.execute(Request.Get(telegramUrlDealer.getMe).viaProxy(proxyHost))
                .returnContent()
                .asString()

        Assert.assertNotNull(result)
    }
}