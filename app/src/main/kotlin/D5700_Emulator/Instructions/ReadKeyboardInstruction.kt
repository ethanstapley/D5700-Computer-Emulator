package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.InputHandler
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class ReadKeyboardInstruction (
    private val rx: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen, input: InputHandler) {
        println("Input: ")
        val input = readln().trim().uppercase().toCharArray()
        val value = when {
            input.isEmpty() -> 0
            input.size > 2 -> throw IllegalStateException("Invalid Input, Char limit is 2")
            else -> (String(input)).toInt(16)
        }
        cpu.registers[rx] = value.toByte()
    }
}