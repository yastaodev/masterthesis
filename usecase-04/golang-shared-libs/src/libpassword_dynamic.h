#ifndef __LIBPASSWORD_H
#define __LIBPASSWORD_H

#include <graal_isolate_dynamic.h>


#if defined(__cplusplus)
extern "C" {
#endif

typedef char* (*generatePassword_fn_t)(graal_isolatethread_t*);

typedef void (*vmLocatorSymbol_fn_t)(graal_isolatethread_t* thread);

typedef void (*dumpImageMethods_fn_t)();

#if defined(__cplusplus)
}
#endif
#endif
