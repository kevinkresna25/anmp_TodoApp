import android.content.Context
import com.ubaya.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"

fun buildDb(context: Context): TodoDatabase {
    val db = TodoDatabase.buildDatabase(context)
    return db
}
