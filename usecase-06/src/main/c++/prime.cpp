#include <iostream>
#include <string>
using namespace std;

string prime(int low, int high);

int main() {
  int low = 0;
  int high = 50;
  cout << "C++: Prime numbers between " << low << " and " << high << " are: " + prime(low, high) + '\n';
  return 0;
}

string prime(int low, int high) {
  int i;
  bool is_prime = true;

  string result = "";

  while (low < high) {
    is_prime = true;

    // 0 and 1 are not prime numbers
    if (low == 0 || low == 1) {
      is_prime = false;
    }

    for (i = 2; i <= low/2; ++i) {
      if (low % i == 0) {
        is_prime = false;
        break;
      }
    }

    if (is_prime) {
      result = result + std::to_string(low) + " ";
    }

    ++low;
  }

  return result;
}

