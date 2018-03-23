import org.apache.http.HttpResponse
import org.apache.http.client.fluent.Request
import org.springframework.stereotype.Component

@Component
class HttpController {
    fun sendGet(url : String) : HttpResponse {
        try {
            val request = Request.Get(url)
            return request.execute().returnResponse()
        }
        catch (ex : Exception) {
            throw ex
        }
    }
}