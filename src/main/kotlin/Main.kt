import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import java.sql.SQLException
import java.time.LocalDate
import java.util.*

fun main(args: Array<String>) {
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

    val database = Database.connect(dataSource)


    database.insert(Employees) {
        set(it.name, "jerry")
    }

    for (row in database.from(Employees).select()) {
        println(row[Employees.name])
    }
}