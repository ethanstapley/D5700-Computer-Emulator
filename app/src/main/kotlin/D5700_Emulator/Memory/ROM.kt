package D5700_Emulator.Memory

class ROM: Memory() {
    override fun write(address: Int, byte: Byte) {
        throw IllegalStateException("This is not a writeable ROM")
    }

    fun load(address: Int, byte: Byte) {
        super.write(address, byte)
    }
}