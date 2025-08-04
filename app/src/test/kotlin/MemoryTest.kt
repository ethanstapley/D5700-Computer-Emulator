import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import D5700_Emulator.Memory.*

class MemoryTest {

    @Test
    fun ram_read_write_ok() {
        val ram = RAM()
        ram.write(0, 0x12)
        ram.write(4095, 0xAB.toByte())
        assertEquals(0x12, ram.read(0).toInt() and 0xFF)
        assertEquals(0xAB, ram.read(4095).toInt() and 0xFF)
    }

    @Test
    fun ram_out_of_bounds_throws() {
        val ram = RAM()
        assertThrows(IllegalArgumentException::class.java) { ram.read(4096) }
        assertThrows(IllegalArgumentException::class.java) { ram.write(4096, 0) }
        assertThrows(IllegalArgumentException::class.java) { ram.read(-1) }
    }

    @Test
    fun rom_load_allows_initialization_but_write_throws() {
        val rom = ROM()
        rom.load(0, 0x7F.toByte())
        assertEquals(0x7F, rom.read(0).toInt() and 0xFF)
        assertThrows(IllegalStateException::class.java) { rom.write(0, 0x01) }
    }
}
