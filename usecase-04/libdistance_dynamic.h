#ifndef __LIBDISTANCE_H
#define __LIBDISTANCE_H

#include <graal_isolate_dynamic.h>


#if defined(__cplusplus)
extern "C" {
#endif

typedef double (*distance_fn_t)(graal_isolatethread_t*, double);

typedef void (*vmLocatorSymbol_fn_t)(graal_isolatethread_t* thread);

typedef void (*dumpImageMethods_fn_t)();

#if defined(__cplusplus)
}
#endif
#endif
