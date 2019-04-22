Feature: File reader

  Scenario Outline: The information in the files read by the service can be found or not found on the website
    Given the service reads files from directory testfiles
    When the service is evoked
    And registration numbers from each file with <name> is entered into the website inturn
    Then the website details for registration <plate> will be <foundOrNot> the exact vehicle make <make> with colour <colour>
    And the website details for registration <plate> with make of <make> with a colour of <colour>  will <matchOrNot> the service with make of <make1> with a colour of <colour1>

    Examples:
      | name       | plate   | foundOrNot | make    | colour | matchOrNot | make1   | colour1 |
      | file1.csv  | YS59ABZ | found      | BMW     | BLUE   | match      | BMW     | BLUE    |
      | file2.csv  | GX18HBL | found      | AUDI    | BLACK  | match      | AUDI    | BLACK   |
      | file3.csv  | V765DPR | not found  | HYUNDAI | SILVER | not match  | HYUNDAI | SILVER  |
      | file4.csv  | GJ13EBK | found      | HONDA   | RED    | match      | HONDA   | RED     |
      | file5.csv  | LA12GZC | found      | NISSAN  | BLUE   | match      | NISSAN  | BLUE    |
      | file6.csv  | GJ13EBX | found      | CITROEN | WHITE  | match      | CITROEN | WHITE   |
      | file7.csv  | YS59ABZ | found      | BMW     | BLUE   | match      | BMW     | BLUE    |
      | file8.csv  | P1LOT   | not found  | BMW     | BLACK  | not match  | BMW     | BLACK   |
      | file9.csv  | PK66PFF | found      | MINI    | BLUE   | match      | MINI    | BLUE    |
      | file10.csv | RK66PFF | found      | AUDI    | WHITE  | match      | AUDI    | WHITE   |

  Scenario: The user enters a correctly formated registration plate
    Given I am a user of the website
    When I enter a correct registration plate of YS59ABZ
    And press continue
    Then I will receive the correct make as BMW and colour of car as BLUE

  Scenario: The user enters a invalid registration plate
    Given I am a user of the website
    When I enter a correctly formatted invalid registration plate of V765DPR
    And press continue
    Then I will be taken to an error page

  Scenario: The user enters an incorrectly formatted registration plate
    Given I am a user of the website
    When I enter a incorrectly formatted registraton plate of AB343467
    And press continue
    Then I will receive an error messsage on the same page

