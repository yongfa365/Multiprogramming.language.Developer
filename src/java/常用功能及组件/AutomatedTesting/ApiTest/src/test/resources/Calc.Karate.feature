Feature: 计算器自动化测试

  这是个计算器程序，当输入两个数后，会计算出其结果
  一下会用数据驱动的方法来验证，数据有：正常、异常、边界值等

  Background:
    * url "http://localhost:8080/"


  Scenario Outline:除法测试 <a>/<b> == <expected>
    Given path "/calc/divide/","<a>","<b>"
    When method get
    And match responseStatus == "<expected>" == "Error" ? 500 :200
    And if ("<expected>" == "Error") karate.abort();
    And match response == "<expected>"

    Examples:
      | a    | b    | expected |
      | 6    | 2    | 3        |
      | -6   | -2   | 3        |
      | 1    | 0    | Error    |
      | null | null | Error    |


  Scenario Outline:加法测试 <a>+<b> == <expected>
    Given path "/calc/add/","<a>","<b>"
    When method get
    And match responseStatus == "<expected>" == "Error" ? 500 :200
    And if ("<expected>" == "Error") karate.abort();
    And match response == "<expected>"

    Examples:
      | a    | b    | expected |
      | 6    | 2    | 8        |
      | -6   | -2   | -8       |
      | -6   | 2    | -4       |
      | null | null | Error    |
