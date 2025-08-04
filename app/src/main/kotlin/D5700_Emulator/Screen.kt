package D5700_Emulator

class Screen {
    private val data = ByteArray(64)

    fun draw(row: Int, col: Int, value: Byte) {
        if ((row < 0 || row > 7) || (col < 0 || col > 7)) throw IllegalArgumentException("Row and Column must be be 0-7")
        data[(row * 8) + col] = value
    }

    fun render() {
        for(row in 0..7) {
            for (col in 0..7) {
                val value = data[(row * 8) + col].toInt()
                print(value.toChar())
            }
            println("")
        }
    }
}