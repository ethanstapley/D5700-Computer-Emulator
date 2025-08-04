package D5700_Emulator.Instructions

import D5700_Emulator.*
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM

class HaltInstruction : Instruction() {
    override val autoIncrementPC = false
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        cpu.halted = true
    }
}
