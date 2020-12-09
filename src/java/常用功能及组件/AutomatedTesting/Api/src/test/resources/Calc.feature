Feature: 计算器自动化测试

  简介：这是个计算器程序，当输入两个数后，会计算出其结果
  一下会用数据驱动的方法来验证，数据有：正常、异常、边界值等

  Background:
    Given 打开计算器

  Scenario Outline:除法测试
    When 输入<a>除<b>，并计算
    Then 显示结果为:<expected>

    Examples:
      | a    | b    | expected |
      | 6    | 2    | 3        |
      | -6   | -2   | 3        |
      | 1    | 0    | Error    |
      | null | null | Error    |


  Scenario Outline:加法测试
    When 输入<a>加<b>，并计算
    Then 显示结果为:<expected>

    Examples:
      | a    | b    | expected |
      | 6    | 2    | 8        |
      | -6   | -2   | -8       |
      | -6   | 2    | -4       |
      | null | null | Error    |