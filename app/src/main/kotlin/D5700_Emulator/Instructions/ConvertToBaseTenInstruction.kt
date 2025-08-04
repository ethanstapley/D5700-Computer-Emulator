package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class ConvertToBaseTenInstruction (
    private val rx: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        val value = cpu.registers[rx].toInt() and 0xFF
        val hundreds = value / 100
        val tens = (value % 100) / 10
        val ones = value % 10

        ram.write(cpu.A, hundreds.toByte())
        ram.write(cpu.A + 1, tens.toByte())
        ram.write(cpu.A + 2, ones.toByte())
    }
}