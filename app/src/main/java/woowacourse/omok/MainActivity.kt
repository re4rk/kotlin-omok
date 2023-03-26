package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.SampleData.generateSampleData
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokPlayerDbHelper
import woowacourse.omok.roomList.RoomListActivity

class MainActivity : AppCompatActivity() {
    val playerDb = OmokPlayerDbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addListenerOnButton()
    }

    private fun addListenerOnButton() {
        val button = findViewById<Button>(R.id.main_button)
        button.setOnClickListener {
            val nameText = findViewById<EditText>(R.id.main_edit_text)
            val player = playerDb.getPlayer(nameText.text.toString()) ?: createPlayer(nameText)

            if (player == null) {
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startRoomListActivity(player)
        }
    }

    private fun createPlayer(nameText: EditText): Player? =
        Player(name = nameText.text.toString(), profile = R.drawable.player_ark)
            .run { playerDb.insert(this) }
            .let { playerDb.getPlayer(it) }

    private fun startRoomListActivity(player: Player) =
        startActivity(
            Intent(this, RoomListActivity::class.java).apply {
                putExtra("player", player)
            },
        )

    override fun onPause() {
        super.onPause()
        generateSampleData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerDb.close()
    }
}
