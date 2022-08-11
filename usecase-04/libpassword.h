#ifndef __LIBPASSWORD_H
#define __LIBPASSWORD_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

char* generatePassword(graal_isolatethread_t*);

void vmLocatorSymbol(graal_isolatethread_t* thread);

void dumpImageMethods();

#if defined(__cplusplus)
}
#endif
#endif
