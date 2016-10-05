# Install script for directory: /Users/AJ/stepcode

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

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/Users/AJ/stepcode/test/src/base/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/express/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/exppp/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/exp2cxx/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/exp2python/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/clstepcore/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/cleditor/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/cldai/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/clutils/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/src/cllazyfile/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/include/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/data/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/doc/cmake_install.cmake")

endif()

if(CMAKE_INSTALL_COMPONENT)
  set(CMAKE_INSTALL_MANIFEST "install_manifest_${CMAKE_INSTALL_COMPONENT}.txt")
else()
  set(CMAKE_INSTALL_MANIFEST "install_manifest.txt")
endif()

string(REPLACE ";" "\n" CMAKE_INSTALL_MANIFEST_CONTENT
       "${CMAKE_INSTALL_MANIFEST_FILES}")
file(WRITE "/Users/AJ/stepcode/test/${CMAKE_INSTALL_MANIFEST}"
     "${CMAKE_INSTALL_MANIFEST_CONTENT}")
