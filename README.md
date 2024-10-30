# 麻將 - Mahjong

* 台灣十六張麻將玩法

## 使用技術與框架

1. Tech Stack：
    * 後端語言：Java(JDK17)
        * Framework: SpringBoot3
        * package: Maven
        * DB： MongoDB
    * 前端：React
2. Practice Stack：
    * Test-Driven Development
    * Domain-Driven Design
    * Clean Architecture
3. Analysis & Design：
    * Event Storming
    * Example Mapping
    * OOA/D
4. DevOps：
    * Github Action
5. Development：
    * 待定

## Practice Stack

- Document Driven design -- 落實文件驅動開發，清楚明瞭的規則文件。
- Event storming -- 同步認知，並清楚了解遊戲最重要的主要功能開發。
- Example mapping -- 將各種需求與規則藉由User story依依列舉，並作為未來TDD開發的測試的參考範本。
- OOAD -- 使用UML，需求分析並設計領域模型。
- Walking skeleton -- 已最簡可行產品 (minimum viable product, MVP),建立最初的版本。
- ATDD -- 驗收驅動開發，已使用者功能使用的角度寫e2e測試。
- TDD -- 測試驅動開發，先寫測試再開發。
- MVC -- 三層式架構
- Clean Architecture -- 乾淨架構

## Maven

- 修正module沒有正常載入對應的package，如果配置是正確的，能用下面的語法來重新下載，不使用緩存的
  `mvn clean install -U`
