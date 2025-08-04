package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class WriteInstruction (
    private val rx: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        val memory = if (cpu.M) rom else ram
        memory.write(cpu.A, cpu.registers[rx])
    }
}