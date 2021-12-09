import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HelloWorldTests {
    @Test
    internal fun `should assert with kotest assertions`() {
        HelloWorld().hello() shouldBe "Hello from me"
    }

    @Test
    internal fun `should assert with junit assertions`() {
        assertEquals("Hello from me", HelloWorld().hello())
    }
}