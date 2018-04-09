package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.controller.HttpController
import telegrambot.UrlDealer
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class HttpRequestTest {
    @Autowired
    private lateinit var httpController : HttpController

    @Autowired
    private lateinit var urlDealer : UrlDealer

    @Test
    fun pingTest() {
        val response = httpController.makeResponseGET(urlDealer.getMe)

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertTrue(content != null && content.length != 0)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test(expected = Exception::class)
    fun incorrectUrlPing() {
        httpController.makeResponseGET("http://telegramIncorrectUrl:3030")
    }

    @Test
    fun getUpdatesTest() {
        val response = httpController.makeResponseGET(urlDealer.getUpdates)
        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertNotNull(content)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test
    fun sendMessageTest() {
//        val response = httpController.makeResponseGet("https")
    }
}