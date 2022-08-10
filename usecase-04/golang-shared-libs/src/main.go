package main

// #cgo CFLAGS: -g -Wall
// #cgo LDFLAGS: -ldistance
// #include <stdlib.h>
// #include "greeter.h"
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
import (
	"fmt"
	"unsafe"
)

func main() {

    C.myfunc()

	name := C.CString("Gopher")
	defer C.free(unsafe.Pointer(name))

	year := C.int(2018)

	ptr := C.malloc(C.sizeof_char * 1024)
	defer C.free(unsafe.Pointer(ptr))

	size := C.greet(name, year, (*C.char)(ptr))

	b := C.GoBytes(ptr, size)
	fmt.Println(string(b))
}
