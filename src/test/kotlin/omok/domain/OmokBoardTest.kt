package omok.domain

import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.WhiteStoneState
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class OmokBoardTest {
    @Test
    fun `각 좌표들에는 StoneState가 있다`() {
        val omokBoard = OmokBoard()
        assertThat(omokBoard.values).allMatch { it is EmptyStoneState }
    }

    @Test
    fun `오목판에 착수 할 수 있다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, BlackStoneState)
        assertThat(omokBoard[point]).isEqualTo(BlackStoneState)
    }

    @Test
    fun `오목판에 같은 곳에 착수 할 수 없다`() {
        var omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard = omokBoard.placeStone(point, BlackStoneState)
        assertThrows<IllegalArgumentException> { omokBoard.placeStone(point, WhiteStoneState) }
    }

    @Test
    fun `오목의 방어적 복사가 된다`() {
        val omokBoard = OmokBoard()
        val point = OmokPoint(XCoordinate('A'), YCoordinate(1))

        omokBoard.placeStone(point, BlackStoneState)
        assertDoesNotThrow { omokBoard.placeStone(point, WhiteStoneState) }
    }
}
