package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class SubInstruction(
    private val rx: Int,
    private val ry: Int,
    private val rz: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        cpu.registers[rz] = (cpu.registers[rx] - cpu.registers[ry]).toByte()
    }
}