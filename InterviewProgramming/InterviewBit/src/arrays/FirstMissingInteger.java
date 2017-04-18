package arrays;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/*Given an unsorted integer array, find the first missing positive integer.

Example:
Given [1,2,0] return 3,
[3,4,-1,1] return 2,
[-8, -7, -6] returns 1
Your algorithm should run in O(n) time and use constant space.*/

public class FirstMissingInteger {
  public int firstMissingPositive(ArrayList<Integer> a) {
    Optional<Integer> maxElement = a.stream().max(Comparator.naturalOrder());
    if (maxElement.get() < 0)
      return 1;

    int[] temp = new int[maxElement.get() + 1];
    a.forEach(value -> {
      if (value >= 0) {
        temp[value] = value;
      }
    });

    int i;
    for (i = 1; i < temp.length; i++) {
      if (temp[i] != i) {
        return i;
      }
    }
    return i;
  }
}
