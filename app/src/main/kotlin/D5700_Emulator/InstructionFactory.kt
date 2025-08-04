package D5700_Emulator

import D5700_Emulator.Instructions.*

class InstructionFactory {
    fun getInstruction(firstByte: Byte, secondByte: Byte): Instruction {
        val b1 = firstByte.toInt() and 0xFF
        val b2 = secondByte.toInt() and 0xFF

        val opcode = (b1 shr 4) and 0xF
        val rx = b1 and 0xF
        val ry = (b2 shr 4) and 0xF
        val rz = b2 and 0xF
        val aaa = (rx shl 8) or b2

        return when (opcode) {
            0 -> StoreInstruction(rx, secondByte)
            1 -> AddInstruction(rx, ry, rz)
            2 -> SubInstruction(rx, ry, rz)
            3 -> ReadInstruction(rx)
            4 -> WriteInstruction(rx)
            5 -> JumpInstruction(aaa)
            6 -> ReadKeyboardInstruction(rx)
            7 -> SwitchMemoryInstruction()
            8 -> SkipEqualInstruction(rx, ry)
            9 -> SkipNotEqualInstruction(rx, ry)
            10 -> SetAInstruction(aaa)
            11 -> SetTInstruction(secondByte)
            12 -> ReadTInstruction(rx)
            13 -> ConvertToBaseTenInstruction(rx)
            14 -> ConvertByteToAsciiInstruction(rx, ry)
            15 -> DrawInstruction(rx, ry, rz)
            else -> throw IllegalArgumentException("Unknown Opcode")
        }
    }
}