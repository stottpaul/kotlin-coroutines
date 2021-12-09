import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

fun main() {
    println("Hello from main")
    HelloWorld().hello()
}


class HelloWorld {
    fun hello() = "Hello from me"
}
