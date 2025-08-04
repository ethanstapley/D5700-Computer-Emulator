import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import D5700_Emulator.*
import D5700_Emulator.Memory.*
import D5700_Emulator.Instructions.*

class InstructionsTest {
    data class Machine(
        val cpu: CPU = CPU(),
        val ram: RAM = RAM(),
        val rom: ROM = ROM(),
        val screen: Screen = Screen()
    )

    // STORE
    @Test
    fun store_sets_register() {
        val (cpu, ram, rom, screen) = Machine()
        StoreInstruction(0, 0xFF.toByte()).execute(cpu, ram, rom, screen)
        assertEquals(0xFF.toByte(), cpu.registers[0])
    }

    // ADD
    @Test
    fun add_basic_and_wraps_mod256() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[0] = 1
        cpu.registers[1] = 2
        AddInstruction(0, 1, 0).execute(cpu, ram, rom, screen)
        assertEquals(3, (cpu.registers[0].toInt() and 0xFF))

        cpu.registers[0] = 250.toByte()
        cpu.registers[1] = 10
        AddInstruction(0, 1, 0).execute(cpu, ram, rom, screen)
        assertEquals(4, (cpu.registers[0].toInt() and 0xFF))
    }

    // SUB
    @Test
    fun sub_basic_and_underflow_mod256() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[0] = 5
        cpu.registers[1] = 7
        SubInstruction(0, 1, 0).execute(cpu, ram, rom, screen)
        assertEquals(254, (cpu.registers[0].toInt() and 0xFF))
    }

    // READ
    @Test
    fun read_from_ram_when_M_is_0() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.M = false
        cpu.A = 123
        ram.write(123, 0x42)
        ReadInstruction(2).execute(cpu, ram, rom, screen)
        assertEquals(0x42, cpu.registers[2].toInt() and 0xFF)
    }

    @Test
    fun read_from_rom_when_M_is_1() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.M = true
        cpu.A = 321
        rom.load(321, 0x7B.toByte())
        ReadInstruction(4).execute(cpu, ram, rom, screen)
        assertEquals(0x7B, cpu.registers[4].toInt() and 0xFF)
    }

    // WRITE
    @Test
    fun write_to_ram_when_M_is_0() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.M = false
        cpu.A = 200
        cpu.registers[3] = 0xAB.toByte()
        WriteInstruction(3).execute(cpu, ram, rom, screen)
        assertEquals(0xAB, ram.read(200).toInt() and 0xFF)
    }

    @Test
    fun write_to_rom_when_M_is_1_should_throw() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.M = true
        cpu.A = 10
        cpu.registers[1] = 0x55
        assertThrows(IllegalStateException::class.java) {
            WriteInstruction(1).execute(cpu, ram, rom, screen)
        }
    }

    // JUMP
    @Test
    fun jump_sets_P_to_even_address() {
        val (cpu, ram, rom, screen) = Machine()
        JumpInstruction(0x1F2).execute(cpu, ram, rom, screen)
        assertEquals(0x1F2, cpu.P)
    }

    @Test
    fun jump_odd_address_throws() {
        val (cpu, ram, rom, screen) = Machine()
        assertThrows(IllegalArgumentException::class.java) {
            JumpInstruction(0x1F1).execute(cpu, ram, rom, screen)
        }
    }

    // READ_KEYBOARD
    @Test
    fun read_keyboard_parses_hex_up_to_two_digits() {
        val (cpu, ram, rom, screen) = Machine()
        System.setIn(ByteArrayInputStream("A\n".toByteArray()))
        ReadKeyboardInstruction(0).execute(cpu, ram, rom, screen)
        assertEquals(0x0A, cpu.registers[0].toInt() and 0xFF)

        System.setIn(ByteArrayInputStream("1F\n".toByteArray()))
        ReadKeyboardInstruction(1).execute(cpu, ram, rom, screen)
        assertEquals(0x1F, cpu.registers[1].toInt() and 0xFF)
    }

    @Test
    fun read_keyboard_empty_becomes_zero() {
        val (cpu, ram, rom, screen) = Machine()
        System.setIn(ByteArrayInputStream("\n".toByteArray()))
        ReadKeyboardInstruction(3).execute(cpu, ram, rom, screen)
        assertEquals(0x00, cpu.registers[3].toInt() and 0xFF)
    }

    @Test
    fun read_keyboard_more_than_two_digits_throws() {
        val (cpu, ram, rom, screen) = Machine()
        System.setIn(ByteArrayInputStream("123\n".toByteArray()))
        assertThrows(IllegalStateException::class.java) {
            ReadKeyboardInstruction(0).execute(cpu, ram, rom, screen)
        }
    }

    // SWITCH_MEMORY
    @Test
    fun switch_memory_toggles_M() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.M = false
        SwitchMemoryInstruction().execute(cpu, ram, rom, screen)
        assertTrue(cpu.M)
        SwitchMemoryInstruction().execute(cpu, ram, rom, screen)
        assertFalse(cpu.M)
    }

    // SKIP_EQUAL
    @Test
    fun skip_equal_advances_P_correctly() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.P = 100
        cpu.registers[1] = 5
        cpu.registers[2] = 5
        SkipEqualInstruction(1, 2).execute(cpu, ram, rom, screen)
        assertEquals(104, cpu.P)

        cpu.P = 100
        cpu.registers[2] = 9
        SkipEqualInstruction(1, 2).execute(cpu, ram, rom, screen)
        assertEquals(102, cpu.P)
    }

    // SKIP_NOT_EQUAL
    @Test
    fun skip_not_equal_advances_P_correctly() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.P = 200
        cpu.registers[1] = 3
        cpu.registers[2] = 9
        SkipNotEqualInstruction(1, 2).execute(cpu, ram, rom, screen)
        assertEquals(204, cpu.P)

        cpu.P = 200
        cpu.registers[2] = 3
        SkipNotEqualInstruction(1, 2).execute(cpu, ram, rom, screen)
        assertEquals(202, cpu.P)
    }

    //SET_A
    @Test
    fun set_a_sets_address() {
        val (cpu, ram, rom, screen) = Machine()
        SetAInstruction(0x255).execute(cpu, ram, rom, screen)
        assertEquals(0x255, cpu.A)
    }

    // SET_T
    @Test
    fun set_t_sets_timer() {
        val (cpu, ram, rom, screen) = Machine()
        SetTInstruction(0x7F.toByte()).execute(cpu, ram, rom, screen)
        assertEquals(0x7F, cpu.T.toInt() and 0xFF)
    }

    // READ_T
    @Test
    fun read_t_moves_timer_into_register() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.T = 0x22
        ReadTInstruction(6).execute(cpu, ram, rom, screen)
        assertEquals(0x22, cpu.registers[6].toInt() and 0xFF)
    }

    //CONVERT_TO_BASE_10
    @Test
    fun convert_to_base10_writes_three_digits_to_ram() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.A = 100
        cpu.registers[2] = 172.toByte()
        ConvertToBaseTenInstruction(2).execute(cpu, ram, rom, screen)
        assertEquals(1, ram.read(100).toInt() and 0xFF)
        assertEquals(7, ram.read(101).toInt() and 0xFF)
        assertEquals(2, ram.read(102).toInt() and 0xFF)
    }

    @Test
    fun convert_to_base10_small_number() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.A = 5
        cpu.registers[0] = 5
        ConvertToBaseTenInstruction(0).execute(cpu, ram, rom, screen)
        assertEquals(0, ram.read(5).toInt() and 0xFF)
        assertEquals(0, ram.read(6).toInt() and 0xFF)
        assertEquals(5, ram.read(7).toInt() and 0xFF)
    }

    // CONVERT_BYTE_TO_ASCII
    @Test
    fun convert_byte_to_ascii_valid_hex_digit() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[0] = 0x0D
        ConvertByteToAsciiInstruction(0, 1).execute(cpu, ram, rom, screen)
        assertEquals('D'.code, cpu.registers[1].toInt() and 0xFF)
    }

    @Test
    fun convert_byte_to_ascii_masks_high_nibble_and_converts_zero() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[3] = 0x10
        ConvertByteToAsciiInstruction(3, 4).execute(cpu, ram, rom, screen)
        assertEquals('0'.code, cpu.registers[4].toInt() and 0xFF)
    }

    @Test
    fun convert_byte_to_ascii_converts_hex_digit_D() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[0] = 0x1D
        ConvertByteToAsciiInstruction(0, 1).execute(cpu, ram, rom, screen)
        assertEquals('D'.code, cpu.registers[1].toInt() and 0xFF)
    }


    // DRAW
    @Test
    fun draw_writes_char_to_screen_ram() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[1] = 'A'.code.toByte()
        cpu.registers[2] = 3
        cpu.registers[3] = 5
        assertDoesNotThrow {
            DrawInstruction(1, 2, 3).execute(cpu, ram, rom, screen)
        }
    }

    @Test
    fun draw_throws_when_char_over_127() {
        val (cpu, ram, rom, screen) = Machine()
        cpu.registers[0] = 200.toByte()
        cpu.registers[2] = 0
        cpu.registers[3] = 0
        assertThrows(IllegalArgumentException::class.java) {
            DrawInstruction(0, 2, 3).execute(cpu, ram, rom, screen)
        }
    }

    // HALT
    @Test
    fun halt_stop_values_change() {
        val (cpu, ram, rom, screen) = Machine()
        HaltInstruction().execute(cpu, ram, rom, screen)
        assertEquals(true, cpu.halted)
    }
}