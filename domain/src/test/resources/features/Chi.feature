Feature: 吃牌

  Scenario Outline: 吃1張牌
    Given 自己手牌有 <handTiles>
    When 上家打了 <tile> 自己喊吃
    Then 自己打了一張 <secondTile>
    Then 吃牌成功

    Examples:
      | handTiles                                         | tile | secondTile |
      | "1萬、1萬、2萬、2萬、3萬、4萬、5萬、6萬、7萬、8萬、9萬、9萬、9萬、東風、東風、東風" | "1萬" | "1萬"       |
      | "1萬、1萬、2萬、2萬、3萬、4萬、5萬、6萬、7萬、8萬、9萬、9萬、9萬、東風、東風、東風" | "4萬" | "2萬"       |
      | "1筒、1筒、2筒、2筒、3筒、4筒、5筒、6筒、7筒、8筒、9筒、9筒、9筒、東風、東風、東風" | "7筒" | "東風"       |
      | "1筒、1筒、2筒、2筒、3筒、4筒、5筒、6筒、7筒、8筒、9筒、9筒、9筒、東風、東風、東風" | "9筒" | "1筒"       |
      | "1條、1條、2條、2條、3條、4條、5條、6條、7條、8條、9條、9條、9條、東風、東風、東風" | "7條" | "東風"       |
      | "1條、1條、2條、2條、3條、4條、5條、6條、7條、8條、9條、9條、9條、東風、東風、東風" | "9條" | "東風"       |
