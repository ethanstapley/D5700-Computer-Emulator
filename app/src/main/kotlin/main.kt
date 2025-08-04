import D5700_Emulator.D5700Emulator

fun main() {
    print("Give the file path of the program you want to run: ")
    val filePath = readln()
    D5700Emulator().start(filePath)
}