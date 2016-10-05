# Install script for directory: /Users/AJ/stepcode/include

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
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode/express" TYPE FILE FILES
    "/Users/AJ/stepcode/include/express/alg.h"
    "/Users/AJ/stepcode/include/express/basic.h"
    "/Users/AJ/stepcode/include/express/caseitem.h"
    "/Users/AJ/stepcode/include/express/dict.h"
    "/Users/AJ/stepcode/include/express/entity.h"
    "/Users/AJ/stepcode/include/express/error.h"
    "/Users/AJ/stepcode/include/express/expbasic.h"
    "/Users/AJ/stepcode/include/express/expr.h"
    "/Users/AJ/stepcode/include/express/express.h"
    "/Users/AJ/stepcode/include/express/hash.h"
    "/Users/AJ/stepcode/include/express/lexact.h"
    "/Users/AJ/stepcode/include/express/linklist.h"
    "/Users/AJ/stepcode/include/express/memory.h"
    "/Users/AJ/stepcode/include/express/object.h"
    "/Users/AJ/stepcode/include/express/resolve.h"
    "/Users/AJ/stepcode/include/express/schema.h"
    "/Users/AJ/stepcode/include/express/scope.h"
    "/Users/AJ/stepcode/include/express/stmt.h"
    "/Users/AJ/stepcode/include/express/symbol.h"
    "/Users/AJ/stepcode/include/express/type.h"
    "/Users/AJ/stepcode/include/express/variable.h"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode/exppp" TYPE FILE FILES "/Users/AJ/stepcode/include/exppp/exppp.h")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode" TYPE FILE FILES
    "/Users/AJ/stepcode/include/ordered_attrs.h"
    "/Users/AJ/stepcode/include/sc_export.h"
    "/Users/AJ/stepcode/include/sc_stdbool.h"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode" TYPE FILE FILES
    "/Users/AJ/stepcode/test/include/sc_cf.h"
    "/Users/AJ/stepcode/test/include/sc_version_string.h"
    )
endif()

