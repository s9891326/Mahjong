Feature: 補花

#  Given
#  牌局起始階段，自己摸到🀢 🀣 🀤 🀥 🀦  🀧  🀨  🀩任一張
#  亮出花牌（系統）
#  When
#  摸一張槓尾牌
#  Then
#  補花成功
#
#
#  Post-Condition
#  若摸到花牌，則需等到四位玩家該輪都補花完成，再換自己補花

  Scenario Outline: 補一張花
    Given 自己手牌有 <handTiles>
    When 輪到自己補花，補到了 <tile>
    Then 補花成功，手牌要 <handTilesSize>，門前要 <doorFrontSize>

    Examples:
      | handTiles                                     | tile          | handTilesSize | doorFrontSize |
      | "1萬、1萬、2萬、2萬、3萬、3萬、4萬、4萬、5萬、6萬、7萬、7萬、梅、蘭、竹、菊" | "東風、東風、東風、北風" | 16            | 4             |
      | "1萬、1萬、2萬、2萬、3萬、3萬、4萬、4萬、5萬、6萬、7萬、7萬、春、夏、秋、冬" | "東風、東風、東風、北風" | 16            | 4             |