import java.util.Scanner

fun main(args: Array<String>) {
    Archive.showArchivesList()
}

fun setValue(): Int {
    val scanner = Scanner(System.`in`)

    println("Введите цифру выбранного действия")
    var input = scanner.nextLine()

    while (true){
        input.trim()
        val number:Int? = input.toIntOrNull()

        if(number != null){
            break
        } else {
            println("Ошибка! Введите числовое значение.")
            input = scanner.nextLine()
        }
    }
    return input.toInt()
}




