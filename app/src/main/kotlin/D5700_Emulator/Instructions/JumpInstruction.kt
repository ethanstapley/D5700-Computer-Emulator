package D5700_Emulator.Instructions

import D5700_Emulator.CPU
import D5700_Emulator.InputHandler
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen

class JumpInstruction (
    private val aaa: Int
): Instruction(){
    override var autoIncrementPC = false
    override fun execute(cpu: CPU, ram: RAM, rom: ROM, screen: Screen, input: InputHandler) {
        if (aaa % 2 != 0) throw IllegalArgumentException("Not a valid address")
        cpu.P = aaa
    }
}