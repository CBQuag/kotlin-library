import java.time.LocalDate

class Book(val title: String, val author: String, val isbn: String) {
    var isCheckedOut: Boolean = false
    var dueDate: LocalDate? = null
}

class Library {
    val books: MutableList<Book> = mutableListOf()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun checkOutBook(isbn: String) {
        val book = findBookByISBN(isbn)
        if (book != null && !book.isCheckedOut) {
            book.isCheckedOut = true
            book.dueDate = LocalDate.now().plusDays(14)
            println("Checked out: ${book.title}")
        } else {
            println("Book not available for checkout or does not exist.")
        }
    }

    fun checkInBook(isbn: String) {
        val book = findBookByISBN(isbn)
        if (book != null && book.isCheckedOut) {
            book.isCheckedOut = false
            book.dueDate = null
            println("Checked in: ${book.title}")
        } else {
            println("Book cannot be checked in or does not exist.")
        }
    }

    private fun findBookByISBN(isbn: String): Book? {
        return books.find { it.isbn == isbn }
    }

    fun listBooks() {
        if (books.isEmpty()) {
            println("No books in the library.")
            return
        }

        println("Books in the library:")
        books.forEachIndexed { index, book ->
            val status = if (book.isCheckedOut) "Checked Out (Due by: ${book.dueDate})" else "Available"
            println("${index + 1}. ${book.title} by ${book.author} - ISBN: ${book.isbn} ($status)")
        }
    }
}

fun main() {
    val library = Library()

    val book1 = Book("Night on the Galactic Railroad", "Kenji Miyazawa", "1")
    val book2 = Book("Kokoro", "Natsume Soseki", "2")
    val book3 = Book("No Longer Human", "Osamu Dazai", "3")

    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)


    while (true) {
        println("\nLibrary Menu:")
        println("1. List Books")
        println("2. Check Out Book")
        println("3. Check In Book")
        println("4. Add New Book")
        println("5. Exit")

        print("Enter your choice: ")
        val choice = readLine()?.toIntOrNull()

        when (choice) {
            1 -> library.listBooks()
            2 -> {
                print("Enter ISBN to check out: ")
                val isbn = readLine()
                isbn?.let { library.checkOutBook(it) }
            }
            3 -> {
                print("Enter ISBN to check in: ")
                val isbn = readLine()
                isbn?.let { library.checkInBook(it) }
            }
            4-> {
                print("Enter Title: ")
                val title = readLine()
                print("Enter Author: ")
                val author = readLine()
                library.addBook(Book("$title", "$author", "${library.books.size+1}"))
            }
            5 -> {
                println("Exiting the library system.")
                return
            }
            else -> println("Invalid choice. Please select a valid option.")
        }
    }
}
