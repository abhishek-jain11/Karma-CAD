# Install script for directory: /Users/AJ/stepcode/src/base

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/Users/AJ/stepcode/../sc-install")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "Debug")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/lib" TYPE SHARED_LIBRARY FILES
    "/Users/AJ/stepcode/test/lib/libbase.2.0.0.dylib"
    "/Users/AJ/stepcode/test/lib/libbase.2.dylib"
    "/Users/AJ/stepcode/test/lib/libbase.dylib"
    )
  foreach(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libbase.2.0.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libbase.2.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libbase.dylib"
      )
    if(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      execute_process(COMMAND "/usr/bin/install_name_tool"
        -id "/usr/local/lib/libbase.2.dylib"
        "${file}")
      if(CMAKE_INSTALL_DO_STRIP)
        execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" "${file}")
      endif()
    endif()
  endforeach()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode/base" TYPE FILE FILES
    "/Users/AJ/stepcode/src/base/sc_benchmark.h"
    "/Users/AJ/stepcode/src/base/sc_memmgr.h"
    "/Users/AJ/stepcode/src/base/sc_getopt.h"
    "/Users/AJ/stepcode/src/base/sc_trace_fprintf.h"
    "/Users/AJ/stepcode/src/base/sc_mkdir.h"
    "/Users/AJ/stepcode/src/base/sc_nullptr.h"
    "/Users/AJ/stepcode/src/base/path2str.h"
    "/Users/AJ/stepcode/src/base/judy/src/judy.h"
    "/Users/AJ/stepcode/src/base/judy/src/judyLArray.h"
    "/Users/AJ/stepcode/src/base/judy/src/judyL2Array.h"
    "/Users/AJ/stepcode/src/base/judy/src/judySArray.h"
    "/Users/AJ/stepcode/src/base/judy/src/judyS2Array.h"
    )
endif()

