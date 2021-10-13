fun main(args: Array<String>) {

    val repository = Repository()
    repository.initDB()
    repository.addMore()
    repository.addMore()
    repository.addMore()
    println(repository.getAll())
}