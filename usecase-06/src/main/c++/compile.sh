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
$LLVM_TOOLCHAIN/clang++ -fPIC -shared $SCRIPT_DIR/prime.cpp -lpolyglot-mock -o lib/prime.so
