#include <iostream>
#include <string>
#include <graalvm/llvm/polyglot.h>

using namespace std;

void *prime(int low, int high);

int main() {
  polyglot_export("prime", prime);
  return 0;
}

void *prime(int low, int high) {
    int i;
    bool is_prime = true;

    string result = "";

    while (low < high) {
      is_prime = true;

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

    return polyglot_from_string(result.c_str(), "ascii");
  }

