package D5700_Emulator

import D5700_Emulator.Instructions.Instruction
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM

class CPU {
    val registers = ByteArray(8)
    var P: Int = 0
    var T: Byte = 0
    var A: Int = 0
    var M: Boolean = true
    var halted: Boolean = false

    fun tick(ram: RAM, rom: ROM, screen: Screen, factory: InstructionFactory) {
        val instruction = loadInstruction(ram, rom, factory)
        instruction.execute(this, ram, rom, screen)

        if (instruction.autoIncrementPC) {
            P += 2
        }
        if (P >= 4095) {
            halted = true
            Timer.stop()
        }
    }


    fun loadInstruction(ram: RAM, rom: ROM, factory: InstructionFactory): Instruction {
        val b1 = rom.read(P)
        val b2 = rom.read(P + 1)
        return factory.getInstruction(b1, b2)
    }

    fun startTimer() {
        Timer.start(this)
    }
}