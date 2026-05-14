// MainActivity.kt
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Состояние: режим просмотра или режим правки
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Секретная кнопка-квадратик (из твоего фото 1000211797.jpg)
        val secretButton = findViewById<ImageView>(R.id.secret_square)
        val btnSave = findViewById<Button>(R.id.btn_save)
        
        // Список всех текстовых полей, которые ты хочешь менять
        val editableFields = listOf(
            findViewById<EditText>(R.id.header_title),   // Заголовок (РОХХАТИ...)
            findViewById<EditText>(R.id.driver_name),    // Имя водителя
            findViewById<EditText>(R.id.car_number),     // Госномер
            findViewById<EditText>(R.id.company_name),   // Название фирмы
            findViewById<EditText>(R.id.odo_start)       // Одометр
        )

        // Секретный клик по квадратику
        secretButton.setOnClickListener {
            isEditMode = true
            btnSave.visibility = View.VISIBLE // Показываем кнопку Сохранить
            for (field in editableFields) {
                field.isEnabled = true // Разрешаем менять текст
                field.setBackgroundResource(android.R.drawable.edit_text) // Показываем рамку правки
            }
            Toast.makeText(this, "Режим редактирования открыт", Toast.LENGTH_SHORT).show()
        }

        // Кнопка Сохранить
        btnSave.setOnClickListener {
            isEditMode = false
            btnSave.visibility = View.GONE // Прячем кнопку Сохранить
            for (field in editableFields) {
                field.isEnabled = false // Закрываем доступ
                field.background = null // Убираем рамки, чтобы выглядело как обычное фото
            }
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
        }
    }
}
