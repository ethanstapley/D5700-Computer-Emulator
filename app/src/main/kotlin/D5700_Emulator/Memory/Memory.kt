package D5700_Emulator.Memory

abstract class Memory {
    protected val data = ByteArray(4096)

    fun read(address: Int): Byte {
        checkAddress(address)
        return data[address]
    }

    fun checkAddress(address: Int) {
        if (address < 0 || address >= 4096) {
            throw IllegalArgumentException("Invalid Address")
        }
    }

}