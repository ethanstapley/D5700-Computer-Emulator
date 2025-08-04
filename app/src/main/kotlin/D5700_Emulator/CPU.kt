package D5700_Emulator

import D5700_Emulator.Instructions.Instruction
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM

class CPU {
    val registers = ByteArray(8)
    var P: Int = 0
    var T: Byte = 0
    var A: Int = 0
    var M: Boolean = false
    var halted: Boolean = false

    fun tick(ram: RAM, rom: ROM, screen: Screen, factory: InstructionFactory) {
        if (halted) return

        val instruction = loadInstruction(ram, rom, factory)
        instruction.execute(this, ram, rom, screen)

        if (instruction.autoIncrementPC) {
            P += 2
        }
    }


    fun loadInstruction(ram: RAM, rom: ROM, factory: InstructionFactory): Instruction {
        val memory = if (M) rom else ram
        val b1 = memory.read(P)
        val b2 = memory.read(P + 1)
        return factory.getInstruction(b1, b2)
    }


}