package telegrambot.model

data class User(val id : Long,
                val isBot : Boolean,
                val firstName : String,
                val username: String?,
                val languageCode : String?)