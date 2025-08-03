package D5700_Emulator.Memory

class RAM: Memory() {
    fun write(address: Int, byte: Byte) {
        checkAddress(address)
        data[address] = byte
    }
}