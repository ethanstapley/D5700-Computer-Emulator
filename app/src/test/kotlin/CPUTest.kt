import D5700_Emulator.CPU
import D5700_Emulator.Memory.RAM
import D5700_Emulator.Memory.ROM
import D5700_Emulator.Screen
import D5700_Emulator.InstructionFactory
import D5700_Emulator.Instructions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CPUTest {
    data class Machine(
        val cpu: CPU = CPU(),
        val ram: RAM = RAM(),
        val rom: ROM = ROM(),
        val screen: Screen = Screen()
    )

    @Test
    fun registers_default_zero() {
        val cpu = CPU()
        assertTrue(cpu.registers.all { it == 0.toByte() })
    }

    @Test
    fun tick_executes_and_increments_P() {
        val (cpu, ram, rom, screen) = Machine()
        val factory = InstructionFactory()

        rom.load(0, 0x30.toByte()) // ReadInstruction r0
        rom.load(1, 0x00.toByte())
        ram.write(0, 42)

        cpu.tick(ram, rom, screen, factory)
        assertEquals(2, cpu.P)
    }

    @Test
    fun tick_halts_when_p_exceeds_memory() {
        val (cpu, ram, rom, screen) = Machine()
        val factory = InstructionFactory()
        cpu.P = 4094
        cpu.tick(ram, rom, screen, factory)
        assertTrue(cpu.halted)
    }

    @Test
    fun load_instruction_test() {
        val (cpu, ram, rom, screen) = Machine()
        val factory = InstructionFactory()
        rom.load(0, 1)
        rom.load(1, 1)
        assertNotNull(cpu.loadInstruction(ram, rom, factory))
    }

}
