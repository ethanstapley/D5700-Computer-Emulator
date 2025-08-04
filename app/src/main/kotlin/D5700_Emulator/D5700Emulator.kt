package D5700_Emulator

import D5700_Emulator.Memory.*
import D5700_Emulator.Timer

class D5700Emulator {
    private val cpu = CPU()
    private val ram = RAM()
    private val rom = ROM()
    private val screen = Screen()
    private val factory = InstructionFactory()

    fun start(programPath: String) {
        val bytes = java.io.File(programPath).readBytes()
        bytes.forEachIndexed { i, byte ->
            rom.load(i, byte)
        }
        println("ROM loaded: ${bytes.size} bytes")
        cpu.startTimer()

        while (!cpu.halted) {
            cpu.tick(ram, rom, screen, factory)
            Thread.sleep(2)
        }
    }


    fun stop() {
        cpu.halted = true
    }

}