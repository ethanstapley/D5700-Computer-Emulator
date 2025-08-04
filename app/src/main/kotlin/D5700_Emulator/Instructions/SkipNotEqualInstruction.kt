package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class SkipNotEqualInstruction (
    private val rx: Int,
    private val ry: Int
): Instruction(){
    override val autoIncrementPC = false
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        cpu.P += if (cpu.registers[rx] != cpu.registers[ry]) 4 else 2
    }
}