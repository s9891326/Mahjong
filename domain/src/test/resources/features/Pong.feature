Feature: 碰牌

  Scenario Outline: 碰1張牌
    Given 自己手牌有 <handTiles>
    When <player> 打了 <tile> 自己喊碰
    Then 自己打了一張 <secondTile>
    Then 碰牌成功

    Examples:
      | handTiles                                         | player | tile | secondTile |
      | "1萬、1萬、2萬、3萬、3萬、4萬、5萬、6萬、7萬、8萬、9萬、9萬、9萬、東風、東風、東風" | "上家"   | "9萬" | "2萬"       |
      | "1萬、1萬、2萬、3萬、3萬、4萬、5萬、6萬、7萬、8萬、9萬、9萬、9萬、東風、東風、東風" | "下家"   | "3萬" | "2萬"       |
      | "1萬、1萬、2萬、3萬、3萬、4萬、5萬、6萬、7萬、8萬、9萬、9萬、9萬、東風、東風、西風" | "對家"   | "東風" | "西風"       |

