#include <stdio.h>
#include <assert.h>

// Public
int main(int argc, char **argv) {

    long n = strtol(argv[1], NULL, 10);

    int fibonacci[n];

    // Call private method
    calculate(fibonacci, n);

    // Test the result and assert its correctness
    callAssertions(fibonacci, n);

    // Print the results
    printf("Fibonacci Series:");
    for (int i = 0; i < n; i++) {
        printf(" %d", fibonacci[i]);
    }
    printf("\n");

    return 0;
}

// Private
int calculate(int *fibonacci, int n) {
    int i;
    int t1 = 0, t2 = 1;
    fibonacci[0] = 0;
    fibonacci[1] = 1;

    int nextTerm = t1 + t2;

    for (i = 2; i < n; ++i) {
        fibonacci[i] = nextTerm;

        t1 = t2;
        t2 = nextTerm;
        nextTerm = t1 + t2;
    }

}

void callAssertions(int *fibonacci, int n) {
    assert(0 == fibonacci[0]);
    assert(1 == fibonacci[1]);
    assert(1 == fibonacci[2]);

    for (int i = 2; i < n; i++) {
        assert(fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2]);
    }
}
