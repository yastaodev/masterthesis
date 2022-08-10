package main

// #cgo LDFLAGS: -ldistance
// #include <stdlib.h>
// #include <stdio.h>
// #include "libdistance.h"
// int myfunc() {
//   graal_isolate_t *isolate = NULL;
//   graal_isolatethread_t *thread = NULL;
//   if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
//     fprintf(stderr, "graal_create_isolate error\n");
//     return 1;
//   }
//   double a_lat   = 59;
//   printf("%.2f km\n", distance(thread, a_lat));
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
