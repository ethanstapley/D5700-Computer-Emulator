package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class DrawInstruction (
    private val rx: Int,
    private val ry: Int,
    private val rz: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen) {
        val v = cpu.registers[rx].toInt() and 0xFF
        require(v <= 0x7F) { "DRAW: value > 0x7F" }
        val row = ry
        val col = rz
        screen.draw(row, col, v.toByte())
    }
}