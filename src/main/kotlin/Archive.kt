import java.util.Scanner

class Archive(
    val name: String,
    val notes: MutableList<Note> = mutableListOf()
){
    companion object{
        private var archives: MutableList<Archive> = mutableListOf()

        fun showArchivesList(){
            println("Список архивов:")
            println("0. Создать Архив")
            if(archives.size == 0){
                println("1. Выход")
            } else {
                for ((index, archive) in archives.withIndex()) {
                    println("${index + 1}. ${archive.name}")
                }
                println("${archives.size + 1}. Выход")
            }
            archiveAction(::setValue, ::createArchive, ::openArchive)
        }

        private fun archiveAction(setValue:() -> Int, onCreate:() -> Unit, openArchive:(Archive) -> Unit){
            when(val value = setValue()){
                0 -> onCreate()
                archives.size + 1 -> println("Завершение программы")
                else -> {
                    for ((index ,archive) in archives.withIndex()){
                        if(index + 1 == value){
                            openArchive.invoke(archive)
                        }
                    }
                }
            }
        }

        fun createArchive(){
            val scanner = Scanner(System.`in`)
            var archiveName: String

            while (true) {
                println("Введите имя архива:")
                archiveName = scanner.nextLine()

                if (archiveName.trim().isEmpty())
                    println("Имя архива не может быть пустым. Введите еще раз.")
                else
                    break
            }
            archives.add(Archive(archiveName, mutableListOf()))
            showArchivesList()
        }

        private fun openArchive(archive: Archive){
            if (archive.notes.isEmpty()) {
                println("Архив пуст.")
                println("0. Создать заметку")
                println("1. Выход")
                noteCreateOrShowArchivesList(archive)
            } else {
                Note.showNoteMenu(archive)
            }
        }

        private fun noteCreateOrShowArchivesList(archive: Archive) {
            while (true) {
                val value = setValue()
                when (value) {
                    0 -> {
                        Note.createNote(archive)
                        break
                    }
                    1 -> {
                        showArchivesList()
                        break
                    }
                    else -> {
                        println("Введите допустимое значение")
                    }
                }
            }
        }
    }
}







