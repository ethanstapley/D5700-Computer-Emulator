package D5700_Emulator

import java.util.concurrent.*

object Timer {
    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor { runnable ->
        Thread(runnable).apply { isDaemon = true }
    }
    fun start(cpu: CPU) {
        executor.scheduleAtFixedRate(
            { if (cpu.T.toInt() > 0) cpu.T = (cpu.T - 1).toByte() },
            0,
            16,
            TimeUnit.MILLISECONDS
        )
    }

    fun stop() {
        executor.shutdownNow()
    }
}