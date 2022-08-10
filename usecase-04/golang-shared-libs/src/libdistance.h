#ifndef __LIBDISTANCE_H
#define __LIBDISTANCE_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

double distance(graal_isolatethread_t*, double);

void vmLocatorSymbol(graal_isolatethread_t* thread);

void dumpImageMethods();

#if defined(__cplusplus)
}
#endif
#endif
