# Install script for directory: /Users/AJ/stepcode/data

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
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/bin" TYPE PROGRAM FILES "/Users/AJ/stepcode/test/bin/schema_scanner")
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/Users/AJ/stepcode/test/schemas/sdai_ISO15926/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap203/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap203e2/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap209/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap210e2/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap210e3/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap214e3/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap219/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap227/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap235/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap238/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap239/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap240/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ap242/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_ifc2x3/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_IFC4/cmake_install.cmake")
  include("/Users/AJ/stepcode/test/schemas/sdai_pdm/cmake_install.cmake")

endif()

