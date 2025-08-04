package D5700_Emulator

import D5700_Emulator.Instructions.Instruction

class CPU {
    val registers = ByteArray(8)
    var P: Int = 0
    var T: Byte = 0
    var A: Int = 0
    var M: Boolean = false
    var halted: Boolean = false

    fun tick() {

    }

    fun executeInstruction(opcode: Byte, operands: ByteArray) {

    }

//    fun loadInstruction(): Instruction {
//        return
//    }

}