package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.controller.HttpController
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class ConnectionServiceToBotTest {
    @Autowired
    lateinit var httpController : HttpController

    @Test
    fun pingTest() {
        val response = httpController.sendGet("https://api.telegram.org/bot412390579:AAGMICSbEDmvQGVndGJuzdPwq3Cfzo0qjNo/getMe")
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

    @Test
    fun getUpdatesTest() {
        val response = httpController.sendGet("https://api.telegram.org/bot412390579:AAGMICSbEDmvQGVndGJuzdPwq3Cfzo0qjNo/getUpdates")

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertNotNull(content)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }

//    @Test
//    fun sendMessageTest() {
//        val response = httpController.sendGet("https")
//    }
}