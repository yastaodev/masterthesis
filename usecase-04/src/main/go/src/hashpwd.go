package main

// #cgo LDFLAGS: -lpwd
// #include <stdlib.h>
// #include <stdio.h>
// #include <libpwd.h>
// const char* generatePwd() {
//   graal_isolate_t *isolate = NULL;
//   graal_isolatethread_t *thread = NULL;
//   if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
//     fprintf(stderr, "graal_create_isolate error\n");
//     return NULL;
//   }
//   char *result = NULL;
//   result = generatePassword(thread);
//   if (graal_detach_thread(thread) != 0) {
//     fprintf(stderr, "graal_detach_thread error\n");
//     return NULL;
//   }
//   return result;
// }
import "C"
import (
    "fmt"
    "crypto/sha256"
)
func main() {

    pwd := C.GoString(C.generatePwd())
    fmt.Printf("Java: generated password: %s\n", pwd)

    hash := sha256.Sum256([]byte(pwd))
    fmt.Printf("GoLang: calculated hash: %x\n", hash)

}
