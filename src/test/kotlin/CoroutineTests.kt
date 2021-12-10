import io.kotest.matchers.shouldBe
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals

class CoroutineTests {
//    A coroutine is an instance of suspendable computation.
//    It is conceptually similar to a thread, in the sense that it takes a block of code to run that works
//    concurrently with the rest of the code. However, a coroutine is not bound to any particular
//    thread. It may suspend its execution in one thread and resume in another one.


    // Difference between threads and coroutines.

    //cores
    //  sysctl -n hw.ncpu

    // Notice that threads are reused as there are only 12 in the default threadpool, 1 per core
    // Notice no order
    // Without the delay then unlikely to need all 12 threads as the code runs quicker than the dispatcher
    @Test
    fun testSimpleCoroutine() = runBlocking(Dispatchers.Default) {
        val i = AtomicInteger(0);

        repeat(14) {
            launch {
                delay(10)
                println("${i.getAndIncrement()}: ${Thread.currentThread().name}")
            }
        }

        println("Hello")
    }


    // Just using threads, notice a new thread is spun up for every piece of work
    @Test
    fun testSimpleThread() {
        val i = AtomicInteger(0);
        repeat(14) {
            Thread(Runnable {
                println("${i.getAndIncrement()}: ${Thread.currentThread().name}")
            }).start()
        }

        sleep(100)
    }

    // Show why lightweight add 5 second delay and repeat 100000 times for java then kotlin

    // Coroutines running on main thread, i.e. the thread running the test, FIFO
    @Test
    fun testSimpleCoroutineThreadMain() = runBlocking {
        var i: Int = 0
        repeat(10) {
            launch(Dispatchers.Unconfined) {
                i++
                println("$i: ${Thread.currentThread().name}")
            }
        }
    }

    // Note the thread is different on resume, also note that it is the suspend in delay
// that sets coroutines apart from executorservices in java, as threads are released upon suspend
    @Test
    fun testSimpleCoroutineWithDelay() = runBlocking {
        repeat(10) {
            launch(Dispatchers.Default) {
                println("Before delay $it: ${Thread.currentThread().name}")
                delay(10)
                println("After delay $it: ${Thread.currentThread().name}")
            }
        }
    }

    // Async
    @Test
    fun coroutinesThatReturnValues() = runBlocking {
        val asyncResponse = async {  delay(1000); "Returned value" }
        val asyncResponse2 = async {  delay(2000); "Returned value 2" }

        println (asyncResponse.await() + " and " + asyncResponse2.await())
    }


    @Test
    fun testBlockingWithScope() = runBlocking {
        val i = AtomicInteger(0);
        repeat(20) {
            coroutineScope {
                launch {
                    delay(100);
                    println("${i.getAndIncrement()}: ${Thread.currentThread().name}")
                }
            }
        }

        // The coroutine scope will not complete until all child coroutines have finished, also
        // if any exception is thrown from a child coroutine then all coroutines within scope will be
        // automatically cancelled.
    }

    @Test
    internal fun `should assert with junit assertions`() {
        assertEquals("Hello from me", HelloWorld().hello())
    }
}