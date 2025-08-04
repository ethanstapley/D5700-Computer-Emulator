package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.InputHandler
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class DrawInstruction (
    private val rx: Int,
    private val ry: Int,
    private val rz: Int
): Instruction(){
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen, input: InputHandler) {
        val value = cpu.registers[rx]
        screen.draw(
            cpu.registers[ry].toInt(),
            cpu.registers[rz].toInt(),
            value
        )
    }
}