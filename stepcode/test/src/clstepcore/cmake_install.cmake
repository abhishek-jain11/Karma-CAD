# Install script for directory: /Users/AJ/stepcode/src/clstepcore

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
    "/Users/AJ/stepcode/test/lib/libstepcore.2.0.0.dylib"
    "/Users/AJ/stepcode/test/lib/libstepcore.2.dylib"
    "/Users/AJ/stepcode/test/lib/libstepcore.dylib"
    )
  foreach(file
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepcore.2.0.0.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepcore.2.dylib"
      "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/lib/libstepcore.dylib"
      )
    if(EXISTS "${file}" AND
       NOT IS_SYMLINK "${file}")
      execute_process(COMMAND "/usr/bin/install_name_tool"
        -id "/usr/local/lib/libstepcore.2.dylib"
        -change "/Users/AJ/stepcode/test/lib/libbase.2.dylib" "/usr/local/lib/libbase.2.dylib"
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
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/stepcode/clstepcore" TYPE FILE FILES
    "/Users/AJ/stepcode/src/clstepcore/aggrTypeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/attrDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/attrDescriptorList.h"
    "/Users/AJ/stepcode/src/clstepcore/baseType.h"
    "/Users/AJ/stepcode/src/clstepcore/complexSupport.h"
    "/Users/AJ/stepcode/src/clstepcore/create_Aggr.h"
    "/Users/AJ/stepcode/src/clstepcore/derivedAttribute.h"
    "/Users/AJ/stepcode/src/clstepcore/dictSchema.h"
    "/Users/AJ/stepcode/src/clstepcore/dictdefs.h"
    "/Users/AJ/stepcode/src/clstepcore/dictionaryInstance.h"
    "/Users/AJ/stepcode/src/clstepcore/dispnode.h"
    "/Users/AJ/stepcode/src/clstepcore/dispnodelist.h"
    "/Users/AJ/stepcode/src/clstepcore/entityDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/entityDescriptorList.h"
    "/Users/AJ/stepcode/src/clstepcore/enumTypeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/ExpDict.h"
    "/Users/AJ/stepcode/src/clstepcore/explicitItemId.h"
    "/Users/AJ/stepcode/src/clstepcore/globalRule.h"
    "/Users/AJ/stepcode/src/clstepcore/implicitItemId.h"
    "/Users/AJ/stepcode/src/clstepcore/instmgr.h"
    "/Users/AJ/stepcode/src/clstepcore/interfaceSpec.h"
    "/Users/AJ/stepcode/src/clstepcore/interfacedItem.h"
    "/Users/AJ/stepcode/src/clstepcore/inverseAttribute.h"
    "/Users/AJ/stepcode/src/clstepcore/inverseAttributeList.h"
    "/Users/AJ/stepcode/src/clstepcore/mgrnode.h"
    "/Users/AJ/stepcode/src/clstepcore/mgrnodearray.h"
    "/Users/AJ/stepcode/src/clstepcore/mgrnodelist.h"
    "/Users/AJ/stepcode/src/clstepcore/needFunc.h"
    "/Users/AJ/stepcode/src/clstepcore/read_func.h"
    "/Users/AJ/stepcode/src/clstepcore/realTypeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/Registry.h"
    "/Users/AJ/stepcode/src/clstepcore/schRename.h"
    "/Users/AJ/stepcode/src/clstepcore/sdai.h"
    "/Users/AJ/stepcode/src/clstepcore/sdaiApplication_instance.h"
    "/Users/AJ/stepcode/src/clstepcore/sdaiSelect.h"
    "/Users/AJ/stepcode/src/clstepcore/selectTypeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/SingleLinkList.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggregate.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrBinary.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrEntity.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrEnum.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrGeneric.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrInt.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrReal.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrSelect.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPaggrString.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPattribute.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPattributeList.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPcomplex.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPinvAttrList.h"
    "/Users/AJ/stepcode/src/clstepcore/STEPundefined.h"
    "/Users/AJ/stepcode/src/clstepcore/stringTypeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/SubSuperIterators.h"
    "/Users/AJ/stepcode/src/clstepcore/typeDescriptor.h"
    "/Users/AJ/stepcode/src/clstepcore/typeDescriptorList.h"
    "/Users/AJ/stepcode/src/clstepcore/typeOrRuleVar.h"
    "/Users/AJ/stepcode/src/clstepcore/uniquenessRule.h"
    "/Users/AJ/stepcode/src/clstepcore/whereRule.h"
    )
endif()

