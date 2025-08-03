package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.InputHandler
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class SetAInstruction(private val aaa: Int) : Instruction() {
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen, input: InputHandler) {
        cpu.A = aaa
    }
}