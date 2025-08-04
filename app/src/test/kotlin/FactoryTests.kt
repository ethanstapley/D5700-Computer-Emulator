import D5700_Emulator.CPU
import D5700_Emulator.InstructionFactory
import D5700_Emulator.Instructions.AddInstruction
import D5700_Emulator.Instructions.JumpInstruction
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FactoryTests {
    data class Computer(val cpu: CPU, val ram: RAM, val rom: ROM, val screen: Screen)

    private fun machine(): Computer {
        val cpu = CPU()
        val ram = RAM()
        val rom = ROM()
        val screen = Screen()
        return Computer(cpu, ram, rom, screen)
    }

    @Test
    fun factory_decodes_add() {
        val f = InstructionFactory()
        val instruction = f.getInstruction(0x10.toByte(), 0x10.toByte())
        assertTrue(instruction is AddInstruction)
        val (cpu, ram, rom, screen) = machine()
        cpu.registers[0] = 5
        cpu.registers[1] = 6
        instruction.execute(cpu, ram, rom, screen)
        assertEquals(11, cpu.registers[0].toInt() and 0xFF)
    }

    @Test
    fun factory_decodes_jump_12bit_address() {
        val f = InstructionFactory()
        val instruction = f.getInstruction(0x5A.toByte(), 0x2C.toByte())
        assertTrue(instruction is JumpInstruction)
        val (cpu, ram, rom, screen) = machine()
        instruction.execute(cpu, ram, rom, screen)
        assertEquals(0xA2C, cpu.P)
    }
}