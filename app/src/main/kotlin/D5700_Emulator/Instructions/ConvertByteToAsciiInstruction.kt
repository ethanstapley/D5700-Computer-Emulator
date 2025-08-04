package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class ConvertByteToAsciiInstruction(
    private val rx: Int,
    private val ry: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        val value = cpu.registers[rx].toInt() and 0xFF
        if (value > 0xF) throw IllegalArgumentException("Value in register must be between 0 and F to convert to ASCII")

        val ascii = if (value < 10) (value + '0'.code) else (value - 10 + 'A'.code)

        cpu.registers[ry] = ascii.toByte()
    }
}