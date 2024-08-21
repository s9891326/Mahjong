# 麻將 - Mahjong
* 台灣十六張麻將玩法

## 使用技術與框架
1. Tech Stack：
    * 後端語言：Java(JDK17)
        * Framework: Spring boot 3
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

## GitHub Flow
1. 團隊任一人皆可依據功能建立 issue，並依據需求類型添加 Lebel 及指派人員處理
2. 分支的建立規則：
    *  分支名稱為 dev 和左斜線與 issue 編號結合
    *  Pattern: `dev/${issueId}`，範例： dev/1
3. 本地開發測試完成之後，並 push 本地分支到遠端
4. 發出 Pull Request，於 commit 裡填寫 issue link，請求 Reviewer Review Code
5. 需有 2 人 Review PR
    * Mahjong Team members could review the PR.
6. 待 Reviewer 均確認無誤後，通知開發人員已確認完成
7. 開發人員將此 PR 合進 Master 並 closed issue
