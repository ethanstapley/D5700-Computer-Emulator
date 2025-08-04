package D5700_Emulator.Memory

abstract class Memory {
    protected val data = ByteArray(4096)

    open fun read(address: Int): Byte {
        checkAddress(address)
        return data[address]
    }

    open fun checkAddress(address: Int) {
        println("Loading instruction at P = $address")
        if (address < 0 || address >= 4096) {
            throw IllegalArgumentException("Invalid Address")
        }
    }

    open fun write(address: Int, byte: Byte) {
        checkAddress(address)
        data[address] = byte
    }
}