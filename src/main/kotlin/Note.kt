import java.util.Scanner

class Note(
    val name: String,
    val content: String
){
    companion object {
        fun showNoteMenu(archive: Archive) {
            println("Список заметок:")
            println("0. Создать заметку:")
            if (archive.notes.isEmpty()){
                println("1. Выход")
            } else {
                for ((index, note) in archive.notes.withIndex()) {
                    println("${index + 1}. ${note.name}")
                }
                println("${archive.notes.size + 1}. Выход")
            }
            noteAction(::setValue, archive, ::createNote, Archive::showArchivesList, ::openNote, ::noteError)
        }

        private fun noteAction(setValue:() -> Int, archive: Archive, onCreate:(Archive) -> Unit, showArchiveList:() -> Unit, openNote:(Note, Archive) -> Unit, onError:(Archive) -> Unit) {
            val value = setValue()
            if(value == 0){
                onCreate.invoke(archive)
            }
            if (archive.notes.isEmpty()){
                when(value){
                    1 -> showArchiveList()
                    else -> onError.invoke(archive)
                }
            } else if(archive.notes.size == 1) {
                when (value) {
                    1 -> openNote.invoke(archive.notes[0], archive)
                    2 -> showArchiveList()
                    else -> onError.invoke(archive)
                }
            } else {
                val firstIndex = 1
                val lastIndex = archive.notes.size

                when(value){
                    lastIndex + 1 -> showArchiveList()
                    in firstIndex..lastIndex -> {
                        for ((index, note) in archive.notes.withIndex()) {
                            if (index + 1 == value) {
                                openNote.invoke(note, archive)
                            }
                        }
                    }
                    else -> onError.invoke(archive)
                }
            }



        }

        fun createNote(archive: Archive){
            val scanner = Scanner(System.`in`)
            var noteName: String
            var contentText: String

            while (true) {
                println("Введите имя заметки:")
                noteName = scanner.nextLine()
                if (noteName.trim().isEmpty()) {
                    println("Имя заметки не может быть пустым. Введите еще раз.")
                } else {
                    break
                }
            }

            while (true) {
                println("Введите содержание заметки:")
                contentText = scanner.nextLine()
                if (contentText.trim().isEmpty()) {
                    println("Содержание заметки не может быть пустым. Введите еще раз.")
                } else {
                    break
                }
            }
            archive.notes.add(Note(noteName, contentText))
            showNoteMenu(archive)
        }

        fun openNote(note: Note, archiveBack: Archive){
            println("${note.name}: ${note.content}")
            println("=====================")
            println("1. Выход")
            backToListOfNotes(archiveBack)
        }

        private fun backToListOfNotes(archive: Archive){
            when(setValue()){
                1 -> showNoteMenu(archive)
                else -> while(true)
                    backToListOfNotes(archive)
            }
        }

        private fun noteError(archive: Archive){
            println("Данной функции не существует, попробуйте еще раз")
            noteAction(::setValue, archive, ::createNote, Archive::showArchivesList, ::openNote, ::noteError)
        }
    }
}
