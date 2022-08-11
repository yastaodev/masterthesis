package main

// #cgo LDFLAGS: -lpassword
// #include <stdlib.h>
// #include <stdio.h>
// #include "libpassword.h"
// int myfunc() {
//   graal_isolate_t *isolate = NULL;
//   graal_isolatethread_t *thread = NULL;
//   if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
//     fprintf(stderr, "graal_create_isolate error\n");
//     return 1;
//   }
//   printf("%.2f km\n", 12.12);
//   char *result = NULL;
//   result = generatePassword(thread);
//   printf("%.2f km\n", 15.15);
//   printf("result: %s\n", result);
//   if (graal_detach_thread(thread) != 0) {
//     fprintf(stderr, "graal_detach_thread error\n");
//     return 1;
//   }
//   return 0;
// }
import "C"
func main() {

    C.myfunc()

}
