# Install script for directory: /Users/AJ/stepcode/src/cleditor

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
    "/Users/AJ/stepcode/test/lib/libstepeditor.2.0.0.dylib"
    "/Users/AJ/stepcode/test/lib/libstepeditor.2.dylib"
    "/Users/AJ/stepcode/test/lib/libstepeditor.dylib"
    )
  foreach(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepeditor.2.0.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepeditor.2.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepeditor.dylib"
      )
    if(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      execute_process(COMMAND "/usr/bin/install_name_tool"
        -id "/usr/local/lib/libstepeditor.2.dylib"
        -change "/Users/AJ/stepcode/test/lib/libbase.2.dylib" "/usr/local/lib/libbase.2.dylib"
        -change "/Users/AJ/stepcode/test/lib/libstepcore.2.dylib" "/usr/local/lib/libstepcore.2.dylib"
        -change "/Users/AJ/stepcode/test/lib/libstepdai.2.dylib" "/usr/local/lib/libstepdai.2.dylib"
        -change "/Users/AJ/stepcode/test/lib/libsteputils.2.dylib" "/usr/local/lib/libsteputils.2.dylib"
        "${file}")
      if(CMAKE_INSTALL_DO_STRIP)
        execute_process(COMMAND "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/strip" "${file}")
      endif()
    endif()
  endforeach()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode/cleditor" TYPE FILE FILES
    "/Users/AJ/stepcode/src/cleditor/STEPfile.h"
    "/Users/AJ/stepcode/src/cleditor/cmdmgr.h"
    "/Users/AJ/stepcode/src/cleditor/editordefines.h"
    "/Users/AJ/stepcode/src/cleditor/SdaiHeaderSchema.h"
    "/Users/AJ/stepcode/src/cleditor/SdaiHeaderSchemaClasses.h"
    "/Users/AJ/stepcode/src/cleditor/SdaiSchemaInit.h"
    "/Users/AJ/stepcode/src/cleditor/seeinfodefault.h"
    )
endif()

