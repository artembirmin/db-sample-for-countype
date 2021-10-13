import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import java.sql.SQLException
import java.util.*
import kotlin.random.Random

class Repository {

     lateinit var database:Database

    fun initDB(): String{
        val dataSource = DataSourceProvider().dataSource
        try {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    val databaseMetadata = connection.metaData
                    val resultSet = databaseMetadata.getTables(
                        null,
                        null,  // Несмотря на то, что мы создаем таблицу в нижнем регистре (и дальше к ней так же обращаемся),
                        // поиск мы осуществляем в верхнем. Такие вот приколы
                        "t_employee".uppercase(Locale.getDefault()),
                        arrayOf("TABLE")
                    )
                    if (resultSet.next()) {
                        println("Table has already been initialized")
                    } else {
                        statement.executeUpdate(
                            """create table t_employee(
                                    id integer,
                                    name varchar(128)
                                    )"""
                        )
                        println("Table was successfully initialized")
                    }
                }
            }
        } catch (e: SQLException) {
            println("Error occurred during table initializing: " + e.message)
        } finally {
            println("===========================================")
        }

        database = Database.connect(dataSource)


        database.insert(Employees) {
            set(it.name, "jerry")
        }
        var string: String = ""
        for (row in database.from(Employees).select()) {
            string = buildString {  row[Employees.name]}
        }
        return string
    }

    fun addMore(){
        database.insert(Employees) {
            set(it.name, "jerry ${Random(47).nextInt()}")
        }
    }

    fun getAll(): String{
        var string: String = ""
        for (row in database.from(Employees).select()) {
            string = string.plus(row[Employees.name]) + "\n"
        }
        return string
    }

}