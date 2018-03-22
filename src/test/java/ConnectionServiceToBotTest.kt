
import org.apache.http.client.fluent.Request
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class ConnectionServiceToBotTest {
    @Test
    fun testBotConnection() {
        val request = Request.Get("https://api.telegram.org/bot412390579:AAGMICSbEDmvQGVndGJuzdPwq3Cfzo0qjNo/getMe")
        val response = request.execute()

        val httpResponse = response.returnResponse()
        Assert.assertEquals(200, httpResponse.statusLine.statusCode)
    }

    @Test
    fun testGetUpdates() {
        val request = Request.Get("https://api.telegram.org/bot412390579:AAGMICSbEDmvQGVndGJuzdPwq3Cfzo0qjNo/getUpdates")
        val response = request.execute().returnResponse()

        val output = ByteArrayOutputStream()
        response.entity.writeTo(output)

        val content = output.toString(Charset.defaultCharset().name())

        Assert.assertNotNull(content)
        Assert.assertEquals(200, response.statusLine.statusCode)
    }
}