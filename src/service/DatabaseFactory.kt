package id.panicLabs.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import id.panicLabs.data.todo.Todos
import id.panicLabs.data.user.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())

        transaction {
            create(Users)
            create(Todos)

            Users.insert {
                it[name] = "Al"
                it[surname] = "Musthofa"
                it[birthDate] = DateTime.parse("1990-01-15")
            }
        }
    }

    private fun hikari(): HikariDataSource {
        return HikariDataSource(HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}