package com.ll;

public class Calc {
  public static int run(String exp) { // 10 + (10 + 5)
    exp = exp.trim();// 값을 넣었을때 그 값을 그대로 반환받을수있게함
    exp = stripOuterBracket(exp);//괄호가 있는 exp 값

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp);//exp 가 공백을 포함하지않으면 Integer.parseInt(exp)을 리턴
    boolean needToMultiply = exp.contains(" * "); //boolean 타입의 needToMultiply 변수는 "*"을 포함하면 true 못하면 false 를 반환
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");//boolean 타입의 needToPlus 변수는 "+"을 포함하거나 "-"를 포함하면 true 못하면 false 를 반환
    boolean needToCompound = needToMultiply && needToPlus;//boolean 타입의 needToCompound 변수는  needToPlu 와 needToMultiply 가 모두 true 여야 true 반환 하나라도 false 일시 false 반환
    boolean needToSplit = exp.contains("(") || exp.contains(")");//boolean 타입의 needToSplit 변수는"("를포함하거나")"를 포함하면 true 못하면 false

    if (needToSplit) {  // 800 + (10 + 5) //needToSplit 이 true 면 실행

      int splitPointIndex = findSplitPointIndex(exp);//변수를 만든다

      String firstExp = exp.substring(0, splitPointIndex);//string 타입의 firstExp는
      String secondExp = exp.substring(splitPointIndex + 1);

      char operator = exp.charAt(splitPointIndex);//문자형 operator 는 splitPointIndex의 위치할수있다


      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);// exp 는 Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp)

      return Calc.run(exp);// Calc.run(exp)을 리턴한다
    } else if (needToCompound) //needTosplit 이 false일때 else if 를 실행하고 needToCompound 이 true인지 확인해서 true면 아래 실행문을 실행한다
    {
      String[] bits = exp.split(" \\+ ");//+를 기준으로 나눈다

      return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO
    }
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-");
      String[] bits = exp.split(" \\+ ");
      int sum = 0;
      for (int i = 0; i < bits.length; i++) {
        sum += Integer.parseInt(bits[i]);
      }
      return sum;
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");
      int rs = 1;
      for (int i = 0; i < bits.length; i++) {
        rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }
    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }

  private static int findSplitPointIndexBy(String exp, char findChar) {
    int bracketCount = 0;

    for (int i = 0; i < exp.length(); i++) {
      char c = exp.charAt(i);

      if (c == '(') {
        bracketCount++;
      } else if (c == ')') {
        bracketCount--;
      } else if (c == findChar) {
        if (bracketCount == 0) return i;
      }
    }
    return -1;
  }

  private static int findSplitPointIndex(String exp) {
    int index = findSplitPointIndexBy(exp, '+');

    if (index >= 0) return index;

    return findSplitPointIndexBy(exp, '*');
  }

  private static String stripOuterBracket(String exp) {
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }
    if (outerBracketCount == 0) return exp;
    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
  }
}