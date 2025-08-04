import D5700_Emulator.CPU
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TimerTest {
    @Test
    fun timer_counts_down_to_zero() {
        val cpu = CPU()
        cpu.T = 3
        cpu.startTimer()
        Thread.sleep(70)
        assertTrue(cpu.T <= 0)
    }
}
