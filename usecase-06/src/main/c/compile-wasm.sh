#!/usr/bin/env bash
get_abs_filename() {
  # $1 : relative filename
  echo "$(cd "$(dirname "$1")" && pwd)/$(basename "$1")"
}
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR
MAIN_DIR=$(realpath ../../..)
cd $MAIN_DIR/target
mkdir -p lib
export LLVM_TOOLCHAIN=$(lli --print-toolchain-path)
export CPATH=/usr/include:/usr/include/x86_64-linux-gnu:/usr/incl
clang --target=wasm32 -emit-llvm -c -S -o lib/fibonacci.ll $SCRIPT_DIR/fibonacci.c
llc-10 -march=wasm32 -filetype=obj lib/fibonacci.ll
docker run --rm -v $(pwd):/src -u $(id -u):$(id -g) emscripten/emsdk emcc --Builtins=wasi_snapshot_preview1 $SCRIPT_DIR/fibonacci.c -o lib/fibonacci.js

