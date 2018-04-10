package telegram.tests

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import telegrambot.Application
import telegrambot.controller.BotController

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(Application::class))
class BotControllerTest {
    @Autowired
    private lateinit var botController : BotController

    @Test
    fun botPingTest() {
        val user = botController.getMe()
        Assert.assertEquals(412390579L, user?.id)
        Assert.assertEquals(true, user?.isBot)
        Assert.assertEquals("fs_bot", user?.firstName)
        Assert.assertEquals("football_stat_bot", user?.username)
    }

    @Test
    fun botGetUpdate() {
        val updates = botController.getUpdates()
        Assert.assertNotNull(updates)
    }

    @Test
    fun botSendMessage() {
//        val response = botController.sendMessage()
    }
}