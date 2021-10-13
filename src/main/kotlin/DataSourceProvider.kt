import java.util.HashMap
import org.apache.derby.jdbc.EmbeddedDataSource
import kotlin.Throws
import java.io.IOException
import java.lang.Exception
import java.util.Properties

/**
 * Провайдер базы данных.
 */
class DataSourceProvider {
    /**
     * Параметры конфигурации.
     */
    private val properties: MutableMap<String, String> = HashMap()

    /**
     * Data source.
     */
    val dataSource by lazy{
        val dataSource = EmbeddedDataSource()
        dataSource.databaseName = properties["database.name"]
        val username = properties["database.username"]
        val password = properties["database.password"]
        if (username != null && !username.isEmpty()
            && password != null && !password.isEmpty()
        ) {
            dataSource.user = username
            dataSource.password = password
        }
        dataSource.createDatabase = "create"
        dataSource
    }

    /**
     * Загружает параметры конфигурации.
     *
     * @throws IOException ошибка при попытке загрузки параметров
     */
    @Throws(IOException::class)
    private fun loadProperties() {
        val properties = Properties()
        try {
            properties.load(
                Thread.currentThread().contextClassLoader.getResourceAsStream("app.properties")
            )
            for ((key, value) in properties) {
                this.properties[key as String] = value as String
            }
        } catch (e: Exception) {
            println("Error occurred during loading properties")
            throw e
        }
    }


    init {
        loadProperties()
    }
}

